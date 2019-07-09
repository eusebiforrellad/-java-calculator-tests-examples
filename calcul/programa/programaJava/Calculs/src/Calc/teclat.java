package Calc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class teclat implements ActionListener {
    teclat tecl;
    public static String tecla;
    public static boolean teclatBol=false;
    static int fil,col,filT,colT;
    static String[][] matriu;
    public static JScrollPane sp;
    public static int teclatFil,teclatCol;
    public static JLabel[][] jl = new JLabel[teclatFil][teclatCol];
    public static int longText=5;//logitud maxima de les cadenes que es mostren al teclat de simbols
    public static boolean back=false;
    static JMenuItem[][]  jmi_ajunta,jmi_treuFil,jmi_treuCol,jmi_insertaFil,jmi_insertaCol,jmi_reduirText,jmi_augmentarText,jmi_reinici,jmi_desactivarTeclat;
    public teclat() {
        if(matriu==null){
                teclatFil=6;teclatCol=12;
                matriu=new String[teclatFil][teclatCol];
                jl = new JLabel[teclatFil][teclatCol];
                reinici();
        }
        Border bor = BorderFactory.createLineBorder(Color.lightGray, 1);
        jmi_ajunta=new  JMenuItem[jl.length][jl[0].length];
        jmi_treuFil=new  JMenuItem[jl.length][jl[0].length];
        jmi_treuCol=new  JMenuItem[jl.length][jl[0].length];
        jmi_insertaFil=new  JMenuItem[jl.length][jl[0].length];
        jmi_insertaCol=new  JMenuItem[jl.length][jl[0].length];
        jmi_reduirText=new  JMenuItem[jl.length][jl[0].length];
        jmi_augmentarText=new  JMenuItem[jl.length][jl[0].length];
        jmi_desactivarTeclat=new  JMenuItem[jl.length][jl[0].length];
        jmi_reinici=new  JMenuItem[jl.length][jl[0].length];
        for(int i = 0; i < jl.length; i++) {for(int j = 0; j < jl[0].length; j++) {
            jl[i][j] = new JLabel();
            jl[i][j].removeAll();
            if(matriu[i][j]!=null){
                if(matriu[i][j].length()>longText)jl[i][j].setText(matriu[i][j].substring(0,longText));
                else jl[i][j].setText(matriu[i][j]);
            }
            else {
                matriu[i][j]="";
                jl[i][j].setText("");
            }
            jl[i][j].setFont(new Font(font.tipus,font.estil,font.tamany));
            jl[i][j].setForeground(Color.blue);
            jl[i][j].setBorder(bor);
            jl[i][j].setHorizontalAlignment(SwingConstants.CENTER);
        }}
        for(int k = 0; k < jl.length; k++) {for(int l = 0; l < jl[0].length; l++) {
        mouse(k,l);}}
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(jl.length, jl[0].length, 0, 0));
        for (int i = 0; i < jl.length; i++)for (int j = 0; j< jl[0].length; j++){
            p.add(jl[i][j]);
        }
        p.setBorder(BorderFactory.createEmptyBorder(0,0,8,0));
        JPanel p2 = new JPanel();
        p2.setLayout(new BorderLayout());
        p2.add(p);
        p2.add(Func.panJP, BorderLayout.SOUTH);
        sp = new JScrollPane(p2);
  }
  public static void reinici(){
        for(int i=0;i<10;i++)matriu[0][i]=""+i;
        matriu[0][10]=",";
        matriu[0][11]=".";
        matriu[1][0]="+";
        matriu[1][1]="-";
        matriu[1][2]="*";
        matriu[1][3]="/";
        matriu[1][4]="^";
        matriu[1][5]="(";
        matriu[1][6]=")";
        matriu[1][7]="=";
        matriu[1][8]="<";
        matriu[1][9]=">";
        matriu[1][10]=splitPan.menor;;
        matriu[1][11]=splitPan.major;;
        for(int i=0;i<matriu[0].length;i++)matriu[4][i]=Func.funcions[i]+"(";
        for(int i=0;i<Func.funcions.length-matriu[0].length;i++)matriu[5][i]=Func.funcions[i+matriu[0].length]+"(";
        for(int i=Func.funcions.length-matriu[0].length;i<matriu[0].length-1;i++)matriu[5][i]="";
        matriu[5][7]=Func.rB.getString("integral");
        matriu[5][8]=Func.rB.getString("increment1");
        matriu[5][9]=Func.rB.getString("derivada");
        matriu[5][10]=Func.rB.getString("aproximat");
        matriu[5][11]=Func.rB.getString("sim0");
        matriu[3][0]=Func.sumat;
        matriu[3][1]=Func.rB.getString("simbolfuncio");
        matriu[3][2]=Func.increm;
        matriu[3][3]=Func.rB.getString("productori");
        matriu[3][4]=Func.rB.getString("noigual");
        matriu[3][5]=Func.rB.getString("grec0");
        matriu[3][6]=Func.rB.getString("grec1");
        matriu[3][7]=Func.rB.getString("grec2");
        matriu[3][8]=Func.rB.getString("grec3");
        matriu[3][9]=Func.rB.getString("grec4");
        matriu[3][10]=Func.rB.getString("grec5");
        matriu[3][11]=Func.rB.getString("grec6");
        matriu[2][0]="x";
        matriu[2][1]="y";
        matriu[2][2]="z";
        matriu[2][3]="a";
        matriu[2][4]="b";
        matriu[2][5]="c";
        matriu[2][6]="d";
        matriu[2][7]="_";
        matriu[2][8]=Func.rB.getString("grecpi");
        matriu[2][9]="3.141592653589793";
        matriu[2][10]="e";
        matriu[2][11]="2.718281828459045";
  }
  private static void longtext(){
      for(int i = 0; i < jl.length; i++) {for(int j = 0; j < jl[0].length; j++) {
            if(matriu[i][j].length()>longText)jl[i][j].setText(matriu[i][j].substring(0,longText));
            else jl[i][j].setText(matriu[i][j]); 
        }}
  }
    //public static void main(String args[]) {new teclat();}
    private void mouse(int i,int j){
        jl[i][j].addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent e) {
                    Toolkit.getDefaultToolkit().beep();
                    fil=i;col=j;
                    if(!SwingUtilities.isRightMouseButton(e)){//si no es el boto de la dreta
                        if(!teclatBol){
                            Func.analisiPreviBol=false;
                            tecla=jl[i][j].getText();
                            cercaT();
                            Func.clipboard c = new Func.clipboard();
                            c.copiaClip(tecla);
                        }
                    }
                }
                public void mousePressed(MouseEvent e) { fil=i;col=j;}
                public void mouseReleased(MouseEvent e) {}public void mouseEntered(MouseEvent e) {}public void mouseExited(MouseEvent e) {}
            });
        jl[i][j].addMouseListener(createPopupMenu(i,j));
    }
    public MouseListener createPopupMenu(int i,int j) {
        JMenuItem menuItem;
        JPopupMenu popup = new JPopupMenu();
        jmi_ajunta[i][j] = new JMenuItem("ajuntar a cel.la");jmi_ajunta[i][j].setIcon(Func.Engan());
        jmi_ajunta[i][j].addActionListener(this);
        popup.add(jmi_ajunta[i][j]);
        popup.addSeparator();
         jmi_reinici[i][j] = new JMenuItem("reiniciar teclat");
         jmi_reinici[i][j].addActionListener(this);
        popup.add( jmi_reinici[i][j]);
        jmi_insertaFil[i][j] = new JMenuItem("incrementar fileres");jmi_insertaFil[i][j].setIcon(Func.Novalinia());
        jmi_insertaFil[i][j].addActionListener(this);
        popup.add(jmi_insertaFil[i][j]);
        jmi_insertaCol[i][j] = new JMenuItem("incrementar columnes");jmi_insertaCol[i][j].setIcon(Func.Novacolumna());
        jmi_insertaCol[i][j].addActionListener(this);
        popup.add(jmi_insertaCol[i][j]);
        jmi_treuFil[i][j]= new JMenuItem("reduir fileres"); jmi_treuFil[i][j].setIcon(Func.Treulinia());
         jmi_treuFil[i][j].addActionListener(this);
        popup.add( jmi_treuFil[i][j]);
        jmi_treuCol[i][j] = new JMenuItem("reduir columnes");jmi_treuCol[i][j].setIcon(Func.Treucolumna());
        jmi_treuCol[i][j].addActionListener(this);
        popup.add( jmi_treuCol[i][j]);
        popup.addSeparator();
        jmi_reduirText[i][j] = new JMenuItem("reduir text");
        jmi_reduirText[i][j].addActionListener(this);
        popup.add(jmi_reduirText[i][j]);
         jmi_augmentarText[i][j] = new JMenuItem("augmentar text");
         jmi_augmentarText[i][j].addActionListener(this);
        popup.add(  jmi_augmentarText[i][j]);
        popup.addSeparator();
        jmi_desactivarTeclat[i][j] = new JMenuItem("desactivar teclat");
        jmi_desactivarTeclat[i][j].addActionListener(this);
        popup.add(jmi_desactivarTeclat[i][j]);
        MouseListener ml = new TaulesPopup(popup);
        return ml;
      }
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("ajuntar a cel.la".equals(e.getActionCommand())) {
            Func.clipboard c = new Func.clipboard();
            String s="";
            try { s = c.ajuntarClip(); } catch (Exception ex) {return; }
            matriu[fil][col]=s;
            if(s.length()>longText)s=s.substring(0,longText);
            try { jl[fil][col].setText(s); } catch (Exception ex) { }
            Func.tooltip(true,fil,col);
            return;//log(
        }
        if ("reiniciar teclat".equals(e.getActionCommand())) {
            teclatFil=6;teclatCol=12;
            matriu=new String[teclatFil][teclatCol];
            jl = new JLabel[teclatFil][teclatCol];
            reinici();
            Func.funcImpl.reiniciparcial();
          return;//log(
        }
        if ("reduir fileres".equals(e.getActionCommand())) {
            if (teclatFil>1)teclatFil--;
            else return;
            jl = new JLabel[teclatFil][teclatCol];
            String[][] m=new String[teclatFil][teclatCol];
            for(int i = 0; i < fil; i++) System.arraycopy(matriu[i], 0, m[i], 0, teclatCol);
            for(int i = fil+1; i <= teclatFil; i++) System.arraycopy(matriu[i], 0, m[i-1], 0, teclatCol);
            matriu=new String[teclatFil][teclatCol];
            for(int i = 0; i < teclatFil; i++) System.arraycopy(m[i], 0, matriu[i], 0, teclatCol);
            Func.funcImpl.reiniciparcial();
          return;//log(
        }
        if ("incrementar fileres".equals(e.getActionCommand())) {
            if (teclatFil<12)teclatFil++;
            else return;
            jl = new JLabel[teclatFil][teclatCol];
            String[][] m=new String[teclatFil][teclatCol];
            for(int i = 0; i < fil; i++) System.arraycopy(matriu[i], 0, m[i], 0, teclatCol);
            for(int i = fil; i < teclatFil-1; i++) System.arraycopy(matriu[i], 0, m[i+1], 0, teclatCol);
            for(int i = 0; i < teclatCol; i++)m[fil][i]="";
            matriu=new String[teclatFil][teclatCol];
            for(int i = 0; i < m.length; i++) System.arraycopy(m[i], 0, matriu[i], 0, teclatCol);
            Func.funcImpl.reiniciparcial();
          return;//log(
        }
        if ("reduir columnes".equals(e.getActionCommand())) {
            if (teclatCol>3)teclatCol--;
            else return;
            jl = new JLabel[teclatFil][teclatCol];
            String[][] m=new String[teclatFil][teclatCol];
            for(int i = 0; i < teclatFil; i++) {
                for(int j = 0; j < col; j++) m[i][j]=matriu[i][j];
                for(int j = col+1; j <= teclatCol; j++) m[i][j-1]=matriu[i][j];
            }
            matriu=new String[teclatFil][teclatCol];
            for(int i = 0; i < m.length; i++) System.arraycopy(m[i], 0, matriu[i], 0, teclatCol);
            Func.funcImpl.reiniciparcial();
          return;//log(
        }
        if ("incrementar columnes".equals(e.getActionCommand())) {
            if (teclatCol<20)teclatCol++;
            else return;
            jl = new JLabel[teclatFil][teclatCol];
            String[][] m=new String[teclatFil][teclatCol];
            for(int i = 0; i < teclatFil; i++) {
                m[i][col]="";
                for(int j = 0; j < col; j++) m[i][j]=matriu[i][j];
                for(int j = col+1; j < teclatCol; j++) m[i][j]=matriu[i][j-1];
            }
            matriu=new String[teclatFil][teclatCol];
            for(int i = 0; i < teclatFil; i++) System.arraycopy(m[i], 0, matriu[i], 0, teclatCol);
            Func.funcImpl.reiniciparcial();
          return;//log(
        }
        if ("reduir text".equals(e.getActionCommand())) {
            if (longText>1)longText--;
            longtext();
          return;
        }
        if ("augmentar text".equals(e.getActionCommand())) {
           if (longText<8)longText++;
            longtext();
          return;
        }
        if ("desactivar teclat".equals(e.getActionCommand())) {
            Func.mostrarTeclat=false;Func.funcImpl.reiniciparcial();
            return;
        }
    }
    public static void font(){
        for(int i = 0; i < jl.length; i++)for(int j = 0; j < jl[0].length; j++){
            jl[i][j].setFont(new Font(font.tipus,font.estil,font.tamany));
        }
        Font fo = new Font(font.tipus, font.estil, font.tamany);
         for(int i = 0; i < jl.length; i++) for(int j = 0; j < jl[0].length; j++) {
            jmi_ajunta[i][j].setFont(fo);
            jmi_treuFil[i][j].setFont(fo);
            jmi_treuCol[i][j].setFont(fo);
            jmi_insertaFil[i][j].setFont(fo);
            jmi_insertaCol[i][j].setFont(fo);
            jmi_reinici[i][j].setFont(fo);
            jmi_reduirText[i][j].setFont(fo);
            jmi_augmentarText[i][j].setFont(fo);
            jmi_desactivarTeclat[i][j].setFont(fo);
         }
    }
    public void cercaT(){
        String s,s1;
        Component k=KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        try{s=k.toString();}catch (Exception e){s="";}
        try{s1=k.getParent().toString();}catch (Exception e){s1="";}
        if(!s.equals("")||!s1.equals("")){
            if(s.startsWith("javax.swing.JTextField")&&s1.startsWith("javax.swing.JViewport")){
                teclatBol=true;
                int i0=Func.JFuncio.getSelectionStart();
                int i1=Func.JFuncio.getSelectionEnd();
                s=Func.JFuncio.getText();
                int i2=i0;
                String s2="";
                if(back){
                    if(i0>0){
                        if(i0!=i1)i2=i0;
                        else i2=i0-1;
                    }
                }
                else s2=matriu[fil][col];
                s=s.substring(0,i2)+s2+s.substring(i1);
                Func.JFuncio.setText(s);
                Func.JFuncio.setCaretPosition(i2+s2.length());
                teclatBol=false;
                return;
            }
            else if(s.startsWith("javax.swing.JTextPane")){
                teclatBol=true;
                int i0=Func.textDeco[Func.saltBol].getSelectionStart();
                int i1=Func.textDeco[Func.saltBol].getSelectionEnd();
                int i2=i1-i0;
                Color c;
                SimpleAttributeSet atribut = new SimpleAttributeSet();
                try{
                    Element elem=((StyledDocument)Func.textDeco[Func.saltBol].getDocument()).getCharacterElement(i0);
                    c=StyleConstants.getForeground(elem.getAttributes());
                }
                catch(Exception e){return;}
                StyleConstants.setForeground(atribut, c);
                if(back){
                    if(i0>0){
                         if(i2>0){
                             try{Func.textDeco[Func.saltBol].getDocument().remove(i0, i2);}
                            catch(Exception e){}
                         }
                        else {
                             try{Func.textDeco[Func.saltBol].getDocument().remove(i0-1, 1);}
                            catch(Exception e){}
                         }
                    }
                }
                else {
                    try{
                        Func.textDeco[Func.saltBol].getDocument().remove(i0, i2);
                        Func.textDeco[Func.saltBol].getDocument().insertString(i0, matriu[fil][col], atribut);
                    }
                    catch(Exception e){}
                }
                teclatBol=false;
                return;
            }
            else if(s.startsWith("javax.swing.JTextArea")){
                teclatBol=true;
                int i0=Func.textDeco1.getSelectionStart();
                int i1=Func.textDeco1.getSelectionEnd();
                s=Func.textDeco1.getText();
                int i2=i0;
                String s2="";
                if(back){
                    if(i0>0){
                        if(i0!=i1)i2=i0;
                        else i2=i0-1;
                    }
                }
                else s2=matriu[fil][col];
                s=s.substring(0,i2)+s2+s.substring(i1);
                Func.textDeco1.setText(s);
                Func.textDeco1.setCaretPosition(i2+s2.length());
                teclatBol=false;
                return;
            }
             else if(s.startsWith("Calc.taulaF")||s1.startsWith("Calc.taulaF")){
                teclatBol=true;
                s=taulaF.cela.getText();
                int i0=taulaF.cela.getSelectionStart();
                int i1=taulaF.cela.getSelectionEnd();
                int i2=i0;
                String s2="";
                if(back){
                    if(i0>0){
                        if(i0!=i1)i2=i0;
                        else i2=i0-1;
                    }
                }
                else s2=matriu[fil][col];
                s=s.substring(0,i2)+s2+s.substring(i1);
                taulaF.cela.setText(s);
                taulaF.cela.setCaretPosition(i2+s2.length());
                teclatBol=false;
                return;
            } 
            else if(s.startsWith("Calc.taulaV")||s1.startsWith("Calc.taulaV")){
                teclatBol=true;
                s=taulaV.cela.getText();
                int i0=taulaV.cela.getSelectionStart();
                int i1=taulaV.cela.getSelectionEnd();
                int i2=i0;
                String s2="";
                if(back){
                    if(i0>0){
                        if(i0!=i1)i2=i0;
                        else i2=i0-1;
                    }
                }
                else s2=matriu[fil][col];
                s=s.substring(0,i2)+s2+s.substring(i1);
                taulaV.cela.setText(s);
                taulaV.cela.setCaretPosition(i2+s2.length());
                teclatBol=false;
                return;
            } 
            else if(s.startsWith("Calc.taulaP")||s1.startsWith("Calc.taulaP")){
                teclatBol=true;
                s=taulaP.cela.getText();
                int i0=taulaP.cela.getSelectionStart();
                int i1=taulaP.cela.getSelectionEnd();
                int i2=i0;
                String s2="";
                if(back){
                    if(i0>0){
                        if(i0!=i1)i2=i0;
                        else i2=i0-1;
                    }
                }
                else s2=matriu[fil][col];
                s=s.substring(0,i2)+s2+s.substring(i1);
                taulaP.cela.setText(s);
                taulaP.cela.setCaretPosition(i2+s2.length());
                teclatBol=false;
                return;
            }
            else if(s.startsWith("Calc.taulaC")||s1.startsWith("Calc.taulaC")){
                teclatBol=true;
                s=taulaC.cela.getText();
                int i0=taulaC.cela.getSelectionStart();
                int i1=taulaC.cela.getSelectionEnd();
                int i2=i0;
                String s2="";
                if(back){
                    if(i0>0){
                        if(i0!=i1)i2=i0;
                        else i2=i0-1;
                    }
                }
                else s2=matriu[fil][col];
                s=s.substring(0,i2)+s2+s.substring(i1);
                taulaC.cela.setText(s);
                taulaC.cela.setCaretPosition(i2+s2.length());
                teclatBol=false;
                return;
            } 
            else if(s.startsWith("Calc.taulaI$")||s1.startsWith("Calc.taulaI$")){
                teclatBol=true;
                s=taulaI.cela.getText();
                int i0=taulaI.cela.getSelectionStart();
                int i1=taulaI.cela.getSelectionEnd();
                int i2=i0;
                String s2="";
                if(back){
                    if(i0>0){
                        if(i0!=i1)i2=i0;
                        else i2=i0-1;
                    }
                }
                else s2=matriu[fil][col];
                s=s.substring(0,i2)+s2+s.substring(i1);
                taulaI.cela.setText(s);
                taulaI.cela.setCaretPosition(i2+s2.length());
                teclatBol=false;
                return;
            }
            else if(s.startsWith("Calc.taulaD$")||s1.startsWith("Calc.taulaD$")){
                teclatBol=true;
                s=taulaD.cela.getText();
                int i0=taulaD.cela.getSelectionStart();
                int i1=taulaD.cela.getSelectionEnd();
                int i2=i0;
                String s2="";
                if(back){
                    if(i0>0){
                        if(i0!=i1)i2=i0;
                        else i2=i0-1;
                    }
                }
                else s2=matriu[fil][col];
                s=s.substring(0,i2)+s2+s.substring(i1);
                taulaD.cela.setText(s);
                taulaD.cela.setCaretPosition(i2+s2.length());
                teclatBol=false;
            }
        }
    }
}