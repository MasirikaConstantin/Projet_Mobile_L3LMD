package com.example.ouvrier;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.ouvrier.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView Extraname;

    private EditText editTextVille;
    private Button btnFiltrer;

    private DatabaseReference reference;
    private ValueEventListener valueEventListener;

    private int CurrentPage = 1;


    private FirebaseDatabase database;
    private AlertDialog loadingDialog;
    private String VraiUrl;
    private TextView Email;

    private Button Suivant;
    private Button Retour;


    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private Url ClassMonurl;



    private DatabaseReference mDatabase;
    private ListView mListView;

    private ListView listView;
    private ArrayList<Ouvrier> userList;
    private MainActivity.UserListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MesAffaires();


        // Initialisation des vues
       // editTextVille = findViewById(R.id.editTextVille);
        //btnFiltrer = findViewById(R.id.btnFiltrer);


        setSupportActionBar(binding.toolbar);
        mListView = findViewById(R.id.listViewUsers);

        //  listView = findViewById(R.id.list_item);
        showLoadingDialog();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();*/
                Intent i = new Intent(getApplicationContext(), Mon_Compte.class);
                startActivity(i);
            }
        });


        listView = findViewById(R.id.listViewUsers);
        userList = new ArrayList<>();
        adapter = new MainActivity.UserListAdapter(userList);
        listView.setAdapter(adapter);




        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ouvrier");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Ouvrier ouvrier = dataSnapshot.getValue(Ouvrier.class);
                    if (ouvrier != null) {
                        hideLoadingDialog();
                        userList.add(ouvrier);

                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Gérer l'erreur d'annulation
            }
        });

        // Gestion du clic sur un élément de la liste
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Ouvrier selectedUser = userList.get(position);
            Intent intent = new Intent(MainActivity.this, Second.class);
            intent.putExtra("USER_EMAIL", selectedUser.getEmail());
            startActivity(intent);
        });




    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }






        //int id = item.getItemId();

        if (id == R.id.action_deconnection) {
            Decconnexion();

            finish();

            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }





        // return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public  void  MesAffaires(){
        //Extraname = findViewById(R.id.textView);
        //username = getIntent().getStringExtra("username");




    }

    public void Decconnexion(){
        SharedPreferences p = getSharedPreferences("myData1", Context.MODE_PRIVATE);

        SharedPreferences.Editor ed = p.edit();

        ed.putString("utilisateur", "");
        ed.putString("email", "");
        ed.putString("motdepasse", "");
        //ed.putString("age", getmEditTextage.getText().toString());
        ed.commit();
        //Toast.makeText(this, "Sciences Séléctionner", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(), Accueil.class);
        startActivity(i);


    }

    private class UserListAdapter extends ArrayAdapter<Ouvrier> {

        private ArrayList<Ouvrier> userList;

        public UserListAdapter(ArrayList<Ouvrier> userList) {
            super(MainActivity.this, 0, userList);
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



