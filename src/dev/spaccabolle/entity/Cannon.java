package dev.spaccabolle.entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Random;

import dev.spaccabolle.Launcher;
import dev.spaccabolle.gfx.Assets;
import dev.spaccabolle.input.KeyManager;

public class Cannon extends DynamicObject{
    
    private static final int SCARTO_X_FRECCIA=34;
    private static final int SCARTO_X_BOLLA=23;
    private static final int SCARTO_Y_BOLLA=15;
    private static final int SCARTO_CANNON_SX = 175;

    
    private boolean ballPos,bounce;
    private Random rand = new Random();
    
    private Ball ball;
    private CollectBall collectBall;
    
    private int angle=0;

    public Cannon(float x, float y, int width, int height, CollectBall collectBall) {
        super(x, y, width, height);
        this.collectBall=collectBall;
        this.setSpeed(50);
        this.ballPos=true;
        this.bounce=true;
        ball=new Ball(this.x+width/2-SCARTO_X_BOLLA,this.y+SCARTO_Y_BOLLA-50,Ball.BOBBLE_SIZE,Ball.BOBBLE_SIZE,getColor());
        while(ball.color==0) {
        
        	ball=new Ball(this.x+width/2-SCARTO_X_BOLLA,this.y+SCARTO_Y_BOLLA-50,Ball.BOBBLE_SIZE,Ball.BOBBLE_SIZE,getColor());
        	
        }
        collectBall.addBall(ball);
    }

    private int getColor() {
        return rand.nextInt(4);
    }
    
    private void getInput() {
        if(KeyManager.right && this.angle<60)
            angle += (int) speed;
        if(KeyManager.left && this.angle>-60)
            angle += (int)-speed;
    }
    
    private void newBall() {
        if(!ball.isMove && !ballPos ) {
            ball=new Ball(this.x+width/2-SCARTO_X_BOLLA,this.y+SCARTO_Y_BOLLA-50,Ball.BOBBLE_SIZE,Ball.BOBBLE_SIZE,getColor());
            while(ball.color==0) {
                
            	ball=new Ball(this.x+width/2-SCARTO_X_BOLLA,this.y+SCARTO_Y_BOLLA-50,Ball.BOBBLE_SIZE,Ball.BOBBLE_SIZE,getColor());
            	
            }
            collectBall.addBall(ball);
            ballPos=true;
            System.out.println("ball creata");
        }
     }
    
    private void shot() {
        if(ballPos && KeyManager.enter) {
            System.out.println("ball shot");
            ball.directMove=(float) Math.toDegrees(Math.toRadians(angle-90));
            ball.direct();
            ball.isMove=true;
            ballPos=false;
        }
    }
    
    public void checkBounce() {
    	if(this.x>Ball.RIGHT_BOUNCE) {
    		System.out.println("Superato limite destro");
    		System.out.println(Ball.RIGHT_BOUNCE);
    		this.bounce=false;
    	}else if(this.x< (-Ball.LEFT_BOUNCE)+SCARTO_CANNON_SX) {
    		this.bounce=true;
    	}
    }
    
    public void tick() {
    	checkBounce();
    	if(this.bounce) {
    		this.setX(this.x+(float)3.5);
    		if(!ball.isMove) {
    			ball.setX(this.x+(float)92);
    		
    		}
    	}else {
    		this.setX(this.x-(float)3.5);
    		if(!ball.isMove) {
    			ball.setX(this.x+(float)92);
    		}
    	}
        getInput();
        shot();
        newBall();
    }

    public void render(Graphics g) {
        AffineTransform at = AffineTransform.getTranslateInstance(Launcher.GAME_WIDTH/2-Cannon.SCARTO_X_FRECCIA,674);
       /* at.rotate(Math.toRadians(angle),Assets.arrow.getWidth()/2,Assets.arrow.getHeight()/2);
        at.scale(1,1);*/
        Graphics2D g2 = (Graphics2D)g;
        g.drawImage(Assets.cannon,(int)this.getX()-50,(int)this.getY()-50, this.getWidth(), this.getHeight(), null);
        /*g2.drawImage(Assets.arrow, at, null);*/
    }

}
