package Lab.MouseBall;

import com.sun.opengl.util.FPSAnimator;

import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class mouseBallApp extends JFrame implements MouseListener, MouseMotionListener {

    GLCanvas glcanvas = new GLCanvas();
    mouseBallGLEventListener listener = new mouseBallGLEventListener();
    FPSAnimator animator = new FPSAnimator(glcanvas, 30);

    public mouseBallApp() {
        setTitle("Mouse Lab.Ball");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        glcanvas.addGLEventListener(listener);
        glcanvas.addMouseMotionListener(this);
        add(glcanvas, BorderLayout.CENTER);
        animator.start();
        setLocationRelativeTo(this);
        glcanvas.requestFocusInWindow();
        setVisible(true);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int width = glcanvas.getWidth();
        int height = glcanvas.getHeight();

        listener.setX(e.getX(), width, height);
        listener.setY(e.getY(), width, height);
    }

    public static void main(String[] args) {
        new mouseBallApp();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }
}
