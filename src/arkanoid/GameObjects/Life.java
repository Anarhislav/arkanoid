/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.GameObjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Drakulic
 */
public class Life extends GameObject {
    
    public Life(){
       Rectangle telo=new Rectangle(-20,-4,40,8);
       Circle lend=new Circle(-20, 0, 4);
       Circle  rend=new Circle(20,0,4);
        telo.setFill(Color.SILVER);
        lend.setFill(Color.RED);
        rend.setFill(Color.RED);
        getChildren().addAll(lend,rend,telo);  
    }
    
}
