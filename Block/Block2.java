package Block;

import java.awt.Point;

import Frame.StoryRoom;
import Object.Block;

public class Block2 extends Block {
	public Block2(Point xy,Point wh,StoryRoom room){	// xy,width,height
		super("box1.png",xy,wh,room);
		num=1;
	}
}
