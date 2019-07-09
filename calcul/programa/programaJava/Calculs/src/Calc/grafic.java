package Calc;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import static java.lang.Double.isFinite;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
public class grafic extends JPanel {
private static final long serialVersionUID = 1L;
public static final int BORDER_GAP = 5;
static Color cyan = Color.CYAN;
static Color color = Color.LIGHT_GRAY;
static Color gris = Color.DARK_GRAY;
static Color negre = Color.BLACK;
static Color magenta = Color.magenta;
static Color blau = Color.BLUE;
static Color verd = new Color(0,120,250);
static  int punt = 2;
public static  double maxY = 0.0,minY = 0.0,maxX = 0.0,minX = 0.0;
public static boolean componentListenerBol=false;
public static int[] puntsAssoc=new int[2];
Graphics2D g2;
public static double[][] punts;
public static int puntminim=-1,puntmaxim=-1;
static List<Point> puntsGraf;
public grafic() {}
public void paintComponent(Graphics g) {
    try{
      super.paintComponent(g);
      g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      double xScale = ((double) getWidth() - 2 * BORDER_GAP) / ( maxX -minX);
      double yScale = ((double) getHeight() - 2 * BORDER_GAP) /( maxY -minY);
      puntsGraf = new ArrayList<Point>();
      int con=-1;
      double min=Cur.maxim;
      double max=-Cur.maxim;
      puntminim=-1;puntmaxim=-1;
      int[] puntAss=new int[2];
      for (int i = 0; i < punts.length; i++) {
          if(isFinite(punts[i][0])&&isFinite(punts[i][1])){
                con++;
                Cur.abcOrd[i][0] = (int) ((punts[i][0]-minX) * xScale + BORDER_GAP);
                Cur.abcOrd[i][1] = (int) ((maxY - punts[i][1]) * yScale + BORDER_GAP);
                puntsGraf.add(new Point(Cur.abcOrd[i][0], Cur.abcOrd[i][1]));
                if(min>punts[i][1]){min=punts[i][1];puntminim=con;}
                if(max<=punts[i][1]){max=punts[i][1];puntmaxim=con;}
                if(puntsAssoc[0]>-1){
                    if(puntsAssoc[0]==i)puntAss[0]=con;
                    if(puntsAssoc[1]>-1){if(puntsAssoc[1]==i)puntAss[1]=con;}
                    else puntAss[1]=-1;
                }
                else puntAss[0]=-1;
         }
      }
      g2.drawLine(getWidth()/2, getHeight() - BORDER_GAP,getWidth()/2,BORDER_GAP);
      g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, getWidth() - BORDER_GAP, getHeight() - BORDER_GAP);
      Stroke oldStroke = g2.getStroke();
      if(Func.unirPunts){
        g2.setColor(cyan);
        for (int i = 0; i < puntsGraf.size() - 1; i++) {
           int x1 = puntsGraf.get(i).x;
           int y1 = puntsGraf.get(i).y;
           int x2 = puntsGraf.get(i + 1).x;
           int y2 = puntsGraf.get(i + 1).y;
           g2.drawLine(x1, y1, x2, y2); 
        }
      }
      g2.setStroke(oldStroke);      
      g2.setColor(blau);
      for (int i = 0; i < puntsGraf.size(); i++) {
            int x = puntsGraf.get(i).x - punt / 2;
            int y = puntsGraf.get(i).y - punt / 2;;
            int ovalW = punt;
            int ovalH = punt;
            g2.fillOval(x, y, ovalW, ovalH);
      }
      if(puntminim>-1){
        g2.setStroke(oldStroke);      
        g2.setColor(magenta);
            try{
                int x = puntsGraf.get(puntminim).x - punt ;
                int y = puntsGraf.get(puntminim).y - punt ;
                int ovalW = 2*punt;
                int ovalH = 2*punt;
                g2.fillOval(x, y, ovalW, ovalH);
            }
            catch(IndexOutOfBoundsException e){}
     }
      if(puntmaxim>-1){
        g2.setStroke(oldStroke);      
        g2.setColor(verd);
            try{
                int x = puntsGraf.get(puntmaxim).x - punt ;
                int y = puntsGraf.get(puntmaxim).y - punt ;
                int ovalW = 2*punt;
                int ovalH = 2*punt;
                g2.fillOval(x, y, ovalW, ovalH);
            }
            catch(IndexOutOfBoundsException e){}
     }
      if(puntAss[0]>-1){
        g2.setStroke(oldStroke);      
        g2.setColor(negre);
        try{
            int x = puntsGraf.get(puntAss[0]).x - punt ;
            int y = puntsGraf.get(puntAss[0]).y - punt ;
            int ovalW = 2*punt;
            int ovalH = 2*punt;
            g2.fillOval(x, y, ovalW, ovalH);
        }
        catch(IndexOutOfBoundsException e){}
     }
      if(Cur.contadorDerivada>0){
            puntsGraf = new ArrayList<Point>();
            for (int i = 0; i < Cur.abcOrdD.length; i++) {
                if(isFinite(Cur.derivada[i][3])&&isFinite(Cur.derivada[i][0])){
                Cur.abcOrdD[i][1] = (int) ((maxY - Cur.derivada[i][3]) * yScale + BORDER_GAP);
                Cur.abcOrdD[i][0]= (int) ((Cur.derivada[i][0]-minX) * xScale + BORDER_GAP);
                puntsGraf.add(new Point(Cur.abcOrdD[i][0], Cur.abcOrdD[i][1]));
                }
            }
            oldStroke = g2.getStroke();
            g2.setColor(color);
            for (int i = 0; i < puntsGraf.size() - 1; i++) {
               int x1 = puntsGraf.get(i).x;
               int y1 = puntsGraf.get(i).y;
               int x2 = puntsGraf.get(i + 1).x;
               int y2 = puntsGraf.get(i + 1).y;
               g2.drawLine(x1, y1, x2, y2); 
            }
            g2.setStroke(oldStroke);      
            g2.setColor(gris);
            for (int i = 0; i < puntsGraf.size(); i++) {
                  int x = puntsGraf.get(i).x - punt / 2;
                  int y = puntsGraf.get(i).y - punt / 2;;
                  int ovalW = punt;
                  int ovalH = punt;
                  g2.fillOval(x, y, ovalW, ovalH);
            }
      }
      if(puntAss[0]>-1){
        g2.setStroke(oldStroke);      
        g2.setColor(negre);
        if(puntAss[1]>-1){
        try{
            if(puntAss[1]>-1){
            int x = puntsGraf.get(puntAss[1]).x - punt ;
            int y = puntsGraf.get(puntAss[1]).y - punt ;
            int ovalW = 2*punt;
            int ovalH = 2*punt;
            g2.fillOval(x, y, ovalW, ovalH);
            }
        }
        catch(IndexOutOfBoundsException e){}
        }
     }
      repaint();
    }
    catch(Exception e){}
    this.setSize(this.getWidth(), this.getHeight()+1);this.setSize(this.getWidth(), this.getHeight()-1);
   }
    public static void reinici(){
        punts=new double[0][0];
        puntsGraf= new ArrayList<Point>();
        
    }
}