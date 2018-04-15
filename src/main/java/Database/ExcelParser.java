package Database;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class ExcelParser {
	public ArrayList<Exercise> parseExercise() {
		String csvFile = "src\\main\\webapp\\WEB_INF\\workout.csv";
		BufferedReader reader = null;
		String line = "";
		String separator = ",";
		
		ArrayList<Exercise> list = new ArrayList<Exercise>();
		Exercise exercise;
		
		try {
			reader = new BufferedReader(new FileReader(csvFile));
			reader.readLine(); // skip that first line of the csvFile
			while((line = reader.readLine()) != null) {
				String[] split = line.split(separator);
				
				String name = split[1];
				String description = split[3];
				String keywordsLine = split[4];
				String startingWeight = split[5];
				String startingReps = split[6];
				String startingSets = split[7];
				String imageName = name;
				
				ArrayList<String> keywords = new ArrayList<String>(Arrays.asList(keywordsLine.split(";\\s*")));
				
				exercise = new Exercise(name, description, keywords, imageName, Integer.parseInt(startingWeight), Integer.parseInt(startingReps), Integer.parseInt(startingSets));
				list.add(exercise);
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return list;
	}
}
