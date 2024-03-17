package ma.fstm.ilisi.buswayticketchatbot.service;

import ma.fstm.ilisi.buswayticketchatbot.dto.DriverDTO;
import ma.fstm.ilisi.buswayticketchatbot.model.Driver;
import ma.fstm.ilisi.buswayticketchatbot.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository){
        this.driverRepository = driverRepository;
    }

    public List<DriverDTO> findAll() {
        return this.driverRepository.findAll().stream().map(this::mapToDriverDTO).toList();
    }

    public DriverDTO findById(Long id) {
        return this.mapToDriverDTO(this.driverRepository.findById(id).orElse(null));
    }

    public Driver mapToDriver(DriverDTO driverDTO){
        return Driver.builder()
                .id(driverDTO.getId())
                .firstname(driverDTO.getFirstname())
                .lastname(driverDTO.getLastname())
                .build();
    }

    public DriverDTO mapToDriverDTO(Driver driver){
        return DriverDTO.builder()
                .id(driver.getId())
                .firstname(driver.getFirstname())
                .lastname(driver.getLastname())
                .build();
    }
}
