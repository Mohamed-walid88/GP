package Lab.Task2_SlidingBall;

import com.sun.opengl.util.FPSAnimator;

import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SlidingBallApp extends JFrame implements KeyListener{

    GLCanvas glcanvas;
    FPSAnimator animator;
    SlidingBallGLEventListener listener = new SlidingBallGLEventListener();

    /**
     *
     */
    public static void main(String[] args) {
        new SlidingBallApp().animator.start();
    }

    public SlidingBallApp() {
        super("Sliding Ball Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(this);
        animator = new FPSAnimator(glcanvas, 60);

        add(glcanvas, BorderLayout.CENTER);
        setSize(700, 700);
        setLocationRelativeTo(this);
        glcanvas.setFocusable(true);
        glcanvas.requestFocus();
        setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                System.out.println("KEY UP");
                listener.setDirection(0);
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("KEY DOWN");
                listener.setDirection(1);
                break;
            case KeyEvent.VK_RIGHT:
                listener.setDirection(2);
                break;
            case KeyEvent.VK_LEFT:
                listener.setDirection(3);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

