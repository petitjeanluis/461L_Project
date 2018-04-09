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
	//@Index private ArrayList<Workout> pastWorkouts;
	@Index private ArrayList<Workout> customWorkouts;
	
	//holds data on each individual exercise performed by user
	@Index private ArrayList<ExerciseData> exerciseData;

	public Client(User user, ArrayList<ExerciseData> exerciseData) {
		this.user = user;
		//this.pastWorkouts = pastWorkouts;
		this.exerciseData = exerciseData;
	}

	public void updateExerciseData(Exercise exercise, DataPoint data) {
		boolean updated = false;
		for(ExerciseData exercises: exerciseData) {
			if(exercise.getName().compareTo(exercises.getExerciseName()) == 0) {
				//this is the exercise we want to update
				exercises.addDataPoint(data);
				updated = true;
			} 
		}
		//was not available to update
		if (updated == false){
			//will create a new DataStorage for the exercise
			ExerciseData newDataEntry = new ExerciseData(exercise, data);
			exerciseData.add(newDataEntry);
		}
		
		Storage.getInstance().saveClient(this);
	}
	
	public void addCustomWorkout(Workout workout) {
		customWorkouts.add(workout);
		Storage.getInstance().saveClient(this);
	}
	
	/*public void addPastWorkout(Workout workout) {
		pastWorkouts.add(workout);
	}*/
	
	public User getUser() {
		return user;
	}

	public Workout getCurrentWorkout() {
		return currentWorkout;
	}

	public void setCurrentWorkout(Workout currentWorkout) {
		this.currentWorkout = currentWorkout;
		Storage.getInstance().saveClient(this);
	}

	/*public ArrayList<Workout> getPastWorkouts() {
		return pastWorkouts;
	}*/

	public ArrayList<Workout> getCustomWorkouts() {
		return customWorkouts;
	}

	public ArrayList<ExerciseData> getExerciseData() {
		return exerciseData;
	}
	
}
