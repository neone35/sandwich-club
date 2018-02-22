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

    private static final String JSON_NAME = "name";
    private static final String JSON_MAIN_NAME = "mainName";
    private static final String JSON_KNOWN_AS = "alsoKnownAs";
    private static final String JSON_IMAGE = "image";
    private static final String JSON_DESCRIPTION = "description";
    private static final String JSON_ORIGIN = "placeOfOrigin";
    private static final String JSON_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject jObject = new JSONObject(json);
        Log.d(TAG, "received JSONObject: " + jObject.toString(4));

        // Get "name" object and parse "mainName" and "alsoKnownAs" fields
        // Use optString (returns "") instead of getString (JSONException)
        String jsonName = jObject.optString(JSON_NAME);
        JSONObject jObjectName = new JSONObject(jsonName);
        String jsonMainName = jObjectName.optString(JSON_MAIN_NAME);
        JSONArray jArrayKnownAs = jObjectName.getJSONArray(JSON_KNOWN_AS);

        // Get other fields from main JSONObject
        String jsonImgUrl = jObject.optString(JSON_IMAGE);
        String jsonDescription = jObject.optString(JSON_DESCRIPTION);
        String jsonOrigin = jObject.optString(JSON_ORIGIN);
        JSONArray jArrayIngredients = jObject.getJSONArray(JSON_INGREDIENTS);

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
