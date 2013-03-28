package controller;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.NotepadModel;
import view.NotepadView;

public class NotepadController implements ActionListener, ListSelectionListener {
	NotepadView view;
	NotepadModel model;
	
	public NotepadController (final NotepadView view, final NotepadModel model) {
		this.view = view;
		this.model = model;
		view.registerActionListeners(this);
		view.registerListListener(this);
		view.registerTextListener(keyPressed);
		view.setTitle("Untitled - Scribble");
		
		java.awt.EventQueue.invokeLater(new Runnable() {
	          public void run() {
	               view.setSize(800, 600);
	          }
	    });
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		
		if (command.equals("New")) {
			System.out.println("New Pressed!");
			this.model = new NotepadModel();
			updateView();
			view.setTitle("Untitled - Scribble");
			
		} else if(command.equals("Open")) {
			System.out.println("Open Pressed!");
			String path = view.openFilePicker();
			model.openFile(path);
			model.setFilePath(path);
			view.setTitle(path + " - Scribble");
			view.updateTextArea(model.toString());
			
			model.retrieveLinks();
			//String[] test = new String[] {"www.google.ie", "Link2", "Link3"};
			List<String> linkList = model.retrieveLinks();
			String[] links = linkList.toArray(new String[linkList.size()]);
			view.displayLinks(links);

		} else if(command.equals("Save As")) {
			System.out.println("Save As Pressed!");
			String path = view.saveFilePicker();
			model.saveFile(path);
			model.setFilePath(path);
			view.setTitle(path + " - Scribble");
			
		} else if(command.equals("Save")) {
			System.out.println("Save Pressed!");
			model.saveFile(model.getFilePath());
			view.setTitle(model.getFilePath() + " - Scribble");
			
			
		} else if(command.equals("Exit")) {
			System.out.println("Exit Pressed!");
			view.dispose();
			
		} else if(command.equals("Undo")) {
			System.out.println("Undo Pressed!");
			model.undo();
			updateView();
			view.enableRedo();
			if (model.canUndo()) {
				view.enableUndo();
			} else {
				view.disableUndo();
			}
			
		} else if(command.equals("Redo")) {
			System.out.println("Redo Pressed!");
			model.redo();
			updateView();
			view.enableUndo();
			if (model.canRedo()) {
				view.enableRedo();
			} else {
				view.disableRedo();
			}
			
		} else if(command.equals("Cut")) {
			System.out.println("Cut Pressed!");
			view.keyPressed();
			model.emptyRedo();
			model.addToUndo();
			model.setText(view.getText());
			view.enableUndo();
			view.disableRedo();
			view.disablePaste();
			
			if (view.getSelectedText() != null) {
				model.setClipboardText(view.getSelectedText());
				view.enablePaste();
			}
			
			int from = view.getTextArea().getSelectionStart();
			int to = view.getTextArea().getSelectionEnd();

			model.cut(from, to);
			this.updateView();
			
		} else if(command.equals("Copy")) {
			System.out.println("Copy Pressed!");
			view.disablePaste();
			if (view.getSelectedText() != null) {
				model.setClipboardText(view.getSelectedText());
				view.enablePaste();
			}
				
			//System.out.println("Text: " + model.getClipboardText());
			
		} else if(command.equals("Paste")) {
			System.out.println("Paste Pressed! Text is:" + model.getClipboardText());
			view.keyPressed();
			model.emptyRedo();
			model.addToUndo();
			model.insert(model.getClipboardText(), view.getTextArea().getCaretPosition());
			view.enableUndo();
			view.disableRedo();
			this.updateView();
			
		} else if(command.equals("Find")) {
			System.out.println("Find Pressed!");
			view.showFindDialog(this);
			model.setNewSearch(true);
			
		} else if (command.equals("FindButton")) {
			JTextArea textArea = view.getTextArea();
			String input = view.getFindInput();
			model.setSearchString(input);
			System.out.println("Input: " + input);
			
			if (model.getPrevSearchString().equals(input)) {
				System.out.println("FindButton Pressed!");
				model.setNewSearch(false);
				System.out.println("Set New Search!");
			} else {
				model.setNewSearch(true);
				System.out.println("New Search!");
			}
			
			if (model.isNewSearch()) {
				model.setFindList(model.find(view.getFindInput()));
				model.setFindIterator();
				System.out.println("Is New Search!");
			}
			
			model.setNewSearch(false);

			Integer point = model.getNextFind();
			System.out.println(point);
			if (point != null) {
				 textArea.setSelectionStart(point);
				 textArea.setSelectionEnd(point + input.length());
			}
			else {
				model.setFindIterator();
			}
			
		} else if(command.equals("Word Count")) {
			System.out.println("WordCount Pressed!");
			view.showStatistics("Word Count: \t\t" + model.getWordCount() + "\nCharacter Count:\t " + model.getCharCount() + "\nLine Count:\t\t" +model.getLineCount());
			
		} else if(command.equals("Display Links")) {
			System.out.println("Links Pressed!");
			model.retrieveLinks();
			//String[] test = new String[] {"www.google.ie", "Link2", "Link3"};
			List<String> linkList = model.retrieveLinks();
			String[] links = linkList.toArray(new String[linkList.size()]);
			view.displayLinks(links);
			
		} else if(command.equals("Open URL")) {
			System.out.println("Open URL Pressed!");
			openLink(model.getSelectedURL());
			
		}
	}
	
	private void openLink(String urlString) {	
		if (urlString.startsWith("www.")) {
			urlString = "http://" + urlString;
		}
		try {
			URL url = new URL(urlString);
	     	URI uri = url.toURI();
	     	Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	     	if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
	     	try {
	     		desktop.browse(uri);
	     	} catch (Exception e) {
	     		e.printStackTrace();
	     	}
	    } catch (URISyntaxException e) {
	        e.printStackTrace();
	    } catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent event) {
		JList list = (JList) event.getSource();
		if (event.getValueIsAdjusting() == false) {
			model.setSelectedURL(list.getSelectedValue());
        }
	}
	
	private KeyListener keyPressed = new KeyAdapter() { 
		public void keyPressed(KeyEvent e) { 
			view.keyPressed();
			model.emptyRedo();
			model.addToUndo();
			model.setText(view.getText());
			view.enableUndo();
			view.disableRedo();
			view.setTitle(model.getFilePath() + "* - Scribble");
		} 
	};
	
	private void updateData() {
		model.setText(view.getText());
		view.setTitle(model.getFilePath() + "* - Scribble");
	}

	private void updateView() {
		view.updateTextArea(model.toString());
		view.setTitle(model.getFilePath() + "* - Scribble");
	}
}