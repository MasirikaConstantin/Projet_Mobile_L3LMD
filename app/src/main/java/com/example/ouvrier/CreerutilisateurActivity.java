package com.example.ouvrier;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

public class CreerutilisateurActivity extends AppCompatActivity {

    private EditText Nom;
    private EditText PostNom;
    private EditText Prenom;
    private EditText Adresse;
    private EditText Email;
    private EditText MotdePasse;
    private EditText Numero;
    FirebaseDatabase Database;

    DatabaseReference reference;

    private Button CreerCompte;

    private TextView SeconnecterCompte;
    private AlertDialog loadingDialog;


    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creerutilisateur);

        SeconnecterCompte =  findViewById(R.id.signUpText);

        CreerCompte = findViewById(R.id.btnLogin);

        SeconnecterCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(), Connection_User.class);
                startActivity(i);
            }
        });

        CreerCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nom = findViewById(R.id.nom);
                PostNom = findViewById(R.id.postnom);
                Prenom = findViewById(R.id.prenom1);
                Adresse = findViewById(R.id.adresse);
                Email = findViewById(R.id.email);
                MotdePasse = findViewById(R.id.mdp);
                Numero = findViewById(R.id.num);
                Database = FirebaseDatabase.getInstance();
                String id = mDatabase.push().getKey();
                reference = Database.getReference("utilisateur");
                String nom, postnom, prenom, adresse, email, motdepasse, numero, type;

                nom = Nom.getText().toString();
                postnom=PostNom.getText().toString();
                prenom=Prenom.getText().toString();
                adresse=Adresse.getText().toString();
                email=Email.getText().toString();
                motdepasse=MotdePasse.getText().toString();
                numero=Numero.getText().toString();
                type="utilisateur";


                if (TextUtils.isEmpty(nom) || TextUtils.isEmpty(postnom) || TextUtils.isEmpty(prenom) || TextUtils.isEmpty(adresse) || TextUtils.isEmpty(email)  || TextUtils.isEmpty(motdepasse)  || TextUtils.isEmpty(numero) ){
                    Toast.makeText(getApplicationContext(), " Remplissez Tous les champs",Toast.LENGTH_LONG).show();

                }


                else if (motdepasse.length()<5) {
                    Toast.makeText(getApplicationContext(), "Le mot de passe doit contenir plus de 6 caracteres",Toast.LENGTH_LONG).show();

                } else if (motdepasse.length()>5) {

                    Query query = reference.orderByChild("email").equalTo(email);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                // L'adresse e-mail existe déjà dans la base de données
                                // Gérez le cas en conséquence
                                Toast.makeText(getApplicationContext(), "L'adresse e-mail est déjà utilisée.", Toast.LENGTH_SHORT).show();

                            } else {
                                HelperClass helperClass = new HelperClass(id ,nom, postnom, prenom, adresse, email, motdepasse, numero, type);
                                reference.child(id).setValue(helperClass);
                                showLoadingDialog();
                                Toast.makeText(getApplicationContext(), "Compte créer avec Succes",Toast.LENGTH_LONG).show();
                            }
                                // L'adresse e-mail n'existe pas dans la base de données
                                // Vous pouvez ajouter l'enregistrement ici
                            }

                        @Override
                        public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {

                        }
                            });





                }



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
        }
        loadingDialog.show();
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoadingDialog();
            }
        },1000);
    }

    private void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            Intent i = new Intent(getApplicationContext(), Connection_User.class);
            startActivity(i);
        }
    }


}

