package Object;

import java.awt.Point;

import javax.swing.JOptionPane;

import Frame.StoryRoom;

@SuppressWarnings("serial")
public class BulletOfCharacter extends Bullet {// �÷��̾��� �Ѿ�
	public int damage;
	public BulletOfCharacter(String nmae, int damage, Point xy, Point point, StoryRoom room) {	//��ġ,�̵�����,����â �Ķ����
		super(nmae, xy, point, room);
		this.damage=damage;
		speed=0.8f;
		// TODO Auto-generated constructor stub
	}
	
	public void attackedDecision() {	//���Ϳ� �÷��̾��� �ǰݴ���� �ٸ�
		setOrigin();
		it=room.monsterList.iterator();
		while(it.hasNext()){
			other=(Monster) it.next();
			if (distanceTo(other)<=other.width/2){
				((Monster)other).damage(damage);	//������
				remove();
				break;
			}
		}
	}
}