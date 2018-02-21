package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject jObject = new JSONObject(json);
        String jsonName = jObject.getString("name");

        JSONObject jObjectName = new JSONObject(jsonName);
        String jsonMainName = jObjectName.getString("mainName");
//        String jsonMainName = jObject.getString("mainName");
//        String jsonKnownAs = jObject.getString("alsoKnownAs");
        Log.i(TAG, "parseSandwichJson: " + jObject);
        Log.i(TAG, "parseSandwichJson: " + jsonName);
        Log.i(TAG, "parseSandwichJson: " + jsonMainName);
        return null;
    }
}
