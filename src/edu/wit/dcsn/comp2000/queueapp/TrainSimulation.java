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

/**
 * @author Your Name
 * @version 1.0.0
 */
public class TrainSimulation
	{

	/**
	 * @param args -unused-
	 */
	public static void main( String[] args ) throws FileNotFoundException
		{
		Configuration config = new Configuration();
		TrainRoute route = new TrainRoute(config.getRoute());

		int[]			theStationSpecs =	config.getStations() ;
		Configuration.PairedLimit[]	thePassengerSpecs =	config.getPassengers() ;

		System.out.printf( "Using configurations:%n\t%-20s\t: %s%n\t%-20s\t: %s%n",
				"Stations",
				Arrays.toString( theStationSpecs ),
				"Passengers",
				Arrays.toString( thePassengerSpecs )
		) ;

		// create a pseudo-random number generator instance
		Random pseudoRandom =	new Random( config.getSeed() ) ;

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

		// TODO: makee the passengers' directions random
		for( int passengerCount = 1; passengerCount <= newPassengerCount; passengerCount++ )
		{
			Passenger	aPassenger =
					new Passenger(
							new Location(
									route,
									theStationSpecs[ pseudoRandom.nextInt( theStationSpecs.length ) ],
									Direction.NOT_APPLICABLE
							),
							new Location(
									route,
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

		int		simulationLoops =	config.getTicks() ;

		// Main loop
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
										route,
										theStationSpecs[ pseudoRandom.nextInt( theStationSpecs.length ) ],
										Direction.NOT_APPLICABLE
								),
								new Location(
										route,
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

		}

	}
