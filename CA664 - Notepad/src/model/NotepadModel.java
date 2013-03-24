package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.line.Line;
import model.line.Position;
import model.line.SelectedText;

public class NotepadModel implements ModelInterface {
	private List<Line> text;
	private String selectedURL;
	
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
	public List<String> retrieveLinks() {
        //String text = "Please go to http://google.com and then mailto:brendan@rice.com to download a file from www.google.com ftp://brendanrice:pass@github/assignemnt.txt";
		if( text == null) {
            throw new IllegalArgumentException("Input text must not be null");
        }
        List<String> resultList = new ArrayList<String>();
        String regexp = "(((https?|mailto|ftp):(//)*|www[.])[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(regexp,Pattern.CASE_INSENSITIVE);

        StringBuffer inputStr = new StringBuffer();
    	for(Line singleLine: text){
    		inputStr.append(singleLine);
    	}
        Matcher m;
    	m = p.matcher(inputStr);
    	while (m.find()) {
    		resultList.add(m.group());
    	}
        return resultList;
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
	
	public String getSelectedURL() {
		return selectedURL;
	}
	
	public void setSelectedURL(String url) {
		this.selectedURL = url;
	}
}
