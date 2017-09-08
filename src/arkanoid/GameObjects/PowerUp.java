/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.GameObjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Drakulic
 */
public class PowerUp extends GameObject {
    
    
    String[] tip={"life","fireBall","size+","size-","extraBall"};
    int t;
    
    public PowerUp(int tip){
        t=tip;
        
        Circle krug=new Circle(30);
        krug.setFill(Color.AQUAMARINE);
        switch(t){
            
            case 0:{
                Life life=new Life();
                
                getChildren().addAll(krug,life);
                break;
            }
            case 1:{
                Circle lopta=new Circle(10);
                
                lopta.setFill(Color.RED);
                getChildren().addAll(krug,lopta);
                break;
            }
            case 2:{
                Rectangle p=new Rectangle(-5,-20, 10, 40);
                Rectangle m=new Rectangle(-20,-5, 40, 10);
               p.setFill(Color.CRIMSON);
               m.setFill(Color.CRIMSON);
                getChildren().addAll(krug,p,m);
                break;
   
            }
            case 3:{
                Rectangle m=new Rectangle(-20,-5, 40, 10);
                m.setFill(Color.CRIMSON);
                getChildren().addAll(krug,m);
                break;
            }
            
        }
        
        
        
    }
    public String getTip(){
        return tip[t];
    }

    @Override
    public void update(){      
        setTranslateY(getTranslateY()+Math.random()*5+1);
    }
      
}
