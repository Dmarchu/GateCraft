import javax.swing.*;
import java.awt.*;

public class MyRectPanel extends JPanel {
    private JSpinner help;
    public static int rectification;
    public int rectificationMem;

    public MyRectPanel() {
        //Creación y configuración del elemento "Rectificación"
        JButton helpl = new JButton();
        helpl.setForeground(Color.white);
        helpl.setBackground(new Color(50, 50, 50));
        helpl.setFocusable(false);
        helpl.setText("ON");
        helpl.setFont(new Font("Verdana", Font.BOLD, 9));
        helpl.setBounds(0, 0, 80, 40);
        helpl.addActionListener(e -> {
            if (helpl.getText().equals("ON")) {
                helpl.setText("OFF");
                rectificationMem = rectification;
                rectification = 0;
                help.setValue(0);
            } else {
                helpl.setText("ON");
                help.setValue(rectificationMem);
                rectification = rectificationMem;
            }
        });

        help = new JSpinner(new SpinnerNumberModel());
        help.setBounds(80, 0, 40, 40);
        help.setFocusable(false);
        help.addChangeListener(e -> {
            //Comprueba si el número de la rectificación es válido
            if (Integer.parseInt(help.getValue().toString()) < 0) help.setValue(0);
            if (Integer.parseInt(help.getValue().toString()) > 100) help.setValue(100);
            rectification = Integer.parseInt(help.getValue().toString());
        });

        //Valor inicial de la rectificación
        help.setValue(15);
        rectification = 15;

        this.setBounds(50, 140 + 390 + 65, 120, 40);
        this.setVisible(false);
        this.setLayout(null);
        this.setBackground(new Color(50, 50, 50));
        this.setForeground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 2, true));
        this.add(helpl);
        this.add(help);
    }
}
