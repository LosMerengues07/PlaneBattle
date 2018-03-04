package plane_battle;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainGame_Server extends JPanel
{
	URL cb= MainGame_Server.class.getResource("enemy0_down.wav");
	 AudioClip aau  = Applet.newAudioClip(cb);
	
	public static int flag1 = 0;
	public DataOutputStream output1 = null;
	public DataInputStream input1 = null;
	public DataOutputStream output2 = null;
	public DataInputStream input2 = null;
	public DataOutputStream output3 = null;
	public DataInputStream input3 = null;
	int last = 0;
	static JFrame frame;
	static boolean U = false;
	static boolean D = false;
	static boolean L = false;
	static boolean R = false;
	static boolean S = false;
	static boolean UU = false;
	static boolean DD = false;
	static boolean LL = false;
	static boolean RR = false;
	static boolean SS = false;
	public static final int WIDTH = 480; // 面板宽
	public static final int HEIGHT = 800; // 面板高
	/** 游戏的当前状态: START RUNNING PAUSE GAME_OVER */
	private int state;
	private static final int START = 0;
	private static final int RUNNING = 1;
	private static final int PAUSE = 2;
	private static final int WIN = 3;
	private static final int LOSE = 4;

	private int score1 = 0; // 得分
	private int score2 = 0; // 得分
	public static BufferedImage background;
	public static BufferedImage start;
	public static BufferedImage enemy0;
	public static BufferedImage enemy0_down1;
	public static BufferedImage enemy0_down2;
	public static BufferedImage enemy0_down3;
	public static BufferedImage enemy0_down4;
	public static BufferedImage enemy1;
	public static BufferedImage enemy1_down1;
	public static BufferedImage enemy1_down2;
	public static BufferedImage enemy1_down3;
	public static BufferedImage enemy1_down4;
	public static BufferedImage enemy2;
	public static BufferedImage enemy2_down0;
	public static BufferedImage enemy2_down1;
	public static BufferedImage enemy2_down2;
	public static BufferedImage enemy2_down3;
	public static BufferedImage enemy2_down4;
	public static BufferedImage enemy2_hit;
	public static BufferedImage bee;
	public static BufferedImage bullet;
	public static BufferedImage hero0;
	public static BufferedImage hero1;
	public static BufferedImage hero_down1;
	public static BufferedImage hero_down2;
	public static BufferedImage hero_down3;
	public static BufferedImage hero_down4;
	public static BufferedImage pause;
	public static BufferedImage gameover;
	public static BufferedImage bullet_enemy;
	private Plane[] airplane = new Plane[25]; // 敌机数组
	private Bullet_Hero[] bullet_p1 =
	{}; // p1子弹
	private Bullet_Hero[] bullet_p2 =
	{}; // p2子弹
	public static Hero p1 = new Hero(300, 620, 1); // 英雄机
	public static Hero p2 = new Hero(60, 620, 2); // 英雄机
	long startTime = 0;
	long endTime = 0;
	static
	{ // 静态代码块，初始化图片资源
		try
		{
			background = ImageIO.read(MainGame_Server.class.getResource("background.png"));
			start = ImageIO.read(MainGame_Server.class.getResource("name.png"));
			enemy0 = ImageIO.read(MainGame_Server.class.getResource("enemy0.png"));
			enemy0_down1 = ImageIO.read(MainGame_Server.class.getResource("enemy0_down1.png"));
			enemy0_down2 = ImageIO.read(MainGame_Server.class.getResource("enemy0_down2.png"));
			enemy0_down3 = ImageIO.read(MainGame_Server.class.getResource("enemy0_down3.png"));
			enemy0_down4 = ImageIO.read(MainGame_Server.class.getResource("enemy0_down4.png"));
			enemy1 = ImageIO.read(MainGame_Server.class.getResource("enemy1.png"));
			enemy1_down1 = ImageIO.read(MainGame_Server.class.getResource("enemy1_down1.png"));
			enemy1_down2 = ImageIO.read(MainGame_Server.class.getResource("enemy1_down2.png"));
			enemy1_down3 = ImageIO.read(MainGame_Server.class.getResource("enemy1_down3.png"));
			enemy1_down4 = ImageIO.read(MainGame_Server.class.getResource("enemy1_down4.png"));
			enemy2 = ImageIO.read(MainGame_Server.class.getResource("enemy2.png"));
			enemy2_down0 = ImageIO.read(MainGame_Server.class.getResource("enemy2_down2.png"));
			enemy2_down1 = ImageIO.read(MainGame_Server.class.getResource("enemy2_down3.png"));
			enemy2_down2 = ImageIO.read(MainGame_Server.class.getResource("enemy2_down4.png"));
			enemy2_down3 = ImageIO.read(MainGame_Server.class.getResource("enemy2_down5.png"));
			enemy2_down4 = ImageIO.read(MainGame_Server.class.getResource("enemy2_down6.png"));
			enemy2_hit = ImageIO.read(MainGame_Server.class.getResource("enemy2_hit.png"));
			hero_down1 = ImageIO.read(MainGame_Server.class.getResource("hero_blowup_n1.png"));
			hero_down2 = ImageIO.read(MainGame_Server.class.getResource("hero_blowup_n2.png"));
			hero_down3 = ImageIO.read(MainGame_Server.class.getResource("hero_blowup_n3.png"));
			hero_down4 = ImageIO.read(MainGame_Server.class.getResource("hero_blowup_n4.png"));
			bee = ImageIO.read(MainGame_Server.class.getResource("bee.png"));
			bullet = ImageIO.read(MainGame_Server.class.getResource("bullet1.png"));
			hero0 = ImageIO.read(MainGame_Server.class.getResource("hero0.png"));
			hero1 = ImageIO.read(MainGame_Server.class.getResource("hero1.png"));
			pause = ImageIO.read(MainGame_Server.class.getResource("pause.png"));
			gameover = ImageIO.read(MainGame_Server.class.getResource("gameover.png"));
			bullet_enemy = ImageIO.read(MainGame_Server.class.getResource("bullet_enemy.png"));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	void playMusic1()
	{// 背景音乐播放

		URL cb;
		cb = MainGame_Server.class.getResource("BGM1.wav");
		AudioClip aau;
		aau = Applet.newAudioClip(cb);
		aau.play();
		aau.loop();
	}


//	void boobMusic()
//	{// 背景音乐播放
//
//		URL cb;
//		cb = MainGame_Server.class.getResource("enemy0_down.wav");
//		AudioClip aau;
//		aau = Applet.newAudioClip(cb);
//		aau.play();
//		// aau.loop();//循环播放
//		System.out.println("可以播放");
//	}

	/** 画 */
	@Override
	public void paint(Graphics g)
	{
		g.drawImage(background, 0, 0, null); // 画背景图
		if (state == RUNNING)
		{
			paintBullets1(g); // 画子弹
			paintBullets2(g); // 画子弹
			paintBullets_enemy(g); // 画敌人子弹
			paintPlane(g); // 画飞机
			paintScore1(g); // 画分数
			paintScore2(g); // 画分数
		}
		paintHero(g); // 画英雄机

		paintState(g); // 画游戏状态
	}

	/** 画英雄机 */
	public void paintHero(Graphics g)
	{
		g.drawImage(p1.getImage(), p1.getX(), p1.getY(), null);
		g.drawImage(p2.getImage(), p2.getX(), p2.getY(), null);
	}

	/** 画敌人子弹 */
	public void paintBullets_enemy(Graphics g)
	{
		for (int i = 0; i < airplane.length; i++)
		{
			Bullet_Enemy[] b = airplane[i].e_bullets;
			for (int j = 0; j < b.length; j++)
			{
				g.drawImage(b[j].getImage(), b[j].getX() - b[j].getWidth() / 2, b[j].getY(), null);
			}
		}

	}

	/** 画子弹 */
	public void paintBullets1(Graphics g)
	{
		for (int i = 0; i < bullet_p1.length; i++)
		{
			Bullet_Hero b = bullet_p1[i];
			g.drawImage(b.getImage(), b.getX() - b.getWidth() / 2, b.getY(), null);
		}
	}

	/** 画子弹 */
	public void paintBullets2(Graphics g)
	{
		for (int i = 0; i < bullet_p2.length; i++)
		{
			Bullet_Hero b = bullet_p2[i];
			g.drawImage(b.getImage(), b.getX() - b.getWidth() / 2, b.getY(), null);
		}
	}

	/** 画飞行物 */
	public void paintPlane(Graphics g)
	{
		for (int i = 0; i < 25; i++)
		{
			FlyingObject f = airplane[i];
			g.drawImage(f.getImage(), f.getX(), f.getY(), null);
		}
	}

	/** 画分数 */
	public void paintScore1(Graphics g)
	{
		int x = 345; // x坐标
		int y = 25; // y坐标
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 20); // 字体
		g.setColor(Color.BLACK);
		g.setFont(font); // 设置字体
		g.drawString("S SCORE:" + score1, x, y); // 画分数
		y = y + 20; // y坐标增20
		g.drawString("S LIFE:" + p1.getLife(), x, y); // 画命
	}

	public void paintScore2(Graphics g)
	{
		int x = 7; // x坐标
		int y = 25; // y坐标
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 20); // 字体
		g.setColor(Color.BLACK);
		g.setFont(font); // 设置字体
		g.drawString("C SCORE:" + score2, x, y); // 画分数
		y = y + 20; // y坐标增20
		g.drawString("C LIFE:" + p2.getLife(), x, y); // 画命
	}

	int startIndex = 0;
	/** 画游戏状态 */
	public void paintState(Graphics g)
	{
		switch (state)
		{
		case START: // 启动状态
			
				g.drawImage(start, 23, 300, null);
				Font font5 = new Font(Font.SANS_SERIF, Font.BOLD, 35); // 字体
				if (startIndex == 0)
				{
					g.setColor(Color.BLACK);
					g.setFont(font5); // 设置字体
					g.drawString("Waiting", 155, 500);
					startIndex = 1;
				} else if (startIndex == 1)
				{
					g.setColor(Color.BLACK);
					g.setFont(font5); // 设置字体
					g.drawString("Waiting.", 155, 500);
					startIndex = 2;
				} else if (startIndex == 2)
				{
					g.setColor(Color.BLACK);
					g.setFont(font5); // 设置字体
					g.drawString("Waiting..", 155, 500);
					startIndex = 3;
				} else if (startIndex == 3)
				{
					g.setColor(Color.BLACK);
					g.setFont(font5); // 设置字体
					g.drawString("Waiting...", 155, 500);
					startIndex = 0;
				}
			break;
		case PAUSE: // 暂停状态
			g.drawImage(pause, 0, 0, null);
			break;
		case WIN: // 游戏终止状态
			g.drawImage(gameover, 0, 0, null);
			Font font = new Font(Font.SANS_SERIF, Font.BOLD, 40); // 字体
			g.setColor(Color.BLACK);
			g.setFont(font); // 设置字体
			g.drawString("You WIN!", 150, 400); // 画分数
			String str[] = new String[50];
			str[0] = "33  34.3s";
			str[1] = "33  35.1s";
			str[2] = "32  34.8s";
			str[3] = "31  32.3s";
			str[4] = "31  33.3s";
			Font font4 = new Font(Font.SANS_SERIF, Font.BOLD, 18); // 字体
			g.setColor(Color.BLACK);
			g.setFont(font4); // 设置字体
			g.drawString("排行榜", 200, 440); // 画分数
			for (int i = 0; i < 5; i++)
			{
				Font font3 = new Font(Font.SANS_SERIF, Font.BOLD, 18); // 字体
				g.setColor(Color.BLACK);
				g.setFont(font3); // 设置字体
				g.drawString(i + 1 + ". " + str[i], 180, 470 + 20 * i); // 画分数
			}
			break;

		case LOSE: // 游戏终止状态
			g.drawImage(gameover, 0, 0, null);
			Font font1 = new Font(Font.SANS_SERIF, Font.BOLD, 40); // 字体
			g.setColor(Color.BLACK);
			g.setFont(font1); // 设置字体
			g.drawString("You Lose!", 150, 400); // 画分数
			String str1[] = new String[50];
			str1[0] = "33  34.3s";
			str1[1] = "33  35.1s";
			str1[2] = "32  34.8s";
			str1[3] = "31  32.3s";
			str1[4] = "31  33.3s";
			Font font6 = new Font(Font.SANS_SERIF, Font.BOLD, 18); // 字体
			g.setColor(Color.BLACK);
			g.setFont(font6); // 设置字体
			g.drawString("排行榜", 200, 440); // 画分数
			for (int i = 0; i < 5; i++)
			{
				Font font3 = new Font(Font.SANS_SERIF, Font.BOLD, 18); // 字体
				g.setColor(Color.BLACK);
				g.setFont(font3); // 设置字体
				g.drawString(i + 1 + ". " + str1[i], 180, 470 + 20 * i); // 画分数
			}
			break;

		}
	}

	public static void main(String[] args) throws InterruptedException
	{
		
		frame = new JFrame("Plane Battle_Server");
		frame.setResizable(false);
		MainGame_Server game = new MainGame_Server(); // 面板对象
		frame.add(game); // 将面板添加到JFrame中
		frame.setSize(WIDTH, HEIGHT); // 设置大小
		frame.setAlwaysOnTop(true); // 设置其总在最上
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 默认关闭操作
		ImageIcon imageIcon = new ImageIcon("icon.png");
		frame.setIconImage(imageIcon.getImage());
		frame.setLocationRelativeTo(null); // 设置窗体初始位置
		frame.setVisible(true);
		p1shoot t1 = game.new p1shoot();
		t1.start();
		p1move t2 = game.new p1move();
		t2.start();
		Client3 t3 = game.new Client3();
		t3.start();
		game.action(); // 启动执行
	}

	/** 启动执行代码 */

	public void action()
	{
		playMusic1();
		for (int i = 0; i < 25; i++)
		{
			airplane[i] = nextOne(Plane.Map[i]);
		}
		Client1 thread = new Client1();
		thread.start();
		Client2 t = new Client2();
		t.start();

		MouseAdapter l = new MouseAdapter()
		{

			@Override
			public void mouseEntered(MouseEvent e)
			{ // 鼠标进入
				if (state == PAUSE)
				{ // 暂停状态下运行
					try
					{
						output1.writeUTF("running");
						output1.flush();
					} catch (IOException e1)
					{
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
					state = RUNNING;
				}
			}

			public void mouseExited(MouseEvent e)
			{ // 鼠标退出
				if (state == RUNNING)
				{ // 游戏未结束，则设置其为暂停
					try
					{
						output1.writeUTF("pause");
						output1.flush();
					} catch (IOException e1)
					{
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
					L = false;
					R = false;
					D = false;
					U = false;
					S = false;
					LL = false;
					RR = false;
					DD = false;
					UU = false;
					SS = false;
					state = PAUSE;
				}
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{ // 鼠标点击
				switch (state)
				{
				case START:
					// state = RUNNING; // 启动状态下运行
					break;
				case LOSE: // 游戏结束，清理现场
					break;
				}
			}
		};
		this.addMouseListener(l); // 处理鼠标点击操作
		this.addMouseMotionListener(l); // 处理鼠标滑动操作

		ActionListener taskPerformer = new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				if (state == RUNNING)
				{ // 运行状态
					enterAction(); // 飞行物入场
					PlaneMove(); // 敌机飞行
					shootAction1(); // P1射击
					shootAction2(); // P2射击
					e_shootAction(); // 敌军射击
					BulletsMove(); // 子弹走一步
					p1bangAction();
					p2bangAction();
					checkGameOverAction(); // 检查游戏结束
					bomb();
					herobomb();
					if (!p1.die)
					{
						p1.move();
					}
					if (!p2.die)
					{
						p2.move();
					}
					SendP1Pos();
					SendP2Pos();
					sendLife1();
					sendLife2();
					sendScore1();
					sendScore2();
					p1.run();
					p2.run();
				}
				repaint(); // 重绘，调用paint()方法
			}
		};
		Timer timer = new Timer(20, taskPerformer);
		timer.start();

	}

	public void herobomb()
	{
		if (state == RUNNING)
		{
			if (p1.die)
			{
				switch (p1.dieIndex)
				{
				case 0:
					aau.play();
					p1.setImage(hero_down1);
					break;
				case 1:
					p1.setImage(hero_down2);
					break;
				case 2:
					p1.setImage(hero_down3);
					break;
				case 3:
					p1.setImage(hero_down4);
					break;
				case 4:
					p1.setImage(null);
					break;
				}
				p1.dieIndex++;
				repaint();
			}
			if (p2.die)
			{
				switch (p2.dieIndex)
				{
				case 0:
					aau.play();
					p2.setImage(hero_down1);
					break;
				case 1:
					p2.setImage(hero_down2);
					break;
				case 2:
					p2.setImage(hero_down3);
					break;
				case 3:
					p2.setImage(hero_down4);
					break;
				default:
					p2.setImage(null);
					break;
				}
				p2.dieIndex++;
				repaint();

			}
		}
	}

	int bombindex = 0;

	public void bomb()
	{
		if (state == RUNNING)
		{
			for (int i = 0; i < last; i++)
			{
				if (airplane[i].die && airplane[i].dieIndex <= 5)
				{
					if (airplane[i].kind == 0)
					{
						switch (airplane[i].dieIndex)
						{
						case 1:
							airplane[i].setImage(enemy1_down1);
							break;
						case 2:
							airplane[i].setImage(enemy1_down2);
							break;
						case 3:
							airplane[i].setImage(enemy1_down3);
							break;
						case 4:
							airplane[i].setImage(enemy1_down4);
							break;
						case 5:
							airplane[i].setImage(null);
							break;
						}
						airplane[i].dieIndex++;
						repaint();
					} else if (airplane[i].kind == 1)
					{
						switch (airplane[i].dieIndex)
						{
						case 1:
							airplane[i].setImage(enemy0_down1);
							break;
						case 2:
							airplane[i].setImage(enemy0_down2);
							break;
						case 3:
							airplane[i].setImage(enemy0_down3);
							break;
						case 4:
							airplane[i].setImage(enemy0_down4);
							break;
						case 5:
							airplane[i].setImage(null);
							break;
						}
						airplane[i].dieIndex++;
						repaint();
					} else if (airplane[i].kind == 2)
					{
						switch (airplane[i].dieIndex)
						{
						case 1:
							airplane[i].setImage(enemy2_down1);
							break;
						case 2:
							airplane[i].setImage(enemy2_down2);
							break;
						case 3:
							airplane[i].setImage(enemy2_down3);
							break;
						case 4:
							airplane[i].setImage(enemy2_down4);
							break;
						case 5:
							airplane[i].setImage(null);
							break;
						}
						airplane[i].dieIndex++;
						repaint();
					}
				}
			}
		}
		bombindex++;
	}

	public void sendScore1()
	{
		try
		{
			output1.writeUTF("C1 " + String.valueOf(score1));
			output1.flush();
		} catch (IOException e1)
		{
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	}

	public void sendScore2()
	{
		try
		{
			output1.writeUTF("C2 " + String.valueOf(score2));
			output1.flush();
		} catch (IOException e1)
		{
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	}

	public void SendP1Pos()
	{
		try
		{
			output1.writeUTF("P1 " + String.valueOf(p1.x) + " " + String.valueOf(p1.y));
			output1.flush();
		} catch (IOException e1)
		{
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	}

	public void SendP2Pos()
	{
		try
		{
			output1.writeUTF("P2 " + String.valueOf(p2.x) + " " + String.valueOf(p2.y));
			output1.flush();
		} catch (IOException e1)
		{
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	}

	public void sendLife1()
	{
		try
		{
			output2.writeUTF("L1 " + String.valueOf(p1.getLife()));
			output2.flush();
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}

	public void sendLife2()
	{
		try
		{
			output2.writeUTF("L2 " + String.valueOf(p2.getLife()));
			output2.flush();
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}

	int flyEnteredIndex = 0; // 飞行物入场计数

	/** 飞行物入场 */
	public void enterAction()
	{
		flyEnteredIndex++;
		if (flyEnteredIndex % 120 == 0 && last <= 24)
		{
			try
			{
				output1.writeUTF("EnterAction");
				output1.flush();
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
			airplane[last].enabled = true;
			last++;
		}
	}

	public void PlaneMove()
	{
		for (int i = 0; i < last; i++)
		{ // 飞行物走一步
			Plane f = airplane[i];
			f.move();
			try
			{
				output2.writeUTF("F " + i + " " + f.getX() + " " + f.getY());
				output2.flush();
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
	}

	/** 走一步 */
	public void BulletsMove()
	{
		try
		{
			output1.writeUTF("bulletsMove");
			output1.flush();
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
		for (int i = 0; i < bullet_p1.length; i++)
		{ // 子弹走一步
			Bullet_Hero b = bullet_p1[i];
			b.move();
		}
		for (int i = 0; i < bullet_p2.length; i++)
		{ // 子弹走一步
			Bullet_Hero b = bullet_p2[i];
			b.move();
		}
		for (int i = 0; i < airplane.length; i++)
		{ // 子弹走一步
			for (int j = 0; j < airplane[i].e_bullets.length; j++)
			{
				Bullet_Enemy b = airplane[i].e_bullets[j];
				b.move();
			}
		}

	}

	int shootIndex1 = 0; // 射击计数

	/** 射击 */
	public void shootAction1()
	{
		shootIndex1++;
		if (shootIndex1 % 6 == 0 && S == true)
		{
			try
			{
				output1.writeUTF("shoot1");
				output1.flush();
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
			Bullet_Hero[] bs = p1.shoot(); // 英雄打出子弹
			bullet_p1 = Arrays.copyOf(bullet_p1, bullet_p1.length + bs.length); // 扩容
			System.arraycopy(bs, 0, bullet_p1, bullet_p1.length - bs.length, bs.length); // 追加数组
		}
	}

	int shootIndex2 = 0; // 射击计数

	public void shootAction2()
	{
		shootIndex2++;
		if (shootIndex2 % 6 == 0 && SS == true)
		{
			try
			{
				output1.writeUTF("shoot2");
				output1.flush();
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
			Bullet_Hero[] bs = p2.shoot(); // 英雄打出子弹
			bullet_p2 = Arrays.copyOf(bullet_p2, bullet_p2.length + bs.length); // 扩容
			System.arraycopy(bs, 0, bullet_p2, bullet_p2.length - bs.length, bs.length); // 追加数组
		}
	}

	/** 敌军射击 */
	int e_shootIndex = 0; // 射击计数

	public void e_shootAction()
	{
		e_shootIndex++;
		if (e_shootIndex % 80 == 0)
		{
			try
			{
				output1.writeUTF("e_shoot");
				output1.flush();
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
			for (int i = 0; i < last; i++)
			{
				if (!airplane[i].die)
				{
					Bullet_Enemy bs[] =
					{ airplane[i].shoot() }; // 英雄打出子弹
					airplane[i].e_bullets = Arrays.copyOf(airplane[i].e_bullets, airplane[i].e_bullets.length + 1); // 扩容
					System.arraycopy(bs, 0, airplane[i].e_bullets, airplane[i].e_bullets.length - 1, 1); // 追加数组
				}
			}
		}
	}

	/** 子弹与飞行物碰撞检测 */
	public void p1bangAction()
	{
		try
		{
			output1.writeUTF("p1bang");
			output1.flush();
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
		for (int i = 0; i < bullet_p1.length; i++)
		{ // 遍历所有子弹

			if (!bullet_p1[i].used)
				bang1(bullet_p1[i], i); // 子弹和飞行物之间的碰撞检查
		}
	}

	public void p2bangAction()
	{
		try
		{
			output1.writeUTF("p2bang");
			output1.flush();
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
		for (int i = 0; i < bullet_p2.length; i++)
		{
			if (!bullet_p2[i].used)
				bang2(bullet_p2[i], i); // 子弹和飞行物之间的碰撞检查
		}
	}

	/**
	 * 检查游戏结束
	 * 
	 * @throws IOException
	 */
	public void checkGameOverAction()
	{

		boolean d1 = p1isDie();
		boolean d2 = p2isDie();
		if (d1 && d2)
		{
			try
			{
				output2.writeUTF("GameLose");
				output2.flush();
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
			if (p1.dieIndex >= 5 && p2.dieIndex >= 5)
			{
				
				for (int i = 0; i < 25; i++)
				{
					airplane[i] = nextOne(Plane.Map[i]);
				}
				bullet_p1 = new Bullet_Hero[0]; // 清空子弹
				bullet_p2 = new Bullet_Hero[0]; // 清空子弹
				score1 = 0; // 清空成绩
				score2 = 0;
				state = LOSE; // 改变状态
			}
			repaint();
		} else if (last >= 18)
		{
			try
			{
				output2.writeUTF("GameWin");
				output2.flush();
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
			endTime = System.currentTimeMillis();
			long time = endTime - startTime;
			System.out.print(time);
			for (int i = 0; i < 25; i++)
			{
				airplane[i] = nextOne(Plane.Map[i]);
			}
			bullet_p1 = new Bullet_Hero[0]; // 清空子弹
			bullet_p2 = new Bullet_Hero[0]; // 清空子弹
			score1 = 0; // 清空成绩
			score2 = 0;
			state = WIN; // 改变状态
		}

	}

	/** 检查游戏是否结束 */
	public boolean p1isDie()
	{
		if (p1.getLife() > 0)
		{
			for (int i = 0; i < last; i++)
			{
				for (int j = 0; j < airplane[i].e_bullets.length; j++)
				{
					Bullet_Enemy b = airplane[i].e_bullets[j];
					if (p1.hit(b) && b.used == 0)
					{
						p1.subtractLife(); // 减命
						// p1.setDoubleFire(0); // 双倍火力解除
						b.used = 1;
						try
						{
							output2.writeUTF("ebhit " + i + " " + j);
							output2.flush();
						} catch (IOException e1)
						{
							e1.printStackTrace();
						}
						b.setImage(null);
					}

				}
				int index = -1;
				FlyingObject obj = airplane[i];
				if (!airplane[i].die && p1.hit(obj))
				{ // 检查英雄机与飞行物是否碰撞
					p1.subtractLife(); // 减命
					// p1.setDoubleFire(0); // 双倍火力解除
					index = i; // 记录碰上的飞行物索引
				}

				if (index != -1)
				{
					try
					{
						output2.writeUTF("pdie " + index);
						output2.flush();
					} catch (IOException e1)
					{
						e1.printStackTrace();
					}
					airplane[index].die = true;
					airplane[index].dieIndex = 1;
				}
			}
		}
		if (p1.getLife() <= 0)
		{
			try
			{
				output2.writeUTF("p1die");
				output2.flush();
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}

			p1.die = true;
			L = false;
			R = false;
			D = false;
			U = false;
			S = false;
			return true;
		} else
			return false;

	}

	public boolean p2isDie()
	{
		if (p2.getLife() > 0)
		{
			for (int i = 0; i < last; i++)
			{
				for (int j = 0; j < airplane[i].e_bullets.length; j++)
				{
					Bullet_Enemy b = airplane[i].e_bullets[j];
					if (p2.hit(b) && b.used == 0)
					{
						p2.subtractLife(); // 减命
						// p2.setDoubleFire(0); // 双倍火力解除
						b.used = 1;
						try
						{
							output2.writeUTF("ebhit " + i + " " + j);
							output2.flush();
						} catch (IOException e1)
						{
							e1.printStackTrace();
						}
						b.setImage(null);
					}

				}
				int index = -1;
				FlyingObject obj = airplane[i];
				if (!airplane[i].die && p2.hit(obj))
				{ // 检查英雄机与飞行物是否碰撞
					p2.subtractLife(); // 减命
					p2.setDoubleFire(0); // 双倍火力解除
					index = i; // 记录碰上的飞行物索引
				}

				if (index != -1)
				{
					try
					{
						output2.writeUTF("pdie " + index);
						output2.flush();
					} catch (IOException e1)
					{
						e1.printStackTrace();
					}
					airplane[index].die = true;
					airplane[index].dieIndex = 1;
				}
			}
		}
		if (p2.getLife() <= 0)
		{
			try
			{
				output2.writeUTF("p2die");
				output2.flush();
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}

			p2.die = true;
			LL = false;
			RR = false;
			DD = false;
			UU = false;
			SS = false;
			return true;
		} else
			return false;

	}

	/** 子弹和飞行物之间的碰撞检查 */
	public void bang1(Bullet_Hero bullet_Hero, int ii)
	{
		int index = -1; // 击中的飞行物索引
		// bullet_Hero.used = 0;
		for (int i = 0; i < last; i++)
		{
			if (!airplane[i].die && airplane[i].shootBy(bullet_Hero) && !bullet_Hero.used)
			{ // 判断是否击中

				index = i; // 记录被击中的飞行物的索引
				bullet_Hero.used = true;
				try
				{
					output2.writeUTF("b1hit " + ii);
					output2.flush();
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
				aau.play();
				bullet_Hero.setImage(null);
				break;
			}
		}
		if (index != -1)
		{ // 有击中的飞行物
			airplane[index].life--;
			if (airplane[index].kind == 2 && airplane[index].life > 0)
			{
				try
				{
					output2.writeUTF("hit2 " + index);
					output2.flush();
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
				airplane[index].setImage(enemy2_down0);
			}

			if (airplane[index].life <= 0)
			{
				try
				{
					output2.writeUTF("pdie " + index);
					output2.flush();
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
				// airplane[index].setImage(null);
				airplane[index].die = true;
				airplane[index].dieIndex = 1;
				score1 += (airplane[index]).getScore(); // 加分
			}

		}
	}

	public void bang2(Bullet_Hero bullet_Hero, int ii)
	{
		int index = -1; // 击中的飞行物索引
		// bullet_Hero.used = 0;
		for (int i = 0; i < last; i++)
		{
			if (!airplane[i].die && airplane[i].shootBy(bullet_Hero) && !bullet_Hero.used)
			{ // 判断是否击中

				index = i; // 记录被击中的飞行物的索引
				bullet_Hero.used = true;
				try
				{
					output2.writeUTF("b2hit " + ii);
					output2.flush();
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
				aau.play();
				bullet_Hero.setImage(null);
				break;
			}
		}
		if (index != -1)
		{ // 有击中的飞行物
			airplane[index].life--;
			if (airplane[index].kind == 2 && airplane[index].life > 0)
			{
				try
				{
					output2.writeUTF("hit2 " + index);
					output2.flush();
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
				airplane[index].setImage(enemy2_down0);
			}

			if (airplane[index].life <= 0)
			{
				try
				{
					output2.writeUTF("pdie " + index);
					output2.flush();
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
				// airplane[index].setImage(null);
				airplane[index].die = true;
				airplane[index].dieIndex = 1;
				score2 += (airplane[index]).getScore(); // 加分
			}

		}
	}

	public static Plane nextOne(int type)
	{

		if (type < 50)
		{
			return new Enemy0(type);
		} else if (type < 75)
		{
			return new Enemy1(type);
		} else
		{
			return new Enemy2(type);
		}
	}

	class p1shoot extends Thread
	{
		public void run()
		{

			KeyAdapter k = new KeyAdapter()
			{
				public void keyPressed(KeyEvent ke)
				{
					if (ke.getKeyCode() == KeyEvent.VK_SPACE && state == RUNNING)
					{
						S = true;
					}
				}

				public void keyReleased(KeyEvent e)
				{
					if (e.getKeyCode() == KeyEvent.VK_SPACE && state == RUNNING)
					{
						S = false;
					}
				}

			};
			frame.addKeyListener(k);

		}
	}

	class p1move extends Thread
	{
		public void run()
		{

			KeyAdapter k = new KeyAdapter()
			{
				@Override
				public void keyTyped(KeyEvent e)
				{
					// TODO 自动生成的方法存根
				}

				@Override
				public void keyPressed(KeyEvent e)
				{
					// TODO 自动生成的方法存根
					if (state == RUNNING)
					{
						switch (e.getKeyCode())
						{
						case KeyEvent.VK_LEFT:
							L = true;
							break;
						case KeyEvent.VK_RIGHT:
							R = true;
							break;
						case KeyEvent.VK_UP:
							U = true;
							break;
						case KeyEvent.VK_DOWN:
							D = true;
							break;
						}
					}

				}

				@Override
				public void keyReleased(KeyEvent e)
				{
					if (state == RUNNING)
					{
						switch (e.getKeyCode())
						{
						case KeyEvent.VK_LEFT:
							L = false;
							break;
						case KeyEvent.VK_RIGHT:
							R = false;
							break;
						case KeyEvent.VK_UP:
							U = false;
							break;
						case KeyEvent.VK_DOWN:
							D = false;
							break;
						}
					}
				}

			};
			frame.addKeyListener(k);
		}
	}

	class Client1 extends Thread
	{
		public void run()
		{
			// 是否成功启动服务端
			boolean isStart = false;
			// 服务端socket
			ServerSocket ss1 = null;
			// 客户端socket
			Socket p_socket = null;
			// 服务端读取客户端数据输入流
			try
			{

				ss1 = new ServerSocket(8000);

			} catch (Exception e1)
			{
				e1.printStackTrace();
			}

			try
			{
				isStart = true;
				if (isStart)
				{
					boolean isConnect = false;
					// 启动监听
					p_socket = ss1.accept();
					// JOptionPane.showMessageDialog(null, "Successfully Connect!");
					output1 = new DataOutputStream(p_socket.getOutputStream());
					System.out.println("one client connect");
					isConnect = true;
					while (isConnect)
					{
						// 获取客户端输入流
						input1 = new DataInputStream(p_socket.getInputStream());
						// 读取客户端传递的数据
						String msg = input1.readUTF();
						if (msg.equals("restart"))
						{
							state = START;
							p1 = new Hero(300, 620, 1); // 重新创建英雄机
							p2 = new Hero(60, 620, 2); // 重新创建英雄机

						} else if (msg.equals("start"))
						{
							System.out.print(msg);
							state = RUNNING;
							// break;
						} else if (msg.equals("SSP"))
						{
							SS = true;
						} else if (msg.equals("SSR"))
						{
							SS = false;
						}
					}
				}
			} catch (EOFException e)
			{
				System.out.println("client closed!");
			} catch (Exception e)
			{
				e.printStackTrace();
			}

		}
	}

	class Client2 extends Thread
	{
		public void run()
		{
			// 是否成功启动服务端
			boolean isStart = false;
			// 服务端socket
			ServerSocket ss1 = null;
			// 客户端socket
			Socket p_socket = null;
			// 服务端读取客户端数据输入流
			try
			{

				ss1 = new ServerSocket(8001);

			} catch (Exception e1)
			{
				e1.printStackTrace();
			}

			try
			{
				isStart = true;
				if (isStart)
				{
					boolean isConnect = false;
					// 启动监听
					p_socket = ss1.accept();
					// JOptionPane.showMessageDialog(null, "Successfully Connect!");
					output2 = new DataOutputStream(p_socket.getOutputStream());
					System.out.println("one client connect");
					isConnect = true;
					while (isConnect)
					{
						// 获取客户端输入流
						input2 = new DataInputStream(p_socket.getInputStream());
						// 读取客户端传递的数据
						String msg = input2.readUTF();
						// System.out.print(msg);
						if (msg.substring(0, 1).equals("P"))
						{
							// System.out.print("SBLZM");
							String[] arr = msg.split("\\s+");

							p2.setX(Integer.valueOf(arr[1]));
							p2.setY(Integer.valueOf(arr[2]));
						} else if (msg.equals("restart"))
						{
							for (int i = 0; i < 25; i++)
							{
								airplane[i] = nextOne(Plane.Map[i]);
							}
							bullet_p1 = new Bullet_Hero[0]; // 清空子弹
							bullet_p2 = new Bullet_Hero[0]; // 清空子弹
							p1 = new Hero(60, 620, 1); // 重新创建英雄机
							p2 = new Hero(300, 620, 2); // 重新创建英雄机
							score1 = 0; // 清空成绩
							score2 = 0;
							last = 0;
							state = START; // 状态设置为启动
						} else if (msg.equals("start"))
						{
							System.out.print(msg);
							startTime = System.currentTimeMillis();
							state = RUNNING;

							// break;
						}

					}
				}
			} catch (EOFException e)
			{
				System.out.println("client closed!");
			} catch (Exception e)
			{
				e.printStackTrace();
			}

		}
	}

	class Client3 extends Thread
	{
		public void run()
		{
			// 是否成功启动服务端
			boolean isStart = false;
			// 服务端socket
			ServerSocket ss1 = null;
			// 客户端socket
			Socket p_socket = null;
			// 服务端读取客户端数据输入流
			try
			{

				ss1 = new ServerSocket(8002);

			} catch (Exception e1)
			{
				e1.printStackTrace();
			}

			try
			{
				isStart = true;
				if (isStart)
				{
					boolean isConnect = false;
					// 启动监听
					p_socket = ss1.accept();
					// JOptionPane.showMessageDialog(null, "Successfully Connect!");
					output3 = new DataOutputStream(p_socket.getOutputStream());
					System.out.println("one client connect");
					isConnect = true;
					while (isConnect)
					{
						// 获取客户端输入流
						input3 = new DataInputStream(p_socket.getInputStream());
						// 读取客户端传递的数据
						String msg = input3.readUTF();
						if (msg.equals("LLP"))
						{
							LL = true;
						} else if (msg.equals("RRP"))
						{
							RR = true;
						} else if (msg.equals("UUP"))
						{
							UU = true;
						} else if (msg.equals("DDP"))
						{
							DD = true;
						} else if (msg.equals("LLR"))
						{
							LL = false;
						} else if (msg.equals("RRR"))
						{
							RR = false;
						} else if (msg.equals("UUR"))
						{
							UU = false;
						} else if (msg.equals("DDR"))
						{
							DD = false;
						}
					}
				}
			} catch (EOFException e)
			{
				System.out.println("client closed!");
			} catch (Exception e)
			{
				e.printStackTrace();
			}

		}
	}

	public static void order(long time[], int score[])
	{
		int size = time.length;
		int i = 0;
		int j = 0;
		long temline;
		int temp;
		while (i < size - 1)
		{
			j = 0;
			while (j < size - 1 - i)
			{
				if (score[j] < score[j + 1])
				{
					temline = time[j];
					time[j] = time[j + 1];
					time[j + 1] = temline;
					temp = score[j];
					score[j] = score[j + 1];
					score[j + 1] = temp;
				}
				j++;

			}
			i++;
		}

	}

}