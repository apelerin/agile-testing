Feature: Fonctionnalités de ma page d'accueil
	Scenario: Vérification le contenu de la homepage
		Given je suis sur la homepage
		Then le titre doit être "Voitures électriques, énergie solaire et propre | Tesla France"
		And la description contient "Tesla accélère la transition mondiale vers une énergie durable en proposant des véhicules électriques, des panneaux solaires et des solutions intégrées d'énergie renouvelable pour les particuliers et les entreprises."
		And il y a plusieurs punchlines '["Model 3", "Model S", "Model X", "Model Y", "Systèmes d'énergie solaire et Powerwalls"]'
		And le menu de la navbar contient ces liens '["https://www.tesla.com/fr_fr/models", "https://www.tesla.com/fr_fr/model3", "https://www.tesla.com/fr_fr/modelx", "https://www.tesla.com/fr_fr/modely", "https://www.tesla.com/fr_fr/powerwall", "https://www.tesla.com/fr_fr/charging"]'
		And un burger menu permet d'accéder à différents liens '["VÉHICULES DISPONIBLES", "VÉHICULES D'OCCASION", "REPRISE", "CYBERTRUCK", "ROADSTER", "ÉNERGIE", "ESSAIS", "FLOTTES & ENTREPRISES", "NOUS TROUVER", "ÉVÉNEMENTS", "ASSISTANCE"]'