/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.GameObjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Drakulic
 */
public class BorderLeft extends GameObject {
    private Rectangle border;
    private Rectangle ukras;
    public BorderLeft(){
        
        border=new Rectangle(30,110,20 ,700 );
        border.setFill(Color.SILVER);
        getChildren().add(border);
        
    }
}
