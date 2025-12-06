package Lab.ShipsAttack;

import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;

public class ShipsApp extends JFrame{

    GLCanvas glcanvas = new GLCanvas();
    ShipsGLEventListener listener = new ShipsGLEventListener();

    public ShipsApp() {
        setTitle("Ships Attack");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        glcanvas.addGLEventListener(listener);
        add(glcanvas, BorderLayout.CENTER);
        setLocationRelativeTo(this);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ShipsApp();
    }
}
