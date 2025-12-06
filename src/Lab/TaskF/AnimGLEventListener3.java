package Lab.TaskF;


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

    int animationIndex = 0, monsterIndex = 5, ballIndex = 6;
    int maxWidth = 100;
    int maxHeight = 100;
    int solderX = 10, pervSolderX = 0, solderY = 90, monsterX = 90, monsterY = 10;
    boolean rightSolder = true, rightMonster = false;
    int SolderAngle = -90;
    List<Integer> ballY = new ArrayList<>();
    List<Integer> ballX = new ArrayList<>();
    List<Integer> ballDirection = new ArrayList<>();
    
    // Download enemy textures from https://craftpix.net/freebies/free-monster-2d-game-items/
    String textureNames[] = {"Man1.png","Man2.png","Man3.png","Man4.png","Back.png", "11.png", "Balloon1.png"};
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
        
        DrawBackground(gl, 4);
        handleKeyPress();
        solderX += (rightSolder ? 1 : -1);
        monsterX += (rightMonster ? -1 : 1);
        animationIndex++;
        animationIndex = animationIndex % 4;
        if (solderX > 90 || solderX < 10) {
            rightSolder = !rightSolder;
            SolderAngle *= -1;
        }
        if (monsterX > 90 || monsterX < 10) {
            rightMonster = !rightMonster;
        }
        
        
//        DrawGraph(gl);
        DrawSprite(gl, solderX, solderY, animationIndex, 1, SolderAngle);
        DrawSprite(gl, monsterX, monsterY, monsterIndex, 1, 0);
        updateBalls(gl);
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
    
    public void DrawSprite(GL gl,int x, int y, int index, float scale, float angle){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On

        gl.glPushMatrix();
            gl.glTranslated( x/(maxWidth/2.0) - 0.9, y/(maxHeight/2.0) - 0.9, 0);
            gl.glScaled(0.1*scale, 0.1*scale, 1);
            gl.glRotated(angle, 0, 0, 1);
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

    private void updateBalls(GL gl){
        if (monsterX % 5 == 0) {
            ballY.add(monsterY);
            ballX.add(monsterX);
            ballDirection.add(1);
        }
        for (int i = 0; i < ballY.size(); i++) {
            ballY.set(i, ballY.get(i) + ballDirection.get(i));
            if (ballY.get(i) > maxHeight - 5) {
                ballDirection.set(i, ballDirection.get(i) * -1);
            }
            if ((ballY.get(i) < maxHeight / 2 && ballDirection.get(i) == -1) ||
                    ((ballX.get(i) >= solderX - 5 && ballX.get(i) <= solderX + 5) && ballY.get(i) >= solderY)) {
                ballY.remove(i);
                ballX.remove(i);
                ballDirection.remove(i);
                i--;
            }
            else
                DrawSprite(gl, ballX.get(i), ballY.get(i), ballIndex, 0.5f, 0);
        }
    }
    
    /*
     * KeyListener
     */    

    public void handleKeyPress() {

        if (isKeyPressed(KeyEvent.VK_SPACE) && Math.abs(solderX - pervSolderX) > 2) {
            rightSolder = (!rightSolder);
            SolderAngle *= -1;
            pervSolderX = solderX;
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
