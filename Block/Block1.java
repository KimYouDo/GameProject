package Block;

import java.awt.Point;

import Frame.StoryRoom;
import Object.Block;

public class Block1 extends Block {
	public Block1(Point xy,Point wh,StoryRoom room){	// xy,width,height
		super("box.png",xy,wh,room);
		num=1;
	}
}
