package Object;
import java.awt.Point;
import java.util.Iterator;

import Frame.StoryRoom;
@SuppressWarnings("serial")
public class Player extends MoveObject {
	public enum Motion {	//플레이어의 모션상태
		Init, Move, Attack, Attack2, Healing, Dash, Defense, deify
	};// 기타 추가 예정
	public float vertical,horizon;	//상하이동,좌우이동
	Hp hp;	//체력
	protected int flagV, flagH;	//key상태 플래그
	// 카운트 합치면 한가지 사용시 다른걸 사용 못해서 분리하였으나 대쉬중 공격이 안되게 구현시 하나로 합쳐도 됨
	int attackCount;	//공격카운트
	int dashCount;	//대쉬카운트
	//public Motion motion;	//모션상태
	private float dashV;	//대쉬세로방향
	private float dashH;	//대쉬가로방향
	private Point mouse;	//마우스 위치(회전에 사용)
	private float xStep,yStep;	//x,y이동값
	public Motion motion;	//현재 모션
	private Bullet bullet;
	Iterator<?> it; //피격판정을 위한 반복자
	Monster other;	//피격대상
	int skillOk;

	public Player(int x, int y,int n, StoryRoom p) { 	//생성자-초기화
		super(p); // MoveObject 초기화
		hp = new Hp(100, room, true);
		//motion = Motion.Init;	//처음엔 기본상태
		speed = 0.15f;
		skillOk=n;
		colliderSpeed = speed;
		flagV = 0;
		flagH = 0;
		this.x = x;
		this.y = y;
		attackCount = 0;
		dashCount = 0;
		mouse=new Point(0,0);
		setImage("playerInit.gif");
		motion = Motion.Init;
		// setSize(64,64);
		setLocation((int) x, (int) y);
		// thread.start();
	}
	public void setMouse(Point point){	//마우스 모션 리스너에서 마우스 위치지정
		mouse=point;
	}
	public Point getmouse(){
		return mouse;
	}
	public void step() {	//매시간 반복
		//setOrigin();
		attackStep();
		if (motion.equals(Motion.Dash)) 
			dashStep();
		else
			move(vertical, horizon);
		setImage(name+motion.name()+".gif");
		setAngle(mouse);
	}

	private void dashStep() {
		{ // 나중에 스킬 계열로 뺄 예정 일단 동작확인을 위해 추가
			if (dashV == 0 && dashH == 0) {
				dashV = vertical * 4;
				dashH = horizon * 4;
				colliderSpeed = speed * 4;
			}
			dashCount += room.step / 2;
			if (dashCount > 70) {
				motion = Motion.Init;
				dashCount = 0;
				dashV = 0;
				dashH = 0;
				colliderSpeed = speed;
			}
			move(dashV, dashH);
		}
	}
	private void attackStep() {
		if (motion.equals(Motion.Attack)) {
			attackCount += room.step / 2;
			if (attackCount > 100) {
				motion = Motion.Init;
				attackCount = 0;
			}
		}
		if (motion.equals(Motion.Attack2)) {
			attackCount += room.step / 2;
			if (attackCount > 100) {
				motion = Motion.Init;
				attackCount = 0;
			}
		}
		if (motion.equals(Motion.Defense)) {
			attackCount += room.step / 2;
			if (attackCount > 500) {
				motion = Motion.Init;
				attackCount = 0;
			}
		}	
		if (motion.equals(Motion.Healing)) {
			attackCount += room.step / 2;
			if (attackCount > 800) {
				//speed = 0.0f;
				motion = Motion.Init;
				attackCount = 0;
			}
		}	
	}


	public void damage(int power) {	//피격
		int damage;
		soundDamage.play();
		damage = power;
		hp.damage(damage);
		// TODO Auto-generated method stub
	}

	public void remove() {	//제거
		hp.remove();
		super.remove();
	}

	public void move(float vertical, float horizon) {	//이동

		// 대각선 이동시 루트2로 나누어줘서 반경속도로 맞춤
		if (horizon != 0 && vertical != 0) {
			xStep = (float) (horizon / Math.sqrt(2));
			yStep = (float) (vertical / Math.sqrt(2));
		} else {
			xStep = horizon;
			yStep = vertical;
		}

		x += xStep * room.step;
		y += yStep * room.step;
		collider(); // 충돌확인
		if (flagX != 0) // x축 충돌
			x = flagX;
		if (flagY != 0) // y축 충돌
			y = flagY;
		if (motion == Motion.Init) {
			if (xStep != 0 || yStep != 0)
				motion = Motion.Move;
			else
				motion = Motion.Init;
		}
		setLocation((int) x, (int) y);

	}


	public void skill(int keyCode) {	//키보드 누를시의 스킬
		if(skillOk==1){
			switch (keyCode) {
			case '1':                       // 스킬 1(마법 공격)
				int playerX=getPoint().x;
				int playerY=getPoint().y;
				it=room.monsterList.iterator();
				while(it.hasNext()){
					other= (Monster) it.next();
					motion = Motion.Attack2;
					bullet = new BulletOfCharacter("bullet0-1.gif",50,new Point((int)x+30,(int)y),other.getPoint() , room);
					room.add(bullet);
					room.bulletList.add(bullet);
				}
				break;
			case '2':                         // 스킬 2(방어)
				motion = Motion.Defense;
				break;
			case '3':                         // 스킬 3(힐)
				motion = Motion.Healing;
				//speed = 0.0f;
				if(hp.hp+30>=hp.hpMax)
					hp.hp=hp.hpMax;
				else
					hp.hp+=20;
				damage(0);
				break;
			case '4':
				motion = Motion.deify;
				break;
			}
		}
	}
	// ////////////////////////////////////////////////////////////////////////////////////////무빙에 필요한
	// 동작
	public void moveS(int keyCode) {	//키보드 누를시의 이동
		switch (keyCode) {
		case 'W':
			if (flagV == 2) {
				vertical = speed;
				flagV = 1;
			} else {
				flagV = 0;
				vertical = 0;
			}
			break;
		case 'S':
			if (flagV == 2) {
				flagV = -1;
				vertical = -speed;
			} else {
				flagV = 0;
				vertical = 0;
			}
			break;
		case 'D':
			if (flagH == 2) {
				flagH = -1;
				horizon = -speed;
			} else {
				flagH = 0;
				horizon = 0;
			}
			break;
		case 'A':
			if (flagH == 2) {
				flagH = 1;
				horizon = speed;
			} else {
				flagH = 0;
				horizon = 0;
			}
			break;
		}
	}

	public void moveM(int keyCode) {//키보드 뗏을시의 이동
		switch (keyCode) {
		case 'W':
			vertical = -speed;
			if (flagV == 0)
				flagV = -1;
			else if (flagV == 1)
				flagV = 2;
			break;
		case 'S':
			vertical = speed;
			if (flagV == 0)
				flagV = 1;
			else if (flagV == -1)
				flagV = 2;
			break;
		case 'D':
			horizon = speed;
			if (flagH == 0)
				flagH = 1;
			else if (flagH == -1)
				flagH = 2;
			break;
		case 'A':
			horizon = -speed;
			if (flagH == 0)
				flagH = -1;
			else if (flagH == 1)
				flagH = 2;
			break;
		}
	}

	
}
