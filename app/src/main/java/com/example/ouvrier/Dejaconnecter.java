package com.example.ouvrier;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Dejaconnecter extends AppCompatActivity {

    private TextView Extraname;
    private String username;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

       // Extraname = findViewById(R.id.UserConnect);
        username = getIntent().getStringExtra("username");
        Extraname.setVisibility(View.VISIBLE);
        Extraname.setText(username);
    }
}