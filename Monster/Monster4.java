package Monster;

import java.awt.Point;

import Frame.*;
import Main.Project;
import Object.Bullet;
import Object.BulletOfMonster;
import Object.Monster;

//ù��° ���� Ŭ����
@SuppressWarnings("serial")
public class Monster4 extends Monster { // �̸� ����Ī-�̸��� ����� �����ݽô�! ����1�̶��! ��� �˾ƸԳ���!
	public enum Motion { // ��� ����
		Init, Move, Attack
	};
	int healingCount = 0;
	Motion motion; // motion�� �浹�� �Ͼ�Ƿ� ������� ������� null�� �������(���� ���� �������� ��������)
	private Bullet bullet; // ���� �Ѿ�
	boolean attackFlag;

	public Monster4(Point xy, StoryRoom room) { // ������ (��ġ�� ����â �޾ƿ�)
		super(400, xy, room);
		setImage("Monster4Init.gif");
		speed = 0.04f;
		num=4;
		motion = Motion.Init;
		attackFlag = false;
	}

	public void step() { // ���ͺ� �ʿ��� �۾��� �߰��� �������ؼ� ����ؾ��Ѵ�.

		super.step();
		setImage(name + motion.name() + ".gif");
	}

	public void attackStep() {
			if (count % (100 * 17 / room.step) == 0) {
				setAngle(room.player.getPoint());
				motion = Motion.Attack;
				attack();
			}
			if (count % (120 * 17 / room.step) == 0) {
				motion = Motion.Init;
				count = 0;
			}
			if (healingCount==12){  //12�� �����̸� �� 100%ȸ��
				hp.hp=hp.hpMax;
				damage(0);
				healingCount=0;
			}
	}

	public void moveStep() {
		room.player.setOrigin();

		if (distanceTo(room.player) < 250){
			speed = 0.2f;
			colliderSpeed=speed;
		}

		if (count % (120 * 17 / room.step) == 0){
			if(speed==0.2f){
				attack2();
			speed = 0.08f;
			}
			healingCount++;  // ���������� ī��Ʈ
			
			gotoX = (Math.random() * Project.windowSize.x) - Project.windowSize.x / 2;
			gotoY = (Math.random() * Project.windowSize.y) - Project.windowSize.y / 2;
			setAngle(room.player.getPoint());
		}

		super.move();
	}
	
	public void attack2() { // ���� ���
		double bulltX =room.player.getPoint().x;
		double bulltY =room.player.getPoint().y;
		bullet = new BulletOfMonster("bullet.png",10,getPoint(), new Point((int)bulltX,(int)bulltY+20), room);
		room.bulletList.add((Bullet) bullet);
		room.add(bullet);

		bullet = new BulletOfMonster("bullet.png",10,getPoint(), new Point((int)bulltX,(int)bulltY), room);
		room.bulletList.add((Bullet) bullet);
		room.add(bullet);
		
		bullet = new BulletOfMonster("bullet.png",10,getPoint(), new Point((int)bulltX,(int)bulltY-20), room);
		room.bulletList.add((Bullet) bullet);
		room.add(bullet);
	}

		public void attack() { // ���� ���
			bullet = new BulletOfMonster("bullet.png",15,getPoint(), room.player.getPoint(), room);
			room.bulletList.add((Bullet) bullet);
			room.add(bullet);
		}
	}