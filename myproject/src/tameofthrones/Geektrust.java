package tameofthrones;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import  java.util.*;
public class Geektrust {

	public static void main(String[] args)throws Exception {
		//Read input from file input.txt	
		String filePath=args[0];
		File file=new File(filePath);
		Scanner sc=new Scanner(file);
		String input[] = null;
		String output="SPACE ";
		int result=0;
		while(sc.hasNextLine())
		{			
			input=sc.nextLine().split(" ",2);
			String kingdomName=input[0].toUpperCase();
			String encoded_message=input[1].toUpperCase();
			
			//check if support of kingdom is already there
			if(!output.contains(kingdomName))
			{	
				//check support by decoding message
				if(decode(kingdomName,encoded_message.replaceAll("\\s","")))
				{
					result++;
					output=output+kingdomName +" ";
				}
			}
			
		}
		//if  kingdoms supporting is more than and equal to three, print their names
		if(result>=3)
			System.out.println(output);
		else
			System.out.println("NONE");   
		
	}

	private static boolean decode(String kingdomName, String encoded_message) {
		// TODO Auto-generated method stub
		final int alphabetCount=26;
		int [] trackArray=new int[alphabetCount];     // stores frequency of letters from decoded message 
		int [] emblemLetterCount=new int[alphabetCount];//stores frequency of letters from kingdom's emblem
		
		try {
			Class<?> kingclass= Class.forName("tameofthrones."+kingdomName);
			Constructor<?> constructor = kingclass.getConstructors()[0];		
			Kingdom object = (Kingdom) constructor.newInstance();
			int decodeFactor=object.getEmblemLength(); //number of letters from emblem used to decode encrypted message
			String emblem=object.Emblem;
			int letter,position;
			for(int i=0;i<encoded_message.length();i++)  //calculate frequency of letters from decoded message
			{
				letter=encoded_message.charAt(i);
				position=(letter-65-decodeFactor+26)%26;
				trackArray[position]+=1;
			}
			for(int i=0;i<decodeFactor;i++)            //calculate frequency of letters from emblem
			{
				letter=emblem.charAt(i);
				position=(letter-65)%26;
				emblemLetterCount[position]+=1;
			}
			for(int i=0;i<decodeFactor;i++)          //check presence of letters from emblem in decoded message
			{
				letter=emblem.charAt(i);
				position=(letter-65)%26;
				if(trackArray[position]<emblemLetterCount[position])
				   return false;
			}
			
			
			
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return true;
	}

}
