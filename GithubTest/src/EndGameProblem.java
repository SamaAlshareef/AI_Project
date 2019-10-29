import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Stack;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import javax.annotation.Generated;

public class EndGameProblem extends GeneralSearchProblem{

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
	
	private static HashMap<Point, String> Warriors;
	private static HashMap<Point, String> Stones;
	private static Point ThanosPos;
	private static HashMap<Actions, Boolean> ActionsMap;
	private static Queue<Node> NodesQueue;
	private static Stack<Node> NodesStack;
	private static HashMap<Integer,Queue<Node>> NodesHashmap;
	private static int NodesExpanded;
	private static HashMap<String, Boolean> AllStates;
	private static String GridString;
	private static int HeuristicFunctionScore;
	
	public EndGameProblem(String grid) {
		Warriors = new HashMap<Point, String>();
		Stones = new HashMap<Point, String>();
		ActionsMap = new HashMap<Actions, Boolean>();
		AllStates = new HashMap<String, Boolean>();
		InstantiateActionMap();
		GridString = grid;
		gridParse(grid);
	
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
	
	
	
	public int CalculateHeuristicFunc(EndGameState nextState, Actions action, int Func) 
	{
		Boolean FoundWarrior = false;
		Boolean FoundThanos = false;
		Boolean FoundStone = false;
		
		if(action == Actions.COLLECT) 
	    {
			System.out.println("I COLLECTED");
	    	HeuristicFunctionScore = 0;
	    	return HeuristicFunctionScore;
	    }
	    	
	    
		HeuristicFunctionScore = 0;
		
	    if((Stones.get(nextState.IronManPos) != null) && (nextState.CollectedStones.get(nextState.IronManPos) == null)) 
	    {
	    	HeuristicFunctionScore = 0;
	    	FoundStone = true;
	    }
	    
	    if(!FoundStone)
	    {
	    	
	   
	    	Point adjPos =  new Point(nextState.IronManPos.x+1 , nextState.IronManPos.y);
	    	if((Warriors.get(adjPos) != null) && (nextState.CollectesWarriors.get(adjPos) == null)) 
		       {
		        	HeuristicFunctionScore += 1;
		        	FoundWarrior = true;
		       }
	    	if(ThanosPos.x == adjPos.x && ThanosPos.y == adjPos.y) 
	    	{
	    		HeuristicFunctionScore += 5;
	        	FoundThanos = true;
	    	}
	    	adjPos =  new Point(nextState.IronManPos.x-1 , nextState.IronManPos.y);
	    	if((Warriors.get(adjPos) != null) && (nextState.CollectesWarriors.get(adjPos) == null)) 
		       {
		        	HeuristicFunctionScore += 1;
		        	FoundWarrior = true;
		       }
	    	if(ThanosPos.x == adjPos.x && ThanosPos.y == adjPos.y) 
	    	{
	    		HeuristicFunctionScore += 5;
	        	FoundThanos = true;
	    	}
	    	adjPos =  new Point(nextState.IronManPos.x , nextState.IronManPos.y+1);
	    	if((Warriors.get(adjPos) != null) && (nextState.CollectesWarriors.get(adjPos) == null)) 
		       {
		        	HeuristicFunctionScore += 5;
		        	FoundWarrior = true;
		       }
	    	if(ThanosPos.x == adjPos.x && ThanosPos.y == adjPos.y) 
	    	{
	    		HeuristicFunctionScore += 1;
	        	FoundThanos = true;
	    	}
	    	adjPos =  new Point(nextState.IronManPos.x , nextState.IronManPos.y-1);
	    	if((Warriors.get(adjPos) != null) && (nextState.CollectesWarriors.get(adjPos) == null)) 
		       {
		        	HeuristicFunctionScore += 5;
		        	FoundWarrior = true;
		       }
	    	if(ThanosPos.x == adjPos.x && ThanosPos.y == adjPos.y) 
	    	{
	    		HeuristicFunctionScore += 1;
	        	FoundThanos = true;
	    	}
			
	    	
	    	if(Func == 1) 
	    	{
	    		if(action == Actions.KILL) 
	    		{
	    			HeuristicFunctionScore += 2;
	    			System.out.println("I KILLED");
	    		}
			    	
	    	}
		    
	    }
	    
	    if(!FoundStone && !FoundWarrior && !FoundThanos)
	    	HeuristicFunctionScore = 0;
	    
	    
	    
	    //HeuristicFunctionScore += ((Stones.size() - nextState.CollectedStones.size()) * 10);
	    
	    return HeuristicFunctionScore;
	   
	    
		 
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
		
		HashMap<Point, String> newStones = CreateMirrorMap(state.CollectedStones);
		
		int newDamage = state.ReceivedDamage;
		
		if(state.CollectedStones.get(state.IronManPos) == null) 
		{
			EndGameState NewState = new EndGameState(state.IronManPos, newDamage , newStones , state.CollectesWarriors);
			
			NewState.CollectedStones.put(state.IronManPos , Stones.get(state.IronManPos));
			int DealtDmg = 3 + CalculateAllDamage(state.IronManPos, state);
			NewState.ReceivedDamage += DealtDmg;
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
		if ((Warriors.get(cell)!= null)&&(NewState.CollectesWarriors.get(cell) == null)) 
		{
			
			NewState.ReceivedDamage += 2;
			NewState.CollectesWarriors.put(cell , Warriors.get(cell));
			
			
		}
		
		//Check Right
		cell = GetCell(state.IronManPos, Actions.RIGHT);
		if ((Warriors.get(cell)!= null)&&(NewState.CollectesWarriors.get(cell) == null)) 
		{
			NewState.ReceivedDamage += 2;
			NewState.CollectesWarriors.put(cell , Warriors.get(cell));
			
			
		}
		
		//Check Left
		cell = GetCell(state.IronManPos, Actions.LEFT);
		if ((Warriors.get(cell)!= null)&&(NewState.CollectesWarriors.get(cell) == null)) 
		{
			NewState.ReceivedDamage += 2;
			NewState.CollectesWarriors.put(cell , Warriors.get(cell));
			
			
		}
		
		//Check Down
		cell = GetCell(state.IronManPos, Actions.DOWN);
		if ((Warriors.get(cell)!= null)&&(NewState.CollectesWarriors.get(cell) == null)) 
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
	void GenericSearch(GeneralSearchProblem problem, GeneralSearchProblem.QingFunc QF) {
		// TODO Auto-generated method stub
		Node currentNode;
		Tree tree;
		switch(QF) 
		{
		
		//Breadth first search case
		case BFS:
			NodesQueue = new LinkedList<Node>();
			currentNode = new Node(InitialState, true);
			tree = new Tree(currentNode);
			NodesQueue.add(currentNode);
			
			do
			{
				currentNode = NodesQueue.remove();
				
				if(GoalTest(currentNode.state))
				{
					Node SnapNode = currentNode.MakeChild(currentNode.state, Actions.SNAP,0);
					NodesExpanded++;
					PrintSolution(GetPath(SnapNode));
					return;
				}
				BFS(Expand(currentNode.state),currentNode);
			}while(!NodesQueue.isEmpty());
			
			break;
			
			
		//Depth first search case
		case DFS:	
			NodesStack = new Stack<Node>();
			currentNode = new Node(InitialState, true);
			tree = new Tree(currentNode);
			NodesStack.push(currentNode);
			
			do
			{
				currentNode = NodesStack.pop();
				
				if(GoalTest(currentNode.state))
				{
					Node SnapNode = currentNode.MakeChild(currentNode.state, Actions.SNAP,0);
					NodesExpanded++;
					PrintSolution(GetPath(SnapNode));
					return;
				}
				DFS(Expand(currentNode.state),currentNode);
			}while(!NodesStack.isEmpty());
			
			break;
			
		case UC:
			NodesHashmap = new HashMap<Integer,Queue<Node>>();
			currentNode = new Node(InitialState, true);
			tree = new Tree(currentNode);
			Queue<Node> nodes = new LinkedList<Node>();
			nodes.add(currentNode);
			NodesHashmap.put(currentNode.pathCost, nodes);
			int min;
			do
			{
				min = GetMinUC();
				currentNode = NodesHashmap.get(min).remove();
				
				if(GoalTest(currentNode.state))
				{
					Node SnapNode = currentNode.MakeChild(currentNode.state, Actions.SNAP,0);
					NodesExpanded++;
					PrintSolution(GetPath(SnapNode));
					return;
				}
				UC(Expand(currentNode.state),currentNode);
			}while(!NodesHashmap.isEmpty());
			
			break;
			
		case IDFS:	
			NodesStack = new Stack<Node>();
			currentNode = new Node(InitialState, true);
			Node firstNode = currentNode;
			tree = new Tree(currentNode);
			
			int i = 0;
			while(i>=0)
			{
				AllStates.clear();
				currentNode = firstNode;
				NodesStack.push(currentNode);
				do
				{
					currentNode = NodesStack.pop();
					
					if(GoalTest(currentNode.state))
					{
						Node SnapNode = currentNode.MakeChild(currentNode.state, Actions.SNAP,0);
						NodesExpanded++;
						PrintSolution(GetPath(SnapNode));
						return;
					}
					if(currentNode.depth != i)
						DFS(Expand(currentNode.state),currentNode);
				}while(!NodesStack.isEmpty());
				i++;
				System.out.println(i);
			}
			
			break;
			
		case Astar:
		case Greedy:
			NodesHashmap = new HashMap<Integer,Queue<Node>>();
			currentNode = new Node(InitialState, true);
			tree = new Tree(currentNode);
			Queue<Node> nodes2 = new LinkedList<Node>();
			nodes2.add(currentNode);
			NodesHashmap.put(currentNode.pathCost, nodes2);
			int min2;
			do
			{
				min2 = GetMinAstar();
				currentNode = NodesHashmap.get(min2).remove();
				
				if(GoalTest(currentNode.state))
				{
					Node SnapNode = currentNode.MakeChild(currentNode.state, Actions.SNAP,0);
					NodesExpanded++;
					PrintSolution(GetPath(SnapNode));
					return;
				}
				if(QF == GeneralSearchProblem.QingFunc.Astar)
					Astar_Greedy(Expand(currentNode.state),currentNode,QingFunc.Astar);
				if(QF == GeneralSearchProblem.QingFunc.Greedy)
					Astar_Greedy(Expand(currentNode.state),currentNode,QingFunc.Greedy);
			}while(!NodesHashmap.isEmpty());
			
			break;
		}
		
	}
	
	HashMap<Actions,Boolean> Expand(EndGameState currState) 
	{
		CheckMovement(currState);
		CheckWarriors(currState);
		CheckStones(currState);
		CheckThanos(currState);
		CheckSnap(currState);
		
		
		
		
		return ActionsMap;
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
				Boolean isStateExist = true;
				newState = GenerateState(entry.getKey(), currentNode.state);
				if(newState.ReceivedDamage < 100)
				{
					checkingState = new EndGameState(newState.IronManPos,0,newState.CollectedStones,newState.CollectesWarriors);
					String StateHashCode = GetHashCode(checkingState);
					if (AllStates.get(StateHashCode) == null)
					{
						isStateExist = false;
					}
					if(!isStateExist) 
					{
						childNode = currentNode.MakeChild(newState, entry.getKey(),CalculateHeuristicFunc(newState, entry.getKey(),2));
						NodesExpanded++;
						genratedNodes.add(childNode);
						AllStates.put(StateHashCode, true);
					}
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
	
	
	//QING FUNCTIONS
	
	void BFS(HashMap<Actions, Boolean> possibleActions, Node currentNode) 
	{
		Queue<Node> generatedNodes = new LinkedList<Node>();
		
		generatedNodes = GenerateNodes(possibleActions,currentNode);
		
		while(!generatedNodes.isEmpty()) 
		{
			NodesQueue.add(generatedNodes.remove());
			
		}
		
		ResetActionsMap();
	}
	
	
	void DFS(HashMap<Actions, Boolean> possibleActions, Node currentNode)
	{
		Queue<Node> generatedNodes = new LinkedList<Node>();
		
		generatedNodes = GenerateNodes(possibleActions,currentNode);
		
		while(!generatedNodes.isEmpty()) 
		{
			NodesStack.push(generatedNodes.remove());
			
		}
		
		ResetActionsMap();
	}
	
	
	void UC(HashMap<Actions, Boolean> possibleActions, Node currentNode)
	{
		Queue<Node> generatedNodes = new LinkedList<Node>();
		
		generatedNodes = GenerateNodes(possibleActions,currentNode);
		
		Node node;
		
		while(!generatedNodes.isEmpty()) 
		{
			node = generatedNodes.remove();
			if(NodesHashmap.get(node.pathCost) == null)
			{
				Queue<Node> nodes = new LinkedList<Node>();
				nodes.add(node);
				NodesHashmap.put(node.pathCost, nodes);
			}
			else
			{
				NodesHashmap.get(node.pathCost).add(node);
			}
		}
		
		ResetActionsMap();
	}
	
	void Astar_Greedy(HashMap<Actions, Boolean> possibleActions, Node currentNode, QingFunc func)
	{
		Queue<Node> generatedNodes = new LinkedList<Node>();
		
		generatedNodes = GenerateNodes(possibleActions,currentNode);
		
		Node node;
		int totalScore = 0;
		while(!generatedNodes.isEmpty()) 
		{
			node = generatedNodes.remove();
			if(func == QingFunc.Astar)
				totalScore = node.HeuristicScore + node.parent.pathCost;
			if(func == QingFunc.Greedy)
				totalScore = node.HeuristicScore;
			if(NodesHashmap.get(totalScore) == null)
			{
				Queue<Node> nodes = new LinkedList<Node>();
				nodes.add(node);
				NodesHashmap.put(totalScore, nodes);
			}
			else
			{
				NodesHashmap.get(totalScore).add(node);
			}
		}
		
		ResetActionsMap();
	}
	
	
	int GetMinUC() 
	{
		for (int min = 0 ; min < 100 ; min++) 
		{
			if( NodesHashmap.get(min) != null && !NodesHashmap.get(min).isEmpty()) 
				return min;
		}	
		return 0;
	}
	
	
	
	int GetMinAstar() 
	{
		for (int min = 0 ; min < 120 ; min++) 
		{
			if( NodesHashmap.get(min) != null && !NodesHashmap.get(min).isEmpty()) 
				return min;
		}	
		return 0;
	}
	
	int GetMinGreedy() 
	{
		for (int min = 0 ; min < 20 ; min++) 
		{
			if( NodesHashmap.get(min) != null && !NodesHashmap.get(min).isEmpty()) 
				return min;
		}	
		return 0;
	}
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	

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
		if((point.x >= GridWidth) || (point.y >= GridHeight) || (point.x < 0) || (point.y < 0))
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
		Point TestingCell = new Point(cell.x,cell.y);
		TestingCell = GetCell(cell,Actions.UP);
		if((Warriors.get(TestingCell) != null) &&(currState.CollectesWarriors.get(TestingCell) == null)) 
		{
			damage += 1;
			
		}
		if ((TestingCell.x == ThanosPos.x) && (TestingCell.y == ThanosPos.y)) {
			damage += 5;
		}
		
		//Check Right
		TestingCell = GetCell(cell,Actions.RIGHT);
		if((Warriors.get(TestingCell) != null) &&(currState.CollectesWarriors.get(TestingCell) == null)) {
			damage += 1;
			
		}
		if ((TestingCell.x == ThanosPos.x) && (TestingCell.y == ThanosPos.y)) {
			damage += 5;
		}
		
		//Check Left
		TestingCell = GetCell(cell,Actions.LEFT);
		if((Warriors.get(TestingCell) != null) &&(currState.CollectesWarriors.get(TestingCell) == null)){
			damage += 1;
			
		}
		if ((TestingCell.x == ThanosPos.x) && (TestingCell.y == ThanosPos.y)) {
			damage += 5;
		}
		//Check Down
		TestingCell = GetCell(cell,Actions.DOWN);
		if((Warriors.get(TestingCell) != null) &&(currState.CollectesWarriors.get(TestingCell) == null)) {
			damage += 1;
			
		}
		if ((TestingCell.x == ThanosPos.x) && (TestingCell.y == ThanosPos.y)) {
			damage += 5;
		}
		
		return damage;
		
	}
	
	//Goal Test
	Boolean GoalTest(EndGameState state)
		{
			System.out.println("IRONMAN POS = X: " +state.IronManPos.x + " Y: " + state.IronManPos.y + "\n Collected Stones: " + state.CollectedStones.size() + "\n Received damage = " + state.ReceivedDamage  );
			if ((state.IronManPos.x == ThanosPos.x) && (state.IronManPos.y == ThanosPos.y) && (state.CollectedStones.size() == Stones.size()) && (state.ReceivedDamage <= 100))
			{ 
				System.out.println("I DID IT");
				return true;
			}
			else 
				return false;
		}
		
		
		
     ArrayList<Node> GetPath(Node n)
     {
    	 ArrayList<Node> solution = new ArrayList<Node>();
    	 
    	 
    	 Node newNode = n;
    	 while(!newNode.isRoot)
    	 {
    		 solution.add(newNode);
    		 newNode = newNode.parent;
    	 }
    	 
    	 return solution;
     }
     
     
     void PrintSolution(ArrayList<Node> nodes) 
     {
    	 String solution = "";
    	 ArrayList<Node> revertedS=revertList(nodes);
    	 for(int i=0; i<revertedS.size(); i++) 
    	 {
    		 solution += revertedS.get(i).action.toString() +"("+revertedS.get(i).state.ReceivedDamage+")" +",";
    	 }
    	 
    	 //Visualizing
    	 String copiedString = GridString + "_" + solution;
    	 StringSelection stringSelection = new StringSelection(copiedString);
    	 Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    	 clipboard.setContents(stringSelection, null);
    	 ////////////
    	 
    	 int TotalDmg = nodes.get(0).state.ReceivedDamage;
    	 
    	 solution += "Total Damage Dealt: "+ TotalDmg + ", Number of Nodes Expanded: " + NodesExpanded;
    	 System.out.println(solution);
     }
     
     
	 
     
    public String GetHashCode(EndGameState state) 
    {
    	String Hashcode = "" + state.IronManPos.x + state.IronManPos.y + state.CollectedStones.hashCode() + state.CollectesWarriors.hashCode();
    	return Hashcode;
    }
     
    public static ArrayList<Node> revertList(ArrayList<Node> nodes) {
    	int size=nodes.size();
    	ArrayList<Node> reverted= new ArrayList<Node>(size);
    	for(int i=0;i<size;i++) {
    		reverted.add(i, nodes.get(size-i-1));
    	}
    	return reverted;
    	
    }
	public void gridParse(String grid) {
		String[] gridArray=grid.split(";");
		for(int i=0;i<gridArray.length;i++) {
		System.out.println(gridArray[i]);}
		String[] gridDimensions=gridArray[0].split(",");
		String[] ironPos=gridArray[1].split(",");
		String[] ThanosP=gridArray[2].split(",");
		String[] stonesPos=gridArray[3].split(",");
		String[] warriorsPos=gridArray[4].split(",");
		GridWidth = Integer.parseInt(gridDimensions[1]);
		GridHeight= Integer.parseInt(gridDimensions[0]);
		Point IronManPosition = new Point(Integer.parseInt(ironPos[1]),Integer.parseInt(ironPos[0]));
		 InitialState = new EndGameState(IronManPosition);
		 ThanosPos = new Point(Integer.parseInt(ThanosP[1]),Integer.parseInt(ThanosP[0]));
		 int j=1;
		 for(int i=0;i<stonesPos.length;i+=2) {
			 int tmp1=Integer.parseInt(stonesPos[i+1]);
			 int tmp2=Integer.parseInt(stonesPos[i]);
			 Point tmpPoint = new Point(tmp1,tmp2);
			 Stones.put(tmpPoint, "s"+j);
			
			 j++;
		 }
		 j=1;
		 for(int i=0;i<warriorsPos.length;i+=2) {
			 int tmp1=Integer.parseInt(warriorsPos[i+1]);
			 int tmp2=Integer.parseInt(warriorsPos[i]);
			 Point tmpPoint = new Point(tmp1,tmp2);
			 Warriors.put(tmpPoint, "w"+j);
			
			 j++;
		 }
		
		
	}	
	
    public static void main(String[]args) {

	
 }


	
	
}
