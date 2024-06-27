package com.example.ouvrier;

import com.google.firebase.Timestamp;

public class UserModel {
    private String id; // Identifiant unique de l'utilisateur dans Firestore
    private String adresse;
    private String email;
    private String nom;
    private String numero;
    private String motdepasse;
    private String postnom;
    private String prenom;
    private String type;
    private String profileImageUrl;
    private String villes_intervention;
    private String adressePostale;


    public UserModel() {
        // Constructeur par défaut requis par Firestore
    }

    public UserModel(String id, String adresse, String email, String nom, String numero, String motdepasse, String postnom, String prenom, String type, String profileImageUrl, String villes_intervention, String adressePostale) {
        this.id = id;
        this.adresse = adresse;
        this.email = email;
        this.nom = nom;
        this.numero = numero;
        this.motdepasse = motdepasse;
        this.postnom = postnom;
        this.prenom = prenom;
        this.type = type;
        this.profileImageUrl = profileImageUrl;
        this.villes_intervention = villes_intervention;
        this.adressePostale = adressePostale;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
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

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getVilles_intervention() {
        return villes_intervention;
    }

    public void setVilles_intervention(String villes_intervention) {
        this.villes_intervention = villes_intervention;
    }

    public String getAdressePostale() {
        return adressePostale;
    }

    public void setAdressePostale(String adressePostale) {
        this.adressePostale = adressePostale;
    }
}
