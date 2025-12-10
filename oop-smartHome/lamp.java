
import java.util.ArrayList;

public class lamp extends devices {


    public static ArrayList<lamp> lampList= new ArrayList<>();
    private int kelvin;
    private int brightness;

    /**+
     * constructors are creating new lamp object with given parameters
     * checking if given field values are in suitable range.If it is not then gives error command
     * @param name lamp name
     * @param status lamp status on or off
     * @param kelvin lamp kelvin value
     * @param brightness lam brightness value
     *
     */

    public lamp(String name, String status, int kelvin, int brightness) {
        if (!devices.contains(name)) {
            if (2000 <= kelvin && kelvin <= 6500) {
                if (0 <= brightness && brightness <= 100) {
                    if (status.equals("On") ^ status.equals("Off")) {
                        this.name = name;
                        deviceObjects.add(this);
                        devices.add(name);
                        lampList.add(this);
                        this.status = status;
                        this.kelvin = kelvin;
                        this.brightness = brightness;

                    }
                    else write("ERROR: Erroneous command!");
                }
                else {
                    write("ERROR: Brightness must be in range of 0%-100%!");
                }
            } else {
                write("ERROR: Kelvin value must be in range of 2000K-6500K!");

            }
        } else {
            write("ERROR: There is already a smart device with same name!");
        }


    }

    public lamp(String name) {

        if (!devices.contains(name)) {
            this.name = name;
            devices.add(name);
            deviceObjects.add(this);
            lampList.add(this);
            this.status="Off";
            this.kelvin=4000;
            this.brightness=100;
        } else {
            write("ERROR: There is already a smart device with same name!");
        }


    }

    public lamp(String name, String status) {

        if (!devices.contains(name)) {

            if (status.equals("On") ^ status.equals("Off")) {

                this.name = name;
                devices.add(name);
                deviceObjects.add(this);
                this.status = status;
                this.brightness=100;
                this.kelvin=4000;
                lampList.add(this);

            }
            else {
                write("ERROR: Erroneous command!");
            }


        } else {
            write("ERROR: There is already a smart device with same name!");
        }
    }


    public void setStatus(String situation) {
        if(situation.equals("On")^ situation.equals("Off")) this.status = situation;
        else write("ERROR: Erroneous command!");
    }

    public String getStatus() {
        return this.status;
    }



    public void setKelvin(int value) {
        if (2000 <= value && value <= 6500) {
            this.kelvin = value;

        }
        else write("ERROR: Kelvin value must be in range of 2000K-6500K!");


    }
    public int getKelvin(){
        return this.kelvin;
    }

    public String getName(){
        return this.name;
    }

    public int getBrightness(){
        return this.brightness;
    }

    /**+
     * this method setting brightness value of lamp
     * @param brightness should be in range[0,100]
     * if value is not suitable then gives error output
     *
     */
    public void setBrightness(int brightness){
        if(0<=brightness && brightness<=100) {
            this.brightness =brightness;
        }
        else write("ERROR: Brightness must be in range of 0%-100%!");
    }

    /**+
     * this method setting kelvin and brightness value of lamp
     * @param kelvin given value in command
     * @param brightness given value in command
     */
    public void setWhite(int kelvin,int brightness){
        if (2000 <= kelvin && kelvin <= 6500) {
            if(0<=brightness && brightness<=100){
                this.kelvin=kelvin;
                this.brightness=brightness;
            }
            else write("ERROR: Brightness must be in range of 0%-100%!");
        }
        else write("ERROR: Kelvin value must be in range of 2000K-6500K!");
    }

}


