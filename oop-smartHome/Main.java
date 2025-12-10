import java.text.DecimalFormat;
import java.time.*;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Integer.parseInt;

public class Main {

    /**+
     * @author Busra Burhan
     * this function writes the texts we want to a txt document
     * @param s is string value that we want to see in output txt
     */
    public static void write(String s) {
        try {
            BufferedWriter bwrite = new BufferedWriter(new FileWriter(devices.arguments[1],true));
            bwrite.write(s);
            bwrite.write("\n");
            bwrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**+
     * this function finds the device as a device object from devicelist
     * @param name is name field device
     * @return index of device in devicelist
     */

    public static int whichDevice(String name){
        int count=0;
        for(devices i: devices.deviceObjects){
            if(i.getName().equals(name)){
                break;
            }
            else{count++;

            }

        }
        return count;
    }

    /**+
     * this function checks switch time of devices and
     * if switch time is now it is making necessary operations
     * @param time is current Time
     */
    public static void IsItSwitchTime(LocalDateTime time){
        for(devices i: devices.deviceObjects){
            if(i.getSwitchTime()!=null) {
                if (i.getSwitchTime().equals(time)) {
                    if(i.getStatus().equals("On")) {
                        i.changeStatus("Off");
                        i.setInitialTime();
                    }
                    else if(i.getStatus().equals("Off")){
                        i.changeStatus("On");
                        i.setInitialTime();
                    }

                }
            }
        }



    }
     public static ArrayList<LocalDateTime> listOfSwitch= new ArrayList<>(); //usen for nop command
    public static DecimalFormat df= new DecimalFormat("#.##");

    public static DateTimeFormatter tf= DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");





    public static void main(String[] args){
        devices.arguments=args;
        /*
          in this try block program reads the input file and make operations up to given command.
         */

        try {
            File input = new File(args[0]);
            BufferedReader br = new BufferedReader(new FileReader(input));
            String line;
            ArrayList<String> allcommands= new ArrayList<>();
            String[] commandLine;


            while((line=br.readLine())!=null){
                if(!line.equals("")) {
                    allcommands.add(line);
                }
            }
            if(allcommands.get(0).startsWith("SetInitialTime")) {
                write("COMMAND: " +allcommands.get(0));
                String[] toSplit=allcommands.get(0).split("\t");
                write("SUCCESS: Time has been set to "+ toSplit[1]+"!");
                String[] firstLine=toSplit[1].split("[-_:]");
                devices.Time=LocalDateTime.of(parseInt(firstLine[0]),parseInt(firstLine[1]),parseInt(firstLine[2]),parseInt(firstLine[3]),parseInt(firstLine[4]),parseInt(firstLine[5]));
                allcommands.remove(0);
                for (String i : allcommands) {
                    write("COMMAND: " + i);
                    commandLine = i.split("\t");
                    switch (commandLine[0]) {
                        case "Add":
                            if (commandLine[1].equals("SmartLamp")) {
                                switch (commandLine.length) {
                                    case 3:
                                        lamp lampWithName = new lamp(commandLine[2]);
                                        break;
                                    case 4:
                                        lamp lampWithStatus = new lamp(commandLine[2], commandLine[3]);
                                        break;
                                    case 6:
                                        try {
                                            lamp lambWithAll = new lamp(commandLine[2], commandLine[3], parseInt(commandLine[4]), Integer.parseInt(commandLine[5]));
                                        } catch (NumberFormatException x) {
                                            write("ERROR: Erroneous command!");
                                        }
                                        break;
                                    default:
                                        write("ERROR: Erroneous command!");
                                        break;
                                }

                            } else if (commandLine[1].equals("SmartColorLamp")) {
                                switch (commandLine.length) {
                                    case 3:
                                        colorLamp colorLampWithName = new colorLamp(commandLine[2]);
                                        break;
                                    case 4:
                                        colorLamp colorLampWithStatus = new colorLamp(commandLine[2], commandLine[3]);
                                        break;
                                    case 6:
                                        try {
                                            colorLamp colorLampWithAll = new colorLamp(commandLine[2], commandLine[3], commandLine[4], parseInt(commandLine[5]));
                                        } catch (NumberFormatException x) {

                                            write("ERROR: Erroneous command!");

                                        }
                                        break;
                                    default:
                                        write("ERROR: Erroneous command!");
                                        break;
                                }
                            } else if (commandLine[1].equals("SmartCamera")) {
                                switch (commandLine.length) {
                                    case 4:
                                        try {
                                            camera camera = new camera(commandLine[2], Double.parseDouble(commandLine[3]));
                                        } catch (NumberFormatException e) {
                                            write("ERROR: Erroneous command!");
                                        }
                                        break;
                                    case 5:
                                        try {
                                            camera camera = new camera(commandLine[2], Double.parseDouble(commandLine[3]), commandLine[4]);
                                        } catch (NumberFormatException e) {
                                            write("ERROR: Erroneous command!");
                                        }
                                        break;
                                    default:
                                        write("ERROR: Erroneous command!");
                                        break;

                                }
                            } else if (commandLine[1].equals("SmartPlug")) {
                                switch (commandLine.length) {
                                    case 3:
                                        plug plug = new plug(commandLine[2]);
                                        break;
                                    case 4:
                                        plug plugwithstatus = new plug(commandLine[2], commandLine[3]);
                                        break;
                                    case 5:
                                        try {
                                            plug plugwitall = new plug(commandLine[2], commandLine[3], Double.parseDouble(commandLine[4]));
                                        } catch (NumberFormatException e) {
                                            write("ERROR: Erroneous command!");
                                        }
                                        break;
                                    default:
                                        write("ERROR: Erroneous command!");
                                        break;
                                }
                            } else write("ERROR: Erroneous command!");
                            break;
                        case "Remove":
                            String nameForNow = commandLine[1];
                            if (devices.devices.contains(commandLine[1])) {
                                write("SUCCESS: Information about removed smart device is as follows:");

                                if(devices.deviceObjects.get(whichDevice(commandLine[1])) instanceof colorLamp) {

                                        devices.deviceObjects.get(whichDevice(commandLine[1])).setStatus("Off");
                                        if(((colorLamp) devices.deviceObjects.get(whichDevice(commandLine[1]))).getColorToWrite()==null){
                                            write("Smart Color Lamp " + devices.deviceObjects.get(whichDevice(commandLine[1])).getName() + " is " + devices.deviceObjects.get(whichDevice(commandLine[1])).getStatus() + " and its color value is "
                                                    + ((colorLamp) devices.deviceObjects.get(whichDevice(commandLine[1]))).getKelvin() + "K with " + ((colorLamp) devices.deviceObjects.get(whichDevice(commandLine[1]))).getBrightness()
                                                    + "% brightness, and its time to switch its status is " + devices.deviceObjects.get(whichDevice(commandLine[1])).getSwitchTime());

                                        }
                                        else{
                                            write("Smart Color Lamp " + devices.deviceObjects.get(whichDevice(commandLine[1])).getName() + " is " + devices.deviceObjects.get(whichDevice(commandLine[1])).getStatus() + " and its color value is "
                                                    + ((colorLamp) devices.deviceObjects.get(whichDevice(commandLine[1]))).getColor() + " with " + ((colorLamp) devices.deviceObjects.get(whichDevice(commandLine[1]))).getBrightness()
                                                    + "% brightness, and its time to switch its status is " + devices.deviceObjects.get(whichDevice(commandLine[1])).getSwitchTime());

                                        }

                                    devices.deviceObjects.removeIf(device -> device.getName().equals(nameForNow));
                                    colorLamp.colorLampList.removeIf(device -> device.getName().equals(nameForNow));
                                    devices.devices.remove(commandLine[1]);


                                }
                                else if(devices.deviceObjects.get(whichDevice(commandLine[1])) instanceof lamp){
                                    devices.deviceObjects.get(whichDevice(commandLine[1])).setStatus("Off");

                                    write("Smart Color Lamp " + devices.deviceObjects.get(whichDevice(commandLine[1])).getName() + " is " + devices.deviceObjects.get(whichDevice(commandLine[1])).getStatus() + " and its color value is "
                                            + ((lamp) devices.deviceObjects.get(whichDevice(commandLine[1]))).getKelvin() + "K with " + ((lamp) devices.deviceObjects.get(whichDevice(commandLine[1]))).getBrightness()
                                            + "% brightness, and its time to switch its status is " + devices.deviceObjects.get(whichDevice(commandLine[1])).getSwitchTime());
                                    devices.deviceObjects.removeIf(device -> device.getName().equals(nameForNow));
                                    lamp.lampList.removeIf(device -> device.getName().equals(nameForNow));
                                    devices.devices.remove(commandLine[1]);
                                }
                                else if(devices.deviceObjects.get(whichDevice(commandLine[1])) instanceof camera){
                                    devices.deviceObjects.get(whichDevice(commandLine[1])).setStatus("Off");
                                    write("Smart Camera " + devices.deviceObjects.get(whichDevice(commandLine[1])).getName() + " is " + devices.deviceObjects.get(whichDevice(commandLine[1])).getStatus() + " and used "
                                            + ((camera) devices.deviceObjects.get(whichDevice(commandLine[1]))).getStorage() +
                                            " MB of storage so far (excluding current status), and its time to switch its status is "
                                            + devices.deviceObjects.get(whichDevice(commandLine[1])).getSwitchTime());
                                    devices.deviceObjects.removeIf(device -> device.getName().equals(nameForNow));
                                    camera.cameraList.removeIf(device -> device.getName().equals(nameForNow));
                                    devices.devices.remove(commandLine[1]);



                                }
                                else if(devices.deviceObjects.get(whichDevice(commandLine[1])) instanceof plug){
                                    devices.deviceObjects.get(whichDevice(commandLine[1])).setStatus("Off");
                                    write("Smart Plug " + devices.deviceObjects.get(whichDevice(commandLine[1])).getName() + " is " + devices.deviceObjects.get(whichDevice(commandLine[1])).getStatus() + " and consumed " + ((plug) devices.deviceObjects.get(whichDevice(commandLine[1]))).getEnergy()
                                            + "W so far (excluding current device), and its time to switch its status is " + devices.deviceObjects.get(whichDevice(commandLine[1])).getSwitchTime());
                                    devices.deviceObjects.removeIf(device -> device.getName().equals(nameForNow));
                                    plug.plugList.removeIf(device -> device.getName().equals(nameForNow));
                                    devices.devices.remove(commandLine[1]);


                                }


                            } else write("ERROR: There is not such a device!");
                        case "SetTime":
                            try {
                                String[] date = commandLine[1].split("[-_:]+");
                                LocalDateTime timeCheck=LocalDateTime.of(parseInt(date[0]), parseInt(date[1]), parseInt(date[2]), parseInt(date[3]), parseInt(date[4]), parseInt(date[5]));
                                if(devices.Time.isBefore(timeCheck)) {
                                    devices.Time = LocalDateTime.of(parseInt(date[0]), parseInt(date[1]), parseInt(date[2]), parseInt(date[3]), parseInt(date[4]), parseInt(date[5]));
                                    IsItSwitchTime(devices.Time);


                                }
                                else write("ERROR: Time cannot be reversed!");
                            }
                            catch (DateTimeException x){
                                write("ERROR: Time format is not correct!");

                            }
                            catch (Exception e) {
                                write("ERROR: Erroneous command!");
                            }
                            break;
                        case "SkipMinutes":
                            try {
                                if (0 < parseInt(commandLine[1])) {
                                    devices.Time = devices.Time.plusMinutes(parseInt(commandLine[1]));
                                    IsItSwitchTime(devices.Time);
                                } else if (0 == parseInt(commandLine[1])) {
                                    write("ERROR: There is nothing to skip!");
                                } else {
                                    write("ERROR: Time cannot be reversed!");
                                }


                            } catch (NumberFormatException e) {
                                write("ERROR: Erroneous command!");
                            }

                            break;
                        case "SetSwitchTime":
                            try {
                                String[] dateTime = commandLine[2].split("[-_:]");
                                LocalDateTime sTime = LocalDateTime.of(parseInt(dateTime[0]), parseInt(dateTime[1]), parseInt(dateTime[2]), parseInt(dateTime[3]), parseInt(dateTime[4]), parseInt(dateTime[5]));
                                if(sTime.isBefore(devices.Time)) {
                                    listOfSwitch.add(sTime);
                                    if (devices.devices.contains(commandLine[1])) {
                                        devices.deviceObjects.get(whichDevice(commandLine[1])).setSwitchTime(sTime);
                                    } else {
                                        write("ERROR: There is not such a device!");
                                    }
                                }
                            } catch (NumberFormatException e) {
                                write("ERROR: Erroneous command!");
                            }
                            catch (DateTimeException e){
                                write("ERROR: Time format is not correct!");
                            }
                            break;
                        case "SetInitialTime":
                            write("ERROR: Erroneous command!");
                            break;
                        case "ChangeName":
                            try {
                                if (devices.devices.contains(commandLine[1])) {
                                    if (!commandLine[1].equalsIgnoreCase(commandLine[2])) {
                                        if (!devices.devices.contains(commandLine[2])) {
                                            devices.deviceObjects.get(whichDevice(commandLine[1])).setName(commandLine[2]);

                                        } else {
                                            write("ERROR: There is already a smart device with same name!");
                                        }
                                    } else {
                                        write("ERROR: Both of the names are the same, nothing changed!");
                                    }
                                } else {
                                    write("ERROR: There is not such a device!");
                                }
                            }
                            catch (Exception e){
                                write("ERROR: Erroneous command!");
                            }
                            break;
                        case "Switch":
                            try {
                                if (devices.devices.contains(commandLine[1])) {
                                    if(devices.deviceObjects.get(whichDevice(commandLine[1])) instanceof camera) {
                                        devices.deviceObjects.get(whichDevice(commandLine[1])).changeStatus(commandLine[2]);
                                    }


                                else if(devices.deviceObjects.get(whichDevice(commandLine[1])) instanceof plug){
                                        devices.deviceObjects.get(whichDevice(commandLine[1])).changeStatus(commandLine[2]);

                                    }
                                else{
                                        devices.deviceObjects.get(whichDevice(commandLine[1])).changeStatus(commandLine[2]);
                                    }

                                }
                                else{
                                    write("ERROR: There is not such a device!");
                                }
                            }
                            catch (Exception e){
                                write("ERROR: Erroneous command!");
                            }
                            break;
                        case "PlugIn":
                            try{
                                if(devices.devices.contains(commandLine[1])){
                                    if(devices.deviceObjects.get(whichDevice(commandLine[1])) instanceof plug) {

                                        ((plug) devices.deviceObjects.get(whichDevice(commandLine[1]))).setPlugStatus("In",Double.parseDouble(commandLine[2]));

                                        }

                                    else{
                                        write("ERROR: This device is not a smart plug!");
                                    }


                                }
                                else{
                                    write("ERROR: There is not such a device!");
                                }


                            }
                            catch(Exception e){
                                write("ERROR: Erroneous command!");
                            }
                            break;
                        case "PlugOut":
                            try{
                                if(devices.devices.contains(commandLine[1])){
                                    if(( devices.deviceObjects.get(whichDevice(commandLine[1]))) instanceof plug) {

                                            ((plug) devices.deviceObjects.get(whichDevice(commandLine[1]))).setPlugStatus("Out",0);
                                        }


                                    else{
                                        write("ERROR: This device is not a smart plug!");
                                    }


                                }
                                else{
                                    write("ERROR: There is not such a device!");
                                }

                            }
                            catch (Exception e){
                                write("ERROR: Erroneous command!");
                            }
                            break;
                        case "ZReport":
                            write("Time is\t"+ devices.Time.format(tf));
                            for(devices device:devices.deviceObjects) {
                                if(device instanceof plug ) {

                                    write("Smart Plug " + device.getName() + " is " + device.getStatus() + " and consumed " + df.format(((plug) device).getEnergy())
                                            + "W so far (excluding current device), and its time to switch its status is " + device.getSwitchTime());
                                }
                                else if(device instanceof camera) {
                                    write("Smart Camera " + device.getName() + " is " + device.getStatus() + " and used "
                                            + ((camera) device).getStorage() +
                                            " MB of storage so far (excluding current status), and its time to switch its status is "
                                            + device.getSwitchTime());
                                }
                                else if(device instanceof  lamp) {
                                    write("Smart Lamp " + device.getName() + " is " + device.getStatus() + " and its kelvin value is "
                                            + ((lamp) device).getKelvin() + "K with " + ((lamp) device).getBrightness()
                                            + "% brightness, and its time to switch its status is " + device.getSwitchTime());

                                }
                                else if (device instanceof colorLamp) {
                                    if(((colorLamp) device).getColorToWrite()==null) {
                                        write("Smart Color Lamp " + device.getName() + " is " + device.getStatus() + " and its color value is "
                                                + ((colorLamp) device).getKelvin() + "K with " + ((colorLamp) device).getBrightness()
                                                + "% brightness, and its time to switch its status is " + device.getSwitchTime());
                                    }
                                    else{
                                        write("Smart Color Lamp " + device.getName() + " is " + device.getStatus() + " and its color value is "
                                                + ((colorLamp) device).getColorToWrite() + " with " + ((colorLamp) device).getBrightness()
                                                + "% brightness, and its time to switch its status is " + device.getSwitchTime());

                                    }

                                }
                            }
                            break;
                        case "SetBrightness":
                            try{
                                if(devices.devices.contains(commandLine[1])){
                                    if(devices.deviceObjects.get(whichDevice(commandLine[1])) instanceof lamp){
                                        ((lamp) devices.deviceObjects.get(whichDevice(commandLine[1]))).setBrightness(parseInt(commandLine[2]));
                                    }
                                    else if(devices.deviceObjects.get(whichDevice(commandLine[1])) instanceof colorLamp){
                                        ((colorLamp) devices.deviceObjects.get(whichDevice(commandLine[1]))).setBrightness(parseInt(commandLine[2]));

                                    }
                                    else write("ERROR: This device is not a smart lamp!");
                                }
                                else write("ERROR: There is not such a device!");
                            }
                            catch(Exception e){
                                write("ERROR: Erroneous command!");
                            }
                            break;
                        case "SetColorCode" :
                            try {
                                if (devices.devices.contains(commandLine[1])) {
                                    if (devices.deviceObjects.get(whichDevice(commandLine[1])) instanceof colorLamp) {
                                        ((colorLamp) devices.deviceObjects.get(whichDevice(commandLine[1]))).setColor(commandLine[2]);

                                    } else write("ERROR: This device is not a smart color lamp!");
                                } else write("ERROR: There is not such a device!");
                            }
                            catch (NumberFormatException x){
                                write("ERROR: Erroneous command!");

                            }
                            catch (Exception e){
                                write("ERROR: Erroneous command!");
                            }
                            break;
                        case "SetColor":
                            try{
                                if(devices.devices.contains(commandLine[1])){
                                    if(devices.deviceObjects.get(whichDevice(commandLine[1])) instanceof colorLamp){
                                        ((colorLamp) devices.deviceObjects.get(whichDevice(commandLine[1]))).
                                                setColorAndBrightness(commandLine[2],parseInt(commandLine[3]));


                                    }
                                    else write("ERROR: This device is not a smart color lamp!");
                                }
                                else write("ERROR: There is not such a device!");
                            }
                            catch (Exception e){
                                write("ERROR: Erroneous command!");
                            }
                            break;
                        case "SetWhite":
                            try{
                                if(devices.devices.contains(commandLine[1])){
                                    if(devices.deviceObjects.get(whichDevice(commandLine[1])) instanceof lamp){
                                        ((lamp) devices.deviceObjects.get(whichDevice(commandLine[1]))).
                                                setWhite(parseInt(commandLine[2]),parseInt(commandLine[3]));
                                    }
                                    else if(devices.deviceObjects.get(whichDevice(commandLine[1])) instanceof colorLamp){
                                        ((colorLamp) devices.deviceObjects.get(whichDevice(commandLine[1]))).
                                                setWhite(parseInt(commandLine[2]),parseInt(commandLine[3]));

                                    }
                                    else write("ERROR: This device is not a smart lamp!");

                                }
                                else write("ERROR: There is not such a device!");

                            }
                            catch(Exception e){
                                write("ERROR: Erroneous command!");
                            }
                            break;
                        case "SetKelvin":
                            try {
                                if (devices.devices.contains(commandLine[1])) {
                                    if (devices.deviceObjects.get(whichDevice(commandLine[1])) instanceof lamp) {
                                        ((lamp) devices.deviceObjects.get(whichDevice(commandLine[1]))).setKelvin(parseInt(commandLine[2]));

                                    } else if (devices.deviceObjects.get(whichDevice((commandLine[1]))) instanceof colorLamp) {
                                        ((colorLamp) devices.deviceObjects.get(whichDevice(commandLine[1]))).setKelvin(parseInt(commandLine[2]));


                                    } else write("ERROR: This device is not a smart lamp!");
                                } else write("ERROR: There is not such a device!");
                            }
                            catch(Exception e){
                                write("ERROR: Erroneous command!");
                            }
                            break;
                        case "Nop":
                            if(!listOfSwitch.isEmpty()){
                                Collections.sort(listOfSwitch);
                                devices.Time = listOfSwitch.get(0);
                                listOfSwitch.remove(0);
                                IsItSwitchTime(devices.Time);
                            }
                            else{
                                write("ERROR: There is nothing to switch!");
                            }
                            break;
                        default:
                            write("ERROR: Erroneous command!");
                            break;






                    }


                }
            }
            else{
                write("ERROR: First command must be set initial time! Program is going to terminate!");
            }



        }

        catch(IOException e) {
            e.printStackTrace();
        }





    }
}