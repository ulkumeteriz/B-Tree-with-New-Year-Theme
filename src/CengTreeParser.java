import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CengTreeParser 
{	
	// You need to parse the input file in order to use GUI tables.
	// Parse the input file, and convert them into CengGifts
	public static ArrayList<CengGift> parseGiftsFromFile(String filename)
	{
		ArrayList<CengGift> giftList = new ArrayList<CengGift>();
		String content = null;
		try {
			content = new String(Files.readAllBytes(Paths.get(filename)));
		} catch (IOException e) {
			//e.printStackTrace();
		}
		String[] lines = content.split(System.lineSeparator());
		for(String line:lines) {
			//System.out.print(line);
			String[] giftContent = line.split("\\|");
			int key = Integer.parseInt(giftContent[0]);
			CengGift newGift = new CengGift(key,giftContent[1],giftContent[2],giftContent[3]);
			//System.out.print(newGift.fullName());
			giftList.add(newGift);
		}
		return giftList;
	}
	
	// Start listening and parsing command line -System.in-.
	// There are 4 commands:
	// 1) quit : End the app, gracefully. Print nothing, call nothing, just break off your command line loop.
	// 2) add : Parse and create the gift, and call CengChristmasList.addGift(newlyCreatedGift).
	// 3) search : Parse the key, and call CengChristmasList.searchGift(parsedKey).
	// 4) print : Print the whole tree, call CengChristmasList.printTree().
	// Commands (quit, add, search, print) are case-insensitive.
	public static void startParsingCommandLine() throws IOException
	{	
		//take input stream, prepare it for BufferedReader class
		InputStreamReader isr = new InputStreamReader(System.in);		
		BufferedReader br = new BufferedReader(isr);	
		String inline;
	
	myloop:
		while((inline = br.readLine()) != null) {
			String command[] = inline.split("\\|");
			int key = 0 ;
			switch(command[0].toLowerCase()) {
				case "add":
					key = Integer.parseInt(command[1]);
					CengGift newGift = new CengGift(key,command[2],command[3],command[4]);
					CengChristmasList.addGift(newGift);
					break;
				
				case "search" :
					key = Integer.parseInt(command[1]);
					CengChristmasList.searchGift(key);
					break;
				
				case "print":
					CengChristmasList.printTree();
					break;
				
				case "quit":
					// break the outer loop
					break myloop; 
			}
		}
	}
}
