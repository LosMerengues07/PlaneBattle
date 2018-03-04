package plane_battle;

/** 
 * 子弹类:是飞行物 
 */  
public class Bullet_Enemy extends FlyingObject {  
    private int speed = 5;  //移动的速度  
      
    /** 初始化数据 */  
    public Bullet_Enemy(int x,int y){  
        this.x = x;  
        this.y = y;  
        this.image = MainGame_Client.bullet_enemy;  
    }  
  
    /** 移动 */  
    @Override  
    public void move(){     
        y+=speed;  
    }  
  
    /** 越界处理 */  
    @Override  
    public boolean outOfBounds() {  
    	 return y>MainGame_Client.HEIGHT;  
    }  
    public int used=0;
  
}  