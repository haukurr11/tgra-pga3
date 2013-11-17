package com.tgra;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.utils.BufferUtils;

public class Ramp {
	private FloatBuffer vertexBuffer;
	float x;
	float z;
	float y;
	public boolean collides(Shuttle shuttle) {
		return Math.abs(shuttle.x-x)<1 && Math.abs(shuttle.z-z) < 1;
	}
	public Ramp(float x, float y, float z) {
		this.x=x;
		this.z=z;
		this.y=y;
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
				
				-0.5f, -0.5f, -0.5f, 
				-0.5f, -0.5f, 0.5f,
				0.5f, 0.5f, -0.5f, 
				0.5f, 0.5f, 0.5f,
				
				-0.5f, -0.5f, -0.5f, 
				-0.5f, -0.5f, 0.5f,
				0.5f, -0.5f, -0.5f, 
				0.5f, -0.5f, 0.5f
				
				
					 });
		
		vertexBuffer.rewind();

	}
	
	public void draw() {
		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glTranslatef(x, y, z);
		Gdx.gl11.glScalef(2.95f, 2.95f, 2.95f);
		Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);
		Gdx.gl11.glNormal3f(0.0f, 0.0f, -1.0f);
//		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
//		Gdx.gl11.glNormal3f(1.0f, 0.0f, 0.0f);
//		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 4, 4);
//		Gdx.gl11.glNormal3f(0.0f, 0.0f, 1.0f);
//		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 8, 4);
//		Gdx.gl11.glNormal3f(-1.0f, 0.0f, 0.0f);
//		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 12, 4);
//		Gdx.gl11.glNormal3f(0.0f, 1.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 16, 4);
//		Gdx.gl11.glNormal3f(0.0f, -1.0f, 0.0f);
//		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 20, 4);
		Gdx.gl11.glPopMatrix();
	}
}
