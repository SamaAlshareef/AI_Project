import java.awt.Point;
import java.util.HashMap;



public class EndGameState extends State{
	
	public Point IronManPos;
	public int ReceivedDamage;
	public HashMap<Point,String> CollectedStones;
	public HashMap<Point,String> CollectesWarriors;
	
	
	
	public EndGameState(Point p) {
		IronManPos = p;
		ReceivedDamage = 0;
		CollectedStones = new HashMap<Point,String>();
		CollectesWarriors = new HashMap<Point,String>();
	}
	
	
	
	public EndGameState(Point p, int damage , HashMap<Point, String> stones, HashMap<Point, String> warriors) {
		IronManPos = p;
		ReceivedDamage = damage;
		CollectedStones = stones;
		CollectesWarriors = warriors;
	}
	
	

	
}
