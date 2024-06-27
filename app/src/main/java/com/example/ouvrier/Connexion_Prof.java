package com.example.ouvrier;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class Connexion_Prof extends AppCompatActivity {
    private Button BoutonSeVerifProf;
    private AlertDialog loadingDialog;

    private Button BoutonSeConnecterProf;
    private TextView CreeruncompteProf;
    private EditText NomutilisateurProf;
    private EditText MotdepasseProf;
    private TextView ErreurConnexionProf;
    private String usernameProf;
    private  String passwordProf;

    private  String MaReponseemailProf;
    private  String MaReponseidentiteProf;
    private  String MaReponsemdpProf;
    private  String TypeProf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion_prof);
        //getSupportActionBar().hide();


        BoutonSeConnecterProf = findViewById(R.id.btnLogin);
        ErreurConnexionProf=findViewById(R.id.error_connexion);
        NomutilisateurProf = findViewById(R.id.emailConnectProf);
        MotdepasseProf = findViewById(R.id.passwordConnectProf);
        CreeruncompteProf = findViewById(R.id.signUpText);

        BoutonSeConnecterProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameProf = NomutilisateurProf.getText().toString();
                passwordProf = MotdepasseProf.getText().toString();
                if (usernameProf.isEmpty()){
                    NomutilisateurProf.setError("Completez ici ....");
                    NomutilisateurProf.requestFocus();
                }
                if(passwordProf.isEmpty()){
                    MotdepasseProf.setError("Completez ici ....");
                    MotdepasseProf.requestFocus();
                }
                else {
                    showLoadingDialog();

                    checkOuvrier(usernameProf, passwordProf);
                }
            }
        });
        CreeruncompteProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(), Creer_compte.class);
                startActivity(i);

            }
        });

    }


    public void checkOuvrier(String maile , String mdp){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ouvrier");
        Query checkuser = reference.orderByChild("email").equalTo(maile);
        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    NomutilisateurProf.setError(null);
                    DataSnapshot userSnapshot = snapshot.getChildren().iterator().next();
                    String PasswordFroDB = userSnapshot.child("motdepasse").getValue(String.class);
                    if (!Objects.equals(PasswordFroDB, mdp)) {
                        MotdepasseProf.setError("Mot de passe incorrect");
                        MotdepasseProf.requestFocus();
                        hideLoadingDialog();

                    } else {
                        SharedPreferences p = getSharedPreferences("myData1", Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed = p.edit();



                        NomutilisateurProf.setError(null);
                        hideLoadingDialog();

                        ed.putString("email", maile);
                        ed.putString("motdepasse", mdp);
                        ed.putString("etat", "ouvrier");

                        ed.commit();

                        Intent i =new Intent(getApplicationContext(), Prof_Connecter.class );
                        i.putExtra("username" , MaReponseidentiteProf);
                        i.putExtra("email" , MaReponseemailProf);

                        startActivity(i);
                    }
                } else {
                    hideLoadingDialog();

                    NomutilisateurProf.setError("Cet utilisateur n'existe pas");
                    NomutilisateurProf.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                hideLoadingDialog();
                // Gérer l'erreur
            }
        });

    }

    private void showLoadingDialog() {
        if (loadingDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.progress, null);
            builder.setView(view);
            builder.setCancelable(false);
            loadingDialog = builder.create();
            loadingDialog.show();


        }

    }

    private void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();

        }
    }

}