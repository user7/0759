package com.androcourse.notebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<User> userList;
    Button addUserBtn;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // задаём лейаут для RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        addUserBtn = findViewById(R.id.addButton);
        addUserBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserFormActivity.class);
            startActivity(intent);
        });
    }

    private void recyclerViewInit() {
        Users users = new Users(MainActivity.this);
        userList = users.getUserList();
        userAdapter = new UserAdapter(userList);
        recyclerView.setAdapter(userAdapter);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(1, 18);
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerViewInit();
    }

    // класс который связывает элемент типа View списка с элементом данных из Users
    class UserHolder extends RecyclerView.ViewHolder {
        TextView itemTextView;
        User user;

        public UserHolder(ViewGroup viewGroup) {
            super(LayoutInflater.from(MainActivity.this).inflate(R.layout.single_item, viewGroup, false));
            // itemView унаследован от RecyclerView.ViewHolder
            // при создании запомнили свой View
            itemTextView = itemView.findViewById(R.id.itemTextView);
            itemTextView.setOnClickListener(v -> {
                User user = UserHolder.this.user;
                Intent intent = new Intent(MainActivity.this, UserFormActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            });
        }

        public void bind(User user) {
            // связывание, отображаем данные из объекта User во View
            itemTextView.setText(user.getUserFullName());
            this.user = user;
        }
    }

    // адаптер предоставляет отдельные объекты из users для отображения в RecyclerView
    class UserAdapter extends RecyclerView.Adapter<UserHolder> {
        ArrayList<User> users;

        public UserAdapter(ArrayList<User> users) {
            this.users = users;
        }

        @Override
        public UserHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            // на каждый отображаемый элемент в RecyclerView создаем UserHolder
            return new UserHolder(viewGroup);
        }

        @Override
        public void onBindViewHolder(UserHolder userHolder, int position) {
            // просим UserHolder отобразить другого юзера
            userHolder.bind(users.get(position));
        }

        @Override
        public int getItemCount() {
            // размер для скроллинга
            return users.size();
        }
    }
}