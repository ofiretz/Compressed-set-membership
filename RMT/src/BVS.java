import java.io.Serializable;

/* BVS.java
 * 
 *   Created on: Jan 21, 2016
 *        Author: Ofir Etz-Hadar.
 */
public class BVS implements Serializable{

	public char[] zeros;
	public int size;
	
	public BVS(int size){
		this.size = size;
		zeros = new char[size];
		for(int i=0; i<size; i++)
			zeros[i] = '0';
	
	}
	
	public String getBVS(int[] keys, int size){
		
		for(int i = 0; i <keys.length; i++){
			this.zeros[keys[i]] = '1';
		}
		
		String bvs = new String(zeros);
		
		clean(zeros, keys, size);
		return bvs;
	}
	
	public void clean(char[] chars, int[] keys, int size){
		
		for(int i = 0; i <keys.length; i++){
			this.zeros[keys[i]] = '0';
		}
		
	}
	
	
	
}
