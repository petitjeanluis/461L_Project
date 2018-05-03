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
	@Index boolean allowSharing;
	
	@Index private Workout currentWorkout;
	@Index private int currentExerciseIndex;
	
	//past workouts with tracking data
	@Index private ArrayList<Workout> customWorkouts;
	@Index private ArrayList<Workout> friendsWorkouts;
	
	//holds friends that you have added based on email
	@Index private ArrayList<String> friendsEmails;
	
	//holds data on each individual exercise performed by user
	@Index private ArrayList<ExerciseData> exerciseData;
	
	public Client() {
		//objectify requires there to be a no-arg constructor
		//can't set user because client has to have a no-arg constructor
		this.exerciseData = new ArrayList<ExerciseData>();
		this.customWorkouts = new ArrayList<Workout>();
		this.friendsEmails = new ArrayList<String>();
		this.friendsWorkouts = new ArrayList<Workout>();
		
		allowSharing = false;
	}
	
	//always set user after making the client
	public void setUser(User user) {
		this.user = user;
	}
	
	public boolean getAllowSharing() {
		return allowSharing;
	}
	
	public void toggleAllowSharingTrue() {
		//sharing can only ever be turned on never off
		allowSharing = true;
	}
	
	public void updateExerciseData(Exercise exercise, DataPoint data) {
		//captures whether or not the exercise was able to updated
		//or if an ExerciseData object will have to be created because
		//it is the first time for the client to do the exercise
		boolean updated = false;
		
		for(ExerciseData exercises: exerciseData) {
			if(exercise.getName().compareTo(exercises.getExerciseName()) == 0) {
				//this is the exercise we want to update
				exercises.addDataPoint(data);
				updated = true;
			} 
		}
		
		//was not available to update because the client has never done the exercise before
		if (updated == false){
			//will create a new ExerciseData entry for the new exercise to the Client
			ExerciseData newDataEntry = new ExerciseData(exercise, data);
			exerciseData.add(newDataEntry);
		}
		
		Storage.getInstance().saveClient(this);
	}
	
	public int getSet(Exercise e) {
		for(ExerciseData exercises: exerciseData) {
			if(e.getName().equals(exercises.getExerciseName())) {
				return exercises.getSetsBasedOnHistory();
			} 
		}
		//if no exercisedata exists yet then it means that the client has not 
		//done the exercise yet i.e. the client should start on the first set
		return 1;
	}
	
	public int getWeight(Exercise e) {
		//this accounts for some exercises like planks where the weight is not applicable		
		for(ExerciseData exercises: exerciseData) {
			if(e.getName().equals(exercises.getExerciseName())) {
				return exercises.getWeightBasedOnHistory();
			} 
		}
		
		//if no history then use the default/startingweight
		return e.getStartingWeight();
	}
	
	public void addFriend(String email) {
		friendsEmails.add(email);
	}
	
	public void removeFriend(String email) {
		for(int i = 0; i < friendsEmails.size(); i++ ) {
			if(friendsEmails.get(i).equals(email)) {
				friendsEmails.remove(i);
			}
		}
	}
	
	public ArrayList<String> getAllPotentialFriendWorkouts(String email) {
		Storage storage = Storage.getInstance();
		ArrayList<String> all = storage.getFriendsWorkoutNamesFromEmail(email);
		
		ArrayList<String> result = new ArrayList<String>();
		for(String s: all) {
			
			//we don't want to be able to add two of the same friend's workout
			boolean found = false;
			
			for(Workout w: friendsWorkouts) {
				if(s.equals(w.getWorkoutName())) {
					found = true;
				}
			}
			
			if(found == false) {
				result.add(s);
			}
		}
		
		return result;
	}
	
	public ArrayList<String> getAllPotentialFriends() {
		Storage storage = Storage.getInstance();
		ArrayList<String> all = storage.getAllClientsEmails();
		ArrayList<String> result = new ArrayList<String>();
		for(String s: all) {
			
			//we don't want to be able to add our friends twice
			boolean found = false;
			
			if(s.equals(user.getEmail())) {
				found = true;
			}
			
			for(String friend: friendsEmails) {
				if(s.equals(friend)) {
					found = true;
				}
			}
			
			if(found == false) {
				result.add(s);
			}
		}
		return result;
	}
	
	public void addFriendsWorkout(String email, String friendsWorkout) {
		Storage storage = Storage.getInstance();
		Workout w = storage.getFriendsWorkoutFromEmail(email, friendsWorkout);
		
		friendsWorkouts.add(w);
	}
	
	public ArrayList<String> getFriendsEmails() {
		return friendsEmails;
	}
	
	public int getReps(Exercise e) {	
		for(ExerciseData exercises: exerciseData) {
			if(e.getName().equals(exercises.getExerciseName())) {
				return exercises.getRepsBasedOnHistory();
			} 
		}
		
		//if no history then using the starting value
		return e.getStartingReps();
	}
	
	public User getUser() {
		return user;
	}
	
	public String getEmail() {
		return user.getEmail();
	}

	public Workout getCurrentWorkout() {
		return currentWorkout;
	}

	public void setCurrentWorkout(Workout currentWorkout) {
		this.currentWorkout = currentWorkout;
		Storage.getInstance().saveClient(this);
	}

	public void addCustomWorkout(Workout workout) {
		customWorkouts.add(workout);
	}
	
	public ArrayList<Workout> getCustomWorkouts() {
		return customWorkouts;
	}
	
	public ArrayList<Workout> getFriendAndCustomWorkout() {
		ArrayList<Workout> result = new ArrayList<Workout>();
		
		for(Workout w: customWorkouts) {
			result.add(w);
		}
		for(Workout w: friendsWorkouts) {
			result.add(w);
		}
		
		return result;
	}

	public ArrayList<ExerciseData> getExerciseData() {
		return exerciseData;
	}
	
	public ExerciseData getFirstExerciseDataSetForGraph() {
		//gets first set of data for testing purposes
		if(exerciseData.size() > 0 && exerciseData.get(0) != null) {
			for(ExerciseData e: exerciseData) {
				if(e.getDataPoints().size() >= 3) {
					return e;
				}
			}
		} else {
			return null;
		}
		return null;
	}
	
	public ExerciseData getData(String exerciseName) {
		//gets data for the graph
		//we want at least 3 data points in order for it to be graphed
		ExerciseData atLeast3 = null;
		
		for(ExerciseData e: exerciseData) {
			//get the first dataSet that has atLeast3 datapoints
			if(e.getDataPoints().size() >= 3 && atLeast3 == null) {
				atLeast3 = e;
			}
			
			//gets the exercisedata for the exercise you want or just the data for a
			if(e.getDataPoints().size() >= 3 && e.getExerciseName().equals(exerciseName)) {
				return e;
			}
		}
		
		return atLeast3;
	}
	
	public void populateFakeData() {
		//old method to allow us to try running the website before we could input data
		//not used anymore but might be needed
		DataPoint d;
		for(int i = 0; i < 10; i++) {
			d = new DataPoint(10+i, 8, 3, new Date(18, 3, i+1));
			System.out.println("Client:PopulateFakeData:" + Storage.getInstance().getExerciseFromName("Bench press").getName());
			updateExerciseData(Storage.getInstance().getExerciseFromName("Bench press"), d);
		}
	}
	
	public Workout getWorkoutFromName(String name) {
		for(Workout w: customWorkouts) {
			if(w.getWorkoutName().equals(name)) {
				return w;
			}
		}
		
		for(Workout w: friendsWorkouts) {
			if(w.getWorkoutName().equals(name)) {
				return w;
			}
		}
		
		return Storage.getInstance().getWorkoutFromName(name);
	}
	
	public void resetSets() {
		//resets all of the datapoints to 1 so that the next time they are used in 
		//a workout the proper number of sets is displayed
		if(currentWorkout != null) {
			ArrayList<Exercise> e = currentWorkout.getExercises();
			for(Exercise exercise: e) {
				for(ExerciseData data: exerciseData)  {
					if(data.getExerciseName().equals(exercise.getName())) {
						//exercise found in the exercise data
						System.out.println(data);
						data.resetSet();
					}
				}
			}
		}
	}
	
	public void updateSetForExercise(Exercise e, int currentSet) {
		for(ExerciseData data: exerciseData)  {
			if(data.getExerciseName().equals(e.getName())) {
				//exercise found in the exercise data
				data.updateSet(currentSet);
			}
		}
	}
	
	public void updateCurrentExerciseIndex(Exercise e) {
		ArrayList<Exercise> exercises = currentWorkout.getExercises();
		for (int i = 0; i < exercises.size(); i++) {
			if(e.getName().equals(exercises.get(i).getName())) {
				currentExerciseIndex = i;
			}
		}
	}
	
	
	public void setCurrentExerciseIndex(int i) {
		currentExerciseIndex = i;
	}
	
	public int getCurrentExerciseIndex() {
		return currentExerciseIndex;
	}
}
