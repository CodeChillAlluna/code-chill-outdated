# Cahier des charges Code & Chill



## Restructuration du projet

Le répository GitHub contient actuellement le back-end et le front-end de l'application. Cependant, nous allon créer deux nouveaux répository pour isoler le front du back. Cela permettra d'avoir une meilleure flexibilité pour la personnalisation du front. L'isolation du back donnera une meilleure visibilité de l'API.



## Déploiement continu de Code&Chill

L'objectif de cette fonctionnalité est de rendre possible le déploiement de l'application en donnant une image docker de notre environnement. Cela permet de s'abstraire de toute méthode de déploiement pour nos utilisateurs. Cela nous posait aussi plusieurs problèmes au niveau de l'intégration continu avec Travis et CodeCoverage notemment



## Implémentation d'un tableau de bord sur l'état des environnements

Implémenter un tableau de bord donnera la possibilité aux utilisateurs d'avoir un suivi de ses environnements avec :

- l'état de chaque container
- la possibilité de les éteindre et les allumer
- ajouter et enlever des paquets



## Différencier usagers et propriétaire d'un environnement

La différenciation se fera avec une refonte du modèle utilisateur pour permettre à un utilisateur de partager son environnement avec le choix entre :

- donner les droits d'administration au nouvel usager
- ne pas donner les droits root au nouvel usager, ce qui créera un nouveau compte pour celui-ci



## Importation et exportation

L'importation et l'exportation d'image docker (ou l'importation de dockerfile) va permettre aux utilisateurs ayant déjà leur environnement docker de ne pas les perdre en utilisant Code&Chill. De plus, l'exportation permettra à terme de créer un hub de partage d'environnement entre utilisateurs.



## Proposer des dockerfiles pré-configurés

Proposer des environnements pré-configurés pour faciliter la création d'un nouvel environnement (ex: à la création d'un compte). Les principaux langages et framework seront disponibles (ex: java, python, c, javascript, etc.), mais aussi les outils d'intégration continue (git, travis, etc.)



## Mise en place d'un IDE sur l'application Web

Actuellement, seul un terminal est présent sur la page web. L'objectif est d'y intégrer un IDE avec tout ce que peut proposer les IDE "bureaux" traditionnels comme :

### - L'auto-complétion du code

### - La review de code

### - L'intégration continu intégrée

### - L'assistance à la création de projet



## Application mobile de Code&Chill

L'application mobile sera en React Native (sauf désacord du corps enseignant) et contiendra dans un premier temps le tableau de bord de l'utilisateurs.



## Telemetry

L'objectif est de permettre à plusieurs utilisateurs de travailler sur le même IDE en même temps et avec les mises à jours en temps réel (telemetry). Cela renforcera le travail colaboratif de nos utilisateurs.



## Faire évoluer l'intégration continue et les tests du dépôt GitHub

- Utilisation du plugin Maven EvoSuite afin d'assurer un coverage de la partie back et front du projet
- Intégration de Sentry afin de pouvoir avoir une traçabilité des erreurs et la stabilité des release
- D'autres plugins seront utilisés en fonction des avancées du projet



# Fonctionalités prévues mais mises à l'écart

## Accès hors-ligne en cas de déconnexion imprévue

Rendre possible l'utilisation de Code&Chill en mode hors-ligne pour la prévention de déconnexion imprévue ou pour avoir une version "locale".

> ! Problème d'architecture
>
> ! Problème de hors-ligne sur le web


