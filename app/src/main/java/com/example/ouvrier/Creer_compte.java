package com.example.ouvrier;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Arrays;
import java.util.List;

public class Creer_compte extends AppCompatActivity {
    private TextView JaiCompte;
    private TextView ErreurCreer;
    private Button Creercompte;
    private EditText NaomEditext;
    private EditText Motdepasse;
    private String username, password;
    private Spinner TypeClient;


    private EditText Nom;
    private EditText PostNom;
    private EditText Prenom;
    private EditText Adresse;
    private EditText Email;
    private EditText MotdePasse;
    private EditText Numero;
    private EditText AdressePostale;
    private TextView ErreurConnexion;
    FirebaseAuth mAuth;
    FirebaseDatabase Database;

    DatabaseReference reference;

    private Button CreerCompte;

    private TextView SeconnecterCompte;
    private AlertDialog loadingDialog;

private  Button Login;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creerouvrier);

        TypeClient = findViewById(R.id.selectType);


        List<String> options = Arrays.asList("Plombier","Elécticien","Maçon", "Carreleur", "Peintre","Charpentier","Menuisier","Terrassier","Couvreur", "Monteur d'échaffaudage");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        TypeClient.setAdapter(adapter);

        Spinner TypeClient = findViewById(R.id.selectType);


        Login = findViewById(R.id.btnLogin);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TypeClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedItem = adapter.getItem(position);
                        // Do something with the selected item (e.g., display a toast)
                        //Toast.makeText(getApplicationContext(), "Vous avez sélectionné: " + selectedItem, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Handle case where nothing is selected (optional)
                    }
                });

                String selectedItem2 = (String) TypeClient.getSelectedItem();

                String expertise = selectedItem2;

                Nom = findViewById(R.id.usernameConnect);
                PostNom = findViewById(R.id.postnomOuv);
                Prenom = findViewById(R.id.prenomOuv);
                Adresse = findViewById(R.id.adresseOuv);
                Email = findViewById(R.id.emailOuv);
                MotdePasse = findViewById(R.id.passwordOuv);
                ErreurConnexion = findViewById(R.id.error_creation);
                Numero = findViewById(R.id.num);
                AdressePostale = findViewById(R.id.adressePostaleOuv);

                Database = FirebaseDatabase.getInstance();



                String id = mDatabase.push().getKey();


                reference = Database.getReference("ouvrier");
                String nom, postnom, prenom, adresse, email, motdepasse, numero, intervention, adressePostale,type;

                nom = Nom.getText().toString();
                postnom=PostNom.getText().toString();
                prenom=Prenom.getText().toString();
                adresse=Adresse.getText().toString();
                email=Email.getText().toString();
                motdepasse=MotdePasse.getText().toString();
                numero=Numero.getText().toString();
                adressePostale=AdressePostale.getText().toString();
                type="ouvier";
                intervention="";


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



                                HelperClassOuvier helperClass = new HelperClassOuvier(id ,nom, postnom, prenom,  numero,adresse, email, motdepasse, expertise,intervention,adressePostale, type, null);
                                reference.child(id).setValue(helperClass);
                                showLoadingDialog();
                                Toast.makeText(getApplicationContext(), "Compte créer avec Succes",Toast.LENGTH_LONG).show();

                                Intent i =new Intent(getApplicationContext(), Connexion_Prof.class);
                                startActivity(i);

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


        JaiCompte=findViewById(R.id.signUpText);
        JaiCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(), Connexion_Prof.class);
                startActivity(i);


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
        }
    }


}