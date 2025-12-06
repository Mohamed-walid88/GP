package Lab.Rocket_Quiz;

import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;

public class App extends JFrame{
    RocketGLEventListener listener = new RocketGLEventListener();
    GLCanvas glcanvas = new GLCanvas();

    public App() {
        setTitle("Quiz Program");
        setSize(300, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        glcanvas.addGLEventListener(listener);
        add(glcanvas,  BorderLayout.CENTER);


        setLocationRelativeTo(this);
        setVisible(true);
    }
    public static void main(String[] args) {
        new Lab.Task1_GardenVally.App();
    }
}
