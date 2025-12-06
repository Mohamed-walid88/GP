package Lab.FarmWind;

import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindApp extends JFrame implements ActionListener {
    FarmGLEventListener listener = new FarmGLEventListener();
    GLCanvas glcanvas = new GLCanvas();

    public WindApp() {
        setTitle("Farm Wind");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        glcanvas.addGLEventListener(listener);
        add(glcanvas, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JPanel NorthPanel = new JPanel();

        JButton RotateClockWise = new JButton("Rotate Right");
        RotateClockWise.addActionListener(this);
        RotateClockWise.setActionCommand("RotateRight");

        JButton RotateAntiClockWise = new JButton("Rotate Left");
        RotateAntiClockWise.addActionListener(this);
        RotateAntiClockWise.setActionCommand("RotateLeft");

        JButton ZoomIn = new JButton("Zoom in");
        ZoomIn.addActionListener(this);
        ZoomIn.setActionCommand("ZoomIn");

        JButton ZoomOut = new JButton("Zoom out");
        ZoomOut.addActionListener(this);
        ZoomOut.setActionCommand("ZoomOut");

        JLabel NorthLabel = new JLabel("Farm Wind");

        panel.setLayout(new FlowLayout());
        panel.add(RotateClockWise);
        panel.add(RotateAntiClockWise);
        panel.add(ZoomIn);
        panel.add(ZoomOut);
        NorthPanel.add(NorthLabel,  BorderLayout.CENTER);

        add(NorthPanel, BorderLayout.NORTH);
        add(panel, BorderLayout.SOUTH);

        setLocationRelativeTo(this);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "RotateRight":
                listener.ChangeRotateDegree(-2f);
                glcanvas.repaint();
                break;
            case "RotateLeft":
                listener.ChangeRotateDegree(2f);
                glcanvas.repaint();
                break;
            case "ZoomIn":
                listener.ChangeZoomDegree(0.1f);
                glcanvas.repaint();
                break;
            case "ZoomOut":
                listener.ChangeZoomDegree(-0.1f);
                glcanvas.repaint();
                break;
        }
    }

    public static void main(String[] args) {
        new WindApp();
    }
}
