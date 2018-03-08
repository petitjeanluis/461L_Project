package Database;

import java.util.ArrayList;
import java.util.Date;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Client {
	@Id Long id;
	@Index private User user;
	
	@Index private Workout currentWorkout;
	
	//past workouts with tracking data
	@Index private ArrayList<Workout> pastWorkouts;
	@Index private ArrayList<Workout> customWorkouts;
	
	//holds data on each individual exercise performed by user
	@Index private ArrayList<ExerciseData> exerciseData;

	public Client(User user, ArrayList<Workout> pastWorkouts, ArrayList<ExerciseData> exerciseData) {
		this.user = user;
		this.pastWorkouts = pastWorkouts;
		this.exerciseData = exerciseData;
	}

	public void updateExerciseData(Exercise exercise, int weight, int reps, Date day) {
		for(ExerciseData data: exerciseData) {
			Exercise e = data.getExercise();
			if(exercise.getName().compareTo(e.getName()) == 0) {
				//this is the exercise we want to update
				data.addDataPoint(weight, reps, day);
			} else {
				ExerciseData dataPoint = new ExerciseData(exercise, weight, reps, day);
				exerciseData.add(dataPoint);
			}
		}
	}
	
	public void addCustomWorkout(Workout workout) {
		customWorkouts.add(workout);
	}
	
	public void addPastWorkout(Workout workout) {
		pastWorkouts.add(workout);
	}
	
	public User getUser() {
		return user;
	}

	public Workout getCurrentWorkout() {
		return currentWorkout;
	}

	public void setCurrentWorkout(Workout currentWorkout) {
		this.currentWorkout = currentWorkout;
	}

	public ArrayList<Workout> getPastWorkouts() {
		return pastWorkouts;
	}

	public ArrayList<Workout> getCustomWorkouts() {
		return customWorkouts;
	}

	public ArrayList<ExerciseData> getExerciseData() {
		return exerciseData;
	}
	
}
