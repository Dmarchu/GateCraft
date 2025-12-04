import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MyFrame extends JFrame {

    //Declaración de Variables estáticas
    public static Color thiscolor = Color.BLACK, theme = Color.WHITE;
    public static Clip clip;
    public static int tipo;
    public static MyPanel panel;
    @SuppressWarnings("ClassEscapesDefinedScope")
    public static ArrayList<MyMenuButtons> botone = new ArrayList<>();

    //Declaración de Variables
    public static int width, height;
    public static JTextField namer;
    public static JPanel topBar, sideBar, runWarningPanel;
    public static JLabel grid, buttoninfo;
    public static JScrollPane scrollPane;
    public static MyForum foro;
    public JLayeredPane mainPanel;
    public JPanel miniPanel, backgroundPanel;
    public JLabel runWarningMessage, runWarningMessage2;
    public MyAdminPanel adminPanel;

    public static ImageIcon grid1 = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\grid1.png"), grid2 = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\grid2.png");

    @SuppressWarnings("ClassEscapesDefinedScope")
    public MouseStateListener mlistener;
    private ImageIcon logo = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\logo.png");
    public static MyRectPanel rectif;
    public static MyLanguagePanel languag;
    public static String nombreProyecto = "Project";

    //Método Constructor
    public MyFrame(Dimension d) {
        //Genera el tamaño a partir del range, determinado por la pantalla completa / no completa
        width = (int) d.getWidth();
        height = (int) d.getHeight();

        añadirComponentes();
        mlistener = new MouseStateListener();
        MyPanel.repintado = true;
        panel.repaint();

        //Configuración del Frame y añade todos los objetos a la interfaz
        this.setVisible(false);
        this.setMinimumSize(d);
        this.setIconImage(logo.getImage());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle(Locales.TITULO);
        this.setSize(d); this.setPreferredSize(d);
        this.setLayout(null);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setContentPane(mainPanel);
        this.setExtendedState(MAXIMIZED_BOTH);
    }

    public void añadirComponentes() {
        //Declaración de los elementos de la interfaz
        panel = new MyPanel(width * 4, height * 2, theme);
        miniPanel = new JPanel();

        //Abre el sonido de clic
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\click.wav")));
            clip.setMicrosecondPosition(0);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }

        //Color de fondo y mantener options visible al cerrar
        setBackground(theme);

        //Crea el panel de selección de las Gates y añade los botones
        miniPanel.setVisible(true); miniPanel.setBackground(new Color(60,60,60));
        miniPanel.setLayout(new GridLayout(9,1));
        miniPanel.setBounds(50,60,310,height - 75);
        for (int i = 0, y = 0; i < 9; i++, y += (((4 * height) / 5) - 50) / 9) {
            int finalI = i;
            //Constructor de los botones
            miniPanel.add(new MyButton(i, 310 - 100, 100, 0, y + 5, theme, e -> {
                tipo = finalI;
                botone.get(9).setScrollPaneVisibility(false);
                botone.get(2).runWarning();
            }, mlistener));
        }

        //Añadir ScrollBar al panel anterior
        scrollPane = new JScrollPane(miniPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVisible(true); scrollPane.setBackground(new Color(60,60,60));
        scrollPane.setBounds(-(310),60,310,height - 75);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(100,100,100), 2, true), Locales.INSERTAR));
        scrollPane.setLayout(new ScrollPaneLayout()); scrollPane.setWheelScrollingEnabled(true);
        scrollPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                if (e.getX() >= 200 && scrollPane.getX() >= 0) MyFrame.botone.get(9).setScrollPaneVisibility(false);
            }
        });
        scrollPane.getVerticalScrollBar().setBackground(new Color(100,100,100)); scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(80,80,80);
            }
        });

        sideBar = new JPanel();
        sideBar.setBounds(0,60, 50, height * 2); sideBar.setVisible(true);
        sideBar.setBackground(new Color(50,50,50));
        sideBar.setBorder(BorderFactory.createLineBorder(new Color(80,80,80), 2, true));

        topBar = new JPanel();
        JLabel logoTop = new JLabel();
        topBar.setBounds(0,0,width * 4,60); topBar.setVisible(true);
        topBar.setBackground(new Color(50,50,50)); topBar.add(logoTop); topBar.setLayout(null);
        logoTop.setIcon(new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\logo2.png")); logoTop.setBounds(10,0,210,60);
        topBar.setBorder(BorderFactory.createLineBorder(new Color(80,80,80), 2, true));

        rectif = new MyRectPanel();
        namer = new JTextField("Project");
        namer.setBounds(270 + 40, 15, 200, 30); namer.setFont(new Font("Verdana", Font.PLAIN, 15));
        namer.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2, true)); namer.setBackground(new Color(85,85,85));
        namer.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                nombreProyecto = namer.getText();
            }
        });

        backgroundPanel = new JPanel(); backgroundPanel.setBounds(0,0,width,height);
        backgroundPanel.setBackground(new Color(60,60,60)); backgroundPanel.setOpaque(true);
        grid = new JLabel(); grid.setIcon(grid2); grid.setOpaque(false); grid.setVisible(true);
        backgroundPanel.add(grid);

        runWarningPanel = new JPanel(); runWarningPanel.setBounds(-175, 155, 175,50); runWarningPanel.setLayout(new GridLayout(2,1, 0, -5));
        runWarningPanel.setBackground(new Color(50,50,50)); runWarningPanel.setBorder(BorderFactory.createLineBorder(new Color(80,80,80), 2, true));
        runWarningMessage = new JLabel("Recuerde desactivarme al"); runWarningMessage.setFont(new Font("Verdana", Font.ITALIC, 12));
        runWarningMessage2 = new JLabel("editar los esquemas"); runWarningMessage2.setFont(new Font("Verdana", Font.ITALIC, 12));
        runWarningMessage.setForeground(Color.white); runWarningMessage2.setForeground(Color.white);
        runWarningPanel.add(runWarningMessage); runWarningPanel.add(runWarningMessage2); runWarningPanel.setVisible(true);

        buttoninfo = new JLabel(); buttoninfo.setVisible(false); buttoninfo.setBackground(new Color(50,50,50)); buttoninfo.setBounds(50,0, 100,50);
        buttoninfo.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2, true)); buttoninfo.setForeground(Color.white); buttoninfo.setOpaque(true);

        adminPanel = new MyAdminPanel(); adminPanel.setBounds(175,85, width - 350, height - 135);
        foro = new MyForum(); foro.setBounds(75,85, width - 150, height - 135); foro.setVisible(false);

        mainPanel = new JLayeredPane();
        mainPanel.setVisible(true); mainPanel.setBounds(0,0,width * 4,height * 2); mainPanel.add(adminPanel); mainPanel.add(foro);
        mainPanel.add(backgroundPanel); mainPanel.add(panel); mainPanel.add(scrollPane); mainPanel.add(sideBar); mainPanel.add(Main.sl);
        mainPanel.add(topBar); mainPanel.add(rectif); mainPanel.add(namer); mainPanel.add(runWarningPanel); mainPanel.add(buttoninfo);
        mainPanel.setLayer(backgroundPanel, 0);
        mainPanel.setLayer(panel, 100);
        mainPanel.setLayer(runWarningPanel, 200);
        mainPanel.setLayer(scrollPane, 200);
        mainPanel.setLayer(sideBar, 300);
        mainPanel.setLayer(topBar, 300);
        mainPanel.setLayer(namer, 400);
        mainPanel.setLayer(rectif, 500);
        mainPanel.setLayer(buttoninfo, 500);
        mainPanel.setLayer(foro, 600);
        mainPanel.setLayer(adminPanel, 700);
        mainPanel.setLayer(Main.sl, 700);

        for (int i = 0; i <= 15; i++) {
            botone.add(new MyMenuButtons(i));
            mainPanel.add(botone.get(i)); mainPanel.setLayer(botone.get(i), 400);
        }

        languag = new MyLanguagePanel();
        mainPanel.add(languag);
        mainPanel.setLayer(languag, 500);
    }

    //Getters

    @Override
    public int getWidth() {return width;}
    public static int getTipo() {return tipo;}

    private class MouseStateListener extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (MyMenuButtons.mode != -1) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }
}

