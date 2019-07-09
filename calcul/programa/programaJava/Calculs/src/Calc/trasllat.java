package Calc;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.util.List;
import java.util.ResourceBundle;

public class trasllat extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static Component controllingFrame;
        @SuppressWarnings("rawtypes")
        static JList drop;
        @SuppressWarnings("rawtypes")
        static DefaultListModel listModel;
        static JScrollPane scrollPane;
        Clipboard clipboard = getToolkit().getSystemClipboard();
        static JFrame frame = new JFrame("traslladar document");
        public static String ruta;
        public static String subdirectori="documentsAssociats";
        
    public trasllat(boolean b) {}
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public trasllat() {
        super(new GridBagLayout());
        listModel = new DefaultListModel();
        drop = new JList(listModel);
        drop.setCellRenderer(new plantilla());
        drop.setTransferHandler(new traspasar(drop));
        drop.setDropMode(javax.swing.DropMode.INSERT);
        drop.setBorder(new TitledBorder("arrosegar aqui arxiu origen"));
        drop.setOpaque(true);
        drop.setBackground(Color.CYAN);
        drop.setFont(new Font("sansserif", Font.PLAIN, 11));
        drop.setToolTipText(Func.rB.getString("drop"));
        scrollPane = new JScrollPane(drop);
        Dimension dimen=new Dimension(500, 50);
        scrollPane.setPreferredSize(dimen);
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
        JPanel p = new JPanel(new GridLayout(1, 0));
        c.fill = GridBagConstraints.HORIZONTAL;
        JButton but2 = new JButton("traslladar");
        but2.setToolTipText(Func.rB.getString("traslladar"));
        but2.addActionListener(this);
        p.add(but2);
        p.setBorder(BorderFactory.createEmptyBorder(0,0,5,5));
        add(p,c);
        c.fill = GridBagConstraints.HORIZONTAL;
        ToolTipManager.sharedInstance().setDismissDelay(60000);
        frame.addWindowListener (new WindowAdapter() {public void windowClosing (WindowEvent e) {tancar();}});
     }
    public static void tancar(){
        listModel.removeAllElements();
    	frame.setVisible(false);
    }
    @SuppressWarnings("unchecked")
    public void actionPerformed(ActionEvent evt) {
    	String cmd = evt.getActionCommand();
    	if ("traslladar".equals(cmd)) {
            if(ruta==null){error("null, no hi ha contingut al arxiu origen");return;}
            String or=ruta;
            if (or.length()==0){error("l'arxiu origen esta buit");return;}
            File f=new File(or);
            if(f.isDirectory()){error("l'arxiu es una carpeta)");return;}
            String de=rutaCarpeta();
            Copia(or,de);
            if(!or.equals(de)){
                f=new File(or);
                if(f.exists())f.delete();
            }
            tancar();
    	}
    }
     public static String hihaArxiuAdjunt(){
        String desti=rutaArx(Func.titol);
        if(desti.equals(""))return "";
        String nom=retallNomCurt(Func.titol);
        if(nom.equals(""))return "";
        String s=desti+subdirectori;
        File f=new File(s);
        if(f.exists()){
            desti=arxDir((new File(s)),nom);
            f=new File(desti);
            if(f.exists())return desti;
        }
        return "";
    }
    static String arxDir(File rut,String nom){
        String arxs[] = rut.list();
            for (String file : arxs) {
                File f = new File(rut, file);
                if(!f.isDirectory()){
                    String s=f.toString();
                    int j=-1,k=-1;
                    char[] ch=s.toCharArray();
                    for(int i=ch.length-1;i>0;i--){
                        if((byte)ch[i]==46){j=i;}
                        if((byte)ch[i]==92||(byte)ch[i]==47){k=i;i=0;}
                    }
                    if(k==-1) return "";
                    if(j==-1)j=ch.length;
                    s=s.substring(k+1,j);
                    if(s.equals(nom))return f.toString();
                }
            }
            return "";
    }
    public static String retallNomCurt(String n){
    	char[] ch=n.toCharArray();
        int j=-1;
        for(int i=ch.length-1;i>=0;i--){if((byte)ch[i]==92||(byte)ch[i]==47){j=i;i=0;}}
        if(j==-1) return "";
        if(j<=ch.length)n=n.substring(j+1);
        j=-1;
        ch=n.toCharArray();
        for(int i=ch.length-1;i>0;i--){if((byte)ch[i]==46){j=i;i=0;}}
        if(j==-1) return "";
        if(j>0&&j<=ch.length){n=n.substring(0,j);}
        return n;
    }
    public static String rutaArx(String n){
        char[] ch=n.toCharArray();
        int j=-1;
        for(int i=ch.length-1;i>=0;i--){if((byte)ch[i]==92||(byte)ch[i]==47){j=i;i=0;}}
        if(j==-1) return "";
        if(j<=ch.length){n=n.substring(0,j+1);}
        return n;
    }
    public static String rutaCarpeta(){//retall ruta amb barra sense titol
        String ext=extensio(ruta);
        String nom=retallNomCurt(Func.titol);
        if(nom.equals(""))return "";
        String desti=rutaArx(Func.titol);
        String barra=desti.substring(desti.length()-1);
        if(desti.equals(""))return "";
        desti+=subdirectori;
        File f=new File(desti);
        if(!f.exists()||!f.isDirectory())f.mkdir();
        desti+=barra+nom+ext;
        return desti;
    }
    public static String extensio(String r){
       	String ext="";
        int i = r.lastIndexOf('.');
        if (i > 0 &&  i < r.length() - 1) {
            ext = r.substring(i).toLowerCase();
        }
        return ext;
    }
    public static String Copia(String or,String de){
        File fo=new File(or);
        File fd=new File(de);
        try{
            InputStream in = new FileInputStream(fo);
            OutputStream out = new FileOutputStream(fd);
            byte[] buffer = new byte[1024];
            int lo;
            while ((lo = in.read(buffer)) > 0){out.write(buffer, 0, lo);}
            in.close();out.close();
          }
        catch(Exception e){return "error al copiar l'arxiu: "+fo.toString()+splitPan.FIL;}
        return "";
    }
    public static boolean presentaDoc(String r){
            File f=new File(r);
	    if(f.exists()){
	    	try {			
                    if (Desktop.isDesktopSupported()) {Desktop.getDesktop().open(f);return true;} 
                    else {error("sistema operatiu no permet obrir document");return false;}
	    	} 
	    	catch (IOException e) {error(" en la lectura del document");return false;}
	    }
	    return false;
	}
    static void error(String msg) {JOptionPane.showMessageDialog(controllingFrame,"ERROR"+splitPan.FIL+msg);}
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    public static void traslladar() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {createAndShowGUI();}
        });
    }
    private static void createAndShowGUI() {
    	frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.add(new trasllat());
        frame.pack();
        frame.setVisible(true);
    }
    class plantilla extends DefaultListCellRenderer {
            private static final long serialVersionUID = 1L;
            @SuppressWarnings("rawtypes")
            public Component getListCellRendererComponent(JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
            if (c instanceof JLabel && value instanceof File) {
                JLabel l = (JLabel)c;
                File f = (File)value;
                l.setText(f.getAbsolutePath());
            }
            return c;
        }
    }
    class traspasar extends TransferHandler {
        private static final long serialVersionUID = 1L;
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private JList list;
        traspasar(JList list) {this.list = list;}
        @Override
        public boolean importData(TransferHandler.TransferSupport info) {
            if (!info.isDrop()) {return false;}
            if (!info.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) { return false;}
            Transferable t = info.getTransferable();
            List<File> data;
            try {
                data = (List<File>)t.getTransferData(DataFlavor.javaFileListFlavor);
                ruta=data.get(0).toString();
            }
            catch (Exception e) {return false;}
            listModel = (DefaultListModel) list.getModel();
            listModel.removeAllElements();
            if(ruta.equals("")) return false;
            listModel.addElement(new File(ruta));
            String de=rutaCarpeta();
            if(de.equals("")){
                    listModel.removeAllElements();
                    listModel.addElement("ERROR directori indefinit,");
                    return false;
                }
            listModel.addElement((File)new File(de));
            File fde=new File(de);
            if(fde.exists())listModel.addElement("ATENCIO: hi ha un arxiu amb la mateixa ruta, si es fa el trasllat s'eliminar"+Func.rB.getString("a")+" l'arxiu previ");
            if(fde.isDirectory()){error("l'arxiu es una carpeta");return false;}
            presentaDoc(ruta);
            return true;
        }
    }
}