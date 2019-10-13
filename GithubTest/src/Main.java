

public class Main {

	
	
	 static void Solve(String grid, GenericSearchProblem.QingFunc q) {
    	 EndGameProblem endGame =  new EndGameProblem(grid);
    	 
    	 endGame.GeneralSearch(endGame, q);
     }
	public static void main (String args[]) {
		
		
		
		 //Solve("2,2;0,0;1,1;0,1;1,0",QingFunc.BFS);
		Solve("5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3",GenericSearchProblem.QingFunc.BFS);
		 
	}
	
}
