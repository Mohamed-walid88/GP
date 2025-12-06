package Lab.Task1_Solar_System_V2;

import com.sun.opengl.util.FPSAnimator;

import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SolarApp extends JFrame implements ActionListener {
    SolarGLEventListener listener =  new SolarGLEventListener();
    GLCanvas glcanvas = new GLCanvas();
    FPSAnimator animator = new FPSAnimator(glcanvas, 30);
    JLabel label = new JLabel("Solar System");

    SolarApp() {
        setTitle("Solar System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        glcanvas.addGLEventListener(listener);
        add(glcanvas, BorderLayout.CENTER);

        animator.start();

        JPanel SouthPanel = new JPanel();
        JPanel NorthPanel = new JPanel();

        JButton RotateClockWise = new JButton("Clockwise");
        RotateClockWise.addActionListener(this);
        RotateClockWise.setActionCommand("Rotate Clockwise");

        JButton RotateAntiClockWise = new JButton("anti-clockwise");
        RotateAntiClockWise.addActionListener(this);
        RotateAntiClockWise.setActionCommand("Rotate AntiClockwise");

        JButton IncreaseRotationStep = new JButton("Step+");
        IncreaseRotationStep.addActionListener(this);
        IncreaseRotationStep.setActionCommand("Increase Rotation Step");

        JButton DecreaseRotationStep = new JButton("Step-");
        DecreaseRotationStep.addActionListener(this);
        DecreaseRotationStep.setActionCommand("Decrease Rotation Step");

        JButton ZoomIn = new JButton("Zoom In");
        ZoomIn.addActionListener(this);
        ZoomIn.setActionCommand("Zoom In");

        JButton ZoomOut = new JButton("Zoom Out");
        ZoomOut.addActionListener(this);
        ZoomOut.setActionCommand("Zoom Out");

        SouthPanel.setLayout(new FlowLayout());
        SouthPanel.add(RotateClockWise);
        SouthPanel.add(RotateAntiClockWise);
        SouthPanel.add(IncreaseRotationStep);
        SouthPanel.add(DecreaseRotationStep);
        SouthPanel.add(ZoomIn);
        SouthPanel.add(ZoomOut);
        add(SouthPanel, BorderLayout.SOUTH);
        NorthPanel.add(label,  BorderLayout.CENTER);
        add(NorthPanel, BorderLayout.NORTH);
        setLocationRelativeTo(this);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Rotate Clockwise":
                listener.setRotationDirection("Clockwise");
                glcanvas.repaint();
                break;
            case "Rotate AntiClockwise":
                listener.setRotationDirection("anti-clockwise");
                glcanvas.repaint();
                break;
            case "Increase Rotation Step":
                listener.ChangeRotationStep(1);
                label.setText("Current Rotation Step: " + listener.getRotationStep());
                break;
            case "Decrease Rotation Step":
                listener.ChangeRotationStep(-1);
                label.setText("Current Rotation Step: " + listener.getRotationStep());
                break;
            case "Zoom In":
                listener.ChangeZoomDegree(0.1f);
                glcanvas.repaint();
                break;
            case "Zoom Out":
                listener.ChangeZoomDegree(-0.1f);
                glcanvas.repaint();
                break;
        }
    }

    public static void main(String[] args) {
        new SolarApp();
    }
}