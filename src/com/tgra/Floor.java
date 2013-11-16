package com.tgra;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.utils.BufferUtils;

public class Floor {
	private ArrayList<Cube> cubes;
	private class Cube {
		float x;
		float z;
		private FloatBuffer vertexBuffer;
		
		public boolean ontop(com.tgra.Cube cube) {
			if(Math.round(cube.y-1) == 1 && Math.abs(Math.round(cube.x)-x) < 1 && Math.abs(Math.round(cube.z)-z) < 1) {
				return true;
			}
			return false;
		}
		public boolean collides(com.tgra.Cube cube) {
			System.out.println(cube.y);
			if(cube.y < 2 && Math.abs(Math.round(cube.x)-x) < 0.5 && Math.abs(Math.round(cube.z)-z) < 0.5) {
				return true;
			}
			return false;
		}
		public Cube(float x, float z) {
			this.x=x;
			this.z=z;
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
			
			}
		public void draw() {
			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glTranslatef(x, 1.0f, z);
			Gdx.gl11.glScalef(0.95f, 0.95f, 0.95f);
			Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);
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
	}

	
	public Floor() {
	    cubes = new ArrayList<Cube>();

		for(float fx = 0.0f; fx < 50; fx += 1.0) {
			if(fx % 4 == 0)
				continue;
			for(float fz = 0.0f; fz < 50/8; fz += 1.0) {
					cubes.add(new Cube(fx,fz));
			}
	      }
	}
	
	public void draw() {

		float[] materialDiffuse2 = {0f, 1f,1f, 0.0f};
		Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_AMBIENT, materialDiffuse2, 0);
		for(Cube cube : cubes) {
			cube.draw();
		}
	}
	public boolean ontop(com.tgra.Cube shuttle) {
		for(Cube cube : cubes) {
			if(cube.ontop(shuttle)) {
				return true;
			}
		}
		return false;
	}
	public boolean collides(com.tgra.Cube shuttle) {
		for(Cube cube : cubes) {
			if(cube.collides(shuttle)) {
				return true;
			}
		}
		return false;
	}
}
