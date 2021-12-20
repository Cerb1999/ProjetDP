# ProjetDP

## Lancement

Dans un terminal :
```bash
# On clone le dépôt...
git clone https://github.com/Cerb1999/ProjetDP.git -b main
# ...et on se déplace dans sa racine
cd maze

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
catalina.bat **start**

# par défaut : port 8080 (voir tomcat/conf/server.xml
localhost:8081/ProjetDP-1.0-SNAPSHOT.war
```
