import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class accomplishes Mission POWER GRID OPTIMIZATION
 */
public class PowerGridOptimization {
    private ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour;

    public PowerGridOptimization(ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour){
        this.amountOfEnergyDemandsArrivingPerHour = amountOfEnergyDemandsArrivingPerHour;
    }

    public ArrayList<Integer> getAmountOfEnergyDemandsArrivingPerHour() {
        return amountOfEnergyDemandsArrivingPerHour;
    }
    /**
     *     Function to implement the given dynamic programming algorithm
     *     SOL(0) <- 0
     *     HOURS(0) <- [ ]
     *     For{j <- 1...N}
     *         SOL(j) <- max_{0<=i<j} [ (SOL(i) + min[ E(j), P(j − i) ] ]
     *         HOURS(j) <- [HOURS(i), j]
     *     EndFor
     *
     * @return OptimalPowerGridSolution
     */
    public OptimalPowerGridSolution getOptimalPowerGridSolutionDP(){
        int size = amountOfEnergyDemandsArrivingPerHour.size();
        int[] solve = new int[size+1];
        ArrayList<Integer>[] hour = new ArrayList[size+1];
        solve[0] = 0;
        hour[0] = new ArrayList<>();

        for (int j = 1; j <=size; j++) {
            hour[j] = new ArrayList<>();
            int num = 0;
            int chosen = 0;
            for (int i = 0; i < j; i++) {
                int energy = Math.min(amountOfEnergyDemandsArrivingPerHour.get(j-1), (j - i) * (j - i));
                if (energy + solve[i] > num) {
                    num = energy + solve[i];
                    chosen = i;
                }
            }
            for (int i : hour[chosen]) {
                hour[j].add(i);
            }
            hour[j].add(j);
            solve[j] = num;
        }
        int max = Arrays.stream(solve).max().getAsInt();
        ArrayList<Integer> discharge =new ArrayList<>();
        for(int i=0;i!=solve.length ; i++){
            if(solve[i]==max){
                discharge=hour[i];

            }
        }

        OptimalPowerGridSolution optimalPowerGridSolution= new OptimalPowerGridSolution(solve[size],hour[size]);
        return optimalPowerGridSolution;
    }

    public int getSum(){
        int sum=0;
        for(int i:amountOfEnergyDemandsArrivingPerHour){
            sum+=i;

        }
        return sum;
    }
}
