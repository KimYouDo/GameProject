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

//첫번째 몬스터 클래스
@SuppressWarnings("serial")
public class Monster9 extends Monster { // 이름 가명칭-이름을 제대로 지어줍시다! 몬스터1이라니! 어떻게 알아먹나요!
	public enum Motion { // 모션 정의
		Init
	};
	Creator creator;
	Motion motion; // motion과 충돌이 일어나므로 사용하지 않을경우 null로 만들어줌(좋은 생각 떠오르면 수정예정)
	private Bullet2 bullet; // 공격 총알
	boolean attackFlag;
	Iterator<?> it; //피격판정을 위한 반복자
	Monster other;	//피격대상

	public Monster9(Point xy, StoryRoom room) { // 생성자 (위치와 게임창 받아옴)
		super(1000,xy, room);                    // 체력설정
		num=1;
		setImage("Monster9Init.gif");
		speed = 0.0f;
		motion = Motion.Init;
		attackFlag = false;
	}

	public void step() { // 몬스터별 필요한 작업은 추가로 재정의해서 사용해야한다.

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

	public void attack() { // 공격 모션
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
