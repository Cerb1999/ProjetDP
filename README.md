# ProjetDP

## Lancement

Dans un terminal :
```bash
# On clone le dépôt...
git clone https://github.com/Cerb1999/ProjetDP.git -b main
# ...et on se déplace dans sa racine
cd ProjetDP

# avant de créer le package : 
cd src/main/resources
# -> modifier fichier application.properties avec BDD locale

# retour à la racine
cd ../../..

# `mvn package` exécute les actions suivantes :
# - `validate` : validation du `pom.xml` et du projet
# - `compile` : compile les sources
# - `test` : exécute les tests
# - `package` : crée un fichier WAR dans `target/`
mvn package -P prod

# Le .war se trouve maintenant dans `target/` si aucune erreur n'est survenue
# dans l'étape précédente
ProjetDP-1.0-SNAPSHOT.war

# Si CATALINA_HOME n'est pas encore une variable d'environnement
cd C:\MY_WAR_FILE_LOCATION
set CATALINA_HOME="MY_APACHE_TOMCAT_FULLPATH"

# transfert du .war dans tomcat/webapps
copy ProjetDP-1.0-SNAPSHOT.war %CATALINA_HOME%\webapps

# pour linux : .sh et windows : .bat
catalina.bat run

# par défaut : port 8080 (voir tomcat/conf/server.xml
localhost:[PORT]/ProjetDP-1.0-SNAPSHOT.war

# Sinon en mode dev : 
Run / Debut configuration -> Nouvelle configuration (Spring boot ou Tomcat)

#Si Springboot
[Configuration] -> Main class : com.projetdp.ProjetdpApplication
- on Update/ on Frame : Update classes and resources

# Si tomcat
- [Deployment] -> + -> /ProjetDP_war_exploded
- [Server] -> http://localhost:[PORT]/ProjetDP_war_exploded/, spécifier HTTP port
- on Update/ on Frame : Update classes and resources

```