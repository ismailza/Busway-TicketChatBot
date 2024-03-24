package ma.fstm.ilisi.buswayticketchatbot.service;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import ma.fstm.ilisi.buswayticketchatbot.dto.BookingDTO;
import ma.fstm.ilisi.buswayticketchatbot.dto.PassengerDTO;
import ma.fstm.ilisi.buswayticketchatbot.dto.StopDTO;
import ma.fstm.ilisi.buswayticketchatbot.dto.TripDTO;
import ma.fstm.ilisi.buswayticketchatbot.model.*;
import ma.fstm.ilisi.buswayticketchatbot.repository.BusRepository;
import ma.fstm.ilisi.buswayticketchatbot.repository.PassengerRepository;
import ma.fstm.ilisi.buswayticketchatbot.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TripService {
    @Autowired
    private BusRepository busRepository;
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private PassengerRepository passengerRepository;

    public TripService(BusRepository busRepository, StationRepository stationRepository, PassengerRepository passengerRepository) {
        this.busRepository = busRepository;
        this.stationRepository = stationRepository;
        this.passengerRepository = passengerRepository;
    }

    public List<TripDTO> findAll(Long fromId, Long toId) {
        List<TripDTO> trips = new ArrayList<>();
        Optional<Station> fromOptional = this.stationRepository.findById(fromId);
        if (fromOptional.isEmpty()) throw new RuntimeException("Station not found");
        Station from = fromOptional.get();
        Optional<Station> toOptional = this.stationRepository.findById(toId);
        if (toOptional.isEmpty()) throw new RuntimeException("Station not found");
        Station to = toOptional.get();
        List<Bus> buses = busRepository.findAll();
        for (Bus bus : buses) {
            BookingDTO bookingDTO = bus.passedBy(from, to);
            if (bookingDTO != null && bus.isAvailable(from, to)) {
                TripDTO trip = TripDTO.builder()
                        .busMatriculation(bus.getMatriculation())
                        .departureAt(bus.getDeparture().getDepartureAt())
                        .arrivalAt(bus.getArrival().getArrivalAt())
                        .departureId(fromId)
                        .arrivalId(toId)
                        .bookingDTO(bookingDTO)
                        .build();
                trips.add(trip);
            }
        }
        return trips;
    }

    public String reserve(Long busMatriculation, Long departureId, Long arrivalId, PassengerDTO passengerDTO) {
        Bus bus = busRepository.findById(busMatriculation)
                .orElseThrow(() -> new RuntimeException("Bus not found"));
        Station departure = stationRepository.findById(departureId)
                .orElseThrow(() -> new RuntimeException("Departure station not found"));
        Station arrival = stationRepository.findById(arrivalId)
                .orElseThrow(() -> new RuntimeException("Arrival station not found"));
        Passenger passenger = Passenger.builder()
                .firstname(passengerDTO.getFirstname())
                .lastname(passengerDTO.getLastname())
                .email(passengerDTO.getEmail())
                .booked(new ArrayList<>())
                .build();
        Booked booked = Booked.builder()
                .bookedAt(LocalDateTime.now())
                .bus(bus)
                .build();
        passenger.getBooked().add(booked);
        this.passengerRepository.save(passenger);
        return "Booking confirmed, " + LocalDateTime.now() + " for " + passengerDTO.getFirstname() + " " + passengerDTO.getLastname() + " from " + departure.getName() + " to " + arrival.getName() + " on bus " + bus.getMatriculation();
    }

    public String createQRCode(String booking) throws WriterException, IOException {
        Gson gson = new Gson();
        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("booking", booking);
        String qrCodeData = gson.toJson(dataMap);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, 80, 80);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] qrCodeImageBytes = pngOutputStream.toByteArray();

        return Base64.getEncoder().encodeToString(qrCodeImageBytes);
    }

    public void save(TripDTO trip) {
        Bus bus = busRepository.findById(trip.getBusMatriculation())
                .orElseThrow(() -> new RuntimeException("Bus not found"));
        Station departureStation = stationRepository.findById(trip.getDepartureId())
                .orElseThrow(() -> new RuntimeException("Departure station not found"));
        Station arrivalStation = stationRepository.findById(trip.getArrivalId())
                .orElseThrow(() -> new RuntimeException("Arrival station not found"));

        List<Stops> stops = new ArrayList<>();
        for (StopDTO stop : trip.getStopStations()) {
            Station station = stationRepository.findById(stop.getId())
                    .orElseThrow(() -> new RuntimeException("Station not found"));
            stops.add(Stops.builder()
                    .station(station)
                    .stopedAt(stop.getStopedAt())
                    .build());
        }

        departureStation.getNextStations().add(Next.builder().station(stops.getFirst().getStation()).build());
        stops.getLast().getStation().getNextStations().add(Next.builder().station(arrivalStation).build());
        for (int i = 0; i < stops.size() - 1; i++) {
            stops.get(i).getStation().getNextStations().add(Next.builder().station(stops.get(i + 1).getStation()).build());
        }

        bus.setDeparture(Departure.builder()
                .station(departureStation)
                .departureAt(trip.getDepartureAt())
                .build());

        bus.setArrival(Arrival.builder()
                .station(arrivalStation)
                .arrivalAt(trip.getArrivalAt())
                .build());

        bus.setStops(stops);

        busRepository.save(bus);
    }

}
