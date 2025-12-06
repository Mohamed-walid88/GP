package Lab.Lab_Task2;

import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestApp extends JFrame implements ActionListener {
    GLCanvas glcanvas = new GLCanvas();
    TestGLEventListener listener =  new TestGLEventListener();

    public TestApp() {
        setTitle("Boat task");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        glcanvas.addGLEventListener(listener);
        add(glcanvas, BorderLayout.CENTER);

        JPanel panel = new JPanel();

        JButton MoveRight = new JButton("Move Right");
        MoveRight.addActionListener(this);
        MoveRight.setActionCommand("MoveRight");

        JButton MoveLeft = new JButton("Move Left");
        MoveLeft.addActionListener(this);
        MoveLeft.setActionCommand("MoveLeft");

        JButton Rotate =  new JButton("Rotate");
        Rotate.addActionListener(this);
        Rotate.setActionCommand("Rotate");

        JButton ZoomIn = new JButton("ZoomIn");
        ZoomIn.addActionListener(this);
        ZoomIn.setActionCommand("ZoomIn");

        JButton ZoomOut = new JButton("ZoomOut");
        ZoomOut.addActionListener(this);
        ZoomOut.setActionCommand("ZoomOut");

        panel.setLayout(new FlowLayout());
        panel.add(MoveRight);
        panel.add(MoveLeft);
        panel.add(Rotate);
        panel.add(ZoomIn);
        panel.add(ZoomOut);
        add(panel, BorderLayout.SOUTH);

        setLocationRelativeTo(this);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "MoveRight":
                listener.ChangeMove(10);
                glcanvas.repaint();
                break;
            case "MoveLeft":
                listener.ChangeMove(-10);
                glcanvas.repaint();
                break;
            case "ZoomIn":
                listener.AddZoom(0.1f);
                glcanvas.repaint();
                break;
            case "ZoomOut":
                listener.AddZoom(-0.1f);
                glcanvas.repaint();
                break;
            case "Rotate":
                listener.AddRotate(5f);
                glcanvas.repaint();
                break;

        }
    }

    public static void main(String[] args) {
        new TestApp();
    }
}
