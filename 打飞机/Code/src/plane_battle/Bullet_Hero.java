package plane_battle;

import java.awt.image.BufferedImage;

/** 
 * 子弹类:是飞行物 
 */  
public class Bullet_Hero extends FlyingObject {  
    private int speed = 14;  //移动的速度  
    /** 初始化数据 */  
    public Bullet_Hero(int x,int y){  
        this.x = x;  
        this.y = y;  
        this.image = MainGame_Client.bullet;  
    }  
  
    /** 移动 */  
    @Override  
    public void move(){     
        y-=speed;  
    }  
  
    /** 越界处理 */  
    @Override  
    public boolean outOfBounds() {  
        return y<-height;  
    }  
    public boolean used=false;
  public void setImage(BufferedImage img) {
	  this.image=img;
	  
  }
}  