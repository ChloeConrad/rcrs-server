package rescuecore2.worldmodel;

/**
   A type-safe ID class for entities. IDs are really just integers.
 */
public final class EntityID {
	
    private final int id;
    private double pheromone;
    private double evaporationRate;

    /**
       Construct a new EntityID object.
       @param id The numeric ID to use.
     */
    public EntityID(int id) {
        this.id = id;
        this.pheromone = 0;
        this.evaporationRate = 0.2; 
        }

    @Override
    public boolean equals(Object o) {
        if (o instanceof EntityID) {
            return this.id == ((EntityID)o).id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }
    
    /**
     * add 1 to the pheromone value
     */
    public void pheromoneUpdate() {
    	pheromone +=1;
    }
    
    /**
     * Get the number of pheromones 
     * @return The number of pheromones 
     */
    public double getPheromone() {
    	return pheromone;
    }
    
    /**
     * Represent the pheromone evaporation
     */
    public void evaporation() {
    	pheromone-=evaporationRate;
    }
      /**
       Get the numeric ID for this object.
       @return The numeric ID.
     */
    public int getValue() {
        return id;
    }
    
    /**
     * Set the evaportaionRate value
     * @param evaporationRate
     */
    public void setEvaporationRate(double evaporationRate) {
    	this.evaporationRate=evaporationRate;
    }
    
    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
