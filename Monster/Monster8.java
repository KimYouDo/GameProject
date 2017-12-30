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

//첫번째 몬스터 클래스
@SuppressWarnings("serial")
public class Monster8 extends Monster { // 이름 가명칭-이름을 제대로 지어줍시다! 몬스터1이라니! 어떻게 알아먹나요!
	public enum Motion { // 모션 정의
		Init
	};

	Motion motion; // motion과 충돌이 일어나므로 사용하지 않을경우 null로 만들어줌(좋은 생각 떠오르면 수정예정)
	private Bullet bullet; // 공격 총알
	boolean attackFlag;

	public Monster8(Point xy, StoryRoom room) { // 생성자 (위치와 게임창 받아옴)
		super(200, xy, room);
		setImage("Monster1Init.gif");
		speed = 0.1f;
		motion = Motion.Init;
		attackFlag = false;
		setVisible(false);
		hp.base.setVisible(false);
		hp.hpBar.setVisible(false);
	}

	public void step() { // 몬스터별 필요한 작업은 추가로 재정의해서 사용해야한다.

		super.step();
		setImage(name + motion.name() + ".gif");
	}

	public void attackStep() {
			if (count % (180 * 17 / room.step) == 0) {
				attack();
				count = 0;
			}
	}

	// 깜빡이며 움직임
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
	

		public void attack() { // 공격 모션
			bullet = new BulletOfMonster("bullet.png",5,getPoint(),room.player.getPoint(),0.3f, room);
			room.bulletList.add(bullet);  // 기술명 넘기기
			room.add(bullet);
		}
	}
