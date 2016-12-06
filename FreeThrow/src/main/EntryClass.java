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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;


public class EntryClass extends Frame {
	
   	static float sphereX = 0; // -5
   	static float sphereY = 0; // 0
   	static float sphereZ = 5; // 5
	static float tipArrowX = 0; //0
	static float tipArrowY = 5; // 0
	static float tipArrowZ = -5; // 0
	
	static boolean powerFlag = true;
	static boolean replayFlag = true;
	
	static int v0 = 0;
	
	int availableTries = 2;


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

      //  setupJOGL();
    }

    public static void main(String[] args) {
    	EntryClass demo = new EntryClass();
        demo.setVisible(true);
    }
    
    
    public class JoglEventListener implements GLEventListener, KeyListener, MouseListener, MouseMotionListener, ActionListener {
    	
    	
    	List<float[]> create_cube(float x, float y, float z, float size)
    	{
    		List<float[]> points = new ArrayList<float[]>();
    		
    		//      XYZ
    		float[] LBB = {x+size, y-size, z+size};
    		float[] RBB = {x-size, y-size, z+size};
    		float[] LBF = {x+size, y-size, z-size};
    		float[] RBF = {x-size, y-size, z-size};
    		float[] LTB = {x+size, y+size, z+size};
    		float[] RTB = {x-size, y+size, z+size};
    		float[] LTF = {x+size, y+size, z-size};
    		float[] RTF = {x-size, y+size, z-size};
    		
    		float[] zero = {0f, 0f};
    		float[] one = {0f, 1f};
    		float[] two = {1f, 0f};
    		float[] three = {1f, 1f};
    		
    		// Front
    		points.add(zero); points.add(LBF);
    		points.add(two); points.add(RBF);
    		points.add(three); points.add(RTF);
    		points.add(one); points.add(LTF);
    		
    		// Left
    		points.add(zero); points.add(LBB);
    		points.add(two); points.add(LBF);
    		points.add(three); points.add(LTF);
    		points.add(one); points.add(LTB);
    		
    		// Back
    		points.add(zero); points.add(RBB);
    		points.add(two); points.add(LBB);
    		points.add(three); points.add(LTB);
    		points.add(one); points.add(RTB);

    		// Right
    		points.add(zero); points.add(RBF);
    		points.add(two); points.add(RBB);
    		points.add(three); points.add(RTB);
    		points.add(one); points.add(RTF);
    		
    		// Top
    		points.add(one); points.add(RTF);
    		points.add(zero); points.add(RTB);
    		points.add(two); points.add(LTB);
    		points.add(three); points.add(LTF);
    		
    		// Bottom
    		points.add(zero); points.add(RBF);
    		points.add(one); points.add(RBB);
    		points.add(three); points.add(LBB);
    		points.add(two); points.add(LBF);

    		return points;
    	}

    	// Found from stack overflow: http://stackoverflow.com/questions/2109046/texture-loading-at-jogl
    	public Texture loadTexture(String file)
    	{
    	    ByteArrayOutputStream os = new ByteArrayOutputStream();
    	    try {
    			ImageIO.write(ImageIO.read(new File(file)), "png", os);
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	    InputStream fis = new ByteArrayInputStream(os.toByteArray());
    	    try {
    			return TextureIO.newTexture(fis, true, TextureIO.PNG);
    		} catch (GLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		return null;
    	}
    	
    	
    	Texture[] skybox_;
    	
    	float backrgb[] = new float[4]; 

    	int windowWidth, windowHeight;
    	float orthoX=40;

    	int mouseX0, mouseY0;	
    	float picked_x = 0.0f, picked_y = 0.0f;
    	
    	float focalLength = 30.0f;
    	
    	//angle of rotation
    	float rotateAngle = 0.0f; // 

    	//diffuse light color variables
    	float dlr = 1.0f;
    	float dlg = 1.0f;
    	float dlb = 1.0f;
    	float dlw = 0.0f;

    	//ambient light color variables
    	float alr = 1.0f;
    	float alg = 1.0f;
    	float alb = 1.0f;

    	//light position variables
    	float lx_0 = 0.0f;
    	float ly_0 = 0.0f;
    	float lz_0 = 0.0f; // light0 is at z = 10.0f
    	float lw_0 = 1.0f; // zero is directional light
    	
    	float lx_1 = 0.0f;
    	float ly_1 = 10.0f;
    	float lz_1 = 0.0f;
    	float lw_1 = 0.0f;
    	
    	float lx_2 = 0.0f;
    	float ly_2 = 0.0f;
    	float lz_2 = focalLength;
    	float lw_2 = 1.0f;
    	
    	/*** SET material property  ***/ 
    	
    	float redDiffuseMaterial []    = { 1.0f, 0.0f, 0.0f }; //set the material to red
    	float whiteSpecularMaterial [] = { 1.0f, 1.0f, 1.0f }; //set the material to white
    	float greenEmissiveMaterial [] = { 0.0f, 1.0f, 0.0f }; //set the material to green
    	float whiteSpecularLight []    = { 1.0f, 1.0f, 1.0f }; //set the light specular to white
    	
    	float blackAmbientLight[] = { 0.0f, 0.0f, 0.0f }; //set the light ambient to black
    	float whiteDiffuseLight[] = { 1.0f, 1.0f, 1.0f }; //set the diffuse light to white
    	float blankMaterial[]     = { 0.0f, 0.0f, 0.0f }; //set the material to black
    	float grayMaterial[]     = { 0.7f, 0.7f, 0.7f }; //set the material to gray
    	float mShininess[]        = { 20 }; //set the shininess of the material

    	boolean diffuse_flag  = false;
    	boolean emissive_flag = false;
    	boolean specular_flag = false;
    	
    	boolean smooth_flag = true;
    	
    	boolean teapot_flag = true;
    	boolean sphere_flag = true;
    	boolean cube_flag = true;
    	
    	int sphereTimePassed = 0;
    	boolean canTransform = false;
    	//GLAutoDrawable gLDrawable;
    	//final GL2 gl = gLDrawable.getGL().getGL2();

        private GLU glu = new GLU();
    	
        private GLUT glut = new GLUT();
    	
    	    public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) {
    		 
    	    }

    	    /** Called by the drawable immediately after the OpenGL context is
    	     * initialized for the first time. Can be used to perform one-time OpenGL
    	     * initialization such as setup of lights and display lists.
    	     * @param gLDrawable The GLAutoDrawable object.
    	     */
    	    public void init(GLAutoDrawable gLDrawable) {
    	    	String top = "CloudyLightRays/CloudyLightRaysBack512.png";
    	        String left = "CloudyLightRays/CloudyLightRaysLeft512.png";
    	        String front = "CloudyLightRays/CloudyLightRaysFront512.png";
    	        String right = "CloudyLightRays/CloudyLightRaysRight512.png";
    	        String up = "CloudyLightRays/CloudyLightRaysUp512.png";
    	        String down = "CloudyLightRays/CloudyLightRaysDown512.png";
    	        
    	        System.out.printf("Textures loading ... ");
    	        Texture[] __skybox = {loadTexture(top),
    	    			loadTexture(left),
    	    			loadTexture(front),
    	    			loadTexture(right),
    	    			loadTexture(up),
    	    			loadTexture(down)};
    	        skybox_ = __skybox;
    	        System.out.printf("Textures loaded\n");
    	    	
    	    	
    	        GL2 gl = gLDrawable.getGL().getGL2();
    	        
    	       // gl.glShadeModel(GL.GL_LINE_SMOOTH);              // Enable Smooth Shading
    	       // gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    // Black Background
    	        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.5f);    // White Background
    	        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
    	        gl.glEnable(GL.GL_DEPTH_TEST);              // Enables Depth Testing
    	        gl.glDepthFunc(GL.GL_LEQUAL);               // The Type Of Depth Testing To Do
    	        // Really Nice Perspective Calculations
    	        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
    	        
    	       
    	       // gl.glViewport(0, 0, 600, 600);
    	        
    	        gl.glEnable(GL2.GL_LIGHTING); // enable lighting
    	        
    	        //gl.glColor4f(1f,1f,1f,1f);
    	        
    	        gl.glEnable(GL2.GL_LIGHT0); // enable light0
    	        //gl.glEnable(GL2.GL_LIGHT2); // enable light2


    	        gl.glMatrixMode(GL2.GL_MODELVIEW);
    	        gl.glLoadIdentity();
    	        
    	        
    	        
    	    }


    	    
    	    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
    	    	windowWidth = width;
    	    	windowHeight = height;
    	        final GL2 gl = gLDrawable.getGL().getGL2();

    	        if (height <= 0) // avoid a divide by zero error!
    	            height = 1;
    	        final float h = (float) width / (float) height;
    	        gl.glViewport(0, 0, width, height);
    	        gl.glMatrixMode(GL2.GL_PROJECTION);
    	        gl.glLoadIdentity();
    	       // gl.glOrtho(-orthoX*0.5, orthoX*0.5, -orthoX*0.5*height/width, orthoX*0.5*height/width, -100, 100);
    	        glu.gluPerspective(45.0f, h, 1, 100000.0);

    	    }
    	    
    	    

    		@Override
    		public void display(GLAutoDrawable gLDrawable) {
    			// TODO Auto-generated method stub
    			final GL2 gl = gLDrawable.getGL().getGL2();

    			//gl.glClearColor(0, 0, 0, 1);
    			gl.glClearColor(1, 1, 1, 1);
    			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    		
    			
    	    	gl.glMatrixMode(GL2.GL_MODELVIEW);
    	    	gl.glLoadIdentity();
    	    		    	
    			float diffuseLight[] = {dlr, dlg, dlb}; // diffuse light property
    			float ambientLight[] = {alr, alg, alb}; // ambient light property
    			
    			float ligthtPosition_0[] = {lx_0, ly_0, lz_0, lw_0}; // light position
    			gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0); // set light0 as diffuse light with related property
    			gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, ligthtPosition_0, 0); // set light0 position
    			gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, whiteSpecularLight, 0);
    			
    			float ligthtPosition_2[] = {lx_2, ly_2, focalLength, lw_0}; // light position
    			gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_DIFFUSE, diffuseLight, 0); // set light2 as diffuse light with related property
    			gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_POSITION, ligthtPosition_0, 0); // set light2 position
    			gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_SPECULAR, whiteSpecularLight, 0);
    			
    			List<float[]> skybox = create_cube(10.0f, -10.0f, -focalLength, 2f);
    			
    			gl.glEnable(GL2.GL_TEXTURE_2D);
    	    	for (int i = 0; i < skybox.size(); i+=8)
    	    	{
    	    		skybox_[i/8].enable(gl);
    	    		skybox_[i/8].bind(gl);
    	    		gl.glBegin(GL2.GL_QUADS);
    	    		for (int j=i; j < i+8; j+=2)
    	    		{
    	    			gl.glTexCoord2f(skybox.get(j)[0], skybox.get(j)[1]);
    	    			gl.glVertex4f(skybox.get(j+1)[0], skybox.get(j+1)[1], skybox.get(j+1)[2], 1f);
    	    		}
    	    		gl.glEnd();
    	    		skybox_[i/8].disable(gl);
    	    	}
    	    	gl.glDisable(GL2.GL_TEXTURE_2D);
    	    	
    			glu.gluLookAt(0.0, 0.0, focalLength, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0); // eye point, x, y, z, looking at x, y, z, Up direction 
    	    	
    			drawBasketball(gl);
    			
    			if(!canTransform) {
    				// Draw Direction Arrow
    				gl.glBegin(gl.GL_LINES);
    				gl.glColor3f(0, 0, 0);
    			
    				// Tail of Arrow - adjusted by Position Panel
    				gl.glVertex3f(sphereX, sphereY, sphereZ);
 
    				// Tip of Arrow - adjusted by Direction Panel
    				gl.glVertex3f(tipArrowX, tipArrowY, tipArrowZ);		
    				gl.glEnd();
    			}
    	    	
    	    	gl.glFlush();
    	    	
    		}
    		
    		//void drawBasketball(final GL2 gl) {
    		void drawBasketball(final GL2 gl) {
    			
    			//final GL2 gl = gLDrawable.getGL().getGL2();
    			
    			if(sphereTimePassed == 0) {
    				gl.glPushMatrix();
    				gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, redDiffuseMaterial, 0);
    			
    				gl.glTranslatef(sphereX, sphereY, sphereZ);
    				glut.glutSolidSphere(2, 50, 50);
    				gl.glPopMatrix();
    			}
    			
    			else { // greater than zero
    				System.out.print("Will be executing this code.");
    				gl.glPushMatrix();
    				gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, redDiffuseMaterial, 0);
    				
    				// calculate velocities
        			// calculate new sphere coordinates
        			
    				// translate sphere
    				gl.glTranslatef(sphereX, sphereY, sphereZ);
        			
    				// draw sphere
    				glut.glutSolidSphere(2, 50, 50);
    				gl.glPopMatrix();
    				
    			}
    			
    		}

    		@Override
    		public void dispose(GLAutoDrawable arg0) {
    			// TODO Auto-generated method stub
    			
    		}

    		@Override
    		public void keyTyped(KeyEvent e) {
    			// TODO Auto-generated method stub
    		    
    			
    		}

    		@Override
    		public void keyPressed(KeyEvent e) {
    			// TODO Auto-generated method stub

    		}

    		@Override
    		public void keyReleased(KeyEvent e) {
    			// TODO Auto-generated method stub
    			char key= e.getKeyChar();
    			System.out.printf("Key typed: %c\n", key); 
    			
    			if(key == 'r') {
    				replayFlag = !replayFlag;
    				if(replayFlag)
    					ReplayPanel.ReplayPanel.setVisible(true);
    				else
    					ReplayPanel.ReplayPanel.setVisible(false);
    			}
    			
    			if(key == ' ') {
    				if(!PowerPanel.t.isRunning()) {
    						PowerPanel.progressBar.setValue(PowerPanel.value);
    						PowerPanel.t.start();
    				}
    				else {
    					PowerPanel.t.stop();
        				v0 = PowerPanel.progressBar.getValue();
        				
        				if(v0 < PowerPanel.progressBar.getMaximum()) {
        					System.out.println("Initial velocity is " + v0);
        					PowerPanel.value = 0;
        					availableTries = 2; // Restore number of tries
        					
        					PowerPanel.PowerPanel.setVisible(false);
        					
        					Timer sphereT = new Timer(1000, new ActionListener() { // Create a new Timer

        						// Perform Action on Timers
        						@Override
        						public void actionPerformed(ActionEvent e) {
        							// TODO Auto-generated method stub
        							sphereTimePassed++;
        							//drawBasketball(gl);
        							//progressBar.setValue(value);
        						}
        					});
        					
        					// begin transformation of sphere
        					canTransform = true;
        					// start Timer for sphere transformation
        					sphereT.start();
        					
        					
        					
        				}
        				
        				else { // Didn't stop before maximum
        					availableTries--; // // Decrease number of tries
        					if(availableTries == 0) { // End game if no tries left
        						System.out.println("Game over. You have " + availableTries + " tries remaining.");
        						PowerPanel.PowerPanel.setVisible(false);
        					}
        					else { // Report number of tries remaining
        						System.out.println("You have " + availableTries + " tries remaining.");
        						PowerPanel.value = 0;
        					}
        				}
    				}
    			}
    		}

    		@Override
    		public void mouseDragged(MouseEvent e) {
    			
    			float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
    			float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowHeight;
    			
    			
    		}
    		
    		@Override
    		public void mouseMoved(MouseEvent e) {
    			// TODO Auto-generated method stub
    			
    		}

    		@Override
    		public void mouseClicked(MouseEvent e) {
    			// TODO Auto-generated method stub
    			System.out.println("Your window get focus."); 
    		}

    		@Override
    		public void mousePressed(MouseEvent e) {
    			// TODO Auto-generated method stub
    			/*
    			 * Coordinates printout
    			 */
    			picked_x = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
    			picked_y = -(e.getY()-windowHeight*0.5f)*orthoX/windowHeight;
    			
    			System.out.printf("Point clicked: (%.3f, %.3f)\n", picked_x, picked_y);
    			
    			mouseX0 = e.getX();
    			mouseY0 = e.getY();
    			
    			if(e.getButton()==MouseEvent.BUTTON1) {	// Left button
    				
    				
    			}
    			else if(e.getButton()==MouseEvent.BUTTON3) {	// Right button
    				
    			}
    		}

    		@Override
    		public void mouseReleased(MouseEvent e) {
    			// TODO Auto-generated method stub
    			
    			
    		}

    		@Override
    		public void mouseEntered(MouseEvent e) { // cursor enter the window
    			// TODO Auto-generated method stub
    			
    		}

    		@Override
    		public void mouseExited(MouseEvent e) { // cursor exit the window
    			// TODO Auto-generated method stub
    			
    		}
    		

    		public void actionPerformed( ActionEvent e ) {
    			
    			/*JButton btn = (JButton) e.getSource();
    			
    			if(btn == sphereBtn) {
    				
    				sphere_flag = !sphere_flag;
    				sphereBtn.setFocusable(false);
    				teapotBtn.setFocusable(false);
    				cubeBtn.setFocusable(false);
    				
    			}
    				
    			else if(btn == teapotBtn) {
    				
    				teapot_flag = !teapot_flag;
    				sphereBtn.setFocusable(false);
    				teapotBtn.setFocusable(false);
    				cubeBtn.setFocusable(false);
    				
    			}
    			
    			else if(btn == cubeBtn) {
    				cube_flag = !cube_flag;
    				cubeBtn.setFocusable(false);
    				sphereBtn.setFocusable(false);
    				teapotBtn.setFocusable(false);
    			}*/
    				
    				
    				
    		//	JOptionPane.showMessageDialog( null, "You pressed: " + e.getActionCommand() );
    			
    		 }

    		

    	
    }

    
    
    
}


//class MyWin extends WindowAdapter {
//	 public void windowClosing(WindowEvent e)
//   {
//       System.exit(0);
//   }
//}