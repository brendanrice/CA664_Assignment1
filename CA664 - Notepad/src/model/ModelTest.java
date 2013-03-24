package model;

import java.util.List;

import model.line.Position;

public class ModelTest {
	private static String inputPath = "src/data.txt";
	private static String outputPath = "src/test.txt";
	
	public static void main(String[] args) {
		
		NotepadModel myModel = new NotepadModel(inputPath);
		myModel.saveFile(outputPath);
		System.out.println("Character Count: " + myModel.getCharCount());
		System.out.println("Line Count: " + myModel.getLineCount());
		System.out.println("Word Count: " + myModel.getWordCount());
		
		System.out.println("Links Found: " + myModel.retrieveLinks());
		
		List<Position> points = myModel.find("how");
		for (int i = 0; i < points.size(); i++) {
			System.out.println("Position - Line: " + points.get(i).lineNo + ", Char: " + points.get(i).charNo);
		}
		
	}

}
