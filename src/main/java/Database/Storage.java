package Database;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.imageio.ImageIO;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.ObjectifyService;

public class Storage {
	private static Storage storage;
	private ArrayList<Exercise> exercises;
	private ArrayList<Workout> workouts;
	
	static {
		//try {
			//ObjectifyService.register(Observable.class);
			ObjectifyService.register(Exercise.class);
			ObjectifyService.register(Workout.class);
			ObjectifyService.register(DataPoint.class);
			ObjectifyService.register(ExerciseData.class);
			ObjectifyService.register(Client.class);
		//} catch (Exception e) {
		//	System.out.println(e);
		//}
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
		if(user == null) {
			return null;
		}
		
		Client result = null;
		
		List<Client> clients = ofy().load().type(Client.class).list();
		System.out.println(clients.toString());
		for(Client c: clients) {
			if(c.getUser().getEmail().equals(user.getEmail())) {
				return c;
			}
		}
		
		//client not found and we are going to create one
		Client newClient = new Client();
		newClient.setUser(user);
		saveClient(newClient);
		
		return newClient;

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
		exercises = excelParser.parseExercise();
		
		//Patrick will create workout parser
		workouts = excelParser.parseWorkout(exercises);
		
		
		/*for(Exercise e: exercises) {
			System.out.println(e.getName());
		}
		for(Workout w: workouts) {
			System.out.println(w.getWorkoutName());
		}*/
	}
	
	public ArrayList<Exercise> getAllExercises() {
		return exercises;
	}
	
	public ArrayList<Workout> getAllWorkouts() {
		return workouts;
	}
	
	public static BufferedImage getImageFromName(String name) {
		try {
			return ImageIO.read(new File("src\\main\\webapp\\WEB_INF\\inputFiles\\" + name + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//image could not be found
		return null;
	}
	
}
