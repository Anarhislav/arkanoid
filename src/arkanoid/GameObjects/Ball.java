/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.GameObjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author Drakulic
 */
public class Ball extends GameObject {
    
    private double velocity;
    private double angle;
    private Circle lopta;
    public Ball(double v,double a){
         lopta= new Circle(10);
         lopta.setFill(Color.WHITESMOKE);
         getChildren().add(lopta);
         
                 
    }
    public void setVelocity(double v){
        velocity=v;           
    }
    public void setAngle(double a){
        angle=a;
    }
    
    @Override
    public void update(){
        
        double novix;
        double noviy;
        novix=getTranslateX()+velocity*Math.cos(angle*Math.PI/180);
        
        noviy=getTranslateY()+velocity*Math.sin(-angle*Math.PI/180);
       
        setTranslateX(novix);
        setTranslateY(noviy);
    }

    public double getAngle() {
        return angle;
        
    }

    public void addAgnle(double addAngle) {
        angle-=addAngle;
        
        if(Math.cos(angle*Math.PI/180)>0.6)
            angle=Math.atan2(Math.sin(angle*Math.PI/180), 0.6)*180/Math.PI;
        if(Math.cos(angle*Math.PI/180)<-0.6)
            angle=Math.atan2(Math.sin(angle*Math.PI/180), -0.6)*180/Math.PI;    
        
        
    }
    
}
