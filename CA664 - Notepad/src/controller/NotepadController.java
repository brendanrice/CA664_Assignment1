package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

import model.NotepadModel;
import view.NotepadView;

public class NotepadController implements ActionListener {
	NotepadView view;
	NotepadModel model;
	
	public NotepadController (final NotepadView view, final NotepadModel model) {
		this.view = view;
		this.model = model;
		view.registerActionListeners(this);
		
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

		} else if(command.equals("SaveAs")) {
			System.out.println("SaveAs Pressed!");
			
			
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
			
			
		} else if(command.equals("WordCount")) {
			System.out.println("WordCount Pressed!");
			
			
		} 
		
		
	}

}
