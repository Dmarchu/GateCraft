import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MyLanguagePanel extends JPanel{
    private boolean redomem, backmem, delmem, hasGraphicsMem;
    private int countermem, counter2mem, counter3mem, modemem;
    private ArrayList<Point> XPointmem, YPointmem, XimageCornermem, YimageCornermem, nextXPointmem, nextYPointmem, nextXimageCornermem, nextYimageCornermem;
    private ArrayList<Integer> type2mem, lastActionmem, nextType2mem, nextActionmem;
    private ArrayList<String> textmem, nextTextmem;
    private ArrayList<Color> colormem, color2mem, nextColormem, nextColor2mem;

    public MyLanguagePanel() {
        JComboBox<String> lenguaje = new JComboBox<>(); lenguaje.setFocusable(false);
        lenguaje.addItem(Locales.es); lenguaje.addItem(Locales.en); lenguaje.setBounds(0,0, 100,30);
        lenguaje.setBackground(new Color(50,50,50)); lenguaje.setForeground(Color.white);

        if (Main.locale.getLocalisation().equals("Locale_ES")) {
            lenguaje.setSelectedItem(Locales.es);
        } else {
            lenguaje.setSelectedItem(Locales.en);
        }

        lenguaje.addActionListener(e -> {
            memOptions();
            if (lenguaje.getSelectedItem().toString().equals(Locales.es)) {
                Main.frame.dispose();
                Main.locale = new Locales("Locale_ES");
                Main.frame = new MyFrame(new Dimension(1550, 862));
            } else if (lenguaje.getSelectedItem().toString().equals(Locales.en)) {
                Main.frame.dispose();
                Main.locale = new Locales("Locale_EN");
                Main.frame = new MyFrame(new Dimension(1550, 862));
            }
            redoOptions();
        });

        this.setBounds(270,60,100,30); this.setVisible(false); this.setLayout(null);
        this.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2, true));
        this.add(lenguaje); this.setFocusable(false);
    }

    public void redoOptions() {
        MyFrame.panel.repintaUniqueOptions(countermem, counter2mem, counter3mem, backmem, redomem, delmem, hasGraphicsMem);
        MyFrame.panel.repintaNextOptions(nextActionmem, nextXPointmem, nextYPointmem, nextXimageCornermem, nextYimageCornermem,
                nextTextmem, nextType2mem, nextColormem, nextColor2mem);
        MyFrame.panel.repintaOptions(lastActionmem, XPointmem, YPointmem, XimageCornermem, YimageCornermem,
                textmem, type2mem, colormem, color2mem);
        MyMenuButtons.setMode(modemem);
    }

    public void memOptions() { //Almacena los datos del frame en memoria
        countermem = MyPanel.counter; counter2mem = MyPanel.counter2; counter3mem = MyPanel.counter3;
        redomem = MyFrame.botone.get(5).isEnable(); backmem = MyFrame.botone.get(4).isEnable(); delmem = MyFrame.botone.get(3).isEnable(); hasGraphicsMem = MyPanel.hasGraphics;

        nextActionmem = MyPanel.nextAction; nextXPointmem = MyPanel.nextXPoint; nextYPointmem = MyPanel.nextYPoint; nextXimageCornermem = MyPanel.nextXimageCorner;
        nextYimageCornermem = MyPanel.nextYimageCorner; nextTextmem = MyPanel.nextText; nextType2mem = MyPanel.nextType2;
        nextColormem = MyPanel.nextColor; nextColor2mem = MyPanel.nextColor2;

        lastActionmem = MyPanel.lastAction;
        XPointmem = MyPanel.XPoint; YPointmem = MyPanel.YPoint;
        XimageCornermem = MyPanel.XimageCorner; YimageCornermem = MyPanel.YimageCorner;
        textmem = MyPanel.text; type2mem = MyPanel.type2; colormem = MyPanel.color; color2mem = MyPanel.color2; modemem = MyMenuButtons.mode;
    }
}
