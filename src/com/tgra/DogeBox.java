package com.tgra;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;

public class DogeBox {
        FloatBuffer texCoordBuffer;
        Texture tex;
        Cube cube;
        
        public class Cube {

            FloatBuffer vertexBuffer;
            FloatBuffer texCoordBuffer;
            
            Texture tex;
            
            private Cube(String texture)
            {
                    vertexBuffer = BufferUtils.newFloatBuffer(72);
                    vertexBuffer.put(new float[] {-0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f,
                                                                              0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f,
                                                                              0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f,
                                                                              0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,
                                                                              0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,
                                                                              -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f,
                                                                              -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f,
                                                                              -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f,
                                                                              -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f,
                                                                              0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f,
                                                                              -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f,
                                                                              0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f});
                    vertexBuffer.rewind();
                    
                    texCoordBuffer = BufferUtils.newFloatBuffer(48);
                    texCoordBuffer.put(new float[] {0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                                                                                    0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                                                                                    0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                                                                                    0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                                                                                    0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                                                                                    0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f});
                    texCoordBuffer.rewind();
                    
                    tex = new Texture(Gdx.files.internal(texture));
            }
            

            public void draw()
            {
                    Gdx.gl11.glShadeModel(GL11.GL_SMOOTH);
                    Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);
                    
                    Gdx.gl11.glEnable(GL11.GL_TEXTURE_2D);
                    Gdx.gl11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
                    
                    tex.bind();  //Gdx.gl11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

                    Gdx.gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, texCoordBuffer);

                    Gdx.gl11.glNormal3f(1.0f, 0.0f, 0.0f);
                    Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 4, 4);
                    Gdx.gl11.glDisable(GL11.GL_TEXTURE_2D);
                    Gdx.gl11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
                    
            }
    }
        
        public DogeBox() {
        	this.cube = new Cube("assets/textures/doge2.png");
        }
        
        public void draw()
        {
             Gdx.gl11.glEnable(GL11.GL_ALPHA_TEST);
             Gdx.gl11.glAlphaFunc(GL11.GL_GREATER, 0.03f);
                
             Gdx.gl11.glEnable(GL11.GL_BLEND);
             Gdx.gl11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_DST_ALPHA);
             
             this.cube.draw();
             
             Gdx.gl11.glDisable(GL11.GL_BLEND);
             Gdx.gl11.glDisable(GL11.GL_ALPHA_TEST);             
        }
}