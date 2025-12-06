package Lab.Ball;

import com.sun.opengl.util.FPSAnimator;

import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BallApp extends JFrame implements ActionListener {
    GLCanvas glcanvas = new GLCanvas();
    BallGLEventListener listener = new BallGLEventListener();
    FPSAnimator animator = new FPSAnimator(glcanvas, 30);

    public BallApp() {
        setTitle("Ball");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        glcanvas.addGLEventListener(listener);
        add(glcanvas, BorderLayout.CENTER);

        animator.start();

        JPanel southPanel = new JPanel();
        JButton changeDirection = new JButton("Change Direction");
        changeDirection.addActionListener(this);
        changeDirection.setActionCommand("changeDirection");

        southPanel.add(changeDirection, BorderLayout.CENTER);

        add(southPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(this);
        setVisible(true);
    }

    public static void main(String[] args) {
        new BallApp();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("changeDirection")) {
            listener.setDirection(-1);
            glcanvas.repaint();
        }
    }
}
