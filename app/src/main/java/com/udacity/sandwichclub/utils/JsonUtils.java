package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject jObject = new JSONObject(json);
        Log.d(TAG, "received JSONObject: " + jObject.toString(4));

        // Get "name" object and parse "mainName" and "alsoKnownAs" fields
        String jsonName = jObject.getString("name");
        JSONObject jObjectName = new JSONObject(jsonName);
        String jsonMainName = jObjectName.getString("mainName");
        JSONArray jArrayKnownAs = jObjectName.getJSONArray("alsoKnownAs");

        // Get other fields from main JSONObject
        String jsonImgUrl = jObject.getString("image");
        String jsonDescription = jObject.getString("description");
        String jsonOrigin = jObject.getString("placeOfOrigin");
        JSONArray jArrayIngredients = jObject.getJSONArray("ingredients");

        Sandwich sandwich = new Sandwich();
        sandwich.setMainName(jsonMainName);
        sandwich.setImage(jsonImgUrl);
        sandwich.setPlaceOfOrigin(jsonOrigin);
        sandwich.setDescription(jsonDescription);
        sandwich.setIngredients(toStringList(jArrayIngredients));
        sandwich.setAlsoKnownAs(toStringList(jArrayKnownAs));
        Log.d(TAG, "parsed object name: " + sandwich.getMainName());

        return sandwich;
    }

    private static List<String> toStringList(JSONArray jArray) {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < jArray.length(); i++) {
            try {
                stringList.add(jArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return stringList;
    }

}
