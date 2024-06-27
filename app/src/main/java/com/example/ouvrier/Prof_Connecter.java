package com.example.ouvrier;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Prof_Connecter extends AppCompatActivity {
    private TextView nomP;
    private AlertDialog loadingDialog;
    private ListView MaVille_interventionList;

    private TextView postnomP;
    private TextView prenomP;
    private TextView emailP;
    private TextView adressePhysiqueP;
    private TextView adressePostaleP;
    private TextView numP;
    private TextView expertiseP;
    private TextView ville_interventionP;
    private  TextView NOMComplet;
    private Button Deconnexion;

    private String MonMail;

    private DatabaseReference mDatabase;
    private List<String> mVillesIntervention;
    private Prof_Connecter.UserListAdapter adapter;
    private ArrayList<Ouvrier> userList;


    private ListView mListViewVilles;
    private VillesAdapter mVillesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_connecter);
        nomP = findViewById(R.id.nom);
        postnomP= findViewById(R.id.postnom);
        prenomP = findViewById(R.id.prenom);
        emailP = findViewById(R.id.email);
        adressePhysiqueP = findViewById(R.id.adrephysique);
        adressePostaleP = findViewById(R.id.adrepostal);
        numP= findViewById(R.id.num);
        expertiseP=findViewById(R.id.expertise);
        ville_interventionP = findViewById(R.id.Ville_intervention);
        NOMComplet = findViewById(R.id.textview_second);
        Deconnexion = findViewById(R.id.button_deconnexion);
        showLoadingDialog();

        MaVille_interventionList = findViewById(R.id.Ville_interventionList);

        SharedPreferences p = getSharedPreferences("myData1", Context.MODE_PRIVATE);
        //Afficher
        String Nom = p.getString("utilisateur", null);
        String Email = p.getString("email", null);
        String mdp = p.getString("mdp", null);
        String Etat = p.getString("etat", null);
        userList = new ArrayList<>();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("ouvrier");

        String userEmail =Email; // Remplacez par l'e-mail de l'utilisateur recherché

        usersRef.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    hideLoadingDialog();
                    // L'utilisateur existe dans la base de données
                    mVillesIntervention = new ArrayList<>();
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String nom = userSnapshot.child("nom").getValue(String.class);
                        String postnom = userSnapshot.child("postnom").getValue(String.class);
                        String adresse = userSnapshot.child("adresse").getValue(String.class);
                        String prenom = userSnapshot.child("prenom").getValue(String.class);
                        String emaille = userSnapshot.child("email").getValue(String.class);
                        String num = userSnapshot.child("numero").getValue(String.class);
                        String expertise = userSnapshot.child("expertise").getValue(String.class);
                        String intervention = userSnapshot.child("intervention").getValue(String.class);
                        DataSnapshot villesSnapshot = userSnapshot.child("villes_intervention");

                                              // Après avoir récupéré les données des villes d'intervention
                        List<String> villesList = new ArrayList<>(); // Liste pour stocker les noms des villes



                        // Afficher les données récupérées dans les TextView correspondants
                        nomP.setText(nom.toUpperCase());
                        postnomP.setText(postnom.toUpperCase());
                        prenomP.setText(prenom.toLowerCase());
                        emailP.setText(emaille);
                        numP.setText(num);
                        adressePhysiqueP.setText(adresse);
                        expertiseP.setText(expertise.toUpperCase());
                        NOMComplet.setText(nom.toUpperCase() + " " + postnom.toLowerCase() + " " + prenom.toLowerCase());

                        MonMail = emaille; // Assigner l'e-mail à la variable MonMail si nécessaire
                        for (DataSnapshot villeSnapshot : villesSnapshot.getChildren()) {
                            String ville = villeSnapshot.getKey(); // Récupérer le nom de la ville
                            Boolean isActive = villeSnapshot.getValue(Boolean.class); // Vérifier si la ville est active (true/false)
                            villesList.add(ville); // Ajouter le nom de la ville à la liste
                        }


// Créer un ArrayAdapter pour la ListView
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(Prof_Connecter.this,android.R.layout.simple_list_item_1, villesList);

// Assigner l'adaptateur à la ListView
                        MaVille_interventionList.setAdapter(adapter);

                    }


                } else {
                    // L'utilisateur n'existe pas
                    // Gérez le cas où l'e-mail ne correspond à aucun utilisateur
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Gérez les erreurs ici
            }
        });


        Deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Decconnexion();
            }
        });


        Button ajouterVilleButton = findViewById(R.id.button_ajouter_ville);
        EditText villeInterventionEditText = findViewById(R.id.edittext_ville_intervention);



        ajouterVilleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nouvelleVille = villeInterventionEditText.getText().toString().trim();

                // Vérifier si la ville n'est pas vide
                if (!nouvelleVille.isEmpty()) {
                    // Ajouter la nouvelle ville à Firebase
                    usersRef.orderByChild("email").equalTo(MonMail).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    DatabaseReference ouvrier = snapshot.getRef();
                                    ouvrier.child("villes_intervention").child(nouvelleVille).setValue(true);
                                    Toast.makeText(Prof_Connecter.this, "Ville ajoutée avec succès", Toast.LENGTH_SHORT).show();
                                    villeInterventionEditText.setText(""); // Effacer le champ après ajout
                                    Intent i = new Intent(getApplicationContext(), Accueil.class);
                                    startActivity(i);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Gérer les erreurs ici
                        }
                    });
                } else {
                    Toast.makeText(Prof_Connecter.this, "Veuillez entrer une ville d'intervention", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Autres initialisations et gestionnaires d'événements




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



    // VillesAdapter.java
    public class VillesAdapter extends ArrayAdapter<String> {

        private Context mContext;
        private List<String> mVilles;

        public VillesAdapter(Context context, List<String> villes) {
            super(context, R.layout.item_ville, villes);
            mContext = context;
            mVilles = villes;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.item_ville, null);
            }

            TextView textViewVille = view.findViewById(R.id.textview_ville);
            textViewVille.setText(mVilles.get(position));

            return view;
        }
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
    private class UserListAdapter extends ArrayAdapter<Ouvrier> {

        private ArrayList<Ouvrier> userList;

        public UserListAdapter(ArrayList<Ouvrier> userList) {
            super(Prof_Connecter.this, 0, userList);
            this.userList = userList;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_user, parent, false);
            }

            Ouvrier currentUser = userList.get(position);

            // Remplir les vues avec les données de l'utilisateur
            ImageView imageViewProfile = listItemView.findViewById(R.id.imageViewProfile);
            TextView textViewName = listItemView.findViewById(R.id.textViewName);
            TextView textViewExpertise = listItemView.findViewById(R.id.textViewExpertise);
            TextView textViewCity = listItemView.findViewById(R.id.textViewCity);

            // Charger l'image de profil (utilisation d'une ressource par défaut)
            if (currentUser.getImageUrl() != null && !currentUser.getImageUrl().isEmpty()) {
                // Chargez votre image à partir de l'URL avec Picasso, Glide, ou une autre bibliothèque
                // Exemple avec une image par défaut pour le test
                imageViewProfile.setImageResource(R.drawable.baseline_person_24);
            } else {
                // Charger une image par défaut si aucune image n'est disponible
                imageViewProfile.setImageResource(R.drawable.baseline_person_24);
            }

            // Afficher le nom et l'expertise de l'utilisateur
            textViewName.setText(currentUser.getNom() + " " + currentUser.getPrenom());
            textViewExpertise.setText(currentUser.getExpertise());

            // Afficher la ville d'intervention, en mettant "null" si la valeur est vide ou null
            String city = currentUser.getAdresse();
            if (city != null && !city.isEmpty()) {
                textViewCity.setText("Adresse Fixe : " + city);
            } else {
                textViewCity.setText("Adresse Fixe : non spécifiée");
            }

            return listItemView;
        }
    }

}