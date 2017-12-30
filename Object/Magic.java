package Object;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

import Frame.StoryRoom;
import Object.Player.Motion;

public class Magic {
	public Magic(){
		
	}
	static String longName="";
	static Iterator<?> it; //�ǰ������� ���� �ݺ���
	static Monster other;	//�ǰݴ��
	
	public static void name(char c, StoryRoom room0){
		StoryRoom room=room0;	//���� â
		if(c=='/'){
			magic(longName,room);
			System.out.println("a  "+longName);
			longName="";
		}
		else
		longName+=c;		
	}
	
	public static void magic(String longName, StoryRoom room){
		String name="";
		Bullet bullet = null;	//�Ѿ�

		Scanner scan = null;
		try {
			scan = new Scanner(new File("magic.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(scan.hasNextLine()){
			if(scan.nextLine().equals(longName))
				name=scan.nextLine();
		}

		if(name.equals("magic0")){
			int X = room.player.getPoint().x;
			int Y = room.player.getPoint().y;
			bullet = new BulletOfCharacter("bullet0-1.gif",80,new Point((int)X,(int)Y),room.player.getmouse() , room);
			room.add(bullet);
			room.bulletList.add(bullet);

		}
		
		if(name.equals("magic1")){
			int X = room.player.getPoint().x;
			int Y = room.player.getPoint().y;
			bullet = new BulletOfCharacter("bullet0-2.png",80,new Point((int)X,(int)Y),room.player.getmouse() , room);
			room.add(bullet);
			room.bulletList.add(bullet);

		}
		
		if(name.equals("magic2")){
			it=room.monsterList.iterator();
			while(it.hasNext()){
			other= (Monster) it.next();
			int X = other.getPoint().x;
			int Y = other.getPoint().y;
			Bullet2 bullet2 = new BulltOfMagic("bullet3.gif",60,new Point((int)X,(int)Y-150),new Point((int)X,(int)Y),0.4f, room);
			room.add(bullet2);
			room.bulletList2.add(bullet2);
			}

		}

	}

}