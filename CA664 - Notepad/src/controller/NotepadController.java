package controller;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.swing.JList;
import javax.swing.JOptionPane;
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
			
			
		} else if(command.equals("Open")) {
			System.out.println("Open Pressed!");
			String title = view.openFilePicker();
			model.openFile(title);
			view.setTitle(title);
			view.updateTextArea(model.toString());

		} else if(command.equals("Save As")) {
			System.out.println("Save As Pressed!");
			
			
		} else if(command.equals("Save")) {
			System.out.println("Save Pressed!");
			
			
		} else if(command.equals("Exit")) {
			System.out.println("Exit Pressed!");
			
			
		} else if(command.equals("Cut")) {
			System.out.println("Cut Pressed!");
			
			
		} else if(command.equals("Copy")) {
			System.out.println("Copy Pressed!");
			
			
		} else if(command.equals("Paste")) {
			System.out.println("Paste Pressed!");
			
			
		} else if(command.equals("Find")) {
			System.out.println("Find Pressed!");
			
			
		} else if(command.equals("Word Count")) {
			System.out.println("WordCount Pressed!");
			
			
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
		JList<String> list = (JList<String>) event.getSource();
		if (event.getValueIsAdjusting() == false) {
			model.setSelectedURL(list.getSelectedValue());
        }
	}

	
}
