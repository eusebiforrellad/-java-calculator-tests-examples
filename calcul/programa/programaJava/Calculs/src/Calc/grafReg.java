package Calc;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
public class grafReg extends JPanel {
    private static final long serialVersionUID = 1L;
    public static final int BORDER_GAP = 5;
    public static Color magenta = Color.magenta;
    public static Color blue = Color.blue;
    static  int punt0 = 4;
    static  int punt1 = 3;
    public static  double maxY = 20.0;
    public static  double minY = 0.0;
    public static  double maxX = 20.0;
    public static  double minX = 0.0;
    public static boolean componentListenerBol=false;
    Graphics2D g2;
    public static ArrayList<Double> puntsx;
    public static ArrayList<Double> puntsy;
    static List<Point> puntsGraf;
    public static int[][] ratlla;
    public static int[][] ratllaCop;
    public grafReg() {}
    @Override
    public void paintComponent(Graphics g) {
        try{
          super.paintComponent(g);
          g2 = (Graphics2D)g;
          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
          double xScale = ((double) getWidth() - 2 * BORDER_GAP) / ( maxX -minX);
          double yScale = ((double) getHeight() - 2 * BORDER_GAP) /( maxY -minY);
          g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP,BORDER_GAP,BORDER_GAP);
          g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, getWidth() - BORDER_GAP, getHeight() - BORDER_GAP);
          puntsGraf = new ArrayList<Point>();
          for (int i = 0; i < puntsx.size(); i++) {
              try{
                     int x1 = (int) ((puntsx.get(i)-minX) * xScale + BORDER_GAP);
                     int y1 = (int) ((maxY - puntsy.get(i)) * yScale + BORDER_GAP);
                     puntsGraf.add(new Point(x1, y1));
              }
              catch(java.lang.NullPointerException e){}
              catch(java.lang.IndexOutOfBoundsException e){}
          }
          Stroke oldStroke = g2.getStroke();
          g2.setStroke(oldStroke);      
          g2.setColor(blue);
          for (int i = 0; i < puntsGraf.size()/2; i++) {//calculats blau
             int x = puntsGraf.get(i).x - punt0/2;
             int y = puntsGraf.get(i).y - punt0/2;
             g2.fillRect(x, y, punt0, punt0);
          }
          g2.setStroke(oldStroke);
          g2.setColor(magenta);
          for (int i = puntsGraf.size()/2; i < puntsGraf.size(); i++) {//fixos magenta
             int x = puntsGraf.get(i).x - punt1/2;
             int y = puntsGraf.get(i).y - punt1/2;
             g2.fillRect(x, y, punt1, punt1);
          }
          g2.setStroke(oldStroke);
          g2.setColor(Color.PINK);
          if(Func.ratllaBol1){
                int j=puntsGraf.size()/2;
                for(int i=0;i<ratlla.length;i++)if(ratlla[i][0]!=ratlla[i][1]){
                    int x1 = puntsGraf.get(ratlla[i][0]+j).x;
                    int y1 = puntsGraf.get(ratlla[i][0]+j).y;
                    int x2 = puntsGraf.get(ratlla[i][1]+j).x;
                    int y2 = puntsGraf.get(ratlla[i][1]+j).y;
                    g2.drawLine(x1, y1, x2, y2); 
                }
          }
          g2.setStroke(oldStroke);
          g2.setColor(Color.CYAN);
          if(Func.ratllaBol){
                for(int i=0;i<ratlla.length;i++)if(ratlla[i][0]!=ratlla[i][1]){
                    int x1 = puntsGraf.get(ratlla[i][0]).x;
                    int y1 = puntsGraf.get(ratlla[i][0]).y;
                    int x2 = puntsGraf.get(ratlla[i][1]).x;
                    int y2 = puntsGraf.get(ratlla[i][1]).y;
                    g2.drawLine(x1, y1, x2, y2); 
                }
          }
          this.setSize(this.getWidth(), this.getHeight()+1);this.setSize(this.getWidth(), this.getHeight()-1);
       }
       catch(Exception e){}
    }
}