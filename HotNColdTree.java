
public class HotNColdTree {
	
		HotNColdNode root; //The root of the tree
		hotList hotList; 
		int traversals = 0; //Used to count the # of nodes required to find the node
		
		//Create a tree with the given key k
		public HotNColdTree(int k){
			root = new HotNColdNode(k);
			hotList = new hotList(root);
		}
		
		//Create a tree with the given HotNColdNode h
		public HotNColdTree(HotNColdNode h){
			root = h;
			hotList = new hotList(root);
		}
		
		//Returns the size of the tree
		public int getSize(HotNColdNode r){
			int size = 0;
			//size++;
			if (r.leftChild != null){
				size += getSize(r.leftChild);
			}
			if (r.rightChild != null){
				size += getSize(r.rightChild);
			}
			return size+1;
		}
		
		//Returns the # of AVL traversals the tree completed to add/delete a node
		public int getTraversals(){
			int t = traversals;
			resetTraversals();
			return t;
		}
		
		//Set traversals value to 0, used for testing
		public void resetTraversals(){
			traversals = 0;
		}
		
		//Check and modify the hotList
		public void checkList(HotNColdNode r){
			hotList.checkHot(r);
		}
		
		//Returns the hotList
		public HotNColdNode[] getHotList(){
			return hotList.getHotList();
		}
		
		//Searches for the node
		public HotNColdNode searchNode(HotNColdNode r, int key){
			HotNColdNode temp = null;
			System.out.println("Node: " + r.key);
			if (r.key == key){
				temp = r;
			} else if (key < r.key){
				if(r.leftChild != null){
					temp = searchNode(r.leftChild, key);
				} else {
					temp =  null;
				}
				
			} else if (key > r.key){
				if (r.rightChild != null){
					temp = searchNode(r.rightChild, key);
				} else {
					temp = null;
				}
				
			}
				return temp;
		}
		
		//Adds a node to the tree by passing a root node and the node intended to be added
		//If the system finds the key already in the tree the key is not added, essentially
		//becoming a find operation. heat value of the node is incremented
		public HotNColdNode addNode(HotNColdNode root, HotNColdNode r){
			traversals++;
			if (r.key == root.key){ //The key was found within the tree
				root.heat++;
				hotList.checkHot(root);
				return root;
			} else if (r.key < root.key){ //Traversal less than
				if(root.leftChild != null){
					addNode(root.leftChild, r);
				} else {
					root.setLeftChild(r);
					hotList.newHotList(getSize(this.root),r);	
				}
				
			} else if (r.key > root.key){ //Traversal greater than
				if(root.rightChild != null){
					addNode(root.rightChild, r);
				} else {
					root.setRightChild(r);
					hotList.newHotList(getSize(this.root),r);
				}
			}
			return null;
		}
		
		//Deletes the desired node
		public String deleteNode(HotNColdNode r, int key){
			if (r.key == key){
				if(r.parent == null){
					//Set the root to one of its children, rotation might be necessary
				}
				if(r.leftChild == null && r.rightChild == null){
					if(r.parent.rightChild == r){
						r.parent.setRightChild(null);
					} else if (r.parent.leftChild == r){
						r.parent.setLeftChild(null);
					}
				} else if (r.leftChild == null && r.rightChild != null){
					if(r.parent.rightChild == r){
						r.parent.setRightChild(r.rightChild);
					} else if (r.parent.leftChild == r){
						r.parent.setLeftChild(r.rightChild);
					}
				} else if (r.leftChild != null && r.rightChild == null){
					if(r.parent.rightChild == r){
						r.parent.setRightChild(r.leftChild);
					} else if (r.parent.leftChild == r){
						r.parent.setLeftChild(r.leftChild);
					}
				} else {
					if(r.parent.rightChild == r){
						r.parent.setRightChild(r.rightChild);
						r.rightChild.setLeftChild(r.leftChild);
					} else if (r.parent.leftChild == r){
						r.parent.setLeftChild(r.rightChild);
						r.rightChild.setLeftChild(r.leftChild);
					}
				}
				return "The Node " + Integer.toString(key) + " was found within the tree";
			} else if (key < r.key){ //Traversal less than
				if(r.leftChild != null){
					deleteNode(r.leftChild, key);
				} else {
					return "The Node " + Integer.toString(key) + " was not found within the tree";
				}
				
			} else if (key > r.key){ //Traversal greater than
				if (r.rightChild != null){
					deleteNode(r.rightChild, key);
				} else {

					return "The Node " + Integer.toString(key) + "was not found within the tree";
				}
				
			}
			return "The Node was found";
		}
		
		//Perform Double-Left Rotation (LR)
		public void doubleLeftRotation(HotNColdNode r){
			rightRotate(r.rightChild);
			leftRotate(r);
		}
		
		//Perform Double-Right Rotation (RL)
		public void doubleRightRotation(HotNColdNode r){
			leftRotate(r.leftChild);
			rightRotate(r);
		}
		
		//PreOrder Traversal
		//Checks for imbalances throughout the tree
		public void Traverse(HotNColdNode r){
			if (r.leftChild != null){
				Traverse(r.leftChild);
			} 
			if (r.rightChild != null){
				Traverse(r.rightChild);
			}
			checkStatus(r);
			
		}
		
		//Checks tree for imbalance, applies the appropriate balancing function if imbalanced
		public void checkStatus(HotNColdNode r){
			if (r.getBalanceFactor() >= 2){
				if (r.rightChild != null){
					if (r.rightChild.getBalanceFactor() <= -2){
						doubleLeftRotation(r);
					} else {
						leftRotate(r);
					}
				}
			} else if (r.getBalanceFactor() <= -2){
				if (r.leftChild != null){
					if (r.leftChild.getBalanceFactor() >= 2){
						doubleRightRotation(r);
					} else {
						rightRotate(r);
					}
				}
				
			}
		}
		
		//RightRotation as defined by the AVL tree property
		public HotNColdNode rightRotate(HotNColdNode node){
			HotNColdNode n0 = node;
			HotNColdNode l0 = node.leftChild;
			HotNColdNode n1 = node.parent;
			
			//Maintain Parent Link
			if (node.parent == null){ //The node is the new root
				l0.setParent(null);
				root = l0;
			} else if (node.parent.key < l0.key){
				n1.setRightChild(l0);
			} else {
				n1.setLeftChild(l0);
			}
			n0.setLeftChild(l0.rightChild);
			l0.setRightChild(n0);
			return l0;
		}
		
		//LeftRotation as defined by the AVL tree property
		public HotNColdNode leftRotate(HotNColdNode node){
			HotNColdNode n0 = node;
			HotNColdNode r0 = node.rightChild;
			HotNColdNode n1 = node.parent;
			
			//Maintain Parent Link
			if (node.parent == null){ //The node is the new root
				r0.setParent(null);
				root = r0;
			} else if (node.parent.key < r0.key){
				n1.setRightChild(r0);
			} else {
				n1.setLeftChild(r0);
			}
			
			n0.setRightChild(r0.leftChild);
			r0.setLeftChild(n0);
			
			return r0;
		}
		
}
