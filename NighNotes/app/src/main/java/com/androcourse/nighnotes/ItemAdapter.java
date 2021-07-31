package com.androcourse.nighnotes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    static String[] fakeItems = new String[] {
            "Hola",
            "Segnora",
            "Jack",
            "And",
            "Rosa",
            "item9",
            "item8",
            "item7",
            "item6",
            "item5",
            "item4",
            "the end",
    };

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ItemViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.item_view);
            Log.d("==", "constructor ItemViewHolder textView=" + textView);
        }

        public void setRecord(String record) {
            textView.setText(record);
        }
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_itemview, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ItemViewHolder holder, int position) {
        holder.setRecord(fakeItems[position]);
    }

    @Override
    public int getItemCount() {
        return fakeItems.length;
    }
}
