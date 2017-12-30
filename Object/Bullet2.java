package Object;

import java.awt.Point;
import java.util.Iterator;

import Frame.StoryRoom;

@SuppressWarnings("serial")
public class Bullet2 extends MoveObject {//총알 오브젝트-플레이어와 몬스터의 공격액션을 담당(둘다 상속받아서 사용)
	Iterator<?> it; //피격판정을 위한 반복자
	MoveObject other;	//피격대상
	public Bullet2(String name, Point xy, Point gotoXY,StoryRoom room) {	// 생성자 위치와 공격할 위치,게임창을 받아옴
		super(room); // 상속 진짜 good!!!!!!!!!
		x = xy.x;
		y = xy.y;
		speed = 0.5f;
		setImage(name);
		setAngle(gotoXY);
	}
	public Bullet2(String name, Point xy, Point gotoXY, float sp,StoryRoom room) {	// 생성자 위치와 공격할 위치,게임창을 받아옴
		super(room); // 상속 진짜 good!!!!!!!!!
		x = xy.x;
		y = xy.y;
		speed = sp;
		setImage(name);
		setAngle(gotoXY);
	}

	public void step(){		//매시간 작동
		moving();
		attackedDecision();
	}
	public void attackedDecision() {
		// TODO Auto-generated method stub
		
	}
	public void moving() {	//이동
		
		angle = (float)Math.sqrt(Math.pow(angleX, (float) 2) + Math.pow(angleY, (float) 2));
		x += speed * (angleX / angle)*room.step;
		y += speed * (angleY / angle)*room.step;
		if(-300>x||1580<x||-300>y||1200<y)
			remove();
		
		setLocation((int) x, (int) y);
	}
	void damage(int power) {	//사용하지 않음(나중에 사용할지도 모름)
		// TODO Auto-generated method stub
	}
}

