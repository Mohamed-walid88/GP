package Lab.MouseBall;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class mouseBallGLEventListener implements GLEventListener {

    private final double X_MIN = -350;
    private final double X_MAX = 350;
    private final double Y_MIN = -350;
    private final double Y_MAX = 350;
    private final double ballRadius = 30;
    private double currentCanvasWidth = 700;
    private double currentCanvasHeight = 700;
    private double BallX = 0;
    private double BallY = 0;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();


        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glOrtho(X_MIN, X_MAX, Y_MIN, Y_MAX, -1.0, 1.0);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl.glColor3f(1.0f, 0.0f, 0.0f);
        updateBallBounders();
        drawBall(gl, BallX, BallY);

    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }

    private void drawBall(GL gl, double xBall, double yBall) {
        gl.glColor3f(0.5f, 0.0f, 0.5f);
        gl.glBegin(GL.GL_POLYGON);
        double THREE_SIXTY = Math.PI * 2;
        double ONE_DEGREE = Math.PI / 180.0;
        for (double a = 0; a < THREE_SIXTY; a += ONE_DEGREE) {
            double x = xBall + ballRadius * (Math.cos(a));
            double y = yBall + ballRadius * (Math.sin(a));
            gl.glVertex2d(x, y);
        }
        gl.glEnd();
    }

    private void updateBallBounders() {
        if (BallX > X_MAX - ballRadius) {
            BallX = X_MAX - ballRadius;
        }
        if (BallX < X_MIN + ballRadius) {
            BallX = X_MIN + ballRadius;
        }

        if (BallY > Y_MAX - ballRadius) {
            BallY = Y_MAX - ballRadius;
        }
        if (BallY < Y_MIN + ballRadius) {
            BallY = Y_MIN + ballRadius;
        }
    }

    public void setX(double ballX, double width, double height) {
        this.currentCanvasWidth = width;
        this.currentCanvasHeight = height;
        this.BallX = centralizeX(ballX);
    }

    public void setY(double ballY, double width, double height) {
        this.currentCanvasWidth = width;
        this.currentCanvasHeight = height;
        this.BallY = centralizeY(ballY);
    }


    private double centralizeX(double ballX) {
        return (ballX / currentCanvasWidth) * (X_MAX - X_MIN) + X_MIN;
    }

    private double centralizeY(double ballY) {
        return (1.0 - (ballY / currentCanvasHeight)) * (Y_MAX - Y_MIN) + Y_MIN;
    }
}
