package com.example.ouvrier;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Second extends AppCompatActivity {
    private TextView Connexion;
    private String user;
    private TextView Lien;

    private String Nom;
    private String Postnom;
    private String Prenom;
    private String Email;
    private String Adressephysique;
    private String Adressepostale;
    private String  Num;

    private TextView nom;
    private TextView postnom;
    private TextView prenom;
    private TextView email;
    private TextView adressePhysique;
    private TextView adressePostale;
    private TextView num;

    private Button SMS;
    private Button EnvoyerEmail;

    private  TextView Villes_interventionE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_second);


        Villes_interventionE =  findViewById(R.id.villes_intervention);


        nom= findViewById(R.id.nom);
        postnom= findViewById(R.id.postnom);
        prenom= findViewById(R.id.prenom);
        email = findViewById(R.id.email);
        // adressephysique= findViewById(R.id.):
        //adressepostale= findViewById(R.id.):
        num= findViewById(R.id.num);

        EnvoyerEmail = findViewById(R.id.buttonEmail);
        SMS = findViewById(R.id.buttonSMS);
        // = findViewById(R.id.):
        Lien = findViewById(R.id.textview_numero);


        String userEmail = getIntent().getStringExtra("USER_EMAIL");

        if (userEmail == null) {
            //textViewDetails.setText("Utilisateur non trouvé.");
            return;
        }



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ouvrier");
        Query query = databaseReference.orderByChild("email").equalTo(userEmail);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Ouvrier ouvrier = dataSnapshot.getValue(Ouvrier.class);
                        if (ouvrier != null) {


                            String details = "Nom: " + ouvrier.getNom() + "\n" +
                                    "Prénom: " + ouvrier.getPrenom() + "\n" +
                                    "Adresse: " + ouvrier.getAdresse() + "\n" +
                                    "Email: " + ouvrier.getEmail() + "\n" +
                                    "Expertise: " + ouvrier.getExpertise() + "\n" +
                                    "Numéro: " + ouvrier.getNumero();
                            //textViewDetails.setText(details);
                            if (dataSnapshot.hasChild("villes_intervention")) {
                                StringBuilder villesBuilder = new StringBuilder();
                                DataSnapshot villesSnapshot = dataSnapshot.child("villes_intervention");

                                for (DataSnapshot villeSnapshot : villesSnapshot.getChildren()) {
                                    String ville = villeSnapshot.getKey(); // Récupérer le nom de la ville
                                    Boolean isActive = villeSnapshot.getValue(Boolean.class); // Vérifier si la ville est active (true/false)
                                    villesBuilder.append(ville).append(", "); // Ajouter la ville à la chaîne
                                }

                                // Retirer la dernière virgule et espace de la chaîne
                                String villesIntervention = villesBuilder.toString();
                                if (villesIntervention.endsWith(", ")) {
                                    villesIntervention = villesIntervention.substring(0, villesIntervention.length() - 2);
                                }
                                Villes_interventionE.setText(villesIntervention);

                            }

                                nom.setText(ouvrier.getNom());
                           // postnom.setText(Postnom);
                            prenom.setText(ouvrier.getPrenom());
                            email.setText(ouvrier.getEmail());

                            num.setText(ouvrier.getNumero());

                           // Num=ouvrier.getNumero();



                            Lien.setText("+243 " + ouvrier.getNumero().toString());

                            Lien.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(Intent.ACTION_DIAL);
                                    i.setData(Uri.parse("tel:" + ouvrier.getNumero().toString()));
                                    startActivity(i);
                                }
                            });
                            Button Second = findViewById(R.id.button_second);
                            Second.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(i);
                                }
                            });

                            EnvoyerEmail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                                    emailIntent.setData(Uri.parse("mailto:" + ouvrier.getEmail().toString()));
                                    startActivity(Intent.createChooser(emailIntent, "Send email..."));

                                }
                            });

                            SMS.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                                    smsIntent.setData(Uri.parse("smsto:" + ouvrier.getNumero().toString()));
                                    startActivity(smsIntent);
                                }
                            });



                        } else {
                            //textViewDetails.setText("Les détails de l'utilisateur ne peuvent pas être chargés.");
                        }
                    }
                } else {
                    //textViewDetails.setText("L'utilisateur n'existe pas.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
               // textViewDetails.setText("Erreur de chargement des données.");
            }
        });


    }
}