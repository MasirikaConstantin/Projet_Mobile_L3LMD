# Projet_Mobile_L3LMD

Voici le document expliquant les choix effectués et la modélisation de l'application mobile développée pour rechercher ou contacter un ouvrier en utilisant Java et Android Studio.

---

# Document Explicatif : Choix et Modélisation de l'Application Mobile de Mise en Relation avec les Ouvriers

## Contexte et Objectifs

Le projet consiste en la création d'une application mobile permettant de faciliter la mise en relation des ouvriers avec les habitants de Kinshasa. L'application répond à un besoin de centraliser les informations sur les ouvriers disponibles et leurs compétences, rendant ainsi plus simple et rapide la recherche et le contact de professionnels tels que des maçons, plombiers, menuisiers, etc. 

## Choix Techniques

1. **Plateforme de Développement :**
   - **Java et Android Studio** : Choisis pour développer l'application mobile spécifiquement pour le système d'exploitation Android. Android Studio est l'IDE officiel pour le développement d'applications Android, offrant de puissants outils de développement et de débogage.

2. **Base de Données :**
   - **Firebase Firestore** : Utilisé pour l'authentification et la gestion de la base de données en temps réel. Firestore est une base de données NoSQL qui permet une gestion efficace des données avec des fonctionnalités de requête en temps réel. Elle est choisie pour sa scalabilité et sa simplicité d'intégration avec les applications Android.

3. **Interface Utilisateur (UI) :**
   - **Material Design** : Utilisé pour créer une interface utilisateur cohérente et intuitive, offrant une expérience utilisateur agréable et facile à naviguer.

## Fonctionnalités de l'Application

1. **Login / Compte / Connexion / Déconnexion :**
   - **Choix du type d'utilisateur :**
     - Lors de la création d'un compte, l'utilisateur doit choisir s'il est un professionnel ou un simple utilisateur.
   - **Informations du Professionnel :**
     - Obligatoires : noms, postnoms, prénoms, numéro de téléphone, ville, expertise, villes d'intervention.
     - Optionnelles : adresse physique, adresse postale.
   - **Informations du Simple Utilisateur :**
     - Obligatoires : informations personnelles basiques (nom, prénom, numéro de téléphone, etc.).

2. **Sauvegarde des Informations :**
   - Toutes les informations saisies par les utilisateurs sont sauvegardées dans une base de données distante pour une accessibilité en temps réel.

3. **Page d'Accueil :**
   - Trois boutons principaux :
     - **Rechercher un ouvrier**
     - **Créer un compte professionnel**
     - **Créer un compte utilisateur**

4. **Recherche d'Ouvrier :**
   - Une barre de recherche permet à tout utilisateur de rechercher un ouvrier par spécialité (ex. plombier).
   - Les résultats sont affichés dans un tableau avec des filtres pour affiner la recherche (ville, quartier, etc.).
   - Chaque ligne de résultat est cliquable et permet d'accéder à la page détaillée de l'ouvrier avec ses informations personnelles et professionnelles.

## Modélisation de l'Application

1. **Diagramme de Cas d'Utilisation :**

```plaintext
+---------------------+          +-----------------------+
|                    |          |                       |
|   Créer un compte  |<---------|    Professionnel      |
|                    |          |                       |
+---------------------+          +-----------------------+
            |
            v
+----------------------+
|                      |
|    Saisie des infos  |
|                      |
+----------------------+
            |
            v
+----------------------+
|                      |
|   Sauvegarde des     |
|   données dans la    |
|   base de données    |
|                      |
+----------------------+

+---------------------+          +-----------------------+
|                    |          |                       |
|   Créer un compte  |<---------|    Utilisateur        |
|                    |          |                       |
+---------------------+          +-----------------------+
            |
            v
+----------------------+
|                      |
|    Saisie des infos  |
|                      |
+----------------------+
            |
            v
+----------------------+
|                      |
|   Sauvegarde des     |
|   données dans la    |
|   base de données    |
|                      |
+----------------------+

+---------------------+
|                    |
|   Rechercher un    |
|   ouvrier          |
|                    |
+---------------------+
            |
            v
+----------------------+
|                      |
|   Saisie des critères|
|   de recherche       |
|                      |
+----------------------+
            |
            v
+----------------------+
|                      |
|   Affichage des      |
|   résultats          |
|                      |
+----------------------+
            |
            v
+----------------------+
|                      |
|   Consultation des   |
|   informations de    |
|   l'ouvrier          |
|                      |
+----------------------+
```

2. **Modèle de Données :**

   - **Utilisateur :**
     - ID
     - Nom
     - Prénom
     - Numéro de téléphone
     - Type (Professionnel ou Utilisateur)
     - (Pour les professionnels) Ville, Expertise, Villes d'intervention, Adresse physique, Adresse postale.

   - **Recherche :**
     - ID de l'ouvrier
     - Spécialité
     - Ville
     - Quartier

3. **Schéma de Navigation :**

   ```plaintext
   Page d'Accueil
      |
      +---> Rechercher un ouvrier
      |         |
      |         +---> Page de recherche
      |                   |
      |                   +---> Résultats de la recherche
      |                               |
      |                               +---> Détails de l'ouvrier
      |
      +---> Créer un compte professionnel
      |         |
      |         +---> Formulaire de création de compte professionnel
      |                   |
      |                   +---> Confirmation et sauvegarde des données
      |
      +---> Créer un compte utilisateur
                |
                +---> Formulaire de création de compte utilisateur
                           |
                           +---> Confirmation et sauvegarde des données
   ```

## Conclusion

Cette application mobile a pour but de résoudre un problème concret rencontré par les habitants de Kinshasa en leur fournissant un moyen efficace de trouver et contacter des ouvriers compétents. Les choix techniques effectués, de l'utilisation de Java et Android Studio pour une compatibilité avec Android à Firebase pour une gestion efficace des données, permettent de garantir une application robuste, scalable et facile à utiliser.

---

