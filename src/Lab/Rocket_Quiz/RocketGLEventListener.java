package Lab.Rocket_Quiz;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class RocketGLEventListener implements GLEventListener {
    GL gl;
    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glViewport(0, 0, 300, 300);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        // Set top-left origin (notice the flipped top and bottom)
        gl.glOrtho(-150.0, 150.0, -300.0, 300.0, -1.0, 1.0);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl.glColor3f(1.0f, 0.0f, 0.0f);
        Square(-40, 0, 40, 0, 40, 150, -40, 150);
        Square(-40, -155, 40, -155, 40, -5, -40, -5);
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        Square(-40, -5, 40, -5, 40, 0, -40, 0);

        gl.glColor3f(0.298f, 0.898f, 1.0f);
        Circle(0, 100, 25);
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        Circle(0, -115, 15);

        gl.glColor3f(0.4f, 0.54f, 1.0f);
        Triangle(-40, -155, -100, -155, -40, -80);
        Triangle(40, -155, 100, -155, 40, -80);

        gl.glColor3f(1.0f, 0.639f, 0.254f);
        Circle(0, -240, 25);

        gl.glColor3f(0.95f, 1.0f, 0.25f);
        Triangle(-40, 150, 40, 150, 0, 250);
        Triangle(-20, -155, 20, -155, 0, -230);

    }

    public void CircleLoop(int x_center, int y_center, int radius) {
        double THREE_SIXTY = Math.PI * 2;
        double Step = Math.PI / 180;

        for (double i = 0; i < THREE_SIXTY; i += Step) {
            int x = (int) (radius * Math.cos(i)) +  x_center;
            int y = (int) (radius * Math.sin(i)) +  y_center;
            gl.glVertex2d(x, y);
        }
    }

    public void Circle(int x_center, int y_center, int radius) {
        gl.glBegin(GL.GL_POLYGON);
        CircleLoop(x_center, y_center, radius);
        gl.glEnd();
    }

    public void Triangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        gl.glBegin(GL.GL_POLYGON);

        gl.glVertex2d(x1, y1);
        gl.glVertex2d(x2, y2);
        gl.glVertex2d(x3, y3);

        gl.glEnd();
    }

    public void Square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        gl.glBegin(GL.GL_POLYGON);

        gl.glVertex2d(x1, y1);
        gl.glVertex2d(x2, y2);
        gl.glVertex2d(x3, y3);
        gl.glVertex2d(x4, y4);

        gl.glEnd();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }
}
