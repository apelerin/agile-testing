Feature: Tableau de caractéristiques
	Scenario Outline: Je veux comprendre les différentes versions de la model3
		Given je suis sur la page du model 3
		When je sélectionne des paramètres "<category>",
		Then les caractéristiques du produit seraient "<weight>", "<acceleration>", "<battery>"
		Examples:
		| category | weight | acceleration | battery |
		| Performance | 1,844 kg | 3,3 secondes | 567 km |
		| Grande Autonomie AWD | 1,844 kg | 4,4 secondes | 580 km |
		| Standard Plus | 1,745 kg | 5,6 secondes | 430 km |
