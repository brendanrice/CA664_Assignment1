package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;

import model.NotepadModel;

public class NotepadView extends JFrame {
	private JTextArea textArea;
	private JSplitPane splitPane;
	private JFileChooser filePicker;
	private JPanel linkPanel;
	private JList linkList;
	private JScrollPane linkScroller;
	private boolean modified, linksShowing;
	private JButton buttonNew, buttonOpen, buttonSave, buttonCut, buttonCopy, buttonPaste, buttonLinks, buttonOpenURL;
	private JMenuItem New, Open, Save, SaveAs, Cut, Copy, Paste, Find, WordCount, Exit, Links; 
	private List<JButton> buttonArray;
	private List<JMenuItem> menuItemArray;
	private String title;
	
	public NotepadView() {
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		textArea = new JTextArea(20, 120);
		filePicker = new JFileChooser(System.getProperty("user.dir"));
		linkPanel = new JPanel();
		linkList = new JList();
		linkScroller = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		modified = false;
		linksShowing = false;
		title = "Untitled";
		
		// Set up Link Viewer
		buttonOpenURL = new JButton("Open URL");
		linkPanel.setLayout(new BorderLayout());
		linkPanel.add(linkScroller, BorderLayout.CENTER);
		linkPanel.add(buttonOpenURL, BorderLayout.SOUTH);
		linkList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		JScrollPane scroller = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		splitPane.add(scroller);
		splitPane.setResizeWeight(0.75);
		
		this.add(splitPane, BorderLayout.CENTER);
		this.setUpButtons();
		this.setUpMenuBar();
		this.pack(); 
		textArea.addKeyListener(keyPressed);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); 
		//setTitle(currentFile); 
		setVisible(true);
		
	}
	
	public void displayLinks(String[] links) {
		if (!linksShowing) {
			linkList.setListData(links);
			linkScroller.setViewportView(linkList);
			splitPane.add(linkPanel);
			linksShowing = true;
		} else {
			splitPane.remove(linkPanel);
			linksShowing = false;
		}
	}
	
	private void setUpMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		JMenu menuFile = new JMenu("File");
		JMenu menuEdit = new JMenu("Edit");
		menuBar.add(menuFile);
		menuBar.add(menuEdit);
		
		New = new JMenuItem("New");
		Open = new JMenuItem("Open");
		SaveAs = new JMenuItem("Save As");
		Save = new JMenuItem("Save");
		Exit = new JMenuItem("Exit");
		menuFile.add(New); menuFile.add(Open);  menuFile.add(new JSeparator()); menuFile.add(Save); menuFile.add(SaveAs); menuFile.add(new JSeparator()); menuFile.add(Exit); 
		menuItemArray.add(New); menuItemArray.add(Open); menuItemArray.add(SaveAs); menuItemArray.add(Save); menuItemArray.add(Exit); 
		
		Cut = new JMenuItem("Cut");
		Copy = new JMenuItem("Copy");
		Paste = new JMenuItem("Paste");
		Find = new JMenuItem("Find");
		WordCount = new JMenuItem("Word Count");
		Links = new JMenuItem("Display Links");
		menuEdit.add(Cut); menuEdit.add(Copy); menuEdit.add(Paste); menuEdit.add(new JSeparator()); menuEdit.add(Find); menuEdit.add(new JSeparator()); menuEdit.add(Links); menuEdit.add(WordCount); 
		menuItemArray.add(Cut); menuItemArray.add(Copy); menuItemArray.add(Paste); menuItemArray.add(Find); menuItemArray.add(WordCount); menuItemArray.add(Links); 
		
		JToolBar tools = new JToolBar();
		this.add(tools, BorderLayout.NORTH);
		
		buttonNew = new JButton(new ImageIcon("resources/new.gif"));		buttonNew.setName("New");
		buttonOpen = new JButton(new ImageIcon("resources/open.gif"));		buttonOpen.setName("Open");
		buttonSave = new JButton(new ImageIcon("resources/save.gif"));		buttonSave.setName("Save");
		buttonCut = new JButton(new ImageIcon("resources/cut.gif"));		buttonCut.setName("Cut");
		buttonCopy = new JButton(new ImageIcon("resources/copy.gif"));		buttonCopy.setName("Copy");
		buttonPaste = new JButton(new ImageIcon("resources/paste.gif"));	buttonPaste.setName("Paste");
		buttonLinks = new JButton(new ImageIcon("resources/paste.gif"));	buttonLinks.setName("Display Links");
		tools.add(buttonNew); tools.add(buttonOpen); tools.add(buttonSave); tools.addSeparator(); tools.add(buttonCut); tools.add(buttonCopy); tools.add(buttonPaste); tools.addSeparator(); tools.add(buttonLinks); 
		buttonArray.add(buttonNew); buttonArray.add(buttonOpen); buttonArray.add(buttonSave); buttonArray.add(buttonCut); buttonArray.add(buttonCopy); buttonArray.add(buttonPaste); buttonArray.add(buttonLinks); 
		buttonArray.add(buttonOpenURL); 
		
		buttonSave.setEnabled(false);
	}
	
	private void setUpButtons() {
		buttonArray = new ArrayList<JButton>(8);
		menuItemArray = new ArrayList<JMenuItem>(10);
	}
	
	private KeyListener keyPressed = new KeyAdapter() { 
		public void keyPressed(KeyEvent e) { 
			modified = true; 
			buttonSave.setEnabled(true);
			int pos = textArea.getCaretPosition();
			System.out.println(pos);
		} 
	};
	
	public void registerActionListeners(ActionListener listener) {
		String commandName;
		
		for (int i = 0; i < buttonArray.size(); i++) {
			commandName = buttonArray.get(i).getName();
			buttonArray.get(i).setActionCommand(commandName);
			buttonArray.get(i).addActionListener(listener);
		}
		
		for (int i = 0; i < menuItemArray.size(); i++) {
			commandName = menuItemArray.get(i).getName();
			menuItemArray.get(i).setActionCommand(commandName);
			menuItemArray.get(i).addActionListener(listener);
		}

	}
	
	public void registerListListener(ListSelectionListener listener) {
		linkList.addListSelectionListener(listener);
	}
	
	public String openFilePicker() {
		String location = "";
			if (filePicker.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
				location = filePicker.getSelectedFile().getAbsolutePath();
			}
		return location;
	}
	
	public void updateTextArea(String text) {
		textArea.setText(text);
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSelectedText () {
		return textArea.getSelectedText();
	}
	
	

}
