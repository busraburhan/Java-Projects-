import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Book {
    public static ArrayList<Book> listOfBook= new ArrayList<>();

    private String type;

    private int IsthisBorrowed=0;

    private int ID;
    private int extendCount=0;

    private LocalDate borrowDate;

    private static int IDcount=1;

    public static HashMap<Integer,String> borrowedBook=  new HashMap<>();

    public static HashMap<Integer,String> libraryBook=  new HashMap<>();


    public static int numberOfP=0;
    public static int numberOfH=0;

    public Book(String type){
        this.ID=IDcount;
        IDcount++;
        listOfBook.add(this);
        this.type=type;
        if(type.equals("P")) {
            write.write("Created new book: Printed [id: "+ this.ID+"]");
            numberOfP++;
        }
        else{
            write.write("Created new book: Handwritten [id: "+ this.ID+"]");
            numberOfH++;
        }
    }
    public int getID(){
        return this.ID;
    }

    public String getType(){
        return this.type;
    }
    public void setBorrowDate(LocalDate date){
        this.borrowDate=date;
    }

    public int getIsthisBorrowed(){
        return this.IsthisBorrowed;
    }
    public void setIsthisBorrowed(int num){
        this.IsthisBorrowed=num;
    }
    public LocalDate getBorrowDate(){
        return this.borrowDate;
    }
    public int getExtendCount(){
        return this.extendCount;
    }

    public void setExtendCount(int extendCount) {
        this.extendCount = extendCount;
    }


}
