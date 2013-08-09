package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Commande extends Model{

	public String date;
	
	@ManyToOne
	public User user;
	
	public int montant;
	
	public int nbSandwiches;
	
	public boolean livraison;	
	
	public Commande(String date, User user, int montant, int nbSandwiches, boolean livraison) {
		this.date = date;
		this.user = user;
		this.montant = montant;
		this.nbSandwiches=nbSandwiches;
		this.livraison = livraison;
	}
	
	public Commande() {
		this.livraison=false;
	}
	
	public String toJSON() {
		return  "{\"montant\":"+montant+",\"nbSandwiches\":"+nbSandwiches+",\"livraison\":"+livraison+", \"id\":"+this.getId()+"}";
	}
	


	
}
