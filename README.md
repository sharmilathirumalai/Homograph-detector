## Homoglyph-detector
   Based on IDN homograph attacks [1], a program to find all such domain names in a given set 'D' is implemented. Homographanes are identified by computing edit distance.
  
**Note:** Hamming Distance can also be used to solve this problem.
   
## Data Structure
Adjacent Graph - To find nearby domain based on the edit distance.

Assumptions
-----------
- set 'D' consists of valid domain names.

Packages
--------
- default: Has main method.
- domain: Has AdjancentGraph class to load and perform domain operations.

Class
-----
- Main: gets domain set 'D' from user and performs desired operations based on the choice.
- AdjancentGraph: loads and compute edit distance, edit path and nearbyNum.

Methods
--------
* load #domain.AdjancentGraph: 
		
    - Gets the set of domain name 'D'
    - Constructs a @graph as <key, edges[]> which will be in form of adjacency list.
    - Edges are connected between domain which has unit edit distance.

* doGraphTravesal #domain.AdjancentGraph:
		
    intializes the path, level and other required factors & calls traverseAllPath method.

* traverseAllPath #domain.AdjancentGraph:
      
   traverse the graph from source to destination recursively by using the adjancent list in the value field of @graph.

* editDistance #domain.AdjancentGraph:
		
    Gives the smallest number of edits to convert domainName1 to domainName2

* editPath #domain.AdjancentGraph:
		
    Outputs all possible sequence to convert domainName1 to domainName2 in the given domain set 'D'.

* numNearby #domain.AdjancentGraph:
		
    Gives the total number of possible by having a max limit set in the given domain set 'D'.

* traverseBydistance #domain.AdjancentGraph:
		
    traverses the graph to till the specified distance in all directions.

* isUnitDistance #domain.AdjancentGraph:
		
    Check whether given strings has edit distance of 1 by either inserting or deleting a char.


## Reference
[1] Umawing, J. (2018, January 22). Out of character: Homograph attacks explained. Retrieved from https://blog.malwarebytes.com/101/2017/10/out-of-character-homograph-attacks-explained/
