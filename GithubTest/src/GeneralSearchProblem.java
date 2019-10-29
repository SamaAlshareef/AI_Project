

public abstract class GeneralSearchProblem {

	public enum QingFunc
	{
		
		BFS,
		DFS,
		UC,
		Astar,
		Greedy,
		IDFS
	}
	
	
	abstract void GenericSearch(GeneralSearchProblem problem,QingFunc QF); 
	
	
	
	
}
