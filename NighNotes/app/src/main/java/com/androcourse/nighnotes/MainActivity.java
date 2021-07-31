package com.androcourse.nighnotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Db db;
    DbDao dbDao;
    ArrayList<Plane> planes;
    TabLayout tabLayout;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("==", "onCreate, тред: " + Thread.currentThread().getId());
        setContentView(R.layout.activity_main);
        db = Room.databaseBuilder(getApplicationContext(), Db.class, "nn").build();
        dbDao = db.dbDao();
        WeakReference<MainActivity> wmain = new WeakReference<>(this);
        new Thread(() -> loadPlanes(wmain)).start();
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("==", "onTabSelected " + tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d("==", "onTabUnselected " + tab.getText());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d("==", "onTabReselected " + tab.getText());
            }
        });

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);

        ItemAdapter itemAdapter = new ItemAdapter();
        recyclerView.setAdapter(itemAdapter);
    }

    static void loadPlanes(WeakReference<MainActivity> wmain) {
        Log.d("==", "начинается загрузка, DB тред: " + Thread.currentThread().getId());
        MainActivity main = wmain.get();
        DbDao dao = main.dbDao;
        ArrayList<Plane> planes = new ArrayList<>();
        List<DbItem> dbItems = dao.getKind("plane");
        for (DbItem it : dbItems) {
            Plane p = MainActivity.unpackPlane(it);
            if (p != null)
                planes.add(p);
        }
        if (dbItems.size() == 0) {
            Log.d("==", "создаём дефолтный плейн");
            DbItem item = null;
            Plane p = new Plane();
            item = packPlane(p);
            saveItem(dao, item); // updates id
            p.dbId = item.id;
            planes.add(p);
        }
        for (Plane plane : planes) {
            Log.d("==", "плэйн id=" + plane.dbId + " имя=" + plane.name + " пин=" + plane.pin);
        }
        main.planes = planes;
        Log.d("==", "загружено плейнов: " + main.planes.size());
        main.runOnUiThread(() -> main.loadCompleted());
    }

    void loadCompleted() {
        Log.d("==", "загрузка завершена, UI тред: " + Thread.currentThread().getId());
        for (Plane p : planes) {
            tabLayout.addTab(tabLayout.newTab().setText(p.name));
        }
        if (planes.size() == 1 && planes.get(0).name.equals(""))
            tabLayout.setVisibility(View.GONE);
        else
            tabLayout.setVisibility(View.VISIBLE);
    }

    static void saveItem(DbDao dao, DbItem item) {
        if (item.id == -1)
            item.id =  dao.insert(item.kind, item.data);
        else
            dao.update(item);
    }

    static Plane unpackPlane(DbItem item) {
        Plane plane = new Plane();
        plane.dbId = item.id;
        try {
            String jsonString = new String(item.data, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonString);
            plane.pin = jsonObject.optString("pin", "");
            plane.name = jsonObject.optString("name", "");
            JSONArray jsonRecords = jsonObject.optJSONArray("records");
            plane.records = new ArrayList<>();
            if (jsonRecords != null) {
                for (int i = 0; i < jsonRecords.length(); ++i) {
                    Record r = new Record();
                    JSONObject jr = jsonRecords.getJSONObject(i);
                    r.text = jr.optString("text", "");
                    Log.d("==", "record #" + i + " text=" + r.text);
                    plane.records.add(r);
                }
            }
            return plane;
        } catch (JSONException | UnsupportedEncodingException e) {
            Log.d("==", "unpackPlane битый json: " + e);
        }
        return null;
    }

    static DbItem packPlane(Plane plane) {
        DbItem item = new DbItem();
        item.id = plane.dbId;
        item.kind = "plane";
        JSONObject js = new JSONObject();
        try {
            js.put("pin", plane.pin);
            js.put("name", plane.name);
            JSONArray jr = new JSONArray();
            for (Record r : plane.records) {
                jr.put(new JSONObject().put("text", r.text));
            }
            js.put("records", jr);
            item.data = js.toString().getBytes("UTF-8");
        } catch (JSONException | UnsupportedEncodingException e) {
            Log.d("==", "packPlane битый json: " + e);
        }
        return item;
    }
}