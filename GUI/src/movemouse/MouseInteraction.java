/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package movemouse;

/**
 *
 * @author pierrereimertz
 */
public class MouseInteraction {
    private long timeStamp;
    private String event;
    
    public MouseInteraction(String event){
        this.event = event;
        timeStamp = System.currentTimeMillis();
    }

    public long getTimeStamp() {
        return timeStamp;
    }
    
    public String getEvent() {
        return event;
    }
  
}
