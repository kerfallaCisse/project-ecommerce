## Politique d'utilisation du Git
Pour les messages de commit : utiliser les conventions de commit voir https://www.conventionalcommits.org/en/v1.0.0/. 
Les éléments qui seront généralement utilisés dans les messages de commit sont les suivants :
-	```fix```: pour fixer un bug,
-	```test``` : pour les tests,
-	```feat``` : lorsqu’on crée une nouvelle fonctionnalité,
-	```chore``` : c’est pour la réalisation des tâches fastidieuses. Lorsque la tâche à effectuer ne nécessite pas de mettre à jour du code, etc. 
-	```docs``` : pour la documentation,
-	```refactor``` : mettre à jour du code, etc.

Format: ```<type>(<scope>): <sujet>```
```<scope>``` est optionnel. Mais nécessaire dans certains cas. Exemple : lorsqu’on crée une nouvelle fonctionnalité, mise à jour.

***Usage*** :
```feat```: gestion des stocks (message spécifiant de manière brève l’objectif).

***Autre exemple*** :
feat(gestion_stocks) : création de la fonctionnalité pour gérer les stocks de vente.

### Politique des branches 
-	Garder nos branches respectives,
-	Tout ce qui est sur la branche master est fonctionnel,
-	On crée une branche pour chaque fonctionnalité/commit en spécifiant le nom de sa branche puis le n° de la issue sur jira (voir section suivante),
-	On crée une branche d’intégration pour les fonctionnalités (branche develop). Quand le développement d’une fonctionnalité est complet, merger sur la branche develop. C’est cette branche qui sera ensuite merger sur la branche master.

## Utilisation de Jira pour la gestion de projet
Lien utile pour le management des issues : https://docs.gitlab.com/ee/integration/jira/issues.html.

**Important** : Lors de chaque commit référencer la issue. 

Exemple (avec le conventionnal commit) :
```feat(gestion_stocks): PIEC-n°issue sur jira```

Nom de la branche:
```kerfalla-PIEC-1```

## Fichier .gitignore
Lien utile pour ajouter des fichiers dossiers à ignorer selon vos IDE respectives, etc: https://www.toptal.com/developers/gitignore