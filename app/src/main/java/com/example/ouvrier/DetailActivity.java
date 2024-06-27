package com.example.ouvrier;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DetailActivity extends AppCompatActivity {

    private TextView textViewDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textViewDetails = findViewById(R.id.textViewDetails);

        String userEmail = getIntent().getStringExtra("USER_EMAIL");

        if (userEmail == null) {
            textViewDetails.setText("Utilisateur non trouvé.");
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
                            textViewDetails.setText(details);
                        } else {
                            textViewDetails.setText("Les détails de l'utilisateur ne peuvent pas être chargés.");
                        }
                    }
                } else {
                    textViewDetails.setText("L'utilisateur n'existe pas.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                textViewDetails.setText("Erreur de chargement des données.");
            }
        });
    }
}
