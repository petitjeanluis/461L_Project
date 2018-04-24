package Database;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Workout {
	@Id Long id;
	
	@Index String workoutName;
	@Index String description;
	
	@Index String imageName;
	
	@Index ArrayList<Exercise> exercises;
	@Index ArrayList<String> keywords;
	
	@Index int currentExerciseIndex;

	public Workout() {
		
	}
	
	public Workout(String workoutName, String description, BufferedImage image, ArrayList<Exercise> exercises,
			int currentExerciseIndex) {
		this.workoutName = workoutName;
		this.description = description;
		//this.image = image;
		this.exercises = exercises;
		this.currentExerciseIndex = currentExerciseIndex;
		Storage.getInstance().saveWorkout(this);
	}
	
	public Workout(String workoutName, String description, ArrayList<Exercise> exercises, 
			int currentExerciseIndex) {
		this.workoutName = workoutName;
		this.description = description;
		this.exercises = exercises;
		this.currentExerciseIndex = currentExerciseIndex;
		Storage.getInstance().saveWorkout(this);
	}
	
	public Workout(String workoutName, String description, ArrayList<Exercise> exercises, ArrayList<String> keywords,
			int currentExerciseIndex) {
		this.keywords = keywords;
		this.workoutName = workoutName;
		this.description = description;
		this.exercises = exercises;
		this.currentExerciseIndex = currentExerciseIndex;
		Storage.getInstance().saveWorkout(this);
	}
	
	public Workout(String workoutName, ArrayList<Exercise> exercises) {
		this.workoutName = workoutName;
		this.description = "";
		this.exercises = exercises;
		this.currentExerciseIndex = 0;
		Storage.getInstance().saveWorkout(this);
	}

	public Exercise getNextExercise() {
		if(currentExerciseIndex < exercises.size()) {
			currentExerciseIndex++;
			return exercises.get(currentExerciseIndex);
		} else {
			return exercises.get(currentExerciseIndex);
		}
	}
	
	public Exercise getExerciseNum(int index) {
		if(index < exercises.size()) {
			return exercises.get(index);
		} else {
			return null;
		}
	}
	
	public String getWorkoutName() {
		return workoutName;
	}

	public void setWorkoutName(String workoutName) {
		this.workoutName = workoutName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BufferedImage getImage() {
		return Storage.getImageFromName(imageName);
	}

	public ArrayList<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(ArrayList<Exercise> exercises) {
		this.exercises = exercises;
	}

	public int getCurrentExerciseIndex() {
		return currentExerciseIndex;
	}

	public void setCurrentExerciseIndex(int currentExerciseIndex) {
		this.currentExerciseIndex = currentExerciseIndex;
	}
	
	public int getNumOfExercises() {
		return exercises.size();
	}
	
	public ArrayList<String> getKeywords() {
		return keywords;
	}
}
