import java.util.*;

// Class representing molecular data
public class MolecularData {

    // Private fields
    private final List<Molecule> molecules; // List of molecules

    // Constructor
    public MolecularData(List<Molecule> molecules) {
        this.molecules = molecules;
    }

    // Getter for molecules
    public List<Molecule> getMolecules() {
        return molecules;
    }

    // Method to identify molecular structures
    // Return the list of different molecular structures identified from the input data
    public List<MolecularStructure> identifyMolecularStructures() {
        ArrayList<MolecularStructure> structures = new ArrayList<>();
        List<String> visitedMolecules = new ArrayList<>();



        for (Molecule m : molecules) {
            if (!visitedMolecules.contains(m.getId())) {
                int c=0;
                for(MolecularStructure i: structures){
                    for(String j: m.getBonds()){
                        if(i.hasMolecule(j)){
                            c++;
                            i.addMolecule(m);
                            visitedMolecules.add(m.getId());
                            break;
                        }
                    }
                }
                if(c==0) {
                    MolecularStructure structure = new MolecularStructure();
                    dfs(m, visitedMolecules, structure);
                    structures.add(structure);
                }
            }
        }
        return structures;



        /* YOUR CODE HERE */ 

    }

    // Method to print given molecular structures
    public void printMolecularStructures(List<MolecularStructure> molecularStructures, String species) {


        System.out.println(molecularStructures.size() +" molecular structures have been discovered in "+species+".");
        for(int i=1 ;i<=molecularStructures.size();i++){
            System.out.print("Molecules in Molecular Structure "+ i+ ": ");
            System.out.println(molecularStructures.get(i-1).toString());
        }

        
        /* YOUR CODE HERE */ 

    }

    // Method to identify anomalies given a source and target molecular structure
    // Returns a list of molecular structures unique to the targetStructure only
    public static ArrayList<MolecularStructure> getVitalesAnomaly(List<MolecularStructure> sourceStructures, List<MolecularStructure> targeStructures) {
        ArrayList<MolecularStructure> anomalyList = new ArrayList<>();

        for(MolecularStructure vitales:targeStructures){
            int count =0;
            for(MolecularStructure human: sourceStructures){
                if(human.equals(vitales)){
                    count++;


                }
            }
            if(count==0){
                anomalyList.add(vitales);
            }
        }
        
        /* YOUR CODE HERE */ 

        return anomalyList;
    }

    // Method to print Vitales anomalies
    public void printVitalesAnomaly(List<MolecularStructure> molecularStructures) {
        System.out.println("Molecular structures unique to Vitales individuals:");
        for(MolecularStructure i: molecularStructures){
            System.out.println(i.toString());
        }

        /* YOUR CODE HERE */ 

    }

    public  void dfs(Molecule molecule, List<String> visitedIds, MolecularStructure structure) {
        visitedIds.add(molecule.getId());
        structure.addMolecule(molecule);

        for (String connectedMolecule : molecule.getBonds()) {
            for(Molecule m: molecules){
                if(m.getId().equals(connectedMolecule)){
                    if (!visitedIds.contains(connectedMolecule)) {
                        dfs(m, visitedIds, structure);
                    }

                }
            }

        }
    }

}
