package Listener;

import java.awt.Point;

import BackGround.BackGround1;
import Block.Block1;
import Block.Block2;
import Frame.StoryRoom;
import Monster.Monster1;
import Monster.Monster2;
import Monster.Monster3;
import Monster.Monster4;
import Monster.Monster5;
import Monster.Monster6;
import Monster.Monster7;
import Monster.Monster8;
import Monster.Monster9;
import Object.BackGround;
import Object.Block;
import Object.Monster;

public class Creator {
	StoryRoom room;

	public Creator(StoryRoom room) {
		this.room = room;
	}

	public Monster getMonster( int num, Point point) {
			Monster monster = null;
			switch (num) { // 여기서 몬스터 종류가 갈리지
			case 1:
				monster = new Monster1(point, room);
				break;
			case 2:
				monster = new Monster2(point, room);
				break;
			case 3:
				monster = new Monster3(point, room);
				break;
			case 4:
				monster = new Monster4(point, room);
				break;
			case 5:
				monster = new Monster5(point, room);
				break;
			case 6:
				monster = new Monster6(point, room);
				break;
			case 7:
				monster = new Monster7(point, room);
				break;
			case 8:
				monster = new Monster8(point, room);
				break;
			case 9:
				monster = new Monster9(point, room);
				break;
			}
			return monster;
	}
	public Block getBlock( int num,Point xy,Point wh) {
		Block block = null;
		switch (num) { // 
		case 1:
			block  = new Block1(xy,wh,room);
			break;
		case 2:
			block = new Block2(xy,wh,room);
			break;
		case 3:
			//block = new Block3(new Point(x, y), room);
			break;
		}
		return block;
}
	public BackGround getBackGround( int num,Point xy,Point wh) {
		BackGround backGround = null;
		switch (num) { // 
		case 1:
			backGround  = new BackGround1(xy,wh,room);
			break;
		case 2:
			backGround  = new BackGround1(xy,wh,room);
			break;
		case 3:
			//block = new Block3(new Point(x, y), room);
			break;
		}
		return backGround;
}

}
