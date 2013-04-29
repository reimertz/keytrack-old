/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package movemouse;

import java.awt.AWTException;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

public class MoveMouse {

    private static MouseController mc;
    private static SerialListener sl;
    private static SerialHandler sh;
    
    public static void main(String[] args) throws AWTException, NativeHookException {
        mc = new MouseController();
        sl = new SerialListener();
        sh = new SerialHandler();
        
        try {
                        GlobalScreen.registerNativeHook();
                }
                catch (NativeHookException ex) {
                        System.err.println("There was a problem registering the native hook.");
                        System.err.println(ex.getMessage());

                        System.exit(1);
                }
        
        SystemTrayController bc = new SystemTrayController(); 
        
    }
    
    public static MouseController getInstangeOfMouseController(){
        return mc;
    }
        public static SerialListener getInstangeOfSerialListener(){
        return sl;
    }
        
    public static SerialHandler getInstanceOfSerialHandler(){
        return sh;
    }
}
