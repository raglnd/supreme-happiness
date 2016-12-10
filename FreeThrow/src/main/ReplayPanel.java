package main;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ReplayPanel extends JPanel {

	static JPanel ReplayPanel;
	
	public ReplayPanel() {
		
		// Create JPanel
		ReplayPanel = new JPanel(new BorderLayout());
		
		JLabel replay = new JLabel("Instant Replay");
		
		JButton speed[] = new JButton[3];
		speed[0] = new JButton("1x");
		speed[1] = new JButton("1/4x");
		speed[2] = new JButton("1/10x");
		
		for(int i = 0; i < 3; i++)
			speed[i].setFocusable(false);
		
		ReplayPanel.add(replay, BorderLayout.NORTH);
		ReplayPanel.add(speed[0], BorderLayout.WEST);
		ReplayPanel.add(speed[1], BorderLayout.CENTER);
		ReplayPanel.add(speed[2], BorderLayout.EAST);
		
		ReplayPanel.setVisible(false);
		
		// Add Panel to Frame
		add(ReplayPanel, BorderLayout.SOUTH);
	}
}