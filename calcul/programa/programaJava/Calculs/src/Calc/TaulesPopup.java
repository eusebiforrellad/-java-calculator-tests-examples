package Calc;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
public class TaulesPopup extends MouseAdapter {
    JPopupMenu popup;
    public TaulesPopup(JPopupMenu popupMenu) {popup = popupMenu;}
    @Override
    public void mousePressed(MouseEvent e) { maybeShowPopup(e);}
    @Override
    public void mouseReleased(MouseEvent e) { maybeShowPopup(e);}
    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {popup.show(e.getComponent(),e.getX(), e.getY()); }
    }
}
