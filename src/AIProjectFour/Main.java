package AIProjectFour;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	private static java.util.Scanner input;
	private static BufferedWriter writer;
	
	private static int[][] array;
	private static ArrayList<Integer> moves = new ArrayList<Integer>();
	private static ArrayList<Integer> sticks = new ArrayList<Integer>();
	
	// main( String args[] ) - the driver for the program  
	public static void main( String args[] ) throws FileNotFoundException 
	{
		// Print welcome message to user
		System.out.println("Welcome to the game of NIM!");
		
		// Print instructions to game to user
		System.out.println("There is a pile of 10 sticks.");
		System.out.println("When it is your turn you may pick up 1, 2 or 3 sticks.");
		System.out.println("The person to pick up the final stick wins!");
		System.out.print("Press enter to begin.");
		try
        {
            System.in.read();
        }  
        catch(Exception e)
        {}  
		
		// Play game
		playNIM();
		
		// Print final message to user
		System.out.println("Thank you for playing the game of NIM!");
	}
	
	// playNIM() - runs the game of NIM
	private static void playNIM() throws FileNotFoundException
	{
		// Create or check for file 
		ArrayList<Integer> values = readFile(openFile());
		createArray( values );
	
		// Play NIM until there are no sticks left in the pile
		int numOfSticks = 10;
		int currPlayer = 0;
		int winner = 0;
		while(numOfSticks > 0)
		{
			// Who's turn is it? (computer = 0, human = 1)
			if(currPlayer == 0)
			{
				// Computer's turn to make a move
				int sticks = computerMove( numOfSticks );
				numOfSticks -= sticks;
				
				// Check if player is winner
				if(numOfSticks == 0)
				{
					winner = 0;
				}
				
				// Next player's turn 
				currPlayer = 1;
			}
			else 
			{
				// User's turn to make a move
				int sticks = userMove( numOfSticks );
				numOfSticks -= sticks;
				
				// Check if player is winner
				if(numOfSticks == 0)
				{
					winner = 1;
				}
				
				// Next player's turn 
				currPlayer = 0;
			}
		}
		
		// Print out winner of the game
		if(winner == 0)
		{
			System.out.println();
			System.out.println("You lose! Better luck next time.");
		}
		else
		{
			System.out.println();
			System.out.println("Great job! You win!");
		}
		rewriteFile( winner );
	}
	
	// computerMove() - the computer returns how many sticks to take from the pile. 
	public static int computerMove( int numOfSticks )
	{
		// Add the number of sticks left to ArrayList
		sticks.add( numOfSticks );
		
		// Find the max number in array
		int max = array[1][numOfSticks];
		int index = 1;
		for(int i = 2; i < 4; i++)
		{
			if(max < array[i][numOfSticks])
			{
				max = array[i][numOfSticks];
				index = i;
			}
		}
		// Add the index number to the ArrayList
		moves.add( index );
		
		// Return the number of sticks the computer will take
		return index;
	}
	
	// userMove() - the user returns how many sticks to take from the pile.  
	public static int userMove( int numOfSticks )
	{
		// Asks the user how many sticks they would like to take
		System.out.println();
		System.out.println("There are " + numOfSticks + " in the pile."); 
		System.out.print("Enter the number of sticks you want to take from the pile: ");
		input = new java.util.Scanner(System.in);	
		int numberOfSticks = input.nextInt();
		
		// Return the user input 
		return numberOfSticks;
	}
	
	// openFile( String fileName ) - opens a file 
	public static File openFile( ){
		File file = null;
		try
		{
			file = new File( "updatedValues.txt" );
			if( !file.exists() )
			{
				file = new File("baseValues.txt");
			}
		}
		catch( Exception e ){ }	
			
		return file;
	}
	
	// readFile( ) - reads the file into an ArrayList
	public static ArrayList<Integer> readFile( File file ) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)));
		
		// Creates an ArrayList<Integer> of numbers for grid from file
		ArrayList<Integer> values = new ArrayList<Integer>();
		while( scanner.hasNext() )
		{

			if( scanner.hasNextInt() )
			{
				values.add(scanner.nextInt());
			}
			else
			{
				scanner.next();
			}
		}
		scanner.close();
		return values;
	}
	
	// createArray( ) - creates an array from the file 
	public static  void createArray( ArrayList<Integer> values )
	{
		array = new int[4][11];
		for( int i = 0; i < 4; i++ )
		{
			for( int j = 0; j < 11; j++ )
			{
				array[i][j] = values.get( 0 );
				values.remove( 0 );
			}
		}
	}
	
	// rewriteFile( ) - rewrites the File 
	public static void rewriteFile( int winner ) 
	{
		if( winner == 0 )
		{
			for(int i = 0; i < moves.size(); i++)
			{
				array[moves.get(i)][sticks.get(i)] += 1;
			}
		}
		else{
			for(int i = 0; i < moves.size(); i++)
			{
				array[moves.get(i)][sticks.get(i)] -= 1;
			}
		}
		try 
		{
			 writer = new BufferedWriter( new FileWriter("updatedValues.txt", false) );
		}
		catch( Exception e )
		{
			System.out.println("Error in writing file.");
		}
		
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 11; j++)
			{
				write( array[i][j] );
			}
			writeNewLine();
		}
	}
	
	// write( ) - writes an integer to the text file 
	public static void write(int num)
	{
		try
		{
			writer.write(num + " ");
			writer.flush();
		}
		catch( Exception e){ }
	}
	
	// writeNewLine( ) - writes a new line in the file
	public static void writeNewLine()
	{
		try
		{
			writer.newLine();
		}
		catch( Exception e){ }
	}
}
