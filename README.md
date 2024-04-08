# ENI-Encheres.org

## Critères techniques
- Respect des principes de l’architecture en couches
- Gestion correcte de la navigation : retour navigateur, rafraîchissement d'une page avec formulaire, etc.
- Sécurisation de l’accès aux pages nécessitant une authentification
- Gestion de la session
- Factorisation du code (Servlets, JSPs, css...)
- Utilisation efficace des contextes mémoire
- Séparation HTML/CSS
- Modularisation des vues (insert, fragments, templates…)
- Portabilité du code (pas d’informations sur le serveur, port, ou nom d’application codées « en dur »)
- Séparation effective des technologies (ex : minimum de scriptlets dans les JSP).
- URI logiques : les noms réels des fichiers ne doivent pas être visibles dans le navigateur. (Ex : /seConnecter vs  ConnexionServlet)
- Respecter le pattern MVC 
- Adaptation de la mise en page au format de l’affichage (Responsive Web Design) 
- Utilisation d’un pool de connexions 
- Pas de fuite de connexions 
- Pas d’injection SQL possible 
- Garantir l’intégrité des données (Exemple : enchérir, nouvelle vente...)

## Critères de qualité 
- Maintenabilité (respect des conventions, nomenclature, utilisation de patterns, documentation…) 
- Robustesse (absence de défauts/bugs, sécurité, bonnes pratiques…) 
- Évolutivité (paramètres externalisés, utilisation de pattern…) 