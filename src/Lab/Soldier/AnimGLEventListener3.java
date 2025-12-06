package Lab.Soldier;


import Lab.Texture.TextureReader;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class AnimGLEventListener3 extends AnimListener {

    final int BALLONS = 10;
    final int ArrowSpeed = 5;
    int animationIndex = 0;
    int maxWidth = 100;
    int maxHeight = 100;
    int x = maxWidth/2, y = maxHeight/2;
    int angle = 0;
    int[] BalloonX = new int[BALLONS];
    int[] BalloonY = new int[BALLONS];
    int[] BalloonDirection = new int[BALLONS];
    List<Arrows> Arrows = new ArrayList<>();
    
    // Download enemy textures from https://craftpix.net/freebies/free-monster-2d-game-items/
    String textureNames[] = {"Man1.png","Man2.png","Man3.png","Man4.png","Back.png", "Balloon1.png", "Arrow.png"};
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

        for (int i = 0; i < BALLONS; i++) {
            int x = (int)(Math.random()*maxWidth) + 1;
            BalloonX[i] = x;
            BalloonY[i] = 90;
            BalloonDirection[i] = 1;
        }
    }
    
    public void display(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity(); 
        
        DrawBackground(gl, 4);
        handleKeyPress();
        animationIndex = animationIndex % 4;
        
        
//        DrawGraph(gl);
        DrawSprite(gl, x, y, animationIndex, 1, angle);
        for (int i = 0; i < BALLONS; i++){
            DrawSprite(gl, BalloonX[i], BalloonY[i], 5, 1, 0);

            BalloonY[i] -= ((int)(Math.random()*5) + 1) * BalloonDirection[i];


            if (BalloonY[i] >= 100 || BalloonY[i] <= 0)
                BalloonDirection[i] *= -1;
        }


        for (int i = 0; i < Arrows.size(); i++) {
            int arrowX = Arrows.get(i).getX();
            int arrowY = Arrows.get(i).getY();
            int arrowAngle = Arrows.get(i).getAngle() + 45;

            int newX = (int) (arrowX + ArrowSpeed * Math.cos(arrowAngle));
            int newY = (int) (arrowY + ArrowSpeed * Math.sin(arrowAngle));
            Arrows.get(i).setX(newX);
            Arrows.get(i).setY(newY);
            DrawSprite(gl, newX, newY, 6, 0.5f, arrowAngle);
            if (newX > 100 || newY > 100 || newX < 0 || newY < 0) {
                Arrows.remove(i);
                i--;
            }
        }
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
    
    public void DrawSprite(GL gl,int x, int y, int index, float scale, int ang){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On

        gl.glPushMatrix();
            gl.glTranslated( x/(maxWidth/2.0) - 0.9, y/(maxHeight/2.0) - 0.9, 0);
            gl.glScaled(0.1*scale, 0.1*scale, 1);
            gl.glRotated(ang, 0, 0, 1);
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
    
    /*
     * KeyListener
     */    

    public void handleKeyPress() {

        if (isKeyPressed(KeyEvent.VK_SPACE)) {
            Arrows.add(new Arrows(x, y, angle));
//            System.out.println("new Arrow added!");
        }

        if (isKeyPressed(KeyEvent.VK_D)) {
            angle -= 45;
//            if (angle < 0) {
//                angle += 360;
//            }
//            angle %= 360;
            animationIndex++;
        }

        if (isKeyPressed(KeyEvent.VK_A)) {
//            angle += 45;
//            angle %= 360;
            animationIndex++;
        }

        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            if (x > 0) {
                x--;
            }
            animationIndex++;
        }
        if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (x < maxWidth-10) {
                x++;
            }
            animationIndex++;
        }
        if (isKeyPressed(KeyEvent.VK_DOWN)) {
            if (y > 0) {
                y--;
            }
            animationIndex++;
        }
        if (isKeyPressed(KeyEvent.VK_UP)) {
            if (y < maxHeight-10) {
                y++;
            }
            animationIndex++;
        }
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
