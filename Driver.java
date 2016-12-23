import java.util.Random;

//Used as a Driver for the instantiation of the HotUI class
public class Driver {
	
	private static HotUI ui; //The UI
	
	public static void main(String[] args){
		HotNColdNode root = new HotNColdNode(50);
		ui = new HotUI(root);
	}
}
