import java.util.List;
import java.util.Random;
/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class Animal
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    private int age;
    private static final Random rand = Randomizer.getRandom();
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
        age = 0;
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);

    /**
     * returns max age of this animal
     * @return max age of this animal
     */
    abstract protected int getMaxAge();
    
    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
    
    /**
     * return's animals age
     * @return the animals age
     */
    protected int getAge()
    {
        return age;
    }
    
    /**
     * sets animals age
     * @param int age, sets an age value for this animal
     */
    protected void setAge(int age)
    {
        this.age = age;
    }
    
    /**
     * Return the breeding age of the animal
     * @return the breeding age of the animal
     */
    abstract protected int getBreedingAge();
    
    /**
     * Return the breeding probability of the animal
     * @return the breeding probability of the animal
     */
    abstract protected double getBreedingProbability();
    
    /**
     * Return the max litter size of the animal
     * @return the max litter size of the animal
     */
    abstract protected int getMaxLitterSize();
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }
    
    /**
     * A animal can breed if it has reached the breeding age.
     */
    protected boolean canBreed()
    {
        return age >= getBreedingAge();
    }
    
    /**
     * raises this animals age by 1. Checks to see if animal hits 
     * max age, if true sets animal to dead
     */
    protected void incrementAge()
    {
        age++;
        if(age > getMaxAge()) {
            setDead();
        }
    }
  
}
