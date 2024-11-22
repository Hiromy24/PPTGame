import javax.swing.*;
import java.awt.*;

public class LoadingCircle extends JPanel{
    private int angle = 0;

    public LoadingCircle() {
        setPreferredSize(new Dimension(200, 200));

        Timer timer = new Timer(20, e -> {
            angle += 5;
            if (angle >= 360) angle = 0;
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.decode("#6A6DAD"));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.decode("#FCBA03"));
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = 70;

        for (int i = 0; i < 6; i++) {
            double theta = Math.toRadians(angle + i * 60);
            int x = (int) (centerX + radius * Math.cos(theta));
            int y = (int) (centerY + radius * Math.sin(theta));
            g2d.fillOval(x - 10, y - 10, 25, 25);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Puntos Rotatorios");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new LoadingCircle());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}