package Lab.Fighter;


import Lab.Texture.TextureReader;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.BitSet;

public class AnimGLEventListener3 extends AnimListener {

    int animationIndex = 0;
    int maxWidth = 100;
    int maxHeight = 100;
    float scaleX = 1.0f;
    int x = maxWidth/2, y = 30, angle = 0;
    boolean walking = false, attack1 = false;
    
    // Download enemy textures from https://craftpix.net/freebies/free-monster-2d-game-items/
    String textureNames[] = {"IdIe_1.png", "IdIe_2.png", "IdIe_3.png", "IdIe_4.png",
                            "IdIe_5.png", "IdIe_6.png", "walk_1.png", "walk_2.png",
                            "walk_3.png", "walk_4.png", "walk_5.png", "walk_6.png",
                            "walk_7.png", "walk_8.png", "attack1_1.png", "attack1_2.png",
                            "attack1_3.png", "attack1_4.png", "tree.png"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];

    /*
     5 means gun in array pos
     x and y coordinate for gun 
     */
    public void init(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black
        
        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);	
        gl.glGenTextures(textureNames.length, textures, 0);
        
        for(int i = 0; i < textureNames.length; i++){
            try {
                texture[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames[i] , true);
                System.out.println("Loaded texture " + i + ": " + textureNames[i] +
                        " - Dimensions: " + texture[i].getWidth() + "x" + texture[i].getHeight());
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

//                mipmapsFromPNG(gl, new GLU(), texture[i]);
                new GLU().gluBuild2DMipmaps(
                    GL.GL_TEXTURE_2D,
                    GL.GL_RGBA, // Internal Texel Format,
                    texture[i].getWidth(), texture[i].getHeight(),
                    GL.GL_RGBA, // External format from image,
                    GL.GL_UNSIGNED_BYTE,
                    texture[i].getPixels() // Imagedata
                    );
            } catch( IOException e ) {
                System.out.println("ERROR loading texture: " + textureNames[i]);
                System.out.println("Path: " + assetsFolderName + "//" + textureNames[i]);
                e.printStackTrace();
            }
        }

        checkTextures();


    }
    
    public void display(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity(); 
        
        DrawBackground(gl, 18);
        handleKeyPress();

        int start = getStart();// 14
        int remainder = getRem();// 4

        animationIndex = animationIndex % remainder;// 0-4

        DrawSprite(gl, x, y, animationIndex + start, 3);// (0-4) + 14

        animationIndex++;
        if (attack1 && animationIndex + start > 17)
            attack1 = false;
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    private void checkTextures() {
        for(int i = 0; i < textureNames.length; i++) {
            System.out.println("Texture " + i + ": " + textureNames[i] +
                    " - Loaded: " + (texture[i] != null));
        }
    }
    
    public void DrawSprite(GL gl,int x, int y, int index, float scale){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On

        gl.glPushMatrix();
            gl.glTranslated( x/(maxWidth/2.0) - 0.9, y/(maxHeight/2.0) - 0.9, 0);
            gl.glScaled(0.1*scale * scaleX, 0.1*scale, 1);
            //System.out.println(x +" " + y);
            gl.glBegin(GL.GL_QUADS);
            // Front Face
                gl.glTexCoord2f(0.0f, 0.0f);
                gl.glVertex3f(-1.0f, -1.0f, -1.0f);
                gl.glTexCoord2f(1.0f, 0.0f);
                gl.glVertex3f(1.0f, -1.0f, -1.0f);
                gl.glTexCoord2f(1.0f, 1.0f);
                gl.glVertex3f(1.0f, 1.0f, -1.0f);
                gl.glTexCoord2f(0.0f, 1.0f);
                gl.glVertex3f(-1.0f, 1.0f, -1.0f);
            gl.glEnd();
        gl.glPopMatrix();
        
        gl.glDisable(GL.GL_BLEND);
    }
    
    public void DrawBackground(GL gl, int idx){
        gl.glEnable(GL.GL_BLEND);	
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[idx]);	// Turn Blending On

        gl.glPushMatrix();
            gl.glBegin(GL.GL_QUADS);
            // Front Face
                gl.glTexCoord2f(0.0f, 0.0f);
                gl.glVertex3f(-1.0f, -1.0f, -1.0f);
                gl.glTexCoord2f(1.0f, 0.0f);
                gl.glVertex3f(1.0f, -1.0f, -1.0f);
                gl.glTexCoord2f(1.0f, 1.0f);
                gl.glVertex3f(1.0f, 1.0f, -1.0f);
                gl.glTexCoord2f(0.0f, 1.0f);
                gl.glVertex3f(-1.0f, 1.0f, -1.0f);
            gl.glEnd();
        gl.glPopMatrix();
        
        gl.glDisable(GL.GL_BLEND);
    }

    private int getRem() {
        if (attack1) return 4;
        if (walking) return 8;
        return 6;
    }

    private int getStart() {
        if (attack1) return 14;
        if (walking) return 6;
        return 0;
    }
    
    /*
     * KeyListener
     */    

    public void handleKeyPress() {

        if (isKeyPressed(KeyEvent.VK_1)) {
            if (!attack1) {
                attack1 = true;
                animationIndex = 0;
            }
        }

        boolean movingLeft = isKeyPressed(KeyEvent.VK_LEFT) && !attack1;
        boolean movingRight = isKeyPressed(KeyEvent.VK_RIGHT) && !attack1;

        if (movingLeft) {
            if (x > 5) {
                x -= 2;
                scaleX = -1.0f;
            }
        }
        if (movingRight) {
            if (x < maxWidth - 5) {
                x += 2;
                scaleX = 1.0f;
            }
        }

        walking = movingLeft || movingRight;
    }

    public BitSet keyBits = new BitSet(256);
 
    @Override 
    public void keyPressed(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.set(keyCode);
    } 
 
    @Override 
    public void keyReleased(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.clear(keyCode);
    } 
 
    @Override 
    public void keyTyped(final KeyEvent event) {
        // don't care 
    } 
 
    public boolean isKeyPressed(final int keyCode) {
        return keyBits.get(keyCode);
    }
}
