import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public  class devices {

    public static String[] arguments;
    public static void write(String s) {
        try {
            BufferedWriter bwrite = new BufferedWriter(new FileWriter(arguments[1], true));
            bwrite.write(s);
            bwrite.write("\n");
            bwrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  static ArrayList<devices> deviceObjects= new ArrayList<>(); //this is list of added devices
    public static  ArrayList<String> devices= new ArrayList<>();// list of added devices name and useful for name control

    /**+
     * fields are general fields of all devices.All device classes using these fields specifically.
     */
    protected String name;

    protected String status;

    protected LocalDateTime initialTime;

    protected LocalDateTime switchTime;

    protected static LocalDateTime Time;



    public String getName(){
        return this.name;

    }
    public LocalDateTime getSwitchTime(){
        return this.switchTime;
    }

    public void setSwitchTime(LocalDateTime switchTime) {
        this.switchTime = switchTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**+
     * this function checks device's current status and make operations up to them
     * @param status is new status of device in given command
     */
    public void changeStatus(String status){
        if(status.equals("Off")) {
            if (this.status.equals("On")) {
                this.setStatus("Off");
            }
            else if(this.status.equals("Off")){
                write("ERROR: This device is already switched off!");
            }
        }
        else{
            if (this.status.equals("Off")) {
                this.setStatus("On");
                this.initialTime=Time;
            }
            else if(this.status.equals("On")){
                write("ERROR: This device is already switched on!");
            }


        }
        this.switchTime=null;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getStatus(){
        return this.status;
    }
    public void setInitialTime(){
        this.initialTime=null;
    }


}




