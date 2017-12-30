package Monster;

import java.awt.Point;
import java.util.Iterator;

import Frame.*;
import Main.Project;
import Monster.Monster1.Motion;
import Object.Bullet;
import Object.BulletOfMonster;
import Object.Monster;
import Object.MoveObject;

//ù��° ���� Ŭ����
@SuppressWarnings("serial")
public class Monster5 extends Monster { // �̸� ����Ī-�̸��� ����� �����ݽô�! ����1�̶��! ��� �˾ƸԳ���!
	public enum Motion { // ��� ����
		Init, Move, Attack
	};
	Motion motion; // motion�� �浹�� �Ͼ�Ƿ� ������� ������� null�� �������(���� ���� �������� ��������)
	private Bullet bullet; // ���� �Ѿ�
	boolean attackFlag;
	Iterator<?> it; //�ǰ������� ���� �ݺ���
	Monster other;	//�ǰݴ��
	public Monster5(Point xy, StoryRoom room) { // ������ (��ġ�� ����â �޾ƿ�)
		super(400, xy, room);
		setImage("Monster5Init.gif");
		speed = 0.04f;
		num=5;
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
			
	}

	public void moveStep() {
		room.player.setOrigin();
		
			if (distanceTo(room.player) < 250) {
				if (!attackFlag){
				speed = 0.10f;
				
				gotoX = (float) (room.player.originX - originX);
				gotoY = (float) (room.player.originY - originY);
				setAngle(room.player.getPoint());
				motion = Motion.Init;
				if (distanceTo(room.player) < 150) {
					motion = Motion.Move;
					speed = 0.2f;
					if (distanceTo(room.player) < 80) {
						attack2();
						gotoX = -(float) (room.player.originX - originX);
						gotoY = -(float) (room.player.originY - originY);
						attackFlag=true;
					}
				}
				}
			} else {
				attackFlag=false;
				if (!attackFlag){
				if (count % (120 * 17 / room.step) == 0) {
					speed = 0.04f;
					room.player.speed=0.12f;
					gotoX = (Math.random() * Project.windowSize.x) - Project.windowSize.x / 2;
					gotoY = (Math.random() * Project.windowSize.y) - Project.windowSize.y / 2;
					setAngle(room.player.getPoint());
					}
				}
			}
		super.move();
	}

	//�����ϸ� �÷��̿� ����
	public void attack2()  { // ���� ���
		room.player.speed=0.0f;
	}

	// �Ϲ� ���ݽ� �ֺ��� ���Ͱ� ������ ȸ��
	public void attack() { // ���� ���
		setOrigin();
		it=room.monsterList.iterator();
		while(it.hasNext()){
			other=(Monster) it.next();
			if (distanceTo(other)<=350){
				if(other.hp.hp+30<other.hp.hpMax)
				other.hp.hp+=30;
				else
					other.hp.hp=other.hp.hpMax;
				other.damage(0);
			}
		}
		bullet = new BulletOfMonster("bullet.png",20,getPoint(), room.player.getPoint(), room);
		room.bulletList.add((Bullet) bullet);
		room.add(bullet);
	}
}