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
	
	@Index private ArrayList<DataPoint> data;
	
	public ExerciseData(Exercise exercise, DataPoint dataPoint) {
		this.exercise = exercise;
		data = new ArrayList<DataPoint>();
		data.add(dataPoint);
	}	
	
	public Exercise getExercise() {
		return exercise;
	}
	
	public String getExerciseName() {
		return exercise.getName();
	}
	
	public void addDataPoint(DataPoint dataPoint) {
		data.add(dataPoint);
	}
}
