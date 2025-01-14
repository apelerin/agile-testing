Feature: Fonctionnalités page évènement
	Scenario: Vérification du contenu de la page
		Given je suis sur la page home
		When je clique sur évènements dans le menu burger
		Then je devrai être sur la page https://www.tesla.com/fr_FR/events

		When une fois que j'ai choisis un lieu
		Then la page doit contenir les 15 prochains évènements autour

		Then un lien existe pour voir tous les évènements de la marque
		When je clique sur Afficher plus
		Then plus de 15 résultats d'évènement s'affichent

	Scenario: Vérification des formulaires de la page
		Given je suis sur la page évènements

		Then un formulaire à droite de l'écran contient les champs suivants: ["Prénom", "Nom", "E-mail", "Téléphone", "Region", "Code postal", "Recevoir les News Tesla"]
		Then le formulaire contient un bouton "Suivant" pour envoyer ces informations

		When je rempli tous les champs du formulaire Soyez informé sauf l'email et que j'appuis sur Suivant
		Then un message textuel apparait sous le champ email indiquant "obligatoire"

		When je cherche un évènement à "Londres, Royaume-Uni"
		Then le premier résultat de recherche indique un évènement localisé à "Royaume-Uni"


	Scenario: je cherche une visio au japon et me retrouve sur une page d'authentification
		Given je suis sur la page évènements
		When je cherche les events au "Japon"
		Then j'ai des résultats
		When je clique sur le deuxième résultat, correspondant à une visio
		Then j'arrive sur une page d'authentification contenant "https://auth.tesla.com/"