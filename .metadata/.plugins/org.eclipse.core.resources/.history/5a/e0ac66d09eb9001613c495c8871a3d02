package helloOpenGL;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;




public class JoglEventListener implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	
	float move_speed = 5f;
	float look_speed = 15f;
	
	float camera_x = 0;
	float camera_y = 20;
	float camera_z = 0;
	float look_at_x = 0;
	float look_at_y = 0;
	float look_at_z = 1;
	
	float old_XX=0;
	float old_YY=0;
	
	float x_angle = 0;
	float y_angle = 0;
	
	float cube_size = 500f;
	
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
	
	float h;
	
	float[] clicked_location={0f,0f};
	char key_released = 's';
	int right_click = 0;
	
	float backrgb[] = new float[4]; 
	float rot; 
	
	/*
	 * Custom variables for mouse drag operations 
	 */
	int windowWidth, windowHeight;
	float orthoX=40;
	float tVal_x, tVal_y, rVal_x, rVal_y, rVal;
	double rtMat[] = new double[16];
	int mouseX0, mouseY0;
	int saveRTnow=0, mouseDragButton=0;
	
	float focalLength = 10.0f;
	float r11 = 1.0f, r12 = 0.0f, r13 = 0.0f, tx = 0.0f,
	      r21 = 0.0f, r22 = 1.0f, r23 = 0.0f, ty = 0.0f,
	      r31 = 0.0f, r32 = 0.0f, r33 = 1.0f, tz = 0.0f;

    private GLU glu = new GLU();

	
	 public void displayChanged(GLAutoDrawable gLDrawable, 
	            boolean modeChanged, boolean deviceChanged) {
	    }

	    /** Called by the drawable immediately after the OpenGL context is
	     * initialized for the first time. Can be used to perform one-time OpenGL
	     * initialization such as setup of lights and display lists.
	     * @param gLDrawable The GLAutoDrawable object.
	     */
	    public void init(GLAutoDrawable gLDrawable) {	    	
	        GL2 gl = gLDrawable.getGL().getGL2();
	               
	        //gl.glShadeModel(GL.GL_LINE_SMOOTH);              // Enable Smooth Shading
	        gl.glClearColor(1f, 1f, 1f, 1f);
	        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
	        
	        
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
	        
	        // Lighting
	        gl.glDisable(GL2.GL_LIGHTING);
	        gl.glEnable(GL2.GL_TEXTURE_2D);
	        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
	        gl.glEnableClientState(GL2.GL_COLOR_ARRAY);
	        gl.glColor4f(1f,1f,1f,1f);
	        
	        gl.glEnable(GL.GL_DEPTH_TEST);              // Enables Depth Testing
	        gl.glDepthFunc(GL.GL_LEQUAL);               // The Type Of Depth Testing To Do
	        // Really Nice Perspective Calculations
	        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);

	        gl.glMatrixMode(GL2.GL_MODELVIEW);
	        gl.glLoadIdentity();
	    }


	    
	    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, 
	            int height) {
	    	windowWidth = width;
	    	windowHeight = height;
	        final GL2 gl = gLDrawable.getGL().getGL2();

	        if (height <= 0) // avoid a divide by zero error!
	            height = 1;
	        h = (float) width / (float) height;
	        gl.glViewport(0, 0, width, height);
	        gl.glMatrixMode(GL2.GL_PROJECTION);
	        gl.glLoadIdentity();
	        glu.gluPerspective(60.0f, h, 1, 10000.0);
	        glu.gluLookAt(camera_x, camera_y, camera_z, look_at_x, look_at_y, look_at_z, 0, 1, 0);
	        gl.glMatrixMode(GL2.GL_MODELVIEW);

	    }

		@Override
		public void display(GLAutoDrawable gLDrawable) {
			look_at_x = (float)Math.sin(Math.toRadians(-x_angle)) + camera_x;
			look_at_y = (float)Math.sin(Math.toRadians( y_angle)) + camera_y;
			look_at_z = (float)Math.cos(Math.toRadians(-x_angle)) + camera_z;

			
			// TODO Auto-generated method stub
			final GL2 gl = gLDrawable.getGL().getGL2();
			
			
			gl.glMatrixMode(GL2.GL_PROJECTION);
			gl.glLoadIdentity();
			glu.gluPerspective(60.0f, h, 1, 10000.0);
	        glu.gluLookAt(camera_x, camera_y, camera_z, look_at_x, look_at_y, look_at_z, 0, 1, 0);
	        //System.out.printf("Camera: (%.3f, %.3f, %.3f), Look At: (%.3f, %.3f, %.3f)\n", camera_x, camera_y, camera_z, look_at_x, look_at_y, look_at_z);
	        
	        gl.glClearColor(1.0f, 0.7f, 0.4f, 1.0f);
			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
			
			List<float[]> skybox = create_cube(camera_x, camera_y, camera_z, cube_size);
	    	
	    	for (int i = 0; i < skybox.size(); i+=8)
	    	{
	    		//gl.glBindTexture(GL2.GL_TEXTURE_2D, _skybox[i/8]);
	    		skybox_[i/8].enable(gl);
	    		skybox_[i/8].bind(gl);
	    		gl.glBegin(GL2.GL_QUADS);
	    		//gl.glColor4f(1f/(i/8+1),1f/(i/8+1),1f/(i/8+1),1f);
	    		for (int j=i; j < i+8; j+=2)
	    		{
	    			gl.glTexCoord2f(skybox.get(j)[0], skybox.get(j)[1]);
	    			gl.glVertex4f(skybox.get(j+1)[0], skybox.get(j+1)[1], skybox.get(j+1)[2], 1f);
	    		}
	    		gl.glEnd();
	    		skybox_[i/8].disable(gl);
	    	}
	     
			
	    	gl.glMatrixMode(GL2.GL_MODELVIEW);
	    	gl.glLoadIdentity();
	    	
	    }

		@Override
		public void dispose(GLAutoDrawable arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
			float t_look_at_x = (float)Math.sin(Math.toRadians(-x_angle-90f)) + camera_x;
			float t_look_at_z = (float)Math.cos(Math.toRadians(-x_angle-90f)) + camera_z;
			
			char key= e.getKeyChar();
			System.out.printf("Key typed: %c\n", key); 
			switch(key) 
			{
			case 'w':
			case 'W':
				camera_x -= move_speed*(camera_x - look_at_x);
				camera_z -= move_speed*(camera_z - look_at_z);
				break;
			case 's':
			case 'S':
				camera_x += move_speed*(camera_x - look_at_x);
				camera_z += move_speed*(camera_z - look_at_z);
				break;
			case 'a':
			case 'A':
				camera_x += move_speed*(camera_x - t_look_at_x);
				camera_z += move_speed*(camera_z - t_look_at_z);
				break;
			case 'd':
			case 'D':
				camera_x -= move_speed*(camera_x - t_look_at_x);
				camera_z -= move_speed*(camera_z - t_look_at_z);
				break;
			case 'o':
			case 'O':
				camera_y += move_speed;
				break;
			case 'l':
			case 'L':
				camera_y -= move_speed;
				break;
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub

			
			
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
			char key= e.getKeyChar();
			System.out.printf("Key released: %c\n", key); 
			
				
			
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
			System.out.printf("Point dragged: (%.3f, %.3f)\n", XX, YY);
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
			System.out.printf("Point moved: (%.3f, %.3f)\n", XX, YY);
			
			
			float dist_x = XX - old_XX;
			float dist_y = YY - old_YY;
			
			old_XX = XX;
			old_YY = YY;
			
			x_angle = x_angle + look_speed*dist_x;
			y_angle = y_angle + look_speed*dist_y;
			if (y_angle >= 89.8f)
			{
				y_angle = 89.8f;
			}
			else if (y_angle <= -89.8f)
			{
				y_angle = -89.8f;
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
			System.out.printf("Point clicked: (%.3f, %.3f)\n", XX, YY);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			/*
			 * Coordinates printout
			 */
			float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
			System.out.printf("Point pressed: (%.3f, %.3f)\n", XX, YY);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
			System.out.printf("Point released: (%.3f, %.3f)\n", XX, YY);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
			System.out.printf("Point entered: (%.3f, %.3f)\n", XX, YY);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
			System.out.printf("Point exited: (%.3f, %.3f)\n", XX, YY);
		}	
}



