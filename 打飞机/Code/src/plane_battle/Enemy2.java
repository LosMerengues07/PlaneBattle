package plane_battle;

/**
 * 加强飞机  kind=2
 */
public class Enemy2 extends Plane implements Enemy
{
	/** 初始化数据 */
	public Enemy2(int t)
	{
		int type=t;
		kind = 2; // 表示为加强飞机
		step = 2;
		life=2;
		this.image = MainGame_Server.enemy2;
		width = image.getWidth();
		height = image.getHeight();
		y = -height;
		x=(int)(type*12.4-930);
		//x = (int) (480-170);   //最右
		//x=0;                 // 最左

	}

	public int getScore() // 得分
	{
		return 3;
	}

	public Bullet_Enemy shoot()
	{
		int xStep = 70; 
		int yStep = 225; 
		Bullet_Enemy bullets = new Bullet_Enemy(x + xStep, y + yStep);
		return bullets;
	}
	public void move() // 移动
	{
		y += 2;
		if (flag % 30 == 0)
		{
			r = (int) (Math.random() * 9);
		}
		if (r <= 3&&x<480-170)
		{
			x += 3;
		} else if (r > 3 && r <= 6)
		{
		} else if(r>6&&x>0)
		{
			x -= 3;
		}
		flag++;
	}
}