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

//첫번째 몬스터 클래스
@SuppressWarnings("serial")
public class Monster6 extends Monster { // 이름 가명칭-이름을 제대로 지어줍시다! 몬스터1이라니! 어떻게 알아먹나요!
	public enum Motion { // 모션 정의
		Init
	};

	Motion motion; // motion과 충돌이 일어나므로 사용하지 않을경우 null로 만들어줌(좋은 생각 떠오르면 수정예정)
	private Bullet2 bullet; // 공격 총알
	boolean attackFlag;
	Iterator<?> it; //피격판정을 위한 반복자
	Monster other;	//피격대상

	public Monster6(Point xy, StoryRoom room) { // 생성자 (위치와 게임창 받아옴)
		super(200,xy, room);                    // 체력설정
		num=1;
		setImage("Monster6Init.gif");
		speed = 0.0f;
		motion = Motion.Init;
		attackFlag = false;
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

	//너가 구현한 부분
	public void moveStep() {
		super.move();
	}

	public void attack() { // 공격 모션
		bullet = new BulltOfPass("bullet3.gif",20,getPoint(),room.player.getPoint(),0.2f, room);
		room.bulletList2.add(bullet);  // 기술명 넘기기
		room.add(bullet);
	}
}
