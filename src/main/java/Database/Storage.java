package Database;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.ObjectifyService;

public class Storage {
	private static Storage storage;
	private static ArrayList<Exercise> exercises;
	private static ArrayList<Workout> workouts;
	
	static {
		ObjectifyService.register(Client.class);
	}
	
	private Storage() {
		exercises = new ArrayList<Exercise>();
		workouts = new ArrayList<Workout>();
		
		populateExerciseAndWorkout();
	}
	
	public static Storage getInstance() {
		if(storage == null) {
			synchronized (Storage.class) {
				if(storage == null) {
					storage = new Storage();
				}
			}
		}
		return storage;
	}
	
	public void saveClient(Client c) {
		ofy().save().entity(c).now();
	}
	
	public Client loadClient(User user) {
		Client result = null;
		
		List<Client> clients = ofy().load().type(Client.class).list();
		for(Client c: clients) {
			if(c.getUser().getEmail().equals(user.getEmail())) {
				return c;
			}
		}
		
		return result;
	}
	
	public Client findFriend(String name) {
		Client result = null;
		
		List<Client> clients = ofy().load().type(Client.class).list();
		for(Client c: clients) {
			if(c.getUser().getEmail().equals(name)) {
				return c;
			}
		}
		
		return result;
	}
	
	private void populateExerciseAndWorkout() {
		//Call Patricks methods
		ExcelParser excelParser = new ExcelParser();
		excelParser.parse();
	}
	
	public ArrayList<Exercise> getAllExercises() {
		return exercises;
	}
	
	public ArrayList<Workout> getAllWorkouts() {
		return workouts;
	}
}
