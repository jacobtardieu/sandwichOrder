import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

	//Useless default test
    /*@Test
    public void aVeryImportantThingToTest() {
        assertEquals(2, 1 + 1);
    }*/
	
	@Test public void CreateAndRetreiveSandwich() {
		new Sandwich("sand1", "Le premier sandwich", 3).save();
		
		Sandwich miam = Sandwich.find("byName", "sand1").first();
		
		assertNotNull(miam);
		assertEquals("sand1", miam.name);
		assertEquals("Le premier sandwich", miam.description);
	}

}
