import models.Sandwich;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job {
	
	public void doJob() {
		
		if(Sandwich.count() == 0) { // Check if the database is empty
			Sandwich s1 = new Sandwich();
			s1.name = "Sandwich1";
			s1.description = "DDDDescription du sandwich1";
			s1.prix=1;
			s1.save();
			
			Sandwich s2 = new Sandwich();
			s2.name = "Sandwich2";
			s2.description = "Description du sandwich2";
			s2.prix=2;
			s2.save();
			
			Sandwich s3 = new Sandwich();
			s3.name = "Sandwich3";
			s3.description = "Le meilleur des sandwiches avec du Caviar et autre";
			s3.prix=2000;
			s3.save();
			
			Sandwich s4 = new Sandwich();
			s4.name = "Sandwich4";
			s4.description = "Le meilleur des sandwiches avec du Caviar et autre";
			s4.prix=2000;
			s4.save();
			
			Sandwich s5 = new Sandwich();
			s5.name = "Sandwich5";
			s5.description = "Le meilleur des sandwiches avec du Caviar et autre";
			s5.prix=2000;
			s5.save();
			
			Sandwich s6 = new Sandwich();
			s6.name = "Sandwich6";
			s6.description = "Le meilleur des sandwiches avec du Caviar et autre";
			s6.prix=2000;
			s6.save();
			
			Sandwich s7 = new Sandwich();
			s7.name = "Sandwich7";
			s7.description = "Le meilleur des sandwiches avec du Caviar et autre";
			s7.prix=2000;
			s7.save();
			
			Sandwich s8 = new Sandwich();
			s8.name = "Sandwich8";
			s8.description = "Le meilleur des sandwiches avec du Caviar et autre";
			s8.prix=2000;
			s8.save();
		}
	}
}
