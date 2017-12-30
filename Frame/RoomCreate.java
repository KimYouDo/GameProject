package Frame;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;

import Block.Block1;
import Listener.DisposeListener;
import Listener.GotoPanel;
import Main.Project;
import Modify.ObjectSel;
import Modify.StageSel;
import Object.Block;
import Object.Monster;
import Object.Player;

@SuppressWarnings("serial")
public class RoomCreate extends StoryRoom {
	GameFrame gameFrame;
	public ObjectSel objectSel;
	public StageSel stageSel;
	private JButton back;

	RoomCreate(GameFrame gameFrame) {
		super();
		objectSel = new ObjectSel(this);
		stageSel = new StageSel(this);
		objectSel.setVisible(false);
		stageSel.setVisible(false);
		addMouseListener(new DisposeListener(this));
		addMouseMotionListener(new DisposeListener(this));
		this.gameFrame = gameFrame;
		back = new JButton("back");
		back.addMouseListener(new GotoPanel(gameFrame, "mainMenu"));
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				objectSel.setVisible(false);
				stageSel.setVisible(false);
		}});
		back.setBounds(15, 15, 100, 50);
		add(back);
	}

	void roomModify() {
		objectSel.setVisible(true);
		stageSel.setVisible(true);
		repaint();
	}

	public void addObject(int object, Point point) {
		// TODO Auto-generated method stub

	}

	public void removeObject(Point point) {
		// TODO Auto-generated method stub

	}

	public void addMonster(int kind, Point point) {
		// TODO Auto-generated method stub
		monster = creator.getMonster(kind, point);
		monsterList.add(monster);
		add(monster);
	}

	public void addBlock(int kind, Point xy, Point wh) {
		if (wh.x != 0 && wh.y != 0) {
			block = creator.getBlock(kind, xy, wh);
			blockList.add(block);
			add(block);
		}
	}
	
}
