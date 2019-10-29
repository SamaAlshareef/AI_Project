import java.util.ArrayList;


public class Node {
	
	EndGameState state;
	Node parent;
	EndGameProblem.Actions action;
	int depth;
	int pathCost;
	int HeuristicScore;
	boolean isRoot;
	ArrayList<Node> children;
	
	public Node(EndGameState State,Node parent,EndGameProblem.Actions action,int heuristicFunc,int depth,boolean isRoot) {
		this.state=State;
		
		this.parent=parent;
		this.depth=depth;
		this.pathCost = State.ReceivedDamage;
		this.action=action;
		this.HeuristicScore = heuristicFunc;
		this.isRoot=isRoot;
		children=new ArrayList<Node>();

		
	}
	
	public Node(EndGameState State,boolean isRoot) {
		this.state=State;
		if (isRoot) {
			parent=null;
			depth=0;
			this.pathCost = 0;
			this.HeuristicScore = 0;
			this.isRoot=isRoot;
			children=new ArrayList<Node>();
		}
		

		
	}
	public Node MakeChild(EndGameState state, EndGameProblem.Actions action, int heuristicScore) {
		
		Node n = new Node(state,this,action,heuristicScore,this.depth+1,false);
		children.add(n);
		return n;
	}
	

}
