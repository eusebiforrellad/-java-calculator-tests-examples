package Calc;
/**
 * @author eusebi
 */
import java.awt.*;
import javax.swing.*;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import static java.lang.Double.NaN;
import static java.lang.Double.isFinite;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.text.StyleConstants;
public class splitPan extends JPanel{
    static Cal calc;
    static int iSplits=12;
    static int[][] splitDimInt=new int[iSplits][3];
    static boolean splitBol=false;
    public static JSplitPane[] split=new JSplitPane[iSplits];
    public static taulaP tTP;
    public static taulaV tTV;
    public static taulaC tTC;
    public static taulaI tTI;
    public static taulaD tTD;
    public static taulaF tTF;
    static Instant previous,previous1;
    public static final String TAB = "\t";
    public static String FIL=System.getProperty("line.separator");
    static String ruta,rutaG;
    static Component controllingFrame;
    static String sFuncio;
    static String funcio="";
    static NumberFormat fn = NumberFormat.getInstance(Locale.GERMAN);
    static NumberFormat fnE = NumberFormat.getInstance(Locale.GERMAN);
    public static String[] funcionsPar;//les Func.funcions ordenades mes parentesi (
    static int [][] limits;
    static Double dVO_[];
    static String cadenaParNumerics;
    String[] vars;
    static teclat tecl;
    static String posVar;
    public static String colorText;
    public static boolean calculUnic;
    static int[] columnesC,columnesP,columnesV,columnesD,columnesI,columnesIF,columnesF;
    public static String ampladaCols="ampladaColumnes:  ";
    public static boolean supIDBol;
    static boolean[] simBolF=new boolean[0],simBolPC0=new boolean[0],simBolPC1=new boolean[0],simBolPC2=new boolean[0],simBolPV=new boolean[0],
    simBolV=new boolean[0],
    simBolI0=new boolean[0],simBolI1=new boolean[0],simBolD=new boolean[0];//booleans associats als simbols de les taules si tru vol dir que s'utitlitza
    static String[] simbolsFuncio;
    static String[] simbolsAbcissa;
    static String[] simbolsOrdenada;
    static String[] simbolsParGraf0;
    static String[] simbolsParGraf1;
    static String[] simbolsParGraf2;
    static ArrayList<String[]>arr_simbolsFuncioTaulaF;
    static ArrayList<String[]>arr_simbolsFuncioTaulaFReduida;
    static ArrayList<String[]>arr_simbolsSumF;
    static ArrayList<String[]>arr_simbolsSum;
    static ArrayList<String[]>arr_suportsimbolsSum;
    public static String sVO[];
    public static Double dVO[];
    static ArrayList<String[]> arr_sVO_Der;
    static ArrayList<Double[]> arr_dVO_Der;
    static ArrayList<String[]> arr_sVO_taulaF;
    static ArrayList<double[]> arr_dVO_taulaF;
    static ArrayList<String[]>arr_sVO_SumF;
    static ArrayList<String[]>arr_sVO_Sum;
    static ArrayList<Double[]>arr_dVO_SumF;
    static ArrayList<Double[]>arr_dVO_Sum;
    static ArrayList<String[]>arr_simbolsDer;
    public static String menor=""+(char)171;
    public static String major=""+(char)187;
    static int contaStatic;
    static String arxiuTextI=FIL+">>===>  ",arxiuTextF="  <===<<"+FIL,arxiuText="=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=", apartat="-------------------------------------------------------------------------"+FIL;
    static String arxiutxt;
    static String[] funcSup;
    static String[][] matriuPsup;
    static String[][] matriuVsup;
    static boolean matriuPsupBol=false;
    static boolean matriuVsupBol=false;
    static Random rand=new Random(0);
    boolean[]bmouse;
    boolean selecBol;    
    public static Random[] random;
    public splitPan() {
        tecl=new teclat();
        tTV=new taulaV();
        tTP=new taulaP();
        tTI=new taulaI();
        tTD=new taulaD();
        tTF=new taulaF();
        JScrollPane funcio;
        funcio = new JScrollPane(Func.JFuncio);
        funcio.setMinimumSize(new Dimension(35, 35));
        if(Func.tipusFuncioBol)try{taulaF.fixaAmplCol(columnesF);}catch(Exception e){}
        try{taulaC.fixaAmplCol(columnesC);}catch(Exception e){}
        try{taulaP.fixaAmplCol(columnesP);}catch(Exception e){}
        try{taulaV.fixaAmplCol(columnesV);}catch(Exception e){}
        try{taulaD.fixaAmplCol(columnesD);}catch(Exception e){}
        try{taulaI.fixaAmplCol(columnesI);}catch(Exception e){}
        if(taulaV.varstaulaV!=null){Cur.pan0.removeAll();Cur.pan01.removeAll();Cur.pan1.removeAll();}
        Cur.pan1=null;
        Cur.pan1=new JPanel(new GridLayout(5, 2));
        calc=new Cal();
        Cur.curs=new Cur();
        Cur.pan1.setVisible(false);
        split[11] = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,tTI, tTD);
        split[5] = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,tTV, split[11]);
        split[9] = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tTP,split[5]);//darrera fila taulas
        if(!Func.tipusFuncioBol){
            split[4] = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tTC,split[9]);//darrera fila taulas
            split[6] = new JSplitPane(JSplitPane.VERTICAL_SPLIT,Cur.pan0, Cur.pan01);//grafics vertical
        }
        else {
            taulaF.pan=new JPanel(new GridLayout(0, 1));
            taulaF.graf=new graficF();
            taulaF.pan.add(taulaF.graf);
            taulaF.pan.addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent e){
                    if(e.getButton() == MouseEvent.BUTTON1){
                        taulaF.PARA=!taulaF.PARA;
                        if(taulaF.PARA){//el primer click para el bucle i reinicia taulaF.arr_xyPos pero previament si no hi ha hagut una seleccio de punts previa reinicia taulaF.relaciopuntsSeleccionats
                            if(taulaF.arr_xyPos.size()>0){selecBol=true;}//si hi ha seleccio previa d'algun punt es true
                            else {selecBol=false;taulaF.relaciopuntsSeleccionats=new ArrayList();}
                            taulaF.hihaSeleccioPuntsBol=false;taulaF.arr_xyPos=new ArrayList();bmouse=new boolean [graficF.puntsGrafics.length];
                        }
                        else {//el segon click 
                            if(taulaF.hihaSeleccioPuntsBol||selecBol)taulaF.contadorBucle=0;// si s'han seleccionat punts reinicia el contador de bucle bucle
                            if(selecBol){
                                if(selecBol&&Func.ampliarInfo)Func.append(0,FIL);
                                for(int i=0;i<taulaF.relaciopuntsSeleccionats.size();i++){
                                    int[] m=taulaF.relaciopuntsSeleccionats.get(i);
                                    if(m[1]!=1){taulaF.relaciopuntsSeleccionats.remove(i);i--;}
                                    else{m[1]=0;taulaF.relaciopuntsSeleccionats.set(i,m);}
                                }
                                for(int i=0;i<taulaF.relaciopuntsSeleccionats.size()-1;i++){
                                    for(int j=i;j<taulaF.relaciopuntsSeleccionats.size();j++){
                                    int[] m=taulaF.relaciopuntsSeleccionats.get(i);
                                    int[] m1=taulaF.relaciopuntsSeleccionats.get(j);
                                    if (m[2]>m1[2]){taulaF.relaciopuntsSeleccionats.set(i,m1);taulaF.relaciopuntsSeleccionats.set(j,m);}
                                }}
                                for(int i=0;i<taulaF.relaciopuntsSeleccionats.size();i++){
                                    int[] m=taulaF.relaciopuntsSeleccionats.get(i);
                                }
                            }
                        }
                    }
                    else if(taulaF.PARA&&e.getButton() == MouseEvent.BUTTON3){ //si el bucle esta parat cada cop que es fa click boto esquerra
                        try{
                            taulaF.hihaSeleccioPuntsBol=true;
                            int[] m=new int[3];
                            double coor[]=new double[3+taulaF.puntsGraf[0].length];
                            coor[0]=e.getX();coor[1]=e.getY();
                            double min=Cur.maxim;
                            String s="";
                            int l=-1,l1=-1;
                            for(int j=0;j<graficF.puntsGrafics.length;j++)if(!bmouse[j]){
                                double d0=coor[0]-graficF.puntsGrafics[j][0];
                                double d1=coor[1]-graficF.puntsGrafics[j][1];
                                double d=Math.sqrt(d0*d0+d1*d1);
                                if(d<min){min=d;l=j;l1=j;}
                            }
                            if(l==-1)return;
                            bmouse[l]=true;
                            if(selecBol){//ja hi ha una primera seleccio de punts
                                if(l<taulaF.relaciopuntsSeleccionats.size()) m=taulaF.relaciopuntsSeleccionats.get(l);
                                else{
                                    taulaF.hihaSeleccioPuntsBol=false;
                                    taulaF.arr_xyPos=new ArrayList();
                                    taulaF.relaciopuntsSeleccionats=new ArrayList();
                                    taulaF.contadorBucle=0;
                                    taulaF.PARA=!taulaF.PARA;
                                    if(Func.ampliarInfo)Func.append(0,"el proc"+Func.rB.getString("e_")+"s gr"+Func.rB.getString("a")+"fic s'ha reiniciat"+FIL);
                                    return;
                                }
                                m[2]=taulaF.arr_xyPos.size();l=m[0];m[1]=1;taulaF.relaciopuntsSeleccionats.set(l1,m);
                            }
                            else{//no hi ha seleccio previa i es tracta del primer seleccio de punts
                                if(bmouse[l]){m[0]=l;m[1]=taulaF.relaciopuntsSeleccionats.size();taulaF.relaciopuntsSeleccionats.add(m);}
                            }
                            coor[2]=l;coor[0]=graficF.puntsGrafics[l1][0];coor[1]=graficF.puntsGrafics[l1][1];//les possicions en el grafi no les referides al global
                            if(l>=0){
                                for(int k=0;k<taulaF.puntsGraf[0].length;k++){coor[k+3]=taulaF.puntsGraf[l][k];s+=coor[k+3]+"; ";}//inclou les possicions globals
                                taulaF.arr_xyPos.add(coor);
                                if(Func.ampliarInfo)Func.append(0,"possici"+Func.rB.getString("o_")+" al gr"+Func.rB.getString("a")+"fic XY: "+coor[0]+"; "+coor[1]+"; punt nº: "+l+ "; coordenades (xyz)"+s+FIL);
                            }
                        }
                        catch(Exception e1){
                            Func.append(1,"error de lectura: ");Func.append(0,"al gr"+Func.rB.getString("a")+"fic XYZ el proc"+Func.rB.getString("e_")+"s pot continuar (stop run): "+FIL);
                             e1.printStackTrace();
                        }
                }}public void mouseEntered(MouseEvent e) {}public void mouseExited(MouseEvent e) {}public void mousePressed(MouseEvent e) {}public void mouseReleased(MouseEvent e) {}
        });}
        if(!Func.tipusFuncioBol){
            split[7] = new JSplitPane(JSplitPane.VERTICAL_SPLIT,Func.text2, Cur.pan1);//text i butons dreta
            split[3] = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,split[6], split[7]);
        }
        split[10]= new JSplitPane(JSplitPane.VERTICAL_SPLIT,Func.text1[1],Func.text1[0]);//ajunta el text de l'esquerra amb el buttons de la esquerra
        if(Func.mostrarTeclat)split[8] = new JSplitPane(JSplitPane.VERTICAL_SPLIT, split[10],tecl.sp);
        else split[8] = new JSplitPane(JSplitPane.VERTICAL_SPLIT, split[10],Func.pan);//ajunta el text de l'esquerra amb el buttons de la esquerra
        if(!Func.tipusFuncioBol){
            split[2] = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, split[8],split[3]);
            split[1] = new JSplitPane(JSplitPane.VERTICAL_SPLIT, split[2],split[4]);//ajunta les tres parts centrals amb la darrera fila de taules
            split[0] = new JSplitPane(JSplitPane.VERTICAL_SPLIT, funcio,split[1]);//funcio superior i resta
        }
        else{
            split[2] = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, split[8],taulaF.pan);
            split[1] = new JSplitPane(JSplitPane.VERTICAL_SPLIT, split[2],split[9]);//ajunta les parts centrals amb la darrera fila de taules
            split[0] = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tTF,split[1]);
        }
        if(!splitBol)valIni();
        for(int i=0;i<iSplits;i++){
            if(split[i]!=null){
                split[i].setOneTouchExpandable(true);
                split[i].setDividerLocation(splitDimInt[i][0]);
                split[i].setDividerSize(6);
                split[i].setBackground(Color.LIGHT_GRAY);
                split[i].setPreferredSize(new Dimension(splitDimInt[i][1],splitDimInt[i][2]));
            }
        }
    }
    private static void valIni(){//0:divisio; 1:amplada; 2 altura
        splitDimInt[0][0]=50;splitDimInt[0][1]=947;splitDimInt[0][2]=546;
        splitDimInt[1][0]=355;splitDimInt[1][1]=943;splitDimInt[1][2]=488;
        splitDimInt[2][0]=312;splitDimInt[2][1]=939;splitDimInt[2][2]=353;
        splitDimInt[3][0]=391;splitDimInt[3][1]=619;splitDimInt[3][2]=349;
        splitDimInt[4][0]=157;splitDimInt[4][1]=939;splitDimInt[4][2]=125;
        splitDimInt[5][0]=226;splitDimInt[5][1]=627;splitDimInt[5][2]=117;
        splitDimInt[6][0]=171;splitDimInt[6][1]=289;splitDimInt[6][2]=345;
        splitDimInt[7][0]=192;splitDimInt[7][1]=320;splitDimInt[7][2]=345;
        splitDimInt[8][0]=168;splitDimInt[8][1]=310;splitDimInt[8][2]=349;
        splitDimInt[9][0]=139;splitDimInt[9][1]=774;splitDimInt[9][2]=121;
        splitDimInt[10][0]=2;splitDimInt[10][1]=306;splitDimInt[10][2]=167;
        splitDimInt[11][0]=203;splitDimInt[11][1]=393;splitDimInt[11][2]=113;
    }
    private static void valIni(int i){
        for(int j=i;j<iSplits;j++){splitDimInt[j][0]=1000;splitDimInt[j][1]=10;splitDimInt[j][2]=10;}
    }
    private static String seleccioText(String s){
        try{
            String c=arxiuTextI+s+arxiuTextF;
            int ini=arxiutxt.indexOf(c)+c.length();
            int fi=arxiutxt.indexOf(arxiuText,ini);
            s=arxiutxt.substring(ini,fi);
            if(s==null)s="";
            arxiutxt=arxiutxt.substring(0,ini-c.length())+arxiutxt.substring(fi+arxiuText.length());
        }
        catch(Exception  e){return "";}
        return s;
    }
    public static void splitC(){
        String s=seleccioText("splitPan");
        try{
            int j=s.lastIndexOf(TAB);
            int i=s.lastIndexOf(FIL);
            Func.posy=Rodo(Integer.valueOf(s.substring(j+1,i))*Func.longY/10000);s=s.substring(0,j);
            j=s.lastIndexOf(FIL);
            Func.posx=Rodo(Integer.valueOf(s.substring(j+1))*Func.longX/10000);s=s.substring(0,j+1);
            if(Func.posx<0)Func.posx=0;
            if(Func.posy<0)Func.posy=0;
            if(Func.posx>Func.longX*0.95)Func.posx=(int)(Func.longX*0.95);
            if(Func.posy>Func.longY*0.95)Func.posy=(int)(Func.longY*0.95);
        }
    	catch(Exception e){Func.posx=0;Func.posy=0;}
        int i=0;
        try{
            for(i=0;i<iSplits;i++){
                int j=s.indexOf(TAB);
                splitDimInt[i][0]=Integer.valueOf(s.substring(0,j));s=s.substring(j+1,s.length());
                j=s.indexOf(TAB);
                splitDimInt[i][1]=Integer.valueOf(s.substring(0,j));s=s.substring(j+1,s.length());
                j=s.indexOf(FIL);
                splitDimInt[i][2]=Integer.valueOf(s.substring(0,j));s=s.substring(j+FIL.length(),s.length());
            }
    	}
    	catch(java.lang.StringIndexOutOfBoundsException e){
            if(i>0)valIni(i);
            else valIni();
        }
        splitBol=true;
    }
    private static boolean taulaFC(){
        String s=seleccioText("taulaF");
        String s1=">=actiu=<"+FIL;
        if(s.startsWith(s1)){
            Func.tipusFuncioBol=true;
            s=s.substring(s1.length());
        }
        else Func.tipusFuncioBol=false;
        int i=s.indexOf(apartat);
        try{
            s1=s.substring(0,i);
            if(s1.contains(ampladaCols)){
                int k=s1.indexOf(FIL);
                columnesF=ampladaCols(s1.substring(0,k));
                s1=s1.substring(k+FIL.length());
            }
            taulaF.matriu=StringAMatriu(s1,false);
        }catch(Exception e){return false;}
        s=s.substring(i+apartat.length());
        try{
           taulaF.intColors=StringAMatriu(s,false);
           taulaF.lectColors();
        }catch(Exception e){return false;}
        return true;
    }
    private static boolean carregaPans(){
        splitC();
        if(!taulaFC())return false;
        Func.infoTexts=seleccioText("info_TextDeco");
        Func.infoTexts1=seleccioText("info_TextDeco1");
        sFuncio=seleccioText("funcio");
        Func.JFuncio.setText(sFuncio);
        String s=seleccioText("taulaP");
        try{
            if(s.contains(ampladaCols)){
                    int k=s.indexOf(FIL);
                    columnesP=ampladaCols(s.substring(0,k));
                    s=s.substring(k+FIL.length());
                }
            taulaP.matriu=StringAMatriu(s,false);
        }catch(Exception e){return false;}
        s=seleccioText("taulaV");
    	try{
            if(s.contains(ampladaCols)){
                    int k=s.indexOf(FIL);
                    columnesV=ampladaCols(s.substring(0,k));
                    s=s.substring(k+FIL.length());
                }
            taulaV.matriu=StringAMatriu(s,false);
        }catch(Exception e){return false;}
        if(!Func.tipusFuncioBol){
            s=seleccioText("taulaC");
            try{
                if(s.contains(ampladaCols)){
                    int k=s.indexOf(FIL);
                    columnesC=ampladaCols(s.substring(0,k));
                    s=s.substring(k+FIL.length());
                }
                taulaC.matriu=StringAMatriu(s,false);
            }catch(Exception e){return false;}
            try{
                taulaC.matriu[2][1]=taulaC.carregaPartaulaC(taulaC.matriu[2][1]);
                taulaC.treuSimbols(taulaC.matriu[2][1].toLowerCase().trim());
                for(int j=0;j<taulaC.textFilera.length;j++){
                    taulaC.tl.setValueAt((Object)taulaC.textFilera[j], j, 0);
                }
            }
            catch(Exception e){}
        }
        s=seleccioText("taulaI");
        int i=s.indexOf(apartat);
        try{
            String s1=s.substring(0,i);
            if(s1.contains(ampladaCols)){
                    int k=s1.indexOf(FIL);
                    columnesIF=ampladaCols(s1.substring(0,k));
                    s1=s1.substring(k+FIL.length());
                }
            taulaI.matriuFunc=StringAMatriu(s1,false);
        }catch(Exception e){return false;}
        s=s.substring(i+apartat.length());
        try{
            if(s.contains(ampladaCols)){
                    int k=s.indexOf(FIL);
                    columnesI=ampladaCols(s.substring(0,k));
                    s=s.substring(k+FIL.length());
                }
            taulaI.matriu=StringAMatriu(s,false);
        }catch(Exception e){return false;}
        s=seleccioText("taulaD");
        try{
            if(s.contains(ampladaCols)){
                int k=s.indexOf(FIL);
                columnesD=ampladaCols(s.substring(0,k));
                s=s.substring(k+FIL.length());
            }
            taulaD.matriu=StringAMatriu(s,false);
        }catch(Exception e){return false;}
        s=seleccioText("simbolsTeclat");
        try{
            if(s.startsWith("longText: ")){
                try{teclat.longText=Integer.parseInt(s.substring(10,11));}
                catch(Exception e){teclat.longText=5;}
                s=s.substring(s.indexOf(FIL)+FIL.length());
            }
            teclat.matriu=StringAMatriu(s,false);
            teclat.teclatFil=teclat.matriu.length;teclat.teclatCol=teclat.matriu[0].length;
            teclat.jl=new JLabel[teclat.teclatFil][teclat.teclatCol];
        }catch(Exception e){return false;}
        return true;
    }
    public static void guardarPans(File f){
        arxiutxt=arxiuTextI+"funcio"+arxiuTextF+Func.JFuncio.getText()+FIL+arxiuText;
        arxiutxt+=arxiuTextI+"taulaF"+arxiuTextF;
        if(Func.tipusFuncioBol)arxiutxt+=">=actiu=<"+FIL;
        arxiutxt+=taulaF.lectAmplCol()+ajuntarMatriu(taulaF.matriu)+apartat;
        taulaF.lectintColors();
        arxiutxt+=ajuntarMatriu(taulaF.intColors)+arxiuText;
        arxiutxt+=arxiuTextI+"taulaP"+arxiuTextF+taulaP.lectAmplCol()+ajuntarMatriu(taulaP.matriu)+arxiuText;
        arxiutxt+=arxiuTextI+"taulaV"+arxiuTextF+taulaV.lectAmplCol()+ajuntarMatriu(taulaV.matriu)+arxiuText; 
        String s0=taulaC.guardaPartaulaC();
        arxiutxt+=arxiuTextI+"taulaC"+arxiuTextF+taulaC.lectAmplCol()+ajuntarMatriu(taulaC.matriu)+arxiuText;
        taulaC.tl.setValueAt((Object)s0,1,1);
        arxiutxt+=arxiuTextI+"taulaI"+arxiuTextF+ajuntarMatriu(taulaI.matriuFunc)+apartat+taulaI.lectAmplCol()+ajuntarMatriu(taulaI.matriu)+arxiuText;
        arxiutxt+=arxiuTextI+"taulaD"+arxiuTextF+taulaD.lectAmplCol()+ajuntarMatriu(taulaD.matriu)+arxiuText;
        arxiutxt+=arxiuTextI+"simbolsTeclat"+arxiuTextF+"longText: "+teclat.longText+FIL+ajuntarMatriu(teclat.matriu)+arxiuText;
        arxiutxt+=splitG();
        String s1=Func.textDeco[Func.saltBol].getText();
        if(!s1.equals("")){
            arxiutxt+=arxiuTextI+"info_TextDeco"+arxiuTextF;
            Func.style = Func.textDeco[Func.saltBol].getStyledDocument();
            Color cl=Color.BLUE;
            Color c;
            String s=colorText+"b"+FIL;
            int ini=0;
            s1="";
            for(int i=0; i<Func.style.getLength();i++) {
                c=StyleConstants.getForeground(Func.style.getCharacterElement(i).getAttributes());
                if(!c.equals(cl)){
                    if(c.equals(Color.BLUE)){s=colorText+"b"+FIL;}
                    else if(c.equals(Color.RED)){s=colorText+"v"+FIL;}
                    else if(c.equals(Color.BLACK)){s=colorText+"n"+FIL;}
                    try{s1=Func.style.getText(ini, i-ini);}
                    catch(Exception e){}
                    arxiutxt+=s1+s;
                    ini=i;
                    cl=c;
                } 
            }
            try{s1=Func.style.getText(ini, Func.style.getLength()-ini);}
            catch(Exception e){}
            arxiutxt+=s1+FIL+arxiuText;
        }
        if(!Func.textDeco1.getText().equals(""))arxiutxt+=arxiuTextI+"info_TextDeco1"+arxiuTextF+Func.textDeco1.getText()+FIL+arxiuText;
	escriu(f,arxiutxt);
    }
    public static String splitG(){
    	String s="";
    	for(int i=0;i<iSplits;i++){
            if(split[i]!=null){
                s+=split[i].getDividerLocation()+TAB;
    		s+=split[i].getWidth()+TAB;
    		s+=split[i].getHeight()+FIL;
            }
            else{
                s+=splitDimInt[i][0]+TAB;
    		s+=splitDimInt[i][1]+TAB;
    		s+=splitDimInt[i][2]+FIL;
            }
    	}
        s+=Rodo(Func.fr.getX()*10000/Func.longX)+TAB;
    	s+=Rodo(Func.fr.getY()*10000/Func.longY)+FIL;
    	return arxiuTextI+"splitPan"+arxiuTextF+s+arxiuText;
    }
    public static void splitSuport(){
    	for(int i=0;i<iSplits;i++){
            if(split[i]!=null){
                splitDimInt[i][0]=split[i].getDividerLocation();
    		splitDimInt[i][1]=split[i].getWidth();
    		splitDimInt[i][2]=split[i].getHeight();
            }
    	}
    }
    static  int Rodo(double f1){
        int i=(int)f1;
        if(f1-i>0.5){i++;}
        return i;
    }
    public JSplitPane getSplitPane() {return split[0];}
    public static String treuTABFIL_(String s,String info){
        int i=s.indexOf(TAB);
        if(i>-1)Func.append(0,info+" eliminats tabuladors"+FIL);
        while(i>-1){
            s=s.substring(0,i)+s.substring(i+2);
            i=s.indexOf(TAB);
        }
        i=s.indexOf(FIL);
        if(i>-1)Func.append(0,info+" eliminats salts de linea"+FIL);
        while(i>-1){
            s=s.substring(0,i)+s.substring(i+2);
            i=s.indexOf(FIL);
        }
        return s;
    }
    private static boolean llistatReinici(){
        if(!tTV.matriuTaula())return false;//comprova  el contingut intern de la taula variables
        if(!tTC.matriuTaula())return false;//comprova  el contingut intern de la taula condicions
        if(!tTP.matriuTaula())return false;//comproba el contingut intern de la taulaparametres
        if(taulaV.hihaVar)if(!tTV.matriuTaulaFi())return false; 
        if(!tTD.matriuTaula())return false;//comprova  el contingut intern de la taula funcions parcials (analitza tambe compatibilitats amb taules previes)
        if(!tTI.matriuTaula())return false;
        if(Func.tipusFuncioBol)if(!tTF.matriuTaula())return false;//tTF.matriuTaula requereix (per la part final) que la taula parametres s'hagi executat
        if(!simbolsDeFuncions())return false;//print_simbolsdeFuncions();
        if(simbolsRepetitsaTaules())return false;
        return simbolsDefinits();
    }
    public boolean llistatVars(){
        calculUnic=false;
        if(!llistatReinici())return false;
        if(!simbolsDefinitsNoUtilitsats())return false;
        if(taulaD.hihaDades)if(!tTD.matriuTaulaFi())return false;
        Cal.segment=(long)(Math.pow(2,62));
        if(Cal.supID!=null){
            Cal.supID.integral=new double[0];
            Cur.supID.integral=new double[0];
        }
        if(taulaD.hihaDades||taulaI.hihaDades){Cal.supID=new suportID();Cur.supID=new suportID();}
        if(taulaI.hihaDades&&taulaD.hihaDades)Cal.hihaFPIntegrBol=new boolean[taulaI.sumat.length+1][taulaD.simbol.length+1];
        else Cal.hihaFPIntegrBol=new boolean[1][1];//boolean de [sumat.length+1][simbols.length+1] que relaciona sumatoris qur contenent funcionsParcials no constants
        supIDBol=false;
        if(taulaD.hihaDades){
            if(!tTD.iniFuncionsVariables())return false;
            suportID.intervalCurDer=new long[taulaD.simbol.length];//suportID.intervalCurDer=new long[taulaD.simbol.length][62];
            Cal.supID.iniciD(taulaD.simbol.length);
            if(!Func.tipusFuncioBol){
                Cur.supID.dVO=new double[Cal.supID.dVO.length];
                System.arraycopy(Cal.supID.dVO, 0, Cur.supID.dVO, 0, Cal.supID.dVO.length);
                Cur.supID.iniciD(taulaD.simbol.length);
            }
            supIDBol=true;
        }
        suportID.hihaVtaulaF_aI=new boolean[0];
        if(Func.tipusFuncioBol&&taulaI.hihaDades)suportID.hihaVtaulaF_aI=new boolean[taulaI.sumat.length];
        if(taulaI.hihaDades){
            if(!tTI.matriuTaulaFi())return false;
            if(supIDBol) Cal.supID.FPaIntegral();//indexs de funcions parcials a integrals que no son constants; les constants han modificat el simbol de sVOI
            if(!taulaV.hihaVar){suportID.indexsVGaI=new int[0][2];}
            Cal.supID.integral=new double[suportID.longi.length];
            Cur.supID.integral=new double[suportID.longi.length];
        }
        if(Func.tipusFuncioBol){//si tipusFuncioBol=true probablement es modificaran algunes de les matrius definides a taulaV i taulaP
            if(!taulaF.matriuTaulaFi())return false;
        }
        else{
            if(!taulaC.definirEscala(""))return false;
            taulaF.varNgenSumBol=new boolean[0];
            taulaF.varNgenDerBol=new boolean[0];
            taulaF.varGenBol=new boolean[0][0];
        }
        if(taulaI.hihaDades)sumatorisConstants();
        Cur.pan01.setVisible(Func.graficReg);
        if(Func.graficReg)splitPan.split[6].setDividerLocation(splitDimInt[6][0]);
        else splitPan.split[10].setDividerLocation(splitPan.split[10].getHeight());
        return cadenes_sVO_deFuncions();
    }
    public static void carregaArx(){//obre explorador
        JFileChooser arbre= new JFileChooser();
        filtre.filtre="txt";
        arbre.addChoosableFileFilter(new filtre());
        try{arbre.setCurrentDirectory(new File(ruta));}
        catch(Exception e){}
        int sel = arbre.showOpenDialog(arbre);
        if (sel == JFileChooser.APPROVE_OPTION) {
            if(!Func.para()){
                Func.append(1,"ordre de parada no executat, l'arxiu no es carrega, intentar-ho de nou"+splitPan.FIL);
                return;
            }
            Cur.unaVarBol=false;
            File f=arbre.getSelectedFile();
            Func.titol=f.toString();
            tTC.reinici();
            tTV.reinici();
            tTI.reinici();
            tTP.reinici();
            tTD.reinici();
            tTF.reinici();
            Cur.pan0.removeAll();
            Cur.pan1.removeAll();
            Func.funcImpl.surt();
            Func.textDeco[Func.saltBol].setText("");
            Func.textDeco1.setText("");
            Func.JFuncio.setText("");
            Func.fr.setTitle(Func.titol);
            if(!FileAString(f))Func.clearAll();//s'executa reinici
            else {
                splitPan.tTC=new taulaC();
                Func.funcImpl.novaFinestra(false);
            }
        }
    }
    public static String retall(String n){//retall ruta amb barra sense titol
        char c0=92;
        char c1=47;
        char[] ch=n.toCharArray();
        int j=0;
        for(int i=ch.length-1;i>0;i--){if((byte)ch[i]==(byte)c0||(byte)ch[i]==(byte)c1){j=i;i=0;}}
         if(j<=ch.length){n=n.substring(0,j+1);}
        return n;
    }
    public static boolean FileAString(File f){
        ruta=retall(Func.titol);//f es el file de titol
    	String s=llegir(f);
        if(s.equals("")){
            Func.titol="regressi"+Func.rB.getString("o_");
            return false;
        }
        arxiutxt=s;
        if(!carregaPans()){
            Func.titol="regressi"+Func.rB.getString("o_");
            error(" el format de l'arxiu no es compatible.");
            return false;
        }
        return true;
    }
    public static void carrTextAtributs(String s){
        int i=0;
        int j=s.indexOf(colorText);
        int k=colorText.length();
        int k1=k+1+FIL.length();
        try{
            String color=s.substring(j+k,j+k1);
            while(j>-1){
                int l=0;
                if(color.equals("b"+FIL))l=0;
                else if(color.equals("v"+FIL))l=1;
                else if(color.equals("n"+FIL))l=2;
                Func.append(i,s.substring(0,j));
                s=s.substring(j+k1);
                i=l;
                j=s.indexOf(colorText);
                if(j>-1)color=s.substring(j+k,j+k1);
            }
            Func.append(i,s);
        }
        catch(Exception e){
            Func.textDeco[Func.saltBol].setText("");
            Func.append(0,Func.infoTexts);}
    }
    static int[] ampladaCols(String s){
        int k=s.indexOf(":  ");
        s=s.substring(k+3);
        k=s.indexOf("  ");
        int k1=k;
        int cont=0;
        while(k>-1){
            cont++;
            k=s.indexOf("  ",k+2);
        }
        int[] cols=new int[cont];
        cont=0;
        k=0;
        while(k1>-1){
            try{cols[cont]=Integer.parseInt(s.substring(k,k1));}
            catch(Exception e){cols[cont]=0;}
            k=k1+2;
            k1=s.indexOf("  ",k);
            cont++;
        }   
        return cols;
    }
    private static String[][] StringAMatriu(String s,boolean b){
        if(s.endsWith(FIL)){s=s.substring(0,s.length()-FIL.length());}
        int j=ContadorFil(s);
        int index;
        String[] sp=new String[j];
        for(int i=0;i<j-1;i++) {
            index=s.indexOf(FIL);
            if (index>-1) {
                    sp[i]=s.substring(0,index);
                    s=s.substring(index+FIL.length(),s.length());
            }
        }
        sp[j-1] = s;
        int cols=ContadorCol(sp[0]);
        String[][] taula;
        if(b)taula= new String[taulaC.fileres+1][cols];
        else taula= new String[sp.length][cols];
        index = 0;
        for (int i=0;i<sp.length;i++) {
                String[] linia = desagrupar(sp[i],cols);
                if (linia!=null) taula[index++]=linia;
        }
        if (index != sp.length) {
                String[][] taulaComp = new String[index][cols];
                System.arraycopy(taula, 0, taulaComp, 0, index);
                taula = taulaComp;
        }
        if(b)for (int i=sp.length;i<=taulaC.fileres;i++)taula[i][1]="";
        return taula;
    }
    private static int ContadorCol(String s){
        int i=0;int columnes=1;
        while(s.indexOf(TAB,i)!=-1){i=s.indexOf(TAB,i)+1;columnes++;}
        return columnes;
    }
    private static int ContadorFil(String s){
        int i=0;int files=1;
        while(s.indexOf(FIL,i)!=-1){i=s.indexOf(FIL,i)+1;files++;}
        return files;
    }
    private static String[] desagrupar(String line,int columnes) {
        if(line==null) return null;
        String[] liniaPar = new String[columnes];
        for(int i=0;i<liniaPar.length-1;i++) {
                int index=line.indexOf(TAB);
                if (index>-1) {
                        liniaPar[i] = line.substring(0,index);
                        line = line.substring(index+TAB.length(), line.length());
                } 
                else return null;
        }
        if (line.contains(TAB)) return null;
        liniaPar[liniaPar.length-1] = line;
        return liniaPar;
    }
    public static boolean guardarArxCom(){
    	JFileChooser arbre= new JFileChooser();
    	filtre.filtre="txt";
        arbre.addChoosableFileFilter(new filtre());
        try{arbre.setCurrentDirectory(new File(rutaG));}
        catch(Exception e){
            try{arbre.setCurrentDirectory(new File(ruta));}catch(Exception e1){}
        }
        int sel = arbre.showSaveDialog(arbre);
        if (sel == JFileChooser.APPROVE_OPTION) {
            String s=  arbre.getSelectedFile().toString();
            int i = s.lastIndexOf('.');
            String ext="";
            if (i > 0 &&  i < s.length() - 1) {ext = s.substring(i+1).toLowerCase();}
            if(!ext.equals("txt")&&i!=-1){ s=s.substring(0,i+1)+"txt";}
            if(i==-1){s=s+".txt";}
            File f=new File(s);
            Func.titol=s;
            Func.fr.setTitle(s);
            rutaG=retall(s);
            guardarPans(f);
            return true;
        }
        else { error(Func.rB.getString("erguarA"));}
        return false;
    }
    public static void guardarArx(){
        if(Func.titol==null||Func.titol.equals("")){guardarArxCom();return;}
        guardarPans(new File(Func.titol));
    }
    public static String ajuntarMatriu(String[][] m){
        String t="";
        for (String[] m1 : m) {t += agrupar(m1);}
        return t;
    }
    static String agrupar(String[] s) {
            String l = "";
            for (int i = 0; i < s.length - 1; i++) {l += s[i] + TAB;}
            l += s[s.length - 1] + FIL;
            return l;
    }
    static boolean escriu(File f,String l){//entra taula[] string escriu l'arxiu i retorna boolean
        try{
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
            out.write(l); 
            out.close();
        }
        catch(Exception e){error(Func.rB.getString("erguarA"));return false;}
        return true;
    }   
    static String llegir(File f){
        String s="";
          try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF8"));
            String s0;
            while ((s0 = in.readLine()) != null) {s+=s0+FIL;}
            in.close();
            } 
            catch (Exception e){error(Func.rB.getString("ercarrA"));return "";}
        return s;
    }
    public static String esNum(String s) {//corretgeig errors posibles de un valor numeric retorna null si no es valid retorna tipus 123456,00 o 1,23456*10E5
        int j = s.length();
        if (j == 0) return null;
        int pos = -1;//posicio de la darrera coma o punt
        for (int k = j - 1; k >= 0; k--) {
          String c = s.substring(k,k+1);
          if ((c.equals(","))||(c.equals("."))) {pos=k; k=-1;}//pos=possicio de la darrera coma o punt
        }
        if (pos != -1) {//si  hi ha coma o punt anula la resta de punts i comes
            for (int k=pos-1; k>=0; k--) {
            String c = s.substring(k,k+1);
            if ((c.equals(","))||(c.equals(".")))s = s.substring(0,k) + s.substring(k+1,s.length());
          }
        }
        for (int k=0;k <s.length();k++){if(s.substring(k,k+1).equals("."))s=s.substring(0,k) + "," + s.substring(k+1,s.length());k=s.length();}
        for (int k=0;k <s.length();k++){if(s.substring(k,k+1).equals("e"))s=s.substring(0,k) + "E" + s.substring(k+1,s.length());k=s.length();}
        int contadorE=0;
        int contadorMenys=0;
        int ini = 0;
        if (s.startsWith("-")) ini = 1;
        String antPos = "";
        for (int k = ini; k < s.length(); k++) {
          String c = s.substring(k, k + 1);
          if(k==s.length()-1){//darrer digit no pot esser - o E
                  if(c.equals("-")) return null;
                  if(c.equalsIgnoreCase("E")) return null;
          }
          if(k==ini){if(c.equals(",")) s=s.substring(0,ini)+"0"+s.substring(ini,s.length());j++;}
          if(c.equalsIgnoreCase("E")){contadorE++;}
          if(c.equals("-")){contadorMenys++;}
          if(contadorE>1||contadorMenys>1){return null;}//si hi ha mes de una E o mes de dos - retorna
          if(c.equals("-")&&!antPos.equalsIgnoreCase("E")){return null;}//el segon signe menys ha d'esser despres d'un exponent
          antPos = c;
        }
        for (int k=0;k<s.length();k++)if(!esNuOeOmenys(s.substring(k,k+1)))return null;
        double d=0;
        try{d = Double.parseDouble(comApun(s));}
        catch(java.lang.NumberFormatException e){return null;}
        return s;
      }
    public static String rodo(double rodo) {
        if(!isFinite(rodo))return "NaN";
        double v = new BigDecimal(rodo).round(new MathContext(taulaC.digitsValids)).doubleValue();    
        return treucerosIcoma(""+v);
    }
    public static String treucerosIcoma(String s){
        String r=s;
        if (s.length() == 0) return "0";
        boolean bol = false;
        int j = 0;
        for (int i = 0; i < s.length(); i++) if (s.substring(i, i + 1).equals("E")) {bol = true;j=i;i=s.length();}
        if(bol){//si hi ha "E"
            String s0=s.substring(j-1,j);//s0 es la coma
            if(s0.equals(",")){s=s.substring(0,j-1)+s.substring(j);j--;}//si darrera la coma no hi ha mes informacio perque hi ha la E treu la coma i resta 1 a la cadena j seguira essent la possicio de E
            s0=s.substring(j+1);//s0 es la cadena darrera "E" o "E-"
            String s1="";
            if(s0.startsWith("-")){s1="-";s0=s0.substring(1);}//s1 es el signe menys del exponent la cadena s0 sera l'exponent sense signe
            s=s.substring(0,j);
            while(s.endsWith("0"))s=s.substring(0,s.length()-1);
            if(s.length()==0)s="0";
            while(s0.startsWith("0")){s0=s0.substring(1);}
            if(s0.length()==0)s0="0";
            r=s+"E"+s1+s0;
            return r;
        }//aqui bol te un altre sentit s'utilitza per comprobar si hi ha coma
        for (int i = 0; i < s.length(); i++) if (s.substring(i, i + 1).equals(",")) {bol = true;i=s.length();}
        if(!bol)return r;//si no hi ha "," retorna// si no hi ha exponent hi hi ha coma treu tots els ceros que es troban al final de la cadena i darrera la coma
        while(s.endsWith("0"))s=s.substring(0,s.length()-1);
        if(s.endsWith(","))s=s.substring(0,s.length()-1);
        if(!s.equals(""))return s;
        else return r;
      }
      public static String comApun(String compun) {
        boolean bol = false;
        int j = 0;
        if (compun.length()==0) compun = "0";
        for (int i=0;i<compun.length();i++) if (compun.substring(i,i+1).equals(",")) {bol= true;i=compun.length();}
        if (bol) {
          for(int i=0;i<compun.length();i++)if(compun.substring(i,i+1).equals(".")){compun=compun.substring(0,i)+compun.substring(i+1);i--;}
          for(int i=compun.length()-1;i>=0;i--)if(compun.substring(i,i+1).equals(",")){compun =compun.substring(0,i)+"."+compun.substring(i+1);j=i;i=1;}
          for(int i=0;i<j;i++)if(compun.substring(i,i+1).equals(",")){compun=compun.substring(0,i)+compun.substring(i+1);i--;j--;}
        }
        return compun;
      }
      public String punAcom(String compun) {
        boolean bol = false;
        int j = 0;
        if (compun.length() == 0) compun = "0";
        for (int i = 0; i < compun.length(); i++) {
          String c = compun.substring(i, i + 1);
          if (c.equals(".")) bol = true;
        }
        if (bol) {
          for (int i = 0; i < compun.length(); i++) {
            String c = compun.substring(i, i + 1);
            if (c.equals(",")) { compun = compun.substring(0, i) + compun.substring(i + 1, compun.length()); i--; }
          }
          for (int i = compun.length() - 1; i >= 0; i--) {
            String c = compun.substring(i, i + 1);
            if (c.equals(".")) { compun = compun.substring(0, i) + "," + compun.substring(i + 1, compun.length()); j = i; i = -1; }
          }
          for (int i = 0; i < j; i++) {
            String c = compun.substring(i, i + 1);
            if (c.equals(".")) { compun = compun.substring(0, i) + compun.substring(i + 1, compun.length()); i--; }
          }
        }
        return compun;
      }
      public String treuPunt(String compun) {
          if (compun.length() == 0) return compun;
          for (int i = 0; i < compun.length(); i++) {
            String c = compun.substring(i, i + 1);
            if (c.equals(".")) compun = compun.substring(0, i) + compun.substring(i + 1, compun.length());
          }
          return compun;
        }
      public static String esSimVar(String s){//els simbols de variables conve que no continguin cadenes de funcio ni operadors ni parentesi ni siguin valors numerics
          if(s.equals("")) return "";
          String info="";
    	  for (int i=0;i<Func.funcions.length;i++)if (s.equals(Func.funcions[i])) return  " es una funci"+Func.rB.getString("o_")+": "+Func.funcions[i];
          String c="";
          for(int i=0;i<Func.operador.length();i++)if(s.contains(Func.operador.substring(i,i+1)))c+=" "+Func.operador.substring(i,i+1)+" ";
          if (s.contains("(")) c+= " ( ";if (s.contains(")")) c+= " ) ";if (s.contains("-")) c+= " - ";
    	  if(c.length()!=0)info= "; el s"+Func.rB.getString("i_")+"mbol no pot contenir operadors ni el signe menys: "+c;
          if(esNu(s.substring(0,1)))info+= "; els primers caracters del s"+Func.rB.getString("i_")+"mbol no poden esser num"+Func.rB.getString("_e")+"rics punt o coma:" ;
          try{Double.parseDouble(comApun(s));return "; es un s"+Func.rB.getString("i_")+"mbol num"+Func.rB.getString("_e")+"ric";}catch(Exception e){}
    	  return  info;
      }
    private static String hihaFun(String s,String[] f) {
        for (int i=0;i<f.length;i++){if (s.contains(f[i]))return f[i];}
        return "";
    }
     //per introduir mes funcions modificar a Cal simbOpFun(int i) i  'caseOpFun(int i)'  ha Cur 'caseOpFun(int i)', a Func funcions i calculFun() a splitPan
    private static Double calculFun(String s,Double d){
      if (s.equals("log")) return Math.log10(d);
      if (s.equals("ln")) return Math.log(d);
      if (s.equals("sin")) return Math.sin(d);
      if (s.equals("asin")) return Math.asin(d);
      if (s.equals("cos")) return Math.cos(d);
      if (s.equals("acos")) return Math.acos(d);
      if (s.equals("tan")) return Math.tan(d);
      if (s.equals("atan")) return Math.atan(d);
      if (s.equals("abs")) return Math.abs(d);
      if (s.equals("rad")) return Math.toRadians(d);
      if (s.equals("gra")) return Math.toDegrees(d);
      if (s.equals("rod")) return (double) Math.round(d);
      if (s.equals("int")) { double d1 = d; long lo = (long)d1; return (double) lo; }
      if (s.equals("fra")) { double d1 = d; long lo = (long)d1; return d1 - lo;}
      if (s.equals("sinh")) return Math.sinh(d);
      if (s.equals("cosh")) return Math.cosh(d);
      if (s.equals("tanh")) return Math.tanh(d);
      if (s.equals("!")) return Cal.factorial(d);
      if (s.equals("rand")) {
          if(d>0)rand=new Random((long)(double)d);
          return rand.nextDouble();
      }
      return null;
    }
    public static int nombrefilesmatriuIndexs(String[] sV){
        int contador=0;//contador de funcions mes operadors sera la dimensio de matriu: indexs (per cada opersador o funcio una filera)
        for(int i=0;i<sV.length;i++){
            if(esFunBol(sV[i])){//per cada funcio es considera una operacio (nova filera)
                contador++;
                if(i>0){if(sV[i-1].equals("-"))contador++;//si funcio va precedit de - es considera operacio (nova filera)
                }
            }
            int j=Func.operador.indexOf(sV[i]);//per cada operador hi ha una nova filera
            if(j>-1)contador++;
        }
        for(int i=0;i<sV.length-1;i++){//cada signe menys davant de ( es considera operacio (nova filera)
           if(sV[i].equals("-")&&sV[i+1].equals("(")) contador++;
        }
        return contador;
    }
    //limits[][0] i[][1] limits de filera de parametres a dVO
    //limits[][2] i[][3] limits de filera de parametres a indexs (operacions)
    //limits[][4] i[][5] limits de filera de parametres a indexsVarP (possicions parametresVariables)
    // taulaP.varPTV_varPTP.length matriu de longiut=nombre de parametres variables(inclus repetits)
    // el primer index es el index del parametre variable a taulaV el segon es la filera de la taulaP en que es troba
    // Cal.indexsParV mateixa longitud que l'anterior 
    // parametres  indexs: 0 i 1:idxVarPar(filera taulaV) u filera taulaP en que es troba 
    // 2 i 3: limit inferior de sequencia de operacions indexs
    // 3 i 4: cota inferior i superior de dVO
    // 5 longitud de parella de valors de indexsPar(primer indes pos dVO segon index posicio taulaV)


    // taulaP.varPTV_varPTP.length matriu de longiut=nombre de parametres variables(inclus repetits)
    // el primer index es el index del parametre variable a taulaV el segon es la filera de la taulaP en que es troba
    // Cal.indexsParV mateixa longitud que l'anterior 
    // parametres  indexs: 0 i 1:idxVarPar(filera taulaV) u filera taulaP en que es troba 
    // 2 i 3: limit inferior de sequencia de operacions indexs
    // 3 i 4: cota inferior i superior de dVO
    // 5 longitud de parella de valors de indexsPar(primer indes pos dVO segon index posicio taulaV)

    //crea les matrius dVO i sVO a partir del String de la funcio que rep    
    public static int funcioTab(String f,boolean bI,boolean bD,boolean bF){
        String f1=f;
        String funcioTabulada="";
        int contador=0;
        String s1;
        String[] m=unitatPrevi(f,bI,bD,bF);
        while(f.length()>0){
            s1=unitat(f,m);//s1=unitat(f,bI,bD,bF);//valit si es paremetre funcio operador signe menys etc sino retorna ""
            int j=s1.length();
            if(j>0){
                    contador+=1;
                    funcioTabulada+=s1+TAB;
                    f=f.substring(j);
            }
            else return -1;
        }
        Cur.dVO_=new Double[contador];
        dVO_=new Double[contador];
        Cur.sVO=new String[contador];
        for (int i=0;i<contador;i++){
            int idx=funcioTabulada.indexOf(TAB);
            Cur.sVO[i]=funcioTabulada.substring(0,idx);
            try{Cur.dVO_[i]=Double.parseDouble(comApun(Cur.sVO[i]));} 
            catch (NumberFormatException ex) {Cur.dVO_[i]=null;}
            funcioTabulada=funcioTabulada.substring(idx+1);
        }
        return contador;
    }
    //calcula les matrius dVO_ i sVO funcioTab(f,b); realitza els calcul numerix i redueix les matrius dVO i sVO
    //si bPV es false substitueix els parametres que nomes apareixen a la primera filera torna a executar calculs numerix i redueix la funcio f, 
    //si bPV true cal distinguir si es tracta de la funcio taulaC.matriu[taulaC.regr+1][0]) o taulaC.matriu[taulaC.regr+2][0]  en que calcula una funcio per cada parametre
    //o no i en aquest cas calcula la funcio suma dels quadrats de de les funcions amb parametre
    //si info.equals(taulaC.matriu[3][0]) fa el mateix que b = false pero en aques cas be d'un procediment que fixa b=true;
    //si bI true i hi ha integrals que no contenen variables generals substitueix el seu valor en la funcio f
    //si bD true i hi ha funcions parcials que no contenen variables generals substitueix el seu valor en la funcio f
    private static void sumatorisConstants(){
       Cal.supID.longiMesLLarg();
       Cur.supID.longiMesLLarg();
       for(int j=0;j<taulaI.sumat.length;j++){
            boolean b=false;if(Func.tipusFuncioBol){
                b=suportID.hihaVtaulaF_aI[j];
                if(!b)b=taulaI.sumatConstants(j);//si hi ha funcions parcials amb variables de la taulaF a sumatoris, el sumatori no es una constant
            }//si true hi ha simbol taulaF a integral i no cal realitzar el calcul per que el sumatori no es una constant
            if(!suportID.hihaVGioFPaI[j]&&!b)if(!taulaI.sumatConstants[j]){ //els sumatoris que no contenen directa o indirectament variables generals ni simbols de taulaF son constant i per tant es poden calcular no cal calcularlos per cada filera
                taulaI.sumatConstants[j]=true;
                Cal.stopBucle=false;
                Cal.supID.bucle(j);
                Func.append(0,"info: ");String s="el sumatori "+taulaI.sumat[j]+" es una constant: ";
                if(Func.noArrodonir)Func.append(1,s+Cal.supID.integral[j]+FIL);
                else Func.append(0,s+splitPan.rodo(Cal.supID.integral[j])+FIL);
                Cur.supID.integral[j]=Cal.supID.integral[j];
            }
        }
    }
    //associa el inici de la cadena el simbol que li correspon si bI es true analitza les variables generals i variables especials sumatori a la funcio general si false analitza variables generals mes variables internes de les funcions de les integrals
    private static String unitat(String f,String[] m){
        String s1=f.substring(0,1);
        if(esNuOmenys(s1)){//cal evitar que en cas que el simbol de alguna varible o parametre  sigui 'e o E' es confongui amb un valor exponencial
            int k=1;boolean b=false;
            for(int j=1;j<f.length();j++){
                String s2=f.substring(j,j+1);
                if(esNuOeOmenys(s2)){k=j;if(s2.equalsIgnoreCase("e")) b=true;}
                else j=f.length();
            }
            if(b){if(esNu(f.substring(k,k+1))){try{return f.substring(0,k+1);} catch(Exception e){}}}
        }
        for(int j=0;j<m.length;j++){
                if(f.startsWith("-"+m[j])) return "-"+m[j];
                else if(f.startsWith(m[j])) return m[j];
            }
        if(Func.operador.contains(s1))return s1;
        if ((s1.equals("(")) || s1.equals(")")) return s1;
        if(esNuOmenys(s1)){//retorna el signe menys si no hi ha un numero a continuacio o  un numero positiu o negatiu, amb exponent o signe menys
            for(int j=0;j<f.length();j++){
                    s1=f.substring(j,j+1);
                    if(j==f.length()-1&&esNu(s1)) return f.substring(0,f.length());
                    if(!esNuOeOmenys(s1)) return f.substring(0,j);
                    }
            }
        return "";
    }
    private static String[] unitatPrevi(String f,boolean bI,boolean bD,boolean bF){//genera la matriu m que conte tots el simbols de parametres i variables depents del tipus de funcio que s'analitzara
        int cont=0;
        if(bI&&taulaI.hihaDades)cont+=taulaI.sumat.length;
        if(bD&&taulaD.hihaDades)cont+=taulaD.simbol.length;
        if(bF&&Func.tipusFuncioBol){cont+=taulaF.simbolsReduitsNoNumerics.length;cont+=taulaV.varsOrdenats.length;}
        cont+=funcionsPar.length;//les funcions definides com cos(
        if(!bI&&taulaI.hihaDades)cont+=taulaI.varInternes.length;///bI es false quan s'analitzen les funcions integrals bI=true (funcio general= suma de funcions generals amb parametres) 
        if(taulaV.varstaulaV!=null){
            if(!bI&&taulaI.hihaDades) cont+=taulaV.varsGenOrdenats.length;
            else cont+=taulaV.varsOrdenats.length;
        }
        if(taulaP.hihaPar)cont+=taulaP.parametres.length;
        String[] m=new String[cont];
        cont=0;
        if(bI&&taulaI.hihaDades)for(int i=0;i<taulaI.sumat.length;i++){m[cont]=taulaI.sumat[i];cont++;}
        if(bD&&taulaD.hihaDades)for(int i=0;i<taulaD.simbol.length;i++){m[cont]=taulaD.simbol[i];cont++;}
        if(bF&&Func.tipusFuncioBol){
            for(int i=0;i<taulaF.simbolsReduitsNoNumerics.length;i++){m[cont]=taulaF.simbolsReduitsNoNumerics[i];cont++;}
            for(int i=0;i<taulaV.varsOrdenats.length;i++){m[cont]=taulaV.varsOrdenats[i];cont++;}
        }
        for(int i=0;i<funcionsPar.length;i++){m[cont]=funcionsPar[i].substring(0,funcionsPar[i].length()-1);cont++;}//les funcions definides com cos(
        if(!bI&&taulaI.hihaDades)for(int i=0;i<taulaI.varInternes.length;i++){m[cont]=taulaI.varInternes[i];cont++;}
        if(taulaV.varstaulaV!=null){
            if(!bI&&taulaI.hihaDades) for(int i=0;i<taulaV.varsGenOrdenats.length;i++){m[cont]=taulaV.varsGenOrdenats[i];cont++;}
            else for(int i=0;i<taulaV.varsOrdenats.length;i++){m[cont]=taulaV.varsOrdenats[i];cont++;}
        }        
        if(taulaP.hihaPar)for(int i=0;i<taulaP.parametres.length;i++){m[cont]=taulaP.parametres[i][0];cont++;}
        String s;
        for(int i=0;i<m.length;i++)for(int j=i+1;j<m.length;j++){
            if(m[j].length()>m[i].length()){s=m[i];m[i]=m[j];m[j]=s;}
        }
        return m;
    }
    //els primers i darrers simbols no poden esser un operador o un parentesi ) i el darrer tampoc pot esser un operador o - o (
    //e o E (de exponent) darrera d'un valor numeric no pot esser seguit del signe mes treure el signe +
    //el signe menis no es un operador en els cassos en que es preveu que indica una operacio de resta es substitueix per +-
    public static boolean modificacionsInicials(String t,String info){
        funcSup=new String[2];
        funcSup[0]=treuBlancs(t);
        boolean b=true,b1=false;
        while(b){//els primers simbols no poden esser ) o operador i darrers simbols no poden esser  ( i per tant funcio ni operador ni signe menys
            if(funcSup[0].length()>0&&esOpPar(funcSup[0].substring(0,1))){funcSup[0] = funcSup[0].substring(1);b1=true;}//treu els simbols ) i operador inicials 
            else b=false;
        }
        b=true;while(b){
            if(funcSup[0].length()>0&&esOpParMenys(funcSup[0].substring(funcSup[0].length()-1))){funcSup[0]=funcSup[0].substring(0,funcSup[0].length()-1);b1=true;}//treu els simbols ( - i operador del final
            else b=false;
        }
        if(b1)if(b1){Func.append(1,Func.avis);Func.append(0,info+"els s"+Func.rB.getString("i_")+"mbols inicials i finals no poden "+Func.rB.getString("e_")+"sser cap dels s"+Func.rB.getString("i_")+"mbols ')' o un operador; ni els darrers el s"+Func.rB.getString("i_")+"mbol '-', la funci"+Func.rB.getString("o_")+" es modificara comprobar els canvis funci"+Func.rB.getString("o_")+"original: "+t+FIL+"nova funci"+Func.rB.getString("o_")+": "+funcSup[0]+FIL+"el proc"+Func.rB.getString("e_")+"s continua amb el canvi"+FIL);}
        b1=false;
        for(int i=0;i<funcSup[0].length()-2;i++){//el primer i darrer simbols ja no son cap dels simbols inicials ni finals anulats
            if(funcSup[0].substring(i,i+2).equals("--")){funcSup[0]=funcSup[0].substring(0,i)+funcSup[0].substring(i+1);b1=true;i--;}// treu els simbols - repetits
        }
        if(b1){Func.append(1,Func.avis);Func.append(0,info+"els s"+Func.rB.getString("i_")+"mbol '-' no pot apareixer mes de un cops seguits, la funci"+Func.rB.getString("o_")+" es modificara comprobar els canvis funci"+Func.rB.getString("o_")+"original: "+t+FIL+"nova funci"+Func.rB.getString("o_")+": "+funcSup[0]+FIL+"el proc"+Func.rB.getString("e_")+"s continua amb el canvi"+FIL);}
        for(int i=0;i<Func.funcions.length;i++){
            int j=funcSup[0].indexOf(Func.funcions[i]+"()");
            if(j>-1){
                if(Func.funcions[i].equals("rand")){
                    Func.append(1,"avis: ");Func.append(0,"la funci"+Func.rB.getString("o_")+" rand (randomize) ha d'incloure un valor num"+Func.rB.getString("_e")+"ric dins del par"+Func.rB.getString("_e")+"ntesi; la funci"+Func.rB.getString("o_")+" es modifica i s'inclou els cero"+FIL);
                    funcSup[0]=funcSup[0].substring(0,j+5)+"0"+funcSup[0].substring(j+5);
                }
                else{Func.append(1,"error: ");Func.append(0,"les funcions han de incorporar contingut dins dels par"+Func.rB.getString("_e")+"ntesis"+FIL);return false;}
            }
        }
        b1=false;
        for(int i=0;i<funcSup[0].length()-1;i++){
            if(funcSup[0].substring(i,i+2).equals("()")){
                funcSup[0]=funcSup[0].substring(0,i)+funcSup[0].substring(i+2);b1=true;
                i-=2;if(i<-1)i=-1;
            }
        }
        if(b1){Func.append(1,Func.avis);Func.append(0,info+" les parelles de caracters '()' sense contingut no es consideren v"+Func.rB.getString("a")+"lides i s'eliminen ,el proc"+Func.rB.getString("e_")+"s continua amb el canvi"+FIL);}
        b1=false;
        funcSup[1]=funcSup[0];
        for (int i=1; i<funcSup[0].length()-1; i++) {
            String s0=funcSup[0].substring(i,i+1);
            if (i<funcSup[0].length()-1&&s0.equalsIgnoreCase("E")){
                exponentE(funcSup,i);
            }
        }
        for(int i=1;i<funcSup[0].length()-1;i++){
            String s1=funcSup[0].substring(i,i+1),s2=funcSup[0].substring(i+1,i+2);
            if(esOp(s1)){
                if(esOp(s2)){
                    if(!s1.equals(s2)){Func.append(1,Func.error);Func.append(0,info+"dos operadors diferents correlatius s"+Func.rB.getString("i_")+"mbols: "+funcSup[0].substring(i,i+2)+" funci"+Func.rB.getString("i_")+": "+funcSup[0]+FIL);return false;}
                    else {funcSup[0]=funcSup[0].substring(0,i)+funcSup[0].substring(i+1);funcSup[1]=funcSup[1].substring(0,i)+funcSup[1].substring(i+1);b1=true;}
                }
                funcSup[1]=funcSup[1].substring(0,i)+"*"+funcSup[1].substring(i+1);//transforma tots els operadors amb el simbol * 
            }
        }
        if(b1)if(b1){Func.append(1,Func.avis);Func.append(0,info+"s'han eliminat els  s"+Func.rB.getString("i_")+"mbol de operadors id"+Func.rB.getString("_e")+"ntics i correlatius comprobar els canvis funci"+Func.rB.getString("o_")+"original: "+t+FIL+"nova funci"+Func.rB.getString("o_")+": "+funcSup[0]+FIL+"el proc"+Func.rB.getString("e_")+"s continua amb el canvi"+FIL);}
        b1=false;
        if(funcSup[1].contains("-*")){Func.append(1,Func.error);Func.append(0,info+"es erroni el signe menys davant d'un operador; funci"+Func.rB.getString("i_")+": "+funcSup[1]+FIL);return false;}//les combinacions *- es mantenen
        for (int i = 1; i < funcSup[1].length()-1; i++) {
            String s0=funcSup[1].substring(i-1, i);String s1=funcSup[1].substring(i, i+1);
            if(!s0.equals("*")&&!s0.equals("(")&&s1.equals("-")){
                funcSup[0]=funcSup[0].substring(0,i)+"+"+funcSup[0].substring(i,funcSup[0].length());
                funcSup[1]=funcSup[1].substring(0,i)+"*"+funcSup[1].substring(i,funcSup[1].length());i++;b1=true;
            }//la resta de simbols - requereixen de un operador i es considera que es el de suma
        }
        if(b1){Func.append(1,"avis: ");Func.append(0,"el signe menys no es considera un operador de resta; suposant que es tracta de una difer"+Func.rB.getString("_e")+"ncia s'ha canviar - per +- revisar la funci"+Func.rB.getString("o_")+": "+FIL+funcSup[0]+FIL+"el procediment continua amb el canvi"+FIL);}
        return true;
    }
    public static String reduirFuncioAVars(String info){//retorna "" si es false
        String s=funcSup[1];
        for(int i=0;i<s.length();i++)if(s.substring(i,i+1).equalsIgnoreCase("-"))s = s.substring(0,i)+s.substring(i+1);//treu tots els simbols negatius el dels exponents ja no hi son  els que representen una resta ja contenen un operador previ +>* i la resta es troba darrera '(' o es al inici 
        String c = hihaFun(s,funcionsPar);//cerca funcio(
        while (!c.equals("")) {//anula les funcions pero mante els parentesi
            int j = s.indexOf(c);
            int m=c.length()-1;
            if (j<s.length()-m-1){// despres de funcio amb parentesi hi ha d'haver un digit i parentesi com a minim; davant una de dues o es a l'inici o si no hi ha d'haver un operador o parentesi o signe -
                if (j == 0)s=s.substring(j+m);
                else if (s.substring(j-1,j).equals("*")||s.substring(j-1,j).equals("(")||s.substring(j-1,j).equals("-")) s=s.substring(0,j)+s.substring(j+m);
                else {Func.append(1,Func.error);Func.append(0,info+"davant de funcions hi ha d'haver un operador o s"+Func.rB.getString("i_")+"mbols '(' o '-'; cadena: "+s.substring(j-1,j+m)+FIL);return null;}
                c = hihaFun(s,funcionsPar);
            }
            else{Func.append(1,Func.error);Func.append(0,info+"darrera de funci"+Func.rB.getString("o_")+" hi ha com a m"+Func.rB.getString("i_")+"nim '(valor)' revisar cadena: "+s.substring(j)+FIL);return null;}
       }
        String t="";
        for(int i=0;i<s.length();i++){//la cadena t contindra nomes parentesi la cadena s la resta (simbols de variables, valors numeric i operador '*' 
            String s0=s.substring(i,i+1);
            switch (s0) {
                case "("://comproba que Valor( i (* son incorrectes
                    if(i<s.length()-2)if(s.substring(i+1,i+2).equals("*")){ Func.append(1,Func.error);Func.append(0,info+"cadenes del tipus: +), (*, *), )( etc. no son v"+Func.rB.getString("a")+"lides"+FIL);return null;}
                    if(i>0)if(!s.substring(i-1,i).equals("*")&&!s.substring(i-1,i).equals("(")){ Func.append(1,Func.error);Func.append(0,info+"cadenes del tipus: valor(, no son v"+Func.rB.getString("a")+"lides cal un operador"+FIL);return null;}
                    s=s.substring(0,i)+s.substring(i+1);
                    i--;t+=s0; break;
                case ")"://comproba que )Valor i *) son incorrectes
                    if(i>2)if(s.substring(i-1,i).equals("*")){ Func.append(1,Func.error);Func.append(0,info+"cadenes del tipus: +), (*, *), )( etc. no son v"+Func.rB.getString("a")+"lides"+FIL);return null;}
                    if(i<s.length()-2)if(!s.substring(i+1,i+2).equals("*")&&!s.substring(i+1,i+2).equals(")")){ Func.append(1,Func.error);Func.append(0,info+"cadenes del tipus: )valor,  no son v"+Func.rB.getString("a")+"lides"+FIL);return null;}
                    s=s.substring(0,i)+s.substring(i+1);
                    i--;t+=s0;break;
            }
        }
        if(!analitzaParentesi(t)){Func.append(1,Func.error);Func.append(0,info+Func.rB.getString("parErr1")+FIL);return null;}
        t="";//la sequencia de la funcio ha d'esser (valor numeric o simbol) operador (valor numeric o simbol) etc el primer i darrer caracter no poden esser un operador
        int i=s.indexOf("*");
        while(i>-1){
            String u=s.substring(0,i);
            if(!esNu(u.substring(0,1)))t+=u+" ";
            else{
                try{Double d=Double.parseDouble(comApun(u));}
                catch(Exception e){ Func.append(1,Func.error);Func.append(0,info+"cadena: "+u+", no es v"+Func.rB.getString("a")+"lida, el primer car"+Func.rB.getString("a")+"cter dels s"+Func.rB.getString("i_")+"mbols de par"+Func.rB.getString("a")+"metres io variables no pot "+Func.rB.getString("e_")+"sser un s"+Func.rB.getString("i_")+"mbol num"+Func.rB.getString("_e")+"ric "+FIL);return null;}
            }
            s=s.substring(i+1);
            i=s.indexOf("*");
        }
        if(!esNu(s.substring(0,1)))t+=s+" ";
        else{
            try{Double d=Double.parseDouble(comApun(s));}
            catch(Exception e){ Func.append(1,Func.error);Func.append(0,info+"cadena: "+s+", no es v"+Func.rB.getString("a")+"lida, el primer car"+Func.rB.getString("a")+"cter dels s"+Func.rB.getString("i_")+"mbols de par"+Func.rB.getString("a")+"metres io variables no pot "+Func.rB.getString("e_")+"sser un s"+Func.rB.getString("i_")+"mbol num"+Func.rB.getString("_e")+"ric "+FIL);return null;}
        }
        return " "+t;
    }
    public static boolean analitzaParentesi(String t){
        if(t.length()==0)return true;
        for(int i=0;i<t.length()-1;i++){
            String s0=t.substring(i,i+2);if(s0.equals("()")){
                t=t.substring(0,i)+t.substring(i+2);
                if(i>0)i--;
                i--;
            }
        }
        return (t.equals(""));
    }
    public static String[] matriuPariVardeFuncio(String info){//retorna matriu on cada filera conte el simbol de parametre io variable entre dos blancs
        if(funcSup[1].equals(""))return null;
        String s=reduirFuncioAVars(info);
        if(s==null)return null;
        return matriudeFuncio(s);
    }
    public static String[] matriudeFuncio(String s){//retorna matriu on cada filera conte el simbol de parametre io variable
        String[] m=matriudeFuncioCompleta(s);
        int conta=0;
        for(int i=0;i<m.length;i++)for (int j=i+1;j<m.length;j++)if(m[i].equals(m[j]))m[j]="";
        for(int i=0;i<m.length;i++)if(!m[i].equals(""))conta++;
        String[] n=new String[conta];
        conta=0;
        for(int i=0;i<m.length;i++)if(!m[i].equals("")){n[conta]=m[i];conta++;}
        return n;
    }
    public static String[] matriudeFuncioCompleta(String s){//retorna matriu on cada filera conte el simbol de parametre io variable
        StringTokenizer scd = new StringTokenizer(s);
        int conta=scd.countTokens();
        String[] m=new String[conta];
        for(int i=0;i<conta;i++)m[i]=scd.nextToken();
        return m;
    }
    public static double[] matriudeFunciodouble(String s){//retorna matriu on cada filera conte el simbol de parametre io variable
        StringTokenizer scd = new StringTokenizer(s);
        int conta=scd.countTokens();
        double[] m=new double[conta];
        for(int i=0;i<conta;i++){
            try{m[i]=Double.parseDouble(scd.nextToken());}
            catch (Exception e){m[i]=NaN;}
        }
        return m;
    }
    public static int[] matriudeFuncioInt1(String s){//retorna matriu ordenada on cada filera conte un sencer
        String[] c=splitPan.treuSimbolsrepetits(splitPan.matriudeFuncioCompleta(s));
        
         int[] m=new int[c.length];
         for(int i=0;i<c.length;i++)m[i]=Integer.parseInt(c[i]);
         for(int i=0;i<m.length-1;i++)for(int j=i+1;j<m.length;j++)if(m[i]>m[j]){int k=m[i];m[i]=m[j];m[j]=k;}
        return m;
    }
    public static int[][] matriudeFuncioInt2(String s){//retorna matriu on cada filera conte el simbol de parametre io variable
        StringTokenizer scd = new StringTokenizer(s);
        int[][] m=new int[scd.countTokens()/2][2];
        for(int i=0;i<m.length;i++){
            m[i][0]=Integer.parseInt(scd.nextToken());m[i][1]=Integer.parseInt(scd.nextToken());
        }
        return m;
    }
    public static int[][] matriudeFuncioInt3(String s){//retorna matriu on cada filera conte el simbol de parametre io variable
        StringTokenizer scd = new StringTokenizer(s);
        int[][] m=new int[scd.countTokens()/3][3];
        for(int i=0;i<m.length;i++){
            m[i][0]=Integer.parseInt(scd.nextToken());m[i][1]=Integer.parseInt(scd.nextToken());m[i][2]=Integer.parseInt(scd.nextToken());
        }
        return m;
    }
    public static String[] treuSimbolsrepetits(String[] s){
        int conta=0;boolean b;
        for(int i=0;i<s.length;i++){
            b=false;
            for(int j=i+1;j<s.length;j++)if(s[i].equals(s[j])){b=true;j=s.length;}
            if((!b))conta++;
        }
        String st[]=new String[conta];
        conta=0;
        for(int i=0;i<s.length;i++){
            b=false;
            for(int j=i+1;j<s.length;j++)if(s[i].equals(s[j])){b=true;j=s.length;}
            if((!b)){st[conta]=s[i];conta++;}
        }
        return st;
    }
    public static String[] faBuitSimbolsrepetits(String[] s){
        for(int i=0;i<s.length;i++){
            for(int j=i+1;j<s.length;j++)if(s[i].equals(s[j])&&!s[j].equals(""))s[j]="";
        }
        return s;
    }
    public static void temp0(){previous = Instant.now();}
    public static void temp1(){previous1 = Instant.now();}
    public static double temp(){long l=ChronoUnit.MILLIS.between(previous,Instant.now());return (double)l/1000.0;}
    public static double tempt(){long l=ChronoUnit.MILLIS.between(previous1,Instant.now());return (double)l/1000.0;}
    public static String treuBlancs(String s){//treu TAB FIL i " "
       String s0=TAB;
       int i=s.indexOf(s0);
       while(i>=0){
           s=s.substring(0,i)+s.substring(i+s0.length());
           i=s.indexOf(s0);
       }
       s0=FIL;
       i=s.indexOf(s0);
       while(i>=0){
           s=s.substring(0,i)+s.substring(i+s0.length());
           i=s.indexOf(s0);
       }
       s0=" ";
       i=s.indexOf(s0);
       while(i>=0){
           s=s.substring(0,i)+s.substring(i+s0.length());
           i=s.indexOf(s0);
       }
        return s;
    }
    public static String treuSimbolsIdentics(String s){//reuneix tots el parametres de interval i limits i funcio principal i treu els repetirs
        s=splitPan.treuBlancDeDos(s);
        for(int i=0;i<s.length();i++){
            int k=s.indexOf(" ",i+1);
            if(k>-1){
                String s0=s.substring(i,k+1);
                String s1=s.substring(k);
                if(s1.contains(s0)){
                        s=s.substring(0,i)+s.substring(k);
                        k=i;
                    }
                i=k-1;
            }
        }
        return s;
    }
    public static String treuBlancDeDos(String s){int i=s.indexOf("  "); while(i>-1){s=s.substring(0,i)+s.substring(i+1);i=s.indexOf("  ");}return s;}
    static boolean exponentE(String[] s,int i0){//i0 es>=1
        int i1=i0+1;
        int k=-1;
        for(int j=i0-1;j>=0;j--)if(!esNu(s[0].substring(j,j+1))){k=j;j=0;}//k es igual al primer simbol de l'esquerra de e que no es numeric en principi si davant de e hi ha un valor numeric es tract de un nombre exponencial
        if(k==i0-1)return false;// el simbol anterior a e no es numeric  e no pot ser part d'un nombre exponecial
        if(k<0||esOpParMenys(s[0].substring(k,k+1))){
            String c=s[0].substring(i1,i1+1);
            if(c.equals("+")){
                s[0]=s[0].substring(0,i1)+s[0].substring(i1+1);
                s[1]=s[0];
            }
            else if(c.equals("-"))i1++;
        }
        else return false;
        int l=s[0].length();
        for(int j=i1;j<s[0].length();j++)if(!esNu(s[0].substring(j,j+1))){l=j;j=s[0].length();}//l es el primersimbol situat a la dreta que no es numeric
        if(l==i1)return false;//Si el primer simbol  posterior no son numerics retorna "" equivalent a false
        if(l==s[0].length()||esOpPar(s[0].substring(l,l+1))){
            String n="";
                for(int j=1;j<(l-k);j++)n+="0";
            s[1]=s[1].substring(0,k+1)+n+s[1].substring(l);
        }
        return true;
    }
    public static boolean esNu(String s) {return ((s.compareTo("0") >= 0) && (s.compareTo("9") <= 0)) || (s.equals(",")) || (s.equals("."));}
    public static boolean esNuOmenys(String s) {return (esNu(s)) || (s.equals("-"));}
    public static boolean esOpOParentesi(String s) {return (s.equals("(")) || s.equals(")") ||esOp(s);}
    public static boolean es0_9(String s) {return ((s.compareTo("0") >= 0) && (s.compareTo("9") <= 0));}
    public static boolean esNuOe(String s) {return (esNu(s)) || (s.equalsIgnoreCase("e"));}
    public static boolean esOp(String s) {
          for(int i=0;i<Func.operador.length();i++)if(s.equals(Func.operador.substring(i,i+1)))return true;
          return false;
    }
    public static boolean esOpParMenys(String s) {return s.equals("(")||esOp(s)||s.equals("-");}
    public static boolean esOpPar(String s) {return s.equals(")")||esOp(s);}
    public static boolean esNuOeOmenys(String s) {return (esNuOe(s)) || (s.equals("-"));}
    public static boolean esFunBol(String s) {
      for (int i=0;i<Func.funcions.length;i++){if (s.equals(Func.funcions[i])) return true;}
      return false;
    }
    public static String[][] matriudeFuncioString(String s){//retorna matriu on cada filera conte el simbol de parametre io variable
        String[][] m=new String[0][0];
        if(s==null)return m; 
        if(s.equals(""))return m;
        int i=s.indexOf(FIL);
        String s1;
        if(i<1)s1=s;
        else s1=s.substring(0,i); 
        try{
            StringTokenizer scd = new StringTokenizer(s1,TAB);
            int col=scd.countTokens();
            String s0=s.replaceAll(FIL, TAB);
            scd = new StringTokenizer(s0,TAB);
            m=new String[scd.countTokens()/col][col];
            for(i=0;i<m.length;i++){for(int j=0;j<m[0].length;j++)m[i][j]=scd.nextToken();}
        }
        catch(Exception e){}
        return m;
    }
    public static void cop_sel(JTextField cel){
        Func.clipboard c = new Func.clipboard();
        int i=cel.getSelectionStart();
        int j=cel.getSelectionEnd();
        String sel=cel.getText().substring(i,j);
        if(sel==null){sel="";}
        c.copiaClip(sel);
    }
    public static void paste_sel(JTextField cel){
        Func.analisiPreviBol=false;//modificat=true;
            Func.clipboard c = new Func.clipboard();
            int i=cel.getSelectionStart();
            int j=cel.getSelectionEnd();
            try {
                String s1 = cel.getText().substring(0,i)+c.ajuntarClip()+cel.getText().substring(j); 
                cel.setText(s1);
                cel.setCaretPosition(i);
            } catch (Exception ex) {}
    }
    public static void cop(JTable t,String[][] mt,boolean bol){
        String Sel="";
        int row=t.getSelectedRow();int col=t.getSelectedColumn();int rows=t.getSelectedRows().length;int cols=t.getSelectedColumns().length;
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                Sel+=mt[i+row+1][j+col];
                if(j!=cols-1)Sel+=splitPan.TAB;
                if(bol)mt[i+row+1][j+col]="";
            }
            if(i!=rows-1)Sel+=splitPan.FIL;
        }
        Func.clipboard c1 = new Func.clipboard();c1.copiaClip(Sel);
    }
    public static void cop_(JTable t,String[][] mt){
        String Sel="";
        int row=t.getSelectedRow();int col=t.getSelectedColumn();int rows=t.getSelectedRows().length;int cols=t.getSelectedColumns().length;
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                Sel+=mt[i+row+1][j+col];
                if(j!=cols-1)Sel+=splitPan.TAB;
            }
            if(i!=rows-1)Sel+=splitPan.FIL;
        }
        Func.clipboard c1 = new Func.clipboard();c1.copiaClip(Sel);
    }
    public static String[][] paste(){
        Func.analisiPreviBol=false;//modificat=true;
        Func.clipboard c = new Func.clipboard();
        String s1;
        String[][] m=new String[0][0];
        try { s1 = c.ajuntarClip(); } catch (Exception ex) {s1 = "";return m;}
        if(s1.equals("")){m=new String[1][1];m[0][0]="";return m;}
        int i=s1.indexOf(TAB+TAB);while(i>-1){s1=s1.substring(0,i+1)+" "+s1.substring(i+1);i=s1.indexOf(TAB+TAB);}
        i=s1.indexOf(TAB+FIL);while(i>-1){s1=s1.substring(0,i+1)+" "+s1.substring(i+1);i=s1.indexOf(TAB+FIL);}
        i=s1.indexOf(FIL+TAB);while(i>-1){s1=s1.substring(0,i+1)+" "+s1.substring(i+1);i=s1.indexOf(FIL+TAB);}
        i=s1.indexOf(FIL+FIL);while(i>-1){s1=s1.substring(0,i+1)+" "+s1.substring(i+1);i=s1.indexOf(FIL+FIL);}
        if(s1.startsWith(FIL))s1=" "+s1;
        if(s1.startsWith(TAB))s1=" "+s1;
        if(s1.endsWith(FIL))s1=s1+" ";
        if(s1.endsWith(TAB))s1=s1+" ";
        m=splitPan.matriudeFuncioString(s1);
        for(i=0;i<m.length;i++)for(int j=0;j<m[0].length;j++)if(m[i][j].startsWith(" "))m[i][j]=m[i][j].substring(1);
        return m;
    }
    public static boolean simbolsRepetitsaTaules(){
        boolean b=false;
        if(taulaI.hihaDades){//comprobar que els simbols de variables sumatori no es troben repetits en les atres taules incloses les internes de sumatoris
            // les variables internes s'han comprobat per cada sumatori i les variables de sumatori i funcions parcials tenen el primer caracter diferent>> for(int i=0;i<taulaI.varOrdenats.length;i++)for(int j=i+1;j<taulaI.varOrdenats.length;j++)if(taulaI.varOrdenats[i].equals(taulaI.varOrdenats[j])){Func.append(1,Func.error);Func.append(0,"a taula sumatoris s"+Func.rB.getString("i_")+"mbol repetit: "+taulaI.varOrdenats[j]+FIL);b=true;}
                                    for(int i=0;i<taulaI.sumat.length;i++)      for(int j=0;j<taulaI.varInternes.length;j++)                if(taulaI.sumat[i].equals(taulaI.varInternes[j])){                  Func.append(1,Func.error);Func.append(0,"a taula sumatoris s"+Func.rB.getString("i_")+"mbol repetit: "+taulaI.sumat[i]+FIL);b=true;}
            if(taulaP.hihaPar)      for(int i=0;i<taulaI.sumat.length;i++)      for(int j=0;j<taulaP.simParCap.length;j++)                  if(taulaI.sumat[i].equals(taulaP.simParCap[j])){                    Func.append(1,Func.error);Func.append(0,"a taula sumatoris i taula parametres: s"+Func.rB.getString("i_")+"mbol repetit: "+taulaI.sumat[i]+FIL);b=true;}
            if(taulaV.hihaVar)      for(int i=0;i<taulaI.sumat.length;i++)      for(int j=0;j<taulaV.varstaulaV.length;j++)                 if(taulaI.sumat[i].equals(taulaV.varstaulaV[j])){                   Func.append(1,Func.error);Func.append(0,"a taula sumatoris i taula variables: s"+Func.rB.getString("i_")+"mbol repetit: "+taulaI.sumat[i]+FIL);b=true;}
            if(Func.tipusFuncioBol) for(int i=0;i<taulaI.sumat.length;i++)      for(int j=0;j<taulaF.simbolsReduitsNoNumerics.length;j++)   if(taulaI.sumat[i].equals(taulaF.simbolsReduitsNoNumerics[j])){     Func.append(1,Func.error);Func.append(0,"a taula sumatoris i taula de funcions: s"+Func.rB.getString("i_")+"mbol repetit: "+taulaI.sumat[i]+FIL);b=true;}
            if(taulaP.hihaPar)      for(int i=0;i<taulaI.varInternes.length;i++)for(int j=0;j<taulaP.simParCap.length;j++)                  if(taulaI.varInternes[i].equals(taulaP.simParCap[j])){              Func.append(1,Func.error);Func.append(0,"a taula sumatoris (variable interna) i taula parametres: s"+Func.rB.getString("i_")+"mbol repetit: "+taulaI.varInternes[i]+FIL);b=true;}
            if(taulaV.hihaVar)      for(int i=0;i<taulaI.varInternes.length;i++)for(int j=0;j<taulaV.varstaulaV.length;j++)                 if(taulaI.varInternes[i].equals(taulaV.varstaulaV[j])){             Func.append(1,Func.error);Func.append(0,"a taula sumatoris (variable interna) i taula variables: s"+Func.rB.getString("i_")+"mbol repetit: "+taulaI.varInternes[i]+FIL);b=true;}
            if(Func.tipusFuncioBol) for(int i=0;i<taulaI.varInternes.length;i++)for(int j=0;j<taulaF.simbolsReduitsNoNumerics.length;j++)   if(taulaI.varInternes[i].equals(taulaF.simbolsReduitsNoNumerics[j])){Func.append(1,Func.error);Func.append(0,"a taula sumatoris (variable interna) i taula de funcions: s"+Func.rB.getString("i_")+"mbol repetit: "+taulaI.varInternes[i]+FIL);b=true;}
            if(taulaD.hihaDades)    for(int i=0;i<taulaI.varInternes.length;i++)     for(int j=0;j<taulaD.simbol.length;j++)if(taulaI.varInternes[i].equals(taulaD.simbol[j])){Func.append(1,Func.error);Func.append(0,"a taula sumatoris (variable interna) i taula funcions parcials: s"+Func.rB.getString("i_")+"mbol repetit: "+taulaI.varInternes[i]+FIL);b=true;}
        }
        if(taulaD.hihaDades){//identitats amb les variables de la tauls sumatoris ja estan comprobades les variables de sumatoris i integrals no s'han d'analitzar en la mateixa taula
            if(taulaP.hihaPar){
                for(int i=0;i<taulaD.simbol.length;i++)for(int j=0;j<taulaP.simParCap.length;j++)if(taulaD.simbol[i].equals(taulaP.simParCap[j])){Func.append(1,Func.error);
            Func.append(0,"a taula funcions parcials i taula par"+Func.rB.getString("a")+"metres: s"+Func.rB.getString("i_")+"mbol repetit: "+taulaD.simbol[i]+FIL);b=true;}
            }
            if(taulaV.hihaVar)for(int i=0;i<taulaD.simbol.length;i++)for(int j=0;j<taulaV.varstaulaV.length;j++)if(taulaD.simbol[i].equals(taulaV.varstaulaV[j])){Func.append(1,Func.error);Func.append(0,"a taula funcions parcials i taula variables: s"+Func.rB.getString("i_")+"mbol repetit: "+taulaD.simbol[i]+FIL);b=true;}
            if(Func.tipusFuncioBol)for(int i=0;i<taulaD.simbol.length;i++)for(int j=0;j<taulaF.simbolsReduitsNoNumerics.length;j++)if(taulaD.simbol[i].equals(taulaF.simbolsReduitsNoNumerics[j])){Func.append(1,Func.error);Func.append(0,"a taula funcions parcials i taula de funcions: s"+Func.rB.getString("i_")+"mbol repetit: "+taulaD.simbol[i]+FIL);b=true;}
        }
        if(taulaP.hihaPar){//les repeticions taulaP.simParCap_taulaP.simParCap ja s'han analitzat a taulaP i les repeticions taulaP.simParCap_taulaV.varstaulaV ja s'han analitzat a taulaV
            if(Func.tipusFuncioBol){
                for(int i=0;i<taulaP.simParCapUnaFil.length;i++)for(int j=0;j<taulaF.simbolsReduitsNoNumerics.length;j++)if(taulaP.simParCapUnaFil[i].equals(taulaF.simbolsReduitsNoNumerics[j])){Func.append(1,Func.error);Func.append(0,"revisar taula parametres, taula de funcions i s"+Func.rB.getString("i_")+"mbols de dimensi"+Func.rB.getString("o_")+" de matrius; possible s"+Func.rB.getString("i_")+"mbol repetit: "+taulaP.simParCapUnaFil[i]+FIL);b=true;}
                for(int i=0;i<taulaP.simParCapVar.length;i++)for(int j=0;j<taulaF.simbolsReduitsNoNumerics.length;j++)if(taulaP.simParCapVar[i].equals(taulaF.simbolsReduitsNoNumerics[j])){Func.append(1,Func.error);Func.append(0,"a taula parametres i taula de funcions: s"+Func.rB.getString("i_")+"mbol repetit: "+taulaP.simParCapVar[i]+FIL);b=true;}
            }
        }
        if(taulaV.hihaVar){//les repeticions taulaV.varstaulaV_taulaV.varstaulaV ja s'han analitzat a taulaV
            if(Func.tipusFuncioBol)for(int i=0;i<taulaV.varstaulaV.length;i++)for(int j=0;j<taulaF.simbolsReduitsNoNumerics.length;j++)if(taulaV.varstaulaV[i].equals(taulaF.simbolsReduitsNoNumerics[j])){Func.append(1,Func.error);Func.append(0,"a taula variables i taula de funcions: s"+Func.rB.getString("i_")+"mbol repetit: "+taulaV.varstaulaV[i]+FIL);b=true;}
        }// els simbols repetits a taulaF ja s'han reduit a un sol simbol per filera >> if(Func.tipusFuncioBol)for(int i=0;i<taulaF.simbolsReduitsNoNumerics.length;i++)for(int j=i+1;j<taulaF.simbolsReduitsNoNumerics.length;j++)if(taulaF.simbolsReduitsNoNumerics[i].equals(taulaF.simbolsReduitsNoNumerics[j])){Func.append(1,Func.error);Func.append(0,"a taula variables: s"+Func.rB.getString("i_")+"mbol repetit: "+taulaF.simbolsReduitsNoNumerics[i]+FIL);b=true;}
        return b;
    }
    public static boolean simbolsDeFuncions(){//previament les matrius de les funcions han d'esser conegudes
        simbolsFuncio=new String[0];
        boolean b=true;
        if(Func.tipusFuncioBol){
            arr_simbolsFuncioTaulaF = new ArrayList<>();arr_simbolsFuncioTaulaFReduida = new ArrayList<>();
            String[] c=new String[0];
            for(int i=0;i<taulaF.funcioT.length;i++){
                arr_simbolsFuncioTaulaF.add(i,c);arr_simbolsFuncioTaulaFReduida.add(i,c);
                if(!taulaF.filaNop[i]){
                    if(!modificacionsInicials(taulaF.funcioT[i],"a taula de funcions (taulaF) fila: "+i+", "))return false;
                    if(!taulaF.funcioT[i].equals(funcSup[0])){taulaF.funcioT[i]=funcSup[0];taulaF.tl.setValueAt((Object)funcSup[0], i, 0);Func.append(1,Func.avis);Func.append(0,"s'ha modificat la funci"+Func.rB.getString("o_")+": "+taulaF.funcioT[i]+ splitPan.FIL);}
                    String[] sm=matriuPariVardeFuncio("a taulaF fila: "+i+", ");if(sm==null)return false;
                    arr_simbolsFuncioTaulaF.set(i,sm);arr_simbolsFuncioTaulaFReduida.set(i,sm);
                }
            }
            taulaC.abcisaFunGrafBol[0]=false;//s'ignoren les funcions de la taulaP
            Func.graficReg=false;
        }
        else {
            funcio=Func.JFuncio.getText().trim();if(!modificacionsInicials(funcio," a funci"+Func.rB.getString("o_")+" principal, "))return false;
            if(!funcio.equals(funcSup[0])){funcio=funcSup[0];Func.JFuncio.setText(funcio);Func.append(1,Func.avis);Func.append(0,"s'ha modificat la funci"+Func.rB.getString("o_")+": "+funcio+ splitPan.FIL);}
            String[] sm=matriuPariVardeFuncio("a funci"+Func.rB.getString("o_")+" principal ");if(sm==null)return false;
            simbolsFuncio=sm;
            if(!taulaV.hihaVar){
                if(taulaC.abcisaFunGrafBol[0]){Func.append(1,Func.error);Func.append(0,"no hi ha variables generals, la funci"+Func.rB.getString("o_")+": "+taulaC.matriu[taulaC.grafA+1][1]+" es calcula en funcio del contingut de la taula variables (taulaV sense contingut)"+splitPan.FIL);return false;}
                if(Func.graficReg){{Func.append(1,Func.error);Func.append(0,"no hi ha variables generals, les funcions de regressi"+Func.rB.getString("o_")+": es calculen en funci"+Func.rB.getString("o_")+" del contingut de la taula variables (taulaV sense contingut)"+splitPan.FIL);return false;}
                }
            }
            else{
                if(taulaC.abcisaFunGrafBol[0]){
                    if(!modificacionsInicials(taulaC.matriu[4][1]," a "+taulaC.textFilera[taulaC.grafA]+", "))return false;
                    if(!funcSup[0].equals(taulaC.matriu[4][1])){taulaC.matriu[4][1]=funcSup[0];Object o=funcSup[0];taulaC.tl.setValueAt(o,taulaC.grafA,1);Func.append(1,Func.avis);Func.append(0,"a "+taulaC.textFilera[taulaC.grafA]+" s'ha modificat la funci"+Func.rB.getString("o_")+": "+taulaC.matriu[4][1]+ splitPan.FIL);}
                    sm=matriuPariVardeFuncio(" a "+taulaC.textFilera[taulaC.grafA]+", ");if(sm==null)return false;
                    simbolsAbcissa=sm;
                }
                if(Func.graficReg){
                    for(int i=0;i<3;i++){
                        String t=taulaC.matriu[i+taulaC.regr+1][1];
                        if(!modificacionsInicials(t," a "+taulaC.textFilera[taulaC.regr+i]+", "))return false;
                        if(!funcSup[0].equals(t)){taulaC.matriu[i+taulaC.regr+1][1]=funcSup[0];taulaC.tl.setValueAt((Object)funcSup[0],taulaC.regr+i,1);Func.append(1,Func.avis);Func.append(0,"a "+taulaC.textFilera[taulaC.regr+i]+" s'ha modificat la funci"+Func.rB.getString("o_")+": "+t+ splitPan.FIL);}
                        sm=matriuPariVardeFuncio(" a "+taulaC.textFilera[taulaC.regr+i]+", ");if(sm==null){
                            return false;
                        }
                        if(i==0)simbolsParGraf0=sm;
                        else if(i==1)simbolsParGraf1=sm;
                        else simbolsParGraf2=sm;
                    }
                }
            }
        }
        if(taulaD.hihaDades){
            arr_simbolsDer = new ArrayList<>();
            String[] c=new String[0];
            for(int i=0;i<taulaD.simbol.length;i++){
                arr_simbolsDer.add(i,c);
                if(!modificacionsInicials(taulaD.matriu[i+1][4],"a taula funcions parcials, "))return false;
                if(!funcSup[0].equals(taulaD.matriu[i+1][4])){taulaD.matriu[i+1][4]=funcSup[0];taulaD.tl.setValueAt((Object)funcSup[0], i, 4);Func.append(1,Func.avis);Func.append(0,"a taula func. parcials s'ha modificat la funci"+Func.rB.getString("o_")+" signe - per +-; de funci"+Func.rB.getString("o_")+": "+taulaD.simbol[i]+ splitPan.FIL);}
                String[] sm=matriuPariVardeFuncio("a taula funcions parcials, ");if(sm==null)return false;
                arr_simbolsDer.set(i,sm);
            }
        }
        return b;
    }
    public static boolean simbolsDeFuncionsSumatoris(){
        arr_simbolsSumF = new ArrayList<>();
        arr_simbolsSum = new ArrayList<>();
        arr_suportsimbolsSum = new ArrayList<>();//utilitzat per corretgir les possicions de les fileres quant la funcio conte variables unternes de fileres posteriors
        String[] c=new String[0];
        int l=2*(taulaI.matriu.length-1);
        int k=taulaI.matriuFunc[0].length*l;
        for(int i=0;i<k;i++){arr_simbolsSum.add(i,c);arr_suportsimbolsSum.add(i,c);}
        for(int i=0;i<taulaI.matriuFunc[0].length;i++){
            arr_simbolsSumF.add(i,c);
            if(!taulaI.matriuFunc[1][i].equals("")){
                 if(!modificacionsInicials(taulaI.matriuFunc[1][i],"a taula sumatoris funci"+Func.rB.getString("o_")+", columna: "+i+", "))return false;
                if(!funcSup[0].equals(taulaI.matriuFunc[1][i])){taulaI.matriuFunc[1][i]=funcSup[0];taulaI.tlF.setValueAt((Object)funcSup[0], 1, i);Func.append(1,Func.avis);Func.append(0,"a taula sumatoris s'ha modificat la funci"+Func.rB.getString("o_")+": "+taulaI.matriuFunc[1][i]+ splitPan.FIL);}
                String[] sm=matriuPariVardeFuncio("a taula sumatoris funci"+Func.rB.getString("o_")+", columna: "+i+", ");if(sm==null)return false;
                arr_simbolsSumF.set(i,sm);
                for(int j=1;j<taulaI.matriu.length;j++){
                    k=i*l+2*(j-1);
                    int col=i*3+1;
                    for(int m=0;m<2;m++){
                        if(!taulaI.matriu[j][col].equals("")){
                            if(!modificacionsInicials(taulaI.matriu[j][col],"a taula sumatoris, fila: "+j+" col: "+col+", "))return false;
                            if(!funcSup[0].equals(taulaI.matriu[j][col])){taulaI.matriu[j][col]=funcSup[0];taulaI.tl.setValueAt((Object)funcSup[0], j-1, col);Func.append(1,Func.avis);Func.append(0,"a taula sumatoris s'ha modificat la funci"+Func.rB.getString("o_")+": "+taulaI.matriu[j][col]+ splitPan.FIL);}
                            sm=matriuPariVardeFuncio("a taula sumatoris, fila: "+j+" col: "+col+", ");if(sm==null)return false;
                            arr_simbolsSum.set(k,sm);
                        }
                        k++;
                        col++;
                    }
                }
            }
        }
        return true;
    }
    public static void print_simbolsdeFuncions(){
        if(Func.tipusFuncioBol){
            System.out.println();System.out.println("s"+Func.rB.getString("i_")+"mbols taulaF: ");
            for(int i=0;i<taulaF.funcioT.length;i++){
                if(!taulaF.filaNop[i]){
                    String[] t=arr_simbolsFuncioTaulaF.get(i);
                    System.out.print("fila: "+i+" >: ");
                    for(int k=0;k<t.length;k++)System.out.print(t[k]+" ");System.out.println();
                }
            }System.out.println();
        }
        else {
            System.out.println();System.out.println("s"+Func.rB.getString("i_")+"mbols Funci"+Func.rB.getString("o_")+": ");
            for(int k=0;k<simbolsFuncio.length;k++)System.out.print(simbolsFuncio[k]+" ");System.out.println();
            
            if(taulaC.abcisaFunGrafBol[0]){
                System.out.println("simbolsAbcissa: ");
                for(int k=0;k<simbolsAbcissa.length;k++)System.out.print(simbolsAbcissa[k]+" ");System.out.println();
            }
            if(Func.graficReg){
                System.out.println();System.out.print("simbolsParGraf0: ");
                for(int k=0;k<simbolsParGraf0.length;k++)System.out.print(simbolsParGraf0[k]+" ");System.out.println();
                System.out.print("simbolsParGraf1: ");
                for(int k=0;k<simbolsParGraf1.length;k++)System.out.print(simbolsParGraf1[k]+" ");System.out.println();
                System.out.print("simbolsParGraf2: ");
                for(int k=0;k<simbolsParGraf2.length;k++)System.out.print(simbolsParGraf2[k]+" ");System.out.println();System.out.println();
            }
        }
        if(taulaI.hihaDades){
            System.out.println();
            System.out.println("simbols Sumatoris: ");
            String[] c=new String[0];
            int l=2*(taulaI.matriu.length-1);
            for(int i=0;i<taulaI.matriuFunc[0].length;i++){ 
                String[] t=arr_simbolsSumF.get(i);
                System.out.print("columna de funci"+Func.rB.getString("o_")+": "+i+" >: ");
                for(int k=0;k<t.length;k++)System.out.print(t[k]+" ");System.out.println();
                for(int j=1;j<taulaI.matriu.length;j++){
                    int k=i*l+2*(j-1);
                    for(int m=0;m<2;m++){
                        t=arr_simbolsSum.get(k);
                        System.out.print("fila: "+(j-1)+" columna"+(i*3+m+1)+" >: ");
                        for(int n=0;n<t.length;n++)System.out.print(t[n]+" ");System.out.println();
                        k++;
                    }
                }
            }      
        }
        if(taulaD.hihaDades){
            System.out.println("s"+Func.rB.getString("i_")+"mbols funcionsParcials: ");
            for(int i=0;i<taulaD.simbol.length;i++){
                String[] t=arr_simbolsDer.get(i);
                System.out.print("fila: "+i+" >: ");
                    for(int k=0;k<t.length;k++)System.out.print(t[k]+" ");System.out.println();
            }
        }
    }
    public static boolean simbolsDefinits(){
        simbolsNoUtilitsatsInici();
        boolean b=true;
        String s;
        if(Func.tipusFuncioBol){
            if(!taulaF.idxdefIndef())b=false;
            for(int i=0;i<taulaF.funcioT.length;i++){// ha taula funcions  hi poden haber totes les variables menys les procedents de funcions parcials i ( aixo ja es contempla el el prcediment que es crida) i les internes de sumatoris
                if(!taulaF.filaNop[i]){
                    String[] t=arr_simbolsFuncioTaulaF.get(i);
                    if(t.length!=0){
                        t=arr_simbolsFuncioTaulaFReduida.get(i);
                        if(t.length!=0){
                            s=simbolsDefinits(t,true,true,true,false,true);if(!s.equals("")){
                                b=false;Func.append(1,Func.error);Func.append(0,"taula de funcions fila: "+i+" hi ha variables indefinides: "+s+splitPan.FIL);
                            }
                        }
                    }
                }
            }
        }
        else {
            if(simbolsFuncio!=null){
                s=simbolsDefinits(simbolsFuncio,true,true,true,false,true);if(!s.equals("")){
                    b=false;Func.append(1,Func.error);Func.append(0," funci"+Func.rB.getString("o_")+" principal hi ha s"+Func.rB.getString("i_")+"mbols indefinits: "+s+splitPan.FIL);
            }}
            if(taulaC.abcisaFunGrafBol[0]){
                boolean bol=taulaV.hihaVar;taulaV.hihaVar=false;//la funcio simbolsDefinits cal que no inclogui cerca de variables generals   
                posVar=simbolsDefinits(simbolsAbcissa,false,false,false,false,false).trim();//nomes parametres de capçaleraFixos funcions i operadors
                if(contaStatic!=1){
                    taulaV.hihaVar=bol;
                    s=simbolsDefinits(simbolsAbcissa,true,true,true,false,true);
                    if(!s.equals("")){b=false;Func.append(1,Func.error);Func.append(0," a funci"+Func.rB.getString("o_")+" abcissa dues opcions: "+FIL+"1.-incloure en la funci"+Func.rB.getString("o_")+" un sol s"+Func.rB.getString("i_")+"mbol de variable diferent als de la taulaV; aquesta variable ser"+Func.rB.getString("a")+" la variable general que es seleccioni en el seu moment;"+FIL+"2.- funci"+Func.rB.getString("o_")+" amb qualsevol tipus de variable directa indirecta general o par"+Func.rB.getString("a")+"metre variable de capçalera; pero; totes definides en las taules"+FIL+"simbols presents en la funci"+Func.rB.getString("o_")+": "+s+splitPan.FIL);}
                    else taulaC.abcisaFunGrafBol[1]=true;
                }
                taulaV.hihaVar=bol;
            }
            if(Func.graficReg){
                s=simbolsDefinits(simbolsParGraf0,true,true,true,false,true);if(!s.equals("")){
                    b=false;Func.append(1,Func.error);Func.append(0," regressi"+Func.rB.getString("o_")+" hi ha s"+Func.rB.getString("i_")+"mbols indefinits: "+s+splitPan.FIL);
                }
                s=simbolsDefinits(simbolsParGraf1,true,true,true,false,true);if(!s.equals("")){
                    b=false;Func.append(1,Func.error);Func.append(0," regressi"+Func.rB.getString("o_")+" hi ha s"+Func.rB.getString("i_")+"mbols indefinits: "+s+splitPan.FIL);
                }   
                s=simbolsDefinits(simbolsParGraf2,true,true,true,false,true);if(!s.equals("")){
                    b=false;Func.append(1,Func.error);Func.append(0," regressi"+Func.rB.getString("o_")+" hi ha s"+Func.rB.getString("i_")+"mbols indefinits: "+s+splitPan.FIL);
                }                   
            }
        }
        if(taulaI.hihaDades)for(int i=0;i<taulaI.matriuFunc[0].length;i++){
                String[] t=arr_simbolsSumF.get(i);
                if(t.length!=0){
                    s=simbolsDefinits(t,false,false,false,true,true);if(!s.equals("")){
                        b=false;Func.append(1,Func.error);Func.append(0,"  a taula sumatoris funcions filera: "+i+" hi ha s"+Func.rB.getString("i_")+"mbols indefinits: : "+s+splitPan.FIL);
                }}
                for(int j=0;j<taulaI.matriuVint.length;j++){
                    int  k=i*2*(taulaI.matriuVint.length)+j*2;
                    for(int l=0;l<2;l++){
                        t=arr_simbolsSum.get(k+l);
                        if(t.length!=0){
                            s=simbolsDefinits(t,false,false,false,true,true);if(!s.equals("")){
                                b=false;Func.append(1,Func.error);Func.append(0," a taula sumatoris funcions l"+Func.rB.getString("i_")+"mit filera: "+(j+1)+" hi ha s"+Func.rB.getString("i_")+"mbols indefinits: "+s+splitPan.FIL);
        }}}}}
        if(taulaD.hihaDades)for(int i=0;i<taulaD.simbol.length;i++){
                String[] t=arr_simbolsDer.get(i);
                if(t.length!=0){
                    s=simbolsDefinits(t,false,false,false,false,true);if(!s.equals("")){
                        b=false;Func.append(1,Func.error);Func.append(0," a taula de funcions parcials hi ha s"+Func.rB.getString("i_")+"mbols indefinits: "+s+splitPan.FIL);
        }}}
        return b;
    }
    public static boolean simbolsDefinitsdeGrafRegaFunc(){
        String s="";boolean bol=true;
        for(int i=0;i<simbolsParGraf0.length;i++){
            boolean b=false;
            for(int j=0;j<simbolsFuncio.length;j++)if(simbolsFuncio[j].equals(simbolsParGraf0[i]))b=true;
            if(!b)s+=simbolsParGraf0[i]+" ";
        }
        String [] m=matriudeFuncioCompleta(s);
        s="";
        for(int i=0;i<m.length;i++)for(int j=0;j<taulaP.simParCapVar.length;j++)if(m[i].equals(taulaP.simParCapVar[j])){s+=m[i]+" ";j=taulaP.simParCapVar.length;}
        if(!s.equals("")){bol=false;Func.append(1,Func.error);Func.append(0," els simbols de capçalera variables presents a "+taulaC.textFilera[4]+" ho han d'estar a la funci"+Func.rB.getString("o_")+" que es minimitza: "+s+splitPan.FIL);}
        s="";
        for(int i=0;i<simbolsParGraf2.length;i++){
            boolean b=false;
            for(int j=0;j<simbolsFuncio.length;j++)if(simbolsFuncio[j].equals(simbolsParGraf2[i]))b=true;
            if(!b)s+=simbolsParGraf2[i]+" ";
        }
        m=matriudeFuncioCompleta(s);
        s="";
        for(int i=0;i<m.length;i++)for(int j=0;j<taulaP.simParCapVar.length;j++)if(m[i].equals(taulaP.simParCapVar[j])){s+=m[i]+" ";j=taulaP.simParCapVar.length;}
        if(!s.equals("")){bol=false;Func.append(1,Func.error);Func.append(0," els s"+Func.rB.getString("i_")+"mbols de capçalera variables presents a "+taulaC.textFilera[6]+" ho han d'estar a la funci"+Func.rB.getString("o_")+" que es minimitza: "+s+splitPan.FIL);}
        return bol;
    }
    //1.- true =parCapNoVar permet tots els parametres de capçalera  que no contenent variables  els parametrede capçalera fixos de una filera es permeten a totes les funcions
    //2.- true =parCapVar permet tots els parametres de capçalera contenent variables  els parametrede capçalera fixos de una filera es permeten a totes les funcions
    //3._ true =varIntSumBol permet les variables internes de sumatoris
    //4._ true=varSumBol permet sumatoris
    //5._ true=funcParBol permet les funcions parcial no s'utilitza a regressio(abcissa)
    // al procediment simbols s'inclou la opcio de permetre parametres variables quan s'analitcen les funcions exteses aqui no cal perque la unica que els pot tenir son a la taulaF
    //boolean parCapNoVar,boolean parCapVar,boolean varBol,boolean parVarBol,boolean varSumBol,boolean varIntSumBol,boolean funcParBol
    public static String simbolsDefinits(String[] mF,boolean parCapNoVar,boolean parCapVar,boolean varSumBol,boolean varIntSumBol,boolean funcParBol){//parCapBolvarIntSumBol true nomes quan s'analitzen les funcions dels sumatoris
        boolean b=false;
        contaStatic=mF.length;
        boolean[] FBol=new boolean[contaStatic];
        if(taulaP.hihaPar){//els parametres de capalera de una filera s'analitzen sempre; els de mes d'una filera nomes a funcio principal, taula funcions i regressio
            if(taulaP.simParCapUnaFil.length>0){simbolsDefinits(mF,taulaP.simParCapUnaFil,simBolPC0,FBol);if(contaStatic==0)return "";}//el simbol que  es troba als parametres de capçalera
            if(parCapNoVar)if(taulaP.simParCapNoVar.length>0){simbolsDefinits(mF,taulaP.simParCapNoVar,simBolPC1,FBol);if(contaStatic==0)return "";}
            if(parCapVar)if(taulaP.simParCapVar.length>0){simbolsDefinits(mF,taulaP.simParCapVar,simBolPC2,FBol);if(contaStatic==0)return "";}
        }
        if(taulaV.hihaVar)simbolsDefinits(mF,taulaV.varsGenerals,simBolV,FBol);if(contaStatic==0)return "";//les variables generals s'analitzen a totes les funcions
        if(Func.tipusFuncioBol){//si hi ha taula de funcions els parametres variables es l'unic cas en que poden esser utilitzats i l'unic cas en que hi variables definides en la taula de funcions principal
            simbolsDefinits(mF,taulaF.simbolsReduitsNoNumerics,simBolF,FBol);if(contaStatic==0)return "";
            if(taulaP.hihaPar){simbolsDefinits(mF,taulaP.simPar,simBolPV,FBol);if(contaStatic==0)return "";}//el simbol que  es troba als parametres variables
        }
        if(taulaD.hihaDades&&funcParBol){simbolsDefinits(mF,taulaD.simbol,simBolD,FBol);if(contaStatic==0)return "";}//nomes en el cas que la funcio principal sigui unica (Func.tipusFuncioBol=false) s'utilitza la taula derivades e 
        if(taulaI.hihaDades){//els sumatoris no es troben en funcions de la mateixa taula de sumatoris ni en la taula Derivades ni a regressio abcissa
            if(varSumBol){simbolsDefinits(mF,taulaI.sumat,simBolI0,FBol);if(contaStatic==0)return "";}//el simbol es troba a taula sumatoris
            if(varIntSumBol){simbolsDefinits(mF,taulaI.varInternes,simBolI1,FBol);if(contaStatic==0)return "";}//nomes quan la funcio que s'analitza procedeix de sumatorisel simbol es troba a taula sumatoris variables internes 
        }
        String s="";
        for(int i=0;i<FBol.length;i++)if(!FBol[i])s+=mF[i]+" ";
        return s;
    }
    public static void simbolsDefinits(String[] mF,String[] mT,boolean[] Tbol,boolean[] FBol){//comprova que els simbols de la matriu mF es troben en la matriu mT; retorna els simbols separats per un spai o un buit
        for(int i=0;i<mF.length;i++)if(!FBol[i]){
            for(int j=0;j<mT.length;j++)if(mF[i].equals(mT[j])){
                FBol[i]=true;Tbol[j]=true;
                contaStatic--;j=mT.length;
    }}}
    public static boolean simbolsDefinitsNoUtilitsats(){//els parametres variables no es mostren perque a taulaF son opcionals i son obligats a funcions exteses
        String s1="",s2="",s3="";
        int cont=taulaP.simParCapNoVar.length+taulaP.simParCapVar.length;
        int cont1=taulaP.simParCapVar.length;
        if(taulaP.hihaPar){
            for(int i=0;i<simBolPC0.length;i++)if(!simBolPC0[i]){s1+=taulaP.simParCapUnaFil[i]+" ";}
            for(int i=0;i<simBolPC1.length;i++)if(!simBolPC1[i]){s2+=taulaP.simParCapNoVar[i]+" ";cont--;}
            for(int i=0;i<simBolPC2.length;i++)if(!simBolPC2[i]){s3+=taulaP.simParCapVar[i]+" ";cont--;cont1--;}
        }
        if(s1.length()>0||s2.length()>0||s3.length()>0){
            if(!Func.tipusFuncioBol){Func.append(1,Func.avis);Func.append(0,"taula Par"+Func.rB.getString("a")+"metres (taulaP): par"+Func.rB.getString("a")+"metres no utilitzats a cap funci"+Func.rB.getString("o_")+": "+s1+s2+s3+FIL);}
            else{
                String[] s=matriudeFuncioCompleta(s1+s2+s3);
                for(int i=0;i<s.length;i++)for(int j=0;j<taulaF.simbolsReduits.length;j++)if(s[i].equals(taulaF.simbolsReduits[j]))s[i]="";
                String s4="";for(int i=0;i<s.length;i++)if (!s[i].equals(""))s4+=s[i];
                if(Func.ampliarInfo&&s4.length()>0){Func.append(1,Func.avis);Func.append(0,"taula Par"+Func.rB.getString("a")+"metres (taulaP): par"+Func.rB.getString("a")+"metres no utilitzats: "+s4+FIL);}
            }
            if(!Func.tipusFuncioBol&&(s2.length()>0||s3.length()>0)){
                matriuPsupBol=true;
                matriuPsup=copiaMatriu(taulaP.matriu);
                String[] s=new String[cont];
                String[] s0=new String[cont1];
                cont=0;cont1=0;
                for(int i=0;i<simBolPC1.length;i++)if(simBolPC1[i]){s[cont]=taulaP.simParCapNoVar[i];cont++;}
                for(int i=0;i<simBolPC2.length;i++)if(simBolPC2[i]){s[cont]=taulaP.simParCapVar[i];s0[cont1]=taulaP.simParCapVar[i];cont++;cont1++;}
                String[][] m=new String[taulaP.matriu.length][cont+1+taulaP.simParCapUnaFil.length];for(int i=0;i<m.length;i++)for(int j=0;j<m[0].length;j++)m[i][j]="";
                int[] l=new int[cont];
                cont=1;
                for(int j=0;j<taulaP.matriu.length;j++)m[j][0]=taulaP.matriu[j][0];//la primera columna es copia
                for(int i=1;i<taulaP.matriu[0].length;i++)for(int k=0;k<s.length;k++)if(s[k].equals(taulaP.matriu[1][i])){
                    l[cont-1]=taulaP.longiP[i-1];
                    for(int j=0;j<taulaP.matriu.length;j++)m[j][cont]=taulaP.matriu[j][i];
                    cont++;
                }
                for(int i=0;i<l.length-1;i++)if(l[i]!=l[i+1]){Func.append(1,Func.error);Func.append(0,"totes les columnes de la taula par"+Func.rB.getString("a")+"metres (taulaP), amb mes d'una filera de valors (excepte par"+Func.rB.getString("a")+"metres fixos d'una sola filera) i que intervenen en el c"+Func.rB.getString("a")+"lcul, han de tenir la mateixa longitud:"+FIL);return false;}
                for(int i=1;i<taulaP.matriu[0].length;i++)for(int k=0;k<taulaP.simParCapUnaFil.length;k++)if(taulaP.simParCapUnaFil[k].equals(taulaP.matriu[1][i])){
                    m[1][cont]=taulaP.matriu[1][i];m[2][cont]=taulaP.matriu[2][i];
                    cont++;
                }
                if(s3.length()>0){
                    matriuVsupBol=true;
                    matriuVsup=copiaMatriu(taulaV.matriu);
                    s3="";
                    for(int i=0;i<s0.length;i++)for(int j=1;j<taulaP.matriu[0].length;j++)if(s0[i].equals(taulaP.matriu[1][j])){
                        for(int k=0;k<taulaP.longiP[j-1];k++){
                            try{Double.parseDouble(taulaP.matriu[k+2][j]);}
                            catch(Exception e){if(!taulaP.matriu[k+2][j].equals(""))s3+=taulaP.matriu[k+2][j]+" ";}
                        }
                    }
                    String [] m1=matriudeFuncioCompleta(s3);
                    taulaV.matriu=new String[taulaV.varsGenerals.length+m1.length+1][3];
                    System.arraycopy(matriuVsup[0], 0, taulaV.matriu[0], 0, 3);
                    int cont2=1;
                    for(int i=0;i<taulaV.idxVarGen.length;i++){
                        System.arraycopy(matriuVsup[taulaV.idxVarGen[i]+1], 0, taulaV.matriu[cont2], 0, 3);
                        cont2++;
                    }
                    for(int i=0;i<m1.length;i++)for(int j=1;j<matriuVsup.length;j++)if(m1[i].equals(matriuVsup[j][0])){
                        System.arraycopy(matriuVsup[j], 0, taulaV.matriu[cont2], 0, 3);
                        cont2++;
                    }
                }
                boolean b;
                cont=0;
                for(int i=m.length-1;i>0;i--){
                    b=false;
                    for(int j=1;j<m[0].length;j++)if(!m[i][j].equals(""))b=true;
                    if(!b)cont++;else i=0;
                }
                taulaP.matriu=new String[m.length-cont][m[0].length];
                for(int i=0;i<taulaP.matriu.length;i++)System.arraycopy(m[i], 0, taulaP.matriu[i], 0, m[i].length);
            }
        }
        s1="";
        if(taulaV.hihaVar)for(int i=0;i<simBolV.length;i++)if(!simBolV[i]){s1+=taulaV.varsGenerals[i]+" ";}
        if(s1.length()>0){
            Func.append(1,Func.avis);Func.append(0,"taula Variables (taulaV): variables generals no utilitzades: "+s1+FIL);
            if(!Func.tipusFuncioBol){
                String[] m=matriudeFuncioCompleta(s1);
                if(!matriuVsupBol){
                    matriuVsup=copiaMatriu(taulaV.matriu);
                    matriuVsupBol=true;
                }
                taulaV.matriu=new String[matriuVsup.length-m.length][3];
                cont=1;
                for(int j=1;j<matriuVsup.length;j++){
                    boolean b=false;
                    for(int i=0;i<m.length;i++)if(m[i].equals(matriuVsup[j][0]))b=true;
                    if(!b){System.arraycopy(matriuVsup[j], 0, taulaV.matriu[cont], 0, 3);cont++;}
                } 
            }
        }
        if(!Func.tipusFuncioBol&&(matriuPsupBol||matriuVsupBol)){
            try{if(!llistatReinici()){taulaP.matriu=copiaMatriu(matriuPsup);Func.analisiPreviBol=false;return false;}}
            catch (Exception e){
                taulaP.matriu=copiaMatriu(matriuPsup);
                Func.append(1,Func.avis);Func.append(0," s'han reiniciat les taules, per fer-les compatible amb les funcions, pero hi ha incompatibilitat probable entre taulesV i taulaP"+FIL);
                Func.analisiPreviBol=false;return false;
            }
        }
        s1="";
        if(taulaI.hihaDades){
            for(int i=0;i<simBolI0.length;i++)if(!simBolI0[i]){s1+=taulaI.sumat[i]+" ";}
            if(s1.length()>0){Func.append(1,Func.avis);Func.append(0,"taula sumatoris: s"+Func.rB.getString("i_")+"mbols no utilitzats a cap funci"+Func.rB.getString("o_")+": "+s1+FIL);s1="";}
        }
        if(taulaD.hihaDades){for(int i=0;i<simBolD.length;i++)if(!simBolD[i]){s1+=taulaD.simbol[i]+" ";}}
        if(s1.length()>0){Func.append(1,Func.avis);Func.append(0,"funcions parcials: s"+Func.rB.getString("i_")+"mbols no utilitzats a cap funci"+Func.rB.getString("o_")+": "+s1+FIL);}
        if(Func.graficReg)return simbolsDefinitsdeGrafRegaFunc();
        return true;
    }
    public static void simbolsNoUtilitsatsInici(){//executar previ a l'analisi de simbolsDefinits; presents a funcions inexistents a les taules
        if(Func.tipusFuncioBol)simBolF=new boolean [taulaF.simbolsReduitsNoNumerics.length];
        if(taulaV.hihaVar){
            simBolV=new boolean [taulaV.varsGenerals.length];
            simBolPV=new boolean [taulaP.simPar.length];
        }
        if(taulaI.hihaDades){
            simBolI0=new boolean [taulaI.sumat.length];
            simBolI1=new boolean [taulaI.varInternes.length];
        }
        if(taulaD.hihaDades)simBolD=new boolean [taulaD.simbol.length];
    }
    public static void simbolsNoUtilitsatsIniciParametres(){//executar previ a l'analisi de simbolsDefinits; presents a funcions inexistents a les taules
        if(taulaP.hihaPar){
            simBolPC0=new boolean [taulaP.simParCapUnaFil.length];
            simBolPC1=new boolean [taulaP.simParCapNoVar.length];
            simBolPC2=new boolean [taulaP.simParCapVar.length];
        }
    }
    public static boolean cadenes_sVO_deFuncions_taulaD(){
        boolean b=true;
        String[] c=new String[0];Double[] d=new Double[0];
        String s;
            arr_sVO_Der = new ArrayList<>();arr_dVO_Der = new ArrayList<>();
            String[] m=simbols(false,false,true,false,false,false,true);//nomes hi ha parametres fixos ja substituits; variables generals variables taulaF i simbols de funcio parcial de fileres superiors
            for(int i=0;i<taulaD.simbol.length;i++){//primer substituir els valors de dVO i sVO associats a parametres de funcions parcials constants de les fileres previes
                s=funcioTabulada(taulaD.matriu[i+1][4],m);
                if(s.length()!=taulaD.matriu[i+1][4].length()){Func.append(0,s+"  ( funcions parcials columna: "+i+FIL+")");taulaD.matriu[i+1][4]=s;taulaD.tl.setValueAt((Object)s, i, 4);}
                if(i>0)for(int j=0;j<i;j++)substituirfuncionsParcialsConstants(j);//cal substituir les funcions parcials constants previes a la funcio
                int cont=calcFuncioTab();if(cont==-1){Func.append(1,Func.error);Func.append(0," taula funcions parcials fila: "+i+FIL);return false;}
                taulaD.resultatFil[i]=NaN;
                if(cont==1){
                    if(dVO[0]!=null&&isFinite(dVO[0])){
                        taulaD.resultatFil[i]=dVO[0];
                        if(taulaD.hihaVGioFP[i]){//en principi el informe nomes es presenta si el programa es incorrecte
                            taulaD.hihaVGioFP[i]=false;
                            Func.append(1,Func.avis);Func.append(0," a taula de funcions parcials fila: "+i+" hi ha informaci"+Func.rB.getString("o_")+" contradict"+Func.rB.getString("_o")+"ria revisar; el precediment segueix considerant la funci"+Func.rB.getString("o_")+" constant = "+taulaD.resultatFil[i]+FIL);
                        }
                    }
                }
                arr_sVO_Der.add(sVO);arr_dVO_Der.add(dVO);
            }
        return b;
    }
    public static void print_cadenes_sVO_deFuncions_taulaD(){
        for(int i=0;i<taulaD.simbol.length;i++){//primer substituir els valors de dVO i sVO associats a parametres de funcions parcials constants de les fileres previes               
            sVO=arr_sVO_Der.get(i);dVO=arr_dVO_Der.get(i);
            print_dVO(i,"sVO-dVO reduida de funcions parcials: ");
        }
    }
    public static boolean cadenes_sVO_deFuncions_taulaI(){
        boolean b=true;
        String[] c=new String[0];Double[] d=new Double[0];
        String s;
        arr_sVO_SumF = new ArrayList<>();arr_dVO_SumF = new ArrayList<>();arr_sVO_Sum = new ArrayList<>();arr_dVO_Sum = new ArrayList<>();
        String[] m=simbols(false,false,true,false,false,true,true);//hi poden haver variables internes i funcions parcials sempre que no es tracti de la taula funcions
        int l=2*taulaI.matriuVint.length;
        int k=taulaI.matriuFunc[0].length*l;
        for(int i=0;i<k;i++){arr_sVO_Sum.add(i,c);arr_dVO_Sum.add(i,d);}
        for(int i=0;i<taulaI.matriuFunc[0].length;i++){
            s=funcioTabulada(taulaI.matriuFunc[1][i],m);
            if(s.length()!=taulaI.matriuFunc[1][i].length()){Func.append(0,s+"  ( funci"+Func.rB.getString("o_")+" sumatoris columna: "+i+FIL+")");taulaI.matriuFunc[1][i]=s;taulaI.tlF.setValueAt((Object)s, 0, i);}
            if(taulaD.hihaDades)substituirfuncionsParcialsConstants();//cal substituir les funcions parcials constants
            if(calcFuncioTab()==-1){Func.append(1,Func.error);Func.append(0," taula sumatoris funci"+Func.rB.getString("o_")+" columna: "+i+FIL);return false;}
            arr_sVO_SumF.add(sVO);arr_dVO_SumF.add(dVO);
            for(int j=0;j<taulaI.matriuVint.length;j++){
                k=i*l+2*j;
                int col=i*3+1;
                for(int n=0;n<2;n++){
                    s=funcioTabulada(taulaI.matriu[j+1][col],m);
                    if(s.length()!=taulaI.matriu[j+1][col].length()){Func.append(0,s+"  ( funci"+Func.rB.getString("o_")+" sumatoris fila: "+j+"; columna: "+i+FIL+")");taulaI.matriu[j+1][col]=s;taulaI.tl.setValueAt((Object)s, j, col);}
                    if(taulaD.hihaDades)substituirfuncionsParcialsConstants();//cal substituir les funcions parcials constants
                    if(calcFuncioTab()==-1){Func.append(1,Func.error);Func.append(0," taula sumatoris funcions l"+Func.rB.getString("i_")+"mit fila: "+j+"; columna: "+col+FIL);return false;}
                    if(!(sVO.length==1&&dVO[0]!=null&&isFinite(dVO[0]))){suportID.integralVaraLimitsBol[i][j][n]=true;suportID.integralVaraLimitsBol[i][j][2]=true;}
                    arr_sVO_Sum.set(k,sVO);arr_dVO_Sum.set(k,dVO);
                    k++;
                    col++;
                }
            }
        }
        return b;
    }
    public static void print_cadenes_sVO_deFuncions_taulaI(){
        int l=2*taulaI.matriuVint.length;
        for(int i=0;i<taulaI.matriuFunc[0].length;i++){
            sVO=arr_sVO_SumF.get(i);dVO=arr_dVO_SumF.get(i);
            for(int j=0;j<taulaI.matriuVint.length;j++){
                int k=i*l+2*j;
                for(int n=0;n<2;n++){
                   sVO=arr_sVO_Sum.get(k+n);dVO=arr_dVO_Sum.get(k+n);
                   printdVO("print_cadenes_sVO_deFuncions_taulaI");
                }
            }
        }
    }
    static void printdVO(String s){
        System.out.println(s+":  ");for(int i=0;i<sVO.length;i++)System.out.print(sVO[i]+" "+dVO[i]+" _ ");System.out.println();
    }
    public boolean cadenes_sVO_deFuncions(){
        iniciaValors();
        boolean b=true;
        String[] c=new String[0];double[] d=new double[0];
        String s;
        if(Func.tipusFuncioBol){
            taulaF.limdVO=new int[taulaF.funcioT.length][2];taulaF.limdVO[0][0]=0;
            arr_sVO_taulaF = new ArrayList<>();arr_dVO_taulaF = new ArrayList<>();
            String[] m=simbols(true,true,true,true,true,false,true);// ha taula funcions  hi poden haber totes les variables menys les internes de sumatoris i les procedents de funcions parcials i ( aixo ja es contempla el el prcediment que es crida)
            if(taulaI.hihaDades){
            }
            if(!taulaF.arraysPrevis())return false;
            int conta=0;
            ArrayList<Double[]> arrdVO=new ArrayList();
            for(int i=0;i<taulaF.funcioT.length;i++){
                if(!taulaF.filaNop[i]){
                    s=funcioTabulada(taulaF.funcioT[i],m);
                    if(s.length()!=taulaF.funcioT[i].length()){Func.append(0,s+"    taulaF fila: "+i+FIL);taulaF.funcioT[i]=s;taulaF.tl.setValueAt((Object)s, i, 0);}
                    arr_sVO_taulaF.add(sVO);arrdVO.add(dVO);
                }
                else{arr_sVO_taulaF.add(c);arrdVO.add(new Double[0]);}
            }
            if(!random(arrdVO))return false;
            for(int i=0;i<taulaF.funcioT.length;i++){
                if(!taulaF.filaNop[i]){
                    sVO=arr_sVO_taulaF.get(i);dVO=arrdVO.get(i);
                    if(taulaD.hihaDades||taulaI.hihaDades)substituirsumatiFPConstants();
                    if(calcFuncioTab()==-1){Func.append(1,Func.error);Func.append(0," taula funcions (taulaF) fila: "+i+FIL);return false;}
                    taulaF.valorsPrevis(i);
                    Cal.longiP=taulaF.longiP[i];
                    if(taulaF.funcArrBol[i])equacioExtesaParametresVariNoVarsubstituits(true);
                    conta+=dVO.length;taulaF.limdVO[i][1]=conta;
                    if(i<taulaF.funcioT.length-1)taulaF.limdVO[i+1][0]=taulaF.limdVO[i][1];
                    taulaF.dVO=Doubleadouble();
                    taulaF.sVO=new String[dVO.length];System.arraycopy(sVO, 0, taulaF.sVO, 0, sVO.length);
                    arr_sVO_taulaF.set(i,sVO);arr_dVO_taulaF.add(taulaF.dVO);
                    if(Cal.longiP<2||!taulaF.funcArrCapBol[i])indexsOperacionsiSimbols(false,true,true,true,true);//boolean tipusparametresBol,boolean variablesBol,boolean SumatorisBol,boolean funcParBol,boolean taulaFBol){
                    else indexsOperacionsiSimbols(true,true,true,true,true);
                    taulaF.arraysPosteriors(i);
                }
                else {
                    taulaF.limdVO[i][1]=conta;
                    if(i<taulaF.funcioT.length-1)taulaF.limdVO[i+1][0]=taulaF.limdVO[i][1];
                    arr_dVO_taulaF.add(d);
                }
            }
            taulaF.sVO=new String[taulaF.limdVO[taulaF.funcioT.length-1][1]];taulaF.dVO=new double[taulaF.sVO.length];
            for(int i=0;i<taulaF.funcioT.length;i++){
                taulaF.limdVO[i][1]=taulaF.limdVO[i][1]-taulaF.limdVO[i][0];
                System.arraycopy(arr_sVO_taulaF.get(i), 0, taulaF.sVO, taulaF.limdVO[i][0], taulaF.limdVO[i][1]);
                System.arraycopy(arr_dVO_taulaF.get(i), 0, taulaF.dVO, taulaF.limdVO[i][0], taulaF.limdVO[i][1]);
            }
            if(taulaF.hihaArray){if(!taulaF.funcionsArrays())return false;}
            if(!taulaF.colorsColors())return false;
            taulaF.valorsPosteriors();
            return true;
        }
        else {
            if(funcio.equals("")){Func.append(1,Func.avis);Func.append(0,"la funci"+Func.rB.getString("o_")+" principal no te contingut"+FIL);return true;} 
            if(taulaC.abcisaFunGrafBol[0]){//funcio amb nomes una variable que pot esser qualsevol de les variables generals i prendra el valor de qualsevol d'elles
                if(!taulaC.abcisaFunGrafBol[1]){
                    String[] m=simbols(false,false,false,false,true,false,true);//inicialment  nomes constants i sumatoris o funcions parcials constants
                    String[] n=new String[m.length+1];System.arraycopy(m, 0, n, 0, m.length);n[m.length]=posVar;
                    s=funcioTabulada(taulaC.matriu[4][1],n);
                    if(s.length()!=taulaC.matriu[4][1].length()){Func.append(0,s+"  "+taulaC.textFilera[taulaC.grafA]+FIL);taulaC.matriu[taulaC.grafA+1][1]=s;taulaC.tl.setValueAt((Object)s,taulaC.grafA,1);}
                    if(taulaD.hihaDades||taulaI.hihaDades)substituirsumatiFPConstants();
                    if(calcFuncioTab()==-1){Func.append(1,Func.error);Func.append(0," taulaC "+taulaC.textFilera[taulaC.grafA]+FIL);return false;}
                    if(taulaI.hihaDades&&fileresP(taulaI.sumat)||taulaD.hihaDades&&fileresP(taulaD.simbol)){
                        Func.append(1,Func.error);Func.append(0,"la funci"+Func.rB.getString("o_")+": "+taulaC.matriu[taulaC.grafA+1][1]+" no pot contenir s"+Func.rB.getString("i_")+"mbols de sumatoris ni funcions parcials variables"+FIL);
                        return false;
                    }
                    String[] cop=new String[taulaV.varstaulaV.length];System.arraycopy(taulaV.varstaulaV, 0, cop, 0, taulaV.varstaulaV.length);
                    taulaV.varstaulaV=new String[1];taulaV.varstaulaV[0]=posVar;
                    indexsOperacionsiSimbols(false,true,false,false,false);
                    taulaV.varstaulaV=new String[cop.length];System.arraycopy(cop, 0, taulaV.varstaulaV, 0, taulaV.varstaulaV.length);
                }
                else{
                    String[] m=simbols(true,true,true,false,true,false,true);// ha taula funcions (no extesa)  hi poden haber totes les variables menys les internes de sumatoris i els parametres variables de la taulaV
                    s=funcioTabulada(taulaC.matriu[4][1],m);
                    if(s.length()!=taulaC.matriu[4][1].length()){Func.append(0,s+"  "+taulaC.textFilera[taulaC.grafA]+FIL);taulaC.matriu[taulaC.grafA+1][1]=s;taulaC.tl.setValueAt((Object)s,taulaC.grafA,1);}
                    if(taulaD.hihaDades||taulaI.hihaDades)substituirsumatiFPConstants();
                    if(calcFuncioTab()==-1){Func.append(1,Func.error);Func.append(0," taulaC "+taulaC.textFilera[taulaC.grafA]+FIL);return false;}
                    boolean pv=fileresP(taulaP.simParCapVar);
                    boolean pf=fileresP(taulaP.simParCapNoVar);
                    if(!pv&&!pf)indexsOperacionsiSimbols(false,true,true,true,false);//boolean tipusparametresBol,boolean variablesBol,boolean SumatorisBol,boolean funcParBol,boolean taulaFBol){
                    else{
                        equacioExtesaParametresVariNoVarsubstituits(true);
                        indexsOperacionsiSimbols(true,true,true,true,false);
                    }
                    if(Cal.integralBol){Cur.indexsAbcisSum=copiaMatriu(Cal.indexsVGI);}//new int[Cal.indexsVGI.length][Cal.indexsVGI[0].length];System.arraycopy(Cal.indexsVGI, 0, Cur.indexsAbcisSum, 0, Cal.indexsVGI.length);}
                    else Cur.indexsAbcisSum=new int[0][0];
                    if(Cal.derivadaBol){Cur.indexsAbcisFP=copiaMatriu(Cal.indexsVGD);}//new int[Cal.indexsVGD.length][Cal.indexsVGD[0].length];System.arraycopy(Cal.indexsVGD, 0, Cur.indexsAbcisFP, 0, Cal.indexsVGD.length);}
                    else Cur.indexsAbcisFP=new int[0][0];
                }
                Cur.dVOAbcis=Doubleadouble();
                Cur.dvoAbcis=new double[dVO.length];
                Cur.sVOAbcis=new String[dVO.length];
                System.arraycopy(sVO, 0, Cur.sVOAbcis, 0, dVO.length);
                System.arraycopy(Cur.dVOAbcis, 0, Cur.dvoAbcis, 0, dVO.length);
                if(Cal.indexs.length>0){
                    Cur.indexs_Abcis=copiaMatriu(Cal.indexs);//=new int[0][0];
                }//matriu de calcul el segon[] si=0 operacio s1 1 index del numero i desti de la operacio si 2 index del numero, el calcul comença pel final de la funcio
                else Cur.indexs_Abcis=new int[0][0];
                Cur.indexsVar_Abcis=copiaMatriu(Cal.indexsVar);//new int[Cal.indexsVar.length][Cal.indexsVar[0].length];
                taulaC.abcisaGraf=Cur.fnc;
            }
            if(Func.graficReg){
                String[] m=simbols(true,false,false,false,true,false,true);//simbols(parCapNoVar,parCapVar,varGenBol,parVarBol,varSumBol,varIntSumBol,funcParBol)
                registre(m,0);
                if(hihaSum()||hihaFP()){//simbols de sumatoris i funcions parcials constants s'han d'haver substituit per un simbol numeric
                    Func.append(1,Func.error);Func.append(0," la funcio a "+taulaC.textFilera[taulaC.regr]+"; no pot contenir funcions parcials ni sumatoris amb variables generals (nomes v"+Func.rB.getString("a")+"lids si son constants)"+FIL+")");
                    return false;
                }
                if(!funcionsambParaNoVargraficRegressio(taulaC.matriu[taulaC.regr+1][0]))return false;//la cadena taulaC.matriu[taulaC.regr+1][0]) s'envia com informacio 
                m=simbols(true,false,false,false,true,false,true);
                registre(m,1);
                if(hihaSum()||hihaFP()){
                    Func.append(1,Func.error);Func.append(0," la funcio a "+taulaC.textFilera[taulaC.regr+1]+"; no pot contenir funcions parcials ni sumatoris amb variables generals (nomes v"+Func.rB.getString("a")+"lids si son constants)"+FIL+")");
                    return false;
                }
                if(!funcionsambParaNoVargraficRegressio(taulaC.matriu[taulaC.regr+2][0]))return false;
                m=simbols(true,true,true,false,true,false,true);
                registre(m,2);
                equacioExtesaParametresVariNoVarsubstituits(true);
                indexsOperacionsiSimbols(true,true,true,true,false);//boolean tipusparametresBol,boolean variablesBol,boolean SumatorisBol,boolean funcParBol,boolean taulaFBol)
                Cur.dVOG=Doubleadouble();
                Cur.dvoG=new double[dVO.length];System.arraycopy(Cur.dVOG, 0, Cur.dvoG, 0, Cur.dVOG.length);
                Cur.sVOG=new String[dVO.length];System.arraycopy(sVO, 0, Cur.sVOG, 0, sVO.length);
                Cur.indexsVar_gr=new int[0][0];
                Cur.indexs_gr=copiaMatriu(Cal.indexs);//=new int[Cal.indexs.length][Cal.indexs[0].length];System.arraycopy(Cal.indexs, 0, Cur.indexs_gr, 0, Cal.indexs.length);//matriu de calcul el segon[] si=0 operacio s1 1 index del numero i desti de la operacio si 2 index del numero, el calcul comença pel final de la funcio
                if(Cal.indexsVar.length>0){Cur.indexsVar_gr=copiaMatriu(Cal.indexsVar);}//=new int[Cal.indexsVar.length][Cal.indexsVar[0].length];System.arraycopy(Cal.indexsVar, 0, Cur.indexsVar_gr, 0, Cal.indexsVar.length);}
                if(Cal.integralBol){Cur.indexsVarInt_gr=copiaMatriu(Cal.indexsVGI);}//=new int[Cal.indexsVGI.length][Cal.indexsVGI[0].length];System.arraycopy(Cal.indexsVGI, 0, Cur.indexsVarInt_gr, 0, Cal.indexsVGI.length);}
                else Cur.indexsVarInt_gr=new int[0][0];
                if(Cal.derivadaBol){Cur.indexsVarDer_gr=copiaMatriu(Cal.indexsVGD);}//=new int[Cal.indexsVGD.length][Cal.indexsVGD[0].length];System.arraycopy(Cal.indexsVGD, 0, Cur.indexsVarDer_gr, 0, Cal.indexsVGD.length);}
                else Cur.indexsVarDer_gr=new int[0][0];
                if(Func.ampliarInfo)Func.append(0,"gr"+Func.rB.getString("a")+"fic inferior, ord. vs. abcs.: "+FIL+taulaC.matriu[taulaC.regr+3][1]+" vs. "+taulaC.matriu[taulaC.regr+1][1]+FIL+taulaC.matriu[taulaC.regr+2][1]+" vs. "+taulaC.matriu[taulaC.regr+1][1]+FIL);
                Cur.limindexs_gr=new int[Cal.longiP+1][2];
                for(int i=0;i<Cal.longiP;i++){Cur.limindexs_gr[i][0]=limits[i][0]+1;Cur.limindexs_gr[i][1]=limits[i][2];}
                Cur.limindexs_gr[Cal.longiP][1]=Cal.indexs.length-Cal.longiP+1;
            }
            String[] m=simbols(true,true,true,false,true,false,true);// ha taula funcions (no extesa)  hi poden haber totes les variables menys les internes de sumatoris i els parametres variables de la taulaV
            s=funcioTabulada(funcio,m);
            if(s.length()!=funcio.length()){Func.append(0,"la funci"+Func.rB.getString("o_")+" general s'ha modificat: "+s+FIL);funcio=s;Func.JFuncio.setText(s);}
            if(taulaD.hihaDades||taulaI.hihaDades)substituirsumatiFPConstants();
            if(calcFuncioTab()==-1){Func.append(1,Func.error);Func.append(0," funci"+Func.rB.getString("o_")+" principal "+FIL);return false;}
            boolean vg=(fileresP(taulaV.varsGenerals)||taulaI.hihaDades&&fileresP(taulaI.sumat)||taulaD.hihaDades&&fileresP(taulaD.simbol));
            boolean pv=fileresP(taulaP.simParCapVar);
            boolean pf=fileresP(taulaP.simParCapNoVar);
            if(!pv&&!pf){
                if(!vg) return calculUnic();//dVO ha d'esser una cadena amb un sol valor numeric
                else{//funcio amb variables generals
                    indexsOperacionsiSimbols(false,true,true,true,false);//boolean tipusparametresBol,boolean variablesBol,boolean SumatorisBol,boolean funcParBol,boolean taulaFBol){
                    Cur.dVO=Doubleadouble();
                    Cur.dvo=new double[dVO.length];System.arraycopy(Cur.dVO, 0, Cur.dvo, 0, Cur.dVO.length);
                    Cur.sVO=new String[dVO.length];System.arraycopy(sVO, 0, Cur.sVO, 0, sVO.length);
                }
            }
            if(pf&&!pv&&!vg){
                equacioExtesaParametresVariNoVarsubstituits(false);
                if(calcFuncioTab()==-1)return false;
                if(calcFuncioTab()==-1){Func.append(1,Func.error);Func.append(0," funci"+Func.rB.getString("o_")+" principal extesa "+FIL);return false;}
                return calculUnic();
            }
            if(pv||vg&&pf){
                equacioExtesaParametresVariNoVarsubstituits(true);
                indexsOperacionsiSimbols(true,true,true,true,false);
                Cur.dVO=Doubleadouble();
                Cur.dvo=new double[dVO.length];System.arraycopy(Cur.dVO, 0, Cur.dvo, 0, Cur.dVO.length);
                Cur.sVO=new String[dVO.length];System.arraycopy(sVO, 0, Cur.sVO, 0, sVO.length);
            }
        }
        return b;
    }
    public static boolean random(ArrayList<Double[]> arrdVO){
        String s="";
        for(int i=0;i<taulaF.colors.length;i++){
            sVO=splitPan.arr_sVO_taulaF.get(i);dVO=arrdVO.get(i);
            for(int j=0;j<sVO.length-3;j++)if(sVO[j].equals("rand")){
                if(!sVO[j+3].equals(")")||dVO[j+2]==null||!isFinite(dVO[j+2])){
                    Func.append(1,Func.error);Func.append(0," la funcio a fila: "+i+" hi ha la funci"+Func.rB.getString("o_")+" 'rand', aquesta funci"+Func.rB.getString("o_")+" nomes accepta un s"+Func.rB.getString("i_")+"mbol num"+Func.rB.getString("_e")+"ric ex: rand(2)"+FIL);return false;}
                s+=j+TAB+dVO[j+2]+TAB+"0"+FIL;
            }
        }
        String[][] st=splitPan.matriudeFuncioString(s);
        int cont=0;
        boolean[] b=new boolean [st.length];
        for(int i=0;i<st.length-1;i++){
            if(!b[i])for(int j=i+1;j<st.length;j++)if ((!b[j])&&st[i][1].equals(st[j][1])){st[j][2]=cont+"";b[j]=true;}
            if(!b[i+1]){cont++;st[i+1][2]=cont+"";}
        }
        int[] r=new int[st.length];for(int i=0;i<r.length;i++)r[i]=Integer.parseInt(st[i][2]);
        double[][] idxrandom=new double[cont+1][2];
        random=new Random[cont+1];
        cont=0;
        for(int i=0;i<r.length;i++)if(r[i]==cont){
            idxrandom[cont][0]=Double.parseDouble(st[i][1]);
            idxrandom[cont][1]=r[i];
            cont++;
        }
        for(int i=0;i<idxrandom.length;i++)random[i]=new Random(((int)idxrandom[i][0]));
        for(int i=0;i<taulaF.colors.length;i++){
            splitPan.sVO=splitPan.arr_sVO_taulaF.get(i);dVO=arrdVO.get(i);
             for(int j=0;j<splitPan.sVO.length-2;j++)if(splitPan.sVO[j].equals("rand")){
                for(int k=0;k<idxrandom.length;k++)if(dVO[j+2]==idxrandom[k][0]){dVO[j+2]=idxrandom[k][1];sVO[j+2]=idxrandom[k][1]+""; }
            }
            arr_sVO_taulaF.set(i,splitPan.sVO);arrdVO.set(i,dVO);
        }
        return true;
    }
    public static Object[][] copiaMatriu(Object[][] s){
        if(s.length==0)return s;
        Object [][]t=new Object[s.length][s[0].length];
        for(int i=0;i<s.length;i++)for(int j=0;j<s[0].length;j++)t[i][j]=s[i][j];
        return t;
    }
    public static boolean[][] copiaMatriu(boolean[][] s){
        if(s.length==0)return s;
        boolean [][]t=new boolean[s.length][s[0].length];
        for(int i=0;i<s.length;i++)for(int j=0;j<s[0].length;j++)t[i][j]=s[i][j];
        return t;
    }
    public static int[][] copiaMatriu(int[][] s){
        if(s.length==0)return s;
        int[][]t=new int[s.length][s[0].length];
        for(int i=0;i<s.length;i++)for(int j=0;j<s[0].length;j++)t[i][j]=s[i][j];
        return t;
    }
    public static String[][] copiaMatriu(String[][] s){
        if(s.length==0)return s;
        String[][]t=new String[s.length][s[0].length];
        for(int i=0;i<s.length;i++)for(int j=0;j<s[0].length;j++)t[i][j]=s[i][j];
        return t;
    }
    public static double[][] copiaMatriuParcial(double[][] t,double[][] s){
        int k=s[0].length;
        if(s.length==0)return s;
        for(int i=0;i<s.length;i++)for(int j=0;j<s[0].length;j++){t[i][j]=s[i][j];t[i][j+k]=s[i][j];}
        return t;
    }
    public static double[][] copiaMatriu(double[][] s){
        if(s.length==0)return s;
        double[][]t=new double[s.length][s[0].length];
        for(int i=0;i<s.length;i++)for(int j=0;j<s[0].length;j++)t[i][j]=s[i][j];
        return t;
    }
    public static double[][][] copiaMatriu(double[][][] s){
        if(s.length==0)return s;
        double[][][]t=new double[s.length][s[0].length][s[0][0].length];
        for(int i=0;i<s.length;i++)for(int j=0;j<s[0].length;j++)System.arraycopy(s[i][j],0,t[i][j],0,s[0][0].length);
        return t;
    }
    public boolean fileresP(String[] s){
        for(int i=0;i<s.length;i++)for(int j=0;j<sVO.length;j++)if(sVO[j].equals(s[i])||sVO[j].equals("-"+s[i]))return true;
        return false;
    }
    private boolean calculUnic(){
        Func.resultat=dVO[0]+"";
        if(!Func.noArrodonir)Func.append(0,funcio+" = "+dVO[0]+";  round("+Cur.fnc+") = "+splitPan.rodo(dVO[0])+splitPan.FIL);
        else Func.append(0,funcio+" = "+Func.resultat+splitPan.FIL);
        Func.resultat="";
        if(Func.clearFuncio){Func.JFuncio.setText(""); Func.JFuncio.requestFocus();}
        calculUnic=true;return true;
    }
    private static boolean hihaFP(){
        if(!taulaD.hihaDades)return false;
        for(int i=0;i<sVO.length;i++)for(int j=0;j<taulaD.simbol.length;j++)if(sVO[i].equals(taulaD.simbol[j]))return true;
        return false;
    }
    private static boolean hihaSum(){
        if(!taulaI.hihaDades)return false;
        for(int i=0;i<sVO.length;i++)for(int j=0;j<taulaI.sumat.length;j++)if(sVO[i].equals(taulaI.sumat[j]))return true;
        return false;
    }
    private static void print_dVO(int i, String s){
        System.out.println(s+" "+i);
        for(int j=0;j<sVO.length;j++)System.out.print(sVO[j]+"  ");System.out.println();
        for(int j=0;j<dVO.length;j++)System.out.print(dVO[j]+" ");System.out.println();System.out.println();
    }
    private static void registre(String[] m,int n){
        String s=funcioTabulada(taulaC.matriu[taulaC.regr+n+1][1],m);
        if(s.length()!=taulaC.matriu[taulaC.regr+n+1][1].length()){
            Func.append(0,s+"  ("+taulaC.textFilera[taulaC.regr+n]+FIL+")");
            taulaC.matriu[taulaC.regr+n+1][1]=s;taulaC.tl.setValueAt((Object)s, taulaC.regr+n, 1);
        }
        if(taulaD.hihaDades||taulaI.hihaDades)substituirsumatiFPConstants();
    }
    private static void substituirfuncionsParcialsConstants(int j){
        if(!isFinite(taulaD.resultatFil[j]))return;
        for(int k=0;k<sVO.length;k++){
            if(sVO[k].equals("-"+taulaD.simbol[j])){dVO[k]=-taulaD.resultatFil[j];sVO[k]=""+dVO[k];}
            else if(sVO[k].equals(taulaD.simbol[j])){dVO[k]=taulaD.resultatFil[j];sVO[k]=""+dVO[k];}
        }
        taulaD.simbolsDconstants[j]=true;
    }
    private static void substituirfuncionsParcialsConstants(){
        for(int j=0;j<taulaD.simbol.length;j++)substituirfuncionsParcialsConstants(j);
    }
    private static void substituirsumatorisConstants(){
        for(int j=0;j<taulaI.sumat.length;j++){
            if(taulaI.sumatConstants[j]){
                for(int k=0;k<sVO.length;k++){
                    if(sVO[k].equals("-"+taulaI.sumat[j])){dVO[k]=-Cal.supID.integral[j];sVO[k]=""+dVO[k];}
                    else if(sVO[k].equals(taulaI.sumat[j])){dVO[k]=Cal.supID.integral[j];sVO[k]=""+dVO[k];}
                }
            }
        }
    }
    private static void substituirsumatiFPConstants(){
        if(taulaD.hihaDades)substituirfuncionsParcialsConstants();
        if(taulaI.hihaDades)substituirsumatorisConstants();
    }
    public static double[] Doubleadouble(){
        double[] dV=new double[dVO.length];
        for(int i=0;i<dVO.length;i++){
            if(dVO[i]!=null)dV[i]=(double)dVO[i];
            else dV[i]=NaN;
        }
        return dV;
    }
    private static void equacioExtesaParametresVariNoVarsubstituits(boolean bol){
        int k=(sVO.length+4)*Cal.longiP-2;
        if (k<=0)return;
        String[] sV=new String[k];Double[] dV =new Double[k];
        int cont=0;
        Double[] dv=new Double[dVO.length];System.arraycopy(dVO, 0, dv, 0, dVO.length);
        String[] sv=new String[sVO.length];System.arraycopy(sVO,0,sv,0,sVO.length);
        for(int i=1;i<=Cal.longiP;i++){
            dVO=new Double[dv.length];System.arraycopy(dv,0,dVO,0,dv.length);
            sVO=new String[sv.length];System.arraycopy(sv,0,sVO,0,sv.length);
            parametresVariNoVaraFuncio(i);
            calcFuncioTab();
            sV[cont]=("(");cont++;
            System.arraycopy(sVO,0,sV,cont,sVO.length);
            System.arraycopy(dVO,0,dV,cont,sVO.length);
            cont+=sVO.length;
            sV[cont]=(")");cont++;
            if(i<Cal.longiP){
                if(bol){sV[cont]=FIL;cont++;}
                sV[cont]="+"; cont++;}
        }
        if(!Func.tipusFuncioBol&&Func.ampliarInfo&&Cal.longiP>1){
                Func.append(0,"funci"+Func.rB.getString("o_")+" amb matriu de par"+Func.rB.getString("a")+"metres"+FIL);
                for(int l=0;l<cont;l++){
                    if(dV[l]!=null)Func.append(0,""+dV[l]);
                    else Func.append(0,sV[l]);
                }
                Func.append(0,FIL);
            }
        sVO=new String[cont];dVO=new Double[cont];
        System.arraycopy(sV,0,sVO,0,cont);
        System.arraycopy(dV,0,dVO,0,cont);
     }
     private static boolean funcionsambParaNoVargraficRegressio(String info){
        Double[] dv=new Double[dVO.length];System.arraycopy(dVO, 0, dv, 0, dVO.length);
        String[] sv=new String[sVO.length];System.arraycopy(sVO,0,sv,0,sVO.length);
        for(int i=1;i<=Cal.longiP;i++){
            dVO=new Double[dv.length];System.arraycopy(dv,0,dVO,0,dv.length);
            sVO=new String[sv.length];System.arraycopy(sv,0,sVO,0,sv.length);
            parametresVariNoVaraFuncio(i);
            calcFuncioTab();
            if(dVO.length==1&&isFinite(dVO[0])){
                if(info.equals(taulaC.matriu[taulaC.regr+1][0]))Cur.funGrafP[0][i-1]=dVO[0];
                 else Cur.funGrafP[1][i-1]=dVO[0];
            }
            else {Func.append(1,Func.error);Func.append(0,"la funci"+Func.rB.getString("o_")+" ("+info+") no pot contenir variables, el gr"+Func.rB.getString("a")+"fic de par"+Func.rB.getString("a")+"metres no s'executara,"+FIL);return false;}
        }
        return true;
    }
    public static void parametresVariNoVaraFuncio(int idx){//b=true quan s'introdueixen els valors dels parametres fixos
        for(int i=0;i<taulaP.parametres.length;i++){//per cada columna
            for(int j=0;j<sVO.length;j++){
                if(sVO[j].equals(taulaP.parametres[i][0])){
                    sVO[j]=taulaP.parametres[i][idx];//per totes les columnes compara el simbol de sVO am els simbols de la filera idx si es una variable Par fa null
                    try{dVO[j]=Double.parseDouble(comApun(sVO[j]));}
                    catch(Exception e){dVO[j]=null;}
                }
                else if(sVO[j].equals("-"+taulaP.parametres[i][0])){
                    sVO[j]="-"+taulaP.parametres[i][idx];
                    if(sVO[j].startsWith("--"))sVO[j]=sVO[j].substring(2);
                    try{dVO[j]=Double.parseDouble(comApun(sVO[j]));}
                    catch(Exception e){dVO[j]=null;}
                }
            }
        }
    }
    public static String funcioTabulada(String f,String[] m){//calcula les matrius dVO isVO i retorna la cadena corretgida amb els parametres fixos de una filera substituits
        String fnc=f;
        String funcioTabulada="";
        String s1;
        while(f.length()>0){
            s1=simbolsDefuncio(f,m);
            if(s1.length()>0){
                funcioTabulada+=s1+" ";
                f=f.substring(contaStatic);
            }
            else return fnc;
        }
        m=matriudeFuncioCompleta(funcioTabulada);
        sVO=redueixParentesi(m);
        if(m.length!=sVO.length){
            Func.append(1,Func.avis+": ");
            f="";for(int j=0;j<sVO.length;j++)f+=sVO[j];
            Func.append(0," funci"+Func.rB.getString("o_")+" "+fnc+FIL+
            "nom"+Func.rB.getString("e_")+"s en el cas de funcions precedint el parent"+Func.rB.getString("_e")+"si '('; es encertat incloure un sol valor entre par"+Func.rB.getString("_e")+"ntesi"+FIL+
            "en la reste de situacions no es necessari;"+FIL+" molta atencio el signe menys '-' en el c"+Func.rB.getString("a")+"lcul te una  prioritat nom"+Func.rB.getString("e_")+"s per darrera dels par"+Func.rB.getString("_e")+"ntesi, canvia el signe del valor que el segueix"+FIL+"tant si es tracta de una variable o par"+Func.rB.getString("a")+"metre com de una seq"+Func.rB.getString("u")+"encia de valors entre par"+Func.rB.getString("_e")+"ntesi per exemple: -(-1+-1)^2=2^2=4 pero -((-1+-1)^2)=-(-2^2)=-4;  s'eliminent els parentesi; comprobarla funci"+Func.rB.getString("o_")+": "+f+FIL); 
            fnc=f;
        }
        //els parametres fixos sempre s'incorporen no modifiquen pero la cadena f1 que retorna la cadena que en tot cas es substituira a taula;
        for (int i=0;i<sVO.length;i++)for(int j=0;j<taulaP.simParCapUnaFil.length;j++){
                if(sVO[i].equals("-"+taulaP.simParCapUnaFil[j])){
                    double d=-taulaP.dsimParCapUnaFil[j];
                    sVO[i]=""+d;
                }
                else if(sVO[i].equals(taulaP.simParCapUnaFil[j])){
                    sVO[i]=""+taulaP.dsimParCapUnaFil[j];
                }
        }
        dVO=new Double[sVO.length];
        for (int i=0;i<sVO.length;i++){
            try{dVO[i]=Double.parseDouble(comApun(sVO[i]));} 
            catch (NumberFormatException ex) {dVO[i]=null;}
        }
        return fnc;
    }
    private static  String[] redueixParentesi(String[] m){
        int conta=0;
        for(int i=0;i<m.length-2;i++)if(m[i].equals("(")&&m[i+2].equals(")")){
            if(i>0){
                if(m[i-1].equals("-")){
                    if(m[i+1].startsWith(("-")))m[i+1]=m[i+1].substring(1);
                    else m[i+1]="-"+m[i+1];
                    m[i-1]="";m[i]="";m[i+2]="";conta+=3;
                }
                else{
                    boolean b=false;
                    for(int j=0;j<Func.funcions.length;j++)if(i>0&&m[i-1].equals(Func.funcions[j])){b=true;j=Func.funcions.length;}
                    if(!b){ m[i]="";m[i+2]="";conta+=2;}
                }
            }
            else {m[i]="";m[i+2]="";conta+=2;}
        }
        if(conta==0)return m;
        String[] m1=new String[m.length-conta];
        conta=0;
        for(int i=0;i<m.length;i++)if(!m[i].equals("")){m1[conta]=m[i];conta++;}
        return m1;
    }
    private static String[] simbols(boolean parCapNoVar,boolean parCapVar,boolean varGenBol,boolean parVarBol,boolean varSumBol,boolean varIntSumBol,boolean funcParBol){//genera la matriu m que conte tots el simbols de parametres i variables depents del tipus de funcio que s'analitzara
        int cont=0;
        if(taulaP.hihaPar){
            cont+=simBolPC0.length;
            if(parCapNoVar)cont+=simBolPC1.length;
            if(parCapVar)cont+=simBolPC2.length;
        }
        if(taulaV.hihaVar){
            if(varGenBol)cont+=simBolV.length;
            if(parVarBol)cont+=simBolPV.length;
        }
        if(taulaI.hihaDades){
            if(varSumBol)cont+=simBolI0.length;
            if(varIntSumBol)cont+=simBolI1.length;
        }
        if(taulaD.hihaDades&&funcParBol)cont+=simBolD.length;
        if(Func.tipusFuncioBol){
            cont+=simBolF.length;
            cont+=taulaF.simbolsElements.length;
        }
        String[] ms=new String[cont];
        cont=0;
        if(taulaP.hihaPar){
            for(int i=0;i<simBolPC0.length;i++){ms[cont]=taulaP.simParCapUnaFil[i];cont++;}
            if(parCapNoVar)for(int i=0;i<simBolPC1.length;i++){ms[cont]=taulaP.simParCapNoVar[i];cont++;}
            if(parCapVar)for(int i=0;i<simBolPC2.length;i++){ms[cont]=taulaP.simParCapVar[i];cont++;}
        }
        if(taulaV.hihaVar){
            if(varGenBol)for(int i=0;i<simBolV.length;i++){ms[cont]=taulaV.varsGenerals[i];cont++;}
            if(parVarBol)for(int i=0;i<simBolPV.length;i++){ms[cont]=taulaP.simPar[i];cont++;}
        }
        if(taulaI.hihaDades){
            if(varSumBol)for(int i=0;i<simBolI0.length;i++){ms[cont]=taulaI.sumat[i];cont++;}
            if(varIntSumBol)for(int i=0;i<simBolI1.length;i++){ms[cont]=taulaI.varInternes[i];cont++;}
        }
        if(taulaD.hihaDades&&funcParBol)for(int i=0;i<simBolD.length;i++){ms[cont]=taulaD.simbol[i];cont++;}
        if(Func.tipusFuncioBol){
            for(int i=0;i<simBolF.length;i++){ms[cont]=taulaF.simbolsReduitsNoNumerics[i];cont++;}
            for(int i=0;i<taulaF.simbolsElements.length;i++){ms[cont]=taulaF.simbolsElements[i];cont++;}
        }
        String s;
        for(int i=0;i<ms.length;i++)for(int j=i+1;j<ms.length;j++){
            if(ms[j].length()>ms[i].length()){s=ms[i];ms[i]=ms[j];ms[j]=s;}
        }
        return ms;
    }
    private static String simbolsDefuncio(String f,String[] m){
        String s1=f.substring(0,1);
        if(Func.operador.contains(s1)){contaStatic=1;return s1;}
        for(int i=0;i<funcionsPar.length;i++){
            if(f.startsWith("-"+funcionsPar[i])){contaStatic=funcionsPar[i].length()+1;return "- "+funcionsPar[i].substring(0,funcionsPar[i].length()-1)+" (";}
            else if(f.startsWith(funcionsPar[i])){contaStatic=funcionsPar[i].length();return funcionsPar[i].substring(0,funcionsPar[i].length()-1)+" (";}
        }
        if (f.startsWith("-(")) {contaStatic=2;return "- (";}
        if ((s1.equals("(")) || s1.equals(")")) {contaStatic=1;return s1;}
        for(int j=0;j<m.length;j++){
            if(f.startsWith("-"+m[j])) {contaStatic=m[j].length()+1;return "-"+m[j];}
            else if(f.startsWith(m[j])) {contaStatic=m[j].length();return m[j];}
        }
        if(esNuOmenys(s1)){
            int k=1;
            for(int j=1;j<f.length();j++){
                s1=f.substring(j,j+1);
                if(esNuOeOmenys(s1)){k++;}
                else j=f.length();
            }
            try{s1=f.substring(0,k);Double.parseDouble(comApun(s1));contaStatic=s1.length();return s1;}
            catch(Exception e){}
        }
        return "";
    }
    private void iniciaValors(){
        Cal.indexs=new int[0][0];
        Cal.indexsVar=new int[0][0];
        Cal.indexsVarG=new int[0][0];
        Cal.limitindexsVarGen=new int[0][0];
        taulaF.indexsSumafuncioF=new int[0][0];
        taulaF.indexsDerafuncioF=new int[0][0];
        
        Cal.indexsVGD=new int[0][0];
        Cal.integralBol=false;
        Cal.derivadaBol=false;
    }
    public boolean indexsOperacionsiSimbols(boolean parametresBol,boolean variablesBol,boolean SumatorisBol,boolean funcParBol,boolean taulaFBol){
        iniciaValors();
        int[] idxVG=new int[0];
        int con=sVO.length;
        if(!taulaV.hihaVar&&!Func.tipusFuncioBol)return true;
        int contador=nombrefilesmatriuIndexs(sVO);
        if(SumatorisBol&&taulaI.hihaDades){//els sumatoris constants ja s'han incorporat com a valors numerics la resta per un motiu o altre s'han de calcular i previament s'hauran de introduir en les funcions dels sumatori els valors de les variables generals directes o idirectes i els valors de les variables de la taulaF
            int co=0,coT=0;//co=ontador de sumatoris dins de sVO per ficarles a indexsVar //coT=contador de sumatoris dins de sVO que contenent simbolsT encara que no variables generals directes o indirectes
            for(int i=0;i<con;i++)for(int j=0;j<taulaI.sumat.length;j++){
                if(sVO[i].equals(taulaI.sumat[j])||sVO[i].equals("-"+taulaI.sumat[j])){
                    if(taulaD.hihaDades&&suportID.hihaFPambVGaI[j]){Cal.derivadaBol=true;}
                    if(suportID.hihaVGioFPaI[j]){co++;}//si el sumatori present a la funcioanalitzada  conte variables generals 
                    else if(Func.tipusFuncioBol)coT++;//si el sumatori present a la funcioanalitzada no conte variables generals pero si variables de la taulaF
                }
            }
            Cal.indexsVGI=new int[co][2];//indexsVGI[i][2] [0]: indexs de dVO; [1]:columna sumatori ; els sumatoris amb variables generals impliquen cerca minim o en filera posterior trasllat dels valors de les variables i calcul funcio normal
            if(Func.tipusFuncioBol)taulaF.indexsSumafuncioF=new int[coT][2];//taulaF.indexsSumafuncioF[i][2] [0]: indexs de dVO; [1]:columna sumatori; sumatoris amb nomes variables taulaF
            co=0;coT=0;
            //el 0 index de indexVar es la posicio de la dins la matriu dVO el 1 es la posicio matriu taulaV.varstaulaV el 3 es el la funcio parcial relacionada amb el parametre o -1 si es variable general
            for(int i=0;i<con;i++)for(int j=0;j<taulaI.sumat.length;j++){
                if(sVO[i].equals(taulaI.sumat[j])||sVO[i].equals("-"+taulaI.sumat[j])){
                    if(suportID.hihaVGioFPaI[j]){//si true hi ha variable general io funcioparcial amb variables generals a la integral limits o funcio del sumatori j
                        Cal.indexsVGI[co][0]=i;Cal.indexsVGI[co][1]=j;
                        Cal.integralBol=true;
                        dVO[i]=1.0;co++;
                    }
                    else if(Func.tipusFuncioBol){
                        taulaF.indexsSumafuncioF[coT][0]=i;taulaF.indexsSumafuncioF[coT][1]=j;
                        dVO[i]=1.0;coT++;
                    }
                }
            }
        }
        //despres de calcular els indexs dels sumatoris que contenet variables generals cal incloure a suportID.hihaVG_aI els sumatoris que no contenint variables generals contenent funcions parcials que contenent variables generals
        if(funcParBol&&taulaD.hihaDades){
            int co=0,coT=0;
            for(int j=0;j<taulaD.simbol.length;j++)for(int i=0;i<con;i++){//con es el nombre de fileres de dVO o sV de la funcio principal
                if(sVO[i].equals(taulaD.simbol[j])||sVO[i].equals("-"+taulaD.simbol[j])){
                    if (taulaD.hihaVGioFP[j]){co++;Cal.derivadaBol=true;}
                    else if(Func.tipusFuncioBol)coT++;
                }
            }
            Cal.indexsVGD=new int[co][2];
            if(Func.tipusFuncioBol)taulaF.indexsDerafuncioF=new int[coT][2];
            co=0;coT=0;
            for(int i=0;i<con;i++)for(int j=0;j<taulaD.simbol.length;j++){
                if(sVO[i].equals(taulaD.simbol[j])||sVO[i].equals("-"+taulaD.simbol[j])){
                     if (taulaD.hihaVGioFP[j]){//0: possicio dVO  1:filera de taula funcions parcials  2: variable general de la funcio parcial taulaD
                        Cal.indexsVGD[co][0]=i;Cal.indexsVGD[co][1]=j;//0: possicio dVO  1:filera de taula funcions parcials  2: variable general de la funcio parcial taulaD
                        dVO[i]=1.0;co++;   
                     }
                     else if(Func.tipusFuncioBol){
                        taulaF.indexsDerafuncioF[coT][0]=i;taulaF.indexsDerafuncioF[coT][1]=j;
                        dVO[i]=1.0;coT++;
                    }
                }
            }
        }
        int co=0,coG=0,coP=0;
        Cal.indexsParv=new int[0][0];
        if(variablesBol){
           for(int i=0;i<con;i++){//con es el nombre de fileres de dVO o sV
               for(int j=0;j<taulaV.varstaulaV.length;j++){
                   if(sVO[i].equals(taulaV.varstaulaV[j])||sVO[i].equals("-"+taulaV.varstaulaV[j])){
                       dVO[i]=1.0;co++;
                       boolean b=false;
                       for(int k=0;k<Cal.idxVarGen.length;k++)if(j==Cal.idxVarGen[k]){coG++;b=true;k=Cal.idxVarGen.length;}
                       if(!b)coP++;
                   }
               }
           }
           Cal.indexsVar=new int[co][2];//variables generals+parametres variables taulaV
           Cal.indexsParv=new int[coP][2];//matriu provisional de parametres variables presents a taulaV
           idxVG=new int[coG];//matriu provisional indexs de variables generals presents a la funcio
           Cal.indexsVarG=new int[coG][2];
           co=0;coG=0;coP=0;
           for(int i=0;i<con;i++)for(int j=0;j<taulaV.varstaulaV.length;j++){
                if(sVO[i].equals(taulaV.varstaulaV[j])||sVO[i].equals("-"+taulaV.varstaulaV[j])){
                    Cal.indexsVar[co][0]=i;Cal.indexsVar[co][1]=j;
                    co++;
                    boolean b=false;
                    for(int k=0;k<Cal.idxVarGen.length;k++)if(j==Cal.idxVarGen[k]){
                        Cal.indexsVarG[coG][0]=i;Cal.indexsVarG[coG][1]=j;
                        idxVG[coG]=j;
                        coG++;b=true;
                        k=Cal.idxVarGen.length;
                    }
                    if(!b){Cal.indexsParv[coP][0]=i;Cal.indexsParv[coP][1]=j;coP++;}
                }
            }
        }
        //contador de variables dins de sVO per ficarles a indexsVar
        if(Func.tipusFuncioBol){
            for(int i=0;i<taulaF.simbolsReduits.length;i++)for(int j=0;j<dVO.length;j++){
                if(sVO[j].equals(taulaF.simbolsReduits[i])||sVO[j].equals("-"+taulaF.simbolsReduits[i]))dVO[j]=1.0;
            }
            for(int i=0;i<taulaF.simbolsElements.length;i++)for(int j=0;j<dVO.length;j++){
                if(sVO[j].equals(taulaF.simbolsElements[i])||sVO[j].equals("-"+taulaF.simbolsElements[i]))dVO[j]=1.0;
            }
        }
        if(!taulaV.hihaVar){//si no hi ha variables tampoc hi ha parametres variables si taulaF es activa hi pot haver variables de taulaF
            if(!Func.tipusFuncioBol)return true;//si no hi ha variables i taulaF inactiva nomes hi ha valors numerics per tant hi ha un resultat numeric calculat a dVO[0]
            else{
                Cal.indexs=new int[contador][3];
                calcIndexFunc();//si no hi ha variables de la taula V hi pot haber variables taulaF
                return true;
            }
        }
        Cal.indexs=new int[contador][3];
        calcIndexFunc();
        int[][] ivg=new int[0][0];
        if(Cal.indexsVarG.length>0){ivg=copiaMatriu(Cal.indexsVarG);}//=new int[Cal.indexsVarG.length][Cal.indexsVarG[0].length]; System.arraycopy(Cal.indexsVarG, 0, ivg, 0, ivg.length); }
        int[] p=new int[2];
        for(int i=0;i<Cal.indexsVarG.length;i++)for(int j=i+1;j<Cal.indexsVarG.length;j++){//ordena les indexs de variables generals a la funcio de manera que els segons index(el index a la taula variables) estguin junts ex 3 0, 15 0, 12 1, 34 1
            if(Cal.indexsVarG[j][1]<Cal.indexsVarG[i][1]){
                p=Cal.indexsVarG[j];
                Cal.indexsVarG[j]=Cal.indexsVarG[i];
                Cal.indexsVarG[i]=p;
            }
        }
        Cal.limitindexsVarGen=new int[Cal.longi][5];
        if(SumatorisBol&&taulaI.hihaDades){
            if(suportID.indexsVGaI.length>0){//els indexs de les variables generals s'ha de reduir, incloure nomes els indexs de variables generals utilitzades en la funcio
                Cal.limitindexsVarGen[suportID.indexsVGaI[0][1]][3]=0;//suportID.indexsVGaI[0][1] [1]: correspont al index la variable general [0]:coreespont al index de dVOI de taula Sumatoris
                for(int i=0;i<suportID.indexsVGaI.length-1;i++){
                    if(suportID.indexsVGaI[i][1]!=suportID.indexsVGaI[i+1][1]){
                        Cal.limitindexsVarGen[suportID.indexsVGaI[i][1]][4]=i+1;
                        Cal.limitindexsVarGen[suportID.indexsVGaI[i+1][1]][3]=i+1;
                    }
                }
                Cal.limitindexsVarGen[suportID.indexsVGaI[suportID.indexsVGaI.length-1][1]][4]=suportID.indexsVGaI.length;
            }
        }
        if(Cal.indexsVarG.length>0){
            Cal.limitindexsVarGen[Cal.indexsVarG[0][1]][1]=0;
            for(int i=0;i<Cal.indexsVarG.length-1;i++){
                if(Cal.indexsVarG[i][1]!=Cal.indexsVarG[i+1][1]){
                    Cal.limitindexsVarGen[Cal.indexsVarG[i][1]][2]=i;
                    Cal.limitindexsVarGen[Cal.indexsVarG[i+1][1]][1]=i+1;
                }
            }
            Cal.limitindexsVarGen[Cal.indexsVarG[Cal.indexsVarG.length-1][1]][2]=Cal.indexsVarG.length-1;
        }
        for(int k=0;k<idxVG.length;k++)Cal.limitindexsVarGen[idxVG[k]][0]=1;//idxVG index de les variables generals que es troben a la funcio analitzada
        boolean[] b=new boolean[Cal.idxVarGen.length];
        if(SumatorisBol&&taulaI.hihaDades){
            if(suportID.indexsVGaI.length>0){
                for(int k=0;k<suportID.idxVarGenIntegr.length;k++){
                    Cal.limitindexsVarGen[suportID.idxVarGenIntegr[k]][0]+=-2;
                    b[suportID.idxVarGenIntegr[k]]=true;
                }
            }
        }
        if(SumatorisBol&&taulaI.hihaDades&&taulaI.hihaDades){    
            if(taulaFBol){
               if(taulaF.sumambFPfilataulaFunaFila[0]){
                    for(int i=1;i<taulaF.sumambFPfilataulaFunaFila.length;i++)if(taulaF.sumambFPfilataulaFunaFila[i]){//si la filera de taulaF conte un sumatori ambfuncions parcial; els sumatoris que les contenent es seleccionen
                        for(int j=1;j<Cal.hihaFPIntegrBol[0].length;j++)if(Cal.hihaFPIntegrBol[i][j]){//pel sumatori especific es seleccionen le fileres de funcins parcials associades al sumatori
                            for(int k=0;k<taulaD.VGaFuncBol[0].length;k++)if (taulaD.VGaFuncBol[j-1][k]){//a funcio parcial es selccione les variables generals associades a aquesta //taulaD.VGaFuncBol per cada filera indica si la funcio es dependent directa o indirectament d'alguna varible (si hi ha tres variables generals la columna 0 es relaciona amb la primera variable  la 1 amb la segona etc) a taula F es corretgeig en funcio de les variables generals de la filera
                                if(!b[k]){Cal.limitindexsVarGen[Cal.idxVarGen[k]][0]+=-2;b[k]=true;}
                            }
                        }
                    }
                } 
           }
            else {
                for(int i=1;i<Cal.hihaFPIntegrBol.length;i++)for(int j=1;j<Cal.hihaFPIntegrBol[0].length;j++)if(Cal.hihaFPIntegrBol[i][j]){//pel sumatori especific es seleccionen le fileres de funcins parcials associades al sumatori
                    for(int k=0;k<taulaD.VGaFuncBol[0].length;k++)if (taulaD.VGaFuncBol[j-1][k]){//per lVGaFuncBola funcio parcial es selccione les variables generals associades a aquesta
                       if(!b[k]){Cal.limitindexsVarGen[Cal.idxVarGen[k]][0]+=-2;b[k]=true;}
                    }
                }   
            }
        }
        //si hi ha variable general a integral vol dir que hi ha variable general que es pot trobar o no a la funcio analitzada per tant valor previ de Cal.limitindexsVarGen[][0]pot esser 0 o 1
        //per tant si hi ha integral amb variables generals el valors sera -2 si no hi ha la variable general a la funcio o -1 si tambe hi ha variable general a la funcio
        //si hi ha funco parcial en aquest punt no sera una constant i per tant hi ha de haver variable general restarem 4 (els valors previs de Cal.limitindexsVarGen[][0] varien de 1 a -2) el resultat se que varia de -3 a -6
        if(Cal.derivadaBol)for(int k=0;k<taulaD.idxvarsdeFunDer.length;k++)Cal.limitindexsVarGen[taulaD.idxvarsdeFunDer[k]][0]+=-4;
        Cal.indexsParVG=new int[0][0];
        Cal.indexsParV=new int[0][0];
        if(parametresBol)indexsParVar(ivg);//parametresBol=true indica que es tracta de una funcio suma de funcions amb parametres variables
        return true;
    } 
    private void indexsParVar(int [][] ivg){
        int k=0,i,l=Cal.longiP-1;
        limits=new int[Cal.longiP][10];//limits[0][0]=0;limits[0][2]=0;limits[0][4]=0;limits[0][7]=0;
        for(i=0;i<l;i++)for(int j=k;j<dVO.length;j++)if(sVO[j].equals(FIL)){//el limit superior no inclou la seleccio el limit inferior si
            limits[i][1]=j;
            k=j+2;
            limits[i+1][0]=k;
            j=dVO.length;
        }
        limits[l][1]=dVO.length;
        k=1;
        for(i=0;i<l;i++)for(int j=k;j<Cal.indexs.length-l;j++)if(Cal.indexs[j][2]>limits[i][1]){//el limit superior el reduim amb les drreres sumes de funcionsprincipals
                limits[i][3]=j-1;
                limits[i+1][2]=j;
                k=j;
                j=Cal.indexs.length;
        }
        limits[l][3]=Cal.indexs.length-l-1;
        if(limits[l][3]<0)limits[l][3]=0;
        k=0;
        for(i=0;i<l;i++)for(int j=k;j<Cal.indexsParv.length;j++){//el limit superior el reduim amb les drreres sumes de funcionsprincipals
            if(Cal.indexsParv[j][0]>limits[i][1]){//si es superior al limit dVO superior fixa el limit superior dels Cal.indexsParv
                limits[i][5]=j-1;
                limits[i+1][4]=j;
                k=j;
                j=Cal.indexsParv.length;
            }
            else limits[i][6]++;
        }
        limits[l][5]=Cal.indexsParv.length-1;
        for(int j=k;j<Cal.indexsParv.length;j++)if(Cal.indexsParv[j][0]<=limits[l][1])limits[l][6]++;
        int VG_VP=Cal.longiP;
        for(int j=0;j<Cal.longiP;j++)if(Cal.longiPBol[j])VG_VP--;//VG_VP(sera el nombre de fileres de taula parametres que no conte parametres variables
        if(ivg.length==0)VG_VP=0;
        if(VG_VP!=0){
            k=0;
            for(i=0;i<l;i++)for(int j=k;j<ivg.length;j++){
                if(ivg[j][0]>limits[i][1]){
                    limits[i][8]=j-1;
                    limits[i+1][7]=j;
                    k=j;
                    j=ivg.length;
                }
                else limits[i][9]++;
            }
            limits[l][8]=ivg.length-1;
            for(int j=k;j<ivg.length;j++)if(ivg[j][0]<=limits[l][1])limits[l][9]++;
        }
        boolean[] b=new boolean[Cal.longiP];
        int cont;
        int max=0;for(int j=0;j<limits.length;j++)if(max<limits[j][6])max=limits[j][6];
        if(Cal.indexsParv.length>0){
            Cal.indexsParV=new int[taulaP.varPTV_varPTP.length][7+max];
            for(int ix=0;ix<Cal.indexsParV.length;ix++){
                Cal.indexsParV[ix][0]=taulaP.varPTV_varPTP[ix][0];//idxVarPar
                int fil=taulaP.varPTV_varPTP[ix][1];//filera de taulaP
                b[fil]=true;
                Cal.indexsParV[ix][1]=fil;//filera de taulaP
                Cal.indexsParV[ix][2]=limits[fil][2];//limit inferior operacions
                Cal.indexsParV[ix][3]=limits[fil][3];//limit superior operacions
                Cal.indexsParV[ix][4]=limits[fil][0]+1;//limit inferior dVO
                Cal.indexsParV[ix][5]=limits[fil][1]-Cal.indexsParV[ix][4];// limit superior dVO referit al limit inferior
                cont=0;
                for(int j=limits[fil][4];j<=limits[fil][5];j++)if(Cal.indexsParV[ix][0]==Cal.indexsParv[j][1])cont++;//per tots els indexs de parametresVariables
                Cal.indexsParV[ix][6]=cont;
                int con=7;
                for(int j=limits[fil][4];j<=limits[fil][5];j++)if(Cal.indexsParV[ix][0]==Cal.indexsParv[j][1]){
                    Cal.indexsParV[ix][con]=Cal.indexsParv[j][0];
                    con++;
                }
            }
        }
        if(VG_VP>0){
            max=0;for(int j=0;j<limits.length;j++)if(max<limits[j][9])max=limits[j][9];
            Cal.indexsParVG=new int[VG_VP][7+2*max];//nomes s'utilitzen quant hi ha variables generals hi hi ha fileres que no contenenent parametres variables aleshores es calcula la part de la funcio amb l'informacio de la matriu
            int[] filera=new int [VG_VP];
            cont=0;
            for(i=0;i<Cal.longiP;i++)if(!Cal.longiPBol[i]){filera[cont]=i;cont++;}
            for(int ix=0;ix<Cal.indexsParVG.length;ix++){
                Cal.indexsParVG[ix][0]=-1;//idxVarPar
                int fil=filera[ix];//filera de taulaP
                b[fil]=true;
                Cal.indexsParVG[ix][1]=fil;//filera de taulaP
                Cal.indexsParVG[ix][2]=limits[fil][2];//limit inferior operacions
                Cal.indexsParVG[ix][3]=limits[fil][3];//limit superior operacions
                Cal.indexsParVG[ix][4]=limits[fil][0]+1;//limit inferior dVO
                Cal.indexsParVG[ix][5]=limits[fil][1]-Cal.indexsParVG[ix][4];// limit superior dVO
                int con=7;
                for(int j=limits[fil][7];j<=limits[fil][8];j++){
                    Cal.indexsParVG[ix][con]=ivg[j][0];
                    con++;
                    Cal.indexsParVG[ix][con]=ivg[j][1];
                    con++;
                }
                Cal.indexsParVG[ix][6]=limits[fil][9];
            }
        }
        cont=0; for(i=0;i<b.length;i++)if(!b[i])cont++;
        Cal.indexsParNoVar=new int[cont][2];//les fileres de taulaP que no contenent parametres variables ni variables generals directes o indirectes contindran un sol valor numeric
        cont=0;for(i=0;i<b.length;i++)if(!b[i]){Cal.indexsParNoVar[cont][0]=i;Cal.indexsParNoVar[cont][1]=limits[i][0]+1;cont++;}
    }
    public static int calcIndexFunc(){
        String[] sV=new String[sVO.length];Double[] dV=new Double[sVO.length];
        System.arraycopy(sVO, 0, sV, 0, sVO.length);System.arraycopy(dVO, 0, dV, 0, sVO.length);
        return calcIndexFunc(sV,dV);
    }
    public static int calcIndexFunc(String[] sV,Double[] dV){//quant no cal coservar les matrius d'entrada
        int contador=0;
        for (int i =0; i<sV.length; i++) {
            if (sV[i].equals(")")) {
                int l;
                sV[i]="";
                for (int j = i-1; j>=0; j--) {
                    if (sV[j].equals("(")) {
                        sV[j]="";
                        contador=calculIndexFunc1(j+1,i,contador,sV,dV);
                        boolean b=false;
                        if(j>0&&esFunBol(sV[j-1])){
                            int posRes=0;//el resultat es situa a la posicio del valor numeric dins la funcio
                            for(l=0;l<Func.funcions.length;l++){if(sV[j-1].equals(Func.funcions[l])){Cal.indexs[contador][0]=l+1;l=Func.funcions.length;}}
                            for(l=j+1;l<i;l++){if (dV[l]!=null){posRes=l;l=sV.length;}}
                            Cal.indexs[contador][2]=posRes;Cal.indexs[contador][1]=posRes;
                            contador++;
                            if(j>1&&sV[j-2].equals("-")){
                                sV[j-2]="";
                                Cal.indexs[contador][0]=-5;//operacio canvi de signe
                                Cal.indexs[contador][2]=posRes;
                                Cal.indexs[contador][1]=posRes;
                                contador++;
                                j--;//hi ha dues posicions  a la esquerra que no son)
                            }
                            j--;
                        }
                        if(j>0&&sV[j-1].equals("-")||b){
                            Cal.indexs[contador][0]=-5;
                            for(l=j+1;l<i;l++){if (dV[l]!=null){Cal.indexs[contador][2]=l;l=i;}}
                            Cal.indexs[contador][1]=Cal.indexs[contador][2];
                            contador++;
                        } 
                        j=0;
                    }
                }
            }  
        }
        return calculIndexFunc1(0,sV.length,contador,sV,dV); 
    }
    private static int calculIndexFunc1(int j,int c,int contador,String[] sV,Double[] dV){
        for(int i=j+1;i<c;i++){
            if(sV[i].equals("=")){
                for(int k=i+1;k<c;k++){if(dV[k]!=null){Cal.indexs[contador][2]=k;dV[k]=null;k=c;}}
                for(int k=i-1;k>=j;k--){if(dV[k]!=null){Cal.indexs[contador][1]=k;k=0;}}
                Cal.indexs[contador][0]=97;
                sV[i]="";
                contador++;
                }
        }
        for(int i=j+1;i<c;i++){
            if(sV[i].equals("<")||sV[i].equals(">")||sV[i].equals(menor)||sV[i].equals(major)){
                for(int k=i+1;k<c;k++){if(dV[k]!=null){Cal.indexs[contador][2]=k;dV[k]=null;k=c;}}
                for(int k=i-1;k>=j;k--){if(dV[k]!=null){Cal.indexs[contador][1]=k;k=0;}}
                if(sV[i].equals("<"))Cal.indexs[contador][0]=98;
                if(sV[i].equals(">"))Cal.indexs[contador][0]=99;
                if(sV[i].equals(menor))Cal.indexs[contador][0]=95;
                if(sV[i].equals(major))Cal.indexs[contador][0]=96;
                sV[i]="";
                contador++;
                }
        }
        for(int i=j+1;i<c;i++){
            if(sV[i].equals("^")){
                for(int k=i+1;k<c;k++){if(dV[k]!=null){Cal.indexs[contador][2]=k;dV[k]=null;k=c;}}
                for(int k=i-1;k>=j;k--){if(dV[k]!=null){Cal.indexs[contador][1]=k;k=0;}}
                Cal.indexs[contador][0]=-1;
                sV[i]="";
                contador++;
                }
        }
        for(int i=j+1;i<c;i++){
            if(sV[i].equals("*")||sV[i].equals("/")){
                for(int k=i+1;k<c;k++){if(dV[k]!=null){Cal.indexs[contador][2]=k;dV[k]=null;k=c;}}
                for(int k=i-1;k>=j;k--){if(dV[k]!=null){Cal.indexs[contador][1]=k;k=0;}}
                if(sV[i].equals("*"))Cal.indexs[contador][0]=-2;
                if(sV[i].equals("/"))Cal.indexs[contador][0]=-3;
                sV[i]="";
                contador++;
                }
        }
        for(int i=j+1;i<c;i++){
            if(sV[i].equals("+")){
                sV[i]="";
                for(int k=i+1;k<c;k++){if(dV[k]!=null){Cal.indexs[contador][2]=k;dV[k]=null;k=c;}}
                for(int k=i-1;k>=j;k--){if(dV[k]!=null){Cal.indexs[contador][1]=k;k=0;}}
                Cal.indexs[contador][0]=-4;
                contador++;
                }
        }

        return contador;
    }
    //calcNum executa las operacions comensan per principi
    //a cada calcul redueix les matrius Cur.sVO idVO 
     //la operacio -func(num) la executa i el resultat el situa a la posicio del-(el valor numeric ja tindra el signe)
    public static int calcFuncioTab(){
        try{
            int contador=sVO.length;
            int cont;
            for (int i = 0; i <contador ; i++) {
                cont=contador;
                if (sVO[i].equals(")")) {
                    sVO[i]=TAB;
                    for (int j = i-1 ; j >=0; j--) {
                        if (sVO[j].equals("(")) {//selecciona la primera parella de parentesi i canvia els simbols pe blanc i TAB
                            sVO[j]=" ";
                            contador=calcFuncioTab1(j+1,i,contador);//analitza els operadors basics dins del parentesi
                            if(contador==-1)return -1;
                            if(j>0&&esFunBol(sVO[j-1])){//si el simbol precedent es una funcio l
                                if(Func.tipusFuncioBol&&sVO[j-1].equals("rand"))dVO[j+1]=null;// els randoms a taula F no s'han de reduir a valors numerics
                                if(j<contador-2&&dVO[j+1]!=null&&sVO[j+2].equals(TAB)){
                                    dVO[j-1]=calculFun(sVO[j-1],dVO[j+1]);
                                    if(!isFinite(dVO[j-1])){Func.append(1,Func.error);Func.append(0," resultat NaN funci"+Func.rB.getString("o_")+": "+sVO[j-1]+" ("+dVO[j+1]+")"+FIL);return -1;}
                                    sVO[j-1]="1";mou3(j);contador-=3;//mou !fun(1)!!>!fun!!
                                    if(j>1&&sVO[j-2].equals("-")){//si hi ha signe menys davant de funcio
                                      dVO[j-2]=-dVO[j-1];sVO[j-2]="1";mou1(j-1);contador-=1;
                                        if(!isFinite(dVO[j-2])){Func.append(1,Func.error);Func.append(0," resultat NaN signe - : "+sVO[j-1]+" ("+dVO[j]+")"+FIL);return -1;}
                                    }
                                }
                            }
                            else if(j>0&&sVO[j-1].equals("-")){
                                if(j<=contador-2&&dVO[j+1]!=null&&sVO[j+2].equals(TAB)){
                                    dVO[j-1]=-dVO[j+1];sVO[j-1]="1";mou3(j);contador-=3;//is el simbol previ a ( es menys i darrer simbol es ) mou -(1)>1
                                } 
                           }
                           else if(j<=contador-2&&dVO[j+1]!=null&&sVO[j+2].equals(TAB)){
                               dVO[j]=dVO[j+1];sVO[j]="1";mou2(j+1);contador-=2;//si es darrer simbol es ) mou (1)>1 i treu els parentesi
                           }
                            j=0;
                            int m=cont-contador;//nombre de possicions que s'eliminen
                            i-=cont-contador;//el index inicial es mou a l'esqueera el nombre de posicions que s'han eliminat
                            if(i<0)i=0;
                        }
                    } 
                }
            }
            contador=calcFuncioTab1(0,contador,contador);
            if(contador!=sVO.length){
                String[] sv=new String[contador];Double[] dv=new Double[contador];
                System.arraycopy(sVO, 0, sv, 0, contador); System.arraycopy(dVO, 0, dv, 0, contador);
                sVO=new String [contador];dVO=new Double [contador];
                System.arraycopy(sv, 0, sVO, 0, contador); System.arraycopy(dv, 0, dVO, 0, contador);
            }
            for(int i=0;i<dVO.length;i++){
                if(sVO[i].equals(" "))sVO[i]="(";
                if(sVO[i].equals(TAB))sVO[i]=")";
                if(dVO[i]==null){//restaura els valors numerics fets null a les potencies
                    try{dVO[i]=Double.parseDouble(comApun(sVO[i]));}
                    catch (NumberFormatException ex) {}
                }
            }
            return contador;
        }
        catch(Exception e){return -1;}
    }
    private  static int calcFuncioTab1(int j,int c,int contador){
        Double d;
        for(int i=j;i<c-2;i++){
            if(dVO[i]!=null&&sVO[i+1].equals("=")&&dVO[i+2]!=null){
                if((double)dVO[i]==(double)dVO[i+2])dVO[i]=1.0;
                else dVO[i]=0.0;
                mou2(i+1);contador-=2;c-=2;i--;
            }
        }
        for(int i=j;i<c-2;i++){
            if(sVO[i+1].equals("=")){
                if(dVO[i]!=null){sVO[i]=""+dVO[i];dVO[i]=null;}
                else if(dVO[i+2]!=null){sVO[i+2]=""+dVO[i+2];dVO[i+2]=null;}
            }
        }
        for(int i=j;i<c-2;i++){
                if(dVO[i]!=null&&(sVO[i+1].equals("<")||sVO[i+1].equals(">")||sVO[i+1].equals(menor)||sVO[i+1].equals(major))&&dVO[i+2]!=null){
                if(sVO[i+1].equals(menor)){
                    boolean pos=(dVO[i]>0);
                    dVO[i]=Math.min(Math.abs(dVO[i]),dVO[i+2]);
                    if(!pos)dVO[i]*=-1;
                    mou2(i+1);contador-=2;c-=2;i--;
                }
                else if(sVO[i+1].equals(major)){
                    boolean pos=(dVO[i]>0);
                    dVO[i]=Math.max(Math.abs(dVO[i]),dVO[i+2]);
                    if(!pos)dVO[i]*=-1;
                    mou2(i+1);contador-=2;c-=2;i--;
                }
                else if(sVO[i+1].equals("<")){dVO[i]=Math.min(dVO[i],dVO[i+2]);mou2(i+1);contador-=2;c-=2;i--;}
                else{dVO[i]=Math.max(dVO[i],dVO[i+2]);mou2(i+1);contador-=2;c-=2;i--;}}
        }
        for(int i=j;i<c-2;i++){
            if(sVO[i+1].equals("<")||sVO[i+1].equals(">")||sVO[i+1].equals(menor)||sVO[i+1].equals(major)){
                if(dVO[i]!=null){sVO[i]=""+dVO[i];dVO[i]=null;}
                else if(dVO[i+2]!=null){sVO[i+2]=""+dVO[i+2];dVO[i+2]=null;}
            }
        }
        for(int i=j;i<c-2;i++){
            if(sVO[i+1].equals("^")){
                if(dVO[i]!=null&&dVO[i+2]!=null){
                    d=dVO[i];
                    dVO[i]=Math.pow(dVO[i],dVO[i+2]);
                    if(!isFinite(dVO[i])){Func.append(1,Func.error);Func.append(0," resultat NaN pot"+Func.rB.getString("_e")+"ncia: "+d+" ^ "+sVO[i+2]+" = "+dVO[i]+FIL);return -1;}
                    mou2(i+1);contador-=2;c-=2;i--;
                }
            }
        }
        for(int i=j;i<c-2;i++){
            if(sVO[i+1].equals("^")){
                if(dVO[i]!=null){sVO[i]=""+dVO[i];dVO[i]=null;}
                else if(dVO[i+2]!=null){sVO[i+2]=""+dVO[i+2];dVO[i+2]=null;}
            }
        }
        for(int i=j;i<c-2;i++){
            if(dVO[i]!=null&&sVO[i+1].equals("*")&&dVO[i+2]!=null){//&&((i<c-3&&!sVO[i+3].equals("^"))||i>=c-3)){
                d=dVO[i];
                dVO[i]=d*dVO[i+2];

                if(!isFinite(dVO[i])){Func.append(1,Func.error);Func.append(0," resultat NaN suma : "+d+" * "+dVO[i+2]+" = "+dVO[i]+FIL);return -1;}
                mou2(i+1);contador-=2;c-=2;i-=1;
            }
            else if(dVO[i]!=null&&sVO[i+1].equals("/")&&dVO[i+2]!=null){//&&((i<c-3&&!sVO[i+3].equals("^"))||i>=c-3)){
                if(dVO[i+2]!=0){
                    d=dVO[i];dVO[i]=d/dVO[i+2];
                    if(!isFinite(dVO[i])){Func.append(1,Func.error);Func.append(0," resultat NaN suma : "+d+" * "+dVO[i+2]+" = "+dVO[i]+FIL);return -1;}
                    mou2(i+1);contador-=2;c-=2;i-=1;
                }
                else{
                    Func.append(1,Func.error);Func.append(0," divisi"+Func.rB.getString("o_")+" per cero: "+dVO[i]+" / "+sVO[i+2]+FIL);
                    return -1;
                }//divisio per cero
            }
        }
        for(int i=j;i<c-2;i++){
            if(sVO[i+1].equals("*")||sVO[i+1].equals("/")){
              if(dVO[i]!=null){sVO[i]=""+dVO[i];dVO[i]=null;}
                else if(dVO[i+2]!=null){sVO[i+2]=""+dVO[i+2];dVO[i+2]=null;}      
            }
        }
        for(int i=j;i<c-2;i++){
            if(dVO[i]!=null&&sVO[i+1].equals("+")&&dVO[i+2]!=null){
                d=dVO[i];
                dVO[i]=d+dVO[i+2];
                if(!isFinite(dVO[i])){Func.append(1,Func.error);Func.append(0," resultat NaN suma : "+d+" + "+dVO[i+2]+" = "+dVO[i]+FIL);return -1;}
                mou2(i+1);contador-=2;c-=2;i-=1;
            }
        }
        return contador;
    }
    private static void mou1 (int j){
        for(int i=j;i<sVO.length-1;i++){sVO[i]=sVO[i+1];dVO[i]=dVO[i+1];}
        sVO[sVO.length-1]="@";dVO[sVO.length-1]=null;
    }
    private static void mou2 (int j){
        for(int i=j;i<sVO.length-2;i++){sVO[i]=sVO[i+2];dVO[i]=dVO[i+2];}
        for(int i=sVO.length-2;i<=sVO.length-1;i++){sVO[i]="@";dVO[i]=null;}
    }
    private static void mou3 (int j){
        for(int i=j;i<sVO.length-3;i++){sVO[i]=sVO[i+3];dVO[i]=dVO[i+3];}
        for(int i=sVO.length-3;i<=sVO.length-1;i++){sVO[i]="@";dVO[i]=null;}
    }
    public static void error(String missatge) {JOptionPane.showMessageDialog(controllingFrame,"ERROR "+splitPan.FIL+missatge);}
    public static void avis(String missatge) {JOptionPane.showMessageDialog(controllingFrame,FIL+missatge+FIL);}     
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("SplitPaneDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        splitPan splitPaneDemo = new splitPan();
        frame.getContentPane().add(splitPaneDemo.getSplitPane());
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
