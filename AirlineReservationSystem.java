import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter; 

class UserDetails {
    private String name, email ,phoneNumber;
    private int age;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
}

public class AirlineReservationSystem {
    
    private static int totalSeats = 50;
    private static int bookedSeats = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserDetails user = new UserDetails();

        System.out.println("_____  TULIP AIRLINE   ______\n\n");
        System.out.println("WELCOME TO TULIP AIRLINE RESERVATION SYSTEM\n");
        System.out.println("PLEASE ENTER YOUR BASIC DETAILS TO CONTINUE\n\n");


        System.out.println("Please enter your name:");
        String name = scanner.nextLine();
        user.setName(name);
        System.out.println("Please enter your age:");
        int age = scanner.nextInt();
        scanner.nextLine();
        user.setAge(age);
        System.out.println("Please enter your email address:");
        String email = scanner.nextLine();
        user.setEmail(email);
        System.out.println("Please enter your phone number:");
        String phoneNumber = scanner.nextLine();
        user.setPhoneNumber(phoneNumber);
        String from , to;
        System.out.println("\n\nFrom : ");
        from = scanner.nextLine();
        System.out.println("To : ");
        to = scanner.nextLine();
        String ddate , rdate;
        System.out.println("ENTER DEPARTURE DATE : ");
        ddate = scanner.nextLine();
        System.out.println("RETURN DATE : ");
        rdate = scanner.nextLine();
            
         
         
        System.out.println("\nTHESE ARE YOUR DEATAILS\n");
        
        System.out.println("Name: " + user.getName());
        System.out.println("Age: " + user.getAge());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Phone number: " + user.getPhoneNumber());
        System.out.println("FROM: "+from);
        System.out.println("TO:"+to);
        System.out.println("DEPARTURE DATE : "+ddate);
        System.out.println("RETURN DATE : "+rdate);
        System.out.println("AIRLINE NUMER : ABC1234");
        System.out.println("GATE NUMBER : 10");


        File inputFile = new File("Details.txt");
        
        System.out.println("\nBEFORE BOOKING YOUR TICKET PLEASE READ INSTRUCTIONS GIVEN BELOW : \n");
        System.out.println("1. The total price displayed on the Site includes all applicable government taxes.");
        System.out.println("2. You are required to pay the entire amount prior to the confirmation of your booking.");
        System.out.println("3. All Tickets issued to the customer shall additionally be governed under the terms and conditions as laid out by the respective Airlines.");
        System.out.println("4. We recommend user to refer airlines terms and conditions before booking the ticket.");
        System.out.println("5. Seats are subject to availability");
        System.out.println("6. Flight schedules and timings are subject to regulatory approvals and change(s)");
        System.out.println("7. As per airline policy, unaccompanied minor passengers are required to carry specific supporting documents. Please contact the airline directly for the same");

        int choice;
        String classe;
        System.out.println("\n\nENTER TYPE OF CLASS (ECONOMY , BUSINESS , FIRST CLASS) :");
        classe=scanner.next();

        do {
            System.out.println("\n\n1. Book a ticket");
            System.out.println("2. Cancel a booking");
            System.out.println("3. Check available seats");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    bookTicket();
                    break;
                case 2:
                    cancelBooking();
                    break;
                case 3:
                    checkAvailableSeats();
                    break;
                case 4:
                    System.out.println("\nNOW YOU CAN PROCEED WITH PAYMENT");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 4);
        int totalPrice=bookedSeats*1000;
        System.out.println("\n\nTOTAL AMOUNT = "+totalPrice);
        
        int choicee , amount,cnum ,cvv , edate;
        String upi ,num, uname ,upass,chname;
        System.out.println("\n\nENTER\n1 FOR UPI\n2 FOR CREDIT/DEBIT CARD\n3 FOR NETBANKING ");
        choicee = scanner.nextInt();
        if(choicee == 1){
             System.out.print("ENTER UPI ID :");
             upi = scanner.next();
             System.out.print("ENTER MOBILE NUMBER :");
             num=scanner.next();
             System.out.print("AMOUNT TO BE PAID = "+ totalPrice);
             System.out.println("\nENTER AMOUNT :");
             amount=scanner.nextInt();
             if(amount==totalPrice){
                System.out.println("PAYMENT SUCCESSFULL");
             }
             else{
                System.out.println("ENTER VALID AMOUNT");
             }
             
            }
        else if(choicee == 2){
            System.out.print("ENTER CARD NUMBER :");
            cnum = scanner.nextInt();
            System.out.print("ENTER CVV :");
            cvv=scanner.nextInt();
            System.out.println("ENTER EXPIRY MONTH AND YEAR : ");
            edate=scanner.nextInt();
            System.out.print("NAME OF CARD HOLDER: ");
            chname=scanner.next();
            System.out.print("AMOUNT TO BE PAID = "+totalPrice);
            System.out.print("\nENTER AMOUNT :");
            amount=scanner.nextInt();
            if(amount==totalPrice){
                System.out.println("PAYMENT SUCCESSFULL");
             }
             else{
                System.out.println("ENTER VALID AMOUNT");
            }
            
        }
        else if(choicee == 3){
            System.out.print("ENTER USERNAME :");
            uname = scanner.next();
            System.out.print("ENTER PASSWORD :");
            upass=scanner.next();
            System.out.print("AMOUNT TO BE PAID = "+totalPrice);
            System.out.print("\nENTER AMOUNT :");
            amount=scanner.nextInt();
            if(amount==totalPrice){
                System.out.println("PAYMENT SUCCESSFULL");
             }
             else{
                System.out.println("ENTER VALID AMOUNT");
            }
        }
        System.out.println("NUMBER OF TICKETS BOOKED :"+bookedSeats);
        System.out.println("\n\nYOUR TICKETS HAVE BEEN BOOKED \nTHANK YOU FOR BOOKING \nHAVE A GOOD DAY ");
        try {
                FileWriter fw = new FileWriter(inputFile);
                PrintWriter pw = new PrintWriter(fw);
                pw.println("_____  TULIP AIRLINE   ______\n\n");
                pw.println("Name: " + user.getName() );
                pw.println("Age: " + user.getAge());
                pw.println("Email: " + user.getEmail());
                pw.println("Phone number: " + user.getPhoneNumber());
                pw.println("FROM: "+from);
                pw.println("TO: "+to);
                pw.println("DEPARTURE DATE: "+ddate);
                pw.println("RETURN DATE: "+rdate);
                pw.println("AIRLINE NUMER : ABC1234");
                pw.println("GATE NUMBER : 10");
                pw.println("\nTOTAL TICKETS BOOKED :" + bookedSeats );
                pw.println("CLASS TYPE : "+classe);
                pw.close();
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
    }

    private static void bookTicket() {
        Scanner scanner = new Scanner(System.in);
        if (bookedSeats == totalSeats) {
            System.out.println("Sorry, no seats available.");
        } else {
            bookedSeats++;
            System.out.println("ENTER NAME OF PASSENGER : ");
            String pname = scanner.next();
            System.out.println("ENTER AGE OF PASSENGER : ");
            int agee = scanner.nextInt();
            System.out.println("ENTER SEAT NUMBER : ");
            String seatn = scanner.next();
            System.out.println("Ticket booked successfully!");
            System.out.println("Remaining seats: " + (totalSeats - bookedSeats));
        }
    }

    private static void cancelBooking() {
        if (bookedSeats == 0) {
            System.out.println("No bookings to cancel.");
        } else {
            bookedSeats--;
            System.out.println("Booking canceled successfully!");
            System.out.println("Remaining seats: " + (totalSeats - bookedSeats));
        }
    }

    private static void checkAvailableSeats() {
        int availableSeats = totalSeats - bookedSeats;
        System.out.println("Available seats: " + availableSeats);
    }

    
}
