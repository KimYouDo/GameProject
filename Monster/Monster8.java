package Monster;

import java.awt.Point;

import javax.swing.JLabel;

import Frame.*;
import Main.Project;
import Object.Bullet;
import Object.Bullet2;
import Object.BulletOfCharacter;
import Object.BulletOfMonster;
import Object.BulltOfPass;
import Object.Hp;
import Object.Monster;

//ù��° ���� Ŭ����
@SuppressWarnings("serial")
public class Monster8 extends Monster { // �̸� ����Ī-�̸��� ����� �����ݽô�! ����1�̶��! ��� �˾ƸԳ���!
	public enum Motion { // ��� ����
		Init
	};

	Motion motion; // motion�� �浹�� �Ͼ�Ƿ� ������� ������� null�� �������(���� ���� �������� ��������)
	private Bullet bullet; // ���� �Ѿ�
	boolean attackFlag;

	public Monster8(Point xy, StoryRoom room) { // ������ (��ġ�� ����â �޾ƿ�)
		super(200, xy, room);
		setImage("Monster1Init.gif");
		speed = 0.1f;
		motion = Motion.Init;
		attackFlag = false;
		setVisible(false);
		hp.base.setVisible(false);
		hp.hpBar.setVisible(false);
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

	// �����̸� ������
	public void moveStep() {
		room.player.setOrigin();

		if (distanceTo(room.player) < 250){
			speed = 0.2f;
			colliderSpeed=speed;
		}

		if (count % (70 * 17 / room.step) == 0){
			if(speed==0.2f){
			speed = 0.1f;
			}
			gotoX = (Math.random() * Project.windowSize.x) - Project.windowSize.x / 2;
			gotoY = (Math.random() * Project.windowSize.y) - Project.windowSize.y / 2;
			setVisible(true); 
			hp.base.setVisible(true);
			hp.hpBar.setVisible(true);
		}
		
		if (count % (120 * 17 / room.step) == 0){
			num=8;
			setVisible(false);
			hp.base.setVisible(false);
			hp.hpBar.setVisible(false);
			count = 0;
		}

		super.move();
	}
	

		public void attack() { // ���� ���
			bullet = new BulletOfMonster("bullet.png",5,getPoint(),room.player.getPoint(),0.3f, room);
			room.bulletList.add(bullet);  // ����� �ѱ��
			room.add(bullet);
		}
	}