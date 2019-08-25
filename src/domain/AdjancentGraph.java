package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class AdjancentGraph {
	// @graph - entry has <key, edges[]>.
	static Map<String, ArrayList<String>> graph = new HashMap<String, ArrayList<String>>();
	static ArrayList<ArrayList<String>> pathList;

	static int minLevel = 0;
	static int totalNearBy = 0;
	static int maxDistance;

	/* @param domainSet - Given set D
	 * Constructs graph in form <key, edges[]> using map.
	 */
	public boolean load(Set<String> domainSet) {
		Iterator<String> itr = domainSet.iterator();
		while(itr.hasNext()) {
			String domain = itr.next();
			graph.put(domain, new ArrayList<String>());
			for (Entry<String, ArrayList<String>> pair : graph.entrySet()) {
				String currentKey = pair.getKey();

				// Inserts the edge only when edit distance is one.
				if(currentKey != domain && isUnitDistance(currentKey, domain)) {
					ArrayList<String> closest = graph.get(domain);
					if(!closest.contains(domain)) {
						closest.add(currentKey);
					}
					pair.getValue().add(domain);
				}
			}
		}
		return true;
	}

	public int editDistance(String domainName1, String domainName2) {
		doGraphTravesal(domainName1, domainName2);
		return minLevel - 1;
	}
	
	/*
	 * traverses and computes all possible sequence to reach destination from source.
	 */
	public String[] editPath(String domainName1, String domainName2) {
		doGraphTravesal(domainName1, domainName2);
		int totalPath = pathList.size();
		String[] allPath = new String[totalPath];
		for (int i = 0; i < totalPath; i++) { 		      
			allPath[i] = pathList.get(i).toString().replaceAll(",", " -> ");	
		}   		
		return allPath;
	}
	
	/*
	 * @param domainName - given domain name to start with
	 * @param distance - Limit within which edits can happen
	 */
	public int numNearby( String domainName, int distance ) {
		ArrayList<String> visited = new ArrayList<>();
		maxDistance = distance;
		traverseBydistance(domainName, visited, 0);
		return totalNearBy;
	}

	/*
	 * Initializes path and required variables to traverse the graph.
	 */
	private void doGraphTravesal(String source, String destination) {
		ArrayList<String> path = new ArrayList<>(); 
		ArrayList<String> visited = new ArrayList<>();
		path.add(source);
		pathList =  new ArrayList<>();
		traverseAllPath(source, destination, visited, path);
	}

	@SuppressWarnings("unchecked")
	/*
	 * @param visited - maintains a list of visited edges
	 * @param path - has intermediate constructed path
	 * 
	 * Inserts the path in pathList once the destination is reached.
	 */
	void traverseAllPath(String source, String destination, ArrayList<String> visited, ArrayList<String> path) {
		visited.add(source);
		
		// Adds the path to pathList if found and replaces minLevel if the currentlevel is lesser.
		if (source.equals(destination))  
		{ 
			pathList.add((ArrayList<String>) path.clone());
			visited.remove(source);
			int currentLevel = path.size();
			if(minLevel == 0 || minLevel > currentLevel) {
				minLevel = currentLevel;
			}
			return ; 
		} 

		ArrayList<String> closest = graph.getOrDefault(source, new ArrayList<String>());
		for (int i=0; i < closest.size(); i++) {
			String currentNode = closest.get(i);
			// If the node is not visited already it is been added to path continues the traverse.
			if (!visited.contains(currentNode)) 
			{ 
				path.add(currentNode); 
				traverseAllPath(currentNode, destination, visited, path); 
				path.remove(currentNode); 
			} 
		} 

		visited.remove(source);
	}

	/*
	 * @param currentLevel -  has intermediate level where the pointer is present.
	 */
	private void traverseBydistance(String source, ArrayList<String> visited, int currentLevel) {
		visited.add(source);
		if(currentLevel != 0) {
			if(currentLevel <= maxDistance) {
				// increases nearBy value when edit distance with limit is encountered.
				totalNearBy++;
			} else {
				// stops the traversal when the level exceed the limit.
				visited.remove(source);
				return;
			}
		}

		ArrayList<String> closest = graph.getOrDefault(source, new ArrayList<String>());
		closest.removeAll(visited);
		
		// traverses to next level by getting the closest string.
		for (int i=0; i < closest.size(); i++) {
			traverseBydistance( closest.get(i), visited, currentLevel+1); 
		} 

		visited.remove(source);
	}

	static boolean isUnitDistance(String str1, String str2) {
		int len1 = str1.length();
		int len2 = str2.length();
		if(len1 - len2 > 1) {
			return false;
		}

		int i=0, j=0, distance=0;

		while(i< len1 && j< len2) {
			if(str1.charAt(i) != str2.charAt(j)) {
				if(++distance > 1) { return false; }
				
				/* if strings are of same length, moves both cursor
				*  else moves the cursor of greater string length.
				*/
				if(len1 == len2) {
					i++;
					j++;
				}
				else if (len1 > len2) { 
					i++; 
				}
				else { 
					j++;
				}

			} else {
				// moves both the cursor if there is no mismatch in char
				i++;
				j++;
			}
		}

		return distance==1;
	}



}
