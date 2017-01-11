
// Global alignment computation can be done with this class
// Author: Ofir Etz-Hadar
//   Date: Jan 2016

public class GlobalAlignment {

	private int DPmatrix[][];
	private String s1;
	private String s2;
	
	
	public GlobalAlignment(String s1, String s2){
		
		DPmatrix = new int[s1.length()+1][s2.length()+1];
		this.s1 = s1;
		this.s2 = s2;
		
	}
	
	// compute alignment matrix
	public void computeMatrix(){
		
		if(this.DPmatrix.length < 1 || this.DPmatrix[0].length < 1) // edge case
			return;
		
		int tmp = 0;
		// fills DP matrix
		for(int i = 1; i < this.DPmatrix.length; i++){
			for(int j = 1; j < this.DPmatrix[0].length; j++){
				if(s1.charAt(i-1) == s2.charAt(j-1)){
					tmp = 1;
				}else{
					tmp = -1;
				}
				
				tmp = Math.max(tmp + this.DPmatrix[i-1][j-1], this.DPmatrix[i][j-1]-1);
				tmp = Math.max(tmp, this.DPmatrix[i-1][j]-1);
				
				this.DPmatrix[i][j] = tmp;
			}
		}
		
	}
	
	public int getAlignmentScore(){
		
		return this.DPmatrix[this.DPmatrix.length-1][this.DPmatrix[0].length-1];
		
	}
	
	
	
}
