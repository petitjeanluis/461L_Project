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
	private static ArrayList<Exercise> exercises;
	private static ArrayList<Workout> workouts;
	
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
		
		//populateExerciseAndWorkout();
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
	
	public void saveDataPoint(DataPoint d) {
		ofy().save().entity(d).now();
	}
	
	public void saveWorkout(Workout w) {
		ofy().save().entity(w).now();
	}
	
	public void saveExerciseData(ExerciseData e) {
		ofy().save().entity(e).now();
	}
	
	public void saveExercise(Exercise e) {
		ofy().save().entity(e).now();
	}
	
	public Client loadClient(User user) {
		if(workouts.size() == 0 || exercises.size()== 0) {
			populateExerciseAndWorkout();
		}
		if(user == null) {
			return null;
		}
		Client result = null;
		
		//this first load makes the second one synchronous
		ofy().load().type(Client.class).first().now();
		List<Client> clients = ofy().load().type(Client.class).list();
		System.out.println(clients.toString());
		for(Client c: clients) {
			if(c.getUser().getEmail().equals(user.getEmail())) {
				//ofy().delete().entity(c).now();
				return c;
			}
		}
		
		System.out.println("Making new client");
		//client not found and we are going to create one
		Client newClient = new Client();
		newClient.setUser(user);
		saveClient(newClient);
		
		return newClient;

	}
	
	public Client findFriend(String name) {
		Client result = null;
		
		//this first load makes the second one synchronous
		ofy().load().type(Client.class).first().now();
		List<Client> clients = ofy().load().type(Client.class).list();
		for(Client c: clients) {
			if(c.getUser().getEmail().equals(name)) {
				return c;
			}
		}
		
		return result;
	}
	
	public void populateExerciseAndWorkout() {
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
		if(workouts.size() == 0 || exercises.size()== 0) {
			populateExerciseAndWorkout();
		}
		return exercises;
	}
	
	public ArrayList<Workout> getAllWorkouts() {
		if(workouts.size() == 0 || exercises.size()== 0) {
			populateExerciseAndWorkout();
		}
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
	
	public Exercise getExercise(String name) {
		//System.out.println("Storage: " + exercises.size());
		if(workouts.size() == 0 || exercises.size()== 0) {
			populateExerciseAndWorkout();
		}
		for(int i = 0; i < exercises.size(); i++) {
			//System.out.println("Storage: " + exercises.get(i).getName());
			if(exercises.get(i).getName().equals(name)) {
				return exercises.get(i);
			}
		}
		
		//exercise not found
		System.out.println("Storage Class");
		System.out.println("Exercise not found");
		System.out.println(name);
		return null;
	}
	
	public Workout getWorkoutFromName(String name) {
		for(Workout w: workouts) {
			if(w.getWorkoutName().equals(name)) {
				return w;
			}
		}
		return null;
	}
	
}
