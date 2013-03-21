package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import model.line.Line;
import model.line.Position;
import model.line.SelectedText;

public class NotepadModel implements ModelInterface {
	private List<Line> text;
	
	private final int DEFAULT_LINES = 100;
	

	public NotepadModel() {
		text = new ArrayList<Line>(DEFAULT_LINES);
		for (int i = 0; i < text.size(); i++) {
			text.add(new Line());
		}
	}
	
	public NotepadModel(String path) {
		BufferedReader br;
		text = new ArrayList<Line>(DEFAULT_LINES);
		try {
			String string;
			Line line;
			br = new BufferedReader(new FileReader(path)); 
			
			while ((string = br.readLine()) != null) {
				//System.out.println(line);
				line = new Line(string);
				text.add(line);
				System.out.println(line.toString());
				
			}
			
			br.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void openFile(String path) {
		BufferedReader br;
		text = new ArrayList<Line>(DEFAULT_LINES);
		try {
			String string;
			Line line;
			br = new BufferedReader(new FileReader(path)); 
			
			while ((string = br.readLine()) != null) {
				//System.out.println(line);
				line = new Line(string);
				text.add(line);
				System.out.println(line.toString());
			}
			br.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveFile(String outputPath) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter( new FileWriter(outputPath));
			for (int i = 0; i < text.size(); i++) {
				text.get(i).toString();
				writer.write(text.get(i).toString() + "\n");
			}
		}
		catch ( IOException e) {
			e.printStackTrace();
		}
		finally {	
			try {
				if ( writer != null)
					writer.close( );
			}
			catch ( IOException e) {
				e.printStackTrace();
			}	
		}
	}

	@Override
	public String copy(int lineNumber, int charNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String cut(int lineNumber, int charNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Position> find(String searchString) {
		List<Position> points = new ArrayList<Position>(0);
		String line;
		int indexInSubstring;
		int indexTemp;
		int charNo;
		int numFound;
		Position pos;
		searchString = searchString.toLowerCase();
		
		//charNo = line.indexOf(searchString);
		for (int i = 0; i < text.size(); i++) {
			line = text.get(i).toString().toLowerCase();
			indexInSubstring = 0;
			indexTemp = 0;
			charNo = 0;
			numFound = 0;
			while (line.indexOf(searchString) != -1) {
				if (numFound == 0) {
					indexInSubstring = line.indexOf(searchString);
					indexTemp = indexInSubstring;
					charNo = indexInSubstring;
				} else {
					//indexTemp = indexInSubstring;
					indexInSubstring = line.indexOf(searchString);
					indexTemp += indexInSubstring + searchString.length();
					charNo = indexTemp;
				}
				numFound++;
				//System.out.println("Line: " + i + ",Index: " + charNo);
				line = line.substring(indexInSubstring + searchString.length());
				//System.out.println("Substring: " + line);
				
				pos = new Position(i, charNo);
				points.add(pos);
			}
		}
		
		
		return points;
	}
	
	/*
	public int getPositionOnLine(String searchText, String line) {
		
		if (getPositionOnLine(searchText, line.substring(line.indexOf(searchText) + searchText.length())) == -1)
			System.out.println("Base Case: " + );
			return 0;
		else 
			return getPositionOnLine(searchText, line.substring(line.indexOf(searchText) + searchText.length()));
			
	}
	*/
	
	@Override
	public int getCharCount() {
		int charCount = 0;
		for (int i = 0; i < text.size(); i++) {
			charCount += text.get(i).getLength();
		}
		return charCount;
	}

	@Override
	public int getLineCount() {
		int lineCount = 0;
		while (lineCount < text.size()) {
			lineCount++;
		}
		return lineCount;
	}

	@Override
	public int getWordCount() {
		int wordCount = 0;
		for (int i = 0; i < text.size(); i++ ) {
			String[] wordsOnLine = text.get(i).toString().split(" ");
			for(int j = 0; j < wordsOnLine.length; j++) {
				wordCount++;
			}
		}
			
		return wordCount;
	}

	@Override
	public String insert(String text, int lineNumber, int charNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insertOver(String text, int lineNumber, int charNumber) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void redo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] retrieveLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SelectedText select() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}
	
	public List<Line> getText() {
		return this.text;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < text.size(); i++) {
			sb.append(text.get(i).toString() + "\n");
		}
		return sb.toString();
	}

}
