package com.example.jsonmoviedb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView showJson = (ImageView) findViewById(R.id.json);
        ImageView showJsonAPI = (ImageView) findViewById(R.id.jsonAPI);
        ImageView movieDB = (ImageView) findViewById(R.id.movieDB);

        showJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JsonParse.class );
                startActivity(intent);
            }
        });
        showJsonAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JsonApi.class );
                startActivity(intent);
            }
        });
        movieDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MovieDB.class );
                startActivity(intent);
            }
        });
    }
}