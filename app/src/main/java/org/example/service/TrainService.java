package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Ticket;
import org.example.entities.Train;
import org.example.entities.User;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class TrainService {

    private List<Train> trainList;

    ObjectMapper objectMapper = new ObjectMapper();
    public static final String TRAINS_PATH = "app/src/main/org/example/localDb/trains.json";
    public TrainService() throws IOException {
        File trains = new File(TRAINS_PATH);
        trainList = objectMapper.readValue(trains, new TypeReference<List<Train>>() {} );
    }


    public List<Train> searchTrains(String source,String Destination){
        return trainList.stream().filter(t-> validTrain(t,source,Destination)).collect(Collectors.toList());
    }

    public Ticket bookTicket(String source, String destination, String trainId, String userId) throws IOException {
        // Find the train
        Optional<Train> trainOptional = trainList.stream()
                .filter(t -> t.getTrainId().equals(trainId))
                .findFirst();

        if (!trainOptional.isPresent()) {
            throw new IllegalArgumentException("Train not found");
        }

        Train train = trainOptional.get();
        List<List<Integer>> trainSeats = train.getSeats();
        int seatNumber = 0;
        boolean seatFound = false;

        // Find and book an available seat
        outerLoop:
        for (int i = 0; i < trainSeats.size(); i++) {
            List<Integer> row = trainSeats.get(i);
            for (int j = 0; j < row.size(); j++) {
                seatNumber++;
                if (row.get(j) == 0) {
                    row.set(j, 1); // Set seat as booked
                    seatFound = true;
                    break outerLoop;
                }
            }
        }
        if (!seatFound) {
            throw new IllegalStateException("No available seats");
        }

        // Update the train's seats
        train.setSeats(trainSeats);

        // Update the train in the trainList
        for (int i = 0; i < trainList.size(); i++) {
            if (trainList.get(i).getTrainId().equals(trainId)) {
                trainList.set(i, train);
                break;
            }
        }

        // Save the updated trainList to file
        File trainFile = new File(TRAINS_PATH);
        objectMapper.writeValue(trainFile, trainList);

        // Create and return the ticket
        Ticket ticket = new Ticket(
                UUID.randomUUID().toString(),
                userId,
                source,
                destination,
                LocalDate.now().toString(),
                train,
                String.valueOf(seatNumber)

        );

        return ticket;
    }

    public Integer getAvailableSeats(String trainId){
        Integer count = 0;
        Optional<Train> train = trainList.stream().filter(t-> t.getTrainId().equals(trainId)).findFirst();
        List<List<Integer>> trainSeats = train.get().getSeats();
        for (int i = 0;i<trainSeats.size();i++){
            List<Integer> Row = trainSeats.get(i);
            for(int j =0;j<Row.size();j++){
                if(trainSeats.get(i).get(j) == 0) {
                    count++;
                }
            }
        }
        return count;

    }
    public boolean validTrain(Train train,String source,String Destination){
        List<String> stationInOrder = train.getStations();
        int sourceIndex = stationInOrder.indexOf(source);
        int destinationIndex = stationInOrder.indexOf(Destination);
        return sourceIndex != -1 && destinationIndex!= -1 && destinationIndex > sourceIndex;
    }
}
