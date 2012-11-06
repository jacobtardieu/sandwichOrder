package models;

import java.util.Date;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Commande extends Model{

	@Required
	public Date date;
	
	@Required
	public int id_user;
	
	@Required
	public int montant;
	
	@Required
	public boolean livraison;
	
}
