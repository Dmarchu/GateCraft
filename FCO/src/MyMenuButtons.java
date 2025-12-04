import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMenuButtons extends JButton implements ActionListener {
    public static int mode = -1;
    public static boolean isRunning = false;
    public Rectangle actualBounds;
    private int butnum, value, targetValue;
    private boolean isEnabled = true;
    private Timer timer;
    private String name; //Convert to locale
    private Timer timerDo;
    private ImageIcon icon,
            archivo = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\archivo.png"),
            save = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\save.png"),
            run = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\run.png"),
            del = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\delete.png"),
            undo = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\undo.png"),
            redo = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\redo.png"),
            opts = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\options.png"),
            color = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\color.png"),
            textmode = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\textmode.png"),
            gatemode = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\gatemode.png"),
            cablemode = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\cablemode.png"),
            activetext = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\activetext.png"),
            activegate = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\activegate.png"),
            activecable = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\activecable.png"),
            activerun = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\activerun.png"),
            foro = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\foro.png"),
            user = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\user.png");


    public MyMenuButtons(int butnum) {
        this.butnum = butnum;

        switch (butnum) {
            case 0:
                this.setBounds(10, 10 + 65, 30, 30);
                icon = archivo;
                name = "Archivo";
                break;
            case 1:
                this.setBounds(10, 20 + 30 + 65, 30, 30);
                icon = save;
                name = "Guardar";
                break;
            case 2:
                this.setBounds(10, 30 + 60 + 65, 30, 30);
                icon = run;
                name = "Iniciar";
                break;
            case 3:
                this.setBounds(10, 40 + 90 + 65, 30, 30);
                icon = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\disableddelete.png");
                this.setEnable(false);
                name = "Limpiar panel";
                break;
            case 4:
                this.setBounds(10, 50 + 120 + 65, 30, 30);
                icon = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\disabledundo.png");
                this.setEnable(false);
                name = "Atrás";
                break;
            case 5:
                this.setBounds(10, 60 + 150 + 65, 30, 30);
                icon = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\disabledredo.png");
                this.setEnable(false);
                name = "Volver";
                break;
            case 6:
                this.setBounds(10, 70 + 180 + 65, 30, 30);
                icon = opts;
                name = "Grilla";
                break;
            case 7:
                this.setBounds(10, 80 + 210 + 65, 30, 30);
                icon = color;
                name = "Colores";
                break;
            case 8:
                this.setBounds(10, 100 + 270 + 65, 30, 30);
                icon = cablemode;
                name = "Modo Cable";
                break;
            case 9:
                this.setBounds(10, 110 + 300 + 65, 30, 30);
                icon = gatemode;
                name = "Modo Puerta";
                break;
            case 10:
                this.setBounds(10, 120 + 330 + 65, 30, 30);
                icon = textmode;
                name = "Modo Comentario";
                break;
            case 11:
                icon = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\fullscreen.png");
                this.setBounds(230, 15, 30, 30);
                name = "Pantalla completa";
                break;
            case 12:
                icon = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\language.png");
                this.setBounds(270, 15, 30, 30);
                name = "Idioma";
                break;
            case 13:
                this.setBounds(10, 140 + 390 + 65, 30, 30);
                icon = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\rectification.png");
                name = "Rectificación";
                break;
            case 14:
                this.setBounds(1440, 15, 30, 30);
                icon = foro;
                name = "Foro";
                break;
            case 15:
                this.setBounds(1480, 15, 30, 30);
                icon = user;
                name = "Usuario";
                break;
        }

        actualBounds = this.getBounds();
        timerDo = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyFrame.buttoninfo.setLocation(50, (int) actualBounds.getY());
                if (butnum >= 11 && butnum != 13) MyFrame.buttoninfo.setLocation((int) actualBounds.getX(), 50);
                MyFrame.buttoninfo.setSize(calculateSpaceForWord(name), 35);
                MyFrame.buttoninfo.setText(name);
                MyFrame.buttoninfo.setVisible(true);
                timerDo.stop();
            }
        });

        this.setEnabled(true);
        this.setBorder(BorderFactory.createLineBorder(new Color(40, 40, 40), 1, true));
        this.setIcon(icon);
        this.setBackground(new Color(50, 50, 50));
        this.setForeground(new Color(50, 50, 50));
        this.addActionListener(this);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                timerDo.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (MyMenuButtons.mode != -1) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                MyFrame.buttoninfo.setVisible(false);
                timerDo.stop();
            }
        });
    }

    public int calculateSpaceForWord(String s) {
        return (int) Math.ceil(s.length() * 7.5);
    }

    public boolean isEnable() {
        return isEnabled;
    }

    public void setEnable(boolean b) {
        isEnabled = b;
        if (isEnabled) {
            switch (butnum) {
                case 3:
                    setIcon(del);
                    break;
                case 4:
                    setIcon(undo);
                    break;
                case 5:
                    setIcon(redo);
                    break;
            }
        } else {
            switch (butnum) {
                case 3:
                    setIcon(new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\disableddelete.png"));
                    break;
                case 4:
                    setIcon(new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\disabledundo.png"));
                    break;
                case 5:
                    setIcon(new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\disabledredo.png"));
                    break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isEnabled) {
            switch (this.butnum) {
                case 0:
                    MyFrame.panel.leerArchivo();
                    break;
                case 1:
                    MyFrame.panel.descargarArchivo();
                    break;
                case 2:
                    if (icon.equals(run)) {
                        icon = activerun;
                        isRunning = true;
                        MyFrame.panel.runCircuito();
                    } else {
                        icon = run;
                        isRunning = false;
                    }
                    this.setIcon(icon);
                    delWarning();
                    break;
                case 3:
                    MyFrame.panel.clear(0);
                    MyFrame.panel.repaint(); //Añadir sonido papelera
                    break;
                case 4:
                    MyFrame.panel.KeyAction();
                    MyFrame.panel.repaint();
                    break;
                case 5:
                    MyFrame.panel.KeyAction2();
                    MyFrame.panel.repaint();
                    break;
                case 6:
                    if (MyFrame.grid.getIcon().equals(MyFrame.grid2)) {
                        MyFrame.grid.setIcon(MyFrame.grid1);
                    } else if (MyFrame.grid.isVisible()) {
                        MyFrame.grid.setVisible(false);
                    } else {
                        MyFrame.grid.setIcon(MyFrame.grid2);
                        MyFrame.grid.setVisible(true);
                    }
                    break;
                case 7:
                    JColorChooser colorChooser = new JColorChooser(Color.BLACK);
                    JOptionPane.showConfirmDialog(null, colorChooser, Locales.SELEC_COLOR, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    MyFrame.thiscolor = colorChooser.getColor();
                    break;
                case 8:
                    if (icon.equals(activecable)) {
                        mode = -1;
                        icon = cablemode;
                    } else {
                        mode = 0;
                        icon = activecable;
                        runWarning();
                    }
                    this.setIcon(icon);
                    MyFrame.botone.get(9).setIcon(gatemode);
                    MyFrame.botone.get(9).newIcon(gatemode);
                    MyFrame.botone.get(10).setIcon(textmode);
                    MyFrame.botone.get(10).newIcon(textmode);
                    break;
                case 9:
                    if (icon.equals(activegate)) {
                        mode = -1;
                        icon = gatemode;
                        setScrollPaneVisibility(false);
                    } else {
                        mode = 1;
                        icon = activegate;
                        setScrollPaneVisibility(true);
                    }
                    MyFrame.botone.get(8).setIcon(cablemode);
                    MyFrame.botone.get(8).newIcon(cablemode);
                    this.setIcon(icon);
                    MyFrame.botone.get(10).setIcon(textmode);
                    MyFrame.botone.get(10).newIcon(textmode);
                    break;
                case 10:
                    if (icon.equals(activetext)) {
                        mode = -1;
                        icon = textmode;
                    } else {
                        mode = 2;
                        icon = activetext;
                        runWarning();
                    }
                    MyFrame.botone.get(8).setIcon(cablemode);
                    MyFrame.botone.get(8).newIcon(cablemode);
                    MyFrame.botone.get(9).setIcon(gatemode);
                    MyFrame.botone.get(9).newIcon(gatemode);
                    this.setIcon(icon);
                    break;
                case 11:
                    if (MyFrame.sideBar.getX() == -72) {
                        value = -1;
                        targetValue = 0;
                    } else {
                        value = 1;
                        targetValue = -72;
                    }
                    timer = new Timer(0, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (MyFrame.sideBar.getX() == targetValue) {
                                timer.stop();
                            } else {
                                for (int i = 0; i <= 13; i++)
                                    if (i != 11 && i != 12)
                                        MyFrame.botone.get(i).setLocation(MyFrame.botone.get(i).getX() - value, MyFrame.botone.get(i).getY());
                                MyFrame.sideBar.setLocation(MyFrame.sideBar.getX() - value, MyFrame.sideBar.getY());
                                MyFrame.topBar.setLocation(MyFrame.topBar.getX(), MyFrame.topBar.getY() - value);
                                MyFrame.botone.get(11).setLocation(MyFrame.botone.get(11).getX() - (value * 3), MyFrame.botone.get(11).getY());
                                MyFrame.botone.get(12).setLocation(MyFrame.botone.get(12).getX(), MyFrame.botone.get(12).getY() - value);
                                MyFrame.namer.setLocation(MyFrame.namer.getX(), MyFrame.namer.getY() - value);
                            }
                        }
                    });
                    timer.start();
                    break;
                case 12:
                    if (MyFrame.languag.isVisible()) MyFrame.languag.setVisible(false);
                    else MyFrame.languag.setVisible(true);
                    break;
                case 13:
                    MyFrame.rectif.setVisible(!MyFrame.rectif.isVisible());
                    break;
                case 14:
                    if (Main.userID != -1) {
                        if (MyFrame.foro.isVisible()) MyFrame.foro.setVisible(false);
                        else MyFrame.foro.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "¡Debes iniciar sesión para \nacceder a GateCraft Forum!", "GateCraft Forum", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case 15:
                    if (Main.sl.isVisible()) Main.sl.setVisible(false);
                    else Main.sl.setVisible(true);
                    break;
            }
        }
    }

    public void setScrollPaneVisibility(boolean b) {
        int target, step;
        if (b) {
            target = 50;
            step = 10;
            timer = new Timer(0, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (MyFrame.scrollPane.getX() == target) {
                        timer.stop();
                    } else {
                        MyFrame.scrollPane.setLocation(MyFrame.scrollPane.getX() + step, MyFrame.scrollPane.getY());
                    }
                }
            });
        } else {
            target = -(310);
            step = -10;
            timer = new Timer(0, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (MyFrame.scrollPane.getX() == target) {
                        timer.stop();
                    } else {
                        MyFrame.scrollPane.setLocation(MyFrame.scrollPane.getX() + step, MyFrame.scrollPane.getY());
                    }
                }
            });
        }
        timer.start();
    }

    public void newIcon(ImageIcon i) {
        icon = i;
    }

    public static void setMode(int num) {
        MyMenuButtons.mode = num;
    }

    public void runWarning() {
        if (isRunning) {
            targetValue = 55;
            value = 3;
            timer = new Timer(0, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (MyFrame.runWarningPanel.getX() >= targetValue) {
                        timer.stop();
                    } else {
                        MyFrame.runWarningPanel.setLocation(MyFrame.runWarningPanel.getX() + value, MyFrame.runWarningPanel.getY());
                    }
                }
            });
            timer.start();
        }
    }

    public void delWarning() {
        if (MyFrame.runWarningPanel.getX() >= 55) {
            targetValue = -175;
            value = 3;
            timer = new Timer(0, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (MyFrame.runWarningPanel.getX() <= targetValue) {
                        timer.stop();
                    } else {
                        MyFrame.runWarningPanel.setLocation(MyFrame.runWarningPanel.getX() - value, MyFrame.runWarningPanel.getY());
                    }
                }
            });
            timer.start();
        }
    }
}
