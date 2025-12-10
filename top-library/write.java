import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class write {
    public static String outputName;
    public static void write(String s){
        try {
            File output = new File("output.txt");
            BufferedWriter bw= new BufferedWriter(new FileWriter(output,true ));
            bw.write(s+"\n");
            bw.close();


        }
        catch(Exception E){
            E.printStackTrace();
        }
    }
}
