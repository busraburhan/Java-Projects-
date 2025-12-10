import java.util.ArrayList;

public class Member {
    public static  ArrayList<Member> listOfMember= new ArrayList<>();


    private String Status;

    private int ID;

    private static int IDcount=1;

    private int HowManyBorrowed;

    public static int numberOfStudent=0;
    public static int numberOfAcademics=0;


    public  Member(String s){
            this.ID = IDcount;
            this.Status = s;
            IDcount++;
            listOfMember.add(this);
            if(s.equals("A")){
                write.write("Created new member: Academic [id: "+ this.ID+"]");
                numberOfAcademics++;
            }
            else{
                write.write("Created new book: Student [id: "+ this.ID+"]");
                numberOfStudent++;
            }


    }

    public String getStatus(){
        return this.Status;

    }
    public int getID() {
        return this.ID;
    }

    public int getHowManyBorrowed(){
        return this.HowManyBorrowed;
    }
    public void setHowManyBorrowed(int num){
        this.HowManyBorrowed=this.HowManyBorrowed +num ;
    }


}
