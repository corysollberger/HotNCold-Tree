import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

//GUI Component to the HotNCold Tree
public class HotUI extends JFrame implements ActionListener{
	
	//Panels
	JPanel easle; //For the painting
	JPanel box;
	JPanel labelP, labelH;
	JPanel buttonPanelTree, buttonPanelSystem;
	
	//Console used to fetch commands from the user
	JTextArea console;
	JLabel hot;
	
	//Buttons used to perform actions
	JButton btn1, btn2, btn3, btn4;
	
	//Node variables which hold the tree itself, and it's root
	HotNColdNode root; //root of the tree to be displayed
	HotNColdTree tree;
	
	//Declares DrawCanvas, which is used to draw 
	private DrawCanvas canvas;
	
	//Constructor, takes the argument r (the root)
	public HotUI(HotNColdNode r){
		
		//Set the root of the tree to the given node r
		root = r;
		
		//Initialize Component(s)
		console = new JTextArea("Type argument here: (key)", 10, 40);
		canvas = new DrawCanvas();
		canvas.setPreferredSize(new Dimension(800,400));
		canvas.repaint();
		
		Container cp = getContentPane();
		
		//Initialize the Panel(s)
		this.setLayout(new GridLayout(1,2));
		cp.add(canvas);
		
		box = new JPanel();
		box.setLayout(new GridLayout(5,1));
		JPanel labelP = new JPanel();
		labelP.setLayout(new GridBagLayout());
		
		//Creates a label to display simple text
		JLabel label = new JLabel("Hot Nodes!");
		labelP.add(label);
		box.add(labelP);
		box.add(console);
		
		//Button Creation and their container, buttonPanelTree
		buttonPanelTree = new JPanel(new GridLayout(1,4));
		btn1 = new JButton("Get Info");
		btn1.setToolTipText("Repaints the tree, mostly used as a debugging tool");
		btn1.addActionListener(this);
		
		btn2 = new JButton("Add Node");
		btn2.addActionListener(this);
		btn2.setToolTipText("Adds a node to the tree, enter an integer value (key) into the textbox above before calling");
		
		btn3 = new JButton("Del Node");
		btn3.addActionListener(this);
		btn3.setToolTipText("Deletes the node from the tree, enter an integer value (key) into the textbox above before calling");
		
		btn4 = new JButton("Test");
		btn4.addActionListener(this);
		
		buttonPanelTree.add(btn1);
		buttonPanelTree.add(btn2);
		buttonPanelTree.add(btn3);
		buttonPanelTree.add(btn4);
		box.add(buttonPanelTree);
		
		cp.add(box);
		
		hot = new JLabel();
		hot.setText("HotNodes: ");
		buttonPanelSystem = new JPanel(new GridLayout(1,2));
		labelH = new JPanel(new GridBagLayout());
		labelH.add(hot);
		
		
		box.add(labelH);
		
		this.setPreferredSize(new Dimension(1920,1080));
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		HotNColdNode child1 = new HotNColdNode(35);
		HotNColdNode child2 = new HotNColdNode(85);

		tree = new HotNColdTree(root);
		setHot();
		tree.addNode(root, child1);
		tree.addNode(root, child2);
		repaint();
	}
	
	//Set the root of the tree to be displayed
	public void setRoot(HotNColdNode r){
		root = r;
	}
	
	//Function to call the request to repaint
	public void repaint(){
		canvas.repaint();
	}
	
	//Updates the hot label to correctly show the nodes on the hotList
	public void setHot(){
		HotNColdNode[] hList = tree.getHotList();
		if (hList == null){
			System.out.println("Hello");
		}
		if (hList.length == 1){
			hot.setText("Hot List[]: " + hList[0].key);
		} else {
			String s = "Hot List[]: ";
			for (int i=0;i<hList.length;i++){
				if(i != hList.length-1){
					s += hList[i].key + ", ";
				} else {
					s += hList[i].key;
				}
			}
			hot.setText(s);
		}
	}
	
	//Class used to draw objects
	private class DrawCanvas extends JPanel {
	      // Override paintComponent to perform your own painting

	      public void paintComponent(Graphics g) {
	         super.paintComponent(g);     // paint parent's background
	         setBackground(Color.BLACK);  // set background color

	         g.setColor(Color.WHITE);    // set drawing color
	         drawTree(g, root, 300, 125, 1);
	         //drawNode(g,30,180,125, Integer.toString(root.key));	         
	      }
	      
	      public void drawTree(Graphics g, HotNColdNode r, int x, int y, int count){
	    	  //Checks for hot node, set color red if it is hot
	    	  if(tree.hotList.inList(r)==1){
	    		  g.setColor(Color.RED);
	    	  } else {
	    		  g.setColor(Color.WHITE); //white if not
	    	  }
	    	  
	    	  drawNode(g,30,x,y, Integer.toString(r.key)); //draw the node

	    	  if(r.leftChild != null){ //navigate children (l)
	    		  g.setColor(Color.WHITE);
	    		  if (count%2 != 0){
	    			  g.drawLine(x, y+23, ((x+15)-(150/count)), y+65);
	    			  drawTree(g, r.leftChild, x-(150/count), y+65, count+1);
	    		  } else {
	    			  g.drawLine(x, y+23, (x-(150/count+5))+15, y+66);
	    			  drawTree(g, r.leftChild, x-(150/count+5), y+65, count+1);
	    		  }
	    	  } 
	    	  
	    	  if (r.rightChild != null){ //navigate children [r]
	    		  g.setColor(Color.WHITE);
	    		  if (count%2 != 0){
	    			  g.drawLine(x+30, y+22, (x+(150/count))+10, y+65);
	    			  drawTree(g, r.rightChild, x+(150/count), y+65, count+1);
	    		  } else {
	    			  g.drawLine(x+30, y+22, (x+(150/count+5))+10, y+65);
	    			  drawTree(g, r.rightChild, x+(150/count+5), y+65, count+1);
	    		  }
	    		  }
	    	  }
	      }
	      
	      //Used to draw the nodes of the tree
	      public void drawNode(Graphics g, int r, int x, int y, String s){
	    	  g.drawOval(x,y,r,r);
	    	  String key; //used to display the key of the node
	    	  g.drawString(s, x+8, y+20);
	      }

	//Event handler
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand() == "Get Info"){ //Repaints the screen
			String k = console.getText();
			HotNColdNode temp = tree.searchNode(root, Integer.parseInt(k));
			if (temp != null){
				String info = temp.getInfo();
				console.setText(info);
			} else {
				console.setText("The node was not found...");
			}
			repaint();
		} else if (arg0.getActionCommand() == "Add Node"){ //Adds a node
			String k = console.getText();
			HotNColdNode newNode = new HotNColdNode(Integer.parseInt(k));
			HotNColdNode temp = tree.hotList.inList(newNode.key);
			if (temp != null){
				temp.heat++;
			} else {
				tree.addNode(root, newNode);
			}
			tree.Traverse(root);
			this.root = tree.root;
			setHot();
			repaint();
		} else if (arg0.getActionCommand() == "Del Node"){ //delete a node
			String k = console.getText();
			String response = tree.deleteNode(root, Integer.parseInt(k));
			console.setText(response);
			tree.Traverse(root);
			this.root = tree.root;
			repaint();
		} else if (arg0.getActionCommand() == "Test"){ //Tests the tree
			Random rand = new Random();
			int totalCount = 0; //Holds the # of times the tree had to search the AVL tree
			int hotCount = 0; //Holds the # of times the tree found the node in the hotList
			for (int i=0;i<50;i++){
				int n = rand.nextInt(500);
				HotNColdNode newNode = new HotNColdNode(n);
				HotNColdNode temp = tree.hotList.inList(newNode.key);
				if (temp != null){
					temp.heat++;
					hotCount++;
				} else {
					tree.addNode(root, newNode);
					totalCount = totalCount + tree.getTraversals();
				}
				tree.Traverse(root);
				this.root = tree.root;
			}
			console.setText("AVL Traversals (NOT IN hotList): " + totalCount + "\nhotList successful: " + hotCount);
			setHot();
			repaint();
		}
		
	}
}
