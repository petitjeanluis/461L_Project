package Database;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


public class DatabaseTest {

	@Before                       //@Before in jUnit 4
	// Informs JUnit that this method should be run before each test
	public void setUp() {
		Storage storage = Storage.getInstance();
	}	
	
	@Test
	public void testExcel() {
		ExcelParser excelParser = new ExcelParser();
		ArrayList<Exercise> exercises = excelParser.parseExercise();
		
	}

	
}
