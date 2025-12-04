import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MyInstallationFrame extends JFrame {
    private int result = -1;
    public static String path = System.getenv("ProgramFiles");

    public MyInstallationFrame() {
        setSize(600, 400);
        setTitle("Gatecraft: setup");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setBackground(new Color(40, 40, 40));
        setLayout(null);
        setIconImage(new ImageIcon("C:\\Users\\david\\Desktop\\FCOPITO\\FCO\\src\\media\\logo.png").getImage());
        setResizable(false);

        JPanel topInstall = new JPanel();
        topInstall.setBackground(new Color(40, 40, 40));
        topInstall.setVisible(true);
        topInstall.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 2, true));
        topInstall.setBounds(0, 0, 600, 60);
        topInstall.setLayout(null);
        add(topInstall);

        JLabel topLogo = new JLabel();
        topLogo.setIcon(new ImageIcon("C:\\Users\\david\\Desktop\\FCOPITO\\FCO\\src\\media\\logo2.png"));
        topLogo.setBounds(10, 2, 300, 60);
        topLogo.setVisible(true);
        topInstall.add(topLogo);

        JLabel topText = new JLabel("[SetUp]");
        topText.setFont(new Font("Verdana", Font.BOLD, 12));
        topText.setForeground(new Color(31, 146, 241));
        topText.setBounds(215, 30, 100, 20);
        topInstall.add(topText);

        JPanel bodyInstall = new JPanel();
        bodyInstall.setBounds(0, 60, 600, 340);
        bodyInstall.setBackground(new Color(50, 50, 50));
        bodyInstall.setLayout(null);
        add(bodyInstall);

        JLabel bodyThanks = new JLabel("<html>" +
                "<div style='text-align: center;'>" +
                "Gracias por elegir Gatecraft, es un placer servirle de ayuda.<br>" +
                "Para utilizar el programa deberá proceder con la instalación.<br>" +
                "Elija un directorio donde almacenarlo." +
                "</div>" +
                "</html>");
        bodyThanks.setBounds(130, 0, 400, 150);
        bodyThanks.setVisible(true);
        bodyThanks.setBackground(bodyInstall.getBackground());
        bodyThanks.setForeground(Color.white);
        bodyInstall.add(bodyThanks);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getenv("ProgramFiles")));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Solo permitir seleccionar directorios

        JTextField pathField = new JTextField(System.getenv("ProgramFiles"));
        pathField.setBounds(90, 150, 375, 35);
        pathField.setBackground(new Color(35, 35, 20));
        pathField.setForeground(Color.white);
        bodyInstall.add(pathField);

        JButton pathButton = new JButton();
        pathButton.setBounds(465, 150, 35, 35);
        pathButton.setBackground(new Color(60, 60, 60));
        pathButton.setIcon(new ImageIcon("C:\\Users\\david\\Desktop\\FCOPITO\\FCO\\src\\media\\archivo.png"));
        pathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    result = fileChooser.showDialog(null, "Aceptar");
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        pathField.setText(selectedFile.getAbsolutePath());
                        path = pathButton.getText();
                    }
                } catch (IllegalArgumentException e1) {
                    e1.printStackTrace();
                }
            }
        });
        bodyInstall.add(pathButton);

        JButton installButton = new JButton("Instalar"); installButton.setBounds(450,250,100,25);
        bodyInstall.add(installButton);

        repaint();
    }
}
