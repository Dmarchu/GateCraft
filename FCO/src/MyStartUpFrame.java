import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class MyStartUpFrame extends JFrame {
    public MyStartUpFrame() {
        setUndecorated(true);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);

        Timer timer = new Timer(3000, e -> {
            this.dispose();
            Main.frame.setVisible(true);
        });

        timer.start();

        JLabel back = new JLabel();
        back.setBounds(0, 0, 600, 400);
        back.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\2024-1-starup.png").getImage().getScaledInstance(back.getWidth(), back.getHeight(), Image.SCALE_SMOOTH)));

        getContentPane().setLayout(null);
        getContentPane().add(back);

        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Shape roundRect = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20);
        g2d.setColor(Color.WHITE);
        g2d.fill(roundRect);

        super.paint(g2d);

        g2d.dispose();
    }
}