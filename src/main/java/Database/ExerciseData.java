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
	
	@Index private ArrayList<DataPoint> data;
	/*
	private Long id;
	private Exercise exercise;
	
	private ArrayList<DataPoint> data;*/
	
	public ExerciseData() {
		
	}
	
	public ExerciseData(Exercise exercise, DataPoint dataPoint) {
		this.exercise = exercise.newExercise();
		data = new ArrayList<DataPoint>();
		data.add(dataPoint);
		Storage.getInstance().saveExerciseData(this);
	}
	
	public ExerciseData(Exercise exercise, DataPoint dataPoint, boolean fake) {		//constructor just for tests
		this.exercise = exercise;
		data = new ArrayList<DataPoint>();
		data.add(dataPoint);
	}	
	
	public Exercise getExercise() {
		return exercise;
	}
	
	public ArrayList<DataPoint> getDataPoints() {
		return data;
	}
	
	public String getExerciseName() {
		return exercise.getName();
	}
	
	public void addDataPoint(DataPoint dataPoint) {
		//this is code to test the graph NOT final code
		data.add( dataPoint);
		
		//below is the correct code
		/*if(data.size()>0) {
			if(data.get(data.size()-1).getDate().equals(dataPoint.getDate())) {
				data.set(data.size()-1, dataPoint);
			} else {
				data.add(dataPoint);
			}
		} else {
			data.add(dataPoint);
		}*/
		
	}
	
	public int getRepsBasedOnHistory() {
		if(data.size()==0) {
			return exercise.getStartingReps();
		}
		return data.get(data.size()-1).getReps();
	}
	
	public int getSetsBasedOnHistory() {


		if(data.size() == 0) {
			return 1;
		}
		for(DataPoint p: data) {
			System.out.println("DataPoints" + p.getSets());
		}
		System.out.println("ExerciseData: getSetsBasedOnHistory: "+exercise.getName() +".getSets: " + (data.get(data.size()-1).getSets() + 1) % exercise.getStartingSets());
		System.out.println("ExerciseData: getSetsBasedOnHistory: " + data.get(data.size()-1).getSets() + data.get(data.size()-2).getWeight());
		System.out.println("ExerciseData: " + exercise.getName() + ", current set: " + data.get(data.size()-1).getSets());
		return ((data.get(data.size()-1).getSets()) % exercise.getStartingSets())+1;
	}
	
	public int getWeightBasedOnHistory() {
		int count = 0;
		int amountInLast2Weeks = exerciseInLast2Weeks();
		if(amountInLast2Weeks>0) {
			//take average of last two weeks
			int average = 0;
			for(int i = data.size()-1; i > data.size()-1 - amountInLast2Weeks; i--) {
				average += data.get(i).getWeight();
			}
			average = average/amountInLast2Weeks;
			
			int additionalWeight = 5;
			if(data.size()%5 == 3 || data.size() % 5 == 4) {
				additionalWeight = 10;
			} 
			
			if(average%5 >3) {
				return 5*(average/5) + 5 + additionalWeight;
			} else {
				return 5*(average/5) + additionalWeight;
			}
		} else {
			//take last workout and add 5
			return data.get(data.size()-1).getWeight();
		}
	}
	
	private int exerciseInLast2Weeks() {
		int result = 0;
		Date twoWeeksAgo = new Date();
		twoWeeksAgo.setTime(twoWeeksAgo.getTime()-20160000);
		for(int i = data.size() -1; i >= 0; i--) {
			Date pastDate = data.get(i).getDate();
			if(pastDate.before(twoWeeksAgo)) {
				result++;
			}
		}
		return result;
	}
}
