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
	
	private static ArrayList<EndGameState> AllStates;
	
	public EndGameProblem(String grid) {
		Warriors = new HashMap<Point, String>();
		Stones = new HashMap<Point, String>();
		ActionsMap = new HashMap<Actions, Boolean>();
		AllStates = new ArrayList<EndGameState>();
		InstantiateActionMap();
		String tempGrid = grid.substring(0, 4);
		 //grid.split(arg0)
		 GridWidth = Character.getNumericValue(tempGrid.charAt(0));
		 GridHeight = Character.getNumericValue(tempGrid.charAt(2));
		 grid = grid.substring(4, grid.length());
		 
		 //Set Ironman position
		 tempGrid = grid.substring(0, 4);
		 Point IronManPosition = new Point(Character.getNumericValue(tempGrid.charAt(2)),Character.getNumericValue(tempGrid.charAt(0)));
		 InitialState = new EndGameState(IronManPosition);
		 grid = grid.substring(4, grid.length());
		 
		 //Set Thanos position
		 tempGrid = grid.substring(0, 4);
		 ThanosPos = new Point(Character.getNumericValue(tempGrid.charAt(2)),Character.getNumericValue(tempGrid.charAt(0)));
		 grid = grid.substring(4, grid.length());
		 
		 //Set Stones positions
		 for(int i=0; i<6; i++) 
		 {
			 tempGrid = grid.substring(0, 4);
			 Point tmpPoint = new Point(Character.getNumericValue(tempGrid.charAt(2)),Character.getNumericValue(tempGrid.charAt(0)));
			 Stones.put(tmpPoint, "s"+(i+1));
			 grid = grid.substring(4, grid.length());
			 
		 }
		 
		//Set Warriors positions
		 while(grid.length()>1)
		 {
			
			 if(grid.length() == 3) 
			 {
				 tempGrid = grid.substring(0, 3);
				 Point tmpPoint2 = new Point(Character.getNumericValue(tempGrid.charAt(2)),Character.getNumericValue(tempGrid.charAt(0)));
				 Warriors.put(tmpPoint2, "w"+(WarriorsCount+1));
				 WarriorsCount++;
				 break;
			 }
			 else 
			 {
				 tempGrid = grid.substring(0, 4);
				 Point tmpPoint = new Point(Character.getNumericValue(tempGrid.charAt(2)),Character.getNumericValue(tempGrid.charAt(0)));
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
	void CheckMovement(EndGameState currState) {
		Point cell = new Point();
		
		//Check UP
		cell = GetCell(currState.IronManPos,Actions.UP);
		if(CheckBorder(cell)) {
			ActionsMap.replace(Actions.UP, false);
		}
		
		
		//Check Right
		cell = GetCell(currState.IronManPos,Actions.RIGHT);
		if(CheckBorder(cell)) {
			ActionsMap.replace(Actions.RIGHT, false);
		}
		
		//Check Left
		cell = GetCell(currState.IronManPos,Actions.LEFT);
		if(CheckBorder(cell)) {
			ActionsMap.replace(Actions.LEFT, false);
		}
		
		//Check Down
		cell = GetCell(currState.IronManPos,Actions.DOWN);
		if(CheckBorder(cell)) {
			ActionsMap.replace(Actions.DOWN, false);
		}
	}
	
	
	void CheckWarriors(EndGameState currState){
		Point cell = new Point();
		Boolean WarriorsClear = true;
		
		//Check UP
		cell = GetCell(currState.IronManPos,Actions.UP);
		if((Warriors.get(cell) != null) && (currState.CollectesWarriors.get(cell) == null)) {
			ActionsMap.replace(Actions.UP, false);
			WarriorsClear = false;
		}
		
		//Check Right
		cell = GetCell(currState.IronManPos,Actions.RIGHT);
		if((Warriors.get(cell) != null) && (currState.CollectesWarriors.get(cell) == null)) {
			ActionsMap.replace(Actions.RIGHT, false);
			WarriorsClear = false;
		}
		
		//Check Left
		cell = GetCell(currState.IronManPos,Actions.LEFT);
		if((Warriors.get(cell) != null) && (currState.CollectesWarriors.get(cell) == null)) {
			ActionsMap.replace(Actions.LEFT, false);
			WarriorsClear = false;
		}
		
		//Check Down
		cell = GetCell(currState.IronManPos,Actions.DOWN);
		if((Warriors.get(cell) != null) && (currState.CollectesWarriors.get(cell) == null)) {
			ActionsMap.replace(Actions.DOWN, false);
			WarriorsClear = false;
		}
		
		if(WarriorsClear) {
			ActionsMap.replace(Actions.KILL, false);
		}
		
	}
	
	void CheckThanos(EndGameState currState) {
		
		Point cell = new Point();
		
		
		
		
		if((currState.CollectedStones.size() != 6)) 
		{
			//Check Up
			cell = GetCell(currState.IronManPos, Actions.UP);
			if ((cell.x == ThanosPos.x) && (cell.y == ThanosPos.y)) 
			{
				ActionsMap.replace(Actions.UP, false);
			}
			
			//Check Right
			cell = GetCell(currState.IronManPos, Actions.RIGHT);
			if ((cell.x == ThanosPos.x) && (cell.y == ThanosPos.y)) 
			{
				ActionsMap.replace(Actions.RIGHT, false);
			}
			
			//Check Left
			cell = GetCell(currState.IronManPos, Actions.LEFT);
			if ((cell.x == ThanosPos.x) && (cell.y == ThanosPos.y)) 
			{
				ActionsMap.replace(Actions.LEFT, false);
			}
			
			//Check Down
			cell = GetCell(currState.IronManPos, Actions.DOWN);
			if ((cell.x == ThanosPos.x) && (cell.y == ThanosPos.y)) 
			{
				ActionsMap.replace(Actions.DOWN, false);
			}
		}
		
		
	}
	
	
	void CheckStones(EndGameState currState) 
	{
		 Point cell = new Point();
		 
		 //Check Stone in the current cell
		 cell = currState.IronManPos;
		 if((Stones.get(cell) == null) || (currState.CollectedStones.get(cell) != null)) 
		 {
			 ActionsMap.replace(Actions.COLLECT,false);
		 }
     
	}
	
	void CheckSnap(EndGameState currState) 
	{
		if (currState.IronManPos != ThanosPos)
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
	HashMap<Actions,Boolean> Expand(EndGameState currState) 
	{
		CheckMovement(currState);
		CheckWarriors(currState);
		CheckStones(currState);
		CheckThanos(currState);
		CheckSnap(currState);
		
		
		
		
		return ActionsMap;
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
		Point NewPosition = new Point(state.IronManPos.x, state.IronManPos.y - 1);
		int DealtDamage = CalculateAllDamage(NewPosition, state);
		EndGameState NewState = new EndGameState(NewPosition, state.ReceivedDamage + DealtDamage , state.CollectedStones , state.CollectesWarriors);
		
		return NewState;
	}
	
	// Move Right Transition Function
	EndGameState MoveRightTransitionFunction(EndGameState state) 
	{
		Point NewPosition = new Point(state.IronManPos.x + 1, state.IronManPos.y);
		int DealtDamage = CalculateAllDamage(NewPosition, state);
		EndGameState NewState = new EndGameState(NewPosition, state.ReceivedDamage + DealtDamage , state.CollectedStones , state.CollectesWarriors);
		
		return NewState;
	}
	
	//Move Left Transition Function
	EndGameState MoveLeftTransitionFunction(EndGameState state) 
	{
		Point NewPosition = new Point(state.IronManPos.x - 1, state.IronManPos.y);
		int DealtDamage = CalculateAllDamage(NewPosition, state);
		EndGameState NewState = new EndGameState(NewPosition, state.ReceivedDamage + DealtDamage , state.CollectedStones , state.CollectesWarriors);
		
		return NewState;
	}
	
	//Move Down Transition Function
	EndGameState MoveDownTransitionFunction(EndGameState state) 
	{
		Point NewPosition = new Point(state.IronManPos.x, state.IronManPos.y + 1);
		int DealtDamage = CalculateAllDamage(NewPosition, state);
		EndGameState NewState = new EndGameState(NewPosition, state.ReceivedDamage + DealtDamage , state.CollectedStones , state.CollectesWarriors);
		
		return NewState;
	}
	
	//Collect Stones Transition Function
	EndGameState CollecStonesTransitionFunction(EndGameState state) 
	{
		//BAYZAAAA
		HashMap<Point, String> newStones = CreateMirrorMap(state.CollectedStones);
		
		int newDamage = state.ReceivedDamage;
		
		if(state.CollectedStones.get(state.IronManPos) == null) 
		{
			EndGameState NewState = new EndGameState(state.IronManPos, newDamage , newStones , state.CollectesWarriors);
			NewState.CollectedStones.put(state.IronManPos , Stones.get(state.IronManPos));
			NewState.ReceivedDamage += 3;
			return NewState;	
		}
		
		else
			return state;
	}
	
	//Killing Warriors in Adjacent Cells
	EndGameState KillTransitionFunction(EndGameState state) 
	{
		Point cell = new Point();
		
		
		HashMap<Point, String> newWarriors = CreateMirrorMap(state.CollectesWarriors);
		int newDamage = state.ReceivedDamage;
		EndGameState NewState = new EndGameState(state.IronManPos, newDamage , state.CollectedStones , newWarriors);
		
		//Check up
		cell = GetCell(state.IronManPos, Actions.UP);
		if (NewState.CollectesWarriors.get(cell) == null) 
		{
			
			NewState.ReceivedDamage += 2;
			NewState.CollectesWarriors.put(cell , Warriors.get(cell));
			
			
		}
		
		//Check Right
		cell = GetCell(state.IronManPos, Actions.RIGHT);
		if (NewState.CollectesWarriors.get(cell) == null) 
		{
			NewState.ReceivedDamage += 2;
			NewState.CollectesWarriors.put(cell , Warriors.get(cell));
			
			
		}
		
		//Check Left
		cell = GetCell(state.IronManPos, Actions.LEFT);
		if (NewState.CollectesWarriors.get(cell) == null) 
		{
			NewState.ReceivedDamage += 2;
			NewState.CollectesWarriors.put(cell , Warriors.get(cell));
			
			
		}
		
		//Check Down
		cell = GetCell(state.IronManPos, Actions.DOWN);
		if (NewState.CollectesWarriors.get(cell) == null) 
		{
			NewState.ReceivedDamage += 2;
			NewState.CollectesWarriors.put(cell , Warriors.get(cell));
			
			
		}
		
		return NewState;
		
		
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
	
	
	
	@Override
	void GeneralSearch(GenericSearchProblem problem, GenericSearchProblem.QingFunc QF) {
		// TODO Auto-generated method stub
		nodes = new LinkedList<Node>();
		Node currentNode = new Node(InitialState, true);
		Tree tree = new Tree(currentNode);
		nodes.add(currentNode);
		
		do
		{
			currentNode = nodes.remove();
			
			if(GoalTest(currentNode.state))
			{
				PrintSolution(GetPath(currentNode));
				return;
			}
			
			
			switch(QF) 
			{
			case BFS:
				BFS(Expand(currentNode.state),currentNode);
			
			}
			
		}while(!nodes.isEmpty());
		
	}
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	//QING FUNCTIONS
	
	void BFS(HashMap<Actions, Boolean> possibleActions, Node currentNode) 
	{
		Queue<Node> generatedNodes = new LinkedList<Node>();
		
		generatedNodes = GenerateNodes(possibleActions,currentNode);
		
		while(!generatedNodes.isEmpty()) 
		{
			nodes.add(generatedNodes.remove());
		}
		
		ResetActionsMap();
	}
	
	
	
	
	
	
	Queue<Node> GenerateNodes(HashMap<Actions, Boolean> possibleActions, Node currentNode)
	{
		Queue<Node> genratedNodes = new LinkedList<Node>();
		EndGameState newState = null;
		EndGameState checkingState = null;
		Node childNode = null;
		for (Map.Entry<Actions,Boolean> entry : possibleActions.entrySet()) 
		{
			
			if(entry.getValue()) 
			{
				Boolean isStateExist = false;
				newState = GenerateState(entry.getKey(), currentNode.state);
				checkingState = new EndGameState(newState.IronManPos,0,newState.CollectedStones,newState.CollectesWarriors);
				for(int i = 0; i<AllStates.size(); i++)
				{
					if(CompareStates(AllStates.get(i), checkingState)) 
					{
						isStateExist = true;
					}
					
				}
				if(!isStateExist) 
				{
					childNode = currentNode.MakeChild(newState, entry.getKey());
					genratedNodes.add(childNode);
					AllStates.add(checkingState);
				}
					
			}
			
		
		}
	        
		return genratedNodes;
		
	}
	
	
	EndGameState GenerateState(Actions action, EndGameState currState) 
	{
		EndGameState newState = null;
		switch(action)
		{
		case UP:
			newState = MoveUpTransitionFunction(currState);
			break;
		case RIGHT:
			newState = MoveRightTransitionFunction(currState);
			break;
		case LEFT:
			newState = MoveLeftTransitionFunction(currState);
			break;
		case DOWN:
			newState = MoveDownTransitionFunction(currState);
			break;
		case KILL:
			newState = KillTransitionFunction(currState);
			break;
		case COLLECT:
			newState = CollecStonesTransitionFunction(currState);
			break;
		case SNAP:
			newState = SnapTransitionFunction(currState);
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
			newPoint.setLocation(self.x, self.y-1);
			break;
		case RIGHT:
			newPoint.setLocation(self.x+1, self.y);
			break;
		case LEFT:
			newPoint.setLocation(self.x-1, self.y);
			break;
		case DOWN:
			newPoint.setLocation(self.x, self.y+1);
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
	
	
	
	HashMap<Point, String> CreateMirrorMap(HashMap<Point, String> original)
	{
		HashMap<Point, String> mirror = new HashMap<Point, String>();
		
		
			mirror.putAll(original);
		
		
		return mirror;
		
	}
	int CalculateAllDamage(Point cell, EndGameState currState) 
	{
		int damage = 0;
		
		cell = GetCell(currState.IronManPos,Actions.UP);
		if(currState.CollectesWarriors.get(cell) == null) 
		{
			damage += 1;
			
		}
		if (cell == ThanosPos) {
			damage += 5;
		}
		
		//Check Right
		cell = GetCell(currState.IronManPos,Actions.RIGHT);
		if(currState.CollectesWarriors.get(cell) == null) {
			damage += 1;
			
		}
		if (cell == ThanosPos) {
			damage += 5;
		}
		
		//Check Left
		cell = GetCell(currState.IronManPos,Actions.LEFT);
		if(currState.CollectesWarriors.get(cell) == null){
			damage += 1;
			
		}
		if (cell == ThanosPos) {
			damage += 5;
		}
		//Check Down
		cell = GetCell(currState.IronManPos,Actions.DOWN);
		if(currState.CollectesWarriors.get(cell) == null) {
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
			System.out.println("IRONMAN POS = X: " +state.IronManPos.x + " Y: " + state.IronManPos.y + "\n Collected Stones: " + state.CollectedStones.size() + "\n Received damage = " + state.ReceivedDamage + "\n Nodes size = "+nodes.size()+ "\n\n" );
			if ((state.IronManPos.x == ThanosPos.x) && (state.IronManPos.y == ThanosPos.y) && (state.CollectedStones.size() == Stones.size()) && (state.ReceivedDamage <= 100))
			{
				System.out.println("I DID IT");
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
    		 newNode = newNode.parent;
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
     
     Boolean CompareStates(EndGameState existState, EndGameState newState) 
     {
    	 Boolean result = true;
    	 
    	 if((existState.IronManPos.x != newState.IronManPos.x) || (existState.IronManPos.y != newState.IronManPos.y)) 
    	 {
    		 result = false;
    	 }
    	 
    	 if(existState.CollectedStones.size() != newState.CollectedStones.size()) 
    	 {
    		 result = false;
    	 }
    	 
    	 if(existState.CollectesWarriors.size() != newState.CollectesWarriors.size()) 
    	 {
    		 result = false;
    	 }
    	 
    	 return result;
    	 
    	 
     }
	 
     
    
		
	
 
 public static void main(String[]args) {

	
	
 }


	
	
}
