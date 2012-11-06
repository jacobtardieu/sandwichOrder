package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Commande extends Model{

	@Required
	public Date date;
	
	@Required
	@ManyToOne
	public User user;
	
	@Required
	public int montant;
	
	@Required
	public boolean livraison;
	
}
