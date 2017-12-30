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
	public Player player; // �÷��̾�
	public Monster monster; // ����
	BackGround backGround;
	Block block; // ��
	public Creator creator;
	int clearNum;
	int MonsterNum;
	MissionStory missionStory;
	public LinkedList<Monster> monsterList; // ���� ����Ʈ
	public LinkedList<Bullet> bulletList; // �Ѿ�(�������� ��ǵ�) ����Ʈ
	public LinkedList<BackGround> backGroundList;
	public LinkedList<Bullet2> bulletList2; // �Ѿ�(�������� ��ǵ�) ����Ʈ
	public LinkedList<Block> blockList; // ���� ����Ʈ
	public int difficulty; // �������,����
	public int stage;
	public boolean stop; // parse�� �������� ��� ������ ���� �� �ְ� ���ִ� �Ҹ���
	public int step; // ������ �ӵ�(�������� ������)
	Thread thread; // ������
	public StoryRoom(GameFrame gameFrame) { // ������
		this();
		add(new RoomInterface(gameFrame));

	}

	public StoryRoom() { // �ʱ�ȭ
		step = 17; // ���� ��������(����ǻ��)
		setLayout(null); // ���̾ƿ��� null�� ����� ����� ��ġ�� ����
		monsterList = new LinkedList<Monster>();
		bulletList = new LinkedList<Bullet>();
		bulletList2 = new LinkedList<Bullet2>();
		blockList = new LinkedList<Block>();
		backGroundList =new LinkedList<BackGround>();
		stop = true; // �����Ҷ��� �������� �ϹǷ� - �غ� ������ ���鶩 true�� ��ٰ� �غ� ���� �� �Ѵ��� false�� �������ֱ�
		thread = new Thread(this);
		thread.start(); // �ѹ��� ȣ��
		creator = new Creator(this);
		addKeyListener(new MyListener(this));
		addMouseMotionListener(new MyListener(this));
		addMouseListener(new MyListener(this));
	}

	public void run() { // �� ������Ʈ�� stepó�� ������
		while (true) {
			if (!stop) {
				player.step();
				try {
					for (Monster monster : monsterList)
						monster.step(); // ���� step
					for (Bullet bullet : bulletList)
						bullet.step(); // �Ѿ� step
					for (Bullet2 bullet2 : bulletList2)
						bullet2.step(); // �Ѿ� step
				} catch (ConcurrentModificationException e) {
					// �߰��� ���ŵǴ� ������Ʈ������ �Ͼ�� ���� �׷��� �׳� ���ϰ� ��������
				}
				if (isClearStage()) { // Ŭ���� ����!
					clearStage();
				}
			}
			try {
				if (step <= 0)
					break;
				Thread.sleep(step); // Step-�ݺ��� �ð��� ���� ���ڰ� �������� ������ �䱸
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	void clearStage() {
		// ���⿡ �������� �̵� �޴�â�� ��� ����(����,������������,������������,�絵�� �� ���� ����//Ŭ����Ÿ�� � ��������)
		// ���� �޼��� �������� +1�� �̵�
		int choiceNum=0;                   // �˾Ƽ� ������.
			String[] choices = {"���� ����","���� ����", "����"};
			choiceNum= JOptionPane.showOptionDialog(null,
					"�̼ǿ� �����ϼ̽��ϴ�.","�̼Ǽ���",
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

	private boolean isClearStage() { // �������� Ŭ����
		// ������������ Ŭ���� ������ �ٸ��� ������ ��� ���⼭ �Ǵ��� �� ����
		// ����� ���Ͱ� ���� ��������� Ŭ����
		if (monsterList.isEmpty()&&clearNum==1)
			return true;
		if (clearNum==2){
			Iterator<?> it; //�ǰ������� ���� �ݺ���
			Monster other;	//�ǰݴ��
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

	public void Initialization(String difficulty, String stage) { // ���̵��� ���ڷ� ��ȯ�� �ʱ�ȭ ȣ��
		int diff;
		if (difficulty.equals("normal"))
			diff = 0;
		else
			diff = 1;
		Initialization(diff, Integer.parseInt(stage));
	}

	public void Initialization(int difficulty, int stage) { // �ʱ�ȭ
		stop = true;
		this.stage = stage;
		this.difficulty = difficulty;
		removeAllObject();
		stageInit(stage);
		repaint();
		//stop = false;
		requestFocus();
		if (thread.getState() == Thread.State.NEW) // �����尡 ���������� ���ٸ�
			thread.start(); // �ѹ��� ȣ��
	}

	public void stageInit(int stage) { // �������� �ʱ�ȭ(������Ʈ ����)
		String line;
		String mission = "";
		StringTokenizer st;
		int x, y, width, height, num;
		boolean scanFinish = false;
		
		try {
			Scanner scan = new Scanner(new File("stage.txt"));
			while (scan.hasNextLine()) {
				line = scan.nextLine();

				if (line.charAt(0) == 'S') // �������� ��ȣ
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
				case "P": // �÷��̾� ��ġ
					x = Integer.parseInt(st.nextToken());
					y = Integer.parseInt(st.nextToken());
					num = Integer.parseInt(st.nextToken());
					player = new Player(x, y, num,this);
					add(player);
					missionStory=new MissionStory(this);
					missionStory.setVisible(true);
					add(missionStory);
					break;
				case "B": // �� ����,��ġ�� ũ��
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
				case "M":// ���� ����,��ġ
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

	public void removeAllObject() { // ��� ��ü(�÷��̾�,����) ����
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