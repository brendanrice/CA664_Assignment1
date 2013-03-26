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
		
		List<Integer> points = myModel.find("how");
		for (int i = 0; i < points.size(); i++) {
			System.out.println("Char: " + points.get(i));
		}
		
		List<String> links = myModel.retrieveLinks();
		for (int i = 0; i < links.size(); i++) {
			System.out.println("Link: " + links.get(i));
		}
		
		myModel.insertOver("happy", 3);
		
		
		String text = myModel.toString();
		System.out.println(text);
		
	}

}
