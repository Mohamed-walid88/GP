package Lab.Lab_ballTask;

import com.sun.opengl.util.FPSAnimator;
import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BallApp extends JFrame implements KeyListener{

    private FPSAnimator animator;
    private GLCanvas glcanvas;
    private BallGLEventListener listener = new BallGLEventListener();

    public static void main(String[] args) {
        new BallApp().animator.start();
    }

    public BallApp() {
        super("Lab.Ball Application");
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
            case KeyEvent.VK_UP:
                listener.setFishMoveDirection(0, true);
                break;
            case KeyEvent.VK_DOWN:
                listener.setFishMoveDirection(1, true);
                break;
            case KeyEvent.VK_RIGHT:
                listener.setFishMoveDirection(2, true);
                break;
            case KeyEvent.VK_LEFT:
                listener.setFishMoveDirection(3, true);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                listener.setFishMoveDirection(0, false);
                break;
            case KeyEvent.VK_DOWN:
                listener.setFishMoveDirection(1, false);
                break;
            case KeyEvent.VK_RIGHT:
                listener.setFishMoveDirection(2, false);
                break;
            case KeyEvent.VK_LEFT:
                listener.setFishMoveDirection(3, false);
                break;
        }
    }
}
