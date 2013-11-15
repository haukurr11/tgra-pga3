package com.tgra;
import java.awt.peer.LightweightPeer;
import java.nio.FloatBuffer;

import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;


public class First3D_Core implements ApplicationListener, InputProcessor
{
	Camera cam;
	private boolean ligthBulbState = true;
	private boolean wiggleLights = false;
	private float wiggleValue = 0f;
	private float count = 0;
	private Skybox skybox;
	private Cube cube;
	private Floor floor;
	private Tunnel tunnel;
	private boolean jumping;
	FloatBuffer vertexBuffer;
		
	@Override
	public void create() {
		jumping = false;
		cube = new Cube();
		floor = new Floor();
		cube.setCoord(cube.x, 5.0f, 5f);
		skybox = new Skybox();
		
		Gdx.input.setInputProcessor(this);
		
		Gdx.gl11.glEnable(GL11.GL_LIGHTING);
		
		//Gdx.gl11.glEnable(GL11.GL_LIGHT1);
		Gdx.gl11.glEnable(GL11.GL_DEPTH_TEST);
		
		Gdx.gl11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		Gdx.gl11.glMatrixMode(GL11.GL_PROJECTION);
		Gdx.gl11.glLoadIdentity();
		Gdx.glu.gluPerspective(Gdx.gl11, 90, 1.333333f, 1.0f, 100.0f);

		Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

		vertexBuffer = BufferUtils.newFloatBuffer(72);
		vertexBuffer.put(new float[] {
				-0.5f, -0.5f, -0.5f, 
				-0.5f, 0.5f, -0.5f,
				0.5f, -0.5f, -0.5f, 
				0.5f, 0.5f, -0.5f,
				0.5f, -0.5f, -0.5f, 
				0.5f, 0.5f, -0.5f,
				0.5f, -0.5f, 0.5f, 
				0.5f, 0.5f, 0.5f,
				0.5f, -0.5f, 0.5f, 
				0.5f, 0.5f, 0.5f,
				-0.5f, -0.5f, 0.5f, 
				-0.5f, 0.5f, 0.5f,
				-0.5f, -0.5f, 0.5f, 
				-0.5f, 0.5f, 0.5f,
				-0.5f, -0.5f, -0.5f, 
				-0.5f, 0.5f, -0.5f,
				-0.5f, 0.5f, -0.5f, 
				-0.5f, 0.5f, 0.5f,
				0.5f, 0.5f, -0.5f, 
				0.5f, 0.5f, 0.5f,
				-0.5f, -0.5f, -0.5f, 
				-0.5f, -0.5f, 0.5f,
				0.5f, -0.5f, -0.5f, 
				0.5f, -0.5f, 0.5f
				
				
					 });
		
		vertexBuffer.rewind();
		
		tunnel = Tunnel.createBicubicBezierPatch(new Point3D[][] {
				{
				new Point3D(0.0f, 0.0f, 0.0f), new Point3D(0.0f, 2.0f, 1.0f), new Point3D(0.0f, 2.0f, 2.0f), new Point3D(0.0f, 0.0f, 3.0f)},
				{new Point3D(1.0f, 2.0f, 0.0f), new Point3D(1.0f, 0.0f, 1.0f), new Point3D(1.0f, 0.0f, 2.0f), new Point3D(1.0f, 2.0f, 3.0f)},
				{new Point3D(2.0f, 2.0f, 0.0f), new Point3D(2.0f, 0.0f, 1.0f), new Point3D(2.0f, 0.0f, 2.0f), new Point3D(2.0f, 2.0f, 3.0f)},
				{new Point3D(3.0f, 0.0f, 0.0f), new Point3D(3.0f, 2.0f, 1.0f), new Point3D(3.0f, 2.0f, 2.0f), new Point3D(3.0f, 0.0f, 3.0f)
				}
		},
		200, 200);
		
		Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);
		cam = new Camera(new Point3D(0.0f, 7f, 5.0f), new Point3D(200.0f, 7f, 6.0f), new Vector3D(0.0f, 1.0f, 0.0f));
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	
	private void update() {

		System.out.println(floor.collides(cube));
		if(jumping) {
			System.out.println("JUMPING");
			float deltaTime = Gdx.graphics.getDeltaTime();
			cube.y += deltaTime * 7.5;
			if(cube.y > 5.0) {
				jumping = false;
			}
		}
		if(!floor.collides(cube)) {
			System.out.println("YES");
			float deltaTime = Gdx.graphics.getDeltaTime();
			cube.y -= deltaTime * 5.0;
		}
		if(this.wiggleLights){
			count += 0.03;
			this.wiggleValue = (float) Math.sin(count) * 10;
		}
		
		
		
		if(this.ligthBulbState)
			Gdx.gl11.glEnable(GL11.GL_LIGHT0);
		else
			Gdx.gl11.glDisable(GL11.GL_LIGHT0);
		
		float deltaTime = Gdx.graphics.getDeltaTime();

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) { 
			cam.roll(90.0f * deltaTime);
			cube.rotate(90f * deltaTime);
		}
		

		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) { 
			if(floor.collides(cube))
			jumping = true;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			cam.roll(-90.0f * deltaTime);
			cube.rotate(-90f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) 
			cam.slide(0.0f, 0.0f, -10.0f * deltaTime);
		
		if(Gdx.input.isKeyPressed(Input.Keys.S)) 
			cam.slide(0.0f, 0.0f, 10.0f * deltaTime);
		
		if(Gdx.input.isKeyPressed(Input.Keys.A))  {
			cube.z -= 10.0f * deltaTime;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.D)) { 
			cube.z +=10.0f * deltaTime;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.R)) 
			cam.slide(0.0f, 10.0f * deltaTime, 0.0f);
		
		if(Gdx.input.isKeyPressed(Input.Keys.F)) 
			cam.slide(0.0f, -10.0f * deltaTime, 0.0f);
	}
	
	private void drawShuttle() {
		Gdx.gl11.glPushMatrix();


		Gdx.gl11.glNormal3f(0.0f, 0.0f, -1.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		Gdx.gl11.glNormal3f(1.0f, 0.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 4, 4);
		Gdx.gl11.glNormal3f(0.0f, 0.0f, 1.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 8, 4);
		Gdx.gl11.glNormal3f(-1.0f, 0.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 12, 4);
		Gdx.gl11.glNormal3f(0.0f, 1.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 16, 4);
		Gdx.gl11.glNormal3f(0.0f, -1.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 20, 4);
		Gdx.gl11.glPopMatrix();
	}
	
	private void display() {

		
        Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
        cam.setModelViewMatrix();
                        
        // Configure light 0
        float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
        Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, lightDiffuse, 0);

        float[] lightPosition = {this.wiggleValue, 10.0f, 15.0f, 1.0f};
        Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition, 0);

        // Configure light 1
        float[] lightDiffuse1 = {0.5f, 0.5f, 0.5f, 1.0f};
        Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, lightDiffuse1, 0);

        float[] lightPosition1 = {-5.0f, -10.0f, -15.0f, 1.0f};
        Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_POSITION, lightPosition1, 0);
        

        
        Gdx.gl11.glPushMatrix();
        this.cube.setCoord(this.cam.eye.x+10f, this.cube.y, this.cube.z);
        this.cube.draw();
		Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);

		Gdx.gl11.glTranslatef(this.cam.eye.x, this.cam.eye.y, this.cam.eye.z);
		Gdx.gl11.glScalef(150f,150f,150f);
		this.skybox.Render();		
		
        Gdx.gl11.glPopMatrix();

        floor.draw();

        Gdx.gl11.glTranslatef(0,1f, 0f);
        this.tunnel.draw();
	}

	@Override
	public void render() {
		update();
		display();
	}

	@Override
	public void resize(int arg0, int arg1) {
	}

	@Override
	public void resume() {
	}

	@Override
	public boolean keyDown(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {

		if(arg0 == Input.Keys.L){
			this.ligthBulbState = this.ligthBulbState ? false:true;
		}
		if(arg0 == Input.Keys.O){
			this.wiggleLights = this.wiggleLights ? false:true;
		}
		
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}
}
