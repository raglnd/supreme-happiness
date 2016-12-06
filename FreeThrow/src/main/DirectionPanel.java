package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

public class DirectionPanel extends JPanel implements ActionListener {
	
	JButton btnUp;
	JButton btnDown;
	JButton btnRight;
	JButton btnLeft;
	
	public DirectionPanel() {
		
		// Create JPanel
		JPanel DirectionPanel = new JPanel(new BorderLayout());
		
		// Create JLabel
		//JLabel directionTitle = new JLabel("Adjust Direction of Shot");
		
		// Create JButtons
		btnUp = new JButton("^");
		btnUp.setPreferredSize(new Dimension(50, 25));
		btnUp.setFocusable(false);
		btnUp.addActionListener(this);
		
		btnDown = new JButton("v");
		btnDown.setPreferredSize(new Dimension(50, 25));
		btnDown.setFocusable(false);
		btnDown.addActionListener(this);
		
		btnRight = new JButton(">");
		btnRight.setPreferredSize(new Dimension(50, 50));
		btnRight.setFocusable(false);
		btnRight.addActionListener(this);
		
		btnLeft = new JButton("<");
		btnLeft.setPreferredSize(new Dimension(50, 50));
		btnLeft.setFocusable(false);
		btnLeft.addActionListener(this);
	
		//DirectionPanel.add(directionTitle);
		DirectionPanel.add(btnUp, BorderLayout.NORTH);
		DirectionPanel.add(btnDown, BorderLayout.SOUTH);
		DirectionPanel.add(btnLeft, BorderLayout.WEST);
		DirectionPanel.add(btnRight, BorderLayout.EAST);
		
		// Add Panel to Frame
		add(DirectionPanel, BorderLayout.WEST);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnUp)
			EntryClass.tipArrowY++;
		
		else if(e.getSource() == btnDown)
			EntryClass.tipArrowY--;
		
		else if(e.getSource() == btnLeft)
			EntryClass.tipArrowX--;
		
		else
			EntryClass.tipArrowX++;
		
	}


}