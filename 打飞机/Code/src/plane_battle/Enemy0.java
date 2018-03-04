package plane_battle;

public class Enemy0 extends Plane implements Enemy
{
	/** 初始化数据 */
	public Enemy0(int t)
	{
		int type=t;
		kind = 0; // 表示为普通飞机
		step = 3;
		life=1;
		this.image = MainGame_Client.enemy1;
		width = image.getWidth();
		height = image.getHeight();
		y = -height;
		x = (int) (type*8);
		//x=480-78;最有

	}

	public int getScore() // 得分
	{
		return 1;
	}

	public Bullet_Enemy shoot()
	{
		int xStep = 23; 
		int yStep = 44; 
		Bullet_Enemy bullets = new Bullet_Enemy(x + xStep, y + yStep);
		return bullets;
	}
	public void move() // 移动
	{
		y += 2;
		if (flag % 30 == 0)
		{
			r = (int) (Math.random() * 120);
		}
		if (r <= 40&&x<480-78)
		{
			x += 4;
		} else if (r > 40 && r <= 80)
		{
		} else if(r>80&&x>0)
		{
			x -= 4;
		}
		flag++;
	}
}