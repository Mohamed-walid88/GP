package Lab.DinosaurGame;


import Lab.Texture.TextureReader;
import com.sun.opengl.util.GLUT;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class AnimGLEventListener3 extends AnimListener {

    GLUT glut = new GLUT();
    private final int landIdx = 11;
    private int maxWidth = 100;
    private int maxHeight = 100;
    private int gameSpeed = 25, gameScore = 0, nextLevel = 200, level = 1, time = 0;
    private int dinosaurIdx = 0, dinoX = 5, dinoY = maxHeight/5, jumpFrames = 0, dinoSpeed = 2, jumpValue = 14,
            pauseTime = time, pervDinoSpeed = dinoSpeed;
    private boolean jumping = false, gameOver = false, pause = false;
    private List<Cactus> cactuses = new ArrayList<>();
    
    // Download enemy textures from https://craftpix.net/freebies/free-monster-2d-game-items/
    String textureNames[] = {"walk1.png","walk2.png", "hit.png", "duck1.png", "duck2.png",
            "cocus1b.png", "cocus1c.png", "cocus1s.png", "cocus2s.png", "cocus3.png",
            "cocus3s.png", "land.png"};
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
              System.out.println(e);
              e.printStackTrace();
            }
        }
    }
    
    public void display(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity(); 
        
        DrawBackground(gl, landIdx);
        handleKeyPress();

        if (!gameOver && !pause) {
            gameScore++;
        }

        DrawScore(gl);


        if (gameOver) {
            DrawSprite(gl, dinoX, dinoY, 2, 1);
            JOptionPane.showMessageDialog(null, "You Lost! Final Score: " + gameScore);
            return;
        }
        DinoGo(gl);
        GenerateCactus(gl);
        DrawCactus(gl);

        if (gameScore % nextLevel == 0) {
            gameSpeed -= 2;
            nextLevel *= 2;
            level++;
        }

        if (pause) {
            pervDinoSpeed = dinoSpeed;
            dinoSpeed = 0;
        }
        else {
            dinoSpeed = 2*level;
        }
        time++;
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
    
    private void DrawSprite(GL gl,int x, int y, int index, float scale){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On

        gl.glPushMatrix();
            gl.glTranslated( x/(maxWidth/2.0) - 0.9, y/(maxHeight/2.0) - 0.9, 0);
            gl.glScaled(0.1*scale, 0.1*scale, 1);
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
    
    private void DrawBackground(GL gl, int idx){
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

    private void DinoGo(GL gl) {
        dinosaurIdx = dinosaurIdx % 2;
        if (jumping)  {
            dinoY += (jumpFrames > jumpValue / 2) ? 2 : -2;

            jumpFrames--;
        }
        if (jumpFrames == 0) jumping = false;


        DrawSprite(gl, dinoX, dinoY, dinosaurIdx++, 1);
    }

    private void GenerateCactus(GL gl) {
        int minRange = 100, maxRange = 150, startIdx = 5, endIdx = 10;
        int randIdx = (int) (Math.random() * (endIdx - startIdx) + startIdx);
        int randLocation = (int) (Math.random() * (maxRange - minRange)) + minRange;
        int pervIdx = cactuses.size() - 1;
        int pervX = (pervIdx >= 0 ? cactuses.get(cactuses.size() - 1).getX() : 0);

        if (randLocation - pervX >= jumpValue * 2 && (randLocation - pervX) % gameSpeed == 0) {
            Cactus newCac = new Cactus(textureNames[randIdx], randLocation, randIdx);
            int hitbox = newCac.getHitBox();

            if (randLocation - hitbox - pervX >= jumpValue) {
                cactuses.add(newCac);
            }
        }
    }

    private void DrawCactus(GL gl) {
        for (int i = 0; i < cactuses.size(); i++) {
            Cactus c = cactuses.get(i);
            int x = c.getX() - dinoSpeed;
            int y = c.getY();
            int hitbox = c.getHitBox();
            cactuses.get(i).setX(x);


            if (dinoX >= x - hitbox && dinoX <= x + hitbox && dinoY <= y + 2)  {
                gameOver = true;
                return;
            }

            if (x <= -5) {
                cactuses.remove(i);
                i--;
            }
            DrawSprite(gl, x, y, c.getIdx(), 1);
        }
    }

    private void DrawScore(GL gl) {
        // 1. Coordinates: Top-left corner (Logic matches your DrawSprite math)
        // Your grid is 100x100. Let's place it at x=2, y=90
        double x = 2;
        double y = 90;

        // Convert to World Coordinates using your formula: x/(maxWidth/2.0) - 0.9
        double worldX = x / (maxWidth / 2.0) - 0.9;
        double worldY = y / (maxHeight / 2.0) - 0.9;

        // 2. Preparation
        gl.glDisable(GL.GL_TEXTURE_2D); // Disable textures to draw raw text color
        gl.glColor3f(0.0f, 0.0f, 0.0f); // Set Text Color to Black (R, G, B)

        // 3. Drawing
        gl.glRasterPos2d(worldX, worldY); // Place cursor
        String scoreString = "Score: " + gameScore;
        glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, scoreString);

        // 4. Cleanup
        gl.glEnable(GL.GL_TEXTURE_2D); // Re-enable textures for sprites
        gl.glColor3f(1.0f, 1.0f, 1.0f); // Reset color to white for textures
    }
    
    /*
     * KeyListener
     */    

    private void handleKeyPress() {

        if (isKeyPressed(KeyEvent.VK_SPACE) && !jumping) {
            jumpFrames = jumpValue;
            jumping = true;
        }

        if (isKeyPressed(KeyEvent.VK_P) && (time - pauseTime > 10)) {
            pause = !pause;
            pauseTime = time;
//            System.out.println(pause);
        }
    }

    private BitSet keyBits = new BitSet(256);
 
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