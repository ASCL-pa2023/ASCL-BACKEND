# Utiliser une image Java 17 comme base
FROM openjdk:19-alpine

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier JAR de l'application dans le conteneur
COPY target/ASCL-BACKEND-1.0.0.jar ASCL-BACKEND-1.0.0.jar

# Exposer le port sur lequel l'application va écouter (facultatif, Heroku attribuera automatiquement un port)
# EXPOSE 8080

# Commande à exécuter lorsque le conteneur démarre
CMD ["java", "-Dserver.port=${PORT}", "-jar", "ASCL-BACKEND-1.0.0.jar"]
