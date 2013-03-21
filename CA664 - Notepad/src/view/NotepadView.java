package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.*;

public class NotepadView extends JFrame {
	private JTextArea textArea;
	private JFileChooser filePicker;
	
	
	public NotepadView() {
		textArea = new JTextArea(20, 120);
		filePicker =new JFileChooser(System.getProperty("user.dir"));
		
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		JScrollPane scroller = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(scroller, BorderLayout.CENTER);
		
		this.setUpMenuBar();
	}
	
	private void setUpMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		JMenu menuFile = new JMenu("File");
		JMenu menuEdit = new JMenu("Edit");
		menuBar.add(menuFile);
		menuBar.add(menuEdit);
		
		JMenuItem New = new JMenuItem("New");
		JMenuItem Open = new JMenuItem("Open");
		JMenuItem SaveAs = new JMenuItem("Save As");
		JMenuItem Save = new JMenuItem("Save");
		JMenuItem Exit = new JMenuItem("Exit");
		menuFile.add(New); menuFile.add(Open);  menuFile.add(new JSeparator()); menuFile.add(Save); menuFile.add(SaveAs); menuFile.add(new JSeparator()); menuFile.add(Exit); 
			
		JMenuItem Cut = new JMenuItem("Cut");
		JMenuItem Copy = new JMenuItem("Copy");
		JMenuItem Paste = new JMenuItem("Paste");
		JMenuItem Find = new JMenuItem("Find");
		JMenuItem WordCount = new JMenuItem("Word Count");
		menuEdit.add(Cut); menuEdit.add(Copy); menuEdit.add(Paste); menuEdit.add(new JSeparator()); menuEdit.add(Find); menuEdit.add(new JSeparator()); menuEdit.add(WordCount);
		
		JToolBar tools = new JToolBar();
		this.add(tools, BorderLayout.NORTH);
		
		JButton buttonNew = new JButton(new ImageIcon("resources/new.gif"));
		JButton buttonOpen = new JButton(new ImageIcon("resources/open.gif"));
		JButton buttonSave = new JButton(new ImageIcon("resources/save.gif"));
		JButton buttonCut = new JButton(new ImageIcon("resources/cut.gif"));
		JButton buttonCopy = new JButton(new ImageIcon("resources/copy.gif"));
		JButton buttonPaste = new JButton(new ImageIcon("resources/paste.gif"));
		
		tools.add(buttonNew); tools.add(buttonOpen); tools.add(buttonSave); tools.addSeparator(); tools.add(buttonCut); tools.add(buttonCopy); tools.add(buttonPaste); tools.addSeparator();
	}

}
