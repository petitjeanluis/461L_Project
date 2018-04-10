package Database;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Exercise {
	@Id Long id;
	@Index private String name;
	@Index private String description;
	
	//this will store keywords/workout types that this is applicable to
	@Index private ArrayList<String> keywords;
	
	@Index private BufferedImage image;
	
	//this will be the minimum weight set by us independent of user
	@Index private int startingWeight;
	@Index private int startingReps;
	@Index private int startingSets;
	
	public Exercise(String name, String description, ArrayList<String> keywords,
			BufferedImage image, int startingWeight, int startingReps, int startingSets) {
		this.name = name;
		this.description = description;
		this.keywords = keywords;
		this.image = image;
		this.startingWeight = startingWeight;
		this.startingReps = startingReps;
		this.startingSets = startingSets;
	}
	
	public Exercise(String name, String description, ArrayList<String> keywords,
			int startingWeight, int startingReps, int startingSets) {
		this.name = name;
		this.description = description;
		this.keywords = keywords;
		this.startingWeight = startingWeight;
		this.startingReps = startingReps;
		this.startingSets = startingSets;
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public ArrayList<String> getKeywords() {
		return keywords;
	}

	public BufferedImage getImage() {
		return image;
	}

	public int getStartingWeight() {
		return startingWeight;
	}

	public int getStartingReps() {
		return startingReps;
	}
	
	public int getStartingSets() {
		return startingSets;
	}
}
