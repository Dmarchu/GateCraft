import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("DuplicatedCode")
public class MyPanel extends JPanel {

    //Declaración de variables estáticas
    public static Point imageCorner, prevPt, imageCorner2;
    public static ArrayList<Point> XPoint, YPoint, XimageCorner, YimageCorner, nextXPoint, nextYPoint, nextXimageCorner, nextYimageCorner;
    public static ArrayList<Integer> type2, lastAction, nextType2, nextAction;
    public static ArrayList<String> text, nextText;
    public static ArrayList<Color> color, nextColor, color2, nextColor2;
    public static int width, height, señal = 0, counter = -1, counter2 = -1, prevCounter, prevCounter2, image = -1, counter3 = -1, prevCounter3;
    public static boolean hasGraphics = false, pointspaint = false;

    public static ArrayList<Line2D> lines, nextLines;
    public static ArrayList<Gate> gates, nextGates;
    public static ArrayList<Cables> cables, nextCables; //Inicializar y implementar redos
    public static Rectangle2D selectedPoint;

    //Declaración de variables
    public static boolean repintado = false;
    private boolean isInFrame = false, confirmed = true;
    private ImageIcon icon;
    private Gate actualGate;
    private FileNameExtensionFilter filter;

    //Método Constructor
    public MyPanel(int width, int height, Color bkgnd) {
        //Inicialización de variables
        MyPanel.width = width;
        MyPanel.height = height;

        XPoint = new ArrayList<>();
        YPoint = new ArrayList<>();
        XimageCorner = new ArrayList<>();
        YimageCorner = new ArrayList<>();
        type2 = new ArrayList<>();
        text = new ArrayList<>();
        color = new ArrayList<>();
        color2 = new ArrayList<>();

        nextXPoint = new ArrayList<>();
        nextYPoint = new ArrayList<>();
        nextXimageCorner = new ArrayList<>();
        nextYimageCorner = new ArrayList<>();
        nextType2 = new ArrayList<>();
        nextText = new ArrayList<>();
        nextColor = new ArrayList<>();
        nextColor2 = new ArrayList<>();

        nextAction = new ArrayList<>();
        nextAction.add(0);

        lastAction = new ArrayList<>();
        lastAction.add(0);

        lines = new ArrayList<>();
        nextLines = new ArrayList<>();

        gates = new ArrayList<>();
        nextGates = new ArrayList<>();

        cables = new ArrayList<>();
        nextCables = new ArrayList<>();

        imageCorner = new Point(0, 25);
        imageCorner2 = new Point(0, 25);
        prevPt = new Point(0, 25);

        filter = new FileNameExtensionFilter(
                "Archivos de texto (.txt)", "txt");

        ClickListener clickListener = new ClickListener();
        addMouseListener(clickListener);

        MotionListener motionListener = new MotionListener();
        addMouseMotionListener(motionListener);

        //Configuraciones del panel (cambiar si se desea)
        setVisible(true);
        setOpaque(false);
        setLayout(null);
        setBounds(0,0,width,height);
    }

    public void clear(int call) { //Método para limpiar panel
        if (hasGraphics) { //Comprueba si hay gráficos en el editor
            confirmed = JOptionPane.showConfirmDialog(null, Locales.CONF_BORRAR, Locales.TITULOS_DIALOGS, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == 0;
            if (confirmed) { //Comprueba la confirmación del diálogo
                señal = 0;
                counter = -1;
                counter2 = -1;
                counter3 = -1;
                image = -1;

                XPoint.clear();
                YPoint.clear();
                XimageCorner.clear();
                YimageCorner.clear();
                type2.clear();
                text.clear();
                color.clear();
                color2.clear();

                lastAction.clear();
                lastAction.add(0);

                lines.clear();
                gates.clear();

                refreshRedo();
                hasGraphics = false;
                MyFrame.botone.get(4).setEnable(false);
                MyFrame.botone.get(3).setEnable(false);

                //Reinicia los puntos
                imageCorner = new Point(0, 25);
                prevPt = new Point(0, 25);
            }
        } else { //Salida en caso de que se llame desde el ActionListener del botón de borrar o desde abrir archivo (Una salida para cada caso)
            if (call == 0) JOptionPane.showMessageDialog(null, Locales.ERR_EDITOR_VACIO, Locales.TITULOS_DIALOGS, JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void paintComponent(Graphics g) { //Método que contiene la creación de líneas y los previews

        //Parte común a todos los if
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3.0f));

        prevCounter = counter;
        prevCounter2 = counter2;
        prevCounter3 = counter3;

        while (counter > -1) {
            g.setColor(color.get(counter));
            g.drawLine((int) XPoint.get(counter).getX(), (int) XPoint.get(counter).getY(), (int) YPoint.get(counter).getX(), (int) YPoint.get(counter).getY());
            counter--;
        }
        while (counter2 > -1) {
            SwitchGate(type2.get(counter2));
            icon.paintIcon(this, g, (int) XimageCorner.get(counter2).getX(), (int) XimageCorner.get(counter2).getY());
            counter2--;
        }
        while (counter3 > -1) {
            g.setColor(color2.get(counter3));
            g2d.setFont(new Font("Verdana", Font.BOLD, 14));
            g.drawString(text.get(counter3), (int) YimageCorner.get(counter3).getX(), (int) YimageCorner.get(counter3).getY());
            counter3--;
        }

        if (MyMenuButtons.mode == 0 && señal == 1) { //Preview de la línea
            counter3 = prevCounter3;
            counter2 = prevCounter2;
            counter = prevCounter;

            //Para enderezar la línea de preview. Cambiar predeterminado en MyRectPanel
            g.setColor(MyFrame.thiscolor);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            if (selectedPoint != null) { //Hacer alineamiento con los puntos de las gates
                Point centralPoint = new Point((int) (selectedPoint.getX() + (selectedPoint.getWidth() / 2)), (int) (selectedPoint.getY() + (selectedPoint.getHeight() / 2)));
                if (imageCorner2.getX() + MyRectPanel.rectification > centralPoint.getX() && imageCorner2.getY() + MyRectPanel.rectification > centralPoint.getY()
                        && imageCorner2.getX() - MyRectPanel.rectification < centralPoint.getX() && imageCorner2.getY() - MyRectPanel.rectification < centralPoint.getY()) {
                    g.drawLine((int) (prevPt.getX()), (int) (prevPt.getY()), (int) centralPoint.getX(), (int) centralPoint.getY());
                } else {
                    g.drawLine((int) (prevPt.getX()), (int) (prevPt.getY()), (int) (imageCorner2.getX()), (int) (imageCorner2.getY()));
                }
                selectedPoint = null;
            } else {
                boolean pointEncountered = false;
                mainloop: for (int i = 0; i < gates.size(); i++) {
                    if (!gates.get(i).equals(actualGate)) {
                        for (int j = 0; j < gates.get(i).getEntryPoints().size(); j++) {
                            if ((int) (imageCorner2.getX()) + MyRectPanel.rectification > (int) (gates.get(i).getEntryPoints(j).getCenterX())
                                    && (int) (imageCorner2.getX()) - MyRectPanel.rectification < (int) (gates.get(i).getEntryPoints(j).getCenterX())) {
                                g.drawLine((int) (prevPt.getX()), (int) (prevPt.getY()),
                                        (int) (gates.get(i).getEntryPoints(j).getCenterX()), (int) (prevPt.getY()));
                                pointEncountered = true;
                                break mainloop;
                            } else if ((int) (imageCorner2.getY()) + MyRectPanel.rectification > (int) (gates.get(i).getEntryPoints(j).getCenterY())
                                    && (int) (imageCorner2.getY()) - MyRectPanel.rectification < (int) (gates.get(i).getEntryPoints(j).getCenterY())) {
                                g.drawLine((int) (prevPt.getX()), (int) (prevPt.getY()),
                                        (int) (prevPt.getX()), (int) (gates.get(i).getEntryPoints(j).getCenterY()));
                                pointEncountered = true;
                                break mainloop;
                            }
                        }
                        for (int j = 0; j < gates.get(i).getExitPoints().size(); j++) {
                            if ((int) (imageCorner2.getX()) + MyRectPanel.rectification > (int) (gates.get(i).getExitPoints(j).getCenterX())
                                    && (int) (imageCorner2.getX()) - MyRectPanel.rectification < (int) (gates.get(i).getExitPoints(j).getCenterX())) {
                                g.drawLine((int) (prevPt.getX()), (int) (prevPt.getY()),
                                        (int) (gates.get(i).getExitPoints(j).getCenterX()), (int) (prevPt.getY()));
                                pointEncountered = true;
                                break mainloop;
                            } else if ((int) (imageCorner2.getY()) + MyRectPanel.rectification > (int) (gates.get(i).getExitPoints(j).getCenterY())
                                    && (int) (imageCorner2.getY()) - MyRectPanel.rectification < (int) (gates.get(i).getExitPoints(j).getCenterY())) {
                                g.drawLine((int) (prevPt.getX()), (int) (prevPt.getY()),
                                        (int) (prevPt.getX()), (int) (gates.get(i).getExitPoints(j).getCenterY()));
                                pointEncountered = true;
                                break mainloop;
                            }
                        }
                    }
                }
                if (!pointEncountered) {
                    if ((int) (prevPt.getX()) + MyRectPanel.rectification > (int) (imageCorner2.getX())
                            && (int) (prevPt.getX()) - MyRectPanel.rectification < (int) (imageCorner2.getX())) {
                        g.drawLine((int) (prevPt.getX()), (int) (prevPt.getY()),
                                (int) (prevPt.getX()), (int) (imageCorner2.getY()));
                    } else if ((int) (prevPt.getY()) + MyRectPanel.rectification > (int) (imageCorner2.getY())
                            && (int) (prevPt.getY()) - MyRectPanel.rectification < (int) (imageCorner2.getY())) {
                        g.drawLine((int) (prevPt.getX()), (int) (prevPt.getY()),
                                (int) (imageCorner2.getX()), (int) (prevPt.getY()));
                    } else {
                        g.drawLine((int) (prevPt.getX()), (int) (prevPt.getY()),
                                (int) (imageCorner2.getX()), (int) (imageCorner2.getY()));
                    }
                }
            }

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));

            selectedPoint = null;
            for (Gate gate : gates) {
                for (int j = 0; j < gate.numOfEntries(); j++) {
                    if (gate.getEntryPoints().get(j).contains(new Point((int) (imageCorner2.getX()), (int) (imageCorner2.getY())))) {
                        g.fillOval((int) gate.getEntryPoints().get(j).getX() - 2, (int) gate.getEntryPoints().get(j).getY() - 2,
                                (int) gate.getEntryPoints().get(j).getWidth() + 4, (int) gate.getEntryPoints().get(j).getHeight() + 4);
                        selectedPoint = gate.getEntryPoints().get(j);
                    } else {
                        g.fillOval((int) gate.getEntryPoints().get(j).getX(), (int) gate.getEntryPoints().get(j).getY(),
                                (int) gate.getEntryPoints().get(j).getWidth(), (int) gate.getEntryPoints().get(j).getHeight());
                    }
                }
                for (int j = 0; j < gate.numOfExits(); j++) {
                    if (gate.getExitPoints().get(j).contains(new Point((int) (imageCorner2.getX()), (int) (imageCorner2.getY())))) {
                        g.fillOval((int) gate.getExitPoints().get(j).getX() - 2, (int) gate.getExitPoints().get(j).getY() - 2,
                                (int) gate.getExitPoints().get(j).getWidth() + 4, (int) gate.getExitPoints().get(j).getHeight() + 4);
                        selectedPoint = gate.getExitPoints().get(j);
                    } else {
                        g.fillOval((int) gate.getExitPoints().get(j).getX(), (int) gate.getExitPoints().get(j).getY(),
                                (int) gate.getExitPoints().get(j).getWidth(), (int) gate.getExitPoints().get(j).getHeight());
                    }
                }
            }
        } else if (MyMenuButtons.mode == 1) { //Preview de las Logical Gates
            counter3 = prevCounter3;
            counter2 = prevCounter2;
            counter = prevCounter;

            SwitchGate(MyFrame.getTipo());

            try {
                if (isInFrame) {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                    boolean isInAnotherGate = false;
                    for (Gate gate : gates) {
                        if (imageCorner2.getX() + MyRectPanel.rectification > gate.getGateBounds().getX()
                                && imageCorner2.getX() - MyRectPanel.rectification < gate.getGateBounds().getX()) {
                            isInAnotherGate = true;
                            icon.paintIcon(this, g, (int) (gate.getGateBounds().getX()), (int) (imageCorner2.getY()));
                            break;
                        } else if (imageCorner2.getY() + MyRectPanel.rectification > gate.getGateBounds().getY()
                                && imageCorner2.getY() - MyRectPanel.rectification < gate.getGateBounds().getY()) {
                            isInAnotherGate = true;
                            icon.paintIcon(this, g, (int) (imageCorner2.getX()), (int) (gate.getGateBounds().getY()));
                            break;
                        }
                    }
                    if (!isInAnotherGate) {
                        icon.paintIcon(this, g, (int) (imageCorner2.getX()),(int) (imageCorner2.getY()));
                    }
                    repintado = true;
                    repaint();
                }
            } catch (NullPointerException e) {repintado = true;}
        } else if (MyMenuButtons.mode == 2) { //Preview de texto. Cambiar "Texto aquí" para modificar el texto de ejemplo
            counter3 = prevCounter3;
            counter2 = prevCounter2;
            counter = prevCounter;

            if (isInFrame) {
                g.setColor(MyFrame.thiscolor);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                g2d.setFont(new Font("Verdana", Font.BOLD, 14));
                g.drawString((int) (imageCorner2.getX()) + "x " + (int) (imageCorner2.getY()) + "y", (int) (imageCorner2.getX()), (int) (imageCorner2.getY()));
                repaint();
            }
        } else if (repintado) { //Para actualizar el paint
            counter3 = prevCounter3;
            counter2 = prevCounter2;
            counter = prevCounter;
            repintado = false;
        } else if (pointspaint) { //Pintar puntos de las gates
            for (Gate gate : gates) {
                for (int j = 0; j < gate.numOfEntries(); j++) {
                    g.fillOval((int) gate.getEntryPoints().get(j).getX(), (int) gate.getEntryPoints().get(j).getY(),
                            (int) gate.getEntryPoints().get(j).getWidth(), (int) gate.getEntryPoints().get(j).getHeight());
                }
                for (int j = 0; j < gate.numOfExits(); j++) {
                    g.fillOval((int) gate.getExitPoints().get(j).getX(), (int) gate.getExitPoints().get(j).getY(),
                            (int) gate.getExitPoints().get(j).getWidth(), (int) gate.getExitPoints().get(j).getHeight());
                }
            }
            counter3 = prevCounter3;
            counter2 = prevCounter2;
            counter = prevCounter;
            pointspaint = false;
        } else { //Salida por si no se pinta nada nuevo
            counter3 = prevCounter3;
            counter2 = prevCounter2;
            counter = prevCounter;
        }
        g2d.dispose();
    }

    public void paintLine(Graphics g, Point p1, Point p2) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3.0f));
        prevCounter = counter;
        prevCounter2 = counter2;
        prevCounter3 = counter3;

        while (counter > -1) {
            g.setColor(color.get(counter));
            g.drawLine((int) XPoint.get(counter).getX(), (int) XPoint.get(counter).getY(), (int) YPoint.get(counter).getX(), (int) YPoint.get(counter).getY());
            counter--;
        }
        while (counter2 > -1) {
            SwitchGate(type2.get(counter2));
            icon.paintIcon(this, g, (int) XimageCorner.get(counter2).getX(), (int) XimageCorner.get(counter2).getY());
            counter2--;
        }
        while (counter3 > -1) {
            g.setColor(color2.get(counter3));
            g2d.setFont(new Font("Verdana", Font.BOLD, 14));
            g.drawString(text.get(counter3), (int) YimageCorner.get(counter3).getX(), (int) YimageCorner.get(counter3).getY());
            counter3--;
        }

        counter3 = prevCounter3;
        counter2 = prevCounter2;
        counter = prevCounter + 1;
        g.setColor(MyFrame.thiscolor);
        color.add(MyFrame.thiscolor);

        Line2D thisLine = null;

        //Para enderezar la línea. Cambiar predeterminado en MyRectPanel
        if (selectedPoint != null) { //Hacer alineamiento con los puntos de las gates
            Point centralPoint = new Point((int) (selectedPoint.getX() + (selectedPoint.getWidth() / 2)), (int) (selectedPoint.getY() + (selectedPoint.getHeight() / 2)));
            if (p2.getX() + MyRectPanel.rectification > centralPoint.getX() && p2.getY() + MyRectPanel.rectification > centralPoint.getY()
                    && p2.getX() - MyRectPanel.rectification < centralPoint.getX() && p2.getY() - MyRectPanel.rectification < centralPoint.getY()) {
                thisLine = new Line2D.Double((int) (p1.getX()), (int) (p1.getY()), (int) centralPoint.getX(), (int) centralPoint.getY());
            } else {
                thisLine = new Line2D.Double((int) (p1.getX()), (int) (p1.getY()), (int) (p2.getX()), (int) (p2.getY()));
            }
            selectedPoint = null;
        } else {
            boolean pointEncountered = false;
            mainloop: for (int i = 0; i < gates.size(); i++) {
                if (!gates.get(i).equals(actualGate) && gates.get(i).getType() != 7 && gates.get(i).getType() != 9) {
                    for (int j = 0; j < gates.get(i).getEntryPoints().size(); j++) {
                        if ((int) (p2.getX()) + MyRectPanel.rectification > (int) (gates.get(i).getEntryPoints(j).getCenterX())
                                && (int) (p2.getX()) - MyRectPanel.rectification < (int) (gates.get(i).getEntryPoints(j).getCenterX())) {
                            thisLine = new Line2D.Double((int) (p1.getX()), (int) (p1.getY()),
                                    (int) (gates.get(i).getEntryPoints(j).getCenterX()), (int) (p1.getY()));
                            pointEncountered = true;
                            break mainloop;
                        } else if ((int) (p2.getY()) + MyRectPanel.rectification > (int) (gates.get(i).getEntryPoints(j).getCenterY())
                                && (int) (p2.getY()) - MyRectPanel.rectification < (int) (gates.get(i).getEntryPoints(j).getCenterY())) {
                            thisLine = new Line2D.Double((int) (p1.getX()), (int) (p1.getY()),
                                    (int) (p1.getX()), (int) (gates.get(i).getEntryPoints(j).getCenterY()));
                            pointEncountered = true;
                            break mainloop;
                        }
                    }
                    for (int j = 0; j < gates.get(i).getExitPoints().size(); j++) {
                        if ((int) (p2.getX()) + MyRectPanel.rectification > (int) (gates.get(i).getExitPoints(j).getCenterX())
                                && (int) (p2.getX()) - MyRectPanel.rectification < (int) (gates.get(i).getExitPoints(j).getCenterX())) {
                            thisLine = new Line2D.Double((int) (p1.getX()), (int) (p1.getY()),
                                    (int) (gates.get(i).getExitPoints(j).getCenterX()), (int) (p1.getY()));
                            pointEncountered = true;
                            break mainloop;
                        } else if ((int) (p2.getY()) + MyRectPanel.rectification > (int) (gates.get(i).getExitPoints(j).getCenterY())
                                && (int) (p2.getY()) - MyRectPanel.rectification < (int) (gates.get(i).getExitPoints(j).getCenterY())) {
                            thisLine = new Line2D.Double((int) (p1.getX()), (int) (p1.getY()),
                                    (int) (p1.getX()), (int) (gates.get(i).getExitPoints(j).getCenterY()));
                            pointEncountered = true;
                            break mainloop;
                        }
                    }
                }
            }
            if (!pointEncountered) {
                if ((int) (p1.getX()) + MyRectPanel.rectification > (int) (p2.getX())
                        && (int) (p1.getX()) - MyRectPanel.rectification < (int) (p2.getX())) {
                    thisLine = new Line2D.Double((int) (p1.getX()), (int) (p1.getY()),
                            (int) (p1.getX()), (int) (p2.getY()));
                } else if ((int) (p1.getY()) + MyRectPanel.rectification > (int) (p2.getY())
                        && (int) (p1.getY()) - MyRectPanel.rectification < (int) (p2.getY())) {
                    thisLine = new Line2D.Double((int) (p1.getX()), (int) (p1.getY()),
                            (int) (p2.getX()), (int) (p1.getY()));
                } else {
                    thisLine = new Line2D.Double((int) (p1.getX()), (int) (p1.getY()),
                            (int) (p2.getX()), (int) (p2.getY()));
                }
            }
        }

        g.drawLine((int) thisLine.getX1(), (int) thisLine.getY1(), (int) thisLine.getX2(), (int) thisLine.getY2());
        XPoint.add(new Point((int) thisLine.getX1(), (int) thisLine.getY1()));
        YPoint.add(new Point((int) thisLine.getX2(), (int) thisLine.getY2()));
        lines.add(thisLine);

        if (!hasGraphics) hasGraphics = true;

        lastAction.add(1);
        refreshRedo();
    }

    public void paintGate(Graphics g, int type, Point point) { //Método para pintar Logical Gates
        Graphics2D g2d = (Graphics2D) g;
        prevCounter = counter;
        prevCounter2 = counter2;
        prevCounter3 = counter3;

        while (counter > -1) {
            g.setColor(color.get(counter));
            g.drawLine((int) XPoint.get(counter).getX(), (int) XPoint.get(counter).getY(), (int) YPoint.get(counter).getX(), (int) YPoint.get(counter).getY());
            counter--;
        }
        while (counter2 > -1) {
            SwitchGate(type2.get(counter2));
            icon.paintIcon(this, g, (int) XimageCorner.get(counter2).getX(), (int) XimageCorner.get(counter2).getY());
            counter2--;
        }
        while (counter3 > -1) {
            g.setColor(color2.get(counter3));
            g2d.setFont(new Font("Verdana", Font.BOLD, 14));
            g.drawString(text.get(counter3), (int) YimageCorner.get(counter3).getX(), (int) YimageCorner.get(counter3).getY());
            counter3--;
        }

        counter3 = prevCounter3;
        counter2 = prevCounter2 + 1;
        counter = prevCounter;

        SwitchGate(type);

        boolean isInAnotherGate = false;
        for (int i = 0; i < gates.size(); i++) {
            if ((int) (point.getX()) + MyRectPanel.rectification > (int) (gates.get(i).getGateBounds().getX())
                    && (int) (point.getX()) - MyRectPanel.rectification < (int) (gates.get(i).getGateBounds().getX())) {
                isInAnotherGate = true;
                icon.paintIcon(this, g, (int) (gates.get(i).getGateBounds().getX()),(int) (point.getY()));
                XimageCorner.add(new Point((int) (gates.get(i).getGateBounds().getX()),(int) (point.getY())));
                gates.add(new Gate(new Rectangle2D.Double((int) (gates.get(i).getGateBounds().getX()),
                        (int) (point.getY()),
                        (int) (icon.getIconWidth()),
                        (int) (icon.getIconHeight())), type));
                break;
            } else if ((int) (point.getY()) + MyRectPanel.rectification > (int) (gates.get(i).getGateBounds().getY())
                    && (int) (point.getY()) - MyRectPanel.rectification < (int) (gates.get(i).getGateBounds().getY())) {
                isInAnotherGate = true;
                icon.paintIcon(this, g, (int) (point.getX()),(int) (gates.get(i).getGateBounds().getY()));
                XimageCorner.add(new Point((int) (point.getX()),(int) (gates.get(i).getGateBounds().getY())));
                gates.add(new Gate(new Rectangle2D.Double((int) (point.getX()),
                        (int) (gates.get(i).getGateBounds().getY()),
                        (int) (icon.getIconWidth()),
                        (int) (icon.getIconHeight())), type));
                break;
            }
        }
        if (!isInAnotherGate) {
            icon.paintIcon(this, g, (int) (point.getX()),(int) (point.getY()));
            XimageCorner.add(new Point((int) (point.getX()),(int) (point.getY())));
            gates.add(new Gate(new Rectangle2D.Double((int) (point.getX()),
                    (int) (point.getY()),
                    (int) (icon.getIconWidth()),
                    (int) (icon.getIconHeight())), type));
        }

        type2.add(type);
        lastAction.add(2);
        MyFrame.botone.get(9).setIcon(new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\src/media/gatemode.png"));
        MyFrame.botone.get(9).newIcon(new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\gatemode.png"));
        MyMenuButtons.mode = -1;

        if (!hasGraphics) hasGraphics = true;

        refreshRedo();
    }

    public void paintText(Graphics g, String texto, Point point) { //Método para pintar texto
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        prevCounter = counter;
        prevCounter2 = counter2;
        prevCounter3 = counter3;

        while (counter > -1) {
            g.setColor(color.get(counter));
            g.drawLine((int) XPoint.get(counter).getX(), (int) XPoint.get(counter).getY(), (int) YPoint.get(counter).getX(), (int) YPoint.get(counter).getY());
            counter--;
        }
        while (counter2 > -1) {
            SwitchGate(type2.get(counter2));
            icon.paintIcon(this, g, (int) XimageCorner.get(counter2).getX(), (int) XimageCorner.get(counter2).getY());
            counter2--;
        }
        while (counter3 > -1) {
            g.setColor(color2.get(counter3));
            g2d.setFont(new Font("Verdana", Font.BOLD, 14));
            g.drawString(text.get(counter3), (int) YimageCorner.get(counter3).getX(), (int) YimageCorner.get(counter3).getY());
            counter3--;
        }

        counter3 = prevCounter3 + 1;
        counter2 = prevCounter2;
        counter = prevCounter;

        g2d.setFont(new Font("Verdana", Font.BOLD, 14));
        g2d.setColor(MyFrame.thiscolor);
        g.drawString(texto, (int) (point.getX()),(int) (point.getY()));

        color2.add(MyFrame.thiscolor);
        text.add(texto);
        YimageCorner.add(new Point((int) (point.getX()),(int) (point.getY())));
        lastAction.add(3);

        if (!hasGraphics) hasGraphics = true;

        refreshRedo();
    }

    public void refreshRedo() { //Refresca los valores de memoria del redo
        nextXPoint.clear();
        nextYPoint.clear();
        nextXimageCorner.clear();
        nextYimageCorner.clear();
        nextType2.clear();
        nextText.clear();
        nextColor.clear();
        nextColor2.clear();

        nextGates.clear();
        nextLines.clear();

        nextAction.clear();
        nextAction.add(0);

        MyFrame.botone.get(5).setEnable(false);
        MyFrame.botone.get(4).setEnable(true);
        MyFrame.botone.get(3).setEnable(true);
    }

    public void KeyAction() { //Acción del back
        MyFrame.botone.get(5).setEnable(true);
        if (lastAction.size() - 2 == 0) MyFrame.botone.get(4).setEnable(false);
        switch (lastAction.get(lastAction.size() - 1)) {
            case 1:
                counter--;
                nextXPoint.add(XPoint.get(XPoint.size() - 1));
                nextYPoint.add(YPoint.get(YPoint.size() - 1));
                nextAction.add(lastAction.get(lastAction.size() - 1));
                nextColor.add(color.get(color.size() - 1));
                nextLines.add(lines.get(lines.size() - 1));

                XPoint.remove(XPoint.size() - 1);
                YPoint.remove(YPoint.size() - 1);
                lastAction.remove(lastAction.size() - 1);
                color.remove(color.size() - 1);
                lines.remove(lines.size() - 1);
                break;
            case 2:
                counter2--;
                nextXimageCorner.add(XimageCorner.get(XimageCorner.size() - 1));
                nextType2.add(type2.get(type2.size() - 1));
                nextAction.add(lastAction.get(lastAction.size() - 1));
                nextGates.add(gates.get(gates.size() - 1));

                XimageCorner.remove(XimageCorner.size() - 1);
                type2.remove(type2.size() - 1);
                lastAction.remove(lastAction.size() - 1);
                gates.remove(gates.size() - 1);
                break;
            case 3:
                counter3--;
                nextYimageCorner.add(YimageCorner.get(YimageCorner.size() - 1));
                nextText.add(text.get(text.size() - 1));
                nextAction.add(lastAction.get(lastAction.size() - 1));
                nextColor2.add(color2.get(color2.size() - 1));

                YimageCorner.remove(YimageCorner.size() - 1);
                text.remove(text.size() - 1);
                lastAction.remove(lastAction.size() - 1);
                color2.remove(color2.size() - 1);
                break;
            default:
                if (nextAction.size() - 1 == 0) MyFrame.botone.get(5).setEnable(false);
                JOptionPane.showMessageDialog(null, Locales.ERR_NACC_PREV, Locales.TITULOS_DIALOGS, JOptionPane.WARNING_MESSAGE);
                break;
        }
        repintado = true;
    }

    public void KeyAction2() { //Acción del redo
        MyFrame.botone.get(4).setEnable(true);
        switch (nextAction.get(nextAction.size() - 1)) {
            case 1:
                counter++;
                XPoint.add(nextXPoint.get(nextXPoint.size() - 1));
                YPoint.add(nextYPoint.get(nextYPoint.size() - 1));
                lastAction.add(nextAction.get(nextAction.size() - 1));
                color.add(nextColor.get(nextColor.size() - 1));
                lines.add(nextLines.get(nextLines.size() - 1));

                nextXPoint.remove(nextXPoint.size() - 1);
                nextYPoint.remove(nextYPoint.size() - 1);
                nextAction.remove(nextAction.size() - 1);
                nextColor.remove(nextColor.size() - 1);
                nextLines.remove(nextLines.size() - 1);
                break;
            case 2:
                counter2++;
                XimageCorner.add(nextXimageCorner.get(nextXimageCorner.size() - 1));
                type2.add(nextType2.get(nextType2.size() - 1));
                lastAction.add(nextAction.get(nextAction.size() - 1));
                gates.add(nextGates.get(nextGates.size() - 1));

                nextXimageCorner.remove(nextXimageCorner.size() - 1);
                nextType2.remove(nextType2.size() - 1);
                nextAction.remove(nextAction.size() - 1);
                nextGates.remove(nextGates.size() - 1);
                break;
            case 3:
                counter3++;
                YimageCorner.add(nextYimageCorner.get(nextYimageCorner.size() - 1));
                text.add(nextText.get(nextText.size() - 1));
                lastAction.add(nextAction.get(nextAction.size() - 1));
                color2.add(nextColor2.get(nextColor2.size() - 1));

                nextYimageCorner.remove(nextYimageCorner.size() - 1);
                nextText.remove(nextText.size() - 1);
                nextAction.remove(nextAction.size() - 1);
                nextColor2.remove(nextColor2.size() - 1);
                break;
            default:
                MyFrame.botone.get(4).setEnable(false);
                JOptionPane.showMessageDialog(null, Locales.ERR_NACC_POSI, Locales.TITULOS_DIALOGS, JOptionPane.WARNING_MESSAGE);
                break;
        }
        if (nextAction.size() == 1) {
            MyFrame.botone.get(5).setEnable(false);
        }
        repintado = true;
    }

    public void descargarArchivo() { //Creación del archivo
        if (counter == -1 && counter2 == -1 && counter3 == -1) {
            JOptionPane.showMessageDialog(null, Locales.INF_SCHEMPTY, Locales.TITULOS_DIALOGS, JOptionPane.WARNING_MESSAGE);
        } else {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(getUserDesktop()));
            fileChooser.setFileFilter(filter);
            fileChooser.setSelectedFile(new File(fileChooser.getCurrentDirectory() + "\\" + MyFrame.nombreProyecto));
            int result = fileChooser.showSaveDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile() + ".txt"))) {
                    int j = 0, q = 0, k = 0;
                    bufferedWriter.write("  <" + counter + "$" + counter2 + "$" + counter3 + ">");
                    for (Integer integer : lastAction) {
                        switch (integer) {
                            case 1:
                                bufferedWriter.write("<" + integer + "/" + (int) XPoint.get(j).getX() + "$" + (int) XPoint.get(j).getY() + "/" + (int) YPoint.get(j).getX() + "$" + (int) YPoint.get(j).getY() + "/" + color.get(j).getRed() + "$" + color.get(j).getGreen() + "$" + color.get(j).getBlue() + ">");
                                j++;
                                break;
                            case 2:
                                bufferedWriter.write("<" + integer + "/" + (int) XimageCorner.get(q).getX() + "$" + (int) XimageCorner.get(q).getY() + "/" + type2.get(q) + ">");
                                q++;
                                break;
                            case 3:
                                bufferedWriter.write("<" + integer + "/" + (int) YimageCorner.get(k).getX() + "$" + (int) YimageCorner.get(k).getY() + "/" + text.get(k) + "/" + color2.get(k).getRed() + "$" + color2.get(k).getGreen() + "$" + color2.get(k).getBlue() + ">");
                                k++;
                                break;
                        }
                        bufferedWriter.newLine();
                    }
                    bufferedWriter.close();
                    JOptionPane.showMessageDialog(null, Locales.SUCC_ARCH_CREAD, Locales.TITULOS_DIALOGS, JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, Locales.ERR_ARCH_CREAD, Locales.TITULOS_DIALOGS, JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, Locales.ERR_OP_CANCEL, Locales.TITULOS_DIALOGS, JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    @SuppressWarnings("StringConcatenationInLoop")
    public void leerArchivo() { //Lector de archivos
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:\\Users\\david\\Desktop"));
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(null);
        clear(1);

        if (result == JFileChooser.APPROVE_OPTION) {
            if (fileChooser.getSelectedFile().getAbsolutePath().endsWith(".txt")) {
                try {
                    Scanner fileReader = new Scanner(fileChooser.getSelectedFile());
                    ArrayList<String> lineas = new ArrayList<>();

                    if (confirmed) { //Comprueba si el usuario ha confirmado la sobrescritura
                        while(fileReader.hasNextLine()) lineas.add(fileReader.nextLine());
                        String contador, contador2, contador3;
                        int j, t;
                        for (int i = 0; i < lineas.size(); i++) {
                            j = 0;
                            if (i == 0) { //Crear contadores
                                contador = "";
                                while (lineas.get(i).charAt(j) != 60) {j++;} j++;
                                while (lineas.get(i).charAt(j) != 36) {contador += lineas.get(i).charAt(j); j++;} counter = Integer.parseInt(contador); contador = ""; j++;
                                while (lineas.get(i).charAt(j) != 36) {contador += lineas.get(i).charAt(j); j++;} counter2 = Integer.parseInt(contador); contador = ""; j++;
                                while (lineas.get(i).charAt(j) != 62) {contador += lineas.get(i).charAt(j); j++;} counter3 = Integer.parseInt(contador);
                            } else { //Crear objetos
                                while (lineas.get(i).charAt(j) != 60) {j++;} j++;
                                t = Integer.parseInt(String.valueOf(lineas.get(i).charAt(j))); j += 2;
                                lastAction.add(t);
                                switch (t) {
                                    case 1: contador = ""; contador2 = "";
                                        while (lineas.get(i).charAt(j) != 36) {
                                            contador += lineas.get(i).charAt(j); j++;
                                        } j++;
                                        while (lineas.get(i).charAt(j) != 47) {
                                            contador2 += lineas.get(i).charAt(j); j++;
                                        } j++; XPoint.add(new Point((int) (Integer.parseInt(contador)), (int) (Integer.parseInt(contador2))));
                                        contador = ""; contador2 = "";
                                        while (lineas.get(i).charAt(j) != 36) {
                                            contador += lineas.get(i).charAt(j); j++;
                                        } j++;
                                        while (lineas.get(i).charAt(j) != 47) {
                                            contador2 += lineas.get(i).charAt(j); j++;
                                        } j++; YPoint.add(new Point((int) (Integer.parseInt(contador)), (int) (Integer.parseInt(contador2))));
                                        for (int h = 0; i < XPoint.size(); i++) {
                                            lines.add(new Line2D.Double((int) (XPoint.get(h).getX()), (int) (XPoint.get(h).getY()), (int) (YPoint.get(h).getX()), (int) (YPoint.get(h).getY())));
                                        }
                                        contador = ""; contador2 = ""; contador3 = "";
                                        while (lineas.get(i).charAt(j) != 36) {
                                            contador += lineas.get(i).charAt(j); j++;
                                        } j++;
                                        while (lineas.get(i).charAt(j) != 36) {
                                            contador2 += lineas.get(i).charAt(j); j++;
                                        } j++;
                                        while (lineas.get(i).charAt(j) != 62) {
                                            contador3 += lineas.get(i).charAt(j); j++;
                                        } color.add(new Color(Integer.parseInt(contador), Integer.parseInt(contador2), Integer.parseInt(contador3))); break;
                                    case 2: contador = ""; contador2 = ""; contador3 = "";
                                        while (lineas.get(i).charAt(j) != 36) {
                                            contador += lineas.get(i).charAt(j); j++;
                                        } j++;
                                        while (lineas.get(i).charAt(j) != 47) {
                                            contador2 += lineas.get(i).charAt(j); j++;
                                        } j++; XimageCorner.add(new Point((int) (Integer.parseInt(contador)), (int) (Integer.parseInt(contador2))));
                                        while (lineas.get(i).charAt(j) != 62) {
                                            contador3 += lineas.get(i).charAt(j); j++;
                                        } type2.add(Integer.parseInt(contador3));
                                        SwitchGate(Integer.parseInt(contador3));
                                        Point corner = new Point((int) (Integer.parseInt(contador)), (int) (Integer.parseInt(contador2)));
                                        gates.add(new Gate(new Rectangle2D.Double(
                                                corner.getX(), corner.getY(),
                                                (int) (icon.getIconWidth()),
                                                (int) (icon.getIconHeight())), Integer.parseInt(contador3)));
                                        break;
                                    case 3: contador = ""; contador2 = ""; contador3 = "";
                                        while (lineas.get(i).charAt(j) != 36) {
                                            contador += lineas.get(i).charAt(j); j++;
                                        } j++;
                                        while (lineas.get(i).charAt(j) != 47) {
                                            contador2 += lineas.get(i).charAt(j); j++;
                                        } j++; YimageCorner.add(new Point((int) (Integer.parseInt(contador)), (int) (Integer.parseInt(contador2))));
                                        while (lineas.get(i).charAt(j) != 47) {
                                            contador3 += lineas.get(i).charAt(j); j++;
                                        } j++; text.add(contador3);
                                        contador = ""; contador2 = ""; contador3 = "";
                                        while (lineas.get(i).charAt(j) != 36) {
                                            contador += lineas.get(i).charAt(j); j++;
                                        } j++;
                                        while (lineas.get(i).charAt(j) != 36) {
                                            contador2 += lineas.get(i).charAt(j); j++;
                                        } j++;
                                        while (lineas.get(i).charAt(j) != 62) {
                                            contador3 += lineas.get(i).charAt(j); j++;
                                        } color2.add(new Color(Integer.parseInt(contador), Integer.parseInt(contador2), Integer.parseInt(contador3))); break;
                                }
                            }
                        }
                        repintado = true;
                        hasGraphics = true;
                        MyFrame.botone.get(4).setEnable(true);
                        MyFrame.botone.get(3).setEnable(true);
                        repaint();
                        JOptionPane.showMessageDialog(null, Locales.SUCC_LEER_ARCH, Locales.TITULOS_DIALOGS, JOptionPane.INFORMATION_MESSAGE);
                    } else JOptionPane.showMessageDialog(null, Locales.ERR_OP_CANCEL, Locales.TITULOS_DIALOGS, JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, Locales.ERR_LEER_ARCH, Locales.TITULOS_DIALOGS, JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, Locales.ERR_EXT_NVALID, Locales.TITULOS_DIALOGS, JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, Locales.ERR_OP_CANCEL, Locales.TITULOS_DIALOGS, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void repintaOptions(ArrayList<Integer> ultimacion, ArrayList<Point> XPunto,  ArrayList<Point> YPunto,
                               ArrayList<Point> XesquinaImagen, ArrayList<Point> YesquinaImagen, ArrayList<String> texto, ArrayList<Integer> tipo,
                               ArrayList<Color> Colorc, ArrayList<Color> Colorc2) { //Leer la memoria al rehacer el frame
        XPoint = XPunto; YPoint = YPunto; type2 = tipo;
        XimageCorner = XesquinaImagen; YimageCorner = YesquinaImagen; text = texto;
        lastAction = ultimacion; color = Colorc; color2 = Colorc2;

        repintado = true; //Llama al repintado en paintComponent
    }

    public void repintaNextOptions(ArrayList<Integer> siguienteacion, ArrayList<Point> nextXPunto,  ArrayList<Point> nextYPunto,
                                   ArrayList<Point> nextXesquinaImagen, ArrayList<Point> nextYesquinaImagen, ArrayList<String> nextTexto, ArrayList<Integer> nextTipo,
                                   ArrayList<Color> nextColorc, ArrayList<Color> nextColorc2) { //Leer memoria de las acciones siguientes al rehacer el frame
        nextXPoint = nextXPunto; nextYPoint = nextYPunto; nextType2 = nextTipo;
        nextXimageCorner = nextXesquinaImagen; nextYimageCorner = nextYesquinaImagen; nextText = nextTexto;
        nextAction = siguienteacion; nextColor = nextColorc; nextColor2 = nextColorc2;
    }

    public void repintaUniqueOptions(int contador, int contador2, int contador3, boolean backmem, boolean redomem, boolean delmem, boolean hasGraphicsmem) { //Leer memoria de los valores únicos al rehacer el frame
        counter = contador; counter2 = contador2; counter3 = contador3;
        MyFrame.botone.get(4).setEnable(backmem); MyFrame.botone.get(5).setEnable(redomem); MyFrame.botone.get(3).setEnable(delmem);
        hasGraphics = hasGraphicsmem;
    }

    public void SwitchGate(int pene) { //Detecta que gate está seleccionada para pintarla
        switch (pene) {
            case 0:
                icon = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\gates\\AND.png");
                break;
            case 1:
                icon = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\gates\\NAND.png");
                break;
            case 2:
                icon = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\gates\\OR.png");
                break;
            case 3:
                icon = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\gates\\NOR.png");
                break;
            case 4:
                icon = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\gates\\XOR.png");
                break;
            case 5:
                icon = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\gates\\XNOR.png");
                break;
            case 6:
                icon = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\gates\\NOT.png");
                break;
            case 7:
                icon = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\gates\\OFF.png");
                break;
            case 8:
                icon = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\gates\\LEDOFF.png");
                break;
            case 9:
                icon = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\gates\\ON.png");
                break;
            case 10:
                icon = new ImageIcon("C:\\Users\\david\\IdeaProjects\\GateCraft\\FCO\\src\\media\\gates\\LEDON.png");
                break;
            //Añadir nuevos elementos aquí (procurar que sean todos del mismo tamaño)
            default:
                JOptionPane.showMessageDialog(null, Locales.ERR_CODIGO, "GateCraft: Error", JOptionPane.ERROR_MESSAGE);
                MyMenuButtons.setMode(0);
                break;
        }
    }

    public String getUserDesktop() { //Buscar el directorio del escritorio del usuario
        String directorioUsuario = System.getProperty("user.home");
        String separadorDeArchivo = System.getProperty("file.separator");

        // Componer la ruta completa al escritorio
        return directorioUsuario + separadorDeArchivo + "Desktop";
    }

    public boolean isTextValid(String textox) { //Detecta si la entrada de texto es válida (Para evitar problemas con la lectura)
        int pitoloco = 0;
        for (int i = 0; i < textox.length(); i++) {
            if (textox.charAt(i) >= 48 && textox.charAt(i) <= 57) {
            } else if (textox.charAt(i) >= 65 && textox.charAt(i) <= 90) {
            } else if (textox.charAt(i) >= 97 && textox.charAt(i) <= 122) {
            } else if (textox.charAt(i) == 'Ç' || textox.charAt(i) == 'ç' || textox.charAt(i) == 'Ñ'
                    || textox.charAt(i) == 'ñ' || textox.charAt(i) == ' ') {} else {
                pitoloco++;
            }
        }
        return pitoloco == 0;
    }

    public void runCircuito() {
        if (MyMenuButtons.isRunning) {
            for (int i = 0; i < gates.size(); i++) gates.get(i).clearValues();

            ArrayList<Gate> parentGates = new ArrayList<>();
            for (int i = 0; i < type2.size(); i++) { //Busca los interruptores
                if (type2.get(i) == 7 || type2.get(i) == 9) {
                    gates.get(i).setType(type2.get(i));
                    parentGates.add(gates.get(i));
                }
            }

            ArrayList<Cables> cables = new ArrayList<>();
            for (int i = 0; i < parentGates.size(); i++) {
                for (int j = 0; j < lines.size(); j++) { //Busca líneas conectadas
                    if (parentGates.get(i).getExitPoints(0).intersectsLine(lines.get(j))) {
                        cables.add(new Cables(lines.get(j), parentGates.get(i).getType() == 9, parentGates.get(i)));
                        cables.get(cables.size() - 1).seguirCable(); //Las sigue
                    }
                }
            }

            boolean hasNext = true;
            ArrayList<Cables> cables2 = new ArrayList<>();
            int iterations = 0, results = 0;
            while (hasNext) {
                if (iterations > 0) {
                    for (int i = 1; i <= results; i++) {
                        cables2.add(cables.get(cables.size() - i));
                        cables2.get(cables2.size() - 1).seguirCable();
                    }
                    cables.clear(); cables.addAll(cables2); cables2.clear();
                    results = 0;
                    if (cables.isEmpty()) break;
                }
                for (int i = 0; i < gates.size(); i++) { //Busca puertas conectadas (AÑADIR SALIDA SI NO HAY + IMPLEMENTAR BUCLE)
                    if (type2.get(i) == 7 || type2.get(i) == 9) continue; //Comprueba que no es un interruptor
                    for (int j = 0; j < cables.size(); j++) {
                        for (int k = 0; k < gates.get(i).numOfEntries(); k++) {
                            if (gates.get(i).getEntryPoints(k).intersectsLine(cables.get(j).getCable())) {
                                if (gates.get(i).numOfEntries() == 1) {
                                    if (type2.get(i) == 6) {
                                        hasNext = false;
                                        for (int l = 0; l < lines.size(); l++) {
                                            if (gates.get(i).getExitPoints(0).intersectsLine(lines.get(l))) {
                                                hasNext = true;
                                                results++;
                                                cables.add(new Cables(lines.get(l), !cables.get(j).getValue(), gates.get(i)));
                                            }
                                        }
                                    } else {
                                        hasNext = false;
                                        for (int l = 0; l < lines.size(); l++) {
                                            if (gates.get(i).getExitPoints(0).intersectsLine(lines.get(l))) {
                                                hasNext = true;
                                                results++;
                                                cables.add(new Cables(lines.get(l), cables.get(j).getValue(), gates.get(i)));
                                            }
                                        }
                                        if (cables.get(j).getValue()) {
                                            type2.set(i, 10);
                                            gates.get(i).setType(10);
                                        } else {
                                            type2.set(i, 8);
                                            gates.get(i).setType(8);
                                        }
                                        repintado = true;
                                        repaint();
                                    }
                                } else if (gates.get(i).numOfEntries() == 2) {
                                    gates.get(i).addValues(cables.get(j).getValue());
                                    if (gates.get(i).getValuesLength() == 2) {
                                        hasNext = false;
                                        for (int l = 0; l < lines.size(); l++) {
                                            if (gates.get(i).getExitPoints(0).intersectsLine(lines.get(l))) {
                                                hasNext = true;
                                                results++;
                                                cables.add(new Cables(lines.get(l), switch (type2.get(i)) {
                                                    case 0 -> gates.get(i).getValues(0) == gates.get(i).getValues(1) && gates.get(i).getValues(1);
                                                    case 1 -> !(gates.get(i).getValues(0) == gates.get(i).getValues(1) && gates.get(i).getValues(1));
                                                    case 2 -> gates.get(i).getValues(0) || gates.get(i).getValues(1);
                                                    case 3 -> !(gates.get(i).getValues(0) || gates.get(i).getValues(1));
                                                    case 4 -> (gates.get(i).getValues(0) || gates.get(i).getValues(1))
                                                            && gates.get(i).getValues(0) != gates.get(i).getValues(1);
                                                    case 5 -> !((gates.get(i).getValues(0) || gates.get(i).getValues(1))
                                                            && gates.get(i).getValues(0) != gates.get(i).getValues(1));
                                                    default -> false;
                                                }, gates.get(i)));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                iterations++;
            }
        }
    }

    private class ClickListener extends MouseAdapter { //Detectores de clic del ratón
        @Override
        public void mousePressed(MouseEvent e) { //Punto de inicio de la línea
        }

        @Override
        public void mouseReleased(MouseEvent e) { //Creación de la línea
        }

        @Override
        public void mouseClicked(MouseEvent e) { //Creación de Gates y cuadros de Texto
            if (MyMenuButtons.mode == 1) { //Comprueba modo gate y la pinta
                paintGate(getGraphics(), MyFrame.tipo, new Point(e.getPoint()));
                repaint();
            } else if (MyMenuButtons.mode == 2) { //Comprueba modo texto y lo pinta
                String textoloco;
                textoloco = JOptionPane.showInputDialog(null, Locales.DIALOG_TEXT, Locales.TITULOS_DIALOGS, JOptionPane.INFORMATION_MESSAGE);
                if (textoloco != null && !textoloco.isEmpty()) { //Comprueba que no se haya salido del input y que no esté vacío
                    if (isTextValid(textoloco)) { //Comprueba si el texto es válido
                        paintText(getGraphics(), textoloco, e.getPoint());
                        repaint();
                    } else {
                        JOptionPane.showMessageDialog(null, Locales.ERR_INV_CHAR, Locales.TITULOS_DIALOGS , JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else if (MyMenuButtons.mode == -1) { //Método de cambiar de posición el interruptor
                for (int k = 0; k < gates.size(); k++) {
                    if (gates.get(k).getGateBounds().contains(e.getX(), e.getY())) {
                        MyFrame.clip.setMicrosecondPosition(0);
                        MyFrame.clip.start();
                        if (type2.get(k) == 7) {
                            type2.set(k, 9);
                        } else if (type2.get(k) == 9) {
                            type2.set(k, 7);
                        }
                        runCircuito();
                        repaint();
                        break;
                    }
                }
            } else if (MyMenuButtons.mode == 0) { //Comprueba si está el modo línea
                if (señal == 0) {
                    for (Gate gate : gates) {
                        for (int j = 0; j < gate.numOfEntries(); j++) {
                            if (gate.getEntryPoints().get(j).contains(new Point((int) (e.getX()), (int) (e.getY())))) {
                                prevPt = new Point((int) (gate.getEntryPoints().get(j).getCenterX()), (int) (gate.getEntryPoints().get(j).getCenterY()));
                                señal = 1;
                            }
                        }
                        for (int j = 0; j < gate.numOfExits(); j++) {
                            if (gate.getExitPoints().get(j).contains(new Point((int) (e.getX()), (int) (e.getY())))) {
                                prevPt = new Point((int) (gate.getExitPoints().get(j).getCenterX()), (int) (gate.getExitPoints().get(j).getCenterY()));
                                señal = 1;
                            }
                        }
                        if (señal == 1) {
                            actualGate = gate;
                            break;
                        }
                    }
                } else if (señal == 1) {
                    for (Gate gate : gates) {
                        for (int j = 0; j < gate.numOfEntries(); j++) {
                            if (gate.getEntryPoints().get(j).contains(new Point((int) (e.getX()), (int) (e.getY())))) {
                                paintLine(getGraphics(), prevPt, new Point((int) (gate.getEntryPoints().get(j).getCenterX()), (int) (gate.getEntryPoints().get(j).getCenterY())));
                                señal = 0;
                            }
                        }
                        for (int j = 0; j < gate.numOfExits(); j++) {
                            if (gate.getExitPoints().get(j).contains(new Point((int) (e.getX()), (int) (e.getY())))) {
                                paintLine(getGraphics(), prevPt, new Point((int) (gate.getExitPoints().get(j).getCenterX()), (int) (gate.getExitPoints().get(j).getCenterY())));
                                señal = 0;
                            }
                        }
                    }
                    if (señal == 1) {
                        paintLine(getGraphics(), prevPt, imageCorner2);
                        prevPt = new Point((int) (lines.get(lines.size() - 1).getX2()), (int) (lines.get(lines.size() - 1).getY2()));
                    }
                    repintado = true;
                    repaint();
                }
            }
        }

        //Comprobación de que el mouse está en el panel para evitar problemas con los previews

        @Override
        public void mouseEntered(MouseEvent e) { //Detecta cuando el mouse entra en el panel
            isInFrame = true;
            switch (MyMenuButtons.mode) {
                case -1: setCursor(new Cursor(Cursor.HAND_CURSOR)); break;
                case 0: setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR)); break;
                case 1: setCursor(new Cursor(Cursor.MOVE_CURSOR)); break;
                case 2: setCursor(new Cursor(Cursor.TEXT_CURSOR)); break;
                default: JOptionPane.showMessageDialog(null, Locales.ERR_CODIGO, Locales.TITULOS_DIALOGS, JOptionPane.ERROR_MESSAGE); break;
            }
        }

        @Override
        public void mouseExited(MouseEvent e) { //Detecta cuando el mouse sale del panel
            isInFrame = false;
        }
    }

    private class MotionListener implements MouseMotionListener { //Detectores de movimiento para los previews
        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (MyMenuButtons.mode == 1 || MyMenuButtons.mode == 2) { //Comprueba para generar el preview
                imageCorner2.setLocation(e.getPoint());
                repaint();
            } else if (MyMenuButtons.mode == 0) {
                imageCorner2.setLocation(e.getPoint());
                for (int i = 0; i < gates.size(); i++) {
                    if (gates.get(i).getGateBounds().contains(new Point((int) (e.getX()), (int) (e.getY())))) {
                        pointspaint = true;
                        repaint();
                    }
                }
                if (!pointspaint) repintado = true;
                repaint();
            }
        }
    }
}

