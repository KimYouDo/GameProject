package Monster;

import java.awt.Point;
import java.util.Iterator;

import Frame.*;
import Main.Project;
import Object.Bullet;
import Object.Bullet2;
import Object.BulletOfMonster;
import Object.BulltOfMagic;
import Object.BulltOfPass;
import Object.Monster;

//ù��° ���� Ŭ����
@SuppressWarnings("serial")
public class Monster6 extends Monster { // �̸� ����Ī-�̸��� ����� �����ݽô�! ����1�̶��! ��� �˾ƸԳ���!
	public enum Motion { // ��� ����
		Init
	};

	Motion motion; // motion�� �浹�� �Ͼ�Ƿ� ������� ������� null�� �������(���� ���� �������� ��������)
	private Bullet2 bullet; // ���� �Ѿ�
	boolean attackFlag;
	Iterator<?> it; //�ǰ������� ���� �ݺ���
	Monster other;	//�ǰݴ��

	public Monster6(Point xy, StoryRoom room) { // ������ (��ġ�� ����â �޾ƿ�)
		super(200,xy, room);                    // ü�¼���
		num=1;
		setImage("Monster6Init.gif");
		speed = 0.0f;
		motion = Motion.Init;
		attackFlag = false;
	}

	public void step() { // ���ͺ� �ʿ��� �۾��� �߰��� �������ؼ� ����ؾ��Ѵ�.
		super.step();
		setImage(name + motion.name() + ".gif");
	}

	public void attackStep() {
		if (count % (180 * 17 / room.step) == 0) {
				attack();
				count = 0;
		}
	}

	//�ʰ� ������ �κ�
	public void moveStep() {
		super.move();
	}

	public void attack() { // ���� ���
		bullet = new BulltOfPass("bullet3.gif",20,getPoint(),room.player.getPoint(),0.2f, room);
		room.bulletList2.add(bullet);  // ����� �ѱ��
		room.add(bullet);
	}
}