/*
 * Dave Rosenberg 
 * Comp 2000 - Data Structures 
 * Lab 3: Queue application - Train Simulation 
 * Spring, 2019
 * 
 * Usage restrictions:
 * 
 * You may use this code for exploration, experimentation, and furthering your
 * learning for this course. You may not use this code for any other
 * assignments, in my course or elsewhere, without explicit permission, in
 * advance, from myself (and the instructor of any other course). Further, you
 * may not post or otherwise share this code with anyone other than current
 * students in my sections of this course. Violation of these usage restrictions
 * will be considered a violation of the Wentworth Institute of Technology
 * Academic Honesty Policy.
 */

/**
 * Logger utility. This class is designed to function in a static fashion,
 * enabling it to be used throughout the simulation without needing to pass an
 * instance around. However, this limits the usefulness of this class to an
 * environment which uses exactly one log file.
 * 
 * <p>
 * <b>WARNING:</b> Because the log file's PrintWriter is static, care must be
 * taken to <i>close()</i> the logger or it is likely to get truncated.
 * 
 * <p>
 * Note: You may use this class, with or without modification, in your Comp
 * 2000, Queue application/Train Simulation solution. You must retain all
 * authorship comments. If you modify this, add your authorship to mine.
 */

package edu.wit.dcsn.comp2000.queueapp ;


import java.io.FileNotFoundException ;
import java.io.PrintWriter ;


/**
 * Logger utility. This class is designed to function in a static fashion,
 * enabling it to be used throughout the simulation without needing to pass an
 * instance around. However, this limits the usefulness of this class to an
 * environment which uses exactly one log file.
 * 
 * <p>
 * <b>WARNING:</b> Because the log file's PrintWriter is static, care must be
 * taken to <i>close()</i> the logger or it is likely to get truncated.
 * 
 * <p>
 * Note: You may use this class, with or without modification, in your Comp
 * 2000, Queue application/Train Simulation solution. You must retain all
 * authorship comments. If you modify this, add your authorship to mine.
 * 
 * @author David M Rosenberg
 * @version 1.1.0	track enhancement to Configuration
 * @version 1.0.0	initial version
 */
public final class Logger
	{

	// only one log can be open at a time
	private static PrintWriter log = null ;


	/**
	 * prevent instantiation
	 */
	private Logger()
		{
		}


	/**
	 * Creates and opens the log file TrainSimulation.log in the default
	 * location (root folder). If the file already exists, it is replaced.
	 * 
	 * @throws FileNotFoundException most likely cause is the file is open in
	 *             another application
	 */
	public static void create() throws FileNotFoundException
		{
		log = new PrintWriter( "./data/TrainSimulation.log" ) ;
		}


	/**
	 * Closes the log file, releasing all allocated resources.
	 */
	public static void close()
		{
		log.close() ;
		log = null ;
		}


	/**
	 * Writes all of the configuration parameters into the log
	 * 
	 * @param theConfiguration the configuration for this execution
	 */
	public static void logConfiguration( Configuration theConfiguration )
		{
		// introduce the information
		Logger.write( "\n----------\nConfiguration:\n" ) ;

		// let Configuration format the output
		Logger.write( theConfiguration.toString() ) ;

		// separate the configuration from the rest of the log
		Logger.write( "----------\n" ) ;

		}


	/**
	 * Appends the provided text to the log, including a terminating newline.
	 * 
	 * @param logEntry the text to append to the log.
	 */
	public static void write( String logEntry )
		{
		log.println( logEntry ) ;
		}


	/**
	 * Unit test driver.
	 * 
	 * @param args -unused-
	 * @throws FileNotFoundException refer to {@link #create()}
	 */
	public static void main( String[] args ) throws FileNotFoundException
		{
		System.out.println( "Starting Logger testing\n" ) ;

		System.out.println( "create the log and insert an intro message" ) ;

		Logger.create() ;		// create the file

		Logger.write( "Start Logger test" ) ;

		// we'll write the configuration to the log file
		System.out.println( "dump the configuration into the log" ) ;
		Logger.logConfiguration( new Configuration() ) ;

		System.out.println( "insert concluding message and close the log" ) ;
		Logger.write( "Finish Logger test" ) ;

		Logger.close() ;		// release the resources

		System.out.println( "\nFinished Logger testing" ) ;
		}

	}
