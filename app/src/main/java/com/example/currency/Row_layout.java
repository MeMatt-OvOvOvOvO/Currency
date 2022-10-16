package com.example.currency;

import static com.example.currency.SomeCode.getCurrencyBitmapFromCode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class Row_layout extends AppCompatActivity {
    ImageView image;
    TextView textViewCode;
    TextView tv1;
    TextView firstNumbers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_row_layout);

        image = findViewById(R.id.image);
        textViewCode = findViewById(R.id.textViewCode);
        tv1 = findViewById(R.id.tv1);
        firstNumbers = findViewById(R.id.firstNumbers);




        image.setImageResource(R.drawable.ic_baseline_bar_chart_24);

    }


}