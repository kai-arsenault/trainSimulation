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
 * Generic positional information for an entity.
 * 
 * <p>
 * Note: You may use this class, with or without modification, in your Comp
 * 2000, Queue application/Train Simulation solution. You must retain all
 * authorship comments. If you modify this, add your authorship to mine.
 */

package edu.wit.dcsn.comp2000.queueapp;

import java.io.FileNotFoundException ;


/**
 * Generic positional information for an entity
 * 
 * <p>
 * Note: You may use this class, with or without modification, in your Comp
 * 2000, Queue application/Train Simulation solution. You must retain all
 * authorship comments. If you modify this, add your authorship to mine.
 * 
 * @author David M Rosenberg
 * @version 1.0.0	initial version
 */
public final class Location implements Comparable<Location>
	{
	// class-wide/shared information
	private static int	nextId =		1 ;	// enables automatic id assignment
	
	// per-instance fields
	private final int	id ;				// unique id for this location
	
	private TrainRoute	onRoute ;			// enable callback to TrainRoute information
												// to support multiple routes, make this an
												// array 
	private int			atPosition ;		// TrainRoute-centric position in 1..Route.length
	private Direction	inDirection ;		// TrainRoute-centric direction
	

	/**
	 * Sets up the new instance referencing the provided Route, a position of 1
	 * (beginning of the Route), and OUTBOUND for a LINEAR route, CLOCKWISE for
	 * a CIRCULAR route, NOT_SPECIFED otherwise
	 * 
	 * @param theRoute the Route on which this Location exists
	 */
	public Location( TrainRoute theRoute )
    	{
    	this( theRoute, 
    	      1, 
    	      theRoute.getStyle() == RouteStyle.LINEAR 
    	      	? Direction.OUTBOUND 
    	      	: theRoute.getStyle() == RouteStyle.CIRCULAR 
    	      		? Direction.CLOCKWISE 
    	      		: Direction.NOT_SPECIFIED
    			) ;
    	}	// end 1-arg constructor
	
	
	/**
	 * Sets up the new instance referencing the provided Route, position, and
	 * direction
	 * 
	 * @param theRoute the Route on which this Location exists
	 * @param initialPosition the position on the Route in the range
	 *            1..theRoute.length
	 * @param initialDirection the direction in which this entity will travel
	 */
	public Location( TrainRoute theRoute,
	                 int initialPosition,
	                 Direction initialDirection )
    	{
    	id =							Location.nextId++ ;
    	
    	onRoute =						theRoute ;
    	atPosition =					initialPosition ;
    	inDirection =					initialDirection ;
    	}	// end 3-arg constructor
	
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo( Location otherLocation )
		{
		return this.atPosition - otherLocation.atPosition ;
		}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object otherObject )
    	{
    	if( this == otherObject )	// they're the same instance
    		{
    		return true ;
    		}
    	
    	if( otherObject == null )	// nothing to compare to
    		{
    		return false ;
    		}
    	
    	
    	boolean		flag =				false ;
    	
    	if( otherObject instanceof Location )	// can only be equal if they're the same class
    		{
    		Location otherLocation =	(Location) otherObject ;
    		
    		flag =						this.onRoute.equals( otherLocation.onRoute ) &&
    									( this.atPosition == otherLocation.atPosition ) ;
    		}
    	
    	return flag ;
    	}	// end equals()
	
	
	/**
	 * @return the entity's direction
	 */
	public Direction getDirection()
		{
		return inDirection ;
		}	// end getDirection()

	
	/**
	 * @return the id
	 */
	public int getId()
		{
		return id ;
		}	// end getId()


	/**
	 * @return the entity's position on the route
	 */
	public int getPosition()
		{
		return atPosition ;
		}	// end getPosition()

	
	/**
	 * @return the entity's route
	 */
	public TrainRoute getRoute()
		{
		return onRoute ;
		}	// end getRoute()

	
	/**
	 * Tests for position at either end of the route
	 * 
	 * @return true if in position 1 or route length; false otherwise
	 */
	public boolean isAtEnd()
    	{
    	boolean		flag =				false ;
    	
    	if( onRoute.getStyle() == RouteStyle.LINEAR )
    		{
    		if( ( ( atPosition == 1 ) &&
    			  ( inDirection == Direction.INBOUND ) ) ||
    			( ( atPosition == onRoute.getLength() ) &&
    			  ( inDirection == Direction.OUTBOUND ) ) )
    			{
    			flag =					true ;
    			}
    		}	// end if( linear )
    	else if( onRoute.getStyle() == RouteStyle.CIRCULAR )
    		{
    		if( ( ( atPosition == 1 ) &&
    			  ( inDirection == Direction.COUNTER_CLOCKWISE ) ) ||
    			( ( atPosition == onRoute.getLength() ) &&
    			  ( inDirection == Direction.CLOCKWISE ) ) )
    			{
    			flag =					true ;
    			}
    		}	// end if( circular )
    	
    	return 	flag ;
    	}	// end isAtEnd()
	
	
	/**
	 * Updates the location to reflect moving one position in the current
	 * direction, reversing direction (linear) or wrapping (circular) if at an
	 * 'end'.
	 */
	public void move()
    	{
		if ( ( onRoute.getStyle() == RouteStyle.LINEAR ) ||
			 ( onRoute.getStyle() == RouteStyle.CIRCULAR ) )
			{
			if( isAtEnd() )
				{
				if( onRoute.getStyle() == RouteStyle.LINEAR )
					{
					reverse();
					}
				else if( onRoute.getStyle() == RouteStyle.CIRCULAR )
					{
					atPosition =		atPosition == 1
											? onRoute.getLength()
											: 1 ;
					}
				}
			else	// not at an end
				{
				if( ( inDirection == Direction.OUTBOUND ) ||
					( inDirection == Direction.CLOCKWISE ) )	// moving 'up'
					{
					atPosition++ ;
					}
				else	// moving 'down'
					{
					atPosition-- ;
					}
				}	// end if/else( at end )
			}	// end if/else( LINEAR or CIRCULAR )
    	}	// end move()
	
	
	/**
	 * Reverses the direction of travel
	 */
	public void reverse()
    	{
    	inDirection =					inDirection.reverse() ;
    	}
	
	
	/**
	 * @param newDirection the new direction for this entity to travel
	 */
	public void setDirection( Direction newDirection )
		{
		inDirection =					newDirection ;
		}	// end setDirection()

	
	/**
	 * @param newPosition the new position along the route for this entity
	 */
	public void setPosition( int newPosition )
		{
		atPosition =					newPosition ;
		}	// end setPosition()

	
	/**
	 * @param newRoute the new route for this entity
	 */
	public void setRoute( TrainRoute newRoute )
		{
		onRoute =						newRoute ;
		}	// end setRoute()

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
    	{
    	return String.format( "%s at position %,d on %s",
    	                      inDirection.toString(),
    	                      atPosition,
    	                      onRoute.toString() ) ;
    	}	// end toString()
	
	
	/**
	 * Unit test driver
	 * 
	 * @param args -unused -
	 * @throws FileNotFoundException {@link Configuration#Configuration()}
	 */
	public static void main( String[] args ) throws FileNotFoundException
		{
		Configuration	theConfig =		new Configuration() ;
		TrainRoute		aRoute =		new TrainRoute( theConfig.getRoute() ) ;
		TrainRoute		anotherRoute =	new TrainRoute( theConfig.getRoute() ) ;
		
		Location		where =			new Location( aRoute ) ;
		Location		here =			new Location( aRoute,
		        		    			                aRoute.getLength() / 2,
		        		    			                Direction.OUTBOUND
		        		    			                ) ;
		Location		alsoHere =		new Location( aRoute,
		        		    			                aRoute.getLength() / 2,
		        		    			                Direction.INBOUND
		        		    			                ) ;
		Location		elsewhere =		new Location( anotherRoute,
		        		         		                anotherRoute.getLength() / 2,
		        		         		                Direction.INBOUND
		        		         		                ) ;
		Location		couldBeAStation = new Location( anotherRoute,
		        		               	                Math.min( anotherRoute.getLength(), 3 ),
		        		               	                Direction.STATIONARY
		        		               	                ) ;
		
		// DMR TODO - tests are poorly organized and barely commented
		System.out.printf( "%-20s%s%n", aRoute, theConfig.getRoute() ) ;
		System.out.printf( "%-20s%s%n", "where:", where ) ;
		System.out.printf( "%-20s%s%n", "here:", here ) ;
		System.out.printf( "%-20s%s%n", "alsoHere:", alsoHere ) ;
		System.out.printf( "%-20s%s%n", "elsewhere:", elsewhere ) ;
		System.out.printf( "%-20s%s%n", "couldBeAStation:", couldBeAStation ) ;
		
		where.reverse();
		System.out.printf( "%n%-20s(reversed direction)%s%n", "where:", where ) ;
		couldBeAStation.reverse();
		System.out.printf( "%-20s(reversed direction)%s%n", "couldBeAStation:", couldBeAStation ) ;
		
		System.out.println() ;
		
		System.out.printf( "%-30s: %b%n", "here.equals( here )", here.equals( here ) ) ;
		System.out.printf( "%-30s: %b%n", "here == here", ( here == here ) ) ;
		System.out.printf( "%-30s: %b%n", "here.equals( alsoHere )", here.equals( alsoHere ) ) ;
		System.out.printf( "%-30s: %b%n", "here == alsoHere", ( here == alsoHere ) ) ;
		System.out.printf( "%-30s: %b%n", "where.equals( here )", where.equals( here ) ) ;
		System.out.printf( "%-30s: %b%n", "where == here", ( where == here ) ) ;
		System.out.printf( "%-30s: %b%n", "alsoHere.equals( elsewhere )", 
		                   									alsoHere.equals( elsewhere ) ) ;
		System.out.printf( "%-30s: %b%n", "here.equals( aRoute )", here.equals( aRoute ) ) ;
		
		System.out.println() ;
		
		Location		endAndMoveTest = new Location(anotherRoute, 1, Direction.OUTBOUND ) ;
		System.out.println( "Linear route:" ) ;
		
		System.out.printf( "%-30s: %s%n", "endAndMoveTest", endAndMoveTest ) ;
		System.out.printf( "\tis at end: %b%n", endAndMoveTest.isAtEnd() ) ;
		
		endAndMoveTest.atPosition		= endAndMoveTest.getRoute().getLength() ;
		System.out.printf( "%-30s: %s%n", "endAndMoveTest", endAndMoveTest ) ;
		System.out.printf( "\tis at end: %b%n", endAndMoveTest.isAtEnd() ) ;
		
		endAndMoveTest.atPosition /=	2 ;				// roughly the middle
		System.out.printf( "%-30s: %s%n", "endAndMoveTest", endAndMoveTest ) ;
		System.out.printf( "\tis at end: %b%n", endAndMoveTest.isAtEnd() ) ;
		
		endAndMoveTest.atPosition =		1 ;
		endAndMoveTest.inDirection =	Direction.INBOUND ;
		
		System.out.printf( "%-30s: %s%n", "endAndMoveTest", endAndMoveTest ) ;
		System.out.printf( "\tis at end: %b%n", endAndMoveTest.isAtEnd() ) ;
		
		endAndMoveTest.atPosition		= endAndMoveTest.getRoute().getLength() ;
		System.out.printf( "%-30s: %s%n", "endAndMoveTest", endAndMoveTest ) ;
		System.out.printf( "\tis at end: %b%n", endAndMoveTest.isAtEnd() ) ;
		
		endAndMoveTest.atPosition /=	2 ;				// roughly the middle
		System.out.printf( "%-30s: %s%n", "endAndMoveTest", endAndMoveTest ) ;
		System.out.printf( "\tis at end: %b%n", endAndMoveTest.isAtEnd() ) ;
		
		System.out.println() ;
		
		endAndMoveTest.atPosition =		1 ;
		endAndMoveTest.inDirection =	Direction.INBOUND ;
		
		System.out.printf( "%-30s: %s%n", "endAndMoveTest", endAndMoveTest ) ;
		endAndMoveTest.move() ;
		System.out.printf( "\tmoved to: %s%n", endAndMoveTest ) ;
		
		endAndMoveTest.atPosition =		endAndMoveTest.getRoute().getLength() ;
		System.out.printf( "%-30s: %s%n", "endAndMoveTest", endAndMoveTest ) ;
		endAndMoveTest.move() ;
		System.out.printf( "\tmoved to: %s%n", endAndMoveTest ) ;
		
		endAndMoveTest.atPosition /=	2 ;				// roughly the middle
		System.out.printf( "%-30s: %s%n", "endAndMoveTest", endAndMoveTest ) ;
		endAndMoveTest.move() ;
		System.out.printf( "\tmoved to: %s%n", endAndMoveTest ) ;
		
		endAndMoveTest.reverse() ;						// reverse direction
		endAndMoveTest.atPosition =		1 ;
		System.out.printf( "%-30s: %s%n", "endAndMoveTest", endAndMoveTest ) ;
		endAndMoveTest.move() ;
		System.out.printf( "\tmoved to: %s%n", endAndMoveTest ) ;
		
		endAndMoveTest.atPosition =		endAndMoveTest.getRoute().getLength() ;
		endAndMoveTest.inDirection =	Direction.INBOUND ;
		
		System.out.printf( "%-30s: %s%n", "endAndMoveTest", endAndMoveTest ) ;
		endAndMoveTest.move() ;
		System.out.printf( "\tmoved to: %s%n", endAndMoveTest ) ;
		
		endAndMoveTest.atPosition /=	2 ;				// roughly the middle
		endAndMoveTest.inDirection =	Direction.OUTBOUND ;
		System.out.printf( "%-30s: %s%n", "endAndMoveTest", endAndMoveTest ) ;
		endAndMoveTest.move() ;
		System.out.printf( "\tmoved to: %s%n", endAndMoveTest ) ;
		
		System.out.println() ;
		System.out.println( "Circular route:" ) ;
		
		TrainRoute		circularRoute =	
							new TrainRoute( theConfig.new RouteSpec( RouteStyle.CIRCULAR, 20 ) ) ;
		Location		circularEndAndMoveTest =
										new Location( circularRoute, 1, Direction.CLOCKWISE ) ;
		
		System.out.printf( "%-30s: %s%n", "circularEndAndMoveTest", circularEndAndMoveTest ) ;
		System.out.printf( "\tis at end: %b%n", circularEndAndMoveTest.isAtEnd() ) ;
		
		circularEndAndMoveTest.atPosition = circularEndAndMoveTest.getRoute().getLength() ;
		System.out.printf( "%-30s: %s%n", "circularEndAndMoveTest", circularEndAndMoveTest ) ;
		System.out.printf( "\tis at end: %b%n", circularEndAndMoveTest.isAtEnd() ) ;
		
		circularEndAndMoveTest.atPosition /= 2 ;		// roughly the middle
		System.out.printf( "%-30s: %s%n", "circularEndAndMoveTest", circularEndAndMoveTest ) ;
		System.out.printf( "\tis at end: %b%n", circularEndAndMoveTest.isAtEnd() ) ;
		
		circularEndAndMoveTest.atPosition = 1 ;
		circularEndAndMoveTest.inDirection = Direction.COUNTER_CLOCKWISE ;
		
		System.out.printf( "%-30s: %s%n", "circularEndAndMoveTest", circularEndAndMoveTest ) ;
		System.out.printf( "\tis at end: %b%n", circularEndAndMoveTest.isAtEnd() ) ;
		
		circularEndAndMoveTest.atPosition = circularEndAndMoveTest.getRoute().getLength() ;
		System.out.printf( "%-30s: %s%n", "circularEndAndMoveTest", circularEndAndMoveTest ) ;
		System.out.printf( "\tis at end: %b%n", circularEndAndMoveTest.isAtEnd() ) ;
		
		circularEndAndMoveTest.atPosition /= 2 ;		// roughly the middle
		System.out.printf( "%-30s: %s%n", "circularEndAndMoveTest", circularEndAndMoveTest ) ;
		System.out.printf( "\tis at end: %b%n", circularEndAndMoveTest.isAtEnd() ) ;
		
		System.out.println() ;
		
		circularEndAndMoveTest.atPosition = 1 ;
		System.out.printf( "%-30s: %s%n", "circularEndAndMoveTest", circularEndAndMoveTest ) ;
		circularEndAndMoveTest.move() ;
		System.out.printf( "\tmoved to: %s%n", circularEndAndMoveTest ) ;
		
		circularEndAndMoveTest.atPosition = circularEndAndMoveTest.getRoute().getLength() ;
		System.out.printf( "%-30s: %s%n", "circularEndAndMoveTest", circularEndAndMoveTest ) ;
		circularEndAndMoveTest.move() ;
		System.out.printf( "\tmoved to: %s%n", circularEndAndMoveTest ) ;
		
		circularEndAndMoveTest.atPosition /= 2 ;		// roughly the middle
		System.out.printf( "%-30s: %s%n", "circularEndAndMoveTest", circularEndAndMoveTest ) ;
		circularEndAndMoveTest.move() ;
		System.out.printf( "\tmoved to: %s%n", circularEndAndMoveTest ) ;
		
		circularEndAndMoveTest.reverse() ;				// reverse direction
		circularEndAndMoveTest.atPosition = 1 ;
		System.out.printf( "%-30s: %s%n", "circularEndAndMoveTest", circularEndAndMoveTest ) ;
		circularEndAndMoveTest.move() ;
		System.out.printf( "\tmoved to: %s%n", circularEndAndMoveTest ) ;
		
		circularEndAndMoveTest.atPosition = circularEndAndMoveTest.getRoute().getLength() ;
		System.out.printf( "%-30s: %s%n", "circularEndAndMoveTest", circularEndAndMoveTest ) ;
		circularEndAndMoveTest.move() ;
		System.out.printf( "\tmoved to: %s%n", circularEndAndMoveTest ) ;
		
		circularEndAndMoveTest.atPosition = circularEndAndMoveTest.getRoute().getLength() / 2 ;
														// roughly the middle
		System.out.printf( "%-30s: %s%n", "circularEndAndMoveTest", circularEndAndMoveTest ) ;
		circularEndAndMoveTest.move() ;
		System.out.printf( "\tmoved to: %s%n", circularEndAndMoveTest ) ;
		
		}	// end test driver main()

	}	// end class Location
