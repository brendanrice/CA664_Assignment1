package view;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FindDialog extends JDialog {
	JTextField input;
	JButton find;
	JButton cancel;
	JPanel panel;
	
	public FindDialog(JFrame parent) {
		super.setLocationRelativeTo(parent);
		input = new JTextField();
		find = new JButton("Find");
		cancel = new JButton("Cancel");
		
		panel = new JPanel();
	    panel.setLayout( new BorderLayout() );
		
		panel.add(input);
		panel.add(find, BorderLayout.CENTER);
		//panel.add(cancel, BorderLayout.CENTER);
		
		this.add(input, BorderLayout.CENTER);
		this.add(panel, BorderLayout.SOUTH);
		this.setTitle("Find");
		this.setSize(200, 85);
		
	}
	
	public void setActionListeners(ActionListener listener) {
		find.addActionListener(listener);
		find.setActionCommand("FindButton");
	}
	
	public String getInput() {
		return this.input.getText();
	}
	
	
}
