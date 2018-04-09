package Database;

import java.util.Date;

public class DataPoint {
	int weight;
	int reps;
	int sets;
	Date date;
	
	public DataPoint(int weight, int reps, int sets, Date date) {
		this.weight = weight;
		this.reps = reps;
		this.date = date;
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
