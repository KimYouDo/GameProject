package Frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Main.Project;
import Frame.MissionStory.VisibleFalse;
import Listener.GotoPanel;

@SuppressWarnings("serial")
public class MainMenu extends JPanel {	//메인메뉴
	JButton button;	//버튼들 정의
	int x,y;	//버튼 위치지정
	public MainMenu(GameFrame gameFrame) {	//생성자
		x=(Project.windowSize.x-200)/2;
		y= 200;
		
		button = new JButton("스토리 모드");
		button.setBounds(x, y+40, 200, 40);
		button.addMouseListener(new GotoPanel(gameFrame,"storyMode"));
		//button.setBackground(Color.green);
		//button.setOpaque(false);//투명하게
		add(button);

		button = new JButton("무한 모드");
		button.setBounds(x, y+100, 200, 40);
		add(button);
		
		button = new JButton("상 점");
		button.setBounds(x, y+160, 200, 40);
		add(button);
		
		button = new JButton("옵 션");
		button.setBounds(x, y+220, 200, 40);
		add(button);

		button = new JButton("종 료");
		button.setBounds(x, y+280, 200, 40);
		button.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent arg0) {
			System.exit(0);
		}});
		add(button);
		
		button = new JButton("맵 구성 관리자");
		button.setBounds(10, 10, 200, 40);
		button.addMouseListener(new GotoPanel(gameFrame,"roomCreate"));
		button.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent arg0) {
				gameFrame.roomCreate.roomModify();
		}});
		add(button);
		setLayout(null);
	}
	
	public void paint(Graphics g){
		Image img = Toolkit.getDefaultToolkit().getImage("MainMenuImage.gif"); //이미지 불러오기
		g.drawImage(img, 0, 0, 1280, 800,this); //이미지 입력
		 setOpaque(false);//투명하게
		 super.paint(g);  
	} 

}
