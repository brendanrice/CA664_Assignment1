package model;

import java.util.List;

import model.line.*;

public interface ModelInterface {
	
	/*** Storage ***/
	// If we want to open a blank file, it will be done using constructor with no arguments 
	public void openFile(String path);
	public void saveFile(String path);
	
	
	/*** Analysis ***/
	public String[] retrieveLinks();
	// View Statistics
	public int getWordCount();
	public int getCharCount();
	public int getLineCount();
	
	
	/*** Manipulation ***/
	public List<Position> find(String text);
	// Text Editing
	public SelectedText select();
	public String insert(String text, int lineNumber, int charNumber);
	
	// Insert characters over the top of existing character. No need to bump down other characters
	public String insertOver(String text, int lineNumber, int charNumber);
	public String copy(int lineNumber, int charNumber);
	public String cut(int lineNumber, int charNumber);

	
	//Undo/Redo - Stack
	public void undo();
	public void redo();
	
	public String toString();
	
}
