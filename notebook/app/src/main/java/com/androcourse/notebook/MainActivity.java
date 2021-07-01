package com.androcourse.notebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Users users = new Users();
    UserAdapter userAdapter = new UserAdapter(users);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // задаём лейаут для RecyclerView
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        // создаем фейковых пользователей
        String[] names = new String[] {"Анита", "Борис", "Виктория", "Григорий", "Диана", "Елена", "Жерар"};
        String[] lastNames = new String[] {"Смит", "Джонсон", "Штейн", "Круг", "Боня"};
        for (int i = 0; i < names.length * lastNames.length; ++i) {
            User user = new User();
            user.setUserName(names[i % names.length]);
            user.setUserLastName(lastNames[i % lastNames.length]);
            users.add(user);
        }

        // задаем адаптер для RecyclerView
        recyclerView.setAdapter(userAdapter);
    }

    // класс который связывает элемент типа View списка с элементом данных из Users
    class UserHolder extends RecyclerView.ViewHolder {
        TextView itemTextView;

        public UserHolder(ViewGroup viewGroup) {
            super(LayoutInflater.from(MainActivity.this).inflate(R.layout.single_item, viewGroup, false));
            // itemView унаследован от RecyclerView.ViewHolder
            // при создании запомнили свой View
            itemTextView = itemView.findViewById(R.id.itemTextView);
        }

        public void bind(User user) {
            // связывание, отображаем данные из объекта User во View
            itemTextView.setText(user.getUserName() + " " + user.getUserLastName());
        }
    }

    // адаптер предоставляет отдельные объекты из users для отображения в RecyclerView
    class UserAdapter extends RecyclerView.Adapter<UserHolder> {
        Users users;

        public UserAdapter(Users users) {
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