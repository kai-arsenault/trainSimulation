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
 * Representation of a train on a train route. A Train has a fixed, limited
 * capacity to carry Passengers. Passengers board() and disembark().
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

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Collections;

import edu.wit.dcsn.comp2000.queueapp.Configuration.TrainSpec;

/**
 * Representation of a train on a train route. A Train has a fixed, limited
 * capacity to carry Passengers. Passengers board() and disembark().
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
 * @author Kai Arsenault
 * @author Ernest Shedden
 * @version 1.0.0 base version
 */
public final class Train {
	// class-wide/shared information
	private static int nextId = 1; // enables automatic id assignment

	// per-instance fields
	private final int id; // unique id for this train route

	private final int capacity;
	private Location currentLocation;
	private ArrayList<Passenger> passengers;

	/**
	 * @param onRoute            the instance of the TrainRoute on which this Train
	 *                           operates
	 * @param trainSpecification the specifications from the configuration file
	 */
	public Train(TrainRoute onRoute, TrainSpec trainSpecification) {
		id = Train.nextId++; // assign the next unique id

		// create an empty collection to hold Passengers while they're on board
		passengers = new ArrayList<>();

		// save the configuration parameters
		capacity = trainSpecification.capacity;
		currentLocation = new Location(onRoute, trainSpecification.location, trainSpecification.direction);

	} // end 2-arg constructor
	/**
	 * Creates a copy of the object passed to it
	 * 
	 * @param source the object to be copied
	 */
	public Train(Train source) {
		id = source.getID();
		capacity = source.getCapacity();
		currentLocation = new Location(source.getLocation().getRoute(), source.getLocation().getPosition(), source.getLocation().getDirection());
	} // end 1-arg constructor

	/**
	 * Retrieves the capacity (maximum number of Passengers simultaneously on this
	 * train) as specified in the configuration file
	 * 
	 * @return the capacity was set when the train was instantiated
	 */
	public int getCapacity() {
		return capacity;
	} // end getCapacity()

	/**
	 * Retrieves the current location along a route
	 * 
	 * @return the current location object
	 */
	public Location getLocation() {
		return currentLocation;
	} // end getLocation()
	
	/**
	 * Test moving of a train
	 */
	public void moveTempTrain() {
		currentLocation.move();
	} // end moveTempTrain()

	/**
	 * Moves the train one position forward.
	 * 
	 * @param stationList List of stations
	 */
	public void moveTrain(ArrayList<Station> stationList) {
		currentLocation.move();
		Station aStation = TrainSimulation.getStation(stationList, this.getLocation());
		if (aStation != null) // Train arrived at a station
			Logger.write(String.format("%s arrived at %s's %s platform on %s carrying %s passenger(s)%n", this,
					aStation.toString(), currentLocation.getDirection(), currentLocation.getRoute(),
					this.getPopulation()));
		else // Train did not yet arrive at a station
			Logger.write(String.format("%s is %s carrying %s passenger(s)%n", this, this.getLocation(),
					this.getPopulation()));
	} // end moveTrain()
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("%s %,d", getClass().getSimpleName(), id);
	} // end toString()

	/**
	 * Retrieves the train id
	 * 
	 * @return the id of the train
	 */
	public int getID() {
		return id;
	}

	/**
	 * Retrieves the current number of passengers on train
	 * 
	 * @return the current number of passengers on train
	 */
	public int getPopulation() {
		return passengers.size();
	}

	/**
	 * Retrieves the current number of empty spots on train
	 * 
	 * @return the number of remaining empty spots on train
	 */
	public int getSeatsRemaining() {
		return getCapacity() - getPopulation();
	}

	/**
	 * Adds all passengers on station platform to train's capacity
	 * 
	 * @param station the current station the train is located
	 */
	public void board(Station station) {
		Passenger[] incomingPassengers = station.getPassengers(currentLocation.getDirection(), getSeatsRemaining());
		int incomingPassengerSize = incomingPassengers.length;
		passengers.addAll(Arrays.asList(incomingPassengers));

		Logger.write(String.format("%s passenger(s) added to %s at %s%n", incomingPassengerSize, this.toString(),
				station.toString()));
	}

	/**
	 * Removes all passengers that are supposed to get off a passed location
	 * 
	 * @param station the current station the train is located
	 */
	public void disembark(Station station) {
		int passengersRemoved = 0;
		for (int i = 0; i < passengers.size(); i++) {
			if (passengers.get(i).getTo().equals(station.getLocation())) {
				passengers.remove(i);
				passengersRemoved++;
			}
		}

		Logger.write(String.format("%s passenger(s) removed from %s at %s%n", passengersRemoved, this.toString(),
				station.toString()));
	}

	/**
	 * Unit test driver
	 * 
	 * @param args -unused-
	 * @throws FileNotFoundException see {@link Configuration#Configuration()}
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Configuration theConfig = new Configuration();
		TrainRoute theRoute = new TrainRoute(theConfig.getRoute());
		TrainSpec[] theTrainSpecs = theConfig.getTrains();

		System.out.printf("Using configuration:%n\t%s%n", Arrays.toString(theTrainSpecs));

		System.out.println("The result is:");

		for (TrainSpec aTrainSpecification : theTrainSpecs) {
			Train aTrain = new Train(theRoute, aTrainSpecification);
			System.out.printf("\t%s is %s with capacity %,d%n", aTrain, aTrain.currentLocation, aTrain.capacity);
		} // end foreach()

	} // end test driver main()

} // end class TrainRoute
