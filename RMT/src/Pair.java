import java.io.IOException;
import java.io.Serializable;

/* Pair.java
 * 
 *   Created on: Jan 21, 2016
 *        Author: Ofir Etz-Hadar.
 */
public class Pair implements Serializable{

	private String first;
	private String second;
	private String third;
	private String fourth;
	private int vector_size;
	private int k;
	private Boolean last;
	
	
	public Pair(){}
	
	public Pair(String f, String s, int b, int k){
		this.first = f;
		this.second = s;
		this.vector_size = b;
		this.k = k;
		this.last = false;
	
	}
	
	public Pair(String f, String s, String t, String fourth, int b, int k, Boolean last){
		this.first = f;
		this.second = s;
		this.third = t;
		this.fourth = fourth;
		this.vector_size = b;
		this.k = k;
		this.last = last;
	
	}
	
	// Copy constructor - deep copy
	public Pair(Pair p){
		this.first = new String(p.getFirst());
		this.second = new String(p.getSecond());
		this.vector_size = p.getVectorSize();
		this.k = p.getK();
		this.last = p.getLast();
	
	}
	
	public void setFirst(String str){
		this.first = str;
	}
	
	public String getFirst(){
		return this.first;
	}
	
	public void setSecond(String str){
		this.second = str;
	}
	
	public String getSecond(){
		return this.second;
	}
	
	public String getThird(){
		return this.third;
	}
	
	public String getFourth(){
		return this.fourth;
	}
	
	public int getVectorSize(){
		return this.vector_size;
	}
	
	public int getK(){
		return this.k;
	}
	
	public Boolean getLast(){
		return this.last;
	}
	
	public void setLast(Boolean last){
		this.last = last;
	}
	
	// create bvs vector 
	public void applyBVS(){
		BVS bvs = new BVS(this.getVectorSize());
		
		try {
			byte[] byteArray = HashProvider.toByteArray(new String(this.getFirst()));
			int[] keys = HashProvider.hashRNG(byteArray, this.getVectorSize(), this.getK());
			this.setFirst(bvs.getBVS(keys, this.getVectorSize()));

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getBVS(){
		BVS bvs = new BVS(this.getVectorSize());
		
		try {
			byte[] byteArray = HashProvider.toByteArray(new String(this.getFirst()));
			int[] keys = HashProvider.hashRNG(byteArray, this.getVectorSize(), this.getK());
			return bvs.getBVS(keys, this.getVectorSize());

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String getBVS(String str){
		BVS bvs = new BVS(this.getVectorSize());
		
		try {
			byte[] byteArray = HashProvider.toByteArray(new String(str));
			int[] keys = HashProvider.hashRNG(byteArray, this.getVectorSize(), this.getK());
			return bvs.getBVS(keys, this.getVectorSize());

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
