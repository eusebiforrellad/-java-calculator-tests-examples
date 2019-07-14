package Calc;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static java.lang.Double.NaN;
import static java.lang.Double.isFinite;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
public class taulaF extends JPanel implements ActionListener{
    private static final long serialVersionUID = 1L;
    public static boolean[] filaNop;
    public static JTable tl;
    static String[][] matriuSup;
    public static String [][] matriu;//la matriu variables mante la mateixa configuracio que la taula i inclou les capçaleres
    public static String [][] intColors;
    public static boolean [][] puntsTFBol;//files nombre de punts; columnes 5 x y z i el color; el tamany; tempshistorial
    public static boolean [][] puntsTFBolSuport;
    public static int [][] puntsTFGraf;//les fileres corresponents a la matriu puntsTFBol
    public static double[][] puntsGraf;
    static boolean[] puntsGrafFinits;
    public static boolean[] puntsBol;//columna eixos per cada filera true si hi ha informacio a la cel.la provisionalment es fan false en la comprobacio de la columna eixos
    public static double[][] limPunts;
    static String[][] eixos;//columna eixos longitud la de la taula el primer subindex el primer caracter de la cadena eixos le segon subindex la resta de la cadena
    static int[] cont,cont_;
    public static int filValSelec;
    public static JTextField  cela;
    static int fil_tl;
    static int col_tl;
    public static int scrollCols;
    static int darreraCela;
    static Color[] colors;
    static Color[] colorsSup;
    //static boolean[] puntsGrafActiusBol;
    static int[] tamanyPuntGraf;
    boolean freqPresNBol;
    boolean[] freqPresNBol0;
    long[] pausa;
    static long pausaGraf;
    static int[][] freqPresN;
    static Instant[] tempsFreq;
    static String[] sfreqPres;
    static int[][] tempsPresHis;
    static double[][] valorInicial;
    public static String[] funcioT;
    public static String[] simbolsT;//les primeres files de simbolsT[i] coincideixen amb les de matriu[i+1][1] (interval 0_colors.length) a partir d'aquest interval inicialment els parametres de capçalera no s'han substituit pels valors numerics despres del analis columna then si; ; els primers simbols son el de la taula columna 1 a continuacio els simbols de la columna then (simvbols de la taula + parametres(el valor numeric)+ valors numerics 
    static boolean[] fixcontidxmatriu;
    static String[] simbolsReduits;//tots els simbolsT menys els repetirs
    static boolean[][] simbolsReduitsArrayIF;
    static boolean[][] simbolsReduitsArrayThen;
    static boolean[][] simbolsReduitsArrayThenReturn;
    static ArrayList<int[][]> arr_IFsimbolsReduitsArrayIndexs0;
    static ArrayList<int[][]> arr_IFsimbolsReduitsArrayIndexs1;
    static ArrayList<int[][]> arr_THENsimbolsReduistArray;
    static ArrayList<int[][]> arr_THENsimbolsReduistArrayReturn;
    static ArrayList<int[][]> arr_THENsimbolsReduitsArrayIndexs0;
    static ArrayList<int[][]> arr_THENsimbolsReduitsArrayIndexs0Return;
    static ArrayList<int[][]> arr_THENsimbolsReduitsArrayIndexs1;
    static ArrayList<int[][]> arr_THENsimbolsReduitsArrayIndexs1Return;
    static ArrayList<int[][]> arr_THEN_destisimbolsReduitsArrayIndexs0;
    static ArrayList<int[][]> arr_THEN_destisimbolsReduitsArrayIndexs0Return;
    static ArrayList<int[][]> arr_THEN_destisimbolsReduitsArrayIndexs1;
    static ArrayList<int[][]> arr_THEN_destisimbolsReduitsArrayIndexs1Return;
    static ArrayList<boolean[][]> arr_iThenPosSimArr;
    static ArrayList<boolean[][]> arr_iThenPosSimArrReturn;
    static int[] idxSimbolFilera;//idxSimbolFilera[j];  la filera 'j' de la matriu simbolsT a fila  'j' conte el index del String[] de simbolsReduits'idxSimbolFilera[j]' on van a parar els resultats de la funcio
    public static boolean[] simbolsTNombreBol;//per cada filera de simbolsT si imbolsTNombreBol[fil]=true vol dir que la filera es un valor numeric
    static boolean[] simbolsReduitsNombreBol;//referits a simbols reduits si=true, els simbols de simbolsReduits son un valor numeric  
    static String[] simbolsReduitsNoNumerics;
    static ArrayList<int[][]> arr_indexsSim;
    public static int[] indexsSim;
    public static int[][] limindexsSim;
    public static int[][] indexs;
    public static int[][] limindexs;
    public static int[][] indexsVaraSim;
    static ArrayList<int[][]> arr_indexsVaraSim;
    //les seguents variables indiquen 1._ les fileres (fileres de la matriu simbolsReduits associat a resultats) en que els seus resultats afecten les taules de integrals io derivades columnes [][0] i[][1]
    //i 2._ les fileres de la taulaF que contenen variable de sumatori de derivada, variables generals o sumatoris amb funcions parcials o variables generals [][2] [][3]  [][4] i [][5]
    public static boolean[][] parCapBol;//si la funcio conte parametres de capçalera (no inclou parametres de capçalera de una filera) la columna 0 i la columna de la taulaP+1 que es troba a la funcio  son true
    public static boolean[][] parCapVarBol;//si la funcio conte parametres de capçalera variables (no inclou parametres de capçalera fixos) la columna 0 i la columna de la taulaP+1 que es troba a la funcio  son true
    public static boolean[][] parCapNoVarBol;//si la funcio conte parametres de capçalera Novariables (no inclou parametres de capçalera fixos) la columna 0 i la columna de la taulaP+1 que es troba a la funcio  son true
    public static boolean[][] varBol;//si la funcio conte o contindra en la funcio extesa o en la original  qualsevol tipus de variable directa es true; no inclou les variables indirectes de taules Deriv i Integr
    public static boolean[][] varGenBol;//per cada filera si es true vol dir que la funcio conte variables generals directes io indirectes
    public static boolean[][] varParFuncOrigBol;//parametres variables de la taulaV presents a la funcio original
    public static boolean[][] varParFuncExtesaBol;//parametres variables de la taulaV presents a la funcio extesa en que s'han substituit els parametres de capçalera
    static boolean[] funcArrCapBol;//si[] true vol dir que es un array pero sense parametres de capçalera i per tant no calcula els indexs dels parametres de taulaP a dVO
    static boolean[] funcArrBol;// si[] true es una funcio amb qualsevol tipus de parametres de mes d'una filera o arrays definits a columna9
    
    public static String[] simbolsArr;//les primeres files de simbolsArr[i] coincideixen amb les de matriu[i+1][1] a partir d'aqui son els simbols dels parametres de capsalera am mes d'una filera (simbolsT+simParCapNoVar+simParCapVar)
    static ArrayList<double[]> arr_matriusdeResultats;//cada filera clasificada com array guarda els valors del les funcions assocides
    static boolean[] resultatUnic;// true indica que el resultat de la funcio es guarda en un array associat al simbol del resultat el array s'ha de trobar definit
    static int[][] indexsderesultatsaMatriu;//[][]0 es la filera que conte la matriu de resultats [][]1 es el contador que indica la possicio [][2] limit de la matriu
    static boolean[] funcioSenseVariables;
    static boolean[] simbolsDefinits;//simbolDefinit=true la variable de la taulaV es coneguda la variable pren el resultat de la darrera cerca de minim (cadena def a darrera columna)
    static ArrayList<int[][]> arr_matriusIndexs;//[][0]indica la filera origen del array[][1] es el nombre de cops que apareix l'array a la funcio  [][2]les possicions a dVO de la funcio
    static boolean hihaArray;
    static String[] simbolsElements;
    static ArrayList<int[][]> arr_matriusIndexs0;
    static ArrayList<int[][]> arr_matriusIndexs1;
    static boolean[][] indexsInternsArrayBol;//a cada fiera indica si conte indexsArrais
    static int[][] indexsInternsArrayResultat;
    static String[] idxdef,idxIndef,simdef,simIndef;
    static ArrayList<int[]> arr_indexsVarModificats0;
    static ArrayList<int[]> arr_indexsVarModificats1;
    static ArrayList<int[][]> arr_varPTV_varPTP;
    public static boolean[][] sumafilataulaF;//sumatoris que es troben a la fila de la taulaF
    public static boolean[][] sumambFPfilataulaF;//sumatoris que es troben a la fila de la taulaF
    public static boolean[] sumambFPfilataulaFunaFila;
    public static boolean[] varNgenSumBol;//la primera columna indica la funcio conte simbols de sumatori i cap simbol del sumatori  conte variables genrals es troba en la funcio de la filera de la taulaF; la resta ifa referencia al sumatori(columna) taylaI
    public static boolean[][] varGenSumBol;//
    public static boolean[] varNgenDerBol;//boolean[colors.length];la filera es correspon amb la filera taulaF; si lafilera es true indica que algun simbol de funcioParcial es troba en la funcio de la filera de la taulaF; per seleccionar una filera especifica de la taula de FP s'utilitza la matriu varGenDerBol (a continuacio)
    public static boolean[][] varGenDerBol;//[i][j]: si la funcio parcial amb variables generals directes o indirectes j es present directa o indirectament mitjançant sumatoris a fila i de tualaF es true; conte variables generals es true sino false; la primera columna indica si algun simbol de funcioParcial es troba (directa o indeirectament mitjançant sumatoris) en la funcio de la filera de la taulaF; 
    public static boolean[] varSumatoriBol;//for(int k=0;k<taulaF.simbolsReduits.length;k++)if(!taulaF.simbolsReduitsNombreBol[k]){if(matriuVarAFun[l].contains(" "+taulaF.simbolsReduits[k]+" "))taulaF.varSumatoriBol[k]=true;per cada filera de taula de simbols Reduits; indica si el simbol de la taula columna 1(simbols reduits no numerics) es present a alguna integral;
    public static boolean[] varDerivadaBol;//per cada filera de taula reduida de funcions principal indica si hi ha variable de la funcio a la taula derivada, cada filera correspont a la filera de taulaF (reduida);
    //les dues parelles seguents son similars la primera parella referida a sumatoris la segona a funcions parcials 
    static ArrayList<int[][]>arr_indexsDerafuncioF;public static int[][] indexsDerafuncioF;//util per el calcul previ de una funcio parcial  sense variables generals  i passar el seu resultat a la funcio de la filera de la taulaF; utilitzat quant a la funcio de la filera hiha derivada primer index es la possicio a dVO de la filera de la funcio a taulaF els segon es la filera de la taula derivades quant ames hi ha variables generals directes o indirectes el trasllat del valor de la variable es fara un cop fixades les variables generals i calculada la funcio
    static ArrayList<int[][]>arr_indexsVGI;// arr de matrius Cal.indexsVGI(sumatoris amb variables generals); sumatoris amb variables generals que es troben a la funcio general el segon index es la columna del sumatoris taulaI, el primer es la posicio del sumatori a sVO de la funcio principal, 
    static ArrayList<int[][]>arr_indexsSumafuncioF;public static int[][] indexsSumafuncioF;//indexs dels sumatoris que no contenent variables generals  a les funcions de la taulaF taulaF.idxVarSum[coT][0]=posicio dVO;taulaF.idxVarSum[coT][1]=index del sumatori taula I;
    static ArrayList<int[][]>arr_indexsVGD;// arr de matrius Cal.indexsVGD; derivades que es troben a la funcio general el segon index es la posicio filera de la funcio parcial taulaD, el primer es la posicio de la funcio parcial a sVO de la funcio principal, 
    static ArrayList<int[][]>arr_indexsVGaD;
    static ArrayList<boolean[]>arr_hihaVGioFPaD;
    static ArrayList<int[][]>arr_derivadesLimFun;
    static ArrayList<boolean[][]>arr_VGaFuncBol_taulaD;

    static ArrayList<int[][]>arr_indexsVGaI;//suportID.indexsVGaI: inde[][0] index o posicio a dVOI de sumatoris [][1] index variable general reduida
    static ArrayList<int[]>arr_idxvarsdeFunDer;//taula de filera de taulaV presents en les funcions parcials derivables necesaries pel callcul de 
    static ArrayList<int[]>arr_idxVarGenIntegr;
    static ArrayList<boolean[][]>arr_indexsInteg_VG;
    static ArrayList<boolean[]>arr_hihaVGioFPaI;
    public static int[][] indexsSum;//el primer index[][0] indica la possicio relativa del simbol de la taulaF columna1(resultat) dins de dVOI de taula sumatoris; el segon index[][1] la filera del simbol a la taula de simbolsReduits (nomes nonumerics).
    public static int[][] indexsDer;//el primer index[][0] indica la possicio relativa del simbol de la taulaF (funcio principal) dins de dVOD de taula derivades; el segon index[][1] la filera del simbol del la taula de simbolsReduits (nomes nonumerics)., el tercer la possicio absoluta a dVO de la taula de derivades.
    static int[][] indexsVar;
    static int[][] indexsVarG;
    static int[][] limitindexsVarGen;
    static ArrayList<int[][]> copiaIndexs;
    static ArrayList<int[][]>arr_indexsParv;//[][0] possicio a dvo; [][1] possicio a taulaV
    static ArrayList<int[][]>arr_indexsParV;//indexsParV: mateixa longitud que taulaP.varPTV_varPTP
    static ArrayList<int[][]>arr_indexsParVG;//en el calcul de les funcions parcials (cada filera) aquest index permeten fer el calcul de la funcio parcial) quan no conte un parametre variable i si variable general a la funcio principal
    static ArrayList<int[][]>arr_indexsParNoVar;//fileres de taulaP que no contenent variables
    public static String[] sVO;
    public static double[] dVO;
    public static int[][] limdVO;
    public static int coordenades;
    static double[] resultat;
    static double[] res;
    static boolean[] hihaCondicio;//si a la filera hi ha condicio vol dir que cal executar la comprobacio y guardar a la filera de la matriu TrueFalse la condicio true o false
    static boolean[] hihaCondicioIndex;//si a la filera hi ha condicio vol dir que cal executar la comprobacio y guardar a la filera de la matriu TrueFalse la condicio true o false
    static boolean[][] thenBol;//si filera i columna if = t  s'executara 1.si la cadena  conte el simbol igual es fara la variable de la filera igual al valor de la funcio si no hi es fara un goto a la filera igual al valor darrera el simbol'=';
    static boolean[] trueFalse;//despres del calcul de la funcio a la mateixa filera un cop feta la comprobacio es guarda true o false
    static ArrayList<int[][]> arr_indexsTF;
    static ArrayList<int[][]> arr_indexsSimTF;
    static ArrayList<boolean[]> arr_bTF;
    static ArrayList<double[]> arr_dTF;
    static int[][] indexsTF;
    static int[][] indexsSimTF;
    static boolean[] bTF;
    static double[] dTF;
    static ArrayList<int[][]> arr_iThenPosSim;
    static ArrayList<int[][]> arr_jThenPosSim;//referit al segon o tresr grup de simbols que s'incorporara al return
    static int[] iThenGoSubRet;
    static int[][] iretCall;
    static int[][] iThenPosSim;
    static boolean[] exeTrueFalse;//si true vol dir que hi ha index  de fileres, condicio true false; i que s'executatra el goto (totes dues han de tenir contingut)
    static ArrayList<Boolean> conjunttrueFalseBol;// mateix nombre de arrays que de fileres si la filera no s'utlitza nomes conte false
    static ArrayList<Integer> conjunttrueFalse;// mateix nombre de arrays que de fileres si la filera no s'utlitza nomes conte un -1
    static int fila;
    static int idxIni=9,idxIf=2,idxThen=3,idxN=4,idxHis=5,idxTam=6,idxEix=7,idxCol=8;
    static int[][] tamany;
    static boolean[][] tamanyBol;
    public static JPanel pan;
    public static graficF graf;
    static int[] longi;
    static int[] longiP;
    static long[] cursor;
    static long[] cursorMinim;
    static double[] varAct;
    static double[] pondIncrVar;
    static boolean[] tI_VG_integralBol;//matriu del bolean Cal.integralBol si true vol dir que la filera de funcions conte una funcio de sumatori
    static boolean[] tD_VG_derivadaBol;//matriu del bolean Cal.derivadaBol
    static ArrayList<boolean[]> arr_longiPBol;
    static ArrayList<double[][]>arr_dVars;
    static ArrayList<long[]>arr_segCero;
    static boolean[] escalaLinial;
    static double[] ceroLog;
    static int[] digitsValids;
    static boolean[] noArrodonir;
    static String[] escala;//relacionat amb taulaC.definirEscala
    static ArrayList<int[]>arr_idxVarGen;
    static ArrayList<int[]>arr_idxParVar;
    static ArrayList<boolean[]>arr_vargOparv;
    static ArrayList<int[]>arr_idxresultatM;
    static ArrayList<int[]> arr_idxVarDer;
    static ArrayList<int[][]>arr_indexs;
    static ArrayList<int[][]>arr_indexsVar;
    static ArrayList<int[][]>arr_indexsVarG;
    static ArrayList<int[][]>arr_limitindexsVarGen;
    static ArrayList<String[]>arr_varstaulaV;//  simbols de les variables totes en el mateix ordre que la taulas
    public static int[][] indexsVarGenAFun;
    static ArrayList<int[][]>arr_indexsVarGenAFun;
    static  int callAntIndexSub=-1;
    public static boolean stopBucle;
    Thread cercleThread;
    static double [] res_varstaulaVFiCerca;//recull els resultata de totes les variables de la taulaV en la cerca de minims
    static boolean[] res_varstaulaVBol;
    static long[] res_cursortaulaVFiCerca;//en principi la possicio del cursor en el minim nomes cal pel calcul de derivades 
    public static boolean PARA;
    
    public static ArrayList<double[]>arr_xyPos;
    public static boolean retallgrafBol;
    public static int[][] relacioPuntOrigenDesti;
    static long contadorBucle;
    static boolean hihaSeleccioPuntsBol;
    public static Object [][] puntsTFColor;//conte els colors de les coordenades x,y,z el mateix nomre de fileres que puntsTF 2 columnes la primera la cadena que defineix el punt x y o z la segona el color
    static boolean[] colorAleatori;
    static double[] coloraleatori;
    public static int[][] grandariaPunt;
    public static int[][] igrandariaPunt;
    static int[][][] historia;//el primer index es el nombre de punts, el segon index es el nombre de valors historics el tercer el nombre de coordenades 2 o 3
    static boolean[][] historiaBol;
    static int[] historiaIntervalTemps;
    static Instant[] tempsHistoria;
    static int[] idxHistoria;
    static int[] ihistorial;
    public static ArrayList<int[]>relaciopuntsSeleccionats;
    public static boolean graficFet;
    static int[][] gofila;
    static boolean[][] gofilaBol;
    static int contadorRand;
    static int[] dimensioArr;
    static JMenuItem  jmi_insertaFila, jmi_treuFila;
    static JMenuItem  jmi_copiSel, jmi_ajuntaSel;
    static JMenuItem jmi_reiniciTaula, jmi_copi,jmi_cut,jmi_ajunta, jmi_carregar, jmi_save, jmi_saveAs, jmi_adjuntar;
    static JMenuItem jmi_arxAdjunt, jmi_reset, jmi_inici, jmi_taulaFuncions, jmi_font;
    static JTableHeader JThd;
    static JCheckBoxMenuItem cbM = new JCheckBoxMenuItem("scroll taula");
    static JCheckBoxMenuItem cbM1 = new JCheckBoxMenuItem("al inici carregar arxiu");
    static JCheckBoxMenuItem cbM4 = new JCheckBoxMenuItem("ampliar informes");
    static JCheckBoxMenuItem cbM6 = new JCheckBoxMenuItem("anular tooltiptext info.");
    static boolean[][] avisBol;
    
    protected String[] columnToolTips = {
        Func.rB.getString("tttC0"),Func.rB.getString("tttC1"),Func.rB.getString("tttC2"),Func.rB.getString("tttC3"),Func.rB.getString("tttC4"),
        Func.rB.getString("tttC5"),Func.rB.getString("tttC6"),Func.rB.getString("tttC7"),Func.rB.getString("tttC8"),Func.rB.getString("tttC9"),
        };

    public taulaF() {super(new GridLayout(1,0));inici();}
    public taulaF(boolean x){}
    public void INI(){
        cela = new JTextField();
        cela.setBorder(Func.border);
        cela.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if(col_tl==idxCol){Colors.colorsIntr(fil_tl);cela.setText("Color: "+Func.rB.getString("cuadrat"));return;}
            }
            public void mouseEntered(MouseEvent e) {}public void mouseExited(MouseEvent e) {}public void mousePressed(MouseEvent e) {}public void mouseReleased(MouseEvent e) {}
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
                try {if(Func.tooltiptxt)tip = "fil: "+rowIndex+";  "+getValueAt(rowIndex, colIndex).toString();} catch (RuntimeException e1) {}
                return tip;
            }
            protected JTableHeader createDefaultTableHeader() {
            return new JTableHeader(columnModel) {
                private static final long serialVersionUID = 1L;
                public String getToolTipText(MouseEvent e) {
                    java.awt.Point p = e.getPoint();
                    int index = columnModel.getColumnIndexAtX(p.x);
                    return columnToolTips[columnModel.getColumn(index).getModelIndex()];
                }
             };
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
                fil_tl = tl.rowAtPoint(e.getPoint());
                col_tl = tl.columnAtPoint(e.getPoint());
            }
            public void mouseEntered(MouseEvent e) {}public void mouseExited(MouseEvent e) {}public void mousePressed(MouseEvent e) {}public void mouseReleased(MouseEvent e) {}
    });
    tl.setAutoResizeMode(scrollCols);
    tl.putClientProperty("terminateEditOnFocusLost", true);
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
        jmi_copi= new JMenuItem("copiar");jmi_copi.setIcon(Func.Copiar());
        jmi_copi.addActionListener(this);
        popup.add(jmi_copi);
        jmi_cut= new JMenuItem("cut");jmi_cut.setIcon(Func.Copiar());
        jmi_cut.addActionListener(this);
        popup.add(jmi_cut);
        jmi_ajunta = new JMenuItem("ajuntar a cel.la"); jmi_ajunta.setIcon(Func.Engan());
         jmi_ajunta.addActionListener(this);
        popup.add( jmi_ajunta);
        popup.addSeparator();
        jmi_insertaFila = new JMenuItem("inserta filera");jmi_insertaFila.setIcon(Func.Novalinia());
       jmi_insertaFila.addActionListener(this);
        popup.add(jmi_insertaFila);
        jmi_treuFila = new JMenuItem("treu filera");jmi_treuFila.setIcon(Func.Treulinia());
        jmi_treuFila.addActionListener(this);
        popup.add(jmi_treuFila);
        popup.addSeparator();
        popup.add(cbM);
        popup.add(cbM);if(scrollCols==0){cbM.setState(true);}else{cbM.setState(false);}
        popup.addSeparator();
        jmi_reiniciTaula = new JMenuItem("reiniciar taula");jmi_reiniciTaula.setIcon(Func.Borrar());
        jmi_reiniciTaula.addActionListener(this);
        popup.add(jmi_reiniciTaula);
        popup.addSeparator();
        popup.addSeparator();
        jmi_adjuntar = new JMenuItem("adjuntar arxiu");
        jmi_adjuntar.addActionListener(this);
        popup.add( jmi_adjuntar);
        popup.add( jmi_arxAdjunt);
        popup.addSeparator();popup.addSeparator();
        jmi_carregar = new JMenuItem("carregar (load)");jmi_carregar.setIcon(Func.Obrirarx());
        jmi_carregar.addActionListener(this);
        popup.add( jmi_carregar);
        jmi_save = new JMenuItem("guardar (save)"); jmi_save.setIcon(Func.Guarda());
        jmi_save.addActionListener(this);
        popup.add( jmi_save);
        jmi_saveAs = new JMenuItem("guardar (save as)");jmi_saveAs.setIcon(Func.GuardaC());
        jmi_saveAs.addActionListener(this);
        popup.add(jmi_saveAs);
        jmi_reset = new JMenuItem("reset");jmi_reset.setIcon(Func.Nouarx1());
        jmi_reset.addActionListener(this);
        popup.add(jmi_reset);
        jmi_inici = new JMenuItem("inici"); jmi_inici.setIcon(Func.Nouarx());
        jmi_inici.addActionListener(this);
        popup.add(jmi_inici);
        popup.addSeparator();
        jmi_taulaFuncions = new JMenuItem("funci"+Func.rB.getString("o_")+" "+Func.rB.getString("u_")+"nica");;
        jmi_taulaFuncions.addActionListener(this);
        popup.add( jmi_taulaFuncions);
        popup.addSeparator();
        jmi_font = new JMenuItem("font"); jmi_font.addActionListener(this); popup.add(jmi_font);
        popup.addSeparator();
        popup.add(cbM1);
        cbM1.setState(Func.obrirDarrerArxiu);Func.cbM1.setState(Func.obrirDarrerArxiu);
        popup.add(cbM4);
        cbM4.setState(Func.ampliarInfo);Func.cbM4.setState(Func.ampliarInfo);
        popup.add(cbM6);
        cbM6.setState(Func.tooltiptxt);Func.cbM6.setState(Func.tooltiptxt);
        MouseListener ml = new TaulesPopup(popup);
        tl.addMouseListener(ml);
      }
  public static void font(){
    Font fo = new Font(font.tipus, font.estil, font.tamany);
    cbM.setFont(fo);
    cbM1.setFont(fo);
    cbM4.setFont(fo);
    cbM6.setFont(fo);
    jmi_insertaFila.setFont(fo);
    jmi_treuFila.setFont(fo);
    jmi_reiniciTaula.setFont(fo);
    jmi_reiniciTaula.setFont(fo);
    jmi_copi.setFont((fo));
    jmi_cut.setFont((fo));
    jmi_ajunta.setFont((fo));
    jmi_adjuntar.setFont((fo));
    jmi_arxAdjunt.setFont((fo));
    jmi_carregar.setFont((fo));
    jmi_save.setFont((fo));
    jmi_saveAs.setFont((fo));
    jmi_reset.setFont((fo));
    jmi_inici.setFont((fo));
    jmi_taulaFuncions.setFont((fo));
    jmi_font.setFont((fo));
    JThd.setFont(fo);
  }
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        String s = source.getText();
        if(s.equals("inserta filera")){if(contingutTaula(tl.getSelectedRows(),true)){nouModel();}}
        if(s.equals("treu filera")){if(contingutTaula(tl.getSelectedRows(),false)){nouModel();}}
        if ("copiar".equals(e.getActionCommand())) {copiaColors();splitPan.cop(tl,matriu,false);}
        if ("cut".equals(e.getActionCommand())) {copiaColors();splitPan.cop(tl,matriu,true);}
        if(s.equals("ajuntar a cel.la")){
            String[][] m=splitPan.paste();
            int[] row=tl.getSelectedRows();int col=tl.getSelectedColumn();
            boolean b=false;
            if(matriu[0].length<col+m[0].length){Func.append(1,"error: ");Func.append(0,"el nombre de columnes de la taulaF es fixa la copia implica un increment del nombre de columnes, no s'executa la copia;"+ splitPan.FIL);return;}
            if(matriu.length<=row[0]+m.length){
                int k=row[0]+m.length-matriu.length;
                int[] r=new int[1];r[0]=matriu.length-2;
                for(int i=0;i<=k;i++)contingutTaula(r,true);
                b=true;
            }
            if(b){nouModel();}
            for(int i=0;i<m.length;i++)for(int j=0;j<m[0].length;j++)tl.setValueAt(m[i][j], row[0]+i,col+j);
            for(int i=0;i<colorsSup.length;i++)colors[row[0]+i]=colorsSup[i];
            capsalInteg();
            return;
        }
        if ("copia sel.".equals(e.getActionCommand())) {splitPan.cop_sel(cela);return;}
        if(s.equals("ajunta sel.")){splitPan.paste_sel(cela);}
        if(s.equals("guardar (save)"))splitPan.guardarArx();
        if(s.equals("guardar (save as)")){splitPan.guardarArxCom();}
        if(s.equals("carregar (load)")){splitPan.carregaArx();}
        if (s.equals("reiniciar taula")) {reinici();}
        if(("inici").equals(e.getActionCommand())){Func.clearAll();}
        if(("reset").equals(e.getActionCommand())) { Func.guardaArxiuProvis();}
        if(("adjuntar arxiu").equals(e.getActionCommand())) trasllat.traslladar();
        if(("obrir arxiu adjunt").equals(e.getActionCommand()))trasllat.presentaDoc(trasllat.hihaArxiuAdjunt());
        if (("funci"+Func.rB.getString("o_")+" "+Func.rB.getString("u_")+"nica").equals(e.getActionCommand())) { Func.tipusFuncioBol=false;Func.funcImpl.reiniciparcial();}
        if ("font".equals(e.getActionCommand())) {font.inici();return;}
    }
    public static  void copiaColors(){
        colorsSup=new Color[0];
        int row=tl.getSelectedRow();int col=tl.getSelectedColumn();int rows=tl.getSelectedRows().length;int cols=tl.getSelectedColumns().length;
        for(int i=0;i<cols;i++)if(col+i==idxCol){
            colorsSup=new Color[rows];
            for(int j=0;j<rows;j++)colorsSup[j]=colors[j+row];
            i=cols;
        }
    }
    public void reinici(){ matriu=null;valorsInicials();nouModel();}
    public void gofila(){
        gofila=new int[colors.length][5];
        gofilaBol=new boolean[colors.length][4];
        for(int i=0;i<colors.length;i++){
            String s=matriu[i+1][idxThen];
            try{gofila[i][0]=Integer.parseInt(s);gofilaBol[i][0]=true;gofilaBol[i][1]=true;}
            catch(Exception e){
                int j=s.indexOf("(");
                if(j>0){
                    try{gofila[i][1]=Integer.parseInt(s.substring(0,j));gofila[i][2]=j;gofilaBol[i][0]=true;gofilaBol[i][2]=true;}
                    catch(Exception e1){}
                }
                j=s.lastIndexOf(")");
                if(j>0){
                    try{gofila[i][3]=Integer.parseInt(s.substring(j+1));gofila[i][4]=j;gofilaBol[i][0]=true;gofilaBol[i][3]=true;}
                    catch(Exception e1){}
                }
            }
        }
    }
    public void gofila(int fil,int interv,boolean bol){//bol=true introduir fila increment 1
        for(int i=0;i<gofilaBol.length;i++)if(!(!bol&&i>=fil&&i<fil+interv)){
            int incr=1;
            if(bol){if(i>fil)incr=2;else incr=1;}//si la fila en curs es troba per sobre de la fila seleccionada augmenta en una unitat la fila a la matriu
            if(!bol){if(i>fil)incr=1-interv;else incr=1;}
            if(gofilaBol[i][0]){
                if(gofilaBol[i][1]){
                    if(bol&&gofila[i][0]>fil)gofila[i][0]++; 
                    else if(!bol&&gofila[i][0]>fil)gofila[i][0]-=interv;
                    matriu[i+incr][idxThen]=gofila[i][0]+"";
                }
                else {
                    if(gofilaBol[i][2]){
                        if(bol&&gofila[i][1]>fil)gofila[i][1]++; 
                        else if(!bol&&gofila[i][1]>fil)gofila[i][1]-=interv;
                        matriu[i+incr][idxThen]=gofila[i][1]+matriu[i+incr][idxThen].substring(gofila[i][2]);
                    }
                    if(gofilaBol[i][3]){
                        if(bol&&gofila[i][3]>fil)gofila[i][3]++; 
                        else if(!bol&&gofila[i][3]>fil)gofila[i][3]-=interv;
                        matriu[i+incr][idxThen]=matriu[i+incr][idxThen].substring(0,gofila[i][4]+1)+gofila[i][3];
                    }
                }
            }
        }
    }
    public boolean contingutTaula( int[] fil,boolean bol){
        int cc=tl.getColumnCount();
    	int cf=tl.getRowCount()+1;
        String [][] matriuSup=new String[matriu.length][matriu[0].length];
        Color[]colorsSup=new Color[colors.length];
        for(int i=0;i<matriu.length;i++)System.arraycopy(matriu[i], 0, matriuSup[i], 0, matriu[0].length);
        System.arraycopy(colors, 0,colorsSup, 0, colors.length);
        gofila();
    	if(bol){
            int fi;
            if(fil.length==0){fil=new int[1];fi=0;}
            else{
                try{ fi=fil[0]+1;}
                catch(Exception e){fi=cf-1;}
            }
            if(fi>=0){//inserta filera
                matriu=new String [cf+1][cc];
                colors=new Color[cf];
                for(int j=0;j<cc;j++){
                    for(int i=0;i<=fi;i++){
                        try{matriu[i][j]=matriuSup[i][j];}
                        catch(java.lang.NullPointerException e){return false;}
                    }
                    matriu[fi+1][j]="";
                    for(int i=fi+1;i<cf;i++) matriu[i+1][j]=matriuSup[i][j];
                }
                for(int i=0;i<=fil[0];i++)colors[i]=colorsSup[i];
                for(int i=fil[0];i<colorsSup.length;i++) colors[i+1]=colorsSup[i];
                gofila((fil[0]),1,bol);
            }
    	}
    	else{
            if(fil.length==0){Func.append(1,"info: ");Func.append(0,"per eliminar una fila cal seleccionar-la"+ splitPan.FIL);return false;}
            int fi=fil[0]+1;
            int lo=fil.length;
            matriu=new String [cf-lo][cc];
            colors=new Color[colors.length-fil.length];
            for(int j=0;j<cc;j++){
                for(int i=1;i<fi;i++){matriu[i][j]=matriuSup[i][j];}
                for(int i=fi;i<cf-lo;i++){matriu[i][j]=matriuSup[i+lo][j];}
                }
            if(matriu.length<2){
                matriu=new String [2][cc];for(int j=0;j<cc;j++)matriu[1][j]="";
            }
            for(int i=0;i<fil[0];i++)colors[i]=colorsSup[i];
            for(int i=fil[0];i<colors.length;i++) colors[i]=colorsSup[i+lo];
            gofila((fil[0]),lo,bol);
            capsalInteg(); 
    	}
        return true;
    }
    public boolean matriuTaula(){//matriuTaula(): fa referenci a la taula actual no hi ha analisi comparatiu amb altres taules
        varDerivadaBol=new boolean[0];
        varSumatoriBol=new boolean[0];
        int cc=tl.getColumnCount();
        int cf=tl.getRowCount()+1;
        boolean hihaVar=false;
        for(int i=1;i<cf;i++)for(int j=0;j<cc;j++)matriu[i][j]=matriu[i][j].trim();
        for(int i=1;i<cf;i++)matriu[i][idxThen]=splitPan.treuBlancDeDos(matriu[i][idxThen]);
        for(int i=1;i<cf;i++)matriu[i][idxTam]=splitPan.treuBlancDeDos(matriu[i][idxTam]);
        if(cf>2){
            if(moufilBuides()){
                Func.append(1,"info: ");
                Func.append(0,"a taula de funcions superior 'taulaF', s'han eliminat files buides"+ splitPan.FIL);
                cc=tl.getColumnCount();
                cf=tl.getRowCount()+1;
                return false;
            }
        }
        for(int i=1;i<cf;i++){for(int j=0;j<cc;j++){if(!matriu[i][j].equals(""))hihaVar=true;}}
        if(!hihaVar){
            Func.append(1,"info: ");Func.append(0,"la taula de funcions superior 'taulaF', no te contingut"+ splitPan.FIL);
            return false;
        }
        filaNop=new boolean[cf-1];
        for(int i=1;i<cf;i++){
            if(matriu[i][0].equals(""))filaNop[i-1]=true;
            else {
                if(!matriu[i][0].equals("")&&matriu[i][1].equals("")){
                    Func.append(1,"info: ");
                    Func.append(0,"a taula de funcions superior fila: "+(i-1)+" la columna de funci"+Func.rB.getString("o_")+" te contingut i la de la variable associda al resultat no"+ splitPan.FIL);
                    return false;
                }
            }
        }
        boolean bo=false;
        for(int i=1;i<cf;i++){
            if(splitPan.esNum(matriu[i][1])!=null){
                Func.append(1,"info: ");
                Func.append(0,"a taula de funcions superior el s"+Func.rB.getString("i_")+"mbol de la variable associada: "+matriu[i][1]+" no pot "+Func.rB.getString("e_")+"sser un valor num"+Func.rB.getString("_e")+"ric"+ splitPan.FIL);
                bo=true;
                i=cf;
            }
        }
        for(int i=1;i<cf;i++){
            if(matriu[i][1].startsWith("-")){
                Func.append(1,"info: ");
                Func.append(0,"a taula de funcions superior el signe negatiu de la variable associada: "+matriu[i][1]+" no "+Func.rB.getString("e_")+"s v"+Func.rB.getString("a")+"lid"+ splitPan.FIL);
                bo=true;
                i=cf;
            }
        }
        if(bo)return false;
        tamany=new int[matriu.length-1][2];
        tamanyBol=new boolean[matriu.length-1][2];
        boolean tamanybol=false;
        for(int i=1;i<cf;i++)if(!matriu[i][idxTam].equals("")){tamanybol=true;tamanyBol[i-1][0]=true;}
        bo=false;
        String s1="";
        if(tamanybol){
            for(int i=1;i<cf;i++){
                if(tamanyBol[i-1][0]){
                    String s=matriu[i][idxTam];
                    s=splitPan.treuBlancDeDos(s);
                    String [] m=splitPan.matriudeFuncioCompleta(s);
                    //System.out.println(m.length);
                    //if(m.length==3)System.out.println(m[2]+" "+(10-Double.parseDouble(splitPan.comApun(m[2]))*8));
                    if(m.length==3){
                        try {graficF.reduccio=Double.parseDouble(splitPan.comApun(m[2]))/2;}catch(Exception e){graficF.reduccio=0.2;bo=true;s1+=(i-1)+"; ";}
                        if(graficF.reduccio<0)graficF.reduccio=0;
                        if(graficF.reduccio>0.5)graficF.reduccio=0.5;
                    }
                    if(m.length>1){tamanyBol[i-1][1]=true;try {tamany[i-1][1]=Integer.parseInt(m[1]);}catch(Exception e){bo=true;s1+=(i-1)+"; ";}}
                    try {tamany[i-1][0]=Integer.parseInt(m[0]);}catch(Exception e){bo=true;s1+=(i-1)+"; ";}
                }
            }
        }
        if(bo){
            Func.append(1,"error: ");
            Func.append(0,"a taula de funcions superior a columna '"+matriu[0][idxTam]+"', files: "+s1+", les "+Func.rB.getString("u_")+"niques cadenes v"+Func.rB.getString("a")+"lides son: " + splitPan.FIL);
            Func.append(0,"un o dos sencers positius  seguit o no de un 'double', els dos primers > cero i <=1000; el terce >=0 <=1; cal que estiguin separats per un espai en blanc i el segon sencer mes petit que el primer;" + splitPan.FIL);
            Func.append(0,"la grand"+Func.rB.getString("a")+"ria del punt es funci"+Func.rB.getString("o_")+" dels dos primers valors; la mida m"+Func.rB.getString("a")+"xima es calcula com la altura o amplada de la finestra (la m"+Func.rB.getString("e_")+"s petita) dividida pel primer valor" + splitPan.FIL);
            Func.append(0,"el segon sencer en el cas de tres dimensions es la mida m"+Func.rB.getString("i_")+"nima, reflecteix la profunditat; el tercer valor (un double 0<=d<=1) nom"+Func.rB.getString("e_")+"s cal que aparegui un cop: modifica la fondaria de la caixa de tres dimensions, quan mes gran, m"+Func.rB.getString("e_")+"s fonda es la caixa" + splitPan.FIL);
            return false;
        }
        bo=false;
        s1="";
        if(tamanybol)for(int i=0;i<tamany.length;i++){
            if(tamanyBol[i][1]){
                if(tamany[i][0]<tamany[i][1]||tamany[i][1]<1||tamany[i][1]>1000){
                    bo=true;
                    s1+=matriu[i+1][idxTam]+"; ";
                }
            }
            else if(tamanyBol[i][0]){
                if(tamany[i][0]<1||tamany[i][0]>1000){
                    bo=true;
                    s1+=matriu[i+1][idxTam]+"; ";
                }
            }
        }
        if(bo){
            Func.append(1,"error: ");
            Func.append(0,"a taula de funcions superior a columna '"+matriu[0][idxTam]+"', files: "+s1+", les "+Func.rB.getString("u_")+"niques cadenes v"+Func.rB.getString("a")+"lides son: " + splitPan.FIL);
            Func.append(0,"un o dos sencers positius  seguit o no de un 'double', els dos primers > cero i <=1000; el terce >=0 <=1; cal que estiguin separats per un espai en blanc i el segon sencer mes petit que el primer;" + splitPan.FIL);
            Func.append(0,"la grand"+Func.rB.getString("a")+"ria del punt es funci"+Func.rB.getString("o_")+" dels dos primers valors; la mida m"+Func.rB.getString("a")+"xima es calcula com la altura o amplada de la finestra (la m"+Func.rB.getString("e_")+"s petita) dividida pel primer valor" + splitPan.FIL);
            Func.append(0,"el segon sencer en el cas de tres dimensions es la mida m"+Func.rB.getString("i_")+"nima, reflecteix la profunditat; el tercer valor (un double 0<=d<=1) nom"+Func.rB.getString("e_")+"s cal que aparegui un cop: modifica la fondaria de la caixa de tres dimensions, quan mes gran, m"+Func.rB.getString("e_")+"s fonda es la caixa" + splitPan.FIL);
            return false;
        }//analisi de taula eixos i colors
        tempsPresHis=new int[colors.length][2];
        graficF.iHistorial=0;
        boolean b=false;
        for(int i=1;i<cf;i++){ 
            String s=matriu[i][idxHis];
            if(!s.equals("")){
                b=true;
                if(s.contains(";"))s=s.replace(";", " ");
                if(s.contains(" ")){
                    s=splitPan.treuBlancDeDos(s);
                    int j= matriu[i][idxHis].indexOf(" ");
                    try{
                        tempsPresHis[i-1][1]=Math.abs(Integer.parseInt(s.substring(j+1)));
                        if(graficF.iHistorial<tempsPresHis[i-1][1])graficF.iHistorial=tempsPresHis[i-1][1];
                        if(tempsPresHis[i-1][1]>1000||tempsPresHis[i-1][1]<1)bo=true;
                        s=s.substring(0,j);
                    }
                    catch(Exception e){bo=true;i=cf;}
                    
                }
                try{tempsPresHis[i-1][0]=Math.abs(Integer.parseInt(s)); if(tempsPresHis[i-1][0]>1000||tempsPresHis[i-1][0]<1)bo=true;}
                catch(Exception e){bo=true;i=cf;}
               
            }
            if(bo){
                Func.append(1,"info: ");
                Func.append(0,"a taula de funcions superior el recorregut del s"+Func.rB.getString("i_")+"mbol gr"+Func.rB.getString("a")+"fic ha d'esser un blanc, un sencer: 0<n<1000; seguit o no d'un segon sencer: 0<m<1000"+ splitPan.FIL);
                Func.append(0,"n es un temps en cent"+Func.rB.getString("_e")+"cimes de segon; m es el nombre de posicions que es guarden; si m no consta en alguna fila, aleshores pren el valor mes gran dels valors presents a altres files"+ splitPan.FIL);
                Func.append(0,"el gr"+Func.rB.getString("a")+"fic mostrara el recorregut del punt de les darreres: 'm' presentacions gr"+Func.rB.getString("a")+"fiques, separades un marge de temps=n"+ splitPan.FIL);
                return false;
            }
        }
        if(b&&graficF.iHistorial==0)graficF.iHistorial=100;
        eixos=new String[matriu.length-1][2];
        puntsBol=new boolean[matriu.length-1];
        for(int i=0;i<cf-1;i++){
            if(matriu[i+1][idxEix].length()>0){
                eixos[i][0]=matriu[i+1][idxEix].substring(0,1);
                if(matriu[i+1][idxEix].length()>1)eixos[i][1]=matriu[i+1][idxEix].substring(1);
                else eixos[i][1]=" ";
                puntsBol[i]=true;
            }
            else {eixos[i][0]="";eixos[i][0]="";}
        }
        bo=false;s1="";
        if(tamanybol)for(int i=0;i<tamany.length;i++)if(tamanyBol[i][0]&&!puntsBol[i]){s1+=s1+=matriu[i+1][idxTam]+"; ";bo=true;}
        if(bo){Func.append(0,"avis: ");Func.append(0,"a taula de funcions superior a columna '"+matriu[0][idxTam]+"', fileres: "+s1+", tenen contingut pero no s'han associat a cap eix: " + splitPan.FIL);}
        
        
        //thenBol[i][0]=true indica que la columna if te contingut es tracta de una execucio condicional;
        //thenBol[i][1] true indica que hi ha informacio a la columna then ; 
        //thenBol[i][2]=true igualtat si false goto; 
        //thenBol[i][3]= true hi ha un simbol despres de '=' el valor a asignar al resultat de la funcio es el de parametre capçalera o variable de taulaF;
        //thenBol[i][4]= true es copia o call
        //thenBol[i][5]= es copia de simbolsDeVariable
        //thenBol[i][6]= es subrutina inici
        //thenBol[i][7]= es un return
        //thenBol[i][8]= hi ha goto despres de copia de simbols
        //thenBol[i][9]= indica que es representara graficament el resultat
        /*
        info:
        columnes if; then;  then pres;
        matriu: thenBol
        columna then pres si filera  then pres conte ‘graf’  executa presentacio grafica >>thenBol[fila][9]=true
        si conte fi; stop; end;  finlitza la execucio>>
        si conte pc; ‘;’;  presenta el resultat sense salt de fila
        si conte nl; ‘n’;  presenta el resultat sense salt de fila
        si la columna if tecontingut: thenBol[fila][1]=true
        si la columna then tecontingut: thenBol[fila][0]=true
        si la columna then te contingut >>thenBol[fila][4]=true
        si la columna then conte ‘)(‘ hi ha copia de simbols
        si primer simbol es ‘(‘ no hi ha subrutina
        si primer simbol es ‘(‘  darrer simbol es numeric: hi ha copia i goto>>thenBol[fila][8]=true
        si primer simbol es ‘(‘  darrer simbol no es numeric: hi ha copia i goto>>thenBol[fila][5]=true
        */
        
        thenBol=new boolean[colors.length][10];//correspon a la columna goto si [][0] true vol dir que s'executara el goto
        freqPresN=new int[0][0];
        freqPresNBol=false;
        freqPresNBol0=new boolean[colors.length];
        int conta=0;
        sfreqPres=new String[colors.length];
        tamanyPuntGraf=new int[colors.length];
        colorAleatori=new boolean[colors.length];coloraleatori=new double[colors.length];
        contadorRand=-1;
        for(int i=0;i<colors.length;i++)if(matriu[i+1][idxCol].startsWith("rand(")&&matriu[i+1][idxCol].endsWith(")")){
            colorAleatori[i]=true;
            try{coloraleatori[i]=(double)Double.parseDouble(splitPan.comApun(matriu[i+1][idxCol].substring(5,matriu[i+1][idxCol].length()-1)));}
            catch(Exception e){coloraleatori[i]=0;}
        }
        pausa=new long[colors.length];
        for(fila=0;fila<colors.length;fila++){
            String c=matriu[fila+1][idxN];
            int i=c.indexOf("graf");
            if(i>-1){
                thenBol[fila][9]=true;
                c=c.replace("graf","");
                graficF.tipusGrafic=new boolean[4];
                for(int j=0;j<3;j++){
                    b=false;
                    if(c.length()>i){
                        String s=c.substring(i,i+1);
                        if(s.equalsIgnoreCase("p")){graficF.tipusGrafic[0]=true;c=c.substring(0,i)+c.substring(i+1);b=true;}
                        if(s.equalsIgnoreCase("t")){graficF.tipusGrafic[2]=true;c=c.substring(0,i)+c.substring(i+1);b=true;}
                        if(s.equalsIgnoreCase("r")){graficF.tipusGrafic[1]=true;c=c.substring(0,i)+c.substring(i+1);b=true;}
                        if(s.equalsIgnoreCase("f")){graficF.tipusGrafic[3]=true;c=c.substring(0,i)+c.substring(i+1);b=true;}
                    }
                    if(!b)j=3;
                }
            }
            if(c.equals("")) sfreqPres[fila]="";
            else{
                if(c.contains("pausa"))c=c.replace("pausa","wait");
                if(c.contains("wait")){
                    int li=c.indexOf("wait(");
                    int ls=c.indexOf(")");
                    if(li>-1&&ls>li){
                        try{
                            pausa[fila]=(long)(Double.parseDouble(c.substring(li+5,ls)));
                            freqPresNBol0[fila]=true;
                            sfreqPres[fila]="wait";
                            c=c.replace(c.substring(li,ls+1),"");
                        }
                        catch(Exception e){b=true;}
                    }
                    else b=true;
                }
                if(c.contains("fi")){c=c.replace("fi","");sfreqPres[fila]+="fi";freqPresNBol0[fila]=true;}
                else if(c.contains("stop")){c=c.replace("end","");sfreqPres[fila]+="fi";freqPresNBol0[fila]=true;}
                else if(c.contains("end")){c=c.replace("end","");sfreqPres[fila]+="fi";freqPresNBol0[fila]=true;}
                if(c.contains(";")){ sfreqPres[fila]+=";";freqPresNBol0[fila]=true;}
                else if(c.contains("nlnl")||c.contains("nln")||c.contains("nnl")||c.contains("nn")){ sfreqPres[fila]+="nn";freqPresNBol0[fila]=true;}
                else if(c.contains("nl")||c.contains("n")){ sfreqPres[fila]+="n";freqPresNBol0[fila]=true;}
                if(c.contains("+-")){ sfreqPres[fila]+="+-";freqPresNBol0[fila]=true;}
                else if(c.contains("-+")){ sfreqPres[fila]+="-+";freqPresNBol0[fila]=true;}
                if(!freqPresNBol0[fila]){freqPresNBol=true;sfreqPres[fila]=c;conta++;}
            }
        }
        int antFreq=-1;
        if(conta>0){
            freqPresN=new int[conta][2];
            tempsFreq=new Instant[conta];
            conta=0;
            for(int i=0;i<colors.length;i++){       
                if(!sfreqPres[i].equals("")&&!sfreqPres[i].contains("n")&&!sfreqPres[i].equals(";")&&!sfreqPres[i].equals("fi")&&!sfreqPres[i].equals("-")){
                    if(antFreq>-1&&sfreqPres[i].equals("=")){
                        freqPresN[conta][1]=antFreq;
                        freqPresN[conta][0]=i;
                        conta++;
                    }
                    else{
                        try{ 
                            freqPresN[conta][1]=Integer.parseInt(sfreqPres[i]);
                            if(freqPresN[conta][1]>0){
                                antFreq=freqPresN[conta][1];
                                freqPresN[conta][0]=i;
                                sfreqPres[i]="";
                                conta++;
                            }
                        }
                        catch(Exception e){b=true;Func.append(1,"error: ");Func.append(0,"fila: "+i+splitPan.FIL); }
                    }
                }
            }
            if(b){
                    Func.append(1,"error: ");
                    Func.append(0,"a taula de funcions superior a la columna '"+matriu[0][idxN]+"'  les cadenes v"+Func.rB.getString("a")+"lides son 'graf' per presentar el gr"+Func.rB.getString("a")+"fic despres del calcul seguit o no dels caracters:"+splitPan.FIL);
                    Func.append(0,"p(projecci"+Func.rB.getString("o_")+" dels punts sobre el pla xy),"+splitPan.FIL);
                    Func.append(0,"t(els punts es fan transparents),"+splitPan.FIL);
                    Func.append(0,"r(gruix del recorregut = tamany del punt),"+splitPan.FIL);
                    Func.append(0,"f(en la selecci"+Func.rB.getString("o_")+" dels punts, el punt seleccionat no passa ha esser el centre del gràfic);"+splitPan.FIL);
                    Func.append(0,"io una de les seguents opcions: "+ splitPan.FIL);
                    Func.append(0,"un sencer positiu >1 que indica la freq"+Func.rB.getString("u")+Func.rB.getString("_e")+"encia de presentaci"+Func.rB.getString("o_")+" del resultat de la filera en d"+Func.rB.getString("_e")+"cimes de segon o el s"+Func.rB.getString("i_")+"mbol  '=', que pren el valor de la fila superior mes propera en que hi hagi un sencer;"+ splitPan.FIL);
                    Func.append(0,"nom"+Func.rB.getString("e_")+"s si la columna '"+matriu[0][idxIf]+"' es true o buida;  les cadenes 'nl'; 'n'; 'nlnl; 'nn' per presentar el resultat de la filera i salt de fila o ';'  sencer salt de fila; o'fi' o 'end' per finalitzar la execuci"+Func.rB.getString("o_")+"."+ splitPan.FIL);
                    Func.append(0,"nom"+Func.rB.getString("e_")+"s si la columna '"+matriu[0][idxIf]+"' es true o buida;  la cadena 'pausa' o 'wait' seguit de un nombre entre parentesi que indica el nombre de centesimes de segon de pausa."+ splitPan.FIL);
                    bo=true;
                    return false;
            }
        }
        conta=0;
        String cd=" ";
       /* for(int i=1;i<cf;i++){ 
            if(!matriu[i][idxThen].equals("")){
                if(matriu[i][idxThen].contains(")")&&matriu[i][idxThen].contains("(")){
                    String t=matriu[i][idxThen];
                    for(int j=0;j<t.length();j++){String s0=t.substring(j,j+1);if(!s0.equals("(")&&!s0.equals(")")){t=t.substring(0,j)+t.substring(j+1);j--;}}
                    if(!splitPan.analitzaParentesi(t)){
                        Func.append(1,"error: ");
                        Func.append(0,"a taula de funcions superior a la columna '"+matriu[0][idxThen]+"; fila:"+i+"; cadena:"+matriu[i][idxThen]+" comprovar els par"+Func.rB.getString("_e")+"ntesi "+ splitPan.FIL);
                        return false;
                    }
                    matriu[i][idxThen]=matriu[i][idxThen].replaceAll(";", " ");
                    matriu[i][idxThen]=splitPan.treuBlancDeDos(matriu[i][idxThen]);
                    int j=matriu[i][idxThen].indexOf("( ");while(j>-1){matriu[i][idxThen]=matriu[i][idxThen].replace("( ", "(");j=matriu[i][idxThen].indexOf("( ");}
                    j=matriu[i][idxThen].indexOf(" (");while(j>-1){matriu[i][idxThen]=matriu[i][idxThen].replace(" (", "(");j=matriu[i][idxThen].indexOf(" (");}
                    j=matriu[i][idxThen].indexOf(") ");while(j>-1){matriu[i][idxThen]=matriu[i][idxThen].replace(") ", ")");j=matriu[i][idxThen].indexOf(") ");}
                    j=matriu[i][idxThen].indexOf(" )");while(j>-1){matriu[i][idxThen]=matriu[i][idxThen].replace(" )", ")");j=matriu[i][idxThen].indexOf(" )");}
                    j=matriu[i][idxThen].indexOf("(");
                    int k=matriu[i][idxThen].lastIndexOf(")");
                    cd+=matriu[i][idxThen].substring(j+1,k).replace(")("," ")+" ";
                }
            }
        }*/
        boolean[] esSubrutina=new boolean[colors.length+1];
        for(int i=1;i<cf;i++){
            if(!matriu[i][idxThen].equals("")){
                String c=matriu[i][idxThen];
                int j=c.indexOf(")(");if(j>-1){
                    j=c.indexOf(")(",j+1);if(j==-1){
                        j=c.indexOf("(");if(j>0){
                            c=c.substring(0,j);if(!c.equals("=")){
                                try{int k=Integer.parseInt(c); esSubrutina[k]=true; }
                                catch(Exception e){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior a la columna '"+matriu[0][idxThen]+"; fila:"+i+"; cadena:"+matriu[i][idxThen]+" comprovar el sencer inicial; aquest no es correspon amb una fila definida"+ splitPan.FIL);return false;}
                            }
                        }
                    }
                }
            }
        }
        
        //for(int i=1;i<esSubrutina.length;i++)System.out.print(i+" "+esSubrutina[i]+" _ ");System.out.println();
        for(int i=1;i<cf;i++){ 
            if(!matriu[i][idxThen].equals("")){
                if(matriu[i][idxThen].contains(")")&&matriu[i][idxThen].contains("(")){
                    String t=matriu[i][idxThen];
                    for(int j=0;j<t.length();j++){String s0=t.substring(j,j+1);if(!s0.equals("(")&&!s0.equals(")")){t=t.substring(0,j)+t.substring(j+1);j--;}}
                    if(!splitPan.analitzaParentesi(t)){
                        Func.append(1,"error: ");
                        Func.append(0,"a taula de funcions superior a la columna '"+matriu[0][idxThen]+"; fila:"+i+"; cadena:"+matriu[i][idxThen]+" comprovar els par"+Func.rB.getString("_e")+"ntesi "+ splitPan.FIL);
                        return false;
                    }
                    matriu[i][idxThen]=matriu[i][idxThen].replaceAll(";", " ");
                    matriu[i][idxThen]=splitPan.treuBlancDeDos(matriu[i][idxThen]);
                    int j=matriu[i][idxThen].indexOf("( ");while(j>-1){matriu[i][idxThen]=matriu[i][idxThen].replace("( ", "(");j=matriu[i][idxThen].indexOf("( ");}
                    j=matriu[i][idxThen].indexOf(" (");while(j>-1){matriu[i][idxThen]=matriu[i][idxThen].replace(" (", "(");j=matriu[i][idxThen].indexOf(" (");}
                    j=matriu[i][idxThen].indexOf(") ");while(j>-1){matriu[i][idxThen]=matriu[i][idxThen].replace(") ", ")");j=matriu[i][idxThen].indexOf(") ");}
                    j=matriu[i][idxThen].indexOf(" )");while(j>-1){matriu[i][idxThen]=matriu[i][idxThen].replace(" )", ")");j=matriu[i][idxThen].indexOf(" )");}
                    t=matriu[i][idxThen];
                    int pos=t.indexOf(")");
                    if(pos<t.length()){//els simbols del primer parentesi es limiten a valors numerics i assays indexats la resta de cadenes presents a simbolsT o simbolrsReduits etc s'han de definir a taulaP columna1 columna3 dins els parenteri desti de resultats etc
                        int posi=t.indexOf("(")+1;
                        String s=splitPan.treuSimbolsIdentics(t.substring(posi,pos));
                        s=splitPan.treuBlancDeDos(s).trim()+" ";
                        String[] c=splitPan.matriudeFuncioCompleta(s);
                        if(esSubrutina[i-1]){
                            if(!parCap(i,c))return false;
                            for(int k=0;k<c.length;k++){cd+=c[k]+" ";b=true;}
                        }
                        else{
                            for(int k=0;k<c.length;k++){
                                b=false;
                                if(c[k].endsWith("]")&&c[k].contains("[")){cd+=c[k]+" ";b=true;}
                                else{
                                    for(int l=0;l<taulaP.simParCapNoVar.length;l++)if(c[k].equals(taulaP.simParCapNoVar[l])){cd+=c[k]+" ";b=true;l=taulaP.simParCapNoVar.length;}
                                    if(!b)for(int l=0;l<taulaP.simParCapVar.length;l++)if(c[k].equals(taulaP.simParCapVar[l])){cd+=c[k]+" ";b=true;l=taulaP.simParCapVar.length;}
                                }
                                if(!b){try{Double.parseDouble(splitPan.comApun(c[k]));cd+=c[k]+" ";}catch(Exception e){} }
                            }
                            t=t.substring(pos+1);
                            if(t.length()>0){//els simbols de taulaP simbols de capçalera de una flla i dimensions de arr predefinita columna 9 amb el signe '=' no poden estar  presents en el desti
                                j=t.indexOf("(");
                                if(j>-1){
                                    int k=t.lastIndexOf(")");
                                    t=t.substring(j+1,k).replace(")("," ")+" ";
                                    cd+=t;
                                    c=splitPan.matriudeFuncioCompleta(t);
                                    if(!parCap(i,c))return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        for(int i=0;i<taulaP.simParCapUnaFil.length;i++)cd+=taulaP.simParCapUnaFil[i]+" ";//incorporar a taulaP els simbols de capçalera de una fila
        for(int i=1;i<cf;i++){ 
            if(!matriu[i][idxIf].equals("")){
                String s=matriu[i][idxIf].replaceAll(">", " ");
                s=s.replaceAll("<", " ");
                s=s.replaceAll("=", " ");
                s=s.replaceAll("&", " ");
                int k=s.indexOf("|");while (k>0){s=s.substring(0,k)+s.substring(k+1);k=s.indexOf("|");}
                s=s.replaceAll("!", " ");
                s=splitPan.treuSimbolsIdentics(s);
                s=splitPan.treuBlancDeDos(s).trim()+" ";
                String[] c=splitPan.matriudeFuncioCompleta(s);
                int cont=0;
                for(int j=0;j<c.length;j++){if(c[j].endsWith("]")&&c[j].contains("["))cd+=c[j]+" ";}
            }
        }
        cd=splitPan.treuSimbolsIdentics(cd);
        cd=splitPan.treuBlancDeDos(cd).trim()+" ";
        StringTokenizer scd = new StringTokenizer(cd);
        conta=scd.countTokens();
        String cdSup="";
        for(int i=0;i<conta;i++){
            int k=cd.indexOf(" ");
            String c=cd.substring(0,k);
            cd=cd.substring(k+1);
            b=false;
            for(int j=0;j<colors.length;j++){if ((" "+c+" ").equals(" "+matriu[j+1][1]+" ")){ b=true;}}
            if(!b){cdSup+=c+" ";}
        }
        scd = new StringTokenizer(cdSup);
        conta=scd.countTokens();
        int lo=colors.length+conta;
        simbolsT=new String[lo];
        simbolsTNombreBol=new boolean[lo];
        funcioT=new String[colors.length];
        idxSimbolFilera=new int[lo];
        simbolsReduits=new String[lo];
        fixcontidxmatriu=new boolean[colors.length];
        for(int i=1;i<cf;i++){ 
            funcioT[i-1]=matriu[i][0];
            simbolsT[i-1]=matriu[i][1];
            if(!simbolsT[i-1].equals("")){
                String c=simbolsT[i-1];
                int j=c.indexOf("[[");if(j>-1){
                    if(c.endsWith("]]")){
                        simbolsT[i-1]=c.substring(0,j)+c.substring(j+1,c.length()-1);
                        c=c.substring(0,j)+c.substring(j+2,c.length()-2);
                        if(c.contains("[")||c.contains("]")){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior a la columna '"+matriu[0][1]+"; fila:"+i+"; cadena:"+matriu[i][1]+" comprovar els simbols'[' i' ]']; nomes dues possibilitats: 'cadena[index]'per seleccionar un element de matriu  o 'cadena[[index]] (poc utilitzat)' per ames modificar el contador interna de la matriu (sense index),desti d'una copia."+ splitPan.FIL);return false;}
                        fixcontidxmatriu[i-1]=true;
                    }
                }
            }
        }
        //for(int i=0;i<fixcontidxmatriu.length;i++)System.out.print(i+" "+fixcontidxmatriu[i]+" _ ");
        for(int i=0;i<conta;i++){
            int j=cdSup.indexOf(" ");
            simbolsT[colors.length+i]=cdSup.substring(0,j);
            cdSup=cdSup.substring(j+1);
        }
        System.arraycopy(simbolsT, 0, simbolsReduits, 0, simbolsT.length);
        conta=0;
        for(int i=0;i<simbolsT.length;i++){
             for(int j=i+1;j<simbolsT.length;j++){
                if(!simbolsReduits[i].equals("")&&simbolsReduits[i].equals(simbolsReduits[j])){simbolsReduits[j]="";}
            }
            if(!simbolsReduits[i].equals(""))conta++;
        }
        String st[]=new String[conta];
        conta=0;
        for(int i=0;i<simbolsT.length;i++){
            if(!simbolsReduits[i].equals("")){
                st[conta]=simbolsReduits[i];
                conta++;
            }
        }
        simbolsReduits=new String[st.length];
        System.arraycopy(st, 0, simbolsReduits, 0, st.length);
        for(int i=0;i<simbolsReduits.length;i++){
            if(simbolsReduits[i].contains("]")){
             b=false;
             String s=simbolsReduits[i].substring(0,simbolsReduits[i].length()-1);
             int j=s.indexOf("[");
             if(j>-1){
                 s=s.substring(0,j)+s.substring(j+1);
                 if(s.contains("[")||s.contains("]"))b=true;
             }
             else b=true;
             if(b){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior els caracters '[ ]' es limiten a seleccionar els elements de matrius, un caracter inicial i un final per cadena o be dos en el cas de modificar el contador de la matriu: '[[ ]]: "+simbolsReduits[i]+splitPan.FIL);return false;}
            }
        }
        simbolsReduitsNombreBol=new boolean[simbolsReduits.length];
        for(int i=0;i<simbolsT.length;i++){
            for(int j=0;j<simbolsReduits.length;j++){
                if(simbolsT[i].equals(st[j]))idxSimbolFilera[i]=j;
            }
        }
        boolean repe=false;
        for(int i=0;i<colors.length;i++)if(!repe&&matriu[i+1][idxThen].equals("")&&!matriu[i+1][idxIf].equals("")&&matriu[i+1][idxN].equals("")){
            repe=true;
            Func.append(1,"avis: ");
            Func.append(0,"a taula de funcions superior columna '"+matriu[0][idxIf]+"', hi ha files amb contingut i alhora" + splitPan.FIL);
            Func.append(0,"les files de les columnes posteriors: "+matriu[0][idxThen]+" i "+matriu[0][idxN]+" estan buides," + splitPan.FIL);
        }
        if(!callSub())return false;//analisi columna then
        if(!columnaIf_i_substitucioDeParametres())return false;
        return true;
    }
    private static boolean parCap(int i,String[] c){
        for(int k=0;k<c.length;k++){
            boolean b=false;
            for(int l=0;l<taulaP.simParCapUnaFil.length;l++)if(c[k].equals(taulaP.simParCapUnaFil[l])){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior a la columna '"+matriu[0][idxThen]+"; fila:"+i+"; cadena:"+matriu[i][idxThen]+" els simbols de capçalera de una fila o dimensions de arrays son valors fixos."+ splitPan.FIL);return false;}
        }                 
        return true;
    }
    private static boolean callSub(){//analisi columna then
        arr_iThenPosSim=new ArrayList<int[][]>();
        arr_jThenPosSim=new ArrayList<int[][]>();
        for(int i=0;i<colors.length;i++){arr_iThenPosSim.add(new int[0][0]);arr_jThenPosSim.add(new int[0][0]);}
        iThenGoSubRet=new int[colors.length];for(int i=0;i<colors.length-1;i++)iThenGoSubRet[i]=i+1;
        iThenGoSubRet[colors.length-1]=0;
        iretCall=new int[colors.length][3];  
        for(int i=0;i<colors.length;i++){
            String strue=matriu[i+1][idxThen];
            if(!strue.equals("")){
                thenBol[i][1]=true;//hi ha informacio a columna then
                thenBol[i][8]=true;
                try{
                    iThenGoSubRet[i]=Integer.parseInt(strue);
                    if(iThenGoSubRet[i]<0||iThenGoSubRet[i]>colors.length-1||iThenGoSubRet[i]==i)thenBol[i][8]=false;
                }
                catch(Exception e){thenBol[i][8]=false;}
                if(strue.contains(")(")){
                    thenBol[i][4]=true;//pot esser copia o call
                    if(!callSub(i,strue)){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior columna '"+matriu[0][idxThen]+"', fila: "+i+ " possible cadena indefinida o incompatibilitat entre cadenes"+splitPan.FIL);return false;}
                        
                }
            }
        }
        boolean[] b=new boolean [colors.length];for(int i=0;i<colors.length;i++){String st=matriu[i+1][idxThen];if(st.startsWith("(")&&!st.contains(")(")&&st.endsWith(")"))b[i]=true;}
        String s="";
        for(int i=0;i<colors.length;i++)if(thenBol[i][4])s+=iThenGoSubRet[i]+" ";
        int[] id=splitPan.matriudeFuncioInt1(s);
        for(int i=0;i<colors.length;i++){
            for(int o=0;o<id.length;o++)if(i==id[o]){//id[] indica les fileres inicials de subrutina en el marge de dos inicis de subrutina nomes hi pot haver un return
                int cont=0;
                int k=colors.length;
                for(int j=i+1;j<colors.length;j++)for(int p=0;p<id.length;p++)if(j==id[p]){ k=j;p=id.length;j=colors.length;}
                for(int l=i+1;l<k;l++)if(b[l]){
                    cont++;
                    if(cont>1){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior columna '"+matriu[0][idxThen]+"', per cada proces parcial(subrutina) nomes i pot haver un sol return ex:" +matriu[l+1][idxThen]+ splitPan.FIL);return false;}
                }
            }
        }
        //System.out.println("llistat de fileres afectades per subrutines iThenGoSubRet indica la fila seguent: ");
        //for(int j=0;j<colors.length;j++)if(iThenGoSubRet[j]>=0)System.out.print(j+" "+iThenGoSubRet[j]+" _ ");System.out.println();
        //System.out.println("iThenPosSim conte [0]  simbol columna 1 origen de copia [1] possicio a simbols reduits [2] i[3] igual pel desti: ");
        //for(int i=0;i<colors.length;i++){iThenPosSim=arr_iThenPosSim.get(i); if(iThenPosSim.length>0){System.out.print("fila: "+i+"  iThenPosSim: ");for(int l=0;l<iThenPosSim.length;l++)System.out.print("("+simbolsReduits[iThenPosSim[l][0]]+" "+simbolsReduits[iThenPosSim[l][1]]+") "+iThenPosSim[l][0]+" "+iThenPosSim[l][1]+" "+iThenPosSim[l][2]+" "+iThenPosSim[l][3]+"  _  ");System.out.println();}}
        //for(int i=0;i<colors.length;i++){iThenPosSim=arr_jThenPosSim.get(i); if(iThenPosSim.length>0){System.out.print("fila: "+i+"  iThenPosSim: ");for(int l=0;l<iThenPosSim.length;l++)System.out.print("("+simbolsReduits[iThenPosSim[l][0]]+" "+simbolsReduits[iThenPosSim[l][1]]+") "+iThenPosSim[l][0]+" "+iThenPosSim[l][1]+" "+iThenPosSim[l][2]+" "+iThenPosSim[l][3]+"  _  ");System.out.println();}}
       return true;
    }
    private static boolean callSub(int fil,String strue){
        boolean b=true;
        int i=strue.indexOf("(");
        int j=strue.indexOf(")(");
        int k=-1;
        if(j>-1)k=strue.indexOf(")(",j+1);
        int l=strue.lastIndexOf(")");
        int iThenGoSubRetCopia=-1;
        if(l!=(strue.length()-1)){//hi ha informacio al final del segon grup de parentesi
            String s=strue.substring(l+1);
            b=splitPan.es0_9(s);
            if(b){
                try{
                    iThenGoSubRet[fil]=Integer.parseInt(s);
                    if(iThenGoSubRet[fil]<0||iThenGoSubRet[fil]>colors.length-1||iThenGoSubRet[fil]==fil)b=false;
                }
                catch(Exception e){b=false;}
            }
            if(!b){errorThen(strue,fil);return false;}  
            thenBol[fil][8]=true;//hi ha un sencer darrera l'ultim parentesi per tant goto sencer
            iThenGoSubRetCopia=iThenGoSubRet[fil];
        }
        if(j>-1){//hi ha almenys un ')('
            if(i==0&&k==-1){//hi ha nomes un ')(' i el primer simbol es ( : es tracta de copia se resultat entre simbols no cerca la subrutina el primer igrup () al subindex [0] iThenPosSim[i][0]
                    if(!thenPosSim(strue.substring(1,j),false)||!thenPosSim(strue.substring(j+2,l),true))return false;
                    if(!errorNumaPar(strue,fil))return false;
                    arr_iThenPosSim.set(fil,iThenPosSim);
                    thenBol[fil][5]=true;//hi ha copia de simbols
                    return true;
            }//en aquest punt ha finalitzat la opcio de copia de simbols i eventual goto incondicional; ha partir 'aqui es tracta de un call o un return
            if(i>0){//hi ha cadena al principi es un call primer es calcula la filera de la subrutina
                String s=strue.substring(0,i);
                if(s.equals("=")&&callAntIndexSub>-1)iThenGoSubRet[fil]=callAntIndexSub;
                else{
                    b=splitPan.es0_9(s);
                    if(b){
                        try{
                            iThenGoSubRet[fil]=Integer.parseInt(s);
                            if(iThenGoSubRet[fil]<0||iThenGoSubRet[fil]>colors.length-1||iThenGoSubRet[fil]==fil)b=false;
                        }
                        catch(Exception e){b=false;}
                        callAntIndexSub=iThenGoSubRet[fil];
                    }
                    if(!b){errorThen(strue,fil);return false;}  
                }
                thenBol[fil][4]=true;//es tracta de un call
                if(k>-1){//hi ha  dos ')(' la comprobacio de compatibitats entre simbos call_inici subrutina  es fa en la mateixa fila
                    if(!thenPosSim(strue.substring(i+1,j),false)||!thenPosSim(strue.substring(j+2,k),true)){errorThen(strue, fil);return false;}
                    if(!errorNumaPar(strue,fil))return false;
                    j=k;//els simbols desti es troban en el tercer grup
                }
                else{
                    String strue1=matriu[iThenGoSubRet[fil]+1][idxThen];
                    if(!strue1.startsWith("(")||strue1.indexOf(")(")>-1||!strue1.endsWith(")")){errorThen(strue, fil);return false;}
                    if(!thenPosSim(strue.substring(i+1,j),false)||!thenPosSim(strue1.substring(1,strue1.length()-1),true)){errorThen(strue, fil);return false;}
                    if(!errorNumaPar(strue1,fil))return false;
                    thenBol[iThenGoSubRet[fil]][6]=true;//filera de subrutina que conte simbols de variables desti del call
                } 
                arr_iThenPosSim.set(fil,iThenPosSim);
                if(k>-1)k=0;
                else k=1;
                for(int m=iThenGoSubRet[fil]+k;m<colors.length;m++){
                    String st=matriu[m+1][idxThen];
                    boolean b1=st.contains(")(");
                    if(st.startsWith("(")&&!b1&&st.endsWith(")")){//m sera la primera filera que complira els requisits del return
                       //el segon subindex es modificara en el temps d'execucio els valors assignats seran els de la segona o tercer grup de simbols
                        if(!thenPosSim(st.substring(1,st.length()-1),false)||!thenPosSim(strue.substring(j+2,l),true)){errorThen(strue, fil);return false;}
                        if(!errorNumaPar(strue,fil))return false; //arr_iThenPosSim.set(m,iThenPosSim);//a la filera del return es guarden els index de trasllat de valors la primea part la segona dependra de quin call crida la subrutina
                        arr_jThenPosSim.set(fil,iThenPosSim);//a la filera del call es guarden els mateixos valors el segon parentesi sera el que es pasara  a la filera del return
                        thenBol[m][7]=true;//filera de subrutina//quan se executi el call la filera seguent al call o si hi goto incondicional s'ha de guardar en la filera del return  iretCall[m]=
                        iretCall[fil][0]=m;// en aquesta possicio es fara iThenGoSubRet[iretCall[fil]]= a la fila seguent al call o el goto
                        if(thenBol[fil][8])iretCall[fil][1]=iThenGoSubRetCopia;//si hi ha goto pasar la filera al return
                        else iretCall[fil][1]=fil+1;// sino es pasa la filera seguent del call 
                        return true;
                    }
                }
            }
        }
        errorThen(strue, fil);return false;
    }
    static void errorThen(String strue,int fil){
        Func.append(1,"error: ");
        Func.append(0,"a taula de funcions superior columna '"+matriu[0][idxThen]+"', cadena: "+strue+", filera: "+fil + splitPan.FIL);
        Func.append(0,"format de goto un "+Func.rB.getString("u_")+"nic sencer o un sencer al final de la cadena de una copia o un call; el sencer != filera actual; >=0 i <= nombre de fileres de taula"+ splitPan.FIL);
        Func.append(0,"format de copia: '(simbol1;simbol2;.....)(s1;s2;..)'  que fa s1=simbol1; s2=simbol2..." + splitPan.FIL);
        Func.append(0,"format de call tipus1 a subrutina: 'fila0(simbol1;simbol2;.....)(sim1;sim2;.....)fila1'" + splitPan.FIL);
        Func.append(0,"format de subrutina tipus1 al inici: (s1;s2;..);" + splitPan.FIL);
        Func.append(0,"format de call tipus2: 'fila0(simbol1;simbol2;.....)(s1 s2 s3)(sim1;sim2;.....)fila1'<br>en aquest cas la columna 'Then' de la filera de l'inici de subrutina, pot no tenir contingut " + splitPan.FIL);
        Func.append(0,"format del retorn: (si1;si2;..)" + splitPan.FIL);
    }
    static boolean errorNumaPar(String strue,int fil){//la comprobacio de que no pot esser un valor numeric o parametre nomes cal fer.lo pel desti iThenPosSim[m][1]
        for(int m=0;m<iThenPosSim.length;m++)if(simbolsReduitsNombreBol[iThenPosSim[m][1]]){
                Func.append(1,"error: ");
                Func.append(0,"a taula de funcions superior columna '"+matriu[0][idxThen]+"', cadena: "+strue+", fila: "+fil+ splitPan.FIL);
                Func.append(0,"no es pot traslladar un valor num"+Func.rB.getString("_e")+"ric a una constant s"+Func.rB.getString("i_")+"mbol de capçalera o valor num"+Func.rB.getString("_e")+"ric" + splitPan.FIL);
                return false;
        }
        return true;
    }
    public static boolean thenPosSim(String cad,boolean bol){//si es false situa en el index[filera][0] el index del simbol si true el situa a [filera][1]
        if(cad.equals(" ")||cad.equals("")){
           if(bol)if(iThenPosSim.length>0){
                Func.append(1,"error: ");
                Func.append(0,"a taula de funcions superior columna '"+matriu[0][idxThen]+ splitPan.FIL);
                Func.append(0,"el nombre de variables del call ha de coincidir amb els de la subrutina i del return" + splitPan.FIL);
            return false;
           }
           iThenPosSim=new int[0][0];return true;
        }
        if(!esSimsense_op_fun_par_menys(cad)){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior columna '"+matriu[0][idxThen]+"', cadena: "+cad+"; simbols amb caracters incompatibles (signe menys, operadors io funcions)" + splitPan.FIL);return false;}
        String cad1=cad;
        int i=cad.indexOf(" ");
        int cont=1;
        while(i>-1){
            cad=cad.substring(i+1);
            cont++;
            i=cad.indexOf(" ");
        }
        if(bol)if(cont!=iThenPosSim.length){
            Func.append(1,"error: ");
            Func.append(0,"a taula de funcions superior columna '"+matriu[0][idxThen]+"', cadena: "+cad1+ splitPan.FIL);
            Func.append(0,"el nombre de variables del call ha de coincidir amb els de la subrutina i del return" + splitPan.FIL);
            return false;
        }
        cad=cad1;
        String[] s=new String[cont];
        if(!bol){iThenPosSim=new int[cont][4];}//s'executa primer
        cont=0;
        i=cad.indexOf(" ");
        while(i>-1){
            s[cont]=cad.substring(0,i);
            cad=cad.substring(i+1);
            cont++;
            i=cad.indexOf(" ");
        }
        if(cad.equals(" ")||cad.equals(""))return false;
        s[cont]=cad;//comprobar que tots els simbols son simbols definits en la taula
        for(i=0;i<s.length;i++){
            boolean b=false;
            try{
                Double.parseDouble(splitPan.comApun(s[i]));
                if(s[i].startsWith("-")){
                    String s0=s[i].substring(1);
                    if(s0.contains("-")){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior columna '"+matriu[0][idxThen]+"', cadena: "+cad+"; simbols amb caracters incompatibles (operadors io funcions io signe menys (nomes v"+Func.rB.getString("a")+"lid al inici de valors num"+Func.rB.getString("_e")+"rics,)" + splitPan.FIL);return false;}
                }
            }
            catch(Exception e){
                if(s[i].contains("-")){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior columna '"+matriu[0][idxThen]+"', cadena: "+cad+"; simbols amb caracters incompatibles (operadors io funcions io signe menys (nomes v"+Func.rB.getString("a")+"lid al inici de valors num"+Func.rB.getString("_e")+"rics,)" + splitPan.FIL);return false;}
            }
            for(int j=0;j<simbolsT.length;j++)if(s[i].equals(simbolsT[j])){
                    if(!bol){iThenPosSim[i][0]=idxSimbolFilera[j];iThenPosSim[i][2]=j;b=true;j=simbolsT.length;}//false s'executa primer i es posa a columnes 0 i 2 (0 direccio fila de simbolsT; 2: filera de la taula)
                    else{iThenPosSim[i][1]=idxSimbolFilera[j];iThenPosSim[i][3]=j;b=true;j=simbolsT.length;}// s'executa segon i es posa a columnes 1 i 3
            }
            if(!b)return false;
        }
        return true;
    }
    public static boolean esSimsense_op_fun_par_menys(String s){//els simbols de variables conve que no continguin cadenes de funcio ni operadors ni parentesi
        for (int i=0;i<Func.funcions.length;i++)if (s.equals(Func.funcions[i])) return  false;
        for(int i=0;i<Func.operador.length();i++)if(s.contains(Func.operador.substring(i,i+1)))return false;
        if (s.contains("(")||s.contains(")")) return false;
        return  true;
    }
    static boolean columnaIf_i_substitucioDeParametres(){//interve la taula parametres; utilitzat a splitPan, substitueix els parametres fixos de les funcions de la taulaF pels seus valors numerics
        //calcul de les matrius simbolsNombreBol isimbolsReduitsNombreBol si true indiquen que en la filera de simbols T o la filera de resultats es tracta de un valor numeric que no implica ni modificacions a la cadena dvo ni a la matriu de resultats
        int conta=0;
        for(int i=colors.length;i<simbolsT.length;i++){
            for(int j=0;j<taulaP.simParCapUnaFil.length;j++)if(simbolsT[i].equals(taulaP.simParCapUnaFil[j]))simbolsT[i]=""+taulaP.dsimParCapUnaFil[j];
            try{
                Double.parseDouble(splitPan.comApun(simbolsT[i]));
                simbolsTNombreBol[i]=true;
            }
            catch(Exception e){}
            if(simbolsTNombreBol[i]){simbolsReduitsNombreBol[idxSimbolFilera[i]]=true;conta++;}
        }
        simbolsReduitsNoNumerics=new String[simbolsReduitsNombreBol.length-conta];
        conta=0;
        for(int i=0;i<simbolsReduitsNombreBol.length;i++){
            if (!simbolsReduitsNombreBol[i]){simbolsReduitsNoNumerics[conta]=simbolsReduits[i];conta++;}
        }
        if(!columnaIf())return false;//analisi columna if
        return true;
    }
    private static boolean columnaIf(){
        avisBol=new boolean[colors.length][3];
        hihaCondicio=new boolean [colors.length];//si a la filera hi ha condicio vol dir que cal executar la comprobacio y guardar a la filera de la matriu TrueFalse la condicio true o false
        hihaCondicioIndex=new boolean [colors.length];//si es true vol dir que la comparacio es fa amb almenys algun simbol de la columna 1
        trueFalse=new boolean [colors.length];//despres del calcul de la funcio a la mateixa filera un cop feta la comprobacio es guarda true o false
        arr_indexsTF = new ArrayList<int[][]>();
        arr_indexsSimTF = new ArrayList<int[][]>(); 
        arr_bTF = new ArrayList<boolean[]>();
        arr_dTF = new ArrayList<double[]>();
        String[] f=new String[simbolsReduitsNoNumerics.length+taulaP.simParCapUnaFil.length];
        for(int i=0;i<simbolsReduitsNoNumerics.length;i++)f[i]=simbolsReduitsNoNumerics[i];
        for(int i=0;i<taulaP.simParCapUnaFil.length;i++)f[i+simbolsReduitsNoNumerics.length]=taulaP.simParCapUnaFil[i];
        for(fila=0;fila<colors.length;fila++){
            if(!matriu[fila+1][idxIf].equals("")){
                matriu[fila+1][idxIf]=splitPan.treuBlancs(matriu[fila+1][idxIf]);
                if(calcIndexTF(matriu[fila+1][idxIf],f)>-1){
                    thenBol[fila][0]=true;
                    arr_indexsTF.add(fila,indexsTF);
                    bTF=new boolean[bTF.length];
                    arr_bTF.add(fila,bTF);
                    arr_indexsSimTF.add(fila,indexsSimTF);
                    arr_dTF.add(fila,dTF);
                }
                else {
                    Func.append(1,"error: ");
                    Func.append(0,"a taula de funcions superior columna '"+matriu[0][idxIf]+"', "+"revisar cadena: "+matriu[fila+1][idxIf]+ splitPan.FIL);
                    return false;  
                }
            }
            else {
                arr_indexsTF.add(fila,new int[0][0]);
                arr_bTF.add(fila,new boolean[0]);
                arr_indexsSimTF.add(fila,new int[0][0]);
                arr_dTF.add(fila,new double[0]);
            }
        }
       return true;
    }
    public static int calcIndexTF(String s,String[] f){
        boolean b=false;
        int id=s.indexOf("&&");
        while (id>-1){s=s.substring(0,id)+s.substring(id+1);id=s.indexOf("&&");b=true;}
        id=s.indexOf("||");
        while (id>-1){s=s.substring(0,id)+s.substring(id+1);id=s.indexOf("||");b=true;}
        id=s.indexOf("!!");
        while (id>-1){s=s.substring(0,id)+s.substring(id+1);id=s.indexOf("!!");b=true;}
        id=s.indexOf("==");
        while (id>-1){s=s.substring(0,id)+s.substring(id+1);id=s.indexOf("==");b=true;}
        id=s.indexOf(">>");
        while (id>-1){s=s.substring(0,id)+s.substring(id+1);id=s.indexOf(">>");b=true;}
        id=s.indexOf("<<");
        while (id>-1){s=s.substring(0,id)+s.substring(id+1);id=s.indexOf("<<");b=true;}
        if(b){
            Func.append(1,"avis: ");
            Func.append(0,"a taula de funcions superior a columna '"+matriu[0][idxIf]+";  fila: "+fila+"', cadena corregida: "+s+", els s"+Func.rB.getString("i_")+"mbols: and, or, negacio, igual, menor, major, '&' '|'  '!' '=' <' '>' son id"+Func.rB.getString("_e")+"ntics a ex: '&&&'  '||'  '!!!!' '==' '<<' '>>'" + splitPan.FIL);
        }
        String t=s;
        for(int j=0;j<t.length();j++){ String s0=t.substring(j,j+1);if(!s0.equals("(")&&!s0.equals(")")){t=t.substring(0,j)+t.substring(j+1);j--;}}
        if(!splitPan.analitzaParentesi(t)){
            Func.append(1,"error: ");
            Func.append(0,"a taula de funcions superior a columna '"+matriu[0][idxIf]+";  fila: "+fila+"', cadena: "+s+", revisar par"+Func.rB.getString("_e")+"ntesi" + splitPan.FIL);
            return -1;
        }
        String s2=s;
        for(int i=0;i<s2.length()-1;i++){
            String s3=s2.substring(i,i+2);
            if(s3.equals(">="))s2=s2.substring(0,i)+" "+s2.substring(i+2);
            if(s3.equals("<="))s2=s2.substring(0,i)+" "+s2.substring(i+2);
         }
        for(int i=0;i<s2.length();i++){
            String s3=s2.substring(i,i+1);
            if(s3.equals(">"))s2=s2.substring(0,i)+" "+s2.substring(i+1);
            if(s3.equals("<"))s2=s2.substring(0,i)+" "+s2.substring(i+1);
            if(s3.equals("="))s2=s2.substring(0,i)+" "+s2.substring(i+1);
            if(s3.equals("&"))s2=s2.substring(0,i)+" "+s2.substring(i+1);
            if(s3.equals("|"))s2=s2.substring(0,i)+" "+s2.substring(i+1);
            if(s3.equals("!"))s2=s2.substring(0,i)+" "+s2.substring(i+1);
        }
        if(s2.contains("  ")){
            Func.append(1,"error: ");
            Func.append(0,"a taula de funcions superior a columna '"+matriu[0][idxIf]+"', cadena: "+s+", no hi pot haver s"+Func.rB.getString("i_")+"mbols de operaci"+Func.rB.getString("o_")+" seguits; ex erronis !& |> ><  <> o >=>" + splitPan.FIL);
            return -1;
        }
        s2=s;
        for(int i=0;i<s2.length()-1;i++){
            String s3=s2.substring(i,i+2);
            if(s3.equals(">="))s2=s2.substring(0,i)+"  "+s2.substring(i+2);
            if(s3.equals("<="))s2=s2.substring(0,i)+"  "+s2.substring(i+2);
        }
        for(int i=0;i<s2.length();i++){
            String s3=s2.substring(i,i+1);
            if(s3.equals(">"))s2=s2.substring(0,i)+" "+s2.substring(i+1);
            if(s3.equals("<"))s2=s2.substring(0,i)+" "+s2.substring(i+1);
            if(s3.equals("="))s2=s2.substring(0,i)+" "+s2.substring(i+1);
            if(s3.equals("&"))s2=s2.substring(0,i)+" "+s2.substring(i+1);
            if(s3.equals("|"))s2=s2.substring(0,i)+" "+s2.substring(i+1);
            if(s3.equals("!"))s2=s2.substring(0,i)+" "+s2.substring(i+1);
            if(s3.equals("("))s2=s2.substring(0,i)+" "+s2.substring(i+1);
            if(s3.equals(")"))s2=s2.substring(0,i)+" "+s2.substring(i+1);
        }
        int contador=0;
        String tab="";
        s2+=" ";
        while(s.length()>0){
            String s1=unitat(s,f,s2);
            if(s1.equals("")){
                Func.append(1,"error: ");
                Func.append(0,"a taula de funcions superior columna '"+matriu[0][idxIf]+"', "+" cadena no identificada: "+s+ splitPan.FIL);
                return -1;
            }
            int j=s1.length();
            if(j>0){
                contador+=1;
                tab+=s1+splitPan.TAB;
                s=s.substring(j);
                s2=s2.substring(j);
            }
        }
        if(contador<2)return -1;
        String[] sV=new String[contador];
        bTF=new boolean[contador];
        dTF=new double[contador];
        for(int i=0;i<dTF.length;i++)dTF[i]=NaN;
        for(int i=0;i<contador;i++){
            int j=tab.indexOf(splitPan.TAB);
            sV[i]=tab.substring(0,j);
            if(tab.length()>0)tab=tab.substring(j+1);
        }
        for(int i=0;i<sV.length;i++)for(int j=0;j<taulaP.simParCapUnaFil.length;j++){
            if(sV[i].equals("-"+taulaP.simParCapUnaFil[j])){dTF[i]=-taulaP.dsimParCapUnaFil[j];sV[i]="_";j=taulaP.simParCapUnaFil.length;}
            else if(sV[i].equals(taulaP.simParCapUnaFil[j])){dTF[i]=taulaP.dsimParCapUnaFil[j];sV[i]="_";j=taulaP.simParCapUnaFil.length;}
        }
        double[] d=new double[dTF.length];
        System.arraycopy(dTF, 0, d, 0, d.length);
        contador=0;
        for(int i=0;i<sV.length;i++)for(int j=0;j<simbolsReduits.length;j++)if(sV[i].equals(simbolsReduits[j])){
            contador++;j=simbolsReduits.length;
        }
        indexsSimTF=new int[contador][2];
        contador=0;
        for(int i=0;i<sV.length;i++)for(int j=0;j<simbolsReduits.length;j++)if(sV[i].equals(simbolsReduits[j])){
                indexsSimTF[contador][0]=i;
                indexsSimTF[contador][1]=j;
                sV[i]="_";
                dTF[i]=1;
                contador++;
                j=simbolsReduits.length;
        }
        contador=0;
        for(int i=0;i<sV.length;i++){
            if(sV[i].startsWith(">")||sV[i].startsWith("<")||sV[i].equals("=")||sV[i].equals("&")||sV[i].equals("|")||sV[i].equals("!"))contador++;
        }
        indexsTF=new int[contador][3];
        contador=0;
        b=false;
        for(int i=0;i<sV.length;i++){
            if(!sV[i].equals("_")&&!sV[i].startsWith("<")&&!sV[i].startsWith(">")&&!sV[i].equals("=")&&!sV[i].equals("&")&&!sV[i].equals("|")&&!sV[i].equals("(")&&!sV[i].equals(")")&&!sV[i].equals("!")){
                try{d[i]=Double.parseDouble(splitPan.comApun(sV[i]));dTF[i]=d[i];}
                catch(Exception e){b=true;}
            }
        }
        if(b){
            Func.append(1,"error: ");
            Func.append(0,"a taula de funcions superior a columna '"+matriu[0][idxThen]+"', cadena: "+s2+", es incorrecta" + splitPan.FIL);
            Func.append(0,"la seq"+Func.rB.getString("u")+"encia correcta es  parella valor num"+Func.rB.getString("_e")+"rics (valors s"+Func.rB.getString("i_")+"mbols o par"+Func.rB.getString("a")+"metres fixos) separat per un operador (< > >= <= o =) " + splitPan.FIL);
            Func.append(0,"es poden incloure negacions '!'  davant de un s"+Func.rB.getString("i_")+"mbol num"+Func.rB.getString("e_")+"ric o par"+Func.rB.getString("_e")+"ntesi '(' davant un s"+Func.rB.getString("i_")+"mbol num"+Func.rB.getString("e_")+"ric , o ')' darrera un s"+Func.rB.getString("i_")+"mbol num"+Func.rB.getString("e_")+"ric , ex: simbol1>simb2&!(simbol3<=simb2|simbol4<=simb5);" + splitPan.FIL);
            return -1;
        }
        for (int i =0; i<sV.length; i++) {
            if (sV[i].equals(")")) {
                sV[i]="_";
                for (int j = i-1; j>=0; j--) {
                    if (sV[j].equals("(")) {
                        sV[j]="_";
                        contador=calculIndexFunc1(j+1,i,contador,sV);
                        //davant de parentesi '!' pasa a tenir maxima prioritat
                        if(j>0&&sV[j-1].equals("!")){
                            for(int k=j;k<i;k++){if(isFinite(dTF[k])){indexsTF[contador][1]=k;k=i;}}
                            indexsTF[contador][0]=5;
                            sV[j-1]="_";
                            contador++;
                         }
                        j=0;
                    }
                }
            }  
        }
        int j=calculIndexFunc1(0,sV.length,contador,sV); 
        System.arraycopy(d, 0, dTF, 0, d.length);
        return j;
    }
    private static int calculIndexFunc1(int j,int c,int contador,String[] sV){
        for(int i=j+1;i<c;i++){
            if(sV[i].equals(">=")){
                for(int k=i+1;k<c;k++){if(isFinite(dTF[k])){indexsTF[contador][2]=k;sV[k]="_";dTF[k]=NaN;k=c;}}
                for(int k=i-1;k>=j;k--){if(isFinite(dTF[k])){indexsTF[contador][1]=k;k=-1; }}
                indexsTF[contador][0]=1;
                sV[i]="_";
                contador++;
                }
        }
        for(int i=j+1;i<c;i++){
            if(sV[i].equals(">")){
                for(int k=i+1;k<c;k++){if(isFinite(dTF[k])){indexsTF[contador][2]=k;sV[k]="_";dTF[k]=NaN;k=c;}}
                for(int k=i-1;k>=j;k--){if(isFinite(dTF[k])){indexsTF[contador][1]=k;k=-1; }}
                indexsTF[contador][0]=0;
                sV[i]="_";
                contador++;
                }
        }
        for(int i=j+1;i<c;i++){
            if(sV[i].equals("<=")){
                for(int k=i+1;k<c;k++){if(isFinite(dTF[k])){indexsTF[contador][2]=k;sV[k]="_";dTF[k]=NaN;k=c;}}
                for(int k=i-1;k>=j;k--){if(isFinite(dTF[k])){indexsTF[contador][1]=k;k=-1; }}
                indexsTF[contador][0]=4;
                sV[i]="_";
                contador++;
                }
        }        
        for(int i=j+1;i<c;i++){
            if(sV[i].equals("<")){
                for(int k=i+1;k<c;k++){if(isFinite(dTF[k])){indexsTF[contador][2]=k;sV[k]="_";dTF[k]=NaN;k=c;}}
                for(int k=i-1;k>=j;k--){if(isFinite(dTF[k])){indexsTF[contador][1]=k;k=-1; }}
                indexsTF[contador][0]=3;
                sV[i]="_";
                contador++;
                }
        }
        for(int i=j+1;i<c;i++){
            if(sV[i].equals("=")){
                for(int k=i+1;k<c;k++){if(isFinite(dTF[k])){indexsTF[contador][2]=k;sV[k]="_";dTF[k]=NaN;k=c;}}
                for(int k=i-1;k>=j;k--){if(isFinite(dTF[k])){indexsTF[contador][1]=k;k=-1; }}
                indexsTF[contador][0]=2;
                sV[i]="_";
                contador++;
                }
        }
        for(int i=j+1;i<c;i++){
            if(sV[i].equals("&")){
                for(int k=i+1;k<c;k++){if(isFinite(dTF[k])){indexsTF[contador][2]=k;sV[k]="_";dTF[k]=NaN;k=c;}}
                for(int k=i-1;k>=j;k--){if(isFinite(dTF[k])){indexsTF[contador][1]=k;k=-1; }}
                indexsTF[contador][0]=6;
                sV[i]="_";
                contador++;
                }
        }
        for(int i=j+1;i<c;i++){
            if(sV[i].equals("|")){
                for(int k=i+1;k<c;k++){if(isFinite(dTF[k])){indexsTF[contador][2]=k;sV[k]="_";dTF[k]=NaN;k=c;}}
                for(int k=i-1;k>=j;k--){if(isFinite(dTF[k])){indexsTF[contador][1]=k;k=-1; }}
                indexsTF[contador][0]=7;
                sV[i]="_";
                contador++;
                }
        }
        for(int i=j;i<c;i++){
            if(sV[i].equals("!")){
                for(int k=i+1;k<c;k++){if(isFinite(dTF[k])){indexsTF[contador][1]=k;k=c;}}
                indexsTF[contador][0]=5;
                sV[i]="_";
                contador++;
                }
        }
        return contador;
    }
    private static String unitat(String s,String[] f,String s2){///s2 es igual a s on s'han substituit els simbols de comparacio per blancs
       String s1="";
         if(s.length()>2){
            s1=s.substring(0,2);
            if (s1.equals(">=") ||s1.equals("<=")) return s1;
        }
        s1=s.substring(0,1);
        if (s1.equals(">") ||s1.equals("<") ||s1.equals("=") ||s1.equals("(") || s1.equals(")")||s1.equals("&")||s1.equals("|")||s1.equals("!")) return s1;

        for(int i=0;i<f.length;i++){//per cada columna
            if (s2.startsWith("-"+f[i]+" "))return "-"+f[i];
            if (s2.startsWith(f[i]+" "))return f[i];
        }
        if(splitPan.esNuOmenys(s1)){//retorna el signe menys si no hi ha un numero a continuacio o  un numero positiu o negatiu, amb exponent o signe menys
            for(int j=0;j<s.length();j++){
                s1=s.substring(j,j+1);
                if(j==s.length()-1&&splitPan.esNu(s1)) return s.substring(0,s.length());
                if(!splitPan.esNuOeOmenys(s1)) return s.substring(0,j);
            }
        }
    return "";
    }
    static boolean matriuTaulaFi(){//les variables de taulaF no estan s'inclouen a varDerBol i varGenDerBol la primera columna indica si algun simbol de sumatori o funcio parcial(directa o indirectament a traves de sumatori) es troba en la funcio de la filera de la taulaF(amb sense variables generals); la resta indica si es troba el (sumatori(columna), funcio parcial) de la columna o filera -1
        varNgenDerBol=new boolean[colors.length];
        if(taulaD.hihaDades){
            varGenDerBol=new boolean[colors.length][1+taulaD.simbol.length];
        }
        else {varGenDerBol=new boolean[colors.length][1];}//funcParpresentaFilFuncBol=new boolean[colors.length][1];}
        int cont=0;
        if(taulaP.parametres.length>0){//paCapBol si true vol dir que h ha parametres de capçalera de mes d0na filera a la funcio
            parCapBol=new boolean[colors.length][1+taulaP.simParCap.length];
            parCapVarBol=new boolean[colors.length][1+taulaP.simParCap.length];
            parCapNoVarBol=new boolean[colors.length][1+taulaP.simParCap.length];
            for(int i=0;i<funcioT.length;i++){
                String[] pv=splitPan.arr_simbolsFuncioTaulaF.get(i);
                for(int l=0;l<pv.length;l++)for(int j=0;j<taulaP.simParCap.length;j++)if(pv[l].equals(taulaP.simParCap[j])){
                    boolean b=false;
                    for(int k=0;k<taulaP.simParCapUnaFil.length;k++)if(pv[l].equals(taulaP.simParCapUnaFil[k]))b=true;
                    if(!b){
                        parCapBol[i][0]=true;parCapBol[i][1+j]=true;
                        for(int k=0;k<taulaP.simParCapVar.length;k++)if(pv[l].equals(taulaP.simParCapVar[k])){parCapVarBol[i][0]=true;parCapVarBol[i][1+j]=true;k=taulaP.simParCapVar.length;}
                        for(int k=0;k<taulaP.simParCapNoVar.length;k++) if(pv[l].equals(taulaP.simParCapNoVar[k])){parCapNoVarBol[i][0]=true;parCapNoVarBol[i][1+j]=true;k=taulaP.simParCapNoVar.length;}
                    }
                    j=taulaP.simParCap.length;
                }
            }
        }
        else {parCapBol=new boolean[colors.length][1];parCapVarBol=new boolean[colors.length][1];parCapNoVarBol=new boolean[colors.length][1];}
        if(taulaV.varstaulaV.length>0){
            varGenBol=new boolean[colors.length][1+taulaV.varstaulaV.length];
            varBol=new boolean[colors.length][1+taulaV.varstaulaV.length];
            res_varstaulaVFiCerca=new double[taulaV.varstaulaV.length];
            res_cursortaulaVFiCerca=new long[taulaV.varstaulaV.length];
            res_varstaulaVBol=new boolean[taulaV.varstaulaV.length];
        }
        else {varGenBol=new boolean[colors.length][1];varBol=new boolean[colors.length][1];}
        //if(taulaV.varsGenOrdenats.length>0){System.out.println("simbols de la taula variables taulaV.varstaulaV");for(int i=0;i<taulaV.varstaulaV.length;i++)System.out.print(taulaV.varstaulaV[i]+" ");System.out.println();}
        //System.out.println("per cada filera tots els simbols presents a la funcio parVars: ");for(int i=0;i<parVars.length;i++)System.out.print(parVars[i]+" _ ");System.out.println();
        //System.out.println("Simbols de capçalera taulaP simParCap: ");for(int i=0;i<taulaP.simParCap.length;i++)System.out.print(taulaP.simParCap[i]+" _ ");System.out.println();
        //System.out.println("parametres de capçalera de mes duna fila  presents a la funcio parCapBol: ");for(int i=0;i<parCapBol.length;i++){for(int j=0;j<parCapBol[0].length;j++){System.out.print(parCapBol[i][j]+" _ ");}System.out.println();}System.out.println();
        //System.out.println("parametres variables de la taulaP_taulaV simPar: ");for(int i=0;i<taulaP.simPar.length;i++)System.out.print(taulaP.simPar[i]+" _ ");System.out.println();
        //if(taulaI.hihaDades){System.out.println("variables generals presents a la sumatoris (fila: sumatori; columna: variable general) taulaI.varGenBol: ");for(int k=0;k<taulaI.varGenBol.length;k++){for(int j=0;j<taulaI.varGenBol[0].length;j++){System.out.print(taulaI.varGenBol[k][j]+" _ ");}System.out.println();}System.out.println();}
        varNgenSumBol=new boolean[colors.length];
        if(taulaI.hihaDades){
            varGenSumBol=new boolean[colors.length][1+taulaI.sumat.length];
            sumafilataulaF=new boolean[colors.length][1+taulaI.sumat.length];
            sumambFPfilataulaF=new boolean[colors.length][1+taulaI.sumat.length];
            sumambFPfilataulaFunaFila=new boolean[1+taulaI.sumat.length];
        }
        else{
            varGenSumBol=new boolean[colors.length][1];
        }
        for(int i=0;i<funcioT.length;i++){
            String[] pv=splitPan.arr_simbolsFuncioTaulaF.get(i);
            if(taulaI.hihaDades)for(int l=0;l<pv.length;l++)for(int j=0;j<taulaI.sumat.length;j++)if(pv[l].equals(taulaI.sumat[j])){
                sumafilataulaF[i][0]=true;sumafilataulaF[i][1+j]=true;
                if(suportID.hihaFPambVGaI[j]){sumambFPfilataulaF[i][0]=true;sumambFPfilataulaF[i][1+j]=true;}
                if(suportID.hihaVGioFPaI[j]){//if(suportID.hihaVG_aI[j]||suportID.hihaFPaI[j]){
                    varGenSumBol[i][0]=true;varGenSumBol[i][1+j]=true;varGenBol[i][0]=true;
                    for(int k=0;k<Cal.indexsInteg_VG.length;k++)if(Cal.indexsInteg_VG[k][j])varGenBol[i][1+k]=true;
                    if(taulaD.hihaDades){//Cal.hihaFPIntegrBol boolean de [sumat.length+1][simbols.length+1] que relaciona sumatoris qur contenent funcionsParcials no constants
                        for(int m=1;m<Cal.hihaFPIntegrBol[0].length;m++)if(Cal.hihaFPIntegrBol[j+1][m]&&taulaD.hihaVGioFP[m-1]){varGenDerBol[i][0]=true;varGenDerBol[i][m]=true;}
                    }
                }
                else varNgenSumBol[i]=true;
            }
            if(taulaD.hihaDades)for(int l=0;l<pv.length;l++)for(int j=0;j<taulaD.simbol.length;j++)if(pv[l].equals(taulaD.simbol[j])){
                if(taulaD.hihaVGioFP[j]){ 
                    varGenDerBol[i][0]=true;varGenDerBol[i][1+j]=true;varGenBol[i][0]=true;
                    for(int k=0;k<taulaD.VGaFuncBol[0].length;k++)if(taulaD.VGaFuncBol[j][k])varGenBol[i][1+Cal.idxVarGen[k]]=true;
                }
                else varNgenDerBol[i]=true;
            }
            if(taulaV.idxVarGen.length>0)for(int l=0;l<pv.length;l++)for(int j=0;j<taulaV.idxVarGen.length;j++)if(pv[l].equals(taulaV.varsGenerals[j])){
                    varGenBol[i][0]=true;varGenBol[i][1+taulaV.idxVarGen[j]]=true;
                    varBol[i][0]=true;varBol[i][1+taulaV.idxVarGen[j]]=true;
            }
        }
        //if(taulaI.hihaDades){System.out.println("calculat a taulaI boolean[] de fileres de simbols reduits la filera en que el simbol resultat de la filera (simbols reduits) es troba present a sumatoris taulaF.varSumatoriBol:"); for(int i=0;i<taulaF.varSumatoriBol.length;i++){System.out.print(taulaF.varSumatoriBol[i]+" ");}System.out.println();System.out.println();}
        //if(taulaI.hihaDades){System.out.println("boolean[][] (el [][0] es el global) de simbols de sumatoris presents a les fileres de taulaF taulaF.varSumBol:"); for(int i=0;i<taulaF.varNgenSumBol.length;i++){for(int j=0;j<taulaF.varNgenSumBol[0].length;j++)System.out.print(taulaF.varNgenSumBol[i][j]+" ");System.out.print ("_ ");}System.out.println();System.out.println();}
        //if(taulaI.hihaDades){System.out.println("boolean[][] (el [][0] es el global) de simbols de sumatoris amb variables generals presents a les fileres de taulaF taulaF.varGenSumBol:"); for(int i=0;i<taulaF.varGenSumBol.length;i++){for(int j=0;j<taulaF.varGenSumBol[0].length;j++)System.out.print(taulaF.varGenSumBol[i][j]+" ");System.out.print ("_ ");}System.out.println();System.out.println();}
        //System.out.println("Cal.idxVarGen: ");for(int i=0;i<Cal.idxVarGen.length;i++){System.out.print(Cal.idxVarGen[i]+" _ ");}System.out.println();
        //System.out.println("true si la funcio conte variables generals directa o indirectament varGenBol: ");for(int i=0;i<varGenBol.length;i++){for(int j=0;j<varGenBol[0].length;j++){System.out.print(varGenBol[i][j]+" _ ");}System.out.println();}System.out.println();
        //if(taulaI.hihaDades){System.out.println("sumatoris de la taulaI sumat:");for(int i=0;i<taulaI.sumat.length;i++)System.out.print(taulaI.sumat[i]+" ");System.out.println();}
        //if(taulaI.hihaDades){System.out.println("sumatoris presents a la funcio varSumBol");for(int i=0;i<varNgenSumBol.length;i++){System.out.print(i+"  ");for(int j=0;j<varNgenSumBol[0].length;j++){System.out.print(varNgenSumBol[i][j]+" ");}System.out.println();}System.out.println();System.out.println();}
        //if(taulaI.hihaDades&&suportID.hihaVG_aTOTI){System.out.println("sumatoris amb variables generals directes o indirectes presents a la funcio varGenSumBol");for(int i=0;i<varGenSumBol.length;i++){System.out.print(i+"  ");for(int j=0;j<varGenSumBol[0].length;j++){System.out.print(varGenSumBol[i][j]+" ");}System.out.println();}System.out.println();System.out.println();}
        //if(taulaD.hihaDades){System.out.println("simbols de funcio parcial a la funcio de la filera i de taulaF columna j taulaFP  varDerBol[i][j]: ");for(int i=0;i<varNgenDerBol.length;i++){System.out.print(i+"  ");{System.out.print(varNgenDerBol[i]+" ");}System.out.println();}System.out.println();System.out.println();}
        //if(taulaD.hihaDades){System.out.println("hi ha algun simbols de funcio parcial (que directa o indirectament contenent variables generals) present a la funcio de la filera i de taulaF; la columna j>0 indica la filera-1 de la funcio parcial taulaFP varGenDerBol[i][j]: ");for(int i=0;i<varGenDerBol.length;i++){System.out.print(i+"  ");for(int j=0;j<varGenDerBol[0].length;j++){System.out.print(varGenDerBol[i][j]+" ");}System.out.println();}System.out.println();System.out.println();}
        //if(taulaD.hihaDades){System.out.println("varGenDerBol.length: "+varGenDerBol.length+" "+varGenDerBol[0].length);}
        //if(taulaD.hihaDades){System.out.println("simbols reduits presents en les funcions de la taula derivades es calcula a taulaD taulaF.varDerivadaBol: ");for(int m=0;m<varDerivadaBol.length;m++){System.out.print(varDerivadaBol[m]+" ");}System.out.println();}System.out.println();
        //if(taulaI.hihaDades){System.out.println("simbols columna 1 presenta a sumatoris es calcula a taulaI varSumatoriBol");for(int i=0;i<varSumatoriBol.length;i++){System.out.print(i+"  ");System.out.print(varSumatoriBol[i]+" ");}System.out.println();System.out.println();}
        //System.out.println(taulaP.simPar.length+" "+parCapBol.length+" "+parCapBol[0].length+" "+taulaP.varPTV_varPTP.length);
        //if(taulaP.simPar.length>0){System.out.println("taulaP.varPTV_varPTP");for(int i=0;i<taulaP.varPTV_varPTP.length;i++){System.out.print(i+"  ");for(int j=0;j<taulaP.varPTV_varPTP[0].length;j++){System.out.print(taulaP.varPTV_varPTP[i][j]+" ");}System.out.println();}System.out.println();System.out.println();}
        //System.out.println("taulaP.hihaParVar: "+taulaP.hihaParVar);System.out.println();
        if(taulaP.hihaParVar){
            varParFuncOrigBol=new boolean[colors.length][1+taulaV.varstaulaV.length];
            varParFuncExtesaBol=new boolean[colors.length][1+taulaV.varstaulaV.length];
            for(int fil=0;fil<colors.length;fil++){
                for(int k=1;k<parCapBol[0].length;k++)if(parCapBol[fil][k]){//primer selecciona les columnes en que el simbol de capçalera de la taula parametres es troba en la funcio
                    for(int l=0;l<taulaP.varPTV_varPTP.length;l++){//el segon subindex 0 es la filera de la taula variables 2 es la columna tula parametres
                        if(taulaP.varPTV_varPTP[l][2]==k-1){//si la columna coincideix
                            varBol[fil][0]=true;varBol[fil][taulaP.varPTV_varPTP[l][0]+1]=true;
                            varParFuncExtesaBol[fil][0]=true;varParFuncExtesaBol[fil][taulaP.varPTV_varPTP[l][0]+1]=true;
                        }
                    }
                }
            }
            for(int i=0;i<funcioT.length;i++){
                String[] pv=splitPan.arr_simbolsFuncioTaulaF.get(i);
                for(int l=0;l<pv.length;l++)for(int j=0;j<taulaV.varstaulaV.length;j++)if(pv[l].equals(taulaV.varstaulaV[j])){
                    if(!varGenBol[i][1+j]){varParFuncOrigBol[i][0]=true;varParFuncOrigBol[i][1+j]=true;}
                    varBol[i][0]=true;varBol[i][1+j]=true;
                }
            }
        }
        else {varParFuncOrigBol=new boolean[colors.length][1];varParFuncExtesaBol=new boolean[colors.length][1];}
        //System.out.println("parametres variables de la taulaV a les funcions (reduides columna0) taulaPvarBol: "+taulaP.hihaParVar+" "+varParBol.length+" "+varParBol[0].length); for(int i=0;i<varParBol.length;i++){for(int j=0;j<varParBol[0].length;j++){System.out.print(i+" "+j+" "+varParBol[i][j]+" _ ");}System.out.println();}
        //if(varGenBol.length>0){System.out.println("varGenBol");for(int i=0;i<varGenBol.length;i++){System.out.print(i+"  ");for(int j=0;j<varGenBol[0].length;j++){System.out.print(varGenBol[i][j]+" ");}System.out.println();}System.out.println();System.out.println();}
        //if(parCapBol.length>1){System.out.println("parCapBol");for(int i=0;i<parCapBol.length;i++){System.out.print(i+"  ");for(int j=0;j<parCapBol[0].length;j++){System.out.print(parCapBol[i][j]+" ");}System.out.println();}System.out.println();System.out.println();}
        //if(parCapVarBol.length>1){System.out.println("parCapVarBol");for(int i=0;i<parCapVarBol.length;i++){System.out.print(i+"  ");for(int j=0;j<parCapVarBol[0].length;j++){System.out.print(parCapVarBol[i][j]+" ");}System.out.println();}System.out.println();System.out.println();}
        //if(parCapNoVarBol.length>1){System.out.println("parCapNoVarBol");for(int i=0;i<parCapNoVarBol.length;i++){System.out.print(i+"  ");for(int j=0;j<parCapNoVarBol[0].length;j++){System.out.print(parCapNoVarBol[i][j]+" ");}System.out.println();}System.out.println();System.out.println();}
        //if(varParFuncOrigBol.length>0){System.out.println("varParFuncOrigenBol");for(int i=0;i<varParFuncOrigBol.length;i++){System.out.print(i+"  ");for(int j=0;j<varParFuncOrigBol[0].length;j++){System.out.print(varParFuncOrigBol[i][j]+" ");}System.out.println();}System.out.println();System.out.println();}
        //if(varParFuncExtesaBol.length>0){System.out.println("varParFuncExtesaBol");for(int i=0;i<varParFuncExtesaBol.length;i++){System.out.print(i+"  ");for(int j=0;j<varParFuncExtesaBol[0].length;j++){System.out.print(varParFuncExtesaBol[i][j]+" ");}System.out.println();}System.out.println();System.out.println();}
        if(!columnaDreta())return false;
        return true;
    }
    private static boolean columnaDreta(){
        funcioSenseVariables=new boolean[colors.length];
        simbolsDefinits=new boolean[colors.length];
        int l=0;
        if(taulaP.simParCapNoVar.length>0)l=taulaP.simParCapNoVar.length;
        if(taulaP.simParCapVar.length>0)l+=taulaP.simParCapVar.length;
        indexsderesultatsaMatriu=new int[colors.length+l][3];for(int i=0;i<colors.length;i++){indexsderesultatsaMatriu[i][0]=-1;indexsderesultatsaMatriu[i][2]=-2;}
        resultatUnic=new boolean[colors.length+l];
        simbolsArr=new String[colors.length+l];System.arraycopy(simbolsT, 0, simbolsArr, 0, colors.length);
        escalaLinial=new boolean[colors.length];
        ceroLog=new double[colors.length];
        digitsValids=new int[colors.length];
        noArrodonir=new boolean[colors.length];
        escala=new String[colors.length];
        longiP=new int[colors.length];
        funcArrCapBol=new boolean[colors.length];
        funcArrBol=new boolean[colors.length];//si true vol dir que la funcio de la filera sera extessa
        int[] o=new int[taulaP.parametres.length];
        for(int i=0;i<taulaP.parametres.length;i++){
            o[i]=taulaP.parametres[0].length-1;
            for(int j=1;j<taulaP.parametres[0].length;j++)if(taulaP.parametres[i][j].equals("")){o[i]=j-1;j=taulaP.parametres[0].length;}
        }
        int con=0;
        for(int i=0;i<o.length;i++){
            if(o[i]>1){
                for(int j=0;j<taulaP.simParCapNoVar.length;j++)if(taulaP.parametres[i][0].equals(taulaP.simParCapNoVar[j])){
                    indexsderesultatsaMatriu[con+colors.length][0]=con+colors.length;indexsderesultatsaMatriu[con+colors.length][2]=o[i];
                    simbolsArr[con+colors.length]=taulaP.simParCapNoVar[j];
                    con++;
                }
            }
        }
        for(int i=0;i<o.length;i++){
            if(o[i]>1){
                for(int j=0;j<taulaP.simParCapVar.length;j++)if(taulaP.parametres[i][0].equals(taulaP.simParCapVar[j])){
                    indexsderesultatsaMatriu[con+colors.length][0]=con+colors.length;indexsderesultatsaMatriu[con+colors.length][2]=o[i];
                    simbolsArr[con+colors.length]=taulaP.simParCapVar[j];
                    con++;
                }
            }
        }
        for(int fil=0;fil<colors.length;fil++){
            longiP[fil]=0;
            for (int j=1;j<parCapBol[0].length;j++)if(parCapBol[fil][j])longiP[fil]=o[j-1];
            for (int j=1;j<parCapBol[0].length;j++)for (int k=j+1;k<parCapBol[0].length;k++)if(parCapBol[fil][j]&&parCapBol[fil][k]){//comproba que totes les columnes de parametres presents en la funcio de la filera fil tenent el mateix nombre de fileres amb dades
                if(o[j-1]!=o[k-1]){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior (taulaF) fila: '"+fil+"', funci"+Func.rB.getString("o_")+": "+matriu[fil+1][0]+ splitPan.FIL);Func.append(0,"el nombre de par"+Func.rB.getString("a")+"metres associats als de capçalera a d'esser el mateix a totes les columnes de la taula parametres afectades" + splitPan.FIL);return false;}
            } 
        } 
        String[] mi=new String[colors.length];for(int i=0;i<colors.length;i++)mi[i]=matriu[i+1][idxIni];
        for(int i=0;i<colors.length;i++){//les condicions predeterminades son funcio simple i calcul de minim si hi hi variables generals io parametres variables
            funcioSenseVariables[i]=true;simbolsDefinits[i]=false;resultatUnic[i]=true;
            if(varGenBol[i][0]||varParFuncOrigBol[i][0]||parCapVarBol[i][0]||varParFuncExtesaBol[i][0]){funcioSenseVariables[i]=false;}
        }
        con=0;
        for(int i=0;i<mi.length;i++){
            if(mi[i].contains("def")){
                mi[i]=mi[i].replace("def", "");
                simbolsDefinits[i]=true;
                if(!varGenBol[i][0]&&varParFuncOrigBol[i][0]&&varParFuncExtesaBol[i][0]){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior columna '"+matriu[0][idxIni]+"', fila: "+i+" la cadena 'def' no "+Func.rB.getString("e_")+"s compatible amb la funci"+Func.rB.getString("o_")+" (no hi ha ni variables generals ni par"+Func.rB.getString("a")+"metres variables) " + splitPan.FIL);return false;}
                funcioSenseVariables[i]=false;
            }
        }
        for(int i=0;i<mi.length;i++){
            if(dimensioArr[i]>0){
                mi[i]=mi[i].replaceAll("mat", "arr");
                int j=mi[i].indexOf("arr[");
                int k=0;
                if(j>-1)k=mi[i].indexOf("]",j);
                mi[i]=mi[i].substring(0,j)+mi[i].substring(k+1);
                if(dimensioArr[i]>1)indexsderesultatsaMatriu[i][2]=dimensioArr[i];
                if(indexsderesultatsaMatriu[i][2]<longiP[i]||dimensioArr[i]<2){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior columna '"+matriu[0][idxIni]+"', fila: "+i+"; cadena de matriu v"+Func.rB.getString("a")+"lida: arr o arr[sencer] o mat o mat[sencer] on sencer>=longitud de matrius presents a la funcio)" + splitPan.FIL);return false;}
                resultatUnic[i]=false;
                indexsderesultatsaMatriu[i][0]=i;
            }
            else if(mi[i].contains("arr")){
                mi[i]=mi[i].replace("arr", "");
                resultatUnic[i]=false;
                String[] t=splitPan.arr_simbolsFuncioTaulaF.get(i);
                for(int j=0;j<t.length;j++)for(int k=1;k<parCapBol[0].length;k++)if(parCapBol[i][k])indexsderesultatsaMatriu[i][2]=longiP[i];
                indexsderesultatsaMatriu[i][0]=i;
            }//en aquest punt totes les fileres que contenent arr a columna9 han definit indexsderesultatsaMatriu[i][0]>-1 pero pot esser modificat per una nova fila origen de la matriu de referencia
            //if((parCapVarBol[i][0]||varGenBol[i][0])&&indexsderesultatsaMatriu[i][0]>-1){ Func.append(1,"avis: ");Func.append(0,"a taula de funcions superior columna '"+matriu[0][idxIni]+"', fila: "+i+"; la cadena de matriu 'arr o mat' es irrelevant en la cerca de minim" + splitPan.FIL);}
        
        }
        for(int i=0;i<colors.length;i++)if(parCapBol[i][0]){funcArrBol[i]=true;funcArrCapBol[i]=true;}
        for(int i=0;i<resultatUnic.length;i++)for(int j=i+1;j<resultatUnic.length;j++)if(simbolsArr[j].equals(simbolsArr[i])){
            if((!resultatUnic[i]||!resultatUnic[j])){resultatUnic[i]=false;resultatUnic[j]=false;}//tots els simbols de columna 1 que coincideixen amb un simbol que s'ha definit com array es defineixen com arrays
        }
        for(int i=0;i<simbolsArr.length;i++)for(int j=i+1;j<simbolsArr.length;j++)if(simbolsArr[j].equals(simbolsArr[i])){
            if(indexsderesultatsaMatriu[i][2]>1&&indexsderesultatsaMatriu[j][2]>1&&indexsderesultatsaMatriu[i][2]!=indexsderesultatsaMatriu[j][2]){
                Func.append(1,"avis: ");Func.append(0,"a taula de funcions superior files: '"+i+" i "+j+"';  hi ha matrius de diferent longitud, es pren la de la fila superior."+splitPan.FIL);
            }
        }
        boolean[] bb=new boolean[simbolsArr.length];
        for(int i=0;i<simbolsArr.length;i++)for(int j=i+1;j<simbolsArr.length;j++)if(simbolsArr[j].equals(simbolsArr[i])){
            if(!resultatUnic[i]&&(!bb[i]||!bb[j])){ //tots els simbols identics que son arrays comparteixen la mateixa matriu de resultats cal redirigir alguns valors de indexsderesultatsaMatriu[][0]
                    if(indexsderesultatsaMatriu[i][0]>-1){indexsderesultatsaMatriu[j][0]=i;indexsderesultatsaMatriu[j][2]=indexsderesultatsaMatriu[i][2];bb[i]=true;bb[j]=true;}
                    else if(indexsderesultatsaMatriu[j][0]>-1){indexsderesultatsaMatriu[i][0]=j;indexsderesultatsaMatriu[i][2]=indexsderesultatsaMatriu[j][2];bb[i]=true;bb[j]=true;}
            }
        }
        for(int i=0;i<colors.length;i++){
            String[] t=splitPan.arr_simbolsFuncioTaulaF.get(i);//simbols presents a la funcio de la fila i
            for(int j=0;j<t.length;j++)for(int k=0;k<colors.length;k++)if(t[j].equals(simbolsT[k])&&!resultatUnic[k]){
                funcArrBol[i]=true;//true indica que la funcio columna0 conte un array definit directa o indirectament a columna 9 o be un parametre de capçalera amb diferents parametres
            }
        }
        for(int i=0;i<resultatUnic.length;i++){
            if (indexsderesultatsaMatriu[i][0]>-1)indexsderesultatsaMatriu[i][2]=indexsderesultatsaMatriu[indexsderesultatsaMatriu[i][0]][2];
        }
        boolean b=false;
        boolean[][] bol=new boolean[colors.length][resultatUnic.length];
        while(!b){
            b=true;
            for(int i=0;i<colors.length;i++){
                String[] t=splitPan.arr_simbolsFuncioTaulaF.get(i);
                for(int j=0;j<t.length;j++)for(int k=0;k<resultatUnic.length;k++)if(t[j].equals(simbolsArr[k])&&i!=k&&indexsderesultatsaMatriu[k][2]>=0){
                    if(!bol[i][k]){
                        longiP[i]=indexsderesultatsaMatriu[k][2];//la longitud de la matriu de la funcio es fa igual a la dimensio del simbol(columna1) de la matriu associada que es troba primer
                        if(!resultatUnic[i]&&indexsderesultatsaMatriu[i][2]==-2)indexsderesultatsaMatriu[i][2]=longiP[i];//si el simbol de la matriu associada no te dimensio se li assigna la de la funcio
                        bol[i][k]=true;b=false;//nomes que hi hagi un nou canvi es tornara ha executar el procediment
                    }//si no es la primera coincidencia i hi ha divergencia aleshores hi ha error
                    else if(longiP[i]!=indexsderesultatsaMatriu[k][2]){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior fila: '"+i+"', funci"+Func.rB.getString("o_")+": "+matriu[i+1][0]+ " hi ha matrius de diferent longitud: "+indexsderesultatsaMatriu[k][2]+"; "+longiP[i] +";"+splitPan.FIL);return false;}
                }
            }
        }
        //for(int i=0;i<colors.length;i++)System.out.println(">"+i+" "+indexsderesultatsaMatriu[i][0]+" "+indexsderesultatsaMatriu[i][2]+" "+longiP[i]);
        //for(int i=colors.length;i<simbolsArr.length;i++)System.out.println(i+" "+indexsderesultatsaMatriu[i][0]+" "+indexsderesultatsaMatriu[i][2]);
        //totes les funcions exteses estan definides mitjantçant longiP[k] i funcArrBol[i]=true;
        taulaF.hihaArray=false;
        arr_matriusIndexs=new ArrayList<int[][]>();//[][0]indica la filera origen del array  [][1]les possicions a dVO de la funcio
        arr_matriusdeResultats=new ArrayList<double[]>();//cada filera clasificada com array guarda els valors al array
        for(int i=0;i<resultatUnic.length;i++){
            if(!resultatUnic[i])hihaArray=true;//if(!resultatUnic[i]||funcArrBol[i]||indexsderesultatsaMatriu[i][2]>1)hihaArray=true;
            if(indexsderesultatsaMatriu[i][0]==i&&indexsderesultatsaMatriu[i][2]>=0){
                double[] d=new double[indexsderesultatsaMatriu[i][2]];
                for(int j=0;j<taulaP.simParCapNoVar.length;j++)if(simbolsArr[i].equals(taulaP.simParCapNoVar[j])){
                    for(int k=0;k<taulaP.parametres.length;k++)if(taulaP.parametres[k][0].equals(simbolsArr[i])){
                        for(l=0;l<indexsderesultatsaMatriu[i][2];l++)d[l]=taulaP.dparametres[k][l+1];
                    }
                }
                for(int j=0;j<taulaP.simParCapVar.length;j++)if(simbolsArr[i].equals(taulaP.simParCapVar[j])){
                    for(int k=0;k<taulaP.parametres.length;k++)if(taulaP.parametres[k][0].equals(simbolsArr[i])){
                        for(l=0;l<indexsderesultatsaMatriu[i][2];l++)d[l]=NaN;
                    }
                }
                arr_matriusdeResultats.add(d);
            }
            else arr_matriusdeResultats.add(new double[0]);
            arr_matriusIndexs.add(new int[0][0]);
        }
        for(int i=0;i<colors.length;i++){
            String s=simbolsArr[i];
            if(s.endsWith("]")){
                s=s.substring(0,s.length()-1);
                int k=s.lastIndexOf("[");
                if(k>-1){
                    if(!resultatUnic[i]){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior columna '"+matriu[0][idxIni]+"', fila: "+i+"; cadena de matriu: 'arr' i al mayeix temps a columna '"+matriu[0][1]+"' semble definit un resultat de un element de matriu simbols'[]', compatible amb un resultat unic" + splitPan.FIL);return false;}
                    s=s.substring(0,k);
                    //for(int j=0;j<colors.length;j++){if(s.equals(simbolsArr[j])&&indexsderesultatsaMatriu[j][0]>-1&&indexsderesultatsaMatriu[j][2]>1){indexsderesultatsaMatriu[i][0]=j;j=colors.length+1;}}
                    for(int j=0;j<colors.length;j++){if(s.equals(simbolsArr[j])&&indexsderesultatsaMatriu[j][0]>-1){indexsderesultatsaMatriu[i][0]=indexsderesultatsaMatriu[j][0];j=colors.length+1;}}
                
                }
            }
        }
        for(int i=0;i<mi.length;i++)digitsValids[i]=5;
        for(int i=0;i<mi.length;i++){ 
            int m=mi[i].indexOf("dv(");
            if(m>-1){
                int k=mi[i].indexOf(")",m);
                try {digitsValids[i]=Integer.parseInt(mi[i].substring(m+3,k));}
                catch(Exception e){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior columna '"+matriu[0][idxIni]+"', cadena associada a digits  v"+Func.rB.getString("a")+"lids: dv(int 0><=14)" + splitPan.FIL);return false;}
                if(digitsValids[i]<0){digitsValids[i]=-digitsValids[i];noArrodonir[i]=true;}
                if(digitsValids[i]>14)digitsValids[i]=14;
                if(digitsValids[i]==0)digitsValids[i]=1;
                for(int j=i+1;j<mi.length;j++)digitsValids[j]=digitsValids[i];
                mi[i]=mi[i].substring(0,m)+mi[i].substring(k+1);
            }
        }
        for(int i=0;i<mi.length;i++)escala[i]="";
        for(int i=0;i<mi.length;i++){
            int m=mi[i].indexOf("esc(");
            if(m>-1){
                int k=mi[i].indexOf(")");
                escala[i]=mi[i].substring(m+4,k);
                for(int j=i;j<mi.length;j++)if(!simbolsDefinits[j])escala[j]=escala[i];
                mi[i]=mi[i].substring(0,m)+mi[i].substring(k+1);
            }
        }
        con=0;
        for(int i=0;i<mi.length;i++)if(!mi[i].equals("")) con++;
        valorInicial=new double[con][2];
        con=0;
        b =false;
        for(int i=0;i<mi.length;i++){
            mi[i]=splitPan.treuBlancs(mi[i]);
            if(!mi[i].equals("")){
                boolean bo=false;
                try{
                    valorInicial[con][0]=i;
                    valorInicial[con][1]=Double.parseDouble(splitPan.comApun(mi[i]));
                    con++;
                }
                catch(NumberFormatException e){
                    Func.append(1,"info: ");
                    Func.append(0,"a taula de funcions superior  columna '"+matriu[0][idxIni]+"'; fila: "+i+"; el valor inicial de la variable ha d'esser una de les seg"+Func.rB.getString("u")+"ents opcions: valor num"+Func.rB.getString("_e")+"ric; arr, arr[sencer], mat, mat[sencer] dv(sencer), esc(sencer), def;"+ splitPan.FIL);
                    bo=true;
                    i=mi.length;
                }
                if(bo)return false;
            }
        }
        return true;
    }
    static void iniArrays(){
        double[][] d=new double[0][0];
        String[][] s=new String[0][0];
        String[] c=new String[0];
        int[] it=new int[0];
        int[][] it1=new int[0][0];
        boolean[] b=new boolean[0];
        boolean[][] b1=new boolean[0][0];
        arr_dVars = new ArrayList<double[][]>();
        arr_segCero= new ArrayList<long[]>();
        arr_idxVarGen = new ArrayList<int[]>();
        arr_vargOparv=new ArrayList<boolean[]>();
        arr_idxresultatM= new ArrayList<int[]>();;
	arr_indexs=new ArrayList<int[][]>();
        arr_indexsVar=new ArrayList<int[][]>();
        arr_indexsVarG=new ArrayList<int[][]>();
        arr_limitindexsVarGen=new ArrayList<int[][]>();        
        arr_indexsVarModificats0 = new ArrayList<int[]>();
        arr_indexsVarModificats1 = new ArrayList<int[]>();
        arr_varstaulaV = new ArrayList<String[]>();
        arr_indexsVaraSim= new ArrayList<int[][]>();
        arr_indexsParNoVar = new ArrayList<int[][]>();
        if(taulaP.hihaParVar){
            arr_varPTV_varPTP=new ArrayList<int[][]>();
            arr_longiPBol=new ArrayList<boolean[]>();
            arr_idxParVar = new ArrayList<int[]>();
            arr_indexsParv = new ArrayList<int[][]>();
            arr_indexsParV = new ArrayList<int[][]>();
            arr_indexsParVG = new ArrayList<int[][]>();
        }
        if(taulaI.hihaDades){
            tI_VG_integralBol=new boolean[colors.length];//associat a Cal.integralBol true si la funcio te derivades
            arr_indexsSumafuncioF=new ArrayList<int[][]>();
            arr_indexsVGI=new ArrayList<int[][]>();
            arr_idxVarGenIntegr=new ArrayList<int[]>();
            arr_indexsVGaI=new ArrayList<int[][]>();
            arr_indexsInteg_VG=new ArrayList<boolean[][]>();
            arr_hihaVGioFPaI=new ArrayList<boolean[]>();
        }
        if(taulaD.hihaDades){
            tD_VG_derivadaBol=new boolean[colors.length];//associat a Cal.derivadaBol true si la funcio te derivades
            arr_indexsDerafuncioF=new ArrayList<int[][]>();
            arr_idxVarDer=new ArrayList<int[]>();
            arr_indexsVGD=new ArrayList<int[][]>();
            arr_indexsVGaD=new ArrayList<int[][]>();
            arr_hihaVGioFPaD=new ArrayList<boolean[]>();
            arr_derivadesLimFun=new ArrayList<int[][]>();
            arr_idxvarsdeFunDer=new ArrayList<int[]>();
            arr_VGaFuncBol_taulaD=new ArrayList<boolean[][]>();
        }
        for(int i=0;i<colors.length;i++){
            arr_dVars.add(i,d);
            arr_segCero.add(i,new long[0]);
            arr_idxVarGen.add(i,it);
            arr_vargOparv.add(i,b);
            arr_idxresultatM.add(i,it);
            arr_varstaulaV.add(i,c);//  simbols de les variables totes en el mateix ordre que la taulas
            arr_indexs.add(i,it1);
            arr_indexsVar.add(i,it1);
            arr_indexsVarG.add(i,it1);
            arr_limitindexsVarGen.add(i,it1);
            arr_indexsVarModificats0.add(it);
            arr_indexsVaraSim.add(i,it1);
            int[] id=new int[taulaV.varstaulaV.length];for(int j=0;j<id.length;j++)id[j]=j;
            arr_indexsVarModificats1.add(id);
            arr_varstaulaV.add(c);
            arr_indexsParNoVar.add(i,it1);
            if(taulaP.hihaParVar){
                arr_longiPBol.add(i,b);
                arr_varPTV_varPTP.add(i,it1);
                arr_idxParVar.add(i,it);
                arr_indexsParv.add(i,it1);
                arr_indexsParV.add(i,it1);
                arr_indexsParVG.add(i,it1);
            }
            if(taulaD.hihaDades){
                arr_indexsDerafuncioF.add(i,it1);
                arr_idxVarDer.add(i,it);
                arr_idxvarsdeFunDer.add(i,it);//variables generals que es troben a les funcions derivades
                arr_indexsVGD.add(i,it1);
                arr_indexsVGaD.add(i,it1);
                arr_hihaVGioFPaD.add(i,b);
                arr_derivadesLimFun.add(i,it1);
                arr_VGaFuncBol_taulaD.add(i,b1);
            }
            if(taulaI.hihaDades){
                arr_indexsSumafuncioF.add(i,it1);
                arr_idxVarGenIntegr.add(i,it);
                arr_indexsVGaI.add(i,it1);
                arr_indexsVGI.add(i,it1);
                arr_indexsInteg_VG.add(i,b1);
                arr_hihaVGioFPaI.add(i,b);
            }
        }
    }
    static boolean arraysPrevis(){//s'utilitza a splitPan.cadenes_sVO_deFuncions()
        iniArrays();
        longi=new int[colors.length];
        //varBol per cada filera indica la primera columna si conte algun parametre variable o variable (directes no  mitjnçan sumatori o derivada) i per les seguents columnes-1 = la posicio a la taula varibles de la variable o parametre variable
        //varGenBol lamateixa informacio pel que fa a les variables generals en aquest cas directes o indirectes
        for(int fil=0;fil<colors.length;fil++)for(int j=1;j<varBol[0].length;j++)if(varBol[fil][j]||varGenBol[fil][j])longi[fil]++;
        for(int fil=0;fil<colors.length;fil++){
            if(longi[fil]>0){
                int[] indexsModificats0=new int[longi[fil]];//indexsModificats0 per cada filera de la taula de variables  (la especifica de la filera reduida) indica la possicio de la filera de la la taula variables
                int[] indexsModificats1=new int[taulaV.varstaulaV.length];//el proces cotrari assigna a cada filera de la taula de variables el index de la taula variables especifica de la filera (reduida) o -1 si no n'hi ha
                String[] varstaulaV=new String[longi[fil]];
                int conta=0;
                for(int j=1;j<varBol[0].length;j++){
                    if(varBol[fil][j]||varGenBol[fil][j]){
                        indexsModificats0[conta]=j-1;
                        indexsModificats1[j-1]=conta;
                        varstaulaV[conta]=taulaV.varstaulaV[j-1];
                        conta++;
                    }
                    else indexsModificats1[j-1]=-1;
                }
                arr_indexsVarModificats0.set(fil,indexsModificats0);
                arr_indexsVarModificats1.set(fil,indexsModificats1);
                arr_varstaulaV.set(fil,varstaulaV);
            }
            //System.out.print("indexsModificats0 fila:"+fil+": ");int[] it=arr_indexsVarModificats0.get(fil);for(int i=0;i<it.length;i++){System.out.print(i+" "+it[i]+" _ ");}System.out.println();
            //System.out.print("indexsModificats1 fila:"+fil+": ");it=arr_indexsVarModificats1.get(fil);for(int i=0;i<it.length;i++){System.out.print(i+" "+it[i]+" _ ");}System.out.println();
            //System.out.print("varstaulaV fila:"+fil+": ");String[] st=arr_varstaulaV.get(fil);for(int i=0;i<st.length;i++){System.out.print(i+" "+st[i]+" _ ");}System.out.println();
        }
        for(int fil=0;fil<colors.length;fil++){
            int[] indexsVarMod=arr_indexsVarModificats1.get(fil);//assigna a cada filera de la taula de variables el index de la taula variables reduida
            int[] idxVarGen=new int[0];
            int[] idxParVar=new int[0];
            if(varGenBol[fil][0]){
                int conta=0;for(int i=1;i<varGenBol[fil].length;i++)if(varGenBol[fil][i])conta++;
                idxVarGen=new int[conta];
                conta=0;
                for(int i=1;i<varGenBol[fil].length;i++)if(varGenBol[fil][i]){idxVarGen[conta]=indexsVarMod[i-1];conta++;}
                arr_idxVarGen.set(fil,idxVarGen);
            }
            if(taulaD.hihaDades&&varGenDerBol[fil][0]){
                int[] m=new int[taulaD.idxVarDer.length];//variable general derivable 
                System.arraycopy(taulaD.idxVarDer, 0, m, 0, m.length);
                for(int i=0;i<m.length;i++)if(taulaD.idxVarDer[i]>-1)m[i]=indexsVarMod[taulaD.idxVarDer[i]];
                arr_idxVarDer.set(fil,m);
                int con=0;
                m=new int[taulaD.idxvarsdeFunDer.length];//variables generals presents a la funcio
                for(int i=0;i<m.length;i++){m[i]=indexsVarMod[taulaD.idxvarsdeFunDer[i]];if(m[i]>-1)con++;}
                int[] n=new int[con];
                con=0;
                for(int i=0;i<m.length;i++)if(m[i]>-1){n[con]=m[i];con++;}
                arr_idxvarsdeFunDer.set(fil,n);
                boolean[] b=new boolean[taulaD.hihaVGioFP.length];
                int[][] m1=splitPan.copiaMatriu(taulaD.indexsVGaD);//indexs de variables generals a funcions parcials taulaD.derivadesLimFun conte els limits de dVO indexs i indexsVGaD 
                for(int i=0;i<m1.length;i++)m1[i][1]=indexsVarMod[m1[i][1]];
                int[][] m2=new int[taulaD.derivadesLimFun.length][6];
                m2=splitPan.copiaMatriu(taulaD.derivadesLimFun);
                int cont=0;
                for(int j=0;j<taulaD.derivadesLimFun.length;j++){
                    for(int l=taulaD.derivadesLimFun[j][4];l<taulaD.derivadesLimFun[j][5];l++){
                        if(m1[l][1]>-1){b[j]=true;}
                        else{
                            cont++;
                            m2[j][5]--;
                            for(int k=j+1;k<m2.length;k++){m2[k][4]--;m2[k][5]--;}
                        }
                    }
                }
                int[][] m3=new int[m1.length-cont][2];
                cont=0;
                for(int i=0;i<m1.length;i++)if(m1[i][1]>-1){m3[cont][0]=m1[i][0]; m3[cont][1]=m1[i][1];cont++;}
                //for(int i=0;i<m1.length;i++)System.out.print(m1[i][0]+" "+m1[i][1]+" _ ");System.out.println();
                //System.out.println("taulaD.hihaVGioFP: idxVarGen.length: "+idxVarGen.length);
                //for(int i=0;i<taulaD.indexsVGaD.length;i++)System.out.print(taulaD.indexsVGaD[i][0]+" "+taulaD.indexsVGaD[i][1]+" _ ");System.out.println();
                //for(int i=0;i<m1.length;i++)System.out.print(m1[i][0]+" "+m1[i][1]+" _ ");System.out.println();
                //for(int i=0;i<b1.length;i++)for(int j=0;j<b1[0].length;j++)System.out.print(i+" "+j+" "+b1[i][j]+" _ ");System.out.println();
                //for(int i=0;i<b.length;i++)System.out.print(b[i]+" _ ");System.out.println();
                arr_indexsVGaD.set(fil,m3);//suportID.indexsVGaI: indexs que associen [][0]: possicio a dVO de la taula funcions parcials; i [][1]:indexs de variables generals reduida
                arr_derivadesLimFun.set(fil,m2);
                arr_hihaVGioFPaD.set(fil,b);
                boolean[][] b1=new boolean[taulaD.simbol.length][idxVarGen.length];
                for(int i=0;i<taulaD.simbol.length;i++){
                    int conta=0;
                    for(int j=0;j<Cal.idxVarGen.length;j++)if(indexsVarMod[Cal.idxVarGen[j]]>-1){
                        b1[i][conta]=taulaD.VGaFuncBol[i][j];conta++;
                    }
                }
                arr_VGaFuncBol_taulaD.set(fil,b1);
            }
            if(taulaI.hihaDades)if(varGenSumBol[fil][0]){
                int[] m=new int[suportID.idxVarGenIntegr.length];//matriu amb indexs modificats
                int con=0;
                for(int i=0;i<m.length;i++){m[i]=indexsVarMod[suportID.idxVarGenIntegr[i]];if(m[i]>-1)con++;}
                int[] n=new int[con];
                con=0;
                for(int i=0;i<m.length;i++)if(m[i]>-1){n[con]=m[i];con++;}
                arr_idxVarGenIntegr.set(fil,n);//
                int[][] m1=splitPan.copiaMatriu(suportID.indexsVGaI);
                con=0;
                for(int i=0;i<m1.length;i++){
                    m1[i][1]=indexsVarMod[m1[i][1]];
                    if(m1[i][1]>-1)con++;
                }
                int[][] n1=new int[con][2];
                con=0;
                for(int i=0;i<m1.length;i++)if(m1[i][1]>-1){n1[con][0]=m1[i][0];n1[con][1]=m1[i][1];con++;}
                arr_indexsVGaI.set(fil,n1);//suportID.indexsVGaI: indexs que associen [][0]: possicio a sdVOI de la taulaSumatoris; i [][1]:indexs de variables generals reduida
                boolean [][] b=new boolean[longi[fil]][taulaI.sumat.length];
                con=0;
                for(int i=0;i<Cal.indexsInteg_VG.length;i++)if(indexsVarMod[i]>-1){
                    for(int j=0;j<b[0].length;j++)b[indexsVarMod[i]][j]=Cal.indexsInteg_VG[i][j];
                }
                arr_indexsInteg_VG.set(fil,b);
                boolean [] b1=new boolean[suportID.hihaVGioFPaI.length];
                for(int j=0;j<taulaI.sumat.length;j++)if(varGenSumBol[fil][j+1])b1[j]=true;
                arr_hihaVGioFPaI.set(fil,b1);
            }
            idxVarGen=arr_idxVarGen.get(fil);
            if(taulaP.hihaParVar){
                boolean[] longiPBol=new boolean[longiP[fil]];
                int cont=0;boolean[] b=new boolean [taulaV.varstaulaV.length];
                for(int k=1;k<parCapBol[0].length;k++)if(parCapBol[fil][k]){//primer selecciona les columnes en que el simbol de capçalera de la taula parametres es troba en la funcio
                    for(int l=0;l<taulaP.varPTV_varPTP.length;l++){//el segon subindex 0 es la filera de la taula variables
                        if(taulaP.varPTV_varPTP[l][2]==k-1){//si la columna coincideix
                            longiPBol[taulaP.varPTV_varPTP[l][1]]=true;//el lngiPBol de la filera es true
                            cont++;
                            b[taulaP.varPTV_varPTP[l][0]]=true;//la filera de la taulaV es true
                        }
                    }
                }
                arr_longiPBol.set(fil,longiPBol);
                idxParVar=new int[cont++];
                cont=0;
                for(int k=0;k<b.length;k++)if(b[k]){idxParVar[cont]=indexsVarMod[k];cont++;}
                arr_idxParVar.set(fil,idxParVar);
                int conta=0;
                for(int j=0;j<taulaP.varPTV_varPTP.length;j++)if(varBol[fil][taulaP.varPTV_varPTP[j][0]+1]&&taulaP.varPTV_varPTP[j][1]<longiP[fil])conta++;
                if(conta>0){
                    int[][] v=new int[conta][3];conta=0;
                    //taulaP.varPTV_varPTP taula parametres el segon index si 0 es la filera de la taula variables amb el parametre variable; si=1 es la filera de la taula parametres en que es troba la variable si 2 es la columna a taula parametres
                    for(int j=0;j<taulaP.varPTV_varPTP.length;j++)if(varBol[fil][taulaP.varPTV_varPTP[j][0]+1]&&taulaP.varPTV_varPTP[j][1]<longiP[fil]){
                        v[conta][0]=indexsVarMod[taulaP.varPTV_varPTP[j][0]];
                        v[conta][1]=taulaP.varPTV_varPTP[j][1];
                        v[conta][2]=taulaP.varPTV_varPTP[j][2];
                        conta++;
                    }
                    arr_varPTV_varPTP.set(fil,v);
                }
            }
            if(idxVarGen.length>0||idxParVar.length>0){
                Cal.vargOparv=new boolean[idxVarGen.length+idxParVar.length];
                for(int i=0;i<idxVarGen.length;i++)Cal.vargOparv[idxVarGen[i]]=true;
                arr_vargOparv.set(fil,Cal.vargOparv);
                Cal.idxresultatM=new int[longi[fil]];
                int conta=0;
                for(int i=0;i<idxParVar.length;i++){Cal.idxresultatM[idxParVar[i]]=conta;conta++;}
                arr_idxresultatM.set(fil,Cal.idxresultatM);
            }
        }
        double[][] dVars=new double[0][0];
        for(int fil=0;fil<colors.length;fil++){
            if((varBol[fil][0]||varGenBol[fil][0])){
                dVars=new double[longi[fil]][Cal.dMatriuVar[0].length];
                int conta=0;
                for(int i=1;i<varBol[fil].length;i++)if(varBol[fil][i]||varGenBol[fil][i]){
                    System.arraycopy(Cal.dMatriuVar[i-1], 0, dVars[conta], 0, dVars[0].length);conta++;
                }
                arr_dVars.set(fil,dVars);
            }
        }
        for(int fil=0;fil<colors.length;fil++)if((varBol[fil][0]||varGenBol[fil][0])){
            Cal.dMatriuVar=arr_dVars.get(fil);
            Cal.longi=longi[fil];
            if(!taulaC.definirEscala(escala[fil])){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior columna '"+matriu[0][idxIni]+"', cadenes relacionada amb escala v"+Func.rB.getString("a")+"lida: 'esc(double -323>=<=0)'" + splitPan.FIL);return false;}
            else{
                escalaLinial[fil]=Cal.escalaLinial;
                ceroLog[fil]=taulaC.ceroLog;   
                if(!Cal.escalaLinial){
                    if(Func.ampliarInfo)Func.append(0,"escala logar"+Func.rB.getString("i_")+"tmica, cero = "+Math.pow(10,ceroLog[fil])+splitPan.FIL);
                    Cal.segCero=new long[Cal.dMatriuVar.length];
                    if(!Cal.escalaLinial) Cal.escalaNoLinial(0,Cal.dMatriuVar.length);
                    arr_segCero.set(fil,Cal.segCero);
                }
            }
            Cal.segCero=arr_segCero.get(fil);
        }
        return true;
    }
    public static boolean idxdefIndef(){
        simbolsElements=new String[0];
        indexsInternsArrayBol=new boolean[colors.length][4];
        idxdef=new String[colors.length];simdef=new String[colors.length];
        idxIndef=new String[colors.length];simIndef=new String[colors.length];
        String sim="",simbolE="";
        String[] s;
        for(int i=0;i<colors.length;i++){
            idxdef[i]="";idxIndef[i]="";simdef[i]="";simIndef[i]="";
            s=splitPan.arr_simbolsFuncioTaulaF.get(i);
            for(int j=0;j<s.length;j++){
                if(s[j].endsWith("]")){
                    int k=0;
                    int ini=s[j].indexOf("[");
                    if(ini<1){Func.append(1,"error: ");Func.append(0," els simbols '[' i ']' nomes s'utilitzen per tractar fileres de arrays de manera individual fan referencia al index d'un array ex: simbo[simol1] indica la filera  = valor de simbol1, de la matriu simbol; fila: "+i+" simbol: "+s[j]+splitPan.FIL);return false;}
                    String n=s[j].substring(ini+1,s[j].length()-1);
                    if(n.equals("")){Func.append(1,"error: ");Func.append(0," els s"+Func.rB.getString("i_")+"mbols '[' i ']' nomes s'utilitzen per tractar fileres de arrays de manera individual i ha de contenir un valor numeric o un simbol, no pot estar buit com a: "+s[j]+"; simbo[simol1] indica la filera  = valor de simbol1, de la matriu simbol; fila: "+i+splitPan.FIL);return false;}
                    try{k=Integer.parseInt(n);indexsInternsArrayBol[i][1]=true;idxdef[i]+=n+" ";simdef[i]+=s[j].substring(0,ini)+" ";}
                    catch(Exception e){idxIndef[i]+=n+" ";indexsInternsArrayBol[i][0]=true;simIndef[i]+=s[j].substring(0,ini)+" ";}
                    if(k<0){Func.append(1,"error: ");Func.append(0," els s"+Func.rB.getString("i_")+"mbols '[' i ']' nomes s'utilitzen per tractar fileres de arrays de manera individual i ha de contenir un valor numeric sencer possitiu o un simbol, ni pot estar buit; simbo[simol1] indica la filera  = valor de simbol1, de la matriu simbol; fila: "+i+splitPan.FIL);return false;}
                    simbolE+=s[j]+" ";
                    sim+=s[j].substring(0,ini)+" ";
                }
                else sim+=s[j]+" ";
            }
            if(sim.length()>0)splitPan.arr_simbolsFuncioTaulaFReduida.set(i,splitPan.matriudeFuncioCompleta(sim));
            }
        if(simbolE.length()>0)simbolsElements=splitPan.treuSimbolsrepetits(splitPan.matriudeFuncioCompleta(simbolE));
        return true;
    }
    static boolean indexsArraysTFThen(){
        String idxI="";String idxD="";String simI="";String simD="";String posD="";String posI="";
        String[] idxIm;String[] idxDm;String[] simIm;String[] simDm;String[] posDm;String[] posIm;
        for(int i=0;i<colors.length;i++){
            idxI="";idxD="";simI="";simD="";posD="";posI="";
            if(thenBol[i][0]){
                indexsSimTF=arr_indexsSimTF.get(i);
                for(int j=0;j<indexsSimTF.length;j++){
                    String s=simbolsReduits[indexsSimTF[j][1]];
                    if(s.endsWith("]")){
                        simbolsReduitsArrayIF[i][0]=true;
                        int ini=s.indexOf("[");
                        if(ini<1){Func.append(1,"error: ");Func.append(0," els simbols '[' i ']' nom"+Func.rB.getString("e_")+"s s'utilitzen per tractar fileres de arrays de manera individual fan refer"+Func.rB.getString("_e")+"ncia al index d'un array ex: simbo[simol1] indica la filera  = valor de simbol1, de la matriu simbol; fila: "+i+" simbol: "+s+splitPan.FIL);return false;}
                        String n=s.substring(ini+1,s.length()-1);
                        if(n.equals("")){Func.append(1,"error: ");Func.append(0," els simbols '[' i ']' nom"+Func.rB.getString("e_")+"s s'utilitzen per tractar fileres de arrays de manera individual i ha de contenir un valor num"+Func.rB.getString("_e")+"ric o un s"+Func.rB.getString("i_")+"mbol, no pot estar buit com a: "+s+"; simbo[simol1] indica la filera  = valor de simbol1, de la matriu simbol; fila: "+i+splitPan.FIL);return false;}
                        try{Integer.parseInt(n);simbolsReduitsArrayIF[i][2]=true;idxD+=n+" ";simD+=s.substring(0,ini)+" ";posD+=indexsSimTF[j][1]+" ";}
                        catch(Exception e){simbolsReduitsArrayIF[i][1]=true;idxI+=n+" ";simI+=s.substring(0,ini)+" ";posI+=indexsSimTF[j][1]+" ";}
                    }
                }
                idxDm=splitPan.matriudeFuncioCompleta(idxD);//valor del index del simbol definit 
                simDm=splitPan.matriudeFuncioCompleta(simD);//simbol de la matriu 
                idxIm=splitPan.matriudeFuncioCompleta(idxI);//simboldel index indefinit 
                simIm=splitPan.matriudeFuncioCompleta(simI);//simbol de la matriu 
                posDm=splitPan.matriudeFuncioCompleta(posD);//possicio del simbol definit a simbolsReduits 
                posIm=splitPan.matriudeFuncioCompleta(posI);//possicio del simbol indefinit a simbolsReduits 
                int[][] it=new int[idxDm.length][3];
                for(int j=0;j<idxDm.length;j++){it[j][1]=Integer.parseInt(idxDm[j]);it[j][0]=Integer.parseInt(posDm[j]);}
                for(int k=0;k<simDm.length;k++){
                    boolean b=false;
                    for(int j=0;j<simbolsArr.length;j++)if(simDm[k].equals(simbolsArr[j])){it[k][2]=indexsderesultatsaMatriu[j][0];j=simbolsArr.length;b=true;}
                    if(!b){Func.append(1,"error: ");Func.append(0," a columna: "+matriu[0][idxThen]+"', fila: "+i+"; els s"+Func.rB.getString("i_")+"mbols de matrius amb index '"+simDm[k]+"[]' s'han de definir previament en la columna 1 o 3 o a taulaP"+ splitPan.FIL);return false;}
                }
                if(simDm.length>0)arr_IFsimbolsReduitsArrayIndexs1.set(i,it);//[][0] possicio del simbol definit a simbolsReduits; [][1] valor del index del simbol definit ; [][2] la possicio de la matriu de referencia on es troben els resultats de la matriu
                it=new int[idxIm.length][3];
                for(int j=0;j<idxIm.length;j++)it[j][0]=Integer.parseInt(posIm[j]);
                for(int k=0;k<idxIm.length;k++){
                    boolean b=false;
                    for(int j=0;j<simbolsReduits.length;j++)if(idxIm[k].equals(simbolsReduits[j])){it[k][1]=j;b=true;j=simbolsReduits.length;}
                    if(!b){Func.append(1,"error: ");Func.append(0," a columna: "+matriu[0][idxThen]+"', fila: "+i+"; indexs no num"+Func.rB.getString("_e")+"rics de s"+Func.rB.getString("i_")+"mbols de matrius: "+simIm[k]+"["+idxIm[k]+"] s'han d'haver definit a columna 1 o 3"+ splitPan.FIL);return false;}
                } 
                for(int k=0;k<simIm.length;k++){
                    boolean b=false;
                    for(int j=0;j<simbolsArr.length;j++)if(simIm[k].equals(simbolsArr[j])){it[k][2]=indexsderesultatsaMatriu[j][0];j=simbolsArr.length;b=true;}
                    if(!b){Func.append(1,"error: ");Func.append(0," a columna: "+matriu[0][idxThen]+"', fila: "+i+"; els s"+Func.rB.getString("i_")+"mbols de matrius amb index '"+simIm[k]+"[]' s'han de definir previament en la columna 1 o 3 o a taulaP"+ splitPan.FIL);return false;}
                }
                if(simIm.length>0)arr_IFsimbolsReduitsArrayIndexs0.set(i,it);//[][0] possicio del simbol indefinit a simbolsReduits; [][1] possicio del index indefinit a simbolsReduits; ; [][2] la possicio de la matriu de referencia on es troben els resultats de la matriu
            }
            //for(int nn=0;nn<simbolsReduits.length;nn++)System.out.println(simbolsReduits[nn]);
            if(thenBol[i][4]){
                iThenPosSim=arr_iThenPosSim.get(i);if(!Then(i,true))return false;
                for(int j=0;j<iThenPosSim.length;j++){try{Double.parseDouble(simbolsReduits[iThenPosSim[j][1]]);Func.append(1,"error: ");Func.append(0," a columna: "+matriu[0][idxThen]+"', fila: "+i+"; el dest"+Func.rB.getString("i_")+" de una copia de valors no pot esser valor num"+Func.rB.getString("_e")+"ric: "+simbolsReduits[iThenPosSim[j][1]]+ splitPan.FIL);return false;}catch(Exception e){}}
                iThenPosSim=arr_jThenPosSim.get(i);if(!Then(i,false))return false;
                for(int j=0;j<iThenPosSim.length;j++){try{Double.parseDouble(simbolsReduits[iThenPosSim[j][1]]);Func.append(1,"error: ");Func.append(0," a columna: "+matriu[0][idxThen]+"', fila: "+i+"; el dest"+Func.rB.getString("i_")+" de una copia de valors no pot esser valor num"+Func.rB.getString("_e")+"ric: "+simbolsReduits[iThenPosSim[j][1]]+ splitPan.FIL);return false;}catch(Exception e){}}
            }
         }
        return true;
    }
    static boolean Then(int i,boolean bol){
        String c="";String idxI="";String idxD="";String[] idxIm;String[] idxDm;String idxO="";
        for(int j=0;j<iThenPosSim.length;j++){
            String s0=simbolsReduits[iThenPosSim[j][0]];
            String s1=simbolsReduits[iThenPosSim[j][1]];
            //System.out.println(i+" "+s0+" "+s1+" "+(!s0.contains("[")||s0.contains("]"))+" "+(!s1.contains("[")||s1.contains("]")));
            boolean b=false;int k;
            if(s0.contains("[")!=s0.contains("]")){ Func.append(1,"error: ");Func.append(0," a columna: "+matriu[0][idxThen]+"', fila: "+i+";  s"+Func.rB.getString("i_")+"mbol amb definicio incorrecta de index de matriu: "+s0+"; "+splitPan.FIL);return false;}
            if(s1.contains("[")!=s1.contains("]")){ Func.append(1,"error: ");Func.append(0," a columna: "+matriu[0][idxThen]+"', fila: "+i+";  s"+Func.rB.getString("i_")+"mbol amb definicio incorrecta de index de matriu: "+s1+"; "+splitPan.FIL);return false;}
            
            /*if(!s0.contains("[")||!s0.contains("]")){
                try{Integer.parseInt(s0);}catch(Exception e){
                    for(k=0;k<simbolsReduits.length;k++)if(s0.equals(simbolsReduits[k])){ b=true;k=simbolsReduits.length;}
                    if(!b){´Func.append(1,"error: ");Func.append(0," a columna: "+matriu[0][idxThen]+"', fila: "+i+";  s"+Func.rB.getString("i_")+"mbol no definit: "+s0+"; "+splitPan.FIL);return false;}
                }
            }
            if(!s1.contains("[")||!s1.contains("]")){
                try{Integer.parseInt(s1);}catch(Exception e){
                    for(k=0;k<simbolsReduits.length;k++)if(s1.equals(simbolsReduits[k])){ b=true;k=simbolsReduits.length;}
                    if(!b){Func.append(1,"error: ");Func.append(0," a columna: "+matriu[0][idxThen]+"', fila: "+i+";  s"+Func.rB.getString("i_")+"mbol no definit: "+s1+"; "+splitPan.FIL);return false;}
                }
            }*/
            boolean b0=false,b1=false;
            for(k=0;k<simbolsArr.length;k++)if(s0.equals(simbolsArr[k])&&(!resultatUnic[k])){b0=true;idxO+=indexsderesultatsaMatriu[k][0]+" ";k=simbolsArr.length;}
            for(k=0;k<simbolsArr.length;k++)if(s1.equals(simbolsArr[k])&&(!resultatUnic[k])){b1=true;idxD+=indexsderesultatsaMatriu[k][0]+" ";k=simbolsArr.length;}
            if(b0&&b1){
                c+="t ";
                if(bol)simbolsReduitsArrayThen[i][3]=true;
                else simbolsReduitsArrayThenReturn[i][3]=true;
            }
            else if(!b0&&!b1)c+="ff ";
            else if(b0&&!b1){Func.append(1,"avis: ");Func.append(0," a columna: "+matriu[0][idxThen]+"', fila: "+i+"; hi ha copia de un array a un s"+Func.rB.getString("i_")+"mbol no definit com array el s"+Func.rB.getString("i_")+"mbol pren el valor de la suma del array;  s"+Func.rB.getString("i_")+"mbols: "+s0+"; "+s1+splitPan.FIL);}
            else if(!b0&&b1){
                c+="ft ";idxO+=iThenPosSim[j][0]+" ";
                if(bol)simbolsReduitsArrayThen[i][3]=true;
                else simbolsReduitsArrayThenReturn[i][3]=true;
            }
        }
        String[] s0=splitPan.matriudeFuncioCompleta(c);
        boolean[][] bo=new boolean[s0.length][2];for(int j=0;j<s0.length;j++){
            if(s0[j].equals("t"))bo[j][0]=true;
            else if(s0[j].equals("ft"))bo[j][1]=true;
        }
        if(bol)arr_iThenPosSimArr.set(i,bo);
        else arr_iThenPosSimArrReturn.set(i,bo);
        idxDm=splitPan.matriudeFuncioCompleta(idxD);
        idxIm=splitPan.matriudeFuncioCompleta(idxO);
        int[][] it=new int[idxDm.length][2]; 
        for(int j=0;j<idxDm.length;j++){it[j][0]=Integer.parseInt(idxIm[j]);it[j][1]=Integer.parseInt(idxDm[j]);}
        if(bol)arr_THENsimbolsReduistArray.add(i,it);
        else arr_THENsimbolsReduistArrayReturn.add(i,it);
        
        idxI="";idxD="";String simI="";String simD="";String posD="";String posI="";
        String[] simIm;String[] simDm;String[] posDm;String[] posIm;
        for(int j=0;j<iThenPosSim.length;j++){
            String s=simbolsReduits[iThenPosSim[j][0]];
            if(s.endsWith("]")){
                if(bol)simbolsReduitsArrayThen[i][0]=true;
                else simbolsReduitsArrayThenReturn[i][0]=true;
                int ini=s.indexOf("[");
                if(ini<1){Func.append(1,"error: ");Func.append(0," els s"+Func.rB.getString("i_")+"mbols '[' i ']' nom"+Func.rB.getString("e_")+"s s'utilitzen per tractar valors especifics de arrays; fan referencia al index d'un array ex: simbo[simol1]] indica el index  = valor de simbol1, de la matriu simbol; fila: "+i+" simbol: "+s+splitPan.FIL);return false;}
                String n=s.substring(ini+1,s.length()-1);
                if(n.equals("")){Func.append(1,"error: ");Func.append(0," els s"+Func.rB.getString("i_")+"mbols '[' i ']' nom"+Func.rB.getString("e_")+"s s'utilitzen per tractar valors especifics de arrays i han de contenir un valor num"+Func.rB.getString("e_")+"ric o un simbol no pot estar buit com a: "+s+"; simbol[simol1] indica la filera  = valor de simbol1, de la matriu simbol; fila: "+i+splitPan.FIL);return false;}
                try{
                    Integer.parseInt(n);
                    if(bol)simbolsReduitsArrayThen[i][2]=true;
                    else simbolsReduitsArrayThenReturn[i][2]=true;
                    idxD+=n+" ";simD+=s.substring(0,ini)+" ";posD+=iThenPosSim[j][0]+" ";
                }
                catch(Exception e){
                    if(bol)simbolsReduitsArrayThen[i][1]=true;
                    else simbolsReduitsArrayThenReturn[i][1]=true;
                    idxI+=n+" ";simI+=s.substring(0,ini)+" ";posI+=iThenPosSim[j][0]+" ";}
            }
        }
        idxDm=splitPan.matriudeFuncioCompleta(idxD);simDm=splitPan.matriudeFuncioCompleta(simD);
        idxIm=splitPan.matriudeFuncioCompleta(idxI);simIm=splitPan.matriudeFuncioCompleta(simI);
        posDm=splitPan.matriudeFuncioCompleta(posD);posIm=splitPan.matriudeFuncioCompleta(posI);
        it=new int[idxDm.length][3];
        for(int j=0;j<idxDm.length;j++){it[j][1]=Integer.parseInt(idxDm[j]);it[j][0]=Integer.parseInt(posDm[j]);}
        for(int k=0;k<simDm.length;k++){
            boolean b=false;
            for(int j=0;j<simbolsArr.length;j++)if(simDm[k].equals(simbolsArr[j])){it[k][2]=indexsderesultatsaMatriu[j][0];j=simbolsArr.length;b=true;}
            if(!b){Func.append(1,"error: ");Func.append(0," a columna: "+matriu[0][idxThen]+"', fila: "+i+"; els s"+Func.rB.getString("i_")+"mbols de matrius amb indexs indefinits: '"+simDm[k]+"[index]'; s'han de definir previament en la columna 1,"+ splitPan.FIL);return false;}
        }
        if(simDm.length>0){
            if(bol)arr_THENsimbolsReduitsArrayIndexs1.set(i,it);
            else arr_THENsimbolsReduitsArrayIndexs1Return.set(i,it);
        }//[][0] l'index [][1] la filera de la matiu amb els valors [][2] la possicio a simbols reduita del simbol
        it=new int[idxIm.length][3];
        for(int j=0;j<idxIm.length;j++)it[j][0]=Integer.parseInt(posIm[j]);
        for(int k=0;k<idxIm.length;k++){
            boolean b=false;
            for(int j=0;j<simbolsReduits.length;j++)if(idxIm[k].equals(simbolsReduits[j])){it[k][1]=j;b=true;j=simbolsReduits.length;}
            if(!b){Func.append(1,"error: ");Func.append(0," a columna: "+matriu[0][idxThen]+"', fila: "+i+"; indexs no num"+Func.rB.getString("_e")+"rics de s"+Func.rB.getString("i_")+"mbols de matrius: "+simIm[k]+"["+idxIm[k]+"] s'han d'haver definit a columna 1 o 3"+ splitPan.FIL);return false;}
        } 
        for(int k=0;k<simIm.length;k++){
            boolean b=false;
            for(int j=0;j<simbolsArr.length;j++)if(simIm[k].equals(simbolsArr[j])){it[k][2]=indexsderesultatsaMatriu[j][0];j=simbolsArr.length;b=true;}
            if(!b){Func.append(1,"error: ");Func.append(0," a columna: "+matriu[0][idxThen]+"', fila: "+i+"; els s"+Func.rB.getString("i_")+"mbols de matrius amb indexs definits num"+Func.rB.getString("_e")+"rics: '"+simDm[k]+"[sencer]'; s'han de definir pr"+Func.rB.getString("_e")+"viament en la columna 1,"+ splitPan.FIL);return false;}
        }
        if(simIm.length>0){
            if(bol)arr_THENsimbolsReduitsArrayIndexs0.set(i,it);
            else  arr_THENsimbolsReduitsArrayIndexs0Return.set(i,it);
        }
        
        idxI="";idxD="";simI="";simD="";posD="";posI="";
        for(int j=0;j<iThenPosSim.length;j++){
            String s=simbolsReduits[iThenPosSim[j][1]];
            if(s.endsWith("]")){
                if(bol)simbolsReduitsArrayThen[i][4]=true;
                else simbolsReduitsArrayThenReturn[i][4]=true;
                int ini=s.indexOf("[");
                if(ini<1){Func.append(1,"error: ");Func.append(0," els s"+Func.rB.getString("i_")+"mbols '[' i ']' nom"+Func.rB.getString("e_")+"s s'utilitzen per tractar valors especifics de arrays; fan refer"+Func.rB.getString("_e")+"ncia al index d'un array ex: simbo[simol1] indica el index  = valor de simbol1, de la matriu simbol; fila: "+i+" s"+Func.rB.getString("i_")+"mbol: "+s+splitPan.FIL);return false;}
                String n=s.substring(ini+1,s.length()-1);
                if(n.equals("")){Func.append(1,"error: ");Func.append(0," els s"+Func.rB.getString("i_")+"mbols '[' i ']' nom"+Func.rB.getString("e_")+"s s'utilitzen per tractar valors especifics de arrays i han de contenir un valor num"+Func.rB.getString("_e")+"ric o un s"+Func.rB.getString("i_")+"mbol no pot estar buit com a: "+s+"; simbo[simol1] indica la filera  = valor de simbol1, de la matriu s"+Func.rB.getString("i_")+"mbol; fila: "+i+splitPan.FIL);return false;}
                try{
                    Integer.parseInt(n);
                    if(bol)simbolsReduitsArrayThen[i][6]=true;
                    else simbolsReduitsArrayThenReturn[i][6]=true;
                    idxD+=n+" ";simD+=s.substring(0,ini)+" ";posD+=iThenPosSim[j][1]+" ";
                }
                catch(Exception e){
                    if(bol)simbolsReduitsArrayThen[i][5]=true;
                    else simbolsReduitsArrayThenReturn[i][5]=true;
                    idxI+=n+" ";simI+=s.substring(0,ini)+" ";posI+=iThenPosSim[j][1]+" ";}
            }
        }
        idxDm=splitPan.matriudeFuncioCompleta(idxD);simDm=splitPan.matriudeFuncioCompleta(simD);
        idxIm=splitPan.matriudeFuncioCompleta(idxI);simIm=splitPan.matriudeFuncioCompleta(simI);
        posDm=splitPan.matriudeFuncioCompleta(posD);posIm=splitPan.matriudeFuncioCompleta(posI);
        it=new int[idxDm.length][3];
        for(int j=0;j<idxDm.length;j++){it[j][1]=Integer.parseInt(idxDm[j]);it[j][0]=Integer.parseInt(posDm[j]);}
        for(int k=0;k<simDm.length;k++){
            boolean b=false;
            for(int j=0;j<simbolsArr.length;j++)if(simDm[k].equals(simbolsArr[j])){it[k][2]=indexsderesultatsaMatriu[j][0];j=simbolsArr.length;b=true;}
            if(!b){Func.append(1,"error: ");Func.append(0," a columna: "+matriu[0][idxThen]+"', fila: "+i+"; els s"+Func.rB.getString("i_")+"mbols de matrius amb indexs indefinits: '"+simDm[k]+"[index]' s'han de definir pr"+Func.rB.getString("_e")+"viament en la columna 1,"+ splitPan.FIL);return false;}
        }
        if(simDm.length>0){
            if(bol)arr_THEN_destisimbolsReduitsArrayIndexs1.set(i,it);
            else arr_THEN_destisimbolsReduitsArrayIndexs1Return.set(i,it);
        }//[][0] l'index [][1] la filera de la matiu amb els valors [][2] la possicio a simbols reduita del simbol
        it=new int[idxIm.length][3];
        for(int j=0;j<idxIm.length;j++)it[j][0]=Integer.parseInt(posIm[j]);
        for(int k=0;k<idxIm.length;k++){
            boolean b=false;
            for(int j=0;j<simbolsReduits.length;j++)if(idxIm[k].equals(simbolsReduits[j])){it[k][1]=j;b=true;j=simbolsReduits.length;}
            if(!b){Func.append(1,"error: ");Func.append(0," a columna: "+matriu[0][idxThen]+"', fila: "+i+"; indexs no num"+Func.rB.getString("_e")+"rics de s"+Func.rB.getString("i_")+"mbols de matrius: "+simIm[k]+"["+idxIm[k]+"] s'han d'haver definit pr"+Func.rB.getString("_e")+"viament a columna 1 o 3"+ splitPan.FIL);return false;}
        } 
        for(int k=0;k<simIm.length;k++){
            boolean b=false;
            for(int j=0;j<simbolsArr.length;j++)if(simIm[k].equals(simbolsArr[j])){it[k][2]=indexsderesultatsaMatriu[j][0];j=simbolsArr.length;b=true;}
            if(!b){Func.append(1,"error: ");Func.append(0," a columna: "+matriu[0][idxThen]+"', fila: "+i+"; els s"+Func.rB.getString("i_")+"mbols de matrius amb indexs definits num"+Func.rB.getString("_e")+"rics: '"+simDm[k]+"[sencer]'; s'han de definir pr"+Func.rB.getString("_e")+"viament en la columna 1,"+ splitPan.FIL);return false;}
        }
        if(simIm.length>0){
            if(bol)arr_THEN_destisimbolsReduitsArrayIndexs0.set(i,it);
            else arr_THEN_destisimbolsReduitsArrayIndexs0Return.set(i,it);
        }
        return true;
    }
    static boolean funcionsArrays(){
        indexsInternsArrayResultat=new int[colors.length][2];
        arr_matriusIndexs0=new ArrayList<int[][]>();arr_matriusIndexs1=new ArrayList<int[][]>();
        int[][] t=new int[0][0];for(int k=0;k<colors.length;k++){arr_matriusIndexs0.add(t);arr_matriusIndexs1.add(t);}
        String[] simbolsArrReduit=splitPan.faBuitSimbolsrepetits(simbolsArr);
        String[] iD=new String[0];String[] sD=new String[0];String[] iI=new String[0];String[] sI=new String[0];
        for(int i=0;i<colors.length;i++){
            iD=splitPan.matriudeFuncioCompleta(idxdef[i]);sD=splitPan.matriudeFuncioCompleta(simdef[i]);
            iI=splitPan.matriudeFuncioCompleta(idxIndef[i]);sI=splitPan.matriudeFuncioCompleta(simIndef[i]);
            int[][] itD=new int[iD.length][2];
            int[][] itI=new int[iI.length][2];
            for(int j=0;j<iD.length;j++)itD[j][1]=Integer.parseInt(iD[j]);
            for(int k=0;k<iD.length;k++){
                boolean b=false;
                for(int j=0;j<simbolsArr.length;j++)if(sD[k].equals(simbolsArr[j])&&i!=j&&indexsderesultatsaMatriu[j][2]>=0){
                    if(indexsderesultatsaMatriu[j][0]<0){Func.append(1,"error: ");Func.append(0," la funci"+Func.rB.getString("o_")+" de la fila: "+i+" cont"+Func.rB.getString("e_")+" el s"+Func.rB.getString("i_")+"mbol: "+sD[k]+"["+iD[k]+"] que fa referencia a un array que no esta definit correctament"+matriu[i+1][1]+splitPan.FIL);return false;}
                    itD[k][0]=indexsderesultatsaMatriu[j][0];j=simbolsArr.length;b=true;
                }
                if(!b){Func.append(1,"error: ");Func.append(0," fila: "+i+"; els s"+Func.rB.getString("i_")+"mbols de arrays "+sD[k]+"["+iD[k]+"] s'han de definir en la columna 1"+ splitPan.FIL);return false;}
            }
            boolean b=false;
            for(int k=0;k<iI.length;k++){
                for(int j=0;j<simbolsReduits.length;j++)if(iI[k].equals(simbolsReduits[j])){itI[k][1]=j;b=true;j=simbolsReduits.length;}
                if(!b){Func.append(1,"error: ");Func.append(0," fila: "+i+"; indexs no num"+Func.rB.getString("_e")+"rics de s"+Func.rB.getString("i_")+"mbols de matrius: "+sI[k]+"["+iI[k]+"] s'han d'haver definit a columna 1 o a s"+Func.rB.getString("i_")+"mbols redu"+Func.rB.getString("i")+"ts columnes 1 o 3"+ splitPan.FIL);return false;}
            } 
            for(int k=0;k<sI.length;k++){
                b=false;
                for(int l=0;l<simbolsArr.length;l++)if(sI[k].equals(simbolsArr[l])&&i!=l&&indexsderesultatsaMatriu[l][2]>=0){
                    if(indexsderesultatsaMatriu[l][0]<0){Func.append(1,"error: ");Func.append(0," la funci"+Func.rB.getString("o_")+" de la fila: "+i+" cont"+Func.rB.getString("e_")+" el s"+Func.rB.getString("i_")+"mbol: "+sI[k]+"["+iI[k]+"] que fa refer"+Func.rB.getString("_e")+"ncia a un array amb index, que no esta definit previament, "+matriu[i+1][1]+splitPan.FIL);return false;}
                    itI[k][0]=indexsderesultatsaMatriu[l][0];l=simbolsArr.length;b=true;}//possicio del simbol de matriu a columna 1
                if(!b){Func.append(1,"error: ");Func.append(0," fila: "+i+"; els s"+Func.rB.getString("i_")+"mbols de arrays "+sI[k]+"["+iI[k]+"] s'han de definir previament a la columna 1"+ splitPan.FIL);return false;}
            }
            String iIndef="";String sIndef="";String sdef="";
            if(simbolsT[i].endsWith("]")){
                int ini=simbolsT[i].indexOf("[");
                if(ini<1){Func.append(1,"error: ");Func.append(0," els s"+Func.rB.getString("i_")+"mbols '[' i ']' nom"+Func.rB.getString("e_")+"s s'utilitzen per tractar fileres de arrays de manera individual, fan refer"+Func.rB.getString("_e")+"ncia al index d'un array ex: simbo[simol1] indica la filera  = valor de simbol1, de la matriu s"+Func.rB.getString("i_")+"mbol ; fila: "+i+" simbol(resultat): "+matriu[i+1][1]+splitPan.FIL);return false;}
                String n=simbolsT[i].substring(ini+1,simbolsT[i].length()-1);
                if(n.equals("")){Func.append(1,"error: ");Func.append(0," els s"+Func.rB.getString("i_")+"mbols '[' i ']' nom"+Func.rB.getString("e_")+"s s'utilitzen per tractar fileres de arrays de manera individual i ha de contenir un valor num"+Func.rB.getString("_e")+"ric o un s"+Func.rB.getString("i_")+"mbol, no pot estar buit com a: "+matriu[i+1][1]+"; simbo[simol1] indica la filera  = valor de simbol1, de la matriu simbol(resultat); fila: "+i+splitPan.FIL);return false;}
                try{int it=Integer.parseInt(n);indexsInternsArrayBol[i][3]=true;indexsInternsArrayResultat[i][1]=it;sdef=simbolsT[i].substring(0,ini);}
                catch(Exception e){iIndef=n;indexsInternsArrayBol[i][2]=true;sIndef=simbolsT[i].substring(0,ini);}
                if(indexsInternsArrayBol[i][2]){
                    b=false;
                    for(int j=0;j<simbolsReduits.length;j++)if(iIndef.equals(simbolsReduits[j])){indexsInternsArrayResultat[i][1]=j;b=true;j=simbolsReduits.length;}
                    if(!b){Func.append(1,"error: ");Func.append(0,"a "+matriu[0][1]+" fila: "+i+"; indexs de matrius han d'esser un sencer; indexs no num"+Func.rB.getString("_e")+"rics de matrius; s'han d'haver definit previament a columna 1 o 3: "+sIndef+"["+iIndef+"]"+ splitPan.FIL);return false;}
                    for(int l=0;l<simbolsArr.length;l++)if(sIndef.equals(simbolsArr[l])&&i!=l&&indexsderesultatsaMatriu[l][2]>=0){indexsInternsArrayResultat[i][0]=l;l=simbolsArr.length;b=true;}//possicio del simbol de matriu a columna 1
                    if(!b){Func.append(1,"error: ");Func.append(0," a "+matriu[0][1]+" fila: "+i+";  indexs de matrius han d'esser un sencer; s"+Func.rB.getString("i_")+"mbols de arrays amb indexs no num"+Func.rB.getString("_e")+"rics; s'han de definir previament en la columna 1: "+sIndef+"["+iIndef+"]"+ splitPan.FIL);return false;}
                }
                else{
                    b=false;
                    for(int j=0;j<simbolsArr.length;j++)if(sdef.equals(simbolsArr[j])&&i!=j&&indexsderesultatsaMatriu[j][2]>=0){indexsInternsArrayResultat[i][0]=j;j=simbolsArr.length;b=true;}
                    if(!b){Func.append(1,"error: ");Func.append(0," a "+matriu[0][1]+" fila: "+i+"; els s"+Func.rB.getString("i_")+"mbols de arrays amb indexs num"+Func.rB.getString("_e")+"rics: "+sdef+"; s'han de definir previament en la columna 1"+ splitPan.FIL);return false;}
                }
            }
             String[] sv=new String[limdVO[i][1]];System.arraycopy(sVO, limdVO[i][0], sv, 0,sv.length);
            double[] dv=new double[limdVO[i][1]];System.arraycopy(dVO, limdVO[i][0], dv, 0,dv.length);
            String c="";
            int arrays=0,interv=0;
            for(int k=0;k<simbolsArrReduit.length;k++)if(!simbolsArrReduit[k].equals("")&&!resultatUnic[k]){
                for(int j=0;j<sv.length;j++)if(sv[j].equals(simbolsArrReduit[k])||sv[j].equals("-"+simbolsArrReduit[k])){arrays=indexsderesultatsaMatriu[k][2];interv=(sv.length+2)/arrays;j=sv.length;k=simbolsArrReduit.length;}
            }
            for(int k=0;k<simbolsArrReduit.length;k++)if(!simbolsArrReduit[k].equals(""))for(int j=0;j<interv;j++)if((!resultatUnic[k])&&i!=k){
                if(sv[j].equals(simbolsArrReduit[k]))for(int l=0;l<arrays;l++){c+=(j+interv*l)+" "+indexsderesultatsaMatriu[k][0]+" ";}
                else if(sv[j].equals("-"+simbolsArrReduit[k]))for(int l=0;l<arrays;l++){c+=(-j-1-interv*l)+" "+indexsderesultatsaMatriu[k][0]+" ";}
            }
            if(c.length()>0)arr_matriusIndexs.set(i,splitPan.matriudeFuncioInt2(c));
            c="";
            if(indexsInternsArrayBol[i][0]){
                for(int m=0;m<itI.length;m++){
                    String simbol=simbolsArr[itI[m][0]]+"["+simbolsReduits[itI[m][1]]+"]";
                    for(int j=0;j<sv.length;j++){
                        if(sv[j].equals(simbol))c+=j+" "+itI[m][0]+" "+itI[m][1]+" ";
                        else if(sv[j].equals("-"+simbol))c+=(-j-1)+" "+itI[m][0]+" "+itI[m][1]+" ";
                    }//[][0]possicio a dvo  [][1]posicio de la matriu [][2]fila valor del index
                    if(c.length()>0)arr_matriusIndexs0.set(i,splitPan.matriudeFuncioInt3(c));
                }
            }
            c="";
            if(indexsInternsArrayBol[i][1]){
                for(int m=0;m<itD.length;m++){
                    String simbol=simbolsArr[itD[m][0]]+"["+itD[m][1]+"]";
                    for(int j=0;j<sv.length;j++){
                        if(sv[j].equals(simbol))c+=j+" "+itD[m][0]+" "+itD[m][1]+" ";
                        else if(sv[j].equals("-"+simbol))c+=(-j-1)+" "+itD[m][0]+" "+itD[m][1]+" ";
                    }//[][0]possicio a dvo  [][1]posicio de la matriu [][2]valor del index
                    if(c.length()>0)arr_matriusIndexs1.set(i,splitPan.matriudeFuncioInt3(c));
                }
            }
        }
        //System.out.println("la primera columna indica que la funcio de la filera conte almenys un index no numeric, la segona numeric i la tercerea i quarta el mateix pel resultat de la columna 1"); System.out.println("la segona numeric: indexsInternsArrayBol:");for(int j=0;j< indexsInternsArrayBol.length;j++)System.out.print(indexsInternsArrayBol[j][0]+" "+indexsInternsArrayBol[j][1]+" "+indexsInternsArrayBol[j][2]+" "+indexsInternsArrayBol[j][3]+" _ ");System.out.println();System.out.println();
        //System.out.println("per cada filera indica el valor del index ja definit en la funciol  i les fileres de la matriu array associades que intervenent en la funcio"); System.out.println("arr_indexsInternsDefinitsArrayaFuncio:");for(int i=0;i<colors.length;i++){int[][] ti=arr_indexsInternsDefinitsArrayaFuncio.get(i);System.out.print("fila: "+i+"> ");for(int j=0;j<ti.length;j++)System.out.print(ti[j][0]+" "+ti[j][1]+"  ");System.out.print(" _ ");}System.out.println();System.out.println();
        //System.out.println("la primera columna indica l'index (possicio del simbol[sencer] a Cal.dvo la segona i tercera columnes la segona i tercera columnes relacionades amb les anteriors"); System.out.println("arr_matriusIndexs1:");for(int i=0;i<colors.length;i++){int[][] ti=arr_matriusIndexs1.get(i);System.out.print("fila: "+i+"> ");for(int j=0;j<ti.length;j++)System.out.print(ti[j][0]+" "+ti[j][1]+" "+ti[j][2]+"  ");System.out.print(" _ ");}System.out.println();System.out.println();
        //System.out.println("per cada filera indica el contador associat al index i les fileres de la matriu array associades que intervenent en la funcio"); System.out.println("indexsInternsArrayaFuncio:");for(int i=0;i<colors.length;i++){int[][] ti=arr_indexsInternsArrayaFuncio.get(i);System.out.print("fila: "+i+"> ");for(int j=0;j<ti.length;j++)System.out.print(ti[j][0]+" "+ti[j][1]+"  ");System.out.print(" _ ");}System.out.println();System.out.println();
        //System.out.println("la primera columna indica l'index (possicio del simbol[simbol1] a Cal.dvo la segona i tercera columnes relacionades amb les anteriors"); System.out.println("arr_matriusIndexs0:");for(int i=0;i<colors.length;i++){int[][] ti=arr_matriusIndexs0.get(i);System.out.print("fila: "+i+"> ");for(int j=0;j<ti.length;j++)System.out.print(ti[j][0]+" "+ti[j][1]+" "+ti[j][2]+"  ");System.out.print(" _ ");}System.out.println();System.out.println();
        //System.out.println("la primera columna indica la filera que conte el array del resultat i la segona la possicio a la matriu o l'index de simbolsReduits que conte el valor de l'index"); System.out.println("la segona numeric: indexsInternsArrayResultat:");for(int j=0;j< indexsInternsArrayBol.length;j++)System.out.print(indexsInternsArrayResultat[j][0]+" "+indexsInternsArrayResultat[j][1]+" _ ");System.out.println();System.out.println();
        int[][] a=new int[0][0];boolean[] b0=new boolean[0];
        simbolsReduitsArrayIF=new boolean[colors.length][3];//columna if [][0]hi ha simbols arrays d'algun tipus tots [1]  indexsIndefinits  [2] indexs definits; 
        simbolsReduitsArrayThen=new boolean[colors.length][7];//columna then [][0] hi ha simbols arrays d'algun tipus i [1] indexsIndefinits origen copia  [2]if indexs definits origen copia; [4][5] i [6] el mateix desti copia; [3] indica que es copia de matriu a matriu o de un valor  a matriu
        simbolsReduitsArrayThenReturn=new boolean[colors.length][7];//identic al superior situat en la mateixa filera pero referit al return del call particular
        arr_iThenPosSimArr=new ArrayList<>();//hi ha matrius a les copies
        arr_iThenPosSimArrReturn=new ArrayList<>();
        arr_IFsimbolsReduitsArrayIndexs0= new ArrayList<>();
        arr_IFsimbolsReduitsArrayIndexs1= new ArrayList<>();
        arr_THENsimbolsReduistArray= new ArrayList<>();
        arr_THENsimbolsReduistArrayReturn= new ArrayList<>();
        arr_THENsimbolsReduitsArrayIndexs0= new ArrayList<>();
        arr_THENsimbolsReduitsArrayIndexs1= new ArrayList<>();
        arr_THEN_destisimbolsReduitsArrayIndexs0= new ArrayList<>();
        arr_THEN_destisimbolsReduitsArrayIndexs1= new ArrayList<>();
        arr_THENsimbolsReduitsArrayIndexs0Return= new ArrayList<>();
        arr_THENsimbolsReduitsArrayIndexs1Return= new ArrayList<>();
        arr_THEN_destisimbolsReduitsArrayIndexs0Return= new ArrayList<>();
        arr_THEN_destisimbolsReduitsArrayIndexs1Return= new ArrayList<>();
        for(int i=0;i<colors.length;i++){
            arr_iThenPosSimArr.add(new boolean[0][0]);
            arr_iThenPosSimArrReturn.add(new boolean[0][0]);
            arr_IFsimbolsReduitsArrayIndexs0.add(a);
            arr_IFsimbolsReduitsArrayIndexs1.add(a);
            arr_THENsimbolsReduistArray.add(a);
            arr_THENsimbolsReduistArrayReturn.add(a);
            arr_THENsimbolsReduitsArrayIndexs0.add(a);
            arr_THENsimbolsReduitsArrayIndexs1.add(a);
            arr_THEN_destisimbolsReduitsArrayIndexs0.add(a);
            arr_THEN_destisimbolsReduitsArrayIndexs1.add(a);
            arr_THENsimbolsReduitsArrayIndexs0Return.add(a);
            arr_THENsimbolsReduitsArrayIndexs1Return.add(a);
            arr_THEN_destisimbolsReduitsArrayIndexs0Return.add(a);
            arr_THEN_destisimbolsReduitsArrayIndexs1Return.add(a);
        }
        if(!indexsArraysTFThen())return false;
        return true;
    }
    static boolean colorsColors(){
        boolean[] pbol =new boolean[matriu.length-1];
        boolean bo=false;
        System.arraycopy(puntsBol, 0, pbol, 0, puntsBol.length);
        int p=0;
        boolean[] pb=new boolean[3];
        cont=new int[3];
        cont_ =new int[3];
        for(int i=0;i<matriu.length-1;i++){//columna eixos del grafic
            if(matriu[i+1][idxCol].equals(""))colors[i]=Color.BLACK;
            if(!eixos[i][0].equals("")&&!eixos[i][0].equalsIgnoreCase("x")&&!eixos[i][0].equalsIgnoreCase("y")&&!eixos[i][0].equalsIgnoreCase("z")){
                Func.append(1,"info: ");
                Func.append(0,"a taula de funcions superior els s"+Func.rB.getString("i_")+"mbols dels eixos del gr"+Func.rB.getString("a")+"fic han de començar per: x,y o z"+ splitPan.FIL);
                bo=true;
                i=matriu.length;
            }
            else{
                if(!resultatUnic[i]){
                    int j=indexsderesultatsaMatriu[i][2];
                    if(eixos[i][0].equalsIgnoreCase("x")){pb[0]=true;cont[0]+=j;cont_[0]++;}
                    if(eixos[i][0].equalsIgnoreCase("y")){pb[1]=true;cont[1]+=j;cont_[1]++;}
                    if(eixos[i][0].equalsIgnoreCase("z")){pb[2]=true;cont[2]+=j;cont_[2]++;}
                }
                else{
                    if(eixos[i][0].equalsIgnoreCase("x")){pb[0]=true;cont[0]++;cont_[0]++;}
                    if(eixos[i][0].equalsIgnoreCase("y")){pb[1]=true;cont[1]++;cont_[1]++;}
                    if(eixos[i][0].equalsIgnoreCase("z")){pb[2]=true;cont[2]++;cont_[2]++;}
                }
            }
        }
        if(bo)return false;
        if(pb[2]&&(!pb[0]||!pb[1])){
            Func.append(1,"info: ");Func.append(0,"a taula de funcions superior quant nomes hi ha menys de tres coordenades els s"+Func.rB.getString("i_")+"mbols dels eixos del gr"+Func.rB.getString("a")+"fic han de començar per: x io y;"+ splitPan.FIL);
            return false;
        }
        String s="a taula de funcions superior als s"+Func.rB.getString("i_")+"mbols dels eixos del gr"+Func.rB.getString("a")+"fic, hi ha d'haber el mateix nombre de coordenades x,y,z"+ splitPan.FIL;
        for(int i=0;i<3;i++)if(pb[i])p++;
        bo=false;
        if(p==3&&(cont[0]!=cont[1]||cont[1]!=cont[2])){
            Func.append(1,"info: "); Func.append(0,s);Func.append(0,"comprobar eixos x:"+cont[0]+" y:"+cont[1]+" z:"+cont[2]+ splitPan.FIL);
            bo=true;
            }
        else if((pb[0]&&pb[1])&&(cont[0]!=cont[1])){Func.append(1,"info: "); Func.append(0,s);Func.append(0,"comprobar eixos x:"+cont[0]+" y:"+cont[1]+ splitPan.FIL);
            bo=true;
            }
        else if((pb[0]&&pb[2])&&(cont[0]!=cont[2])){
            Func.append(1,"info: "); Func.append(0,s);Func.append(0,"comprobar eixos x:"+cont[0]+" z:"+cont[2]+ splitPan.FIL);
            bo=true;
            }
        else if((pb[1]&&pb[2])&&(cont[0]!=cont[1])){
            Func.append(1,"info: "); Func.append(0,s);Func.append(0,"comprobar eixos y:"+cont[1]+" z:"+cont[2]+ splitPan.FIL);
            bo=true;
            }
        if(bo)return false;
        int co=cont[0];
        if(cont[1]>co)co=cont[1];
        if(cont[2]>co)co=cont[2];//co es el nombre de fileres que començent per x y o z) les que n'hi ha mes
        if(p>0){
            puntsGraf=new double[co][3];
            graficF.punts=new double[co][3];
            graficF.puntsTFColor=new Object[co][2];
            graficF.igrandariaPunt=new int[co][2];
            graficF.historia=new int[co][graficF.iHistorial][2];
            graficF.historiagrandariaPunt=new int[co][graficF.iHistorial];
            graficF.historiaBol=new boolean[co][graficF.iHistorial];
            graficF.tempsHistoria=new Instant[co];
            graficF.idxHistoria=new int[co];
            graficF.historiaIntervalTemps=new int[co];
            graficF.ihistorial=new int[co];
            puntsTFColor=new Object[co][2];
            grandariaPunt=new int[co][3];
            igrandariaPunt=new int[co][2];
            historia=new int[co][graficF.iHistorial][2];
            historiaBol=new boolean[co][graficF.iHistorial];
            tempsHistoria=new Instant[co];
            idxHistoria=new int[co];
            historiaIntervalTemps=new int[co];
            ihistorial=new int[co];
            //puntsGrafActiusBol=new boolean[co];
        }
        else{
            puntsGraf=new double[0][0];
            graficF.punts=new double[0][0];
            graficF.puntsTFColor=new Object[0][0];
            graficF.igrandariaPunt=new int[0][0];
            graficF.historia=new int[0][0][0];
            graficF.historiagrandariaPunt=new int[0][0];
            graficF.historiaBol=new boolean[0][0];
            graficF.historiaIntervalTemps=new int[0];
            graficF.tempsHistoria=new Instant[0];
            graficF.idxHistoria=new int[0];
            graficF.ihistorial=new int[0];
            
            puntsTFColor=new Object[0][0];
            grandariaPunt=new int[0][0];
            igrandariaPunt=new int[0][0];
            historia=new int[0][0][0];
            historiaBol=new boolean[0][0];
            tempsHistoria=new Instant[0];
            idxHistoria=new int[0];
            historiaIntervalTemps=new int[0];
            ihistorial=new int[0];
        }
        co=cont_[0];
        if(cont_[1]>co)co=cont_[1];
        if(cont_[2]>co)co=cont_[2];
        if(p>0){
            puntsTFBol=new boolean[co][6];
            puntsTFGraf=new int[co][3];
        }
        else{
            puntsTFBol=new boolean[0][0];
            puntsTFGraf=new int[0][0];
        }
        cont=new int[3];
        cont_=new int[3];
        bo=false;
        boolean b=false;
        if(p>0){
            int x=0;
            int l=matriu.length;
            String pun="";
            for(int i=0;i<matriu.length-1;i++){//Selecciona la primera filera que te x y o z    
                if(eixos[i][0].equals("x")){x=0;pun="x";l=i;i=matriu.length;}
                else if(eixos[i][0].equals("y")){x=1;pun="y";l=i;i=matriu.length;}
                else if(eixos[i][0].equals("z")){x=2;pun="z";l=i;i=matriu.length;}
            }
            String pu[]={"x","y","z"};
            for(int i=l;i<matriu.length-1;i++){
                if(puntsBol[i]&&eixos[i][0].equals(pun)){//per cada conjunt de simbols de eixos[i][1]lel primer calcul es fa per la variable que s'ha trobat primer
                    b=punt(i,x,eixos[i][1]);
                    if(!b)bo=true;
                    else for(int eix=0;eix<3;eix++)if(pb[eix]&&eix!=x)for(int j=l;j<matriu.length-1;j++){
                        if(puntsBol[j]&&eixos[j][1].equals(eixos[i][1])&&eixos[j][0].equals(pu[eix])){b=punt(j,eix,eixos[i][1]);j=matriu.length;}
                        if(!b){bo=true;i=matriu.length;j=matriu.length;eix=3;}
                    }
                }
            }
            for(int i=0;i<puntsTFBol.length;i++)if(!puntsTFBol[i][4]){grandariaPunt[i][0]=20;grandariaPunt[i][1]=grandariaPunt[i][0];}           
            if(bo){
                Func.append(1,"info: ");Func.append(0,"a taula de funcions superior els s"+Func.rB.getString("i_")+"mbols dels eixos del gr"+Func.rB.getString("a")+"fic son x,y,z; quant hi ha mes d'un punt grafic cal escriure a continuaci"+Func.rB.getString("o_")+" una mateixa cadena per les coordenades referides a un mateix punt ex: punt 1:(x1,y1,z1), punt primer:(xprimer,yprimer,zprimer)");
                return false;
            }
        }
        System.arraycopy(pbol, 0, puntsBol, 0, puntsBol.length);
        //System.out.println("puntsTFColor");for(int i=0;i<puntsTFColor.length;i++)for(int j=0;j<puntsTFColor[0].length;j++){System.out.print(puntsTFColor[i][j]+" ");}System.out.println();System.out.println();
        //System.out.println("puntsTFBol: les tres primeres posicion indiquen quines cordenades xyz, i a la cuarta si hi ha color :  ");for(int i=0;i<puntsTFBol.length;i++){for(int j=0;j<puntsTFBol[0].length;j++)System.out.print(i+" "+puntsTFBol[i][j]+"  ");System.out.println();}System.out.println();
        //System.out.println("puntsTFGraf.lenfth:   "+puntsTFGraf.length);
        //System.out.println("puntsTFGraf: fleres de la taulaF asociades als punts :  ");for(int i=0;i<puntsTFGraf.length;i++){for(int j=0;j<puntsTFGraf[0].length;j++)System.out.print(i+" "+puntsTFGraf[i][j]+"  ");System.out.println();}System.out.println();
        //System.out.println("puntsBol si[i]=true indica que la filera implica presentacio grafica");for(int i=0;i<puntsBol.length;i++)System.out.print(i+" "+puntsBol[i]+" _ ");System.out.println();System.out.println();
        //System.out.println("eixos: String que conte per cada filera la cordenada x y o z (columna 0) i el nombre del punt blanc,0,1,2,3,4 etc (columna1): ");for(int i=0;i<eixos.length;i++){for(int j=0;j<eixos[0].length;j++)System.out.print(eixos[i][j]+" ");System.out.print(" __ ");}System.out.println();
            
        return true;
    }
        //calcul les indexs associats a operacions numeriques(operacions indexs dels simbols, cadenes dVO sVO, i limits
    public static void valorsPrevis(int fila){//s'utilitza a splitPan.cadenes_sVO_deFuncions()
        Cal.longi=longi[fila];
        Cal.longiP=longiP[fila];
        Cal.idxVarGen=arr_idxVarGen.get(fila);
        taulaV.varstaulaV=arr_varstaulaV.get(fila);
        if(taulaP.hihaParVar){
            Cal.longiPBol=arr_longiPBol.get(fila);
            taulaP.varPTV_varPTP=arr_varPTV_varPTP.get(fila);
        }
        if(taulaD.hihaDades){
            if(varGenDerBol[fila][0]||varGenSumBol[fila][0])taulaD.idxVarDer=arr_idxVarDer.get(fila);
            taulaD.idxvarsdeFunDer=arr_idxvarsdeFunDer.get(fila);
            taulaD.VGaFuncBol=arr_VGaFuncBol_taulaD.get(fila);
        }
        if(taulaI.hihaDades){//if(varGenSumBol[fila][0]){
                suportID.idxVarGenIntegr=arr_idxVarGenIntegr.get(fila);
                suportID.indexsVGaI=arr_indexsVGaI.get(fila);
                System.arraycopy(sumambFPfilataulaF[fila], 0, sumambFPfilataulaFunaFila, 0, sumambFPfilataulaF[0].length);
        }
    }
    static void arraysPosteriors(int fil){//s'utilitza a splitPan.cadenes_sVO_deFuncions()
        arr_indexs.set(fil,Cal.indexs);
        if(taulaV.hihaVar){
            arr_indexsVar.set(fil,Cal.indexsVar);
            arr_indexsVarG.set(fil,Cal.indexsVarG);
            arr_limitindexsVarGen.set(fil,Cal.limitindexsVarGen);
            
            if(taulaP.hihaParVar){
                arr_indexsParv.set(fil,Cal.indexsParv);
                arr_indexsParV.set(fil,Cal.indexsParV);
                arr_indexsParVG.set(fil,Cal.indexsParVG);
            }
        }
        if(taulaP.simParCapNoVar.length>0)arr_indexsParNoVar.set(fil,Cal.indexsParNoVar);
        if(taulaI.hihaDades){
            tI_VG_integralBol[fil]=Cal.integralBol;
            if(varNgenSumBol[fil])arr_indexsSumafuncioF.set(fil,indexsSumafuncioF);
            if(varGenSumBol[fil][0])arr_indexsVGI.set(fil,Cal.indexsVGI);
        }
        if(taulaD.hihaDades){
            tD_VG_derivadaBol[fil]=Cal.derivadaBol;//si true vol dir que directa o indirectament mitjançant sumatoris hi ha funcions parcials no constants que afecten la funcio
            if(varNgenDerBol[fil])arr_indexsDerafuncioF.set(fil,indexsDerafuncioF);
            if(varGenDerBol[fil][0]) arr_indexsVGD.set(fil,Cal.indexsVGD);
        }
    }
    public static void valorsPosteriors(){//s'utilitza a splitPan.cadenes_sVO_deFuncions()
        int i;
        int contadorVarP=0;
        int[][] indexsVGS;
        cont=new int[1];
        cont[0]=0;
        for (i=0;i<simbolsT.length;i++)if(!simbolsTNombreBol[i])for(int j=0;j<sVO.length;j++)if(sVO[j].equals(simbolsT[i])||sVO[j].equals("-"+simbolsT[i]))cont[0]++;
        indexsSim=new int[cont[0]];
        limindexsSim=new int[simbolsT.length][2];
        limindexsSim[0][0]=0;
        cont[0]=0;
        for (i=0;i<simbolsT.length;i++){
            if(!simbolsTNombreBol[i]){
                for(int j=0;j<sVO.length;j++){
                    if(sVO[j].equals(simbolsT[i])){indexsSim[cont[0]]=j;cont[0]++;}
                    else if(sVO[j].equals("-"+simbolsT[i])){indexsSim[cont[0]]=-j-1;cont[0]++;}
                }
            }
            limindexsSim[i][1]=cont[0];
        }
        for(i=0;i<limindexsSim.length-1;i++)limindexsSim[i+1][0]=limindexsSim[i][1];
        for(fila= 0;fila<taulaF.colors.length;fila++)if(varParFuncExtesaBol[fila][0]) contadorVarP+=arr_indexsVar.get(fila).length;
        if(taulaV.hihaVar){
            int conta=0;
            indexsVGS=new int[contadorVarP][2];//inclou els indexs de totes les variables
            for(fila=0;fila<taulaF.colors.length;fila++){
                if(varBol[fila][0]){//hi ha variables directes en la funcio   quant s'executa minim de funcio despres de la execucio cal modificar les fileres amb variables que no son cerca de minim
                    int[] idx=arr_indexsVarModificats0.get(fila);
                    int[][] it =arr_indexsVar.get(fila);
                    if(!varGenBol[fila][0])for(i=0;i<it.length;i++){//nomes hi ha  paramtres variables i no hi ha variables generals directes o indirectes
                        indexsVGS[conta][0]=it[i][0]+limdVO[fila][0];
                        indexsVGS[conta][1]=idx[it[i][1]];
                        conta++;
                    }
                    else {
                        indexsVaraSim=new int[it.length][2];//contindra
                        for(i=0;i<it.length;i++){
                            indexsVaraSim[i][0]=it[i][0]+limdVO[fila][0];
                            indexsVaraSim[i][1]=idx[it[i][1]];
                        }
                        arr_indexsVaraSim.set(fila,indexsVaraSim);//per cada filera amb varGen sense cerca de minim tenim la matriu de indexs referits a taulaV i dVO
                    }
                }
            }
            conta=0;
            for(fila= 0;fila<taulaF.colors.length;fila++){
                conta=0;
                if(!simbolsDefinits[fila]){//hi ha variable directe o indirecte i es cerca de minim en aquest cas no s'incorporen el valors de les variables pero des`es del calcul caldra incorporar els valors dels parametres variable on es trobin
                    int[][] it =arr_indexsVaraSim.get(fila);
                    for(int j=0;j<indexsVGS.length;j++){
                        for(i=0;i<it.length;i++)if(indexsVGS[j][1]==it[i][1]){conta++;}//b=true;}
                    }
                    indexsVaraSim=new int[conta][2];
                    conta=0;
                    for(int j=0;j<indexsVGS.length;j++)for(i=0;i<it.length;i++)if(indexsVGS[j][1]==it[i][1]){
                        indexsVaraSim[conta][0]=indexsVGS[j][0];
                        indexsVaraSim[conta][1]=indexsVGS[j][1];
                        conta++;
                    } 
                    arr_indexsVaraSim.set(fila,indexsVaraSim);
                 }
            }
        }
    }
    static void carrega_Arr_Fila(int fil){//utilitzat pel calcul de cada filera especifica
        Cal.longi=longi[fil];
        Cal.cursor=new long[longi[fil]];
        Cal.cursorMinim=new long[longi[fil]];
        Cal.varAct=new double[longi[fil]];
        varAct=new double[longi[fil]];
        Cal.varCopia=new double[longi[fil]];
        Cal.pondIncrVar=new double[longi[fil]];
        Cal.limSupCalc=new int[longi[fil]];
        Cal.minIncrSignif=new long[longi[fil]];//for(int i=0;i<Cal.minIncrSignif.length;i++)Cal.minIncrSignif[i]=1;
        Cal.limSCalc=0;
        Cal.longiP=longiP[fil];
        Cal.resultatM=new double[longiP[fil]];
        Cal.resultatMcop=new double[Cal.resultatM.length];
        if(taulaP.hihaParVar){
            Cal.idxParVar=arr_idxParVar.get(fil);// es carrega a arraysPrevis
            Cal.longiPBol=arr_longiPBol.get(fil);// es carrega a arraysPrevis
            Cal.indexsParv=arr_indexsParv.get(fil);
            Cal.indexsParV=arr_indexsParV.get(fil);
            Cal.indexsParVG=arr_indexsParVG.get(fil);
            Cal.resultatP=new double[Cal.idxParVar.length];
        }
        if(taulaP.simParCapNoVar.length>0)Cal.indexsParNoVar=arr_indexsParNoVar.get(fil);
        taulaV.varstaulaV=arr_varstaulaV.get(fil);
        Cal.dMatriuVar=arr_dVars.get(fil);
        Cal.segCero=arr_segCero.get(fil);
        Cal.escalaLinial=escalaLinial[fil];
        taulaC.ceroLog=ceroLog[fil];
        taulaC.digitsValids=digitsValids[fil];
        Func.noArrodonir=noArrodonir[fil];
        
        Cal.idxVarGen=arr_idxVarGen.get(fil);
        Cal.vargOparv=arr_vargOparv.get(fil);
        Cal.idxresultatM=arr_idxresultatM.get(fil);
        Cal.indexsVar=arr_indexsVar.get(fil);
        Cal.indexsVarG=arr_indexsVarG.get(fil);
        Cal.limitindexsVarGen=arr_limitindexsVarGen.get(fil);//si
        
        if(taulaD.hihaDades){
            taulaD.idxVarDer=arr_idxVarDer.get(fil);
            Cal.derivadaBol=tD_VG_derivadaBol[fil];
            if(varGenDerBol[fil][0]){
                Cal.indexsVGD=arr_indexsVGD.get(fil);//si la funcio parcial conte variables generals es true sino false; la primera columna indica si algun simbol de funcioParcial es troba en la funcio de la filera de la taulaF; la resta indica si es troba la funcioParcial de la filera-1
                taulaD.hihaVGioFP=arr_hihaVGioFPaD.get(fil);
                taulaD.indexsVGaD=arr_indexsVGaD.get(fil);
                taulaD.derivadesLimFun=arr_derivadesLimFun.get(fil);
                for(int j=1;j<varGenDerBol[fil].length;j++)if(varGenDerBol[fil][j]&&taulaD.ordreDer[j-1]>0){
                    Cal.supID.estimarIncrement(j-1);
                }
                taulaD.VGaFuncBol=arr_VGaFuncBol_taulaD.get(fil);
            }
            //System.out.println();System.out.println("parcial Cal.dMatriuVar: fila: "+fil);for(int i=0;i<Cal.dMatriuVar.length;i++){for(int j=0;j<Cal.dMatriuVar[0].length;j++){System.out.print(Cal.dMatriuVar[i][j]+" _ ");}System.out.println();}
            //System.out.print("sVO: ");for(int i=0;i<Cal.idxVarGen.length;i++)System.out.print(Cal.idxVarGen[i]+" _ ");System.out.println();
            //System.out.print("variables generals taulaD.idxVarGen: ");for(int i=0;i<Cal.idxVarGen.length;i++)System.out.print(Cal.idxVarGen[i]+" _ ");System.out.println();
            //System.out.println("Cal.derivadaBol: "+Cal.derivadaBol);
            //System.out.print("variables derivables taulaD.idxVarDer: ");for(int i=0;i<taulaD.idxVarDer.length;i++)System.out.print(taulaD.idxVarDer[i]+" _ ");System.out.println();
            //System.out.print("taulaD.hihaVGioFP: ");for(int i=0;i<taulaD.hihaVGioFP.length;i++)System.out.print(taulaD.hihaVGioFP[i]+" _ ");System.out.println();
            //System.out.print("varGenDerBol: ");for(int i=0;i<varGenDerBol[0].length;i++)System.out.print(varGenDerBol[fil][i]+" _ ");System.out.println();
            //System.out.print("taulaD.derivadesLimFun: ");for(int i=0;i<taulaD.derivadesLimFun.length;i++)System.out.print(taulaD.derivadesLimFun[i][4]+" "+taulaD.derivadesLimFun[i][5]+" _ ");System.out.println();
            //System.out.print("arr_indexsVGaD.taulaD indexsVGaD: fila: "+fil+"  ");for(int i=0;i<taulaD.indexsVGaD.length;i++)System.out.print(i+" "+taulaD.indexsVGaD[i][0]+" "+taulaD.indexsVGaD[i][1]+" _ ");System.out.println();
            for(int i=0;i<taulaD.simbol.length;i++){
                int i0=taulaD.derivadesLimFun[i][0];int i1=taulaD.derivadesLimFun[i][1]-taulaD.derivadesLimFun[i][0];
                String[]svo=new String[i1];System.arraycopy(taulaD.sVO,i0,svo,0,i1);
             }
        }
        if(taulaI.hihaDades){//Cal.limitindexsVarGen ja s'ha carregat per contenir variables generals
            Cal.integralBol=tI_VG_integralBol[fil];
            if(varGenSumBol[fil][0]){//en principi conte variables generals o funcions parcials amb variables generals
                Cal.indexsVGI=arr_indexsVGI.get(fil);//sumatoris amb variables generals que es troben a la funcio general el segon index es la posicio del sumatoris, el primer es la posicio del sumatori a sVO de la funcio principal, 
                suportID.idxVarGenIntegr=arr_idxVarGenIntegr.get(fil);//les variables generals presents a integrals
                suportID.indexsVGaI=arr_indexsVGaI.get(fil);//indexsVGaI  conte a: 0 la posicicio dins dVOI, a: 1 el index de la variable general
                Cal.indexsInteg_VG=arr_indexsInteg_VG.get(fil);
                suportID.hihaVGioFPaI=arr_hihaVGioFPaI.get(fil);
             }
            //System.out.println();System.out.println("fila: "+fil);
            //System.out.print("sumatoris amb variables generals i presents a la funcio de la filera  suportID.hihaFPaI: ");for(int i=0;i<suportID.hihaFPaI.length;i++)System.out.print(suportID.hihaFPaI[i]+" ");System.out.println();
            //System.out.print("idx de variables generals reduida suportID.idxVarGenIntegr: ");for(int i=0;i<suportID.idxVarGenIntegr.length;i++)System.out.print(suportID.idxVarGenIntegr[i]+" ");System.out.println();
            //System.out.print("indexs de vvg a dvoi integrals suportID.indexsVGaI: ");for(int i=0;i<suportID.indexsVGaI.length;i++)System.out.print(suportID.indexsVGaI[i][0]+" "+suportID.indexsVGaI[i][1]+" _ ");System.out.println();
            //System.out.print("indexs de sumatoris a filera taulaF Cal.indexsVGI: ");for(int i=0;i<Cal.indexsVGI.length;i++)System.out.print(Cal.indexsVGI[i][0]+" "+Cal.indexsVGI[i][1]+" _ ");System.out.println();System.out.println();
        }
        for(int i=0;i<Cal.longi;i++)Cal.cursorMinim[i]=Cal.segment/2;
        System.arraycopy(Cal.cursorMinim,0,Cal.cursor,0,Cal.longi);
        for(int j=0;j<Cal.longi;j++){
               Cal.varAct[j]=Cal.posic(j,Cal.cursor[j]);
               Cal.pondIncrVar[j]=1.0;
        }
        Cal.resultat=Cur.maxim;
        Cal.dVO=new double[limdVO[fil][1]];
        Cal.sVO=new String[limdVO[fil][1]];
        System.arraycopy(Cal.dvo, 0, Cal.dVO, 0, Cal.dvo.length);//dvo ha incorporat lels sumatoris en que no hi ha variables generals
        System.arraycopy(sVO, limdVO[fil][0], Cal.sVO, 0, limdVO[fil][1]);
        Cal.actualitzadVO(Cal.dvo, Cal.dVO,Cal.sVO,Cal.varAct,Cal.cursor,Cal.cursorMinim);                   
    }
    static void presentacarrega_Arr_Fila(){
        System.out.println();System.out.println();System.out.println("Cal.dMatriuVar.length: "+Cal.dMatriuVar.length+"    ");for(int i=0;i<Cal.dMatriuVar.length;i++){for(int j=0;j<Cal.dMatriuVar[0].length;j++)System.out.print(Cal.dMatriuVar[i][j]+" ");System.out.println();}System.out.println();
        if(taulaP.hihaParVar){
            System.out.println("Cal.resultatM.length: "+Cal.resultatM.length+" "+Cal.resultatMcop.length);
            System.out.println("Cal.longiP: "+Cal.longiP);
            System.out.print("Cal.idxParVar:    ");for(int i=0;i<Cal.idxParVar.length;i++)System.out.print(Cal.idxParVar[i]+" ");System.out.println();
            System.out.print("Cal.longiPBol:    ");for(int i=0;i<Cal.longiPBol.length;i++)System.out.print(Cal.longiPBol[i]+" ");System.out.println();
            System.out.print("Cal.indexsParv:    ");for(int i=0;i<Cal.indexsParv.length;i++)System.out.print(Cal.indexsParv[i][0]+" "+Cal.indexsParv[i][1]+" _ ");System.out.println();
            System.out.println();System.out.println("Cal.indexsParV:    ");for(int i=0;i<Cal.indexsParV.length;i++){for(int j=0;j<Cal.indexsParV[0].length;j++)System.out.print(Cal.indexsParV[i][j]+" ");System.out.println();}
            System.out.println();System.out.println("Cal.indexsParVG:    ");for(int i=0;i<Cal.indexsParVG.length;i++){for(int j=0;j<Cal.indexsParVG[0].length;j++)System.out.print(Cal.indexsParVG[i][j]+" ");System.out.println();}
            System.out.print("Cal.indexsParNoVar:    ");for(int i=0;i<Cal.indexsParNoVar.length;i++)System.out.print(Cal.indexsParNoVar[i][0]+" "+Cal.indexsParNoVar[i][1]+" _ ");System.out.println();
            System.out.println("Cal.resultatP: "+Cal.resultatP.length);
            System.out.print("taulaV.varstaulaV:    ");for(int i=0;i<taulaV.varstaulaV.length;i++)System.out.print(taulaV.varstaulaV[i]+" ");System.out.println();
        }
        System.out.println("Cal.escalaLinial  taulaC.ceroLog  taulaC.digitsValids  Func.noArrodonir:  "+Cal.escalaLinial+" "+taulaC.ceroLog+" "+taulaC.digitsValids+" "+Func.noArrodonir);
        System.out.print("Cal.idxVarGen:    ");for(int i=0;i<Cal.idxVarGen.length;i++)System.out.print(Cal.idxVarGen[i]+" ");System.out.println();
        System.out.println();System.out.println("Cal.indexsVar:    ");for(int i=0;i<Cal.indexsVar.length;i++)System.out.print(Cal.indexsVar[i][0]+" "+Cal.indexsVar[i][1]+" _ ");System.out.println();
        System.out.println();System.out.println("Cal.indexsVarG:    ");for(int i=0;i<Cal.indexsVarG.length;i++)System.out.print(Cal.indexsVarG[i][0]+" "+Cal.indexsVarG[i][1]+" _ ");System.out.println();
        System.out.println();System.out.println("Cal.limitindexsVarGen:    ");for(int i=0;i<Cal.limitindexsVarGen.length;i++){for(int j=0;j<Cal.limitindexsVarGen[0].length;j++)System.out.print(Cal.limitindexsVarGen[i][j]+" ");System.out.println();}
        System.out.print("Cal.vargOparv:    ");for(int i=0;i<Cal.vargOparv.length;i++)System.out.print(Cal.vargOparv[i]+" ");System.out.println();
    }
    static boolean exeTF(double[] res){
        indexsTF=arr_indexsTF.get(fila);
        indexsSimTF=arr_indexsSimTF.get(fila); 
        bTF=arr_bTF.get(fila);
        dTF=arr_dTF.get(fila);
        if(hihaArray&&simbolsReduitsArrayIF[fila][0]){
            try{
                if(simbolsReduitsArrayIF[fila][1]){
                    int[][] ijI=arr_IFsimbolsReduitsArrayIndexs0.get(fila);
                    for(int r=0;r<ijI.length;r++){
                        double[] d=arr_matriusdeResultats.get(ijI[r][2]);
                        int i=(int)res[ijI[r][1]];
                        if(i<d.length)res[ijI[r][0]]=d[i];
                        else if(!avisBol[fila][0]){ avisBol[fila][0]=true;Func.append(1,"avis: ");Func.append(0," fila: "+fila+"; els indexs de matrius  han de encaixar amb la dimensi"+Func.rB.getString("o_")+" de la matriu es considera resultat = false"+ splitPan.FIL);}
                    }
                }
                if(simbolsReduitsArrayIF[fila][2]){
                    int[][] ijD=arr_IFsimbolsReduitsArrayIndexs1.get(fila);
                    for(int r=0;r<ijD.length;r++){
                        double[] d=arr_matriusdeResultats.get(ijD[r][2]);
                        if(ijD[r][1]<d.length)res[ijD[r][0]]=d[ijD[r][1]];
                        else if(!avisBol[fila][1]){avisBol[fila][1]=true;Func.append(1,"avis: ");Func.append(0," fila: "+fila+"; els indexs de matrius  han de encaixar amb la dimensi"+Func.rB.getString("o_")+" de la matriu es considera resultat = false"+ splitPan.FIL);}
                    }
                }
            }
            catch(Exception e){Func.append(1,"error: ");Func.append(0," fila: "+fila+"; comprobar columna if; els indexs de matrius  han de encaixar amb la dimensi"+Func.rB.getString("o_")+" de la matriu"+ splitPan.FIL);stopBucle=true;}
        }
        for(int i=0;i<indexsSimTF.length;i++){
            dTF[indexsSimTF[i][0]]=res[indexsSimTF[i][1]];
        }
        for(int i=0;i<indexsTF.length;i++)bTF[indexsTF[i][1]]=exeFilTF(i);
        if(indexsTF.length==0){Func.append(1,"error: ");Func.append(0," columna: "+matriu[0][idxIf]+" fila: "+fila+ splitPan.FIL);stopBucle=true;return false;}
        return bTF[indexsTF[indexsTF.length-1][1]];
    }
    static boolean exeFilTF(int i){
            switch(indexsTF[i][0]){
                case 0: return dTF[indexsTF[i][1]]>dTF[indexsTF[i][2]];
                case 1: return dTF[indexsTF[i][1]]>=dTF[indexsTF[i][2]];
                case 2: return dTF[indexsTF[i][1]]==dTF[indexsTF[i][2]];
                case 3: return dTF[indexsTF[i][1]]<dTF[indexsTF[i][2]];
                case 4: return dTF[indexsTF[i][1]]<=dTF[indexsTF[i][2]];
                case 5: return !bTF[indexsTF[i][1]];
                case 6: return bTF[indexsTF[i][1]]&&bTF[indexsTF[i][2]];
                case 7: return bTF[indexsTF[i][1]]||bTF[indexsTF[i][2]];
            }
            return false;
    }
    public void cercleThread(){
        PARA=false;
        taulaF.hihaSeleccioPuntsBol=false;
        taulaF.arr_xyPos=new ArrayList();
        cercleThread = new Thread(new ThreadCercle()); cercleThread.start();
        Thread tempsThread = new Thread(new ThreadTemps()); tempsThread.setPriority(Thread.MAX_PRIORITY);tempsThread.start();
    }
    class ThreadTemps implements Runnable{
        public void run() {
            for(int i=0;i<freqPresN.length;i++)tempsFreq[i] = Instant.now();
            if(puntsTFGraf.length>0||freqPresNBol){
                while (cercleThread.isAlive()){
                    try{Thread.sleep(20L);} catch( InterruptedException e) {}
                    while(PARA&&!stopBucle){try{Thread.sleep(250L);} catch( InterruptedException e) {}}
                    
                    if(contadorBucle==0)puntsSeleccionats(hihaSeleccioPuntsBol);
                    if(puntsTFGraf.length>0){
                        if(graficFet){
                            graficF.graficFet=false;
                            if(hihaSeleccioPuntsBol)puntsGraficsSeleccionats();
                            else puntsGrafics();
                            graficF.graficFet=true;
                        }
                    }
                    if(freqPresNBol){
                        boolean b=false;
                        Instant ara = Instant.now();
                        if(sfreqPres[fila].contains("wait")){try {Thread.sleep(pausa[fila]);} catch (InterruptedException ex) {}}
                        for(int i=0;i<freqPresN.length;i++){
                            String r="";
                            if(ChronoUnit.MILLIS.between(tempsFreq[i],ara)/100>freqPresN[i][1]){
                                int k=freqPresN[i][0];
                                if(!resultatUnic[k]){
                                    double[] d=arr_matriusdeResultats.get(indexsderesultatsaMatriu[k][0]);
                                    r="(";
                                    for(int j=0;j<d.length;j++){
                                        if(noArrodonir[k])r+=d[j]+"; ";
                                        else {
                                            taulaC.digitsValids=digitsValids[k];
                                            r+=splitPan.rodo(d[j])+"; ";
                                        }
                                    }
                                    r+="); ";
                                    if(noArrodonir[fila])r+=res[idxSimbolFilera[k]]+"";
                                    else {
                                        taulaC.digitsValids=digitsValids[k];
                                        r+=splitPan.rodo(res[idxSimbolFilera[k]]);
                                    }
                                    Func.append(0, simbolsT[(int)freqPresN[i][0]]+": "+r+ splitPan.FIL);
                                }
                                else Func.append(0, simbolsT[(int)freqPresN[i][0]]+": "+resultat[idxSimbolFilera[(int)freqPresN[i][0]]]+ splitPan.FIL);
                                tempsFreq[i] = ara;
                                b=true;
                            }
                        }
                        if(b)Func.append(0, splitPan.FIL);
                    }
                }
                graficF.inicia=false;
                Func.falseTrue();
            }
            else{
                while (cercleThread.isAlive()){
                    try{Thread.sleep(100L);} catch( InterruptedException e) {}
                }                
                graficF.inicia=false;
                Func.falseTrue();
            }
        }
    }
    class ThreadCercle implements Runnable{
        public void run() {
            int contadorGraf=0;
            graficFet=false;
            Instant temp=Instant.now();
            arr_xyPos=new ArrayList();
            taulaF.relaciopuntsSeleccionats=new ArrayList();
            res=new double[simbolsReduits.length];
            resultat=new double[simbolsReduits.length];
            if(varDerivadaBol.length==0)varDerivadaBol=new boolean[simbolsReduits.length];//fa false totes les fileres cap filera conte variables que es trobin a derivades
            if(varSumatoriBol.length==0)varSumatoriBol=new boolean[simbolsReduits.length];//fa false totes les fileres cap filera conte variables que es trobin a sumatoris
            for(fila=0;fila<simbolsT.length;fila++){//els resultats de fileres amb els simbols que son numerics cal que assignem el seu valor a la matriu de res (resultat)
                int fil=idxSimbolFilera[fila];//fil rela ciona la fila amb els simbolsReduits on van a parar els resultats de la funcio
                if(simbolsReduitsNombreBol[fil]){
                    try{res[fil]=Double.parseDouble(splitPan.comApun(simbolsT[fila]));}
                    catch(Exception e){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior la cadena: "+simbolsT[fila]+" no es una cadena que es correspon amb un valor num"+Func.rB.getString("_e")+"ric"+ splitPan.FIL);stopBucle=true;return;}
                }
            }
            for(int sim=0;sim<simbolsT.length;sim++){//tots els simbols que no son numerics prenen un valor=NaN
                if(!simbolsT[sim].equals(""))for(int idx=0;idx<dVO.length;idx++)if(!simbolsTNombreBol[sim]&&simbolsT[sim].equals(sVO[idx]))dVO[idx]=NaN;
            }
            for(int fls=0;fls<valorInicial.length;fls++){
                int fil=(int)valorInicial[fls][0];//fil es la filera de la taula
                for(int sim=limindexsSim[fil][0];sim<limindexsSim[fil][1];sim++){
                    if(indexsSim[sim]>=0)dVO[indexsSim[sim]]=valorInicial[fls][1];
                    else dVO[(-indexsSim[sim]-1)]=-valorInicial[fls][1];
                }
            }
            Func.contBol=false;//aplicable quan hi ha variables generals si false cerca de minim no presentacio grafica
            stopBucle=false;Cal.stopBucle=false;
            contadorBucle=0;fila=0;PARA=false;
            while(!stopBucle){
                while(PARA&&!stopBucle){try{Thread.sleep(250L);} catch( InterruptedException e) {}}
                int fil=idxSimbolFilera[fila];
                if(!filaNop[fila]){
                    boolean bo=false;
                    String[] svo=new String [limdVO[fila][1]];System.arraycopy(sVO, limdVO[fila][0], svo, 0,svo.length);
                    Cal.indexs=arr_indexs.get(fila);
                    Cal.dvo=new double[limdVO[fila][1]];System.arraycopy(dVO, limdVO[fila][0], Cal.dvo, 0,Cal.dvo.length);
                    if(varNgenDerBol[fila]){//si ha la funcio derivada present a la filera de taulaF i no hi ha variables general cal calcular la funcio derivable i situar el resultat en la possicio de la funcio de la taulaF
                        indexsDerafuncioF=arr_indexsDerafuncioF.get(fila);
                        for(int j=0;j<taulaD.simbol.length;j++)for(int m=0;m<svo.length;m++)if(taulaD.simbol[j].equals(svo[m])||("-"+taulaD.simbol[j]).equals(svo[m])){
                            if(!taulaD.simbolsDconstants[j]&&!varGenDerBol[fila][j+1]){//hiha derivada sense variableGeneral
                            Cal.supID.resultatFil[j]=Cal.supID.calculF(j);//les possicions a 
                            for(int k=0;k<indexsDerafuncioF.length;k++)if(indexsDerafuncioF[k][1]==j){
                                if(svo[indexsDerafuncioF[k][0]].startsWith("-"))Cal.dvo[indexsDerafuncioF[k][0]]=- Cal.supID.resultatFil[j];
                                else Cal.dvo[indexsDerafuncioF[k][0]]= Cal.supID.resultatFil[j];
                            }
                        }
                        }
                        bo=true;
                       /* for(int j=0;j<taulaD.simbol.length;j++)System.out.print(j+" "+taulaD.simbolsDconstants[j]+" "+varGenDerBol[fila][j+1]+" "+(!taulaD.simbolsDconstants[j]&&!varGenDerBol[fila][j+1])+" _ ");System.out.println();
                        for(int j=0;j<taulaD.simbol.length;j++)if(!taulaD.simbolsDconstants[j]&&!varGenDerBol[fila][j+1]){//hiha derivada sense variableGeneral
                            System.out.println("siD: "+fila+" "+j+" "+taulaD.simbolsDconstants[j]);
                            Cal.supID.resultatFil[j]=Cal.supID.calculF(j);//les possicions a 
                            for(int k=0;k<indexsDerafuncioF.length;k++)if(indexsDerafuncioF[k][1]==j){
                                if(svo[indexsDerafuncioF[k][0]].startsWith("-"))Cal.dvo[indexsDerafuncioF[k][0]]=- Cal.supID.resultatFil[j];
                                else Cal.dvo[indexsDerafuncioF[k][0]]= Cal.supID.resultatFil[j];
                            }
                        }*/
                    }
                    if(varNgenSumBol[fila]){
                        bo=true;
                        indexsSumafuncioF=arr_indexsSumafuncioF.get(fila);
                        for(int j=0;j<taulaI.sumat.length;j++)if(sumafilataulaF[fila][1+j]&&!varGenSumBol[fila][j+1]){//substitueix a la funcio principal el valor de tors els sumatoris que no contenent variable generals i que es troben presents a la funcio
                            if(!taulaI.sumatConstants[j]){//sumatoris que no contenent variables generals pero si variables de la taulaF
                                    for(int l=0;l<taulaD.simbol.length;l++)if(!varGenDerBol[fila][l+1]&&Cal.hihaFPIntegrBol[j+1][l+1])Cal.supID.FPaIntegral(l); 
                                    Cal.supID.bucle(j);
                                    for(int k=0;k<indexsSumafuncioF.length;k++)if(indexsSumafuncioF[k][1]==j){
                                        if(svo[indexsSumafuncioF[k][0]].startsWith("-"))Cal.dvo[indexsSumafuncioF[k][0]]=-Cal.supID.integral[indexsSumafuncioF[k][1]];
                                        else Cal.dvo[indexsSumafuncioF[k][0]]=Cal.supID.integral[indexsSumafuncioF[k][1]];
                                    }
                                }
                        }
                    }
                    //les matrius amb indexs definits i indefinits es busquen els valorsen els matrius de resultats i es coloquen en les possicions a dvo
                    if(indexsInternsArrayBol[fila][1]){//index definit
                        bo=true;
                        int[][] ti=arr_matriusIndexs1.get(fila);
                        for(int i=0;i<ti.length;i++){
                            double[] d=arr_matriusdeResultats.get(ti[i][1]);
                            if(ti[i][2]>=d.length||ti[i][2]<0){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior fila: "+fila+"; calcul de la funci"+Func.rB.getString("o_")+": "+funcioT[fila]+" = NaN  el index del array: "+ti[i][2]+" no encaixa amb la dimensi"+Func.rB.getString("o_")+" de la matriu: "+d.length+ splitPan.FIL);stopBucle=true;return;}
                            if(ti[i][0]<0)Cal.dvo[(-ti[i][0]-1)]=-d[ti[i][2]];
                            else Cal.dvo[ti[i][0]]=d[ti[i][2]];
                        }
                    }
                    if(indexsInternsArrayBol[fila][0]){//index es una variable indefinida cal buscar el seu valor a simbolsReduits: res 
                        bo=true;
                        int[][] ti=arr_matriusIndexs0.get(fila);
                        for(int i=0;i<ti.length;i++){
                            int j=(int)res[ti[i][2]];
                            double[] d=arr_matriusdeResultats.get(ti[i][1]);
                            if(j>=d.length||j<0){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior fila: "+fila+"; calcul de la funci"+Func.rB.getString("o_")+": "+funcioT[fila]+" = NaN  el index del array: "+j+" no encaixa amb la dimensi"+Func.rB.getString("o_")+" de la matriu: "+d.length+ splitPan.FIL);stopBucle=true;return;}
                            if(ti[i][0]<0)Cal.dvo[(-ti[i][0]-1)]=-d[j];
                            else Cal.dvo[ti[i][0]]=d[j];
                        }
                    }
                    if(bo)System.arraycopy(Cal.dvo,0, dVO, limdVO[fila][0],Cal.dvo.length);
                    //hi pot have parametres variables (io variables generals amb la condicio no cerca de minim) i per tant no es cerca de minim (en aquest cas els parametres variables ja s'han de haver substituit pel se u valor)
                    if(funcioSenseVariables[fila]){//!varParBol[fila][0]&&!varGenBol[fila][0]){//si no hi ha variables generals directes o indirectes o mes restringit si no hi ha cerca de minim
                        if(funcArrBol[fila]){
                            if(!iniciIndexsDeMatriu())return;
                            if(!varParFuncOrigBol[fila][0]&&!varGenBol[fila][0])calculArraySenseVariables(res,fil);
                        }
                        else{
                            res[fil]=Cal.calcul(Cal.dvo);//if(!simbolsReduitsNombreBol[fil])res[fil]=Cal.calcul();//els resultat de les funcions de simbols nonumerics es calculen
                            if(!isFinite(res[fil])){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior fila: "+fila+"; calcul de la funci"+Func.rB.getString("o_")+": "+funcioT[fila]+" = NaN"+ splitPan.FIL);stopBucle=true;return;}
                            if(!resultatUnic[fila]){
                                double[] d=new double[1];d[0]=res[fil];
                                emplenaMatriu(d,fila);
                            }
                        }
                    }
                    else{//si hi ha variables directes o indirectes o parametres de capçalera
                        carrega_Arr_Fila(fila);
                        int [][] matriusIndexs =arr_matriusIndexs.get(fila);
                        int longitudMat,contDeMat;
                        if(matriusIndexs.length>0){
                            double[] am=arr_matriusdeResultats.get(matriusIndexs[0][1]);
                            longitudMat=am.length;
                            contDeMat=matriusIndexs.length/longiP[matriusIndexs[0][1]];
                            if(longiP[fila]>1)for(int i=0;i<contDeMat;i++){
                                int con=0;
                                am=arr_matriusdeResultats.get(matriusIndexs[i*longitudMat][1]);
                                for(int j=0;j<longitudMat;j++){
                                    int k=j+i*longitudMat;
                                    if(matriusIndexs[k][0]<0)Cal.dVO[(-matriusIndexs[k][0]-1)]=-am[con];
                                    else Cal.dVO[matriusIndexs[k][0]]=am[con];
                                    con++;
                                }
                            }
                       }
                       if(!simbolsDefinits[fila]){ //hi ha cerca de minim o es un array amb parametres variables
                            if(!varParFuncExtesaBol[fila][0]&&!varParFuncOrigBol[fila][0]&&!varGenBol[fila][0])calculArraySenseVariables(res,fil);//si no hi ha parametres variables a la funcio sense expandir i no hi ha variables generals es un calcul normal 
                            else{
                                Cal.varPocSignif=new boolean[Cal.longi];
                                for(int i=0;i<Cal.longi;i++) Cal.minIncrSignif[i]=(long)Math.pow(2,28);
                                //Cal.reiniciBol=false;//executa un grafic inicil que s'ha de ajustar aqui
                                Cal.stopBucle=false;
                                Thread tempsThread = new Thread(new Temps()); tempsThread.setPriority(Thread.MIN_PRIORITY);tempsThread.start();
                                Cal.stop=false;
                                if(sfreqPres[fila].contains("n")||sfreqPres[fila].contains(";"))Cal.stop=true;
                                boolean pv=taulaP.hihaParVar;
                                taulaP.hihaParVar=parCapVarBol[fila][0];
                                splitPan.calc.cerca();
                                taulaP.hihaParVar=pv;
                                res[fil]=Cal.resultat;
                            }
                            int[] idx=arr_indexsVarModificats0.get(fila);
                            for(int i=0;i<Cal.cursorMinim.length;i++){ 
                                res_varstaulaVFiCerca[idx[i]]=Cal.posic(i,Cal.cursorMinim[i]);
                                res_cursortaulaVFiCerca[idx[i]]=Cal.cursorMinim[i];//en principi la possicio del cursor nomes cal pel calcul de derivades 
                                res_varstaulaVBol[idx[i]]=true;
                            }
                            if(taulaP.hihaParVar){
                                int[][] pv=arr_varPTV_varPTP.get(fila);
                                for(int i=0;i<Cal.cursorMinim.length;i++)for(int j=0;j<pv.length;j++)if(pv[j][0]==i){
                                    for(int k=colors.length;k<simbolsArr.length;k++){
                                        if(taulaP.simParCap[pv[j][2]].equals(simbolsArr[k])){
                                            double[] d=arr_matriusdeResultats.get(k);
                                            if(d.length==0){Func.append(1,"avis: ");Func.append(0,"a taula de funcions superior fila: "+fila+"; probable definicio de array en que calgi definir la dimensi"+Func.rB.getString("o_")+""+ splitPan.FIL);Cal.stopBucle=true;stopBucle=true;return;}
                                            d[pv[j][1]]=res_varstaulaVFiCerca[idx[i]];
                                            arr_matriusdeResultats.set(k,d);
                                        }
                                    }
                                }
                            }
                            indexsVaraSim=arr_indexsVaraSim.get(fil);
                            for(int i=0;i<indexsVaraSim.length;i++){
                                if(sVO[indexsVaraSim[i][0]].startsWith("-"))dVO[indexsVaraSim[i][0]]=-res_varstaulaVFiCerca[indexsVaraSim[i][1]];
                                else dVO[indexsVaraSim[i][0]]=res_varstaulaVFiCerca[indexsVaraSim[i][1]];
                            }
                            Cal.stopBucle=false;
                        }
                        else{
                            Cal.stop=true;
                            Cal.stopBucle=false;
                            int[] idx1=arr_indexsVarModificats1.get(fila);
                            for(int j=0;j<res_varstaulaVBol.length;j++)if(idx1[j]>-1){
                                if(res_varstaulaVBol[j]){
                                    Cal.cursorMinim[idx1[j]]=res_cursortaulaVFiCerca[j];
                                    Cal.varAct[idx1[j]]=res_varstaulaVFiCerca[j]; 
                                } 
                                else {Func.append(1,"error: ");Func.append(0,"a taula de funcions superior fila: "+fila+" cadena: "+simbolsT[fila]+" hi ha variables generals indefinides i no es cerca de m"+Func.rB.getString("i_")+"nim"+ splitPan.FIL);stopBucle=true;return;}
                            }
                            Cal.actualitzadVO(Cal.dvo, Cal.dVO,Cal.sVO,Cal.varAct,Cal.cursor,Cal.cursorMinim);
                            if(longiP[fila]>1)calculArraySenseVariables(res,fil); 
                            else res[fil]= Cal.calcul(Cal.dvo);//res[fil]=Cal.resultatdVO(Cal.dvo, Cal.dVO,Cal.sVO,Cal.varAct,Cal.cursor,Cal.cursorMinim,Cal.idxAmp);
                        }
                        if(Cal.resultatM.length>0)arr_matriusdeResultats.set(fila,Cal.resultatM);
                    }
                    if(indexsInternsArrayBol[fila][2]){
                        double[] arr=arr_matriusdeResultats.get(indexsInternsArrayResultat[fila][0]);
                        int r=(int)(res[indexsInternsArrayResultat[fila][1]]);
                        if(r>=arr.length||r<0){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior columna 1: "+matriu[fila+1][1]+" el index del array: "+r+" no encaixa amb la dimensi"+Func.rB.getString("o_")+" de la matriu: "+arr.length+ splitPan.FIL);stopBucle=true;return;}
                        arr[r]=res[fil];
                        arr_matriusdeResultats.set(indexsInternsArrayResultat[fila][0],arr);
                        if(fixcontidxmatriu[fila])modificacontador(fila,r);
                    }
                    if(indexsInternsArrayBol[fila][3]){
                        double[] arr=arr_matriusdeResultats.get(indexsInternsArrayResultat[fila][0]);
                        if(indexsInternsArrayResultat[fila][1]>=arr.length||indexsInternsArrayResultat[fila][1]<0){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior columna 1: "+matriu[fila+1][1]+" el index del array: "+indexsInternsArrayResultat[fila][1]+" no encaixa amb la dimensi"+Func.rB.getString("o_")+" de la matriu: "+arr.length+ splitPan.FIL);stopBucle=true;return;}
                        arr[indexsInternsArrayResultat[fila][1]]=res[fil];
                        arr_matriusdeResultats.set(indexsInternsArrayResultat[fila][0],arr);
                        if(fixcontidxmatriu[fila])modificacontador(fila,indexsInternsArrayResultat[fila][1]);
                    }
                }
                else if(fixcontidxmatriu[fila]){
                    if(indexsInternsArrayBol[fila][2]){
                        double[] arr=arr_matriusdeResultats.get(indexsInternsArrayResultat[fila][0]);
                        int r=(int)(res[indexsInternsArrayResultat[fila][1]]);
                        if(r>=arr.length||r<0){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior columna 1: "+matriu[fila+1][1]+" el index del array: "+r+" no encaixa amb la dimensi"+Func.rB.getString("o_")+" de la matriu: "+arr.length+ splitPan.FIL);stopBucle=true;return;}
                        modificacontador(fila,r-1);
                    }
                    if(indexsInternsArrayBol[fila][3]){
                        double[] arr=arr_matriusdeResultats.get(indexsInternsArrayResultat[fila][0]);
                        if(indexsInternsArrayResultat[fila][1]>=arr.length||indexsInternsArrayResultat[fila][1]<0){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior columna 1: "+matriu[fila+1][1]+" el index del array: "+indexsInternsArrayResultat[fila][1]+" no encaixa amb la dimensi"+Func.rB.getString("o_")+" de la matriu: "+arr.length+ splitPan.FIL);stopBucle=true;return;}
                        modificacontador(fila,indexsInternsArrayResultat[fila][1]-1);
                    }
                }
                int seguentFila=fila+1;
                boolean tf=false;
                if(thenBol[fila][0])tf=exeTF(res);
                if(thenBol[fila][1]&&(tf||!thenBol[fila][0])){//si la columna then te contingut i  ( if es true o no hi ha informacio a la filera if)
                    int[][] ijI;int[][] ijD;
                    if(thenBol[fila][4]){
                        boolean[][] b=new boolean[0][0];boolean b1=false;int[][] i0=new int[0][0];int con=0;
                        iThenPosSim=arr_iThenPosSim.get(fila);
                        if(hihaArray&&simbolsReduitsArrayThen[fila][0]){
                            if(simbolsReduitsArrayThen[fila][1]){
                                ijI=arr_THENsimbolsReduitsArrayIndexs0.get(fila);
                                for(int r=0;r<ijI.length;r++){
                                    double[] d=arr_matriusdeResultats.get(ijI[r][2]);
                                    try{res[ijI[r][0]]=d[(int)res[ijI[r][1]]];}
                                    catch(Exception e){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior columna : "+matriu[0][idxThen]+" fila: "+fila+" a la cadena"+matriu[fila+1][3]+"; es probable que l'index del array: '"+simbolsT[ijI[r][2]]+"' no encaixi amb la seva dimensi"+Func.rB.getString("o_")+"  index:"+res[ijI[r][1]]+";  dimensio arr: "+d.length+ splitPan.FIL);stopBucle=true;return;}
                                }
                            }
                            if(simbolsReduitsArrayThen[fila][2]){
                                ijD=arr_THENsimbolsReduitsArrayIndexs1.get(fila);
                                for(int r=0;r<ijD.length;r++){
                                    double[] d=arr_matriusdeResultats.get(ijD[r][2]);
                                    try{res[ijD[r][0]]=d[ijD[r][1]];}
                                    catch(Exception e){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior columna : "+matriu[0][idxThen]+" fila: "+fila+" a la cadena"+matriu[fila+1][3]+"; es probable que l'index del array: '"+simbolsT[ijD[r][2]]+"' no encaixi amb la seva dimensi"+Func.rB.getString("o_")+"  index:"+ijD[r][1]+";  dimensio arr: "+d.length+ splitPan.FIL);stopBucle=true;return;}
                                }
                            }    
                        }
                        if(hihaArray&&simbolsReduitsArrayThen[fila][3]){
                                b=arr_iThenPosSimArr.get(fila);b1=true;//arr_iThenPosSimArr laparella [0]_[1] ha d'esset tt o ft no pot esser ff  ni tf (t=true =es una matriu) 
                                i0=arr_THENsimbolsReduistArray.get(fila);
                        }
                        for(int r=0;r<iThenPosSim.length;r++){
                            if(b1&&(b[r][0]||b[r][1])){//(b[r][0]||b[r][1]) les opcion [0] mat>mat; [1] valor>mat
                                if(b[r][0]){emplenaMatriu(arr_matriusdeResultats.get(i0[con][0]),i0[con][1]);}
                                else if(b[r][1]){
                                    double[] d=new double[1];d[0]=res[i0[con][0]];
                                    emplenaMatriu(d,i0[con][1]);
                                }
                               con++;
                            }
                            else{
                                res[iThenPosSim[r][1]]=res[iThenPosSim[r][0]];
                                actualitzadvo(iThenPosSim[r][3],res[iThenPosSim[r][1]]);
                            }
                        }
                        if(hihaArray&&simbolsReduitsArrayThen[fila][4]){
                            if(simbolsReduitsArrayThen[fila][5]){
                                ijI=arr_THEN_destisimbolsReduitsArrayIndexs0.get(fila);
                                for(int r=0;r<ijI.length;r++){
                                    double[] d=arr_matriusdeResultats.get(ijI[r][2]);
                                    try{d[(int)res[ijI[r][1]]]=res[ijI[r][0]];}
                                    catch(Exception e){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior columna : "+matriu[0][idxThen]+" fila: "+fila+" a la cadena"+matriu[fila+1][3]+"; es probable que l'index del array: '"+simbolsT[ijI[r][2]]+"' no encaixi amb la seva dimensi"+Func.rB.getString("o_")+"  index:"+res[ijI[r][1]]+";  dimensio arr: "+d.length+ splitPan.FIL);stopBucle=true;return;}
                                    arr_matriusdeResultats.set(ijI[r][2],d);
                                }
                            }
                            if(simbolsReduitsArrayThen[fila][6]){
                                ijD=arr_THEN_destisimbolsReduitsArrayIndexs1.get(fila);
                                for(int r=0;r<ijD.length;r++){
                                    double[] d=arr_matriusdeResultats.get(ijD[r][2]);
                                    try{d[ijD[r][1]]=res[ijD[r][0]];}
                                    catch(Exception e){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior columna : "+matriu[0][idxThen]+" fila: "+fila+" a la cadena"+matriu[fila+1][3]+"; es probable que l'index del array: '"+simbolsT[ijD[r][2]]+"' no encaixi amb la seva dimensio  index:"+ijD[r][1]+";  dimensio arr: "+d.length+ splitPan.FIL);stopBucle=true;return;}
                                    arr_matriusdeResultats.set(ijD[r][2],d);
                                }
                            }    
                        }
                    }
                    if(thenBol[fila][7]){//hi ha return 
                        boolean[][] b=new boolean[0][0];boolean b1=false;int[][] i0=new int[0][0];int con=0;
                        int retfil=iretCall[fila][2];
                        iThenPosSim=arr_jThenPosSim.get(retfil);
                        if(hihaArray&&simbolsReduitsArrayThenReturn[retfil][0]){
                            if(simbolsReduitsArrayThenReturn[retfil][1]){
                                ijI=arr_THENsimbolsReduitsArrayIndexs0Return.get(retfil);
                                for(int r=0;r<ijI.length;r++){
                                    double[] d=arr_matriusdeResultats.get(ijI[r][2]);
                                    try{res[ijI[r][0]]=d[(int)res[ijI[r][1]]];}
                                    catch(Exception e){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior columna : "+matriu[0][idxThen]+" fila: "+fila+" a la cadena"+matriu[fila+1][3]+"; es probable que l'index del array: '"+simbolsT[ijI[r][2]]+"' no encaixi amb la seva dimensi"+Func.rB.getString("o_")+"  index:"+res[ijI[r][1]]+";  dimensio arr: "+d.length+ splitPan.FIL);stopBucle=true;return;}
                                }
                            }
                            if(simbolsReduitsArrayThenReturn[retfil][2]){
                                ijD=arr_THENsimbolsReduitsArrayIndexs1Return.get(retfil);
                                for(int r=0;r<ijD.length;r++){
                                    double[] d=arr_matriusdeResultats.get(ijD[r][2]);
                                    try{res[ijD[r][0]]=d[ijD[r][1]];}
                                    catch(Exception e){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior columna : "+matriu[0][idxThen]+" fila: "+fila+" a la cadena"+matriu[fila+1][3]+"; es probable que l'index del array: '"+simbolsT[ijD[r][2]]+"' no encaixi amb la seva dimensi"+Func.rB.getString("o_")+"  index:"+ijD[r][1]+";  dimensio arr: "+d.length+ splitPan.FIL);stopBucle=true;return;}
                                }
                            }    
                        }
                        if(hihaArray&&simbolsReduitsArrayThenReturn[retfil][3]){
                                b=arr_iThenPosSimArrReturn.get(retfil);b1=true;
                                i0=arr_THENsimbolsReduistArrayReturn.get(retfil);
                        }
                        for(int r=0;r<iThenPosSim.length;r++){
                            if(b1&&(b[r][0]||b[r][1])){//(b[r][0]||b[r][1]) les opcion [0] mat>mat; [1] valor>mat
                                if(b[r][0]){emplenaMatriu(arr_matriusdeResultats.get(i0[con][0]),i0[con][1]);}
                                else if(b[r][1]){
                                    double[] d=new double[1];d[0]=res[i0[con][0]];
                                    emplenaMatriu(d,i0[con][1]);
                                }
                               con++;
                            }
                            else{
                                res[iThenPosSim[r][1]]=res[iThenPosSim[r][0]];
                                actualitzadvo(iThenPosSim[r][3],res[iThenPosSim[r][1]]);
                            }
                        }
                        if(hihaArray&&simbolsReduitsArrayThenReturn[retfil][4]){
                            if(simbolsReduitsArrayThenReturn[retfil][5]){
                                ijI=arr_THEN_destisimbolsReduitsArrayIndexs0Return.get(retfil);
                                for(int r=0;r<ijI.length;r++){
                                    double[] d=arr_matriusdeResultats.get(ijI[r][2]);
                                    try{d[(int)res[ijI[r][1]]]=res[ijI[r][0]];}
                                    catch(Exception e){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior columna : "+matriu[0][idxThen]+" fila: "+fila+" a la cadena"+matriu[fila+1][3]+"; es probable que l'index del array: '"+simbolsT[ijI[r][2]]+"' no encaixi amb la seva dimensi"+Func.rB.getString("o_")+"  index:"+res[ijI[r][1]]+";  dimensio arr: "+d.length+ splitPan.FIL);stopBucle=true;return;}
                                    arr_matriusdeResultats.set(ijI[r][2],d);
                                }
                            }
                            if(simbolsReduitsArrayThenReturn[retfil][6]){
                                ijD=arr_THEN_destisimbolsReduitsArrayIndexs1Return.get(retfil);
                                for(int r=0;r<ijD.length;r++){
                                    double[] d=arr_matriusdeResultats.get(ijD[r][2]);
                                    try{d[ijD[r][1]]=res[ijD[r][0]];}
                                    catch(Exception e){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior columna : "+matriu[0][idxThen]+" fila: "+fila+" a la cadena"+matriu[fila+1][3]+"; es probable que l'index del array: '"+simbolsT[ijD[r][2]]+"' no encaixi amb la seva dimensio  index:"+ijD[r][1]+";  dimensio arr: "+d.length+ splitPan.FIL);stopBucle=true;return;}
                                    arr_matriusdeResultats.set(ijD[r][2],d);
                                }
                            }    
                        }
                        seguentFila=(iThenGoSubRet[fila]);
                    }
                    else if(thenBol[fila][4]&&!thenBol[fila][5]){//si es un call i no copia
                        seguentFila=iThenGoSubRet[fila];
                        //a la filera del return es coloca la filera seguent al call al return ja se li restara 1 que despres es suma al buclefor()
                        iThenGoSubRet[iretCall[fila][0]]=iretCall[fila][1];
                        iretCall[iretCall[fila][0]][2]=fila;
                    }
                    else if(thenBol[fila][8])seguentFila=iThenGoSubRet[fila];
                }
                if(!filaNop[fila]){
                    for(int sim=limindexsSim[fila][0];sim<limindexsSim[fila][1];sim++){
                        if(indexsSim[sim]>=0)dVO[indexsSim[sim]]=res[fil];
                        else dVO[-indexsSim[sim]-1]=-res[fil];
                    }
                    if(varSumatoriBol[fil]){//for(int k=0;k<taulaF.simbolsReduits.length;k++)if(!taulaF.simbolsReduitsNombreBol[k]){if(matriuVarAFun[l].contains(" "+taulaF.simbolsReduits[k]+" "))taulaF.varSumatoriBol[k]=true;per cada filera de taula de simbols Reduits; indica si el simbol de la taula columna 1(simbols reduits no numerics) es present a alguna integral;
                        for(int j=0;j<indexsSum.length;j++){
                            if(fil==indexsSum[j][1]){
                                if(indexsSum[j][0]>=0) Cal.supID.dVOI[indexsSum[j][0]]=res[fil];
                                else Cal.supID.dVOI[-indexsSum[j][0]-1]=-res[fil];
                            }
                        }
                    }
                    if(varDerivadaBol[fil]){//per cada filera de taula reduida de funcions principal indica si hi ha variable de la funcio a la taula derivada, cada filera correspont a la filera de taulaF (reduida);
                    for(int j=0;j<indexsDer.length;j++){
                            if(fil==indexsDer[j][1]){
                                if(indexsDer[j][0]>=0) Cal.supID.dVO[indexsDer[j][0]]=res[fil];
                                else Cal.supID.dVO[-indexsDer[j][0]-1]=-res[fil];
                            }
                        }
                    }
                }
                if(thenBol[fila][9]){//a columna then pres  hi ha representacio grafica "graf" en aquest punt
                    if(contadorGraf==0){
                        coordenades=0;
                        if(puntsTFGraf.length>0){
                            for(int j=0;j<puntsTFGraf[0].length;j++)if(puntsTFBol[0][j])coordenades++;
                            int cor=coordenades;
                            if(coordenades<3)cor=2;
                            puntsGraf=new double[puntsGraf.length][cor];
                            graficF.punts=new double[puntsGraf.length][cor];//els puns a graficF.punts tornen a tenir tres coordenades les cordenades no utilitzades seran cero si son tres les coordenade aquestes seran corretgides per una representacio en dues dimensions
                            limPunts=new double[cor][5];//les fileres de limPunts son el nombre de cordenades les dues columnes son limit inferior i superior de les coordenades
                        }
                        graf.repaint();
                        puntsGraficsInici();
                        contadorGraf++;       
                    }
                    System.arraycopy(res, 0, resultat, 0, res.length);
                    if(puntsTFGraf.length>0){
                        graficF.inicia=true;
                        int cok=0,coj=0,cokSup=0;
                        graficFet=false;
                        for(int k=0;k<puntsTFGraf.length;k++){
                            coj=0;
                            for(int j=0;j<puntsGraf[0].length;j++){//per cada coordenada x y io z
                                if(resultatUnic[puntsTFGraf[k][j]]){//si la fila es una funcio simple
                                    if(puntsTFBol[k][j]){puntsGraf[cok][coj]=resultat[idxSimbolFilera[puntsTFGraf[k][j]]];}
                                    coj++;
                                }
                                else{//si la fila conte funcio extesa
                                    if(puntsTFBol[k][j]){
                                        double[] d=arr_matriusdeResultats.get(indexsderesultatsaMatriu[puntsTFGraf[k][j]][0]);
                                        cok=cokSup;
                                        for(int l=0;l<indexsderesultatsaMatriu[puntsTFGraf[k][j]][2];l++){
                                            puntsGraf[cok][coj]=d[l];
                                            cok++;
                                        }
                                        coj++;
                                    }
                                }
                                if(j>=puntsGraf[0].length-1){
                                    if(!resultatUnic[puntsTFGraf[k][j]])cokSup=cok;
                                    else {cok++;cokSup=cok;}
                                }
                            }
                        }
                    }
                    graficFet=true;
                    //for(int i=0;i<puntsGraf.length;i++){if(!isFinite(puntsGraf[i][0])){for(int j=0;j<puntsGraf.length;j++) System.out.print(puntsGraf[j][0]+" ");System.out.println();}}
                    /*int con=0;boolean[] b=new boolean[puntsGraf.length];
                    for(int i=0;i<puntsGraf.length;i++){if(!isFinite(puntsGraf[i][0])){con++;b[i]=true;}}
                    if(con>0){
                       puntsGrafFinits=new double[puntsGraf.length-con][puntsGraf[0].length];
                       con=0;
                       for(int i=0;i<puntsGraf.length;i++){
                            if(!b[i]){
                                System.arraycopy(puntsGraf[i], 0, puntsGrafFinits[con], 0, puntsGrafFinits[0].length);
                                con++;
                            }
                       }
                   }
                  else puntsGrafFinits=splitPan.copiaMatriu(puntsGraf);
                    */
                }
                if(freqPresNBol0[fila]){
                    if(tf||!thenBol[fila][0]){//si columna if es true o no hi es
                        String resultat="";
                        if(!varParFuncOrigBol[fila][0]&&!varGenBol[fila][0]&&!varBol[fila][0]||simbolsDefinits[fila]){//si no hi ha variables o els simbols variales s'han definit
                            if(!resultatUnic[fila]){
                                double[] d=arr_matriusdeResultats.get(indexsderesultatsaMatriu[fila][0]);
                                resultat+="(";
                                for(int i=0;i<d.length;i++){
                                    if(noArrodonir[fila])resultat+=d[i]+"; ";
                                    else {
                                        taulaC.digitsValids=digitsValids[fila];
                                        resultat+=splitPan.rodo(d[i])+"; ";
                                    }
                                }
                                resultat+="); ";
                                int f=indexsderesultatsaMatriu[fila][0];
                                d=arr_matriusdeResultats.get(f);
                                res[fil]=0;for(int i=0;i<d.length;i++)res[fil]+=d[i];
                            }
                            if(noArrodonir[fila])resultat+=res[fil]+"";
                            else {
                                taulaC.digitsValids=digitsValids[fila];
                                resultat+=splitPan.rodo(res[fil]);
                            }
                            if(sfreqPres[fila].contains("-+")){
                                if(sfreqPres[fila].contains(";")){Func.append(0, resultat);}
                                else if(sfreqPres[fila].contains("nn")){Func.append(0, resultat);}
                                else if(sfreqPres[fila].contains("n")){Func.append(0, resultat);}
                            }
                            else if(sfreqPres[fila].contains("+-")){
                                if(sfreqPres[fila].contains(";")){Func.append(0, simbolsT[fila]);}
                                else if(sfreqPres[fila].contains("nn")){Func.append(0, simbolsT[fila]);}
                                else if(sfreqPres[fila].contains("n")){Func.append(0, simbolsT[fila]);}
                            }
                            else{
                                if(sfreqPres[fila].contains(";")){Func.append(0, simbolsT[fila]+" "+resultat);}
                                else if(sfreqPres[fila].contains("nn")){Func.append(0, simbolsT[fila]+" "+resultat);}
                                else if(sfreqPres[fila].contains("n")){Func.append(0, simbolsT[fila]+" "+resultat);}
                            }
                        }
                        if(sfreqPres[fila].contains(";")){Func.append(0, "; ");}
                        else if(sfreqPres[fila].contains("nn")){Func.append(0, splitPan.FIL+splitPan.FIL);}
                        else if(sfreqPres[fila].contains("n")){Func.append(0, splitPan.FIL);}
                        pausaGraf=0;
                        if(sfreqPres[fila].contains("wait")){try {pausaGraf=pausa[fila];Thread.sleep(pausa[fila]);} catch (InterruptedException ex) {}}
                        pausaGraf=0;
                        if(sfreqPres[fila].contains("fi")){Cal.stopBucle=true;stopBucle=true;}
                    }
                }
                fila=seguentFila;
                Instant ara = Instant.now();
                Instant ara1 = Instant.now();
                if(ChronoUnit.MILLIS.between(temp,ara)>100){
                    temp=Instant.now();
                    try {Thread.sleep(50);} catch (InterruptedException ex) {}
                }
                if(fila==colors.length)fila=0;
            }
            graficF.inicia=false;
        }
    }
    static boolean iniciIndexsDeMatriu(){//quant a la funcio  hi ha simbols associats a arrays cal actualitar els simbols amb els valors procedents de arrays de una o mes fileres calen les  posicions a dvo i les filees dels arrays que contenent els varlors
        carrega_Arr_Fila(fila);
        int [][] matriusIndexs =arr_matriusIndexs.get(fila);
        int longitudMat,contDeMat;
        if(matriusIndexs.length>0){
            double[] am=arr_matriusdeResultats.get(matriusIndexs[0][1]);//carrega la matriu de la filera associada
            if(longiP[fila]==0){Func.append(1,"error: ");Func.append(0,"a taula de funcions superior fila: "+fila+" cadena: "+simbolsT[fila]+" s'ha definit directa o indirectament com un array de parametres de longitud=0 (longiP=0) s'ha executat una divisi"+Func.rB.getString("o_")+" per 0 = NaN"+ splitPan.FIL);stopBucle=true;return false;}
            longitudMat=am.length;// longitud del array els valors s'incorporen a la funcio
            contDeMat=matriusIndexs.length/longiP[fila];//com que tots els simbols associat a arrays han de tenir la mateixa longitud  contdeMat es pot calcular aixi;  matriuindexs[0][1] es la filera del array associat pero la longitud del array es el mateix que el de la fila actual
            if(longiP[fila]>1)for(int i=0;i<contDeMat;i++){
                int con=0;
                am=arr_matriusdeResultats.get(matriusIndexs[i*longitudMat][1]);//carrega la matriu de resultats associada a  ala part de la funcio
                for(int j=0;j<longitudMat;j++){
                    int k=j+i*longitudMat;
                    if(matriusIndexs[k][0]<0)Cal.dVO[-matriusIndexs[k][0]-1]=-am[con];
                    else Cal.dVO[matriusIndexs[k][0]]=am[con];
                    con++;
                }
            }
        }
        return true;
    }
    static void calculArraySenseVariables(double[] res,int fil){
            res[fil]=0;
            System.arraycopy(Cal.dVO, 0, Cal.dvo, 0, Cal.dvo.length);
            int incr=(Cal.indexs.length-(longiP[fila]-1))/longiP[fila];
            if(incr==0){
                incr=1;
                for(int i=0;i<longiP[fila];i++){
                    Cal.resultatM[i]=Cal.dvo[incr];
                    res[fil]+=Cal.resultatM[i];
                    incr+=5;
                }
            }
            else{
                int posi=0;
                int[][] sup=splitPan.copiaMatriu(Cal.indexs);
                Cal.indexs=new int[incr][3];
                for(int i=0;i<longiP[fila];i++){
                    System.arraycopy(sup,posi,Cal.indexs,0,incr);
                    posi+=incr;
                    Cal.resultatM[i]=Cal.calcul(Cal.dvo);
                    res[fil]+=Cal.resultatM[i];
                }
            }
            if(!isFinite(res[fil])&&avisBol[fila][2]){avisBol[fila][2]=true;Func.append(1,"avis: ");Func.append(0,"a taula de funcions superior calcul fila:"+fila+"; la funci"+Func.rB.getString("o_")+": "+funcioT[fila]+" = NaN; el procediment continua i no es seguira mostrant aquest avis"+ splitPan.FIL);}
        if(!resultatUnic[fila]){emplenaMatriu(Cal.resultatM,fila);}
    }
    static void emplenaMatriu(double[] mat,int filD){//començant en la possicio del contador del array de la fila associada, que conte els resultats,  es passan els resultats de la matriu mat i s'actualitza el contador
        int f=indexsderesultatsaMatriu[filD][0];
        double[] d=arr_matriusdeResultats.get(f);
        int cont=indexsderesultatsaMatriu[f][1];
        int lim=indexsderesultatsaMatriu[f][2];
        if(lim<0||d.length==0){Func.append(1,"avis: ");Func.append(0,"a taula de funcions superior fila: "+fila+"; probable definicio de array en que calgi definir la dimensi"+Func.rB.getString("o_")+""+ splitPan.FIL);Cal.stopBucle=true;stopBucle=true;return;}
        for(int j=0;j<mat.length;j++){
            if (cont==lim)cont=0;
            d[cont]=mat[j];
            cont++;
        }
        indexsderesultatsaMatriu[f][1]=cont;
        arr_matriusdeResultats.set(f,d);
    }
    static void modificacontador(int filD,int cont){
        cont++;
        int f=indexsderesultatsaMatriu[filD][0];
        if (cont>=indexsderesultatsaMatriu[f][2]||cont<0)cont=0;
        indexsderesultatsaMatriu[f][1]=cont;
    }
    public class Temps implements Runnable{
        public void run() {
            splitPan.calc.temps();
        }
    }
    static void actualitzadvo(int fil,double res){
        for(int sim=limindexsSim[fil][0];sim<limindexsSim[fil][1];sim++){
            if(indexsSim[sim]>=0)dVO[indexsSim[sim]]=res;
            else dVO[-indexsSim[sim]-1]=-res;
        }
    }
    static boolean punt(int fil,int eix,String cad){//eix es 0,1 o 2 per simbol x,y o z
        
        Color[] col=new Color[1];
        puntsBol[fil]=false;//la filera de la taula s'ha utilitzat i no s'ha de tornar a analitzar
        if(!puntsTFBol[cont_[eix]][eix]){//si la possicio de la cordenada no s'ha introduit fa puntsTFBol[cont[eix]][eix]=true
            puntsTFBol[cont_[eix]][eix]=true;
            puntsTFGraf[cont_[eix]][eix]=fil;
        }
        else return false;
        if(!resultatUnic[fil]){
            if(colorAleatori[fil]){
                col=rand(coloraleatori[fil],fil);
                for(int i=0;i<indexsderesultatsaMatriu[fil][2];i++)unpunt(fil,eix,cad,col[i]);
            }
            else{for(int i=0;i<indexsderesultatsaMatriu[fil][2];i++)unpunt(fil,eix,cad,col[0]);}
        }
        else unpunt(fil,eix,cad,col[0]);
        puntsTFBol[cont_[eix]][4]=true;puntsTFBol[cont_[eix]][5]=true;puntsTFBol[cont_[eix]][3]=true;
        cont_[eix]++;
        return true;
    }
    static void unpunt(int fil,int eix,String cad,Color col){
        if(!puntsTFBol[cont_[eix]][4]){//la cinquena columna s'emplena el tamany de la primera filera (x y o z) que te un tamany
            if(tamanyBol[fil][0]){
                grandariaPunt[cont[eix]][0]=tamany[fil][0]; 
                if(tamanyBol[fil][1])grandariaPunt[cont[eix]][1]=tamany[fil][0]-tamany[fil][1];
                else grandariaPunt[cont[eix]][1]=0;
            }
        }
        if(!puntsTFBol[cont_[eix]][5]){//la darreda columna s'emplena el tempshistorial de la primera filera (x y o z) que te un temps
            if(tempsPresHis[fil][0]>0){
                historiaIntervalTemps[cont[eix]]=tempsPresHis[fil][0];
                if(tempsPresHis[fil][1]>0)ihistorial[cont[eix]]=tempsPresHis[fil][1];
                else ihistorial[cont[eix]]=graficF.iHistorial;
            }
        }
        if(colorAleatori[fil]){
            puntsTFColor[cont[eix]][1]=col;
            puntsTFColor[cont[eix]][0]=cad;
        }
        else if(!puntsTFBol[cont_[eix]][3]){//la quarta columna (3) s'emplena el color de la primera filera (x y o z) que te color
            puntsTFColor[cont[eix]][1]=colors[fil];
            puntsTFColor[cont[eix]][0]=cad;
        }
        cont[eix]++;
    }
    static Color[] rand(double d,int fil){//m[0]=0;cyan;m[1]=0; rosa magenta;m[2]=0;groc;; 0 1blau  0 2verd 12 vermell
        int k=(int)d;
        int cont=0;
        Random ran=new Random((long)(d*1E16));
        Color[] col=new Color[indexsderesultatsaMatriu[fil][2]];   
        if (k<1||k>9)k=-1;
        int[] m=new int[3];
        for(int i=cont;i<col.length;i++){
            for(int j=0;j<3;j++)m[j]=ran.nextInt(255);
            if(k==1){m[0]=m[0]/4;m[1]+=125;m[2]+=75;}//incrementar el color cyan i reduir la intensitat del rosa i el groc
            if(k==2){m[1]=m[1]/4;m[0]+=125;m[2]+=75;}
            if(k==3){m[2]=m[2]/6;m[0]+=125;m[1]+=125;}
            if(k==4){m[0]=(int)(m[0]/1.5);m[1]=(int)(m[1]/1.5);m[2]+=75;}
            if(k==5){m[0]=(int)(m[0]/1.5);m[2]=(int)(m[2]/1.8);m[1]+=125;}
            if(k==6){m[1]=(int)(m[1]/1.5);m[2]=(int)(m[2]/1.8);m[0]+=125;}
            for(int j=0;j<3;j++)if(m[j]>255)m[j]=510-m[j];
            int min=m[0],mn=0;if(min>m[1])mn=1;if(min>m[2])mn=2;
            if(k==7){if(m[mn]>130)m[mn]=260-m[mn];}
            if(k==8){
                    if(m[mn]<80)m[mn]+=100;
                    if(m[mn]>180)m[mn]=360-m[mn];
                    for(int j=0;j<3;j++)if(j!=mn){if(m[j]<80)m[j]+=175;}
            }
            if(k==9){
                    if(m[mn]<150)m[mn]+=80;
                    if(m[mn]>230)m[mn]=460-m[mn];
                    for(int j=0;j<3;j++)if(j!=mn){if(m[j]<150)m[j]+=105;}
            }
            if(m[mn]>220)m[mn]=440-m[mn];//si el minim del color mes intens es molt feble conve incrementar-lo
            col[i]=new Color(m[0],m[1],m[2]);
        }
        return col;
    }
    static void limits(){//limPunts te com a maxim tres fileres  cada filera es una cordenada que poden esser x,(0,2)  o x,y o y,x o x,yz//puntsTFBol te una filera per cada punt te quatre columnes les tres primeres true o false depenent de si hi ha la coordenada la quarte el color
        if(coordenades==1){
            if(puntsTFBol[0][1]){
                limPunts[1][0]=Cur.maxim;limPunts[1][1]=-Cur.maxim;
                for(int i=0;i<graficF.punts.length;i++)if(limPunts[1][0]>graficF.punts[i][1])limPunts[1][0]=graficF.punts[i][1];
             }
            if(puntsTFBol[0][0]){
                if(puntsTFBol[0][0]){limPunts[0][0]=Cur.maxim;limPunts[0][1]=-Cur.maxim;}
                for(int i=0;i<graficF.punts.length;i++)if(limPunts[0][0]>graficF.punts[i][0])limPunts[0][0]=graficF.punts[i][0];
            }
        }
        else{
            limPunts[0][0]=Cur.maxim;limPunts[0][1]=-Cur.maxim;
            if(coordenades>1){limPunts[1][0]=Cur.maxim;limPunts[1][1]=-Cur.maxim;}
            if(coordenades>2){limPunts[2][0]=Cur.maxim;limPunts[2][1]=-Cur.maxim;}
            for(int i=0;i<graficF.punts.length;i++){
                if(limPunts[0][0]>graficF.punts[i][0])limPunts[0][0]=graficF.punts[i][0];
                if(limPunts[0][1]<graficF.punts[i][0])limPunts[0][1]=graficF.punts[i][0];
            }
            if(coordenades>1)for(int j=1;j<limPunts.length;j++)for(int i=0;i<graficF.punts.length;i++){
                if(limPunts[j][0]>graficF.punts[i][j])limPunts[j][0]=graficF.punts[i][j];
                if(limPunts[j][1]<graficF.punts[i][j])limPunts[j][1]=graficF.punts[i][j];
            }
        }
        for(int j=0;j<limPunts.length;j++){
            if(limPunts[j][0]==limPunts[j][1]){
                if(limPunts[j][0]!=0){
                    double min=Math.abs(limPunts[j][0])/1E13;
                    limPunts[j][0]-=min;limPunts[j][1]+=min;
                }
                else{limPunts[j][0]-=1;limPunts[j][1]+=1; }
            }
            limPunts(j);
        }//j com a maxim pren els valors 0, 1 i 2
    }

    static void nouslimits(){
        System.out.print(puntsGrafFinits.length+" "+limPunts.length);
        if(coordenades==1){
            if(puntsTFBol[0][1]){for(int i=0;i<graficF.punts.length;i++)if(!puntsGrafFinits[i]){
                    if(limPunts[1][0]>graficF.punts[i][1])limPunts[1][0]=graficF.punts[i][1];
                    if(limPunts[1][1]<graficF.punts[i][1])limPunts[1][1]=graficF.punts[i][1];
                }
            }
            else if(puntsTFBol[0][0]){for(int i=0;i<graficF.punts.length;i++)if(!puntsGrafFinits[i]){
                    if(limPunts[0][0]>graficF.punts[i][0])limPunts[0][0]=graficF.punts[i][0];
                    if(limPunts[0][1]<graficF.punts[i][0])limPunts[0][1]=graficF.punts[i][0];
        }}}
        else for(int j=0;j<limPunts.length;j++){for(int i=0;i<graficF.punts.length;i++)if(!puntsGrafFinits[i]){
            if(limPunts[j][0]>graficF.punts[i][j])limPunts[j][0]=graficF.punts[i][j];
            if(limPunts[j][1]<graficF.punts[i][j])limPunts[j][1]=graficF.punts[i][j];
        }}
        for(int j=0;j<limPunts.length;j++){limPunts(j);}
    }
    static void limPunts(int j){
        limPunts[j][2]=limPunts[j][1]-limPunts[j][0];//[2] segment compres entre limit superior i inferior [1]-[0]
        limPunts[j][3]=limPunts[j][0]+limPunts[j][2]/2;//columna [3] punt central
    }
    static void puntsGraficsInici(){
        coordenades=0;
        for(int j=0;j<puntsTFGraf[0].length;j++)if(puntsTFBol[0][j])coordenades++;
        if(coordenades==1){//si hi ha una coordenada pot esser x o y si es x la cordenada y es fixa al centre
            if(puntsTFBol[0][1]){limPunts[0][0]=0;limPunts[0][1]=2;}
            if(puntsTFBol[0][0]){limPunts[1][0]=0;limPunts[1][1]=2;}
            if(puntsTFBol[0][1]){for(int i=0;i<puntsGraf.length;i++){graficF.punts[i][0]=1;}}
            if(puntsTFBol[0][0]){for(int i=0;i<puntsGraf.length;i++){graficF.punts[i][1]=1;}}
        }
    }
    static void puntsSeleccionats(boolean b){
        int lo=arr_xyPos.size();
        if (!b||lo<2){
            puntsSeleccionatsSub(puntsGraf.length);
            System.arraycopy(historiaIntervalTemps, 0, graficF.historiaIntervalTemps, 0, historiaIntervalTemps.length);
            System.arraycopy(ihistorial, 0, graficF.ihistorial, 0, ihistorial.length);
            graficF.grandariaPunt=splitPan.copiaMatriu(grandariaPunt);
            graficF.puntsTFColor=splitPan.copiaMatriu(puntsTFColor);      
        }
        else puntsSeleccionatsSub(lo);
    }
    static void puntsSeleccionatsSub(int lo){
        graficF.historiaIntervalTemps=new int[lo];
        graficF.ihistorial=new int[lo];
        graficF.puntsTFColor=new Object[lo][2];
        graficF.grandariaPunt=new int[lo][2];
        graficF.historia=new int[lo][graficF.iHistorial][2];//a inici grafic nomes cal la dimensio
        graficF.historiaBol=new boolean[lo][graficF.iHistorial];//a inici grafic nomes cal la dimensio
        graficF.idxHistoria=new int[lo];//a inici grafic nomes cal la dimensio  
        for(int i=0;i<lo;i++)graficF.tempsHistoria[i]=Instant.now();// a inici grafic no cal copia sino nomes la definicio
    }
    static void puntsGrafics(){
        if(coordenades==1){//si hi ha una coordenada pot esser x o y
            if(puntsTFBol[0][1]){for(int i=0;i<puntsGraf.length;i++){graficF.punts[i][1]=puntsGraf[i][1];}}
            if(puntsTFBol[0][0]){for(int i=0;i<puntsGraf.length;i++){graficF.punts[i][0]=puntsGraf[i][0];}}
        }//if(coordenades==2){//si hi ha dues coordendes caldra que aquestes siguin la x i la y la z sera cero
        if(coordenades>1)graficF.punts=splitPan.copiaMatriu(puntsGraf);
        puntsGrafFinits=new boolean[puntsGraf.length];
         for(int i=0;i<puntsGraf.length;i++){if(!isFinite(puntsGraf[i][0])){;puntsGrafFinits[i]=true;}}
         puntsGraficsFi(puntsGraf.length);
         
         /*
         int con=0;boolean[] b=new boolean[puntsGraf.length];
        for(int i=0;i<puntsGraf.length;i++){if(!isFinite(puntsGraf[i][0])){con++;b[i]=true;}}
        if(con>0){
           graficF.punts=new double[puntsGraf.length-con][puntsGraf[0].length];
           con=0;
           for(int i=0;i<puntsGraf.length;i++){
                if(!b[i]){
                    System.arraycopy(puntsGraf[i], 0, graficF.punts[con], 0, puntsGraf[0].length);
                    con++;
                }
           }
       }
      else graficF.punts=splitPan.copiaMatriu(puntsGraf);
      puntsGraficsFi(graficF.punts.length);*/
    }
    static void puntsGraficsSeleccionats(){
        int lo=arr_xyPos.size();
        if(lo==1){
            double[] d=arr_xyPos.get(0);
            int j=(int)d[2];
            double[] centreXYZ=new double[puntsGraf[0].length];
            if(!graficF.tipusGrafic[3])for(int k=0;k<coordenades;k++)centreXYZ[k]=puntsGraf[j][k];
            graficF.punts=new double[puntsGraf.length][coordenades];//els puns a graficF.punts tornen a tenir tres coordenades les cordenades no utilitzades seran cero si son tres les coordenade aquestes seran corretgides per una representacio en dues dimensions
            if(coordenades==1){//si hi ha una coordenada pot esser x o y
               if(puntsTFBol[0][1]){for(int i=0;i<puntsGraf.length;i++){graficF.punts[i][1]=puntsGraf[i][1]-centreXYZ[1];}}
               if(puntsTFBol[0][0]){for(int i=0;i<puntsGraf.length;i++){graficF.punts[i][0]=puntsGraf[i][0]-centreXYZ[0];}}
            }//if(coordenades==2){//si hi ha dues coordendes caldra que aquestes siguin la x i la y la z sera cero
            if(coordenades>1)for(int k=0;k<coordenades;k++){for(int i=0;i<puntsGraf.length;i++){graficF.punts[i][k]=puntsGraf[i][k]-centreXYZ[k];}};
            puntsGraficsFi(puntsGraf.length);
        }
        else{
            graficF.punts=new double[lo][coordenades];
            double[] centreXYZ=new double[coordenades];
            int co=0;
            for(int i=0;i<lo;i++){
                double[] d=arr_xyPos.get(i);
                int j=(int)d[2];
                graficF.puntsTFColor[co]=puntsTFColor[j];
                graficF.historiaIntervalTemps[co]=historiaIntervalTemps[j];
                graficF.ihistorial[co]=ihistorial[j];
                graficF.grandariaPunt[co][0]=grandariaPunt[j][0];
                graficF.grandariaPunt[co][1]=grandariaPunt[j][1];
                if(!graficF.tipusGrafic[3])if(i==0)for(int k=0;k<coordenades;k++)centreXYZ[k]=puntsGraf[j][k];
                if(coordenades==1){//si hi ha una coordenada pot esser x o y
                    if(puntsTFBol[0][1])graficF.punts[co][1]=puntsGraf[j][1]-centreXYZ[1];
                    if(puntsTFBol[0][0])graficF.punts[co][0]=puntsGraf[j][0]-centreXYZ[0];
                }
                if(coordenades>1)for(int k=0;k<coordenades;k++)graficF.punts[co][k]=puntsGraf[j][k]-centreXYZ[k];
                co++;
            }//if(coordenades==2){//si hi ha dues coordendes caldra que aquestes siguin la x i la y la z sera cero
            puntsGraficsFi(lo);
        }
        
    }
    static void puntsGraficsFi(int lo){
        if(contadorBucle==0)limits();
        else nouslimits();
        contadorBucle++;
        for(int i=0;i<lo;i++){
            graficF.igrandariaPunt[i][0]=(int)(graficF.grandariaPunt[i][0]*graficF.minCoordenada/1000);
            if(graficF.igrandariaPunt[i][0]<1)graficF.igrandariaPunt[i][0]=1;
        }
        if(taulaF.coordenades>2)for(int i=0;i<lo;i++){
            graficF.igrandariaPunt[i][1]=(int)(graficF.grandariaPunt[i][1]*graficF.minCoordenada/1000);
            if(graficF.igrandariaPunt[i][1]<1)graficF.igrandariaPunt[i][1]=1;
            graficF.igrandariaPunt[i][0]=(int)(graficF.igrandariaPunt[i][0]-graficF.igrandariaPunt[i][1]*(graficF.punts[i][2]-taulaF.limPunts[2][0])/taulaF.limPunts[2][2]);
            graficF.punts[i][2]=(graficF.punts[i][2]-limPunts[2][0])*graficF.reduccio;
            double z=2*graficF.punts[i][2]/taulaF.limPunts[2][2];// multiplicat per la part de la coordenada que s'ha d'augmentar o disminuir
            for(int j=0;j<2;j++)graficF.punts[i][j]=graficF.punts[i][j]-(graficF.punts[i][j]-taulaF.limPunts[j][3])*z;
        }
    }
    private  boolean moufilBuides(){
        boolean correcte=false;
       for(int i=1;i<matriu.length;i++){
            boolean b=false;
            for(int j=0;j<matriu[0].length;j++)if(!matriu[i][j].equals(""))b=true;
            if(!b){
                correcte=true;
                tl.setRowSelectionInterval(i-1, i-1);
                jmi_treuFila.doClick();
                i--;
                }
            if(matriu.length<=2)i=matriu.length;
        }
        return correcte;
       }
    public  void nouModel(){
        int cc=tl.getColumnCount();
        int[] cols=new int[cc];
        for(int i=0;i<cc;i++)cols[i]=tl.getColumnModel().getColumn(i).getWidth();
        tl.setModel(new model());
        for(int i=1;i<matriu.length;i++)for(int j=0;j<matriu[0].length;j++)tl.setValueAt((Object)matriu[i][j], i-1, j);
        int i=tl.getRowCount();
        for(int j=0;j<i;j++)tl.setRowHeight( j, 18 );
        for(i=0;i<cc;i++)tl.getColumnModel().getColumn(i).setPreferredWidth(cols[i]);
        color();
        splitPan.tTF.repaint();
        editaCela();
    }
     public void color(){
        int cc=tl.getColumnCount();
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
                    for(int i=0;i<cc;i++)if (column == i)lb.setBackground(gmh);
                return lb;
                }
        });
        for(int i=0;i<cc;i++){
            tl.getColumn(tl.getColumnName(i)).setCellRenderer(new DefaultTableCellRenderer() {
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    DefaultTableCellRenderer r = (DefaultTableCellRenderer) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if(!isSelected){
                        if ((row % 2) != 0) r.setBackground(gc);
                        else r.setBackground(gmc);
                        if(column==idxCol)r.setForeground(colors[row]);
                    } 
                    return r;
                }   
            });
        }
    }
    private static void capsalInteg(){
        try{
            matriu[0][0] = "Funci"+Func.rB.getString("o_");
            matriu[0][1] = "S"+Func.rB.getString("i_")+"mbol var.";
            matriu[0][idxIni] = "Condicions";
            matriu[0][idxIf] = "if";
            matriu[0][idxThen] = "then";
            matriu[0][idxN] = "then pres.";
            matriu[0][idxHis] = "recorregut";
            matriu[0][idxTam] = "tamany punt";
            matriu[0][idxEix] = "eix x,y,z";
            matriu[0][idxCol] = "color";
        }
        catch(Exception e){}
    }
    public void valorsInicials(){
        if(matriu==null){reiniciParcial();}
    	else{capsalInteg();}
    }
    private void reiniciParcial(){
        matriu=new String[2][10];
        colors=new Color[1];
        capsalInteg();
        for(int i=0;i<matriu[0].length;i++)matriu[1][i]="";
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
    public static String lectAmplCol(){//utilitzat a splitPan.guardarInfo
        int cc=tl.getColumnCount();
        String s=splitPan.ampladaCols;
        for(int i=0;i<cc;i++)s+=tl.getColumnModel().getColumn(i).getWidth()+"  ";
        s+=splitPan.FIL;
        return s;
    }
    public static void lectintColors(){//utilitzat a splitPan.guardarInfo
        intColors=new String[colors.length][3];
        for(int i=0;i<colors.length;i++){
            try{
            intColors[i][0]=colors[i].getRed()+"";
            intColors[i][1]=colors[i].getGreen()+"";
            intColors[i][2]=colors[i].getBlue()+"";
            }
            catch(Exception e){
                intColors[i][0]="0";
                intColors[i][1]="0";
                intColors[i][2]="0";
            }
        }
    }
    public static void lectColors(){//utilitzat a splitPan.StringAMatrius()
        colors=new Color[matriu.length-1];
        for(int i=0;i<intColors.length;i++){
            colors[i]=new Color(Integer.parseInt(intColors[i][0]),Integer.parseInt(intColors[i][1]),Integer.parseInt(intColors[i][2]));
        }
    }
    public static void fixaAmplCol(int[] amp){//utilitzat a splitPan
        int cc=tl.getColumnCount();
        for(int i=0;i<cc;i++)tl.getColumnModel().getColumn(i).setPreferredWidth(amp[i]);
    }
}
 