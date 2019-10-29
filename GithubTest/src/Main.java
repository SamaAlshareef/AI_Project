import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Main {

	
	
	 static void Solve(String grid, GeneralSearchProblem.QingFunc q) {
    	 EndGameProblem endGame =  new EndGameProblem(grid);
    	 
    	 endGame.GenericSearch(endGame, q);
     }
	 
	 
	 
	 
	 
	 
	 
	public static void main (String args[]) {
	
		long startTime = System.nanoTime();
		
		
		
		//Solve("5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3",GenericSearchProblem.QingFunc.BFS);
		Solve("13,13;4,2;2,4;6,1,1,10,8,4,9,2,2,8,9,4;6,4,3,4,3,11,1,12,1,9",GeneralSearchProblem.QingFunc.Astar);
		
		//Solve("12,12;0,6;9,11;8,3,3,0,11,8,7,4,7,7,10,2;2,8,11,2,2,6,4,6,9,8,11,7",GenericSearchProblem.QingFunc.Astar);
		
		
		
		
		//Solve("15,15;12,13;5,7;7,0,9,14,14,8,5,8,8,9,8,4;6,6,4,3,10,2,7,4,3,11",GenericSearchProblem.QingFunc.Astar);
		
		
		
		//5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3_DOWN(1) , RIGHT(1) , DOWN(4) , KILL(10) , DOWN(10) , LEFT(10) , LEFT(15) , COLLECT(23) , RIGHT(23) , RIGHT(23) , RIGHT(23) , UP(23) , UP(23) , UP(23) , UP(24) , KILL(26) , DOWN(26) , DOWN(26) , DOWN(26) , DOWN(26) , LEFT(26) , LEFT(26) , UP(31) , UP(31) , UP(31) , UP(31) , COLLECT(34) , DOWN(34) , RIGHT(34) , DOWN(34) , DOWN(34) , DOWN(34) , LEFT(34) , LEFT(39) , LEFT(40) , COLLECT(44) , RIGHT(49) , RIGHT(49) , RIGHT(49) , RIGHT(49) , UP(49) , UP(49) , UP(49) , UP(49) , LEFT(49) , LEFT(49) , DOWN(49) , DOWN(49) , COLLECT(52) , DOWN(57) , DOWN(57) , RIGHT(57) , RIGHT(57) , UP(57) , UP(57) , UP(57) , UP(57) , LEFT(57) , LEFT(57) , LEFT(57) , DOWN(57) , COLLECT(60) , DOWN(65) , COLLECT(73) , DOWN(74)		
		//5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3_LEFT(0) , COLLECT(3) , DOWN(8) , COLLECT(16) , LEFT(17) , KILL(19) , DOWN(24) , DOWN(24) , COLLECT(27) , RIGHT(32) , COLLECT(40) , RIGHT(42) , KILL(46) , UP(51) , UP(51) , COLLECT(54) , UP(54) , UP(55) , COLLECT(59) , LEFT(59) , DOWN(59) , DOWN(64) , DOWN(64)
		//15,15;2,7;3,1;14,14,4,0,0,14,4,14,4,4,0,4;0,3,3,0,3,2,3,4,4,3_LEFT(0) , LEFT(0) , DOWN(1) , DOWN(1) , LEFT(3) , COLLECT(8) , KILL(12) , LEFT(12) , LEFT(13) , LEFT(18) , LEFT(19) , COLLECT(23) , RIGHT(28) , RIGHT(29) , RIGHT(29) , UP(30) , UP(30) , UP(31) , RIGHT(31) , UP(32) , COLLECT(36) , RIGHT(36) , RIGHT(36) , RIGHT(36) , RIGHT(36) , RIGHT(36) , RIGHT(36) , RIGHT(36) , RIGHT(36) , RIGHT(36) , RIGHT(36) , COLLECT(39) , DOWN(39) , DOWN(39) , DOWN(39) , DOWN(39) , COLLECT(42) , DOWN(42) , DOWN(42) , DOWN(42) , DOWN(42) , DOWN(42) , DOWN(42) , DOWN(42) , DOWN(42) , DOWN(42) , DOWN(42) , COLLECT(45) , LEFT(45) , LEFT(45) , LEFT(45) , LEFT(45) , LEFT(45) , LEFT(45) , LEFT(45) , LEFT(45) , LEFT(45) , LEFT(45) , LEFT(45) , LEFT(45) , LEFT(45) , UP(45) , UP(45) , UP(45) , UP(45) , UP(45) , UP(45) , UP(45) , UP(45) , UP(45) , UP(50) , UP(52)
		
		
		long endTime = System.nanoTime();

		long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
		
		System.out.println("Execution Time is: " + (duration/1000000));
		
		
		
		
//		HashMap<Point,String> CollectedStones = new HashMap<Point,String>();
//		HashMap<Point,String> CollectesWarriors = new HashMap<Point,String>();
//		
//		
//		
//		
//		CollectedStones.put(new Point(4, 4), "s1");
//		CollectedStones.put(new Point(4, 2), "s2");
//		
//		
//		CollectesWarriors.put(new Point(3, 4), "w1");
//		CollectesWarriors.put(new Point(3, 2), "w2");
//		CollectesWarriors.put(new Point(1, 2), "w3");
//		
//		
//		int StonesHash = CollectedStones.hashCode();
//		int WarriorsHash = CollectesWarriors.hashCode();
//		HashMap<Point,String> CollectedStones2 = new HashMap<Point,String>();
//		HashMap<Point,String> CollectesWarriors2 = new HashMap<Point,String>();
//		
//		CollectedStones2.put(new Point(4, 4), "s1");
//		CollectedStones2.put(new Point(4, 2), "s2");
//		
//		
//		CollectesWarriors2.put(new Point(3, 4), "w1");
//		CollectesWarriors2.put(new Point(3, 2), "w2");
//		CollectesWarriors2.put(new Point(1, 2), "w3");
//		
//		int StonesHash2 = CollectedStones2.hashCode();
//		int WarriorsHash2 = CollectesWarriors.hashCode();
//		
//		
//		
//		Point p = new Point(4, 4);
//		
//		EndGameState NewState = new EndGameState(p,0,CollectedStones,CollectesWarriors);
//		int NewStateHashCode = NewState.hashCode();
//		
//		//p = new Point(1,1);
//		EndGameState State = new EndGameState(p,0,CollectedStones2,CollectesWarriors2);
//		
//		int SecondHashCode = State.hashCode();
//		
//		
//		
//		System.out.println("First HashCode: " + p.x+p.y+StonesHash+WarriorsHash);
//		System.out.println("Second HashCode: " + p.x+p.y+StonesHash2+WarriorsHash2);
		
		
		
//		String path = "C:\\Omar\\University\\Semester9\\AI\\AI_Project\\Visualizer\\EndGameVisualization.exe";
//		File file = new File(path);
//		if (! file.exists()) {
//		   throw new IllegalArgumentException("The file " + path + " does not exist");
//		}
//		try {
//			Process p = Runtime.getRuntime().exec(file.getAbsolutePath());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
	}
	
}
