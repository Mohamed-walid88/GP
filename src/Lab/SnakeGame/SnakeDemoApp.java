package Lab.SnakeGame;

import com.sun.opengl.util.FPSAnimator;
import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SnakeDemoApp extends JFrame implements KeyListener {
    GLCanvas glcanvas = new GLCanvas();
    SnakeDemoGLEventListener listener = new SnakeDemoGLEventListener();
    FPSAnimator animator = new FPSAnimator(glcanvas, 60);


    public SnakeDemoApp() {
        setTitle("Snake Game (Demo)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);

        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(this);

        add(glcanvas, BorderLayout.CENTER);

        animator.start();

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
                listener.setDirectionUp();
                break;
            case KeyEvent.VK_DOWN:
                listener.setDirectionDown();
                break;
            case KeyEvent.VK_LEFT:
                listener.setDirectionLeft();
                break;
            case KeyEvent.VK_RIGHT:
                listener.setDirectionRight();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        new SnakeDemoApp();
    }
}