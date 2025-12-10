import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class camera extends devices{
    public static ArrayList<camera> cameraList= new ArrayList<>();

    private double mG;

    private double storage;

    private LocalDateTime initialTime;

    /**+
     * constuctors are creating new variables up to given values
     * @param name name in given command
     * @param mG double value in given command
     * mG value should be positive
     * device name shouldn't be in added device list so checking it before operations.
     */

    public camera(String name, Double mG){
        if(!devices.contains(name)){
            if(0<mG){
                deviceObjects.add(this);
                devices.add(name);
                cameraList.add(this);
                this.name=name;
                this.mG=mG;
                this.status="Off";




            }
            else write("ERROR: Megabyte value must be a positive number!");
        }
        else write("ERROR: There is already a smart device with same name!");
    }

    public camera(String name, double mG,String status){
        if(!devices.contains(name)){
            if(0.00<mG){
                if(status.equals("On") ^ status.equals("Off")){
                    deviceObjects.add(this);
                    devices.add(name);
                    cameraList.add(this);
                    this.name=name;
                    this.mG=mG;
                    if(status.equals("On")) {

                        this.initialTime=Time;
                    }
                    this.status = status;


                }
                else{write("ERROR: Erroneous command!");}

            }
            else write("ERROR: Megabyte value must be a positive number!");
        }
        else write("ERROR: There is already a smart device with same name!");

    }
    public String getName(){
        return this.name;

    }
    public void setStatus(String situation) {
        if(situation.equals("On")^situation.equals("Off")) {

            if(situation.equals("On")){
                this.status="On";
                this.initialTime= Time;
            }
            else {
                this.status="Off";
                Duration duration3= Duration.between(this.initialTime,Time);
                long minutes= duration3.toMinutes();
                this.storage=this.mG*minutes;



            }
        }
        else write("ERROR: Erroneous command!");
    }

    /**+
     * this method doing same operations with the one in devices class but this one makes storage calculatins up to status
     * @param status is new status of device in given command
     */
    public void changeStatus(String status){
        if(status.equals("Off")) {
            if (this.status.equals("On")) {
                this.status = "Off";
                Duration duration = Duration.between(this.initialTime, Time);
                long minutes = duration.toMinutes();
                this.storage = this.storage+(minutes * this.mG);
            }
            else if(this.status.equals("Off")){
                write("ERROR: This device is already switched off!");
            }
        }
        else{
            if(this.status.equals("Off")) {
                this.status = "On";
                this.initialTime = Time;
            }
            else if(this.status.equals("On")){
                write("ERROR: This device is already switched on!");
            }
        }
    }
    public String getStatus(){
        return this.status;
    }
    public double getStorage(){
        return this.storage;
    }








}


