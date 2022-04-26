package sample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import rescuecore2.standard.entities.Area;
import rescuecore2.standard.entities.StandardWorldModel;
import rescuecore2.worldmodel.EntityID;

public class AntSearchPolice extends AntSearchRcrs{
	
	public AntSearchPolice(StandardWorldModel world ) {
		super(world);
	}
	
	/**
	 * Méthode permettant de définir un chemin entre l'entité start et une des entités goal en utilisant un algorithme d'essaim 
	 * @param start
	 * @param goals
	 * @return le chemin choisi sous forme d'une liste d'EntityID
	 */
	public List<EntityID> seachBySwarm( EntityID start,
		      Collection<EntityID> goals ){
		List<EntityID> path = new ArrayList<EntityID>();
		EntityID currentStart = start;
		boolean findGoals = false;
		boolean lastGoal = false;
		
		//Test si je suis sur un de mes objectifs 
		if(goals.contains(start)) {
			path.add(start);
			start.pheromoneUpdate();
			return path;
		}
		
		
		do {
			Set<EntityID> possibilities = graph.get(currentStart);
			EntityID nextStart = currentStart;
			double maxProba = Float.MIN_VALUE;
			for(EntityID voisin : possibilities) {
				//Si le voisin fait parti des objectifs de l'agent findGoal devient True et je sors de la boucle de recherche d'objectif
				if(goals.contains(voisin)) {
					findGoals = true;
					nextStart = voisin;
					
					break;
				}
				
				//recherche du voisin avec le plus de phéromones et qui n'a pas déja été pris par l'agent 
				double proba = voisin.getPheromone()/pheromoneSomme(possibilities);
				if(proba>maxProba && !path.contains(voisin)) {
					maxProba = proba;
					nextStart = voisin;
				}
			}
			
			//Si aucun voisin n'a de phéromone, choisi un chemin random + test si chemin n'a pas déja été emprunté 
			if(maxProba==0)
				do {
					nextStart = super.randomWay(possibilities);
					
				}while(path.contains(nextStart));
			path.add(nextStart);
			currentStart = nextStart;
		}while(!findGoals);
		
		//mise à jour des phéromones 
		for(EntityID step : path) 
			step.pheromoneUpdate();
			
		
		
		return path;
		 
	 }
	
	
}
