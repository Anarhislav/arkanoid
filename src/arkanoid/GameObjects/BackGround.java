/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.GameObjects;


import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Drakulic
 */
public class BackGround extends GameObject{
    private Rectangle pozadina;
    
    public BackGround(double sirina, double visina,Image slika){
        pozadina= new Rectangle(0,0,sirina,visina);
        ImagePattern image= new ImagePattern(slika, 0, 0, 1, 1, true);
        pozadina.setFill(image);
        getChildren().add(pozadina);
    }
    
}
