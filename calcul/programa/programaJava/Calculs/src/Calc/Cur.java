package Calc;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static java.lang.Double.NaN;
import static java.lang.Double.isFinite;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
public class Cur extends JPanel {
    public static Cur curs;
    public static Double dVO_[];
    public static double dVO[],dvo[];
    public static String sVO[];
    public static double dVOG[],dvoG[];
    public static String sVOG[];
    public static int indexs_Orden[][];
    public static int indexsVar_Orden[][];
    public static int indexsVarSelec_Orden;//index de la variable seleccionada a ordenada, sera la funcio de la-les variables independents
    public static int indexsVarSelec_lliure;//index de la variable seleccionada a abscissa, sera la variable que fa un recorregut del segment a cada punt es clacula el mini deixant lliura la de la ordenada
    public static int indexsSumat_Orden[][];
    public static boolean sumatOrdenBol;//true = hi ha sumatoris a la funcio ordenada
    public static double dVOAbcis[],dvoAbcis[];
    public static String sVOAbcis[];
    public static int indexs_Abcis[][];
    public static int indexsVar_Abcis[][];
    public static int indexsAbcisSum[][];
    public static int indexsAbcisFP[][];
    public static double[] resultatFuncAbcis;
    public static int indexs_gr[][];//matriu de calcul el segon[] si=0 operacio s1 1 index del numero i desti de la operacio si 2 index del numero, el calcul comença pel final de la funcio
    public static int indexsVar_gr[][];
    public static int limindexs_gr[][];//es copia de indexsParV les columnes 0 i 1
    public static int indexsVarInt_gr[][];
    public static int indexsVarDer_gr[][];
    public static double resultat; //resultat de la funcio global
    public static double resultatMin; //resultat minim absolud capturat que pot disminuir si el el resultat grafic de la funcio global es inferior
    public static double[] resultatM;//matriu de resultats de les funcions parcials
    public static double[] resultatP;//matriu de resultats de les funcions parcials una filera per cada variable a taulaP
    public static double[] resultatMcop;// copia matriu de resultats de les funcions parcials
    static double [] varAct,varCopia,varActGraf;
    public static int indexPunts;
    public static int[] puntsGraf=new int[11],mitatPuntsGraf=new int[11];
    static double[] funGraf;
    static boolean[] funGrafBol;
    static int indexAmp;
    static long[][] incCur;
    static double maxim=1.79769313486231580793728E308;
    static int[] limCur;
    static int[] limSupCalc;
    static int limSCalc;
    public static String text="";
    public static JPanel pan0,pan01,pan1;
    static long[] minIncrSignif;
    static double[] pondIncrVar;//static int cursorSenser=2147483647;// el cursor (sliper) pot esser possitiu o negatiu i ha d'esser un sencer//static int _cursorSenser=-2147483647;//abans-2147483648
    public static long[] cursor;//eslong i sempre positiu associat a les abcisees (variables) que poden esser + o -  el seu valor maxim es Cal.segment
    public static long[] cursorMinim;// inicialment =Cal.cursorMinim es el minim a cada calcul diagonal desprese de claculdiagonalGrafic1
    public static long[] cursorMinimGraf;//captura el cursorMinim del calcul despres si el resultat<resultatMin pren el valor de cursorMinim
    public static long[] cursorCentreGrafic;//es el punt mitat del grafic, s'obte al executar calculDiagonalGrafic 
    public static double[] variableFixa;
    public static long[] cursorFixa;
    public static boolean[] variableFixaBol;
    public static boolean cursorsCanviSentit;
    public static int idxP;//index de la variable amb maxim pendent (es corretgeig quan es mou el cursor  i tambe quan el cursor supera els limits min maxSlider)
    static int idxGraf;//al grafic es l'index de la variable seleccionada de la taula variables inicialment prent el valor de idxP
    public static JLabel[] sim=new JLabel[4];
    public static JButton capturaC,punts_mes,punts_menys,veu_esq,veu_dre,amp_mes,amp_menys,amp_minim,var_but,calcul_G,deriv,func;
    public static JLabel var_mes,var_menys,fixaV,alliberar,punts_text,mou_text,amp_text;
    static boolean paraCalcBol=false;
    static boolean paraCalcB=false;
    public static boolean ampCentreMinim=false;
    static boolean unaVarBol=false;//si true calcula el grafic en el cas en que nomes una variable es variable les altres son fixas
    public static boolean[] NaNBol;
    public static double[][] derivada;
    static double[][] suportDerivada;
    static double[][] puntsGrafics;
    static int contadorDerivada=0;
    public static boolean capBol=false;//si s'ha fet captura habilita el calcul grafic
    public static boolean capGBol=false;//si true  s'ha executat graficInicial() fent enter o minim aFunc. per tant hi ha informacio tant  Cal com a Cur 
    public static boolean veuBol;
    Thread temps,veu;
    static boolean capturaInfoBol=false;//true indica que s'ha fet click a grafic superior in s'ha seleccionat un punt del grafic si es fa captura de grafic es la info que passa a calcul numeric
    static int captFun;//es el punt mes proper al clicat a grafic superior
    static int captDer;
    public static int[][] abcOrd;//posicio relativa dels punts que es troben a grafic (grafic superior)
    public static int[][] abcOrdD;//grafic derivadaposicio relativa dels punts que es troben a grafic (grafic superior)
    public static String fnc=Func.rB.getString("simbolfuncio");
    public static boolean regresioGrafBol;
    public static double[][] funGrafP;
    public static boolean stopBucle;
    static suportID supID;
    public  Cur() {
        ini();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        grafic.punts = new double[0][0];
        derivada = new double[0][0];
        grafReg.puntsx = new ArrayList<Double>();
        grafReg.puntsy = new ArrayList<Double>();
        pan0 = new JPanel(new GridLayout(0, 1));
    	pan01 = new JPanel(new GridLayout(0, 1));
    	pan01.add(new grafReg());
        pan0.add(new grafic());
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        pan0.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {info(e);}public void mouseEntered(MouseEvent e) {}public void mouseExited(MouseEvent e) {}public void mousePressed(MouseEvent e) {}public void mouseReleased(MouseEvent e) {}
        });
        pan01.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {posGRegr(e);}public void mouseEntered(MouseEvent e) {}public void mouseExited(MouseEvent e) {}public void mousePressed(MouseEvent e) {}public void mouseReleased(MouseEvent e) {}
        });
    }
    public static void info(MouseEvent e){
        if(paraCalcB)return;
        try{
            paraCalcBol=true;paraCalcB=true;curs.Threadenable();
            if(funGraf==null)return;
            if(funGraf.length==0)return;
            captFun=0;captDer=0;
            double x,y;
            boolean derBol=false;
            try{//x i y indiquen la possicio en el grafic al fer click al ratoli 
                x=(e.getX());y=(e.getY());
                double min=maxim;
                double mx=min,my=min,d=min;
                for(int i=0;i<abcOrd.length;i++){//cerca el punt mes proper al clicat a grafic superior; abcOrdr es la distancia relativa dels punts respecte minX i maxY
                    mx=x-abcOrd[i][0];
                    my=y-abcOrd[i][1];
                    d=mx*mx+my*my;
                    if (d<min) {min=d;captFun=i;}
                }
                if(contadorDerivada>0)for(int i=0;i<abcOrdD.length;i++){//cerca el punt mes proper al clicat a grafic superior
                    mx=x-abcOrdD[i][0];
                    my=y-abcOrdD[i][1];
                    d=mx*mx+my*my;
                    if (d<min) {
                        derBol=true;
                        min=d;
                        captDer=i;
                        captFun=i+contadorDerivada;
                    }
                } 
                if(captFun<0)return;
                if(captFun>=puntsGraf[indexPunts])return;//l1=puntsGraf[indexPunts]-1;
            }
            catch(java.lang.NullPointerException ex){return;}
            grafic.puntsAssoc[0]=captFun;
            if(derBol)grafic.puntsAssoc[1]=captDer;
            else if(contadorDerivada>0&&captFun>=contadorDerivada)grafic.puntsAssoc[1]=captFun-contadorDerivada;
            String s1="",s2="";
            if(!unaVarBol)s2=taulaV.varstaulaV[idxP];
            else s2=taulaV.varstaulaV[idxGraf];
            String s=splitPan.FIL+"punt: "+(captFun+1)+", de ("+abcOrd.length+")"+splitPan.FIL;
            int k=captFun-mitatPuntsGraf[indexPunts];
            for(int j=0;j<Cal.longi;j++){
                if(variableFixaBol[j])cursor[j]=cursorFixa[j];
                else{
                    cursor[j]=cursorCentreGrafic[j]+(long)(k*incCur[indexPunts][indexAmp]*pondIncrVar[j]);
                    if(cursor[j]>Cal.segment)cursor[j]=Cal.segment;
                    else if(cursor[j]<0)cursor[j]=0;
                }
            }
            if(!unaVarBol){
                s+="abcisa principal: "+taulaV.varstaulaV[idxP]+splitPan.FIL;
                if(Func.ampliarInfo){
                    for(int j=0;j<Cal.longi;j++){
                        if(variableFixaBol[j])s1+=taulaV.varstaulaV[j]+" (fixa): "+cursor[j]+", ";
                        else{
                            if(cursor[j]>Cal.segment){s1+=taulaV.varstaulaV[j]+": "+">l"+Func.rB.getString("i_")+"mit ("+Cal.dMatriuVar[j][8]+")";}
                            else if(cursor[j]<0){s1+=taulaV.varstaulaV[j]+": "+"<l"+Func.rB.getString("i_")+"mit ("+Cal.dMatriuVar[j][7]+")";}
                        }
                    }
                    if(s1.length()>0){s+="cursors:  "+s1+splitPan.FIL;}
                }
            }
            else{
                if(Func.ampliarInfo){
                    cursor[idxGraf]=cursorCentreGrafic[idxGraf]+(long)(k*incCur[indexPunts][indexAmp]);
                    if(cursor[idxGraf]>Cal.segment){s+="cursor: "+taulaV.varstaulaV[idxGraf]+": "+">l"+Func.rB.getString("i_")+"mit ("+Cal.dMatriuVar[idxGraf][8]+")"+splitPan.FIL;}
                    else if(cursor[idxGraf]<0){s+="cursor: "+taulaV.varstaulaV[idxGraf]+": "+"<l"+Func.rB.getString("i_")+"mit ("+Cal.dMatriuVar[idxGraf][7]+")"+splitPan.FIL;}
                }
            }
            s+="funci"+Func.rB.getString("o_")+": "+funGraf[captFun]+";   (min.graf.: "+funGraf[grafic.puntminim]+"; min.abs.: "+resultatMin+")"+splitPan.FIL;
            s1="";
            if(!unaVarBol){
                for(int j=0;j<Cal.longi;j++){
                    if(variableFixaBol[j])s1+=taulaV.varstaulaV[j]+"(fixa): "+varAct[j]+", ";
                    else{
                        if(cursor[j]<0)s1+=taulaV.varstaulaV[j]+": "+Cal.dMatriuVar[j][7]+";  ";
                        else {
                            varAct[j]=Cal.posic(j,cursor[j]);
                            s1+=taulaV.varstaulaV[j]+": "+varAct[j]+", ";
                        }
                    }
                }
                if(s1.length()>0){s+="variables:  "+s1+splitPan.FIL;}
            }
            else{
                cursor[idxGraf]=cursorCentreGrafic[idxGraf]+(long)(k*incCur[indexPunts][indexAmp]);
                if(cursor[idxGraf]>Cal.segment){s+="variables:  "+taulaV.varstaulaV[idxGraf]+": "+Cal.dMatriuVar[idxGraf][8]+", "+splitPan.FIL;}
                else if(cursor[idxGraf]<0){s+="variables:  "+taulaV.varstaulaV[idxGraf]+": "+Cal.dMatriuVar[idxGraf][7]+", "+splitPan.FIL;}
                else {
                    if(variableFixaBol[idxGraf]){
                        cursorFixa[idxGraf]=cursor[idxGraf];
                        variableFixa[idxGraf]=Cal.posic(idxGraf,cursor[idxGraf]);
                        pondIncrVar[idxGraf]=0;
                        cursorMinim[idxGraf]=cursor[idxGraf];
                        cursorMinimGraf[idxGraf]=cursor[idxGraf];
                    }
                    varAct[idxGraf]=Cal.posic(idxGraf,cursor[idxGraf]);
                    if(taulaC.abcisaFunGrafBol[0]){
                        if(!taulaC.abcisaFunGrafBol[1])s+=taulaC.textFilera[taulaC.grafA]+" "+resultatAbcisa(varAct[idxGraf])+splitPan.FIL;
                        else s+=taulaC.textFilera[taulaC.grafA]+" "+resultatFuncAbcis[captFun]+splitPan.FIL;
                    }
                    s+="variables:  "+taulaV.varstaulaV[idxGraf]+": "+varAct[idxGraf]+", "+splitPan.FIL;
                }
            }
            if(derBol){//if(l1% 2 != 0)s=splitPan.FIL+"punt: "+(int)l1/2+"-"+(int)(l1+1)/2+splitPan.FIL;
                if(contadorDerivada>1){
                    s=splitPan.FIL+"punt "+Func.increm+"("+contadorDerivada+")"+": "+captDer+";   punt: "+captFun+splitPan.FIL;
                    s+=Func.increm+"("+contadorDerivada+")"+fnc+"/"+Func.increm+s2+"^"+contadorDerivada+": "+derivada[captDer][1]+splitPan.FIL;
                }
                else {
                    s=splitPan.FIL+"punt "+Func.increm+": "+captDer+";   punt: "+captFun+splitPan.FIL;
                    s+=Func.increm+fnc+"/"+Func.increm+s2+": "+derivada[captDer][1]+splitPan.FIL;
                }
            }
            if (e.getButton() == MouseEvent.BUTTON3) {mou_mes_menys((float)k/(float)mitatPuntsGraf[indexPunts],false);}
            append(0,s);
            paraCalcBol=false;
            while(paraCalcB){try{Thread.sleep(50L);}catch( InterruptedException e1) {}}
            capturaInfoBol=true;
        }
        catch (Exception ex){}
    }
    public static void posGRegr(MouseEvent e){
        int l=grafReg.puntsGraf.size();
        if (l<1)return;
        int[][] marges=new int[l][4];
        int x0=e.getX();int y0=e.getY();
        for (int i = 0; i < l; i++) {
            marges[i][0]=i;
            marges[i][2]=Math.abs(x0-grafReg.puntsGraf.get(i).x);
            marges[i][3]=Math.abs(y0-grafReg.puntsGraf.get(i).y);
            marges[i][1]=(int)(Math.pow(marges[i][2],2.0)+Math.pow(marges[i][3],2.0));
        }
        int[] k=new int[4];
        for(int i=0;i<l;i++)for(int j=i;j<l;j++)if (marges[i][1]>marges[j][1]){k=marges[i];marges[i]=marges[j];marges[j]=k;}
        int i=marges[0][0];
        int j=l/2-1;
        if(marges[0][2]<12&&marges[0][3]<12){
        	if(i>j)i=i-j-1;//els punts del grafic contenen la primera mitat fnGrafP[2] color blau, la segona funGrafP[1] color magenta
        	int m=i+1;
        	String s="par"+Func.rB.getString("a")+"metre associat:"+splitPan.FIL;
        	s+="filera del par"+Func.rB.getString("a")+"metre: "+ m+splitPan.FIL;
        	s+="abscissa: "+funGrafP[0][i]+splitPan.FIL;
        	s+="ordenada 1: "+funGrafP[1][i]+" (magenta)"+splitPan.FIL;
        	s+="ordenada 2: "+funGrafP[2][i]+" (blau)"+splitPan.FIL;//inclou les constans de formacio
        	append(0,s);
        }
    } 
    void ini(){
        capturaC=new JButton("capt.");capturaC.setMargin(new Insets(0,0,0,0));
        capturaC.setEnabled(false);
        calcul_G=new JButton("calc.");calcul_G.setMargin(new Insets(0,0,0,0));
        JPanel capSup=new JPanel(new GridLayout(1, 2));
        capSup.add(capturaC);capSup.add(calcul_G);
        pan1.add(capSup);
        deriv=new JButton(Func.rB.getString("derivada"));deriv.setMargin(new Insets(0,0,0,0));
        func=new JButton(fnc);func.setMargin(new Insets(0,0,0,0));
        JPanel derivadaSup=new JPanel(new GridLayout(1, 2));
        derivadaSup.add(deriv);derivadaSup.add(func);
        pan1.add( derivadaSup);
        punts_text=new JLabel("punts");
        pan1.add(punts_text);
        punts_mes=new JButton("+");punts_mes.setMargin(new Insets(0,0,0,0));
        punts_menys=new JButton("-");punts_menys.setMargin(new Insets(0,0,0,0));
        JPanel panPuntsSup=new JPanel(new GridLayout(1, 2));
        panPuntsSup.add(punts_mes);panPuntsSup.add(punts_menys);
        pan1.add(panPuntsSup);
        amp_text=new JLabel("ampl.");
        amp_minim=new JButton("centre");amp_minim.setMargin(new Insets(0,0,0,0));
        JPanel panGrafSup=new JPanel(new GridLayout(1, 2));
        panGrafSup.add(amp_text);panGrafSup.add(amp_minim);
        pan1.add(panGrafSup);
        amp_mes=new JButton("+");amp_mes.setMargin(new Insets(0,0,0,0));
        amp_menys=new JButton("-");amp_menys.setMargin(new Insets(0,0,0,0));
        JPanel panGrafSup1=new JPanel(new GridLayout(1, 2));
        panGrafSup1.add(amp_mes);panGrafSup1.add(amp_menys);
        pan1.add(panGrafSup1);
        mou_text=new JLabel("mou");
        pan1.add(mou_text);
        veu_esq=new JButton("<");veu_esq.setMargin(new Insets(0,0,0,0));
        veu_dre=new JButton(">");veu_dre.setMargin(new Insets(0,0,0,0));
        JPanel mouGrafSup1=new JPanel(new GridLayout(1, 2));
        mouGrafSup1.add(veu_esq);mouGrafSup1.add(veu_dre);
        pan1.add(mouGrafSup1);
        var_but=new JButton("vars");var_but.setMargin(new Insets(0,0,0,0));
        JPanel mesvarSup=new JPanel(new GridLayout(1, 2));
        JPanel mesvarSup1=new JPanel(new GridLayout(1, 4));
        JPanel mesvarSup2=new JPanel(new GridLayout(1, 4));
        mesvarSup.add(var_but);
        sim[0]=new JLabel("l");sim[1]=new JLabel("s");sim[2]=new JLabel("x");sim[3]=new JLabel("p");
        for(int i=0;i<2;i++){
            mesvarSup1.add(sim[i]);
            sim[i].setForeground(Color.LIGHT_GRAY);
            sim[i].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            sim[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        }
        for(int i=2;i<4;i++){
            sim[i].setVisible(false);
            sim[i].setForeground(Color.LIGHT_GRAY);
            mesvarSup1.add(sim[i]);
            sim[i].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            sim[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        }
        mesvarSup.add(mesvarSup1);
        pan1.add(mesvarSup);
        var_mes=new JLabel("+");var_menys=new JLabel("-");
        fixaV=new JLabel("free");alliberar=new JLabel("reset");
        var_mes.setForeground(Color.BLACK);
        var_mes.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        var_mes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        var_menys.setForeground(Color.BLACK);
        var_menys.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        var_menys.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fixaV.setForeground(Color.LIGHT_GRAY);
        fixaV.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        fixaV.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        alliberar.setForeground(Color.LIGHT_GRAY);
        alliberar.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        alliberar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mesvarSup2.add(var_mes);
        mesvarSup2.add(var_menys);
        mesvarSup2.add(fixaV);
        mesvarSup2.add(alliberar);
        pan1.add(mesvarSup2);
        ttt(true);
        sim[0].addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                splitPan.tecl.cercaT();
                if(paraCalcB)return;paraCalcBol=true;paraCalcB=true;curs.Threadenable();
                if(taulaC.ordenadaGraf.equals("l")){
                    taulaC.ordenadaGraf="n";
                    sim[0].setBorder(BorderFactory.createLineBorder(Color.lightGray));
                    sim[0].setForeground(Color.lightGray);
                }
                else{taulaC.ordenadaGraf="l";Colors(0);}
                exeGraf(true);
                paraCalcBol=false;
            }
            public void mousePressed(MouseEvent e) {}public void mouseReleased(MouseEvent e) {}public void mouseEntered(MouseEvent e) {}public void mouseExited(MouseEvent e) {}
        });
        sim[1].addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if(paraCalcB)return;paraCalcBol=true;paraCalcB=true;curs.Threadenable();
                if(taulaC.ordenadaGraf.equals("s")){
                    taulaC.ordenadaGraf="n";
                    sim[1].setBorder(BorderFactory.createLineBorder(Color.lightGray));
                    sim[1].setForeground(Color.lightGray);
                }
                else{taulaC.ordenadaGraf="s";Colors(1);}
                exeGraf(true);
                paraCalcBol=false;
            }
            public void mousePressed(MouseEvent e) {}public void mouseReleased(MouseEvent e) {}public void mouseEntered(MouseEvent e) {}public void mouseExited(MouseEvent e) {}
        });
        sim[2].addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if(paraCalcB)return;paraCalcBol=true;paraCalcB=true;curs.Threadenable();
                if(taulaC.abcisaFunGrafBol[0]){
                    if(taulaC.abcisaGraf.equals(fnc)){
                        taulaC.abcisaGraf="n";
                        sim[2].setBorder(BorderFactory.createLineBorder(Color.lightGray));
                        sim[2].setForeground(Color.lightGray);
                    }
                    else{taulaC.abcisaGraf=fnc;Colors1(2);}
                }
                else{
                    if(taulaC.abcisaGraf.equals("x")){
                        taulaC.abcisaGraf="n";
                        sim[2].setBorder(BorderFactory.createLineBorder(Color.lightGray));
                        sim[2].setForeground(Color.lightGray);
                    }
                    else{taulaC.abcisaGraf="x";Colors1(2);}
                }
                calculDiagonalGrafic(true);//si false conserva els punt de funGraf que no canvien
                paraCalcBol=false;
            }
            public void mousePressed(MouseEvent e) {}public void mouseReleased(MouseEvent e) {}public void mouseEntered(MouseEvent e) {}public void mouseExited(MouseEvent e) {}
        });
        sim[3].addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if(paraCalcB)return;paraCalcBol=true;paraCalcB=true;curs.Threadenable();
                if(taulaC.abcisaGraf.equals("p")){
                    taulaC.abcisaGraf="n";
                    sim[3].setBorder(BorderFactory.createLineBorder(Color.lightGray));
                    sim[3].setForeground(Color.lightGray);
                }
                else{
                    taulaC.abcisaGraf="p";
                    Colors1(3);
                }
                calculDiagonalGrafic(true);//si false conserva els punt de funGraf que no canvien
                paraCalcBol=false;
            }
            public void mousePressed(MouseEvent e) {}public void mouseReleased(MouseEvent e) {}public void mouseEntered(MouseEvent e) {}public void mouseExited(MouseEvent e) {}
        });
        punts_mes.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){
            if(paraCalcB)return;paraCalcBol=true;paraCalcB=true;curs.Threadenable();
            indexPunts+=1;
            if(indexPunts>10){indexPunts=10;paraCalcBol=false;return;}
            if(indexAmp>=limCur[indexPunts]){
                indexAmp-=1;
                calculDiagonalGrafic(true);
            }
            else{
                conservarPuntsmes();
                calculDiagonalGrafic(false);
            }
            punts_text.setText("punts: "+puntsGraf[indexPunts]);
            amp_text.setText(indexAmp+" < "+limCur[indexPunts]);
            paraCalcBol=false;
        }});
        punts_menys.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){
            if(paraCalcB)return;paraCalcBol=true;paraCalcB=true;curs.Threadenable();
            indexPunts-=1;
            if(indexPunts<0)indexPunts=0;
            punts_text.setText("punts: "+puntsGraf[indexPunts]);
            amp_text.setText(indexAmp+" < "+limCur[indexPunts]);
            if(puntsGraf[indexPunts]==funGraf.length){paraCalcBol=false;return;}
            conservarPuntsmenys();
            calculDiagonalGrafic(false);
            paraCalcBol=false;
        }});
        fixaV.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if(paraCalcB)return;
                paraCalcBol=true;paraCalcB=true;curs.Threadenable();
                    int cont=0;//nombre de variables fixades
                    for(int j=0;j<Cal.longi;j++)if(variableFixaBol[j])cont++;
                    int cont1=Cal.longi-cont;
                    if(variableFixaBol[idxGraf]){//la variable esta fixada
                        variableFixaBol[idxGraf]=false;
                        fixaV.setText("free");
                        fixaV.setBorder(BorderFactory.createLineBorder(Color.lightGray));
                        fixaV.setForeground(Color.lightGray);
                        if(cont==1){//si no hi ha variables
                            alliberar.setBorder(BorderFactory.createLineBorder(Color.lightGray));
                            alliberar.setForeground(Color.lightGray);
                        }
                    }
                    else{//la variable no es fixa
                        if(cont1<2){
                            append(0,"nom"+Func.rB.getString("e_")+"s hi ha una variable lliura no te sentit fixar-la"+splitPan.FIL);
                            paraCalcBol=false;
                            return;
                        }
                        variableFixaBol[idxGraf]=true;
                        cursor[idxGraf]=cursorMinim[idxGraf];
                        variableFixa[idxGraf]=Cal.posic(idxGraf,cursorMinim[idxGraf]);
                        cursorFixa[idxGraf]=cursor[idxGraf];
                        pondIncrVar[idxGraf]=0;
                        cursorMinimGraf[idxGraf]=cursor[idxGraf];
                        if(Func.ampliarInfo)append(0,"la variable "+taulaV.varstaulaV[idxGraf]+", es fixa en el minim (fent click al gr"+Func.rB.getString("a")+"fic superiorior es fixara en el valor del punt seleccionat)"+splitPan.FIL);
                        fixaV.setText("fix");
                        fixaV.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        fixaV.setForeground(Color.BLACK);
                        alliberar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        alliberar.setForeground(Color.BLACK);
                    }
                calculDiagonalGrafic(true);//si false conserva els punt de funGraf que no canvien
                paraCalcBol=false;
            }
            public void mousePressed(MouseEvent e) {}public void mouseReleased(MouseEvent e) {}public void mouseEntered(MouseEvent e) {}public void mouseExited(MouseEvent e) {}
        });
        alliberar.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if(paraCalcB)return;paraCalcBol=true;paraCalcB=true;curs.Threadenable();
                allibera();
                paraCalcBol=false;
            }
            public void mousePressed(MouseEvent e) {}public void mouseReleased(MouseEvent e) {}public void mouseEntered(MouseEvent e) {}public void mouseExited(MouseEvent e) {}
        });
        amp_mes.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){ampmes(1);}});
        amp_menys.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){ampmenys(1);}});
        amp_minim.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){
            if(paraCalcB)return;paraCalcBol=true;paraCalcB=true;curs.Threadenable();
            if(amp_minim.getText().equals("centre")){ampCentreMinim=true;amp_minim.setText("min abs");}
            else if(amp_minim.getText().equals("min abs")){ampCentreMinim=false;amp_minim.setText("centre");}
            else {ampCentreMinim=false;amp_minim.setText("centre");}
            paraCalcBol=false;
        }});
        calcul_G.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){
            if(paraCalcB)return;paraCalcBol=true;paraCalcB=true;curs.Threadenable();
            if(capGBol)pendent();
            paraCalcBol=false;
        }});
        capturaC.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){
            if(paraCalcB)return;paraCalcBol=true;paraCalcB=true;curs.Threadenable();
            Func.capturaManualBol=true;
            captura();
            paraCalcBol=false;
            if(!paraCalcBol)Func.cap.setEnabled(true);
        }});
        var_but.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){
            if(paraCalcB)return;paraCalcBol=true;paraCalcB=true;curs.Threadenable();
            if(taulaV.varstaulaV.length==1)Cur.unaVarBol=true;
            else unaVarBol=!unaVarBol;
            if(unaVarBol){//Cur.funcOrdenActiva=false;//quan s'activa el grafic de una variable cal reiniciar funcio ordenada
                for(int i=2;i<4;i++)sim[i].setVisible(true);
                if(taulaC.abcisaFunGrafBol[0])sim[2].setText(fnc);
                else sim[2].setText("x");
                if(taulaC.abcisaGraf.equals("x")||taulaC.abcisaGraf.equals(fnc))Colors1(2);
                else if(taulaC.abcisaGraf.equals("p")) Colors1(3);
                else {
                    sim[2].setBorder(BorderFactory.createLineBorder(Color.lightGray));
                    sim[2].setForeground(Color.lightGray);
                    sim[3].setBorder(BorderFactory.createLineBorder(Color.lightGray));
                    sim[3].setForeground(Color.lightGray);
                }
                if(taulaC.ordenadaGraf.equals("l"))Colors(0);
                else if(taulaC.ordenadaGraf.equals("s"))Colors(1);
                else {
                    sim[0].setBorder(BorderFactory.createLineBorder(Color.lightGray));
                    sim[0].setForeground(Color.lightGray);
                    sim[1].setBorder(BorderFactory.createLineBorder(Color.lightGray));
                    sim[1].setForeground(Color.lightGray);
                }
                if(Cal.longi>1){
                    fixaV.setVisible(true);
                    var_mes.setVisible(true);
                    var_menys.setVisible(true);
                    alliberar.setVisible(true);
                    var_but.setText("var: "+taulaV.varstaulaV[idxGraf]);
                    Colors2(idxGraf);
                }
            }
            else iniciaCurVars();
            calculDiagonalGrafic(true);//si false conserva els punt de funGraf que no canvien
            paraCalcBol=false;
        }});
        var_mes.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if(paraCalcB)return;paraCalcBol=true;paraCalcB=true;curs.Threadenable();
                idxGraf++;
                if(idxGraf>=Cal.longi)idxGraf=0;
                var_but.setText("var: "+taulaV.varstaulaV[idxGraf]);
                Colors2(idxGraf);
                trueFalse();
                calculDiagonalGrafic(true);
                paraCalcBol=false;
            }
            public void mousePressed(MouseEvent e) {}public void mouseReleased(MouseEvent e) {}public void mouseEntered(MouseEvent e) {}public void mouseExited(MouseEvent e) {}
        });
        var_menys.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if(paraCalcB)return;paraCalcBol=true;paraCalcB=true;curs.Threadenable();
                idxGraf--;
                if(idxGraf<0)idxGraf=Cal.longi-1;
                var_but.setText("var: "+taulaV.varstaulaV[idxGraf]);
                Colors2(idxGraf);
                trueFalse();
                calculDiagonalGrafic(true);
                paraCalcBol=false;
            }
            public void mousePressed(MouseEvent e) {}public void mouseReleased(MouseEvent e) {}public void mouseEntered(MouseEvent e) {}public void mouseExited(MouseEvent e) {}
        });
        veu_esq.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt){}public void mouseExited(MouseEvent evt){}public void mousePressed(MouseEvent evt){
                if(paraCalcB)return;paraCalcBol=true;paraCalcB=true;curs.Threadenable();
                curs.veuE();
            }
            public void mouseReleased(MouseEvent evt){
                veuBol=false;
                while(curs.veu.isAlive()){
                    try{Thread.sleep(100L);} 
                    catch( InterruptedException e) {}
                }
                paraCalcBol=false;
            }
        });
        veu_dre.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt){}public void mouseExited(MouseEvent evt){}public void mousePressed(MouseEvent evt){
                if(paraCalcB)return;paraCalcBol=true;paraCalcB=true;curs.Threadenable();
                curs.veuD();
            }
            public void mouseReleased(MouseEvent evt){
                veuBol=false;
                while(curs.veu.isAlive()){
                    try{Thread.sleep(100L);} 
                    catch( InterruptedException e) {}
                }
                paraCalcBol=false;
            }
        });
        deriv.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){
            if(paraCalcB)return;paraCalcBol=true;paraCalcB=true;curs.Threadenable();
            exeGraf(false);
            deriv.setText(Func.rB.getString("derivada")+"("+contadorDerivada+")");
            paraCalcBol=false;
        }});
        func.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){
            if(paraCalcB)return;paraCalcBol=true;paraCalcB=true;curs.Threadenable();
            if(contadorDerivada>0){
                NaNBol=new boolean[funGraf.length];
                for(int i=0;i<funGraf.length;i++){if(!isFinite(funGraf[i]))NaNBol[i]=true;}
                exeGraf(true);
            }
            anulaDerivada();
            paraCalcBol=false;
        }});
    }
    public static void font(){
        Font fo = new Font(font.tipus, font.estil, font.tamany);
        capturaC.setFont(fo);calcul_G.setFont(fo);deriv.setFont(fo);func.setFont(fo);punts_text.setFont(fo);
        punts_mes.setFont(fo);punts_menys.setFont(fo);amp_text.setFont(fo);amp_minim.setFont(fo);
        amp_mes.setFont(fo);amp_menys.setFont(fo);mou_text.setFont(fo);veu_esq.setFont(fo);
        veu_dre.setFont(fo);var_but.setFont(fo);var_mes.setFont(fo);var_menys.setFont(fo);
        fixaV.setFont(fo);alliberar.setFont(fo);
        for(int i=0;i<4;i++){sim[i].setFont(fo);}
    }
    public static void iniciaCurVars(){
        sim[2].setVisible(false);
        sim[3].setVisible(false);
        fixaV.setVisible(false);
        alliberar.setVisible(false);
        var_mes.setVisible(false);
        var_menys.setVisible(false);
        var_but.setText("vars");
        idxGraf=idxP;
    }
    private static void Colors(int j){
        if(j==0) {
            sim[0].setForeground(Color.BLACK);sim[0].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            sim[1].setForeground(Color.LIGHT_GRAY);sim[1].setBorder(BorderFactory.createLineBorder(Color.lightGray));
        }
        else{
            sim[1].setForeground(Color.BLACK);sim[1].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            sim[0].setForeground(Color.LIGHT_GRAY);sim[0].setBorder(BorderFactory.createLineBorder(Color.lightGray));
        } 
    }
    private static void Colors1(int j){
        if(j==2) {
            sim[2].setForeground(Color.BLACK);sim[2].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            sim[3].setForeground(Color.LIGHT_GRAY);sim[3].setBorder(BorderFactory.createLineBorder(Color.lightGray));
        }
        else{
            sim[3].setForeground(Color.BLACK);sim[3].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            sim[2].setForeground(Color.LIGHT_GRAY);sim[2].setBorder(BorderFactory.createLineBorder(Color.lightGray));
        } 
    }
    public static void Colors2(int i){
        if(variableFixaBol[i]){
            fixaV.setText("fix");
            fixaV.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            fixaV.setForeground(Color.BLACK);
        }
        else{
            fixaV.setText("free");
            fixaV.setBorder(BorderFactory.createLineBorder(Color.lightGray));
            fixaV.setForeground(Color.lightGray);
        }
    }
    private static void allibera(){
        variableFixaBol=new boolean[Cal.longi];
        alliberar.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        alliberar.setForeground(Color.lightGray);
        fixaV.setText("free");
        fixaV.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        fixaV.setForeground(Color.lightGray);
    }
  public static void ttt(boolean b){
      if(b){
        sim[1].setToolTipText("ordenada: escala simplificada o escala normal");
        sim[2].setToolTipText("ordenada: escala logar"+Func.rB.getString("i_")+"tmica o escala normal");
        sim[2].setToolTipText("abscissa: escala variable(funci"+Func.rB.getString("o_")+") o escala normal");
        sim[3].setToolTipText("abscissa: escala polar o escala normal");
        fixaV.setToolTipText("despr"+Func.rB.getString("e_")+"s de fer click a un punt del gr"+Func.rB.getString("a")+"fic superior fixa l'abscissa de la variable en aques punt (s'aplica nome"+Func.rB.getString("e_")+"s en aquest jPanel)");
        alliberar.setToolTipText("allibera totes les variables fixades (tamb"+Func.rB.getString("e_")+" s'executa quan hi ha captura o nova aproximaci"+Func.rB.getString("o_")+" de m"+Func.rB.getString("i_")+"nim");
        var_mes.setToolTipText("motra el gr"+Func.rB.getString("a")+"fic de la variable seg"+Func.rB.getString("u")+"ent (la resta de variables es mant"+Func.rB.getString("e_")+" constant en el m"+Func.rB.getString("i_")+"nim, si no esta fixada)");
        var_menys.setToolTipText("motra el gr"+Func.rB.getString("a")+"fic de la variable anterior (la resta de variables es mant"+Func.rB.getString("e_")+" constant en el m"+Func.rB.getString("i_")+"nim, si no esta fixada)");
        capturaC.setToolTipText("<html>quan hi ha activa una cerca de m"+Func.rB.getString("i_")+"nim, captura els resultats en curs<br>i anula temporalment si es activa la captura autom"+Func.rB.getString("a")+"tica");
        calcul_G.setToolTipText("<html>cerca un nou m"+Func.rB.getString("i_")+"nim<br>si s'ha seleccionat un punt (fent click al gr"+Func.rB.getString("a")+"fic) aquest es el punt de partida de la cerca");
        deriv.setToolTipText("presenta sequencialment aproximacions de les derivades d'ordre 1, 2,... ");
        punts_text.setToolTipText("augmenta o disninueix el nombre de punts del gr"+Func.rB.getString("a")+"fic");
        amp_text.setToolTipText("augmenta o disninueix la ampliaci"+Func.rB.getString("o_")+" del gr"+Func.rB.getString("a")+"fic");
        mou_text.setToolTipText("<html>mou el gr"+Func.rB.getString("a")+"fic cap la dreta o esquerra<br>per invertir el sentit a taula condicions (altres condicions) escriura la lletra s<br>chick boto dreta: centra el gr"+Func.rB.getString("a")+"fic en el punt selecionat");
        amp_minim.setToolTipText("la ampliaci"+Func.rB.getString("o_")+" es fa centrada en punt mitj"+Func.rB.getString("a")+" del gr"+Func.rB.getString("a")+"fic o en el m"+Func.rB.getString("i_")+"nim absolut (el indicat al button)");
        var_but.setToolTipText("<html>permet seleccionar la abscissa sobre la que es presenta el gr"+Func.rB.getString("a")+"fic<br> si "+taulaC.matriu[taulaC.grafA][0]+" te contingut, nom"+Func.rB.getString("e_")+"s hi ha un m"+Func.rB.getString("a")+"xim de dues variables actives la resta son fixes");
      }
      else{
        sim[0].setToolTipText(null);
        sim[1].setToolTipText(null);
        sim[2].setToolTipText(null);
        sim[3].setToolTipText(null);
        capturaC.setToolTipText(null);
        calcul_G.setToolTipText(null);
        deriv.setToolTipText(null);
        punts_text.setToolTipText(null);
        amp_text.setToolTipText(null);
        mou_text.setToolTipText(null);
        amp_minim.setToolTipText(null);
        var_but.setToolTipText(null);
      }
  }
  public void Threadenable(){//fa inaccesible els button mentre paraCalcBol= true
       temps = new Thread(new Th1());
       temps.setPriority(Thread.MAX_PRIORITY);temps.start();
    }
    class Th1 implements Runnable{public void run()  {
        trueFalse();
        while(paraCalcBol){
        try{Thread.sleep(100L);} 
        catch( InterruptedException e) {}
        }
        falseTrue();
        paraCalcB=false;
    }}
    public void veuE(){
       veu = new Thread(new Th2());
       veu.setPriority(Thread.MAX_PRIORITY);veu.start();
    }
    class Th2 implements Runnable{public void run()  {
        veuBol=true;
        double l=indexPunts+1;
        if(indexPunts>3)l=4;
        while(veuBol){
            if(!Func.mouInvertBol) mou_mes_menys(-l,true);
            else mou_mes_menys(l,true);
            try{Thread.sleep(50L);} 
        catch( InterruptedException e) {}
        }
    }}
    public void veuD(){
       veu = new Thread(new Th3());
       veu.setPriority(Thread.MAX_PRIORITY);veu.start();
    }
    class Th3 implements Runnable{public void run()  {
        veuBol=true;
        double l=indexPunts+1;
        if(indexPunts>3)l=4;
        while(veuBol){
            if(!Func.mouInvertBol) mou_mes_menys(l,true);
            else mou_mes_menys(-l,true);
            try{Thread.sleep(50L);} 
            catch( InterruptedException e) {}
        }
    }}
  public static void trueFalse(){
    capturaInfoBol=false;
    pan0.setEnabled(false);
    capturaC.setEnabled(false);
    punts_mes.setVisible(false);
    punts_menys.setVisible(false);
    amp_mes.setVisible(false);
    amp_menys.setVisible(false);
    veu_esq.setVisible(false);
    veu_dre.setVisible(false);
    calcul_G.setEnabled(false);
    deriv.setEnabled(false);
    func.setEnabled(false);
    var_but.setEnabled(false);
    var_mes.setEnabled(false);
    var_menys.setEnabled(false);
    amp_minim.setEnabled(false);
    sim[0].setVisible(false);
    sim[1].setVisible(false);
  }
  public static void falseTrue(){
    punts_mes.setVisible(true);
    punts_menys.setVisible(true);
    amp_mes.setVisible(true);
    amp_menys.setVisible(true);
    veu_esq.setVisible(true);
    veu_dre.setVisible(true);
    if(capGBol)capturaC.setEnabled(true);
    else capturaC.setEnabled(false);
    if(capBol)calcul_G.setEnabled(true);
    else calcul_G.setEnabled(false);
    deriv.setEnabled(true);
    func.setEnabled(true);
    var_but.setEnabled(true);
    var_mes.setEnabled(true);
    var_menys.setEnabled(true);
    pan0.setEnabled(true);
    amp_minim.setEnabled(true);
    sim[0].setVisible(true);
    sim[1].setVisible(true);
  }
  private static void anulaDerivada(){
      contadorDerivada=0;
      derivada=new double[0][0];
      deriv.setText(Func.rB.getString("derivada"));
  }
  public static String presentaRes(long[] cm,boolean esCalc){//esCal=thue; be de calculnumeric
        String s="";
        String s2="(round) variables:";
        if(Cal.longi==1){
            idxP=0;
            pondIncrVar[0]=1;
            if(Func.ampliarInfo){return(splitPan.FIL+"variable: "+taulaV.varstaulaV[0]+" = "+Cal.posic(0, cm[0])+splitPan.FIL+s2+" "+taulaV.varstaulaV[0]+" = "+splitPan.rodo(Cal.posic(0,cm[0])));}
            else return(splitPan.FIL+s2+" "+taulaV.varstaulaV[0]+" = "+splitPan.rodo(Cal.posic(0,cm[0])));
        }
        else{
            if(!esCalc&&Func.ampliarInfo){
                s=splitPan.FIL+"abscissa principal: "+taulaV.varstaulaV[idxP]+splitPan.FIL;
                s+="cursors: "+splitPan.FIL;
                for(int i=0;i<Cal.longi;i++){ 
                    double d=(1000*(double)cm[i]);
                    d/=(double)Cal.segment;
                    s+=taulaV.varstaulaV[i]+": "+(int)d+";  ";
                }
                s+=splitPan.FIL+"increments ponderats:"+splitPan.FIL;
                if(Cal.idxVarGen.length>0)for(int i=0;i<Cal.idxVarGen.length;i++){ 
                    int j=Cal.idxVarGen[i];
                    s+=taulaV.varstaulaV[j]+": "+splitPan.rodo(Cal.pondIncrVar[j])+";   ";
                }
                if(taulaP.hihaPar)for(int i=0;i<Cal.idxParVar.length;i++){ 
                    int j=Cal.idxParVar[i];
                    s+=taulaV.varstaulaV[j]+": "+splitPan.rodo(Cal.pondIncrVar[j])+";   ";
                }
                s+=splitPan.FIL;
            }
            boolean[] b=new boolean[taulaV.varstaulaV.length];
            if(Func.ampliarInfo){//es presenta tan a la esquerra com a la dreta
                s+="variables:"+splitPan.FIL;
                if(Cal.idxVarGen.length>0)for(int i=0;i<Cal.idxVarGen.length;i++){ 
                    int j=Cal.idxVarGen[i];
                    b[j]=true;
                    s+=taulaV.varstaulaV[j]+": "+Cal.posic(j,cm[j])+";   ";
                }
                if(taulaP.hihaPar) for(int i=0;i<Cal.idxParVar.length;i++){ 
                    int j=Cal.idxParVar[i];
                    b[j]=true;
                    s+=taulaV.varstaulaV[j]+": "+Cal.posic(j,cm[j])+";   ";
                }
                for(int i=0;i<b.length;i++)if(!b[i])s+=taulaV.varstaulaV[i]+": "+Cal.posic(i,cm[i])+";   ";
                s+="variables:"+splitPan.FIL;
            }
            s+=s2+splitPan.FIL;
            if(Cal.idxVarGen.length>0)for(int i=0;i<Cal.idxVarGen.length;i++){ 
                int j=Cal.idxVarGen[i];
                b[j]=true;
                s+=taulaV.varstaulaV[j]+": "+splitPan.rodo(Cal.posic(j,cm[j]))+";   ";
            }
            if(taulaP.hihaPar)for(int i=0;i<Cal.idxParVar.length;i++){ 
                int j=Cal.idxParVar[i];
                b[j]=true;
                s+=taulaV.varstaulaV[j]+": "+splitPan.rodo(Cal.posic(j,cm[j]))+";   ";
            }
            for(int i=0;i<b.length;i++)if(!b[i])s+=taulaV.varstaulaV[i]+": "+splitPan.rodo(Cal.posic(i,cm[i]))+";   ";
        }
        return s;
    }
private static void mou_mes_menys(double d,boolean b){
    long inc=incCur[indexPunts][indexAmp];
    long l=0;
    if(b){
        if(d>0){l=incCur[(int)d-1][indexAmp];}
        else if(d<0){l=-incCur[-(int)d-1][indexAmp];}
    }
    else {
        inc=incCur[indexPunts][indexAmp];
        int mpg=mitatPuntsGraf[indexPunts];
        l=(long)(inc*mpg*d);
    }
    int idx=idxP;
    if(unaVarBol)idx=idxGraf;
    long cg=cursorCentreGrafic[idx];//guarda el cursoCentreGrafic
    if(cursorCentreGrafic[idx]>=Cal.segment&&l>0){paraCalcBol=false;return;}
    cursorCentreGrafic[idx]+=l;
    if(cursorCentreGrafic[idx]>Cal.segment){
        cursorCentreGrafic[idx]=Cal.segment;
        mou_text.setText("> l"+Func.rB.getString("i_")+"mit ("+Cal.dMatriuVar[idx][8]+")");
    }
    else if(cursorCentreGrafic[idx]<0){
        cursorCentreGrafic[idx]=0;
        mou_text.setText("< l"+Func.rB.getString("i_")+"mit ("+Cal.dMatriuVar[idx][7]+")");
    }
    if(d!=0){conservarPuntsDespl(idx,cg,inc);calculDiagonalGrafic(false);}
    else calculDiagonalGrafic(true);
    mou_text.setText(posInterval(mitatPuntsGraf[indexPunts]));
}
private static void conservarPuntsDespl(int idx,long cg,long inc){
    int l=(int)((cursorCentreGrafic[idx]-cg)/inc);
    funGrafBol=new boolean[funGraf.length];
    if(l>0)for(int i=funGrafBol.length-1;i>funGrafBol.length-l-1;i--) funGrafBol[i]=true;
    else for(int i=0;i<-l;i++) funGrafBol[i]=true;
    if(l>0)for(int i=0;i<funGrafBol.length-l;i++) {funGraf[i]=funGraf[i+l];varActGraf[i]=varActGraf[i+l];}
    else for(int i=funGrafBol.length-1;i>=-l;i--) {funGraf[i]=funGraf[i+l];varActGraf[i]=varActGraf[i+l];}
 }
private static void conservarPuntsmes(){
    double[][]e=new double[5][funGraf.length];
    System.arraycopy(funGraf, 0, e[0], 0, e[0].length);
    System.arraycopy(varActGraf, 0, e[1], 0, e[1].length);
    funGrafBol=new boolean[puntsGraf[indexPunts]];
    funGraf=new double[funGrafBol.length];
    varActGraf=new double[funGraf.length];
    if(taulaC.abcisaFunGrafBol[1])resultatFuncAbcis=new double[funGraf.length];
    for(int i=1;i<funGraf.length-1;i+=2) funGrafBol[i]=true;
    for(int i=0;i<funGraf.length;i+=2) funGraf[i]=e[0][i/2];
    for(int i=0;i<funGraf.length;i+=2) varActGraf[i]=e[1][i/2];
}
private static void conservarPuntsmenys(){
    double[][]e=new double[5][funGraf.length];
    System.arraycopy(funGraf, 0, e[0], 0, e[0].length);
    System.arraycopy(varActGraf, 0, e[1], 0, e[1].length);
    funGrafBol=new boolean[puntsGraf[indexPunts]];
    funGraf=new double[puntsGraf[indexPunts]];
    varActGraf=new double[funGraf.length];
    if(taulaC.abcisaFunGrafBol[1])resultatFuncAbcis=new double[funGraf.length];
    for(int i=0;i<funGraf.length;i++)funGraf[i]=e[0][i*2];
    for(int i=0;i<funGraf.length;i++) varActGraf[i]=e[1][i*2];
}
private static String posInterval(int i){
    long j=2*incCur[indexPunts][indexAmp]*mitatPuntsGraf[indexPunts];
    long c=(long)(1+(cursorCentreGrafic[idxGraf]+i*incCur[indexPunts][indexAmp])/j);
    j=1+Cal.segment/j;
    return "pos.: "+c+" de ("+j+")";
}
private static void ampmes(int i){
    if(paraCalcB)return;
    paraCalcBol=true;paraCalcB=true;curs.Threadenable();
    while(indexAmp>=limCur[indexPunts])indexAmp--;
    indexAmp+=i;
    if(indexAmp>=limCur[indexPunts])indexAmp=limCur[indexPunts]-1;
    punts_text.setText(""+puntsGraf[indexPunts]);
    amp_text.setText(indexAmp+" < "+limCur[indexPunts]);
    if(ampCentreMinim)System.arraycopy(cursorMinimGraf,0,cursorCentreGrafic,0,Cal.longi);
    mou_mes_menys(0,true);
    if(!unaVarBol)amp_text.setText(indexAmp+"<"+limCur[indexPunts]+" ("+limSCalc+")");
    else amp_text.setText(indexAmp+"<"+limCur[indexPunts]+"  ("+limSupCalc[idxGraf]+")");
    paraCalcBol=false;
}
private static void ampmenys(int i){  
    if(paraCalcB)return;paraCalcBol=true;paraCalcB=true;curs.Threadenable();
    if(indexAmp<=0){indexAmp=0;paraCalcBol=false;return;}
    indexAmp-=i;
    if(indexAmp<0)indexAmp=0;
    if(ampCentreMinim)System.arraycopy(cursorMinimGraf,0,cursorCentreGrafic,0,Cal.longi);
    mou_mes_menys(0,true);
    if(!unaVarBol)amp_text.setText(indexAmp+" < "+limCur[indexPunts]+"  ("+limSCalc+")");
    else amp_text.setText(indexAmp+" < "+limCur[indexPunts]+"  ("+limSupCalc[idxGraf]+")");
    paraCalcBol=false;
}
private static void derivada(){
        double min=maxim,max=-maxim;
        if(contadorDerivada==0){
            suportDerivada=new double[grafic.punts.length][3];//grafic.punts es el valor de funD i funcio abcisa
            for(int i=0;i<grafic.punts.length;i++){
                suportDerivada[i][0]=grafic.punts[i][0];suportDerivada[i][1]=funGraf[i];suportDerivada[i][2]=varActGraf[i];
            }
        }
        else{
            suportDerivada=new double[derivada.length][3];
            for(int i=0;i<suportDerivada.length;i++){
                suportDerivada[i][0]=derivada[i][0];suportDerivada[i][1]=derivada[i][1];suportDerivada[i][2]=derivada[i][2];
            }
        }
        if(suportDerivada.length<2)return;
        derivada=new double[suportDerivada.length-2][4];
        for(int i=0;i<derivada.length;i++){
            derivada[i][0]=suportDerivada[i+1][0];
            derivada[i][1]=(suportDerivada[i+2][1]-suportDerivada[i][1])/(suportDerivada[i+2][2]-suportDerivada[i][2]);
            derivada[i][2]=suportDerivada[i+1][2];
        }
        contadorDerivada++;
        for(int i=0;i<derivada.length;i++)if(!NaNBol[i]){
            if(max<derivada[i][1]){max=derivada[i][1];}
            if(min>derivada[i][1]){min=derivada[i][1];}
        }
        double k=max-min;
        if(k==0)for(int i=0;i<derivada.length;i++)derivada[i][3]=0.0;
        else for(int i=0;i<derivada.length;i++)derivada[i][3]=grafic.minY+(derivada[i][1]-min)*(grafic.maxY-grafic.minY)/k;
        if(derivada.length<0)return;
        abcOrdD=new int[derivada.length][2];//utilitzat a grafic i a click info posicions dels punts en el grafic per despres al fer click a un punt del grafic  trobar el mes proper
     }
    private static void exeGraf(boolean  b){
        grafic.puntsAssoc[0]=-1;grafic.puntsAssoc[1]=-1;
        if(b)anulaDerivada();
        boolean bo=unaVarBol&&!taulaC.abcisaGraf.equals("n");//si nomes una varible es variable i el eix d'abcises no es el lineal de punts ordenats
        grafic.punts=new double[0][0];
        double[] d=new double[varActGraf.length];
        if(unaVarBol){//activat el grafic en que nomes una variable es variable
            if(taulaC.abcisaFunGrafBol[0]){
                if(!taulaC.abcisaFunGrafBol[1])for(int i=0;i<funGraf.length;i++)d[i]=resultatAbcisa(varActGraf[i]);
                else for(int i=0;i<funGraf.length;i++)d[i]=resultatFuncAbcis[i];
            }
            else System.arraycopy(varActGraf,0,d,0,d.length);
        }
        abcOrd=new int[funGraf.length][2];
        double[] funD=punts();//punts() nomes afexta a les ordinades
        grafic.maxY=-maxim;grafic.minY=maxim;
        grafic.maxX=funGraf.length-1;grafic.minX=0;
        if(bo){
            grafic.maxX=-maxim;
            grafic.minX=maxim;
            if(taulaC.abcisaGraf.equals("p"))for(int i=0;i<funD.length;i++) {
                d[i]=Math.cos(d[i])*funD[i];
                funD[i]=Math.sin(varActGraf[i])*funD[i];//d[i] s'ha modificat i cal utilitzar la matriu varActGraf
                if(!isFinite(funD[i]))NaNBol[i]=true;
            }
            for(int i=0;i<funD.length;i++)if(!isFinite(d[i]))NaNBol[i]=true;
        }
        for(int i=0;i<funGraf.length;i++)if(!NaNBol[i]){
                if(grafic.maxY<funD[i]){grafic.maxY=funD[i];}
                if(grafic.minY>funD[i]){grafic.minY=funD[i];}
        }
        grafic.punts=new double[funD.length][2];
        if(bo)for(int i=0;i<funD.length;i++){
            if(!NaNBol[i]){
                if(grafic.maxX<d[i]){grafic.maxX=d[i];}
                if(grafic.minX>d[i]){grafic.minX=d[i];}
            }
            grafic.punts[i][0]=d[i];
            grafic.punts[i][1]=funD[i];
        }
        else for(int i=0;i<funD.length;i++) {
            grafic.punts[i][0]=(double)i;
            grafic.punts[i][1]=funD[i];
        }
        if(!b) derivada();
    }
    private static double[] punts(){
    	double[] funD=new double[funGraf.length];
        System.arraycopy(funGraf,0,funD, 0, funGraf.length);
        if(taulaC.ordenadaGraf.equals("s")){funD=simplificat(funD);}
        else if(taulaC.ordenadaGraf.equals("l")){
            grafic.maxY=-maxim;
            grafic.minY=maxim;
            double[] funlog=new double[funD.length];
            boolean bneg=false;//cerca el minim i maxim dels logaritmes de menys la part negativa de lafuncio
            for(int i=0;i<funGraf.length;i++)if(!NaNBol[i]&&funD[i]<0){
                bneg=true;
                funlog[i]=Math.log10(-funD[i]);
                if(grafic.maxY<funlog[i])grafic.maxY=funlog[i];
                if(grafic.minY>funlog[i])grafic.minY=funlog[i];
            }//suma a  la part negativa del logarime de la funcio canviada de signe el anterior maxim de manera que el valor mes petit sigui el cero
            double maxmin=-maxim;
            for(int i=0;i<funGraf.length;i++)if(!NaNBol[i]&&funD[i]<0){
                funlog[i]=-funlog[i]+grafic.maxY;
                if(maxmin<funlog[i])maxmin=funlog[i];
            }//part positiva
            double maY=-maxim;
            double miY=maxim;
            boolean bpos=false;//calcula el maxim i el minim de la part positiva
            for(int i=0;i<funGraf.length;i++)if(!NaNBol[i]&&funD[i]>0){
                bpos=true;
                funlog[i]=Math.log10(funD[i]);
                if(maY<funlog[i])maY=funlog[i];
                if(miY>funlog[i])miY=funlog[i];
            }//calcula el nombre de punts finits que hi ha
            int con=0;
            for(int i=0;i<funGraf.length;i++)if(!NaNBol[i]){
                if(isFinite(funlog[i]))con++;
                else NaNBol[i]=true;
            }
            double seg=1;
            if(!bneg){grafic.maxY=0;grafic.minY=0;maxmin=0;}
            if(!bpos){maY=0;miY=0;}
            if(con>0)seg=(maY+grafic.maxY-miY-grafic.minY)/con;
            for(int i=0;i<funGraf.length;i++)if(!NaNBol[i]&&funD[i]>0)funlog[i]=funlog[i]-miY+maxmin+seg;
            if(miY!=0)seg*=grafic.minY/(grafic.minY+miY);//minim negatiu/minim positiu
            for(int i=0;i<funGraf.length;i++)if(!NaNBol[i]&&funD[i]==0)funlog[i]=maxmin+seg;
            for(int i=0;i<funGraf.length;i++)if(!NaNBol[i])funD[i]=funlog[i];
        }
        return funD;
    }
    private static double[] simplificat(double[] d){
    	int j=d.length;
    	double[] d1=new double[j];
        int[] i1=new int[j];
        for(int i=0;i<j;i++)i1[i]=i;
        int i2;
        for(int i=0;i<j-1;i++)for(int k=i+1;k<j;k++)if(d[i1[i]]>d[i1[k]]){
            i2=i1[i];i1[i]=i1[k];i1[k]=i2;
        }
        d1[i1[0]]=0;
    	for(int i=0;i<j-1;i++){
            int cont=1;
            if(!NaNBol[i1[i]]){
                while(i+cont<j&&NaNBol[i1[i+cont]]){d1[i1[i+cont]]=0;cont++;}
                if(i+cont<j)d1[i1[i+cont]]=d1[i1[i]]+1;
            }
    	}
    	return d1;
    }
     public static void testIncr(double min){
        for(int i=0;i<Cal.longi;i++) minIncrSignif[i]=(long)Math.pow(2,28);
        for(int i=0;i<Cal.longi;i++)varAct[i]=Cal.posic(i,cursorMinim[i]);
        if(Cal.indexsParV.length>0){resultatP=new double[Cal.idxParVar.length];resultat=Cal.resultatMatrP(true,dvo,dVO,sVO,varAct,resultatP,resultatM,cursor,cursorMinim);}//resultat=resultatMatrP(true);
        else {Cal.actualitzadvo(dvo,dVO,sVO,varAct,cursor);resultat=Cal.calcul(dvo);}//resultat=resultatdVO();}
        for(int i=0;i<Cal.longi;i++)Cal.testIncr(i,min,cursor,cursorMinim,varAct,minIncrSignif,resultat, resultatP,limSupCalc,dvo,dVO,sVO,resultatMcop);//testInc(i,min,true,false);
        limSCalc=0;for(int i=0;i<Cal.longi;i++) if(limSCalc<limSupCalc[i])limSCalc=limSupCalc[i];
    }
   public static double resultatAbcisa(double j){// nomes es requereix el indexs de dvoAbcis el valor de entrada es el valor de la variable seleccionada
        System.arraycopy(dVOAbcis, 0, dvoAbcis, 0, dvoAbcis.length);
        for(int i=0;i<indexsVar_Abcis.length;i++){
             if(sVOAbcis[indexsVar_Abcis[i][0]].startsWith("-"))dvoAbcis[indexsVar_Abcis[i][0]]=-j;
             else dvoAbcis[indexsVar_Abcis[i][0]]=j;
        }
        if(indexs_Abcis.length<1)return dvoAbcis[0];
        for(int i=0;i<indexs_Abcis.length;i++){
            dvoAbcis[indexs_Abcis[i][1]]=Cal.caseOpFun(indexs_Abcis[i][0],dvoAbcis[indexs_Abcis[i][1]],dvoAbcis[indexs_Abcis[i][2]]);
        }
        return dvoAbcis[indexs_Abcis[indexs_Abcis.length-1][1]];
    }
    public static double actualitzadvo_i_resultatAbcisa(){//varAct es conegut
        for(int i=0;i<indexsVar_Abcis.length;i++){
             if(sVOAbcis[indexsVar_Abcis[i][0]].startsWith("-"))dvoAbcis[indexsVar_Abcis[i][0]]=-varAct[indexsVar_Abcis[i][1]];
             else dvoAbcis[indexsVar_Abcis[i][0]]=varAct[indexsVar_Abcis[i][1]];
        }
        if(Cal.derivadaBol){//en principi totes les funcions parcials s'han calculat per la funcio principal
            System.arraycopy(varAct, 0, varCopia, 0, varAct.length);
            double[] d=supID.calculDerivada(varCopia,cursor);
            for(int i=0;i<indexsAbcisFP.length;i++){
                if(sVOAbcis[indexsAbcisFP[i][0]].startsWith("-"))dvoAbcis[indexsAbcisFP[i][0]]=-supID.resultatFil[indexsAbcisFP[i][1]];
                else dvoAbcis[indexsAbcisFP[i][0]]=supID.resultatFil[indexsAbcisFP[i][1]];
           }
       }
       if(Cal.integralBol){//si hi ha sumatoris a la funcio principal que contenen variables generals: substitueix totes les variables generals a totes les funcions integrals calcula les integrals i les adjunta a la funcio principal
            if(Cal.hihaFPIntegrBol[0][0])supID.FPIntegral();
            supID.substitueixVG(varAct);//substitueix totes les variables generals a totes les integrals(funcions i intervals) no a l funcio principal general (Cur.indexVGI)
            for(int i=0;i<taulaI.sumat.length;i++){//substitueix a la funcio principal el valor de tors els sumatoris amb variables generals actualitzats
                if(suportID.hihaVGioFPaI[i]){//si hi ha variable o funcio parcial amb variable
                    supID.bucle(i);
                }
            }
            for(int i=0;i<indexsAbcisSum.length;i++){//substitueix a la funcio principal el valor de tors els sumatoris amb variables generals actualitzats
                if(sVOAbcis[indexsAbcisSum[i][0]].startsWith("-"))dvoAbcis[indexsAbcisSum[i][0]]=-supID.integral[indexsAbcisSum[i][1]];
                else dvoAbcis[indexsAbcisSum[i][0]]=supID.integral[indexsAbcisSum[i][1]];
            }
        }
        if(indexs_Abcis.length<1)return dvoAbcis[0];
        for(int i=0;i<indexs_Abcis.length;i++){
            dvoAbcis[indexs_Abcis[i][1]]=Cal.caseOpFun(indexs_Abcis[i][0],dvoAbcis[indexs_Abcis[i][1]],dvoAbcis[indexs_Abcis[i][2]]);
        }
        return dvoAbcis[indexs_Abcis[indexs_Abcis.length-1][1]];
    }
   //els punts es calculen prenent com a centre el centre grafic
   //el punts calculats van referits al cursorCentreGrafic (punt central), i aquest referit al cursorMinimGraf (que nomes canvia quan el minim parcial (cursorMinim) es inferior al minim absolut(cursorMinimGraf)) 
   //inicialment el resultat es fixa a maxim i per tant sempre troba un punt minim i un nou resultat
   //el minim parcial es guarda cursorMinim que sera un minim parcial
   //el cursorCentreGrafic que es el punt central no es modifica
   //els punts tals que la variable directora surt dels limita es fan igual a NaN
   //cursorMinimGraf pot no esser el minim  absolut despres del calcul
   //cal executar resultatMin() per ajustar tots els parametres al minim
   //el valor de les variables en el minim no estan definides
   public static boolean calculDiagonalGrafic(boolean b){//si false conserva els punt de funGraf que no canvien 
       //if(Cal.derivadaBol)idxAmp[0]=indexAmp+indexPunts;
       try{
        resultat=maxim;
         boolean vp=false;
        if(unaVarBol){
            for(int j=0;j<Cal.longi;j++){varAct[j]=Cal.posic(j,cursorCentreGrafic[j]);}
            if(Cal.derivadaBol)System.arraycopy(cursorCentreGrafic, 0, cursor, 0, cursor.length);
            Cal.actualitzadVO(dvo,dVO,sVO,varAct,cursor,cursorMinim);//actualitzadVO();
            if(Cal.limitindexsVarGen[idxGraf][0]<6){vp=true;}//es una variable general
        }
        else idxGraf=idxP;
        if(b){
            funGraf=new double[puntsGraf[indexPunts]];
            varActGraf=new double[funGraf.length];
            if(taulaC.abcisaFunGrafBol[1])resultatFuncAbcis=new double[funGraf.length];
        }
        NaNBol=new boolean[funGraf.length];
        long inc=incCur[indexPunts][indexAmp];
        long cota=(long)(cursorCentreGrafic[idxGraf]-cursorMinimGraf[idxGraf]);
        int contI=mitatPuntsGraf[indexPunts];
        if(unaVarBol)for(int j=0;j<Cal.longi;j++){
            if(variableFixaBol[j])varAct[j]=variableFixa[j];
            else varAct[j]=Cal.posic(j,cursorCentreGrafic[j]);
        }
        for(int i=-contI;i<=contI;i++){
            int k=i+contI;
            cursor[idxGraf]=cursorMinimGraf[idxGraf]+(long)(cota+i*inc);
            if(cursor[idxGraf]<0||cursor[idxGraf]>Cal.segment)NaNBol[k]=true;
            if(b||funGrafBol[k]){
                if(!NaNBol[k]){
                    if(!unaVarBol){
                        for(int j=0;j<Cal.longi;j++){
                            if(variableFixaBol[j])varAct[j]=variableFixa[j];
                            else{
                                cursor[j]=cursorMinimGraf[j]+(long)((cota+i*inc)*pondIncrVar[j]);
                                if(cursor[j]<0)cursor[j]=0;else if(cursor[j]>Cal.segment)cursor[j]=Cal.segment;
                                varAct[j]=Cal.posic(j,cursor[j]);
                                if(j==idxP)varActGraf[k]=varAct[idxP];
                            }
                        }
                        Cal.actualitzadvo(dvo,dVO,sVO,varAct,cursor);funGraf[k]=Cal.calcul(dvo);
                        if(resultat>funGraf[k]&&isFinite(funGraf[k])){
                             resultat=funGraf[k];
                             System.arraycopy(cursor,0,cursorMinim,0,Cal.longi);
                        }
                    }
                    else{
                        cursor[idxGraf]=cursorMinimGraf[idxGraf]+(long)(cota+i*inc);//*pondIncrVar[0][idxGraf]);
                        varAct[idxGraf]=Cal.posic(idxGraf,cursor[idxGraf]);
                        varActGraf[k]=varAct[idxGraf];
                        if(vp){Cal.actualitzadvo(dvo,dVO,sVO,varAct,cursor);funGraf[k]=Cal.calcul(dvo);}
                        else funGraf[k]=Cal.resultat1P(idxGraf,dvo,dVO,sVO,varAct,resultatMcop);//else funGraf[k]=resultat1P(idxGraf);
                        if(taulaC.abcisaFunGrafBol[1])resultatFuncAbcis[k]=actualitzadvo_i_resultatAbcisa();
                        if(resultat>funGraf[k]&&isFinite(funGraf[k])){
                            resultat=funGraf[k];
                            cursorMinim[idxGraf]=cursor[idxGraf];
                }}}
                else{funGraf[k]=NaN;}
            }
       }
        exeGraf(true);
        resultatMin();
       }
       catch(Exception e){Func.append(1,splitPan.FIL+Func.error);Func.append(0," a Cur>calculDiagonalGrafic; comprobar en particular, que la funci"+Func.rB.getString("o_")+" principal cont"+Func.rB.getString("_e")+" algun tipus de variable directa o indirecta"+splitPan.FIL);return false;}
       return true;
   }
   private static void resultatMin(){
    if(resultat<resultatMin){
        resultatMin=resultat;
        System.arraycopy(cursorMinim,0,cursorMinimGraf,0,Cal.longi);
    }
    else {
        resultat=resultatMin;
        System.arraycopy(cursorMinimGraf,0,cursorMinim,0,Cal.longi);
    }
   }
   void pendent(){
       splitPan.temp0();
       stopBucle=false;
        String s="",s0="";
        if(Cal.longi==1){
            idxP=0;
            pondIncrVar[0]=1;
            varAct[0]=Cal.posic(0, cursorMinimGraf[0]);
            //else varAct[0]=Cal.posic(0, cursorCentreGrafic[0]);
            s="nom"+Func.rB.getString("e_")+"s hi ha una variable,";
            if(!taulaP.hihaParVar) Cal.calcMinPendentVar(true,cursor,cursorMinim,varAct,resultat,dvo,dVO,sVO,limSupCalc,minIncrSignif,resultatP,resultatMcop);
            else calcMinPendentPar();
        }
        else{
            for(int j=0;j<Cal.longi;j++){varAct[j]=Cal.posic(j,cursorMinimGraf[j]);}
            //else for(int j=0;j<Cal.longi;j++)varAct[j]=Cal.posic(j,cursorCentreGrafic[j]);
            Cal.actualitzadVO(dvo,dVO,sVO,varAct,cursor,cursorMinim);//actualitzadVO();
            cercaGraf();
            if(Cal.idxVarGen.length>0)s0+=splitPan.FIL+"variables:"+splitPan.FIL;
            for(int i=0;i<Cal.idxVarGen.length;i++)s0+=taulaV.varstaulaV[Cal.idxVarGen[i]]+": "+varAct[Cal.idxVarGen[i]]+";  ";
            if(Cal.idxParVar.length>0)s+=splitPan.FIL;
            for(int i=0;i<Cal.idxParVar.length;i++)s0+=taulaV.varstaulaV[Cal.idxParVar[i]]+": "+varAct[Cal.idxParVar[i]]+";  ";
            if(Cal.idxVarGen.length>0)s+=splitPan.FIL+"increments ponderats:"+splitPan.FIL;
            if(Cal.idxParVar.length>0)s+=splitPan.FIL;
            for(int i=0;i<Cal.idxVarGen.length;i++){ 
                int j=Cal.idxVarGen[i];
                s+=taulaV.varstaulaV[j]+": "+pondIncrVar[j]+";  ";
            }
            if(Cal.idxParVar.length>0)s+=splitPan.FIL;
            for(int i=0;i<Cal.idxParVar.length;i++){ 
                int j=Cal.idxParVar[i];
                s+=taulaV.varstaulaV[j]+": "+pondIncrVar[j]+";  ";
            }
            if(Cal.idxParVar.length>0)s+=splitPan.FIL;
        }
        append (0,s0);
        if(Func.ampliarInfo)append(0,s);
        for(int i=0;i<Cal.longi;i++){
            if(Math.abs(pondIncrVar[i])==1)idxP=i;
        }
        indexAmp=0;
        if(!unaVarBol){
            amp_text.setText(indexAmp+" < "+limCur[indexPunts]+"  ("+limSCalc+")");
            idxGraf=idxP;
        }
        else{amp_text.setText(indexAmp+" < "+limCur[indexPunts]+"  ("+limSupCalc[idxGraf]+")");}
        calculDiagonalGrafic(true);
        regresioGraf();
        s=splitPan.FIL+"m"+Func.rB.getString("i_")+"nim funci"+Func.rB.getString("o_")+": "+resultat+splitPan.FIL;
        append(0,s);
   }
    void cercaGraf(){
        testIncr(1E15);
        int j=1;if(indexPunts>1) j=indexPunts*3;//if(indexPunts<=1)j=1;if(indexPunts>1)j=7;if(indexPunts==6)j=11;if(indexPunts==7)j=15;if(indexPunts>7)j=19;if(indexPunts>9)j=23;
        int cont=0;
        for(int i=0;i<j;i++){
            if(stopBucle)return;
            cerca();
            cont++;
            if(cont==4){cont=0;testIncr(1E15);}
        }
        resultatMin();
    }
    void cerca(){
        long[] d=new long[Cal.longi];
        System.arraycopy(cursorMinim, 0, d,0,Cal.longi);
        Cal.calcMinPendentVar(true,cursor,cursorMinim,varAct,resultat,dvo,dVO,sVO,limSupCalc,minIncrSignif,resultatP,resultatMcop);//aqui resultat es el menor dels calculs del procediment
        if(taulaP.hihaParVar)calcMinPendentPar();//aqui resultat es la suma dels resultatsM[] que sont els menors
        if(Cal.longi>1){
            long max=0;
            for(int j=0;j<Cal.longi;j++){
                d[j]=cursorMinim[j]-d[j];//diferencia entre el cursorMinim actual i l'anterior
                long i=Math.abs(d[j]);
                if(max<i){max=i;}
            }
            if(max==0)for(int j=0;j<Cal.longi;j++)pondIncrVar[j]=1;
            else for(int j=0;j<Cal.longi;j++)pondIncrVar[j]=(double)d[j]/(double)max;
        }
        idxP++;if (idxP>=Cal.idxVarGen.length)idxP=0;
        if (idxP==idxGraf)idxP++;if (idxP>=Cal.idxVarGen.length)idxP=0;
        resultat=Cal.calcMinPendent(idxP,limSCalc,cursor,cursorMinim,pondIncrVar,dvo,dVO,sVO,varAct,resultat);
    }
    void calcMinPendentPar(){
        resultatP=new double[Cal.idxParVar.length];Cal.resultatMatrP(false,dvo,dVO,sVO,varAct,resultatP,resultatM,cursor,cursorMinim);
        int[] per={1,-1};
        for(int o=0;o<Cal.idxParVar.length;o++){
            int i=Cal.idxParVar[o];
            if(variableFixaBol[i]){
                varAct[i]=variableFixa[i];
                for(int id=0;id<Cal.indexsParV.length;id++){
                    if(Cal.indexsParV[id][0]==i){
                        for(int ix=0;ix<Cal.indexsParV[id][6];ix++){
                            int j=Cal.indexsParV[id][ix+7];
                            if(sVO[j].startsWith("-"))dVO[j]=-varAct[i];
                            else dVO[j]=varAct[i];
                        }
                    }      
                    resultatM[Cal.indexsParV[id][1]]=resultatMcop[Cal.indexsParV[id][1]];
                }
            }
            else{
                long am=Cal.segment;
                for(int n=0;n<=limSupCalc[i];n++){
                    int l=2;
                    per[0]=1;
                    am/=2;
                    if(cursorMinim[i]+am>Cal.segment) {per[0]=-1;l=1;}
                    else if(cursorMinim[i]-am<0) {l=1;}
                    for(int k=0;k<l;k++){
                        cursor[i]=cursorMinim[i]+(long)(per[k]*am);
                        varAct[i]=Cal.posic(i,cursor[i]);
                        double res=Cal.resultat1P(i,dvo,dVO,sVO,varAct,resultatMcop);
                        if(resultatP[o]>=res){
                            if(resultatP[o]>res){
                                resultatP[o]=res;
                                cursorMinim[i]=cursor[i];
                                for (int[] m : Cal.indexsParV) {
                                    if (m[0] == i)for (int ix = 0; ix < m[6]; ix++) {
                                        int j = m[ix+7];
                                        if(sVO[j].startsWith("-"))dVO[j]=-varAct[i];
                                        else dVO[j]=varAct[i];
                                    }
                                    resultatM[m[1]] = resultatMcop[m[1]];
                                }
                            }
                            else {
                                if(n<limSupCalc[i])n=limSupCalc[i];//si son iguals realitza un calcul mes
                            }//si es el mateix valor retorna hipotesi de funcio cte en l'interval
                        }
                    }
                }
            }
        }
        double sum=0;
        for(int i=0;i<resultatM.length;i++)sum+=resultatM[i];
        if(resultat>sum)resultat=sum;
    }
    public static void testCursor(String info){
        System.out.println(resultat+" "+resultatMin);
        for(int i=0;i<Cal.longi;i++){System.out.print(cursor[i]+" ");}System.out.println();
        for(int i=0;i<Cal.longi;i++){System.out.print(cursorMinim[i]+" ");}System.out.println();
        for(int i=0;i<Cal.longi;i++){System.out.print(cursorMinimGraf[i]+" ");}System.out.println();
        for(int i=0;i<Cal.longi;i++){varAct[i]=Cal.posic(i,cursorMinim[i]);System.out.print(varAct[i]+" ");}System.out.println();
        System.out.println();
    }
    //es calcula el minim de la diagonal del hiperEspai  diagonal  increments 1, 0, punt original el del mitg
    //si hi ha integral s'executa dins d'un thread
    public static boolean graficInicial(){
        splitPan.temp0();
        dvo=new double[dVO.length];
        for(int i=0;i<Cal.longi;i++)Cur.cursorMinim[i]=Cal.segment/2;
        for(int j=0;j<Cal.longi;j++){
               varAct[j]=Cal.posic(j,Cur.cursorMinim[j]);
               pondIncrVar[j]=1.0;
               cursorCentreGrafic[j]=Cal.segment/2;
        }
        indexAmp=0;
        idxP=0;
        resultat=Cur.maxim;
        resultatMin=Cur.maxim;
        if(Cal.derivadaBol&&!Func.tipusFuncioBol)for(int idxFil=0;idxFil<taulaD.simbol.length;idxFil++)supID.estimarIncrement(idxFil);
        System.arraycopy(Cur.cursorMinim,0,cursor,0,Cal.longi);
        Cal.actualitzadVO(dvo,dVO,sVO,varAct,cursor,cursorMinim);//actualitzadVO();
        if(!calculDiagonalGrafic(true))return false;
        for(int j=0;j<Cal.longi;j++)varAct[j]=Cal.posic(j,cursorMinim[j]);
        System.arraycopy(Cur.cursorMinim,0,cursor,0,Cal.longi);
        Cal.actualitzadVO(dvo,dVO,sVO,varAct,cursor,cursorMinim);//actualitzadVO();
        Cal.resultat=resultat;//despres calculDiagonalGrafic(boolean b) resultat i resultatMin coincideixen
        Cal.dVO=new double[dVO.length];
        Cal.dvo=new double[dVO.length];
        Cal.sVO=new String[sVO.length];
        System.arraycopy(dVO, 0, Cal.dVO, 0, dVO.length);
        System.arraycopy(sVO, 0, Cal.sVO, 0, sVO.length);
        System.arraycopy(cursorMinim,0,Cal.cursorMinim,0,Cal.longi);
        if(taulaD.hihaDades)System.arraycopy(Cur.supID.resultatFil,0,Cal.supID.resultatFil,0,Cal.supID.resultatFil.length);
        pan1.setVisible(true);
        int i=splitPan.split[7].getDividerLocation();
        int j=splitPan.split[0].getDividerLocation();
        int k=splitPan.split[1].getDividerLocation()-j;
        if(i>j+k*0.8||i<j+k*0.2) splitPan.split[7].setDividerLocation(j+k/2);
        capGBol=true;capturaC.setEnabled(true);//es pot efectuar captura//aqui els parametres principals cursorMinim, varAct, dVO, resultat, estan actualitzats tant a Cur i Cal  en el minim del calcul diagonal grafic
    return true;
    }
    //els calculs es fan sobre la matriu dvo i els resultats s'escriuen sobre aquesta matriu, despres del calcul dvo no conte els valors adequats per un calcul posterior
    //el proces que es segueix es el seguen: despres d'un resultat que millora l' anterior calcular les variables del nou minim a partir dels cursors i guardarles a dVO
    //per aixou tilitzar els procediments actualitzadVO() i actualitzadVO(o)
    //previ a un nou calcul fer copia de dVO a dvo guardar els nous valors de les variables que es probaran, a dvo i realizar el calcul
    //en la cerca del interval adequat minIncrSignif es dona el cas que les proves amb difernts intervals parteixen sempre de les nateixes variables inicials
    //en aques cas es mes normal fer copia de dVO a dvo i fer el canvi de la variable a dvo
    //en el cas de les integrals tambe fa el calcul de la integral que conte la variable general i  situa el resultat a la funcio principal dvo 
    //Cal.indexsInteg_VG[o][i] si true vol dir que la variable general del primer index o(taula variables filera) es troba al menys un cop en la integral del segon index i
    // Cal.Cal.indexsVGI[j][x]posicio dels sumatoris amb variables generals que es troben a la funcio general el segon index x=1 es la posicio del sumatoris(taules integrals columna funcio), el primer x=0 es la posicio del sumatori a sVO de la funcio principal, 
    public static void captura(){
        if(!capGBol)return;
        if(unaVarBol)unaVarBol=false;
        iniciaCurVars();
        System.arraycopy(Cal.dVO,0,dVO,0,dVO.length);
        System.arraycopy(Cal.cursorMinim,0,cursorMinim,0,Cal.longi);
        System.arraycopy(cursorMinim,0,cursorMinimGraf,0,Cal.longi);
        System.arraycopy(cursorMinim,0,cursorCentreGrafic,0,Cal.longi);
        resultat=Cal.resultat;
        resultatMin=resultat;
        punts_text.setText("punts: "+puntsGraf[indexPunts]);
        indexAmp=0;
        //idxAmp[0]=puntsGraf[indexPunts];
        if(!unaVarBol){amp_text.setText(indexAmp+" < "+limCur[indexPunts]+"  ("+limSCalc+")"+splitPan.FIL);}
        else{amp_text.setText(indexAmp+" < "+limCur[indexPunts]+"  ("+limSupCalc[idxGraf]+")"+splitPan.FIL);}
        regresioGraf();
        calculDiagonalGrafic(true);
        if(Cal.resultat<resultatMin){
            resultat=Cal.resultat;
            System.arraycopy(Cal.cursorMinim,0,cursorMinim,0,Cal.longi);
        }
        String s=presentaRes(cursorMinim,false);
        append(0,s+splitPan.FIL);
        append(0,"min. graf. funci"+Func.rB.getString("o_")+": "+resultat+splitPan.FIL);
        capBol=true;
    }
    //si previ a captura s'ha seleccionat un punt al grafic superior la captuta es fa dels valors dels cuursors del punt seleccionat
    //si no la captura Grafica es fa sobre el cursorMinimGraf que no te per que esser es el que es mostra a la pantalla, que pot no esser el minim absolut
    public static void capturaG(){
        if(!Func.cbM5.getState())Func.capturaManualBol=false;
        System.arraycopy(dVO,0,Cal.dVO,0,dVO.length);//captura carrega les dades de calcul grafic
        if(capturaInfoBol)System.arraycopy(cursor,0,Cal.cursorMinim,0,Cal.cursor.length);
        else System.arraycopy(cursorMinimGraf,0,Cal.cursorMinim,0,Cal.cursor.length);
        for(int i=0;i<Cal.longi;i++) if(variableFixaBol[i])Cal.cursorMinim[i]=cursorFixa[i];
    }
   private static void regresioGraf(){
	if(Func.graficReg){
            resultatRegrGraf();
            grafReg.puntsy.clear();
            grafReg.puntsx.clear();
            double m=max(funGrafP[1]);
            grafReg.maxY=max(funGrafP[2]);
            if(m>grafReg.maxY)grafReg.maxY=m;
            m=min(funGrafP[1]);
            grafReg.minY=min(funGrafP[2]);
            if(m<grafReg.minY)grafReg.minY=m;
            grafReg.maxX=max(funGrafP[0]);
            grafReg.minX=min(funGrafP[0]);
            for(int j=0;j<Cal.longiP;j++){
                grafReg.puntsy.add(funGrafP[2][j]);
                grafReg.puntsx.add(funGrafP[0][j]);
            }
            for(int j=0;j<Cal.longiP;j++){  
                grafReg.puntsy.add(funGrafP[1][j]);
                grafReg.puntsx.add(funGrafP[0][j]);
            }
            ratlla();
            regresioGrafBol=true;
            pan01.repaint();
	}
        else{regresioGrafBol=false;} 
    }
    public static void resultatRegrGraf(){
        System.arraycopy(dVOG, 0, dvoG, 0, dvoG.length);
        //for(int i=0;i<3;i++){System.out.println(variableFixaBol[i]+" "+varAct[i]+" "+variableFixa[i]+" "+Cal.posic(i,cursorMinim[i]));}
        for(int i=0;i<Cal.longi;i++){
            if(variableFixaBol[i])varAct[i]=variableFixa[i];
            else varAct[i]=Cal.posic(i,cursorMinim[i]);
        }
        for(int i=0;i<indexsVar_gr.length;i++){
            if(sVOG[indexsVar_gr[i][0]].startsWith("-"))dvoG[indexsVar_gr[i][0]]=-varAct[indexsVar_gr[i][1]];
            else dvoG[indexsVar_gr[i][0]]=varAct[indexsVar_gr[i][1]];
        }
        //for(int i=0;i<3;i++)System.out.print(varAct[i]+" ");System.out.println();
        //for(int i=0;i<10;i++)System.out.print(dVO[i]+" ");System.out.println();
        //for(int i=0;i<10;i++)System.out.print(dvoG[i]+" ");System.out.println();
        if(indexsVarDer_gr.length>0){//si hi funcions parcials a funcio de regressio o hi ha integrals amb dependencia
            System.arraycopy(varAct, 0, varCopia, 0, varAct.length);
            double[] d=supID.calculDerivada(varCopia,cursorMinim);
            if(Cal.derivadaBol){
                for(int i=0;i<indexsVarDer_gr.length;i++){
                    if(sVOG[indexsVarDer_gr[i][0]].startsWith("-"))dvoG[indexsVarDer_gr[i][0]]=-d[indexsVarDer_gr[i][1]];
                    else dvoG[indexsVarDer_gr[i][0]]=d[indexsVarDer_gr[i][1]];
                }
            }
        }
        if(indexsVarInt_gr.length>0){ 
            if(Cal.hihaFPIntegrBol[0][0])supID.FPIntegral();
            supID.substitueixVG(varAct);//substitueix totes les variables generals al totes les integrals(funcions i intervals) no a l funcio del grafic
            for(int i=0;i<taulaI.sumat.length;i++){//substitueix a la funcio principal el valor de tors els sumatoris amb variables generals actualitzats
                if(suportID.hihaVGioFPaI[i])supID.bucle(i);
            }
            for(int i=0;i<indexsVarInt_gr.length;i++){//substitueix a la funcio grafica el valor de tors els sumatoris amb variables generals actualitzats
                if(sVOG[indexsVarInt_gr[i][0]].startsWith("-"))dvoG[indexsVarInt_gr[i][0]]=supID.integral[indexsVarInt_gr[i][1]];
                else dvoG[indexsVarInt_gr[i][0]]=supID.integral[indexsVarInt_gr[i][1]];
            }
       }
       for(int i=0;i<Cal.longiP;i++){
            if(limindexs_gr[i][1]==limindexs_gr[i+1][1])funGrafP[2][i]=dvoG[limindexs_gr[i][0]];
            else{
                for(int j=limindexs_gr[i][1];j<limindexs_gr[i+1][1];j++){
                    dvoG[indexs_gr[j][1]]=Cal.caseOpFun(indexs_gr[j][0],dvoG[indexs_gr[j][1]],dvoG[indexs_gr[j][2]]);
                }
                funGrafP[2][i]=dvoG[indexs_gr[limindexs_gr[i+1][1]-1][1]];
             }
        }
    }
    static void ratlla(){
        int lo=Cal.longiP;
        if(lo<2){grafReg.ratlla=new int[0][0];return;}
        int m[][]=new int[lo][2];
        double[] d=new double[lo];
        System.arraycopy(funGrafP[0], 0, d, 0, lo);
        int creix=0;
        int decreix=0;
        for(int i=1;i<lo;i++){
            double k=d[i]-d[i-1];
            if(k>0)creix++;
            else if(k<0) decreix++;
        }
        boolean bol=decreix>creix;
        int con=0;
        boolean[][] b=new boolean[lo][2];
        for(int l=0;l<lo-1;l++)for(int i=0;i<lo-1;i++)if(!b[i][0])for(int j=i+1;j<lo;j++)if(!b[j][1]){
            double k=d[j]-d[i];
            if(bol)k=-k;
            if(k>0){
                m[con][0]=i; m[con][1]=j;
                b[i][0]=true;b[j][1]=true;
                i=j-1;j=lo;
                con++;
            }
        }
        grafReg.ratlla=new int[con][2];
        con=0;
        for(int i=0;i<lo;i++){
            if(m[i][0]!=m[i][1]){
            grafReg.ratlla[con][0]=m[i][0];
            grafReg.ratlla[con][1]=m[i][1];
            con++;
            }
        }
    }
    private static double min(double[] p){
    	double m=maxim;
    	for(int i=0;i<p.length;i++)if(m>p[i])m=p[i];
    	return m;
    }
    private static double max(double[] p){
    	double m=-maxim;
    	for(int i=0;i<p.length;i++)if(m<p[i])m=p[i];
    	return m;
    }
      public static void append(int i, String s) {
      Color c=Color.BLUE;
      if(i==1) c=Color.RED;
      else if(i==2) c=Color.BLACK;
      Func.textDeco1.setCaretPosition(Func.textDeco1.getText().length());
      StyleContext sc = StyleContext.getDefaultStyleContext();
      AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,StyleConstants.Foreground, c);
      Func.textDeco1.replaceSelection(s);
      c=Color.BLUE;
      aset = sc.addAttribute(SimpleAttributeSet.EMPTY,StyleConstants.Foreground, c);
      Func.textDeco1.replaceSelection("");
    }
}