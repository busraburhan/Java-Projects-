import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class plug extends devices {
    private double ampere;


    private String plugStatus;

    private double energy= 0;

    public static ArrayList<plug> plugList= new ArrayList<>();

    /**+
     * consturctor are checking if given variables are suitable for plug fields
     * if it is not then gives error specifically.
     * @param name is given name in command for device
     */
    public plug(String name){
        if(!devices.contains(name)){
            this.name=name;
            this.status="Off";
            devices.add(name);
            deviceObjects.add(this);
            plugList.add(this);

        }
        else write("ERROR: There is already a smart device with same name!");

    }
    public plug(String name, String status){
        if(!devices.contains(name)){
            if(status.equals("On") ^ status.equals("Off")) {
                this.name = name;
                devices.add(name);
                deviceObjects.add(this);
                this.status = status;
                plugList.add(this);
            }
            else write("ERROR: Erroneous command!");

        }
        else write("ERROR: There is already a smart device with same name!");

    }
    public plug(String name, String status, double ampere){
        if(!devices.contains(name)){
            if(status.equals("On") ^ status.equals("Off")) {
                if(0<ampere){
                    this.name = name;
                    devices.add(name);
                    deviceObjects.add(this);
                    plugList.add(this);

                    this.status = status;
                    this.ampere = ampere;

                }
                else write("ERROR: Ampere value must be a positive number!");


            }
            else write("ERROR: Erroneous command!");

        }
        else write("ERROR: There is already a smart device with same name!");

    }
    public String getName(){
        return this.name;
    }

    /**+
     * this function make operations when plug in/out command is given
     * @param plugStatus given status in command
     * @param amper given status in command
     */

    public void setPlugStatus(String plugStatus,double amper) {
        if(plugStatus.equals("In") ) {
            if(this.plugStatus==null || this.plugStatus.equals("Out")) {
                if(0<amper) {
                    this.ampere = amper;
                    if (this.status.equals("On")) {
                        this.plugStatus = plugStatus;
                        this.initialTime = Time;
                    }
                }
                else  write("ERROR: Ampere value must be a positive number!");
            }
            else write("ERROR: There is already an item plugged in to that plug!");
        }
        else if(plugStatus.equals("Out")) {
            if(!this.plugStatus.equals("Out")) {
                this.plugStatus = plugStatus;
                if (this.status.equals("On")) {
                    Duration duration1 = Duration.between(Time, this.initialTime);
                    long minute = duration1.toMinutes();
                    double hour=minute/60.00;
                    this.energy = this.energy + (220 * this.ampere * hour);
                }
            }
            else {
                write("ERROR: This plug has no item to plug out from that plug!");
            }


        }
    }
    public String getStatus(){
        return this.status;
    }
    public double getEnergy(){
        return this.energy;
    }

    /**+
     * this function make status changes up to command
     * makes energy calulatons up to status and time
     * @param Status is given status to change
     */
    public void setStatus(String Status){
        if(status.equals("On")){
            this.initialTime=Time;
            this.status="On";
        }
        else if(status.equals("Off")){
            this.status="Off";
            Duration duration2= Duration.between(this.initialTime,Time);
            long minute= duration2.toMinutes();
            double hour=minute/60.00;
            this.energy=this.energy+(220*this.ampere*hour);
        }


    }

    /**+
     * this function makes status changes when switch time come.
     * makes energy calculatons up to status and time
     * @param status is new status of device in given command
     */


    public void changeStatus(String status){
        if(this.status.equals("On")){
            if(status.equals("Off")) {
                Duration dur = Duration.between(this.initialTime, Time);
                long minutes = dur.toMinutes();
                double saat=minutes/60.00;
                this.energy = this.energy+(saat * 220 * this.ampere);
                this.status = "Off";
            }
            else if(status.equals("On")){
                write("ERROR: This device is already switched on!");
            }

        }
        else if(this.status.equals("Off")){
            if(status.equals("On")) {
                this.status = "On";
                if(this.plugStatus.equals("In")) {

                    this.initialTime = Time;
                }
            }
            else if(status.equals("Off")){
                write("ERROR: This device is already switched off!");
            }
        }
    }

}
