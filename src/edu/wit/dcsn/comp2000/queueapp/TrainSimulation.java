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

package edu.wit.dcsn.comp2000.queueapp;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;

import edu.wit.dcsn.comp2000.queueapp.Configuration.PairedLimit;

/**
 * @author Your Name
 * @version 1.0.0
 */
public class TrainSimulation {

	/**
	 * @param args -unused-
	 */
	public static void main(String[] args) throws FileNotFoundException {
    	Configuration	theConfig =		new Configuration() ;
    	
    	
    	/*
    	 * Station stuff
    	 */
    	TrainRoute		theRoute =		new TrainRoute( theConfig.getRoute() ) ;
    	int[]			theStationSpecs =	theConfig.getStations() ;
    	
    	System.out.printf( "Using configuration:%n\t%s%n",
    	                   Arrays.toString( theStationSpecs )
    	                   ) ;
    	
    	System.out.println( "The result is:" ) ;
    	
    	for( int stationPosition : theStationSpecs )
    		{
    		Station		aStation =		new Station( theRoute, stationPosition ) ;
    		System.out.printf( "\t%s is %s%n",
    		                   aStation,
    		                   aStation.getLocation()
    						) ;
    		}	// end foreach()
    	
    	
    	/*
    	 * Passenger stuff
    	 */
    	PairedLimit[]	thePassengerSpecs =	theConfig.getPassengers() ;
    	
    	System.out.printf( "Using configurations:%n\t%-20s\t: %s%n\t%-20s\t: %s%n",
    	                   "Stations",
    	                   Arrays.toString( theStationSpecs ),
    	                   "Passengers",
    	                   Arrays.toString( thePassengerSpecs )
    	                   ) ;
    	
    	// create a pseudo-random number generator instance
    	Random			pseudoRandom =	new Random( theConfig.getSeed() ) ;
    	
    	int				minimumPassengers =
    							thePassengerSpecs[ Configuration.PASSENGERS_INITIAL ].minimum ;
    	int				maximumPassengers =
    							thePassengerSpecs[ Configuration.PASSENGERS_INITIAL ].maximum ;
    	int				newPassengerCount =	
    							minimumPassengers == maximumPassengers 
    								? minimumPassengers
    								: pseudoRandom.nextInt( maximumPassengers - minimumPassengers )
    										+ minimumPassengers + 1 ;
    	
    	System.out.printf( "Generating %d passengers (initial):%n",
    	                   newPassengerCount ) ;
    	System.out.println( "Note: Same from/to possible - additional work required to ensure they're different." ) ;
    	
    	// create initial 50 passengers
    	for( int passengerCount = 1; passengerCount <= newPassengerCount; passengerCount++ )
    		{
    		Passenger	aPassenger =
							new Passenger( 
							        new Location(
    			                             theRoute,
    			                             theStationSpecs[ pseudoRandom.nextInt( theStationSpecs.length ) ],
    			                             Direction.NOT_APPLICABLE
    			                             ),
        			                new Location(
    			                             theRoute,
    			                             theStationSpecs[ pseudoRandom.nextInt( theStationSpecs.length ) ],
    			                             Direction.NOT_APPLICABLE
    			                             ),
        			                0	// current time indicates that clock hasn't started
        			                ) ;
    		System.out.printf( "\t%s%n",
    		                   aPassenger.toStringFull()
    						) ;
    		}	// end for()


	}

}
