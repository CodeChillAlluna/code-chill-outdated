# Cahier des charges Code & Chill



## Restructuration du projet

Isolation du back et du front, ce qui permettra de créer une API pour le back-end.



## Déploiement continu de Code&Chill

Permettre à n'importe quel utilisateur de récupérer une image docker de Code&Chill qui permettra de le déployer ensuite sur son propre environnement.



## Implémentation d'un tableau de bord sur l'état des environnements

Etat de chaque environnement de développement avec le statut de chaque docker. Donner la possibilité de gérer les paquets et librairies depuis ce tableau de bord.



## Différencier usagers et propriétaire d'un environnement

Permettre à un utilisateur de partager son environnement avec le choix entre :

- donner les droits d'administration au nouvel usager
- ne pas donner les droits root au nouvel usager, ce qui créera un nouveau compte pour celui-ci



## Import / export des dockerfiles

Rendre prossible l'importation et l'exportation d'environnement docker pour faciliter le déploiement d'environnements déjà créés.



## Proposer des dockerfiles pré-configurés

Proposer des environnements pré-configurés pour faciliter la création d'un nouvel environnement (ex: à la création d'un compte).

Langages proposés dans un premier temps : 

* Java
* Python
* C et C++
* NodeJS



## Mise en place d'un IDE sur l'application Web

Fonctionnalités proposées : 

- auto-complétion
- etc.



## Application mobile de Code&Chill

Portage du code React en React Native pour le déploiment de l'application sur mobile.



## Support de l'intégration continue

Ajouter un support graphique de l'intégration continue dans l'environnement

* Permettre aux utilisateurs de vérifier la qualité du code produit avec l'aide d'outils tel que Git et tous les autres outils compatibles avec celui-ci



## Ajouter de la review de code

Rendre possible la review du code des projets



## Permettre à deux utilisateurs de coder sur le même environnement en même temps



## Accès hors-ligne en cas de déconnexion imprévue

> ! problème d'architecture



## Faire évoluer l'intégration continue et les tests du dépôt GitHub

* Utilisation du plugin Maven EvoSuite afin d'assurer un coverage de la partie back et front du projet
* Intégration de Sentry afin de pouvoir avoir une traçabilité des erreurs et la stabilité des release
* D'autres seront utilisés en fonction des avancées du projet