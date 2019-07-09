package Calc;
import java.io.File;
import javax.swing.filechooser.*;
public class filtre extends FileFilter {
    public static String filtre="";
    public boolean accept(File f) {
        if (f.isDirectory())return true;
        String ext = getExtension(f);
        if (ext != null&&ext.equals(filtre))return true;
        return false;
    }
public static String getExtension(File f) {
        String s = f.getName();
        int i = s.lastIndexOf('.');
        if(i>0&&i<s.length()-1)return s.substring(i+1).toLowerCase();
        return null;
    }
    public String getDescription() {
        if(filtre.equals("txt")){return "funcions format txt";}
        return "";}
}