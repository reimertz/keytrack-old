/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package movemouse;

import java.util.ArrayList;

/**
 *
 * @author pierrereimertz
 */

public  class SerialHandler {
    private ArrayList<MouseInteraction> mInteractions = new ArrayList<MouseInteraction>(); 
            
    public SerialHandler(){
        
    } 
    
    public void eventToSerialHandler(String s){
       MouseInteraction temp = new MouseInteraction(s);
       
       if(mInteractions.isEmpty()){
           mInteractions.add(temp);   
       }
               
       else if( ((temp.getTimeStamp() - mInteractions.get(0).getTimeStamp()) < 400)){
            mInteractions.add(temp);    
       }
       else{
           readMouseInteractions(mInteractions);
           mInteractions.clear();
           mInteractions.add(temp);
       }
    }
    
    private void readMouseInteractions(ArrayList<MouseInteraction> mI ) {
        if( mI.size() == 2 &&mI.get(0).getEvent().equals("1ON") && mI.get(1).getEvent().equals("1OFF")){

            MoveMouse.getInstangeOfMouseController().leftClick();
         }
       else if( mI.size() == 2 &&mI.get(0).getEvent().equals("2ON") && mI.get(1).getEvent().equals("2OFF")){

            MoveMouse.getInstangeOfMouseController().rightClick();
         }
        else if( mI.size() == 4 &&mI.get(0).getEvent().equals("1ON") && mI.get(1).getEvent().equals("1OFF")&& mI.get(2).getEvent().equals("2ON")&& mI.get(3).getEvent().equals("2OFF")){

            MoveMouse.getInstangeOfMouseController().moveHorisontal(15);
         }
        else if( mI.size() == 4 &&mI.get(0).getEvent().equals("2ON") && mI.get(1).getEvent().equals("2OFF")&& mI.get(2).getEvent().equals("1ON")&& mI.get(3).getEvent().equals("1OFF")){

            MoveMouse.getInstangeOfMouseController().moveHorisontal(-15);
         }
    }
    
}
     