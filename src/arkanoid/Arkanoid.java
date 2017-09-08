/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid;


import arkanoid.GameObjects.*;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.pow;
import java.util.ArrayList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Drakulic
 */
public class Arkanoid extends Application {
    private Group root;
    private Scene scene;
    private Group pozadina;
    private Group gameElements;
    int gameover=0;
    private Player player;
    private List<Block> blokovi;
    private int zivot=3;
    private Ball lopta;
    private BorderLeft leviZid;
    private BorderRight desniZid;
    private BorderTop gornjiZid;
    private boolean hited=false;
    private UserInterface UI;
    Polygon kvadar;
    AudioClip playerHit;
    AudioClip wallHit;
    AudioClip blockHit;
    AudioClip levelCleared;
    AudioClip lostLife;
    MediaPlayer start1;
    MediaPlayer start2;
    MediaPlayer start3;
    public MediaPlayer gameOver;
      int level=1;
     private Group boss;
     List<PowerUp> powers;
   private Group powerUps;
   private int powerUp=0;
   private int powerCountDown=100;
    @Override
    public void start(Stage stage) {
        //game objecti
        boss=new Group();
        gameElements=new Group();
        pozadina=new Group();
        root = new Group();
        powerUps=new Group();
        
        makeAudioClips();
        start1.setCycleCount(Timeline.INDEFINITE);
        start1.play();
        
        
        
        //pravljenje lista objekata
        blokovi=new ArrayList<>();
        powers=new ArrayList<>();
        
        prepareBorder();
        prepareScene1();
        
        lopta=new Ball(0, 0);
        gameElements.getChildren().add(lopta);
        
        
        player=new Player(8,lopta,this);
        player.setTranslateX(500);
        player.setTranslateY(750);
        
        gameElements.getChildren().add(player);
        
        UI=new UserInterface(zivot,this);
        
        
        //dodavanje u root
        root.getChildren().add(pozadina);
        root.getChildren().add(boss);
        root.getChildren().add(powerUps);
        root.getChildren().add(gameElements);
        root.getChildren().add(UI);
        
       
        scene = new Scene(root, 1000, 800);
        scene.setOnKeyPressed(player);
        scene.setOnKeyReleased(player);
        scene.setFill(Color.BLACK);
        stage.setResizable(false);       
        stage.setTitle("Arkanoid");
        stage.setScene(scene);
        stage.show();
        
        
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                update();
            }
        }.start();
    }
    int wait=1;
    
    private void update(){
        powers.forEach(p->{
            p.update();
            
            if(p.getTranslateY()>900){
                powers.remove(p);
                powerUps.getChildren().remove(p);
            
            }
            if(p.getBoundsInParent().intersects(player.getBoundsInParent())){
                
                aktivirajPowerUp(p.getTip());
                
                powers.remove(p);
                powerUps.getChildren().remove(p);
            }
            
        });
        player.update();
        lopta.update();
        
        checkHitBounds();
        if(hited==true){
            checkLeverCleared();
            wait--;
            if(wait<=0){
                wait=1;
                hited=false;
            }
            
        }
        if(hited==false)
        checkHitBlock();
        UI.update();
        
        checkBall();
        
    }
    private void checkHitBounds(){
        
        if(lopta.getBoundsInParent().intersects(leviZid.getBoundsInParent())){           
           
            wallHit.play(0.5);
            double ugao=vertikalniSudar(lopta.getAngle());
           lopta.setTranslateX(50+10);
            
            lopta.setAngle(ugao);
        }
        if(lopta.getBoundsInParent().intersects(desniZid.getBoundsInParent())){
            
            wallHit.play(0.5);
            double ugao=vertikalniSudar(lopta.getAngle());
            lopta.setTranslateX(950-10);
            
            lopta.setAngle(ugao); 
            
            
        }
        
      
  
        if(lopta.getBoundsInParent().intersects(gornjiZid.getBoundsInParent())){
           
           wallHit.play(0.5);
            double ugao=   horizontalniSudar(lopta.getAngle());

             lopta.setAngle(ugao);
   
        }
         if(lopta.getBoundsInParent().intersects(player.getBoundsInParent())){
             if(!player.WithBall()&&gameover==0){
                 
             playerHit.play(0.5);}
             if(!player.WithBall()&&lopta.getTranslateY()<=player.getTranslateY()){
           
             lopta.setTranslateY(player.getTranslateY()-25);
              double pozicija=lopta.getTranslateX()-player.getTranslateX();
              pozicija=pozicija/65;
              double addAngle=60*pozicija;
              
               
            
              double ugao=   horizontalniSudar(lopta.getAngle());

             lopta.setAngle(ugao);
             lopta.addAgnle(addAngle);
             }
   
        }    
        
        
    }
    private void checkBall(){
        if(lopta.getTranslateY()>player.getTranslateY()+50){
            if(zivot>1)
            lostLife.play(0.5);
            lopta.setVelocity(0);
            player.setBall();
            zivot--;
            
            UI.killLife(); 
            if(zivot<=0){
                if(gameover==0)
                    gameOver.play();
                
                gameover=1;
                player.gameOver();
                UI.gameOver();
                
                
            }
        }
   
    }
    private void checkHitBlock(){
        
        Block min=null;
        double duzina=200;
        hited=false;
        for(int i=0;i<blokovi.size();i++){
            if(lopta.getBoundsInParent().intersects(blokovi.get(i).getBoundsInParent())){
                hited=true;
                Block b=blokovi.get(i);
                double rastojanje=duzina(lopta.getTranslateX(),lopta.getTranslateY(),b.getTranslateX(),b.getTranslateY());
                
                if(rastojanje<duzina){
                    duzina=rastojanje;
                    min=b;
                }
                
                
            }
           
       
        }
         if(min!=null){
                odbijanje(min);
                min.damage();
                
                blockHit.play(0.5);
                min.decLife();
                
                if(min.zivot==0){
                min.animationDie(gameElements);
                UI.addScore(50);
                if(Math.random()<0.3){
                    double tip=Math.random();
                    PowerUp power=null;
                    if(tip<0.2) 
                      power  = new PowerUp(0);
                    else if(tip<0.4)
                        power  = new PowerUp(1);
                    
                    else  if(tip<0.6)
                        power  = new PowerUp(2);
                    else if(tip<0.8)
                        power  = new PowerUp(3);
                        
                    if(power!=null){
                    
                    power.setTranslateX(min.getTranslateX());
                    power.setTranslateY(min.getTranslateY());
                    powers.add(power);
                    powerUps.getChildren().add(power);
                    }
                }
                blokovi.remove(min);
                
                }else{
                UI.addScore(10);
                
                
                }
         }
    
    }
      
    
    private double horizontalniSudar(double ugao){
        double noviugao=Math.atan2(-Math.sin(ugao*Math.PI/180), Math.cos(ugao*Math.PI/180));
        return noviugao*180/Math.PI;
    }
    private double vertikalniSudar(double u){
                 double ugao=u;
           
            double noviugao=Math.acos(-Math.cos(ugao*Math.PI/180));
            
            if(Math.sin(ugao*Math.PI/180)<0) noviugao=-noviugao;
            return noviugao*180/Math.PI;
    }
    private void prepareBorder(){
        desniZid =new BorderRight();
        leviZid=new BorderLeft();
        gornjiZid=new BorderTop();
        
        pozadina.getChildren().addAll(desniZid,leviZid,gornjiZid);
    }
    
    public void prepareScene1(){
        level=1;
        
        Image slika=new Image("/resourses/pozadinaScene1.png");
        BackGround p= new BackGround(900, 700,slika);
        p.setTranslateX(50);
        p.setTranslateY(110);
        pozadina.getChildren().add(p);
               
        popuniredBoss(5,0,2,0,15);
        popuniredBoss(4,1,1,0,15);
        popuniredBoss(3,2,1,0,15);
       popuniredBoss(1,3,1,0,15);
            
        }
    public void prepareScene2(){
        System.out.println("scena2");
        
        lopta.setVelocity(0);
        lopta.setAngle(0);
        player.setBall();
        start1.stop();
        start2.setCycleCount(Timeline.INDEFINITE);
        start2.play();
        Image slika=new Image("/resourses/pozadinaScene2.png");
        BackGround p= new BackGround(900, 700,slika);
        p.setTranslateX(50);
        p.setTranslateY(110);
        pozadina.getChildren().add(p);
               
        popuniredBoss(3,0,1,0,2); 
        popuniredBoss(1,0,2,2,3);
        popuniredBoss(4,1,1,0,4);popuniredBoss(1,1,2,4,5);
        popuniredBoss(3,2,1,0,6);popuniredBoss(1,2,2,6,7);
        popuniredBoss(4,3,1,0,8); popuniredBoss(1,3,2,8,9);
       popuniredBoss(3,4,1,0,10); popuniredBoss(1,4,2,10,11);
        popuniredBoss(5,5,2,0,13);
        
    }
    public void prepareScene3(){
        System.out.println("scena3");
        
        lopta.setVelocity(0);
        lopta.setAngle(0);
        player.setBall();
        start2.stop();
        start3.setCycleCount(4);
        
        start3.play();
        start3.setOnEndOfMedia(()->{
            start3.seek(Duration.ZERO);
            start3.play();
        });
        Image slika=new Image("/resourses/pozadinaScene3.png");
        BackGround p= new BackGround(900, 700,slika);
        p.setTranslateX(50);
        p.setTranslateY(110);
        pozadina.getChildren().add(p);
               
        popuniredBoss(4,0,2,0,1);  popuniredBoss(4,0,2,14,15);
        popuniredBoss(4,1,2,1,2);  popuniredBoss(2,1,1,3,12); popuniredBoss(4,1,2,13,14);
        popuniredBoss(2,2,1,2,13); popuniredBoss(3,2,2,4,5); popuniredBoss(3,2,2,10,11);
        popuniredBoss(2,3,1,2,13);
        popuniredBoss(4,4,2,0,2); popuniredBoss(2,4,1,2,13); popuniredBoss(4,4,2,13,15);
        popuniredBoss(4,5,2,0,1);   popuniredBoss(2,5,1,2,4); popuniredBoss(2,5,1,6,9); popuniredBoss(2,5,1,11,13); popuniredBoss(4,5,2,14,15);
        popuniredBoss(4,6,2,0,1); popuniredBoss(4,6,2,14,15);
        
        
        for(int i=0;i<boss.getChildren().size();i++){
        TranslateTransition tt=new TranslateTransition(Duration.seconds(5),boss.getChildren().get(i));
        tt.setByY(100);
        tt.setCycleCount(Timeline.INDEFINITE);
        tt.setAutoReverse(true);
        tt.play();
        }
    }
    
    private void popunired(int vrsta,int red,int tezina,int od, int doo){
        double fromx=150;
        double fromy=225;
      
        for(int i=od;i<doo;i++){
            Block blok=new Block(fromx+50*i,fromy+red*25,vrsta,tezina);
            blokovi.add(blok);
            gameElements.getChildren().add(blok);
        }
    }
    private void popuniredBoss(int vrsta,int red,int tezina,int od, int doo){
        double fromx=150;
        double fromy=225;
      
        for(int i=od;i<doo;i++){
            Block blok=new Block(fromx+50*i,fromy+red*25,vrsta,tezina);
            blokovi.add(blok);
            boss.getChildren().add(blok);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void odbijanje(Block b) {
       
        double xb=b.getTranslateX();
        double yb=b.getTranslateY();
        
        double xl=lopta.getTranslateX();
        double yl=lopta.getTranslateY();
        
        double xrazlika=Math.abs(xl-xb)/35;
        double yrazlika=Math.abs(yl-yb)/16;
        System.out.println("xrazlika"+xrazlika+"   yrazlika"+yrazlika);
        if(xrazlika>yrazlika){
            
          //  lopta.setTranslateX(lopta.getTranslateX()+xl-xb);
            double newangle=vertikalniSudar(lopta.getAngle());
            lopta.setAngle(newangle);
        }else{
         //  lopta.setTranslateY(lopta.getTranslateY()+yl-yb);
            double newangle=horizontalniSudar(lopta.getAngle());
            lopta.setAngle(newangle);
        }
            
            
        
    }

    private void checkLeverCleared() {
       
        
        if(blokovi.size()==0){
            
            
             player.setBall();
            
            nextLevel();
        }
    }

    private void makeAudioClips() {
        playerHit=new AudioClip((this.getClass().getResource("/resourses/hitPlayer.wav").toExternalForm()));
        blockHit=new AudioClip((this.getClass().getResource("/resourses/hitBlock.wav").toExternalForm()));
        wallHit=new AudioClip((this.getClass().getResource("/resourses/hitWall.wav").toExternalForm()));
       levelCleared=new AudioClip((this.getClass().getResource("/resourses/levelCleared.wav").toExternalForm()));
        gameOver=new MediaPlayer(new Media(this.getClass().getResource("/resourses/gameOver.wav").toExternalForm()));
        lostLife=new AudioClip((this.getClass().getResource("/resourses/lostLife.wav").toExternalForm()));
        start1=new MediaPlayer(new Media(this.getClass().getResource("/resourses/scena1.mp3").toExternalForm()));
        start2=new MediaPlayer(new Media(this.getClass().getResource("/resourses/scena2.mp3").toExternalForm()));
        start3=new MediaPlayer(new Media(this.getClass().getResource("/resourses/scena3.mp3").toExternalForm()));
        
    }

    
    public double duzina(double x,double y,double x1,double y1){
        
        return Math.sqrt(pow(x-x1,2)+pow(y-y1,2));
    }
    
    
    
    public void nextLevel() {
        level++;
      System.out.println(level);
       if(level==2)
           prepareScene2(); 
       if(level==3)
           prepareScene3();
       if(level==4){
           UI.GameCleared();
           start3.pause();
       }
    }
    
   
    public void restart(){
        
        zivot=3;
        level=1;
        blokovi.clear();
        boss.getChildren().clear();
        UI.restart();
        
        prepareScene1();
          
    }

    private void aktivirajPowerUp(String tip) {
       switch(tip){
           case "life": {
               
               zivot++;
               UI.addLife(zivot-1);
               
               
               break;
           }
           case "fireBall":{
               
               
               
               break;
           }
           case "size+":{
               player.setScaleX(1.5);
               
               break;
           }
           case "size-":{
               player.setScaleX(0.5);
               
               break;
           }
           
       }
    }
    
}
