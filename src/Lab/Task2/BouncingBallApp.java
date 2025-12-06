package Lab.Task2;

import com.sun.opengl.util.FPSAnimator;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BouncingBallApp extends JFrame implements KeyListener{

    private FPSAnimator animator;
    private GLCanvas glcanvas;
    private BouncingBallGLEventListener listener = new BouncingBallGLEventListener();

    public static void main(String[] args) {
        new BouncingBallApp().animator.start();
    }

    public BouncingBallApp() {
        super("Bouncing Lab.Ball");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(this);
        animator = new FPSAnimator(glcanvas, 60);

        add(glcanvas, BorderLayout.CENTER);
        setSize(700, 700);
        setLocationRelativeTo(this);
        setVisible(true);
        glcanvas.requestFocusInWindow();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_MINUS:
                listener.decreaseSpeed();
                break;
            case KeyEvent.VK_ADD:
//                System.out.println(KeyEvent.VK_PLUS);
                listener.increaseSpeed();
                break;
            case KeyEvent.VK_R:
                listener.restartBall();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

class BouncingBallGLEventListener implements GLEventListener {
    // UP = 0, Down = 1, Right = 2, Left = 3
    // Up_right = 4, Up_left = 5, Down_right = 6, Down_left = 7
    private final int OPP_UP = 1;
    private final int OPP_DOWN = 0;
    private final int OPP_RIGHT = 3;
    private final int OPP_LEFT = 2;

    private final int OPP_UP_RIGHT = 7;
    private final int OPP_UP_LEFT = 6;
    private final int OPP_DOWN_RIGHT = 5;
    private final int OPP_DOWN_LEFT = 4;

    private final double X_MIN = -350.0;
    private final double X_MAX = 350.0;
    private final double Y_MIN = -350.0;
    private final double Y_MAX = 350.0;
    private final int NUMBER_OF_DIRECTIONS = 8;
    private final double ONE_DEGREE = (Math.PI / 180);
    private final double THREE_SIXTY = 2 * Math.PI;
    private double ballRadius;
    private double BallSpeed = 1;
    private double BallX = 0;
    private double BallY = 0;
    private int ballDirection;


    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glOrtho(X_MIN, X_MAX, Y_MIN, Y_MAX, -1.0, 1.0);

        ballRadius = 30;
        play();
    }

    // UP = 0, Down = 1, Right = 2, Left = 3
    // Up_right = 4, Up_left = 5, Down_right = 6, Down_left = 7
    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        drawBall(gl, BallX, BallY);

        if (ballDirection == 0) {
            BallY += BallSpeed;
        }
        else if (ballDirection == 1) {
            BallY -= BallSpeed;
        }
        else if (ballDirection == 2) {
            BallX += BallSpeed;
        }
        else if (ballDirection == 3) {
            BallX -= BallSpeed;
        }
        else if (ballDirection == 4) {
            BallX += BallSpeed;
            BallY += BallSpeed;
        }
        else if (ballDirection == 5) {
            BallX -= BallSpeed;
            BallY += BallSpeed;
        }
        else if (ballDirection == 6) {
            BallX += BallSpeed;
            BallY -= BallSpeed;
        }
        else if (ballDirection == 7) {
            BallX -= BallSpeed;
            BallY -= BallSpeed;
        }
        updateBallPosition();
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
        for (double a = 0; a < THREE_SIXTY; a += ONE_DEGREE) {
            double x = xBall + ballRadius * (Math.cos(a));
            double y = yBall + ballRadius * (Math.sin(a));
            gl.glVertex2d(x, y);
        }
        gl.glEnd();
    }

    private void updateBallPosition() {
        if (BallX > X_MAX - ballRadius && BallY > Y_MAX - ballRadius) {
            ballDirection = OPP_UP_RIGHT;

        }
        else if (BallX < X_MIN + ballRadius && BallY > Y_MAX - ballRadius) {
            ballDirection = OPP_UP_LEFT;
        }
        else if (BallX < X_MIN + ballRadius && BallY < Y_MIN + ballRadius) {
            ballDirection = OPP_DOWN_LEFT;
        }
        else if (BallX > X_MAX - ballRadius && BallY < Y_MIN + ballRadius) {
            ballDirection = OPP_DOWN_RIGHT;
        }
        else if (BallY >  Y_MAX - ballRadius) {
            ballDirection = OPP_UP;
        }
        else if (BallY < Y_MIN + ballRadius) {
            ballDirection = OPP_DOWN;
        }
        else if (BallX < X_MIN + ballRadius) {
            ballDirection = OPP_LEFT;
        }
        else if (BallX > X_MAX - ballRadius) {
            ballDirection = OPP_RIGHT;
        }
    }

    private void play() {
        ballDirection = (int) (Math.random() * NUMBER_OF_DIRECTIONS);
    }

    public void decreaseSpeed() {
        if (BallSpeed >= 5)
            BallSpeed -= 5;
    }
    public void increaseSpeed() {
        BallSpeed += 5;
    }
    public void restartBall() {
        BallX = 0;
        BallY = 0;
        play();
    }
}

