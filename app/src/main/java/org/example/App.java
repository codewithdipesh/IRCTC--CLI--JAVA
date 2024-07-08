/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example;

import org.example.entities.Train;
import org.example.entities.User;
import org.example.service.TrainService;
import org.example.service.UserBookingService;
import org.example.utils.UserServiceUtil;

import java.io.IOException;
import java.util.*;

public class App {


    public static void main(String[] args) {

        System.out.println("Running the IRCTC booking App.....");
        Scanner scanner = new Scanner(System.in);
        int option =0;
        UserBookingService userBookingService;
        try {
            userBookingService = new UserBookingService();
        } catch (IOException e) {
            System.out.println("There is something wrong");
              // Add this line
            return;
        }
        while(option!=7){
            System.out.println("Enter Number for choosing options ");
            System.out.println("1. SignUp");
            System.out.println("2. Login");
            System.out.println("3. Fetch Booking");
            System.out.println("4. Search Train");
            System.out.println("5. Book a seat");
            System.out.println("6. Cancel my Booking");
            System.out.println("7. Exit the App!");
            option = scanner.nextInt();
            switch(option){
                case 1:
                    System.out.println("Enter the username :");
                    String nameToSignUp = scanner.next();
                    System.out.println("Enter the password :");
                    String passwordToSignUp = scanner.next();
                    User userToSignUp = new User(
                            nameToSignUp,
                            UserServiceUtil.hashPassword(passwordToSignUp),
                            new ArrayList<>(),
                            UUID.randomUUID().toString()
                    );
                    if(userBookingService.SignUp(userToSignUp)){
                        System.out.println("User Created succesfully");
                    }else{
                        System.out.println("Something Error Happened");
                    }
                    break;
                case 2:
                    System.out.println("Enter the username :");
                    String nameToLogin = scanner.next();
                    System.out.println("Enter the password :");
                    String passwordToLogin = scanner.next();
                    if(userBookingService.loginUser(nameToLogin,passwordToLogin)){
                        System.out.println("User Logged In succesfully");
                    }else{
                        System.out.println("Something Error Happened");
                    }
                    break;

                case 3:
                    System.out.println("The Booking Are ......");
                    if(userBookingService.fetchBooking()){
                      //////////printing tickets
                    }else{
                        System.out.println("Something Error Happened");
                    }
                    break;

                case 4:
                    System.out.println("Enter source and destination for Searching Any Trains");
                    System.out.println("From :");
                    String sourceInput = scanner.next();
                    System.out.println("To :");
                    String destinationInput = scanner.next();

                   try{
                       System.out.println("Searching for trains from '" + sourceInput + "' to '" + destinationInput + "'");
                       List<Train> availableTrains = userBookingService.getTrains(sourceInput,destinationInput);
                       if(availableTrains.isEmpty()){
                           System.out.println("No Trains Available");
                           break;
                       }
                       for(int i = 0; i< availableTrains.size();i++){
                           System.out.printf("Train Id :- %s Train No:- %s%n" ,availableTrains.get(i).getTrainId(),availableTrains.get(i).getTrainNo());
                       }
                   }catch(IOException e){
                       System.out.println("Something Error Happened");
                   }
                    break;

                case 5:

                    System.out.println("Enter the Train Id to check available seats");
                    String trainId = scanner.next();
                    if(userBookingService.fetchSeat(trainId) > 0){
                        System.out.printf("There are %s available seats%n",userBookingService.fetchSeat(trainId).toString());
                    }else{
                        System.out.println("No seats Available");
                    }
                    System.out.println("Do you want to Book : yes : 1 , no:0");
                    int yes = scanner.nextInt();
                    if(yes == 1){
                        System.out.println("Enter source and destination ");
                        System.out.println("From :");
                        String source = scanner.next();
                        System.out.println("To :");
                        String destination = scanner.next();
                       try{
                           userBookingService.bookSeat(trainId,source,destination);
                       }catch(IOException e){
                           System.out.println("Something went wrong");
                       }
                    }
                    break;

                case 6:
                    System.out.println("Your Bookings Are ......");
                    if(userBookingService.fetchBooking()){
                        //////////printing tickets
                        System.out.println("Tell the TicketId U want to cancel");
                        String ticketId = scanner.next();
                        try{
                            if(userBookingService.cancelBooking(ticketId)){
                               System.out.println("Ticket Canceled Successfully");
                            }
                        }catch(IOException e){
                            System.out.println("Something went wrong");
                        }
                    }else{
                        break;
                    }
                    break;


            }
        }


    }
}
