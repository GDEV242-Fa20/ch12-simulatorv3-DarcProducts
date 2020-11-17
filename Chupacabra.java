import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * scaly greenish-gray skin and sharp spines or quills running down its back.
 * eats rabbits, foxes, and other Chupucabra. Should spread out and give the 
 * foxes a hard time but shouldn't exterminate any species.
 *
 * @author Craig Hussey
 * @version 11.16.2020
 */
public class Chupacabra extends Animal
{
    // Characteristics shared by all Chupacabra (class variables).
    
    // The age at which a Chupacabra can start to breed.
    private static final int BREEDING_AGE = 6;
    // The age to which a Chupacabra can live.
    private static final int MAX_AGE = 66;
    // The likelihood of a Chupacabra breeding.
    private static final double BREEDING_PROBABILITY = 0.058;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;
    // The food value of a single piece of food. In effect, this is the
    // number of steps a Chupacabra can go before it has to eat again.
    private static final int FOOD_VALUE = 7;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).

    // The Chupacabra's food level, which is increased by eating food.
    private int foodLevel;

    /**
     * Create a Chupacabra. A Chupacabra can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the Chupacabra will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Chupacabra(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            foodLevel = rand.nextInt(FOOD_VALUE);
        }
        else {
            setAge(0);
            foodLevel = FOOD_VALUE;
        }
    }
    
    /**
     * This is what the Chupacabra does most of the time: it hunts for
     * food. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newChupacabra A list to return newly born Chupacabra.
     */
    public void act(List<Animal> newChupacabra)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newChupacabra);            
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }
    
    /**
     * Make this Chupacabra more hungry. This could result in the Chupacabra's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    /**
     * Look for food adjacent to the current location.
     * Only the first live food is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            //eats rabbits, foxes, and other Chupacabra
            if(animal instanceof Rabbit || animal instanceof Fox || animal instanceof Chupacabra) {
                Animal thisAnimal = (Animal)animal;
                if(thisAnimal.isAlive()) { 
                    thisAnimal.setDead();
                    foodLevel = FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }
    
    /**
     * Check whether or not this Chupacabra is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newChupacabra A list to return newly born Chupacabra.
     */
    private void giveBirth(List<Animal> newChupacabra)
    {
        // New Chupacabra are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Chupacabra young = new Chupacabra(false, field, loc);
            newChupacabra.add(young);
        }
    }
        
    /**
     * Returns the breeding age of this Chupacabra
     * @return the breeding age of this Chupacabra
     */
    protected int getBreedingAge()
    {
        return BREEDING_AGE;
    }
    
    /**
     * returns this animals breeding probability
     * @return this animals breeding probability
     */
    protected double getBreedingProbability()
    {
        return BREEDING_PROBABILITY;
    }
    
    /**
     * returns this animals max litter size
     * @return this animals max litter size
     */
    protected int getMaxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }
    
    /**
     * returns this animals max age
     * @return this animals max age
     */
    protected int getMaxAge()
    {
        return MAX_AGE;
    }

}
