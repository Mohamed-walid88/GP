package Lab.ShipsAttack;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class ShipsGLEventListener implements GLEventListener {
    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClearColor(0.2f, 0.827f, 1.0f, 1.0f);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-400.0, 400.0, -250.0, 250.0, -1.0, 1.0);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        setHexColor(gl, "#006994");
        Square(gl, -400, -250, 400, -250, 400, 0, -400, 0);

        setHexColor(gl, "#914800");

    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }


    // ---------------- Helper Methods ------------------
    private void setHexColor(GL gl, String hex) {
        if(hex.length() < 7) {
            System.err.println("Hex string length is less than 7 Characters");
            return;
        }
        try {
            int r =  Integer.parseInt(hex.substring(1, 3), 16);
            int g = Integer.parseInt(hex.substring(3, 5), 16);
            int b = Integer.parseInt(hex.substring(5, 7), 16);
            gl.glColor3f(r / 255f,g / 255f,b /  255f);
        }catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void Square(GL gl, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        gl.glBegin(GL.GL_POLYGON);

        gl.glVertex2d(x1, y1);
        gl.glVertex2d(x2, y2);
        gl.glVertex2d(x3, y3);
        gl.glVertex2d(x4, y4);

        gl.glEnd();
    }

    private void QuarterCircle(GL gl, int xCenter, int yCenter, int radius) {
        gl.glBegin(GL.GL_POLYGON);
        double Quarter = Math.PI / 2;
        double Step = Math.PI / 180.0;

        for (double i = 0; i < Quarter; i += Step) {
            int x = (int) (radius * Math.cos(i)) + xCenter;
            int y = (int) (radius * Math.sin(i)) + yCenter;
            gl.glVertex2d(x, y);
        }
        gl.glEnd();
    }
}
