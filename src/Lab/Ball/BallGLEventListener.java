package Lab.Ball;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class BallGLEventListener implements GLEventListener {
    private int BallX = 0;
    private int TriangleX = 50;
    private int SquareX = 775;
    private int TransitionX = 0;
    private int Direction = 1;
    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();


        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glOrtho(-400.0, 400.0, -250.0, 250.0, -1.0, 1.0);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl.glColor3f(1.0f, 1.0f, 1.0f);
        // Draw a triangle 100 units wide, centered at TriangleX
        Triangle(gl, TriangleX - 50, -25, TriangleX + 50, -25, TriangleX, 25);
        Square(gl, SquareX, -25, SquareX + 50, -25, SquareX + 50, 25, SquareX, 25);

        gl.glColor3f(1.0f, 0.0f, 0.0f);
        Circle(gl, BallX, 0, 50);


        if (BallX == 350) {
            Direction *= -10;
            TransitionX = 50;
        }
        if (BallX == -350)  {
            if (Math.abs(Direction) > 1) Direction /= 10;

            Direction *= -1;
        }

        if (SquareX < -25)
            TransitionX = 0;

        TriangleX -= TransitionX;
        SquareX -= TransitionX;
        BallX += (5 * Direction);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }

    //------------Helper Methods -----------------
    private void CircleLoop(GL gl, int x_center, int y_center, int radius) {
        double THREE_SIXTY = Math.PI * 2;
        double Step = Math.PI / 180.0;

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

    private void Square(GL gl, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        gl.glBegin(GL.GL_POLYGON);

        gl.glVertex2d(x1, y1);
        gl.glVertex2d(x2, y2);
        gl.glVertex2d(x3, y3);
        gl.glVertex2d(x4, y4);

        gl.glEnd();
    }

    private void Triangle(GL gl, int x1, int y1, int x2, int y2, int x3, int y3) {
        gl.glBegin(GL.GL_POLYGON);

        gl.glVertex2d(x1, y1);
        gl.glVertex2d(x2, y2);
        gl.glVertex2d(x3, y3);

        gl.glEnd();
    }

    public void setBallX(int ballX) {
        BallX = ballX;
    }

    public void setDirection(int direction) {
        Direction *= direction;
    }
}
