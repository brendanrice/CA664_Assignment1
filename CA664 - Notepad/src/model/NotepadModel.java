package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class NotepadModel implements ModelInterface {
	//private List<Line> text;
	private String selectedURL;
	private String clipboard;
	private String text;
	private Stack<String> undoStack;
	private Stack<String> redoStack;
	private List<Integer> findList;
	private boolean newSearch;
	private Iterator<Integer> findIterator;
	private String searchString;
	private String prevSearchString;
	private String filePath;
	
	public NotepadModel() {
		text = new String();
		searchString = "";
		filePath = "Untitled";
		undoStack = new Stack<String>();
		redoStack = new Stack<String>();
	}
	
	public NotepadModel(String path) {
		this();
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
	public void cut(int from, int to) {
		String preText = text.substring(0, from);
		String postText = text.substring(to);
		
		this.text = preText + postText;		
		
	}
	
	/********* FIND METHODS *********/
	/*** Clunky, should really be abstracted into it's own class... ***/
	@Override
	public List<Integer> find(String string) {
		this.searchString = string;
		findList = new ArrayList<Integer>(0);
		
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
		while (tempText.indexOf(string) != -1) {
			if (numFound == 0) {
				indexInSubstring = tempText.indexOf(string);
				indexTemp = indexInSubstring;
				charNo = indexInSubstring;
			} else {
				//indexTemp = indexInSubstring;
				indexInSubstring = tempText.indexOf(string);
				indexTemp += indexInSubstring + string.length();
				charNo = indexTemp;
			}
			numFound++;
			//System.out.println("Line: " + i + ",Index: " + charNo);
			tempText = tempText.substring(indexInSubstring + string.length());
			//System.out.println("Substring: " + line);
			
			findList.add(charNo);
		}
		
		return findList;
	}
	
	public boolean isNewSearch() {
		return newSearch;
	}
	public void setNewSearch(boolean b) {
		newSearch = b;
	}
	public void setFindList(List<Integer> list) {
		this.findList = list;
	}
	public void setFindIterator() {
		this.findIterator = findList.iterator();
	}
	public Integer getNextFind() {
		Integer result = null;
		if (findIterator.hasNext()) {
			result = findIterator.next();
		}
		return result;
	}
	public void setSearchString(String s) {
		this.prevSearchString = this.searchString;
		this.searchString = s;
	}
	public String getPrevSearchString() {
		return this.prevSearchString;
	}
	/*********** END FIND METHODS ***********/
	
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
		int wordCount = text.split("\\s+").length;
		return wordCount;
	}

	@Override
	public void insert(String string, int index) {
		if ((text.length()) == index-1) {
			System.out.println("Index is at end");
			index -= 1;
		}
		
		System.out.println(text.length() + " Index:" + index);
		String preText = text.substring(0, index);
		String postText = "";
		try { postText = text.substring(index); } catch (Exception e) {}
		System.out.println(preText);
		System.out.println(postText);
		
		char[] stringArray = string.toCharArray();
		
		char[] preArray = preText.toCharArray();
		char[] postArray = postText.toCharArray();
		char[] newText = new char[stringArray.length + preArray.length + postArray.length];
		
		for (int i = 0; i < preArray.length; i++) {
			newText[i] = preArray[i];
			System.out.print(newText[i]);
		}
		
		for (int i = 0; i < stringArray.length; i++) {
			newText[index + i] = stringArray[i];
			System.out.print(newText[index + i]);
		}
		
		for (int i = 0; i < postArray.length; i++) {
			newText[stringArray.length + index + i] = postArray[i];
			System.out.print(newText[i]);
		}
		
		System.out.println(this.text);
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
	
	/**** Undo/Redo ****/
	@Override
	public void undo() {
		redoStack.push(text);
		this.text = undoStack.pop();
	}
	
	@Override
	public void redo() {
		undoStack.push(text);
		this.text = redoStack.pop();
	}
	
	public void addToUndo() {
		undoStack.push(text);
	}
	
	public void emptyRedo() {
		redoStack.clear();
	}
	
	public boolean canUndo() {
		return !undoStack.isEmpty();
	}
	
	public boolean canRedo() {
		return !redoStack.isEmpty();
	}
	
	/**** URL Methods ****/
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
	
	public String getSelectedURL() {
		return selectedURL;
	}
	
	public void setSelectedURL(Object object) {
		this.selectedURL = (String) object;
	}
	
	
	/**** Clipboard ****/
	public void setClipboardText(String selectedText) {
		this.clipboard = selectedText;
	}
	
	public String getClipboardText() {
		return this.clipboard;
	}
	
	
	/**** Get/Set Text ****/
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(text);
		return sb.toString();
	}
	
	/**** Filepath ****/
	public void setFilePath(String path) {
		this.filePath = path;
	}
	public String getFilePath() {
		return this.filePath;	
	}

}
