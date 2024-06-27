package com.example.ouvrier;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import java.net.URI;

public class Accueil extends AppCompatActivity {
    private Button MonBouton;
    private TextView CreerUtili;
    private TextView CreerOuvrier;

    private TextView ConnexionProf;
    private TextView ConnexionUtili;
    private String nom;
    private String email;
    private String mdp;
    private String etat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        /*
        * Lecture des données
        * */

         try {
             SharedPreferences p = getSharedPreferences("myData1", Context.MODE_PRIVATE);
             //Afficher
             //String nom = p.getString("utilisateur", null);
             String email = p.getString("email", null);
             String mdp = p.getString("motdepasse", null);
             String etat = p.getString("etat", null);


             if(email.equals("")){
                 setContentView(R.layout.activity_accueil);

             }else {

                 if(etat.equals("utilisateur")){
                     Intent i =new Intent(getApplicationContext(), MainActivity.class );
                     i.putExtra("username" , nom);
                     i.putExtra("email" , email);
                     //i.putExtra("mdp" , mdp);

                     startActivity(i);
                     finish();
                 }else{
                     Intent i =new Intent(getApplicationContext(), Prof_Connecter.class );
                     i.putExtra("username" , nom);
                     i.putExtra("email" , email);
                     //i.putExtra("mdp" , mdp);

                     startActivity(i);
                     finish();
                 }
             }
         }catch (Exception e){
             System.out.println(e);
         }
        /*-------------------------------------------*/
       MonBouton = findViewById(R.id.BtnIgnorer);
       CreerUtili = findViewById(R.id.creer_Utilisa);
       CreerOuvrier =  findViewById(R.id.creer_Prof);
        ConnexionProf = findViewById(R.id.Connecter_prof);
       ConnexionUtili = findViewById(R.id.Connecter_utilisateur);

        MonBouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FirebaseAuth mAuth = FirebaseAuth.getInstance();
                //mAuth.signOut();

                Intent i =new Intent(getApplicationContext(), Guide.class);
                startActivity(i);
            }
        });

        CreerOuvrier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii =new Intent(getApplicationContext(), Creer_compte.class);
                startActivity(ii);
            }
        });

        CreerUtili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iii =new Intent(getApplicationContext(), CreerutilisateurActivity.class);
                startActivity(iii);
            }
        });
        ConnexionUtili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iii =new Intent(getApplicationContext(), Connection_User.class);
                startActivity(iii);
            }
        });
        ConnexionProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iii =new Intent(getApplicationContext(), Connexion_Prof.class);
                startActivity(iii);
            }
        });


  }




    // Méthode pour enregistrer la langue sélectionnée dans SharedPreferences
    private void saveLanguagePreference(String languageCode) {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("language", languageCode);
        editor.apply();
    }

}
