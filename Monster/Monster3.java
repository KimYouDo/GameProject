package Monster;

import java.awt.Point;

import javax.swing.JLabel;

import Frame.*;
import Main.Project;
import Object.Bullet;
import Object.BulletOfMonster;
import Object.Hp;
import Object.Monster;

//ù��° ���� Ŭ����
@SuppressWarnings("serial")
public class Monster3 extends Monster { // �̸� ����Ī-�̸��� ����� �����ݽô�! ����1�̶��! ��� �˾ƸԳ���!
	public enum Motion { // ��� ����
		Init, Move, Attack
	};

	Motion motion; // motion�� �浹�� �Ͼ�Ƿ� ������� ������� null�� �������(���� ���� �������� ��������)
	private Bullet bullet; // ���� �Ѿ�
	boolean attackFlag;

	public Monster3(Point xy, StoryRoom room) { // ������ (��ġ�� ����â �޾ƿ�)
		super(400, xy, room);
		setImage("Monster1Init.gif");
		speed = 0.1f;
		motion = Motion.Init;
		attackFlag = false;
	}

	public void step() { // ���ͺ� �ʿ��� �۾��� �߰��� �������ؼ� ����ؾ��Ѵ�.

		super.step();
		setImage(name + motion.name() + ".gif");
	}

	public void attackStep() {
			if (count % (110 * 17 / room.step) == 0) {
				setAngle(room.player.getPoint());
				//motion = Motion.Attack;
				attack();
			}
			if (count % (130 * 17 / room.step) == 0) {
				//motion = Motion.Init;
				count = 0;
			}
	}

	// �����̸� ������
	public void moveStep() {
		room.player.setOrigin();

		if (distanceTo(room.player) < 250){
			speed = 0.2f;
			colliderSpeed=speed;
		}

		if (count % (70 * 17 / room.step) == 0){
			if(speed==0.2f){
				attack2();
			speed = 0.1f;
			}
			gotoX = (Math.random() * Project.windowSize.x) - Project.windowSize.x / 2;
			gotoY = (Math.random() * Project.windowSize.y) - Project.windowSize.y / 2;
			setVisible(false);
			hp.base.setVisible(false);
			hp.hpBar.setVisible(false);
			setAngle(new Point((int) gotoX, (int) gotoY));
		}
		
		if (count % (100 * 17 / room.step) == 0){
			motion = Motion.Move;
			setVisible(true); 
			num=3;
			hp.base.setVisible(true);
			hp.hpBar.setVisible(true);
		}

		super.move();
	}
	
	public void attack2() { // ���� ���
		double bulltX =room.player.getPoint().x;
		double bulltY =room.player.getPoint().y;
		bullet = new BulletOfMonster("bullet3.png",10,getPoint(), room.player.getPoint(), room);
		room.bulletList.add((Bullet) bullet);
		room.add(bullet);

		bullet = new BulletOfMonster("bullet3.png",10,getPoint(), room.player.getPoint(), room);
		room.bulletList.add((Bullet) bullet);
		room.add(bullet);
		
		bullet = new BulletOfMonster("bullet3.png",10,getPoint(), room.player.getPoint(), room);
		room.bulletList.add((Bullet) bullet);
		room.add(bullet);
	}

		public void attack() { // ���� ���
			bullet = new BulletOfMonster("bullet3.png",10,getPoint(), room.player.getPoint(), room);
			room.bulletList.add((Bullet) bullet);
			room.add(bullet);
		}
	}