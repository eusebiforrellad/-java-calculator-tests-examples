package Calc;
/**
 *
 * @author EFO
 */
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
import static java.lang.Double.isFinite;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class taulaD extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    static JTable tl;
   public static String[][] matriu;
    public static String [][] matriuSup;
    public static String[] simbol;//simbols de la funcio derivada primera columna
    public static String[] varDer;//simbols de la variable de les derivades columna 1
    public static double[] intervDer;//increment pel calcul de les derivades columna 2 el interval[] si el valor es valid es calcula a partir d'aquest valor
    public static String[] simbolIntervalDer;//simbols de  les derivades Δx
    public static int[] ordreDer;// ordre de la derivada un sencer ex: 2 i ndica derivada de segon ordre
    public static boolean[] hihaVGioFP;// si hi ha variable general a la funcio derivable o funcions parcials  es true (no inclou variable de la taulaF); posteriormen nomes funcions parcials constants no es contabiliyzen (les funcions constants ja s'han incorporat a la funcio veure: cadenes_sVO_deFuncions_taulaD( )
    public static boolean[] hihaInterval;
    public static boolean hihaDades=false;
    public static int[][] indexsVGaD;
    public static int[][] derivadesLimFun;
    public static String[] sVO;
    public static int[][] indexs;
    static int[][] indexsFPD;//fileres es el nombre de relacions entre simbols de funcions FP que son dependents  i simbols de les funcions previes [x][] es l'index a dVO que conte el simbol  [][x] es la filera amb el simbol de la variable 
    static int[][] limFPD;//per cada filera els limits de indexsFPD
    public static boolean[] hihaDependencia;
    public static boolean hihaDependencies;
    public static boolean[][] VGaFuncBol;//per cada filera indica si la funcio es dependent directa o indirectament d'alguna varible (si hi ha tres variables generals la columna 0 es relaciona amb la primera variable  la 1 amb la segona etc) a taula F es corretgeig en funcio de les variables generals de la filera
    public static boolean[][] dependencia;
    public static JCheckBoxMenuItem cbM = new JCheckBoxMenuItem("scroll taula");
    public static int scrollCols;
    JMenuItem menuItemFil;
    public static JTextField cela;
    boolean[][] bln;
    public static double[] resultatFil;
    public static int[] idxVarDer;//aplicaple a les derivades; una sola variable general per funcio i filera per cada filera hi ha  el idxVarGen corresponent
    public static int[] idxvarsdeFunDer;//variables generals que es troben a totes les funcions derivades necesari previ al calcul splitPan.tabCalcFun(
    public static boolean[] simbolsDconstants;
    static JMenuItem  jmi_copiSel, jmi_ajuntaSel, jmi_copi,jmi_cut, jmi_ajunta,jmi_carregar,jmi_save,jmi_treuFil,jmi_insertaFil,jmi_saveAs,jmi_reinici,jmi_scroll;
    static JTableHeader JThd;
    public taulaD() {
        super(new GridLayout(1,0));
        inici();
    }
    public taulaD(boolean x){}
    public void INI(){
        cela = new JTextField();
        cela.setBorder(Func.border);
        cela.addKeyListener(new KeyAdapter() {
           public void keyTyped(KeyEvent e) {Func.analisiPreviBol=false;}
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
        tl.setRowSelectionAllowed(true);
        tl.setColumnSelectionAllowed(true);
        tl.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(tl);
        add(scrollPane);
        setOpaque(true);
        createPopupMenu();
        tl.putClientProperty("terminateEditOnFocusLost", true);
        tl.setAutoResizeMode(scrollCols);
        color();
        editaCela();
    }
    private void editaCela() {
        TableColumnModel t = tl.getColumnModel();
        for(int i=1;i<matriu[0].length;i++){
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
        if(s.equals("inserta filera")){ contingutTaula(tl.getSelectedRows(),true);nouModel();}
        if(s.equals("treu filera")){ contingutTaula(tl.getSelectedRows(),false);nouModel();}
        if (s.equals("guardar dades")) {splitPan.guardarArx();}
        if(s.equals("guardar (save as)")){splitPan.guardarArxCom();}
        if (s.equals("carregar dades")) {splitPan.carregaArx();}
        if ("copiar".equals(e.getActionCommand())) {splitPan.cop(tl,matriu,false);}
        if ("cut".equals(e.getActionCommand())) {splitPan.cop(tl,matriu,true);}
        if(s.equals("ajuntar a cel.la")){
            String[][] m=splitPan.paste();
            int row=tl.getSelectedRow();int col=tl.getSelectedColumn();
            boolean b=false;
            if(matriu[0].length<col+m[0].length){Func.append(1,"error: ");Func.append(0,"el nombre de columnes de la taulaD es fixa la c"+Func.rB.getString("_o")+"pia implica un increment del nombre de columnes;"+ splitPan.FIL);return;}
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
        if ("copia sel.".equals(e.getActionCommand())) {splitPan.cop_sel(cela);return; }
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
        jmi_insertaFil.setFont(fo);
        jmi_reinici.setFont(fo);
        jmi_carregar.setFont(fo);
        jmi_save.setFont(fo);
        jmi_saveAs.setFont(fo);
        JThd.setFont(fo);
    }
    public void reinici(){ matriu=null;valorsInicials();nouModel();}
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
                    for(int i=1;i<fi+1;i++){matriu[i][j]=matriuSup[i][j];}
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
                matriu=new String [2][cc];for(int j=0;j<cc;j++){matriu[1][j]="";}
            }
            capsalInteg();
    	}
    }
    private void inici(int i){
        varDer=new String[i];//simbol de la variable independent
        intervDer=new double[i];//double interval
        simbolIntervalDer=new String[i];
        ordreDer=new int[i];//int ordre de la derivada
        idxVarDer=new int[i];for(int j=0;j<i;j++)idxVarDer[j]=-1;
        hihaInterval=new boolean[i];
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
    public  boolean matriuTaula() {
        lectAmplCol();
        hihaDades=false;
        idxvarsdeFunDer=new int[0];
        simbol=new String[0];
        resultatFil=new double[0];
        hihaVGioFP=new boolean[0];
        inici(0);
        int cc=tl.getColumnCount();
        int cf=tl.getRowCount()+1;
        for(int i=1;i<cf;i++)for(int j=1;j<cc;j++)matriu[i][j]=matriu[i][j].trim();
        boolean b0=false;
        if(cf>2)if(moufilBuides()){Func.append(1,"info: ");Func.append(0,"a taula func. parcials s'han eliminat fileres buides"+ splitPan.FIL);cf=tl.getRowCount()+1; }
        bln=new boolean[cf+1][4];
        for(int i=1;i<cf;i++){
                if(matriu[i][4].equals("")){bln[i][3]=true;bln[0][3]=true;}
                else hihaDades=true;
            }
        if(hihaDades&&bln[0][3]){// si hi ha dades i alguna cel.la  de funcio es buida: comprovar cada cel.la
            for(int i=1;i<cf;i++){
                if(bln[i][3]){Func.append(1,Func.error);Func.append(0,"a taula func. parcials cal definir les funcions, columna: '"+Func.rB.getString("simbolfuncio")+" filera: "+i+ splitPan.FIL);}
            }
            return false;
        }
        for(int j=1;j<cc-1;j++)for(int i=1;i<cf;i++)if(!matriu[i][j].equals("")){bln[i][j-1]=true;bln[0][j-1]=true;hihaDades=true;}
        if(!hihaDades){return true;}
        simbol=new String[matriu.length-1];//simbol primera columna 0
        simbolsDconstants=new boolean[simbol.length];
        resultatFil=new double[simbol.length];
        inici(simbol.length);
        boolean correcte=true;
        for(int i=1;i<cf;i++){
            if(bln[i][0]){
                boolean b=false;
                for(int j=0;j<taulaV.varsGenerals.length;j++)if(taulaV.varsGenerals[j].equals(matriu[i][1])){b=true;j=taulaV.varsGenerals.length;}
                if(!b){Func.append(1,Func.error);Func.append(0,"a taula func. parcials fila: "+(i-1)+" la variable derivable x: "+matriu[i][1]+"; ha d'esser una variable general"+ splitPan.FIL);return false;}
            }
            if(!bln[i][2]&&bln[i][0]){//si !bln[i][2] (ordre derivada false = no te ontingut) 
                Func.append(1,Func.avis);Func.append(0,"a taula func. parcials ordre de la derivada no definit i hi ha variable general  (columnes n i x) es considera funcio parcial amb ordre de derivada = 0"+ splitPan.FIL);
                ordreDer[i-1]=0;
            }
            if(!bln[i][2]&&bln[i][1]){//si !bln[i][2] (ordre derivada false = no te ontingut) 
                Func.append(1,Func.avis);Func.append(0,"a taula func. parcials ordre de la derivada no definit i hi ha interval definit  (columnees n i "+Func.increm+"x) es considera funci"+Func.rB.getString("o_")+" parcial amb ordre de derivada = 0"+ splitPan.FIL);
                ordreDer[i-1]=0;
            }
            if(bln[i][2]) {
                try{ordreDer[i-1]=Integer.parseInt(matriu[i][3]);}
                catch(Exception e){
                    Func.append(1,Func.error);Func.append(0,"a taula func. parcials ordre de la derivada (columna n) ha d'"+Func.rB.getString("e_")+"sser un blanc o 0, 1, 2, 3 ..."+ splitPan.FIL);
                    correcte= false;
                }
                if(ordreDer[i-1]<0){
                    Func.append(1,Func.error);Func.append(0,"a taula func. parcials ordre de la derivada (columna n) ha d'"+Func.rB.getString("e_")+"sser un blanc o un sencer  0, 1, 2, 3 ..."+ splitPan.FIL);
                    correcte= false;
                }
            }
        }
        for(int i=1;i<cf;i++){
            if(!bln[i][0]&&ordreDer[i-1]>0){
                Func.append(1,Func.avis);Func.append(0,"a taula func. parcials variable derivable no definida (columna x)"+ splitPan.FIL);
                varDer[i-1]="";//mes endavat si la funcio de la derivada conte una sola variable general es selecciona
            }
            else varDer[i-1]=matriu[i][1];
            hihaInterval[i-1]=bln[i][1];
            if(hihaInterval[i-1]&&ordreDer[i-1]==0){
                Func.append(1,Func.avis);Func.append(0,"a taula func. parcials hi ha interval hi no es tracta de un c"+Func.rB.getString("a")+"lcul de derivada"+ splitPan.FIL+"el procediment continua ignorant l'increment"+ splitPan.FIL);
                tl.setValueAt("", i-1, 2);
                hihaInterval[i-1]=false;
            }
            if(hihaInterval[i-1]){
                try{intervDer[i-1]=Integer.parseInt(splitPan.comApun(matriu[i][2]));}
                catch(Exception e){
                    Func.append(1,Func.avis);Func.append(0,"a taula func. parcials l'interval ha d'"+Func.rB.getString("e_")+"sser un sencer positiu >5 i <58^(1/ordreDer) el interval ser"+Func.rB.getString("a")+" el l"+Func.rB.getString("i_")+"mit superior - l"+Func.rB.getString("i_")+"mit inferior de la variable dividit per 2^sencer"+ splitPan.FIL);
                    tl.setValueAt("", i-1, 2);
                    hihaInterval[i-1]=false;
                }
                
                int lim=(int)58/(int)Math.pow(2,ordreDer[i-1]);
                if(lim<6)lim=6;
                if(hihaInterval[i-1]&&(intervDer[i-1]>lim||intervDer[i-1]<6)){
                    Func.append(1,Func.avis);Func.append(0,"a taula func. parcials l'interval ha d'"+Func.rB.getString("e_")+"sser un sencer positiu >=6 i <58/2^ordreDerivada; el interval ser"+Func.rB.getString("a")+" el l"+Func.rB.getString("i_")+"mit superior - l"+Func.rB.getString("i_")+"mit inferior de la variable dividit per 2^sencer"+ splitPan.FIL);
                    tl.setValueAt("", i-1, 2);
                    hihaInterval[i-1]=false;
                }
            }
        }
        if(!correcte)return false;
        for(int i=0;i<simbol.length;i++){simbol[i]=matriu[i+1][0];}
        return true;
    }
     public boolean matriuTaulaFi() {
        suportID.darrerDigits=new int[simbol.length];
        int cf=tl.getRowCount()+1;
        boolean[] bol=new boolean[taulaV.idxVarGen.length];
        hihaVGioFP=new boolean[matriu.length-1];
        for(int i=0;i<taulaD.simbol.length;i++){
            String[] t=splitPan.arr_simbolsDer.get(i);
            if(t.length!=0){
               for(int j=0;j<t.length;j++)for(int k=0;k<taulaV.varsGenerals.length;k++){
                   if(t[j].equals(taulaV.varsGenerals[k])){taulaD.hihaVGioFP[i]=true;bol[k]=true;}
               } 
               for(int j=0;j<t.length;j++)for(int k=0;k<i;k++){
                   if(t[j].equals(taulaD.simbol[k])){
                       if(taulaD.hihaVGioFP[k])taulaD.hihaVGioFP[i]=true;//si la funcio d' alguna filera anterior conte el mateix simbol que el simbol deFP actual i la funcio conte variables generals
                    }
               }
               for(int k=i;k<taulaD.simbol.length;k++)for(int j=0;j<t.length;j++)if(t[j].equals(taulaD.simbol[k])){
                    Func.append(1,Func.error);Func.append(0,"a taula funcions parcials(derivades) les funcions parcials no poden contenir les de la mateixa filera o superior, ex: la funci"+Func.rB.getString("o_")+"  parcial "+simbol[i]+"; es troba en fileres posteriors"+splitPan.FIL);
                    return false;
               }
            }
        }
        int cont=0;
        for(int i=0;i<bol.length;i++)if(bol[i])cont++;
        idxvarsdeFunDer=new int [cont];
        cont=0;for(int i=0;i<bol.length;i++)if(bol[i]){idxvarsdeFunDer[cont]=taulaV.idxVarGen[i];cont++;}
        bol=new boolean[taulaV.idxVarGen.length];
        for(int j=0;j<Cal.idxVarGen.length;j++)for(int i=0;i<simbol.length;i++){
                if(varDer[i].equals(taulaV.varstaulaV[Cal.idxVarGen[j]])){idxVarDer[i]=Cal.idxVarGen[j];}
        }
        for(int i=0;i<simbol.length;i++)simbolIntervalDer[i]=Func.increm+varDer[i];
        splitPan.cadenes_sVO_deFuncions_taulaD();//calcula les matrius sVO dVO reduides
        hihaDependencies=false;
            hihaDependencia=new boolean[simbol.length];
            dependencia =new boolean[simbol.length][simbol.length];
            for(int l=1;l<simbol.length;l++){
                sVO=splitPan.arr_sVO_Der.get(l);
                for(int i=0;i<l;i++){
                    for(int k=0;k<sVO.length;k++)if(simbol[i].equals(sVO[k])||("-"+simbol[i]).equals(sVO[k])){
                        hihaDependencia[l]=true;hihaDependencies=true;dependencia[l][i]=true;
                        for(int j=0;j<i;j++)if(dependencia[i][j])dependencia[l][j]=true;//si la filera i que afecta j es afectada per una filera anterior x aixo implica que x tambe afecta la filera j
                    }
                }
            }
        for(int i=0;i<taulaD.simbol.length;i++)if(isFinite(taulaD.resultatFil[i])) {simbolsDconstants[i]=true;Func.append(1,Func.avis);Func.append(0,"a taula func. parcials a la funci"+Func.rB.getString("o_")+": "+taulaD.simbol[i]+" no hi ha variables es pren com una constant = "+taulaD.resultatFil[i]+splitPan.FIL);}
        for(int l=1;l<simbol.length;l++)if(hihaDependencia[l]&&ordreDer[l]>0){
            for(int i=0;i<l;i++)if(dependencia[l][i]&&ordreDer[i]>0){Func.append(1,Func.error);Func.append(0,"a taula func. parcials funci"+Func.rB.getString("o_")+":  "+simbol[l]+", les funcions parcials amb ordre derivada > 0 dependents d'altres amb ordre derivada >0 no es deriven."+splitPan.FIL);return false;}
        }
        String[] svo=new String[0];
        Double[] dvo=new Double[0];
        Double[] dVO=new Double[0];//aqui dVO es una variable interna del procediment
        boolean b1=false,b2=false;
        String s="";
        for(int idx=1;idx<simbol.length;idx++)if(hihaDependencia[idx]&&ordreDer[idx]>0){//si la funcio dependent es deriva cal traslladar tote la funcio
            b1=false;s="";
            sVO=splitPan.arr_sVO_Der.get(idx);
            dVO=splitPan.arr_dVO_Der.get(idx);
            boolean[] dep=new boolean[dependencia[0].length];
            System.arraycopy(dependencia[idx], 0, dep, 0, dep.length);
            int tam=-1;
            while(tam!=sVO.length){
                tam=sVO.length;
                System.arraycopy(dep,0,dependencia[idx], 0, dep.length);
                for(int l=0;l<idx;l++)if(dependencia[idx][l]){
                    String[] sv=splitPan.arr_sVO_Der.get(l);
                    Double[] dv=splitPan.arr_dVO_Der.get(l);
                    boolean b=false;
                    for(int j=0;j<sv.length;j++){
                        if(varDer[idx].equals(sv[j])||varDer[idx].equals("-"+sv[j])){b=true;j=sVO.length;}//c
                    }
                    if(b){
                        for(int i=0;i<idx;i++)if(dependencia[idx][i]){
                            sv=splitPan.arr_sVO_Der.get(i);
                            dv=splitPan.arr_dVO_Der.get(i);
                            boolean bl=false;
                            for(int j=0;j<sVO.length;j++){   
                                if(sVO[j].equals(simbol[i])){
                                    int k=sVO.length+sv.length+1;//treiem un simbol i fiquem la longitud de la funcio i dos parentesi per tant sumem 1 a la suma de longituds de les dues cadenes
                                    svo=new String[k];dvo=new Double[k];
                                    System.arraycopy(sVO, 0, svo, 0, j);System.arraycopy(dVO, 0, dvo, 0, j);
                                    svo[j]="(";dvo[j]=null;
                                    System.arraycopy(sv, 0, svo, j+1, sv.length);System.arraycopy(dv, 0, dvo, j+1, sv.length);
                                    svo[sv.length+j+1]=")";dvo[sv.length+j+1]=null;
                                    System.arraycopy(sVO, j+1, svo, sv.length+j+2, sVO.length-j-1);System.arraycopy(dVO, j+1, dvo, sv.length+j+2, sVO.length-j-1);
                                    b1=true;bl=true;
                                }
                                else if(sVO[j].equals("-"+simbol[i])){
                                    int k=sVO.length+sv.length+2;//treiem un simbol i fiquem la longitud de la funcio i dos parentesi i un signe menys per tant sumem 2 a la suma de longituds de les dues cadenes
                                    svo=new String[k];dvo=new Double[k];
                                    System.arraycopy(sVO, 0, svo, 0, j);System.arraycopy(dVO, 0, dvo, 0, j);
                                    svo[j]="-";dvo[j]=null;svo[j+1]="(";dvo[j+1]=null;
                                    System.arraycopy(sv, 0, svo, j+2, sv.length);System.arraycopy(dv, 0, dvo, j+2, sv.length);
                                    svo[sv.length+j+2]=")";dvo[sv.length+j+2]=null;
                                    System.arraycopy(sVO, j+1, svo, sv.length+j+3, sVO.length-j-1);System.arraycopy(dVO, j+1, dvo, sv.length+j+3, sVO.length-j-1);
                                    b1=true;bl=true;
                                }
                            }
                            if(bl){
                                splitPan.arr_sVO_Der.set(idx, svo);splitPan.arr_dVO_Der.set(idx, dvo);
                                sVO=splitPan.arr_sVO_Der.get(idx);dVO=splitPan.arr_dVO_Der.get(idx);
                                dependencia[idx][i]=false;
                                i=-1;
                            }
                        }
                    }
                }
                
            }
            if(b1){
                    b2=true;for(int j=0;j<svo.length;j++)s+=svo[j]+" ";
                    Func.append(1,Func.avis);Func.append(0,"a taula func. parcials funcions amb ordre derivada>0  es substitueix els s"+Func.rB.getString("i_")+"mbols de funcions per les funcions, fila: "+idx+" resultat: "+s+splitPan.FIL);
                }
            hihaDependencia[idx]=false;
        }
        for(int l=0;l<simbol.length;l++){
            sVO=splitPan.arr_sVO_Der.get(l);
            dVO=splitPan.arr_dVO_Der.get(l);
        }
        for(int i=0;i<taulaD.simbol.length;i++){
            String[] t=splitPan.arr_sVO_Der.get(i);
            if(hihaVGioFP[i]){//hi ha variables generals directes o indirectes comprobar que la variable derivable  es troba a la funcio de la derivada
                int pos=0;
                cont=0;
                for(int l=0;l<taulaV.idxVarGen.length;l++)for(int m=0;m<t.length;m++)if(taulaV.varstaulaV[taulaV.idxVarGen[l]].equals(t[m])){pos=Cal.idxVarGen[l];cont++;m=t.length;}//la variable derivable es troba a la funcio
                if(ordreDer[i]!=0){
                    if(varDer[i].equals("")){
                        if(cont==1){
                            varDer[i]=taulaV.varstaulaV[taulaV.idxVarGen[pos]];
                            Func.append(1,Func.avis);Func.append(0,"a taula func. parcials una sola variable general no definida (columna x), i ordre derivada positiu es pren la de la funci"+Func.rB.getString("o_")+" "+Func.rB.getString("simbolfuncio")+" ex.: "+simbol[i]+ splitPan.FIL);
                            tl.setValueAt(varDer[i], i, 1);
                        }
                        else {Func.append(1,Func.error);Func.append(0,"a taula func. parcials (columna: x) cal definir una variable general derivable a la funci"+Func.rB.getString("o_")+" '"+Func.rB.getString("simbolfuncio")+"'; hi ha mes d'una variable general"+" ex.: "+simbol[i]+ splitPan.FIL);return false;}
                    }
                    else{
                        boolean b=false;
                        for(int m=0;m<t.length;m++)if(t[m].equals(varDer[i]))b=true;
                        if(!b){Func.append(1,Func.error);Func.append(0,"a taula func. parcials variable general (columna x),no coincideix amb cap variable general de la funci"+Func.rB.getString("o_")+" "+Func.rB.getString("simbolfuncio")+" ex.: "+simbol[i]+ splitPan.FIL);return false;}
                    }
                }
                else if(!varDer[i].equals("")){ Func.append(1,Func.avis);Func.append(0,"quant no es tracta de una derivada de funci"+Func.rB.getString("o_")+" no cal definir variable derivable a funci"+Func.rB.getString("o_")+" "+Func.rB.getString("simbolfuncio")+" ex.: "+simbol[i]+ splitPan.FIL);}
            }
        }
        return true;
     }
    public boolean iniFuncionsVariables(){
        int cont=0;
        int[] contdVO=new int[simbol.length];//contdVO acumula la longitud de les matrius dVO de les diferents fileres
        for(int l=0;l<simbol.length;l++){//fa el canvi de Cur.dVO[posicio del simbol]=null pel valor 1 perque en el calcul de indexs es fasi la operacio correcte
            splitPan.sVO=splitPan.arr_sVO_Der.get(l);splitPan.dVO=splitPan.arr_dVO_Der.get(l);
            boolean b=false;
            if(l>0)for(int j=0;j<l;j++)for(int k=0;k<splitPan.sVO.length;k++)if(splitPan.sVO[k].equals(simbol[j])||splitPan.sVO[k].equals("-"+simbol[j])){splitPan.dVO[k]=1.0;b=true;}
            contdVO[l]=splitPan.sVO.length;
            if(l>0)contdVO[l]+=contdVO[l-1];
            if(b)splitPan.arr_dVO_Der.set(l,splitPan.dVO);
        }
        for(int l=0;l<simbol.length;l++){
           splitPan.sVO=splitPan.arr_sVO_Der.get(l);
           splitPan.dVO=splitPan.arr_dVO_Der.get(l);
        }
        limFPD=new int[simbol.length][2];
        cont=0;
        String sp="";
        for(int l=1;l<simbol.length;l++){ 
            splitPan.sVO=splitPan.arr_sVO_Der.get(l);
            for(int i=0;i<l;i++)for(int k=0;k<splitPan.sVO.length;k++){//per cada filera a partir de la segona comprovem si hi ha referencia a alguna funcio de les fileres anteriors
                if(simbol[i].equals(splitPan.sVO[k])||("-"+simbol[i]).equals(splitPan.sVO[k])){
                    sp+=(k+contdVO[l-1])+" "+i+" ";
                    cont++;
                }
            }
            limFPD[l][1]=cont;
        }
        for(int l=1;l<simbol.length;l++)limFPD[l][0]=limFPD[l-1][1];
        indexsFPD=new int [cont][2];//indexsFPD van referits al conjun de fileres (sequencia de fileres) en canvi indexsVar van referits a cada filera individual
        indexsFPD=splitPan.matriudeFuncioInt2(sp);
        //System.out.print("limits de la matriu indexsFPD que conte les posicions de[][0]=dVO i[][1]fila de la funcio parcial amb el resultat de les funcions parcials dependents: ");
        //for(int i=0;i<simbol.length;i++)System.out.print(simbol[i]+" "+limFPD[i][0]+" "+limFPD[i][1]+" _ ");System.out.println("");
        //System.out.print("indexsFPD: format (i indexsFPD[i][0] indexsFPD[i][1]): ");for(int i=0;i<indexsFPD.length;i++)System.out.print("("+i+") "+indexsFPD[i][0]+" "+indexsFPD[i][1]+" _ ");System.out.println("");
        //en aquest punt disposem dels indexs de les funcions parcials previes a les funcio parcials i dels limits d'aquests indexs per cada filera
        //assignarem a les funcions parcials el valor 1 per que en els passos seguents es considerin valors numerics
        //if(Func.tipusFuncioBol){System.out.println("taulaF.indexsDer posterior tres columnes taulaF.indexsDer[possicio parcial dins la funcio derivable][filera de la taula de funcions(simbols reduits)][possicio global a dVO (conjunt de funcions derivables) del simbol resultat de la taulaF]: ");for(i=0;i<taulaF.indexsDer.length;i++)System.out.print(taulaF.indexsDer[i][0]+" "+taulaF.indexsDer[i][1]+" "+taulaF.indexsDer[i][2]+" _ ");System.out.println();}System.out.println();
       /* System.out.print("taulaD sVO: ");for(i=0;i<sVO.length;i++)System.out.print(sVO[i]+" _ ");System.out.println();
        System.out.print("taulaD dVO: ");for(i=0;i<sVO.length;i++)System.out.print(dVO[i]+" _ ");System.out.println();
        System.out.print("derivadesLimFun dVO: ");for(i=0;i<derivadesLimFun.length;i++)System.out.print(derivadesLimFun[i][0]+" "+derivadesLimFun[i][1]+" _ ");System.out.println();
        System.out.print("taulaD indexs: ");for(i=0;i<indexs.length;i++)System.out.print(indexs[i][0]+" "+indexs[i][1]+" "+indexs[i][2]+" _ ");System.out.println();
        System.out.print("derivadesLimFun indexs: ");for(i=0;i<derivadesLimFun.length;i++)System.out.print(derivadesLimFun[i][2]+" "+derivadesLimFun[i][3]+" _ ");System.out.println();
        System.out.print("taulaD indexsVar: ");for(i=0;i<indexsVar.length;i++)System.out.print(i+" "+indexsVar[i][0]+" "+indexsVar[i][1]+" _ ");System.out.println();
        System.out.print("derivadesLimFun indexsVar: ");for(i=0;i<derivadesLimFun.length;i++)System.out.print(derivadesLimFun[i][4]+" "+derivadesLimFun[i][5]+" _ ");System.out.println();
        if(Func.tipusFuncioBol){System.out.print("taulaD taulaF.indexsDer: ");for(i=0;i<taulaF.indexsDer.length;i++)System.out.print(taulaF.indexsDer[i][0]+" "+taulaF.indexsDer[i][1]+" "+taulaF.indexsDer[i][2]+" _ ");System.out.println();}
        */
        matriusCombinades();
        VGaFuncBol=new boolean[simbol.length][Cal.idxVarGen.length];
        for(int i=0;i<simbol.length;i++){
            String[] m=splitPan.arr_sVO_Der.get(i);
            for(int j=0;j<Cal.idxVarGen.length;j++)
                for(int k=0;k<m.length;k++)
                    if(m[k].equals(taulaV.varstaulaV[Cal.idxVarGen[j]]))
                        VGaFuncBol[i][j]=true;
        }
        for(int i=0;i<simbol.length;i++)for(int j=0;j<Cal.idxVarGen.length;j++)if(VGaFuncBol[i][j]){
            for(int k=i+1;k<simbol.length;k++){
                String[] m=splitPan.arr_sVO_Der.get(k);
                for(int l=0;l<m.length;l++)if(m[l].equals(simbol[i]))VGaFuncBol[k][j]=true;
            }
        }
        return true;
    }
    public boolean matriusCombinades(){
        derivadesLimFun=new int[simbol.length][8];
        String s0="",s1="",s2="",s3="",s4="";
        int conta=0,conta1=0,conta2=0,conta3=0;
        for(int l=0;l<simbol.length;l++){
            splitPan.sVO=splitPan.arr_sVO_Der.get(l);
            splitPan.dVO=splitPan.arr_dVO_Der.get(l);
            for(int k=0;k<splitPan.sVO.length;k++){s0+=splitPan.sVO[k]+" ";}
            for(int k=0;k<splitPan.sVO.length;k++){s1+=splitPan.dVO[k]+" ";}
            derivadesLimFun[l][0]=conta;//cota inferio de dVO primera funcio
            conta+=splitPan.dVO.length;
            derivadesLimFun[l][1]=conta;//cota superior de dVO primera funcio
            if(!tabCalcFun())return false;
            for(int n=0;n<Cal.indexs.length;n++){s2 += Cal.indexs[n][0] + " " + Cal.indexs[n][1] + " " + Cal.indexs[n][2] +" ";}
            derivadesLimFun[l][2]=conta1;
            conta1+=Cal.indexs.length;
            derivadesLimFun[l][3]=conta1;
            for(int n=0;n<Cal.indexsVar.length;n++){s3 += Cal.indexsVar[n][0] + " " + Cal.indexsVar[n][1] +" ";}
            derivadesLimFun[l][4]=conta2;
            conta2+=Cal.indexsVar.length;
            derivadesLimFun[l][5]=conta2;
            if(Func.tipusFuncioBol){
                for(int n=0;n<taulaF.indexsDer.length;n++){
                    if(taulaF.indexsDer[n][0]>=0)taulaF.indexsDer[n][0]+=conta3;
                    else taulaF.indexsDer[n][0]=taulaF.indexsDer[n][0]-conta3;
                    s4 += taulaF.indexsDer[n][0] + " " + taulaF.indexsDer[n][1] +" ";}
                conta3+=splitPan.dVO.length;
                s4+=splitPan.FIL;
            }
        }
        sVO=splitPan.matriudeFuncioCompleta(s0);
        Cal.supID.dVO=splitPan.matriudeFunciodouble(s1);
        indexs=splitPan.matriudeFuncioInt3(s2);//if(indexs==null)return false;
        indexsVGaD=splitPan.matriudeFuncioInt2(s3);//if(indexsVar==null)return false;
        if(Func.tipusFuncioBol){taulaF.indexsDer=splitPan.matriudeFuncioInt2(s4);if(taulaF.indexsDer==null)return false;}
        if(Func.tipusFuncioBol){
            taulaF.varDerivadaBol=new boolean[taulaF.simbolsReduits.length];
            for(int k=0;k<taulaF.simbolsReduits.length;k++)if(!taulaF.simbolsReduitsNombreBol[k])for(int m=0;m<sVO.length;m++){
                if(sVO[m].equals(taulaF.simbolsReduits[k])||sVO[m].equals("-"+taulaF.simbolsReduits[k])){
                    taulaF.varDerivadaBol[k]=true;
                    m=sVO.length;
                }
            }
        }
        return true;
    }
    //calcul de les funcions dvo i svo, indexsVar i indexs operacions de les funcions parcials
    //aqui no s'inclouen parametres variables ni integrals ni funcions parcials
    public boolean tabCalcFun(){
        int con=splitPan.dVO.length;
        int co=0;
        for(int k=0;k<Cal.idxVarGen.length;k++){
            int i=Cal.idxVarGen[k];for(int m=0;m<con;m++){
                if(splitPan.sVO[m].equals(taulaV.varstaulaV[i])||splitPan.sVO[m].equals("-"+taulaV.varstaulaV[i])){splitPan.dVO[m]=1.0;co++;}
            }
        }
        int contador=splitPan.nombrefilesmatriuIndexs(splitPan.sVO);
        Cal.indexsVar=new int[co][3];
        co=0;
        for(int k=0;k<Cal.idxVarGen.length;k++){
            int i=Cal.idxVarGen[k];for(int m=0;m<con;m++){
                if(splitPan.sVO[m].equals(taulaV.varstaulaV[i])||splitPan.sVO[m].equals("-"+taulaV.varstaulaV[i])){
                    Cal.indexsVar[co][0]=m;//posicio filera dVO
                    Cal.indexsVar[co][1]=k;//index de la variable general
                    co++;
                }
            }
        }
        if(Func.tipusFuncioBol){
            co=0;//
            for(int k=0;k<taulaF.simbolsReduits.length;k++)if(!taulaF.simbolsReduitsNombreBol[k])for(int m=0;m<con;m++){
                    if(splitPan.sVO[m].equals(taulaF.simbolsReduits[k])||splitPan.sVO[m].equals("-"+taulaF.simbolsReduits[k])){splitPan.dVO[m]=1.0;co++;}
            }
            taulaF.indexsDer=new int[co][2];
            co=0;
            for(int k=0;k<taulaF.simbolsReduits.length;k++)if(!taulaF.simbolsReduitsNombreBol[k])for(int m=0;m<con;m++){
                if(splitPan.sVO[m].equals(taulaF.simbolsReduits[k])){
                    taulaF.indexsDer[co][0]=m;//posicio filera dVO
                    taulaF.indexsDer[co][1]=k;//filera de la taulaF que conte el simbol del resultat de la funcio
                    co++;
                }
                else if(splitPan.sVO[m].equals("-"+taulaF.simbolsReduits[k])){
                    taulaF.indexsDer[co][0]=-m-1;//posicio filera dVO
                    taulaF.indexsDer[co][1]=k;//filera de la taulaF que conte el simbol del resultat de la funcio
                    co++;
                }
            }
        }
        Cal.indexs=new int[contador][3];
        String[] sV=new String[con];System.arraycopy(splitPan.sVO, 0, sV, 0, con);
        Double[] dV=new Double[con];System.arraycopy(splitPan.dVO, 0, dV, 0, con);
        splitPan.calcIndexFunc(sV,dV);
        return true;
    }
    private  boolean moufilBuides(){
        boolean correcte=false;
        for(int i=1;i<matriu.length;i++){
            boolean b=false;
            for(int j=1;j<matriu[0].length;j++)if(!matriu[i][j].equals(""))b=true;
            if(!b){
                correcte=true;
                tl.setRowSelectionInterval(i-1, i-1);
                menuItemFil.doClick();
                i--;
            }
            if(matriu.length<=2)i=matriu.length;
        }
        return correcte;
    }
    public void nouModel(){
        Func.analisiPreviBol=false;//modificat=true;
        int cc=tl.getColumnCount();
        int[] cols=new int[cc];
        for(int i=0;i<cc;i++)cols[i]=tl.getColumnModel().getColumn(i).getWidth();
        tl.setModel(new model());
        for(int i=1;i<matriu.length;i++)for(int j=0;j<matriu[0].length;j++)tl.setValueAt((Object)matriu[i][j], i-1, j);
        int i=tl.getRowCount();
        for(int j=0;j<i;j++)tl.setRowHeight( j, 18 );
        for(i=0;i<cc;i++)tl.getColumnModel().getColumn(i).setPreferredWidth(cols[i]);
        splitPan.tTP.repaint();
        color();
        editaCela();
    }
     public void color(){
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
                    for(int i=0;i<5;i++)if (column == i)lb.setBackground(gmh);
                return lb;
                }
        });
        for(int i=0;i<5;i++){
            tl.getColumn(tl.getColumnName(i)).setCellRenderer(new DefaultTableCellRenderer() {
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    DefaultTableCellRenderer r = (DefaultTableCellRenderer) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if(!isSelected){
                        if(column==0){
                            if ((row % 2) != 0) r.setBackground(gh);
                            else r.setBackground(gmh);
                        }
                        else{
                            if ((row % 2) != 0) r.setBackground(gc);
                            else r.setBackground(gmc);
                        }
                    }  
                    return r;
                }   
            });
        }
    }    
    private void capsalInteg(){
        matriu[0][0] = Func.increm+"\u207F"+Func.rB.getString("simbolfuncio")+"/"+Func.increm+"x"+"\u207F";
        matriu[0][1] = "x";
        matriu[0][2] = Func.increm+"x";
        matriu[0][3] = "n";
        matriu[0][4] = Func.rB.getString("simbolfuncio");
        for(int i=1;i<matriu.length;i++){int i0=i-1;matriu[i][0]=Func.increm+i0;}
    }
    public void valorsInicials(){
        if(matriu==null){reiniciParcial();}
    	else{try{capsalInteg();}catch(Exception e){reiniciParcial();}}
        hihaDades=false;
    }
    private void reiniciParcial(){
        matriu=new String[2][5];
        capsalInteg();
        matriu[1][0]=Func.increm+"0";;
        matriu[1][1]="";
        matriu[1][2]="";
        matriu[1][3]="";
        matriu[1][4]="";
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
