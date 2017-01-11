/* SetDistanceProvider.java
 * 
 *   Created on: Jan 24, 2016
 *       Author: Ofir Etz-Hadar.
 */
import java.util.ArrayList;



public class SetDistanceProvider {

	public SetDistanceProvider(){}
	
	// create the set of all distance_hamming <= 1
	public static ArrayList<String> create1HammingSet(String l){
		
		ArrayList<String> hamms = new ArrayList<String>();
		
		if(l == null || l.length() < 1)
			return null;
				
		hamms.add(l); // add the string itself too

		for(int i = 0; i < l.length(); i++){
			if(l.charAt(i) == 'A'){
				hamms.add(l.substring(0,i).concat("C").concat(l.substring(i+1,l.length())));
				hamms.add(l.substring(0,i).concat("G").concat(l.substring(i+1,l.length())));
				hamms.add(l.substring(0,i).concat("T").concat(l.substring(i+1,l.length())));

			} else if(l.charAt(i) == 'C'){
				hamms.add(l.substring(0,i).concat("A").concat(l.substring(i+1,l.length())));
				hamms.add(l.substring(0,i).concat("G").concat(l.substring(i+1,l.length())));
				hamms.add(l.substring(0,i).concat("T").concat(l.substring(i+1,l.length())));
				
			} else if(l.charAt(i) == 'G'){
				hamms.add(l.substring(0,i).concat("A").concat(l.substring(i+1,l.length())));
				hamms.add(l.substring(0,i).concat("C").concat(l.substring(i+1,l.length())));
				hamms.add(l.substring(0,i).concat("T").concat(l.substring(i+1,l.length())));
				
			} else if(l.charAt(i) == 'T'){
				hamms.add(l.substring(0,i).concat("A").concat(l.substring(i+1,l.length())));
				hamms.add(l.substring(0,i).concat("C").concat(l.substring(i+1,l.length())));
				hamms.add(l.substring(0,i).concat("G").concat(l.substring(i+1,l.length())));
				
			}
			
			else{
				return null;
			}
						
		}
		
		return hamms;
		
	}
	
	public static ArrayList<String> create1DelitionSet(String l){
		
		ArrayList<String> dels = new ArrayList<String>();
		
		if(l == null || l.length() < 1)
			return null;
	
		for(int i = 0; i < l.length(); i++){
			dels.add(l.substring(0,i).concat(l.substring(i+1,l.length())));
		}
		
		return dels;
	}
	
	public static ArrayList<String> create1InsertionSet(String l){
		
		ArrayList<String> inserts = new ArrayList<String>();
		
		if(l == null || l.length() < 1)
			return null;
				
		for(int i = 0; i < l.length()+1; i++){
			inserts.add(l.substring(0,i).concat("A").concat(l.substring(i,l.length())));
			inserts.add(l.substring(0,i).concat("C").concat(l.substring(i,l.length())));
			inserts.add(l.substring(0,i).concat("G").concat(l.substring(i,l.length())));
			inserts.add(l.substring(0,i).concat("T").concat(l.substring(i,l.length())));	
		}
		
		return inserts;
		
	}
	
	
	
}
