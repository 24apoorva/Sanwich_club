package com.udacity.sandwichclub.utils;

import android.content.Context;

//import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        //Creating and empty Sandwich to add sandwich details
        Sandwich item = null;
        JSONObject sampleResponse = null;
        try {
            sampleResponse = new JSONObject(json);
            JSONObject name = sampleResponse.getJSONObject("name");
            String mainName = name.getString("mainName");
            JSONArray arr = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnown = convJSONArrayToList(arr);
            String placeOfOrig = sampleResponse.getString("placeOfOrigin");
            String description = sampleResponse.getString("description");
            String imageUrl = sampleResponse.getString("image");
            JSONArray ing = sampleResponse.getJSONArray("ingredients");
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
     * @param array - Input JSONArray
     * @return List
     */
    public static List<String> convJSONArrayToList(JSONArray array) {

        List<String> list = new ArrayList<String>();
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
