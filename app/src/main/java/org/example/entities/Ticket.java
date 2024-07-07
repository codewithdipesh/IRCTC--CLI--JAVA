package org.example.entities;

import java.util.Date;

public class Ticket {
    private String ticketId;
    private String userId;
    private String source;
    private String destination;
    private String DateOfTravel;
    private Train train;
    private String seatNo;

    public Ticket(String ticketId,
     String userId,
     String source,
     String destination,
     String DateOfTravel,
     Train train,
     String seatNo){
        this .source = source;
        this .ticketId = ticketId;
        this .userId = userId;
        this .DateOfTravel = DateOfTravel;
        this .destination = destination;
        this .seatNo = seatNo;
        this .train = train;

    }

    public String getTicketId(){ return this.ticketId;}
    public String getUserId(){return this.userId;}
    public String getSource(){return this.source;}
    public String getDestination(){return this.destination;}
    public String getDateOfTravel(){return this.DateOfTravel;}
    public Train getTrain(){return this.train;}
    public String getseatNo(){return this.seatNo;}



    public String TicketInfo(){
        return String.format("Ticket Id : %s Issued By User %s  will be valid from %s station To %s station On %s",ticketId,userId,source,destination,DateOfTravel);
    }

    public void setTicketId(String ticketId){  this.ticketId = ticketId;}
    public void setUserId(String userId){ this.userId = userId;}
    public void setSource(String source){ this.source = source;}
    public void setDestination(String destination){ this.destination = destination;}
    public void setDateOfTravel(String DateOfTravel){ this.DateOfTravel = DateOfTravel;}
    public void setTrain(Train train){ this.train = train;}
    public void setSeatNo(String seatNo){ this.seatNo = seatNo;}


}
