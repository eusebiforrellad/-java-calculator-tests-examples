package Calc;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JFrame;
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
import static java.lang.Double.NaN;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
public class taulaV extends JPanel implements ActionListener{
    private static final long serialVersionUID = 1L;
    public static JTable tl;
    public static String [][] matriu;//la matriu variables mante la mateixa configuracio que la taula i inclou les capçaleres
    public static String [][] matriuSup;
    public static int filValSelec;
    public static boolean hihaVar=false;
    public static String[] varstaulaV;//  simbols de les variables totes en el mateix ordre que la taulas
    public static String[] varsOrdenats;//simbols de totes les variables  en el  ordre cadenes mes llarges primer
    public static String[] varsGenOrdenats;//simbols de totes les variables  en el  ordre cadenes mes llarges primer
    public static String[] varsGenerals;//simbols de totes les variables generals
    public static int idxVarGen[];//aqui l'index de variables generals esta ordenat de mes a menys per cadenes llargues curtes
    public static JTextField  cela;
    static int fil_tl;
    static int col_tl;
    JMenuItem menuItemFil;
    public static JCheckBoxMenuItem cbM = new JCheckBoxMenuItem("scroll taula");
    public static int scrollCols;
    static JMenuItem  jmi_copiSel, jmi_ajuntaSel, jmi_copi,jmi_cut,jmi_ajunta,jmi_carregar,jmi_save,jmi_treuFil,jmi_insertaFil,jmi_saveAs,jmi_reinici,jmi_scroll;
    static JTableHeader JThd;
    public taulaV() {
        super(new GridLayout(1,0));
        inici();
    }
    public taulaV(boolean x){}
    public void INI(){
        cela = new JTextField();
        cela.setBorder(Func.border);
        cela.addKeyListener(new KeyAdapter() {
           public void keyTyped(KeyEvent e) {
               Func.analisiPreviBol=false;
           }
        });
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
                try {if(Func.tooltiptxt)tip = getValueAt(rowIndex, colIndex).toString();} catch (RuntimeException e1) {}
                return tip;
            }
        };
        tl.setPreferredScrollableViewportSize(new Dimension(300, 80));
        JScrollPane scrollPane = new JScrollPane(tl);
        add(scrollPane);
        setOpaque(true);
        createPopupMenu();
        tl.setRowSelectionAllowed(true);
        tl.setColumnSelectionAllowed(true);
        tl.getTableHeader().setReorderingAllowed(false);
        tl.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if(!Func.analisiPreviBol)return;
                fil_tl = tl.rowAtPoint(e.getPoint());
                col_tl = tl.columnAtPoint(e.getPoint());
                if(!taulaI.hihaDades){
                    try{
                        if(Cur.unaVarBol||Cal.longi==1){
                            int[] i=tl.getSelectedRows();
                            filValSelec=i[0];
                            if(taulaV.varstaulaV.length>0){
                                if(i[0]<taulaV.varstaulaV.length){
                                    if(Cur.paraCalcB)return;Cur.paraCalcBol=true;Cur.paraCalcB=true;Cur.curs.Threadenable();
                                    Cur.idxGraf=i[0];
                                    Cur.var_but.setText("var: "+taulaV.varstaulaV[Cur.idxGraf]);
                                    Cur.Colors2(Cur.idxGraf);
                                    Cur.trueFalse();
                                    Cur.calculDiagonalGrafic(true);
                                    Cur.paraCalcBol=false;
                                }
                            }
                        }
                    }
                    catch(Exception e1){}
                }
            }
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {
                String s=tl.getValueAt(fil_tl, col_tl).toString();
            if(!s.equals("")){
                    Func.minim.setEnabled(true);
                    Func.minimT.setEnabled(true);
                    Func.minimBol=true;
                }
                else {
                    Func.minim.setEnabled(false);
                    Func.minimT.setEnabled(false);
                    Func.minimBol=false;
            }}
            public void mousePressed(MouseEvent e) {}public void mouseReleased(MouseEvent e) {}
    });
    tl.setAutoResizeMode(scrollCols);
    tl.putClientProperty("terminateEditOnFocusLost", true);
    color();
    editaCela();
    }
    private void editaCela() {
        TableColumnModel t = tl.getColumnModel();
        for(int i=0;i<matriu[0].length;i++){t.getColumn(i).setCellEditor(new DefaultCellEditor(cela));} 
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
        jmi_insertaFil = new JMenuItem("inserta filera");jmi_insertaFil.setIcon(Func.Novalinia());
        jmi_insertaFil.addActionListener(this);
        popup.add(jmi_insertaFil);
        jmi_treuFil = new JMenuItem("treu filera");jmi_treuFil.setIcon(Func.Treulinia());
        jmi_treuFil.addActionListener(this);
        popup.add(jmi_treuFil);
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
        popup.add(cbM);if(scrollCols==0){cbM.setState(true);}else{cbM.setState(false);}
        MouseListener ml = new TaulesPopup(popup);
        tl.addMouseListener(ml);
    }
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        String s = source.getText();
        if(s.equals("inserta filera")){if(contingutTaula(tl.getSelectedRows(),true)){nouModel();}}
        if(s.equals("treu filera")){if(contingutTaula(tl.getSelectedRows(),false)){nouModel();}}
        if ("copiar".equals(e.getActionCommand())) {splitPan.cop(tl,matriu,false);}
        if ("cut".equals(e.getActionCommand())) {splitPan.cop(tl,matriu,true);}
        if(s.equals("ajuntar a cel.la")){
            String[][] m=splitPan.paste();
            int row=tl.getSelectedRow();int col=tl.getSelectedColumn();
            boolean b=false;
            if(matriu[0].length<col+m[0].length){Func.append(1,"error: ");Func.append(0,"el nombre de columnes de la taulaV es fixe la copia implica un increment del nombre de columnes;"+ splitPan.FIL);return;}
            if(matriu.length<row+m.length+1){
                b=true;
                matriuSup=splitPan.copiaMatriu(matriu);
                matriu=new String[row+m.length+1][matriu[0].length];
                for(int i=0;i<matriuSup.length;i++)for(int j=0;j<matriuSup[0].length;j++)matriu[i][j]=matriuSup[i][j];
                for(int i=matriuSup.length;i<matriu.length;i++)for(int j=0;j<matriu[0].length;j++)matriu[i][j]="";
            }
            capsalInteg();
            if(b){nouModel();}
            for(int i=0;i<m.length;i++)for(int j=0;j<m[0].length;j++)tl.setValueAt(m[i][j], row+i,col+j);
            capsalInteg();
            return;
        }
        if ("copia sel.".equals(e.getActionCommand())) {splitPan.cop_sel(cela);return;}
        if(s.equals("ajunta sel.")){splitPan.paste_sel(cela);}
        if(s.equals("guardar dades")){splitPan.guardarArx();}
        if(s.equals("guardar (save as)")){splitPan.guardarArxCom();}
        if(s.equals("carregar dades")){splitPan.carregaArx();}
        if (s.equals("reiniciar taula")) {reinici();}
    }
    public static void font(){
        Font fo = new Font(font.tipus, font.estil, font.tamany);
        cbM.setFont(fo);
        jmi_copiSel.setFont(fo);
        jmi_ajuntaSel.setFont(fo);
        jmi_copi.setFont(fo);
        jmi_cut.setFont(fo);
        jmi_ajunta.setFont(fo);
        jmi_treuFil.setFont(fo);
        jmi_insertaFil.setFont(fo);
        jmi_reinici.setFont(fo);
        jmi_carregar.setFont(fo);
        jmi_save.setFont(fo);
        jmi_saveAs.setFont(fo);
        JThd.setFont(fo);
    }
    public void reinici(){ matriu=null;valorsInicials();nouModel();}
    public boolean contingutTaula( int[] fil,boolean bol){
        int cc=tl.getColumnCount();
    	int cf=tl.getRowCount()+1;
        matriuSup=splitPan.copiaMatriu(matriu);
    	if(bol){
            int fi;
            try{ fi=fil[0]+1;}
            catch(Exception e){fi=cf-1;}
            if(fi>=0){//inserta filera
                matriu=new String [cf+1][cc];
                for(int j=0;j<cc;j++){
                    for(int i=0;i<fi+1;i++){
                        try{matriu[i][j]=matriuSup[i][j];}
                        catch(java.lang.NullPointerException e){return false;}
                    }
                    matriu[fi+1][j]="";
                    for(int i=fi+1;i<cf;i++) matriu[i+1][j]=matriuSup[i][j];
                }
            }
    	}
    	else{
            if(fil.length==0){Func.append(1,"info: ");Func.append(0,"per eliminar una filera cal seleccionar-la"+ splitPan.FIL);return false;}
            int fi=fil[0]+1;
            int lo=fil.length;
            matriu=new String [cf-lo][cc];
            for(int j=0;j<cc;j++){
                for(int i=1;i<fi;i++){matriu[i][j]=matriuSup[i][j];}
                for(int i=fi;i<cf-lo;i++){matriu[i][j]=matriuSup[i+lo][j];}
                }
            if(matriu.length<2){
                matriu=new String [2][cc];for(int j=0;j<cc;j++)matriu[1][j]="";
            }
            capsalInteg(); 
    	}
        return true;
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
    public boolean matriuTaula(){
        boolean correcte=true;
        int cc=matriu[0].length;
        int cf=matriu.length;
        hihaVar=false;
        idxVarGen=new int[0];
        varsGenOrdenats=new String[0];
        varsGenerals=new String[0];
        Cal.idxVarGen=new int[0];
        suportID.idxVarGenIntegr=new int[0];
        varstaulaV=new String[0];
        varsOrdenats=new String[0];
        Cal.dMatriuVar=new double[0][0];
        Cal.idxParVar=new int[0];
        Cal.longi=0;
        Cal.indexsParNoVar=new int[0][0];
        for(int i=1;i<cf;i++)for(int j=0;j<cc;j++)matriu[i][j]=splitPan.treuBlancs(matriu[i][j]);
        if(cf>2){
            if(moufilBuides()){
                Func.append(1,"info: ");
                Func.append(0,"a taula variables s'han eliminat fileres buides"+ splitPan.FIL);
                cc=tl.getColumnCount();
                cf=tl.getRowCount()+1;
            }
        }
        int cont=3*(cf-1);
        for(int i=1;i<cf;i++)for(int j=0;j<cc;j++)if(!matriu[i][j].equals("")){hihaVar=true;cont--;}
        if(!hihaVar){
            return true;}
        if(cont!=0){
            Func.append(1,Func.error);
            if(cont==1){Func.append(0,"a taula variables hi ha una cel.la buida"+ splitPan.FIL);return false;}//la primera filera de dades es mante buida quant no hi ha variables per tant si cont =3 i cf==2 vol dir que la taulaV es buida i no hi ha dades
            else {if(!(cont==3&&cf==2)){Func.append(0,"a taula variables hi ha "+cont+" cel.les buides"+ splitPan.FIL);return false;}}
        }
        String s="";
        Double[][] Dmatriu=new Double[cf-1][cc-1];
        for(int i=1;i<cf;i++)for(int j=0;j<cc-1;j++){
            try{Dmatriu[i-1][j]=Double.parseDouble(splitPan.comApun(matriu[i][j+1]));}
            catch(java.lang.NumberFormatException e){
                Dmatriu[i-1][j]=null;
                correcte=false;
                int i0=i-1;int i1=j+1;
                s+="("+i0+";"+i1+"), ";
            }
        }
        if (!correcte){Func.append(1,Func.error);Func.append(0,"a taula variables hi ha l"+Func.rB.getString("i_")+"mits no num"+Func.rB.getString("_e")+"rics  (fil,col): "+s+ splitPan.FIL);return false;}
        for(int i=1;i<cf;i++)if(Dmatriu[i-1][0]>Dmatriu[i-1][1]){//l'interval ha de complir valor cel.la esquerra<cel.la dreta
            s=matriu[i][2];
            matriu[i][2]=matriu[i][1];
            matriu[i][1]=s;
            tl.setValueAt(matriu[i][1], i-1, 1);
            tl.setValueAt(matriu[i][2], i-1, 2);
        }// comproba que a variables simbols son simbols i no es confonen amb valors numerics, funcions operadors etc
        varstaulaV=new String[matriu.length-1];
        for (int i = 1; i < matriu.length; i++){
            int k=i-1;
            varstaulaV[k]=matriu[i][0];
            if(!splitPan.esSimVar(varstaulaV[k]).equals("")) {Func.append(1,Func.error);Func.append(0,Func.rB.getString("variablesSim")+k+" simbol: "+varstaulaV[k]+splitPan.FIL);return false;}
        }
        for(int i=0;i<varstaulaV.length;i++)for(int j=i+1;j<varstaulaV.length;j++)if(varstaulaV[i].equals(varstaulaV[j])){Func.append(1,Func.error);Func.append(0,"a taula variables: s"+Func.rB.getString("i_")+"mbol repetit: "+taulaV.varstaulaV[i]+splitPan.FIL);return false;}
        varsOrdenats=new String[varstaulaV.length];
        System.arraycopy(varstaulaV,0,varsOrdenats,0,varstaulaV.length);
        Func.ordenaMatriu(varsOrdenats);
        if(hihaVar)Cal.longi=taulaV.varstaulaV.length;
        Cal.dMatriuVar=new double[Cal.longi][10];
        Cur.cursorCentreGrafic=new long[Cal.longi];
        Cur.cursorMinimGraf=new long[Cal.longi];
        Cur.cursor=new long[Cal.longi];Cal.cursor=new long[Cal.longi];
        Cur.cursorMinim=new long[Cal.longi];Cal.cursorMinim=new long[Cal.longi];
        Cur.cursorFixa=new long[Cal.longi];
        Cur.minIncrSignif=new long[Cal.longi];Cal.minIncrSignif=new long[Cal.longi];//taulaD.minIncrSignif=new long[Cal.longi];
        Cur.pondIncrVar=new double[Cal.longi];Cal.pondIncrVar=new double[Cal.longi];
        Cur.varAct=new double[Cal.longi];Cal.varAct=new double[Cal.longi];
        Cur.varCopia=new double[Cal.longi];Cal.varCopia=new double[Cal.longi];
        Cur.variableFixa=new double[Cal.longi];Cur.variableFixaBol=new boolean[Cal.longi];
        Cur.limSupCalc=new int[Cal.longi];Cal.limSupCalc=new int[Cal.longi];
        Cur.cursorsCanviSentit=false;
        //matriu dMatriuVar[][x]
        //x=0: limit inferior variable; x=1: limit superior; x=2: diferencia de limits; x=3 log del limit inferior
        //x=4: log limit superior o cero funcional positiu o negatiu (mi, taulaC.ceroLog)
        //x=5: interval entre log dels (limits inf i sup,  o limit inferior i cero funcional)
        //x=6: 0 o interval entre cero funcional i limit superior
        //x=7: =0 o interval linial;x=8: =1 o interval linial;x=9: =2 o interval linial
        for(int i=0;i<Cal.longi;i++){
                for(int j=0;j<2;j++){
                       try {Cal.dMatriuVar[i][j]=Double.parseDouble(splitPan.comApun(matriu[i+1][j+1]));}
                       catch(NumberFormatException e){Cal.dMatriuVar[i][j]=NaN;}
                }
                Cal.dMatriuVar[i][2]=Cal.dMatriuVar[i][1]-Cal.dMatriuVar[i][0];
                if(Cal.dMatriuVar[i][2]==0){
                    Func.append(0,"els l"+Func.rB.getString("i_")+"mits m"+Func.rB.getString("i_")+"nim i m"+Func.rB.getString("a")+"xim de: "+taulaV.varstaulaV[i]+" son el mateix, no es tracta d'una variable"+ splitPan.FIL);}
        }
        for(int i=0;i<Cal.longi;i++){
            Cal.dMatriuVar[i][7]=Cal.dMatriuVar[i][0];
            Cal.dMatriuVar[i][8]=Cal.dMatriuVar[i][1];
            Cal.dMatriuVar[i][9]=Cal.dMatriuVar[i][2];
        }
        Cur.indexAmp=0;
        Cur.mitatPuntsGraf[0]=2;
        for(int j=1;j<Cur.mitatPuntsGraf.length;j++)Cur.mitatPuntsGraf[j]=2*Cur.mitatPuntsGraf[j-1];
        for(int j=0;j<Cur.mitatPuntsGraf.length;j++)Cur.puntsGraf[j]=2*Cur.mitatPuntsGraf[j]+1;
        Cal.segment=(long)(Math.pow(Cur.mitatPuntsGraf[0],62));
        Cur.incCur=new long[Cur.mitatPuntsGraf.length][62];
        Cur.limCur=new int[Cur.incCur.length];
        Cur.limCur[0]=Cur.incCur[0].length-4;
        for(int i=1;i<Cur.incCur.length;i++){Cur.limCur[i]=Cur.limCur[0]-i;}//-3;}
        for(int i=0;i<Cur.incCur.length;i++){Cur.incCur[i][0]=Cal.segment/(2*Cur.mitatPuntsGraf[i]);}
        for(int i=0;i<Cur.incCur.length;i++)for(int j=1;j<Cur.limCur[i];j++)Cur.incCur[i][j]=Cur.incCur[i][j-1]/2;
        return true;
    }
    public boolean matriuTaulaFi(){//s'executa si hi ha variables //la execucio del procediment ha d'esser posterior a taulaP. matriuTaula()
        boolean correcte=true;//comprobar que els simbol de parametres variables tenen el seu equivalent a variables no hi ha cap variable de parametre que no estigui a variable
        int cont=0;
        if(taulaP.hihaParVar){
            for (int i = 0; i < taulaP.simPar.length; i++) {//simbols de parametres que no son de capçalera
                boolean b=false;
                for (int j = 1; j < matriu.length; j++) {//tots els simbols de variables
                    if(taulaP.simPar[i].equals(matriu[j][0])){j=matriu.length;b=true;}
                }
                if(!b){correcte=false;Func.append(1,Func.error);Func.append(0,"hi ha variables de la taula par"+Func.rB.getString("a")+"metres que no es troben a la taula variables ex: " +taulaP.simPar[i]+ splitPan.FIL);}
             }
            cont=0;
            int contP=0;
            for (int i = 0; i < varstaulaV.length; i++) {//variables generals i parametres variables
                boolean b=false;
                for (int j = 0; j < taulaP.simPar.length; j++)if(taulaP.simPar[j].equals(varstaulaV[i])){contP++;j=taulaP.simPar.length;b=true;}
                if(!b)cont++;
             }
            Cal.idxVarGen=new int[cont];//index de la variable generals dins la matriu variables
            Cal.idxParVar=new int[contP];//index dels parametres variables dins la matriu variables
            idxVarGen=new int[cont];//es fan per despres recuperar la mateixa posicio de la taula
            cont=0;
            contP=0;
            for (int i = 0; i < varstaulaV.length; i++) {
                boolean b=false;
                for (int j = 0; j < taulaP.simPar.length; j++)if(taulaP.simPar[j].equals(varstaulaV[i])){
                    Cal.idxParVar[contP]=i;
                    contP+=1;
                    j=taulaP.simPar.length;
                    b=true;
                }
                if(!b){Cal.idxVarGen[cont]=i;cont+=1;}
            }
            System.arraycopy(Cal.idxVarGen, 0, idxVarGen, 0, Cal.idxVarGen.length);
            Cal.vargOparv=new boolean[varstaulaV.length];
            for(int i=0;i<idxVarGen.length;i++)Cal.vargOparv[idxVarGen[i]]=true;
            Cal.idxresultatM=new int[Cal.longi];
            cont=0;
            
            for(int i=0;i<Cal.idxParVar.length;i++){
               Cal.idxresultatM[Cal.idxParVar[i]]=cont;
               cont++ ;
            } 
        }
        else {//si no hi ha parametres variables pero hi ha variables
            Cal.idxVarGen=new int[varstaulaV.length];//index de la variable dins la matriu variables
            idxVarGen=new int[varstaulaV.length];//es fan per despres recuperar la mateixa posicio de la taula
            for (int i = 0; i < varstaulaV.length; i++) {Cal.idxVarGen[i]=i;}
            System.arraycopy(Cal.idxVarGen, 0, idxVarGen, 0, Cal.idxVarGen.length);
            Cal.vargOparv=new boolean[idxVarGen.length];
            for(int i=0;i<Cal.longi;i++)for(int j=0;j<idxVarGen.length;j++)Cal.vargOparv[j]=true;
        }
        varsGenerals=new String[idxVarGen.length];for (int i = 0; i < idxVarGen.length; i++)varsGenerals[i]=varstaulaV[idxVarGen[i]];
        for (int i = 0; i < idxVarGen.length; i++)for (int j = i+1; j < idxVarGen.length; j++) {//ordena els index de les variables generals de mes llarc a mes curt
            if(varstaulaV[idxVarGen[i]].length()<varstaulaV[idxVarGen[j]].length()){
                int k=idxVarGen[i];
                idxVarGen[i]=idxVarGen[j];
                idxVarGen[j]=k;
            }
        }
        varsGenOrdenats=new String[idxVarGen.length];for (int i = 0; i < idxVarGen.length; i++)varsGenOrdenats[i]=varstaulaV[idxVarGen[i]];
        if(taulaP.hihaPar)for(int i=0;i<taulaP.simParCap.length;i++)for(int j=0;j<taulaV.varstaulaV.length;j++)if(taulaP.simParCap[i].equals(taulaV.varstaulaV[j])){Func.append(1,Func.error);Func.append(0,"a taula parametres (s"+Func.rB.getString("i_")+"mbol de cap"+Func.rB.getString("c_")+"alera) i taula variables:  mateix s"+Func.rB.getString("i_")+"mbol: "+taulaP.simParCap[i]+splitPan.FIL);return false;}
        Cur.resultatP=new double[Cal.idxParVar.length];Cal.resultatP=new double[Cal.idxParVar.length];
        return correcte;
    }
    private  boolean moufilBuides(){
       boolean correcte=false;
       for(int i=1;i<matriu.length;i++){
            boolean b=false;
            for(int j=0;j<matriu[0].length;j++)if(!matriu[i][j].equals(""))b=true;
            if(!b){//si b=false totes les cel.les de la filera estan buides
                correcte=true;
                tl.setRowSelectionInterval(i-1, i-1);
                menuItemFil.doClick();
                i--;
                }
            if(matriu.length<=2)i=matriu.length;
        }
        return correcte;
       }
    public  void nouModel(){
        Func.analisiPreviBol=false;
        int cc=tl.getColumnCount();
        int[] cols=new int[cc];
        for(int i=0;i<cc;i++)cols[i]=tl.getColumnModel().getColumn(i).getWidth();        
        tl.setModel(new model());
        for(int i=1;i<matriu.length;i++){
            for(int j=0;j<matriu[0].length;j++){tl.setValueAt((Object)matriu[i][j], i-1, j);}
        }
        int i=tl.getRowCount();
        for(int j=0;j<i;j++){
            tl.setRowHeight( j, 18);
        }
        for(i=0;i<cc;i++)tl.getColumnModel().getColumn(i).setPreferredWidth(cols[i]);
        color();
        splitPan.tTV.repaint();
        editaCela();
    }
     public void color(){
        Color gc=new Color(220,220,220);
        Color gmc=new Color(235,235,235);
        Color gmh=new Color(215,228,235);
        JThd = tl.getTableHeader();
        TableCellRenderer hr = tl.getTableHeader().getDefaultRenderer();
        JThd.setDefaultRenderer(new TableCellRenderer() {
            JLabel lb;
            @Override
            public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus,int row, int column) {
                    lb = (JLabel) hr.getTableCellRendererComponent(table, value, false, false, row, column);
                    for(int i=0;i<3;i++)if (column == i)lb.setBackground(gmh);
                return lb;
                }
        });
        for(int i=0;i<3;i++){
            tl.getColumn(tl.getColumnName(i)).setCellRenderer(new DefaultTableCellRenderer() {
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    DefaultTableCellRenderer r = (DefaultTableCellRenderer) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if(!isSelected){
                        if ((row % 2) != 0) r.setBackground(gc);
                        else r.setBackground(gmc);
                    } 
                    return r;
                }   
            });
        }
    }
    private static void capsalInteg(){matriu[0][0] = "Variables (s"+Func.rB.getString("i_")+"mbol)";matriu[0][1] = "l"+Func.rB.getString("i_")+"mit Inferior";matriu[0][2] = "l"+Func.rB.getString("i_")+"mit Superior";}
    public void valorsInicials(){
        if(matriu==null){reiniciParcial();}
    	else{capsalInteg();}
        hihaVar=false;
    }
    private void reiniciParcial(){
        matriu=new String[2][3];
        capsalInteg();
        matriu[1][0]="";
        matriu[1][1]="";
        matriu[1][2]="";
    }
    class model extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
        public int getColumnCount() {return matriu[0].length;}
        public int getRowCount() {return matriu.length-1;}
        public String getColumnName(int col) {return matriu[0][col]; }
        public Object getValueAt(int row, int col) {
            if(row<0||col<0)return matriu[1][0];
            try{return matriu[row+1][col];}
            catch(Exception e){return matriu[1][0];}
        }
        public Class getColumnClass(int c,int d) {return getValueAt(d, c).getClass();}
        public boolean isCellEditable(int row, int col) {if (col < 0) {return false;} else { return true; }}
        public void setValueAt(Object value, int row, int col) {
            matriu[row+1][col]=(String)value;
            fireTableCellUpdated(row, col);
        }
    }
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("TableFTFEditDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        taulaV newContentPane = new taulaV();
        newContentPane.setOpaque(true); 
        frame.setContentPane(newContentPane);
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