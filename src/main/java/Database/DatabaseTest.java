package Database;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;


public class DatabaseTest {
	
	final String[] expectedExercises = new String[] {"Bench press", "Flat dumbbell press", "Incline bench press", "Incline dumbbell press",
			"Pec fly machine", "Cable flys", "Flat dumbbell flys", "Incline dumbbell flys", "Chest press machine", "Barbell curl", "Dumbbell curl", 
			"Incline dumbbell hammer curl", "Incline dumbbell curl", "Hammer curl", "Preacher curl", "Dips", "Pushups", "Skullcrushers", "Tricep pulldowns", 
			"Close grip bench press", "Overhead dumbbell extension", "Wrist curls", "Farmer's walk", "Zottman curls", "Reverse wrist curls", 
			"Pullup bar hang", "Plate tosses", "Pinch carries", "Calf press machine", "Toe raises", "Jumping jacks", "Box jumps", "Bulgarian split squat", 
			"Leg extension", "Kneeling leg curl", "Leg press machine", "Lunges", "Step ups", "Squats", "Overhead press", "Shrugs", "Side lateral raise", 
			"Upright barbell row", "Front lateral raise", "Dumbbell shoulder press", "Dumbbell side bend", "Plank", "Air bike", "Hanging leg raises",
			"Mountain climbers", "Weighted incline crunches", "Flutter kicks", "Deadlifts", "Row machine", "One arm dumbbell row", "Pulldowns", 
			"Pullups", "Bent over barbell row", "Back extension"};

	final String[] expectedWorkouts = new String[] {"Chest", "Arms", "Basic Legs", "Shoulders", "Abs", "Swimming", "Basketball", 
			"Explosiveness", "Baseball", "Football", "Golf", "Tennis"};
	
	Storage storage;
	Workout w;
	Exercise ex;
	DataPoint dp;
	ExerciseData ed;
	Client c;
	
	@Before                       //@Before in jUnit 4
	// Informs JUnit that this method should be run before each test
	public void setUp() {
		//storage = Storage.getInstance();
		//storage.populateExerciseAndWorkout();
		ex = new Exercise("Poop", "Squat on toilet", new ArrayList<String>(), "fakeImage", 10, 10, 10, false);
		
		dp = new DataPoint(10, 10, 10, new Date(), false);
		
		w = new Workout();
		
		ed = new ExerciseData(ex, dp, false);
		
		c = new Client();
	}	
	
//	@Test
//	public void testExerciseParse() {
//		ArrayList<String> expected = new ArrayList<String>();	//put in expected exercises
//		
//		Collections.addAll(expected, expectedExercises);
//		//System.out.println(Arrays.toString(expected.toArray()));
//		
//		ExcelParser excelParser = new ExcelParser();
//		ArrayList<Exercise> exercises = excelParser.parseExercise();
//		
//		for(Exercise e : exercises) {
//			assertTrue(expected.contains(e.getName()));		//check if we expected this exercise
//			if(expected.contains(e.getName())) expected.remove(e.getName());	
//		}
//	}
	
//	@Test
//	public void testWorkoutParse() {
//		ArrayList<String> expected = new ArrayList<String>();	//put in expected exercises
//		
//		//TODO add ALL exercises into arraylist
//		Collections.addAll(expected, expectedWorkouts);
//		
//		ExcelParser excelParser = new ExcelParser();
//		
//		ArrayList<Workout> workouts = excelParser.parseWorkout(excelParser.parseExercise());
//		
//		for(Workout w : workouts) {
//			assertTrue(expected.contains(w.getWorkoutName()));		//check if we expected this exercise
//			if(expected.contains(w.getWorkoutName())) expected.remove(w.getWorkoutName());	
//		}
//	}
	
//	@Test
//	public void testStorage() {
//		for(String exercise : expectedExercises) {
//			Exercise e = Storage.getInstance().getExercise(exercise);
//			assertTrue(e != null);
//			assertTrue((e.getDescription() != null) && (e.getImage() != null) && (e.getKeywords() != null) && (e.getName() != null)
//					&& (e.getStartingReps() > 0) && (e.getStartingSets() > 0) && (e.getStartingWeight() > 0));
//		}	
//	}

	@Test
	public void testExercise() { 
		System.out.println(ex.getDescription());
		System.out.println(ex.getName());
		System.out.println(ex.getStartingReps());
		System.out.println(ex.getStartingWeight());
		System.out.println(ex.getStartingSets());
		assertTrue(ex.getName().equals("Poop") && ex.getDescription().equals("Squat on toilet") && ex.getKeywords().isEmpty() 
				&& ex.getStartingReps() == 10 && ex.getStartingSets() == 10 && ex.getStartingWeight() == 10);
	}
	
	@Test
	public void testDataPoint() {
		assertTrue(dp.getWeight() == 10 && dp.getReps() == 10);
	}
	
	@Test
	public void testWorkout() {
		ArrayList<Exercise> e = new ArrayList<Exercise>();
		e.add(ex);
		w.setExercises(e);
		w.setDescription("Simon Peter");
		w.setWorkoutName("what");
		assertTrue(w.getDescription().equals("Simon Peter") && w.getExerciseNum(0).equals(ex) && 
				w.getExercises().equals(e) && w.getNumOfExercises() == 1 && w.getWorkoutName().equals("what"));
	}

	@Test
	public void testExerciseData() {
		assertTrue(ed.getExercise().equals(ex) && ed.getDataPoints().get(0).equals(dp));
	}
	
	@Test
	public void testClient() {
		ArrayList<Exercise> e = new ArrayList<Exercise>();
		e.add(ex);
		w.setExercises(e);
		
		c.addCustomWorkout(w);
		assertTrue(c.getCustomWorkouts().get(0).equals(w));
	}
	
}
