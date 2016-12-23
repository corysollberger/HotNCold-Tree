
public class HotNColdNode {
	
		int key; //The key of the node
		int heat; //The heat of the node
		HotNColdNode leftChild, rightChild, parent; //Children, l and r and the parent p
		
		//Initialize HotNColdNode with the given key k
		public HotNColdNode(int k){
			this.key = k;
			this.leftChild = null;
			this.rightChild = null;
			this.parent = null;
		}
		
		//Manually place children onto a new node
		public HotNColdNode(int k, HotNColdNode l, HotNColdNode r){
			this.key = k;
			this.leftChild = l;
			this.rightChild = r;
			this.parent = null;
		}
		
		//Sets the left child of the node
		public void setLeftChild(HotNColdNode l){
			this.leftChild = l;
			if (l != null){
			l.setParent(this);
			}
		}
		
		//Sets the right child of the node
		public void setRightChild(HotNColdNode r){
			this.rightChild = r;
			if (r != null){
			r.setParent(this);
			}
			
		}
		
		//Set both the left and right children of the node
		public void setChildren(HotNColdNode l, HotNColdNode r){
			this.leftChild = l;
			this.rightChild = r;
			l.setParent(this);
			r.setParent(this);
		}
		
		//Sets the parent of the node
		public void setParent(HotNColdNode p){
			this.parent = p;
		}
		
		//Returns the height of the node as an integer value
		public int getHeight(HotNColdNode r){
			if (r == null){ //Check if the given node is null (Returns null, represented by -1)
				return -1;
			}
			int heightL = getHeight(r.leftChild); //Gets the height of the left subtree
			int heightR = getHeight(r.rightChild); //Gets the height of the right subtree
			if(heightL>heightR){
				return heightL + 1; //Return the higher height value, adding 1 after traversing the current node
			} else {
				return heightR + 1;
			}
		}
		//Returns the depth of the node as an integer value
		public int getDepth(){
			int depth = 0;
			HotNColdNode temp = this;
			while(temp.parent != null){
				temp = temp.parent;
				depth++;
			}
			return depth;
		}
		
		//Returns the "balance factor" of the tree
		//The value corresponding to the difference in heights
		//of the subtrees
		public int getBalanceFactor(){
			int bf = getHeight(rightChild)-getHeight(leftChild);
			return bf;
		}
		
		//Return a string containing node information to post to console
		//Key, Parent, LeftChild, RightChild, and Heat information is returned
		public String getInfo(){
			String s = "Key: " + this.key + "\nParent: ";
			//Displays Parent info
			if (this.parent != null){
				s = s + this.parent.key + "\nLeft Child: ";
			} else {
				s = s + "null\nLeft Child: ";
			}
			
			//Displays LeftChild info
			if (this.leftChild != null){
				s = s + this.leftChild.key + "\nRight Child: ";
			} else {
				s = s + "null\nRight Child: ";
			}
			//Displays RightChild info
			if (this.rightChild != null){
				s = s + this.rightChild.key + "\nHeat: ";
			} else {
				s = s + "null\nHeat: ";
			}
			s = s + this.heat + "\nDepth: " + this.getDepth() + "\nHeight: " + this.getHeight(this) +
					"\nBF: " + this.getBalanceFactor();
			return s;
		}
}
