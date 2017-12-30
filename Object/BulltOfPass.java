package Object;

import java.awt.Point;

import javax.swing.JOptionPane;

import Frame.StoryRoom;
import Object.Player.Motion;

@SuppressWarnings("serial")
public class BulltOfPass extends Bullet2 {// 플레이어의 총알
	public int damage;
	public BulltOfPass(String nmae, int damage, Point xy, Point point, StoryRoom room) {	//위치,이동방향,게임창 파라메터
		super(nmae, xy, point, room);
		this.damage=damage;
		speed=0.8f;
		// TODO Auto-generated constructor stub
	}
	
	public BulltOfPass(String nmae, int damage, Point xy, Point point,float sp, StoryRoom room) {	//위치,이동방향,게임창 파라메터
		super(nmae, xy, point, room);
		this.damage=damage;
		speed=sp;
		// TODO Auto-generated constructor stub
	}
	
	public void attackedDecision() {//몬스터와 플레이어의 피격대상이 다름
		setOrigin();
		other = room.player;
		if (distanceTo(other)<=other.width/2) {
			if(room.player.motion!=Motion.Defense)
			((Player) other).damage(damage); // 데미지
			remove();
			
			int choiceNum=0;                   // 알아서 보세요.
			if(((Player) other).hp.hp<0){
				String[] choices = {"다시하기","이전 라운드", "종료"};
				choiceNum= JOptionPane.showOptionDialog(null,
						"미션에 실패하셨습니다.","미션실패",
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
