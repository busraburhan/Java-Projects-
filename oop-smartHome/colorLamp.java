
import java.util.ArrayList;

public class colorLamp extends devices{

    public static ArrayList<colorLamp> colorLampList = new ArrayList<>();


    private int color;

    private int brightness;

    private int kelvin;





    private String colorToWrite;

    public colorLamp(String name){
        if(!devices.contains(name)){
            this.name=name;
            devices.add(name);
            this.kelvin=4000;
            this.brightness=100;
            deviceObjects.add(this);
            colorLampList.add(this);

            this.status="Off";
        }
        else{write("ERROR: There is already a smart device with same name!");}

    }

    public colorLamp(String name,String status){
        if(!devices.contains(name)){
            if (status.equals("On")^status.equals("Off")){
                this.name=name;
                devices.add(name);
                this.status=status;
                this.kelvin=4000;
                this.brightness=100;
                deviceObjects.add(this);
                colorLampList.add(this);

            }
            else{write("ERROR: Erroneous command!");}


        }
        else write("ERROR: There is already a smart device with same name!");

    }

    public colorLamp(String name, String status, String kelvinOrColor,int brightness){
        if(!devices.contains(name)){
            if (status.equals("On")^status.equals("Off")){
                if(kelvinOrColor.startsWith("0x")){
                    String colorX=kelvinOrColor;
                    kelvinOrColor=kelvinOrColor.substring(2);
                    if(0x000000<=Integer.parseInt(kelvinOrColor,16) && Integer.parseInt(kelvinOrColor,16)<=0xFFFFFF) {
                        if (0 <= brightness && brightness <= 100) {
                            this.colorToWrite=colorX;
                            this.name = name;
                            devices.add(name);
                            this.status = status;
                            this.brightness=brightness;
                            this.color = Integer.parseInt(kelvinOrColor,16);
                            deviceObjects.add(this);
                            colorLampList.add(this);

                        }
                        else write("ERROR: Brightness must be in range of 0%-100%!");
                    }
                    else write("ERROR: Color code value must be in range of 0x0-0xFFFFFF!");


                }
                else{
                    if(2000<=Integer.parseInt(kelvinOrColor) && Integer.parseInt(kelvinOrColor)<=6500){
                        if(0<=brightness && brightness<=100){
                            this.name=name;
                            devices.add(name);
                            deviceObjects.add(this);
                            colorLampList.add(this);
                            this.status=status;
                            this.brightness=brightness;
                            this.kelvin=Integer.parseInt(kelvinOrColor);

                        }
                        else write("ERROR: Brightness must be in range of 0%-100%!");
                    }
                    else write("ERROR: Kelvin value must be in range of 2000K-6500K!");
                }


            }
            else{write("ERROR: Erroneous command!");}


        }
        else write("ERROR: There is already a smart device with same name!");

    }
    public String getName(){
        return this.name;
    }
    public void setStatus(String situation) {
        if(situation.equals("On")^ situation.equals("Off")) {
            this.status = situation;
        }
        else {
            write("ERROR: Erroneous command!");

        }
    }
    public void setKelvin(int value) {
        if (2000 <= value && value <= 6500) {
            this.kelvin = value;

        }
        else write("ERROR: Kelvin value must be in range of 2000K-6500K!");


    }

    /**+
     *this function sets color code of colorlamp
     * @param color given color code in command
     * colorX is copy of string value of color code, it will be used in zReport command to print
     *
     */
    public void setColor(String color){
        if(color.startsWith("0x")) {
            String ColorX=color;
            color=color.substring(2);
            if (0x000000 <= Integer.parseInt(color,16) && Integer.parseInt(color,16) <= 0xFFFFFF) {
                this.colorToWrite=ColorX;
                this.color = Integer.parseInt(color,16);
            } else write("ERROR: Color code value must be in range of 0x0-0xFFFFFF!");
        }
        else write("ERROR: Erroneous command!");
    }
    public String getStatus(){
        return this.status;
    }
    public int getBrightness(){
        return this.brightness;
    }
    public int getKelvin(){
        return this.kelvin;
    }

    public int getColor(){
        return this.color;
    }
    public void setBrightness(int brightness){
        if(0<=brightness && brightness<=100) {
            this.brightness =brightness;
        }
        else write("ERROR: Brightness must be in range of 0%-100%!");

    }
    public void setColorAndBrightness(String color,int brightness){
        if(color.startsWith("0x")) {
            String ColorX=color;
            color=color.substring(2);
            if (0x000000 <= Integer.parseInt(color,16) && Integer.parseInt(color,16) <= 0xFFFFFF) {
                if (0 <= brightness && brightness <= 100) {
                    this.colorToWrite=ColorX;
                    this.color = Integer.parseInt(color,16);
                    this.brightness = brightness;
                } else write("ERROR: Brightness must be in range of 0%-100%!");
            } else write("ERROR: Color code value must be in range of 0x0-0xFFFFFF!");
        }
        else write("ERROR: Erroneous command!");
    }
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
    public String getColorToWrite(){
        return this.colorToWrite;
    }




}
