package com.example.ouvrier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Verif extends AppCompatActivity {
private TextView Email;
private Button btn;


    private TextView usernameTextView;
    private TextView emailTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verif);
        Email = findViewById(R.id.textViewEmail);
        btn =  findViewById(R.id.buttoncoed);



        usernameTextView = findViewById(R.id.textViewUsername);
        emailTextView = findViewById(R.id.textViewEmail);

        Intent intent = getIntent();
        if (intent != null) {
            String username = intent.getStringExtra("username");
            String email = intent.getStringExtra("email");

            usernameTextView.setText(username);
            emailTextView.setText(email);
        }





        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Decconnexion();
            }
        });

        try {
            SharedPreferences p = getSharedPreferences("myData1", Context.MODE_PRIVATE);
            //Afficher
            String email = p.getString("email", null);

            Email.setText(email);











        }catch (Exception e){
            Email.setText(e.getMessage().toString());
            System.out.println(e);
        }

    }


    public void Decconnexion(){
        SharedPreferences p = getSharedPreferences("myData1", Context.MODE_PRIVATE);

        SharedPreferences.Editor ed = p.edit();

        ed.putString("utilisateur", "");
        ed.putString("email", "");
        ed.putString("motdepasse", "");
        ed.commit();
        Toast.makeText(this, "Vous êtes bien déconnecté", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(), Accueil.class);
        startActivity(i);


    }


















































   // public class UserDetailsActivity extends AppCompatActivity {

   // }

}