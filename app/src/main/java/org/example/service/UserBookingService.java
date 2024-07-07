package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Ticket;
import org.example.entities.User;
import org.example.utils.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserBookingService {
    private User user;
    private List<User> userList;

    private ObjectMapper objectMapper = new ObjectMapper();

    public static final String USERS_PATH = "app/src/main/java/org/example/localDb/users.json";

    public UserBookingService(User user) throws IOException {
        this.user = user;
        UsersFetching();
    }
    public UserBookingService()throws IOException{
        UsersFetching();
    }
    private List<User> UsersFetching() throws IOException{
        File users = new File(USERS_PATH);
        userList = objectMapper.readValue(users, new TypeReference<List<User>>() {} );
        return userList;
    }
    public Boolean loginUser(){
        Optional<User> foundUser = userList.stream().filter(user1 -> {                    //user input password       //database save hashed Password
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getHashepassword() , user1.getHashepassword() );
        }).findFirst();

        return foundUser.isPresent();

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
            user.printTickets();
            return Boolean.TRUE;
        } else {
           return Boolean.FALSE;
        }

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



}
