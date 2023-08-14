package Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Elevator {

	
	private static final int MIN_FLOOR = 0;
	private static final int MAX_FLOOR = 10;
	private static int processingTime = 500;
	
	private int currentFloor;
	private Direction currentDirection;
	
	private Map<Integer, List<Integer>> requestedPathsMap;
	
	private Boolean[] currentFloorDestinations;
	
	public Elevator() {
		this.currentFloor = 0;
		this.currentDirection = Direction.UP;
		this.requestedPathsMap = new HashMap<>();
		this.currentFloorDestinations = new Boolean[MAX_FLOOR + 1];
		Arrays.fill(this.currentFloorDestinations, Boolean.FALSE);
	}
	
	public void processingTime(int processingTime) {
		Elevator.processingTime = processingTime;
	}
	
	
	public int getCurrentFloor() {
		return this.currentFloor;
	}
	
	public Map<Integer, List<Integer>> getRequestedPathSMap() {
		return this.requestedPathsMap;
	}
	public Boolean[] getCurrentDirection() {
		return this.currentFloorDestinations;
	}
	
	public void start() throws InterruptedException {
			currentDirection = Direction.UP;
			
			do {
				System.out.println("--------");
				processFloor(currentFloor);
				System.out.println("--------");
			}while (currentDirection !=Direction.IDLE);
			
			System.out.println(" No one is waiting and " + " no one is looking to go anywhere");
			System.out.println(" Chilling for now ");
	}
	
	public void lunchTimeElevatorRush() {
		Random random = new Random();
		for(int i = 0; i < 30;i++) {
			callElevator(random.nextInt(11),
						random.nextInt(10) + 1);
		}
		
	}
	  public void callElevator(int start,int destination) {
		  if(isInvalidFloor(start) || isInvalidFloor (destination) || start == destination) {
			  System.out.println("INVALID FLOORS. Try again");
			  return;
		  }if(requestedPathsMap.containsKey(start))
			  requestedPathsMap.get(start).add(destination);
		  else {
			  requestedPathsMap.put(start, new ArrayList<>() {{ 
				  add(destination);
			  }});
			  }
		  }
	  
	  private void processFloor(int floor) throws InterruptedException{
		  if(currentFloorDestinations[floor])
			  System.out.println("UN-BOARDING at Floor : " + floor);
		  if(requestedPathsMap.containsKey(floor)) {
			  System.out.println("BOARDING at floor :  " + floor);
		  		requestedPathsMap.get(floor).forEach(destinationFloor -> currentFloorDestinations[destinationFloor]=true);
		  		requestedPathsMap.remove(floor);
		  		
		  }
		  currentFloorDestinations[floor] = false;
		  moveElevator();
		  
	  }
	  		public void moveElevator() throws InterruptedException {
	  			if(!Arrays.asList(currentFloorDestinations).contains(true) && requestedPathsMap.isEmpty()) {
	  				currentDirection = Direction.IDLE;
	  				return;
	  			}else if(isInvalidFloor(currentFloor + 1)) {
	  				currentDirection = Direction.DOWN;
	  			}else if(isInvalidFloor(currentFloor - 1)) {
	  				currentDirection = Direction.UP;
	  			}
	  			switch(currentDirection) {
	  			case UP:{
	  			  moveUp();
	  			    break;
	  			}case DOWN:{
	  				moveDown();
	  				break;
	  			}default:{
	  				System.out.println("Elevator Malfunctioned");
	  			}
	  				
	  			}
	  		}
	  	
	  		private void moveUp() throws InterruptedException{
	  			currentFloor++;
	  			System.out.println("GOING UP TO "+ currentFloor);
	  		    Thread.sleep(processingTime);
	  		}
	  		private void moveDown() throws InterruptedException{
	  			currentFloor--;
	  			System.out.println("GOING DOWN TO " + currentFloor);
	  		    Thread.sleep(processingTime);
	  		}
	  		private boolean isInvalidFloor(int floor) {
	  			return floor < MIN_FLOOR || floor > MAX_FLOOR;
	  		}
	  }

