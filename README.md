KhushiBagh
==========


##App mobile:##

- Ajouter une nouvelle entrée (nom, type, qtty)
	-> file d'attente d'envoi (jusqu'à ce qu'une connexion à un réseau puisse être établie)

- Voir les entrées
	-> pouvoir les checker
	-> valider à la fin des courses pour les enlever de la liste des choses à avoir


##Serveur:##
- list all
	db -> select * from liste courante

- list all categories
	db -> select * from categories

- validate elements ?elements=csv with elements
	db -> move elements from liste courante to archive des achats (if !already exist)

- add to list param: ?element=name&qtty=quantitiy&(cid=category id || cname=categoryName)
	change name to lowercase (or other, but all should be formatted similarly)
	db -> insert into liste courante element
	if new category -> insert into category new category


##Database:##
- Liste courante
- Archive des achats
