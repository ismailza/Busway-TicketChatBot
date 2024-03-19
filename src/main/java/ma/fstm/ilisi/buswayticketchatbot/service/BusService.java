package ma.fstm.ilisi.buswayticketchatbot.service;

import ma.fstm.ilisi.buswayticketchatbot.dto.ArrivalDTO;
import ma.fstm.ilisi.buswayticketchatbot.dto.BusDTO;
import ma.fstm.ilisi.buswayticketchatbot.dto.DepartureDTO;
import ma.fstm.ilisi.buswayticketchatbot.model.Arrival;
import ma.fstm.ilisi.buswayticketchatbot.model.Bus;
import ma.fstm.ilisi.buswayticketchatbot.model.Departure;
import ma.fstm.ilisi.buswayticketchatbot.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusService {
    @Autowired
    private BusRepository busRepository;
    @Autowired
    private DriverService driverService;
    @Autowired
    private StationService stationService;

    public BusService(BusRepository busRepository, DriverService driverService, StationService stationService) {
        this.busRepository = busRepository;
        this.driverService = driverService;
        this.stationService = stationService;
    }

    public List<BusDTO> findAll() {
        return this.busRepository.findAll().stream().map(this::mapToBusDTO).toList();
    }

    public BusDTO findById(Long id) {
        return this.mapToBusDTO(this.busRepository.findById(id).orElse(null));
    }

    public void save(BusDTO busDTO) {
        busDTO.setDriver(this.driverService.findById(busDTO.getDriver().getId()));
        this.busRepository.save(this.mapToBus(busDTO));
    }

    public Bus mapToBus(BusDTO busDTO) {
        return Bus.builder()
                .matriculation(busDTO.getMatriculation())
                .busLine(busDTO.getBusLine())
                .capacity(busDTO.getCapacity())
                .driver(this.driverService.mapToDriver(busDTO.getDriver()))
                .build();
    }

    public BusDTO mapToBusDTO(Bus bus) {
        return BusDTO.builder()
                .matriculation(bus.getMatriculation())
                .busLine(bus.getBusLine())
                .capacity(bus.getCapacity())
                .driver(this.driverService.mapToDriverDTO(bus.getDriver()))
                .build();
    }
}
