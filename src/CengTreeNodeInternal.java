import java.util.ArrayList;

public class CengTreeNodeInternal extends CengTreeNode
{
	private ArrayList<Integer> keys;
	private ArrayList<CengTreeNode> children;	
	
	public CengTreeNodeInternal(CengTreeNode parent) 
	{
		super(parent);
		this.type = CengNodeType.Internal;
		keys = new ArrayList<Integer>();
		children = new ArrayList<CengTreeNode>();
	}
	
	// GUI Methods - Do not modify
	public ArrayList<CengTreeNode> getAllChildren()
	{
		return this.children;
	}
	public Integer keyCount()
	{
		return this.keys.size();
	}
	public Integer keyAtIndex(Integer index)
	{
		if(index >= this.keyCount() || index < 0)
		{
			return -1;
		}
		else
		{
			return this.keys.get(index);			
		}
	}
	
	// Extra Functions


	public void addNewKey(int key, CengTreeNode child) {
		// insert the new key into keys array , and insert the child into children array.
		int i=0;
		for(Integer K:keys) {
			if(K>key) { // if key is smaller than any existing key (K)
				keys.add(i, key);
				children.add(i+1, child);
				return; // after insertion return immediately.
			}
			i++;
		}
		// if no insertion is done, append key and child at the end.
		keys.add(key);
		children.add(child);
	}
	
	
	public void pushKey(Integer key) {
		keys.add(key);
	}
	
	public void pushChildren(CengTreeNode child) {
		children.add(child);
	}
	
	public CengTreeNode findSubtree(Integer key) {
		if(key < keys.get(0)) {
			return children.get(0);
		}
		else {
			int i = 0;
			for(Integer K:keys) {
				if(key<K) {
					return children.get(i);
				}
				i++;
			}
		}
		return children.get(children.size()-1);
	}

	public void printKeys(int i){
		int j = i;
		while(j!=0){
			System.out.print("\t");
			j--;
		}
		System.out.println("<index>");
		for(Integer key:keys){
			j=i;
			while(j!=0){
				System.out.print("\t");
				j--;
			}
			System.out.println(key);
		}
		j=i;
		while(j!=0){
			System.out.print("\t");
			j--;
		}
		System.out.println("</index>");
	}
	
	public void printChildrenSize() {
			System.out.println(children.size());
	}

	public CengTreeNodeInternal InternalInsert(Integer key, CengTreeNode child) {
		//there is enough space in parent internal node to insert a new key and a child.
		CengTreeNodeInternal R = null;
		
		if(this.keyCount()<2*CengTreeNode.order) {
			addNewKey(key,child);
			//return null;
		}
		// if part for internal node is also the root
		else if(this.getParent()==null) {
			
			addNewKey(key,child);
			R = new CengTreeNodeInternal(null);
			this.setParent(R);
			CengTreeNodeInternal I = new CengTreeNodeInternal(R);
			Integer toGo = this.keyAtIndex(CengTreeNode.order);
			//split keys array
			splitKeys(I,CengTreeNode.order,2*CengTreeNode.order); // (order,2*order]
			//split children array
			splitChildren(I,CengTreeNode.order); // divide into 2.
			
			R.pushKey(toGo);
			R.pushChildren(this);
			R.pushChildren(I);
			
			return R;
		}
		else {
			addNewKey(key,child);
			CengTreeNodeInternal I = new CengTreeNodeInternal(this.getParent());
			// get pushed key
			Integer toGo = this.keyAtIndex(CengTreeNode.order);
			//split keys array
			splitKeys(I,CengTreeNode.order,2*CengTreeNode.order); // (order,2*order]
			//split children array
			splitChildren(I,CengTreeNode.order); // divide into 2.
			//send the information of new child to parent
			R = ((CengTreeNodeInternal)(this.getParent())).InternalInsert(toGo,I);
			//return null;
		}
		return R;
	}

	public void splitKeys(CengTreeNodeInternal I, int from , int to) {
		int i=0;
		for(Integer K: this.keys) {
			if(i>from && i<=to) {
				I.pushKey(K);
			}
			i++;
		}
		for(i=0;i<this.keys.size();) { //remove the copied keys
			if(i>=from && i<=to) {
				this.keys.remove(i);
			}
			else {i++;}	
		}
	}
	
	public void splitChildren(CengTreeNodeInternal I, int order) {
		int i=0;
		// split the children array into 2
		for(CengTreeNode child : children) {
			if(i>order) {
				// for newly constructed internal node, reset its children's parent!
				// before push the child into its new position reset its parent.
				child.setParent(I);
				// push the child its new position.
				I.pushChildren(child);
			}
			i++;
		}
		// remove copied children
		for(i=0;i<children.size();) {
			if(i>order) {
				this.children.remove(i);
			}
			else i++;
		}
	}

}
