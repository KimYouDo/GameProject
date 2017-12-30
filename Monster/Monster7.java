package Monster;

import java.awt.Point;
import java.util.Iterator;

import Frame.*;
import Main.Project;
import Object.Bullet;
import Object.BulletOfMonster;
import Object.Monster;
import Object.MoveObject;

//ù��° ���� Ŭ����
@SuppressWarnings("serial")
public class Monster7 extends Monster { // �̸� ����Ī-�̸��� ����� �����ݽô�! ����1�̶��! ��� �˾ƸԳ���!
	public enum Motion { // ��� ����
		Init
	};

	Motion motion; // motion�� �浹�� �Ͼ�Ƿ� ������� ������� null�� �������(���� ���� �������� ��������)
	private Bullet bullet; // ���� �Ѿ�
	boolean attackFlag;
	Iterator<?> it; //�ǰ������� ���� �ݺ���
	Monster other;	//�ǰݴ��

	public Monster7(Point xy, StoryRoom room) { // ������ (��ġ�� ����â �޾ƿ�)
		super(30,xy, room);                    // ü�¼���
		num=1;
		setImage("Monster7Init.gif");
		speed = 0.3f;
		motion = Motion.Init;
		attackFlag = false;
	}

	public void step() { // ���ͺ� �ʿ��� �۾��� �߰��� �������ؼ� ����ؾ��Ѵ�.

		super.step();
		setImage(name + motion.name() + ".gif");
	}

	public void attackStep() {
		
	}

	//�ʰ� ������ �κ�
	public void moveStep() {
		if (count % (80 * 17 / room.step) == 0){
		room.player.setOrigin();
		gotoX = (Math.random() * Project.windowSize.x) - Project.windowSize.x / 2;
		gotoY = (Math.random() * Project.windowSize.y) - Project.windowSize.y / 2;
		setAngle(room.player.getPoint());
		count = 0;
		}
		it=room.monsterList.iterator();
		while(it.hasNext()){
			other=(Monster) it.next();
			if(other.hp.hp<other.hp.hpMax)
				other.hp.hp=other.hp.hpMax;		
			}
		
		super.move();
	}

	public void attack() { // ���� ���
		
	}
}