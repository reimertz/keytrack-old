/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package movemouse;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;

/**
 *
 * @author pierrereimertz
 */
public class MouseController {

    private Robot robot;
    public static int MOUSE_X = MouseInfo.getPointerInfo().getLocation().x;
    public static int MOUSE_Y = MouseInfo.getPointerInfo().getLocation().y;
    public static final Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
  
    public MouseController() throws AWTException {
        robot = new Robot();
        System.out.println("höjd " + SCREEN_DIMENSION.height  + "bred: " + SCREEN_DIMENSION.width);
    }

    public void moveHorisontal(int x) {
        updateMousePosition();
        System.out.println("Move Horisontal");
        int newX = MOUSE_X + x;
        if(newX < 0) {
            robot.mouseMove(0, MOUSE_Y);
        }
        
        else if(newX > SCREEN_DIMENSION.width){
            robot.mouseMove(SCREEN_DIMENSION.width -1, MOUSE_Y);
        }
        
        else{   
            robot.mouseMove(newX, MOUSE_Y);
        }
    }

    public void moveVertical(int y) {
        updateMousePosition();
        System.out.println("Move Vertical");
        int newY = MOUSE_Y + y;
        
        if(newY < 0) {
            robot.mouseMove(MOUSE_X, 0);
        }
        
        else if(newY > SCREEN_DIMENSION.height){
            robot.mouseMove(MOUSE_X, SCREEN_DIMENSION.height-1);
        }
        
        else{   
            robot.mouseMove(MOUSE_X, newY);
        }
    }

    public void moveToPoint(int x, int y) {
        this.moveHorisontal(x);
        this.moveVertical(y);
    }

    public void updateMousePosition() {
        MOUSE_X = MouseInfo.getPointerInfo().getLocation().x;
        MOUSE_Y = MouseInfo.getPointerInfo().getLocation().y;
    }
    
    public void leftClick(){
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        System.out.println("left click");
    }

    void rightClick() {
        robot.mousePress(InputEvent.BUTTON2_MASK);
        robot.mouseRelease(InputEvent.BUTTON2_MASK);
        System.out.println("right click");
    }
}
