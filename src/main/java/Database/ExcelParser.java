package Database;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class ExcelParser {
	public ArrayList<Exercise> parseExercise() {
		String csvFile = "WEB-INF/exercises.csv";
		BufferedReader reader = null;
		String line = "";
		String separator = ",";
		
		ArrayList<Exercise> list = new ArrayList<Exercise>();
		Exercise exercise;
		
		try {
			reader = new BufferedReader(new FileReader(csvFile));
			reader.readLine(); // skip that first line of the csvFile
			line = reader.readLine();
			while(line != null) {
				String[] split = line.split(separator);
				
				String name = split[1];
				String description = split[3];
				String keywordsLine = split[4];
				String startingWeight = split[5];
				String startingReps = split[6];
				String startingSets = "3";
				String imageName = name;
				
				ArrayList<String> keywords = new ArrayList<String>(Arrays.asList(keywordsLine.split(";\\s*")));
				
				exercise = new Exercise(name, description, keywords, imageName, Integer.parseInt(startingWeight), Integer.parseInt(startingReps), Integer.parseInt(startingSets));
				list.add(exercise);
				line = reader.readLine();
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
		
		return list;
	}
	
	public ArrayList<Workout> parseWorkout(ArrayList<Exercise> exercises) {
		String csvFile = "WEB-INF/workouts.csv";
		//System.out.println("testing");
		BufferedReader reader = null;
		String line = "";
		String separator = ",";
		
		ArrayList<Workout> workoutList = new ArrayList<Workout>();
		Workout workout;
		
		try {
			reader = new BufferedReader(new FileReader(csvFile));
			reader.readLine(); // skip the first line
			line = reader.readLine();
			while(line != null) {
				//System.out.println(line);
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
				//System.out.println(workoutName);
				
				
				exerciseList.add(getExercise(exerciseOne, exercises));
				exerciseList.add(getExercise(exerciseTwo, exercises));
				exerciseList.add(getExercise(exerciseThree, exercises));
				exerciseList.add(getExercise(exerciseFour, exercises));
				exerciseList.add(getExercise(exerciseFive, exercises));
				exerciseList.add(getExercise(exerciseSix, exercises));
				
				workout = new Workout(workoutName, workoutDescription, exerciseList, 0);
				workoutList.add(workout);
				line = reader.readLine();
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
	
	public Exercise getExercise(String name, ArrayList<Exercise> exercises) {
		for(int i = 0; i < exercises.size(); i++) {
			if(exercises.get(i).getName().equals(name)) {
				return exercises.get(i);
			}
		}
		
		//exercise not found
		return null;
	}
}
