package com.example.ouvrier;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AndroidUtil {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void setProfilPic(Context context, Uri imageUri, ImageView imageView) {
        Glide.with(context).load(imageUri).apply(RequestOptions.circleCropTransform()).into(imageView);
    }

    public static StorageReference getCurrentProfilePicStorage(String userEmail) {
        String userId = currentUserId();
        if (userEmail == null) {
            throw new IllegalArgumentException("User ID is null");
        }
        return FirebaseStorage.getInstance().getReference().child("profil_pic").child(userEmail);
    }

    public static String currentUserId() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        return (user != null) ? user.getUid() : null;
    }

    public static DocumentReference currentUserDetails(String userEmail) {
        String userId = currentUserId();

        if (userEmail == null || userEmail.isEmpty()) {
            throw new IllegalArgumentException("Email is null or empty");
        }
        return FirebaseFirestore.getInstance().collection("utilisateur").document(userEmail);
    }

    public static StorageReference getCurrentProfilePicStorageRef(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email is null or empty");
        }
        // Remplacer les points par des underscores pour éviter les problèmes dans les chemins de stockage
        String sanitizedEmail = email.replace(".", "_");
        return FirebaseStorage.getInstance().getReference().child("profile_pic").child(sanitizedEmail);
    }
}
