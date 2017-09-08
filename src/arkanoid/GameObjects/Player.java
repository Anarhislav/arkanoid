/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.GameObjects;

import arkanoid.Arkanoid;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 *
 * @author Drakulic
 */
public class Player extends GameObject implements EventHandler<KeyEvent>{
    private Arkanoid game;
    public void setBall() {
        stanje=stanje.withBall;
    }

    public boolean isGameOver() {
        return stanje==stanje.gameOver;
    }

    
    
    private enum Stanje {withBall,withoutBall,gameOver}
    Stanje stanje=Stanje.withBall;
    double velocity;
    int pausa=0;
    Ball lopta;
    Rectangle telo;
    Circle lend;
    Circle rend;
    int kretanje=0;
    double pozicija;
    public void setPozicija(double p){
        pozicija=p;
    }
    public Player(double velocity,Ball lopta,Arkanoid a){
        game=a;
        this.lopta=lopta;
        this.velocity=velocity;
        telo=new Rectangle(-50,-10,100,20);
        lend=new Circle(-50, 0, 10);
        rend=new Circle(50,0,10);
        telo.setFill(Color.SILVER);
        lend.setFill(Color.RED);
        rend.setFill(Color.RED);
        getChildren().addAll(lend,rend,telo);  
    }
    @Override
    public void update(){
        
        if(stanje==Stanje.withBall){
            lopta.setTranslateX(getTranslateX());
            lopta.setTranslateY(getTranslateY()-20);
        }
        
        
        
        if(kretanje!=0){
            if(kretanje==-1&&getTranslateX()>110){                
            double novix=getTranslateX()-velocity;
            setTranslateX(novix);
            }else if(kretanje==1&&getTranslateX()<890){                
               double novix=getTranslateX()+velocity;
              setTranslateX(novix);
           
            }
        }
    }
    public boolean WithBall(){
        return stanje==Stanje.withBall;
    }
    public void gameOver(){
        stanje=Stanje.gameOver;
        lopta.setTranslateX(getTranslateX());
        lopta.setTranslateY(getTranslateY()-20);
        kretanje=0;
    }
    
    @Override
    public void handle(KeyEvent event) {
        
        if (stanje!=stanje.gameOver){
        if (event.getCode() == KeyCode.RIGHT && event.getEventType() == KeyEvent.KEY_PRESSED) {
            kretanje=1;
        } else if (event.getCode() == KeyCode.RIGHT && event.getEventType() == KeyEvent.KEY_RELEASED) {
            kretanje=0;
        } else if (event.getCode() == KeyCode.LEFT && event.getEventType() == KeyEvent.KEY_PRESSED) {
            kretanje=-1;
        } else if (event.getCode() == KeyCode.LEFT && event.getEventType() == KeyEvent.KEY_RELEASED) {
            kretanje=0;
        } else if (event.getCode() == KeyCode.SPACE && event.getEventType() == KeyEvent.KEY_PRESSED) {
            if(stanje==Stanje.withBall){
            stanje=Stanje.withoutBall;
            lopta.setVelocity(8);
            lopta.setAngle(90);
            }
        } else if(event.getCode() == KeyCode.P && event.getEventType() == KeyEvent.KEY_PRESSED) {
            
            if(pausa==0){
            lopta.setVelocity(0);
             pausa=1;}
            else{
                lopta.setVelocity(8);
            }
        }}else if(event.getCode() == KeyCode.N && event.getEventType() == KeyEvent.KEY_PRESSED) {
            if(stanje==Stanje.gameOver){
                game.restart();
                stanje=Stanje.withBall;
                lopta.setAngle(90);
                lopta.setVelocity(0);
            }
        }
    }
}
