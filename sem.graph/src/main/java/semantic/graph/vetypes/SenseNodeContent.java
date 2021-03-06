package semantic.graph.vetypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import semantic.graph.NodeContent;



/**
 * The {@link EdgeContent} for a {@link SenseNode}. 
 * Contains non-trivial amounts of information 
 * (sense id, taxonomic concepts, RACs).
 *
 */
public class SenseNodeContent extends NodeContent implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5405041614966233844L;
	private String senseId;
	private List<String> concepts;
	//private List<Integer> racs;
	private boolean hierarchyPrecomputed = false;
	private Map<String, Integer> subConcepts;
	private Map<String, Integer> superConcepts;
	
	/**
	 * Create (empty) SenseNodeContent
	 */
	public SenseNodeContent() {
		senseId = "";
		concepts = new ArrayList<String>();
		//racs = new ArrayList<Integer>();
		// We don't always include sub and super concepts
		// Use null to indicate that they have not been added
		subConcepts = null;
		superConcepts = null;
	}
	
	/**
	 * Create sense node concept with specified senseId, and empty concepts and racs
	 * @param senseId
	 */
	public SenseNodeContent(String senseId) {
		this.senseId = senseId;
		concepts = new ArrayList<String>();
		//racs = new ArrayList<Integer>();
	}

	public String getSenseId() {
		return senseId;
	}

	public void setSenseId(String senseId) {
		this.senseId = senseId;
	}

	/**
	 * Get the list of taxonomic concept ids associated with the sense
	 * @return
	 */
	public List<String> getConcepts() {
		return concepts;
	}

	public void setConcepts(List<String> concepts) {
		this.concepts = concepts;
	}
	
	/**
	 * Add a new concept id to the sense's taxonomic concepts
	 * @param concept
	 */
	public void addConcept(String concept) {
		this.concepts.add(concept);
	}

	/**
	 * Get the RAC (referentially anchored concept) ids for the sense
	 * @return
	 */
	/*public List<Integer> getRacs() {
		return racs;
	}

	public void setRacs(List<Integer> racs) {
		this.racs = racs;
	}*/

	/** 
	 * Add a RAC id to the sense;
	 * @param rac
	 */
	/*public void addRac(int rac) {
		this.racs.add(rac);		
	}
	*/
	
	public boolean isHierarchyPrecomputed() {
		return hierarchyPrecomputed;
	}

	public void setHierarchyPrecomputed(boolean hierarchyPrecomputed) {
		this.hierarchyPrecomputed = hierarchyPrecomputed;
	}

	public Map<String, Integer> getSubConcepts() {
		return subConcepts;
	}

	public Map<String, Integer> getSuperConcepts() {
		return superConcepts;
	}
	
	/**
	 * Get minimum distance (number of links) between the specified super concept and
	 * any of the taxonomic concepts associated with the sense
	 * @param superConcept
	 * @return -1 is no super concepts are associated with the edge
	 */
	public int getSuperConceptDistance(String superConcept) {
		if (superConcepts == null) {
			return -1;
		} 
		Integer distance = superConcepts.get(superConcept);
		if (distance == null) {
			if (concepts.contains(superConcept)) {
				return 0;
			} else {
				return -1;
			}
		} else {
			return distance;
		}
	}
	
	/**
	 * Get minimum distance (number of links) between the specified sub concept and
	 * any of the taxonomic concepts associated with the sense
	 * @param subConcept
	 * @return -1 is no sub concepts are associated with the edge
	 */
	public int getSubConceptDistance(String subConcept) {
		if (subConcepts == null) {
			return -1;
		} 
		Integer distance = subConcepts.get(subConcept);
		if (distance == null) {
			if (concepts.contains(subConcept)) {
				return 0;
			} else {
				return -1;
			}
		} else {
			return distance;
		}
	}
	
	/**
	 * Add a new superconcept, plus distance, to superconcepts associated with sense
	 * @param superConcept
	 * @param distance
	 */
	public void addSuperConcept(String superConcept, int distance) {
		if (superConcepts == null) {
			superConcepts = new HashMap<String, Integer>();
		}
		superConcepts.put(superConcept, distance);
	}
	
	/**
	 * Add a new subconcept, plus distance, to subconcepts associated with sense
	 * @param superConcept
	 * @param distance
	 */
	public void addSubConcept(String subConcept, int distance) {
		if (subConcepts == null) {
			subConcepts = new HashMap<String, Integer>();
		}
		subConcepts.put(subConcept, distance);
	}
	
	
	public void setSuperConcepts(Map<String, Integer> superConcepts) {
		this.superConcepts = superConcepts;
	}

	public void setSubConcepts(Map<String, Integer> subConcepts) {
		this.subConcepts = subConcepts;
	}
	
	/**
	 * Test to see if the sub- and super-concept fields on the edge have been populated.
	 * @return
	 */
	public boolean isConceptHierarchyPrecomputed() {		
		return this.hierarchyPrecomputed ||
			(subConcepts != null && !subConcepts.isEmpty()) ||
			(superConcepts != null && !superConcepts.isEmpty());
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(senseId);
		sb.append("([ ");
		for (String concept : concepts) {
			sb.append(concept).append(' ');
		}
		sb.append("],[ ");
		return sb.toString();
	}


}
