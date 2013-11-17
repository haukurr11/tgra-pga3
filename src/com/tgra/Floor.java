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
		float y;
		private FloatBuffer vertexBuffer;
		
		
		public boolean ontop(com.tgra.Shuttle cube) {
			if(Math.sqrt(Math.pow(cube.x-cube.x,2)+Math.pow(cube.z-cube.z,2)) > 40)
				return false;
			if(cube.y-(y+1) < 0.2 && Math.abs(Math.round(cube.x)-x) < 1 && Math.abs(Math.round(cube.z)-z) < 1) {
				return true;
			}
			return false;
		}
		public boolean collides(com.tgra.Shuttle cube) {
			if(Math.sqrt(Math.pow(cube.x-cube.x,2)+Math.pow(cube.z-cube.z,2)) > 40)
				return false;
			if(cube.y-(y+1) < 0 && Math.abs(Math.round(cube.x)-x) < 0.5 && Math.abs(Math.round(cube.z)-z) < 0.5) {
				return true;
			}
			return false;
		}
		public Cube(float x,float y, float z) {
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
			Gdx.gl11.glTranslatef(x, y, z);
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

		for(float fx = 0.0f; fx < 40; fx += 1.0) {
			for(float fz = 0.0f; fz < 5; fz += 1.0) {
					cubes.add(new Cube(fx,0,fz));
			}
	      }
		for(float fx = 40f; fx < 90f; fx += 1.0) {
			for(float fz = 10f; fz < 20; fz += 1.0) {
					cubes.add(new Cube(fx,0,fz));
			}
	      }

		for(float fx = 150f; fx < 190f; fx += 1.0) {
			for(float fz = 10f; fz < 20; fz += 1.0) {
					cubes.add(new Cube(fx,0,fz));
			}
	      }

		for(float fx = 190f; fx < 300f; fx += 1.0) {
			for(float fz = 15f; fz < 17; fz += 1.0) {
					cubes.add(new Cube(fx,1,fz));
			}
	      }
		
		for(float fx = 310f; fx < 400f; fx += 1.0) {
			for(float fz = 10f; fz < 20; fz += 1.0) {
					cubes.add(new Cube(fx,0,fz));
			}
	      }
		for(float fx = 420f; fx < 480f; fx += 1.0) {
			for(float fz = 10f; fz < 15; fz += 1.0) {
					cubes.add(new Cube(fx,0,fz));
			}
	      }
		for(float fx = 500f; fx < 520f; fx += 1.0) {
			for(float fz = 10f; fz < 15; fz += 1.0) {
					cubes.add(new Cube(fx,0,fz));
			}
	      }
		for(float fx = 520f; fx < 560f; fx += 1.0) {
			for(float fz = 10f; fz < 15; fz += 1.0) {
					cubes.add(new Cube(fx,0,fz));
			}
	      }
		for(float fx = 580f; fx < 680f; fx += 1.0) {
			for(float fz = 10f; fz < 13; fz += 1.0) {
					cubes.add(new Cube(fx,0,fz));
			}
	      }
		for(float fx = 700f; fx < 900f; fx += 1.0) {
			for(float fz = 10f; fz < 13; fz += 1.0) {
					cubes.add(new Cube(fx,0,fz));
					if(fx % 35 == 0)
						cubes.add(new Cube(fx,1,fz));
			}
	      }
		for(float fx = 930f; fx < 990f; fx += 1.0) {
			for(float fz = 10f; fz < 13; fz += 1.0) {
					cubes.add(new Cube(fx,0,fz));
			}
	      }
		for(float fx = 1200f; fx < 1400f; fx += 1.0) {
			for(float fz = 10f; fz <12f; fz += 1.0) {
					cubes.add(new Cube(fx,0,fz));
			}
	      }
		for(float fx = 1500f; fx < 1800f; fx += 1.0) {
			for(float fz = 10f; fz <12f; fz += 1.0) {
					cubes.add(new Cube(fx,0,fz));
			}
	      }
	}
	
	public void draw(com.tgra.Shuttle shuttle) {

		float[] materialDiffuse2 = {0f, 2f,0f, 0.0f};
		Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_AMBIENT, materialDiffuse2, 0);
		for(Cube cube : cubes) {
			if(Math.sqrt(Math.pow(shuttle.x-cube.x,2)+Math.pow(shuttle.z-cube.z,2)) < 40) {
			   if(cube.y > 0) {
					float[] materialDiffuse3 = {2f, 0f,0f, 0.0f};
					Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_AMBIENT, materialDiffuse3, 0);
			   }
				cube.draw();
			}
		}
	}
	public boolean ontop(com.tgra.Shuttle shuttle) {
		for(Cube cube : cubes) {
			if(cube.ontop(shuttle)) {
				return true;
			}
		}
		return false;
	}
	public boolean collides(com.tgra.Shuttle shuttle) {
		for(Cube cube : cubes) {
			if(cube.collides(shuttle)) {
				return true;
			}
		}
		return false;
	}
}
