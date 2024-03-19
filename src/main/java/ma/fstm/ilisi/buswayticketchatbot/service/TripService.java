package ma.fstm.ilisi.buswayticketchatbot.service;

import ma.fstm.ilisi.buswayticketchatbot.dto.TripDTO;
import ma.fstm.ilisi.buswayticketchatbot.model.Arrival;
import ma.fstm.ilisi.buswayticketchatbot.model.Bus;
import ma.fstm.ilisi.buswayticketchatbot.model.Departure;
import ma.fstm.ilisi.buswayticketchatbot.model.Station;
import ma.fstm.ilisi.buswayticketchatbot.repository.BusRepository;
import ma.fstm.ilisi.buswayticketchatbot.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TripService {
    @Autowired
    private BusRepository busRepository;
    @Autowired
    private StationRepository stationRepository;

    public TripService(BusRepository busRepository, StationRepository stationRepository) {
        this.busRepository = busRepository;
        this.stationRepository = stationRepository;
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
            if (bus.isAvailable(from, to)) {
                TripDTO trip = TripDTO.builder()
                        .busMatriculation(bus.getMatriculation())
                        .departureAt(bus.getDeparture().getDepartureAt())
                        .arrivalAt(bus.getArrival().getArrivalAt())
                        .departureId(fromId)
                        .arrivalId(toId)
                        .build();
                trips.add(trip);
            }
        }
        return trips;
    }

    public void save(TripDTO trip) {
        Bus bus = busRepository.findById(trip.getBusMatriculation())
                .orElseThrow(() -> new RuntimeException("Bus not found"));
        Station departureStation = stationRepository.findById(trip.getDepartureId())
                .orElseThrow(() -> new RuntimeException("Departure station not found"));
        Station arrivalStation = stationRepository.findById(trip.getArrivalId())
                .orElseThrow(() -> new RuntimeException("Arrival station not found"));

        bus.setDeparture(Departure.builder()
                .station(departureStation)
                .departureAt(trip.getDepartureAt())
                .build());

        bus.setArrival(Arrival.builder()
                .station(arrivalStation)
                .arrivalAt(trip.getArrivalAt())
                .build());
        busRepository.save(bus);
    }

}
