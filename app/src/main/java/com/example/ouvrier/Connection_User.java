package com.example.ouvrier;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Connection_User extends AppCompatActivity {
   private Button BoutonSeVerif;

    private Button BoutonSeConnecter;
    private AlertDialog loadingDialog;

    private TextView Creeruncompte;
    private EditText Nomutilisateur;
    private EditText Motdepasse;
    private TextView ErreurConnexion;
    private String email;
    private  String password;
    //private FirebaseAuth mAuth;

  //  FirebaseApp.initializeApp;



    private ProgressBar Progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_user);
        //getSupportActionBar().hide();
       // mAuth = FirebaseAuth.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        BoutonSeConnecter = findViewById(R.id.btnLogin);
        ErreurConnexion=findViewById(R.id.error_connexionuser);
        Nomutilisateur = findViewById(R.id.emailConnect);
        Motdepasse = findViewById(R.id.passwordConnect);
        Creeruncompte = findViewById(R.id.signUpText);
        Progress = findViewById(R.id.progressBar);






                BoutonSeConnecter.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View view) {
                    String usermail = Nomutilisateur.getText().toString();
                    String pass = Motdepasse.getText().toString();



                    if (usermail.isEmpty()){
                        Nomutilisateur.setError("Completez ici ....");
                        Nomutilisateur.requestFocus();
                    }
                    if(pass.isEmpty()){
                        Motdepasse.setError("Completez ici ....");
                        Motdepasse.requestFocus();
                    }
                    else{
                        showLoadingDialog();


                        checkUser(usermail, pass);




                    }
                }
                });





           // }
        //});
        Creeruncompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(), CreerutilisateurActivity.class);
                startActivity(i);

            }
        });

    }
        public Boolean ValiderLeNom(){
                String val = email;
                if(val.isEmpty()){
                    Nomutilisateur.setError("L'adresse email est vide");
                    return false;
                }else {
                    Nomutilisateur.setError(null);
                    return true;
                }
        }
    public Boolean ValiderMdp(){
        String val = password;
        if(val.isEmpty()){
            Motdepasse.setError("Le mot de passe est vide");
            return false;
        }else {
            Motdepasse.setError(null);
            return true;
        }

    }
    public void checkUser(String maile , String mdp){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("utilisateur");
        Query checkuser = reference.orderByChild("email").equalTo(maile);
        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Nomutilisateur.setError(null);
                    DataSnapshot userSnapshot = snapshot.getChildren().iterator().next();
                    String PasswordFroDB = userSnapshot.child("motdepasse").getValue(String.class);
                    if (!Objects.equals(PasswordFroDB, mdp)) {
                        Motdepasse.setError("Mot de passe incorrect");
                        Motdepasse.requestFocus();
                        hideLoadingDialog();

                    } else {
                        SharedPreferences p = getSharedPreferences("myData1", Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed = p.edit();
                        ed.putString("email", maile);
                        ed.putString("motdepasse", mdp);
                        ed.putString("etat", "utilisateur");

                        ed.commit();
                        Nomutilisateur.setError(null);
                        hideLoadingDialog();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    hideLoadingDialog();

                    Nomutilisateur.setError("Cet utilisateur n'existe pas");
                    Nomutilisateur.requestFocus();
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
