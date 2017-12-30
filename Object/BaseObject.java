package Object;

import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Frame.StoryRoom;

public class BaseObject extends JLabel{
	protected StoryRoom room;	//�������� ���� ��ü��� ���� ��ü
	public float x;	//x,y��ǥ(�������)
	public float y;
	public float originX, originY;	//������Ʈ�� ���߾���ǥ
	public float width;	//����ũ��
	public float height;	//����ũ��
	protected String name; // ��ü�� �̸�
	BaseObject( StoryRoom room){
		this.room=room;
		name = this.getClass().getName().split("\\.")[1];
	}
	public void setImage(String img) { // �̹����� �ּҰ��� �־��ָ� �˾Ƽ� ôô ��ġ���� �����
		ImageIcon icon = new ImageIcon(img);
		setIcon(icon);
		width = icon.getIconWidth();
		height = icon.getIconHeight();
		setBounds((int) x, (int) y, (int) width, (int) height);
	}
	public Point getPoint() { // �ڽ��� ��ġ��(���߾�)
		Point p = new Point((int) (x + width / 2), (int) (y + height / 2));
		return p;
	}
	public void setOrigin() { // �ڽ��� ��ġ��(���߾�)
		originX = (x + width / 2);
		originY = (y + height / 2);
	}
	
	public void remove() { // ������Ʈ ����!
		room.remove(this);
		//Bullet t;
		if (this instanceof Bullet){
			room.bulletList.remove(this);
			}
		if (this instanceof Bullet2){
			room.bulletList2.remove(this);
			}
		if (this instanceof Monster){
			room.monsterList.remove(this);
			}
		if (this instanceof Block){
			room.blockList.remove(this);
			}
		room.repaint();
	}
}