package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.Timer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class JoglEventListener implements GLEventListener, KeyListener, MouseListener, MouseMotionListener, ActionListener {
    	
    	
    	float backrgb[] = new float[4]; 

    	int windowWidth, windowHeight;
    	float orthoX=40;

    	int mouseX0, mouseY0;	
    	float picked_x = 0.0f, picked_y = 0.0f;
    	
    	float focalLength = 1.0f;
    	
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
    	float ly_0 = 100.0f;
    	float lz_0 = 0.0f; // light0 is at z = 10.0f
    	float lw_0 = 1.0f; // zero is directional light
    	
    	float lx_1 = 0.0f;
    	float ly_1 = 10.0f;
    	float lz_1 = 0.0f;
    	float lw_1 = 0.0f;
    	
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
    	
    	float eyeX = 0;
    	float eyeY = 0;
    	float eyeZ = -5;
    	
    	/* -------- Texture Variables --------- */
    	Texture texNegX = null;
    	Texture texPosX = null;
    	Texture texNegY = null;
    	Texture texPosY = null;
    	Texture texNegZ = null;
    	Texture texPosZ = null;
    	Texture texSmiley = null;
    	
    	int texIDNegX; int texIDPosX; int texIDNegY; int texIDPosY; int texIDNegZ; int texIDPosZ; int texIDSmiley;
    	/* --------- End of Texture Variables ------------ */
    	
    	static float sphereX = 0; // -5
       	static float sphereY = 0; // 0
       	static float sphereZ = -20; // 5
    	static float tipArrowX = 0; //0
    	static float tipArrowY = 5; // 0
    	static float tipArrowZ = -5; // 0
    	
    	static boolean powerFlag = true;
    	static boolean replayFlag = true;
    	
    	static int v0 = 0;
    	
    	static int availableTries = 2;

        private GLU glu = new GLU();
    	
        private GLUT glut = new GLUT();
        
        
        
        /* ----- Draw Skybox Function --------- */
        int skybox_dist = 10;
        float skybox_4 = 0f;
        public void drawSkybox(final GL2 gl) {
            
        	gl.glDepthMask(false);
        	
        	// Quad -X
        	gl.glBindTexture(GL.GL_TEXTURE_2D, texIDNegX);
        	gl.glBegin(GL2.GL_QUADS);
               
            gl.glTexCoord2f(0, 0); 
            gl.glVertex4f(-skybox_dist, -skybox_dist, skybox_dist, skybox_4);   
                
            gl.glTexCoord2f(1, 0);   
            gl.glVertex4f(-skybox_dist, -skybox_dist, -skybox_dist, skybox_4); 
                
            gl.glTexCoord2f(1, 1);   
            gl.glVertex4f(-skybox_dist, skybox_dist, -skybox_dist, skybox_4);   
                
            gl.glTexCoord2f(0, 1);   
            gl.glVertex4f(-skybox_dist, skybox_dist, skybox_dist, skybox_4); 
            gl.glEnd();
            
            // Quad -Z
        	gl.glBindTexture(GL.GL_TEXTURE_2D, texIDNegZ);
        	gl.glBegin(GL2.GL_QUADS);
     
            gl.glTexCoord2f(0, 0); 
            gl.glVertex4f(-skybox_dist, -skybox_dist, -skybox_dist, skybox_4);   
        
            gl.glTexCoord2f(1, 0);   
            gl.glVertex4f(skybox_dist, -skybox_dist, -skybox_dist, skybox_4); 
                
            gl.glTexCoord2f(1, 1);   
            gl.glVertex4f(skybox_dist, skybox_dist, -skybox_dist, skybox_4);   
                 
            gl.glTexCoord2f(0, 1);   
            gl.glVertex4f(-skybox_dist, skybox_dist, -skybox_dist, skybox_4); 
            gl.glEnd();
            
            // Quad +X
            gl.glBindTexture(GL.GL_TEXTURE_2D, texIDPosX);
        	gl.glBegin(GL2.GL_QUADS);
      
            gl.glTexCoord2f(0, 0); 
            gl.glVertex4f(skybox_dist, -skybox_dist, -skybox_dist, skybox_4);   
                
            gl.glTexCoord2f(1, 0);   
            gl.glVertex4f(skybox_dist, -skybox_dist, skybox_dist, skybox_4); 
                 
            gl.glTexCoord2f(1, 1);   
            gl.glVertex4f(skybox_dist, skybox_dist, skybox_dist, skybox_4);   
                 
            gl.glTexCoord2f(0, 1);   
            gl.glVertex4f(skybox_dist, skybox_dist, -skybox_dist, skybox_4); 
            gl.glEnd();
            
            // Quad -Y
            gl.glBindTexture(GL.GL_TEXTURE_2D, texIDNegY);
        	gl.glBegin(GL2.GL_QUADS);
      
            gl.glTexCoord2f(0, 0); 
            gl.glVertex4f(-skybox_dist, -skybox_dist, skybox_dist, skybox_4);   
               
            gl.glTexCoord2f(1, 0);   
            gl.glVertex4f(skybox_dist, -skybox_dist, skybox_dist, skybox_4); 
                 
            gl.glTexCoord2f(1, 1);   
            gl.glVertex4f(skybox_dist, -skybox_dist, -skybox_dist, skybox_4);   
               
            gl.glTexCoord2f(0, 1);   
            gl.glVertex4f(-skybox_dist, -skybox_dist, -skybox_dist, skybox_4); 
            gl.glEnd();
            
            // Quad +Y
            gl.glBindTexture(GL.GL_TEXTURE_2D, texIDPosY);
        	gl.glBegin(GL2.GL_QUADS);
     
            gl.glTexCoord2f(0, 0); 
            gl.glVertex4f(-skybox_dist, skybox_dist, -skybox_dist, skybox_4);   
                
            gl.glTexCoord2f(1, 0);   
            gl.glVertex4f(skybox_dist, skybox_dist, -skybox_dist, skybox_4); 
       
            gl.glTexCoord2f(1, 1);   
            gl.glVertex4f(skybox_dist, skybox_dist, skybox_dist, skybox_4);   
                
            gl.glTexCoord2f(0, 1);   
            gl.glVertex4f(-skybox_dist, skybox_dist, skybox_dist, skybox_4); 
            gl.glEnd();
            
            // Quad +Z
            gl.glBindTexture(GL.GL_TEXTURE_2D, texIDPosZ);
        	gl.glBegin(GL2.GL_QUADS);
     
            gl.glTexCoord2f(0, 0); 
            gl.glVertex4f(skybox_dist, -skybox_dist, skybox_dist, skybox_4);   
                 
            gl.glTexCoord2f(1, 0);   
            gl.glVertex4f(-skybox_dist, -skybox_dist, skybox_dist, skybox_4); 
               
            gl.glTexCoord2f(1, 1);   
            gl.glVertex4f(-skybox_dist, skybox_dist, skybox_dist, skybox_4);   
               
            gl.glTexCoord2f(0, 1);   
            gl.glVertex4f(skybox_dist, skybox_dist, skybox_dist, skybox_4); 
            gl.glEnd();
            
            gl.glDepthMask(true);
        	
        }

    	
    	    public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) {
    		 
    	    }

    	    /** Called by the drawable immediately after the OpenGL context is
    	     * initialized for the first time. Can be used to perform one-time OpenGL
    	     * initialization such as setup of lights and display lists.
    	     * @param gLDrawable The GLAutoDrawable object.
    	     */
    	    public void init(GLAutoDrawable gLDrawable) {
    	        GL2 gl = gLDrawable.getGL().getGL2();
    	       // gl.glShadeModel(GL.GL_LINE_SMOOTH);              // Enable Smooth Shading
    	       // gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    // Black Background
    	        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.5f);    // White Background
    	        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
    	        gl.glEnable(GL.GL_DEPTH_TEST);              // Enables Depth Testing
    	        gl.glEnable(GL2.GL_DEPTH_CLAMP_NV);              // Enables Depth Clamping
    	        gl.glDepthFunc(GL.GL_LEQUAL);               // The Type Of Depth Testing To Do
    	        // Really Nice Perspective Calculations
    	        //gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
    	        
    	       
    	        /* ------ Load the Textures -------- */
    			try {
    				
    				texNegX = TextureIO.newTexture(new File("negx.jpg"), false);
    				texIDNegX = texNegX.getTextureObject();
    			    gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);  
    			    gl.glBindTexture(GL.GL_TEXTURE_2D, texIDNegX);
    			    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
    			    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
    			    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
    			    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
    			    gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
    			    
    			    texPosX = TextureIO.newTexture(new File("posx.jpg"), false);
    				texIDPosX = texPosX.getTextureObject();
    			    gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);  
    			    gl.glBindTexture(GL.GL_TEXTURE_2D, texIDPosX);
    			    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
    			    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
    			    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
    			    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
    			    gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
    			    
    			    texNegY = TextureIO.newTexture(new File("negy.jpg"), false);
    				texIDNegY = texNegY.getTextureObject();
    			    gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);  
    			    gl.glBindTexture(GL.GL_TEXTURE_2D, texIDNegY);
    			    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
    			    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
    			    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
    			    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
    			    gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
    			    
    			    texPosY = TextureIO.newTexture(new File("posy.jpg"), false);
    				texIDPosY = texPosY.getTextureObject();
    			    gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);  
    			    gl.glBindTexture(GL.GL_TEXTURE_2D, texIDPosY);
    			    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
    			    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
    			    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
    			    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
    			    gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
    			    
    			    texNegZ = TextureIO.newTexture(new File("negz.jpg"), false);
    				texIDNegZ = texNegZ.getTextureObject();
    			    gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);  
    			    gl.glBindTexture(GL.GL_TEXTURE_2D, texIDNegZ);
    			    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
    			    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
    			    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
    			    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
    			    gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
    			    
    			    texPosZ = TextureIO.newTexture(new File("posz.jpg"), false);
    				texIDPosZ = texPosZ.getTextureObject();
    			    gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);  
    			    gl.glBindTexture(GL.GL_TEXTURE_2D, texIDPosZ);
    			    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
    			    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
    			    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
    			    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
    			    gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
    		
    			    
    			    
    			             
    			     } catch (Exception ex) {
    			    	 ex.printStackTrace();
    			       }
    			
    			float diffuseLight[] = {dlr, dlg, dlb}; // diffuse light property
    			float ambientLight[] = {alr, alg, alb}; // ambient light property
    			
    			float lightPosition_0[] = {lx_0, ly_0, lz_0, lw_0}; // light position
    			gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0); // set light0 as diffuse light with related property
    			gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0); // set light0 as diffuse light with related property
    			gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition_0, 0); // set light0 position
    			gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, whiteSpecularLight, 0);
    			
    			gl.glEnable(GL2.GL_LIGHTING);
    			gl.glEnable(GL2.GL_LIGHT0);
    			

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
    	    		    	
    	    	glu.gluLookAt(0, 0, 0, eyeX, eyeY, eyeZ, 0, 1, 0);
    	    	
    			
    			
    			//glu.gluLookAt(0.0, 0.0, focalLength, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0); // eye point, x, y, z, looking at x, y, z, Up direction 
    			
    			
    		    
    		    gl.glLoadIdentity(); 
    		    
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
    	    	
    	    	//gl.glFlush();
    	    	
    			gl.glEnable(GL.GL_TEXTURE_2D);
    	    	gl.glPushMatrix();
    		    //gl.glTranslatef(0, 0, translateZ); // Zoom Image - Key Typed W/S
    		    //gl.glRotatef(rot, 0, 1, 0); // Rotate Image - Key Typed A/D
    		    drawSkybox(gl); // Draw Skybox
    		    gl.glPopMatrix();
    		    gl.glDisable(GL.GL_TEXTURE_2D);
    	    	
    		}
    		
    		//void drawBasketball(final GL2 gl) {
    		void drawBasketball(final GL2 gl) {
    			
    			//final GL2 gl = gLDrawable.getGL().getGL2();
    			
    			if(sphereTimePassed == 0) {
    				gl.glPushMatrix();
    				gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, redDiffuseMaterial, 0);
    				//gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, redDiffuseMaterial, 0);
    				
    				gl.glTranslatef(sphereX, sphereY, sphereZ);
    				glut.glutSolidSphere(2, 50, 50);
    				gl.glPopMatrix();
    			}
    			
    			else { // greater than zero
    				System.out.print("Will be executing this code.");
    				gl.glPushMatrix();
    				gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, redDiffuseMaterial, 0);
    				//gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, redDiffuseMaterial, 0);
    				
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
    		    //char key= e.getKeyChar();
    			//System.out.printf("Key typed: %c\n", key); 
    			
    			/*if(key == 'r') {
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
    			}*/
    			
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


//class MyWin extends WindowAdapter {
//	 public void windowClosing(WindowEvent e)
//   {
//       System.exit(0);
//   }
//}