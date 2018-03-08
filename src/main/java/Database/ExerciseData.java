package Database;

import java.util.ArrayList;
import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class ExerciseData {
	@Id private Long id;
	@Index private Exercise exercise;
	@Index private int numOfTimesDone;
	
	@Index private ArrayList<Integer> actualWeight;
	@Index private ArrayList<Integer> actualReps;
	@Index private ArrayList<Date> dateExercised;
	
	public ExerciseData(Exercise exercise, ArrayList<Integer> actualWeight,
			ArrayList<Integer> actualReps, ArrayList<Date> dateExercised) {
		this.exercise = exercise;
		this.numOfTimesDone = 1;
		this.actualWeight = actualWeight;
		this.actualReps = actualReps;
		this.dateExercised = dateExercised;
	}	
	
	public ExerciseData(Exercise exercise, int actualWeight,
			int actualReps, Date dateExercised) {
		this.exercise = exercise;
		this.numOfTimesDone = 1;
		
		ArrayList<Integer> weight = new ArrayList<Integer>();
		ArrayList<Integer> reps = new ArrayList<Integer>();
		ArrayList<Date> date = new ArrayList<Date>();
		
		weight.add(actualWeight);
		this.actualWeight = weight;
		reps.add(actualReps);
		this.actualReps = reps;
		date.add(dateExercised);
		this.dateExercised = date;
	}	
	
	public Exercise getExercise() {
		return exercise;
	}
	
	public void addDataPoint(int weight, int reps, Date day) {
		actualWeight.add(weight);
		actualReps.add(reps);
		dateExercised.add(day);
		
		numOfTimesDone++;
	}
}
