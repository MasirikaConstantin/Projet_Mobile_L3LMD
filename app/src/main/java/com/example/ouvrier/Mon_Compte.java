package com.example.ouvrier;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Mon_Compte extends AppCompatActivity {
    private TextView nomP;
    private TextView postnomP;
    private AlertDialog loadingDialog;

    private TextView prenomP;
    private TextView emailP;
    private TextView adressePhysiqueP;
    private ProgressBar progressBar;
    private TextView numP;
    private UserModel currentUserModel;
    private TextView NOMComplet;
    private Button Deconnexion;
    private Button button_secondBTN;
    private Button updateProfileBtn;
    private ImageView profilePic;
    private Button updateProfilBtn;
    private AndroidUtil androidUtil = new AndroidUtil();
    private String id, adresse, email, nom, numero, motdepasse, postnom, prenom, type, profileImageUrl, villes_intervention, adressePostale;
    private String userEmail;
    private ActivityResultLauncher<Intent> imagePickLauncher;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_compte);
        nomP = findViewById(R.id.nomO);
        postnomP = findViewById(R.id.postnomO);
        prenomP = findViewById(R.id.prenomO);
        emailP = findViewById(R.id.emailO);
        adressePhysiqueP = findViewById(R.id.adrephysiqueO);
        numP = findViewById(R.id.numO);
        profilePic = findViewById(R.id.imageView4O);
        //progressBar = findViewById(R.id.profile_progress_bar);
        updateProfileBtn = findViewById(R.id.buttonPhoto);

        showLoadingDialog();
        imagePickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getData() != null) {
                            selectedImageUri = data.getData();
                            androidUtil.setProfilPic(getApplicationContext(), selectedImageUri, profilePic);
                        }
                    }
                }
        );

        profilePic.setOnClickListener((v) -> {
            ImagePicker.with(this).cropSquare().compress(512).maxResultSize(512, 512)
                    .createIntent(new Function1<Intent, Unit>() {
                        @Override
                        public Unit invoke(Intent intent) {
                            imagePickLauncher.launch(intent);
                            return null;
                        }
                    });
        });

        updateProfilBtn = findViewById(R.id.buttonPhoto);
        NOMComplet = findViewById(R.id.textview_secondO);
        Deconnexion = findViewById(R.id.button_deconnexionO);

        updateProfileBtn.setOnClickListener((v -> {
            updateBtnClick();
        }));
        hideLoadingDialog();
        button_secondBTN = findViewById(R.id.button_secondO);
        button_secondBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent is = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(is);
            }
        });

        SharedPreferences p = getSharedPreferences("myData1", Context.MODE_PRIVATE);
        String Nom = p.getString("utilisateur", null);
        String Email = p.getString("email", null);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("utilisateur");

        userEmail = Email;

        if (userEmail != null && !userEmail.isEmpty()) {
            usersRef.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            nom = userSnapshot.child("nom").getValue(String.class);
                            postnom = userSnapshot.child("postnom").getValue(String.class);
                            adresse = userSnapshot.child("adresse").getValue(String.class);
                            prenom = userSnapshot.child("prenom").getValue(String.class);
                            String emaille = userSnapshot.child("email").getValue(String.class);
                            String num = userSnapshot.child("numero").getValue(String.class);
                            id = userSnapshot.child("id").getValue(String.class);
                            email = userSnapshot.child("email").getValue(String.class);
                            numero = userSnapshot.child("numero").getValue(String.class);
                            motdepasse = userSnapshot.child("motdepasse").getValue(String.class);
                            profileImageUrl = userSnapshot.child("profileImageUrl").getValue(String.class);
                            villes_intervention = userSnapshot.child("villes_intervention").getValue(String.class);
                            adressePostale = userSnapshot.child("adressePostale").getValue(String.class);

                            nomP.setText(nom.toUpperCase());
                            postnomP.setText(postnom.toUpperCase());
                            prenomP.setText(prenom.toLowerCase());
                            emailP.setText(emaille);
                            numP.setText(num);
                            adressePhysiqueP.setText(adresse);
                            NOMComplet.setText(nom.toUpperCase() + " " + postnom.toUpperCase() + " " + prenom.toUpperCase());

                            androidUtil.getCurrentProfilePicStorageRef(userEmail).getDownloadUrl()
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Uri uri = task.getResult();
                                            androidUtil.setProfilPic(getApplicationContext(), uri, profilePic);
                                        }
                                    });
                        }
                        currentUserModel = new UserModel(id, adresse, userEmail, nom, numero, motdepasse, postnom, prenom, type, profileImageUrl, villes_intervention, adressePostale);
                        System.out.println(currentUserModel);

                        if (selectedImageUri != null) {
                            androidUtil.getCurrentProfilePicStorageRef(userEmail).putFile(selectedImageUri)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            updateToFirestore(userEmail);
                                            AndroidUtil.showToast(getApplicationContext(), "Photo de profil Changé");

                                        } else {
                                            //setInProgress(false);
                                            AndroidUtil.showToast(getApplicationContext(), "Image upload failed");
                                        }
                                    });
                        }
                    } else {
                        // L'utilisateur n'existe pas
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Gérez les erreurs ici
                }
            });
        } else {
            AndroidUtil.showToast(getApplicationContext(), "Email is invalid or missing");
        }

        Deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Deconnexion();
            }
        });
    }

    void updateToFirestore(String userEmaile) {
        if (userEmail != null && !userEmail.isEmpty()) {
            androidUtil.currentUserDetails(userEmaile).set(currentUserModel)
                    .addOnCompleteListener(task1 -> {
                        //setInProgress(false);
                        if (task1.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Updated successfully", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "Update failed", Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            AndroidUtil.showToast(getApplicationContext(), "User email is invalid or missing");
        }
    }


    void updateBtnClick() {
        if (selectedImageUri != null && userEmail != null && !userEmail.isEmpty()) {
            androidUtil.getCurrentProfilePicStorageRef(userEmail).putFile(selectedImageUri)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            updateToFirestore(userEmail);
                        } else {
                            //setInProgress(false);
                            AndroidUtil.showToast(getApplicationContext(), "Image upload failed");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Error: Selectionner une photo avant de cliquer ici", Toast.LENGTH_LONG).show();
        }
    }

    public void Deconnexion() {
        SharedPreferences p = getSharedPreferences("myData1", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = p.edit();
        ed.putString("utilisateur", "");
        ed.putString("email", "");
        ed.putString("motdepasse", "");
        ed.commit();
        Toast.makeText(this, "Vous êtes bien déconnecté", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(), Accueil.class);
        startActivity(i);
        finish();
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
