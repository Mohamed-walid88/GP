package Lab;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class SimpleGLEventListener implements GLEventListener {
    private String whatToDraw = "";
    int move = 5;

    public void setWhatToDraw(String whatToDraw) {
        this.whatToDraw = whatToDraw;
    }
    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glViewport(0, 0, 300, 300);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        // Set top-left origin (notice the flipped top and bottom)
        gl.glOrtho(0.0, 600.0, 0.0, 300.0, -1.0, 1.0);

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        if (whatToDraw.equals("Circle")) {
            gl.glVertex3f(0.0f, 0.0f, 1.0f);

            gl.glBegin(GL.GL_POLYGON);

            int radius = 100;
            double step = (Math.PI / 720);
            double THREE_SIXTY = (Math.PI * 2);

            for (double i = 0; i < THREE_SIXTY; i += step) {
                int x = (int) (radius * Math.cos(i)) + 300 + move;
                int y = (int) (radius * Math.sin(i)) + 150;
                gl.glVertex2d(x, y);
            }

            gl.glEnd();
        }
        else if (whatToDraw.equals("Triangle")) {
            gl.glVertex3f(0.0f, 0.0f, 1.0f);
            gl.glBegin(GL.GL_POLYGON);

            gl.glVertex2i(300, 250);
            gl.glVertex2i(150, 50);
            gl.glVertex2i(450, 50);
            gl.glEnd();
        }
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }
}
