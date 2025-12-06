package Lecture;

import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;

public class TestApp extends JFrame {

    GLCanvas glcanvas = new GLCanvas();
    Line listener = new Line();

    public static void main(String[] args) {
        new TestApp();
    }

    public TestApp() {
        super("Test Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        glcanvas.addGLEventListener(listener);
        add(glcanvas, BorderLayout.CENTER);
        setSize(700, 700);

        // TODO logic here

        setLocationRelativeTo(this);
        setVisible(true);
    }
}