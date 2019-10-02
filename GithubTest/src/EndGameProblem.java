import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import javax.annotation.Generated;

public class EndGameProblem extends GenericSearchProblem{

	public enum Actions
	{
		UP,
		RIGHT,
		LEFT,
		DOWN,
		KILL,
		COLLECT,
		SNAP
	}
	
	public enum QingFunc
	{
		BFS,
		DFS,
		UC,
		Astar,
		Greedy,
		IDFS
	}
	
	private static int GridWidth;
	private static int GridHeight;
	private static EndGameState InitialState;
	private int WarriorsCount = 0;
	private static HashMap<Point, String> Warriors;
	private static HashMap<Point, String> Stones;
	private static Point ThanosPos;
	private static HashMap<Actions, Boolean> ActionsMap;
	private static Node CurrentNode;
	private static EndGameState CurrentState;
	private static Queue<Node> nodes;
	
	public EndGameProblem(String grid) {
		Warriors = new HashMap<Point, String>();
		Stones = new HashMap<Point, String>();
		ActionsMap = new HashMap<Actions, Boolean>();
		InstantiateActionMap();
		String tempGrid = grid.substring(0, 4);
		 GridWidth = Character.getNumericValue(tempGrid.charAt(0));
		 GridHeight = Character.getNumericValue(tempGrid.charAt(2));
		 grid = grid.substring(4, grid.length());
		 
		 //Set Ironman position
		 tempGrid = grid.substring(0, 4);
		 Point IronManPosition = new Point(Character.getNumericValue(tempGrid.charAt(0)),Character.getNumericValue(tempGrid.charAt(2)));
		 InitialState = new EndGameState(IronManPosition);
		 grid = grid.substring(4, grid.length());
		 
		 //Set Thanos position
		 tempGrid = grid.substring(0, 4);
		 ThanosPos = new Point(Character.getNumericValue(tempGrid.charAt(0)),Character.getNumericValue(tempGrid.charAt(2)));
		 grid = grid.substring(4, grid.length());
		 
		 //Set Stones positions
		 for(int i=0; i<6; i++) 
		 {
			 tempGrid = grid.substring(0, 4);
			 Point tmpPoint = new Point(Character.getNumericValue(tempGrid.charAt(0)),Character.getNumericValue(tempGrid.charAt(2)));
			 Stones.put(tmpPoint, "s"+(i+1));
			 grid = grid.substring(4, grid.length());
			 
		 }
		 
		//Set Warriors positions
		 while(grid.length()>1)
		 {
			
			 if(grid.length() == 3) 
			 {
				 tempGrid = grid.substring(0, 3);
				 Point tmpPoint2 = new Point(Character.getNumericValue(tempGrid.charAt(0)),Character.getNumericValue(tempGrid.charAt(2)));
				 Warriors.put(tmpPoint2, "w"+(WarriorsCount+1));
				 WarriorsCount++;
				 break;
			 }
			 else 
			 {
				 tempGrid = grid.substring(0, 4);
				 Point tmpPoint = new Point(Character.getNumericValue(tempGrid.charAt(0)),Character.getNumericValue(tempGrid.charAt(2)));
				 Warriors.put(tmpPoint, "w"+(WarriorsCount+1));
				 WarriorsCount++;
				 grid = grid.substring(4, grid.length());
			 }
		 }
		 
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
  
	//EXPAND FUNCTION HELPERS
	void CheckMovement() {
		Point cell = new Point();
		
		//Check UP
		cell = GetCell(CurrentState.IronManPos,Actions.UP);
		if(CheckBorder(cell)) {
			ActionsMap.replace(Actions.UP, false);
		}
		
		
		//Check Right
		cell = GetCell(CurrentState.IronManPos,Actions.RIGHT);
		if(CheckBorder(cell)) {
			ActionsMap.replace(Actions.RIGHT, false);
		}
		
		//Check Left
		cell = GetCell(CurrentState.IronManPos,Actions.LEFT);
		if(CheckBorder(cell)) {
			ActionsMap.replace(Actions.LEFT, false);
		}
		
		//Check Down
		cell = GetCell(CurrentState.IronManPos,Actions.DOWN);
		if(CheckBorder(cell)) {
			ActionsMap.replace(Actions.DOWN, false);
		}
	}
	
	
	void CheckWarriors(){
		Point cell = new Point();
		Boolean WarriorsClear = true;
		
		//Check UP
		cell = GetCell(CurrentState.IronManPos,Actions.UP);
		if(Warriors.get(cell) != null) {
			ActionsMap.replace(Actions.UP, false);
			WarriorsClear = false;
		}
		
		//Check Right
		cell = GetCell(CurrentState.IronManPos,Actions.RIGHT);
		if(Warriors.get(cell) != null) {
			ActionsMap.replace(Actions.RIGHT, false);
			WarriorsClear = false;
		}
		
		//Check Left
		cell = GetCell(CurrentState.IronManPos,Actions.LEFT);
		if(Warriors.get(cell) != null) {
			ActionsMap.replace(Actions.LEFT, false);
			WarriorsClear = false;
		}
		
		//Check Down
		cell = GetCell(CurrentState.IronManPos,Actions.DOWN);
		if(Warriors.get(cell) != null) {
			ActionsMap.replace(Actions.DOWN, false);
			WarriorsClear = false;
		}
		
		if(WarriorsClear) {
			ActionsMap.replace(Actions.KILL, false);
		}
		
	}
	
	void CheckThanos() {
		
		Point cell = new Point();
		
		//Check Up
		cell = GetCell(CurrentState.IronManPos, Actions.UP);
		
		if((CurrentState.ReceivedDamage > 100) || (CurrentState.CollectedStones.size() != 6)) 
		{
			if (cell == ThanosPos) 
			{
				ActionsMap.replace(Actions.UP, false);
			}
			
			//Check Right
			cell = GetCell(CurrentState.IronManPos, Actions.RIGHT);
			if (cell == ThanosPos) 
			{
				ActionsMap.replace(Actions.RIGHT, false);
			}
			
			//Check Left
			cell = GetCell(CurrentState.IronManPos, Actions.LEFT);
			if (cell == ThanosPos) 
			{
				ActionsMap.replace(Actions.LEFT, false);
			}
			
			//Check Down
			cell = GetCell(CurrentState.IronManPos, Actions.DOWN);
			if (cell == ThanosPos) 
			{
				ActionsMap.replace(Actions.DOWN, false);
			}
		}
		
		
	}
	
	
	void CheckStones() 
	{
		 Point cell = new Point();
		 
		 //Check Stone in the current cell
		 cell = CurrentState.IronManPos;
		 if(Stones.get(cell) == null) 
		 {
			 ActionsMap.replace(Actions.COLLECT,false);
		 }
     
	}
	
	void CheckSnap() 
	{
		if (CurrentState.IronManPos != ThanosPos)
		{
			ActionsMap.replace(Actions.SNAP, false);
		}
	}
	
	void ResetActionsMap() 
	{
		 for (Map.Entry<Actions,Boolean> entry : ActionsMap.entrySet()) 
		 { 
	         entry.setValue(true);
		 }
	}
	
	
	void InstantiateActionMap() {
		ActionsMap.put(Actions.UP, true);
		ActionsMap.put(Actions.RIGHT, true);
		ActionsMap.put(Actions.LEFT, true);
		ActionsMap.put(Actions.DOWN, true);
		ActionsMap.put(Actions.KILL, true);
		ActionsMap.put(Actions.COLLECT, true);
		ActionsMap.put(Actions.SNAP, true);
	}
	//Expand Function
	HashMap<Actions,Boolean> Expand() 
	{
		CheckMovement();
		CheckWarriors();
		CheckStones();
		CheckThanos();
		CheckSnap();
		
		HashMap<Actions,Boolean> tempActionsMap = ActionsMap;
		ResetActionsMap();
		
		return tempActionsMap;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Transition Functions	
	//Move Up Transition Function
	EndGameState MoveUpTransitionFunction(EndGameState state) 
	{
		Point NewPosition = new Point(state.IronManPos.x, state.IronManPos.y + 1);
		int DealtDamage = CalculateAllDamage(NewPosition);
		EndGameState NewState = new EndGameState(NewPosition, state.ReceivedDamage + DealtDamage , state.CollectedStones , state.CollectesWarriors);
		
		return NewState;
	}
	
	// Move Right Transition Function
	EndGameState MoveRightTransitionFunction(EndGameState state) 
	{
		Point NewPosition = new Point(state.IronManPos.x + 1, state.IronManPos.y);
		int DealtDamage = CalculateAllDamage(NewPosition);
		EndGameState NewState = new EndGameState(NewPosition, state.ReceivedDamage + DealtDamage , state.CollectedStones , state.CollectesWarriors);
		
		return NewState;
	}
	
	//Move Left Transition Function
	EndGameState MoveLeftTransitionFunction(EndGameState state) 
	{
		Point NewPosition = new Point(state.IronManPos.x - 1, state.IronManPos.y);
		int DealtDamage = CalculateAllDamage(NewPosition);
		EndGameState NewState = new EndGameState(NewPosition, state.ReceivedDamage + DealtDamage , state.CollectedStones , state.CollectesWarriors);
		
		return NewState;
	}
	
	//Move Down Transition Function
	EndGameState MoveDownTransitionFunction(EndGameState state) 
	{
		Point NewPosition = new Point(state.IronManPos.x, state.IronManPos.y - 1);
		int DealtDamage = CalculateAllDamage(NewPosition);
		EndGameState NewState = new EndGameState(NewPosition, state.ReceivedDamage + DealtDamage , state.CollectedStones , state.CollectesWarriors);
		
		return NewState;
	}
	
	//Collect Stones Transition Function
	EndGameState CollecStonesTransitionFunction(EndGameState state) 
	{
		
		if(Stones.get(state.IronManPos) != null) 
		{
			state.ReceivedDamage += 3;
			state.CollectedStones.put(state.IronManPos , Stones.get(state.IronManPos));
			Stones.remove(state.IronManPos);
		}
		
		return state;	
	}
	
	//Killing Warriors in Adjacent Cells
	EndGameState KillTransitionFunction(EndGameState state) 
	{
		Point cell = new Point();
		
		//Check up
		cell = GetCell(state.IronManPos, Actions.UP);
		if (Warriors.get(cell) != null) 
		{
			state.ReceivedDamage += 2;
			state.CollectesWarriors.put(cell , Warriors.get(cell));
			Warriors.remove(cell);
			
		}
		
		//Check Right
		cell = GetCell(state.IronManPos, Actions.RIGHT);
		if (Warriors.get(cell) != null) 
		{
			state.ReceivedDamage += 2;
			state.CollectesWarriors.put(cell , Warriors.get(cell));
			Warriors.remove(cell);
			
		}
		
		//Check Left
		cell = GetCell(state.IronManPos, Actions.LEFT);
		if (Warriors.get(cell) != null) 
		{
			state.ReceivedDamage += 2;
			state.CollectesWarriors.put(cell , Warriors.get(cell));
			Warriors.remove(cell);
			
		}
		
		//Check Down
		cell = GetCell(state.IronManPos, Actions.DOWN);
		if (Warriors.get(cell) != null) 
		{
			state.ReceivedDamage += 2;
			state.CollectesWarriors.put(cell , Warriors.get(cell));
			Warriors.remove(cell);
			
		}
		
		return state;
		
		
	}
	
	//Snap Tranisition Function 
	EndGameState SnapTransitionFunction(EndGameState state) 
	{
		if (GoalTest(state)) 
		{
			System.out.println("You WON");
			return state;
		}
		else 
			return null;	
	}
	
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//General Search Function
	
	void GeneralSearch(EndGameProblem problem, QingFunc QF ) {
		
		nodes = new LinkedList<Node>();
		CurrentNode = new Node(InitialState, true);
		Tree tree = new Tree(CurrentNode);
		nodes.add(CurrentNode);
		
		while(!nodes.isEmpty())
		{
			CurrentNode = nodes.remove();
			CurrentState = CurrentNode.state;
			if(GoalTest(CurrentNode.state))
			{
				PrintSolution(GetPath(CurrentNode));
			}
			
			
			switch(QF) 
			{
			case BFS:
				BFS(Expand());
			
			}
			
		}
		
	}
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	//QING FUNCTIONS
	
	void BFS(HashMap<Actions, Boolean> possibleActions) 
	{
		Queue<Node> generatedNodes = new LinkedList<Node>();
		
		generatedNodes = GenerateNodes(possibleActions);
		
		while(!generatedNodes.isEmpty()) 
		{
			nodes.add(generatedNodes.remove());
		}
	}
	
	
	
	
	
	
	Queue<Node> GenerateNodes(HashMap<Actions, Boolean> possibleActions)
	{
		Queue<Node> genratedNodes = new LinkedList<Node>();
		EndGameState newState = null;
		Node childNode = null;
		for (Map.Entry<Actions,Boolean> entry : ActionsMap.entrySet()) 
		{
			if(entry.getValue()) 
			{
				newState = GenerateState(entry.getKey());
				childNode = CurrentNode.MakeChild(newState, entry.getKey());
				genratedNodes.add(childNode);
			}
			
		
		}
	        
		return genratedNodes;
		
	}
	
	
	EndGameState GenerateState(Actions action) 
	{
		EndGameState newState = null;
		switch(action)
		{
		case UP:
			newState = MoveUpTransitionFunction(CurrentState);
			break;
		case RIGHT:
			newState = MoveRightTransitionFunction(CurrentState);
			break;
		case LEFT:
			newState = MoveLeftTransitionFunction(CurrentState);
			break;
		case DOWN:
			newState = MoveDownTransitionFunction(CurrentState);
			break;
		case KILL:
			newState = KillTransitionFunction(CurrentState);
			break;
		case COLLECT:
			newState = CollecStonesTransitionFunction(CurrentState);
			break;
		case SNAP:
			newState = SnapTransitionFunction(CurrentState);
			break;
		}
		
		return newState;
	}
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	Point GetCell(Point self,Actions action) {
		Point newPoint = new Point();
		switch(action)
		{
		case UP:
			newPoint.setLocation(self.x, self.y+1);
			break;
		case RIGHT:
			newPoint.setLocation(self.x+1, self.y);
			break;
		case LEFT:
			newPoint.setLocation(self.x-1, self.y);
			break;
		case DOWN:
			newPoint.setLocation(self.x, self.y-1);
			break;
		}
		return newPoint;
	}
	
	Boolean CheckBorder(Point point) {
		if((point.x > GridWidth) || (point.y > GridHeight) || (point.x < 0) || (point.y < 0))
			return true;
		else 
			return false;
	}
	
	int CalculateAllDamage(Point cell) 
	{
		int damage = 0;
		
		cell = GetCell(CurrentState.IronManPos,Actions.UP);
		if(Warriors.get(cell) != null) 
		{
			damage += 1;
			
		}
		if (cell == ThanosPos) {
			damage += 5;
		}
		
		//Check Right
		cell = GetCell(CurrentState.IronManPos,Actions.RIGHT);
		if(Warriors.get(cell) != null) {
			damage += 1;
			
		}
		if (cell == ThanosPos) {
			damage += 5;
		}
		
		//Check Left
		cell = GetCell(CurrentState.IronManPos,Actions.LEFT);
		if(Warriors.get(cell) != null){
			damage += 1;
			
		}
		if (cell == ThanosPos) {
			damage += 5;
		}
		//Check Down
		cell = GetCell(CurrentState.IronManPos,Actions.DOWN);
		if(Warriors.get(cell) != null) {
			damage += 1;
			
		}
		if (cell == ThanosPos) {
			damage += 5;
		}
		
		return damage;
		
	}
	
	//Goal Test
		Boolean GoalTest(EndGameState state)
		{
			if ((state.IronManPos == ThanosPos) && (state.CollectedStones.size() == Stones.size()) && (state.ReceivedDamage <= 100))
			{
				return true;
			}
			else 
				return false;
		}
		
		
		
     ArrayList<Actions> GetPath(Node n)
     {
    	 ArrayList<Actions> solution = new ArrayList<Actions>();
    	 
    	 
    	 Node newNode = n;
    	 while(!newNode.isRoot)
    	 {
    		 solution.add(newNode.action);
    		 newNode = n.parent;
    	 }
    	 
    	 return solution;
     }
     
     
     void PrintSolution(ArrayList<Actions> actions) 
     {
    	 String solution = "Path: ";
    	 
    	 for(int i=0; i<actions.size(); i++) 
    	 {
    		 solution += actions.get(i).toString() + " , ";
    	 }
    	 
    	 System.out.println(solution);
     }
	 
     
     static void Solve(String grid, QingFunc q) {
    	 EndGameProblem endGame =  new EndGameProblem(grid);
    	 
    	 endGame.GeneralSearch(endGame, q);
     }
		
	
 
 public static void main(String[]args) {

	 
	 
	Solve("5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3",QingFunc.BFS);
	 
 }
	
	
}
