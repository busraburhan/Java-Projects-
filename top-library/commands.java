
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class commands {


    public static void borrowBook(int IDofBook, int IDofMember, LocalDate date) {
        if (Book.listOfBook.get(IDofBook-1).getType().equals("P")) {
            if (Member.listOfMember.get(IDofMember-1).getStatus().equals("A")) {
                if (Member.listOfMember.get(IDofMember-1).getHowManyBorrowed() < 4) {
                    Member.listOfMember.get(IDofMember-1).setHowManyBorrowed(1);
                    System.out.println("*"+Book.listOfBook.get(IDofBook-1).getBorrowDate());
                    Book.listOfBook.get(IDofBook-1).setBorrowDate(date.plusDays(14));
                    System.out.println("-"+Book.listOfBook.get(IDofBook-1).getBorrowDate());
                    Book.listOfBook.get(IDofBook-1).setIsthisBorrowed(1);
                    Book.borrowedBook.put(IDofBook,"The book ["+IDofBook+"] was borrowed by member ["+IDofMember+"] at "+date);
                    write.write("The book ["+IDofBook+"] was borrowed by member ["+IDofMember+"] at "+date);

                } else write.write("You have exceeded the borrowing limit!");
            } else {
                if (Member.listOfMember.get(IDofMember-1).getHowManyBorrowed() < 2) {
                    Member.listOfMember.get(IDofMember-1).setHowManyBorrowed(1);
                    Book.listOfBook.get(IDofBook-1).setBorrowDate(date.plusDays(7));
                    Book.listOfBook.get(IDofBook-1).setIsthisBorrowed(1);
                    Book.borrowedBook.put(IDofBook,"The book ["+IDofBook+"] was borrowed by member ["+IDofMember+"] at "+date);
                    write.write("The book ["+ IDofBook+"] was borrowed by member ["+IDofMember+"] at "+date);

                } else write.write("You have exceeded the borrowing limit!");

            }

        } else write.write("You cannot borrow this book!");


    }

    public static void ReadInLibrary(int IDofBook, int IDofMember, LocalDate date) {
        if (Book.listOfBook.get(IDofBook-1).getType().equals("P")) {
            if (Book.listOfBook.get(IDofBook-1).getIsthisBorrowed() != 1) {
                Book.listOfBook.get(IDofBook-1).setIsthisBorrowed(1);
                Book.libraryBook.put(IDofBook,"The book ["+IDofBook+"] was read in library by member ["+IDofMember+"] at "+date);
                write.write("The book ["+IDofBook+"] was read in library by member ["+IDofMember+"] at "+date);


            } else write.write("You cannot read this book!");

        } else {
            if (Member.listOfMember.get(IDofMember-1).getStatus().equals("A")) {
                if (Book.listOfBook.get(IDofBook-1).getIsthisBorrowed() != 1) {
                    Book.listOfBook.get(IDofBook-1).setIsthisBorrowed(1);
                    Book.libraryBook.put(IDofBook,"The book ["+IDofBook+"] was read in library by member ["+IDofMember+"] at "+date);

                    write.write("The book ["+IDofBook+"] was read in library by member ["+IDofMember+"] at "+date);



                } else write.write("You cannot read this book!");


            } else write.write("Students can not read handwritten books!");
        }


    }

    public static void returnBook(int IDofBook, int IDofMember, LocalDate date) {
        if(Book.listOfBook.get(IDofBook-1).getBorrowDate()!=null) {
            if (Book.listOfBook.get(IDofBook - 1).getBorrowDate().compareTo(date) <= 0) {
                Book.listOfBook.get(IDofBook - 1).setIsthisBorrowed(0);
                Member.listOfMember.get(IDofMember - 1).setHowManyBorrowed(-1);
                Book.listOfBook.get(IDofBook - 1).setExtendCount(0);
                Book.borrowedBook.remove(IDofBook);
                write.write("The book [" + IDofBook + "] was returned by member [" + IDofMember + "] at " + date + " Fee: 0");


            } else {
                long fee = ChronoUnit.DAYS.between(date, Book.listOfBook.get(IDofBook - 1).getBorrowDate());
                write.write("You must pay a penalty!");
                Book.listOfBook.get(IDofBook - 1).setIsthisBorrowed(0);
                Member.listOfMember.get(IDofMember - 1).setHowManyBorrowed(-1);
                Book.listOfBook.get(IDofBook - 1).setExtendCount(0);
                Book.borrowedBook.remove(IDofBook);
                write.write("The book [" + IDofBook + "] was returned by member [" + IDofMember + "] at " + date+" Fee: "+fee);


            }
        }
        else {
            Book.libraryBook.remove(IDofBook);
            Book.listOfBook.get(IDofBook - 1).setIsthisBorrowed(0);
            Member.listOfMember.get(IDofMember - 1).setHowManyBorrowed(-1);
            Book.listOfBook.get(IDofBook - 1).setExtendCount(0);
            write.write("The book [" + IDofBook + "] was returned by member [" + IDofMember + "] at " + date + " Fee: 0");

        }

    }
    public static void extendBook(int IDofBook, int IDofMember, LocalDate date){
        if(Book.listOfBook.get(IDofBook-1).getExtendCount()==0){
            write.write("The deadline of book ["+IDofBook+"] was extended by member ["+IDofMember+"] at "+Book.listOfBook.get(IDofBook-1).getBorrowDate());
            long daysBetween= ChronoUnit.DAYS.between(Book.listOfBook.get(IDofBook-1).getBorrowDate(),date);
            LocalDate NewDate= Book.listOfBook.get(IDofBook-1).getBorrowDate().plusDays(daysBetween);
            Book.listOfBook.get(IDofBook-1).setBorrowDate(NewDate);
            Book.listOfBook.get(IDofBook-1).setExtendCount(1);
            write.write("New deadline of book ["+IDofBook+"] is 2023-04-21");


        }
        else write.write("You cannot extend the deadline!");

    }

    public static void history(){
        write.write("History of library:\n");
        write.write("Number of students:" + Member.numberOfStudent);
        for(Member i: Member.listOfMember){
            if(i.getStatus().equals("S")){
                write.write("Student [id: "+i.getID()+"]");

            }
        }
        write.write("\nNumber of academics: "+Member.numberOfAcademics);
        for(Member i: Member.listOfMember){
            if(i.getStatus().equals("A")){
                write.write("Academic [id: "+i.getID()+"]");

            }
        }
        write.write("\nNumber of printed books: "+Book.numberOfP);
        for(Book i: Book.listOfBook){
            if(i.getType().equals("P")){
                write.write("Printed [id: "+i.getID()+"]");
            }
        }

        write.write("\nNumber of handwritten books: "+Book.numberOfP);
        for(Book i: Book.listOfBook){
            if(i.getType().equals("H")){
                write.write("Handwritten [id: "+i.getID()+"]");
            }
        }

        write.write("\nNumber of borrowed books: "+Book.borrowedBook.size());
        for(String i: Book.borrowedBook.values()){
            write.write(i);

        }
        write.write("\nNumber of books read in library: "+Book.libraryBook.size());
        for(String i: Book.libraryBook.values()){
            write.write(i);

        }


    }
}
