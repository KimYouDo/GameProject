package Frame;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Block.Block1;
import Listener.Creator;
import Listener.MyListener;
import Main.Project;
import Object.BackGround;
import Object.Block;
import Object.Bullet;
import Object.Bullet2;
import Object.Magic;
import Object.Monster;
import Object.MoveObject;
import Object.Player;
import Monster.*;

@SuppressWarnings("serial")
public class StoryRoom extends JPanel implements Runnable {
	public Player player; // 플레이어
	public Monster monster; // 몬스터
	BackGround backGround;
	Block block; // 벽
	public Creator creator;
	int clearNum;
	int MonsterNum;
	MissionStory missionStory;
	public LinkedList<Monster> monsterList; // 몬스터 리스트
	public LinkedList<Bullet> bulletList; // 총알(공격판정 모션들) 리스트
	public LinkedList<BackGround> backGroundList;
	public LinkedList<Bullet2> bulletList2; // 총알(공격판정 모션들) 리스트
	public LinkedList<Block> blockList; // 블럭 리스트
	public int difficulty; // 어려움모드,레벨
	public int stage;
	public boolean stop; // parse를 눌렀을때 모든 동작을 멈출 수 있게 해주는 불리안
	public int step; // 스레드 속도(낮을수록 고성능)
	Thread thread; // 스레드
	public StoryRoom(GameFrame gameFrame) { // 생성자
		this();
		add(new RoomInterface(gameFrame));

	}

	public StoryRoom() { // 초기화
		step = 17; // 현재 최적설정(내컴퓨터)
		setLayout(null); // 레이아웃을 null로 해줘야 맘대로 배치가 가능
		monsterList = new LinkedList<Monster>();
		bulletList = new LinkedList<Bullet>();
		bulletList2 = new LinkedList<Bullet2>();
		blockList = new LinkedList<Block>();
		backGroundList =new LinkedList<BackGround>();
		stop = true; // 시작할때는 움직여야 하므로 - 준비 시작을 만들땐 true를 썼다가 준비 시작 끝 한다음 false로 변경해주기
		thread = new Thread(this);
		thread.start(); // 한번만 호출
		creator = new Creator(this);
		addKeyListener(new MyListener(this));
		addMouseMotionListener(new MyListener(this));
		addMouseListener(new MyListener(this));
	}

	public void run() { // 각 오브젝트의 step처리 스레드
		while (true) {
			if (!stop) {
				player.step();
				try {
					for (Monster monster : monsterList)
						monster.step(); // 몬스터 step
					for (Bullet bullet : bulletList)
						bullet.step(); // 총알 step
					for (Bullet2 bullet2 : bulletList2)
						bullet2.step(); // 총알 step
				} catch (ConcurrentModificationException e) {
					// 중간에 제거되는 오브젝트때문에 일어나는 오류 그러니 그냥 쿨하게 무시하자
				}
				if (isClearStage()) { // 클리어 조건!
					clearStage();
				}
			}
			try {
				if (step <= 0)
					break;
				Thread.sleep(step); // Step-반복의 시간을 결정 숫자가 작을수록 고성능 요구
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	void clearStage() {
		// 여기에 스테이지 이동 메뉴창을 띄울 예정(메인,스테이지선택,다음스테이지,재도전 등 넣을 예정//클리어타임 등도 넣을거임)
		// 조건 달성시 스테이지 +1로 이동
		int choiceNum=0;                   // 알아서 보세요.
			String[] choices = {"다음 라운드","이전 라운드", "종료"};
			choiceNum= JOptionPane.showOptionDialog(null,
					"미션에 성공하셨습니다.","미션성공",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
			switch (choiceNum) {
			case 0:
				Initialization(difficulty, stage+=1);
				break;

			case 1:
				if(stage!=1)
				Initialization(difficulty, stage-=1);
				break;

			default:
				System.exit(0);
				break;
			}	
		
	}

	private boolean isClearStage() { // 스테이지 클리어
		// 스테이지별로 클리어 조건을 다르게 설정할 경우 여기서 판단할 수 있음
		// 현재는 몬스터가 전부 사라졌을시 클리어
		if (monsterList.isEmpty()&&clearNum==1)
			return true;
		if (clearNum==2){
			Iterator<?> it; //피격판정을 위한 반복자
			Monster other;	//피격대상
			it=monsterList.iterator();
			while(it.hasNext()){
				other=(Monster) it.next();
				if(other.getClass().toString().equals("class Monster.Monster"+MonsterNum+"")){
					return false;
				}
			}
			return true;
		}
		if (clearNum==3){
			return true;
		}
		else
			return false;
	}

	public void Initialization(String difficulty, String stage) { // 난이도를 숫자로 변환후 초기화 호출
		int diff;
		if (difficulty.equals("normal"))
			diff = 0;
		else
			diff = 1;
		Initialization(diff, Integer.parseInt(stage));
	}

	public void Initialization(int difficulty, int stage) { // 초기화
		stop = true;
		this.stage = stage;
		this.difficulty = difficulty;
		removeAllObject();
		stageInit(stage);
		repaint();
		//stop = false;
		requestFocus();
		if (thread.getState() == Thread.State.NEW) // 스레드가 시작한적이 없다면
			thread.start(); // 한번만 호출
	}

	public void stageInit(int stage) { // 스테이지 초기화(오브젝트 생성)
		String line;
		String mission = "";
		StringTokenizer st;
		int x, y, width, height, num;
		boolean scanFinish = false;
		
		try {
			Scanner scan = new Scanner(new File("stage.txt"));
			while (scan.hasNextLine()) {
				line = scan.nextLine();

				if (line.charAt(0) == 'S') // 스테이지 번호
					if (Integer.parseInt(line.split(" ")[1]) == stage)
						break;
			}
			while (scan.hasNextLine() && !scanFinish) {
				line = scan.nextLine();
				st = new StringTokenizer(line);
				switch (st.nextToken()) {
				case "S":
					scanFinish = true;
					break;
				case "P": // 플레이어 위치
					x = Integer.parseInt(st.nextToken());
					y = Integer.parseInt(st.nextToken());
					num = Integer.parseInt(st.nextToken());
					player = new Player(x, y, num,this);
					add(player);
					missionStory=new MissionStory(this);
					missionStory.setVisible(true);
					add(missionStory);
					break;
				case "B": // 벽 종류,위치와 크기
					num = Integer.parseInt(st.nextToken());
					while (st.hasMoreTokens()) {
						x = Integer.parseInt(st.nextToken());
						y = Integer.parseInt(st.nextToken());
						width = Integer.parseInt(st.nextToken());
						height = Integer.parseInt(st.nextToken());
						block = creator.getBlock(num, new Point(x, y), new Point(width, height));
						blockList.add(block);
						add(block);
					}
					break;
				case "M":// 몬스터 종류,위치
					num = Integer.parseInt(st.nextToken());

					while (st.hasMoreTokens()) {
						x = Integer.parseInt(st.nextToken());
						y = Integer.parseInt(st.nextToken());
						monster = creator.getMonster(num, new Point(x, y));
						monsterList.add(monster);
						add(monster);
					}
					break;
				case "ground":
					num = Integer.parseInt(st.nextToken());
						x = Integer.parseInt(st.nextToken());
						y = Integer.parseInt(st.nextToken());
						width = Integer.parseInt(st.nextToken());
						height = Integer.parseInt(st.nextToken());
						backGround = creator.getBackGround(num, new Point(x, y), new Point(width, height));
						backGroundList.add(backGround);
						add(backGround);
					
					break;
				case "L":
					num = Integer.parseInt(st.nextToken());
					for(int i=0; i<num; i++){
						missionStory.mission.add(scan.nextLine());
					}
					break;
				case "clearNum":
					clearNum = Integer.parseInt(st.nextToken());
					if(clearNum==2)
						MonsterNum= Integer.parseInt(st.nextToken());
					break;
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void removeAllObject() { // 모든 객체(플레이어,몬스터) 제거
		if (player != null) {
			player.remove();
			player=null;
		}
		while (!backGroundList.isEmpty())
			backGroundList.pop().remove();
		while (!monsterList.isEmpty())
			monsterList.pop().remove();
		while (!bulletList.isEmpty())
			bulletList.pop().remove();
		while (!bulletList2.isEmpty())
			bulletList2.pop().remove();
		while (!blockList.isEmpty()) {
			Block block = blockList.pop();
			this.remove(block);
			blockList.remove(block);
		}
	}
}