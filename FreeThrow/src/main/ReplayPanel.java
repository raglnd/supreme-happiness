package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		speed[0].addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JoglEventListener.TIME_MULT = 1f;			
				
				if (!JoglEventListener.replayTime)
				{
					JoglEventListener.sphereX = JoglEventListener.launchSphereX;
					JoglEventListener.sphereY = JoglEventListener.launchSphereY;
					JoglEventListener.sphereZ = JoglEventListener.launchSphereZ;
					
					JoglEventListener.sphereVelocity[0] = JoglEventListener.launchSphereVelocity[0];
					JoglEventListener.sphereVelocity[1] = JoglEventListener.launchSphereVelocity[1];
					JoglEventListener.sphereVelocity[2] = JoglEventListener.launchSphereVelocity[2];
					
					JoglEventListener.aboveRim = false;
					JoglEventListener.gameOver = false;
					JoglEventListener.victory = false;
				}
				
				JoglEventListener.replayTime = true;
			}
			
		});
		speed[1] = new JButton("1/4x");
		speed[1].addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JoglEventListener.TIME_MULT = 0.25f;
				
				
				if (!JoglEventListener.replayTime)
				{
					JoglEventListener.sphereX = JoglEventListener.launchSphereX;
					JoglEventListener.sphereY = JoglEventListener.launchSphereY;
					JoglEventListener.sphereZ = JoglEventListener.launchSphereZ;
					
					JoglEventListener.sphereVelocity[0] = JoglEventListener.launchSphereVelocity[0];
					JoglEventListener.sphereVelocity[1] = JoglEventListener.launchSphereVelocity[1];
					JoglEventListener.sphereVelocity[2] = JoglEventListener.launchSphereVelocity[2];
					
					JoglEventListener.aboveRim = false;
					JoglEventListener.gameOver = false;
					JoglEventListener.victory = false;
				}
				
				JoglEventListener.replayTime = true;
			}
			
		});
		speed[2] = new JButton("1/10x");
		speed[2].addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JoglEventListener.TIME_MULT = 0.1f;
				
				
				if (!JoglEventListener.replayTime)
				{
					JoglEventListener.sphereX = JoglEventListener.launchSphereX;
					JoglEventListener.sphereY = JoglEventListener.launchSphereY;
					JoglEventListener.sphereZ = JoglEventListener.launchSphereZ;
					
					JoglEventListener.sphereVelocity[0] = JoglEventListener.launchSphereVelocity[0];
					JoglEventListener.sphereVelocity[1] = JoglEventListener.launchSphereVelocity[1];
					JoglEventListener.sphereVelocity[2] = JoglEventListener.launchSphereVelocity[2];
					
					JoglEventListener.aboveRim = false;
					JoglEventListener.gameOver = false;
					JoglEventListener.victory = false;
				}
				
				JoglEventListener.replayTime = true;
			}
			
		});
		
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