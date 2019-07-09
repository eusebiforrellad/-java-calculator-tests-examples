package Calc;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author EFO
 */
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
public class Colors extends JPanel implements ChangeListener {
    private static final long serialVersionUID = 1L;
    protected JColorChooser tcc;
    static JFrame frame=new JFrame("Colors");
    static int possicio;
    public Colors() {
        frame.setLocation(0,0);
        frame.setSize(200,200);
        tcc = new JColorChooser(Color.BLACK);
        tcc.getSelectionModel().addChangeListener(this);
        add(tcc, BorderLayout.PAGE_END);
    }
    public void stateChanged(ChangeEvent e) {
        taulaF.colors[possicio]=tcc.getColor();
        frame.dispose();
    }
    private static void createAndShowGUI() {
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        JComponent newContentPane = new Colors();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        frame.pack();
        frame.setVisible(true);
    }
    public static void colorsIntr(int i) {
        possicio=i;
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}