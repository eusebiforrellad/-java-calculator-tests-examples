package Calc;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
public class font extends JFrame {
    private static final long serialVersionUID = 1L;
    DisplayPanel dP;
    String[] tipusFon = { "Plain", "Bold", "Italic", "Bold&Italic" };
    int[] tipusFonI = { Font.PLAIN, Font.BOLD, Font.ITALIC,  Font.BOLD | Font.ITALIC };
    String[] tamanyS = {"8","9","10","11","12","13","14","16","18"};
    JComboBox fontsBox, 
    estilBox = new JComboBox(tipusFon), 
    tamanyBox  = new JComboBox(tamanyS);
    static String nom="rFonts";
    static String tipus = "Arial";
    static int estil = Font.PLAIN;
    static int tamany = 12;
    public static JButton enter=new JButton("enter");
    static boolean obert=false;
public font() {
    if(obert)return;
    obert=true;
    fontIni("");
    Container container = getContentPane();
    dP = new DisplayPanel(); container.add(dP);
    JPanel controlPanel = new JPanel();controlPanel.setLayout(new GridLayout(1, 2));
    JPanel controlPanel1 = new JPanel();controlPanel1.setLayout(new GridLayout(1,3));
    fontsBox= new JComboBox(dP.fFN);
    fontsBox.setSelectedItem(tipus); fontsBox.setSelectedIndex(3);
    tamanyBox.setSelectedItem(tamany);  tamanyBox.setSelectedIndex(4);
    fontsBox.addActionListener(new ComboBoxListener());
    estilBox.addActionListener(new ComboBoxListener());
    tamanyBox.addActionListener(new ComboBoxListener());
    enter=new JButton("enter");
    controlPanel1.add(estilBox);controlPanel1.add(tamanyBox);controlPanel1.add(enter);controlPanel.add(fontsBox);controlPanel.add(controlPanel1);
    container.add(BorderLayout.SOUTH, controlPanel);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    pack();
    setLocation(0,0);setSize(450,90);setVisible(true);
    enter.addActionListener((ActionEvent e) -> {
        fontIni(tipus+" "+estil+" "+tamany);
     });
    addWindowListener (new WindowAdapter() {  @Override public void windowClosing (WindowEvent e) {obert=false; } }); 
}
 static void fontIni(String s){
    if(s.equals("")){ tipus = "arial"; estil =  Font.PLAIN;tamany = 12;return;}
    try{
       String[] t=splitPan.matriudeFuncioCompleta(s); 
       tipus = t[0];
       estil =  Integer.parseInt(t[1]);
       tamany = Integer.parseInt(t[2]);
    }
    catch(Exception e){ tipus = "arial"; estil =  Font.PLAIN;tamany = 12;}
   //fonts();
 }
 static void fonts(){
    Font fo = new Font(tipus, estil, tamany);
    UIManager.put("ToolTip.font",new FontUIResource(tipus, estil, tamany));
    Func.textDeco[0].setFont(fo);
    Func.textDeco[1].setFont(fo);
    Func.textDeco1.setFont(fo);
    Func.JFuncio.setFont(fo);
    taulaI.cela.setFont(fo);taulaI.tl.setFont(fo);taulaI.tlF.setFont(fo);
    taulaD.cela.setFont(fo);taulaD.tl.setFont(fo);
    taulaV.cela.setFont(fo);taulaV.tl.setFont(fo);
    taulaP.cela.setFont(fo);taulaP.tl.setFont(fo);
    taulaC.cela.setFont(fo);taulaC.tl.setFont(fo);
    taulaF.cela.setFont(fo);taulaF.tl.setFont(fo);
    Func.font();Cur.font();taulaF.font();
    taulaC.font();taulaP.font();taulaV.font();taulaI.font();taulaD.font();teclat.font();
    int i=taulaC.tl.getRowCount(); for(int j=0;j<i;j++)taulaC.tl.setRowHeight( j, font.tamany+6);
    i=taulaD.tl.getRowCount(); for(int j=0;j<i;j++)taulaD.tl.setRowHeight( j, font.tamany+6);
    i=taulaI.tl.getRowCount(); for(int j=0;j<i;j++)taulaI.tl.setRowHeight( j, font.tamany+6);
    i=taulaI.tlF.getRowCount(); for(int j=0;j<i;j++)taulaI.tlF.setRowHeight( j, font.tamany+6); 
    i=taulaF.tl.getRowCount(); for(int j=0;j<i;j++)taulaF.tl.setRowHeight( j, font.tamany+6);
    i=taulaV.tl.getRowCount(); for(int j=0;j<i;j++)taulaV.tl.setRowHeight( j, font.tamany+6);
    i=taulaP.tl.getRowCount(); for(int j=0;j<i;j++)taulaP.tl.setRowHeight( j, font.tamany+6);
    for( i = 0; i <teclat. jl.length; i++)for(int j = 0; j <teclat. jl[0].length; j++) teclat. jl[i][j].setFont(fo);
 }
  static String guardar(){
     String s="";
     if(!tipus.equals("arial")||estil!=Font.PLAIN||tamany!=12){s="("+tipus+" "+estil+" "+tamany+")";}
     return s;
 }
  class ComboBoxListener implements ActionListener { public void actionPerformed(ActionEvent e) {
      JComboBox tempBox = (JComboBox) e.getSource();
      if (tempBox.equals(fontsBox)) {tipus = (String) tempBox.getSelectedItem();dP.repaint();}
      else if (tempBox.equals(estilBox)){ estil = tipusFonI[tempBox.getSelectedIndex()];dP.repaint();}
      else if (tempBox.equals(tamanyBox)) {tamany = Integer.parseInt((String) tempBox.getSelectedItem());dP.repaint();}
  } }
    class DisplayPanel extends JPanel {
      private static final long serialVersionUID = 1L;
      String[] fFN;
      public DisplayPanel() {     
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        fFN = ge.getAvailableFontFamilyNames();
      }
      public void update(Graphics g) {g.clearRect(0, 0, getWidth(), getHeight()); paintComponent(g); }
      public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setFont(new Font(tipus, estil, tamany));
        g2D.drawString(" ABCDEFGHIJklmnopqrstuvwxyz0123456789+-*/", 25, 25);
      }
    }
    public static void inici() { javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {new font();} });
    }
    public static void main(String[] args) { javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {new font();}  });
    }
}