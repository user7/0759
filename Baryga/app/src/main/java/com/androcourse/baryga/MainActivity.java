package com.androcourse.baryga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    TextView sum;
    Button add;
    RecyclerView recyclerView;
    Records records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            records = (Records) savedInstanceState.getSerializable("records");
        } else {
            records = new Records();
        }

        add = findViewById(R.id.addId);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runEditActivity(new Record());
            }
        });

        sum = findViewById(R.id.sumId);
        sum.setText("0");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("records", records);
    }

    void runEditActivity(Record r) {
        Log.d("===", "unimplemented");
    }
}