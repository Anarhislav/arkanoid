/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.GameObjects;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author Drakulic
 */
public class Block extends GameObject{
    
    
    Rectangle block;
    Rectangle ukras;
    public int zivot;
    ImagePattern ip;
    public Block(double x, double y, int vrsta,int zivot){
        setTranslateX(x);
        setTranslateY(y);
        String vr=Integer.toString(vrsta);
        ip=new ImagePattern(new Image("/resourses/brick"+vr+".png"));
        
        block=new Rectangle(-25, -12.5,50, 25);
        block.setFill(ip);
        block.setStroke(Color.BLACK);
        getChildren().add(block);
        
        this.zivot=zivot;
        
            
    }
    public void decLife(){
        zivot--;
    }
    public boolean isDead(){
        return zivot<=0;
    }
    
    public void damage(){
        setOpacity(0.5);
    }
    public void animationDie(Group elements){
        ScaleTransition st=new ScaleTransition(Duration.seconds(1),block);
        st.setFromX(1); st.setToX(0);
        st.setFromY(1); st.setToY(0);
        st.play();
        st.setOnFinished(b->{
                elements.getChildren().remove(this);
        
        });
        
       
        
    }
    
}
