package main;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Timer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class JoglEventListener implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	
	float move_speed = 5f;
	float look_speed = 15f;
	
	float old_XX=0;
	float old_YY=0;
	
	float x_angle = 0;
	float y_angle = 0;
	
	static boolean replayTime = false;
	
	// INITIAL VALUES //
	float INIT_sphereTimePassed = 0;
	boolean INIT_canTransform = false;

	float INIT_cameraX = 6;
	float INIT_cameraY = 5.5f * 12;
	float INIT_cameraZ = -1 * 12;
	
	float INIT_lookAtX = 6;
	float INIT_lookAtY = 5.5f * 12;
	float INIT_lookAtZ = 1;
	
	static float INIT_sphereX = 0;
	static float INIT_sphereY = 5 * 12;
	static float INIT_sphereZ = 0;
	static float INIT_tipArrowX = 0;
	static float INIT_tipArrowY = 40 * 12;
	static float INIT_tipArrowZ = 18 * 12;
	
	static float INIT_TIME_MULT = 1f;

	float INIT_v0 = 0;
	// END INITIAL VALUES //
	
	static boolean aboveRim = false;
	static boolean gameOver = false;
	static boolean victory = false;
	
	float backrgb[] = new float[4]; 

	int windowWidth, windowHeight;
	float orthoX=40;

	int mouseX0, mouseY0;	
	float picked_x = 0.0f, picked_y = 0.0f;

	//angle of rotation
	float rotateAngle = 0.0f; // 

	//diffuse light color variables
	float dlr = 1.0f;
	float dlg = 1.0f;
	float dlb = 1.0f;
	float dlw = 0.0f;

	// ambient light color variables
	float alr = 1.0f;
	float alg = 1.0f;
	float alb = 1.0f;

	// light position variables
	float lx_0 = 0.0f;
	float ly_0 = 100.0f;
	float lz_0 = 0.0f; // light0 is at z = 10.0f
	float lw_0 = 1.0f; // zero is directional light

	/*** SET material property  ***/ 

	float redDiffuseMaterial []    = { 1.0f, 0.0f, 0.0f }; //set the material to red
	float greenEmissiveMaterial [] = { 0.0f, 1.0f, 0.0f }; //set the material to green
	float whiteSpecularLight []    = { 1.0f, 1.0f, 1.0f }; //set the light specular to white
	float whiteDiffuseLight[] = { 1.0f, 1.0f, 1.0f }; //set the diffuse light to white
	float blankMaterial[]     = { 0.0f, 0.0f, 0.0f }; //set the material to black

	static int sphereTimePassed = 0;
	static boolean canTransform = false;

	float cameraX = INIT_cameraX;
	float cameraY = INIT_cameraY;
	float cameraZ = INIT_cameraZ;
	
	float lookAtX = INIT_lookAtX;
	float lookAtY = INIT_lookAtY;
	float lookAtZ = INIT_lookAtZ;

	/* -------- Texture Variables --------- */
	Texture texNegX = null;
	Texture texPosX = null;
	Texture texNegY = null;
	Texture texPosY = null;
	Texture texNegZ = null;
	Texture texPosZ = null;

	int texIDNegX; int texIDPosX; int texIDNegY; int texIDPosY; int texIDNegZ; int texIDPosZ;
	 /* --------- End of Texture Variables ------------ */
	
	static int timerUpdateMilliseconds = 20;
	public static Timer sphereT = new Timer(timerUpdateMilliseconds, new ActionListener() 
	{ 
		// Perform Action on Timers
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			sphereTimePassed += timerUpdateMilliseconds;
		}
	});
	
	static float sphereRadius = 4.775f; // A basketball is ~ 4.775 inches in diameter.
	static float sphereX = INIT_sphereX; // -5
	static float sphereY = INIT_sphereY; // 0
	static float sphereZ = INIT_sphereZ; // 5
	static float tipArrowX = INIT_tipArrowX; //0
	static float tipArrowY = INIT_tipArrowY; // 0
	static float tipArrowZ = INIT_tipArrowZ; // 0
	
	static float launchSphereX = sphereX;
	static float launchSphereY = sphereY;
	static float launchSphereZ = sphereZ;
	
	static float[] launchSphereVelocity = {0,0,0};
	
	static float freeThrowZ = sphereZ;
	
	static float dampening = 0.9f;
	
	static float floorY = 0;
	static float floorFar = 20 * 12;
	static float floorNear = -20 * 12;
	static float floorLeft = -10 * 12;
	static float floorRight = 10 * 12;

	float backboardZ = 15 * 12 + freeThrowZ; // 15 feet from free throw line at z = -3 feet;	
	float backboardTop = 162;
	float backboardBottom= 120;
	float backboardLeft = -36;
	float backboardRight = 36;
	
	float rimHeight = 10 * 12; // 10 feet
	float rimOuterRadius = 10;
	float rimInnerRadius = 1;
	float rimZ = backboardZ - rimOuterRadius - 6; // 6 inches away from backboard.
	
	static float[] sphereVelocity = {0, 0, 0};
	
	static float TIME_MULT = INIT_TIME_MULT;
	
	static float[] getUnitVelocity(float x, float y, float z)
	{
		float mag = (float)Math.sqrt(x*x+y*y+z*z);
		float[] vel = {x/mag, y/mag, z/mag};
		
		return vel;
	}

	static int v0 = 0;

	static int availableTries = 2;

	private GLU glu = new GLU();

	private GLUT glut = new GLUT();



	/* ----- Draw Skybox Function --------- */
	int skybox_dist = 100000;
	float skybox_4 = 0f;
	public void drawSkybox(final GL2 gl) 
	{
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


	public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) 
	{

	}

	private void bindTexture(GL2 gl, int texID)
	{
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);  
		gl.glBindTexture(GL.GL_TEXTURE_2D, texID);
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
	}
	
	/** Called by the drawable immediately after the OpenGL context is
	 * initialized for the first time. Can be used to perform one-time OpenGL
	 * initialization such as setup of lights and display lists.
	 * @param gLDrawable The GLAutoDrawable object.
	 */
	public void init(GLAutoDrawable gLDrawable) 
	{
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
		try 
		{	
			texNegX = TextureIO.newTexture(new File("purplenebula_negx.jpg"), false);
			texPosX = TextureIO.newTexture(new File("purplenebula_posx.jpg"), false);
			texNegY = TextureIO.newTexture(new File("purplenebula_negy.jpg"), false);
			texPosY = TextureIO.newTexture(new File("purplenebula_posy.jpg"), false);
			texNegZ = TextureIO.newTexture(new File("purplenebula_negz.jpg"), false);
			texPosZ = TextureIO.newTexture(new File("purplenebula_posz.jpg"), false);

			texIDNegX = texNegX.getTextureObject();
			texIDPosX = texPosX.getTextureObject();
			texIDNegY = texNegY.getTextureObject();
			texIDPosY = texPosY.getTextureObject();
			texIDNegZ = texNegZ.getTextureObject();
			texIDPosZ = texPosZ.getTextureObject();
			
			bindTexture(gl, texIDNegX);
			bindTexture(gl, texIDPosX);
			bindTexture(gl, texIDNegY);
			bindTexture(gl, texIDPosY);
			bindTexture(gl, texIDNegZ);
			bindTexture(gl, texIDPosZ);
		} 
		catch (Exception ex) 
		{
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
		glu.gluPerspective(75.0f, h, 1, 100000.0);

	}



	@Override
	public void display(GLAutoDrawable gLDrawable) {
		lookAtX = (float)Math.sin(Math.toRadians(-x_angle)) + cameraX;
		lookAtY = (float)Math.sin(Math.toRadians( y_angle)) + cameraY;
		lookAtZ = (float)Math.cos(Math.toRadians(-x_angle)) + cameraZ;
		
		// TODO Auto-generated method stub
		final GL2 gl = gLDrawable.getGL().getGL2();

		gl.glClearColor(1, 1, 1, 1);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();

		glu.gluLookAt(cameraX, cameraY, cameraZ, lookAtX, lookAtY, lookAtZ, 0, 1, 0);

		drawBasketball(gl);
		drawBackboard(gl);
		drawRim(gl);
		drawFloor(gl);

		if(!canTransform) {
			
			// Draw Direction Arrow
			gl.glBegin(gl.GL_LINES);
			gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, whiteDiffuseLight, 0);
			gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, whiteDiffuseLight, 0);

			// Tail of Arrow - adjusted by Position Panel
			gl.glVertex3f(sphereX, sphereY, sphereZ);

			// Tip of Arrow - adjusted by Direction Panel
			gl.glVertex3f(tipArrowX, tipArrowY, tipArrowZ);		
			gl.glEnd();
		}

		gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glPushMatrix();
		drawSkybox(gl); // Draw Skybox
		gl.glPopMatrix();
		gl.glDisable(GL.GL_TEXTURE_2D);

	}

	void bounced() {
		for (int i = 0; i < 3; ++i)
			sphereVelocity[i] *= dampening;
	}
	
	float magnitude(float[] a) {
		float ans = (float)Math.sqrt(Math.pow(a[0], 2) + Math.pow(a[1], 2) + Math.pow(a[2], 2));
		return ans;
	}
	
	float dot_prod(float[] a, float[] b) {
		return a[0]*b[0] + a[1]*b[1] + a[2]*b[2];
	}
	
	
	int updateVelocity()  // Globals are fun!
	{
		int millisecondsSinceLastUpdate = (int) (sphereTimePassed * TIME_MULT);
		sphereTimePassed = 0;
	
		// Collision checks.
		// Floor
		if (floorLeft-sphereRadius < sphereX && sphereX < floorRight+sphereRadius)
		{
			if (floorY-sphereRadius < sphereY && sphereY < floorY+sphereRadius)
			{
				if (floorNear-sphereRadius < sphereZ && sphereZ < floorFar+sphereRadius)
				{
					sphereVelocity[1] = -sphereVelocity[1];
					sphereY = floorY + sphereRadius;
					
					bounced();
				}
			}
		}
		
		// Backboard
		if (backboardLeft-sphereRadius < sphereX && sphereX < backboardRight+sphereRadius)
		{
			if (backboardBottom < sphereY && sphereY < backboardTop)
			{
				if (backboardZ-sphereRadius < sphereZ && sphereZ < backboardZ+sphereRadius)
				{
					sphereVelocity[2] = -sphereVelocity[2];
					sphereZ = sphereZ - sphereRadius;
					
					bounced();
				}
			}
		}
		
		// Rim
		float[] xzProj = getUnitVelocity(-sphereX, 0f, rimZ-sphereZ);
		xzProj[0] *= rimOuterRadius; xzProj[2] *= rimOuterRadius;
		float[] cCenter = {xzProj[0], rimHeight, rimZ+xzProj[2]};
		float[] normal = {cCenter[0]-sphereX, cCenter[1]-sphereY, cCenter[2]-sphereZ};
		float distance = magnitude(normal);

		if (distance < sphereRadius + rimInnerRadius)
		{
			float mult = 2 * (float)(dot_prod(sphereVelocity,normal) / Math.pow(magnitude(normal), 2));
			sphereVelocity[0] -= mult*normal[0];
			sphereVelocity[1] -= mult*normal[1];
			sphereVelocity[2] -= mult*normal[2];
			
			bounced();
		}
		
		// Change due to gravity.
		sphereVelocity[1] -= (0.0003861 * millisecondsSinceLastUpdate); // WolframAlpha calculated.
	
		return millisecondsSinceLastUpdate;
	}
	
	void drawBasketball(final GL2 gl) {
		gl.glPushMatrix();
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, redDiffuseMaterial, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, blankMaterial, 0);
		if(sphereTimePassed == 0) 
		{
			// Intentionally blank.
		}
		else { // greater than zero		
			// calculate velocity updates
			int millisecondssSinceLastUpdate = updateVelocity();

			// calculate new sphere coordinates
			sphereX += sphereVelocity[0] * millisecondssSinceLastUpdate;
			sphereY += sphereVelocity[1] * millisecondssSinceLastUpdate;
			sphereZ += sphereVelocity[2] * millisecondssSinceLastUpdate;
			
			if (sphereY >= rimHeight + sphereRadius)
			{
				aboveRim = true;
			}
			
			if (sphereVelocity[1] <= 0)
			{
				if (!gameOver)
				{
					if (!aboveRim)
					{
						gameOver = true;
						victory = false;
					}
					else
					{
						if (sphereY <= rimHeight)
						{
							gameOver = true;
							
							if (sphereX > -(rimOuterRadius-rimInnerRadius) && sphereX < (rimOuterRadius-rimInnerRadius) &&
									sphereZ > rimZ - (rimOuterRadius-rimInnerRadius) && sphereZ < rimZ + (rimOuterRadius-rimInnerRadius))
							{
								victory = true;
								sphereVelocity[0]=0;
								sphereVelocity[2]=0;
								TIME_MULT = 0.25f;
							}
							else
							{
								victory = false;
							}
						}
					}
					if (gameOver)
					{
						if (victory)
						{
							System.out.printf("Winner!\n");
							playSound();
						}
						else
						{
							System.out.printf("Loser.\n");
							playSound();
						}
						ReplayPanel.ReplayPanel.setVisible(true);
					}
				}
			}
			
		}

		// translate sphere
		gl.glTranslatef(sphereX, sphereY, sphereZ);

		// draw sphere
		glut.glutSolidSphere(sphereRadius, 50, 50);
		gl.glPopMatrix();
	}
	
	// Resource: http://stackoverflow.com/questions/6045384/playing-mp3-and-wav-in-java
	void playSound() {
		if(victory) {
			try {
				// Resource: http://soundbible.com/468-Applause-Moderate-3-.html
		        AudioInputStream sound = AudioSystem.getAudioInputStream(new File("win.wav").getAbsoluteFile());
		        Clip c = AudioSystem.getClip();
		        c.open(sound);
		        c.start();
		    } catch(Exception ex) {
		        System.out.println("Error playing sound.");
		        ex.printStackTrace();
		    }
		}
		
		else {
			try {
				// Resource: http://soundbible.com/445-Crowd-Boo-1.html
		        AudioInputStream sound = AudioSystem.getAudioInputStream(new File("lose.wav").getAbsoluteFile());
		        Clip c = AudioSystem.getClip();
		        c.open(sound);
		        c.start();
		    } catch(Exception ex) {
		        System.out.println("Error playing sound.");
		        ex.printStackTrace();
		    }
		}
	}
	
	void drawBackboard(final GL2 gl) {
		
		gl.glPushMatrix();
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, greenEmissiveMaterial, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, blankMaterial, 0);
		
		gl.glBegin(gl.GL_QUADS);
		
		gl.glVertex3f(backboardLeft, backboardTop, backboardZ);
		gl.glVertex3f(backboardLeft, backboardBottom, backboardZ);
		gl.glVertex3f(backboardRight, backboardBottom, backboardZ);
		gl.glVertex3f(backboardRight, backboardTop, backboardZ);
	
		gl.glEnd();
		
		gl.glPopMatrix();
		
	}
	
	void drawRim(final GL2 gl) {
		gl.glPushMatrix();
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, redDiffuseMaterial, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, blankMaterial, 0);
		
		gl.glTranslatef(0, rimHeight, rimZ);
		gl.glRotatef(90, 1, 0, 0);
		
		glut.glutSolidTorus(rimInnerRadius, rimOuterRadius, 50, 50); // Standard rim is 18 inches in diameter
		gl.glPopMatrix();
		
	}
	
	void drawFloor(final GL2 gl) {
		gl.glPushMatrix();
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, greenEmissiveMaterial, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, blankMaterial, 0);
			
		gl.glBegin(gl.GL_QUADS);
		
		gl.glVertex3f(floorLeft, floorY, floorNear);
		gl.glVertex3f(floorLeft, floorY, floorFar);
		gl.glVertex3f(floorRight, floorY, floorFar);
		gl.glVertex3f(floorRight, floorY, floorNear);
		
		gl.glEnd();
		
		gl.glPopMatrix();
		
	}

	@Override
	public void dispose(GLAutoDrawable arg0) 
	{

	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		float t_look_at_x = (float)Math.sin(Math.toRadians(-x_angle-90f)) + cameraX;
		float t_look_at_z = (float)Math.cos(Math.toRadians(-x_angle-90f)) + cameraZ;
		
		char key= e.getKeyChar();
		System.out.printf("Key typed: %c\n", key); 
		
		if (replayTime)
		{
			switch(key) 
			{
			case 'w':
			case 'W':
				cameraX -= move_speed*(cameraX - lookAtX);
				cameraZ -= move_speed*(cameraZ - lookAtZ);
				break;
			case 's':
			case 'S':
				cameraX += move_speed*(cameraX - lookAtX);
				cameraZ += move_speed*(cameraZ - lookAtZ);
				break;
			case 'a':
			case 'A':
				cameraX += move_speed*(cameraX - t_look_at_x);
				cameraZ += move_speed*(cameraZ - t_look_at_z);
				break;
			case 'd':
			case 'D':
				cameraX -= move_speed*(cameraX - t_look_at_x);
				cameraZ -= move_speed*(cameraZ - t_look_at_z);
				break;
			case 'o':
			case 'O':
				cameraY += move_speed;
				break;
			case 'l':
			case 'L':
				cameraY -= move_speed;
				break;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{

	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// TODO Auto-generated method stub

		char key= e.getKeyChar();
		
		if(key == 'n') { // Resets game
			
			sphereT.stop();
			
			ReplayPanel.ReplayPanel.setVisible(false);
			
			sphereTimePassed = 0;
			canTransform = false;

			cameraX = INIT_cameraX;
			cameraY = INIT_cameraY;
			cameraZ = INIT_cameraZ;
			
			lookAtX = INIT_lookAtX;
			lookAtY = INIT_lookAtY;
			lookAtZ = INIT_lookAtZ;
			
			sphereX = INIT_sphereX;
			sphereY = INIT_sphereY;
			sphereZ = INIT_sphereZ;
			tipArrowX = INIT_tipArrowX;
			tipArrowY = INIT_tipArrowY;
			tipArrowZ = INIT_tipArrowZ;
			
			old_XX=0;
			old_YY=0;
			
			x_angle = 0;
			y_angle = 0;
			
			aboveRim = false;
			gameOver = false;
			victory = false;
			TIME_MULT = INIT_TIME_MULT;

			launchSphereX = INIT_sphereX;
			launchSphereY = INIT_sphereY;
			launchSphereZ = INIT_sphereZ;
			
			launchSphereVelocity[0] = 0;
			launchSphereVelocity[1] = 0;
			launchSphereVelocity[2] = 0;
			
			replayTime = false;
			
			v0 = 0;
			PowerPanel.progressBar.setValue(v0);
			PowerPanel.PowerPanel.setVisible(true);
			
			DirectionPanel.btnUp.setEnabled(true);
			DirectionPanel.btnDown.setEnabled(true);
			DirectionPanel.btnLeft.setEnabled(true);
			DirectionPanel.btnRight.setEnabled(true);
			PositionPanel.btnRight.setEnabled(true);
			PositionPanel.btnLeft.setEnabled(true);
			
		}

		else if(key == ' ') 
		{
			if(!PowerPanel.t.isRunning()) 
			{
				PowerPanel.progressBar.setValue(PowerPanel.value);
				PowerPanel.t.start();
			}
			else 
			{
				PowerPanel.t.stop();
				v0 = PowerPanel.progressBar.getValue();

				if(v0 < PowerPanel.progressBar.getMaximum()) 
				{
					System.out.println("Initial velocity is " + v0);
					PowerPanel.value = 0;
					availableTries = 2; // Restore number of tries

					PowerPanel.PowerPanel.setVisible(false);

					// begin transformation of sphere
					canTransform = true;
					
					// disable all buttons
					DirectionPanel.btnUp.setEnabled(false);
					DirectionPanel.btnDown.setEnabled(false);
					DirectionPanel.btnLeft.setEnabled(false);
					DirectionPanel.btnRight.setEnabled(false);
					PositionPanel.btnRight.setEnabled(false);
					PositionPanel.btnLeft.setEnabled(false);
					
					// start Timer for sphere transformation
					sphereT.start();
					System.out.println(String.format("{%f, %f, %f}", sphereVelocity[0], sphereVelocity[1], sphereVelocity[2]));
					sphereVelocity = getUnitVelocity(tipArrowX-sphereX, tipArrowY-sphereY, tipArrowZ-sphereZ);
					for (int i = 0; i < 3; ++i)
					{
						sphereVelocity[i] *= v0;
						sphereVelocity[i] *= .005f;
					}
					System.out.println(String.format("{%f, %f, %f}", sphereVelocity[0], sphereVelocity[1], sphereVelocity[2]));
					
					// Store initial values for everything...
					launchSphereX = sphereX;
					launchSphereY = sphereY;
					launchSphereZ = sphereZ;
					
					launchSphereVelocity[0] = sphereVelocity[0];
					launchSphereVelocity[1] = sphereVelocity[1];
					launchSphereVelocity[2] = sphereVelocity[2];
					
				}
				else // Didn't stop before maximum 
				{ 
					
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
		float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
		float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
		System.out.printf("Point moved: (%.3f, %.3f)\n", XX, YY);
		
		
		float dist_x = XX - old_XX;
		float dist_y = YY - old_YY;
		
		old_XX = XX;
		old_YY = YY;
		if (replayTime)
		{
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

}