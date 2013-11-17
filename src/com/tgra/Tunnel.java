package com.tgra;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.utils.BufferUtils;

public class Tunnel {
	FloatBuffer vertexBuffer;
	FloatBuffer normalBuffer;
	float[][] heightValues;
	Point3D[][] points;
	Vector3D[][] normals;
	int widthRes;
	int depthRes;
	float x;
	float z;
	
	public boolean ontop(Shuttle cube) {
		for(int i=0;i<points.length;i++)
			for(int j=0;j<points[i].length;j++)
				if(Math.abs(points[i][j].y-cube.y) < 1.5 
				&& Math.abs(points[i][j].z-cube.z)<0.5
				&& Math.abs(points[i][j].x-cube.x)<0.5)
					return true;
		return false;
	}
	public boolean leftcol(Shuttle cube) {
		for(int i=0;i<points.length;i++)
			for(int j=0;j<points[i].length;j++)
				if(Math.round(Math.abs(points[i][j].y-cube.y)) <= 0 
				&& points[i][j].z-cube.z < 0 && points[i][j].z-cube.z > -1
				&& Math.round(Math.abs(points[i][j].x-cube.x)) <= 0
				) {
					return true;
				}
		return false;
	}
	public boolean rightcol(Shuttle cube) {
		for(int i=0;i<points.length;i++)
			for(int j=0;j<points[i].length;j++)
				if(Math.abs(points[i][j].y-cube.y) < 1 
				&& cube.z-points[i][j].z < 0 && cube.z-points[i][j].z > -1
				&& Math.round(Math.abs(points[i][j].x-cube.x)) <= 0)
					return true;
		return false;
	}
	public Tunnel(float x, float z,int widthRes, int depthRes) {
		this.x = x;
		this.z = z;
		this.widthRes = widthRes;
		this.depthRes = depthRes;
		this.createBicubicBezierPatch(x, z);
	}

	public void createBicubicBezierPatch(float x, float z) {
		this.makeBezierPatch(new Point3D[][] {
				{new Point3D(x, 0f, z),		new Point3D(x, 3.0f, z+1.0f), 		new Point3D(x, 3.0f, z+2.0f),		new Point3D(x, 0f, z+3.0f)},
				{new Point3D(x+1.0f, 0f, z),	new Point3D(x+1.0f, 3.0f, z+1.0f),	new Point3D(x+1.0f, 3.0f, z+2.0f),	new Point3D(x+1.0f, 0f, z+3.0f)},
				{new Point3D(x+2.0f, 0f, z), 	new Point3D(x+2.0f, 3.0f, z+1.0f),	new Point3D(x+2.0f, 3.0f, z+2.0f),	new Point3D(x+2.0f, 0f, z+3.0f)},
				{new Point3D(x+3.0f, 0f, z), 	new Point3D(x+3.0f, 3.0f, z+1.0f),	new Point3D(x+3.0f, 3.0f, z+2.0f),	new Point3D(x+3.0f, 0f, z+3.0f)
				}
		});
		this.calculateNormals();
		this.loadArrays();
	}

	private void makeBezierPatch(Point3D[][] controlPoints) {
		points = new Point3D[widthRes + 1][depthRes + 1];
		for (int i = 0; i <= widthRes; i++) {
			Point3D P1 = bezier(controlPoints[0][0], controlPoints[1][0],
					controlPoints[2][0], controlPoints[3][0], (float) i
							/ (float) widthRes);
			Point3D P2 = bezier(controlPoints[0][1], controlPoints[1][1],
					controlPoints[2][1], controlPoints[3][1], (float) i
							/ (float) widthRes);
			Point3D P3 = bezier(controlPoints[0][2], controlPoints[1][2],
					controlPoints[2][2], controlPoints[3][2], (float) i
							/ (float) widthRes);
			Point3D P4 = bezier(controlPoints[0][3], controlPoints[1][3],
					controlPoints[2][3], controlPoints[3][3], (float) i
							/ (float) widthRes);
			for (int j = 0; j <= depthRes; j++) {
				points[i][j] = bezier(P1, P2, P3, P4, (float) j
						/ (float) depthRes);
			}
		}
	}

	private Point3D bezier(Point3D P1, Point3D P2, Point3D P3, Point3D P4,
			float t) {
		return new Point3D((1 - t) * (1 - t) * (1 - t) * P1.x + 3 * (1 - t)
				* (1 - t) * t * P2.x + 3 * (1 - t) * t * t * P3.x + t * t * t
				* P4.x, (1 - t) * (1 - t) * (1 - t) * P1.y + 3 * (1 - t)
				* (1 - t) * t * P2.y + 3 * (1 - t) * t * t * P3.y + t * t * t
				* P4.y, (1 - t) * (1 - t) * (1 - t) * P1.z + 3 * (1 - t)
				* (1 - t) * t * P2.z + 3 * (1 - t) * t * t * P3.z + t * t * t
				* P4.z);
	}

	private void calculateNormals() {
		normals = new Vector3D[widthRes + 1][depthRes + 1];

		for (int i = 0; i <= widthRes; i++) {
			for (int j = 0; j <= depthRes; j++) {
				if (i == 0) {
					if (j == 0) {
						normals[i][j] = normalTri(points[i][j],
								points[i][j + 1], points[i + 1][j]);
					} else if (j == depthRes) {
						normals[i][j] = normalTri(points[i][j],
								points[i + 1][j], points[i][j - 1]);
					} else {
						normals[i][j] = normalTri(points[i][j + 1],
								points[i + 1][j], points[i][j - 1]);
					}
				} else if (i == widthRes) {
					if (j == 0) {
						normals[i][j] = normalTri(points[i][j],
								points[i - 1][j], points[i][j + 1]);
					} else if (j == depthRes) {
						normals[i][j] = normalTri(points[i][j],
								points[i][j - 1], points[i - 1][j]);
					} else {
						normals[i][j] = normalTri(points[i][j - 1],
								points[i - 1][j], points[i][j + 1]);
					}
				} else if (j == 0) {
					normals[i][j] = normalTri(points[i - 1][j],
							points[i][j + 1], points[i + 1][j]);
				} else if (j == depthRes) {
					normals[i][j] = normalTri(points[i + 1][j],
							points[i][j - 1], points[i - 1][j]);
				} else {
					normals[i][j] = normalQuad(points[i][j - 1],
							points[i - 1][j], points[i][j + 1],
							points[i + 1][j]);
				}
			}
		}
	}

	private Vector3D normalTri(Point3D P1, Point3D P2, Point3D P3) {
		return new Vector3D((P1.y - P2.y) * (P1.z + P2.z) + (P2.y - P3.y)
				* (P2.z + P3.z) + (P3.y - P1.y) * (P3.z + P1.z), (P1.z - P2.z)
				* (P1.x + P2.x) + (P2.z - P3.z) * (P2.x + P3.x) + (P3.z - P1.z)
				* (P3.x + P1.x), (P1.x - P2.x) * (P1.y + P2.y) + (P2.x - P3.x)
				* (P2.y + P3.y) + (P3.x - P1.x) * (P3.y + P1.y));
	}

	private Vector3D normalQuad(Point3D P1, Point3D P2, Point3D P3, Point3D P4) {
		return new Vector3D((P1.y - P2.y) * (P1.z + P2.z) + (P2.y - P3.y)
				* (P2.z + P3.z) + (P3.y - P4.y) * (P3.z + P4.z) + (P4.y - P1.y)
				* (P4.z + P1.z), (P1.z - P2.z) * (P1.x + P2.x) + (P2.z - P3.z)
				* (P2.x + P3.x) + (P3.z - P4.z) * (P3.x + P4.x) + (P4.z - P1.z)
				* (P4.x + P1.x), (P1.x - P2.x) * (P1.y + P2.y) + (P2.x - P3.x)
				* (P2.y + P3.y) + (P3.x - P4.x) * (P3.y + P4.y) + (P4.x - P1.x)
				* (P4.y + P1.y));
	}

	private void loadArrays() {
		vertexBuffer = BufferUtils
				.newFloatBuffer(widthRes * (depthRes + 1) * 6);
		normalBuffer = BufferUtils
				.newFloatBuffer(widthRes * (depthRes + 1) * 6);
		for (int i = 0; i < widthRes; i++) {
			for (int j = 0; j <= depthRes; j++) {
				vertexBuffer.put(points[i][j].x);
				vertexBuffer.put(points[i][j].y);
				vertexBuffer.put(points[i][j].z);

				vertexBuffer.put(points[i + 1][j].x);
				vertexBuffer.put(points[i + 1][j].y);
				vertexBuffer.put(points[i + 1][j].z);

				normalBuffer.put(normals[i][j].x);
				normalBuffer.put(normals[i][j].y);
				normalBuffer.put(normals[i][j].z);

				normalBuffer.put(normals[i + 1][j].x);
				normalBuffer.put(normals[i + 1][j].y);
				normalBuffer.put(normals[i + 1][j].z);
			}
		}
		vertexBuffer.rewind();
		normalBuffer.rewind();
	}

	public void draw() {
		Gdx.gl11.glShadeModel(GL11.GL_SMOOTH);
		Gdx.gl11.glEnable(GL11.GL_NORMALIZE);

		Gdx.gl11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
		Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);
		Gdx.gl11.glNormalPointer(GL11.GL_FLOAT, 0, normalBuffer);

		float[] materialDiffuse2 = {1f, 1f,2f, 0.0f};
		Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_AMBIENT, materialDiffuse2, 0);
		for (int i = 0; i < widthRes; i++) {
			Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, i * (depthRes + 1)
					* 2, (depthRes + 1) * 2);
		}

		Gdx.gl11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
	}
}
