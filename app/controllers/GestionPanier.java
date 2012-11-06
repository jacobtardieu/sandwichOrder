package controllers;

import java.util.HashMap;

import models.Sandwich;
import play.cache.Cache;
import play.mvc.Controller;

public class GestionPanier extends Controller {

	public HashMap<Sandwich, Integer> panier;
	
	public static void index(Sandwich sandwich, int quantite) {
		
		HashMap<Sandwich, Integer> panier = new HashMap<Sandwich, Integer>();

		if (Cache.get(session.getId()+"panier")!=HashMap.class) {
	//		HashMap<Sandwich, Integer> panier = new HashMap<Sandwich, Integer>();
		}
		else {
		//	HashMap panier = (HashMap) Cache.get(session.getId()+"panier");
		}
		panier.put(sandwich, quantite);
		Cache.set(session.getId()+"panier", panier);


	}

}
