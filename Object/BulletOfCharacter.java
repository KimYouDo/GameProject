package Object;

import java.awt.Point;

import javax.swing.JOptionPane;

import Frame.StoryRoom;

@SuppressWarnings("serial")
public class BulletOfCharacter extends Bullet {// 플레이어의 총알
	public int damage;
	public BulletOfCharacter(String nmae, int damage, Point xy, Point point, StoryRoom room) {	//위치,이동방향,게임창 파라메터
		super(nmae, xy, point, room);
		this.damage=damage;
		speed=0.8f;
		// TODO Auto-generated constructor stub
	}
	
	public void attackedDecision() {	//몬스터와 플레이어의 피격대상이 다름
		setOrigin();
		it=room.monsterList.iterator();
		while(it.hasNext()){
			other=(Monster) it.next();
			if (distanceTo(other)<=other.width/2){
				((Monster)other).damage(damage);	//데미지
				remove();
				break;
			}
		}
	}
}
