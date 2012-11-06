package models;

import javax.persistence.Entity;

import play.cache.Cache;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Panier extends Model{
	
	@Required
	public int id_commande;
	
	@Required
	public int id_sandwich;
	
	@Required
	public int quantite;


}
