package plane_battle;

import java.awt.image.BufferedImage;

/**
 * 英雄机:是飞行物
 */
public class Hero extends FlyingObject
{
	public int dieIndex = 0;
	private BufferedImage[] images =
	{}; // 英雄机图片
	private int index = 20; // 英雄机图片切换索引
	public boolean die = false;
	private int doubleFire; // 双倍火力
	private int life; // 命
	public int step = 15;
	private int player = 0;

	/** 初始化数据 */
	public Hero(int xx, int yy, int p)
	{
		life = 1; // 初始3条命
		doubleFire = 0; // 初始火力为0
		images = new BufferedImage[]
		{ MainGame_Client.hero0, MainGame_Client.hero1 }; // 英雄机图片数组
		image = MainGame_Client.hero0; // 初始为hero0图片
		width = image.getWidth();
		height = image.getHeight();
		x = xx;
		y = yy;
		player = p;
	}

	/** 获取双倍火力 */
	public int isDoubleFire()
	{
		return doubleFire;
	}

	public void setLife(int l)
	{
		life = l;
	}

	/** 设置双倍火力 */
	public void setDoubleFire(int doubleFire)
	{
		this.doubleFire = doubleFire;
	}

	/** 增加火力 */
	public void addDoubleFire()
	{
		doubleFire = 40;
	}

	/** 增命 */
	public void addLife()
	{ // 增命
		life++;
	}

	/** 减命 */
	public void subtractLife()
	{ // 减命
		life--;
	}

	/** 获取命 */
	public int getLife()
	{
		return life;
	}

	/** 当前物体移动了一下，相对距离，x,y鼠标位置 */
	public void moveTo(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/** 越界处理 */
	@Override
	public boolean outOfBounds()
	{
		return false;
	}

	/** 发射子弹 */
	public Bullet_Hero[] shoot()
	{
		int xStep = 23; // 4半
		int yStep = 20; // 步
		if (doubleFire > 0)
		{ // 双倍火力
			Bullet_Hero[] bullets = new Bullet_Hero[2];
			bullets[0] = new Bullet_Hero(x + xStep, y - yStep); // y-yStep(子弹距飞机的位置)
			bullets[1] = new Bullet_Hero(x + 3 * xStep, y - yStep);
			return bullets;
		} else
		{ // 单倍火力
			Bullet_Hero[] bullets = new Bullet_Hero[1];
			bullets[0] = new Bullet_Hero(x + 2 * xStep, y - yStep);
			return bullets;
		}
	}

	/** 移动 */
	@Override
	public void move()
	{
		if (images.length > 0)
		{
			image = images[index++ / 10 % images.length]; // 切换图片hero0，hero1
		}
	}

	/** 碰撞算法 */
	public boolean hit(FlyingObject other)
	{

		int x1 = other.x - this.width / 2; // x坐标最小距离
		int x2 = other.x + this.width / 2 + other.width; // x坐标最大距离
		int y1 = other.y - this.height / 2; // y坐标最小距离
		int y2 = other.y + this.height / 2 + other.height; // y坐标最大距离

		int herox = this.x + this.width / 2; // 英雄机x坐标中心点距离
		int heroy = this.y + this.height / 2; // 英雄机y坐标中心点距离

		return herox > x1 && herox < x2 && heroy > y1 && heroy < y2; // 区间范围内为撞上了
	}

	public void run()
	{
		if (player == 1)
		{
			if (!MainGame_Server.U && !MainGame_Server.D && MainGame_Server.L && !MainGame_Server.R && x > 0)
			{
				x -= 8;
			} else if (!MainGame_Server.U && !MainGame_Server.D && !MainGame_Server.L && MainGame_Server.R
					&& x < MainGame_Server.WIDTH - 115)
			{
				x += 8;
			} else if (MainGame_Server.U && !MainGame_Server.D && !MainGame_Server.L && !MainGame_Server.R && y > 0)
			{
				y -= 7;
			} else if (!MainGame_Server.U && MainGame_Server.D && !MainGame_Server.L && !MainGame_Server.R
					&& y < MainGame_Server.HEIGHT - 165)
			{
				y += 7;
			} else if (MainGame_Server.U && !MainGame_Server.D && MainGame_Server.L && !MainGame_Server.R && x > 0
					&& y > 0)
			{
				x -= 8;
				y -= 7;
			} else if (!MainGame_Server.U && MainGame_Server.D && MainGame_Server.L && !MainGame_Server.R && x > 0
					&& y < MainGame_Server.HEIGHT - 165)
			{
				x -= 8;
				y += 7;
			} else if (MainGame_Server.U && !MainGame_Server.D && !MainGame_Server.L && MainGame_Server.R
					&& x < MainGame_Server.WIDTH - 115 && y > 0)
			{
				x += 8;
				y -= 7;
			} else if (!MainGame_Server.U && MainGame_Server.D && !MainGame_Server.L && MainGame_Server.R
					&& x < MainGame_Server.WIDTH - 115 && y < MainGame_Server.HEIGHT - 165)
			{
				x += 8;
				y += 7;
			}
		} else if (player == 2)
		{
			if (!MainGame_Server.UU && !MainGame_Server.DD && MainGame_Server.LL && !MainGame_Server.RR && x > 0)
			{
				x -= 8;
			} else if (!MainGame_Server.UU && !MainGame_Server.DD && !MainGame_Server.LL && MainGame_Server.RR
					&& x < MainGame_Server.WIDTH - 115)
			{
				x += 8;
			} else if (MainGame_Server.UU && !MainGame_Server.DD && !MainGame_Server.LL && !MainGame_Server.RR && y > 0)
			{
				y -= 7;
			} else if (!MainGame_Server.UU && MainGame_Server.DD && !MainGame_Server.LL && !MainGame_Server.RR
					&& y < MainGame_Server.HEIGHT - 165)
			{
				y += 7;
			} else if (MainGame_Server.UU && !MainGame_Server.DD && MainGame_Server.LL && !MainGame_Server.RR && x > 0
					&& y > 0)
			{
				x -= 8;
				y -= 7;
			} else if (!MainGame_Server.UU && MainGame_Server.DD && MainGame_Server.LL && !MainGame_Server.RR && x > 0
					&& y < MainGame_Server.HEIGHT - 165)
			{
				x -= 8;
				y += 7;
			} else if (MainGame_Server.UU && !MainGame_Server.DD && !MainGame_Server.LL && MainGame_Server.RR
					&& x < MainGame_Server.WIDTH - 115 && y > 0)
			{
				x += 8;
				y -= 7;
			} else if (!MainGame_Server.UU && MainGame_Server.DD && !MainGame_Server.LL && MainGame_Server.RR
					&& x < MainGame_Server.WIDTH - 115 && y < MainGame_Server.HEIGHT - 165)
			{
				x += 8;
				y += 7;
			}
		}
	}
}