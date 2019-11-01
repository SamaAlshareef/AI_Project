import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Main {

	
	
	 static String solve(String grid, String q, boolean visualize) {
		 String Solution = null;
    	 EndGameProblem endGame =  new EndGameProblem(grid);
    	 
    	 
    	 Solution = endGame.GenericSearch(endGame, SwitchToEnum(q));
    	 
    	 
    	 if(visualize) 
    	 {
    			String path = System.getProperty("user.dir")+"\\Visualizer\\EndGameVisualization.exe";
    			File file = new File(path);
    			if (! file.exists()) {
    			   throw new IllegalArgumentException("The file " + path + " does not exist");
    			}
    			try {
    				Process p = Runtime.getRuntime().exec(file.getAbsolutePath());
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    	 }
    	 
    	 return Solution;
     }
	 
	  static GeneralSearchProblem.QingFunc SwitchToEnum(String func) 
	 {
		 GeneralSearchProblem.QingFunc QFunctions = null;
		 switch(func) 
		 {
		 case "BF":
			 QFunctions = GeneralSearchProblem.QingFunc.BF;break;
		 case "DF":
			 QFunctions = GeneralSearchProblem.QingFunc.DF;break;
		 case "ID":
			 QFunctions = GeneralSearchProblem.QingFunc.ID;break;
		 case "UC":
			 QFunctions = GeneralSearchProblem.QingFunc.UC;break;
		 case "AS1":
			 QFunctions = GeneralSearchProblem.QingFunc.AS1;break;
		 case "AS2":
			 QFunctions = GeneralSearchProblem.QingFunc.AS2;break;
		 case "GR1":
			 QFunctions = GeneralSearchProblem.QingFunc.GR1;break;
		 case "GR2":
			 QFunctions = GeneralSearchProblem.QingFunc.GR2;break;
		 }
		 return QFunctions;
	 }
	 
	 
	 
	 
	 
	public static void main (String args[]) {
	
		long startTime = System.nanoTime();
		
		String grid15 = "15,15;12,13;5,7;7,0,9,14,14,8,5,8,8,9,8,4;6,6,4,3,10,2,7,4,3,11";
		String grid8 = "8,8;7,2;2,2;7,6,2,3,3,0,0,1,6,0,5,5;7,3,4,4,1,6,2,4,2,6";
		String grid5 = "5,5;2,2;4,2;4,0,1,2,3,0,2,1,4,1,2,4;3,2,0,0,3,4,4,3,4,4";
		solve(grid5,"BF",true);
//		solve(grid15,"DF",false);
//		solve(grid15,"ID",false);
//		solve(grid15,"UC",false);
//		solve(grid8,"GR1",false);
//		solve(grid8,"AS1",true);
//		solve(grid15,"GR2",false);
//		solve(grid15,"AS2",false);
		
		
		
		//solve("13,13;4,2;2,4;6,1,1,10,8,4,9,2,2,8,9,4;6,4,3,4,3,11,1,12,1,9","UC",false);
		
		//solve("12,12;0,6;9,11;8,3,3,0,11,8,7,4,7,7,10,2;2,8,11,2,2,6,4,6,9,8,11,7","AS2",false);
		
		//solve("11,11;9,5;7,1;9,0,8,8,9,1,8,4,2,3,9,10;2,0,0,10,6,3,10,6,6,2","AS2",false);
		
		
		//solve("15,15;12,13;5,7;7,0,9,14,14,8,5,8,8,9,8,4;6,6,4,3,10,2,7,4,3,11","UC",false);
		
		long endTime = System.nanoTime();

		long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
		
		System.out.println("Execution Time is: " + (duration/1000000));
		
		
		
		

		
		
	
		
	}
	
}
