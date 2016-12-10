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

public class PositionPanel extends JPanel implements ActionListener {
	
	static JButton btnRight;
	static JButton btnLeft;
	
	
	public PositionPanel() {
		
		// Create JPanel
		JPanel PositionPanel = new JPanel(new BorderLayout());
		
		// Create JButtons
		btnLeft = new JButton("L");
		btnLeft.setPreferredSize(new Dimension(50, 50));
		btnLeft.setFocusable(false);
		btnLeft.addActionListener(this);
		
		btnRight = new JButton("R");
		btnRight.setPreferredSize(new Dimension(50, 50));
		btnRight.setFocusable(false);
		btnRight.addActionListener(this);
		
		// Add Components to Panel
		PositionPanel.add(btnLeft, BorderLayout.WEST);
		PositionPanel.add(btnRight, BorderLayout.EAST);
		
		// Add Panel to Frame
		add(PositionPanel, BorderLayout.EAST);	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnRight)
			JoglEventListener.sphereX--;
		
		else
			JoglEventListener.sphereX++;
	}
}