package Database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class WorkoutParser {
	public ArrayList<Workout> parseWorkout() {
		String csvFile = "src\\main\\webapp\\WEB_INF\\recommended.csv";
		BufferedReader reader = null;
		String line = "";
		String separator = ",";
		
		Storage storage = Storage.getInstance();
		ArrayList<Workout> workoutList = new ArrayList<Workout>();
		Workout workout;
		
		try {
			reader = new BufferedReader(new FileReader(csvFile));
			reader.readLine(); // skip the first line
			while((line = reader.readLine()) != null) {
				String[] split = line.split(separator);
				ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();
				
				String workoutName = split[0];
				String exerciseOne = split[1];
				String exerciseTwo = split[2];
				String exerciseThree = split[3];
				String exerciseFour = split[4];
				String exerciseFive = split[5];
				String exerciseSix = split[6];
				String workoutDescription = split[7];
				
				
				exerciseList.add(storage.sendExercise(exerciseOne));
				exerciseList.add(storage.sendExercise(exerciseTwo));
				exerciseList.add(storage.sendExercise(exerciseThree));
				exerciseList.add(storage.sendExercise(exerciseFour));
				exerciseList.add(storage.sendExercise(exerciseFive));
				exerciseList.add(storage.sendExercise(exerciseSix));
				
				workout = new Workout(workoutName, workoutDescription, exerciseList, 0);
				workoutList.add(workout);
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		return workoutList;
	}
}
