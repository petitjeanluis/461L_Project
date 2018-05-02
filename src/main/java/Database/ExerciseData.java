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

	public ExerciseData() {
		//no-arg constructor for objectify
	}
	
	public ExerciseData(Exercise exercise, DataPoint dataPoint) {
		this.exercise = exercise.newExercise();
		data = new ArrayList<DataPoint>();
		data.add(dataPoint);
		Storage.getInstance().saveExerciseData(this);
	}
	
	//constructor just for tests
	public ExerciseData(Exercise exercise, DataPoint dataPoint, boolean fake) {		
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
		data.add( dataPoint);
		
		//below is code for if we only want one datapoint per exercise per day
		/*if(data.size()>0) {
			if(sameDate(data.get(data.size()-1).getDate(),dataPoint.getDate())) {
				data.get(data.size()-1).updateDataPoint(dataPoint); 
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
		} else {
			return data.get(data.size()-1).getSets();
		}
		
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
		Date twoWeeksAgo = new Date();
		twoWeeksAgo.setTime(twoWeeksAgo.getTime()-20160000);
		
		int result = 0;
		for(int i = data.size() -1; i >= 0; i--) {
			Date pastDate = data.get(i).getDate();
			if(pastDate.before(twoWeeksAgo)) {
				result++;
			}
		}
		
		return result;
	}
	
	public void resetSet() {
		if (data.size()> 0) {
			DataPoint p = data.get(data.size()-1);
			p.resetSet();
		}
	}
	
	public void updateSet(int currentSet) {
		if (data.size()> 0) { 
			DataPoint p = data.get(data.size()-1);
			p.setSet(currentSet);
		}
	}
	
	public boolean sameDate (Date d1, Date d2) {
		return (d1.getYear() == d2.getYear() && d1.getMonth() == d2.getMonth() && d1.getDay() == d2.getDay());
	}
}
