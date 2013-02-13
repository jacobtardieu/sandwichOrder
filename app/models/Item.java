package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.cache.Cache;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Panier extends Model{
	
	@Required
	public Commande commande;
	
	@Required
	@ManyToOne // Judicieux ?
	public Sandwich sandwich;
	
	@Required
	public int quantite;


}
