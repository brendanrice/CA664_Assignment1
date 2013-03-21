package controller;

import view.NotepadView;

public class NotepadController {

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
	          public void run() {
	               NotepadView notepad = new NotepadView();
	               notepad.setSize(800, 600);
	               notepad.setVisible(true);
	          }
	    });
		
	}

}
