package com.androcourse.nighnotes;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

// represents data plane
public class Plane {
    public String pin = "";
    public String name = "";
    public ArrayList<Record> records = new ArrayList<>();
    public long dbId = -1;

    boolean isSavedToDb() {
        return dbId != -1;
    }
}
