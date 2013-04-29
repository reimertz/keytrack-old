/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package movemouse;

import java.awt.event.KeyEvent;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 *
 * @author pierrereimertz
 */
public class GlobalKeyListener implements NativeKeyListener {

    MouseController mv;
    private static GlobalKeyListener gKL;

    private GlobalKeyListener(){
        this.mv = MoveMouse.getInstangeOfMouseController();
        
    }

    public static GlobalKeyListener getInstance(){
        if(gKL==null){
            gKL = new GlobalKeyListener();
        }
        return gKL;
    }
    public void startListeningForInput(){
        GlobalScreen.getInstance().addNativeKeyListener(GlobalKeyListener.getInstance());
    }
    public void stopListeningForInput(){
        GlobalScreen.getInstance().removeNativeKeyListener(GlobalKeyListener.getInstance());
    }
    
    
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                mv.moveVertical(-15);
                break;
            case KeyEvent.VK_DOWN:
                mv.moveVertical(15);
                break;
            case KeyEvent.VK_LEFT:
                mv.moveHorisontal(-15);
                break;
            case KeyEvent.VK_RIGHT:
                mv.moveHorisontal(15);
                break;
            case KeyEvent.VK_SPACE:
                mv.leftClick();
                break;
            default:
        }
        System.out.println("x: " + MouseController.MOUSE_X + "y: " + MouseController.MOUSE_Y);

        if (e.getKeyCode() == NativeKeyEvent.VK_ESCAPE) {
            GlobalScreen.unregisterNativeHook();
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nke) {}

    @Override
    public void nativeKeyTyped(NativeKeyEvent nke) {}

}

