package Object;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Frame.StoryRoom;
import Main.Project;

@SuppressWarnings("serial")
public class Block extends BaseObject {	//º®
	public int num;
	public Block(String name, Point xy,Point wh,StoryRoom room){
		super(room);
		this.x=xy.x;
		this.y=xy.y;
		setImage(name);
		//Project.setLabelImage(this, "box.png");
		setLocation((int)x,(int)y);
		setSize(wh.x,wh.y);
		this.width=this.getWidth();
		this.height=this.getHeight();
		setOrigin();
		num=0;
	}
}
