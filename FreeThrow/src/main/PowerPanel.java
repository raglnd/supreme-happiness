package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

public class PowerPanel extends JPanel {

	static Timer t;
	static int value;
	static JProgressBar progressBar;
	static JPanel PowerPanel;
	
	public PowerPanel() {
				
		// Create JPanel
		PowerPanel = new JPanel(new BorderLayout());
		
		progressBar = new JProgressBar();
		//progressBar.setValue(value);
		
		PowerPanel.add(progressBar);
	
		t = new Timer(10, new ActionListener() { // Create a new Timer

			// Perform Action on Timers
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				value++;
				progressBar.setValue(value);
			}
		});
		
		
		// Add Panel to Frame
		add(PowerPanel, BorderLayout.WEST);
	}
	
}
