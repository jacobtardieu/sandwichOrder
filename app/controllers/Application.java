package controllers;

import play.*;
import play.cache.Cache;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {


    public static void index() {
        List<Sandwich> sandwiches = Sandwich.findAll();
       
        //panier(null,2);
       
        render(sandwiches);
    }
   
    public static void panier(Sandwich s, int quantite) {
        HashMap<Sandwich, Integer> panier = (HashMap) Cache.get(session.getId()+"panier");
        if (panier==null) {
            panier = new HashMap<Sandwich, Integer>();
        }

        panier.put(s, quantite);
        panier.put(new Sandwich("test", "toto", 3), 1);
        Cache.set(session.getId()+"panier", panier);
       
    }
    

}