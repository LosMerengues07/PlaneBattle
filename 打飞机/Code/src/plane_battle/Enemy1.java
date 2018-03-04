package plane_battle;

/**
 * 普通飞机 kind=0
 */
public class Enemy1 extends Plane implements Enemy
{
	/** 初始化数据 */
	public Enemy1(int t)
	{
		int type = t;
		kind = 1; // 表示为自杀飞机
		step = 3;
		life = 1;
		this.image = MainGame_Server.enemy0;
		width = image.getWidth();
		height = image.getHeight();
		y = -height;
		x = (int) (type * 16.8 - 840);
		// x=480-60; //最右
		x = 0;
		if (type < 65)
		{
		} else
		{
		}

	}

	public int getScore() // 得分
	{
		return 1;
	}

	public Bullet_Enemy shoot()
	{
		int xStep = 17;
		int yStep = 30;
		Bullet_Enemy bullets = new Bullet_Enemy(x + xStep, y + yStep);
		return bullets;
	}

	public void move()
	{
		if (MainGame_Server.L && x > 0)
		{
			x -= 8;
		}
		if (MainGame_Server.R && x < 420)
		{
			x += 8;
		}
		if(!MainGame_Server.L&&!MainGame_Server.R) {
			if(x<MainGame_Server.p1.x-8&&x<420) {
				x+=8;
			}else if(x>MainGame_Server.p1.x+8&&x>0) {
				x-=8;
			}else if(x>=MainGame_Server.p1.x-8&&x<=MainGame_Server.p1.x+8) {
				
			}
		}
		y += 3;
	}
}