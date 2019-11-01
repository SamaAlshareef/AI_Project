

public abstract class GeneralSearchProblem {

	public enum QingFunc
	{
		
		BF,
		DF,
		ID,
		UC,
		AS1,
		AS2,
		GR1,
		GR2
	}
	
	
	abstract String GenericSearch(GeneralSearchProblem problem,QingFunc QF); 
	
	
	
	
}
