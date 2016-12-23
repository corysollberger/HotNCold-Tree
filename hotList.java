public class hotList {
	
	HotNColdNode[] hotList;
	
	//Constructor
	public hotList(HotNColdNode r){
		hotList = new HotNColdNode[1];
		hotList[0] = r;
	}

	//Returns an integer value specifying whether the node was found or not
	public int inList(HotNColdNode r){
		for(int i=0;i<hotList.length;i++){
			if (hotList[i] == r){
				return 1;
			}
		}
		return -1;
	}
	
	//Returns the node if it is in the hotList
	public HotNColdNode inList(int k){
		for(int i=0;i<hotList.length;i++){
			if (hotList[i].key == k){
				return hotList[i];
			}
		}
		return null;
	}
	
	//Create a new expanded list
	public void newHotList(int size, HotNColdNode n){
		int newSize = (size/25)+1; //Ratio determines when the hotList is expanded
		if (newSize > hotList.length){ //When the size of the tree warrants an increase in list size, expand
			HotNColdNode[] tempArray = new HotNColdNode[newSize];
			System.arraycopy(hotList, 0, tempArray, 0, hotList.length);
			hotList = tempArray;
			hotList[hotList.length-1] = n;
		}
	}
	
	//checks if the new node should be in the hotList
	//If so, places it into the hotList and sorts
	//If its already in the list, sort
	public void checkHot(HotNColdNode node){
		if (inList(node)==1){
			sort();
		} else {
			if (node.heat > hotList[0].heat){
				//System.out.println("hotList[0] = " + node.key);
				hotList[0] = node;
				sort();
			}
		}
	}
	
	//Sorts the hotList by heat value, Ascending
	private void sort(){
		for(int i=0;i<hotList.length;i++){
			for (int j=(i+1);j<hotList.length;j++){
				HotNColdNode temp = hotList[i];
				hotList[i] = hotList[j];
				hotList[j] = temp;
			}
		}
	}
	
	//Returns the hotList
	public HotNColdNode[] getHotList(){
		return this.hotList;
	}
}
