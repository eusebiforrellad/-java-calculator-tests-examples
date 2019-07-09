package Calc;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static java.lang.Double.NaN;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.KeyAdapter;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import static java.lang.Double.isFinite;
public class taulaI extends JPanel implements ActionListener{
    private static final long serialVersionUID = 1L;
    public static boolean hihaDades=false;
    public static String[] varInternes;//conte les variables de integrals 
    public static String[] sumat;//simbols de la capçalera dels sumatoris
    public static JTextField cela;
    public static String [][] matriu;//matriu  mante la mateixa configuracio que la taula tl i inclou les capçaleres
    public static String [][] matriuFunc;//matriuFunc mante la mateixa configuracio que la taula tlF i inclou les capçaleres
    public static String [][] matriuVint;//String amb simbol del segmentIntegral + intervals=suportID.matriuInt + suportID.ceroLog;  suportID.matriuInt: indica el nombre de intervals entre limits de la integral,(segment/aquest numero)  es el primer valor numeric positiu de la primera de cada tres columnes de matriu,
    public static String [][] matriuInt;
    static JTable tl;
    static JTable tlF;
    static String [][] matriuSup;
    static String [][] matriuFuncSup;
    static int filValSelec;
    static boolean[] integralFuncioBol;//indica si  la funcio integral superior e un valor numeric o funcio amb eventualment variable
    JMenuItem menuItemCol;
    static boolean[][] integralLimitsBuitBol;// true si hi ha informacio a limits inferior i superior; inicialment integrallimitBuitBol s'utilitza per descartar parelles liminf lims erronis despres per identificar parelles buides
    public static JCheckBoxMenuItem cbM = new JCheckBoxMenuItem("tipus ajust columnes");
    public static JCheckBoxMenuItem cbM1 = new JCheckBoxMenuItem("tipus ajust columnes");
    public static int scrollCols;
    public static boolean actualitzar;
    static boolean margesBol;
    static int[] cols;
    static int[] colsF;
    public static boolean[] sumatConstants;
    static JMenuItem  jmi_copiSel, jmi_ajuntaSel, jmi_copi,jmi_cut, jmi_ajunta,jmi_carregar,jmi_save,jmi_treuFil,jmi_treuCol,jmi_insertaFil,jmi_insertaCol,jmi_saveAs,jmi_reinici;
    static JMenuItem  jmi_copiSel1, jmi_ajuntaSel1, jmi_copi1,jmi_cut1, jmi_ajunta1,jmi_carregar1,jmi_save1,jmi_treuCol1,jmi_insertaCol1,jmi_saveAs1,jmi_reinici1;
    static JTableHeader JThd,JThd1;
    public taulaI() {
        super(new GridLayout(1,0));
        valorsInicials();
        add (panel());
    }
    public taulaI(boolean x){}
    public void INI(){
        cela = new JTextField();
        cela.setBorder(Func.border);
        cela.addKeyListener(new KeyAdapter() {
           @Override
           public void keyTyped(KeyEvent e) {Func.analisiPreviBol=false;}
        });
        cela.addMouseListener(createPopupMenu1());
    }
    public Container panel() {
        Container pane=new Container();
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
        tlF = new JTable(new modelI()){
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);
                try {if(Func.tooltiptxt)tip = getValueAt(rowIndex, colIndex).toString();} catch (RuntimeException e1) {}
                return tip;
            }
        };
        tl.setPreferredScrollableViewportSize(new Dimension(100, 80));
        tlF.setPreferredScrollableViewportSize(new Dimension(300, 80));
        tl.setRowSelectionAllowed(true);
        tl.setColumnSelectionAllowed(true);
        tlF.setColumnSelectionAllowed(true);
        tl.getTableHeader().setReorderingAllowed(false);
        tlF.getTableHeader().setReorderingAllowed(false);
        tl.putClientProperty("terminateEditOnFocusLost", true);
        tlF.putClientProperty("terminateEditOnFocusLost", true);
        setOpaque(true);
        JScrollPane scrollP0 = new JScrollPane(tlF);
        JScrollPane scrollP1 = new JScrollPane(tl);
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.ipadx = 15;
        c.ipady = 15;//augmenta o disminueix la alçada de la taula funcio
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(scrollP0, c);
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.PAGE_END;
        pane.add(scrollP1, c);
        tl.addMouseListener(createPopupMenu(true));
        tlF.addMouseListener(createPopupMenu(false));
        editaCela();
        tl.getColumnModel().addColumnModelListener(new TableColumnModelListener(){
            public void columnMarginChanged(ChangeEvent e){
                if (margesBol){
                    int conta=0;
                    boolean b=false;
                    while(!b){
                        conta++;
                        int[] col=new int[cols.length];
                        for(int i=0;i<cols.length;i++)col[i]=tl.getColumnModel().getColumn(i).getWidth();
                        boolean b1=false;
                        for(int i=0;i<cols.length;i++)if(col[i]!=cols[i])b1=true;
                        if(b1)for(int i=0;i<cols.length;i++)tl.getColumnModel().getColumn(i).setWidth(cols[i]);
                        else b=true;
                        if (conta>200) b=true;
                    }
                     margesBol=false;
                     return;
                }
                TableColumnModel tc = tl.getTableHeader().getColumnModel();
                int i = tc.getColumnCount();
                int[] l=new int[i/3];
                for(int j =0;j<i; j++) l[(int)(j/3)]+=tc.getColumn(j).getWidth();
                tc = tlF.getTableHeader().getColumnModel();
                for(int j = 0; j < l.length; j++)tc.getColumn(j).setPreferredWidth(l[j]);
            }
            @Override public void columnAdded(TableColumnModelEvent e) {}@Override public void columnRemoved(TableColumnModelEvent e) {}@Override public void columnMoved(TableColumnModelEvent e) {} @Override public void columnSelectionChanged(ListSelectionEvent e) {}
        });
        tlF.getColumnModel().addColumnModelListener(new TableColumnModelListener(){
            public void columnMarginChanged(ChangeEvent e){
                TableColumnModel tc = tlF.getTableHeader().getColumnModel();
                int i = tc.getColumnCount();
                int[] l=new int[i*3];
                for(int j=0;j<i;j++)for(int k=0;k<3;k++)l[j*3+k]=tc.getColumn(j).getWidth()/3;
                tc = tl.getTableHeader().getColumnModel();
                for(int j = 0; j < l.length; j++)tc.getColumn(j).setPreferredWidth(l[j]);
            }
            @Override public void columnAdded(TableColumnModelEvent e) {}@Override public void columnRemoved(TableColumnModelEvent e) {}@Override public void columnMoved(TableColumnModelEvent e) {} @Override public void columnSelectionChanged(ListSelectionEvent e) {}
        });
        tl.setAutoResizeMode(scrollCols);
        tlF.setAutoResizeMode(scrollCols);
        color();
        return pane;
    }
    private void editaCela() {
        TableColumnModel t = tl.getColumnModel();
        for(int i=0;i<matriu[0].length;i++)t.getColumn(i).setCellEditor(new DefaultCellEditor(cela));
        t = tlF.getColumnModel();
        for(int i=0;i<matriuFunc[0].length;i++)t.getColumn(i).setCellEditor(new DefaultCellEditor(cela));
    }
    public MouseListener createPopupMenu1() {
        JMenuItem menuItem;
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
    public MouseListener createPopupMenu(boolean b) {
        JMenuItem menuItem;
        JPopupMenu popup = new JPopupMenu();
        if(b){
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
             jmi_treuFil = new JMenuItem("treu filera"); jmi_treuFil.setIcon(Func.Treulinia());
             jmi_treuFil.addActionListener(this);
            popup.add( jmi_treuFil);
            jmi_insertaCol = new JMenuItem("inserta columnes ");jmi_insertaCol.setIcon(Func.Novacolumna());
            jmi_insertaCol.addActionListener(this);
            popup.add(jmi_insertaCol);
            jmi_treuCol = new JMenuItem("treu columnes ");jmi_treuCol.setIcon(Func.Treucolumna());
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
            popup.add(cbM);if(scrollCols==1){cbM.setState(true);}else{cbM.setState(false);}//cbM.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
            cbM1.setState(cbM.getState());
        }
        else{
            jmi_copi1 = new JMenuItem("copiar ");jmi_copi1.setIcon(Func.Copiar());
            jmi_copi1.addActionListener(this);
            popup.add(jmi_copi1);
            jmi_cut1= new JMenuItem("cut ");jmi_cut1.setIcon(Func.Copiar());
            jmi_cut1.addActionListener(this);
            popup.add(jmi_cut1);
            jmi_ajunta1 = new JMenuItem("ajuntar a cel.la ");jmi_ajunta1.setIcon(Func.Engan());
            jmi_ajunta1.addActionListener(this);
            popup.add(jmi_ajunta1);
            popup.addSeparator();
            jmi_insertaCol1 = new JMenuItem("inserta columnes ");jmi_insertaCol1.setIcon(Func.Novacolumna());
            jmi_insertaCol1.addActionListener(this);
            popup.add(jmi_insertaCol1);
            jmi_treuCol1 = new JMenuItem("treu columnes ");jmi_treuCol1.setIcon(Func.Treucolumna());
            jmi_treuCol1.addActionListener(this);
            popup.add(jmi_treuCol1);
            popup.addSeparator();
            jmi_reinici1 = new JMenuItem("reiniciar taula");jmi_reinici1.setIcon(Func.Borrar());
            jmi_reinici1.addActionListener(this);
            popup.add(jmi_reinici1);
            popup.addSeparator();
            jmi_carregar1 = new JMenuItem("carregar dades");jmi_carregar1.setIcon(Func.Obrirarx());
            jmi_carregar1.addActionListener(this);
            popup.add(jmi_carregar1);
            jmi_save1 = new JMenuItem("guardar dades");jmi_save.setIcon(Func.Guarda());
            jmi_save1.addActionListener(this);
            popup.add(jmi_save);
            jmi_saveAs1 = new JMenuItem("guardar (save as)");jmi_saveAs1.setIcon(Func.GuardaC());
            jmi_saveAs1.addActionListener(this);
            popup.add(jmi_saveAs1);
            popup.add(cbM1);if(scrollCols==1){cbM1.setState(true);}else{cbM1.setState(false);}
            cbM.setState(cbM1.getState());
        }
        
        MouseListener ml = new TaulesPopup(popup);
        return ml;
    }
    /*
            AUTO_RESIZE_OFF: Don't automatically adjust the column's widths at all. Use a horizontal scrollbar to accomodate the columns when their sum exceeds the width of the Viewport. If the JTable is not enclosed in a JScrollPane this may leave parts of the table invisible.
            AUTO_RESIZE_NEXT_COLUMN: Use just the column after the resizing column. This results in the "boundary" or divider between adjacent cells being independently adjustable.
            AUTO_RESIZE_SUBSEQUENT_COLUMNS: Use all columns after the one being adjusted to absorb the changes. This is the default behavior.
            AUTO_RESIZE_LAST_COLUMN: Automatically adjust the size of the last column only. If the bounds of the last column prevent the desired size from being allocated, set the width of the last column to the appropriate limit and make no further adjustments.
            AUTO_RESIZE_ALL_COLUMNS: Spread the delta amongst all the columns in the JTable, including the one that is being adjusted. 
    */
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        String s = source.getText();
        if(s.equals("inserta filera"))if(contingutTaula(tl.getSelectedRows(),true))nouModel(true);
        if(s.equals("treu filera"))if(contingutTaula(tl.getSelectedRows(),false))nouModel(true);
        if (s.equals("inserta columnes"))if(contingutTaulaCol(tl.getSelectedColumn(), true))nouModel(false);
        if (s.equals("treu columnes"))if( contingutTaulaCol(tl.getSelectedColumn(), false))nouModel(false); 
        if (s.equals("inserta columnes "))if(contingutTaulaCol(3*tlF.getSelectedColumn(), true))nouModel(false);
        if (s.equals("treu columnes "))if(contingutTaulaCol(3*tlF.getSelectedColumn(), false))nouModel(false);
        if ("copiar".equals(e.getActionCommand())) {splitPan.cop(tl,matriu,false);return;}
        if ("cut".equals(e.getActionCommand())) {splitPan.cop(tl,matriu,true);return;}
        if(s.equals("ajuntar a cel.la")){Func.analisiPreviBol=false;ajun(tl,true);return;}
        if ("copiar ".equals(e.getActionCommand())) {splitPan.cop(tlF,matriuFunc,false);return;}
        if ("cut ".equals(e.getActionCommand())) {splitPan.cop(tlF,matriuFunc,true);return;}
        if(s.equals("ajuntar a cel.la ")){Func.analisiPreviBol=false;ajun(tlF,false);return;}
        if ("copia sel.".equals(e.getActionCommand())) {splitPan.cop_sel(cela);return;}
        if(s.equals("ajunta sel.")){splitPan.paste_sel(cela);}
        if(s.equals("guardar dades")){splitPan.guardarArx();}
        if(s.equals("guardar (save as)")){splitPan.guardarArxCom();}
        if(s.equals("carregar dades")){splitPan.carregaArx();}
        if(s.equals("reiniciar taula")){ reinici();}
    }
    public static void font(){
        Font fo = new Font(font.tipus, font.estil, font.tamany);
        cbM.setFont(fo);cbM1.setFont(fo);
        jmi_copiSel.setFont(fo); 
        jmi_ajuntaSel.setFont(fo);
        jmi_copi.setFont(fo);jmi_copi1.setFont(fo);
        jmi_cut.setFont(fo);jmi_cut1.setFont(fo);
        jmi_ajunta.setFont(fo); jmi_ajunta1.setFont(fo);
        jmi_treuFil.setFont(fo);
        jmi_treuCol.setFont(fo); jmi_treuCol1.setFont(fo);
        jmi_insertaFil.setFont(fo);
        jmi_insertaCol.setFont(fo);jmi_insertaCol1.setFont(fo);
        jmi_reinici.setFont(fo);jmi_reinici1.setFont(fo);
        jmi_carregar.setFont(fo); jmi_carregar1.setFont(fo);
        jmi_save.setFont(fo);jmi_save1.setFont(fo);
        jmi_saveAs.setFont(fo);jmi_saveAs1.setFont(fo);
        JThd.setFont(fo);JThd1.setFont(fo);
  }
    public void reinici(){ matriu=null;valorsInicials();nouModel(false);}
    private  void ajun(JTable t,boolean bol){//si bol es false es tracta de les funcion superiors
        String[][] m=splitPan.paste();
        if(!bol&&m.length>1){Func.append(1,"error: ");Func.append(0,"el nombre de fileres a funcions principals de la taula sumatoris es limita a una filera la copia pot no esser completa;"+ splitPan.FIL);return;}
        int row=t.getSelectedRow();int col=t.getSelectedColumn();
        boolean[] b=new boolean[2];
        if(bol&&matriu.length<row+m.length+1){//l'increment de fileres nomes afecten a la taula de limits si bol es true no cal comprobar fileres
            b[0]=true;
            matriuSup=splitPan.copiaMatriu(matriu);
            matriu=new String[row+m.length+1][matriu[0].length];
            for(int i=0;i<matriuSup.length;i++)for(int j=0;j<matriuSup[0].length;j++)matriu[i][j]=matriuSup[i][j];
            for(int i=matriuSup.length;i<matriu.length;i++)for(int j=0;j<matriu[0].length;j++)matriu[i][j]="";
        }
        if(bol&&matriu[0].length<col+m[0].length||!bol&&matriuFunc[0].length<col+m[0].length&&m.length<2){
            b[1]=true;
            double d=col+m[0].length;if(bol)d=Math.ceil(d/3);int col3=(int)d;
            matriuSup=splitPan.copiaMatriu(matriu);
            matriu=new String[matriu.length][col3*3];
            for(int i=0;i<matriuSup.length;i++)for(int j=0;j<matriuSup[0].length;j++)matriu[i][j]=matriuSup[i][j];
            for(int i=0;i<matriu.length;i++)for(int j=matriuSup[0].length;j<matriu[0].length;j++)matriu[i][j]="";
            matriuFuncSup=splitPan.copiaMatriu(matriuFunc);
            matriuFunc=new String[matriuFunc.length][col3];
            for(int i=0;i<matriuFuncSup.length;i++)for(int j=0;j<matriuFuncSup[0].length;j++)matriuFunc[i][j]=matriuFuncSup[i][j];
            for(int i=0;i<matriuFunc.length;i++)for(int j=matriuFuncSup[0].length;j<matriuFunc[0].length;j++)matriuFunc[i][j]="";
        }
        if(b[0]||b[1]){capsalInteg();capsalInteg1();nouModel(true);}
        if(bol){for(int i=0;i<m.length;i++)for(int j=0;j<m[0].length;j++)tl.setValueAt(m[i][j], row+i,col+j);}
        else{for(int j=0;j<m[0].length;j++)tlF.setValueAt(m[0][j], row,col+j);}
        capsalInteg();capsalInteg1();
        return;
    }
    public boolean contingutTaulaCol(int col, boolean bol) {
        int colI=col/3;
        col=colI*3;
        int cc = tl.getColumnCount();
        int cf = tl.getRowCount() + 1;
        int ccI = tlF.getColumnCount();
        matriusVar();
        try{
        if (bol) {
            if (col >= 0) {//inserta columna
                matriu = new String[cf][cc + 3];
                for (int i = 1; i < cf; i++) {
                    for (int j = 0; j < col+3; j++) {matriu[i][j] =matriuSup[i][j];}
                    matriu[i][col+3] = "";matriu[i][col + 4] = "";matriu[i][col + 5] = "";
                    for (int j = col+6; j < matriu[0].length; j++) {matriu[i][j] = matriuSup[i][j-3];}
                }
                capsalInteg();
                matriuFunc = new String[2][ccI + 1];
                for (int j = 0; j < colI + 1; j++) {matriuFunc[1][j] =matriuFuncSup[1][j];}
                matriuFunc[1][colI + 1] = "";
                for (int j = colI + 1; j < ccI; j++) {matriuFunc[1][j + 1] = matriuFuncSup[1][j];}
                capsalInteg1();
                return true;
            }
        } else {
            if (cc > 3) {//treu columna
                matriu = new String[cf][cc - 3];
                for (int i = 1; i < cf; i++) {
                    for (int j = 0; j < col; j++) {matriu[i][j] = matriuSup[i][j];}
                    for (int j = col; j < matriu[0].length; j++) { matriu[i][j] = matriuSup[i][j+3];}
                }
                capsalInteg();
                matriuFunc = new String[2][ccI - 1];
                for (int j = 0; j < colI; j++) {matriuFunc[1][j] = matriuFuncSup[1][j];}
                for (int j = colI + 1; j < ccI; j++) { matriuFunc[1][j - 1] = matriuFuncSup[1][j];}
                capsalInteg1();
                return true;
            }
        }
        }
        catch(Exception e){
            matriusVarSup();
            capsalInteg1();
        }
        return false;
    }
    public boolean contingutTaula(int[] fil,boolean bol){
        int cc=tl.getColumnCount();
    	int cf=tl.getRowCount()+1;
        matriusVar();
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
                    for(int i=fi+1;i<cf;i++){matriu[i+1][j]=matriuSup[i][j];}
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
    private  void matriusVar(){
        matriusVar1();
        matriuFuncSup=new String[2][matriuFunc[0].length];
        for(int i=0;i<matriuFunc.length;i++){
            System.arraycopy(matriuFunc[i], 0, matriuFuncSup[i], 0, matriuFunc[0].length);
        }
    }
    private  void matriusVar1(){
        matriuSup=new String[matriu.length][matriu[0].length];
        for(int i=0;i<matriu.length;i++){
            System.arraycopy(matriu[i], 0, matriuSup[i], 0, matriu[0].length);
        }
    }
    private  void matriusVarSup(){
        matriusVar1Sup();
        matriuFunc=new String[2][matriuFuncSup[0].length];
        for(int i=0;i<matriuFunc.length;i++){
            System.arraycopy(matriuFuncSup[i], 0, matriuFunc[i], 0, matriuFunc[0].length);
        }
    }
    private  void matriusVar1Sup(){
        matriu=new String[matriuSup.length][matriuSup[0].length];
        for(int i=0;i<matriu.length;i++){
            System.arraycopy(matriuSup[i], 0, matriu[i], 0, matriu[0].length);
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
        for(int i=0;i<cc;i++){
            tl.getColumnModel().getColumn(i).setWidth(amp[i]);
        }
    }
    public  boolean matriuTaula(){//cal coneixer els parametres fixos
        varInternes=new String[0];
        hihaDades=false;
        for(int j=0;j<matriuFunc[0].length;j++)matriuFunc[1][j]=matriuFunc[1][j].trim();
        for(int j=0;j<matriu[0].length;j++)for(int i=1;i<matriu.length;i++)matriu[i][j]=matriu[i][j].trim();
        boolean b1=false;
        if(matriu[0].length>3)b1=mouColBuides();
        if(b1){Func.append(1,"info: ");Func.append(0,"a taula sumatoris s'han eliminat columnes buides"+ splitPan.FIL);}
        boolean[] b=new boolean[2];
        if(matriu.length>2)b=moufilBuides();
        if(b[0]){Func.append(1,"info: ");Func.append(0,"a taula sumatoris s'han eliminat o modificat fileres total o parcialment buides comprobar els canvis; aquests poden afectar variables internes o intervals que no estiguesin especificament definits"+ splitPan.FIL);return false;}
        if(b[1])return false;
        boolean correcte=true;
        b1=false;
        for(int j=0;j<matriuFunc[0].length;j++){
            if(!matriuFunc[1][j].equals(""))hihaDades=true;
            else b1=true;
        }
        if(b1&&hihaDades){Func.append(1,"info: ");Func.append(0,"a taula sumatoris les funcions han de tenir contingut"+ splitPan.FIL);correcte=false;}
        integralLimitsBuitBol=new boolean[matriuFunc[0].length][matriu.length-1];
        matriuVint=new String[matriu.length-1][matriuFunc[0].length];
        matriuInt=new String[matriuVint.length][matriuVint[0].length];
        suportID.integralVaraLimitsBol=new boolean[matriuFunc[0].length][matriuVint.length][3];
        suportID.ceroLog=new double[matriuVint.length][matriuFunc[0].length];
        suportID.logaritmeBol=new boolean[matriuVint.length][matriuFunc[0].length];
        suportID.matriuInt=new int[matriuVint.length][matriuFunc[0].length];
        boolean b2=false,b3=false;
        for(int i=0;i<matriuFunc[0].length;i++){
            for(int j=1;j<matriu.length;j++){
                b1=false;b2=false;b3=false;
                int k=i*3;
                if(!matriu[j][k].equals("")){b1=true;hihaDades=true;}
                if(!matriu[j][k+1].equals("")){b2=true;hihaDades=true;}
                if(!matriu[j][k+2].equals("")){b3=true;hihaDades=true;}
                if(!b2&&!b3)integralLimitsBuitBol[i][j-1]=true;
                if(b1&&integralLimitsBuitBol[i][j-1]){Func.append(1,"error: ");Func.append(0,Func.sumat+i+": a taula sumatoris les dues cel.les l"+Func.rB.getString("i_")+"mit han de tenir contingut;"+splitPan.FIL);return false;}
                if(b2!=b3){Func.append(1,"error: ");Func.append(0,Func.sumat+i+": a taula sumatoris les dues cel.les l"+Func.rB.getString("i_")+"mit han de tenir contingut o les dues buides;"+splitPan.FIL);return false;}
            }
        }
        if(!correcte)return false;
        if(!hihaDades){matriuVint=new String[0][0];return true;}
        sumat=new String[matriuFunc[0].length];
        sumatConstants=new boolean[sumat.length];
        for(int i=0;i<matriuFunc[0].length;i++)sumat[i]=Func.sumat+i;
        boolean[][][] bol=analisiVariablesInternes();
        if(bol==null)return false;
        boolean bvi=false,bint=false;
        for(int i=0;i<integralLimitsBuitBol[0].length;i++)for(int j=0;j<integralLimitsBuitBol.length;j++){
           if(!integralLimitsBuitBol[j][i]){//la primera columna de cada fila que te els limits amb informacio integralLimitsBuitBol[i][j]=true; ha de contenir el simbol de la variable interna
               if(!bol[i][j][0]){Func.append(1,"error: ");Func.append(0," a taula: "+ Func.sumat+": al menys la cel.la del extrem esquerra amb contingut a limits; ha de tenir definida una variable interna; per la resta es copiar"+Func.rB.getString("a")+" la de la seva esquerra"+splitPan.FIL);return false;}
           }
        }
        for(int i=0;i<bol.length;i++)for(int j=0;j<bol[0].length;j++){
            if(!bol[i][j][0]){         
                if(j>0&&!integralLimitsBuitBol[j][i]){
                    matriuVint[i][j]=matriuVint[i][j-1];
                    bol[i][j][0]=true;
                    bvi=true;
                }
            }
            if(!bol[i][j][1]&&bol[i][j][0]){
                if(i==0&&j==0){
                    Func.append(1,"avis: ");Func.append(0,Func.sumat+j+":per la primera cel.la el nombre d intervals no esta definit i s'estimar"+Func.rB.getString("a")+"."+splitPan.FIL+"Per la resta de columnes es pren de les cel.les superiors o de la situada a la esquerra"+splitPan.FIL);
                    if(bol.length<2)suportID.matriuInt[0][0]=10000;
                    else if(bol.length<3)suportID.matriuInt[0][0]=250;
                    else if(bol.length<4)suportID.matriuInt[0][0]=50;
                    else suportID.matriuInt[0][0]=(int)Math.pow(1E6, (1/bol.length));
                    if (suportID.matriuInt[0][0]<5)suportID.matriuInt[0][0]=5;
                    matriuInt[i][j]=""+suportID.matriuInt[i][j];
                    matriu[i+1][j*3]=matriuVint[i][j]+" "+matriuInt[i][j];
                    if(bol[i][j][2])matriu[i+1][j*3]+=" "+suportID.ceroLog[i][j];
                    tl.setValueAt((Object)matriu[i+1][j*3], i, j*3);
                }
                else{
                    if(i>0){
                        matriuInt[i][j]=matriuInt[i-1][j];
                        suportID.matriuInt[i][j]=suportID.matriuInt[i-1][j];
                    }
                    else{
                        matriuInt[i][j]=matriuInt[i][j-1];
                        suportID.matriuInt[i][j]=suportID.matriuInt[i][j-1];
                    }
                    bint=true;
                }
             }
        }
        if(bvi){Func.append(1,"avis: ");Func.append(0,"a taula: "+ Func.sumat+":  hi ha variables internes no definides es prenen els s"+Func.rB.getString("i_")+"mbols de les cel.les situades a la esquerra"+splitPan.FIL);}
        if(bint){Func.append(1,"avis: ");Func.append(0,"a taula: "+ Func.sumat+": hi ha intervals no definits es prenen els valors de les cel.les superiors o de les situades a la esquerra"+splitPan.FIL);}
        if(!compatibilitatVariablesInternesFuncions())return false;
        String s="";
        for(int i=0;i<matriuVint.length;i++)for(int j=0;j<matriuVint[0].length;j++)if(matriuVint[i][j]!=null)s+=matriuVint[i][j]+" ";
        varInternes=splitPan.matriudeFuncio(splitPan.treuSimbolsIdentics(s));
        return correcte;
    }
    private boolean[][][] analisiVariablesInternes(){
        boolean[][][] bol=new boolean[matriuVint.length][matriuVint[0].length][3];
        for(int j=0;j<matriuVint[0].length;j++)for(int i=0;i<matriuVint.length;i++){
            String[] s=splitPan.matriudeFuncio(matriu[i+1][j*3]);
            int index=s.length-1;
            boolean b=false;
            String s1="";
            String error="comprobar que la cel.la de variable interna, es (com a m"+Func.rB.getString("a")+"xim tres cadenes) estan separades per un blanc; cadenes: (1._ variable interna, 2._ interval: sencer o funci"+Func.rB.getString("o_")+" (amb eventualment nomes par"+Func.rB.getString("a")+"metres) i 3._  nom"+Func.rB.getString("e_")+"s si el punt 2.-  s'ha definit, nombre double negatiu(cero de escala logaritmica))"+splitPan.FIL;
            if(index>2){Func.append(1,Func.error+Func.sumat+j+" filera: "+(i+1)+";  ");Func.append(0,error);return null;}
            if(index>0){
                try{
                    suportID.ceroLog[i][j]=(double)Double.parseDouble(splitPan.comApun(s[index]));//prova si la cadena de la dreta es numerica i negativa
                    if(suportID.ceroLog[i][j]<0){
                        if(suportID.ceroLog[i][j]<-323.3062153431158){{Func.append(1,Func.error+Func.sumat+j+" filera: "+(i+1)+";  ");Func.append(0,error);}Func.append(0,"escala logar"+Func.rB.getString("i_")+"tmica, interval v"+Func.rB.getString("a")+"lid: = -0.0>x>-323,306 "+splitPan.FIL);return null;}
                        suportID.logaritmeBol[i][j]=true;
                        bol[i][j][2]=true;index--;
                    }
                    else{//si es un nombre positiu no pot estar situat en la tercera possicio a la dreta
                        if(s.length>2){Func.append(1,Func.error+Func.sumat+j+" filera: "+(i+1)+";  ");Func.append(0,error);return null;}
                        suportID.matriuInt[i][j]=(int)suportID.ceroLog[i][j];
                        matriuInt[i][j]=""+suportID.matriuInt[i][j];
                        bol[i][j][1]=true;index--;
                    }
                }
                catch(NumberFormatException e1){}
                if(bol[i][j][2]||!bol[i][j][1]&&!bol[i][j][2]){//no es un valor numeric hi ha dos simbols
                    if(!splitPan.modificacionsInicials(s[index],"a taula Sumatoris cel.la de variable interna_interval, sumatori: "+Func.sumat+j+" filera: "+(i+1)+", "))return null;
                    if(!s[index].equals(splitPan.funcSup[0])){
                        s[index]=splitPan.funcSup[0];
                        String cad="";for(int o=0;o<s.length;o++)cad+=s[o]+" ";
                        tl.setValueAt((Object)cad.trim(), i, j*3);Func.append(1,Func.avis);Func.append(0,"s'ha modificat la funci"+Func.rB.getString("o_")+": interval de "+Func.sumat+j+" filera: "+(i+1)+" funci"+Func.rB.getString("o_")+": "+s[index]+ splitPan.FIL);
                    }
                    String[] m=splitPan.matriuPariVardeFuncio("a taula sumatoris variables internes_interval, sumatori: "+Func.sumat+j+" filera: "+(i+1)+", ");if(m==null)return null;
                    for(int o=0;o<taulaP.simParCapUnaFil.length;o++)for(int p=0;p<m.length;p++)if(m[p].equals(taulaP.simParCapUnaFil[o]))splitPan.simBolPC0[o]=true;
                    int con=0;
                    s1=splitPan.funcioTabulada(s[index],taulaP.simParCapUnaFil);
                    con=splitPan.calcFuncioTab();
                    if(con!=1||!isFinite((int)(double)splitPan.dVO[0])){Func.append(1,Func.error);Func.append(0,"a "+Func.sumat+j+", el interval fil:"+(i+1)+" no es pot reduir a un valor num"+Func.rB.getString("e_")+"ric"+splitPan.FIL);return null;}
                    suportID.matriuInt[i][j]=(int)(double)splitPan.dVO[0];
                    matriuInt[i][j]=""+s1;
                    if(!s1.equals(s[index])){b=true;Func.append(0," "+s1);}
                    bol[i][j][1]=true;
                    index--;
                }
            }
            if(index==0){
                matriuVint[i][j]=s[index];
                if(!splitPan.esSimVar(matriuVint[i][j]).equals("")){Func.append(1,Func.sumat+j+" filera"+i+": ");Func.append(0,"hi ha variables internes que es confonen amb funcions valors num"+Func.rB.getString("_e")+"rics o operadors, ex. s"+Func.rB.getString("i_")+"mbol: "+matriuVint[i][j]+", "+splitPan.FIL);return null;}
                bol[i][j][0]=true;
            }
             if(b){
                 matriu[i+1][j*3]="";
                 if (bol[i][j][0])matriu[i+1][j*3]+=matriuVint[i][j]+" ";
                 if(bol[i][j][2])matriu[i+1][j*3]+=s1+" "+suportID.ceroLog[i][j];
                 else matriu[i+1][j*3]+=s1;
                 tl.setValueAt((Object)matriu[i+1][j*3], i, j*3);
             }
        }
        for(int j=0;j<matriuVint[0].length;j++)for(int i=0;i<matriuVint.length;i++)if(matriuVint[i][j]!=null){
            for(int k=i+1;k<matriuVint.length;k++)if(matriuVint[i][j].equals(matriuVint[k][j])){Func.append(1,Func.sumat+j+": ");Func.append(0,"hi ha variables internes identiques ex: "+matriuVint[k][j]+ splitPan.FIL);return null; }
            String s=splitPan.esSimVar(matriuVint[i][j]);
            if(!s.equals("")){Func.append(1,Func.sumat+j+" filera"+i+": ");Func.append(0,"hi ha variables  internes que es confonen amb funcions valors num"+Func.rB.getString("_e")+"rics o operadors, ex. s"+Func.rB.getString("i_")+"mbols: "+matriuVint[i][j]+", "+s+splitPan.FIL);return null;}
        }
     return bol;   
    }
    private  boolean compatibilitatVariablesInternesFuncions(){
        if(!splitPan.simbolsDeFuncionsSumatoris())return false;
        for(int j=0;j<matriuFunc[0].length;j++){
            boolean mod=false;
            int[] index=new int[matriuVint.length];
            int[] indexMinim=new int[matriuVint.length];
            for(int i=0;i<matriuVint.length;i++)indexMinim[i]=matriuVint.length;
            for(int i=0;i<matriuVint.length;i++){
                int z=j*2*matriuVint.length;
                int y=z+2*i;
                String[] s0=splitPan.arr_simbolsSum.get(y);String[] s1=splitPan.arr_simbolsSum.get(y+1);
                String[] s=new String[s0.length+s1.length];//inclou limit inferior i superior; si alguna de les dues funcions no cumpeix amb la condicio caldra mourala
                for(int l=0;l<s0.length;l++)s[l]=s0[l];
                for(int l=0;l<s1.length;l++)s[l+s0.length]=s1[l];
                //selecciona les variables de les funcions limits filera i simbols s
                for(int n=0;n<s.length;n++)for(int k=0;k<matriuVint.length;k++)if(s[n].equals(matriuVint[k][j])){
                    if(k<indexMinim[i])indexMinim[i]=k;
                }
            }
            for(int i=0;i<matriuVint.length;i++){
                if(indexMinim[i]>=i&&indexMinim[i]!=matriuVint.length){
                    Func.append(1,Func.error+Func.sumat+j+" filera: "+(i+1)+";  funci"+Func.rB.getString("o_")+" l"+Func.rB.getString("i_")+"mit inferior");Func.append(0,"; les variables internes presents en les funcions l"+Func.rB.getString("i_")+"mit han de estar definides en una cel.la de la mateixa filera o anterior (c"+Func.rB.getString("a")+"lcul previ)"+splitPan.FIL);
                    mod=true;
                }
            }
            if(mod){//reordena les fileres
                for(int m=0;m<index.length;m++)index[m]=m;
                for(int m=0;m<index.length-1;m++){
                    for(int n=m+1;n<index.length;n++){
                        if(indexMinim[m]>indexMinim[n]){
                          int o=  indexMinim[m];
                          indexMinim[m]=indexMinim[n];
                          indexMinim[n]=o;
                          o=  index[m];
                          index[m]=index[n];
                          index[n]=o;
                        }
                    }
                }
                for(int m=0;m<matriuVint.length;m++)if(indexMinim[m]>m)return false;
                for(int m=0;m<index.length;m++)splitPan.arr_suportsimbolsSum.set(m,splitPan.arr_simbolsSum.get(m));
                for(int m=0;m<index.length;m++){
                    int z=j*matriuVint.length;
                    splitPan.arr_simbolsSum.set(z+2*index[m],splitPan.arr_suportsimbolsSum.get(z+2*m));
                }
                String[] suport=new String[matriuVint.length];
                for(int m=0;m<index.length;m++)suport[m]=matriuVint[index[m]][j];
                for(int m=0;m<index.length;m++)matriuVint[m][j]=suport[m];
                
                for(int m=0;m<index.length;m++)suport[m]=matriuInt[index[m]][j];
                for(int m=0;m<index.length;m++)matriuInt[m][j]=suport[m];
                
                int[] isuport=new int[matriuVint.length];
                for(int m=0;m<index.length;m++)isuport[m]=suportID.matriuInt[index[m]][j];
                for(int m=0;m<index.length;m++)suportID.matriuInt[m][j]=isuport[m];
                
                double[] dsuport=new double[matriuVint.length];
                for(int m=0;m<index.length;m++)dsuport[m]=suportID.ceroLog[index[m]][j];
                for(int m=0;m<index.length;m++)suportID.ceroLog[m][j]=dsuport[m];
                
                for(int m=0;m<matriuVint.length;m++)if(indexMinim[m]>m)return false;
                int q=j*3;
                for(int n=0;n<3;n++){
                    for(int m=0;m<index.length;m++)suport[m]=matriu[index[m]+1][q+n];
                    for(int m=0;m<index.length;m++)matriu[m+1][q+n]=suport[m];
                    for(int m=0;m<index.length;m++)tl.setValueAt((Object)matriu[m+1][q+n], m, q+n);
                }
                Func.append(1,Func.error+Func.sumat+j+"   funci"+Func.rB.getString("o_")+" limit");
                Func.append(0,"; la possici"+Func.rB.getString("o_")+" de les fileres s'ha modificat comprovar els canvis, el proc"+Func.rB.getString("e_")+"s per"+Func.rB.getString("_o")+" continua"+splitPan.FIL);
                return false;
            }
        }
        return true;
    }
    public  boolean matriuTaulaFi(){
        splitPan.cadenes_sVO_deFuncions_taulaI();//les matrius sVO i dVO ja s'han reduit fins a valors numerics si no son funcions amb variables internes, generals o de taulaF
        if(!splitPan.supIDBol){
            Cal.supID=new suportID();
            Cur.supID=new suportID();
        }
        Cal.supID.dMatriuVar=new double[matriuFunc[0].length][matriu.length-1][10];
        Cur.supID.dMatriuVar=new double[matriuFunc[0].length][matriu.length-1][10];
        Double[] dvoi=new Double[0];
        Double[] dvof=new Double[0];
        for(int i=0;i<taulaI.matriuFunc[0].length;i++){
            for(int j=0;j<taulaI.matriuVint.length;j++){
                boolean b=false;
                int k=i*2*(matriuVint.length)+2*j;
                dvoi=splitPan.arr_dVO_Sum.get(k);dvof=splitPan.arr_dVO_Sum.get(k+1);
                if(suportID.integralVaraLimitsBol[i][j][0]){Cal.supID.dMatriuVar[i][j][0]=NaN;b=true;}
                else Cal.supID.dMatriuVar[i][j][0]=dvoi[0];
                if(suportID.integralVaraLimitsBol[i][j][1]){Cal.supID.dMatriuVar[i][j][1]=NaN;b=true;}
                else Cal.supID.dMatriuVar[i][j][1]=dvof[0];
                try{Cal.supID.dMatriuVar[i][j][2]=Cal.supID.dMatriuVar[i][j][1]-Cal.supID.dMatriuVar[i][j][0];}
                catch(NumberFormatException e){Cal.supID.dMatriuVar[i][j][2]=NaN;}
                Cal.supID.dMatriuVar[i][j][7]=Cal.supID.dMatriuVar[i][j][0];
                Cal.supID.dMatriuVar[i][j][8]=Cal.supID.dMatriuVar[i][j][1];
                Cal.supID.dMatriuVar[i][j][9]=Cal.supID.dMatriuVar[i][j][2];
            }
        }
        Cur.supID.dMatriuVar=splitPan.copiaMatriu(Cal.supID.dMatriuVar);//System.arraycopy(Cal.supID.dMatriuVar, 0, Cur.supID.dMatriuVar, 0, Cal.supID.dMatriuVar.length);
        suportID.hihaVG_aTOTI=false;
        boolean correcte=true;
        suportID.longi=new int[matriuFunc[0].length];
        if(!funcionsiVariables())return false;
        for(int i=0;i<suportID.longi.length;i++){suportID.longi[i]=integralLimitsBuitBol[0].length;}
        for(int i=0;i<suportID.longi.length;i++){
            for(int j=0;j<integralLimitsBuitBol[0].length;j++){
                if(integralLimitsBuitBol[i][j]){//la primera filera buida de integral determina la longitud (ordre) de l integral
                    suportID.longi[i]=j;
                    j=integralLimitsBuitBol[0].length;
                }
            }
        }
        return correcte;
    }
    public  boolean funcionsiVariables(){
        String s0="",s3="",s1="",s2="",s4="";
        Cal.supID.dMatriuFunc=new double[matriuFunc[0].length];
        Cur.supID.dMatriuFunc=new double[matriuFunc[0].length];
        int cont0=0,cont1=0,cont2=0,cont3=0;
        integralFuncioBol=new boolean[matriuFunc[0].length];
        for(int l=0;l<matriuFunc[0].length;l++){
            try {
                Cal.supID.dMatriuFunc[l]=Double.parseDouble(splitPan.comApun(matriuFunc[1][l]));
                Cur.supID.dMatriuFunc[l]=Cal.supID.dMatriuFunc[l];
            }
            catch(NumberFormatException e){
                Cal.supID.dMatriuFunc[l]=NaN;
                Cur.supID.dMatriuFunc[l]=NaN;
                integralFuncioBol[l]=true;cont0++;
            }
            for(int i=0;i<matriuVint.length;i++){for(int j=0;j<2;j++){if(suportID.integralVaraLimitsBol[l][i][j])cont0++;}}
        }
        suportID.integralLimits=new int[matriuFunc[0].length][suportID.integralVaraLimitsBol[0].length][2][6];//0limit inferior 1superior 2inferior del limit superior 3superior limit superior iaixi per les tres  matrius
        suportID.integralLimitsFun=new int[matriuFunc[0].length][8];
        suportID.hihaVG_aI=new boolean[matriuFunc[0].length];
        suportID.hihaVGioFPaI=new boolean[matriuFunc[0].length];
        suportID.hihaFPambVGaI=new boolean[matriuFunc[0].length];
        suportID.hihaVG_aTOTI=false;
        cont0=0;
        boolean[] varGenBol=new boolean[Cal.idxVarGen.length];
        if(Func.tipusFuncioBol){
            taulaF.varSumatoriBol=new boolean[taulaF.simbolsReduits.length];
        }
        for(int j=0;j<matriuFunc[0].length;j++){//varGenBol  indiquen  quines funcions tenen variables generals la primera filera engloba tota la resta de fileres; i taulaF.varSumatoriBol indica quines filers de taula F contenen integrals; simbolsF[j] indica que el sumatori j conte variables de taulaF
            if(integralFuncioBol[j]){
                String[] m0=splitPan.arr_simbolsSumF.get(j);
                for(int k=0;k<m0.length;k++)for(int l=0;l<Cal.idxVarGen.length;l++)if(m0[k].equals(taulaV.varstaulaV[Cal.idxVarGen[l]])){
                    varGenBol[l]=true;suportID.hihaVG_aI[j]=true;suportID.hihaVG_aTOTI=true;
                }
                if(Func.tipusFuncioBol){
                    for(int k=0;k<m0.length;k++)for(int l=0;l<taulaF.simbolsReduits.length;l++)if(!taulaF.simbolsReduitsNombreBol[l]){//a taulaV idxVarGen estan ordenats per llargaria; taulaF.simbolsReduitsNombreBol si true fileres de taulaF que son valors numerics
                        if(m0[k].equals(taulaF.simbolsReduits[l])){taulaF.varSumatoriBol[l]=true;suportID.hihaVtaulaF_aI[j]=true;}//simbolsF[j]=true;}
                    }
                }
            }
            int z=j*2*matriuVint.length;
            for(int i=0;i<matriuVint.length;i++)for(int n=0;n<2;n++){
                int y=z+2*i;
                if(suportID.integralVaraLimitsBol[j][i][n]){
                    String[] m0=splitPan.arr_simbolsSum.get(y+n);
                    for(int k=0;k<m0.length;k++)for(int l=0;l<Cal.idxVarGen.length;l++)if(m0[k].equals(taulaV.varstaulaV[Cal.idxVarGen[l]])){
                        varGenBol[l]=true;suportID.hihaVG_aI[j]=true;suportID.hihaVG_aTOTI=true;
                    }
                    if(Func.tipusFuncioBol){
                        for(int k=0;k<m0.length;k++)for(int l=0;l<taulaF.simbolsReduits.length;l++)if(!taulaF.simbolsReduitsNombreBol[l]){//a taulaV idxVarGen estan ordenats per llargaria
                            if(m0[k].equals(taulaF.simbolsReduits[l])){taulaF.varSumatoriBol[l]=true;suportID.hihaVtaulaF_aI[j]=true;}//simbolsF[j]=true;}
                        }
                    }
                }    
            }
        }
        int con=0;
        for(int i=0;i<Cal.idxVarGen.length;i++)if(varGenBol[i])con++;
        suportID.idxVarGenIntegr=new int[con];//matriu de index de les variables generals a integrals
        con=0;
        for(int i=0;i<Cal.idxVarGen.length;i++)if(varGenBol[i]){suportID.idxVarGenIntegr[con]=Cal.idxVarGen[i];con++;} 
        con=0;
        for(int l=0;l<matriuFunc[0].length;l++){
            String[] m0=splitPan.arr_simbolsSumF.get(l);
            if(integralFuncioBol[l]){//nombre de variables internes a la funcio
                con=0;
                for(int j=0;j<m0.length;j++)for(int k=0;k<matriuVint.length;k++)if(m0[j].equals(matriuVint[k][l])) con++;
                int [] mv=new int[con];
                con=0;
                for(int j=0;j<m0.length;j++)for(int k=0;k<matriuVint.length;k++)if(m0[j].equals(matriuVint[k][l])){mv[con]=k;con++;}
                con=0;
                String s="variable: "+Func.sumat+l+" ";
                Cur.sVO=splitPan.arr_sVO_SumF.get(l);Cur.dVO_=splitPan.arr_dVO_SumF.get(l);
                tabCalcFun(mv,l,s);
                suportID.integralLimitsFun[l][0]=cont0;
                for(int k=0;k<Cur.sVO.length;k++){s3+=Cur.sVO[k]+" ";}
                for(int k=0;k<Cur.dVO.length;k++){s0+=Cur.dVO[k]+" ";}
                cont0+=Cur.dVO.length;
                suportID.integralLimitsFun[l][1]=cont0;
                suportID.integralLimitsFun[l][2]=cont1;
                for (int[] index : Cal.indexs) {s1 += index[0] + " " + index[1] + " " + index[2] + " ";}
                cont1+=Cal.indexs.length;
                suportID.integralLimitsFun[l][3]=cont1;
                suportID.integralLimitsFun[l][4]=cont2;
                for(int n=0;n<Cal.indexsVar.length;n++){s2 += Cal.indexsVar[n][0] + " " + Cal.indexsVar[n][1] +  " ";}
                cont2+=Cal.indexsVar.length;
                suportID.integralLimitsFun[l][5]=cont2;
                if(Func.tipusFuncioBol){
                    for(int n=0;n<taulaF.indexsSum.length;n++){
                        if(taulaF.indexsSum[n][0]>=0)taulaF.indexsSum[n][0]+=cont3;//cont3 es l'index absolut del conjunt de funcions sumatoris
                        else taulaF.indexsSum[n][0]-=cont3;
                        s4 += taulaF.indexsSum[n][0] + " " + taulaF.indexsSum[n][1]+ " ";
                    }
                    cont3+=Cur.sVO.length;
                }
            }
            con=0;
            for(int i=0;i<matriuVint.length;i++)for(int j=0;j<2;j++){
                int y=l*2*matriuVint.length+2*i;
                m0=splitPan.arr_simbolsSum.get(y+j);
                if(suportID.integralVaraLimitsBol[l][i][j]){
                    con=0;
                    for(int o=0;o<m0.length;o++)for(int k=0;k<i;k++)if(m0[o].equals(matriuVint[k][l])) con++;
                    int [] mv=new int[con];
                    con=0;
                    for(int o=0;o<m0.length;o++)for(int k=0;k<i;k++)if(m0[o].equals(matriuVint[k][l])){mv[con]=k;con++;}
                    con=0;
                    String s="variable: "+Func.rB.getString("integral")+" l"+Func.rB.getString("i_")+"mit ";
                    if(j==0)s+=" inferior ";else s+=" superior ";
                    Cur.sVO=splitPan.arr_sVO_Sum.get(y+j);Cur.dVO_=splitPan.arr_dVO_Sum.get(y+j);
                    tabCalcFun(mv,l,s);
                    suportID.integralLimits[l][i][j][0]=cont0;
                    for(int k=0;k<Cur.sVO.length;k++){s3+=Cur.sVO[k]+splitPan.TAB;}
                    for(int k=0;k<Cur.dVO.length;k++){s0+=Cur.dVO[k]+splitPan.TAB;}
                    cont0+=Cur.sVO.length;
                    suportID.integralLimits[l][i][j][1]=cont0;
                    suportID.integralLimits[l][i][j][2]=cont1;
                    for (int[] index : Cal.indexs) {s1 += index[0] + " " + index[1] + " " + index[2] + splitPan.TAB;}
                    cont1+=Cal.indexs.length;
                    suportID.integralLimits[l][i][j][3]=cont1;
                    suportID.integralLimits[l][i][j][4]=cont2;
                    for(int n=0;n<Cal.indexsVar.length;n++){s2 += Cal.indexsVar[n][0] + " " + Cal.indexsVar[n][1] + splitPan.TAB;}
                    cont2+=Cal.indexsVar.length;
                    suportID.integralLimits[l][i][j][5]=cont2;
                    if(Func.tipusFuncioBol){
                        for(int n=0;n<taulaF.indexsSum.length;n++){
                            if(taulaF.indexsSum[n][0]>=0)taulaF.indexsSum[n][0]+=cont3;
                            else taulaF.indexsSum[n][0]-=cont3;
                            s4 += taulaF.indexsSum[n][0] + " " + taulaF.indexsSum[n][1] + splitPan.TAB;
                        }
                        cont3+=Cur.sVO.length;
                    }
                }
            }
        }
        suportID.sVOI=splitPan.matriudeFuncioCompleta(s3);
        Cal.supID.dVOI=splitPan.matriudeFunciodouble(s0);
        Cal.supID.indexsI=splitPan.matriudeFuncioInt3(s1);
        Cal.supID.indexsVarI=splitPan.matriudeFuncioInt2(s2);
        if(!Func.tipusFuncioBol){
            Cur.supID.dVOI=new double[cont0];System.arraycopy(Cal.supID.dVOI, 0, Cur.supID.dVOI, 0, Cal.supID.dVOI.length);
            if(cont1>0)Cur.supID.indexsI=splitPan.copiaMatriu(Cal.supID.indexsI);//new int [cont1][3];System.arraycopy(Cal.supID.indexsI, 0, Cur.supID.indexsI, 0, Cal.supID.indexsI.length);
            if(cont2>0)Cur.supID.indexsVarI=splitPan.copiaMatriu(Cal.supID.indexsVarI);//=new int [cont2][2];System.arraycopy(Cal.supID.indexsVarI, 0, Cur.supID.indexsVarI, 0, Cal.supID.indexsVarI.length);
        }
        else taulaF.indexsSum=splitPan.matriudeFuncioInt2(s4);
        for(int l=0;l<matriuFunc[0].length;l++){
            suportID.integralLimitsFun[l][6]=(int)1E9;
            suportID.integralLimitsFun[l][7]=0;
            if(integralFuncioBol[l]){
                        suportID.integralLimitsFun[l][6]=suportID.integralLimitsFun[l][0];
                        suportID.integralLimitsFun[l][7]=suportID.integralLimitsFun[l][1];
                    }
            for(int i=0;i<matriuVint.length;i++)for(int j=0;j<2;j++){
                if(suportID.integralVaraLimitsBol[l][i][j]){//limit inferior
                    if(suportID.integralLimitsFun[l][6]>suportID.integralLimits[l][i][j][0])suportID.integralLimitsFun[l][6]=suportID.integralLimits[l][i][j][0];
                    if(suportID.integralLimitsFun[l][7]<suportID.integralLimits[l][i][j][1])suportID.integralLimitsFun[l][7]=suportID.integralLimits[l][i][j][1];
                }
            }
        }
        Cal.indexsInteg_VG=new boolean[0][0];
        if(taulaV.hihaVar){
            Cal.indexsInteg_VG=new boolean[Cal.longi][taulaI.matriuFunc[0].length];//indexsInteg_VG[o][i] si true vol dir que la variable general del primer index o(taula variables filera) es troba directa o indirectament mitjançant funcions parcials al menys un cop en la integral del segon index i
            for(int j=0;j<suportID.idxVarGenIntegr.length;j++){//indexs de les variables generals presents a sumatoris
                for(int l=0;l<taulaI.matriuFunc[0].length;l++){
                    for(int k=suportID.integralLimitsFun[l][6];k<suportID.integralLimitsFun[l][7];k++){
                        if(taulaV.varstaulaV[suportID.idxVarGenIntegr[j]].equals(suportID.sVOI[k])||("-"+taulaV.varstaulaV[suportID.idxVarGenIntegr[j]]).equals(suportID.sVOI[k]))Cal.indexsInteg_VG[suportID.idxVarGenIntegr[j]][l]=true;
                    }
                }
            }
            int co=0;//contador de variables dins de sVO per ficarles a indexsVar
            for(int i=0;i<suportID.sVOI.length;i++)for(int j=0;j<suportID.idxVarGenIntegr.length;j++){//indexs de les variables generals presents a sumatoris
                if(suportID.sVOI[i].equals(taulaV.varstaulaV[suportID.idxVarGenIntegr[j]])||suportID.sVOI[i].equals("-"+taulaV.varstaulaV[suportID.idxVarGenIntegr[j]])){
                     Cal.supID.dVOI[i]=1.0;
                     if(!Func.tipusFuncioBol)Cur.supID.dVOI[i]=1.0;co++;
                     j=suportID.idxVarGenIntegr.length;
                }
            }
            suportID.indexsVGaI=new int[co][2];
            co=0;//el 0 index de indexVar es la posicio dins la matriu dVO el 2 es la posicio matriu taulaV.varstaulaV 
            for(int j=0;j<suportID.idxVarGenIntegr.length;j++)for(int i=0;i<suportID.sVOI.length;i++){
                //con es el nombre de fileres de dVO o sV
                if(suportID.sVOI[i].equals(taulaV.varstaulaV[suportID.idxVarGenIntegr[j]])||suportID.sVOI[i].equals("-"+taulaV.varstaulaV[suportID.idxVarGenIntegr[j]])){
                    suportID.indexsVGaI[co][0]=i;
                    suportID.indexsVGaI[co][1]=suportID.idxVarGenIntegr[j];
                    co++;
                }
            }
        }
        System.arraycopy(suportID.hihaVG_aI, 0, suportID.hihaVGioFPaI, 0, suportID.hihaVG_aI.length);
        if(taulaD.hihaDades){// si hi ha funcions parcials cal corretgir si cal les funcions  Cal.indexsInteg_VG i suportID.hihaFPaI
            for(int j=0;j<taulaD.simbol.length;j++)if(taulaD.hihaVGioFP[j])for(int l=0;l<matriuFunc[0].length;l++){
                for(int k=suportID.integralLimitsFun[l][6];k<suportID.integralLimitsFun[l][7];k++){
                    if(taulaD.simbol[j].equals(suportID.sVOI[k])){
                        suportID.hihaFPambVGaI[l]=true;suportID.hihaVGioFPaI[l]=true;suportID.hihaVG_aTOTI=true;
                        for(int i=0;i<Cal.idxVarGen.length;i++){
                            if(taulaD.VGaFuncBol[j][i])Cal.indexsInteg_VG[Cal.idxVarGen[i]][l]=true;//Cal.indexsInteg_VG[suportID.idxVarGen[j]][l]=true;
                        }
                    }
                }
            }
        }
        return true;
    }
    //calcul de les funcions dvo i svo, indexsVar i indexs operacions de les funcions limit i funcio integral
    // aqui no s'inclouen els parametres de les variables comples de integrals i funcions parcials
    //mv conte primer els index de variables generals i en un segon terme  els index de les variables internes presents a la funcio
    public static void tabCalcFun(int[] mv,int pos, String info){
        int con=Cur.sVO.length;
        String[] sV=new String[con];
        Double[] dV=new Double[con];
        System.arraycopy(Cur.sVO, 0, sV, 0, con);
        Cur.dVO=new double[con];
        for(int k=0;k<con;k++){
            dV[k]=Cur.dVO_[k];
            try{Cur.dVO[k]=(double)Cur.dVO_[k];}
            catch(java.lang.NullPointerException e){Cur.dVO[k]=NaN;}
        }
        if (splitPan.supIDBol)for(int j=0;j<taulaD.simbol.length;j++)for(int m=0;m<Cur.sVO.length;m++){
            if(sV[m].equals(taulaD.simbol[j])||sV[m].equals("-"+taulaD.simbol[j])){sV[m]="1";dV[m]=1.0;}
        }
        for(int k=0;k<suportID.idxVarGenIntegr.length;k++)for(int m=0;m<Cur.sVO.length;m++){
            if(sV[m].equals(taulaV.varstaulaV[suportID.idxVarGenIntegr[k]])||sV[m].equals("-"+taulaV.varstaulaV[suportID.idxVarGenIntegr[k]])){sV[m]="1";dV[m]=1.0;}
        }
        int co=0;
        if(Func.tipusFuncioBol){
            for(int k=0;k<taulaF.simbolsReduits.length;k++)if(!taulaF.simbolsReduitsNombreBol[k])if(taulaF.varSumatoriBol[k]){
                for(int i=0;i<con;i++){
                    if(sV[i].equals(taulaF.simbolsReduits[k])||sV[i].equals("-"+taulaF.simbolsReduits[k])){dV[i]=1.0;co++;}
                } 
            }
            taulaF.indexsSum=new int[co][2];
            co=0;
            for(int k=0;k<taulaF.simbolsReduits.length;k++)if(!taulaF.simbolsReduitsNombreBol[k])if(taulaF.varSumatoriBol[k]){
                for(int i=0;i<con;i++){
                    if(sV[i].equals(taulaF.simbolsReduits[k])){
                        taulaF.indexsSum[co][0]=i;
                        taulaF.indexsSum[co][1]=k;
                        co++;
                    }
                    else if(sV[i].equals("-"+taulaF.simbolsReduits[k])){
                        taulaF.indexsSum[co][0]=-i-1;
                        taulaF.indexsSum[co][1]=k;
                        co++;
                    }
                }
            }
        }
        splitPan.cadenaParNumerics="";
        int contador=splitPan.nombrefilesmatriuIndexs(Cur.sVO);
        co=0;//contador de variables dins de sVO per ficarles a indexsVar
        for(int i=0;i<con;i++)for(int j=0;j<mv.length;j++){
            if(sV[i].equals(taulaI.matriuVint[mv[j]][pos])||sV[i].equals("-"+taulaI.matriuVint[mv[j]][pos])){
                dV[i]=1.0;co++;
            }
        }
        Cal.indexsVar=new int[co][2];//aqui nomes les variables internes
        co=0;
        for(int j=0;j<mv.length;j++){
            for(int i=0;i<con;i++){//con es el nombre de fileres de dVO o sV
                if(sV[i].equals(taulaI.matriuVint[mv[j]][pos])||sV[i].equals("-"+taulaI.matriuVint[mv[j]][pos])){
                    Cal.indexsVar[co][0]=i;//posicio filera dVO
                    Cal.indexsVar[co][1]=mv[j];//index variable integral
                    co++;
                }
            }
        }
        Cal.indexs=new int[contador][3];
        splitPan.calcIndexFunc(sV,dV);//calcula els indexs de les operacions
    }
    private  boolean[] moufilBuides(){
    boolean[] b0=new boolean[2];
    for(int l=0;l<matriu[0].length;l+=3){
        int cont=matriu.length;
        for(int i=cont-1;i>0;i--){
            if(matriu[i][l].equals("")&&matriu[i][l+1].equals("")&&matriu[i][l+2].equals(""))cont--;
         }
        for(int i=1;i<cont;i++){
            if(matriu[i][l].equals("")&&matriu[i][l+1].equals("")&&matriu[i][l+2].equals("")){
                b0[0]=true;
                for(int k=i+1;k<matriu.length;k++){
                    if(matriu[k][l].equals("")&&!matriu[k][l+1].equals("")&&!matriu[k][l+2].equals(""))b0[1]=true;
                    matriu[k-1][l]=matriu[k][l];
                    matriu[k-1][l+1]=matriu[k][l+1];
                    matriu[k-1][l+2]=matriu[k][l+2];
                }
                matriu[matriu.length-1][l]="";
                matriu[matriu.length-1][l+1]="";
                matriu[matriu.length-1][l+2]="";
                cont--;i--;
             }
         }
    }
    for(int i=1;i<matriu.length;i++){
        for(int j=0;j<matriu[0].length;j++){tl.setValueAt((Object)matriu[i][j], i-1, j);}
    }
    matriusVar1();
    for(int i=matriu.length-1;i>0;i--){
        boolean b=false;
        for(int j=0;j<matriu[0].length;j++){
            if(!matriu[i][j].equals(""))b=true;
        }
        if(!b){
            b0[0]=true;
            tl.setRowSelectionInterval(i-1, i-1);
            if(contingutTaula(tl.getSelectedRows(),false)){nouModel(true);}
        }
    }
    return b0;
   }
    private  boolean mouColBuides(){
        boolean b0=false;
        int n=tlF.getColumnCount();
        for(int j=0;j<matriuFunc[0].length;j++){
            int l=j*3;
            boolean b=false;
            for(int i=1;i<matriu.length;i++){
                if(!matriuFunc[1][j].equals("")||!matriu[i][l].equals("")||!matriu[i][l+1].equals("")||!matriu[i][l+2].equals(""))b=true;//hi ha alguna cel.la plena
            }
            if(!b){
                b0=true;
                if(matriuFunc[0].length>1){
                    tl.setColumnSelectionInterval(l, l);
                    menuItemCol.doClick();
                }
                int m=tlF.getColumnCount();
                if(m<n)j--;
            }
            if(matriuFunc[0].length<2)j=matriu[0].length;
        }
        return b0;
   }
        
    public void nouModel(boolean b){
        int cc=tl.getColumnCount();
        cols=new int[cc];
        if(b)for(int i=0;i<cc;i++)cols[i]=tl.getColumnModel().getColumn(i).getWidth();
        int ccF=tlF.getColumnCount();
        colsF=new int[ccF];
        if(b)for(int i=0;i<ccF;i++)colsF[i]=tlF.getColumnModel().getColumn(i).getWidth();
        tl.setModel(new model());
        for(int i=1;i<matriu.length;i++){
            for(int j=0;j<matriu[0].length;j++){tl.setValueAt((Object)matriu[i][j], i-1, j);}
        }
        tlF.setModel(new modelI());
        for(int i=1;i<matriuFunc.length;i++){
            for(int j=0;j<matriuFunc[0].length;j++){tlF.setValueAt((Object)matriuFunc[i][j], i, j);}
        }
        int i=tlF.getRowCount();
        for(int j=0;j<i;j++){
            tlF.setRowHeight( j, 18 );
        }
        i=tl.getRowCount();
        for(int j=0;j<i;j++){
            tl.setRowHeight( j, 18 );
        }
        if(b)margesBol=true;
        if(b)for(i=0;i<ccF;i++)tlF.getColumnModel().getColumn(i).setPreferredWidth(colsF[i]);
        if(b)for(i=0;i<cc;i++)tl.getColumnModel().getColumn(i).setPreferredWidth(cols[i]);
        
        color();
        splitPan.tTI.repaint();
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
                    int c=tl.getColumnCount();
                    for(int i=0;i<c-2;i+=6){
                        for(int j=i;j<i+3;j++) if (column == j) lb.setBackground(gmh);
                        for(int j=i+3;j<i+6;j++)if (column == j)lb.setBackground(gh);
                    }
                return lb;
                }
        });
        JThd1 = tl.getTableHeader();
        TableCellRenderer hrF = tl.getTableHeader().getDefaultRenderer();
        JThd1.setDefaultRenderer(new TableCellRenderer() {
            JLabel lbF;
            @Override
            public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus,int row, int column) {
                    lbF = (JLabel) hrF.getTableCellRendererComponent(table, value, false, false, row, column);
                    int c=tl.getColumnCount();
                    for(int i=0;i<c-1;i+=2){
                        if (column == i) lbF.setBackground(gmh);
                        if (column == i+1)lbF.setBackground(gh);
                    }
                return lbF;
                }
        });
        
        int c=tl.getColumnCount();
        for(int i=0;i<c-5;i+=6){
            for(int j=i+3;j<i+6;j++){
            tl.getColumn(tl.getColumnName(j)).setCellRenderer(new DefaultTableCellRenderer() {
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    DefaultTableCellRenderer r = (DefaultTableCellRenderer) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if(!isSelected)r.setBackground(gc); 
                    return r;
                }   
            });
            }
            for(int j=i;j<i+3;j++){
            tl.getColumn(tl.getColumnName(j)).setCellRenderer(new DefaultTableCellRenderer() {
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    DefaultTableCellRenderer r = (DefaultTableCellRenderer) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if(!isSelected)r.setBackground(gmc); 
                    return r;
                }   
            });
            }
        }
        c=tlF.getColumnCount();
        for(int i=0;i<c-1;i+=2){
            tlF.getColumn(tlF.getColumnName(i+1)).setCellRenderer(new DefaultTableCellRenderer() {
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    DefaultTableCellRenderer r = (DefaultTableCellRenderer) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if(!isSelected)r.setBackground(gc); 
                    return r;
                }   
            });
            tlF.getColumn(tlF.getColumnName(i)).setCellRenderer(new DefaultTableCellRenderer() {
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    DefaultTableCellRenderer r = (DefaultTableCellRenderer) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if(!isSelected)r.setBackground(gmc); 
                    return r;
                }   
            });
        }
        if ((c % 2) != 0) {
            for(int j=c*3-3;j<c*3;j++){
            tl.getColumn(tl.getColumnName(j)).setCellRenderer(new DefaultTableCellRenderer() {
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    DefaultTableCellRenderer r = (DefaultTableCellRenderer) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if(!isSelected)r.setBackground(gmc); 
                    return r;
                }   
            });
            }
            tlF.getColumn(tlF.getColumnName(c-1)).setCellRenderer(new DefaultTableCellRenderer() {
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    DefaultTableCellRenderer r = (DefaultTableCellRenderer) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if(!isSelected)r.setBackground(gmc); 
                    return r;
                }   
            });
        }
    }
    private  void capsalInteg(){for(int i=0;i<matriu[0].length;i+=3){matriu[0][i] = "var.  interv.";matriu[0][i+1] = "l"+Func.rB.getString("i_")+"mit Inferior";matriu[0][i+2] = "l"+Func.rB.getString("i_")+"mit Superior";}}
    private  void capsalInteg1(){for(int i=0;i<matriuFunc[0].length;i++){matriuFunc[0][i] = "funci"+Func.rB.getString("o_")+": "+Func.sumat+i;}}
    
    public void valorsInicials(){
        if(matriu==null){reiniciParcial();}
    	else{
            capsalInteg1();
            capsalInteg();
    	}
        hihaDades=false;
    }
    private void reiniciParcial(){
        matriu=new String[2][3];
        matriuFunc=new String[2][1];
        capsalInteg1();
        capsalInteg();
        matriu[1][0]="";
        matriu[1][1]="";
        matriu[1][2]="";
        matriuFunc[1][0]="";
    }
    class model extends AbstractTableModel {
        public int getColumnCount() {return matriu[0].length;}
        public int getRowCount() {return matriu.length-1;}
        public String getColumnName(int col) {return matriu[0][col]+" "+(int)(col/3); }
        public Object getValueAt(int row, int col) {return matriu[row+1][col];}
        public boolean isCellEditable(int row, int col) {if (col < 0) {return false;} else { return true; }}
        public void setValueAt(Object value, int row, int col) {
            matriu[row+1][col]=(String)value;
            fireTableCellUpdated(row, col);
        }
    }
    class modelI extends AbstractTableModel {
        public int getColumnCount() {return matriuFunc[0].length;}
        public int getRowCount() {return 1;}
        public String getColumnName(int col) {return matriuFunc[0][col]; }
        public Object getValueAt(int row, int col) {return matriuFunc[1][col]; }
        public boolean isCellEditable(int row, int col) {if (col < 0) {return false;} else { return true; }}
        public void setValueAt(Object value, int row, int col) {
           matriuFunc[1][col]=(String)value;
            fireTableCellUpdated(0, col);
        }
    }
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("TableFTFEditDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        taulaI newContentPane = new taulaI();
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
