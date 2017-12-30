package Object;

import java.awt.Point;
import java.util.Iterator;

import Frame.StoryRoom;

@SuppressWarnings("serial")
public class Bullet2 extends MoveObject {//�Ѿ� ������Ʈ-�÷��̾�� ������ ���ݾ׼��� ���(�Ѵ� ��ӹ޾Ƽ� ���)
	Iterator<?> it; //�ǰ������� ���� �ݺ���
	MoveObject other;	//�ǰݴ��
	public Bullet2(String name, Point xy, Point gotoXY,StoryRoom room) {	// ������ ��ġ�� ������ ��ġ,����â�� �޾ƿ�
		super(room); // ��� ��¥ good!!!!!!!!!
		x = xy.x;
		y = xy.y;
		speed = 0.5f;
		setImage(name);
		setAngle(gotoXY);
	}
	public Bullet2(String name, Point xy, Point gotoXY, float sp,StoryRoom room) {	// ������ ��ġ�� ������ ��ġ,����â�� �޾ƿ�
		super(room); // ��� ��¥ good!!!!!!!!!
		x = xy.x;
		y = xy.y;
		speed = sp;
		setImage(name);
		setAngle(gotoXY);
	}

	public void step(){		//�Žð� �۵�
		moving();
		attackedDecision();
	}
	public void attackedDecision() {
		// TODO Auto-generated method stub
		
	}
	public void moving() {	//�̵�
		
		angle = (float)Math.sqrt(Math.pow(angleX, (float) 2) + Math.pow(angleY, (float) 2));
		x += speed * (angleX / angle)*room.step;
		y += speed * (angleY / angle)*room.step;
		if(-300>x||1580<x||-300>y||1200<y)
			remove();
		
		setLocation((int) x, (int) y);
	}
	void damage(int power) {	//������� ����(���߿� ��������� ��)
		// TODO Auto-generated method stub
	}
}
