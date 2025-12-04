import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

//APLICAR SEGURIDAD AL MANTAIN SESION

public class MySocialLogIn extends JPanel {
    private final JTextField nameField, mailField;
    private final JPasswordField passwordField;
    private final JLabel title, mailTitle, permission;
    private final JButton mantenerSesion, register, login, enter, logout, adminbutton;

    public static boolean isLoginSelected = false;
    public static int IDsex = -1;
    public String defaultName = "", defaultMail = "";
    public String userName = "N/A", userMail = "N/A", permissionLevel = "N/A";

    public MySocialLogIn() {
        enter = new JButton("Aceptar"); enter.setBackground(new Color(40,40,40)); enter.setForeground(Color.white);
        enter.setBounds(50, 270, 200, 50); enter.setFont(new Font("Verdana", Font.BOLD, 10)); enter.setFocusable(false);
        enter.setBorder(BorderFactory.createLineBorder(Color.white, 2, true));
        MyActionListener myActionListener = new MyActionListener();
        enter.addActionListener(myActionListener);

        login = new JButton("Iniciar Sesión"); login.setBackground(new Color(40,40,40)); login.setForeground(Color.white);
        login.setBounds(50, 330, 95, 50); login.setFont(new Font("Verdana", Font.BOLD, 10)); login.setFocusable(false);
        login.setBorder(BorderFactory.createLineBorder(Color.white, 2, true)); login.addActionListener(myActionListener);

        register = new JButton("Registrarse"); register.setBackground(new Color(40,40,40)); register.setForeground(Color.blue);
        register.setBounds(155, 330, 95, 50); register.setFont(new Font("Verdana", Font.BOLD, 10)); register.setFocusable(false);
        register.setBorder(BorderFactory.createLineBorder(Color.blue, 2, true)); register.addActionListener(myActionListener);

        title = new JLabel("SocialCraft");
        title.setBounds(30,10, 240, 50);
        title.setFont(new Font("Verdana", Font.BOLD, 30));
        title.setForeground(Color.white);

        nameField = new JTextField(); nameField.setText(defaultName); nameField.setCaretColor(Color.white);
        nameField.setBounds(50,75,200,50);
        nameField.setBackground(new Color(40,40,40)); nameField.setForeground(Color.white); nameField.setFont(new Font("Verdana", Font.BOLD, 15));
        nameField.addKeyListener(new KeyAdapter() {
            @Override public void keyTyped(KeyEvent e) {if(nameField.getText().length() > 10) nameField.setText(nameField.getText().substring(0, 10));}
        });

        passwordField = new JPasswordField(); passwordField.setCaretColor(Color.white);
        passwordField.setBounds(50,140,200,50);
        passwordField.setBackground(new Color(40,40,40)); passwordField.setForeground(Color.white); passwordField.setFont(new Font("Verdana", Font.BOLD, 15));

        mailField = new JTextField(); mailField.setText(defaultMail); mailField.setCaretColor(Color.white);
        mailField.setBounds(50,205,200,50);
        mailField.setBackground(new Color(40,40,40)); mailField.setForeground(Color.white); mailField.setFont(new Font("Verdana", Font.BOLD, 15));

        nameField.setBorder(createTitledBorder("Usuario", Color.white, 2, true, new Font("Verdana", Font.BOLD, 10), TitledBorder.LEFT));
        passwordField.setBorder(createTitledBorder("Contraseña", Color.white, 2, true, new Font("Verdana", Font.BOLD, 10), TitledBorder.LEFT));
        mailField.setBorder(createTitledBorder("E-Mail", Color.white, 2, true, new Font("Verdana", Font.BOLD, 10), TitledBorder.LEFT));

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds(0,0,350,450);
        loginPanel.setBackground(new Color(50,50,50));
        loginPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray, 3, true));
        loginPanel.add(title); loginPanel.add(passwordField); loginPanel.add(mailField); loginPanel.add(nameField);
        loginPanel.add(login); loginPanel.add(register); loginPanel.add(enter);

        //Boton salir, boton mantener sesión (interruptor), mail debajo de nombre, redimensionar
        logout = new JButton("Cerrar Sesión"); logout.setBackground(new Color(40,40,40)); logout.setForeground(Color.red);
        logout.setBounds(30, 90, 95, 50); login.setFont(new Font("Verdana", Font.BOLD, 10)); logout.setFocusable(false);
        logout.setBorder(BorderFactory.createLineBorder(Color.red, 2, true)); logout.addActionListener(myActionListener);
        logout.setVisible(false);

        mantenerSesion = new JButton("Mantener sesión"); mantenerSesion.setBackground(new Color(40,40,40)); mantenerSesion.setForeground(Color.white);
        mantenerSesion.setBounds(160, 90, 105, 50); mantenerSesion.setFont(new Font("Verdana", Font.BOLD, 10)); mantenerSesion.setFocusable(false);
        mantenerSesion.setBorder(BorderFactory.createLineBorder(Color.white, 2, true)); mantenerSesion.addActionListener(myActionListener);
        mantenerSesion.setVisible(false);

        mailTitle = new JLabel(userMail); mailTitle.setBackground(new Color(40,40,40)); mailTitle.setForeground(Color.white);
        mailTitle.setBounds(30,35, 240, 50); mailTitle.setFont(new Font("Verdana", Font.BOLD, 10));
        mailTitle.setVisible(false);

        permission = new JLabel(permissionLevel); permission.setBackground(new Color(40,40,40)); permission.setForeground(Color.white);
        permission.setBounds(275,35, 160, 50); permission.setFont(new Font("Verdana", Font.BOLD, 15));
        permission.setVisible(false);

        adminbutton = new JButton("Panel"); adminbutton.setBackground(new Color(40,40,40)); adminbutton.setForeground(Color.red);
        adminbutton.setBounds(160, 85, 50, 25); adminbutton.setFont(new Font("Verdana", Font.BOLD, 8)); adminbutton.setFocusable(false);
        adminbutton.setBorder(BorderFactory.createLineBorder(Color.red, 2, true)); adminbutton.addActionListener(myActionListener);
        adminbutton.setVisible(false);

        loginPanel.add(logout); loginPanel.add(mantenerSesion); loginPanel.add(mailTitle); loginPanel.add(permission); loginPanel.add(adminbutton);

        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.setSize(310,450);
        this.setLocation(1225,55);
        this.setLayout(null);
        this.setVisible(false);
        this.add(loginPanel);

        try {
            BufferedReader fileReader = new BufferedReader(new FileReader("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\config"));
            String mantain = fileReader.readLine(); int result1 = 0, result2 = 0;
            String lastus = fileReader.readLine(); boolean valid = false; fileReader.close();
            for (int i = 0; i < mantain.length(); i++) {
                if (valid) {result1 = Integer.parseInt(String.valueOf(mantain.charAt(i))); valid = false; break;
                } else if (mantain.charAt(i) == '=') valid = true;}
            for (int i = 0; i < lastus.length(); i++) {
                if (valid) {result2 = Integer.parseInt(String.valueOf(lastus.charAt(i))); break;
                } else if (lastus.charAt(i) == '=') valid = true;}
            if (result1 == 1) {
                Statement s = Main.conexion.createStatement();
                ResultSet rs = s.executeQuery("SELECT * FROM users WHERE ID = " + result2 + ";");
                if (rs.next() && rs.getInt(6) == 1) {
                    mantenerSesion.setBorder(BorderFactory.createLineBorder(Color.green, 2, true));
                    mantenerSesion.setForeground(Color.green);
                    Main.userID = rs.getInt(1);
                    Main.userName = rs.getString(2);
                    Main.userPermission = rs.getString(4);
                    userName = rs.getString(2);
                    permissionLevel = rs.getString(4);
                    userMail = rs.getString(5);
                    Main.mantainSession = rs.getInt(6);
                    setVisible(false);
                    turnElements(false);
                }
            }
        } catch (Exception o) {o.printStackTrace();}
    }

    public static TitledBorder createTitledBorder(String title, Color color, int thickness, boolean rounded, Font font, int alignment) {
        return new TitledBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(color, thickness, rounded), title, alignment,
                TitledBorder.DEFAULT_POSITION, font, color));
    }

    public boolean isPasswordSecure(String pswd) { // ¿Barra de progresión con seguridad?
        if (pswd.length() >= 8 && pswd.length() <= 20) {
            boolean containsUpperCase = false;
            for (int i = 0; i < pswd.length(); i++) {if (pswd.charAt(i) >= 65 && pswd.charAt(i) <= 90) {containsUpperCase = true; break;}}
            if (containsUpperCase) {
                return true;
            } else JOptionPane.showMessageDialog(null, "Su contraseña debe contener alguna letra mayúscula.", "Gatecraft Security", JOptionPane.INFORMATION_MESSAGE);
        } else JOptionPane.showMessageDialog(null, "Su contraseña debe tener entre 8 y 20 caracteres.", "Gatecraft Security", JOptionPane.INFORMATION_MESSAGE);
        return false;
    }

    public void turnElements(boolean b) {
        nameField.setText("");
        nameField.setVisible(b);
        passwordField.setText("");
        passwordField.setVisible(b);
        mailField.setText("");
        mailField.setVisible(b);
        enter.setVisible(b);
        login.setVisible(b);
        register.setVisible(b);
        logout.setVisible(!b);
        mantenerSesion.setVisible(!b);
        mailTitle.setVisible(!b);
        permission.setVisible(!b);
        if (b) {
            title.setText("SocialCraft"); title.setSize(240, 50);
            adminbutton.setVisible(false);
            this.setSize(310,450);
        } else {
            title.setText(userName);
            title.setSize((int) (calculateSpaceForWord(userName) * 3.5), title.getHeight());
            mailTitle.setText(userMail);
            mailTitle.setSize(calculateSpaceForWord(userMail), mailTitle.getHeight());
            adminbutton.setLocation(mailTitle.getX() + mailTitle.getWidth(), mailTitle.getHeight());
            permission.setText("[" + permissionLevel + "]");
            permission.setLocation(title.getX() + title.getWidth(), title.getY());
            if (permissionLevel.equals("admin")) {permission.setForeground(Color.red); adminbutton.setVisible(true);}
            else {permission.setForeground(Color.white); adminbutton.setVisible(false);}
            this.setSize(500, 175);
        }
    }

    public int calculateSpaceForWord(String s) {return (int) Math.ceil(s.length() * 7.5);}

    @SuppressWarnings({"deprecation", "DuplicatedCode"})
    private class MyActionListener implements ActionListener {
        private Timer timer = null;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(register)) {
                if (!login.getBorder().equals(BorderFactory.createLineBorder(Color.white, 2, true))) {
                    login.setBorder(BorderFactory.createLineBorder(Color.white, 2, true));
                    login.setForeground(Color.white);
                }
                register.setBorder(BorderFactory.createLineBorder(Color.blue, 2, true));
                register.setForeground(Color.blue);
                timer = new Timer(0, e1 -> {
                    if (mailField.getX() >= 50) timer.stop();
                    else {
                        mailField.setLocation(mailField.getX() + 5, mailField.getY());
                        enter.setLocation(enter.getX(), enter.getY() + 1);
                        login.setLocation(login.getX(), login.getY() + 1);
                        register.setLocation(register.getX(), register.getY() + 1);
                    }
                }); timer.start();
                enter.setLocation(enter.getX(), enter.getY() + 15);
                login.setLocation(login.getX(), login.getY() + 15);
                register.setLocation(register.getX(), register.getY() + 15);
                isLoginSelected = false;
            } else if (e.getSource().equals(login)) {
                if (!register.getBorder().equals(BorderFactory.createLineBorder(Color.white, 2, true))) {
                    register.setBorder(BorderFactory.createLineBorder(Color.white, 2, true));
                    register.setForeground(Color.white);
                }
                login.setBorder(BorderFactory.createLineBorder(Color.blue, 2, true));
                login.setForeground(Color.blue);
                timer = new Timer(0, e1 -> {
                    if (mailField.getX() <= -200) timer.stop();
                    else {
                        mailField.setLocation(mailField.getX() - 5, mailField.getY());
                        enter.setLocation(enter.getX(), enter.getY() - 1);
                        login.setLocation(login.getX(), login.getY() - 1);
                        register.setLocation(register.getX(), register.getY() - 1);
                    }
                }); timer.start();
                enter.setLocation(enter.getX(), enter.getY() - 15);
                login.setLocation(login.getX(), login.getY() - 15);
                register.setLocation(register.getX(), register.getY() - 15);
                isLoginSelected = true;
            } else if (e.getSource().equals(logout)) {
                try {
                    Main.conexion.createStatement().executeUpdate("UPDATE users SET user_logged = 0 WHERE ID = " + Main.userID + ";");
                } catch (Exception o) {o.printStackTrace();}
                    Main.userID = -1;
                userName = "N/A";
                userMail = "N/A";
                turnElements(true);
            } else if (e.getSource().equals(mantenerSesion)) {
                if (Main.mantainSession == 1) {
                    mantenerSesion.setBorder(BorderFactory.createLineBorder(Color.white, 2, true));
                    mantenerSesion.setForeground(Color.white); Main.mantainSession = 0;
                } else {
                    mantenerSesion.setBorder(BorderFactory.createLineBorder(Color.green, 2, true));
                    mantenerSesion.setForeground(Color.green); Main.mantainSession = 1;
                }
                try {
                    Statement s = Main.conexion.createStatement();
                    s.executeUpdate("UPDATE users SET user_logged = " + Main.mantainSession + " WHERE ID = " + Main.userID + ";");
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\config")); bufferedWriter.flush();
                    bufferedWriter.write("mantainsession =" + Main.mantainSession); bufferedWriter.newLine();
                    bufferedWriter.write("lastuser =" + Main.userID); bufferedWriter.close();
                } catch (Exception o) {o.printStackTrace();}
            } else if (e.getSource().equals(adminbutton)) {
                Main.frame.adminPanel.setVisible(!Main.frame.adminPanel.isVisible());
                Main.frame.adminPanel.reload(null);
            } else {
                try {
                    Statement s = Main.conexion.createStatement();
                    if (isLoginSelected) {
                        String user = nameField.getText().trim(), password = passwordField.getText().trim();
                        ResultSet rs = s.executeQuery("SELECT * FROM users WHERE user_name = '" + user + "';");
                        if (rs.next()) {
                            if (rs.getString(3).equals(password)) {
                                Main.userID = rs.getInt(1);
                                Main.userName = rs.getString(2);
                                userName = rs.getString(2);
                                permissionLevel = rs.getString(4);
                                userMail = rs.getString(5);
                                Main.mantainSession = rs.getInt(6);
                                Main.sl.setVisible(false);
                                turnElements(false);
                                if (rs.getInt(6) != 1) {
                                    mantenerSesion.setBorder(BorderFactory.createLineBorder(Color.white, 2, true));
                                    mantenerSesion.setForeground(Color.white);
                                } else {
                                    mantenerSesion.setBorder(BorderFactory.createLineBorder(Color.green, 2, true));
                                    mantenerSesion.setForeground(Color.green);
                                }
                                BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\config")); writer.flush();
                                writer.write("mantainsession =" + Main.mantainSession); writer.newLine();
                                writer.write("lastuser =" + Main.userID); writer.close();
                            }
                        }
                    } else {
                        try {
                            ResultSet rs = Main.conexion.createStatement().executeQuery("SELECT * FROM users WHERE user_name = '" + nameField.getText().trim() + "';");
                            if (!rs.next()) {
                                rs = Main.conexion.createStatement().executeQuery("SELECT * FROM users WHERE user_mail = '" + mailField.getText().trim() + "';"); //añadir msg verificacion
                                if (!rs.next()) {
                                    if (isPasswordSecure(passwordField.getText().trim())) {
                                        s.executeUpdate("INSERT INTO `users` (`ID`, `user_name`, `user_password`, `user_permission`, `user_mail`, `user_logged`) " +
                                                "VALUES (NULL, '" + nameField.getText().trim() + "', '" + passwordField.getText().trim() +
                                                "', 'user', '" + mailField.getText().trim() + "', '0');");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "¡Lo sentimos! Ese correo ya está en uso", "GateCraft Forum", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "¡Lo sentimos! Ese nombre ya está en uso", "GateCraft Forum", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception o) {o.printStackTrace();}
                    }
                } catch (Exception exc) {System.out.println("\u001B[31m" + "Algo salió mal: [" + exc + "]");}
            }
        }
    }
}

class MyAdminPanel extends JPanel {
    public JPanel topBar;
    public MySidePanel sideMenu;
    public JLabel logoTop, adminTop;
    public JButton closebutton;
    public Point iniPoint = new Point(0,60);
    public ArrayList<MyAdminLabels> users = new ArrayList<>();
    public Timer timer;

    public MyAdminPanel() {
        topBar = new JPanel();
        logoTop = new JLabel(); adminTop = new JLabel("[admin]"); closebutton = new JButton(); closebutton.setFocusable(false);
        closebutton.setBounds(1150, 19, 22,22); closebutton.setIcon(new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\cross.png"));
        closebutton.addActionListener(e1 -> {
            timer = new Timer(200, e2 -> {
                closebutton.setIcon(new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\cross.png")); this.setVisible(false); timer.stop();
            }); closebutton.setIcon(new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\activecross.png")); timer.start();
        });
        topBar.setBounds(0,0,1200,50); topBar.setVisible(true);
        topBar.setBackground(new Color(50,50,50)); topBar.add(logoTop); topBar.setLayout(null); closebutton.setBackground(topBar.getBackground());
        logoTop.setIcon(new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\minilogo2.png")); logoTop.setBounds(7,7,140,40);
        topBar.setBorder(BorderFactory.createLineBorder(new Color(80,80,80), 2, true));
        adminTop.setFont(new Font("Verdana", Font.BOLD, 8)); adminTop.setForeground(Color.red);
        adminTop.setBounds(147, 25, 80, 15); topBar.add(adminTop); topBar.add(closebutton); this.add(topBar);

        sideMenu = new MySidePanel(); this.add(sideMenu);

        reload(null);

        this.setLayout(null);
        this.setBackground(new Color(40,40,40));
        this.setVisible(false);
    }

    public void reload(String search) {
        for (MyAdminLabels user : users) this.remove(user);
        users.clear();
        iniPoint = new Point(0,50);
        try {
            Statement s = Main.conexion.createStatement();

            ResultSet rs = s.executeQuery ("SELECT * FROM users");
            MyAdminLabels label;
            while (rs.next()) {
                if (search == null || rs.getString(2).contains(search)) {
                    label = new MyAdminLabels(rs.getInt(1), rs.getString(2), rs.getString(4),
                            rs.getString(5));
                    label.setLocation((int) iniPoint.getX(), (int) iniPoint.getY()); users.add(label);
                    this.add(label); iniPoint = new Point((int) iniPoint.getX(), (int) iniPoint.getY() + 50);
                    paint(getGraphics());
                }
            }
        } catch (Exception o) {o.printStackTrace();}

    }
}

class MyAdminLabels extends JPanel {
    public JLabel info;
    public JButton delete, mute, ban, note;
    public JSpinner warn;
    public Actions x = new Actions();

    private final int ID;
    private final String permission;

    public int banned = 0, muted = 0, warnings = 0;
    public static String[] notes;
    public static int[] emptys;

    public MyAdminLabels(int ID, String name, String permission, String mail) {
        this.ID = ID;
        this.permission = permission;

        this.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 2, true));
        this.setSize(900, 50);
        this.setBackground(new Color(40,40,40));
        this.setLayout(null);
        this.setVisible(true);

        info = new JLabel("ID: " + ID + " | Nombre: " + name + " | Permission: " + permission + " | Correo: " + mail + " |");
        info.setForeground(Color.white);
        info.setFont(new Font("Verdana", Font.BOLD, 10)); info.setBounds(5,0,500,50);
        if (permission.equals("admin")) info.setForeground(Color.red);

        delete = new JButton("Borrar"); delete.setBackground(new Color(40,40,40)); delete.setForeground(Color.red);
        delete.setFont(new Font("Verdana", Font.BOLD, 8)); delete.setFocusable(false);
        delete.setBorder(BorderFactory.createLineBorder(Color.red, 2, true)); delete.addActionListener(x);
        delete.setBounds(510, 10, 60, 30);

        try {
            Statement s = Main.conexion.createStatement();
            ResultSet rs = s.executeQuery ("SELECT * FROM users_sanciones WHERE ID = " + getID() + ";");
            if (rs.next()) {
                if (rs.getInt(3) == 1) muted = 1;
                if (rs.getInt(2) == 1) banned = 1;
                warnings = rs.getInt(4);
            }
        } catch (Exception o) {o.printStackTrace();}

        mute = new JButton("Mutear"); mute.setBackground(new Color(40,40,40)); mute.setForeground(Color.orange);
        mute.setFont(new Font("Verdana", Font.BOLD, 8)); mute.setFocusable(false);
        mute.setBorder(BorderFactory.createLineBorder(Color.orange, 2, true)); mute.addActionListener(x);
        mute.setBounds(580, 10, 60, 30);
        if (muted == 1) {
            mute.setText("Desmutear"); mute.setBorder(BorderFactory.createLineBorder(Color.white, 3 ,true));
            mute.setForeground(Color.white); mute.setBackground(Color.orange);
        }

        ban = new JButton("Banear"); ban.setBackground(new Color(40,40,40)); ban.setForeground(Color.yellow);
        ban.setFont(new Font("Verdana", Font.BOLD, 8)); ban.setFocusable(false);
        ban.setBorder(BorderFactory.createLineBorder(Color.yellow, 2, true)); ban.addActionListener(x);
        ban.setBounds(650, 10, 60, 30);
        if (banned == 1) {
            info.setText(info.getText() + " BAN |"); ban.setForeground(Color.white); ban.setBackground(Color.yellow);
            info.setForeground(Color.yellow); ban.setText("Desbanear"); ban.setBorder(BorderFactory.createLineBorder(Color.white, 3 ,true));
        }

        note = new JButton("Nota"); note.setBackground(new Color(40,40,40)); note.setForeground(Color.blue);
        note.setFont(new Font("Verdana", Font.BOLD, 8)); note.setFocusable(false);
        note.setBorder(BorderFactory.createLineBorder(Color.blue, 2, true)); note.addActionListener(x);
        note.setBounds(720, 10, 60, 30);

        warn = new JSpinner(new SpinnerNumberModel(warnings,0,3,1)); warn.setBackground(new Color(40,40,40)); warn.setForeground(Color.white);
        warn.setFont(new Font("Verdana", Font.BOLD, 8)); warn.setFocusable(false);
        warn.setBorder(BorderFactory.createLineBorder(Color.white, 2, true));
        warn.setBounds(790, 10, 60, 30);
        warn.addChangeListener(e -> {
            if (Integer.parseInt(warn.getValue().toString()) < 0) warn.setValue(0);
            if (Integer.parseInt(warn.getValue().toString()) > 3) warn.setValue(3);
            try {
                Statement s = Main.conexion.createStatement();
                if (s.executeQuery("SELECT * FROM users_sanciones WHERE ID = " + getID() + ";").next()) {
                    s.executeUpdate("UPDATE users_sanciones SET warnings = " + warn.getValue() + " WHERE ID = " + getID() + ";");
                } else {
                    s.executeUpdate("INSERT INTO `users_sanciones` (`ID`, `banned`, `muted`, `warnings`, `notes`, `bannedby`) " +
                            "VALUES (" + getID() + ", 0, 0, " + warn.getValue() + ", NULL, NULL);");
                }
                warnings = ((int) warn.getValue());
                if (warnings == 3) {
                    banned = 1; info.setText(info.getText() + " BAN |");
                    info.setForeground(Color.yellow); ban.setText("Desbanear"); ban.setBorder(BorderFactory.createLineBorder(Color.white, 3 ,true));
                    ban.setForeground(Color.white); ban.setBackground(Color.yellow);
                }
            } catch (Exception o) {o.printStackTrace();}
        });

        add(info); add(delete); add(mute); add(ban); add(note); add(warn);
    }

    public int getID() {return ID;}

    public class Actions implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(delete)) {
                if (JOptionPane.showConfirmDialog(null, "¿Está seguro? Esta acción no\nse puede revertir.", "AVISO",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
                    try {
                        Statement s = Main.conexion.createStatement();
                        s.executeUpdate("DELETE FROM users WHERE ID = " + getID() + ";");
                        Main.frame.adminPanel.reload(null);
                    } catch (Exception ignored) {
                    }
                }
            } else if (e.getSource().equals(mute)) {
                if (muted == 1) {
                    muted = 0;
                    mute.setText("Mutear");
                    mute.setBorder(BorderFactory.createLineBorder(Color.orange, 3, true));
                    mute.setForeground(Color.orange);
                    mute.setBackground(new Color(40, 40, 40));
                } else {
                    muted = 1;
                    mute.setText("Desmutear");
                    mute.setBorder(BorderFactory.createLineBorder(Color.white, 3, true));
                    mute.setForeground(Color.white);
                    mute.setBackground(Color.orange);
                }
                try {
                    Statement s = Main.conexion.createStatement();
                    if (s.executeQuery("SELECT * FROM users_sanciones WHERE ID = " + getID() + ";").next()) {
                        s.executeUpdate("UPDATE users_sanciones SET muted = " + muted + " WHERE ID = " + getID() + ";");
                    } else {
                        s.executeUpdate("INSERT INTO `users_sanciones` (`ID`, `banned`, `muted`, `warnings`, `notes`, `bannedby`) " +
                                "VALUES (" + getID() + ", 0, " + muted + ", 0, NULL, NULL);");
                    }
                } catch (Exception ignored) {
                }
            } else if (e.getSource().equals(ban)) {
                if (banned == 1) {
                    banned = 0;
                    info.setText(info.getText().substring(0, info.getText().length() - 6));
                    info.setForeground(Color.white);
                    ban.setText("Banear");
                    ban.setBorder(BorderFactory.createLineBorder(Color.yellow, 3, true));
                    ban.setForeground(Color.yellow);
                    ban.setBackground(new Color(40, 40, 40));
                    if (permission.equals("admin")) info.setForeground(Color.red);
                } else {
                    banned = 1;
                    info.setText(info.getText() + " BAN |");
                    info.setForeground(Color.yellow);
                    ban.setText("Desbanear");
                    ban.setBorder(BorderFactory.createLineBorder(Color.white, 3, true));
                    ban.setForeground(Color.white);
                    ban.setBackground(Color.yellow);
                }
                try {
                    Statement s = Main.conexion.createStatement();
                    if (s.executeQuery("SELECT * FROM users_sanciones WHERE ID = " + getID() + ";").next()) {
                        s.executeUpdate("UPDATE users_sanciones SET banned = " + banned + " WHERE ID = " + getID() + ";");
                    } else {
                        s.executeUpdate("INSERT INTO `users_sanciones` (`ID`, `banned`, `muted`, `warnings`, `notes`, `bannedby`) " +
                                "VALUES (" + getID() + ", " + banned + ", 0, 0, NULL, NULL);");
                    }
                    if (banned == 1)
                        s.executeUpdate("UPDATE users_sanciones SET bannedby = '" + Main.userName + "' WHERE ID = " + getID() + ";");
                    else s.executeUpdate("UPDATE users_sanciones SET bannedby = NULL WHERE ID = " + getID() + ";");
                } catch (Exception ignored) {
                }
            } else if (e.getSource().equals(note)) {
                MySocialLogIn.IDsex = getID();
                notes = new String[3];
                try {
                    Statement s = Main.conexion.createStatement(); String notefull;
                    ResultSet rs = s.executeQuery("SELECT * FROM users_sanciones WHERE ID = " + getID() + ";");
                    if (rs.next()) {
                        notefull = rs.getString(5);
                        Arrays.fill(notes, "");
                        int pos = 0;
                        for (int i = 1; i < notefull.length(); i++) {
                            if (notefull.charAt(i) == '}') {
                                if (i != 1) pos++;
                                break;
                            } else if (notefull.charAt(i) == ',') {
                                pos++;
                            } else {
                                notes[pos] += notefull.charAt(i);
                            }
                        }
                        switch (pos) {
                            case 0: notes[1] = ""; notes[2] = "";
                                break;
                            case 1: notes[2] = "";
                                break;
                        }
                    }
                } catch (Exception e1) {e1.printStackTrace();} emptys = new int[3];
                emptys[0] = -1; emptys[1] = -1; emptys[2] = -1;
                for (int i = 0; i < MySidePanel.textFields.length; i++) {
                    MySidePanel.textFields[i].setVisible(true);
                    MySidePanel.textFields[i].setText(notes[i]);
                    if (MySidePanel.textFields[i].getText().isEmpty() && i != 0) {
                        MySidePanel.textFields[i].setVisible(false);
                        emptys[i] = i;
                    }
                }
                MySidePanel.addNotes.setVisible(emptys[0] != -1 || emptys[1] != -1 || emptys[2] != -1);
            }
        }
    }
}

class MySidePanel extends JPanel {
    public JTextField searchBar;
    public static MyNotes[] textFields = new MyNotes[3];
    public static JButton addNotes;

    public MySidePanel() {
        this.setBackground(new Color(40, 40, 40));
        this.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 2, true));
        this.setBounds(900, 50, 300, 690);
        this.setLayout(null);

        searchBar = new JTextField();
        searchBar.setBounds(5, 5, 295, 30);
        searchBar.setBorder(this.getBorder());
        searchBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (searchBar.getText().isEmpty()) Main.frame.adminPanel.reload(null);
                else Main.frame.adminPanel.reload(searchBar.getText());
            }
        });
        this.add(searchBar);

        addNotes = new JButton("Añadir Nota"); addNotes.setForeground(Color.blue); addNotes.setVisible(false);
        addNotes.setFont(new Font("Verdana", Font.BOLD, 8)); addNotes.setFocusable(false);
        addNotes.setBorder(BorderFactory.createLineBorder(Color.blue, 2, true)); addNotes.setBounds(230,650,50,25);
        addNotes.addActionListener(e -> {
            if (MyAdminLabels.emptys[0] == -1 && MyAdminLabels.emptys[1] == -1 && MyAdminLabels.emptys[2] == -1) {addNotes.setVisible(false);}
            else {
                int i; for (i = 0;i < MyAdminLabels.emptys.length; i++) if (MyAdminLabels.emptys[i] != -1) break;
                MySidePanel.textFields[i].setVisible(true); MyAdminLabels.emptys[i] = -1;
                if (MyAdminLabels.emptys[0] == -1 && MyAdminLabels.emptys[1] == -1 && MyAdminLabels.emptys[2] == -1) addNotes.setVisible(false);
            }
        });
        add(addNotes);

        int x = 5; int y = 45;
        for (int i = 0; i < 3; i++) {
            textFields[i] = new MyNotes(x, y);
            y += 55; add(textFields[i]);
        }
    }

    static class MyNotes extends JTextField {
        public MyNotes(int x, int y) {
            setBounds(x,y,290,50);
            setBackground(Color.white);
            setBorder(BorderFactory.createLineBorder(new Color(40,40,40), 2, true));
            setVisible(false);
            addActionListener(new Actionss());
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    FontMetrics fm = getFontMetrics(getFont()); int count = 0; String concatenation = "";
                    for (int i = 0; i < getText().length(); i++) {
                        count += fm.charWidth(getText().charAt(i));
                        if (count >= getWidth() - 50) {setText(concatenation); break;}
                        else {concatenation += getText().charAt(i);}
                    }
                }
            });
        }

        private class Actionss implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Statement s = Main.conexion.createStatement(); String res;
                    if (MyAdminLabels.notes[0].isEmpty() && MyAdminLabels.notes[1].isEmpty() && MyAdminLabels.notes[2].isEmpty()
                    && getText().isEmpty()) {res = "{" + getText() + "}";}
                    else {
                        if (e.getSource().equals(MySidePanel.textFields[0])) MyAdminLabels.notes[0] = getText();
                        else if (e.getSource().equals(MySidePanel.textFields[1])) MyAdminLabels.notes[1] = getText();
                        else MyAdminLabels.notes[2] = getText();
                        res = "{" + MyAdminLabels.notes[0] + "," + MyAdminLabels.notes[1] + "," + MyAdminLabels.notes[2] + "}";
                    }
                    s.executeUpdate("UPDATE users_sanciones SET notes = '" + res + "' WHERE ID = " + MySocialLogIn.IDsex + ";");
                } catch (Exception e1) {e1.printStackTrace();}
            }
        }
    }
}

class MyForum extends JPanel {
    private boolean isChatMode = true;
    private final JButton closeButton, chatButton, projectsButton;
    private Timer timer;
    public static MyChats chats = new MyChats();
    public static MyProjectsPage projectsPage = new MyProjectsPage();
    public static MySideForum sideList = new MySideForum();
    public static Color forumColor = new Color(250, 106, 9), backColor = new Color(40,40,40);

    public MyForum() {
        setVisible(true);
        setBackground(new Color(40,40,40));
        setBorder(BorderFactory.createLineBorder(new Color(80,80,80), 3, true));
        setLayout(null);

        Actionsh al = new Actionsh();

        JPanel mainBar = new JPanel();
        mainBar.setBounds(0,0,1400, 50); mainBar.setVisible(true);
        mainBar.setBackground(new Color(50,50,50)); mainBar.setLayout(null);
        mainBar.setBorder(BorderFactory.createLineBorder(new Color(80,80,80), 2, true));

        JLabel logoTop = new JLabel(); logoTop.setIcon(new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\minilogo2.png")); logoTop.setBounds(20,7,140,40);
        JLabel forumTop = new JLabel("[foro]"); forumTop.setFont(new Font("Verdana", Font.BOLD, 8)); forumTop.setForeground(forumColor);
        forumTop.setBounds(160, 25, 80, 15);

        chatButton = new JButton("Chats"); chatButton.setFocusable(false); chatButton.setBackground(forumColor);
        chatButton.setBorder(BorderFactory.createLineBorder(Color.white, 3, true)); chatButton.setForeground(Color.white);
        chatButton.setBounds(220,10,80,30);
        chatButton.addActionListener(al);

        projectsButton = new JButton("Proyectos"); projectsButton.setFocusable(false); projectsButton.setBackground(backColor);
        projectsButton.setBorder(BorderFactory.createLineBorder(forumColor, 3, true)); projectsButton.setForeground(forumColor);
        projectsButton.setBounds(300,10,80,30); projectsButton.addActionListener(al);

        closeButton = new JButton(); closeButton.setFocusable(false); closeButton.setBackground(backColor);
        closeButton.setBounds(1370, 8, 22,22); closeButton.setIcon(new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\cross.png"));
        closeButton.addActionListener(e1 -> {
            timer = new Timer(200, e2 -> {
                closeButton.setIcon(new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\cross.png")); this.setVisible(false); timer.stop();
            }); closeButton.setIcon(new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\activecross.png")); timer.start();
        }); mainBar.add(logoTop); mainBar.add(forumTop); mainBar.add(closeButton); mainBar.add(chatButton); mainBar.add(projectsButton);
        add(mainBar); add(chats); add(projectsPage); add(sideList);
    }

    private class Actionsh implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MyChats.chat.setVisible(false);
            MyChats.writeBar.setVisible(false);
            if (e.getSource().equals(chatButton)) {
                if (!isChatMode) {
                    chatButton.setBackground(forumColor); projectsButton.setBackground(backColor); isChatMode = true;
                    chatButton.setBorder(BorderFactory.createLineBorder(Color.white, 3, true)); chatButton.setForeground(Color.white);
                    projectsButton.setBorder(BorderFactory.createLineBorder(forumColor, 3, true)); projectsButton.setForeground(forumColor);
                    chats.setVisible(true); projectsPage.setVisible(false);
                }
            } else if (e.getSource().equals(projectsButton)) {
                if (isChatMode) {
                    projectsButton.setBackground(forumColor); chatButton.setBackground(backColor); isChatMode = false;
                    projectsButton.setBorder(BorderFactory.createLineBorder(Color.white, 3, true)); projectsButton.setForeground(Color.white);
                    chatButton.setBorder(BorderFactory.createLineBorder(forumColor, 3, true)); chatButton.setForeground(forumColor);
                    chats.setVisible(false); projectsPage.setVisible(true);
                }
            }
        }
    }
}

class MyChats extends JLayeredPane {
    public static JScrollPane chat;
    public static JScrollBar scrollbar;
    public static JTextArea writeBar;
    public static JTextArea vacio;
    public static String[] frases = {
            "¡Hola! Parece que este chat está esperando tus brillantes mensajes. ¿Qué hay de nuevo?",
            "¡Hola! Este chat está tan vacío como una página en blanco, ¡pero eso significa que hay espacio para llenarlo de buenas conversaciones!",
            "¡Hola, amigo/a! Parece que este chat está buscando un poco de acción. ¿Te animas a empezar la conversación?",
            "¡Hola! Este chat está como una pista de baile sin música. ¿Listo/a para ponerle ritmo con tus preguntas o comentarios?",
            "¡Hola, hola! Este chat está en modo pausa, pero estoy listo/a para reactivarlo contigo. ¿Hay algo que quieras compartir o preguntar?",
            "¡Hola de nuevo! Parece que este chat está esperando tus palabras mágicas. ¿Qué tienes en mente?"};
    public MyChats() {
        setVisible(false); setVisible(true);
        setBounds(0,50,1400,677);
        setPreferredSize(new Dimension(1400,677));
        setBackground(MyForum.backColor);

        scrollbar = new JScrollBar(); scrollbar.setVisible(true); scrollbar.setBounds(990,0,10,627); scrollbar.setOrientation(Scrollbar.VERTICAL);
        scrollbar.setBackground(MyForum.backColor); scrollbar.setForeground(new Color(80,80,80));

        chat = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        chat.setVisible(false); chat.setLayout(null); chat.setBackground(MyForum.backColor); chat.setBounds(0,0,1000, 627); chat.setPreferredSize(new Dimension(1000, 627));
        chat.setBorder(BorderFactory.createLineBorder(MyForum.forumColor, 2, true)); chat.setVerticalScrollBar(scrollbar);
        add(chat); setLayer(chat, 300);

        vacio = new JTextArea(); vacio.setBounds(20,20,960,140); vacio.setText(frases[new Random().nextInt(5)]); vacio.setEditable(false);
        vacio.setBackground(MyForum.backColor); vacio.setLayout(new FlowLayout(FlowLayout.LEADING)); vacio.setVisible(false); vacio.setLineWrap(true);
        vacio.setWrapStyleWord(true); vacio.setFont(new Font("Verdana", Font.BOLD, 15)); vacio.setForeground(Color.white); chat.add(vacio);

        writeBar = new JTextArea(); writeBar.setBounds(5, 630, 950, 35); writeBar.setVisible(true); writeBar.setBackground(MyForum.backColor);
        writeBar.setBorder(BorderFactory.createLineBorder(MyForum.forumColor, 2, true)); writeBar.setForeground(Color.white); add(writeBar);
        writeBar.setLayout(null); writeBar.setLineWrap(true); writeBar.setWrapStyleWord(true); writeBar.setVisible(false);
        writeBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n' && !writeBar.getText().isEmpty()) {
                    MyChats.vacio.setVisible(false);
                    try {
                        ResultSet rs = Main.conexion.createStatement().executeQuery("SELECT * FROM users_sanciones WHERE ID = " + Main.userID + ";");
                        if (rs.next() && rs.getInt(3) != 1) {
                            LocalDateTime n = LocalDateTime.now();
                            String date = n.getYear() + "-" + n.getMonthValue() + "-" + n.getDayOfMonth();
                            String time = n.getHour() + ":" + n.getMinute() + ":" + n.getSecond() + ".000000";
                            Main.conexion.createStatement().executeUpdate("INSERT INTO `messages` (`message_ID`, `chat_ID`, `message_author`, `message_text`, `message_date`, `message_hour`, `message_pinned`) " +
                                    "VALUES (NULL, '" + Main.isInChat + "', '" + Main.userName +
                                    "', '" + writeBar.getText() + "', '" + date + "', '" + time + "', '0');");
                            chat.setVisible(true);
                            MyChatMessages actualMessage;
                            rs = Main.conexion.createStatement().executeQuery("SELECT * FROM messages WHERE chat_ID = " + Main.isInChat + ";");
                            if (!MyChatsPanel.addedMsgs.isEmpty()) {
                                for (int i = 0; i < MyChatsPanel.addedMsgs.size(); i++) chat.remove(MyChatsPanel.addedMsgs.get(i));
                                MyChatsPanel.addedMsgs.clear();
                            } int x2 = 5; int y2 = 5; boolean containsMessages = false;
                            ResultSet rs3 = Main.conexion.createStatement().executeQuery(
                                    "SELECT * FROM messages WHERE chat_ID = " + Main.isInChat + " AND message_pinned = 1;");
                            while (rs3.next()) {
                                actualMessage = new MyChatMessages(x2, y2, rs3.getInt(1), rs3.getInt(2), rs3.getString(3),
                                        rs3.getString(4), rs3.getDate(5).toString() + " " + rs3.getTime(6).toString(), true); add(actualMessage);
                                chat.add(actualMessage);
                                MyChatsPanel.addedMsgs.add(actualMessage);
                                y2 += actualMessage.heightToSum;
                            }
                            while (rs.next()) {
                                actualMessage = new MyChatMessages(x2, y2, rs.getInt(1), rs.getInt(2), rs.getString(3),
                                        rs.getString(4), rs.getDate(5).toString() + " " + rs.getTime(6).toString(), false); add(actualMessage);
                                if (!MyChatsPanel.arrayListContains(MyChatsPanel.addedMsgs, actualMessage)) {
                                    chat.add(actualMessage);
                                    MyChatsPanel.addedMsgs.add(actualMessage);
                                    y2 += actualMessage.heightToSum;
                                } if (!containsMessages) containsMessages = true;
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Lo sentimos ¡Has sido muteado por un administrador! " +
                                            "\n Para recurrir esta sanción contacta con el equipo de administración."
                                    , "GateCraft", JOptionPane.ERROR_MESSAGE, new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\logo.png"));
                        }
                        writeBar.setText("");
                    } catch (Exception o) {o.printStackTrace();}
                }
            }
        });

        try { int x = 5; int y = 5;
            Statement s = Main.conexion.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM chats");
            while (rs.next()) {
                add(new MyChatsPanel(x,y,rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                y += 60;
            }
        } catch (Exception o) {o.printStackTrace();}
    }

    private class MyChatsPanel extends JPanel {
        public static ArrayList<MyChatMessages> addedMsgs = new ArrayList<>();
        public int chatID;
        public MyChatsPanel(int x, int y, int chatID, String title, String description, String author) {
            this.chatID = chatID;
            setBounds(x,y,985, 60);
            setLayout(null);
            setBackground(MyForum.backColor);
            setBorder(BorderFactory.createLineBorder(new Color(250, 106, 9), 1, true));

            JButton titlecase = new JButton(title); titlecase.setBounds(5,5,600,15);
            titlecase.setFont(new Font("Verdana", Font.BOLD, 14)); titlecase.setForeground(Color.white);
            titlecase.setBackground(MyForum.backColor); titlecase.setBorder(null); titlecase.setHorizontalAlignment(SwingConstants.LEFT);
            titlecase.addActionListener(e -> printMessages());
            JLabel descCase = new JLabel(description); descCase.setBounds(5,25, 990, 25);
            descCase.setFont(new Font("Verdana", Font.BOLD, 10)); descCase.setForeground(Color.white);
            JLabel authCase = new JLabel("Por: " + author); authCase.setBounds(950 - (int) Math.ceil(author.length() * 7.5),5, 300, 15);
            authCase.setFont(new Font("Verdana", Font.BOLD, 10)); authCase.setForeground(new Color(250, 106, 9));
            add(titlecase); add(descCase); add(authCase);
        }

        public void printMessages() {
            writeBar.setVisible(true);
            try {
                Main.isInChat = chatID;
                ResultSet rs = Main.conexion.createStatement().executeQuery("SELECT * FROM users_sanciones WHERE ID = " + Main.userID + ";");
                if (rs.next() && rs.getInt(2) != 1) {
                    chat.setVisible(true);
                    MyChatMessages actualMessage;
                    rs = Main.conexion.createStatement().executeQuery("SELECT * FROM messages WHERE chat_ID = " + chatID + ";");
                    if (!addedMsgs.isEmpty()) {
                        for (MyChatMessages addedMsg : addedMsgs) chat.remove(addedMsg);
                        addedMsgs.clear();
                    } int x2 = 5; int y2 = 5; boolean containsMessages = false;
                    ResultSet rs2 = Main.conexion.createStatement().executeQuery(
                            "SELECT * FROM messages WHERE chat_ID = " + chatID + " AND message_pinned = 1;");
                    while (rs2.next()) {
                        actualMessage = new MyChatMessages(x2, y2, rs2.getInt(1), rs2.getInt(2), rs2.getString(3),
                                rs2.getString(4), rs2.getDate(5).toString() + " " + rs2.getTime(6).toString(), true); add(actualMessage);
                        chat.add(actualMessage);
                        addedMsgs.add(actualMessage);
                        y2 += actualMessage.heightToSum;
                    }
                    while (rs.next()) {
                        actualMessage = new MyChatMessages(x2, y2, rs.getInt(1), rs.getInt(2), rs.getString(3),
                                rs.getString(4), rs.getDate(5).toString() + " " + rs.getTime(6).toString(), false); add(actualMessage);
                        if (!arrayListContains(addedMsgs, actualMessage)) {
                            chat.add(actualMessage);
                            addedMsgs.add(actualMessage);
                            y2 += actualMessage.heightToSum;
                        } if (!containsMessages) containsMessages = true;
                        MyChats.vacio.setVisible(false);
                    } if (!containsMessages) {
                        MyChats.vacio.setText(MyChats.frases[new Random().nextInt(5)]);
                        MyChats.vacio.setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Lo sentimos ¡Has sido vetado de GateCraft! " +
                                    "\n Para recurrir esta sanción contacta con el equipo de administración."
                            , "GateCraft", JOptionPane.ERROR_MESSAGE, new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\logo.png"));
                }
            } catch (Exception e1) {e1.printStackTrace();}
        }
        public static boolean arrayListContains(ArrayList<MyChatMessages> a, MyChatMessages b) {
            for (MyChatMessages myChatMessages : a) if (myChatMessages.chatID == b.chatID && myChatMessages.messageID == b.messageID) return true;
            return false;
        }
    }

    private class MyChatMessages extends JPanel {
        private final int messageID, chatID;
        public int heightToSum = 0;
        public JButton deleteButton;
        public Timer timer;
        public MyChatMessages(int x, int y, int messageID, int chatID, String author, String text, String date, boolean messagePinned) {
            this.messageID = messageID;
            this.chatID = chatID;

            setVisible(true);
            setBackground(MyForum.backColor);
            setLayout(null);
            String permission = "user";
            try { ResultSet rs = Main.conexion.createStatement().executeQuery("SELECT * FROM users WHERE user_name = '" + author + "';");
                if (rs.next()) permission = rs.getString(4);
            } catch (Exception o) {o.printStackTrace();}
            if (messagePinned) {
                JLabel pin = new JLabel("Fijado"); pin.setBounds(900, 20, 100,24); pin.setFont(new Font("Verdana", Font.BOLD, 10));
                pin.setIcon(new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\pinnedmessage.png")); pin.setForeground(MyForum.forumColor); pin.setHorizontalTextPosition(SwingConstants.LEFT);
                add(pin);
                setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(MyForum.forumColor, 3, true),
                        author + " [" + permission + "]"));
            } else {
                setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(80,80,80), 2, true),
                        author + " [" + permission + "]"));
            }

            TitledBorder border = (TitledBorder) getBorder(); border.setTitleColor(MyForum.forumColor);
            JLabel dateCase = new JLabel(date.substring(0, date.length() - 3)); dateCase.setFont(new Font("Verdana", Font.BOLD, 8));
            dateCase.setBounds(900,7, 100, 10); dateCase.setForeground(new Color(250, 106, 9));
            add(dateCase);
            JTextArea textCase = new JTextArea(); textCase.setFont(new Font("Verdana", Font.BOLD, 12)); textCase.setForeground(Color.white);
            textCase.setText(insertLineJumps(text, textCase.getFont()));
            textCase.setBounds(5,20, 895, getNecessaryLines(text, textCase.getFont()) - 30);
            setBounds(x, y, 990, getNecessaryLines(text, textCase.getFont())); textCase.setBackground(MyForum.backColor);
            heightToSum = getNecessaryLines(text, textCase.getFont());
            add(textCase);

            deleteButton = new JButton(); deleteButton = new JButton(); deleteButton.setFocusable(false); deleteButton.setVisible(false);
            deleteButton.setBounds(975, (int) getSize().getHeight() - 15, 10,10); deleteButton.setIcon(new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\cross.png"));
            deleteButton.setBackground(MyForum.backColor); deleteButton.setBorder(BorderFactory.createLineBorder(new Color(80,80,80), 2, true));
            try {
                ResultSet rs = Main.conexion.createStatement().executeQuery("SELECT * FROM users WHERE ID = " + Main.userID + ";");
                if (rs.next() && (author.equals(Main.userName) || rs.getString(4).equals("admin"))) {deleteButton.setVisible(true);}
            } catch (Exception o) {o.printStackTrace();}
            deleteButton.addActionListener(e1 -> {
                timer = new Timer(200, e2 -> {
                    if (JOptionPane.showConfirmDialog(null, "¿Está seguro? Esta acción no\nse puede revertir.", "AVISO",
                            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
                        try {
                            Main.conexion.createStatement().executeUpdate("DELETE FROM messages WHERE message_ID = " + messageID + " AND chat_ID = " + chatID + ";");
                            this.setVisible(false);

                            chat.setVisible(true);
                            MyChatMessages actualMessage;
                            ResultSet rs = Main.conexion.createStatement().executeQuery("SELECT * FROM messages WHERE chat_ID = " + Main.isInChat + ";");
                            if (!MyChatsPanel.addedMsgs.isEmpty()) {
                                for (int i = 0; i < MyChatsPanel.addedMsgs.size(); i++) chat.remove(MyChatsPanel.addedMsgs.get(i));
                                MyChatsPanel.addedMsgs.clear();
                            } int x2 = 5; int y2 = 5; boolean containsMessages = false;
                            ResultSet rs3 = Main.conexion.createStatement().executeQuery(
                                    "SELECT * FROM messages WHERE chat_ID = " + Main.isInChat + " AND message_pinned = 1;");
                            while (rs3.next()) {
                                actualMessage = new MyChatMessages(x2, y2, rs3.getInt(1), rs3.getInt(2), rs3.getString(3),
                                        rs3.getString(4), rs3.getDate(5).toString() + " " + rs3.getTime(6).toString(), true); add(actualMessage);
                                chat.add(actualMessage);
                                MyChatsPanel.addedMsgs.add(actualMessage);
                                y2 += actualMessage.heightToSum;
                            }
                            while (rs.next()) {
                                actualMessage = new MyChatMessages(x2, y2, rs.getInt(1), rs.getInt(2), rs.getString(3),
                                        rs.getString(4), rs.getDate(5).toString() + " " + rs.getTime(6).toString(), false); add(actualMessage);
                                if (!MyChatsPanel.arrayListContains(MyChatsPanel.addedMsgs, actualMessage)) {
                                    chat.add(actualMessage);
                                    MyChatsPanel.addedMsgs.add(actualMessage);
                                    y2 += actualMessage.heightToSum;
                                } if (!containsMessages) containsMessages = true;
                            }
                            if (!containsMessages) {
                                MyChats.vacio.setText(MyChats.frases[new Random().nextInt(5)]);
                                MyChats.vacio.setVisible(true);
                            }
                            chat.paint(chat.getGraphics());
                        } catch (Exception o) {o.printStackTrace();}
                    }
                    deleteButton.setIcon(new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\cross.png")); timer.stop();
                }); deleteButton.setIcon(new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\activecross.png")); timer.start();
            }); add(deleteButton);
        }
        public int getNecessaryLines(String message, Font font) {
            FontMetrics fontMetrics = getFontMetrics(font);
            int textWidth = fontMetrics.stringWidth(message);
            int lines = (int) Math.ceil((double) textWidth / 900);
            int lineHeight = fontMetrics.getHeight();
            return 30 + (lines * lineHeight);
        }
        public String insertLineJumps(String text, Font font) {
            FontMetrics fontMetrics = getFontMetrics(font);
            if (fontMetrics.stringWidth(text) <= 900) { return text;
            } else { int ini = 0; String result = "";
                for (int i = 0; i < text.length(); i++) {
                    ini += fontMetrics.charWidth(text.charAt(i));
                    result += text.charAt(i);
                    if (ini >= 900 && (text.charAt(i) == ' ' || text.charAt(i) == ',' || text.charAt(i) == '.'
                            || text.charAt(i) == ')' || text.charAt(i) == '!' || text.charAt(i) == '?')) {
                        result += "\n";
                        ini = 0;
                    }
                }
                return result.trim();
            }
        }
    }
}

class MyProjectsPage extends JLayeredPane {
    public MyProjectsPage() {
        setVisible(false); setVisible(true);
        setBounds(0,50,1400,677);
        setBackground(MyForum.backColor); //mensajes=  setBounds(x,y,300, 600); if (x2 >= 1000) {x2 = 25; y2 += 625;} else {x2 += 325;}
    }
}

class MySideForum extends JPanel {
    public MySideForum() {
        setBounds(1000,50,400,677);
        setBackground(MyForum.backColor);
        setLayout(null);
        setVisible(true);
        setBorder(BorderFactory.createLineBorder(new Color(80,80,80), 2, true));

    }
}