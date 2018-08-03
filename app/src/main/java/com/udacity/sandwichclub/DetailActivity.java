package com.udacity.sandwichclub;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private final String BULLET_POINT = "\u2022";
    @BindView(R.id.origin_tv)
    TextView mainName_tv;
    @BindView(R.id.also_known_inv_tv)
    TextView inv_alsoKnown_tv;
    @BindView(R.id.also_known_tv)
    TextView alsoKnown_tv;
    @BindView(R.id.description_tv)
    TextView description_tv;
    @BindView(R.id.ingredients_tv)
    TextView ingredients_tv;
    @BindView(R.id.place_of_origin_tv)
    TextView placeOfOrigin_tv;
    @BindView(R.id.image_iv)
    ImageView image_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

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
                .into(image_iv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //to display Main Name
        mainName_tv.setText(sandwich.getMainName());
        //To display Other Names ie: Also known as names
        String otherNames = TextUtils.join("\n" + BULLET_POINT, sandwich.getAlsoKnownAs());
        if (sandwich.getAlsoKnownAs().size() == 1 || otherNames.isEmpty()) {
            //if also known As is empty display no data available
            if (otherNames.isEmpty()) {
                otherNames = getResources().getString(R.string.no_data);
            }
            alsoKnown_tv.setVisibility(View.VISIBLE);
            alsoKnown_tv.setText(otherNames);
        } else {
            inv_alsoKnown_tv.setVisibility(View.VISIBLE);
            inv_alsoKnown_tv.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
            inv_alsoKnown_tv.setText(BULLET_POINT + otherNames);
        }
        //To display Place of Origin
        String place = sandwich.getPlaceOfOrigin();
        //if Place of Origin is empty display no data available
        if (place.isEmpty()) {
            place = getResources().getString(R.string.no_data);
        }
        placeOfOrigin_tv.setText(place);
        //To display description
        description_tv.setText(sandwich.getDescription());
        //To display ingredients
        String ingredientsList = BULLET_POINT;
        ingredientsList += TextUtils.join("\n" + BULLET_POINT, sandwich.getIngredients());
        ingredients_tv.setText(ingredientsList);

    }
}
