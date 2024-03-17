package ma.fstm.ilisi.buswayticketchatbot.service;

import ma.fstm.ilisi.buswayticketchatbot.dto.BusDTO;
import ma.fstm.ilisi.buswayticketchatbot.model.Bus;
import ma.fstm.ilisi.buswayticketchatbot.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusService {
    @Autowired
    private BusRepository busRepository;

    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    public List<BusDTO> findAll() {
        return this.busRepository.findAll().stream().map(this::mapToBusDTO).toList();
    }

    public BusDTO findById(Long id) {
        return this.mapToBusDTO(this.busRepository.findById(id).orElse(null));
    }

    public void save(BusDTO busDTO) {
        this.busRepository.save(this.mapToBus(busDTO));
    }

    public Bus mapToBus(BusDTO busDTO) {
        return Bus.builder()
                .immatriculation(busDTO.getImmatriculation())
                .numero(busDTO.getNumero())
                .capacity(busDTO.getCapacity())
                .build();
    }

    public BusDTO mapToBusDTO(Bus bus) {
        return BusDTO.builder()
                .immatriculation(bus.getImmatriculation())
                .numero(bus.getNumero())
                .capacity(bus.getCapacity())
                .build();
    }
}
