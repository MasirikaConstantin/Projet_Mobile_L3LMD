package com.example.ouvrier;

import java.util.List;

public class Ouvrier {
    private String adresse;
    private String email;
    private String expertise;
    private String intervention;
    private String motdepasse;
    private String nom;
    private String numero;
    private String postnom;
    private String prenom;
    private String type;
    private String imageUrl;
    private List<String> villesIntervention; // Liste des villes d'intervention

    public Ouvrier() {
        // Constructeur par défaut nécessaire pour Firebase
    }

    // Getters et setters pour tous les champs

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

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getIntervention() {
        return intervention;
    }

    public void setIntervention(String intervention) {
        this.intervention = intervention;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getVillesIntervention() {
        return villesIntervention;
    }

    public void setVillesIntervention(List<String> villesIntervention) {
        this.villesIntervention = villesIntervention;
    }
}
