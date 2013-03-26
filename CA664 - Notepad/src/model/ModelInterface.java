package model;

import java.util.List;

import model.line.*;

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
	public SelectedText select();
	public void insert(String string, int index);
	
	// Insert characters over the top of existing character. No need to bump down other characters
	public void insertOver(String string, int index);
	public String copy(int lineNumber, int charNumber);
	public String cut(int lineNumber, int charNumber);

	
	//Undo/Redo - Stack
	public void undo();
	public void redo();
	
	public String toString();
	
}
