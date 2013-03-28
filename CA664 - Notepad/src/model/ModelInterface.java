package model;

import java.util.List;


public interface ModelInterface {
	
	/*** Storage ***/
	// If we want to open a blank file, it will be done using constructor with no arguments 
	public void openFile(String path);
	public void saveFile(String path);
	
	
	/*** Analysis ***/
	public List<String> retrieveLinks();
	// View Statistics
	public int getWordCount();
	public int getCharCount();
	public int getLineCount();
	
	
	/*** Manipulation ***/
	public List<Integer> find(String inputText);
	// Text Editing
	// Provided by JTextArea
	// public SelectedText select();
	public void insert(String string, int index);
	
	// Insert characters over the top of existing character. No need to bump down other characters
	public void insertOver(String string, int index);
	// Provided by JTextArea
	//public String copy(int from, int to);
	// Doesn't need to return string as this will already be provideded by getHighlights() in JtextArea
	public void cut(int from, int to);
	// Functionality provided by insert(String text, int index)
	//public void paste(String string, int index);

	
	//Undo/Redo - Stack
	public void undo();
	public void redo();
	
	public String toString();
	
}
