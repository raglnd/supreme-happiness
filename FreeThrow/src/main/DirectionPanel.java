package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class DirectionPanel extends JPanel implements ActionListener {
	
	static JButton btnUp;
	static JButton btnDown;
	static JButton btnRight;
	static JButton btnLeft;
	
	public DirectionPanel() {
		
		// Create JPanel
		JPanel DirectionPanel = new JPanel(new BorderLayout());
		
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
	
		// Add Components to Panel
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
			JoglEventListener.tipArrowY++;
		
		else if(e.getSource() == btnDown)
			JoglEventListener.tipArrowY--;
		
		else if(e.getSource() == btnLeft)
			JoglEventListener.tipArrowX++;
		
		else
			JoglEventListener.tipArrowX--;	
	}
}