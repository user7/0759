package com.androcourse.baryga;

import java.io.Serializable;
import java.util.ArrayList;

public class Record implements Serializable {
    private String name = "";
    private int price = 0;
    private boolean checked = false;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

class Records extends ArrayList<Record> {
}