import java.util.ArrayList;


/**
 * 
 * @author Ofir Etz-Hadar
 *
 * A BTrie (Prefix Tree) where to each bit-string inserted in the tree can be associated
 * one object of a generic type.
 * This implementation DOES NOT deal with duplicate keys: every time a string in
 * the tree is inserted, the previous value associated with the string is overwritten.
 * 
 * Leaves should correspond to bit-strings in the b-trie (might not be so because of a lazy
 * implementation of the delete method).
 * Intermediate nodes can either correspond to bit-string in the trie (if the item field
 * is not null) or to intermediate paths (if item == null).
 *
 * @param <T>	-	The type of objects this container can hold.
 */
public class BTrie<T> {

	
	/**
	 * B-Trie's root.
	 */
	private TrieNode root = new TrieNode();
	
	private class TrieNode{
		
		
		private char edgeLabel;	// The char-bit of the edge from the parent of this node;
		private final ArrayList<TrieNode> children = new ArrayList<TrieNode>();	//The children 
		private T item = null;
		
		/**
		 * Empty constructor (used to create trie's root).
		 * The associated item is initialized to null, and the list of children is initialized to an empty list.
		 */
		public TrieNode(){
			
		}
		
		/**
		 * Parameterized constructor.
		 * The associated item is initialized to null, and the list of children is initialized
		 * to an empty list.
		 * @param c:	The label of the edge that connects this node to its parent.
		 */
		public TrieNode(char c){
			this.edgeLabel = c;
		}
		
		/**
		 * Search if the current node has a child connected by an edge labeled
		 * with the given character - i.e., it searches if there is a path from
		 * these node towards the bottom of the tree which starts with the
		 * given character.
		 * 
		 * 
		 * @param c:	The character to search
		 * @return
		 */
		public TrieNode search(char c){
			int l = 0, r = children.size() - 1, pos = 0;
			char tmp_c;
			
			//Binary search on the edges to the children (they are stored in lexicographic order).
			while (l <= r){
				pos = (l + r) / 2;
				tmp_c = children.get(pos).edgeLabel;
				//System.out.print(tmp_c);
				if (tmp_c == c){
					return children.get(pos);
				}else if (tmp_c < c){
					l = pos + 1;
				}else{
					r = pos - 1;
				}
			}

			return null;
		}
		
		/**
		 * Insert a new edge leaving this node, if an edge with the same label
		 * isn't already present.
		 * 
		 * @param c:	The label of the edge to insert.
		 * @return:	The child of this node that is connected to it by an edge
		 * 			whose label is the specified character (possibly a newly
		 * 			created edge).
		 */
		public TrieNode insertNode(char c){
			
			int size = children.size(), l = 0, r = size - 1, pos = 0;
			char tmp_c;
			//Binary search over the edges to the children (sorted lexicographically)
			while (l <= r){
				pos = (l + r) / 2;
				TrieNode child = children.get(pos);
				tmp_c = child.edgeLabel;
				if (tmp_c == c){
					//The edge already exist => returns it
					return child;
				}else if (tmp_c < c){
					l = pos + 1;
				}else{
					r = pos - 1;
				}
			}
			
			//The edge doesn't exist: a new node needs to be created
			TrieNode node = new TrieNode(c);
			this.children.add(l, node);
			return node;
			
		}
		
				
		/**
		 * Change the object associated with the string corresponding to the
		 * path from the root of the b-trie to this node;
		 * 
		 * @param item:	The new object to store
		 */
		public void setItem(T item){
			this.item = item;
		}
		
	} // End of TrieNode class
	
	/**
	 * Insert a bit-string into the b-trie, together with an object associated with it.
	 * 
	 * @param keys:	Sorted array of the 'true' positions on the bit-string to be inserted (instead of the whole string)
	 * @param item:	The object associated with the string to insert.
	 * @param list: The list of the strings in item if necessary (can be null if not important).
	 * @param length: The size of the bit-strings in the b-tire (they are all the same size)

	 */
	public void insertString(int[] keys, T item, ArrayList<String> list, int length){

		TrieNode node = root;
		int currentMin = 0;
		
		for(int i = 0; i < length; i++){
			if(currentMin < keys.length && i == keys[currentMin]){
				node = node.insertNode('1');
				currentMin++; // The next minimum key
			}
			else{ // Should be '0' in this position
				node = node.insertNode('0');
			}
		}

		if(node.item != null){ // Union two trees
			if(node.item instanceof Trie<?>){
				//insertAll((BTrie<String>)node.item, list);	
			}
		}
		else{
			node.setItem(item);
		}
	}
	
	/**
	 * Searches a bit-string to test if it belongs to the b-trie.
	 * 
	 * @param keys:	Sorted array of the 'true' positions on the bit-string to be inserted (instead of the whole string)
	 * @param length: The size of the bit-strings in the b-tire (they are all the same size)
	 * @return:	If the bit-string is stored into the b-trie, the item associated
	 * 			with it is returned; w returns null.
	 */
	public T search(int[] keys, int length){

		TrieNode node = root, child;
		char c = '0';
		int currentMin = 0;
		
		for(int i = 0; i < length; i++){
			if(currentMin < keys.length && i == keys[currentMin]){
				c = '1';
				currentMin++;
			}
			else{
				c = '0';
			}
			
			try{
				child = node.search(c);
				node = child;
			}catch(NullPointerException e){
				return null;
			}
		}
		
		try{
			return node.item;			
		}catch(NullPointerException e){
			return null;
		}

		
	}
	
	
}
