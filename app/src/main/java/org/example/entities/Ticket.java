package org.example.entities;

import java.util.Date;

public class Ticket {
    private String ticketId;
    private String userId;
    private String source;
    private String destination;
    private String DateOfTravel;
    private Train train;

    public String getTicketId(){ return this.ticketId;}
    public String getUserId(){return this.userId;}
    public String getSource(){return this.source;}
    public String getDestination(){return this.destination;}
    public String getDateOfTravel(){return this.DateOfTravel;}
    public Train getTrain(){return this.train;}

    public String TicketInfo(){
        return String.format("Ticket Id : %s Issued By User %s  will be valid from %s station To %s station On %s",ticketId,userId,source,destination,DateOfTravel);
    }

    public void setTicketId(){  this.ticketId = ticketId;}
    public void setUserId(){ this.userId = userId;}
    public void setSource(){ this.source = source;}
    public void setDestination(){ this.destination = destination;}
    public void setDateOfTravel(){ this.DateOfTravel = DateOfTravel;}
    public void setTrain(){ this.train = train;}


}
