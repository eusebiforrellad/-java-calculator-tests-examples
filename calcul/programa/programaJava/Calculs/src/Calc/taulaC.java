package Calc;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
public class taulaC extends JPanel implements ActionListener{
    private static final long serialVersionUID = 1L;
    public static JTable tl;
    public static String [][] matriu;
    public static int  digitsValids;
    public static Double ceroLog;
    public static String antCalcGraf="nul";
    public static String ordenadaGraf="n";
    public static String abcisaGraf="n";//inicialment pren el valors x, o p, o la funcio, si pren el de la funcio despres de executarse el programa hihafunAbcisa() si la funcio es correcte pren el valor del simbol f
    public static boolean[] abcisaFunGrafBol;
    static String[] TT;
    static int regr=4,grafA=3,fileres=7;
    public static String[] textFilera;
    public static JTextField cela;
    public static String parVar;
    public static JCheckBoxMenuItem cbM = new JCheckBoxMenuItem("scroll taula");
    public static int scrollCols; 
    static JMenuItem  jmi_copiSel, jmi_ajuntaSel, jmi_copi,jmi_cut, jmi_ajunta,jmi_carregar,jmi_save,jmi_saveAs,jmi_reinici;//,jmi_scroll;
    static JTableHeader JThd;
    public taulaC(){
        super(new GridLayout(1,0));
        inici();
    }
    public taulaC(boolean x){}
    public void INI(){
        textFilera=new String[fileres];
    	textFilera[0]="precissi"+Func.rB.getString("o_")+" round:";
        textFilera[1]="cero(log):";
        textFilera[2]="param. graf.:";
    	textFilera[grafA]="gr"+Func.rB.getString("a")+"fic func.abs.:";
        textFilera[regr]="regressi"+Func.rB.getString("o_")+" abs.:";
    	textFilera[regr+1]="regressi"+Func.rB.getString("o_")+" ord.exp.:";
    	textFilera[regr+2]="regressi"+Func.rB.getString("o_")+" ord.calc.:";
        TT=new String[textFilera.length];
        TT[0]="<html>valors v"+Func.rB.getString("a")+"lids: de -14 a 14 o blanc<br>en la cerca de m"+Func.rB.getString("i_")+"nim, quan el nombre de d"+Func.rB.getString("i_")+"gits mes significatius (relacionats amb aquest valor)<br>es repeteix un cert nombre de vegades la cerca finalitza.<br>el resultat final s'arrodoneix en funci"+Func.rB.getString("o_")+" d'aquest valor (el signe - no arrodoneix el resultat)";//<br>si hi ha sumatoris i ha altres condicion no hi ha el s"+Func.rB.getString("i_")+"mbol a, limita la fondaria de la cerca de m"+Func.rB.getString("i_")+"nim a una ampliacio = 26+2*valor";
        TT[1]="<html>les variables es mouen en una escala l"+Func.rB.getString("i_")+"neal (0) o logar"+Func.rB.getString("i_")+"tmica. (negatiu) en aquest segon cas,<br>quan els l"+
                Func.rB.getString("i_")+"mits inclouen el cero es pot escriure un valor, per exemple -18 per(1E-18)."
                + "<br>en aquest cas, si absolut(l"+Func.rB.getString("i_")+"mit superior) * absolut(l"+Func.rB.getString("i_")+"mit inferior) son <0  i abs(l"+Func.rB.getString("i_")+"mits)>0"
                + "<br>el cursor al passar pel cero fara que la variable salti de -10E-18 a 10E-18<br>si hi ha un l"+Func.rB.getString("i_")+"mit inferior a cero aquest valor ser"+Func.rB.getString("a")+" el menor de limitSuperior*1E-18 o limitInferior*1E-18"
                + "<br>si la cel.la es buida es selecciona lineal o logar"+Func.rB.getString("i_")+"tmica en funci"+Func.rB.getString("o_")+" de dels l"+Func.rB.getString("i_")+"mits, si la elecci"+Func.rB.getString("o_")+" es log. es pren el valor -16";
        TT[2]="<html>el primer s"+Func.rB.getString("i_")+"mbol opcional: 's' o 'l' "
                + "<br>si es 's' el gr"+Func.rB.getString("a")+"fic mostra, a ordenada, el valor de la funci"+Func.rB.getString("o_")+" simplificada"
                + "<br>si es 'l' el gr"+Func.rB.getString("a")+"fic mostra, a ordenada, logar"+Func.rB.getString("i_")+"tme decimal de la funci"+Func.rB.getString("o_")+","
                + "<br>en aquest cas la possici"+Func.rB.getString("o_")+" log(0) es representa com log(proper a cero)"
                + "<br>quan no hi ha cap d'aquests s"+Func.rB.getString("i_")+"mbols el valor predeteminat es mostrar a ordenada, el valor de la funci"+Func.rB.getString("o_")
                +"<br>el segon s"+Func.rB.getString("i_")+"mbol opcional es: 'p' o'x' nom"+Func.rB.getString("e_")+"s actius quan hi ha seleccionada la opci"+Func.rB.getString("o_")+" de variable "+Func.rB.getString("u_")+"nica"
                + "<br>si es 'x' el gr"+Func.rB.getString("a")+"fic mostra funci"+Func.rB.getString("i_")+" vs. variable seleccionada"
                + "<br>si es 'p' el gr"+Func.rB.getString("a")+"fic mostra funci"+Func.rB.getString("o_")+"*sinus(variable seleccionada) vs. cos(variable seleccionada)";
        TT[grafA]="<html>dues posibilitats 1.- funci"+Func.rB.getString("o_")+", amb nom"+Func.rB.getString("e_")+"s una variable nova ,diferent de les de la taula V, que es substituira per la variable activa"
                + "<br>en aquest cas (quan la opci"+Func.rB.getString("o_")+" de variable "+Func.rB.getString("u_")+"nica es activa) la abscissa representa els valors d'aquesta funci"+Func.rB.getString("o_")+" de la variable activa)<br>2.- funci"+Func.rB.getString("o_")+" amb qualsevol tipus de par"+Func.rB.getString("a")+"metre io variable de qualsevol taula, en aquest cas la "+Func.rB.getString("u_")+"nica variable que es modificara ser"+Func.rB.getString("a")+" la aciva";
        TT[regr]="variable o funci"+Func.rB.getString("o_")+" de la abscissa, un punt per par"+Func.rB.getString("a")+"metre";
        TT[regr+1]="funci"+Func.rB.getString("o_")+" ordenada de refer"+Func.rB.getString("_e")+"ncia, un punt per par"+Func.rB.getString("a")+"metre";
        TT[regr+2]="funci"+Func.rB.getString("o_")+" ordenada, un punt per par"+Func.rB.getString("a")+"metre que varia en funci"+Func.rB.getString("o_")+" del valor de les variables";
        cela = new JTextField();
        cela.setBorder(Func.border);
        cela.addKeyListener(new KeyAdapter() {public void keyTyped(KeyEvent e) {Func.analisiPreviBol=false;}});
        cela.addMouseListener(createPopupMenu1());
    }
    public void inici(){
        valorsInicials();
        tl = new JTable(new model()){
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);
                if(Func.tooltiptxt){
                    if(colIndex==0){
                        try {if(rowIndex >= 0){tip = TT[rowIndex];}}
                        catch (RuntimeException e1) {}
                        return tip; 
                    }
                    else {try {tip = getValueAt(rowIndex, colIndex).toString();} catch (RuntimeException e1) {}}
                }
                return tip;
            }
        };
        tl.putClientProperty("terminateEditOnFocusLost", true);
        tl.setPreferredScrollableViewportSize(new Dimension(300, 80));
        tl.setRowSelectionAllowed(true);
        tl.setColumnSelectionAllowed(true);
        tl.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(tl);
        add(scrollPane);
        setOpaque(true);
        createPopupMenu();
        tl.setAutoResizeMode(scrollCols);
        color();
        editaCela();
    }
    private void editaCela() {
        TableColumnModel t = tl.getColumnModel();
        for(int i=0;i<matriu[0].length;i++)t.getColumn(i).setCellEditor(new DefaultCellEditor(cela));
    }
    public MouseListener createPopupMenu1() {
        JPopupMenu popup = new JPopupMenu();
         jmi_copiSel = new JMenuItem("copia sel."); jmi_copiSel.setIcon(Func.Copiar());
         jmi_copiSel.addActionListener(this);
        popup.add( jmi_copiSel);
        jmi_ajuntaSel = new JMenuItem("ajunta sel.");jmi_ajuntaSel.setIcon(Func.Engan());
        jmi_ajuntaSel.addActionListener(this);
        popup.add(jmi_ajuntaSel);
        MouseListener ml = new TaulesPopup(popup);
        return ml;
    }
    public void createPopupMenu() {
        JPopupMenu popup = new JPopupMenu();
        jmi_copi = new JMenuItem("copiar");jmi_copi.setIcon(Func.Copiar());
        jmi_copi.addActionListener(this);
        popup.add(jmi_copi);
        jmi_cut= new JMenuItem("cut");jmi_cut.setIcon(Func.Copiar());
        jmi_cut.addActionListener(this);
        popup.add(jmi_cut);
        jmi_ajunta = new JMenuItem("ajuntar a cel.la");jmi_ajunta.setIcon(Func.Engan());
        jmi_ajunta.addActionListener(this);
        popup.add(jmi_ajunta);
         popup.addSeparator();
        jmi_reinici = new JMenuItem("reiniciar taula");jmi_reinici.setIcon(Func.Borrar());
        jmi_reinici.addActionListener(this);
        popup.add(jmi_reinici);
        popup.addSeparator();
        jmi_carregar = new JMenuItem("carregar dades");jmi_carregar.setIcon(Func.Obrirarx());
        jmi_carregar.addActionListener(this);
        popup.add(jmi_carregar);
        jmi_save = new JMenuItem("guardar dades");jmi_save.setIcon(Func.Guarda());
        jmi_save.addActionListener(this);
        popup.add(jmi_save);
        jmi_saveAs = new JMenuItem("guardar (save as)");jmi_saveAs.setIcon(Func.GuardaC());
        jmi_saveAs.addActionListener(this);
        popup.add(jmi_saveAs);
        popup.addSeparator();
        popup.add(cbM);//cbM.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
        if(scrollCols==0){cbM.setState(true);}else{cbM.setState(false);}
        MouseListener ml = new TaulesPopup(popup);
        tl.addMouseListener(ml);
      }
    public static String lectAmplCol(){
        int cc=tl.getColumnCount();
        String s=splitPan.ampladaCols;
        for(int i=0;i<cc;i++)s+=tl.getColumnModel().getColumn(i).getWidth()+"  ";
        s+=splitPan.FIL;
        return s;
    }
    public static void fixaAmplCol(int[] amp){
        int cc=tl.getColumnCount();
        for(int i=0;i<cc;i++)tl.getColumnModel().getColumn(i).setPreferredWidth(amp[i]);
    }    
    public  boolean matriuTaula(){
        abcisaFunGrafBol=new boolean[2];
        if(Func.tipusFuncioBol)return true;
        parVar="";
        try{digitsValids=Integer.parseInt(matriu[1][1]);}
    	catch(Exception e){
            Func.append(1,Func.error);Func.append(0,textFilera[1]+" format erroni (sencer 1-14), valor predeterminat = 5"+splitPan.FIL);
            Object o="5";tl.setValueAt(o,0,1);
            digitsValids=5;
    	}
        if(digitsValids==0){Object o="1";tl.setValueAt(o,0,1);digitsValids=1;}
        if(digitsValids>14){Object o="14";tl.setValueAt(o,0,1);}
        if(digitsValids<-14){Object o="-14";tl.setValueAt(o,0,1);}
        if(digitsValids<0){digitsValids=-digitsValids;Func.noArrodonir=true;}else Func.noArrodonir=false;
        if(digitsValids>14){digitsValids=14;}
        if(!matriu[regr+1][1].equals("")&&!matriu[regr+2][1].equals("")&&!matriu[regr+3][1].equals("")){Func.graficReg=true;}
        else{
            if(!matriu[regr+1][1].equals("")||!matriu[regr+2][1].equals("")||!matriu[regr+3][1].equals(""))
            {Func.append(1,Func.error);Func.append(0,"per presentar el gr"+Func.rB.getString("a")+"fic de regressi"+Func.rB.getString("o_")+" cal que les tres fileres de funcions gr"+Func.rB.getString("a")+"fiques tinguin contingut"+splitPan.FIL+"el procediment continua sense presentar el gr"+Func.rB.getString("a")+"fic de regressi"+Func.rB.getString("o_")+splitPan.FIL);}
            Func.graficReg=false;
        }
        boolean b=false;
        if (!matriu[4][1].trim().equals("")){
            if(!taulaV.hihaVar){Func.append(1,Func.error);Func.append(0,"no hi ha variables generals, la funci"+Func.rB.getString("o_")+": "+matriu[taulaC.grafA+1][1]+"ha de contenir alguna variable general de la taulaV"+splitPan.FIL);return false;}
            abcisaFunGrafBol[0]=true;
        }
        return true;
    }
      public static void font(){
        Font fo = new Font(font.tipus, font.estil, font.tamany);
        cbM.setFont(fo);
        jmi_copiSel.setFont(fo);
        jmi_ajuntaSel.setFont(fo);
        jmi_copi.setFont(fo);
        jmi_cut.setFont(fo);
        jmi_ajunta.setFont(fo);
        jmi_reinici.setFont(fo);
        jmi_carregar.setFont(fo);
        jmi_save.setFont(fo);
        jmi_saveAs.setFont(fo);
        JThd.setFont(fo);
  }
    public static String carregaPartaulaC(String s){
        Func.ampliarInfo=false;
        Func.mostrarTeclat=false;
        Func.clearFuncio=false;
        Func.capturaManualBol=false;
        Func.tooltiptxt=true;
        Func.mouInvertBol=false;
        Func.unirPunts=true;
        Func.saltBol=0;
        Func.saltBol1=false;
        ordenadaGraf="n";
        abcisaGraf="n";
        Cur.indexPunts=2;
        taulaC.scrollCols=2;
        taulaP.scrollCols=2;
        taulaV.scrollCols=2;
        taulaD.scrollCols=2;
        taulaI.scrollCols=2;
        taulaF.scrollCols=2;
        taulaI.actualitzar=false;
        int i=s.indexOf("(");
        int j=s.indexOf(")");
        if(i>-1&&j>i){
            font.fontIni(s.substring(i,j));
            taulaC.scrollCols=0;
            s=s.substring(0,i)+s.substring(j+1);
        }
        else font.fontIni("");
        s=s.toLowerCase().trim();
        if(s.contains("b0")){taulaC.scrollCols=0;j=s.indexOf("b0");s=s.substring(0,j)+s.substring(j+2);}
        if(s.contains("b1")){taulaP.scrollCols=0;j=s.indexOf("b1");s=s.substring(0,j)+s.substring(j+2);}
        if(s.contains("b2")){taulaV.scrollCols=0;j=s.indexOf("b2");s=s.substring(0,j)+s.substring(j+2);}
        if(s.contains("b3")){taulaD.scrollCols=0;j=s.indexOf("b3"); s=s.substring(0,j)+s.substring(j+2);}
        if(s.contains("b4")){taulaI.scrollCols=1;j=s.indexOf("b4");s=s.substring(0,j)+s.substring(j+2); }
        if(s.contains("b5")){taulaF.scrollCols=0;j=s.indexOf("b5");s=s.substring(0,j)+s.substring(j+2);}
        if(s.contains("c")){Func.capturaManualBol=true;j=s.indexOf("c");s=s.substring(0,j)+s.substring(j+1);}
        if(s.contains("d")){Func.mouInvertBol=true;j=s.indexOf("d");s=s.substring(0,j)+s.substring(j+1);}
        if(s.contains("f")){Func.clearFuncio=true; j=s.indexOf("f");s=s.substring(0,j)+s.substring(j+1);}
        if(s.contains("g")){Func.ratllaBol=true;j=s.indexOf("g");s=s.substring(0,j)+s.substring(j+1);}
        if(s.contains("h")){Func.ratllaBol1=true;j=s.indexOf("h");s=s.substring(0,j)+s.substring(j+1);}
        if(s.contains("i")){ Func.ampliarInfo=true;j=s.indexOf("i"); s=s.substring(0,j)+s.substring(j+1); }
        if(s.contains("l")){ordenadaGraf="l";j=s.indexOf("l");s=s.substring(0,j)+s.substring(j+1);}
        if(s.contains("m")){Func.mostrarTeclat=true;j=s.indexOf("m");s=s.substring(0,j)+s.substring(j+1);}
        if(s.contains("p")){abcisaGraf="p";j=s.indexOf("p");s=s.substring(0,j)+s.substring(j+1);}
        if(s.contains("s")){ordenadaGraf="s"; j=s.indexOf("s");s=s.substring(0,j)+s.substring(j+1);}
        if(s.contains("t")){Func.tooltiptxt=false;j=s.indexOf("t");s=s.substring(0,j)+s.substring(j+1);}
        if(s.contains("u")){Func.unirPunts=false;j=s.indexOf("u"); s=s.substring(0,j)+s.substring(j+1);}
        if(s.contains("v")){Func.saltBol=1;j=s.indexOf("v");s=s.substring(0,j)+s.substring(j+1);}
        if(s.contains("w")){Func.saltBol1=true;j=s.indexOf("w");s=s.substring(0,j)+s.substring(j+1);}
        if(s.contains("x")){abcisaGraf="x";j=s.indexOf("x");s=s.substring(0,j)+s.substring(j+1);}
        if(s.contains("y")){taulaI.actualitzar=true;j=s.indexOf("y");s=s.substring(0,j)+s.substring(j+1); }
        if(s.contains("a")){//indexPunts
            for(i=Cur.mitatPuntsGraf.length;i>=0;i--){
                String s1="a"+i;
                j=s.indexOf(s1);
                if(j>-1){
                    try{Cur.indexPunts=Integer.parseInt(s1.substring(1));}
                    catch(Exception e){Cur.indexPunts=2;}
                    s=s.substring(0,j)+s.substring(j+s1.length());
                    i=Cur.mitatPuntsGraf.length;
                }
            }
        }
        tl.setValueAt(s,1,1);
        Func.setState();
        return s;
    }
    public static String treuSimbols(String s){
        while(s.contains("(")){int j=s.indexOf("(");s=s.substring(0,j)+s.substring(j+1);}
        while(s.contains(")")){int j=s.indexOf(")");s=s.substring(0,j)+s.substring(j+1);}
        while(s.contains("i")){int j=s.indexOf("i");s=s.substring(0,j)+s.substring(j+1);}
        while(s.contains("m")){int j=s.indexOf("m");s=s.substring(0,j)+s.substring(j+1);}
        while(s.contains("c")){int j=s.indexOf("c");s=s.substring(0,j)+s.substring(j+1);}
        while(s.contains("t")){int j=s.indexOf("t");s=s.substring(0,j)+s.substring(j+1);}
        while(s.contains("d")){int j=s.indexOf("d"); s=s.substring(0,j)+s.substring(j+1);}
        while(s.contains("u")){int j=s.indexOf("u"); s=s.substring(0,j)+s.substring(j+1);}
        while(s.contains("f")){int j=s.indexOf("f"); s=s.substring(0,j)+s.substring(j+1);}
        while(s.contains("l")){int j=s.indexOf("l"); s=s.substring(0,j)+s.substring(j+1);}
        while(s.contains("s")){int j=s.indexOf("s");s=s.substring(0,j)+s.substring(j+1);}
        while(s.contains("p")){int j=s.indexOf("p"); s=s.substring(0,j)+s.substring(j+1);}
        while(s.contains("x")){int j=s.indexOf("x"); s=s.substring(0,j)+s.substring(j+1);}
        while(s.contains("v")){int j=s.indexOf("v"); s=s.substring(0,j)+s.substring(j+1);}
        while(s.contains("w")){int j=s.indexOf("w"); s=s.substring(0,j)+s.substring(j+1);}
        while(s.contains("j")){int j=s.indexOf("j"); s=s.substring(0,j)+s.substring(j+1);}
        while(s.contains("g")){int j=s.indexOf("g"); s=s.substring(0,j)+s.substring(j+1);}
        while(s.contains("h")){int j=s.indexOf("h"); s=s.substring(0,j)+s.substring(j+1);}
        while(s.contains("y")){int j=s.indexOf("y"); s=s.substring(0,j)+s.substring(j+1);}
        for(int i=0;i<6;i++)while(s.contains("b"+i)){int j=s.indexOf("b"+i); s=s.substring(0,j)+s.substring(j+2);}
        if(s.contains("a"))for(int i=0;i<Cur.mitatPuntsGraf.length;i++){
            String s1="a"+i;
            int j=s.indexOf(s1);
            if(j>-1){
                try{Cur.indexPunts=Integer.parseInt(s1.substring(1));}
                catch(Exception e){Cur.indexPunts=2;}
                s=s.substring(0,j)+s.substring(j+s1.length());
                j--;
            }
        }
        tl.setValueAt(s,1,1);
        return s;
    }
    public static String guardaPartaulaC(){
        String s="";
        if(!matriu[2][1].equals("")){if(ceroLog!=null)s+=ceroLog;}
        String s0=s;
        if(Func.ampliarInfo)s+="i";
        if(Func.mostrarTeclat)s+="m";
        if(Func.capturaManualBol)s+="c";
        if(!Func.tooltiptxt)s+="t";
        if(Func.mouInvertBol)s+="d";
        if(!Func.unirPunts)s+="u";
        if(Func.clearFuncio)s+="f";
        if(ordenadaGraf.equals("l"))s+="l";
        if(ordenadaGraf.equals("s"))s+="s";
        if(abcisaGraf.equals("p"))s+="p";
        if(abcisaGraf.equals("x"))s+="x";
        if(Func.saltBol==1)s+="v";
        if(Func.saltBol1)s+="w";
        if(Func.ratllaBol)s+="g";
        if(Func.ratllaBol1)s+="h";
        if(taulaC.scrollCols==0)s+="b0";
        if(taulaP.scrollCols==0)s+="b1";
        if(taulaV.scrollCols==0)s+="b2";
        if(taulaD.scrollCols==0)s+="b3";
        if(taulaI.scrollCols==1)s+="b4";
        if(taulaF.scrollCols==0)s+="b5";
        if(taulaI.actualitzar)s+="y";
        s+="a"+Cur.indexPunts;
        s+=font.guardar();
        tl.setValueAt(s,1,1);
        return s0;
    }
    public static boolean definirEscala(String s){
        boolean correcte=true;
        if(!Func.tipusFuncioBol){
            if(matriu[2][1]==null)matriu[2][1]="";
            s=matriu[2][1].toLowerCase().trim();
        }
        if(s.equals("")){
            Cal.escalaLinial=true;ceroLog=-16.0;
            for(int i=0;i<Cal.longi;i++){
                double d1=0,d2=0;
                if(Cal.dMatriuVar[i][0]!=0)d1=Math.log10(Math.abs(Cal.dMatriuVar[i][0]));
                if(Cal.dMatriuVar[i][1]!=0)d2=Math.log10(Math.abs(Cal.dMatriuVar[i][1]));
                if(Cal.dMatriuVar[i][0]*Cal.dMatriuVar[i][1]<0&&Math.abs((d2+d1))>8){Cal.escalaLinial=false;ceroLog=-16.0;i=Cal.longi;}//tots dos diferents de cero un posistius l'altre negatius
                else if(Cal.dMatriuVar[i][0]==0&&d2>8){Cal.escalaLinial=false;ceroLog=-16.0;i=Cal.longi;}
                else if(Cal.dMatriuVar[i][1]==0&&d1>8){Cal.escalaLinial=false;ceroLog=-16.0;i=Cal.longi;}
                else if(Math.abs((d2-d1))>8){Cal.escalaLinial=false;ceroLog=-16.0;i=Cal.longi;}//tots dos positius o negatius
            }
        }
        else if(s.equals("0")){Cal.escalaLinial=true;ceroLog=0.0;}
        else{
            Cal.escalaLinial=false;
            try{ceroLog=Double.parseDouble(splitPan.comApun(s));}
            catch(Exception e){texErr();correcte=false;}
        }
        if (correcte)if(ceroLog>0||ceroLog<-323.3062153431158){Func.append(1,textFilera[1]+Func.error);Func.append(0," interval v"+Func.rB.getString("a")+"lid: = 0.0>x>-323.3062153431158 (0: abcises lineals)"+splitPan.FIL);correcte=false;}
        if(!Func.tipusFuncioBol){
            if(correcte&&taulaV.hihaVar){
                if(Func.ampliarInfo){
                    if(Cal.escalaLinial)Func.append(0,"escala Lineal"+splitPan.FIL);
                    else Func.append(0,"escala logar"+Func.rB.getString("i_")+"tmica, cero = "+Math.pow(10,ceroLog)+splitPan.FIL);
                }
                Cal.segCero=new long[Cal.longi];
                if(!Cal.escalaLinial) Cal.escalaNoLinial(0,Cal.longi);
            }
        }
        return correcte;
    }
    private static void texErr(){
        Func.append(1,Func.error);Func.append(0,textFilera[1]+" cadenes v"+Func.rB.getString("a")+"lides: "
                        + "valor num"+Func.rB.getString("_e")+"ric: 0  abscisses l"+Func.rB.getString("i_")+"nials"+splitPan.FIL
                        + "valor num"+Func.rB.getString("_e")+"ric: 0.0>x>-308.0 abscisses logar"+Func.rB.getString("i_")+"tmiques"+splitPan.FIL);
    }

    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        String s = source.getText();
        if(s.equals("reiniciar taula")){reinici();}
        if(s.equals("guardar dades")){splitPan.guardarArx();}
        if(s.equals("guardar (save as)")){splitPan.guardarArxCom();}
        if(s.equals("carregar dades")){splitPan.carregaArx();}
        if ("copiar".equals(e.getActionCommand())) {splitPan.cop(tl,matriu,false);}
        if ("cut".equals(e.getActionCommand())) {splitPan.cop(tl,matriu,true);}
        if(s.equals("ajuntar a cel.la")){
            String[][] m=splitPan.paste();
            int row=tl.getSelectedRow();int col=tl.getSelectedColumn();
            boolean b=false;
            if(matriu[0].length<col+m[0].length||matriu.length<row+m.length+1){Func.append(1,"error: ");Func.append(0,"elnombre de fileres i columnes es fixe i no es pot incrementarel la copia implica un increment del nombre de files io columnes;"+ splitPan.FIL);return;}
            capsalInteg();
            if(b){nouModel();}
            for(int i=0;i<m.length;i++)for(int j=0;j<m[0].length;j++)tl.setValueAt(m[i][j], row+i,col+j);
            capsalInteg();
            return;
        }
        if ("copia sel.".equals(e.getActionCommand())) {splitPan.cop_sel(cela);return;}
        if(s.equals("ajunta sel.")){splitPan.paste_sel(cela);}
    }
    public void reinici(){ matriu=null;valorsInicials();nouModel();}
    public void nouModel(){
        Func.analisiPreviBol=false;
        tl.setModel(new model());
        for(int i=1;i<matriu.length;i++)for(int j=0;j<matriu[0].length;j++){tl.setValueAt((Object)matriu[i][j], i-1, j);}
        int i=tl.getRowCount();
        for(int j=0;j<i;j++)tl.setRowHeight( j, 18 );
        splitPan.tTC.repaint();
        color();
        editaCela();
    }
     public void color(){
        Color gc=new Color(235,235,235);
        Color gmc=new Color(220,220,220);
        Color gmh=new Color(215,228,235);
        JThd = tl.getTableHeader();
        TableCellRenderer hr = tl.getTableHeader().getDefaultRenderer();
        JThd.setDefaultRenderer(new TableCellRenderer() {
            JLabel lb;
            @Override
            public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus,int row, int column) {
                    lb = (JLabel) hr.getTableCellRendererComponent(table, value, false, false, row, column);
                    for(int i=0;i<2;i++)if (column == i)lb.setBackground(gmh);
                return lb;
                }
        });
        for(int i=0;i<2;i++){
            tl.getColumn(tl.getColumnName(i)).setCellRenderer(new DefaultTableCellRenderer() {
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    DefaultTableCellRenderer r = (DefaultTableCellRenderer) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if(!isSelected){
                        if (column==0) r.setBackground(gmh);
                        else r.setBackground(gc);
                    } 
                    return r;
                }   
            });
        }
    }    
    private static void capsalInteg(){
        matriu[0][0] = "Descripci"+Func.rB.getString("o_");matriu[0][1] = "par"+Func.rB.getString("a")+"metre";
        for(int i=1;i<matriu.length;i++){matriu[i][0]=textFilera[i-1];}
    }
    public void valorsInicials(){
        if(matriu==null){reiniciParcial();}
    	else{capsalInteg();}
    }
    private void reiniciParcial(){
        matriu=new String[fileres+1][2];
        capsalInteg();
        matriu[1][1]="5";
        for(int i=2;i<fileres+1;i++)matriu[i][1]="";
    }
    class model extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
        public int getColumnCount() {return matriu[0].length;}
        public int getRowCount() {return matriu.length-1;}
        public String getColumnName(int col) {return matriu[0][col]; }
        public Object getValueAt(int row, int col) {return matriu[row+1][col]; }
        public boolean isCellEditable(int row, int col) {if (col < 1) {return false;} else { return true; }}
        public void setValueAt(Object value, int row, int col) {
            matriu[row+1][col]=(String)value;
            fireTableCellUpdated(row, col);
        }
    }
}