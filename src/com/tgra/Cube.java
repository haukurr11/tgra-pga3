package com.tgra;

import java.nio.FloatBuffer;
import java.util.Timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;

public class Cube {

	FloatBuffer vertexBuffer;
	FloatBuffer texCoordBuffer;
	
	Texture tex;
	float angle;
	float jumpheight;
	float y;
	float x;
	float z;
	public Cube()
	{
		this.x=0;
		this.y=0;
		this.z=0;
		jumpheight = 0;
		angle = 0;
		vertexBuffer = BufferUtils.newFloatBuffer(72);
		vertexBuffer.put(new float[] {
				 -0.5f, -0.5f, -0.5f, 
				 -0.5f, 0.5f, -0.5f,
				  2.0f, -0.5f, 0f,
				  2.0f, -0.5f, 0f,
				  
				  0.5f, -0.5f, -0.5f, 
				  0.5f, 0.5f, -0.5f,
				  0.5f, -0.5f, 0.5f, 
				  0.5f, 0.5f, 0.5f,
				  
				  2f, -0.5f, 0f,
				  2f,-0.5f, 0f,
				  -0.5f, -0.5f, 0.5f,
				  -0.5f, 0.5f, 0.5f,
				  
				  -0.5f, -0.5f, 0.5f, 
				  -0.5f, 0.5f, 0.5f,
				  -0.5f, -0.5f, -0.5f, 
				  -0.5f, 0.5f, -0.5f,
				  
				  -0.5f, 0.5f, -0.5f,
				  -0.5f, 0.5f, 0.5f,
				  2f, -0.5f, 0f,
				  2f, -0.5f, 0f,
				  
				  -0.5f, -0.5f, -0.5f, 
				  -0.5f, -0.5f, 0.5f,
				  2f, -0.5f, 0f, 
				  2f, -0.5f, 0f
				  
		});
		vertexBuffer.rewind();

	
	}
	public void setCoord(float x,float y,float z) {
		this.x=x;
		this.y=y;
		this.z=z;
	}
	public void rotate(float angle) {
		this.angle += angle;
		this.angle = this.angle % 360;
	}
	
	public void jump() {
		
	}

	public void draw()
	{
		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glShadeModel(GL11.GL_SMOOTH);
		Gdx.gl11.glTranslatef(this.x, this.y,this.z);
		Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);
		
		//Gdx.gl11.glRotatef(-this.angle, 1f, 0f, 0f);

		float[] materialDiffuse = {7f, 0f, 0f, 0.0f};
		Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_AMBIENT, materialDiffuse, 0);
		Gdx.gl11.glNormal3f(0.0f, 0.0f, -1.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		Gdx.gl11.glNormal3f(0.0f, 0.0f, 1.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 8, 4);
		Gdx.gl11.glNormal3f(-1.0f, 0.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 12, 4);
		Gdx.gl11.glNormal3f(0.0f, 1.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 16, 4);
		Gdx.gl11.glNormal3f(0.0f, -1.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 20, 4);
		Gdx.gl11.glPopMatrix();

		float[] materialDiffuse2 = {0.8f, 0.8f,1.8f, 0.0f};
		Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_AMBIENT, materialDiffuse2, 0);

	}
}
