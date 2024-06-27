package com.example.ouvrier;

import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;

public class HelperClass {

    private EditText Nom;
    private EditText PostNom;
    private EditText Prenom;
    private EditText Adresse;
    private EditText Email;
    private EditText MotdePasse;
    private EditText Numero;
    private String id;
    String nom, postnom, prenom, adresse, email, motdepasse, numero, type;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPostnom() {
        return postnom;
    }

    public void setPostnom(String postnom) {
        this.postnom = postnom;
    }

    public String getPrenom() {
        return prenom;
    }
    public String getType() {
        return type;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }



    public void setid(String id) {
        this.id = id;
    }



    public HelperClass(String id, String nom, String postnom, String prenom, String adresse, String email, String motdepasse, String numero, String type) {
        this.id = id;
        this.nom = nom;
        this.postnom = postnom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.motdepasse = motdepasse;
        this.numero = numero;
        this.type = type;
    }

    public HelperClass() {
    }
}
