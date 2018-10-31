import java.util.ArrayList;

public class CengTreeNodeLeaf extends CengTreeNode
{
	private ArrayList<CengGift> gifts;
	
	public CengTreeNodeLeaf(CengTreeNode parent) 
	{
		super(parent);
		this.type = CengNodeType.Leaf;
		gifts = new ArrayList<CengGift>() ;
	}
	
	// GUI Methods - Do not modify
	public int giftCount()
	{
		return gifts.size();
	}
	public Integer giftKeyAtIndex(Integer index)
	{
		if(index >= this.giftCount())
		{
			return -1;
		}
		else
		{
			CengGift gift = this.gifts.get(index);
			
			return gift.key();
		}
	}
	
	// Extra Functions
	
	public void printGifts(int i) {
		int j;
		j=i;
		while(j!=0){
			System.out.print("\t");
			j--;
		}
		System.out.println("<data>");
		for(CengGift gift:gifts) {
			j=i;
			while(j!=0){
				System.out.print("\t");
				j--;
			}
			System.out.println("<record>"+gift.fullName()+"</record>");
		}
		j=i;
		while(j!=0){
			System.out.print("\t");
			j--;
		}
		System.out.println("</data>");
		
	}
	
	public void addGifts(CengGift newGift) {
		gifts.add(newGift);
	}
	
	public CengGift findGift(int key){
		
		for(CengGift gift:gifts) {
			if(gift.key()==key) {
				return gift;
			}
		}	
		return null;
	}
	
	public void addWithIndex(CengGift newGift) {
		int index = 0;
		for(CengGift existingGift:gifts) {
			// newGift is inserted in first position.
			if(gifts.get(0).key() > newGift.key()) {
				gifts.add(0,newGift);
				break;
			}
			// find suitable position for newGift.
			else if (existingGift.key() < newGift.key() && index < gifts.size()) {
				index++;
				// there is no key smaller than the newGift.key()
				if (index == gifts.size()) {
					gifts.add(newGift);
					break;
				}
			}
			// newGift is inserted in its suitable position.
			else if (existingGift.key()>newGift.key()) {	
				gifts.add(index,newGift);
				index=0;
				break;
			}
		}
	}


	public void splitLeafNode(CengTreeNodeLeaf newNode,Integer from , Integer to) {
		int i=0;
		for(CengGift gift: this.gifts) { //copy gifts
			if(i>=from && i<=to) {
				if(i==from) {
					// copy the first one.
					newNode.addGifts(gift);
				}
				else {
					//copy others.
					newNode.addWithIndex(gift);
				}
			}
			i++;
		}
		
		for(i=0;i<this.gifts.size();) { //remove the copied gifts
			if(i>=from && i<=to) {
				this.gifts.remove(i);
			}
			else {i++;}	
		}
	}

}
