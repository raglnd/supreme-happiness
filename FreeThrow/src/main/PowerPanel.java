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
		
		PowerPanel.add(progressBar);
	
		t = new Timer(10, new ActionListener() { // Create a new Timer

			// Perform Action on Timers
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				value++;
				if (value > 100)
				{
					value = 0;
					
					JoglEventListener.availableTries--; // // Decrease number of tries
					if(JoglEventListener.availableTries == 0) 
					{ 
						// End game if no tries left
						System.out.print("Game over. ");
						PowerPanel.setVisible(false);

						// I want the ball to just drop...
						// begin transformation of sphere
						JoglEventListener.canTransform = true;
						
						// disable all buttons
						DirectionPanel.btnUp.setEnabled(false);
						DirectionPanel.btnDown.setEnabled(false);
						DirectionPanel.btnLeft.setEnabled(false);
						DirectionPanel.btnRight.setEnabled(false);
						PositionPanel.btnRight.setEnabled(false);
						PositionPanel.btnLeft.setEnabled(false);
						
						// start Timer for sphere transformation
						JoglEventListener.sphereT.start();
					}
					
					System.out.print("You have " + JoglEventListener.availableTries + " tries remaining.\n");
				}
				
				progressBar.setValue(value);
				if (value == 0)
					t.stop();
			}
		});
			
		// Add Panel to Frame
		add(PowerPanel, BorderLayout.WEST);
		
	}	
}