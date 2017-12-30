package Object;

import java.awt.Point;

import javax.swing.JOptionPane;

import Frame.StoryRoom;
import Object.Player.Motion;

@SuppressWarnings("serial")
public class BulletOfMonster extends Bullet {
	public int damage;
	public BulletOfMonster(String name,int damage, Point xy, Point point, StoryRoom room) {
		super(name, xy, point, room);
		speed = 0.8f;
		// TODO Auto-generated constructor stub
	}
	
	public BulletOfMonster(String name,int damage, Point xy, Point point,float sp, StoryRoom room) {
		super(name, xy, point, room);
		this.damage=damage;
		speed = sp;
		// TODO Auto-generated constructor stub
	}

	public void attackedDecision() {//���Ϳ� �÷��̾��� �ǰݴ���� �ٸ�
		setOrigin();
		other = room.player;
		if (distanceTo(other)<=other.width/2) {
			if(room.player.motion!=Motion.Defense)
			((Player) other).damage(damage); // ������
			remove();
			
			int choiceNum=0;                   // �˾Ƽ� ������.
			if(((Player) other).hp.hp<0){
				String[] choices = {"�ٽ��ϱ�","���� ����", "����"};
				choiceNum= JOptionPane.showOptionDialog(null,
						"�̼ǿ� �����ϼ̽��ϴ�.","�̼ǽ���",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
				switch (choiceNum) {
				case 0:
					room.Initialization(room.difficulty, room.stage);
					break;

				case 1:
					if(room.stage!=1)
						room.Initialization(room.difficulty, room.stage-=1);
					break;

				default:
					System.exit(0);
					break;
				}	
			}
		}
	}
}