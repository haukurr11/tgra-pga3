package com.tgra;

import java.nio.FloatBuffer;

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
	
	private Tunnel(int widthRes, int depthRes) {
		this.widthRes = widthRes;
		this.depthRes = depthRes;
	}

	public static Tunnel createBicubicBezierPatch(Point3D[][] controlPoints,
			int widthRes, int depthRes) {
		Tunnel bpatch = new Tunnel(widthRes, depthRes);
		bpatch.makeBezierPatch(controlPoints);
		bpatch.calculateNormals();
		bpatch.loadArrays();
		return bpatch;
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

		for (int i = 0; i < widthRes; i++) {
			Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, i * (depthRes + 1)
					* 2, (depthRes + 1) * 2);
		}

		Gdx.gl11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
	}
}
