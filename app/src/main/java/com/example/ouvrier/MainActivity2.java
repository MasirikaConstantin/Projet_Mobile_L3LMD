package com.example.ouvrier;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Ouvrier> userList;
    private UserListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listView = findViewById(R.id.listView);
        userList = new ArrayList<>();
        adapter = new UserListAdapter(userList);
        listView.setAdapter(adapter);



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ouvrier");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Ouvrier ouvrier = dataSnapshot.getValue(Ouvrier.class);
                    if (ouvrier != null) {
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
            Intent intent = new Intent(MainActivity2.this, DetailActivity.class);
            intent.putExtra("USER_EMAIL", selectedUser.getEmail());
            startActivity(intent);
        });
    }

    // Adapter personnalisé pour la liste des utilisateurs
    private class UserListAdapter extends ArrayAdapter<Ouvrier> {

        private ArrayList<Ouvrier> userList;

        public UserListAdapter(ArrayList<Ouvrier> userList) {
            super(MainActivity2.this, 0, userList);
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
                textViewCity.setText("Ville d'intervention : " + city);
            } else {
                textViewCity.setText("Ville d'intervention : non spécifiée");
            }

            return listItemView;
        }
    }
}
