package Lab.Task1_Transformation;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class TransformationGLEventListener implements GLEventListener {
    private final int START_Y = -500;
    private final int END_Y = 500;
    private final int START_X = -300;
    private final int END_X = 300;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glViewport(0, 0, 300, 300);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glOrtho(START_Y, END_Y, START_X, END_X, -1.0, 1.0);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();

        drawGraph(gl);
        drawPyramids(gl);
        Cloud(gl);
        Trees(gl);

//        gl.glPushMatrix();
//        gl.glScaled(0.5f, 0.5f, 1.0f);
//        gl.glTranslatef(0, -300, 0);
//        Cloud(gl);
//        gl.glPopMatrix();
//
//        gl.glPushMatrix();
//        gl.glTranslatef(0, -300, 0);
//        gl.glScaled(0.5f, 0.5f, 1.0f);
//        Cloud(gl);
//        gl.glPopMatrix();
//
//        Cloud(gl);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }

    private void drawGraph(GL gl) {
        float red;
        float green;
        float blue;
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
////////////////////
//drawing the grid
        red = 0.2f;
        green = 0.2f;
        blue = 0.2f;
        int X_axis_length = END_Y - 24;
        int Y_axis_length = END_X - 20;
        gl.glColor3f(red, green, blue);
//You may notice I'm using GL_LINES here.
//Details of glBegin() will be discussed latter.
        gl.glBegin(GL.GL_LINES);
//draw the vertical lines
        for (int y = START_Y; y <= END_Y; y += 20) {
            gl.glVertex2d(y, START_X);
            gl.glVertex2d(y, END_X);
        }
//draw the horizontal lines
        for (int x = START_X; x <= END_X; x += 20) {
            gl.glVertex2d(START_Y, x);
            gl.glVertex2d(END_Y, x);
        }
        gl.glEnd();
//////////////////////////////
// draw the x-axis and y-axis
        red = 0.0f;
        blue = 0.4f;
        gl.glColor3f(red, green, blue);
        gl.glBegin(GL.GL_LINES);
//line for y-axis
        gl.glVertex2d(0, Y_axis_length);
        gl.glVertex2d(0, -Y_axis_length);
//line for x-axis
        gl.glVertex2d(X_axis_length, 0);
        gl.glVertex2d(-X_axis_length, 0);
        gl.glEnd();
/////////////////////
// draw arrow heads
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glVertex2d(0, END_X);
        gl.glVertex2d(-10, Y_axis_length);
        gl.glVertex2d(10, Y_axis_length);
        gl.glVertex2d(0, START_X);
        gl.glVertex2d(-10, -Y_axis_length);
        gl.glVertex2d(10, -Y_axis_length);
        gl.glVertex2d(END_Y, 0);
        gl.glVertex2d(X_axis_length, -10);
        gl.glVertex2d(X_axis_length, 10);
        gl.glVertex2d(START_Y, 0);
        gl.glVertex2d(-X_axis_length, -10);
        gl.glVertex2d(-X_axis_length, 10);
        gl.glEnd();
    }

    private void Triangle(GL gl, int x1, int y1, int x2, int y2, int x3, int y3) {
        gl.glBegin(GL.GL_POLYGON);

        gl.glVertex2d(x1, y1);
        gl.glVertex2d(x2, y2);
        gl.glVertex2d(x3, y3);

        gl.glEnd();
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

    private void drawTree(GL gl) {
        gl.glColor3f(0.749f, 0.796f, 0.196f);
        Circle(gl, 40, -60, 60);
        Circle(gl, 0, -20, 60);
        Circle(gl, -40, -60, 60);

        gl.glPushMatrix();
        gl.glTranslated(0, -45, 0);
        gl.glColor3f(0.576f, 0.666f, 0.164f);
        Circle(gl, 25, -30, 40);
        Circle(gl, 0, 5, 40);
        Circle(gl, -25, -30, 40);
        gl.glPopMatrix();

        gl.glColor3f(0.568f, 0.284f, 0.0f);
        Triangle(gl, -10, -160, 10, -160, 0, -40);
        Triangle(gl, -3, -80, -3, -95, -25, -60);
        Triangle(gl, 3, -80, 3, -95, 25, -60);
    }

    private void Cloud(GL gl) {
        gl.glColor3f(1.0f, 1.0f, 1.0f);

        gl.glPushMatrix();
        gl.glTranslated(50, 50, 0);
        gl.glScaled(0.75f, 0.75f, 1);
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

    private void drawPyramids(GL gl) {
        gl.glColor3f(1.0f, 0.8f, 0.007f);
        Triangle(gl, 75, -100, 275, -100, 175, 100);

        gl.glPushMatrix();
        gl.glScaled(1.25f, 1.2f, 1.0f);
        gl.glTranslated(-180, 0, 0);
        Triangle(gl, 75, -100, 275, -100, 175, 100);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glScaled(1.5f, 1.4f, 1.0f);
        gl.glTranslated(-320, 0, 0);
        Triangle(gl, 75, -100, 275, -100, 175, 100);
        gl.glPopMatrix();
    }

    private void Trees(GL gl) {
        gl.glPushMatrix();
        gl.glScaled(0.8f, 0.8f, 1.0f);
        gl.glTranslated(485, 50, 0);
        drawTree(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glScaled(1.5f, 1.5f, 1.0f);
        gl.glTranslated(-270, -25, 0);
        drawTree(gl);
        gl.glPopMatrix();
    }
}