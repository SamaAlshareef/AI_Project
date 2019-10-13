

public abstract class GenericSearchProblem {

	public enum QingFunc
	{
		
		BFS,
		DFS,
		UC,
		Astar,
		Greedy,
		IDFS
	}
	
	
	abstract void GeneralSearch(GenericSearchProblem problem,QingFunc QF); 
	
	
	
	
}
