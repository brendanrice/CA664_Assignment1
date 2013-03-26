package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.line.Line;
import model.line.Position;
import model.line.SelectedText;

public class NotepadModel implements ModelInterface {
	//private List<Line> text;
	private String selectedURL;
	private String clipboard;
	private String text;

	public NotepadModel() {
		text = new String();
	}
	
	public NotepadModel(String path) {
		openFile(path);
	}
	
	
	public void openFile(String path) {
		BufferedReader br;
		try {
			String string;
			br = new BufferedReader(new FileReader(path)); 
			
			
			StringBuilder sb = new StringBuilder();
			while ((string = br.readLine()) != null) {
				sb.append(string + "\n");
			}
			
			text = sb.toString();
			
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
			writer.write(text);
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
	public List<Integer> find(String searchString) {
		List<Integer> points = new ArrayList<Integer>(0);
		
		int indexInSubstring;
		int indexTemp;
		int charNo;
		int numFound;
		searchString = searchString.toLowerCase();
		
		//charNo = line.indexOf(searchString);
		String tempText = new String(text.toLowerCase());
		indexInSubstring = 0;			
		System.out.println("");
		indexTemp = 0;
		charNo = 0;
		numFound = 0;
		while (tempText.indexOf(searchString) != -1) {
			if (numFound == 0) {
				indexInSubstring = tempText.indexOf(searchString);
				indexTemp = indexInSubstring;
				charNo = indexInSubstring;
			} else {
				//indexTemp = indexInSubstring;
				indexInSubstring = tempText.indexOf(searchString);
				indexTemp += indexInSubstring + searchString.length();
				charNo = indexTemp;
			}
			numFound++;
			//System.out.println("Line: " + i + ",Index: " + charNo);
			tempText = tempText.substring(indexInSubstring + searchString.length());
			//System.out.println("Substring: " + line);
			
			points.add(charNo);
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
		int charCount = text.toCharArray().length;
		return charCount;
	}

	@Override
	public int getLineCount() {
		String[] lines = text.split("\r\n|\r|\n");
		int lineCount = lines.length;
		return lineCount;
	}

	@Override
	public int getWordCount() {
		int wordCount = text.split(" ").length;
		return wordCount;
	}

	@Override
	public void insert(String string, int index) {
		String preText = text.substring(0, index);
		String postText = text.substring(index);
		
		char[] stringArray = string.toCharArray();
		
		char[] preArray = preText.toCharArray();
		char[] postArray = postText.toCharArray();
		char[] newText = new char[stringArray.length + preArray.length + postArray.length];
		
		for (int i = 0; i < preArray.length; i++) {
			newText[i] = preArray[i];
		}
		
		for (int i = 0; i < stringArray.length; i++) {
			newText[index + i] = stringArray[i];
		}
		
		for (int i = 0; i < postArray.length; i++) {
			newText[i] = postArray[i];
		}
		
		this.text = new String(newText);
	}

	@Override
	public void insertOver(String string, int index) {
		char[] array = text.toCharArray();
		char[] stringArray = string.toCharArray();
		for (int i = 0; i < stringArray.length; i++) {
			array[index + i] = stringArray[i];
		}
		this.text = new String(array);
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
        String regexp = "(((https?|ftp):(//)*|www[.])[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(regexp,Pattern.CASE_INSENSITIVE);
        
        
        BufferedReader br = new BufferedReader(new StringReader(text));
        String line;
        StringBuffer inputStr = new StringBuffer();
        
        try {
			while ((line = br.readLine()) != null) {
				 inputStr.append(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(text);
		return sb.toString();
	}
	
	public String getSelectedURL() {
		return selectedURL;
	}
	
	public void setSelectedURL(Object object) {
		this.selectedURL = (String) object;
	}
	
	public void setClipboardText(String selectedText) {
		this.clipboard = selectedText;
	}
	
	public String getClipboardText() {
		return this.clipboard;
	}
}
