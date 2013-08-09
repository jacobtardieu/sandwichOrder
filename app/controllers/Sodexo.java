package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import models.Commande;
import models.Item;
import models.Sandwich;
import play.cache.Cache;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;
/**
 * Gère l'interface d'administration de la Sodexo.
 * @author Jacob Tardieu
 *
 */
@With(Secure.class)
@Check("admin")
public class Sodexo extends Controller{

	/**
	 * Liste les commandes du jour
	 * @param date
	 * date à laquelle on veut les commandes
	 */
	public static void listerCommandes(String date) {
		
		//Récupération des infos de l'utilisateur
		String userid = Cache.get(session.getId()+"USERID", String.class);
		String firstname = Cache.get(session.getId()+"FIRSTNAME", String.class);
		String lastname = Cache.get(session.getId()+"LASTNAME", String.class);
		String email = Cache.get(session.getId()+"EMAIL", String.class);
		String status = Cache.get(session.getId()+"STATUS", String.class);
	
		List<Item> nbSandwiches = new ArrayList<Item>();
		boolean present = false;
		
		List<Commande> commandes = Commande.find("date",date).fetch();
		List<Item> items = Item.findAll();
		
		//Comptage du nombre de sandwich de chaque type
		List<Sandwich> sandwiches = Sandwich.findAll();
		for (Sandwich s : sandwiches) {
			int count=0;
			List<Item> listeItems = Item.find("sandwich like ? AND commande.date = ?",
					s, date).fetch();
			for (Item i : listeItems) {
				count+=i.quantite;
			}
			if (count!=0) {
			nbSandwiches.add(new Item(s, count));
			}
		}
		
		
		render(commandes, nbSandwiches, userid, firstname, lastname, status, date);
	}
	
	/**
	 * Liste les commandes en filtrant par nom et prénom
	 * Méthode non utilisée dans le code et non testée, destinée à une amélioration de l'interface.
	 * @param recherche
	 * La chaîne peut être le nom, le prénom ou les deux.
	 */
	public static void rechercheCommandes(String recherche) {
		String[] mots = recherche.split(" ");
		String query = "SELECT c FROM Commande c WHERE ";
		for (int i=0; i<mots.length; i++) {
			if (i>0) {
				query+=" OR ";
			}
			query+="c.user.firstname =" + mots[i] + "OR c.user.lastname =" + mots[i];
		}
		List<Commande> commandes = Commande.find(query, "").fetch();
		renderJSON(commandes);
	}
	
	/**
	 * Permet de lister les sandwiches de la base de donnée
	 */
	public static void listerSandwiches() {
		String status = Cache.get(session.getId()+"STATUS", String.class);
		String firstname = Cache.get(session.getId()+"FIRSTNAME", String.class);
		String lastname = Cache.get(session.getId()+"LASTNAME", String.class);
		
		List<Sandwich> l = Sandwich.findAll();
		render(l, status, firstname, lastname);		
	}
	
	
	/**
	 * Permet de modifier un sandwich existant
	 * @param s
	 * Sandwich à modifier
	 * @param name
	 * Nouveau nom
	 * @param description
	 * Nouvelle description
	 * @param prix
	 * Nouveau prix
	 */
	public static void modifierSandwich(Sandwich sandwich) {
		sandwich.save();
		listerSandwiches();	
	}

	/**
	 * Permet de créer un nouveau sandwich
	 * @param name
	 * Nom du sandwich
	 * @param description
	 * Description du sandwich
	 * @param prix
	 * Prix du sandwich
	 */
	public static void nouveauSandwich(String name, String description, float prix) {
		Sandwich s = new Sandwich(name, description, prix);
		s.save();
		listerSandwiches();	
	}

	/**
	 * Supprimer un sandwich existant
	 * @param s
	 * Sandwich à supprimer
	 */
	public static void supprimerSandwich(Sandwich s) {
		Sandwich.delete("name", s.name);
		listerSandwiches();	
	}
	
	/**
	 * Marque la commande comme livrée.
	 * @param c
	 */
	public static void livraison(Commande c) {
		c.livraison=true;
		System.out.println(c.livraison);
		c.save();
	}
	
	/**
	 * Marque la commande comme non livrée.
	 * @param c
	 */
	public static void retirerLivraison(Commande c) {
		c.livraison=false;
		c.save();
	}
	
}
