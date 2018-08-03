package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String FALL_BACK_STRING = "";
    private static final String JSON_NAME = "name";
    private static final String JSON_MAIN_NAME = "mainName";
    private static final String JSON_PLACE = "placeOfOrigin";
    private static final String JSON_DESCRIPTION = "description";
    private static final String JSON_IMAGE = "image";
    private static final String JSON_ALSO_KNOWN = "alsoKnownAs";
    private static final String JSON_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        //Creating and empty Sandwich to add sandwich details
        Sandwich item = null;
        JSONObject sampleResponse;
        try {
            sampleResponse = new JSONObject(json);
            JSONObject name = sampleResponse.optJSONObject(JSON_NAME);
            String mainName = name.optString(JSON_MAIN_NAME, FALL_BACK_STRING);
            JSONArray arr = name.optJSONArray(JSON_ALSO_KNOWN);
            List<String> alsoKnown = convJSONArrayToList(arr);
            String placeOfOrig = sampleResponse.optString(JSON_PLACE, FALL_BACK_STRING);
            String description = sampleResponse.optString(JSON_DESCRIPTION, FALL_BACK_STRING);
            String imageUrl = sampleResponse.optString(JSON_IMAGE, FALL_BACK_STRING);
            JSONArray ing = sampleResponse.optJSONArray(JSON_INGREDIENTS);
            List<String> ingredients = convJSONArrayToList(ing);
            item = new Sandwich(mainName, alsoKnown, placeOfOrig, description, imageUrl, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returns the list of sandwich details
        return item;
    }

    /**
     * Used to convert JSONArray to a List Array.
     *
     * @param array - Input JSONArray
     * @return List
     */
    private static List<String> convJSONArrayToList(JSONArray array) {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                list.add(array.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }
}
