import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.Comparator;

/**
 * This class accomplishes Mission Eco-Maintenance
 */
public class OptimalESVDeploymentGP
{
    private ArrayList<Integer> maintenanceTaskEnergyDemands;

    /*
     * Should include tasks assigned to ESVs.
     * For the sample input:
     * 8 100
     * 20 50 40 70 10 30 80 100 10
     * 
     * The list should look like this:
     * [[100], [80, 20], [70, 30], [50, 40, 10], [10]]
     * 
     * It is expected to be filled after getMinNumESVsToDeploy() is called.
     */
    private ArrayList<ArrayList<Integer>> maintenanceTasksAssignedToESVs = new ArrayList<>();

    ArrayList<ArrayList<Integer>> getMaintenanceTasksAssignedToESVs() {
        return maintenanceTasksAssignedToESVs;
    }

    public OptimalESVDeploymentGP(ArrayList<Integer> maintenanceTaskEnergyDemands) {
        this.maintenanceTaskEnergyDemands = maintenanceTaskEnergyDemands;
    }

    public ArrayList<Integer> getMaintenanceTaskEnergyDemands() {
        return maintenanceTaskEnergyDemands;
    }

    /**
     *
     * @param maxNumberOfAvailableESVs the maximum number of available ESVs to be deployed
     * @param maxESVCapacity the maximum capacity of ESVs
     * @return the minimum number of ESVs required using first fit approach over reversely sorted items.
     * Must return -1 if all tasks can't be satisfied by the available ESVs
     */
    public int getMinNumESVsToDeploy(int maxNumberOfAvailableESVs, int maxESVCapacity)
    {
        Collections.sort(maintenanceTaskEnergyDemands, Comparator.reverseOrder());

        int[] Capacity = new int[maxNumberOfAvailableESVs];
        Arrays.fill(Capacity, maxESVCapacity);

        int numberOfUsedESV=0;



        for(int i=0; i<maintenanceTaskEnergyDemands.size();i++){
            int task=maintenanceTaskEnergyDemands.get(i);
            boolean bool=false;
            for(int j=0 ;j<maxNumberOfAvailableESVs;j++){
                if(task<=Capacity[j]){
                    bool=true;
                    Capacity[j]-=task;
                    if(j+1>numberOfUsedESV){
                        numberOfUsedESV=j+1;
                    }
                    if (maintenanceTasksAssignedToESVs.size() <= j) {
                        maintenanceTasksAssignedToESVs.add(new ArrayList<>());
                    }
                    maintenanceTasksAssignedToESVs.get(j).add(task);
                    break;
                }
            }
            if(!bool){
                return -1;

            }


        }


        // TODO: Your code goes here
        return numberOfUsedESV;
    }

}
