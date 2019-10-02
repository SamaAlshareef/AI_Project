import java.util.ArrayList;


public class Node {
	
	EndGameState state;
	Node parent;
	EndGameProblem.Actions action;
	int depth;
	boolean isRoot;
	ArrayList<Node> children;
	
	public Node(EndGameState State,Node parent,EndGameProblem.Actions action,int depth,boolean isRoot) {
		this.state=State;
		
		this.parent=parent;
		this.depth=depth;
		
		this.action=action;
		
		this.isRoot=isRoot;
		children=new ArrayList<Node>();

		
	}
	
	public Node(EndGameState State,boolean isRoot) {
		this.state=State;
		if (isRoot) {
			parent=null;
			depth=0;
			this.isRoot=isRoot;
			children=new ArrayList<Node>();
		}
		

		
	}
	public Node MakeChild(EndGameState state, EndGameProblem.Actions action) {
		
		Node n = new Node(state,this,action,this.depth+1,false);
		children.add(n);
		return n;
	}
	

}
