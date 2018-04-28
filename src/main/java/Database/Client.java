package Database;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

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


	//holds log of messages from friends
	//@Index private ArrayList<String> messageLog;
	
	public Client() {
		//can't set user because client has to have a no-arg constructor
		this.exerciseData = new ArrayList<ExerciseData>();
		this.customWorkouts = new ArrayList<Workout>();
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public void updateExerciseData(Exercise exercise, DataPoint data) {
		boolean updated = false;
		//System.out.println(exercise + "data: " + data);
		//System.out.println(user);
		for(ExerciseData exercises: exerciseData) {
			System.out.println("Client: updateExerciseData" + exercises.getExerciseName());
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
	
	/*public void addCustomWorkout(Workout workout) {
		customWorkouts.add(workout);
		Storage.getInstance().saveClient(this);
	}*/
	
	/*public void addPastWorkout(Workout workout) {
		pastWorkouts.add(workout);
	}*/
	
	public int getSet(Exercise e) {
		for(ExerciseData exercises: exerciseData) {
			if(e.getName().compareTo(exercises.getExerciseName()) == 0) {
				return exercises.getSetsBasedOnHistory();
			} 
		}
		return 1;
	}
	
	public int getWeight(Exercise e) {
		for(ExerciseData exercises: exerciseData) {
			if(e.getName().compareTo(exercises.getExerciseName()) == 0) {
				//
				return exercises.getWeightBasedOnHistory();
			} 
		}
		return e.getStartingWeight();
	}
	
	public int getReps(Exercise e) {
		if(e.getStartingReps() == 0) {
			return e.getStartingReps();
		}
		System.out.println("Client:GetReps: " + e.getName());
		for(ExerciseData exercises: exerciseData) {
			System.out.println("Client: GetReps: " + e.getName() + "exerciseData " + exercises.getExerciseName());
			if(e.getName().compareTo(exercises.getExerciseName()) == 0) {
				//
				return exercises.getRepsBasedOnHistory();
			} 
		}
		return e.getStartingReps();
	}
	
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

	public void addCustomWorkout(Workout workout) {
		customWorkouts.add(workout);
	}
	
	public ArrayList<Workout> getCustomWorkouts() {
		return customWorkouts;
	}

	public ArrayList<ExerciseData> getExerciseData() {
		return exerciseData;
	}
	
	public ExerciseData getFirstExerciseDataSet() {
		if(exerciseData.size() > 0 && exerciseData.get(0) != null) {
			return exerciseData.get(0);
		} else {
			return null;
		}
	}
	
	public ExerciseData getData(String exerciseName) {
		ExerciseData atLeast3 = null;
		for(ExerciseData e: exerciseData) {
			//get the first dataSet that has atLeast3 datapoints
			if(e.getDataPoints().size() > 3 && atLeast3 == null) {
				atLeast3 = e;
			}
			if(e.getDataPoints().size() > 3 && e.getExerciseName().equals(exerciseName)) {
				return e;
			}
		}
		return atLeast3;
	}
	
	public void populateFakeData() {
		DataPoint d;
		for(int i = 0; i < 10; i++) {
			d = new DataPoint(10+i, 8, 3, new Date(18, 3, i+1));
			System.out.println("Client:PopulateFakeData:" + Storage.getInstance().getExercise("Bench press").getName());
			updateExerciseData(Storage.getInstance().getExercise("Bench press"), d);
		}
	}
	
	public Workout getWorkoutFromName(String name) {
		for(Workout w: customWorkouts) {
			if(w.getWorkoutName().equals(name)) {
				return w;
			}
		}
		
		return Storage.getInstance().getWorkoutFromName(name);
	}
	
	/*public void sendToFriends(String message) {
		notifyObservers(message);
	}
	
	public void addFriends(String name) {
		Client client = Storage.getInstance().findFriend(name);
		if(client != null) {
			client.addObserver(this);
		} else System.out.println("No such name");
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO display message (or add to a log?)
		String friend = t
		String message = (String)arg1;
		messageLog.add(friend + " said:  " + message);
		Storage.getInstance().saveClient(this);
	}*/
	
}
