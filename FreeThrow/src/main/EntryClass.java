package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.Animator;


public class EntryClass extends Frame {
	
	static Animator anim = null;
	
    public EntryClass() {
    	
    	JFrame frame = new JFrame("Basketball Game");
        
        JoglEventListener jgl = new JoglEventListener();
        
        frame.setLayout(new BorderLayout()); 
        
		// Create JPanels
		DirectionPanel dir = new DirectionPanel();
		PositionPanel pos = new PositionPanel();
		PowerPanel pow = new PowerPanel();
		ReplayPanel rep = new ReplayPanel();
       
        frame.add(dir, BorderLayout.WEST);
        frame.add(pos, BorderLayout.EAST);
        frame.add(pow, BorderLayout.CENTER);
        frame.add(rep, BorderLayout.SOUTH);
        
        GLCapabilities caps = new GLCapabilities(null);
	    caps.setDoubleBuffered(true);  // request double buffer display mode
	    caps.setHardwareAccelerated(true);
	    
	    GLJPanel gljpanel = new GLJPanel(caps);
	   
	    gljpanel.setPreferredSize(new Dimension(600, 400));
	    
	    frame.add(gljpanel, BorderLayout.NORTH);
	     
        gljpanel.addGLEventListener(jgl); 
        gljpanel.addKeyListener(jgl); 
        gljpanel.addMouseListener(jgl);
        gljpanel.addMouseMotionListener(jgl);

        anim = new Animator(gljpanel);
        anim.start();
 
        frame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        
        frame.setSize(600, 600);
        frame.setLocation(40, 40);
        
        gljpanel.setVisible(true);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
    	EntryClass demo = new EntryClass();
        demo.setVisible(true);
    }
}