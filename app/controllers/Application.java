package controllers;

import play.*;
import play.cache.Cache;
import play.mvc.*;

import java.util.*;

import models.*;

/**
 * Gère l'interface utilisateur.
 * @author Jacob Tardieu
 *
 */
@With(Secure.class)
public class Application extends Controller {

	/**
	 * Génère la page d'accueil qui propose de commander des sandwiches
	 */
	public static void index() {
		//Récupération des infos de l'utilisateur
		String userid = Cache.get(session.getId()+"USERID", String.class);
		String firstname = Cache.get(session.getId()+"FIRSTNAME", String.class);
		String lastname = Cache.get(session.getId()+"LASTNAME", String.class);
		String email = Cache.get(session.getId()+"EMAIL", String.class);
		String status = Cache.get(session.getId()+"STATUS", String.class);

		List<Sandwich> sandwiches = Sandwich.find("disponibilite like true").fetch();
		render(sandwiches, userid, firstname, lastname, status);
	}

	/**
	 * Méthode appelée à chaque ajout ou suppression d'un sandwich dans le panier.
	 */
	public static void editPanier(){

		Sandwich s = params.get("sandwich", Sandwich.class);
		Integer quantite = params.get("quantite", Integer.class);

		boolean present=false;
		Item aEnlever = null;
		List<Item> panier = (List<Item>) Cache.get(session.getId()+"panier");

		if (panier == null) {
			panier = new ArrayList<Item>();
		}
		if (s.name!=null) {
			for (Item i : panier) {
				if (i.sandwich.name.equals(s.name)) {
					int nb=i.quantite;
					if (nb + quantite >=0) {
						i.quantite=nb+quantite;
						if (nb + quantite == 0) {

							aEnlever=i; //On marque celui qu'il faudra enlever
						}
					}

					present=true;
				}
			}
			if (!present && quantite>0) {
				panier.add(new Item(s,quantite));
			}
			//Suppression de l'item si il y en a 0 dans le panier :
			panier.remove(aEnlever);
		}

		Cache.set(session.getId()+"panier", panier);

		renderJSON(panier);
	}

	/**
	 * Enregistrement d'une commande
	 * @param enregistrement
	 * Booléen qui définit s'il s'agit d'un clic sur envoyer la commande ou d'un clic sur l'onglet "Mes commandes".
	 * Vaut true pour une commande et false pour un clic sur "Mes commmandes".
	 * @param date
	 * Date choisie de livraison.
	 */
	public static void commande(boolean enregistrement, String date) {

		String userid = Cache.get(session.getId()+"USERID", String.class);
		String firstname = Cache.get(session.getId()+"FIRSTNAME", String.class);
		String lastname = Cache.get(session.getId()+"LASTNAME", String.class);
		String email = Cache.get(session.getId()+"EMAIL", String.class);
		String status = Cache.get(session.getId()+"STATUS", String.class);

		List<User> users = User.find("userid", userid).fetch();
		User user;
		if (users.isEmpty()) {
			user = new User(userid, firstname, lastname, email, status);
		}
		else {
			user = users.get(0);
		}
		user.save();

		if (enregistrement) { //N'est executé que si il s'agit d'une commande et non pas d'un clic sur l'onglet commande.
			List<Item> panier = (List<Item>) Cache.get(session.getId()+"panier");

			if (panier == null) {
				panier = new ArrayList<Item>();
			}

			int montant=0;
			int nbSandwiches=0;

			Commande comm=new Commande();
			comm.date=date;
			comm.user=user;
			comm.save();

			Sandwich s =new Sandwich("oij","ijijiri",3);
			s.save();

			for (Item i : panier) {
				montant+=i.quantite*i.sandwich.prix;
				nbSandwiches+=i.quantite;
				i.commande=comm;
				i.save();
			}

			Cache.delete(session.getId()+"panier");
		}

		List<Commande> commandes = Commande.find("user = ? order by Id desc",user).fetch();

		render(userid, firstname, lastname, commandes);
	}

	/**
	 * Liste tous les items d'une commande.
	 */
	public static void itemsCommande() {
		Commande c = params.get("com", Commande.class); 
		List<Item> items = Item.find("commande", c).fetch();
		renderJSON(items);
	}

	/**
	 * Comme son nom l'indique, vide le panier.
	 */
	public static void viderPanier() {
		Cache.delete(session.getId()+"panier");
	}


}