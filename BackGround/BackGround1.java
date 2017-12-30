package BackGround;

import java.awt.Point;

import Frame.StoryRoom;
import Object.BackGround;


public class BackGround1 extends BackGround {
	public BackGround1(Point xy,Point wh,StoryRoom room){	// xy,width,height
		super("background2.png",xy,wh,room);
		num=1;
	}
}
