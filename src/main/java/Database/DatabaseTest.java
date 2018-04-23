package Database;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;


public class DatabaseTest {

	@Before                       //@Before in jUnit 4
	// Informs JUnit that this method should be run before each test
	public void setUp() {
		Storage storage = Storage.getInstance();
	}	
	
	@Test
	public void testExerciseParse() {
		ArrayList<String> expected = new ArrayList<String>();	//put in expected exercises
		
		Collections.addAll(expected, new String[]{"Bench press", "Flat dumbbell press", "Incline bench press", "Incline dumbbell press",
				"Pec fly machine", "Cable flys", "Flat dumbbell flys", "Incline dumbbell flys", "Chest press machine", "Barbell curl", "Dumbbell curl", 
				"Incline dumbbell hammer curl", "Incline dumbbell curl", "Hammer curl", "Preacher curl", "Dips", "Pushups", "Skullcrushers", "Tricep pulldowns", 
				"Close grip bench press", "Overhead dumbbell extension", "Wrist curls", "Farmer's walk", "Zottman curls", "Reverse wrist curls", 
				"Pullup bar hang", "Plate tosses", "Pinch carries", "Calf press machine", "Toe raises", "Jumping jacks", "Box jumps", "Bulgarian split squat", 
				"Leg extension", "Kneeling leg curl", "Leg press machine", "Lunges", "Step ups", "Squats", "Overhead press", "Shrugs", "Side lateral raise", 
				"Upright barbell row", "Front lateral raise", "Dumbbell shoulder press", "Dumbbell side bend", "Plank", "Air bike", "Hanging leg raises",
				"Mountain climbers", "Weighted incline crunches", "Flutter kicks", "Deadlifts", "Row machine", "One arm dumbbell row", "Pulldowns", 
				"Pullups", "Bent over barbell row", "Back extension"});
		//System.out.println(Arrays.toString(expected.toArray()));
		
		ExcelParser excelParser = new ExcelParser();
		ArrayList<Exercise> exercises = excelParser.parseExercise();
		
		for(Exercise e : exercises) {
			assertTrue(expected.contains(e.getName()));		//check if we expected this exercise
			if(expected.contains(e.getName())) expected.remove(e.getName());	
		}
	}
	
	@Test
	public void testWorkoutParse() {
		ArrayList<String> expected = new ArrayList<String>();	//put in expected exercises
		
		//TODO add ALL exercises into arraylist
		Collections.addAll(expected, new String[]{""});
		
		ExcelParser excelParser = new ExcelParser();
		
		ArrayList<Workout> workouts = excelParser.parseWorkout(excelParser.parseExercise());
		
		for(Workout w : workouts) {
			assertTrue(expected.contains(w.getWorkoutName()));		//check if we expected this exercise
			if(expected.contains(w.getWorkoutName())) expected.remove(w.getWorkoutName());	
		}
	}

	
}
