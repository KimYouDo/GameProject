package Monster;

import java.awt.Point;
import java.util.Iterator;

import Frame.*;
import Listener.Creator;
import Main.Project;
import Monster.Monster4.Motion;
import Object.Bullet;
import Object.Bullet2;
import Object.BulletOfMonster;
import Object.BulltOfMagic;
import Object.BulltOfPass;
import Object.Monster;

//ù��° ���� Ŭ����
@SuppressWarnings("serial")
public class Monster9 extends Monster { // �̸� ����Ī-�̸��� ����� �����ݽô�! ����1�̶��! ��� �˾ƸԳ���!
	public enum Motion { // ��� ����
		Init
	};
	Creator creator;
	Motion motion; // motion�� �浹�� �Ͼ�Ƿ� ������� ������� null�� �������(���� ���� �������� ��������)
	private Bullet2 bullet; // ���� �Ѿ�
	boolean attackFlag;
	Iterator<?> it; //�ǰ������� ���� �ݺ���
	Monster other;	//�ǰݴ��

	public Monster9(Point xy, StoryRoom room) { // ������ (��ġ�� ����â �޾ƿ�)
		super(1000,xy, room);                    // ü�¼���
		num=1;
		setImage("Monster9Init.gif");
		speed = 0.0f;
		motion = Motion.Init;
		attackFlag = false;
	}

	public void step() { // ���ͺ� �ʿ��� �۾��� �߰��� �������ؼ� ����ؾ��Ѵ�.

		super.step();
		setImage(name + motion.name() + ".gif");
	}

	public void attackStep() {
		if (count % (300 * 17 / room.step) == 0) {
			attack();
			count = 0;
		}
	}

	
	public void moveStep() {
		int ok=0;
		it=room.monsterList.iterator();
		while(it.hasNext()){
			other=(Monster) it.next();
			if(other.getClass().toString().equals("class Monster.Monster8")){
				ok=1;
			}
		}
		if(ok==1)
			this.hp.hp=this.hp.hpMax;
		super.move();
	}

	public void attack() { // ���� ���
		int X,Y;
		do{
			X = (int)(Math.random() * 1000+100);
			Y = (int)(Math.random() * 550+100);
		}while(X<getPoint().x+80&&X>getPoint().x-80&&Y<getPoint().x+80&&Y>getPoint().x-80);
		
		room.monster = room.creator.getMonster(6, new Point((int)X, (int)Y));
		room.monsterList.add(room.monster);
		room.add(room.monster);
	}
}