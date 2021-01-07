Feature: Fonctionnalités
	Scenario: Vérification du contenu de la page Model S et des fonctionnalités de personnalisation
		Given je suis sur la page du Model S
		When je clique sur commander
		Then l'url devrait être "https://www.tesla.com/fr_fr/models/design#battery"

		Then le prix affiché par défaut est un prix LOA à "768 € /mois" et "108 € /mois" d'économie de carburant
		Then le montant total au terme du contrat est de "94 841 €"
		When je sélectionne le modèle Performance
		Then le prix en LOA est à "969 € /mois" et "108 € /mois" d'économie de carburant
		Then le montant total au terme du contrat est de "114 052 €"
		
		When je sélectionne l'option Capacité de conduite entièrement autonome
		Then le prix augmente de 89 €/mois

		When je clique sur le logo en haut à gauche
		Then j'arrive sur la page d'accueil US "https://www.tesla.com/"
		When je clique sur le lien localisations en bas de page
		Then l'url est "https://www.tesla.com/findus/list"
