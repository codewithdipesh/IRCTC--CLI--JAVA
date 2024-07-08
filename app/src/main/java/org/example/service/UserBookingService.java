package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Ticket;
import org.example.entities.Train;
import org.example.entities.User;
import org.example.utils.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserBookingService {
    private User user;
    private List<User> userList;

    private TrainService trainService;

    private ObjectMapper objectMapper = new ObjectMapper();

    public static final String USERS_PATH = "app/src/main/java/org/example/localDb/users.json";

    public UserBookingService(User user) throws IOException {
        this.user = user;
        UsersFetching();
         trainService = new TrainService();
    }
    public UserBookingService()throws IOException{
        UsersFetching();
         trainService = new TrainService();
    }
    private List<User> UsersFetching() throws IOException {
        File users = new File(USERS_PATH);
        if (!users.exists() || users.length() == 0) {
            System.out.println("Users file is empty or doesn't exist. Initializing with an empty list.");
            return new ArrayList<>();
        }
        userList = objectMapper.readValue(users, new TypeReference<List<User>>() {});
        return userList;
    }
    public Boolean loginUser(String name,String password){

        Optional<User> foundUser = userList.stream().filter(user1 -> {                    //user input password       //database save hashed Password
            return user1.getName().equals(name) && UserServiceUtil.checkPassword( password, user1.getHashepassword() );
        }).findFirst();

         if(foundUser.isPresent()){
             user = foundUser.get();
             return Boolean.TRUE;
         }
         else{
             return Boolean.FALSE;
         }

    }

    public Boolean SignUp(User user){
        try{
            userList.add(user);
            saveUserListToFile();
            return Boolean.TRUE;

        }catch(IOException ex){
            return Boolean.FALSE;
        }
    }

    private void saveUserListToFile() throws  IOException{
        File userFiles = new File(USERS_PATH);
        objectMapper.writeValue(userFiles,userList);
    }

    private void updateUserInList() {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUUID().equals(user.getUUID())) {
                userList.set(i, user);
                break;
            }
        }
    }

    public boolean fetchBooking(){
        if (user != null) {
            if(user.printTickets()){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;

    }
    public boolean cancelBooking(String ticketId) throws IOException{
        if(user == null){
            return Boolean.FALSE;
        }
        Optional<Ticket> foundTicket = user.getTicketBooked().stream()
                .filter(t-> t.getTicketId().equals(ticketId))
                .findFirst();
        if(foundTicket.isEmpty()){
            return Boolean.FALSE;
        }
        user.getTicketBooked().remove(foundTicket);
        updateUserInList();
        saveUserListToFile();

        return Boolean.TRUE;
    }
    public List<Train> getTrains(String source, String Destination) throws IOException {

        return trainService.searchTrains(source,Destination);

    }
    public boolean bookSeat(String trainId,String source, String destination) throws IOException {
        if(fetchSeat(trainId) > 0){
         Ticket bookedTicket = trainService.bookTicket(source,destination,trainId,user.getUUID());
         System.out.printf(bookedTicket.TicketInfo());
         List<Ticket> previousTickets = user.getTicketBooked();
         previousTickets.add(bookedTicket);
         updateUserInList();
         return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Integer fetchSeat(String trainId){
        return trainService.getAvailableSeats(trainId);
    }



}
