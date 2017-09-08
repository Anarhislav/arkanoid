/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.GameObjects;


import arkanoid.Arkanoid;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Drakulic
 */
public class UserInterface extends GameObject{
    
    int score;
    int zivot;
    Text textScore;
    ImagePattern gameOver;
    ImagePattern won;
    Group z;
    List<Life> zivoti;
    Group g;
    Arkanoid game;
    
    Rectangle kvadar;
    
    
    public UserInterface(int zivot,Arkanoid instace){
        game=instace;
        z=new Group();
        g=new Group();
        g.setTranslateX(450);
        g.setTranslateY(350);
        this.zivot=zivot;
        score=0;
        zivoti=new ArrayList();
        for(int i=0;i<zivot;i++){
            Life l=new Life();
            l.setTranslateX(i*70);
            zivoti.add(l);
            z.getChildren().add(l);
        }
        z.setTranslateX(100);
        z.setTranslateY(785);
        getChildren().add(z);
        String t = String.format("%09d", score);
        textScore=new Text(t);
        textScore.setFont(Font.font("Comic Sans MS",50));
        textScore.setFill(Color.WHITE);
       
        textScore.setTranslateX(360);
        textScore.setTranslateY(60);
 
        getChildren().add(textScore);
        getChildren().add(g);
    }
    @Override
    public void update(){
        String t = String.format("%09d", score);
        textScore.setText(t);
    }
    public void addScore(int points){
        score+=points;
    }
    public void killLife(){
        if(zivoti.size()>0){
        Life i=zivoti.get(zivoti.size()-1);
        zivoti.remove(i);
        z.getChildren().remove(i);
        }
    }
    public void gameOver(){
        
        kvadar=new Rectangle(0,0,1000,900);
        
        
        gameOver= new ImagePattern(new Image("/resourses/gameover.jpg"), 0, 0, 1, 1, true);
      
        kvadar.setFill(gameOver);
        FadeTransition fd = new FadeTransition(Duration.seconds(2), kvadar);
        fd.setFromValue(0); fd.setToValue(1); fd.play();
        
       
        this.getChildren().add(kvadar);
    }
    
    public void newGame(){
        g.getChildren().remove(gameOver);
        score=0;
        zivot=3;
    }
    public void GameCleared(){
        
        kvadar=new Rectangle(1000,800);
        
        won=new ImagePattern(new Image("/resourses/win.png"), 0, 0, 1, 1, true);
        kvadar.setFill(won);
        FadeTransition fd = new FadeTransition(Duration.seconds(2), kvadar);
        fd.setFromValue(0); fd.setToValue(1); fd.play();
        this.getChildren().add(kvadar);
       MediaPlayer win=new MediaPlayer(new Media(this.getClass().getResource("/resourses/win music 1.wav").toExternalForm()));
       
        win.play();
    }

    public void restart() {
        System.out.print("remove object");
        FadeTransition fd = new FadeTransition(Duration.seconds(2), kvadar);
        fd.setFromValue(1); fd.setToValue(0); fd.play();
        getChildren().remove(kvadar);
        for(int i=0;i<zivot;i++){
            killLife();
        }
        zivot=3;
        for(int i=0;i<zivot;i++){
            addLife(i);
        }
    }
    
    public void addLife(int i){
        Life l=new Life();
       l.setTranslateX(i*70);
        zivoti.add(l);
       z.getChildren().add(l);
    }
   
    
}
