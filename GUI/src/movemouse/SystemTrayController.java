/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package movemouse;

/**
 *
 * @author pierrereimertz
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SystemTrayController {
    
    
    
   
    boolean keyIsOn = false;
    boolean irIsOn = false;
    TrayIcon trayIcon;
    Image image = Toolkit.getDefaultToolkit().getImage("SystemTray.gif");
    
    public SystemTrayController() {
        Runnable runner = new Runnable() {
            public void run() {
                if (SystemTray.isSupported()) {

                    SystemTray tray = SystemTray.getSystemTray();
                    
                    PopupMenu popup = new PopupMenu();
                    
                    final MenuItem onOff = new MenuItem("Turn ON KeyTracking.");
                    ActionListener aOnOff = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if(keyIsOn){
                                onOff.setLabel("Turn ON KeyTracking.");
                                trayIcon.setImage(Toolkit.getDefaultToolkit().getImage("SystemTray.gif"));
                                GlobalKeyListener.getInstance().stopListeningForInput();
                                keyIsOn = false;
                            }
                            else{
                                onOff.setLabel("Turn OFF KeyTrackin.");
                                trayIcon.setImage(Toolkit.getDefaultToolkit().getImage("SystemTrayOn.gif"));
                                GlobalKeyListener.getInstance().startListeningForInput();
                                keyIsOn = true;
                                
                            }
                        }
                    };
                    onOff.addActionListener(aOnOff);
                    popup.add(onOff);
                    
                    
                    final MenuItem ir = new MenuItem("Turn ON IRListener.");
                    ActionListener aIR = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if(irIsOn){
                                ir.setLabel("Turn ON IRListener.");
                                trayIcon.setImage(Toolkit.getDefaultToolkit().getImage("SystemTray.gif"));
                                MoveMouse.getInstangeOfSerialListener().close();
                                irIsOn = false;
                            }
                            else{
                                ir.setLabel("Turn OFF IRListener.");
                                trayIcon.setImage(Toolkit.getDefaultToolkit().getImage("SystemTrayOn.gif"));
                                MoveMouse.getInstangeOfSerialListener().initialize();
                                irIsOn = true;
                                
                            }
                        }
                    };
                    ir.addActionListener(aIR);
                    popup.add(ir);
                    
                    
                    MenuItem move = new MenuItem("Move Mouse a Specific amount steps");

                    ActionListener aMove = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {

                            String j = JOptionPane.showInputDialog("Enter the x y values you want to move the mouse.", null);
                            String xyValues[] = j.split("\\s+");
                            try {
                                if (xyValues.length > 0) {
                                    int x = Integer.parseInt(xyValues[0]);
                                    int y = Integer.parseInt(xyValues[1]);
                                    System.out.println("Moving Mouse");
                                    MoveMouse.getInstangeOfMouseController().moveToPoint(x, y);
                                }
                            } catch (Exception ex) {
                                System.out.println("Error");
                                System.out.println(ex.getMessage());
                            }
                        }
                    };

                    move.addActionListener(aMove);
                    popup.add(move);


                    MenuItem exit = new MenuItem("Exit");
                    ActionListener aExit;
                    aExit = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("Close KeyTrack");
                            System.exit(0);
                        }
                    };
                    exit.addActionListener(aExit);
                    popup.add(exit);

                    trayIcon = new TrayIcon(image, "The Tip Text", popup);
                    try {
                        tray.add(trayIcon);
                    } catch (AWTException e) {
                        System.err.println("Can't add to tray");
                    }

                } else {
                    System.err.println("Tray unavailable");
                }
            }
        };
    EventQueue.invokeLater(runner);
    }
    public void updateIcon(Image i){
        trayIcon.setImage(i);
        
    }
}
