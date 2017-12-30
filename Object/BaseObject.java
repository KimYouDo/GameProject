package Object;

import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Frame.StoryRoom;

public class BaseObject extends JLabel{
	protected StoryRoom room;	//스테이지 내부 객체제어를 위한 객체
	public float x;	//x,y좌표(좌측상단)
	public float y;
	public float originX, originY;	//오브젝트의 정중앙좌표
	public float width;	//가로크기
	public float height;	//세로크기
	protected String name; // 객체의 이름
	BaseObject( StoryRoom room){
		this.room=room;
		name = this.getClass().getName().split("\\.")[1];
	}
	public void setImage(String img) { // 이미지의 주소값만 넣어주면 알아서 척척 위치까지 잡아줌
		ImageIcon icon = new ImageIcon(img);
		setIcon(icon);
		width = icon.getIconWidth();
		height = icon.getIconHeight();
		setBounds((int) x, (int) y, (int) width, (int) height);
	}
	public Point getPoint() { // 자신의 위치값(정중앙)
		Point p = new Point((int) (x + width / 2), (int) (y + height / 2));
		return p;
	}
	public void setOrigin() { // 자신의 위치값(정중앙)
		originX = (x + width / 2);
		originY = (y + height / 2);
	}
	
	public void remove() { // 오브젝트 제거!
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
