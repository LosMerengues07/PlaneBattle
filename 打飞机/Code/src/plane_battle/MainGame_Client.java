package plane_battle;

import java.awt.Font;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import javax.swing.Timer;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainGame_Client extends JPanel
{
	URL cb= MainGame_Server.class.getResource("enemy0_down.wav");
	 AudioClip aau  = Applet.newAudioClip(cb);
	public static int flag = 0;
	private Socket socket1 = null;
	private DataOutputStream output1 = null;
	private DataInputStream input1 = null;
	private Socket socket2 = null;
	private DataInputStream input2 = null;
	private Socket socket3 = null;
	private DataOutputStream output3 = null;
	static String IP = "";
	int port1 = 0;
	int port2 = 0;
	static JFrame frame;
	int last = 0;
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
	public static BufferedImage pause;
	public static BufferedImage gameover;
	public static BufferedImage bullet_enemy;
	public static BufferedImage hero_down1;
	public static BufferedImage hero_down2;
	public static BufferedImage hero_down3;
	public static BufferedImage hero_down4;

	private Plane[] airplane = new Plane[25]; // 敌机数组
	private Bullet_Hero[] bullet_p1 =
	{}; // p1子弹
	private Bullet_Hero[] bullet_p2 =
	{}; // p2子弹
	private Hero p1 = new Hero(60, 620, 1); // 英雄机
	private Hero p2 = new Hero(300, 620, 2); // 英雄机
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
			enemy2_hit = ImageIO.read(MainGame_Client.class.getResource("enemy2_hit.png"));
			hero_down1 = ImageIO.read(MainGame_Client.class.getResource("hero_blowup_n1.png"));
			hero_down2 = ImageIO.read(MainGame_Client.class.getResource("hero_blowup_n2.png"));
			hero_down3 = ImageIO.read(MainGame_Client.class.getResource("hero_blowup_n3.png"));
			hero_down4 = ImageIO.read(MainGame_Client.class.getResource("hero_blowup_n4.png"));
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
		int x = 7; // x坐标
		int y = 25; // y坐标
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 20); // 字体
		g.setColor(Color.BLACK);
		g.setFont(font); // 设置字体
		g.drawString("C SCORE:" + score2, x, y); // 画分数
		y = y + 20; // y坐标增20
		g.drawString("C LIFE:" + p2.getLife(), x, y); // 画命
	}

	public void paintScore2(Graphics g)
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

	/** 画游戏状态 */
	public void paintState(Graphics g)
	{
		switch (state)
		{
		case START: // 启动状态
			g.drawImage(start, 23, 300, null);
			Font font5 = new Font(Font.SANS_SERIF, Font.BOLD, 35); // 字体
			g.setColor(Color.BLACK);
			g.setFont(font5); // 设置字体
			g.drawString("Click to Run!", 140, 500);
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

	public static void main(String[] args)
	{
		String str = (String) JOptionPane.showInputDialog(null, "IP：\n", "输入IP", JOptionPane.PLAIN_MESSAGE, null, null,
				"127.0.0.1");
		IP = str;
		frame = new JFrame("Plane Battle_Client");
		frame.setResizable(false);
		MainGame_Client game = new MainGame_Client(); // 面板对象
		frame.add(game); // 将面板添加到JFrame中
		frame.setSize(WIDTH, HEIGHT); // 设置大小
		frame.setAlwaysOnTop(true); // 设置其总在最上
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 默认关闭操作
		ImageIcon imageIcon = new ImageIcon("icon.png");
		frame.setIconImage(imageIcon.getImage());
		frame.setLocationRelativeTo(null); // 设置窗体初始位置
		frame.setVisible(true);
		shoot t1 = game.new shoot();
		t1.start();
		P2Move t2 = game.new P2Move();
		t2.start();
		Socket3 t3 = game.new Socket3();
		t3.start();
		game.action(); // 启动执行
	}

	/** 启动执行代码 */
	void playMusic1()
	{// 背景音乐播放

		URL cb;
		cb = MainGame_Server.class.getResource("BGM1.wav");
		AudioClip aau;
		aau = Applet.newAudioClip(cb);
		aau.play();
	}

	void boobMusic()
	{// 背景音乐播放

		URL cb;
		cb = MainGame_Server.class.getResource("enemy0_down.wav");
		AudioClip aau;
		aau = Applet.newAudioClip(cb);

		aau.play();
		// aau.loop();//循环播放
		System.out.println("可以播放");
	}

	public void action()
	{
		 playMusic1();
		for (int i = 0; i < 25; i++)
		{
			airplane[i] = nextOne(Plane.Map[i]);
		}
		Socket1 Playthread = new Socket1();
		Playthread.start();
		Socket2 t = new Socket2();
		t.start();
		MouseAdapter l = new MouseAdapter()
		{

			// @Override
			// public void mouseEntered(MouseEvent e)
			// { // 鼠标进入
			// if (state == PAUSE)
			// { // 暂停状态下运行
			// try
			// {
			// output1.writeUTF("running");
			// output1.flush();
			// } catch (IOException e1)
			// {
			// // TODO 自动生成的 catch 块
			// e1.printStackTrace();
			// }
			// state = RUNNING;
			// }
			// }
			// public void mouseExited(MouseEvent e)
			// { // 鼠标退出
			// if (state == RUNNING)
			// { // 游戏未结束，则设置其为暂停
			// try
			// {
			// output1.writeUTF("pause");
			// output1.flush();
			// } catch (IOException e1)
			// {
			// // TODO 自动生成的 catch 块
			// e1.printStackTrace();
			// }
			// state = PAUSE;
			// }
			// }

			@Override
			public void mouseClicked(MouseEvent e)
			{ // 鼠标点击
				switch (state)
				{
				case START:
					try
					{
						output1.writeUTF("start");
						output1.flush();
					} catch (IOException e1)
					{
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
					state = RUNNING; // 启动状态下运行
					break;
				case WIN: // 游戏结束，清理现场

					break;
				}
			}
		};
		// frame.addKeyListener(k);
		this.addMouseListener(l); // 处理鼠标点击操作
		this.addMouseMotionListener(l); // 处理鼠标滑动操作

		ActionListener taskPerformer = new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				if (state == RUNNING)
				{ // 运行状态
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
				}
				repaint(); // 重绘，调用paint()方法
			}
		};
		Timer timer = new Timer(20, taskPerformer);
		timer.start();
	}

	public void herobomb()
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
							aau.play();
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
							aau.play();
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
							aau.play();
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
	}

	public void enterAction()
	{

		airplane[last].enabled = true;
		last++;
		// System.out.print("创建了第" + (airplane.length - 1) + "个飞机\n");
	}

	public void BulletsMove()
	{
		for (int i = 0; i < bullet_p1.length; i++)
		{ // 子弹走一步
			Bullet_Hero b = bullet_p1[i];
			b.move();
			repaint();
		}
		for (int i = 0; i < bullet_p2.length; i++)
		{ // 子弹走一步
			Bullet_Hero b = bullet_p2[i];
			b.move();
			repaint();
		}
		for (int i = 0; i < airplane.length; i++)
		{ // 子弹走一步
			for (int j = 0; j < airplane[i].e_bullets.length; j++)
			{
				Bullet_Enemy b = airplane[i].e_bullets[j];
				b.move();
				repaint();
			}
		}
	}

	public void shootAction1()
	{
		Bullet_Hero[] bs = p1.shoot(); // 英雄打出子弹
		bullet_p1 = Arrays.copyOf(bullet_p1, bullet_p1.length + bs.length); // 扩容
		System.arraycopy(bs, 0, bullet_p1, bullet_p1.length - bs.length, bs.length); // 追加数组
	}

	public void shootAction2()
	{
		Bullet_Hero[] bs = p2.shoot(); // 英雄打出子弹
		bullet_p2 = Arrays.copyOf(bullet_p2, bullet_p2.length + bs.length); // 扩容
		System.arraycopy(bs, 0, bullet_p2, bullet_p2.length - bs.length, bs.length); // 追加数组
	}

	/** 敌军射击 */
	int e_shootIndex = 0; // 射击计数

	public void e_shootAction()
	{
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

	/**
	 * 随机生成飞行物
	 * 
	 * @return 飞行物对象
	 */
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

	class shoot extends Thread
	{
		public void run()
		{
			KeyAdapter k = new KeyAdapter()
			{
				public void keyPressed(KeyEvent ke)
				{
					if (ke.getKeyCode() == KeyEvent.VK_SPACE && state == RUNNING)
					{
						try
						{
							output1.writeUTF("SSP");
							output1.flush();
						} catch (IOException e1)
						{
							e1.printStackTrace();
						}
					}
				}

				public void keyReleased(KeyEvent e)
				{
					if (e.getKeyCode() == KeyEvent.VK_SPACE && state == RUNNING)
					{

						try
						{
							output1.writeUTF("SSR");
							output1.flush();
						} catch (IOException e1)
						{
							e1.printStackTrace();
						}
					}
				}

			};
			frame.addKeyListener(k);
		}
	}

	class P2Move extends Thread
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
							try
							{
								output3.writeUTF("LLP");
								output3.flush();
							} catch (IOException e1)
							{
								e1.printStackTrace();
							}
							break;
						case KeyEvent.VK_RIGHT:
							try
							{
								output3.writeUTF("RRP");
								output3.flush();
							} catch (IOException e1)
							{
								e1.printStackTrace();
							}
							break;
						case KeyEvent.VK_UP:
							try
							{
								output3.writeUTF("UUP");
								output3.flush();
							} catch (IOException e1)
							{
								e1.printStackTrace();
							}
							break;
						case KeyEvent.VK_DOWN:
							try
							{
								output3.writeUTF("DDP");
								output3.flush();
							} catch (IOException e1)
							{
								e1.printStackTrace();
							}
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
							try
							{
								output3.writeUTF("LLR");
								output3.flush();
							} catch (IOException e1)
							{
								e1.printStackTrace();
							}
							break;
						case KeyEvent.VK_RIGHT:
							try
							{
								output3.writeUTF("RRR");
								output3.flush();
							} catch (IOException e1)
							{
								e1.printStackTrace();
							}

							break;
						case KeyEvent.VK_UP:
							try
							{
								output3.writeUTF("UUR");
								output3.flush();
							} catch (IOException e1)
							{
								e1.printStackTrace();
							}

							break;
						case KeyEvent.VK_DOWN:
							try
							{
								output3.writeUTF("DDR");
								output3.flush();
							} catch (IOException e1)
							{
								e1.printStackTrace();
							}

							break;
						}
					}
				}

			};
			frame.addKeyListener(k);
		}
	}

	class Socket1 extends Thread
	{
		public void run()
		{
			try
			{
				// 新建服务端连接
				socket1 = new Socket(IP, 8000);
				// 获取客户端输出流
				System.out.println("S1连上服务器");
				// JOptionPane.showMessageDialog(null, "Connect!");
				output1 = new DataOutputStream(socket1.getOutputStream());

			} catch (UnknownHostException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			try
			{

				while (true)
				{
					input1 = new DataInputStream(socket1.getInputStream());
					String msg = input1.readUTF();
					if (msg.substring(0, 2).equals("P1"))
					{
						String[] arr = msg.split("\\s+");
						p1.setX(Integer.valueOf(arr[1]));
						p1.setY(Integer.valueOf(arr[2]));
					} else if (msg.substring(0, 2).equals("P2"))
					{
						String[] arr = msg.split("\\s+");

						p2.setX(Integer.valueOf(arr[1]));
						p2.setY(Integer.valueOf(arr[2]));
					} else if (msg.substring(0, 2).equals("C1"))
					{
						String[] arr = msg.split("\\s+");
						score1 = Integer.valueOf(arr[1]);
					} else if (msg.substring(0, 2).equals("C2"))
					{
						String[] arr = msg.split("\\s+");
						score2 = Integer.valueOf(arr[1]);
					} else if (msg.equals("EnterAction"))
					{
						enterAction();
					} else if (msg.equals("bulletsMove"))
					{
						BulletsMove();
					} else if (msg.equals("shoot1"))
					{
						shootAction1();
					} else if (msg.equals("shoot2"))
					{
						shootAction2();
					} else if (msg.equals("e_shoot"))
					{
						e_shootAction();
					} else if (msg.equals("pause"))
					{
						state = PAUSE;
					} else if (msg.equals("running"))
					{
						state = RUNNING;
					}
				}
			} catch (EOFException e)
			{
				// System.out.println("client closed!");
			} catch (Exception e)
			{
				e.printStackTrace();
			}

		}
	}

	class Socket2 extends Thread
	{
		public void run()
		{
			try
			{
				// 新建服务端连接
				socket2 = new Socket(IP, 8001);
				// 获取客户端输出流
				System.out.println("S2连上服务器");
				new DataOutputStream(socket2.getOutputStream());

			} catch (UnknownHostException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			try
			{

				while (true)
				{

					input2 = new DataInputStream(socket2.getInputStream());
					String msg = input2.readUTF();
					if (msg.substring(0, 1).equals("F"))
					{
						String[] arr = msg.split("\\s+");
						int i = Integer.valueOf(arr[1]);
						// System.out.print(i + "\n");
						airplane[i].setX(Integer.valueOf(arr[2]));
						airplane[i].setY(Integer.valueOf(arr[3]));
						repaint();
					} else if (msg.substring(0, 2).equals("L1"))
					{
						String[] arr = msg.split("\\s+");

						p1.setLife(Integer.valueOf(arr[1]));
						repaint();

					} else if (msg.substring(0, 2).equals("L2"))
					{
						String[] arr = msg.split("\\s+");
						p2.setLife(Integer.valueOf(arr[1]));
						repaint();
					} else if (msg.substring(0, 4).equals("pdie"))
					{
						String[] arr = msg.split("\\s+");
						airplane[Integer.valueOf(arr[1])].die = true;
						airplane[Integer.valueOf(arr[1])].dieIndex = 1;
					} else if (msg.substring(0, 4).equals("hit2"))
					{
						String[] arr = msg.split("\\s+");
						airplane[Integer.valueOf(arr[1])].setImage(enemy2_down0);
					} else if (msg.substring(0, 5).equals("b1hit"))
					{
						String[] arr = msg.split("\\s+");
						bullet_p1[Integer.valueOf(arr[1])].setImage(null);
					} else if (msg.substring(0, 5).equals("b2hit"))
					{
						String[] arr = msg.split("\\s+");
						bullet_p2[Integer.valueOf(arr[1])].setImage(null);
					} else if (msg.substring(0, 5).equals("ebhit"))
					{
						String[] arr = msg.split("\\s+");
						airplane[Integer.valueOf(arr[1])].e_bullets[Integer.valueOf(arr[2])].setImage(null);
					} else if (msg.equals("p1die"))
					{
						p1.die = true;
					} else if (msg.equals("p2die"))
					{
						p2.die = true;
					} else if (msg.equals("GameLose"))
					{
						p1.setImage(null);
						p2.setImage(null);
						for (int i = 0; i < 25; i++)
						{
							airplane[i] = nextOne(Plane.Map[i]);
						}
						bullet_p1 = new Bullet_Hero[0]; // 清空子弹
						bullet_p2 = new Bullet_Hero[0]; // 清空子弹
						score1 = 0; // 清空成绩
						score2 = 0;
						state = LOSE; // 改变状态

					} else if (msg.equals("GameWin"))
					{
						
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
			} catch (Exception e)
			{
				e.printStackTrace();
			}

		}
	}

	class Socket3 extends Thread
	{
		public void run()
		{
			try
			{
				// 新建服务端连接
				socket3 = new Socket(IP, 8002);
				// 获取客户端输出流
				System.out.println("S3连上服务器");
				// JOptionPane.showMessageDialog(null, "Connect!");
				output3 = new DataOutputStream(socket3.getOutputStream());

			} catch (UnknownHostException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			try
			{

				while (true)
				{
					new DataInputStream(socket3.getInputStream());
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}

		}

	}
}