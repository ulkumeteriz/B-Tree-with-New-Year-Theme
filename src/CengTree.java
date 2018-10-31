import java.util.ArrayList;

public class CengTree
{
	public CengTreeNode root;
	
	public CengTree(Integer order)
	{
		CengTreeNode.order = order;
		// Create an empty Tree
		root = null;
	}
	
	public void addGift(CengGift gift)
	{			
		insert(root,gift);
	}
	
	
	public ArrayList<CengTreeNode> searchGift(Integer key)
	{
		// Search within whole Tree, return visited nodes.
		// Return null if not found.
		ArrayList<CengTreeNode> result = new ArrayList<CengTreeNode>();
		
		// push root as visited.
		result.add(root);
		
		//construct the result array.
		searchHelper(result,root,key);
		
		// get the last element which is the leaf node.
		CengTreeNodeLeaf temp = (CengTreeNodeLeaf)result.get(result.size()-1); 
		
		CengGift foundGift = temp.findGift(key);
		
		if(foundGift == null) {
			System.out.println("Could not find " +key+".");
		}
		else {
			int i=0;
			for(CengTreeNode node : result) {
				if(node.getType()==CengNodeType.Internal) {
					((CengTreeNodeInternal)(node)).printKeys(i);
					i++;
				}
				else {
					while(i!=0){
						System.out.print("\t");
						i--;
					}
					System.out.println("<record>"+foundGift.fullName()+"</record>");
				}
			}
		}
		
		return result;
	}
	
	public void printTree()
	{
		// Print the whole tree to console
		printHelper(root,0);
	}

	// Extra Functions
	
	public void printHelper(CengTreeNode node,int i) {
		if(node.getType()==CengNodeType.Internal) {
			ArrayList<CengTreeNode>children= ((CengTreeNodeInternal)(node)).getAllChildren();
			((CengTreeNodeInternal)(node)).printKeys(i);
			for(CengTreeNode child:children){
				printHelper(child,i+1);
			}
		}
		else {
			((CengTreeNodeLeaf)(node)).printGifts(i);
			return;
		}
	}
	
	
	public void searchHelper(ArrayList<CengTreeNode> result,CengTreeNode node, Integer key) {	
		if(node.getType()==CengNodeType.Internal) {
			// find corresponding subTree.
			CengTreeNode subTree = ((CengTreeNodeInternal)(node)).findSubtree(key);
			// push the subTree to the result array.
			result.add(subTree);
			// go to next subTree.
			searchHelper(result,subTree,key);
		}
		
		else if(node.getType()==CengNodeType.Leaf) {
			return;
		}
	}
	
	public void insert(CengTreeNode node, CengGift gift) {
		// the tree is empty.
		if(node == null) {
			root = new CengTreeNodeLeaf(null);
			((CengTreeNodeLeaf)root).addGifts(gift);
		}
		//case 1: given node is root and a leaf node.
		else if(node.getParent()==null && node.getType()== CengNodeType.Leaf) {
			insertCase1(node,gift);
		}
		
		//case 2: given node is just a leaf node.
		else if(node.getType()==CengNodeType.Leaf) {
			insertCase2(node,gift);
		}
		
		//case 3: given node is a internal node.
		else if (node.getType()==CengNodeType.Internal) {
			//find suitable child to go 
			CengTreeNode temp = ((CengTreeNodeInternal)(node)).findSubtree(gift.key());
			insert(temp,gift);
		}
	}
	
	public void insertCase1(CengTreeNode node, CengGift gift) {
		
		// there is enough space for new gift.
		if( (((CengTreeNodeLeaf)node).giftCount()) < 2*CengTreeNode.order) {
			((CengTreeNodeLeaf)node).addWithIndex(gift);
		}
		// there is no space for new gift.
		else {
			// insert normally the given gift
			((CengTreeNodeLeaf)node).addWithIndex(gift);
			// construct a new internal node which is going to be new root.
			CengTreeNodeInternal N = new CengTreeNodeInternal(null);
			root = N;
			// since node is not the root anymore, set its parent as new root.
			node.setParent(N);
			// push root's first key element into its "keys" array.
			N.pushKey(((CengTreeNodeLeaf)node).giftKeyAtIndex(CengTreeNode.order));
			// construct a new leaf node for split operation			
			CengTreeNodeLeaf newNode = new CengTreeNodeLeaf(node.getParent());
			((CengTreeNodeLeaf)node).splitLeafNode(newNode, CengTreeNode.order, 2*CengTreeNode.order);
			// Now , we have node and newLeaf as children. Let's push them as N's children.
			N.pushChildren(node);
			N.pushChildren(newNode);
		}
	
	}
	
	public void insertCase2(CengTreeNode node, CengGift gift) {
		// there is enough space for new gift.
		if( (((CengTreeNodeLeaf)node).giftCount()) < 2*CengTreeNode.order) {
			((CengTreeNodeLeaf)node).addWithIndex(gift);
		}
		//there is no space for new gift.
		else {
			((CengTreeNodeLeaf)node).addWithIndex(gift);
			// Construct a new leaf node whose parent is the parent of node.
			CengTreeNodeLeaf L = new CengTreeNodeLeaf(node.getParent());
			// Split the original node.
			((CengTreeNodeLeaf)node).splitLeafNode(L, CengTreeNode.order, 2*CengTreeNode.order);
			// Send new constructed child and the key value to the parent.		
			CengTreeNode newRoot = ((CengTreeNodeInternal)(node.getParent())).InternalInsert(L.giftKeyAtIndex(0),L);
			if(newRoot!=null)
			{	
				root = newRoot;
			}

		}
	}

	
}
