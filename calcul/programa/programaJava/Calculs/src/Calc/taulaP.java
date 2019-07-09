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
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
public class taulaP extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    static JTable tl;
    static String simbol = "s" + Func.rB.getString("i_") + "mbols:";
    static String valor = "1 valor:";
    JMenuItem menuItemFil;
    JMenuItem menuItemCol;
    public static String[][] matriu;
    public static String [][] matriuSup;
    public static boolean hihaPar=false;
    public static boolean hihaParVar=false;
    public static String[][] parametres;//el primer index son les columnes, el segon les fileres la primera columna i la primera filera de matriu  no son incloses nomes els parametres inclosa la capsalera
    public static Double[][] dparametres;//
    public static int[][] varPTV_varPTP;//taula parametres el segon index si 0 es la filera de la taula variables amb el parametre variable; si=1 es la filera de la taula parametres en que es troba la variable si 2 es la columna a taula parametres 
    public static String[] simPar;//simbols de parametres que no son de capçalera
    public static String[] simParCap;//simbols capçalera 
    public static String[] simParCapUnaFil;//simbols de capçalera que contenent un sol valor numeric
    public static double[] dsimParCapUnaFil;//valor numeric associat a simParCapUnaFil
    public static String[] simParCapNoVar;//simbols de capçalera amb valors numerics
    public static String[] simParCapVar;//simbols de capçalera que contenent variables
    boolean[][] simbNum;
    boolean[] filasimbVar;
    public static JCheckBoxMenuItem cbM = new JCheckBoxMenuItem("scroll taula");
    public static int scrollCols;
    public static JTextField cela;
    public static int[] longiP;
    static JMenuItem  jmi_copiSel, jmi_ajuntaSel, jmi_copi,jmi_cut, jmi_ajunta,jmi_carregar,jmi_save,jmi_treuFil,jmi_treuCol,jmi_insertaFil,jmi_insertaCol,jmi_saveAs,jmi_reinici,jmi_scroll;
    static JTableHeader JThd;
    public taulaP() {
        super(new GridLayout(1,0));
        inici();
    }
    public taulaP(boolean x){}
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
    private void inici(){
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
        tl.setRowSelectionAllowed(true);
        tl.setColumnSelectionAllowed(true);
        tl.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(tl);
        add(scrollPane);
        setOpaque(true);
        createPopupMenu();
        tl.setAutoResizeMode(scrollCols);
        tl.putClientProperty("terminateEditOnFocusLost", true);
        color();
        editaCela();
        ListSelectionModel rc = tl.getSelectionModel();
        rc.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) return;
                ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                    lsm.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

                }
        });
    }
    private void editaCela() {
        TableColumnModel t = tl.getColumnModel();
        for(int i=0;i<matriu[0].length;i++){
        t.getColumn(i).setCellEditor(new DefaultCellEditor(cela));
        }
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
        jmi_insertaCol = new JMenuItem("inserta columna");jmi_insertaCol.setIcon(Func.Novacolumna());
        jmi_insertaCol.addActionListener(this);
        popup.add(jmi_insertaCol);
        jmi_treuCol = new JMenuItem("treu columna");jmi_treuCol.setIcon(Func.Treucolumna());
        jmi_treuCol.addActionListener(this);
        popup.add(jmi_treuCol);
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
        JMenuItem source = (JMenuItem) (e.getSource());
        String s = source.getText();
        if(s.equals("inserta filera")){ contingutTaula(tl.getSelectedRows(),true);nouModel(true);}
        if(s.equals("treu filera")){ contingutTaula(tl.getSelectedRows(),false);nouModel(true);}
        if (s.equals("inserta columna")) {contingutTaulaCol(tl.getSelectedColumn(), true);nouModel(false);}
        if (s.equals("treu columna")) {contingutTaulaCol(tl.getSelectedColumn(), false);nouModel(false); }
        if (s.equals("guardar dades")) {splitPan.guardarArx();}
        if(s.equals("guardar (save as)")){splitPan.guardarArxCom();}
        if (s.equals("carregar dades")) {splitPan.carregaArx();}
        if ("copiar".equals(e.getActionCommand())) {splitPan.cop(tl,matriu,false);}
        if ("cut".equals(e.getActionCommand())) {splitPan.cop(tl,matriu,true);}
        if(s.equals("ajuntar a cel.la")){
            String[][] m=splitPan.paste();
            int row=tl.getSelectedRow();int col=tl.getSelectedColumn();
            boolean[] b=new boolean[2];
            if(matriu.length<row+m.length+1){
                b[0]=true;
                matriuSup=splitPan.copiaMatriu(matriu);
                matriu=new String[row+m.length+1][matriu[0].length];
                for(int i=0;i<matriuSup.length;i++)for(int j=1;j<matriuSup[0].length;j++)matriu[i][j]=matriuSup[i][j];
                for(int i=matriuSup.length;i<matriu.length;i++)for(int j=1;j<matriu[0].length;j++)matriu[i][j]="";
            }
            if(matriu[0].length<col+m[0].length){
                b[1]=true;
                matriuSup=splitPan.copiaMatriu(matriu);
                matriu=new String[matriu.length][col+m[0].length];
                for(int i=0;i<matriuSup.length;i++)for(int j=1;j<matriuSup[0].length;j++)matriu[i][j]=matriuSup[i][j];
                for(int i=0;i<matriu.length;i++)for(int j=matriuSup[0].length;j<matriu[0].length;j++)matriu[i][j]="";
            }
            capsalInteg();
            if(b[0]||b[1]){nouModel(true);}
            for(int i=0;i<m.length;i++)for(int j=0;j<m[0].length;j++)tl.setValueAt(m[i][j], row+i,col+j);
            capsalInteg();
            return;
        }
        if ("copia sel.".equals(e.getActionCommand())) {splitPan.cop_sel(cela);return;}
        if(s.equals("ajunta sel.")){splitPan.paste_sel(cela);}
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
        jmi_treuCol.setFont(fo);
        jmi_insertaFil.setFont(fo);
        jmi_insertaCol.setFont(fo);
        jmi_reinici.setFont(fo);
        jmi_carregar.setFont(fo);
        jmi_save.setFont(fo);
        jmi_saveAs.setFont(fo);
        JThd.setFont(fo);
  }
    public void reinici(){ matriu=null;valorsInicials();nouModel(false);}
    public void contingutTaulaCol(int col, boolean bol) {
        int cc = tl.getColumnCount();
        int cf = tl.getRowCount() + 1;
        matriuSup=splitPan.copiaMatriu(matriu);
        if (bol) {
            if (col >= 0) {//inserta columna
                matriu = new String[cf][cc + 1];
                for (int i = 1; i < cf; i++) {
                    for (int j = 0; j < col+1; j++) {matriu[i][j] =matriuSup[i][j];}
                    matriu[i][col+1] = "";
                    for (int j = col+2; j < matriu[0].length; j++) {matriu[i][j] = matriuSup[i][j-1];}
                }
                capsalInteg();
            }
        } 
        else {
            if (cc > 2) {//treu columna
                matriu = new String[cf][cc - 1];
                for (int i = 1; i < cf; i++) {
                    for (int j = 1; j < col; j++) {matriu[i][j] = matriuSup[i][j];}
                    if(col>0)for (int j = col; j < matriu[0].length; j++) { matriu[i][j] = matriuSup[i][j+1];}
                }
                capsalInteg();
            }
        }
        matriu[1][0] = simbol;
        matriu[2][0] = valor;
        for (int i = 3; i < cf; i++) {int k = i - 1;matriu[i][0] = "" + k;}
    }
    public void contingutTaula(int[] fil,boolean bol){
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
                    for(int i=0;i<fi+1;i++){matriu[i][j]=matriuSup[i][j];}
                    matriu[fi+1][j]="";
                    for(int i=fi+1;i<cf;i++){matriu[i+1][j]=matriuSup[i][j];}
                    }
            }
            capsalInteg();
    	}
    	else{
            if(fil.length==0){Func.append(1,"info: ");Func.append(0,"per eliminar una filera cal seleccionar-la"+ splitPan.FIL);return;}
            int fi=fil[0]+1;
            int lo=fil.length;
            matriu=new String [cf-lo][cc];
            for(int j=0;j<cc;j++){
                for(int i=1;i<fi;i++){matriu[i][j]=matriuSup[i][j];}
                for(int i=fi;i<cf-lo;i++){matriu[i][j]=matriuSup[i+lo][j];}
                }
            if(matriu.length<2){
                matriu=new String [3][cc];for(int j=0;j<cc;j++){matriu[1][j]="";matriu[2][j]="";}
            }
            else if(matriu.length<3){
                matriu=new String [3][cc];
                if(fi==2){for(int j=0;j<cc;j++){matriu[2][j]="";matriu[1][j]=matriuSup[1][j];}}
                else if(fi==1){for(int j=0;j<cc;j++){matriu[1][j]="";matriu[2][j]=matriuSup[2][j];}}
            }
            capsalInteg();
    	}
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
    private void matriusacero(){
        simParCapNoVar=new String [0];
        simParCapVar=new String [0];
        Cal.longiP=0;
        longiP=new int[0];
        parametres=new String[0][0];
        varPTV_varPTP=new int[0][0];
        simPar=new String [0];
        simParCap=new String [0];
        hihaParVar=false;
        splitPan.simBolPC1=new boolean [0];
        splitPan.simBolPC2=new boolean [0];
    }
    public  boolean matriuTaula() {//la execucio del procediment ha d'esser posterior a taulaV. matriuTaula()
        simParCapUnaFil=new String[0];
        dsimParCapUnaFil=new double[0];
        int cc=matriu[0].length;
        int cf=matriu.length;
        hihaPar=false;
        splitPan.simBolPC0=new boolean [0];
        matriusacero();
        for(int i=1;i<cf;i++)for(int j=1;j<cc;j++)matriu[i][j]=splitPan.treuBlancs(matriu[i][j]);
        boolean b0=false;
        if(cf>3)b0=moufilBuides();
        if(b0){
            Func.append(1,"info: ");Func.append(0,"a taula par"+Func.rB.getString("a")+"metres s'han eliminat fileres buides"+ splitPan.FIL);
            cf=tl.getRowCount()+1;
        }
        b0=false;
        if(cc>2)b0=mouColBuides();
        if(b0){
            Func.append(1,"info: ");Func.append(0,"a taula par"+Func.rB.getString("a")+"metres s'han eliminat columnes buides"+ splitPan.FIL);
            cc=tl.getColumnCount();
        }
        int cont=0;
        if(matriu[0].length<1||matriu.length-1<1)return true;
        parametres=new String[matriu[0].length-1][matriu.length-1];
        dparametres=new Double[matriu[0].length-1][matriu.length-1];
        for(int i=1;i<matriu.length;i++)for(int j=1;j<matriu[0].length;j++)parametres[j-1][i-1]=matriu[i][j];
        simbNum=new boolean[parametres.length][parametres[0].length];
        filasimbVar=new boolean[parametres.length];
        for(int i=0;i<simbNum.length;i++)for(int j=0;j<simbNum[0].length;j++)if(!parametres[i][j].equals("")){
            hihaPar=true;
            try{dparametres[i][j]=Double.parseDouble(splitPan.comApun(parametres[i][j]));parametres[i][j]=""+dparametres[i][j];simbNum[i][j]=true;}
            catch(Exception e){if(j>0){hihaParVar=true;filasimbVar[i]=true;cont++;}}
        }
        if(!hihaPar){
            if(!hihaidxArr())return false;
            matriusacero();
            if(cc>2||cf>3)reinici();
            return true; 
        }//si no hi ha parametres reduir el nombre de fileres buids a dos i el de columnes a 1

        for(int i=0;i<simbNum.length;i++){//si hi ha parametres les primeres fileres han de tenir contingut
            if(parametres[i][0].equals("")||parametres[i][1].equals("")){Func.append(1,Func.error);Func.append(0,"a taula parametres totes les cel.les de les dues primeres fileres, han de tenir contingut"+ splitPan.FIL);return false;}
        }
        simParCap=new String[parametres.length];
        for(int i=0;i<parametres.length;i++){simParCap[i]=parametres[i][0];}
        for(int i=0;i<simParCap.length-1;i++)for(int j=i+1;j<simParCap.length;j++)if(simParCap[i].equals(simParCap[j])){
            Func.append(1,Func.error);Func.append(0,"hi ha s"+Func.rB.getString("i_")+"mbols de cap"+Func.rB.getString("c_")+"alera  repetits a la taula par"+Func.rB.getString("a")+"metres"+ splitPan.FIL);return false;
        }
        String[] simP=new String [cont];//simbols no numerics
        varPTV_varPTP=new int[cont][3];         
        cont=0;
        for(int i=0;i<simbNum.length;i++)for(int j=1;j<simbNum[0].length;j++)if(!parametres[i][j].equals("")&&!simbNum[i][j]){
            simP[cont]=parametres[i][j];cont++;
        }
        for(int i=0;i<simP.length;i++)for(int j=i+1;j<simP.length;j++)if(simP[i].equals(simP[j])){simP[i]=null;cont--;j=simP.length;}
        simPar=new String[cont];
        cont=0;
        for(int i=0;i<simP.length;i++)if(simP[i]!=null){simPar[cont]=simP[i];cont++;}
        if(simP.length!=simPar.length){Func.append(1,Func.avis);Func.append(0,"hi ha par"+Func.rB.getString("a")+"metres repetits a la taula par"+Func.rB.getString("a")+"metres"+ splitPan.FIL);}
        cont=0;
        int cont1=0;
        boolean[] b=new boolean[cc-1];
        for(int i=0;i<simbNum.length;i++)if(simbNum[i][0]){Func.append(1,Func.error);Func.append(0,"a taula par"+Func.rB.getString("a")+"metres la primera filera de simbols no poden esser valors numerics"+ splitPan.FIL);return false;}
        int[] parametresFileres=new int[parametres.length];
        for(int j=0;j<parametres.length;j++){
            cont=0;
            for(int i=2;i<parametres[0].length;i++)if(!parametres[j][i].equals(""))cont++;//a partir de la segona filera de valors la primera ja te contingut
            if(Func.tipusFuncioBol)for(int i=2;i<parametres[0].length-1;i++){//quant hi ha la possibilitat de mes d'una taula cal considerar que les cel.les buides seguides d'una cel.la amb contingut es un error
                if(parametres[j][i].equals("")&&!parametres[j][i+1].equals("")){Func.append(1,Func.error);Func.append(0,"a taula par"+Func.rB.getString("a")+"metres columna: "+j+"; no es v"+Func.rB.getString("a")+"lid: cel.les buides insertades en un conjunt de cel.les amb contingut"+ splitPan.FIL);return false;}
            }
            parametresFileres[j]=cont+1;
            if(cont==0){cont1++;b[j]=true;}//per cada columna de taulaP cont es el nombre de fileres a partir de la segona que son blancs b[j]=true indica que la columna te nomes un valor
        }
        simParCapUnaFil=new String[cont1];
        dsimParCapUnaFil=new double[cont1];
        cont1=0;
        for(int i=0;i<b.length;i++)if(b[i]){//si la col te nomes un valor
            simParCapUnaFil[cont1]=parametres[i][0];
            if(simbNum[i][1])dsimParCapUnaFil[cont1]=dparametres[i][1];
            else{Func.append(1,Func.error);Func.append(0,"a taula par"+Func.rB.getString("a")+"metres columna: "+i+" hi ha un sol valor a filera 1, cal que aquest sigui num"+Func.rB.getString("_e")+"ric"+ splitPan.FIL);return false;}
            cont1++;
        }
        if(!hihaidxArr())return false;
        Cal.longiP=taulaP.parametres[0].length-1;
        longiP=new int[taulaP.parametres.length];
        Cal.longiPBol=new boolean[Cal.longiP];
        Cur.funGrafP=new double[3][Cal.longiP];
        Cur.resultatM=new double[Cal.longiP];Cal.resultatM=new double[Cur.resultatM.length];
        Cur.resultatMcop=new double[Cur.resultatM.length];Cal.resultatMcop=new double[Cur.resultatM.length];
        for(int i=0;i<parametres.length;i++)for(int j=parametres[0].length-1;j>0;j--)if(!parametres[i][j].equals("")){longiP[i]=j;j=0;}
        String s=" ",s1="",s2="";
        for(int i=0;i<parametres.length;i++){//a cada filera hi ha el parametre de capçalera seguit dels parametres variables o valors numerics
            if(!b[i]){//si la columna te mes d'un valor
                b0=false;
                for(int j=1;j<longiP[i];j++)if(!simbNum[i][j]){b0=true;j=parametres[0].length;}
                if(!b0){
                    if(s1.equals(""))s1=" "+simParCap[i]+" ";
                    else s1+=simParCap[i]+" ";
                }
                else{
                    if(s2.equals(""))s2=" "+simParCap[i]+" ";
                    else s2+=simParCap[i]+" ";
                }
            }
        }
        simParCapNoVar=splitPan.matriudeFuncio(s1);
        simParCapVar=splitPan.matriudeFuncio(s2);
        splitPan.simbolsNoUtilitsatsIniciParametres();
        boolean correcte=true;//comprobar si hi ha parametres que es poden comfondre amb funcions operadors o nombres
        for (int j = 0; j < simPar.length; j++) {
            s = splitPan.esSimVar(simPar[j]);
            if(!s.equals("")) {Func.append(1,Func.error);Func.append(0,Func.rB.getString("parametresSim")+j+s+splitPan.FIL);correcte=false;}
        }
        for (int j = 0; j < simParCap.length; j++) {
            s = splitPan.esSimVar(simParCap[j]);
            if(!s.equals("")) {Func.append(1,Func.error);Func.append(0,Func.rB.getString("parametresSimCap")+j+s+splitPan.FIL);correcte=false;}
        }
        if(!correcte){return false;}
        if(!taulaV.hihaVar&&hihaParVar){Func.append(0,"a taula par"+Func.rB.getString("a")+"metres hi ha cel.les amb contingut no num"+Func.rB.getString("e_")+"ric i taula variables es buida"+ splitPan.FIL);return false;}
        cont=0;
        for(int i=0;i<parametres.length;i++)for(int j=1;j<parametres[0].length;j++)if(!parametres[i][j].equals("")&&!simbNum[i][j]){
            varPTV_varPTP[cont][1]=j-1;
            Cal.longiPBol[j-1]=true;
            varPTV_varPTP[cont][2]=i;
            for(int k=0;k<taulaV.varstaulaV.length;k++)if(parametres[i][j].equals(taulaV.varstaulaV[k]))varPTV_varPTP[cont][0]=k;
            cont++;
        }
        boolean bol=false;
        for(int j=0;j<parametres.length;j++){
            for(int i=2;i<parametres[0].length;i++){//a partir de la segona filera de valors la primera ja te contingut
                if(parametres[j][i].equals("")&&!Func.tipusFuncioBol){// si false en les columnes en que no hi parametres variables carrega les ce.les buides a partir de la filera 1 amb els valors de l'anterior;  si es true aleshores i poden haver mes d'una taulaP
                    if(simbNum[j][1]){//si el simbol de la primera filera es numeric
                        if(!filasimbVar[j]){//si la columna no conte cap parametre variable 
                            parametres[j][i]=parametres[j][i-1];
                            simbNum[j][i]=true;
                            bol=true;
                        }
                        else{Func.append(1,Func.error);Func.append(0,"a taula par"+Func.rB.getString("a")+"metres columna: "+j+" les cel.les buides en una columna amb par"+Func.rB.getString("a")+"metres variables es incorrecte"+ splitPan.FIL);return false;}
                    }
                }
            }
        }
        if(bol){Func.append(1,Func.avis);Func.append(0,"a taula par"+Func.rB.getString("a")+"metres hi ha cel.les buides; es fan igual als de la cel.la superior"+ splitPan.FIL);}
        return true;
    }
    private static boolean hihaidxArr(){
        if(Func.tipusFuncioBol){
            String[] mi=new String[taulaF.matriu.length-1];
            for(int i=0;i<mi.length;i++)mi[i]=taulaF.matriu[i+1][taulaF.idxIni];
            int cont1=0;
            String s3="";
            taulaF.dimensioArr=new int[mi.length];
            for(int i=0;i<mi.length;i++){//determina 
                mi[i]=mi[i].replaceAll("mat", "arr");
                int j=mi[i].indexOf("arr[");
                int k=0;
                if(j>-1)k=mi[i].indexOf("]",j);
                String s1="",s2="";
                int m=-1;
                try{
                    if(k>0){
                        String s=mi[i].substring(j+4,k);
                        m=s.indexOf("=");
                        if(m>-1){
                            s1=s.substring(0,m);
                            boolean bo=false;
                            for(int l=0;l<simParCapUnaFil.length;l++)if(s1.equals(simParCapUnaFil[l])){
                                {Func.append(1,Func.error);Func.append(0,"a taula par"+Func.rB.getString("a")+"metres columna: "+9+"; fila: "+i+"; els simbols de capçalera de una sola fila, o els simbols previament definits com dimensions d'una matriu a columna 9,  no son compatibles amb el simbol '='; "+ splitPan.FIL);return false;}
                            }
                            s2=s.substring(m+1);
                            mi[i]="arr["+s1+"]";
                            s3+=s1+splitPan.TAB+s2+splitPan.FIL;
                            cont1++;
                            }
                        }
                }
                catch (Exception e){}
            }
            if(cont1>0){
                taulaP.hihaPar=true;
                String[] s0=new String[simParCapUnaFil.length+cont1];
                double[] s1=new double[dsimParCapUnaFil.length+cont1];
                System.arraycopy(simParCapUnaFil, 0, s0, 0, simParCapUnaFil.length);
                System.arraycopy(dsimParCapUnaFil, 0, s1, 0, dsimParCapUnaFil.length);
                String[][] s=splitPan.matriudeFuncioString(s3);
                for(int i=0;i<s.length;i++){
                    s0[simParCapUnaFil.length+i]=s[i][0];
                    s1[simParCapUnaFil.length+i]=(double)Double.parseDouble(s[i][1]);
                }
                simParCapUnaFil=new String[s0.length];System.arraycopy(s0, 0, simParCapUnaFil, 0, s0.length);
                dsimParCapUnaFil=new double[s1.length];System.arraycopy(s1, 0, dsimParCapUnaFil, 0, s1.length);
                splitPan.simBolPC0=new boolean [taulaP.simParCapUnaFil.length];
            }
            //for(int m=0;m<simParCapUnaFil.length;m++)System.out.println(simParCapUnaFil[m]+" <> "+dsimParCapUnaFil[m]);
            for(int i=0;i<mi.length;i++){
                int j=mi[i].indexOf("arr[");
                int k=0;
                if(j>-1)k=mi[i].indexOf("]",j);
                int m=-1;
                try{
                    if(k>0){
                        String s=mi[i].substring(j+4,k);
                        boolean bo=false;
                        for(int l=0;l<simParCapUnaFil.length;l++)if(s.equals(simParCapUnaFil[l])){
                            taulaF.dimensioArr[i]=(int)dsimParCapUnaFil[l];l=simParCapUnaFil.length;bo=true;
                        }
                        if(!bo){
                            m=Integer.parseInt(s);
                            taulaF.dimensioArr[i]=m;
                        }
                    }
                }
                catch (Exception e){{Func.append(1,Func.error);Func.append(0,"a taula par"+Func.rB.getString("a")+"metres columna: "+9+"; fila: "+i+"; els simbols no es corresponent amb un valor numeric,; "+ splitPan.FIL);return false;}}
            } 
        }
        return true;
    }
    private  boolean moufilBuides(){
        boolean correcte=false;
        for(int i=2;i<matriu.length;i++){
            boolean b=false;
            for(int j=1;j<matriu[0].length;j++)if(!matriu[i][j].equals(""))b=true;
            if(!b){
                correcte=true;
                tl.setRowSelectionInterval(i-1, i-1);
                menuItemFil.doClick();
                i--;
                }
            if(matriu.length<=3)i=matriu.length;
        }
        return correcte;
    }
    private  boolean mouColBuides(){
        boolean correcte=false;
        for(int j=1;j<matriu[0].length;j++){
            boolean b=false;
            for(int i=1;i<matriu.length;i++)if(!matriu[i][j].equals(""))b=true;
            if(!b){
                correcte=true;
                tl.setColumnSelectionInterval(j, j);
                menuItemCol.doClick();
                j--;
                }
            if(matriu[0].length<=3)j=matriu[0].length;
            }
        return correcte;
    }
    public void nouModel(boolean b){
        Func.analisiPreviBol=false;
        int cc=tl.getColumnCount();
        int[] cols=new int[cc];
        if(b)for(int i=0;i<cc;i++)cols[i]=tl.getColumnModel().getColumn(i).getWidth();
        tl.setModel(new model());
        for(int i=1;i<matriu.length;i++)for(int j=0;j<matriu[0].length;j++){tl.setValueAt((Object)matriu[i][j], i-1, j);}
        int i=tl.getRowCount();
        for(int j=0;j<i;j++)tl.setRowHeight( j, 18 );
        if(b)for(i=0;i<cc;i++)tl.getColumnModel().getColumn(i).setPreferredWidth(cols[i]);
        splitPan.tTP.repaint();
        color();
        editaCela();
    }
    public void color(){
        int c=tl.getColumnCount();
        Color gc=new Color(220,220,220);
        Color gmc=new Color(235,235,235);
        Color gmh=new Color(215,228,235);
        Color gh=new Color(200,213,220);
        JThd = tl.getTableHeader();
        TableCellRenderer hr = tl.getTableHeader().getDefaultRenderer();
        JThd.setDefaultRenderer(new TableCellRenderer() {
            JLabel lb;
            @Override
            public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus,int row, int column) {
                    lb = (JLabel) hr.getTableCellRendererComponent(table, value, false, false, row, column);
                    for(int i=0;i<c;i++)if (column == i)lb.setBackground(gmh);
                return lb;
                }
        });
        for(int i=0;i<c;i++){
            tl.getColumn(tl.getColumnName(i)).setCellRenderer(new DefaultTableCellRenderer() {
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    DefaultTableCellRenderer r = (DefaultTableCellRenderer) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if(!isSelected){
                        if(column==0){
                            if (row == 0) r.setBackground(gmh);
                            else{
                                if ((row % 2) != 0) r.setBackground(gmh);
                                else r.setBackground(gh);
                            }
                        }
                        else{
                            if (row == 0) r.setBackground(gmc);
                            else{
                                if ((row % 2) != 0) r.setBackground(gmc);
                                else r.setBackground(gc);
                            }
                        }
                    } 
                    return r;
                }   
            });
        }
    }
    private void capsalInteg(){
        matriu[0][0] = "Par" + Func.rB.getString("a") + "metres";for(int i=1;i<matriu[0].length;i++){matriu[0][i] = ""+i;}
        if (matriu.length > 0) {matriu[1][0] = simbol;}
        if (matriu.length > 1) {matriu[2][0] = valor;}
        for(int i=3;i<matriu.length;i++){int i0=i-1;matriu[i][0] = ""+i0;}
    }
    private void valorsInicials(){
        if(matriu==null){reiniciParcial();}
    	else{capsalInteg();}
        hihaPar=false;
    }
    private void reiniciParcial(){
        matriu=new String[3][2];
        capsalInteg();
        matriu[1][1]="";
        matriu[2][1]="";
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
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("TableFTFEditDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        taulaP newContentPane = new taulaP();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        frame.pack();
        frame.setVisible(true);
        Font fo = new Font(font.tipus, font.estil, font.tamany);
        frame.setFont(fo);
    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}