package Calc;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import javax.swing.*;

public class graficF extends JPanel {
private static final long serialVersionUID = 1L;
public static final int BORD = 5;
public static double reduccio=0.2;
static Color gris = Color.LIGHT_GRAY;
static Color negre = Color.BLACK;
public static double segmentZ;
public static boolean[] tipusGrafic=new boolean[3];
public static boolean componentListenerBol=false;
static Graphics2D g2;
public static int[][] puntsGrafics;
public static boolean actiu;
public static boolean inicia;
static int[] sequencia;//permet representar els punt mes llunyans abans que els mes propers i si el color es opac no es veuen els posteriors
public static double[][] punts;
public static Object [][] puntsTFColor;//conte els colors de les coordenades x,y,z el mateix nomre de fileres que puntsTF 2 columnes la primera la cadena que defineix el punt x y o z la segona el color
public static int[][] grandariaPunt;
public static int[][] igrandariaPunt;
public static int[][] historiagrandariaPunt;
static int[][][] historia;//el primer index es el nombre de punts, el segon index es el nombre de valors historics el tercer el nombre de coordenades 2 o 3
static boolean[][] historiaBol;
static int[] historiaIntervalTemps;
static Instant[] tempsHistoria;
static int[] idxHistoria;
static int[] ihistorial;
static int iHistorial;

static double minCoordenada;
public static boolean graficFet;
int cont;
public graficF() {}
public void paintComponent(Graphics g) {
      super.paintComponent(g);
      actiu=true;
      int w=getWidth();
      int h=getHeight();
      if(taulaF.stopBucle){actiu=false;return;}
      g2 = (Graphics2D)g;
      float f=1f;
      if(tipusGrafic[2])f=0.7f;
      AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, f);
      if(tipusGrafic[2]||taulaF.PARA)f=0.1f;
      AlphaComposite ac1 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, f);
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setColor(negre);
      int punx0=BORD;//partinferior esquerra
      int puny0=BORD;
      int punx1=w - BORD;//partinferior esquerra
      int puny1=h - BORD;//partinferior esquerr
      if(taulaF.coordenades==1){
         if(taulaF.puntsTFBol[0][0]){
             puny0=getHeight()/2;
             g2.drawLine(punx0, puny0, punx1, puny0);
         }
         if(taulaF.puntsTFBol[0][1]){
             punx0=getWidth()/2;
             g2.drawLine(punx0, puny0, punx0, puny1);
         }
      }
      if(taulaF.coordenades>1){
            g2.drawLine(punx0, puny0, punx1, puny0);
            g2.drawLine(punx0, puny1, punx1, puny1);
            g2.drawLine(punx0, puny0, punx0, puny1);
            g2.drawLine(punx1, puny0, punx1, puny1);
      }
      if(taulaF.coordenades>2){
            g2.setColor(gris);
            int fondariax=(int)((w-2*BORD)*reduccio);
            int fondariay=(int)((h-2*BORD)*reduccio);
            int punfx0=punx0+fondariax;
            int punfy0=puny0+fondariay;
            int punfx1=punx1-fondariax;
            int punfy1=puny1-fondariay;
            g2.drawLine(punfx0, punfy0, punfx1, punfy0);
            g2.drawLine(punfx0, punfy1, punfx1, punfy1);
            g2.drawLine(punfx0, punfy0, punfx0, punfy1);
            g2.drawLine(punfx1, punfy0, punfx1, punfy1);
            g2.drawLine(punx0, puny0, punfx0, punfy0);
            g2.drawLine(punx0, puny1, punfx0, punfy1);
            g2.drawLine(punx1, puny0, punfx1, punfy0);
            g2.drawLine(punx1, puny1, punfx1, punfy1);
      }
      if(punts==null||punts.length==0){actiu=false;return;}
      while(!graficFet&&!taulaF.stopBucle){ try{Thread.sleep(20L);} catch( InterruptedException e) {}};
      minCoordenada=w;
      if(w>h)minCoordenada=h;
      try{
          sequencia=new int[punts.length];for(int i=0;i<punts.length;i++)sequencia[i]=i;
          if(taulaF.coordenades>2){
            for(int i=0;i<punts.length-1;i++)for(int j=i+1;j<punts.length;j++)if(punts[sequencia[i]][2]<punts[sequencia[j]][2]){int k=sequencia[i];sequencia[i]=sequencia[j];sequencia[j]=k;}
        }
        puntsGrafics=new int[punts.length][3];
        double xS = (w - 4 * BORD) / (taulaF.limPunts[0][2]);
        double yS = (h - 4 * BORD) /(taulaF.limPunts[1][2]);
        double zS=0;if(taulaF.coordenades>2)zS = (h - 2 * BORD) /(taulaF.limPunts[2][2]);
        for (int i = 0; i < punts.length; i++) {
              if(taulaF.coordenades>2)puntsGrafics[i][2] = (int) ((taulaF.limPunts[2][2] - punts[i][2]) * zS + BORD);
              puntsGrafics[i][0]= (int) ((punts[i][0]-taulaF.limPunts[0][0]) * xS+ 2*BORD);
              puntsGrafics[i][1] = (int) ((taulaF.limPunts[1][1] - punts[i][1]) * yS + 2*BORD);
        }
        g2.setComposite(ac1);
        for (int i = 0; i < puntsGrafics.length; i++) if(!taulaF.puntsGrafFinits[i]){
            int k=sequencia[i];
            if(historiaIntervalTemps[k]>0&&taulaF.coordenades>1&&inicia){
            Instant ara = Instant.now();
            if(taulaF.pausaGraf==0&&ChronoUnit.MILLIS.between(tempsHistoria[k],ara)/10>historiaIntervalTemps[k]){
                historia[k][idxHistoria[k]][0]=puntsGrafics[k][0];
                historia[k][idxHistoria[k]][1]=puntsGrafics[k][1];
                if(tipusGrafic[1])historiagrandariaPunt[k][idxHistoria[k]]=igrandariaPunt[k][0];
                historiaBol[k][idxHistoria[k]]=true;
                tempsHistoria[k]=ara;
                idxHistoria[k]++;if(idxHistoria[k]>=ihistorial[k])idxHistoria[k]=0;
            }
            g2.setColor((Color) puntsTFColor[k][1]);
            if(tipusGrafic[1]){g2.setColor(new Color((g2.getColor().getRed()+510)/3,(g2.getColor().getGreen()+510)/3,(g2.getColor().getBlue()+510)/3));}
            for (int j = idxHistoria[k]-1;j>0;j--)if(historiaBol[k][j]&&historiaBol[k][j-1]){
                if(tipusGrafic[1])g2.setStroke(new BasicStroke(historiagrandariaPunt[k][j]));
                g2.drawLine(historia[k][j][0], historia[k][j][1], historia[k][j-1][0], historia[k][j-1][1]);
            }
            if(idxHistoria[k]!=0&&historiaBol[k][0]&&historiaBol[k][ihistorial[k]-1]){
                if(tipusGrafic[1])g2.setStroke(new BasicStroke(historiagrandariaPunt[k][ihistorial[k]-1]));
                g2.drawLine(historia[k][ihistorial[k]-1][0], historia[k][ihistorial[k]-1][1], historia[k][0][0], historia[k][0][1]);
            } 
            for (int j=ihistorial[k]-1;j>idxHistoria[k];j--)if(historiaBol[k][j]&&historiaBol[k][j-1]){
                if(tipusGrafic[1])g2.setStroke(new BasicStroke(historiagrandariaPunt[k][j]));
                g2.drawLine(historia[k][j][0], historia[k][j][1], historia[k][j-1][0], historia[k][j-1][1]);
            }
            if(tipusGrafic[1])g2.setStroke(new BasicStroke(1));
          }
        }
        g2.setComposite(ac);
        if(taulaF.coordenades>2){
            for (int i = 0; i < puntsGrafics.length; i++) if(!taulaF.puntsGrafFinits[i]){
                int k=sequencia[i];
                g2.setColor((Color) puntsTFColor[k][1]);
                if(tipusGrafic[0])g2.drawLine(puntsGrafics[k][0], puntsGrafics[k][1], puntsGrafics[k][0], puntsGrafics[k][2]); 
                g2.setColor((Color) puntsTFColor[k][1]);
                if(tipusGrafic[0])g2.drawLine(puntsGrafics[k][0]-igrandariaPunt[k][0]/2, puntsGrafics[k][2], puntsGrafics[k][0]+igrandariaPunt[k][0]/2, puntsGrafics[k][2]);
           }
        } 
        for (int i = 0; i < puntsGrafics.length; i++) if(!taulaF.puntsGrafFinits[i]){
            int k=sequencia[i]; 
            g2.setColor((Color) puntsTFColor[k][1]);
            g2.fillOval(puntsGrafics[k][0]-igrandariaPunt[k][0]/2, puntsGrafics[k][1]-igrandariaPunt[k][0]/2, igrandariaPunt[k][0], igrandariaPunt[k][0]);
        }
        if(taulaF.PARA){
            g2.setColor(negre);g2.setStroke(new BasicStroke(2));
            for(int i=0;i<taulaF.arr_xyPos.size();i++){
                int  j=10;
                if(i==0&&!tipusGrafic[3])j=20;
                double[] xy=taulaF.arr_xyPos.get(i);
                g2.drawLine((int)xy[0]-j, (int)xy[1],(int)xy[0]+j, (int)xy[1]);
                g2.drawLine((int)xy[0], (int)xy[1]-j,(int)xy[0], (int)xy[1]+j);
            }
            g2.setStroke(new BasicStroke(1));
        }
      }
      catch(Exception e){}
    this.setSize(this.getWidth(), this.getHeight()+1);this.setSize(this.getWidth(), this.getHeight()-1);
    actiu=false;
   }
    public static void historia(int i){
        g2.setColor((Color) puntsTFColor[i][1]);
        for (int j = idxHistoria[i]-1;j>0;j--)if(historiaBol[i][j]&&historiaBol[i][j-1]){
            if(tipusGrafic[1])g2.setStroke(new BasicStroke(historiagrandariaPunt[i][j]));
            g2.drawLine(historia[i][j][0], historia[i][j][1], historia[i][j-1][0], historia[i][j-1][1]);
        }
        if(idxHistoria[i]!=0&&historiaBol[i][0]&&historiaBol[i][ihistorial[i]-1]){
            if(tipusGrafic[1])g2.setStroke(new BasicStroke(historiagrandariaPunt[i][ihistorial[i]-1]));
            g2.drawLine(historia[i][ihistorial[i]-1][0], historia[i][ihistorial[i]-1][1], historia[i][0][0], historia[i][0][1]);
        } 
        for (int j=ihistorial[i]-1;j>idxHistoria[i];j--)if(historiaBol[i][j]&&historiaBol[i][j-1]){
            if(tipusGrafic[1])g2.setStroke(new BasicStroke(historiagrandariaPunt[i][j]));
            g2.drawLine(historia[i][j][0], historia[i][j][1], historia[i][j-1][0], historia[i][j-1][1]);
        }
        if(tipusGrafic[1])g2.setStroke(new BasicStroke(1));
    }
    public static void reinici(){
        punts=new double[0][0];
        puntsGrafics=new int[0][0];
    }
}
