package rescuecore2.worldmodel;

/**
   A type-safe ID class for entities. IDs are really just integers.
 */
public final class EntityID {
	 
	private final int id;
	//éléments nessécaire au path finfing par swarm vers les victimes
    private double pheromoneCivilians;
    private double evaporationRateCivilians;
    
    //éléments nessaires au pathfinding par swarm vers les blacages 
    private double evaporationRateBlockades;
    private double pheromoneBlockades;
    
  //éléments nessaires au pathfinding par swarm vers les batiments unexplorés
    private double evaporationRateBuildings;
    private double pheromoneBuildings;
    

    /**
       Construct a new EntityID object.
       @param id The numeric ID to use.
     */
    public EntityID(int id) {
        this.id = id;
        this.pheromoneBlockades = 0;
        this.pheromoneCivilians = 0;
        this.evaporationRateBlockades = 0.2; 
        this.evaporationRateCivilians = 0.2;
        this.evaporationRateBuildings=0.2;
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
    
    // Ensemble de méthodes relatives aux phéromones permttant la recherches de civils ////////////////////////////////////////////:
    /**
     * add 1 to the pheromoneCivilians value
     */
    public void pheromoneCiviliansUpdate() {
    	pheromoneCivilians +=1;
    }
    
    /**
     * Get the number of pheromoneCivilians
     * @return The number of pheromones 
     */
    public double getPheromoneCivilians() {
    	return pheromoneCivilians;
    }
    
    /**
     * Represent the pheromone evaporation
     */
    public void evaporationCivilians() {
    	pheromoneBlockades -= evaporationRateCivilians;
    }
    // Ensemble de méthodes relatives aux phéromones permttant la recherches de ////////////////////////////////////////////:

    /**
     * add 1 to the pheromoneBlockades value
     */
    public void pheromoneBlockadesUpdate() {
    	pheromoneBlockades+=1;
    }
    
    /**
     * Get the number of pheromonesBlockades 
     * @return The number of pheromones 
     */
    public double getPheromoneBlockades() {
    	return pheromoneBlockades;
    }

    /**
     * Represent the pheromone evaporation
     */
    public void evaporationBlockades() {
    	pheromoneBlockades -= evaporationRateBlockades;
    }
    
    // Ensemble de méthodes relatives aux phéromones permttant la recherches de batiments ////////////////////////////////////////////:

    /**
     * add 1 to the pheromoneBuildings value
     */
    public void pheromoneBuidingsUpdate() {
    	pheromoneBuildings+=1;
    }
    
    /**
     * Get the number of pheromonesBuildings
     * @return The number of pheromones 
     */
    public double getPheromoneBuildings() {
    	return pheromoneBuildings;
    }

    /**
     * Represent the pheromoneBuildings evaporation
     */
    public void evaporationBuildings() {
    	pheromoneBlockades -= evaporationRateBlockades;
    }
    
      /**
       Get the numeric ID for this object.
       @return The numeric ID.
     */
    public int getValue() {
        return id;
    }
    
    /**
     * Set the evaportaionRateCivilians value
     * @param evaporationRate
     */
    public void setEvaporationRateCivilians(double evaporationRate) {
    	this.evaporationRateCivilians=evaporationRate;
    }
    
    /**
     * Set the evaportaionRateCivilians value
     * @param evaporationRate
     */
    public void setEvaporationRateBlockades(double evaporationRate) {
    	this.evaporationRateBlockades=evaporationRate;
    }
    
    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
