import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        write.outputName=args[1];
        try {
            File input = new File(args[0]);
            BufferedReader br = new BufferedReader(new FileReader(input));
            String line;
            while ((line =br.readLine())!= null){
                String[] Line=line.split("\t");
                switch(Line[0]){
                    case "addBook":
                        Book book=new Book(Line[1]);
                        break;
                    case "addMember":
                        Member member =new Member(Line[1]);
                        break;
                    case "borrowBook":
                        commands.borrowBook(Integer.parseInt(Line[1]),Integer.parseInt(Line[2]), LocalDate.parse(Line[3]));
                        break;

                    case "readInLibrary":
                        commands.ReadInLibrary(Integer.parseInt(Line[1]),Integer.parseInt(Line[2]), LocalDate.parse(Line[3]));
                        break;

                    case "returnBook":
                        commands.returnBook(Integer.parseInt(Line[1]),Integer.parseInt(Line[2]), LocalDate.parse(Line[3]));
                        break;
                    case "extendBook":
                        commands.extendBook(Integer.parseInt(Line[1]),Integer.parseInt(Line[2]), LocalDate.parse(Line[3]));
                        break;
                    case "getTheHistory":
                        commands.history();
                        break;


                }
            }
        }
        catch(Exception e){
             e.printStackTrace();
        }

    }
}