package com.example.ouvrier;

import java.util.HashMap;
import java.util.Map;

public class HelperClassOuvier {

    private String id;
    private String nom;
    private String postnom;
    private String prenom;
    private String numero;
    private String adresse;
    private String email;
    private String motdepasse;
    private String type;
    private String expertise;
    private String intervention;
    private String adressePostale;
    private Map<String, Boolean> villes_intervention; // Map pour les villes d'intervention

    public HelperClassOuvier() {
        // Constructeur par défaut nécessaire pour Firebase
    }

    // Getters et setters pour tous les champs

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getAdressePostale() {
        return adressePostale;
    }

    public void setAdressePostale(String adressePostale) {
        this.adressePostale = adressePostale;
    }

    public Map<String, Boolean> getVilles_intervention() {
        return villes_intervention;
    }

    public void setVilles_intervention(Map<String, Boolean> villes_intervention) {
        this.villes_intervention = villes_intervention;
    }

    public HelperClassOuvier(String id, String nom, String postnom, String prenom,  String numero, String adresse, String email, String motdepasse,String expertise, String intervention, String adressePostale, String type, Map<String, Boolean> villes_intervention) {
        this.id = id;
        this.nom = nom;
        this.postnom = postnom;
        this.prenom = prenom;
        this.numero = numero;
        this.adresse = adresse;
        this.email = email;
        this.motdepasse = motdepasse;
        this.type = type;
        this.expertise = expertise;
        this.intervention = intervention;
        this.adressePostale = adressePostale;
        this.villes_intervention = villes_intervention;
    }
}
