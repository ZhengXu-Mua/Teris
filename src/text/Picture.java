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
			return image;//��ȡͬ���е�ͼƬ��Դ
		}catch(Exception e) {//��ӡ�쳣
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	private static BufferedImage[] images=new BufferedImage[3];
	static {
		images[0]=LoadImage("background.png");//��ȡͼƬ
		images[1]=LoadImage("background.png");
		images[2]=LoadImage("hero0.png");
	}
	public void step() {
		y1+=step;//����
		y3+=step;//����
		if(y1>=this.height1) {//��y>=���ڵĸߣ���ζ�ų��˴�����
			y1=-this.height1;//��yͼƬ�ŵ�������
		}
		if(y3>=this.height1) {//��y3>=���ڵĸߣ���ζ�ų��˴�����
			y3=-this.height1;//��y3ͼƬ�ŵ�������
		}
	}
	public void paint(Graphics g) {//������Ķ���
		g.drawImage(images[0],x1,y1,null);
		g.drawImage(images[1],x1,y3,null);
		g.drawImage(images[2],x2,y2,null);
	}
	public void action() {
		MouseAdapter l =new MouseAdapter() {//��������
			public void mouseMoved(MouseEvent e) {
				int x=e.getX();//��ȡ����x����
				int y=e.getY();//��ȡ����y����
				x2=x-width2/2;//ͼƬ��x�������x�ƶ�
				y2=y-height2/2; //ͼƬ��y�������y�ƶ�
			}
		};
		this.addMouseListener(l);//�����������¼�
		this.addMouseMotionListener(l);//������껬���¼�
		Timer timer = new Timer();// ��ʱ������
		int interval = 10;// ʱ�������Ժ���Ϊ��λ��
		timer.schedule(new TimerTask() {
			public void run() {//��ʱ�ɵ���
				step();
				repaint();
			}
		// ��һ��10�ӳ�����������һ�δ�����ʱ�䣬�ڶ���10�ӵ�n�ε�n+1�ε�ʱ��
		}, interval, interval);
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame("Fly");// �������ڶ���
		Picture picture = new Picture();// ����һ��������
		frame.add(picture);// �������ӵ�������
		frame.setSize(WIDTH, HEIGHT);// ���ô��ڵĿ�͸�
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// ���ô��ڹر�ʱ�˳�����
		frame.setLocationRelativeTo(null);// ���þ�����ʾ
		frame.setVisible(true);// ���ô��ڿɼ�
		picture.action();// ���������ִ��
	}

}
