import java.util.*;
import java.text.DecimalFormat;

// Class representing the Mission Synthesis
public class MissionSynthesis {

    // Private fields

    private final List<MolecularStructure> humanStructures; // Molecular structures for humans
    private final ArrayList<MolecularStructure> diffStructures; // Anomalies in Vitales structures compared to humans

    private List<Molecule> humanSelectedMolecules;
    private ArrayList<Molecule> vitalesSelectedMolecules;
    // Constructor
    public MissionSynthesis(List<MolecularStructure> humanStructures, ArrayList<MolecularStructure> diffStructures) {
        this.humanStructures = humanStructures;
        this.diffStructures = diffStructures;
    }

    // Method to synthesize bonds for the serum
    public List<Bond> synthesizeSerum() {
        List<Bond> serum = new ArrayList<>();
        this.humanSelectedMolecules = new ArrayList<>();
        this.vitalesSelectedMolecules = new ArrayList<>();
        List<Molecule> total = new ArrayList<>();
        List<Bond> totalBonds = new ArrayList<>();

        for(MolecularStructure i: humanStructures){
            int min=i.getMolecules().get(0).getBondStrength();
            Molecule selected=i.getMolecules().get(0);
            for(Molecule m: i.getMolecules()){
                if(m.getBondStrength()<min){
                    min= m.getBondStrength();
                    selected=m;
                }
            }
            total.add(selected);
            humanSelectedMolecules.add(selected);
        }
        for(MolecularStructure i: diffStructures){
            int min = i.getMolecules().get(0).getBondStrength();
            Molecule selected=i.getMolecules().get(0);
            for(Molecule m: i.getMolecules()){
                if(m.getBondStrength()<min){
                    min= m.getBondStrength();
                    selected=m;
                }
            }
            total.add(selected);
            vitalesSelectedMolecules.add(selected);

        }

        for(int i=0;i< total.size();i++){
            for(int j=i+1;j<total.size();j++){
                double weight = (total.get(i).getBondStrength() + total.get(j).getBondStrength())/2.0;
                Bond bond = new Bond(total.get(j),total.get(i),weight);
                totalBonds.add(bond);
            }
        }


        Set<Molecule> visited = new HashSet<>();
        Collections.sort(totalBonds,Comparator.comparingDouble(Bond::getWeight));

        Molecule startMolecule = totalBonds.get(0).getFrom();
        visited.add(startMolecule);

        for (Bond b : totalBonds) {
            Molecule from = b.getFrom();
            Molecule to = b.getTo();


            if (!visited.contains(from) || !visited.contains(to)) {
                serum.add(b);
                visited.add(from);
                visited.add(to);
            }

            if (serum.size() == total.size() - 1) {
                break;
            }
        }




        /* YOUR CODE HERE */
        Collections.sort(serum, Comparator.comparingDouble(Bond::getWeight));

        return serum;
    }

    // Method to print the synthesized bonds
    public void printSynthesis(List<Bond> serum) {
        System.out.println("Typical human molecules selected for synthesis: " + humanSelectedMolecules);
        System.out.println("Vitales molecules selected for synthesis: "+ vitalesSelectedMolecules);
        System.out.println("Synthesizing the serum...");
        int strengh=0;
        DecimalFormat df = new DecimalFormat("#.00");
        for(Bond b: serum){
            String formattedValue = df.format(b.getWeight());
            if(Integer.parseInt(b.getTo().getId().substring(1)) >Integer.parseInt(b.getFrom().getId().substring(1))) {
                System.out.println("Forming a bond between " + b.getFrom() + " - " + b.getTo() + " with strength " + formattedValue);
                strengh += b.getWeight();
            }
            else{
                System.out.println("Forming a bond between " + b.getTo() + " - " + b.getFrom() + " with strength " + formattedValue);
                strengh += b.getWeight();

            }
        }
        String formattedValue2 = df.format(strengh);
        System.out.println("The total serum bond strength is "+formattedValue2);



        /* YOUR CODE HERE */ 

    }
}
