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
			s3.description = "Le meilleur des sandwiches";
			s3.prix=2000;
			s3.save();
		}
	}
}
