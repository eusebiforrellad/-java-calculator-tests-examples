
package Calc;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ResourceBundle;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ToolTipManager;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.ParagraphView;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
public class Func extends JFrame implements ActionListener{
  private static final long serialVersionUID = 1L;
  static ResourceBundle rB = ResourceBundle.getBundle("cadenesReferencia");
  public static boolean tipusFuncioBol;
  static JTextField JFuncio;
  public static boolean ampliarInfo=false;
  public static boolean capturaManualBol=false;
  public static boolean tooltiptxt=true;
  public static boolean mouInvertBol=false;
  public static boolean unirPunts=true;
  public static boolean mostrarTeclat=true;
  public static boolean clearFuncio=false;
  static JMenuItem   jmi_clearText, jmi_adjuntar;
  static JMenuItem  jmi_copi,jmi_cut,jmi_ajunta, jmi_copi1,jmi_cut1,jmi_ajunta1,jmi_copi11,jmi_cut11,jmi_ajunta11, jmi_copi2,jmi_cut2,jmi_ajunta2;
  static JMenuItem jmi_arxAdjunt, jmi_reset, jmi_inici, jmi_taulaFuncions, jmi_font;
  static JMenuItem jmi_save, jmi_saveAs,jmi_save1, jmi_saveAs1,jmi_save11, jmi_saveAs11,jmi_save2, jmi_saveAs2;
  static JMenuItem  jmi_carregar,jmi_carregar1,jmi_carregar11,jmi_carregar2;
  static JMenuItem  jmi_clearPan1, jmi_clearPan11,jmi_clearPan2 ;
  static JMenuItem  jmi_wrap1, jmi_wrap11,jmi_wrap2 ;
  static JMenuItem[]  jmi_activarTecl=new JMenuItem[9], jmi_desactivarTecl=new JMenuItem[9];
  public static JCheckBoxMenuItem cbM1 = new JCheckBoxMenuItem("al inici carregar arxiu");
  public static JCheckBoxMenuItem cbM3 = new JCheckBoxMenuItem("enter > nova funcio");
  public static JCheckBoxMenuItem cbM4 = new JCheckBoxMenuItem("ampliar informes");
  public static JCheckBoxMenuItem cbM5 = new JCheckBoxMenuItem("captura manual gr"+Func.rB.getString("a")+"fic");
  public static JCheckBoxMenuItem cbM6 = new JCheckBoxMenuItem("anular tooltiptext info.");
  public static JCheckBoxMenuItem cbM7 = new JCheckBoxMenuItem("invertir sentit < > (gr"+Func.rB.getString("a")+"fic)");
  public static JCheckBoxMenuItem cbM8 = new JCheckBoxMenuItem("traçar linia entre punts gr"+Func.rB.getString("a")+"fics");
  public static JCheckBoxMenuItem cbM9 = new JCheckBoxMenuItem("linia regr.exp.pink");
  public static JCheckBoxMenuItem cbM10 = new JCheckBoxMenuItem("linia regr.calc.cyan");
  public static JPanel pan,panJP;
  public static String operador="^*/+<>="+splitPan.menor+splitPan.major;
  public static String[] funcions = {"log","ln","sin","asin","cos","acos","tan","atan","abs","rad","gra","rod","int","fra","sinh","cosh","tanh","!","rand"}; //per introduir mes funcions modificar a suportID: simbOpFun(int i); a Cal  'caseOpFun(int i)'  ha Cur 'caseOpFun(int i)', a Func funcions; calculFun() a splitPan i
  public static String cadenaFuncions = "";
  public static String avis="advertencia: ";
  public static String error="error: ";
  public static final String increm=rB.getString("increment");
  public static final String sumat=rB.getString("sumatori");
  static boolean hihaResultat=false;
  static boolean graficReg=false;
  static boolean enterBol;
  public static JFrame fr;
  static JTextPane[] textDeco;
  static JTextArea textDeco1;
  public static JScrollPane[] text1;
  public static JScrollPane text2;
  static Func funcImpl;
  static splitPan split;
  public static int posx = 0;
  public static int posy = 0;
  public static int longX = 0;
  public static int longY = 0;
  public static byte saltBol=0;
  public static boolean saltBol1=false;
  public Action copi2;
  public Action copi1;
  public Action copi;
  public static boolean analisiPreviBol=false;//true indica que el conjunt de funcions parametres i variables es concordant 
  public static String titol="";
  public static JButton enter,minim,reset;
  public static JButton para,cont,cap;
  public static JButton carr,guar,guarC;
  public static JButton enterT,minimT;
  public static JButton paraT,contT,enrrT;
  public static JButton capT,carrT,guarT,guarCT;
  public static boolean minimBol;
  public static boolean contBol;//si true s'esta executant el thread de minim
  public static boolean paraCalcBol=false;//s'activa desactiva els procediments trueFalse i falseTrue
  public static boolean obrirDarrerArxiu;
  public static String resultat;
  public static boolean noArrodonir;
  static Border borN = BorderFactory.createLineBorder(Color.black, 1);
  static Border borV = BorderFactory.createLineBorder(Color.red, 1);
  public static int idxTeclat;
  public static boolean argumentRutaBol=false;
  //>>>>>>>>tipusInici
  public static boolean tipusInici;//fer ho false quans'edita el programs i s'executa amb netbeans  i true quan es compila i s'executara el programa compilat
  //>>>>>>>>
  //public static boolean reinici=false;//si true caldra fer un reset al finalitzar una sequencia d'operacions 
  static String rutaArxProv="";
  static String userDir;
  static char barraUserDir;
  public static String infoTexts="";
  public static String infoTexts1="";
  public static StyledDocument style;
  public  static Border border;
  public static boolean ratllaBol;
  public static boolean ratllaBol1;
  public boolean ratllaLimBol;
  public static Action arxAdjunt = new arxAdjunt();
  public static class arxAdjunt extends AbstractAction {private static final long serialVersionUID = 1L;public arxAdjunt() {putValue(NAME, "obrir arxiu adjunt");}public void actionPerformed(ActionEvent e0) {}}
  Thread CAL;
  static ActionListener[] infoAL=new ActionListener[10];
  public Func() {
    //ATENCIO permetre hiHaErr() en el programa compilat;
    hiHaErr();
    userDir=System.getProperty("user.dir");
    splitPan.colorText=splitPan.FIL+"cOlOr: ";
    //
    //ATENCIO desde netbeans "tipusInici=false" pel programa compilat fer "tipusInici=true" desactivar les dues fileres seguents i activar la quarta filera seguent;
    //tipusInici=false;
    //if(!userDir.equals("/Volumes/LLAPIS1X/aplic/pro/edi/netbeans/Calcul"))tipusInici=true;
    //pel programa compilat definitiu deixar nomes la linia seguent 
    tipusInici=true;
    //
    Dimension tamP = Toolkit.getDefaultToolkit().getScreenSize();
    longX=tamP.width;
    longY=tamP.height;
    JFuncio= new JTextField();
    textDeco = new JTextPane[2];
    textDeco1 = new JTextArea();
    text1=new JScrollPane[2];
    font.fontIni("");
    barraUserDir=barraRuta(userDir);
    border = BorderFactory.createLineBorder(Color.blue, 1);
    new taulaC(true).INI();new taulaV(true).INI();new taulaP(true).INI();
    new taulaI(true).INI();new taulaD(true).INI();new taulaF(true).INI();
    splitPan.tTC=new taulaC();
    teclat.teclatFil=6;
    teclat.teclatCol=12;
    Cur.mitatPuntsGraf=new int[11];
    Cur.indexPunts=2;
    if(!rutaArxProv.equals("")){//s'executa quant al carregar el programa despres del reset hi ha arguments en el main
        String s=titol;
        titol=rutaArxProv;
        splitPan.FileAString(new File(titol));
        obrirDarrerArxiu=true;
        File f=new File(rutaArxProv);
        if(f.exists())f.delete();
        rutaArxProv="";
        titol=s;
    }
    else if(!titol.equals("")||lectRuta()){
        splitPan.FileAString(new File(titol));
        obrirDarrerArxiu=true;
    }
    else{
        titol="regressi"+rB.getString("o_");
        obrirDarrerArxiu=false;
    }
    textDeco[0]=new JTextPane();
    textDeco[1]=new JTextPane();
    textDeco[0].setEditorKit(new WrapEditorKit());
    JPanel saltSi = new JPanel( new BorderLayout());
    saltSi.add( textDeco[0] );
    text1[0] = new JScrollPane(saltSi);
    text1[0].setViewportView(textDeco[0]);
    textDeco[1].setEditorKit(new WrapEditorKit());
    JPanel saltNo = new JPanel( new BorderLayout());
    saltNo.add( textDeco[1] );
    text1[1] = new JScrollPane(saltNo);
    text1[1].setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    text1[1].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    text2 = new JScrollPane(Func.textDeco1);
    text2.setVerticalScrollBarPolicy(20);
    text2.setHorizontalScrollBarPolicy(30);
    text2.setMinimumSize(new Dimension(10, 10));
    enterT=new JButton(Func.rB.getString("enter"));
    minimT=new JButton("min");
    paraT=new JButton("stop");
    contT=new JButton("cont");
    enrrT=new JButton(Func.rB.getString("back"));
    capT=new JButton("capt");
    carrT=new JButton("load");
    guarT=new JButton("save");
    guarCT=new JButton("save as");
    enterT.setPreferredSize(new Dimension(25,30));
    minimT.setPreferredSize(new Dimension(25,30));
    paraT.setPreferredSize(new Dimension(25,30));
    contT.setPreferredSize(new Dimension(25,30));
    enrrT.setPreferredSize(new Dimension(25,30));
    capT.setPreferredSize(new Dimension(25,30));
    carrT.setPreferredSize(new Dimension(25,30));
    guarT.setPreferredSize(new Dimension(25,30));
    guarCT.setPreferredSize(new Dimension(25,30));
    enterT.setMargin(new Insets(-5,-5,-5,-5));
    minimT.setMargin(new Insets(-5,-5,-5,-5));
    paraT.setMargin(new Insets(-5,-5,-5,-5));
    contT.setMargin(new Insets(-5,-5,-5,-5));
    enrrT.setMargin(new Insets(-5,-5,-5,-5));
    capT.setMargin(new Insets(-5,-5,-5,-5));
    carrT.setMargin(new Insets(-5,-5,-5,-5));
    guarT.setMargin(new Insets(-5,-5,-5,-5));
    guarCT.setMargin(new Insets(-5,-5,-5,-5));
    minim=new JButton("minim");
    para=new JButton("stop");
    cont=new JButton("cont.");
    cap=new JButton("capt.");
    reset=new JButton("reset");
    carr=new JButton("load");
    guar=new JButton("save");
    guarC=new JButton("saveAs");
    enter=new JButton("enter");
    splitPan.funcionsPar=new String[funcions.length];
    System.arraycopy(funcions,0,splitPan.funcionsPar,0,funcions.length);
    ordenaMatriu(splitPan.funcionsPar);
    for(int i=0;i<funcions.length;i++)splitPan.funcionsPar[i]+="(";
    enter.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){funcImpl.threadcalcula();}});
    minim.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){funcImpl.threadcalcul();}});
    reset.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){guardaArxiuProvis();}});
    para.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){parada();}});
    cont.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){cont();}});
    cap.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){capt();}});
    carr.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){splitPan.carregaArx();}});
    guar.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){splitPan.guardarArx();}});
    guarC.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){splitPan.guardarArxCom();}});
    enterT.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){Toolkit.getDefaultToolkit().beep();funcImpl.threadcalcula();}});
    minimT.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){Toolkit.getDefaultToolkit().beep();funcImpl.threadcalcul();}});
    capT.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){Toolkit.getDefaultToolkit().beep();capt();}});
    paraT.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){Toolkit.getDefaultToolkit().beep();parada();}});
    contT.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){Toolkit.getDefaultToolkit().beep();cont();}});
    enrrT.setFocusable(false);
    enrrT.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){Toolkit.getDefaultToolkit().beep();teclat.back=true;splitPan.tecl.cercaT();teclat.back=false;}});
    carrT.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){Toolkit.getDefaultToolkit().beep();splitPan.carregaArx();}});
    guarT.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){Toolkit.getDefaultToolkit().beep();splitPan.guardarArx();}});      
    guarCT.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){Toolkit.getDefaultToolkit().beep();splitPan.guardarArxCom();}});      
    JFuncio.addKeyListener(new KeyListener(){
        public void keyPressed(KeyEvent e) {}
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode()!=KeyEvent.VK_ENTER) analisiPreviBol=false;
            else{if (!enterBol)calcula();}}
        public void keyTyped(KeyEvent e) {}
    });
    infoAL[0] = new ActionListener() {public void actionPerformed(ActionEvent e) {
        Func.obrirDarrerArxiu=!Func.obrirDarrerArxiu;
        if(!obrirDarrerArxiu)elimCarrIni();
    }};
    cbM1.addActionListener(infoAL[0]);taulaF.cbM1.addActionListener(infoAL[0]);
    infoAL[1] = new ActionListener() {public void actionPerformed(ActionEvent e) {ampliarInfo=!ampliarInfo;}};
    cbM4.addActionListener(infoAL[1]);taulaF.cbM4.addActionListener(infoAL[1]);
    infoAL[2] = new ActionListener() {public void actionPerformed(ActionEvent e) {tooltiptxt=!tooltiptxt;}};
    cbM6.addActionListener(infoAL[2]);taulaF.cbM6.addActionListener(infoAL[2]);
    infoAL[3] = new ActionListener() {public void actionPerformed(ActionEvent e) {
        taulaC.cbM.setIcon(Func.Moucolumna());
        if(taulaC.scrollCols==0)taulaC.scrollCols=2;
        else taulaC.scrollCols=0;
        taulaC.tl.setAutoResizeMode(taulaC.scrollCols);
    }};
    taulaC.cbM.addActionListener(infoAL[3]);
    taulaF.cbM.setIcon(Func.Moucolumna());
    infoAL[7] = new ActionListener() {public void actionPerformed(ActionEvent e) {
        if(taulaF.scrollCols==0)taulaF.scrollCols=2;
        else taulaF.scrollCols=0;
        taulaF.tl.setAutoResizeMode(taulaF.scrollCols);
    }};
    taulaF.cbM.addActionListener(infoAL[7]);
    jmi_arxAdjunt = new JMenuItem(arxAdjunt);
    taulaF.jmi_arxAdjunt = new JMenuItem(arxAdjunt);
    infoAL[8] = new ActionListener() {public void actionPerformed(ActionEvent e) {trasllat.presentaDoc(trasllat.hihaArxiuAdjunt());}};
    jmi_arxAdjunt.addActionListener(infoAL[8]);taulaF.jmi_arxAdjunt.addActionListener(infoAL[8]);
    
    taulaI.cbM.setIcon(Func.Moucolumna1());taulaI.cbM1.setIcon(Func.Moucolumna1());
    infoAL[9] = new ActionListener() {public void actionPerformed(ActionEvent e) {
        if(taulaI.scrollCols==2)taulaI.scrollCols=1;
        else taulaI.scrollCols=2;
        taulaI.tl.setAutoResizeMode(taulaI.scrollCols);
        taulaI.tlF.setAutoResizeMode(taulaI.scrollCols);
    }};
    taulaI.cbM.addActionListener(infoAL[9]);taulaI.cbM1.addActionListener(infoAL[9]);
    taulaD.cbM.setIcon(Func.Moucolumna());
    infoAL[4] = new ActionListener() {public void actionPerformed(ActionEvent e) {
        if(taulaD.scrollCols==0)taulaD.scrollCols=2;
        else taulaD.scrollCols=0;
        taulaD.tl.setAutoResizeMode(taulaD.scrollCols);
    }};
    taulaD.cbM.addActionListener(infoAL[4]);
    taulaP.cbM.setIcon(Func.Moucolumna());
    infoAL[5] = new ActionListener() {public void actionPerformed(ActionEvent e) {
        if(taulaP.scrollCols==0)taulaP.scrollCols=2;
        else taulaP.scrollCols=0;
        taulaP.tl.setAutoResizeMode(taulaP.scrollCols);
    }};
    taulaP.cbM.addActionListener(infoAL[5]);
    taulaV.cbM.setIcon(Func.Moucolumna());
    infoAL[6] = new ActionListener() {public void actionPerformed(ActionEvent e) {
        if(taulaV.scrollCols==0)taulaV.scrollCols=2;
        else taulaV.scrollCols=0;
        taulaV.tl.setAutoResizeMode(taulaV.scrollCols);
    }};
    taulaV.cbM.addActionListener(infoAL[6]);
    JFuncio.addMouseListener(new TaulesPopup(popupM()));
    textDeco[0].addMouseListener(new TaulesPopup(popupM1()));
    textDeco[1].addMouseListener(new TaulesPopup(popupM11()));
    textDeco1.addMouseListener(new TaulesPopup(popupM2()));
    enter.addMouseListener(new TaulesPopup(popupteclatSi(0)));
    minim.addMouseListener(new TaulesPopup(popupteclatSi(1)));
    reset.addMouseListener(new TaulesPopup(popupteclatSi(2)));
    para.addMouseListener(new TaulesPopup(popupteclatSi(3)));
    cont.addMouseListener(new TaulesPopup(popupteclatSi(4)));
    cap.addMouseListener(new TaulesPopup(popupteclatSi(5)));
    carr.addMouseListener(new TaulesPopup(popupteclatSi(6)));
    guar.addMouseListener(new TaulesPopup(popupteclatSi(7)));
    guarC.addMouseListener(new TaulesPopup(popupteclatSi(8)));
    enterT.addMouseListener(new TaulesPopup(popupteclatNo(0)));
    minimT.addMouseListener(new TaulesPopup(popupteclatNo(1)));
    enrrT.addMouseListener(new TaulesPopup(popupteclatNo(2)));
    paraT.addMouseListener(new TaulesPopup(popupteclatNo(3)));
    contT.addMouseListener(new TaulesPopup(popupteclatNo(4)));
    capT.addMouseListener(new TaulesPopup(popupteclatNo(5)));
    carrT.addMouseListener(new TaulesPopup(popupteclatNo(6)));
    guarT.addMouseListener(new TaulesPopup(popupteclatNo(7)));
    guarCT.addMouseListener(new TaulesPopup(popupteclatNo(8)));

    novaFinestra(false);
    ttt(tooltiptxt);
  }
  public JPopupMenu popupteclatSi(int i) {//textDeco1
    JPopupMenu popup = new JPopupMenu();
    jmi_activarTecl[i] = new JMenuItem("activar teclat");
    jmi_activarTecl[i].addActionListener(this);
    popup.add(jmi_activarTecl[i]);
    return popup;
  }
  public JPopupMenu popupteclatNo(int i) {
    JPopupMenu popup = new JPopupMenu();
    jmi_desactivarTecl[i] = new JMenuItem("desactivar teclat");
    jmi_desactivarTecl[i].addActionListener(this);
    popup.add(jmi_desactivarTecl[i]);
    return popup;
  }
  public JPopupMenu popupM2() {//textDeco1
    JPopupMenu popup = new JPopupMenu();
    jmi_copi2 = new JMenuItem("copiar  "); jmi_copi2.setIcon(Copiar());
    jmi_copi2.addActionListener(this); 
    popup.add(jmi_copi2);
    jmi_cut2 = new JMenuItem("cut  "); jmi_cut2.setIcon(Copiar());
    jmi_cut2.addActionListener(this); 
    popup.add(jmi_cut2);
    jmi_ajunta2 = new JMenuItem("ajuntar  "); jmi_ajunta2.setIcon(Engan());
    jmi_ajunta2.addActionListener(this); 
    popup.add(jmi_ajunta2);
    popup.addSeparator();
    jmi_clearPan2  = new JMenuItem("clear pan "); jmi_clearPan2.setIcon(Borrar());
    jmi_clearPan2.addActionListener(this);
    popup.add(jmi_clearPan2);
    jmi_wrap2 = new JMenuItem("wrap ");jmi_wrap2.setIcon(Wrap());
    jmi_wrap2.addActionListener(this);
    popup.add(jmi_wrap2);
    popup.addSeparator();
    jmi_carregar2 = new JMenuItem("carregar (load)"); jmi_carregar2.setIcon(Obrirarx());
    jmi_carregar2.addActionListener(this);
    popup.add( jmi_carregar2);
    jmi_save2 = new JMenuItem("guardar (save)"); jmi_save2.setIcon(Guarda());
     jmi_save2.addActionListener(this);
    popup.add( jmi_save2);
     jmi_saveAs2 = new JMenuItem("guardar (save as)");jmi_saveAs2.setIcon(GuardaC());
    jmi_saveAs2.addActionListener(this);
    popup.add(jmi_saveAs2);
    return popup;
  }
  public JPopupMenu popupM1() {//textDeco
    JPopupMenu popup = new JPopupMenu();
    jmi_copi1 = new JMenuItem("copiar "); jmi_copi1.setIcon(Copiar());
    jmi_copi1.addActionListener(this); 
    popup.add(jmi_copi1);
    jmi_cut1 = new JMenuItem("cut "); jmi_cut1.setIcon(Copiar());
    jmi_cut1.addActionListener(this); 
    popup.add(jmi_cut1);
    jmi_ajunta1 = new JMenuItem("ajuntar "); jmi_ajunta1.setIcon(Engan());
    jmi_ajunta1.addActionListener(this); 
    popup.add(jmi_ajunta1);
    popup.addSeparator();
    jmi_clearPan1  = new JMenuItem("clear pan"); jmi_clearPan1.setIcon(Borrar());
    jmi_clearPan1.addActionListener(this);
    popup.add(jmi_clearPan1);
    jmi_wrap1 = new JMenuItem("wrap");jmi_wrap1.setIcon(Wrap());
    jmi_wrap1.addActionListener(this);
    popup.add(jmi_wrap1);
    popup.addSeparator();
    jmi_carregar1 = new JMenuItem("carregar (load)"); jmi_carregar1.setIcon(Obrirarx());
    jmi_carregar1.addActionListener(this);
    popup.add( jmi_carregar1);
    jmi_save1 = new JMenuItem("guardar (save)"); jmi_save1.setIcon(Guarda());
     jmi_save1.addActionListener(this);
    popup.add( jmi_save1);
     jmi_saveAs1 = new JMenuItem("guardar (save as)");jmi_saveAs1.setIcon(GuardaC());
    jmi_saveAs1.addActionListener(this);
    popup.add(jmi_saveAs1);
    return popup;
  }  
  public JPopupMenu popupM11() {//textDeco
    JPopupMenu popup = new JPopupMenu();
    jmi_copi11 = new JMenuItem("copiar "); jmi_copi11.setIcon(Copiar());
    jmi_copi11.addActionListener(this); 
    popup.add(jmi_copi11);
    jmi_cut11 = new JMenuItem("cut "); jmi_cut11.setIcon(Copiar());
    jmi_cut11.addActionListener(this); 
    popup.add(jmi_cut11);
    jmi_ajunta11 = new JMenuItem("ajuntar "); jmi_ajunta11.setIcon(Engan());
    jmi_ajunta11.addActionListener(this); 
    popup.add(jmi_ajunta11);
    popup.addSeparator();
    jmi_clearPan11  = new JMenuItem("clear pan"); jmi_clearPan11.setIcon(Borrar());
    jmi_clearPan11.addActionListener(this);
    popup.add(jmi_clearPan11);
    jmi_wrap11 = new JMenuItem("wrap");jmi_wrap11.setIcon(Wrap());
    jmi_wrap11.addActionListener(this);
    popup.add(jmi_wrap11);
    popup.addSeparator();
    jmi_carregar11 = new JMenuItem("carregar (load)"); jmi_carregar11.setIcon(Obrirarx());
    jmi_carregar11.addActionListener(this);
    popup.add( jmi_carregar11);
    jmi_save11 = new JMenuItem("guardar (save)"); jmi_save11.setIcon(Guarda());
     jmi_save11.addActionListener(this);
    popup.add( jmi_save11);
     jmi_saveAs11 = new JMenuItem("guardar (save as)");jmi_saveAs11.setIcon(GuardaC());
    jmi_saveAs11.addActionListener(this);
    popup.add(jmi_saveAs11);
    return popup;
  }
  public JPopupMenu popupM() {
    JPopupMenu popup = new JPopupMenu();
    jmi_copi = new JMenuItem("copiar");  jmi_copi.setIcon(Copiar());
    jmi_copi.addActionListener(this);  popup.add( jmi_copi);
    jmi_cut = new JMenuItem("cut");  jmi_cut.setIcon(Copiar());
    jmi_cut.addActionListener(this);  popup.add( jmi_cut);
    jmi_ajunta = new JMenuItem("ajuntar");  jmi_ajunta.setIcon(Engan());
    jmi_ajunta.addActionListener(this); 
    popup.add( jmi_ajunta);
    popup.addSeparator();
    jmi_clearText = new JMenuItem("clear text");jmi_clearText.setIcon(Borrar());
    jmi_clearText.addActionListener(this);
    popup.add(jmi_clearText);
    popup.addSeparator();
    jmi_carregar = new JMenuItem("carregar (load)");jmi_carregar.setIcon(Obrirarx());
    jmi_carregar.addActionListener(this);
    popup.add(jmi_carregar);
   jmi_save = new JMenuItem("guardar (save)");jmi_save.setIcon(Guarda());
    jmi_save.addActionListener(this);
    popup.add(jmi_save);
   jmi_saveAs = new JMenuItem("guardar (save as)");jmi_saveAs.setIcon(GuardaC());
    jmi_saveAs.addActionListener(this);
    popup.add(jmi_saveAs);
    popup.addSeparator();
    jmi_adjuntar = new JMenuItem("adjuntar arxiu");
    jmi_adjuntar.addActionListener(this);
    popup.add(jmi_adjuntar);
    popup.add(jmi_arxAdjunt);
    popup.addSeparator();
    jmi_reset = new JMenuItem("reset");jmi_reset.setIcon(Nouarx1());
    jmi_reset.addActionListener(this);
    popup.add(jmi_reset);
    jmi_inici = new JMenuItem("inici");jmi_inici.setIcon(Nouarx());
    jmi_inici.addActionListener(this);
    popup.add(jmi_inici);
    popup.addSeparator();
    jmi_taulaFuncions = new JMenuItem("taula de funcions");
    jmi_taulaFuncions.addActionListener(this);
    popup.add(jmi_taulaFuncions);
    popup.addSeparator();
    jmi_font = new JMenuItem("font");
    jmi_font.addActionListener(this);
    popup.add(jmi_font);
    popup.addSeparator();
    
    popup.add(cbM1);
    cbM1.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
    cbM1.setState(obrirDarrerArxiu);Func.cbM1.setState(Func.obrirDarrerArxiu);
    ActionListener Info7 = new ActionListener() {public void actionPerformed(ActionEvent e) {Func.clearFuncio=!Func.clearFuncio;}};
    cbM3.addActionListener(Info7);popup.add(cbM3);cbM3.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
    cbM3.setState(clearFuncio);
    popup.add(cbM4);cbM4.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
    cbM4.setState(ampliarInfo);
    ActionListener Info2 = new ActionListener() {public void actionPerformed(ActionEvent e) {Func.capturaManualBol=!Func.capturaManualBol;}};
    cbM5.addActionListener(Info2);popup.add(cbM5);cbM5.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
    cbM5.setState(capturaManualBol);
    popup.add(cbM6);cbM6.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
    cbM6.setState(Func.tooltiptxt);
    ActionListener Info4 = new ActionListener() {public void actionPerformed(ActionEvent e) {Func.mouInvertBol=!Func.mouInvertBol;}};
    cbM7.addActionListener(Info4);popup.add(cbM7);cbM7.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
    cbM7.setState(Func.mouInvertBol);
    ActionListener Info5 = new ActionListener() {public void actionPerformed(ActionEvent e) {Func.unirPunts=!Func.unirPunts;}};
    cbM8.addActionListener(Info5);popup.add(cbM8);cbM8.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
    cbM8.setState(unirPunts);
    ActionListener Info10 = new ActionListener() {public void actionPerformed(ActionEvent e) {
    ratllaBol1=!ratllaBol1;
    if (!Cur.regresioGrafBol)grafReg.ratlla=new int[0][0];
    }};
    cbM9.addActionListener(Info10);popup.add(cbM9);cbM9.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
    cbM9.setState(ratllaBol1);
    ActionListener Info9 = new ActionListener() {public void actionPerformed(ActionEvent e) {
    ratllaBol=!ratllaBol;
    if (!Cur.regresioGrafBol)grafReg.ratlla=new int[0][0];
    }};
    cbM10.addActionListener(Info9);popup.add(cbM10);cbM10.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
    cbM10.setState(ratllaBol);
    return popup;
  }
  public void actionPerformed(ActionEvent e) {
    if ("copiar  ".equals(e.getActionCommand())) {
        Func.clipboard c = new Func.clipboard();
        String sel=textDeco1.getSelectedText();
        if(sel==null){sel="";}
        c.copiaClip(sel);
        return;
      }
    if ("copiar ".equals(e.getActionCommand())) {
      Func.clipboard c = new Func.clipboard();
      String sel=textDeco[saltBol].getSelectedText();
      if(sel==null){sel="";}
      c.copiaClip(sel);
      return;
    }
    if ("copiar".equals(e.getActionCommand())) {
      Func.clipboard c = new Func.clipboard();
      String sel=JFuncio.getSelectedText();
      if(sel==null){sel="";}
      c.copiaClip(sel);
      return;
    }
    if ("cut  ".equals(e.getActionCommand())) {
        Func.clipboard c = new Func.clipboard();
        String sel=textDeco1.getSelectedText();
        if(sel==null){sel="";}
        c.copiaClip(sel);
        textDeco1.replaceSelection("");
        return;
    }
    if ("cut ".equals(e.getActionCommand())) {
      Func.clipboard c = new Func.clipboard();
      String sel=textDeco[saltBol].getSelectedText();
      if(sel==null){sel="";}
      c.copiaClip(sel);
      textDeco[saltBol].replaceSelection("");
      return;
    }
    if ("cut".equals(e.getActionCommand())) {
      Func.clipboard c = new Func.clipboard();
      String sel=JFuncio.getSelectedText();
      if(sel==null){sel="";}
      c.copiaClip(sel);
      JFuncio.replaceSelection("");
      return;
    }
    if ("ajuntar".equals(e.getActionCommand())) {
      Func.clipboard c = new Func.clipboard();
      String s1;
      int ini=JFuncio.getSelectionStart();
      int fin=JFuncio.getSelectionEnd();
      try { s1 = c.ajuntarClip(); } catch (UnsupportedFlavorException | IOException ex) {s1 = ""; }
      
      String t=JFuncio.getText();
      int car=JFuncio.getCaretPosition();
      if(fin!=0){t=t.substring(0,ini)+t.substring(fin);car=ini;}
      
      t=t.substring(0,car)+s1+t.substring(car,t.length());
      JFuncio.setText(t);
      analisiPreviBol=false;
      return;
    }
    if ("ajuntar ".equals(e.getActionCommand())) {
      Func.clipboard c = new Func.clipboard();
      String s1;
      try { s1 = c.ajuntarClip(); } catch (UnsupportedFlavorException | IOException ex) {s1 = ""; }
      int car=textDeco[saltBol].getCaretPosition();
      textDeco[saltBol].replaceSelection(s1);
      return;
    }
    if ("ajuntar  ".equals(e.getActionCommand())) {
      Func.clipboard c = new Func.clipboard();
      String s1;
      try { s1 = c.ajuntarClip(); } catch (UnsupportedFlavorException | IOException ex) {s1 = ""; }
      int car=textDeco1.getCaretPosition();
      textDeco1.replaceSelection(s1);
      return;
    }
    if(("wrap").equals(e.getActionCommand())){
        style = textDeco[saltBol].getStyledDocument();
        if(saltBol==0)saltBol=1;
        else saltBol=0;
        Document d2 =textDeco[saltBol].getDocument();
        for(int i=0; i<style.getLength();i++) {
            try {d2.insertString(d2.getLength(), style.getText(i, 1), style.getCharacterElement(i).getAttributes());} catch (Exception ex) {}
        }
        fixaSplit();
    }
    if(("wrap ").equals(e.getActionCommand())){
        String s=textDeco1.getText();
        saltBol1=!saltBol1;
        if(!saltBol1)textDeco1.setLineWrap(false);
        else textDeco1.setLineWrap(true);
        textDeco1.setText(s);
    }
    if ("taula de funcions".equals(e.getActionCommand())) {
        tipusFuncioBol=true;funcImpl.reiniciparcial();
        return;
    }
    if(("guardar (save as)").equals(e.getActionCommand())) splitPan.guardarArxCom();
    if(("guardar (save)").equals(e.getActionCommand()))splitPan.guardarArx();
    if(("carregar (load)").equals(e.getActionCommand())) splitPan.carregaArx();
    if(("clear pan ").equals(e.getActionCommand())) Func.textDeco1.setText("");
    if(("clear pan").equals(e.getActionCommand())) Func.textDeco[saltBol].setText("");
    if(("clear text").equals(e.getActionCommand())) Func.JFuncio.setText("");
    if(("inici").equals(e.getActionCommand())){clearAll();}
    if(("reset").equals(e.getActionCommand())) { guardaArxiuProvis();}
    if(("adjuntar arxiu").equals(e.getActionCommand())) trasllat.traslladar();
    if(("obrir arxiu adjunt").equals(e.getActionCommand()))trasllat.presentaDoc(trasllat.hihaArxiuAdjunt());
    if ("activar teclat".equals(e.getActionCommand())) {
        Func.mostrarTeclat=true;Func.funcImpl.reiniciparcial();
        return;
    }
    if ("desactivar teclat".equals(e.getActionCommand())) {
        Func.mostrarTeclat=false;Func.funcImpl.reiniciparcial();
        return;
    }
    if ("font".equals(e.getActionCommand())) {
        font.inici();
        return;
    }
  }
  public static void font(){
    Font fo = new Font(font.tipus, font.estil, font.tamany);
    textDeco[0].setFont(fo);
    textDeco[1].setFont(fo);
    textDeco1.setFont(fo);
    minim.setFont(fo);para.setFont(fo);cont.setFont(fo);cap.setFont(fo);
    reset.setFont(fo);carr.setFont(fo);guar.setFont(fo);guarC.setFont(fo);enter.setFont(fo);
    enterT.setFont(fo);minimT.setFont(fo);paraT.setFont(fo);contT.setFont(fo);
    enrrT.setFont(fo);capT.setFont(fo);carrT.setFont(fo);guarT.setFont(fo);guarCT.setFont(fo);
    cbM1.setFont(fo);
    cbM3.setFont(fo);
    cbM4.setFont(fo);
    cbM5.setFont(fo);
    cbM6.setFont(fo);
    cbM7.setFont(fo);
    cbM8.setFont(fo);
    cbM9.setFont(fo);
    cbM10.setFont(fo);
    jmi_copi.setFont(fo);jmi_copi1.setFont(fo);jmi_copi11.setFont(fo);jmi_copi2.setFont(fo);
    jmi_cut.setFont(fo);jmi_cut1.setFont(fo);jmi_cut11.setFont(fo);jmi_cut2.setFont(fo);
    jmi_ajunta.setFont(fo);jmi_ajunta1.setFont(fo);jmi_ajunta11.setFont(fo);jmi_ajunta2.setFont(fo);
    jmi_clearText.setFont(fo);
    jmi_carregar.setFont(fo); jmi_carregar1.setFont(fo); jmi_carregar11.setFont(fo); jmi_carregar2.setFont(fo);
    jmi_save.setFont(fo); jmi_save1.setFont(fo); jmi_save11.setFont(fo); jmi_save2.setFont(fo);
    jmi_saveAs.setFont(fo);jmi_saveAs1.setFont(fo); jmi_saveAs11.setFont(fo); jmi_saveAs2.setFont(fo);
    jmi_adjuntar.setFont(fo);
    jmi_arxAdjunt.setFont(fo);
    jmi_reset.setFont(fo);
    jmi_inici.setFont(fo);
    jmi_taulaFuncions.setFont(fo);
    jmi_font.setFont(fo);
    jmi_clearPan1.setFont(fo);jmi_clearPan11.setFont(fo);jmi_clearPan2.setFont(fo);
    jmi_wrap1.setFont(fo);jmi_wrap11.setFont(fo);jmi_wrap2.setFont(fo);
    for(int i=0;i<jmi_activarTecl.length;i++){ jmi_activarTecl[i].setFont(fo); jmi_desactivarTecl[i].setFont(fo);}
  }
    public static void ordenaMatriu(String[] mV){
        if(mV.length<2)return;
        for(int i=0;i<mV.length;i++)for(int j=i+1;j<mV.length;j++)if(mV[i].length()<mV[j].length()){
              String s=mV[i];
              mV[i]=mV[j];
              mV[j]=s;
          }
    }
  ///ATENCIO depenent de si s'executa desde  netbeans cal modificar el procediment seguent
  public void reiniciparcial() {
    surt();
    splitPan.tTC=new taulaC();
    splitPan.columnesC=splitPan.ampladaCols(taulaC.lectAmplCol());
    splitPan.columnesP=splitPan.ampladaCols(taulaP.lectAmplCol());
    splitPan.columnesV=splitPan.ampladaCols(taulaV.lectAmplCol());
    splitPan.columnesD=splitPan.ampladaCols(taulaD.lectAmplCol());
    splitPan.columnesI=splitPan.ampladaCols(taulaI.lectAmplCol());
    splitPan.splitSuport();
    novaFinestra(true);
  }
  public static void guardaTxtiColors(){
        Func.style = Func.textDeco[Func.saltBol].getStyledDocument();
    }
    public static void carregaTxtiColors(){
        Func.textDeco[Func.saltBol].setStyledDocument(Func.style);
    }
  public void surt(){
    posx = fr.getX();
    posy = fr.getY();
    fr.dispose();
  }
  public void novaFinestra(boolean b) {
    if(!trasllat.hihaArxiuAdjunt().equals(""))arxAdjunt.setEnabled(true);
    else arxAdjunt.setEnabled(false);
    if(!tipusFuncioBol){
        panJP=new JPanel(new GridLayout(1,8,0,0));
        panJP.add(enterT); panJP.add(minimT); panJP.add(paraT);panJP.add(contT);panJP.add(enrrT);panJP.add(capT);panJP.add(carrT);panJP.add(guarT);
        pan=new JPanel(new GridLayout(3, 4));
        pan.add(enter);pan.add(minim);pan.add(reset);pan.add(para);pan.add(cont);pan.add(cap);pan.add(carr);pan.add(guar);pan.add(guarC);
    }
    else{
        panJP=new JPanel(new GridLayout(1,6,0,0));
        panJP.add(enterT);panJP.add(paraT);panJP.add(enrrT);panJP.add(carrT);panJP.add(guarT);panJP.add(guarCT);
        pan=new JPanel(new GridLayout(3, 3));
        pan.add(enter);pan.add(para);pan.add(reset);pan.add(carr);pan.add(guar);pan.add(guarC);
    }
    ToolTipManager.sharedInstance().setDismissDelay(60000);
    resultat="";
    analisiPreviBol=false;
    grafReg.ratlla=new int[0][0];
    fr = new JFrame("");
    fr.setTitle(titol);
    fr.addWindowListener(new WindowAdapter() {public void windowClosing (WindowEvent e){
        if(obrirDarrerArxiu) escriuRuta();
        System.exit(0);
    }});
    if(!b){
        splitPan.carrTextAtributs(infoTexts);
        Func.textDeco1.setLineWrap(saltBol1);
        Func.textDeco1.setText(infoTexts1);
        textDeco[0].setForeground(Color.BLUE);
        textDeco[1].setForeground(Color.BLUE);
        textDeco1.setForeground(Color.BLUE);
    }
    ini();
    split=new splitPan();
    Cur.capGBol=false;
    contBol=false;
    if(!taulaV.matriu[1][0].equals("")){
        minim.setEnabled(true);
        minimT.setEnabled(true);
        minimBol=true;
    }
    else {
        minim.setEnabled(false);
        minimT.setEnabled(false);
        minimBol=false;
    }
    fr.getContentPane().add(split.getSplitPane());
    fr.pack();
    cadenaFuncions="";
    for(int i=0;i<Func.funcions.length;i++){Func.cadenaFuncions+=Func.funcions[i]+" ";}
    fixaSplit();
    fr.setLocation(posx, posy);
    fr.setVisible(true);
    tooltip(false,0,0);
    font.fonts();
  }
  private static File rutaInici(){return new File(userDir+barraUserDir+"rutaDarrerArxiuCarregat.txt");}
  private static String rutaPrograma(){//canvia blanc a titol=direccio nou arxiu; i genera la sequencia direccio programa amb carrega arxiu incorporada
        int j=0;
        while(j>-1){
            j=titol.indexOf(" ",j);
            if(j!=-1) {titol=titol.substring(0,j)+"__es_Blanc__"+titol.substring(j+1,titol.length());}
        }
        while(j>-1){
            j=rutaArxProv.indexOf(" ",j);
            if(j!=-1) {rutaArxProv=rutaArxProv.substring(0,j)+"__es_Blanc__"+rutaArxProv.substring(j+1,rutaArxProv.length());}
        }
        if(!titol.equals(""))titol=" "+titol;
        String s="java -jar "+userDir;
        String s1=s+barraUserDir+"Calcul.jar";
        if(tipusInici){
            if(!rutaArxProv.equals(""))return s1+titol+" "+rutaArxProv;
            else return s1+titol;
        }
        s1=s+barraUserDir+"dist"+barraUserDir+"Calcul.jar";
        return s1+titol;
  }
  public static void guardaArxiuProvis(){//anomena i guarda titol = arxiu copia de les dades actual del programaguarda
        rutaArxProv=userDir+barraUserDir+"arxCalcProvis.txt";
        if(tipusInici) splitPan.guardarPans(new File(rutaArxProv));
        if(tipusInici){
            String ruta=rutaPrograma();
            try{ Runtime.getRuntime().exec(ruta);}
            catch(Exception e){}
            System.exit(0);
        }
        else Func.funcImpl.reiniciparcial();
  }
  public static void clearAll(){//anomena i guarda titol = arxiu copia de les dades actual del programaguarda
    if(!para()){
      append(1,"ordre de parada = false, el clear no s'ha executat intentar-ho de nou "+splitPan.FIL);
      return;
    }
    falseTrue();  
    splitPan.tTC.reinici();
    splitPan.tTV.reinici();
    splitPan.tTI.reinici();
    splitPan.tTP.reinici();
    splitPan.tTD.reinici();
    splitPan.tTF.reinici();
    Func.textDeco1.setText("");
    Func.textDeco[saltBol].setText("");
    JFuncio.setText("");
    Cur.pan0.removeAll();
    Cur.pan1.removeAll();
    titol="regressi"+rB.getString("o_");
    guardaArxiuProvis();
  }
  public static void elimCarrIni(){
        File a=rutaInici();
        if (a.exists()) a.delete();
  }
    public static boolean lectRuta(){;
        File a=rutaInici();
        if (!a.exists()) return false;
        titol=splitPan.llegir(a);
        if(titol.endsWith(splitPan.FIL))titol=titol.substring(0,titol.length()-1);
        if(titol.equals(""))return false;
        return true;
    }
    public static boolean escriuRuta(){
        File a=rutaInici();
        if (a.exists()) a.delete();
        try{a.createNewFile();}catch(Exception e){return false;}
        splitPan.escriu(a, titol);
        return true; 
    }
    public static char barraRuta(String n){
        char c0=92;
        char c1=47;
        char[] ch=n.toCharArray();
        for(int i=ch.length-1;i>0;i--){if((byte)ch[i]==(byte)c0||(byte)ch[i]==(byte)c1){return ch[i];}}
        return c0;
    }
    public static void ttt(boolean b){//b=boolean tooltiptext
      if(b){
        enter.setToolTipText("calcula la funci"+Func.rB.getString("o_")+" del text superior i presenta un gr"+Func.rB.getString("a")+"fic si hi ha variables generals");
        minim.setToolTipText("cerca del m"+Func.rB.getString("i_")+"nim de la funci"+Func.rB.getString("o_")+" principal");
        reset.setToolTipText("reinicia el programa i mante la informaci"+Func.rB.getString("o_")+" actual");
        para.setToolTipText("para els threads actius");
        cont.setToolTipText("continua la cerca de m"+Func.rB.getString("i_")+"nim");
        cap.setToolTipText("captura del gr"+Func.rB.getString("a")+"fic (el m"+Func.rB.getString("i_")+"nim o un punt si s'ha fet click a gr"+Func.rB.getString("a")+"fic) i els fixa com valors inicials per una cerca de m"+Func.rB.getString("i_")+"nim");
        carr.setToolTipText("carrega (load)");
        guar.setToolTipText("guarda (save)");
        guarC.setToolTipText("guarda ruta (save as)");
        enterT.setToolTipText("calcula la funci"+Func.rB.getString("o_")+" del text superior i presenta un gr"+Func.rB.getString("a")+"fic si hi ha variables generals");
        minimT.setToolTipText("cerca del m"+Func.rB.getString("i_")+"nim de la funci"+Func.rB.getString("o_")+" principal");
        paraT.setToolTipText("para els threads actius");
        contT.setToolTipText("continua la cerca de m"+Func.rB.getString("i_")+"nim");
        enrrT.setToolTipText("treu el s"+Func.rB.getString("i_")+"mbol anterior al cursor o el interval seleccionat");
        capT.setToolTipText("captura del gr"+Func.rB.getString("a")+"fic (el m"+Func.rB.getString("i_")+"nim o un punt si s'ha fet click a gr"+Func.rB.getString("a")+"fic) i els fixa com valors inicials per una cerca de m"+Func.rB.getString("i_")+"nim");
        carrT.setToolTipText("carrega (load)");
        guarT.setToolTipText("guarda (save)");
        guarCT.setToolTipText("guarda ruta (save as)");
        cbM3.setToolTipText("si es tracta d'un calcul "+Func.rB.getString("u_")+"nic de la funci"+Func.rB.getString("o_")+" principal, si actiu esborra la cadena");
        tooltip(false,0,0);
        JFuncio.setToolTipText("<html>funcions v"+Func.rB.getString("a")+"lides: "+cadenaFuncions+"<br>operadors v"+Func.rB.getString("a")+"lids: "+operador+"&lt&gt"+"<br>el signe menys (-) canvia el signe del valor que el segueix (no es una resta)");
      }
      else{
        enter.setToolTipText(null);
        minim.setToolTipText(null);
        reset.setToolTipText(null);
        para.setToolTipText(null);
        cont.setToolTipText(null);
        cap.setToolTipText(null);
        carr.setToolTipText(null);
        guar.setToolTipText(null);
        guarC.setToolTipText(null);
        enterT.setToolTipText(null);
        minimT.setToolTipText(null);
        paraT.setToolTipText(null);
        contT.setToolTipText(null);
        enrrT.setToolTipText(null);
        capT.setToolTipText(null);
        carrT.setToolTipText(null);
        guarT.setToolTipText(null);
        guarCT.setToolTipText(null);
        for(int i=0;i<teclat.teclatCol;i++)for(int j=0;i<teclat.teclatFil;i++)teclat.jl[i][j].setToolTipText(null);
        teclat.sp.setToolTipText(null);
        JFuncio.setToolTipText(null);
      }
  }
    public static void tooltip(boolean b,int i, int j){
        String s="cel.les del JLabel mostren els primers caracters de la cadena associada"
                + "<br>clic a JLabel: copia la cadena associada a la possici"+Func.rB.getString("o_")+" del cursor i fa copia al portapapers.<br>"
                + "clic al boto dreta del ratol"+Func.rB.getString("i_")+": mostra les opcions:<br>1.- ajuntar el contingut del portapapers a la cel.la JLabel<br>2.- reiniciar la taula<br>3.- ampliar o reduir columnes io files; col(8-20); fil(1-10)<br>4.- amliar o reduir la longitud de la cadena associada que es mostra al JLabel (1-8);<br>5.- sortir del teclat de s"+Func.rB.getString("i_")+"mbols;";
        if(b){teclat.jl[i][j].setToolTipText("<html>"+teclat.matriu[i][j]+"<br>"+s);return;}
        for(i=0;i<teclat.teclatFil;i++)for(j=0;j<teclat.teclatCol;j++){
            teclat.jl[i][j].setToolTipText("<html>"+teclat.matriu[i][j]+"<br>"+s);
        }
        teclat.sp.setToolTipText("<html>"+s);
    }
  public static void ini(){
        cap.setEnabled(false);
        cont.setEnabled(false);
        para.setEnabled(false);
        capT.setEnabled(false);
        contT.setEnabled(false);
        paraT.setEnabled(false);
  }
  private static void parada(){
      if(!para()){
          append(1,"ordre de parada executat, threads actius i parada forçada (threads: interrupt())"+splitPan.FIL);
          try{
            funcImpl.CAL.interrupt();
            Cal.cercaThread.interrupt();
            Cal.tempsThread.interrupt();
          }
          catch(Exception e){}
      }
      else append(1,"ordre de parada;  ");
      append(0,"threads inactius"+splitPan.FIL);
      falseTrue();
  }
  private static void capt(){
        if(paraCalcBol)return;
        paraCalcBol=true;
        trueFalse();
        Cur.capturaG();
        //splitPan.calc.cercaThread(false);
        paraCalcBol=false;
  }
    public void threadcalcula(){trueFalse();CAL = new Thread(new threadcalcula()); CAL.setPriority(Thread.MAX_PRIORITY);CAL.start();}
    class threadcalcula implements Runnable{public void run() {try{calcula();}catch(Exception e){err(e);}}}
    public void threadcalcul(){trueFalse();CAL = new Thread(new threadcalcul()); CAL.setPriority(Thread.MAX_PRIORITY);CAL.start();}
    class threadcalcul implements Runnable{public void run() {try{calcul();}catch(Exception e){err(e);}}}
    private void err(Exception e){
        StringWriter s = new StringWriter();
        e.printStackTrace(new PrintWriter(s));
        String s1="s'ha produit un error en l'an"+rB.getString("a")+"lisi previ o calcul posterior"+splitPan.FIL+"Informaci"+rB.getString("o_")+" del error"+splitPan.FIL+splitPan.FIL+s.toString();
        
        if(!para()){
          append(1,"ordre de parada"+splitPan.FIL+"Informaci"+rB.getString("o_")+" del error"+splitPan.FIL);
          append(0,s1);
          try{
            funcImpl.CAL.interrupt();
            Cal.cercaThread.interrupt();
            Cal.tempsThread.interrupt();
          }
          catch(Exception e1){}
      }
      else append(1,"ordre de parada;  ");
      append(0,"threads inactius"+splitPan.FIL);
      falseTrue();
    }
  private static boolean analisiPrevi(){
    splitPan.temp0();splitPan.temp1();
    Cur.capGBol=false;
    Cur.capBol=false;
    trueFalse();
    Cur.trueFalse();
    splitPan.matriuPsupBol=false;splitPan.matriuVsupBol=false;
    if(Func.tipusFuncioBol){
        if(!split.llistatVars()){falseTrue();return false;}
        splitPan.tTF.cercleThread();
    }
    else{
        if(!Cur.unaVarBol)Cur.iniciaCurVars();//fa visible= false algunes funcions
        if(!analisiPreviBol){
            Func.resultat="";
            Cur.dVO=new double[0];
            if(!split.llistatVars()){
                if(splitPan.matriuPsupBol)taulaP.matriu=splitPan.copiaMatriu(splitPan.matriuPsup);
                if(splitPan.matriuVsupBol)taulaV.matriu=splitPan.copiaMatriu(splitPan.matriuVsup);
                falseTrue();return false;
            }// al inici de split.llistatVars() fa calculUnic=false;
            if(taulaV.varstaulaV.length==1)Cur.unaVarBol=true;
            if(!splitPan.calculUnic){//es true quant despres de calcular les funcions dVO hi ha resultat numeric
                if(Func.ampliarInfo)Func.append(0,"an"+rB.getString("a")+"lisi, temps: "+splitPan.tempt()+" seg., "+splitPan.FIL);
                splitPan.temp0();
                analisiPreviBol=true;
            }
            else analisiPreviBol=false;
        }
        if(!resultat.equals("")){//si resultat es diferent de "" vol dir que hi un resultat numeric sense variables ni parametres ni integrals
            if(!noArrodonir)Func.append(0,resultat+splitPan.FIL);
            else Func.append(0,resultat.substring(0,resultat.indexOf(";"))+splitPan.FIL);
        }
    }
    Cur.falseTrue();
    capturaManualBol=cbM5.getState();
    if(splitPan.matriuPsupBol)taulaP.matriu=splitPan.copiaMatriu(splitPan.matriuPsup);
    if(splitPan.matriuVsupBol)taulaV.matriu=splitPan.copiaMatriu(splitPan.matriuVsup);
    return true;
  }
  public static boolean para(){
    Cal.stopBucle=true;
    Cur.stopBucle=true;
    Cal.stop=true;
    taulaF.stopBucle=true;
    int con=0;
    while(paraCalcBol){
        con++;
        try{Thread.sleep(100L);} 
        catch( InterruptedException e) {}
        if(con>30){
            paraCalcBol=false;
            return false;
        }
    }
    try{
        while(splitPan.calc.cercaThread.isAlive()){
            try{Thread.sleep(100L);} 
            catch( InterruptedException e) {}
            if(con>30){Cur.paraCalcBol=false;return false;}
        }
    }
    catch(Exception e){}
    while(Cur.paraCalcBol){
        con++;
        try{Thread.sleep(100L);} 
        catch( InterruptedException e) {}
        if(con>30){
            Cur.paraCalcBol=false;
            return false;
        }
    } 
    paraCalcBol=false;
    Cur.paraCalcBol=false;
    return true;
  }
  public static void trueFalse(){
    enterBol=true;
    paraCalcBol=true;
    enter.setEnabled(false);
    minim.setEnabled(false);
    para.setEnabled(true);
    cap.setEnabled(false);
    cont.setEnabled(false);
    enterT.setEnabled(false);
    minimT.setEnabled(false);
    paraT.setEnabled(true);
    capT.setEnabled(false);
    contT.setEnabled(false);
  }
  public static void falseTrue(){
    enterBol=false;
    para.setEnabled(false);
    paraT.setEnabled(false);
    if(contBol){
        cont.setEnabled(true);
        contT.setEnabled(true);
    }
    if(minimBol){
        minim.setEnabled(true);
        minimT.setEnabled(true);
    }
    enter.setEnabled(true);
    enterT.setEnabled(true);
    paraCalcBol=false;
    if(Cur.capGBol) {
        cap.setEnabled(true);
        capT.setEnabled(true);
    }
  }
  public static void calcula(){//click a enter
    analisiPreviBol=false;
    if(!analisiPrevi()) return;
    if(Func.tipusFuncioBol){
        taulaF.graf.setVisible(true);
        fr.setLocation(posx+1,posy+1);
        taulaF.graf.setVisible(true);
        fr.setLocation(posx,posy);
        return;
    }
    calculGrafic();//es un thread el que s'executi a continuacio ho fara abans que el trhead
    Cur.indexsVarSelec_lliure=-1;
  }
  public static void calcul(){//click a minim
    if(ampliarInfo)analisiPreviBol=false;
    contBol=false;
    if(!analisiPrevi()) return;
    if(!taulaV.hihaVar){
        Func.append(0,"no hi ha variables generals, s'executa c"+rB.getString("a")+"lcul simple"+splitPan.FIL);
        calculGrafic();
        return;
    }
    if(!Cur.graficInicial()){falseTrue();return;}
    Cal.stop=true;
    splitPan.calc.cercaThread(false);//el thread inicialment es molt semblan a calcula si Cal.reiniciBol=true executa reinici que executa Cur.graficinicial
    Cur.indexsVarSelec_lliure=-1;//reinicialita la opcio de grafic funcio ordenada vc funcio abcisa
  }
  private static void cont(){
    contBol=false;
    if(!taulaV.hihaVar)return;
    trueFalse();
    splitPan.calc.continuaThread();
  }

  public static void calculGrafic(){
        if(Cur.dVO.length==0){Func.falseTrue();return;}
        if(!taulaV.hihaVar){Func.falseTrue();return;}
        if(!taulaI.hihaDades){//taula sumatoris buida
            Cur.graficInicial();
            Func.falseTrue();
            return;
        }
        if(suportID.hihaVG_aTOTI){
            if(!Cal.integralBol){// hi ha variables generals pero la funcio principal no conte sumatoris
                Cur.supID.buclIntegral(1);
                if(taulaV.hihaVar)Cur.graficInicial();
                return;
            }
            else if(Cal.integralBol)Cur.supID.buclIntegral(2);
        }
    }
public static void fixaSplit(){
    text1[saltBol].setVisible(true);
    if(saltBol==1){
            splitPan.split[10].setDividerLocation(splitPan.split[10].getHeight());
            text1[0].setVisible(false);
            textDeco[0].setText("");
    }
    else {
        splitPan.split[10].setDividerLocation(0);
        text1[1].setVisible(false);
        textDeco[1].setText("");
    }
}
class WrapEditorKit extends StyledEditorKit {
  ViewFactory defaultFactory = new WrapColumnFactory();
  public ViewFactory getViewFactory() {
    return defaultFactory;
  }
}
class WrapColumnFactory implements ViewFactory {
  public View create(Element elem) {
    String kind = elem.getName();
    if (kind != null) {
      if (kind.equals(AbstractDocument.ContentElementName))return new WrapLabelView(elem);
      else if (kind.equals(AbstractDocument.ParagraphElementName))return new ParagraphView(elem);
      else if (kind.equals(AbstractDocument.SectionElementName))return new BoxView(elem, View.Y_AXIS);
      else if (kind.equals(StyleConstants.ComponentElementName))return new ComponentView(elem);
      else if (kind.equals(StyleConstants.IconElementName))return new IconView(elem);
    }
    return new LabelView(elem);
  }
}
class WrapLabelView extends LabelView {
  public WrapLabelView(Element elem) {super(elem);}
  public float getMinimumSpan(int axis) {
    switch (axis) {
        case View.X_AXIS:return 0;
        case View.Y_AXIS:return super.getMinimumSpan(axis);
        default:throw new IllegalArgumentException("error wrap: "+ axis);
    }
  }
}
public static void main(String[] args) {
      if(args.length==2){
            rutaArxProv=args[1];
            int j=0;
            while(j>-1){
            j=rutaArxProv.indexOf("__es_Blanc__",j);
            if(j!=-1) {rutaArxProv=rutaArxProv.substring(0,j)+" "+rutaArxProv.substring(j+12,rutaArxProv.length());}
        }
      }
      if(args.length>0){
            titol=args[0];
            int j=0;
            while(j>-1){
                j=titol.indexOf("__es_Blanc__",j);
                if(j!=-1) {titol=titol.substring(0,j)+" "+titol.substring(j+12,titol.length());}
            }
      }
      funcImpl=new Func();
  }
public static void setState(){
    cbM1.setState(obrirDarrerArxiu);
    cbM3.setState(clearFuncio);  
    cbM4.setState(ampliarInfo);  
    cbM5.setState(capturaManualBol);  
    cbM6.setState(Func.tooltiptxt);  
    cbM7.setState(Func.mouInvertBol);  
    cbM8.setState(unirPunts); 
    cbM9.setState(ratllaBol1);
    cbM10.setState(ratllaBol); 
    if(taulaC.scrollCols==0)taulaC.cbM.setState(true);
    else taulaC.cbM.setState(false);
    if(taulaP.scrollCols==0)taulaP.cbM.setState(true);
    else taulaP.cbM.setState(false);
    if(taulaV.scrollCols==0)taulaV.cbM.setState(true);
    else taulaV.cbM.setState(false);
    if(taulaI.scrollCols==4){taulaI.cbM.setState(true);taulaI.cbM1.setState(true);}
    else {taulaI.cbM.setState(false);taulaI.cbM1.setState(false);}
    if(taulaD.scrollCols==0)taulaD.cbM.setState(true);
    else taulaD.cbM.setState(false);
    if(taulaF.scrollCols==0)taulaF.cbM.setState(true);
    else taulaF.cbM.setState(false);
  }
  public static void append1(int i, String s) {
      Color c=Color.BLUE;
      if(i==1) c=Color.RED;
      else if(i==2) c=Color.BLACK;
      textDeco[saltBol].setCaretPosition(textDeco[saltBol].getText().length());
      StyleContext sc = StyleContext.getDefaultStyleContext();
      AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,StyleConstants.Foreground, c);
      textDeco[saltBol].setCharacterAttributes(aset, false);
      textDeco[saltBol].replaceSelection(s);
      c=Color.BLUE;
      aset = sc.addAttribute(SimpleAttributeSet.EMPTY,StyleConstants.Foreground, c);
      textDeco[saltBol].setCharacterAttributes(aset, false);
      textDeco[saltBol].replaceSelection("");
    }
    public static void append(int i, String s) {
        Color c=Color.BLUE;
        if(i==1) c=Color.RED;
        else if(i==2) c=Color.BLACK;
        style = textDeco[saltBol].getStyledDocument();
        SimpleAttributeSet atribut = new SimpleAttributeSet();
        StyleConstants.setForeground(atribut, c);
        try{style.insertString(style.getLength(), s, atribut );}
        catch(Exception e) {}
        StyleConstants.setForeground(atribut, Color.BLUE);
        textDeco[saltBol].setCaretPosition(style.getLength());
    }
    public static void hiHaErr() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){
  	public void uncaughtException(Thread th, Throwable ex){
            StringWriter s = new StringWriter();
            ex.printStackTrace(new PrintWriter(s));
            String s1="Informaci"+rB.getString("o_")+" de l'error"+splitPan.FIL+splitPan.FIL+s.toString();
            if(!para()) {
                append(1,"ordre de parada activa."+splitPan.FIL+"Informaci"+rB.getString("o_")+" de l'error"+splitPan.FIL);
                append(0,s1);
            }
  	}});
    }
  public static class clipboard implements ClipboardOwner{
    public clipboard(){ }
    public void lostOwnership(Clipboard aClipboard, Transferable aContents){}
    public void copiaClip(String a){
      StringSelection s = new StringSelection(a);
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      clipboard.setContents(s, this);
    }
  public String ajuntarClip() throws UnsupportedFlavorException, IOException {
      String r = "";
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      Transferable c = clipboard.getContents(null);
      boolean b = (c != null) && (c.isDataFlavorSupported(DataFlavor.stringFlavor));
      if(b){ try {r = (String)c.getTransferData(DataFlavor.stringFlavor);}catch (Exception ex) {}}
      return r;
    }
  }
  private static Color defC(int j,Color c){
      int[] i=new int[3];
      i[0]=c.getRed();
      i[1]=c.getGreen();
      i[2]=c.getBlue();
      if(j==0){
          i[0]=i[0]/2+50;
          i[1]=i[1]/2+50;
          i[2]=i[2]/2+50;
      }
      if(i[0]>255){i[0]=255;}
      if(i[1]>255){i[1]=255;}
      if(i[2]>255){i[2]=255;}
      c=new Color(i[0],i[1],i[2]);
      return c;
  }
  public static ImageIcon Copiar(){ImageIcon icon = new ImageIcon(cCopiar()); return icon; } 
  public static Image cCopiar() { return copiar(); } 
  public static Image copiar() {
    BufferedImage bi = new BufferedImage(16, 16, 1);
    Graphics g = bi.getGraphics();
    g.setColor(Color.WHITE); g.fillRect(0, 0, 16, 16);
    g.setColor(Color.black);
    g.drawLine(4, 0, 13, 0);
    g.drawLine(1, 4, 10, 4);
    g.drawLine(1, 14, 10, 14);
    g.drawLine(11, 10, 13, 10);
    g.drawLine(4, 0, 4, 3);
    g.drawLine(1, 4, 1, 14);
    g.drawLine(10, 4, 10, 14);
    g.drawLine(13, 0, 13, 10);
    g.setColor(Color.darkGray);
    g.drawLine(10, 7, 10, 7);
    g.drawLine(10, 8, 11, 8);
    g.drawLine(10, 9, 12, 9);
    g.drawLine(3, 10, 13, 10);
    g.drawLine(3, 11, 14, 11);
    g.drawLine(3, 12, 13, 12);
    g.drawLine(10, 13, 12, 13);
    g.drawLine(10, 14, 11, 14);
    g.drawLine(10, 15, 10, 15);
    g.setColor(Color.blue);
    g.drawLine(3, 7, 8, 7);
    g.drawLine(3, 9, 8, 9);
    g.drawLine(3, 12, 8, 12);
    return bi;
  }
  public static ImageIcon Engan() { ImageIcon icon = new ImageIcon(cEngan()); return icon; } 
  public static Image cEngan() { return engan(); } 
  public static Image engan() {
    BufferedImage bi = new BufferedImage(16, 16, 1);
    Graphics g = bi.getGraphics();
    g.setColor(Color.WHITE); g.fillRect(0, 0, 16, 16);
    g.setColor(Color.black);
    g.drawLine(4, 0, 13, 0);
    g.drawLine(1, 4, 10, 4);
    g.drawLine(1, 14, 10, 14);
    g.drawLine(11, 10, 13, 10);
    g.drawLine(4, 0, 4, 3);
    g.drawLine(1, 4, 1, 14);
    g.drawLine(10, 4, 10, 14);
    g.drawLine(13, 0, 13, 10);
    g.setColor(Color.blue);
    g.drawLine(6, 2, 11, 2);
    g.setColor(Color.darkGray);
    g.drawLine(7, 5, 7, 5);
    g.drawLine(6, 6, 7, 6);
    g.drawLine(5, 7, 7, 7);
    g.drawLine(4, 8, 14, 8);
    g.drawLine(3, 9, 14, 9);
    g.drawLine(4, 10, 14, 10);
    g.drawLine(5, 11, 7, 11);
    g.drawLine(6, 12, 7, 12);
    g.drawLine(7, 13, 7, 13);
    return bi;
  }
    public static ImageIcon Obrirarx() {ImageIcon icon = new ImageIcon(cObrirarx());return icon;}
    public static Image cObrirarx() {return obrirarx();}
    public static Image obrirarx() {
    	BufferedImage bi = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.setColor(Color.WHITE);g.fillRect(0, 0, 16, 16);
        g.setColor(defC(0,Color.yellow));g.fillRect(2, 3, 13, 6);
        g.setColor(Color.yellow);g.fillRect(1, 7, 16, 6);
        g.setColor(Color.black);
        g.drawLine(1, 2, 6, 2);
        g.drawLine(6, 3, 15, 3);
        g.drawLine(0, 6, 12, 6);
        g.drawLine(2, 13, 15, 13);
        g.drawLine(1, 2, 1, 5);
        g.drawLine(0, 6, 0, 7);
        g.drawLine(1, 8, 1, 10);
        g.drawLine(2, 11, 2, 13);
        g.drawLine(13, 6, 13, 7);
        g.drawLine(14, 8, 14, 10);
        g.drawLine(15, 11, 15, 13);
        g.drawLine(15, 3, 15, 13);
        g.setColor(defC(0,Color.yellow));
        g.drawLine(14, 7, 14, 7);
        return bi;
    }
  public static ImageIcon Guarda() {ImageIcon icon = new ImageIcon(cGuarda());return icon;}
    public static Image cGuarda() {return guarda();}
    public static Image guarda() {
    	BufferedImage bi = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.setColor(Color.WHITE);g.fillRect(0, 0, 16, 16);
        g.setColor(Color.black);
        g.drawLine(0, 11, 11, 0);
        g.drawLine(4, 15, 15, 4);
        g.drawLine(0, 12, 3, 15);
        g.drawLine(0, 12, 3, 15);
        g.drawLine(8, 4, 11, 7);
        g.setColor(Color.cyan);
        g.drawLine(1, 11, 7, 5);
        g.drawLine(1, 12, 8, 5);
        g.drawLine(2, 12, 8, 6);
        g.drawLine(2, 13, 9, 6);
        g.drawLine(3, 13, 9, 7);
        g.drawLine(3, 14, 10, 7);
        g.drawLine(4, 14, 10, 8);
        g.setColor(Color.yellow);
        g.drawLine(9, 3, 12, 0);
        g.drawLine(10, 4, 13, 1);
        g.drawLine(11, 5, 14, 2);
        g.drawLine(12, 6, 15, 3);
        g.setColor(Color.gray);
        g.drawLine(9, 4, 12, 1);
        g.drawLine(10, 5, 13, 2);
        g.drawLine(11, 6, 14, 3);
        g.drawLine(12, 7, 15, 4);   
        return bi;
    }
    public static ImageIcon GuardaC() {ImageIcon icon = new ImageIcon(cGuardaC());return icon;}
    public static Image cGuardaC() {return guardaC();}
    public static Image guardaC() {
    	BufferedImage bi = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.setColor(Color.WHITE);g.fillRect(0, 0, 16, 16);
        g.setColor(Color.black);
        g.drawLine(0, 11, 11, 0);
        g.drawLine(4, 15, 15, 4);
        g.drawLine(0, 12, 3, 15);
        g.drawLine(0, 12, 3, 15);
        g.drawLine(8, 4, 11, 7);
        g.setColor(Color.red);
        g.drawLine(1, 11, 7, 5);
        g.drawLine(1, 12, 8, 5);
        g.drawLine(2, 12, 8, 6);
        g.drawLine(2, 13, 9, 6);
        g.drawLine(3, 13, 9, 7);
        g.drawLine(3, 14, 10, 7);
        g.drawLine(4, 14, 10, 8);
        g.setColor(Color.yellow);
        g.drawLine(9, 3, 12, 0);
        g.drawLine(10, 4, 13, 1);
        g.drawLine(11, 5, 14, 2);
        g.drawLine(12, 6, 15, 3);
        g.setColor(Color.gray);
        g.drawLine(9, 4, 12, 1);
        g.drawLine(10, 5, 13, 2);
        g.drawLine(11, 6, 14, 3);
        g.drawLine(12, 7, 15, 4);   
        return bi;
    }
    public static ImageIcon Moucolumna1() {ImageIcon icon = new ImageIcon(cMoucolumna1());return icon;}
    public static Image cMoucolumna1() {return moucolumna1();}
    public static Image moucolumna1() {
    	BufferedImage bi = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.setColor(Color.WHITE);g.fillRect(0, 0, 16, 16);
        g.setColor(Color.black);
        g.drawRect(1, 0, 13, 14);
        g.setColor(Color.lightGray);
        g.fillRect(2, 1, 12, 13);
        g.setColor(Color.WHITE);
        g.drawLine(6, 2, 6, 13);
        g.drawLine(7, 2, 7, 13);
        g.drawLine(8, 2, 8, 13);
        return bi;
    }
    public static ImageIcon Moucolumna() {ImageIcon icon = new ImageIcon(cMoucolumna());return icon;}
    public static Image cMoucolumna() {return moucolumna();}
    public static Image moucolumna() {
    	BufferedImage bi = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.setColor(Color.WHITE);g.fillRect(0, 0, 16, 16);
        g.setColor(Color.black);
        g.drawRect(1, 0, 13, 14);
        g.setColor(Color.lightGray);
        g.fillRect(2, 1, 12, 9);
        g.setColor(Color.WHITE);
        g.drawLine(7, 2, 7, 13);
        g.drawLine(8, 2, 8, 13);
        g.setColor(Color.black);
        g.drawLine(1, 9, 14, 9);
        g.drawLine(3, 11, 9, 11);
        g.drawLine(3, 12, 9, 12);
        return bi;
    }
    public static ImageIcon Treucolumna() {ImageIcon icon = new ImageIcon(cTreucolumna());return icon;}
    public static Image cTreucolumna() {return treucolumna();}
    public static Image treucolumna() {
    	BufferedImage bi = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.setColor(Color.WHITE);g.fillRect(0, 0, 16, 16);
        g.setColor(Color.black);
        g.drawRect(1, 0, 13, 14);
        g.setColor(Color.lightGray);
        g.fillRect(2, 1, 12, 13);
        g.setColor(Color.red);
        g.drawLine(7, 2, 7, 13);
        g.drawLine(8, 2, 8, 13);
        g.setColor(Color.black);
        g.drawLine(3, 2, 3, 2);
        g.drawLine(3, 3, 4, 3);
        g.drawLine(3, 4, 5, 4);
        g.drawLine(3, 5, 6, 5);
        g.drawLine(3, 6, 5, 6);
        g.drawLine(3, 7, 4, 7);
        g.drawLine(3, 8, 3, 8);
        g.drawLine(12, 7, 12, 7);
        g.drawLine(11, 8, 12, 8);
        g.drawLine(10, 9, 12, 9);
        g.drawLine(9, 10, 12, 10);
        g.drawLine(10, 11, 12, 11);
        g.drawLine(11, 12, 12, 12);
        g.drawLine(12, 13, 12, 13);
        return bi;
    }
    public static ImageIcon Novacolumna() {ImageIcon icon = new ImageIcon(cNovacolumna());return icon;}
    public static Image cNovacolumna() {return novacolumna();}
    public static Image novacolumna() {
    	BufferedImage bi = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.setColor(Color.WHITE);g.fillRect(0, 0, 16, 16);
        g.setColor(Color.black);
        g.drawRect(1, 0, 13, 14);
        g.setColor(Color.lightGray);
        g.fillRect(2, 1, 12, 13);
        g.setColor(Color.cyan);
        g.drawLine(7, 2, 7, 13);
        g.drawLine(8, 2, 8, 13);
        g.setColor(Color.black);
        g.drawLine(6, 2, 6, 2);
        g.drawLine(5, 3, 6, 3);
        g.drawLine(4, 4, 6, 4);
        g.drawLine(3, 5, 6, 5);
        g.drawLine(4, 6, 6, 6);
        g.drawLine(5, 7, 6, 7);
        g.drawLine(6, 8, 6, 8);
        g.drawLine(9, 7, 9, 7);
        g.drawLine(9, 8, 10, 8);
        g.drawLine(9, 9, 11, 9);
        g.drawLine(9, 10, 12, 10);
        g.drawLine(9, 11, 11, 11);
        g.drawLine(9, 12, 10, 12);
        g.drawLine(9, 13, 9, 13);
        return bi;
    } 
        public static ImageIcon Novalinia() {ImageIcon icon = new ImageIcon(cNovalinia());return icon;}
    public static Image cNovalinia() {return novalinia();}
    public static Image novalinia() {
    	BufferedImage bi = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.setColor(Color.WHITE);g.fillRect(0, 0, 16, 16);
        g.setColor(Color.black);
        g.drawRect(1, 0, 13, 14);
        g.setColor(Color.lightGray);
        g.fillRect(2, 1, 12, 13);
        g.setColor(Color.cyan);
        g.drawLine(2, 6, 13, 6);
        g.drawLine(2, 7, 13, 7);
        g.setColor(Color.black);
        g.drawLine(6, 2, 6, 2);
        g.drawLine(5, 3, 7, 3);
        g.drawLine(4, 4, 8, 4);
        g.drawLine(3, 5, 9, 5);
        g.drawLine(6, 8, 12, 8);
        g.drawLine(7, 9, 11, 9);
        g.drawLine(8, 10, 10, 10);
        g.drawLine(9, 11, 9, 11);
        return bi;
    }
        public static ImageIcon Treulinia() {ImageIcon icon = new ImageIcon(cTreulinia());return icon;}
    public static Image cTreulinia() {return treulinia();}
    public static Image treulinia() {
    	BufferedImage bi = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.setColor(Color.WHITE);g.fillRect(0, 0, 16, 16);
        g.setColor(Color.black);
        g.drawRect(1, 0, 13, 14);
        g.setColor(Color.lightGray);
        g.fillRect(2, 1, 12, 13);
        g.setColor(Color.red);
        g.drawLine(2, 6, 13, 6);
        g.drawLine(2, 7, 13, 7);
        g.setColor(Color.black);
        g.drawLine(6, 5, 6, 5);
        g.drawLine(5, 4, 7, 4);
        g.drawLine(4, 3, 8, 3);
        g.drawLine(3, 2, 9, 2);
        g.drawLine(6, 11, 12, 11);
        g.drawLine(7, 10, 11, 10);
        g.drawLine(8, 9, 10, 9);
        g.drawLine(9, 8, 9, 8);
        return bi;
    }
    public static ImageIcon Nouarx() {ImageIcon icon = new ImageIcon(cNouarx());return icon;}
    public static Image cNouarx() {return nouarx();}
    public static Image nouarx() {
    	BufferedImage bi = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.setColor(Color.WHITE);g.fillRect(0, 0, 16, 16);
        g.setColor(Color.black);
        g.drawLine(4, 0, 13, 0);
        g.drawLine(1, 4, 10, 4);
        g.drawLine(1, 14, 10, 14);
        g.drawLine(11, 10, 13, 10);
        g.drawLine(4, 0, 4, 3);
        g.drawLine(1, 4, 1, 14);
        g.drawLine(10, 4, 10, 14);
        g.drawLine(13, 0, 13, 10);
        g.setColor(Color.lightGray);    
        return bi;
    }   
    public static ImageIcon Nouarx1() {ImageIcon icon = new ImageIcon(cNouarx1());return icon;}
    public static Image cNouarx1() {return nouarx1();}
    public static Image nouarx1() {
    	BufferedImage bi = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.setColor(Color.WHITE);g.fillRect(0, 0, 16, 16);
        g.setColor(Color.black);
        g.drawLine(4, 0, 13, 0);
        g.drawLine(1, 4, 10, 4);
        g.drawLine(1, 14, 10, 14);
        g.drawLine(11, 10, 13, 10);
        g.drawLine(4, 0, 4, 3);
        g.drawLine(1, 4, 1, 14);
        g.drawLine(10, 4, 10, 14);
        g.drawLine(13, 0, 13, 10);
        g.setColor(Color.blue); 
        g.drawLine(6, 2, 11, 2);
        g.drawLine(3, 7, 8, 7);
        g.drawLine(3, 9, 8, 9);
        g.drawLine(3, 11, 8, 11);  
        return bi;
    } 
    public static ImageIcon Wrap() {ImageIcon icon = new ImageIcon(cWrap());return icon;}
    public static Image cWrap() {return cwrap();}
    public static Image cwrap() {
    	BufferedImage bi = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.setColor(Color.WHITE);g.fillRect(0, 0, 16, 16);
        g.setColor(Color.black);
        g.drawLine(0, 0, 9, 0);
        g.drawLine(0, 15, 9, 15);
        g.drawLine(0, 0, 0, 15);
        g.drawLine(10, 0, 10, 15);
        g.setColor(Color.blue);
        g.drawLine(0, 2, 15, 2);
        g.drawLine(0, 11, 9, 11);
        g.drawLine(0, 13, 4, 13);
        g.setColor(Color.darkGray); 
        g.drawLine(5, 3, 5, 3);
        g.drawLine(4, 4, 6, 4);;
        g.drawLine(3, 5, 7, 5);
        g.drawLine(5, 6, 5, 6);
        g.drawLine(5, 7, 5, 7);
        g.drawLine(3, 8, 7, 8);
        g.drawLine(4, 9, 6, 9);
        g.drawLine(5, 10, 5, 10);
        return bi;
    }
    public static ImageIcon Informacio() {ImageIcon icon = new ImageIcon(cInformacio());return icon;}
    public static Image cInformacio() {return informacio();}
    public static Image informacio() {
    	BufferedImage bi = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.setColor(Color.WHITE);g.fillRect(0, 0, 16, 16);
        g.setColor(Color.blue);
        g.fillOval(0, 0, 16, 16);
        g.setColor(Color.WHITE);
        g.fillRect(7, 3, 3, 3);
        g.fillRect(7, 8, 3, 6);
        return bi;
    }
    public static ImageIcon Stop() {ImageIcon icon = new ImageIcon(cStop());return icon;}
    public static Image cStop() {return stop();}
    public static Image stop() {
    	BufferedImage bi = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.setColor(Color.WHITE);g.fillRect(0, 0, 16, 16);
        g.setColor(Color.red);
        g.fillOval(0, 0, 16, 16);
        g.setColor(Color.WHITE);
        g.fillRect(3, 7, 11, 3);
        return bi;
    }
    public static ImageIcon Gregweb() {ImageIcon icon = new ImageIcon(cGregweb());return icon;}
    public static Image cGregweb() {return gregweb();}
    public static Image gregweb() {
        BufferedImage bi = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.setColor(Color.WHITE);g.fillRect(0, 0, 16, 16);
        g.setColor(Color.black);
        g.drawRect(1, 3, 10, 11);
        g.setColor(Color.red);
        g.fillRect(2, 4, 9, 10);
        g.setColor(Color.white);
        g.drawLine(3, 6, 8, 6);
        g.drawLine(3, 8, 8, 8);
        g.setColor(Color.lightGray);
        g.drawLine(3, 2, 11, 2);
        g.drawLine(12, 3, 12, 12);
        g.setColor(Color.black);
        g.drawLine(3, 1, 13, 1);
        g.drawLine(13, 1, 13, 12);
        g.drawLine(2,2,2,2);
        g.drawLine(12, 2, 12, 2);
        g.drawLine(12, 13, 12, 13);
        return bi;
    }
    public static ImageIcon Esq() {ImageIcon icon = new ImageIcon(cEsq());return icon;}
    public static Image cEsq() {return esq();}
    public static Image esq() {
    	BufferedImage bi = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.setColor(Color.WHITE);g.fillRect(0, 0, 16, 16);
        g.setColor(Color.darkGray);
        g.drawLine(14, 1, 14, 14);
        g.drawLine(13, 1, 13, 14);
        g.drawLine(12, 2, 12, 13);
        g.drawLine(11, 2, 11, 13);
        g.drawLine(10, 3, 10, 12);
        g.drawLine(9, 3, 9, 12);
        g.drawLine(8, 4, 8, 11);
        g.drawLine(7, 4, 7, 11);
        g.drawLine(6, 5, 6, 10);
        g.drawLine(5, 5, 5, 10);
        g.drawLine(4, 6, 4, 9);
        g.drawLine(3, 6, 3, 9);
        g.drawLine(2, 7, 2, 8);
        g.drawLine(1, 7, 1, 8);
        return bi;
    }
    public static ImageIcon Dreta() {ImageIcon icon = new ImageIcon(cDreta());return icon;}
    public static Image cDreta() {return dreta();}
    public static Image dreta() {
    	BufferedImage bi = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.setColor(Color.WHITE);g.fillRect(0, 0, 16, 16);
        g.setColor(Color.darkGray);
        g.drawLine(1, 1, 1, 14);
        g.drawLine(2, 1, 2, 14);
        g.drawLine(3, 2, 3, 13);
        g.drawLine(4, 2, 4, 13);
        g.drawLine(5, 3, 5, 12);
        g.drawLine(6, 3, 6, 12);
        g.drawLine(7, 4, 7, 11);
        g.drawLine(8, 4, 8, 11);
        g.drawLine(9, 5, 9, 10);
        g.drawLine(10, 5, 10, 10);
        g.drawLine(11, 6, 11, 9);
        g.drawLine(12, 6, 12, 9);
        g.drawLine(13, 7, 13, 8);
        g.drawLine(14, 7, 14, 8);
        return bi;
    }
    public static ImageIcon Borrar() {ImageIcon icon = new ImageIcon(cBorrar());return icon;}
    public static Image cBorrar() {return borrar();}
    public static Image borrar() {
    	BufferedImage bi = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.setColor(Color.WHITE);g.fillRect(0, 0, 16, 16);
        g.setColor(Color.blue);
        g.drawLine(2, 6, 5, 9);
        g.drawLine(3, 6, 6, 9);
        g.drawLine(4, 5, 7, 8);
        g.drawLine(5, 5, 8, 8);
        g.drawLine(5, 4, 8, 7);
        g.drawLine(6, 4, 9, 7);
        g.drawLine(6, 3, 9, 6);
        g.drawLine(7, 3, 10, 6);
        g.drawLine(2, 4, 4, 2);
        g.drawLine(3, 4, 5, 2);
        g.setColor(Color.red);
        g.drawLine(6, 10, 9, 13);
        g.drawLine(7, 10, 10, 13);
        g.drawLine(8, 9, 12, 13);
        g.drawLine(9, 9, 12, 12);
        g.drawLine(9, 8, 13, 12);
        g.drawLine(10, 8, 13, 11);
        g.drawLine(10, 7, 14, 11);
        g.drawLine(11, 7, 14, 10);
        g.setColor(Color.black);
        g.drawLine(0, 5, 9, 14);
        g.drawLine(3, 5, 12, 14);
        g.drawLine(6, 1, 15, 10);
        g.drawLine(0, 5, 4, 1);
        g.drawLine(3, 5, 6, 2);
        g.drawLine(12, 14, 15, 11);
        g.drawLine(1, 5, 2, 5);
        g.drawLine(10, 14, 11, 14);
        g.drawLine(5, 1, 6, 1);
        return bi;
    }
}