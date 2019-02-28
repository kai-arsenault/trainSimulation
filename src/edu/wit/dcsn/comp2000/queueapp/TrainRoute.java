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
 * Representation of a train route consisting of a pair of parallel tracks,
 * Station(s) at fixed locations along the tracks, and Train(s) at varying
 * locations along the tracks. Each Station serves both tracks. No two Stations
 * are at the same location. Two trains may be at the same location however only
 * one per track.
 * 
 * <p>
 * NOTE: This class is incomplete - you may want to restructure it based on your
 * implementation's requirements.
 * 
 * <p>
 * <b>WARNING</b>: Some CIRCULAR route functionality is not yet implemented!
 * 
 * <p>
 * Note: You may use this class, with or without modification, in your Comp
 * 2000, Queue application/Train Simulation solution. You must retain all
 * authorship comments. If you modify this, add your authorship to mine.
 */

package edu.wit.dcsn.comp2000.queueapp;

import java.io.FileNotFoundException ;
import java.util.ArrayList ;

import edu.wit.dcsn.comp2000.queueapp.Configuration.RouteSpec ;


/**
 * Representation of a train route consisting of a pair of parallel tracks,
 * Station(s) at fixed locations along the tracks, and Train(s) at varying
 * locations along the tracks. Each Station serves both tracks. No two Stations
 * are at the same location. Two trains may be at the same location however only
 * one per track.
 * 
 * <p>
 * NOTE: This class is incomplete - you may want to restructure it based on your
 * implementation's requirements.
 * 
 * <p>
 * <b>WARNING</b>: Some CIRCULAR route functionality is not yet implemented!
 * 
 * <p>
 * Note: You may use this class, with or without modification, in your Comp
 * 2000, Queue application/Train Simulation solution. You must retain all
 * authorship comments. If you modify this, add your authorship to mine.
 * 
 * @author David M Rosenberg
 * @version 1.0.0	base version
 */
public final class TrainRoute
	{
	// class-wide/shared information
	private static int		nextId =	1 ;	// enables automatic id assignment
	
	// per-instance fields
	private final int		id ;			// unique id for this train route
	
	private final RouteStyle style ;
	private final int		length ;
	
	private ArrayList< Station > stations =	null ;
	private ArrayList< Train > trains =	null ;
	
	
	/**
	 * @param routeSpecification the route style and length from the
	 *            configuration file
	 */
	public TrainRoute( RouteSpec routeSpecification )
    	{
    	id =							TrainRoute.nextId++ ;	// assign the next unique id
    	
    	// save the configuration parameters
    	style =							routeSpecification.style ;
    	length =						routeSpecification.length ;
    	
    	}	// end 1-arg constructor
	
	
	/**
	 * Retrieves the route length as specified in the configuration file
	 * 
	 * @return the route length was set when the route was instantiated
	 */
	public int getLength()
    	{
    	return length ;
    	}	// end getLength()
	
	
	/**
	 * Retrieves the route style as specified in the configuration file
	 * 
	 * @return the route style was set when the route was instantiated
	 */
	public RouteStyle getStyle()
    	{
    	return style ;
    	}	// end getStyle()
	
	
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
	 * Determines the direction an entity needs to travel to move from the
	 * 'from' station to the 'to' station. It takes into account the route
	 * style.
	 * 
	 * @param fromStationId starting point station id #
	 * @param toStationId ending point station id #
	 * @return the Direction in which an entity must travel
	 */
	public Direction whichDirection( int fromStationId,
	                                 int toStationId )
		{
		return whichDirection( stations.get( fromStationId ).getLocation(),
		                       stations.get( toStationId ).getLocation()
								) ;
		}


	/**
	 * Determines the direction an entity needs to travel to move from the
	 * 'from' station to the 'to' station. It takes into account the route
	 * style.
	 * 
	 * @param fromStation starting point station instance
	 * @param toStation ending point station instance
	 * @return the Direction in which an entity must travel
	 */
	public Direction whichDirection( Station fromStation,
	                                 Station toStation )
		{
		return whichDirection( fromStation.getLocation(),
		                       toStation.getLocation() ) ;
		}


	/**
	 * Determines the Direction an entity must move to travel between
	 * <i>from</i> and <i>to</i>
	 * 
	 * <p>
	 * <b>WARNING</b>: As implemented, this method only works correctly for
	 * LINEAR routes!
	 * 
	 * @param fromLocation the Location at the start of travel
	 * @param toLocation the Location at the destination
	 * @return the Direction in which an entity must travel
	 */
	public Direction whichDirection( Location fromLocation,
	                                 Location toLocation )
    	{
    	Direction calculatedDirection	= Direction.NOT_APPLICABLE ;
    	if( fromLocation.getRoute().equals( toLocation.getRoute() ) )	// same route so continue
    		{
    		int comparison				= fromLocation.compareTo( toLocation ) ;
    		if( comparison < 0 ) 
    			{
    			calculatedDirection		= Direction.OUTBOUND ;
    			}
    		else if ( comparison > 0 )
    			{
    			calculatedDirection		= Direction.INBOUND ;
    			}
    		else							// at same location
    			{
    			calculatedDirection		= Direction.STATIONARY ;
    			}
    		}
//    	else	// DMR TODO - to support multi-route simulation, need to enhance to 
    			//			find a location to transfer

    	return calculatedDirection ;
    	}
	
	
	
	// TODO complete this
	
	
	
	/**
	 * Unit test driver
	 * 
	 * @param args -unused-
	 * @throws FileNotFoundException see {@link Configuration#Configuration()}
	 */
	public static void main( String[] args ) throws FileNotFoundException
    	{
    	Configuration	theConfig			= new Configuration() ;
    	RouteSpec		theRouteSpec		= theConfig.getRoute() ;
    	
    	TrainRoute		theRoute			= new TrainRoute( theRouteSpec ) ;
    	
    	System.out.printf( "Using configuration:%n\t%s%n",
    	                   theRouteSpec
    	                   ) ;
    	System.out.printf( "The result is:%n\t%s is %s with length %,d%n",
    	                   theRoute,
    	                   theRoute.getStyle(),
    	                   theRoute.getLength()
    	                   ) ;
    	
    	System.out.println() ;
    	Location		fromLocation		= new Location( theRoute, 3, Direction.NOT_SPECIFIED ) ;
    	Location		toLocation			= new Location( theRoute, 3, Direction.NOT_SPECIFIED ) ;
    	System.out.printf( "From: %s --> to: %s --> is: %s%n",
    	                   fromLocation,
    	                   toLocation,
    	                   theRoute.whichDirection( fromLocation, toLocation ) ) ;
    	
    	toLocation.setPosition( 5 );
    	System.out.printf( "From: %s --> to: %s --> is: %s%n",
    	                   fromLocation,
    	                   toLocation,
    	                   theRoute.whichDirection( fromLocation, toLocation ) ) ;
    	
    	toLocation.setPosition( 1 );
    	System.out.printf( "From: %s --> to: %s --> is: %s%n",
    	                   fromLocation,
    	                   toLocation,
    	                   theRoute.whichDirection( fromLocation, toLocation ) ) ;
    	
//    	// DMR TODO - support for handling CIRCULAR routes is not implemented
//    	System.out.println() ;
//    	RouteSpec		circularRouteSpec	= theConfig.new RouteSpec( RouteStyle.CIRCULAR, 20 ) ;
//    	TrainRoute		circularRoute		= new TrainRoute( circularRouteSpec ) ;
//    	fromLocation.setRoute( circularRoute );
//    	toLocation.setRoute( circularRoute );
//    	toLocation.setPosition( 3 );
//    	System.out.printf( "From: %s --> to: %s --> is: %s%n",
//    	                   fromLocation,
//    	                   toLocation,
//    	                   circularRoute.whichDirection( fromLocation, toLocation ) ) ;
    	
    	
    	// DMR TODO - need to test remaining methods
    	
    	}	// end test driver main()

	}	// end class TrainRoute
