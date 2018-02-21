package com.udacity.sandwichclub;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    ActivityDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        Intent intent = getIntent();

        int position = 0;
        if (intent != null) {
            position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        } else {
            closeOnError();
        }
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;

        // Try to parse received JSON
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        mBinding.tvMainName.setText(sandwich.getMainName());
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mBinding.imageIv);

        checkIfEmptyString(mBinding.tvOrigin, sandwich.getPlaceOfOrigin());
        mBinding.tvDescription.setText(sandwich.getDescription());
        checkIfEmptyList(mBinding.tvIngredients, sandwich.getIngredients());
        checkIfEmptyList(mBinding.tvAlsoKnownAs, sandwich.getAlsoKnownAs());


    }

    private void checkIfEmptyString(TextView textView, String modelString) {
        if (!modelString.isEmpty()) {
            textView.setText(modelString);
            return;
        }
        textView.setText(getResources().getText(R.string.unknown));
        textView.setTextColor(getResources().getColor(R.color.colorGray));
    }

    private void checkIfEmptyList(TextView textView, List<String> modelListString) {
        if (modelListString != null)
            if (!modelListString.isEmpty()) {
                textView.setText(TextUtils.join(", ", modelListString));
                return;
            }
        textView.setText(getResources().getText(R.string.unknown));
        textView.setTextColor(getResources().getColor(R.color.colorGray));
    }
}
