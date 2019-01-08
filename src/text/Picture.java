package text;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;
 
public class Picture extends JPanel {
	public static final int WIDTH=400;
	public static final int HEIGHT=700;
	private int x1=0;
	private int y1=0;
	private int x2=140;
	private int y2=400;
	private int width1=WIDTH;
	private int height1=HEIGHT;
	private int y3=-this.height1;
	private int step=1;
	private int width2=97;
	private int height2=124;
	public static BufferedImage LoadImage(String fireName) {
		try {
			BufferedImage image = ImageIO.read(Picture.class.getResource(fireName));
			return image;//读取同包中的图片资源
		}catch(Exception e) {//打印异常
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	private static BufferedImage[] images=new BufferedImage[3];
	static {
		images[0]=LoadImage("background.png");//读取图片
		images[1]=LoadImage("background.png");
		images[2]=LoadImage("hero0.png");
	}
	public void step() {
		y1+=step;//向下
		y3+=step;//向下
		if(y1>=this.height1) {//当y>=窗口的高，意味着出了窗口了
			y1=-this.height1;//将y图片放到最上面
		}
		if(y3>=this.height1) {//当y3>=窗口的高，意味着出了窗口了
			y3=-this.height1;//将y3图片放到最上面
		}
	}
	public void paint(Graphics g) {//画具体的对象
		g.drawImage(images[0],x1,y1,null);
		g.drawImage(images[1],x1,y3,null);
		g.drawImage(images[2],x2,y2,null);
	}
	public void action() {
		MouseAdapter l =new MouseAdapter() {//创建对象
			public void mouseMoved(MouseEvent e) {
				int x=e.getX();//获取鼠标的x坐标
				int y=e.getY();//获取鼠标的y坐标
				x2=x-width2/2;//图片的x随着鼠标x移动
				y2=y-height2/2; //图片的y随着鼠标y移动
			}
		};
		this.addMouseListener(l);//处理鼠标操作事件
		this.addMouseMotionListener(l);//处理鼠标滑动事件
		Timer timer = new Timer();// 定时器对象
		int interval = 10;// 时间间隔（以毫秒为单位）
		timer.schedule(new TimerTask() {
			public void run() {//定时干的事
				step();
				repaint();
			}
		// 第一个10从程序启动到第一次触发的时间，第二个10从第n次到n+1次的时间
		}, interval, interval);
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame("Fly");// 创建窗口对象
		Picture picture = new Picture();// 创建一个面板对象
		frame.add(picture);// 将面板添加到窗口中
		frame.setSize(WIDTH, HEIGHT);// 设置窗口的宽和高
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设置窗口关闭时退出程序
		frame.setLocationRelativeTo(null);// 设置居中显示
		frame.setVisible(true);// 设置窗口可见
		picture.action();// 启动程序的执行
	}

}
