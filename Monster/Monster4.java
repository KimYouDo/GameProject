package Monster;

import java.awt.Point;

import Frame.*;
import Main.Project;
import Object.Bullet;
import Object.BulletOfMonster;
import Object.Monster;

//첫번째 몬스터 클래스
@SuppressWarnings("serial")
public class Monster4 extends Monster { // 이름 가명칭-이름을 제대로 지어줍시다! 몬스터1이라니! 어떻게 알아먹나요!
	public enum Motion { // 모션 정의
		Init, Move, Attack
	};
	int healingCount = 0;
	Motion motion; // motion과 충돌이 일어나므로 사용하지 않을경우 null로 만들어줌(좋은 생각 떠오르면 수정예정)
	private Bullet bullet; // 공격 총알
	boolean attackFlag;

	public Monster4(Point xy, StoryRoom room) { // 생성자 (위치와 게임창 받아옴)
		super(400, xy, room);
		setImage("Monster4Init.gif");
		speed = 0.04f;
		num=4;
		motion = Motion.Init;
		attackFlag = false;
	}

	public void step() { // 몬스터별 필요한 작업은 추가로 재정의해서 사용해야한다.

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
			if (healingCount==12){  //12번 움직이면 피 100%회복
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
			healingCount++;  // 힐링까지의 카운트
			
			gotoX = (Math.random() * Project.windowSize.x) - Project.windowSize.x / 2;
			gotoY = (Math.random() * Project.windowSize.y) - Project.windowSize.y / 2;
			setAngle(room.player.getPoint());
		}

		super.move();
	}
	
	public void attack2() { // 공격 모션
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

		public void attack() { // 공격 모션
			bullet = new BulletOfMonster("bullet.png",15,getPoint(), room.player.getPoint(), room);
			room.bulletList.add((Bullet) bullet);
			room.add(bullet);
		}
	}
