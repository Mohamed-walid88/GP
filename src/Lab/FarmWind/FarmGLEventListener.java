package Lab.FarmWind;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class FarmGLEventListener implements GLEventListener {
    private float ZoomDegree = 1;
    private float RotateDegree = 10;
    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();


        gl.glClearColor(0.2f, 0.827f, 1.0f, 1.0f);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glOrtho(-400.0, 400.0, -225.0, 225.0, -1.0, 1.0);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        if(ZoomDegree < 0) ZoomDegree = 0;

        gl.glColor3f(1.0f, 0.925f, 0.133f);
        Square(gl, -400, -225, 400, -225, 400, 0, -400, 0);

        gl.glPushMatrix();
        gl.glScalef(ZoomDegree, ZoomDegree, 1);
        Cloud(gl);

        gl.glColor3f(0.568f, 0.284f, 0.0f);
        Square(gl, -40, -80, 40, -80, 20, 0, -20, 0);

        gl.glColor3f(1.0f, 0.0f, 0.0f);
        Circle(gl, 0, 0, 20);

        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glPushMatrix();
        gl.glRotatef(RotateDegree, 0f, 0f, 1f);
        Square(gl, -6, 25, 6, 25, 10, 85, -10, 85);
        Square(gl, -25, -6, -25, 6, -85, 10, -85, -10);
        Square(gl, 25, -6, 25, 6, 85, 10, 85, -10);
        Square(gl, -6, -30, 6, -30, 10, -90, -10, -90);
        gl.glPopMatrix();

        gl.glPopMatrix();


    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }

    private void CircleLoop(GL gl, int x_center, int y_center, int radius) {
        double THREE_SIXTY = Math.PI * 2;
        double Step = Math.PI / 180;

        for (double i = 0; i < THREE_SIXTY; i += Step) {
            int x = (int) (radius * Math.cos(i)) +  x_center;
            int y = (int) (radius * Math.sin(i)) +  y_center;
            gl.glVertex2d(x, y);
        }
    }

    private void Circle(GL gl, int x_center, int y_center, int radius) {
        gl.glBegin(GL.GL_POLYGON);
        CircleLoop(gl, x_center, y_center, radius);
        gl.glEnd();
    }

    public void Square(GL gl, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        gl.glBegin(GL.GL_POLYGON);

        gl.glVertex2d(x1, y1);
        gl.glVertex2d(x2, y2);
        gl.glVertex2d(x3, y3);
        gl.glVertex2d(x4, y4);

        gl.glEnd();
    }

    private void Cloud(GL gl) {
        gl.glColor3f(1.0f, 1.0f, 1.0f);

        gl.glPushMatrix();
        gl.glTranslated(75, 25, 0);
        gl.glScaled(0.5f, 0.5f, 1);
        Circle(gl, 75, 175, 60);
        Circle(gl, 113, 163, 63);
        Circle(gl, 175, 163, 63);
        Circle(gl, 250, 150, 50);
        Circle(gl, 338, 165, 60);
        Circle(gl, 413, 175, 63);
        Circle(gl, 350, 225, 75);
        Circle(gl, 235, 225, 100);
        Circle(gl, 125, 225, 75);
        gl.glPopMatrix();
    }

    public void ChangeZoomDegree(float  zoomDegree) {
        this.ZoomDegree += zoomDegree;
    }
    public void ChangeRotateDegree(float rotateDegree) {
        this.RotateDegree += rotateDegree;
    }
}
