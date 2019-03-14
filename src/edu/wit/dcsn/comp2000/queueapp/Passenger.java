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
 * Representation of an individual who will travel via train between <i>from</i>
 * and <i>to</i> stations.
 */

package edu.wit.dcsn.comp2000.queueapp;

import java.io.FileNotFoundException ;		// for testing
import java.util.Arrays ;					// for testing
import java.util.Random ;					// for testing

import edu.wit.dcsn.comp2000.queueapp.Configuration.PairedLimit ;	// for testing


/**
 * Representation of an individual who will travel via train between <i>from</i>
 * and <i>to</i> stations.
 * 
 * @author	David M Rosenberg
 * @version	1.0.0	initial version
 * @version	1.0.1	minor fixes (don't affect behaviors)
 */
public final class Passenger
	{
	// class-wide/shared information
	private static int		nextId =	1 ;	// enables automatic id assignment
	
	// per-instance fields
	private final int		id ;			// unique id for this passenger
	
	private final Location	from ;
	private final Location	to ;
	
	private final int		startTime ;
	private int				boardTime ;
	private int				endTime ;
	
	/**
	 * @param fromLocation location of station where this passenger starts its journey  
	 * @param toLocation location of station where this passenger ends its journey
	 * @param currentTime will be used to mark the start of journey
	 */
	public Passenger( Location	fromLocation,
	                  Location	toLocation,
	                  int		currentTime
	                  )
    	{
    	id =							Passenger.nextId++ ;	// assign the next unique id
    	
    	// set up the rest of the fields
    	from =							fromLocation ;
    	to =							toLocation ;
    	
    	startTime =						currentTime ;
    	boardTime =						-1 ;
    	endTime =						-1 ;

    	}	// end 3-arg constructor


	/**
	 * Retrieves the starting station's location
	 * 
	 * @return the Location corresponding to the station where this passenger
	 *         began its journey
	 */
	public Location getFrom()
    	{
    	return from ;
    	}	// end getFrom()
	
	
	/**
	 * Retrieves the ending station's location
	 * 
	 * @return the Location corresponding to the station where this passenger
	 *         will end its journey
	 */
	public Location getTo()
    	{
    	return to ;
    	}	// end getTo()
	
	
	/**
	 * Retrieves the time the passenger started its journey
	 * 
	 * @return the time when this passenger started its journey
	 */
	public int getStartTime()
    	{
    	return startTime ;
    	}	// end getEndTime()
	
	
	/**
	 * Retrieves the time the passenger boarded a train
	 * 
	 * @return the time when this passenger boarded a train
	 */
	public int getTimeBoarded()
    	{
    	return boardTime ;
    	}	// end getTimeBoarded()

	
	/**
	 * Retrieves the time the passenger ended its journey
	 * 
	 * @return the time when this passenger ended its journey
	 */
	public int getEndTime()
    	{
    	return endTime ;
    	}	// end getEndTime()

	
	/**
	 * Sets the time the passenger boarded a train
	 * 
	 * @param currentTime the time the passenger boarded a train
	 */
	public void setTimeBoarded( int currentTime )
    	{
    	boardTime =						currentTime ;
    	}	// end setTimeBoarded()

	
	/**
	 * Sets the time the passenger reached its destination
	 * 
	 * @param currentTime the time the passenger reached its destination
	 */
	public void setTimeEnded( int currentTime )
    	{
    	endTime =						currentTime ;
    	}	// end setTimeEnded()

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
    	{
    	return String.format( "%s %,d", 
    	                      getClass().getSimpleName(),
    	                      id ) ;
    	}	// end toString()
	
	
	/**
	 * Superset of functionality of toString()
	 * 
	 * @return all instance information formatted for human consumption
	 */
	public String toStringFull()
    	{
    	return String.format( "%-15s\t: from %s to %s,; start @ %,d, board @ %,d, end @ %,d%n",
    	                      toString(),
    	                      from,
    	                      to,
    	                      startTime,
    	                      boardTime,
    	                      endTime
    	                      ) ;
    	}	// end toStringFull()
	

	/**
	 * Unit test driver
	 * 
	 * @param args -unused-
	 * @throws FileNotFoundException see {@link Configuration#Configuration()}
	 */
	public static void main( String[] args ) throws FileNotFoundException
    	{
    	Configuration	theConfig =		new Configuration() ;
    	
    	TrainRoute		theRoute =		new TrainRoute( theConfig.getRoute() ) ;
    	int[]			theStationSpecs =	theConfig.getStations() ;
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
    	
    	
    	minimumPassengers =			thePassengerSpecs[ Configuration.PASSENGERS_PER_TICK ].minimum ;
        maximumPassengers =			thePassengerSpecs[ Configuration.PASSENGERS_PER_TICK ].maximum ;
        
        int		simulationLoops =	theConfig.getTicks() ;
        
        for( int currentTime = 1; currentTime <= simulationLoops; currentTime++ )
        	{
        	newPassengerCount =		minimumPassengers == maximumPassengers 
                        				? minimumPassengers
                        				: pseudoRandom.nextInt( maximumPassengers - minimumPassengers )
                        					+ minimumPassengers + 1 ;
        
        	System.out.printf( "%,5d: Generating %d passengers (per-tick):%n",
        	                   currentTime,
        	                   newPassengerCount ) ;
        
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
                				      currentTime
                					  ) ;
                System.out.printf( "\t%s%n",
                                   aPassenger.toStringFull()
                				   ) ;
                }	// end for()
            }	// end for()

    	}	// end test driver main()

	}	// end class TrainRoute
