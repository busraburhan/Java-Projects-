import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Main class
 */
// FREE CODE HERE
public class Main {
    public static void main(String[] args) throws IOException {

       /** MISSION POWER GRID OPTIMIZATION BELOW **/


        // TODO: Your code goes here
        File input = new File(args[0]);
        BufferedReader br = new BufferedReader(new FileReader(input));
        String[] line=br.readLine().split(" ");
        ArrayList<Integer> demands = new ArrayList<>();
        for(String i: line){
            demands.add(Integer.parseInt(i));
        }
        PowerGridOptimization powerGridOptimization= new PowerGridOptimization(demands);
        powerGridOptimization.getOptimalPowerGridSolutionDP();
        System.out.println("##MISSION POWER GRID OPTIMIZATION##");
        System.out.println("The total number of demanded gigawatts: " + powerGridOptimization.getSum());
        System.out.println("Maximum number of satisfied gigawatts: " + powerGridOptimization.getOptimalPowerGridSolutionDP().getmaxNumberOfSatisfiedDemands());

        String result = powerGridOptimization.getOptimalPowerGridSolutionDP().getHoursToDischargeBatteriesForMaxEfficiency().stream()
                .map(Object::toString)
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
        System.out.println("Hours at which the battery bank should be discharged: "+result);
        System.out.println("The number of unsatisfied gigawatts: "+ (powerGridOptimization.getSum()-powerGridOptimization.getOptimalPowerGridSolutionDP().getmaxNumberOfSatisfiedDemands()));
        System.out.println("##MISSION POWER GRID OPTIMIZATION COMPLETED##");

        // You are expected to read the file given as the first command-line argument to read 
        // the energy demands arriving per hour. Then, use this data to instantiate a 
        // PowerGridOptimization object. You need to call getOptimalPowerGridSolutionDP() method
        // of your PowerGridOptimization object to get the solution, and finally print it to STDOUT.


        /** MISSION ECO-MAINTENANCE BELOW **/

        System.out.println("##MISSION ECO-MAINTENANCE##");
        // TODO: Your code goes here
        File input2 = new File(args[1]);
        BufferedReader br2 = new BufferedReader(new FileReader(input2));
        String[] line1=br2.readLine().split(" ");
        String[] line2=br2.readLine().split(" ");
        ArrayList<Integer> max = new ArrayList<>();
        ArrayList<Integer> demandsESV = new ArrayList<>();
        for(String i: line1){
            max.add(Integer.parseInt(i));

        }
        for(String i: line2){
            demandsESV.add(Integer.parseInt(i));
        }
        OptimalESVDeploymentGP optimalESVDeploymentGP= new OptimalESVDeploymentGP(demandsESV);
        int number = optimalESVDeploymentGP.getMinNumESVsToDeploy(max.get(0),max.get(1));
        System.out.println("The minimum number of ESVs to deploy: "+number);
        for(int i=1; i<=number ; i++){
            System.out.println("ESV "+i+ " tasks: " + optimalESVDeploymentGP.getMaintenanceTasksAssignedToESVs().get(i-1));
        }

        // You are expected to read the file given as the second command-line argument to read
        // the number of available ESVs, the capacity of each available ESV, and the energy requirements 
        // of the maintenance tasks. Then, use this data to instantiate an OptimalESVDeploymentGP object.
        // You need to call getMinNumESVsToDeploy(int maxNumberOfAvailableESVs, int maxESVCapacity) method
        // of your OptimalESVDeploymentGP object to get the solution, and finally print it to STDOUT.
        System.out.println("##MISSION ECO-MAINTENANCE COMPLETED##");
    }
}
