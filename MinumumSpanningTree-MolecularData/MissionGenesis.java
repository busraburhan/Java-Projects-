// Class representing the mission of Genesis
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MissionGenesis {

    // Private fields
    private MolecularData molecularDataHuman; // Molecular data for humans
    private MolecularData molecularDataVitales; // Molecular data for Vitales


    // Getter for human molecular data
    public MolecularData getMolecularDataHuman() {
        return molecularDataHuman;
    }

    // Getter for Vitales molecular data
    public MolecularData getMolecularDataVitales() {
        return molecularDataVitales;
    }

    // Method to read XML data from the specified filename
    // This method should populate molecularDataHuman and molecularDataVitales fields once called
    public void readXML(String filename) {
        try {
            File xmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            List<Molecule> humanMolecules = new ArrayList<>();
            NodeList humanMoleculeList = doc.getElementsByTagName("HumanMolecularData").item(0).getChildNodes();
            for (int i = 0; i < humanMoleculeList.getLength(); i++) {
                if (humanMoleculeList.item(i) instanceof Element) {
                    Element moleculeElement = (Element) humanMoleculeList.item(i);
                    String id = moleculeElement.getElementsByTagName("ID").item(0).getTextContent();
                    int bondStrength = Integer.parseInt(moleculeElement.getElementsByTagName("BondStrength").item(0).getTextContent());
                    NodeList bondsNodeList = moleculeElement.getElementsByTagName("Bonds").item(0).getChildNodes();
                    List<String> bonds = new ArrayList<>();
                    for (int j = 0; j < bondsNodeList.getLength(); j++) {
                        if (bondsNodeList.item(j) instanceof Element) {
                            bonds.add(bondsNodeList.item(j).getTextContent());
                        }
                    }
                    Molecule molecule = new Molecule(id, bondStrength, bonds);
                    humanMolecules.add(molecule);


                }
            }
            molecularDataHuman = new MolecularData(humanMolecules);

            List<Molecule> vitalesMolecules = new ArrayList<>();
            NodeList vitalesMoleculeList = doc.getElementsByTagName("VitalesMolecularData").item(0).getChildNodes();
            for (int i = 0; i < vitalesMoleculeList.getLength(); i++) {
                if (vitalesMoleculeList.item(i) instanceof Element) {
                    Element moleculeElement = (Element) vitalesMoleculeList.item(i);
                    String id = moleculeElement.getElementsByTagName("ID").item(0).getTextContent();
                    int bondStrength = Integer.parseInt(moleculeElement.getElementsByTagName("BondStrength").item(0).getTextContent());
                    NodeList bondsNodeList = moleculeElement.getElementsByTagName("Bonds").item(0).getChildNodes();
                    List<String> bonds = new ArrayList<>();
                    for (int j = 0; j < bondsNodeList.getLength(); j++) {
                        if (bondsNodeList.item(j) instanceof Element) {
                            bonds.add(bondsNodeList.item(j).getTextContent());
                        }
                    }
                    Molecule molecule = new Molecule(id, bondStrength, bonds);
                    vitalesMolecules.add(molecule);
                }
            }

            molecularDataVitales = new MolecularData(vitalesMolecules);



            // Use humanMolecules and vitalesMolecules lists to create MolecularData objects
            // and perform further operations
        } catch (Exception e) {
            e.printStackTrace();
        }



        /* YOUR CODE HERE */ 
        
    }
}
