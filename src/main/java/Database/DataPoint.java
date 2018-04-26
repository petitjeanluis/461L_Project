package Database;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class DataPoint {
	@Id Long id;
	
	@Index int weight;
	@Index int reps;
	@Index int sets;
	@Index Date date;
	/*long id;
	
	int weight;
	int reps;
	int sets;
	Date date;*/
	
	public DataPoint() {
		
	}
	
	/*public DataPoint(int weight, int reps, Date date) {
		this.weight = weight;
		this.reps = reps;
		this.date = date;
		Storage.getInstance().saveDataPoint(this);
	}*/
	
	public DataPoint(int weight, int reps, int sets, Date date) {
		this.weight = weight;
		this.reps = reps;
		this.date = date;
		Storage.getInstance().saveDataPoint(this);
	}
	
	public int getWeight() {
		return weight;
	}

	public int getReps() {
		return reps;
	}

	public Date getDate() {
		return date;
	}
	
	public int getSets() {
		return sets;
	}
}
