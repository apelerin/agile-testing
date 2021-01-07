Feature: Fonctionnalités
	Scenario: Vérification du contenu de la page Model S et des fonctionnalités de personnalisation
		Given je suis sur la page du Model S
		When je clique sur commander
		Then l'url devrait être "https://www.tesla.com/fr_fr/models/design"
		Then le prix affiché par défaut est un prix LOA à "768 € /mois"