package models;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import play.cache.Cache;
import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * Classe qui représente un item du panier, i.e. un sandwich et une quantité.
 * @author jacob
 *
 */
@Entity
public class Item extends Model{

	@Required
	@ManyToOne
	public Commande commande;

	@Required
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	public Sandwich sandwich;

	@Required
	public int quantite;

	public Item(Sandwich sandwich, int quantite) {
		this.sandwich = sandwich;
		this.quantite = quantite;
	}

	//TODO
	public String toJSON() {
		return null;
	}


}
