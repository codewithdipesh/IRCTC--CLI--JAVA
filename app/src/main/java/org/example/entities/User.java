package org.example.entities;

import java.util.List;

public class User {


    private String name;
    private String hashepassword;
    private List<Ticket> ticketBooked;
    private String UUID;
    public User(String name,String hashepassword,List<Ticket> ticketBooked,String UUID){
        this.name = name;
        this.hashepassword = hashepassword;
        this.ticketBooked = ticketBooked;
        this.UUID = UUID;
    }
    public User() {}

    public String getName(){ return this.name; }
    public String getHashepassword(){ return this.hashepassword; }
    public List<Ticket> getTicketBooked(){ return this.ticketBooked; }

    public void printTickets(){
        for(int i = 0;i<= ticketBooked.size();i++){
            System.out.println(ticketBooked.get(i).TicketInfo());
        }
    }
    public String getUUID(){ return this.UUID; }



    public void setName(String name){  this.name = name; }
    public void setHashepassword(String hashepassword){  this.hashepassword = hashepassword; }
    public void setTicketBooked(List<Ticket> ticketBooked){  this.ticketBooked = ticketBooked; }
    public void setUUID(String UUID){  this.UUID = UUID; }
}
