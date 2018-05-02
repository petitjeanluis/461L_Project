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

	public Workout() {
		//no-arg constructor for objectify
	}
	
	public Workout(String workoutName, String description, ArrayList<Exercise> exercises) {
		this.workoutName = workoutName;
		this.description = description;
		this.exercises = exercises;
		Storage.getInstance().saveWorkout(this);
	}
	
	public Workout(String workoutName, ArrayList<Exercise> exercises) {
		this.workoutName = workoutName;
		this.exercises = exercises;
		Storage.getInstance().saveWorkout(this);
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

	public ArrayList<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(ArrayList<Exercise> exercises) {
		this.exercises = exercises;
	}
	
	public int getNumOfExercises() {
		return exercises.size();
	}
}
