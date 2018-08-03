package com.udacity.sandwichclub;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.image_unavailable)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //to display Main Name
        displayTextView(R.id.origin_tv, sandwich.getMainName());
        //To display Other Names ie: Also known as names
        String otherNames = convToString(sandwich.getAlsoKnownAs());
        TextView alsoKnown;
        if (sandwich.getAlsoKnownAs().size() == 1 || otherNames.isEmpty()) {
            if (otherNames.isEmpty()) {
                otherNames = getResources().getString(R.string.no_data);
            }
            alsoKnown = (TextView) findViewById(R.id.also_known_tv);
            alsoKnown.setVisibility(View.VISIBLE);
            //if alsoknownAs is empty display no data available

        } else {
            alsoKnown = (TextView) findViewById(R.id.also_known_inv_tv);
            alsoKnown.setVisibility(View.VISIBLE);
            alsoKnown.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
        }
        alsoKnown.setText(otherNames);
        //To display Place of Origin
        String place = sandwich.getPlaceOfOrigin();
        //if Place of Origin is empty display no data available
        if (place.isEmpty()) {
            place = getResources().getString(R.string.no_data);
        }
        displayTextView(R.id.place_of_origin_tv, place);
        //To display description
        displayTextView(R.id.description_tv, sandwich.getDescription());

        //To display ingredients
        String ingredientsList = convToString(sandwich.getIngredients());
        displayTextView(R.id.ingredients_tv, ingredientsList);

    }

    /**
     * This method is used to display TextView
     *
     * @param id     input id of the text view
     * @param string -- string to display
     */
    private void displayTextView(int id, String string) {
        TextView textView = (TextView) findViewById(id);
        textView.setText(string);
    }

    /**
     * This method can be used to convert list items to single string
     *
     * @param string List array
     * @return single string
     */
    private String convToString(List<String> string) {
        StringBuilder builder = new StringBuilder();
        int size = string.size();
        for (String otherNames : string) {
            if (string.size() == 1) {
                builder.append(otherNames);
                break;
            }
            if (size < 2) {
                builder.append("\u2022 " + otherNames);
            } else {
                builder.append("\u2022 " + otherNames + "\n");
                size--;
                Log.i("for my record", "size is " + size);

            }
        }
        return builder.toString();
    }
}
