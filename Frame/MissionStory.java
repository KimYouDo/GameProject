package Frame;


import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;

import Frame.StopMenu.VisibleFalse;
import Frame.StoryMode.StageAction;
import Listener.GotoPanel;
import Main.Project;

@SuppressWarnings("serial")
public class MissionStory extends Container {	//정지 메뉴
	JButton button;
	int x, y;
	StoryRoom room;
	JLabel label;
	ArrayList<String> mission;
	MissionStory(StoryRoom storyRoom) {	//생성자
		this.room=storyRoom;
		mission=new ArrayList<String>();
		x = (Project.windowSize.x - 200) / 2;
		y = 200;
		
		setSize(Project.windowSize.x, Project.windowSize.y); // 창 크기
		setLayout(null);
	}

	class VisibleFalse  extends MouseAdapter {
		@SuppressWarnings("deprecation")
		public void mouseReleased(MouseEvent e) {
				setVisible(false);
				room.stop = false;
				room.requestFocus();			
		}
	}

	public void paint(Graphics g){
		Image img = Toolkit.getDefaultToolkit().getImage("mission2.png"); //이미지 불러오기
		g.drawImage(img, 450, 120, 350, 500,this); //이미지 입력
		for(int i=0; i<mission.size(); i++){
			if(i==0){
				g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
				g.setColor(Color.RED);
				g.drawString(mission.get(i).toString(), 500, 230); 
			}
			else{
				g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
				g.setColor(Color.BLACK);
				g.drawString(mission.get(i).toString(), 500, 250+(i*20)); 
			}
		}
		label = new JLabel();
		Project.setLabelImage(label, "stage.png");
		label.addMouseListener(new VisibleFalse());
		label.setLocation(705,470);
		add(label);  
	} 

}
