package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
//import javax.media.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;


public class EntryClass extends Frame {
	

	static Animator anim = null;
	
	private void setupJOGL(){
	    GLCapabilities caps = new GLCapabilities(null);
	    caps.setDoubleBuffered(true);  // request double buffer display mode
	    caps.setHardwareAccelerated(true);
	    
	    GLCanvas canvas = new GLCanvas(caps); 
        add(canvas);

        JoglEventListener jgl = new JoglEventListener();
        
        canvas.addGLEventListener(jgl); 
        canvas.addKeyListener(jgl); 
        canvas.addMouseListener(jgl);
        canvas.addMouseMotionListener(jgl);

        anim = new Animator(canvas);
        anim.start();

	}
	
    public EntryClass() {
    	
    	JFrame frame = new JFrame("Basketball Game");

   //     Container pane = frame.getContentPane();
        
    //    BorderLayout layout = new BorderLayout();
        
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

        //setupJOGL();
    }

    public static void main(String[] args) {
    	EntryClass demo = new EntryClass();
        demo.setVisible(true);
    }
}
