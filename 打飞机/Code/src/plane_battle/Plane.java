package plane_battle;

import java.awt.image.BufferedImage;

public abstract class Plane extends FlyingObject
{
	public int dieIndex=0;
	public static int[] Map =
	{ 70, 23, 46, 79, 59, 97, 2, 82, 76, 34, 23, 42, 75, 38, 20, 76, 48, 83, 6, 79, 74, 41, 68, 19, 83 };
	public int life;
	public int step; // 移动步长
	public boolean enabled = false;
	public BufferedImage[] images =new BufferedImage[5];

	public abstract int getScore();

	int r = 0;
	int flag = (int) (Math.random() * 10);
	public boolean die = false;

	public boolean outOfBounds() // 判断是否出界
	{
		return y > MainGame_Client.HEIGHT;
	}

	public void move() // 移动
	{
	}

	/** 发射子弹 */
	public Bullet_Enemy e_bullets[] =
	{};

	public abstract Bullet_Enemy shoot();

	public void setImage(BufferedImage img)
	{
		this.image = img;
	}

}
