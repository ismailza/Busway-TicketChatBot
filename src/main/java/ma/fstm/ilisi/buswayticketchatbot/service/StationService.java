package ma.fstm.ilisi.buswayticketchatbot.service;

import ma.fstm.ilisi.buswayticketchatbot.dto.StationDTO;
import ma.fstm.ilisi.buswayticketchatbot.model.Station;
import ma.fstm.ilisi.buswayticketchatbot.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {
    @Autowired
    private StationRepository stationRepository;

    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public List<StationDTO> findAll() {
        return this.stationRepository.findAll().stream().map(this::mapToStationDTO).toList();
    }

    public StationDTO findById(Long id) {
        return mapToStationDTO(this.stationRepository.findById(id).orElse(null));
    }

    public void save(StationDTO stationDTO) {
        mapToStationDTO(this.stationRepository.save(mapToStation(stationDTO)));
    }

    public void delete(Long id) {
        this.stationRepository.deleteById(id);
    }

    public StationDTO mapToStationDTO(Station station) {
        return StationDTO.builder()
                .id(station.getId())
                .name(station.getName())
                .latitude(station.getLatitude())
                .longitude(station.getLongitude())
                .build();
    }

    public Station mapToStation(StationDTO stationDTO) {
        return Station.builder()
                .id(stationDTO.getId())
                .name(stationDTO.getName())
                .latitude(stationDTO.getLatitude())
                .longitude(stationDTO.getLongitude())
                .build();
    }

    public StationDTO findNearest(Double latitude, Double longitude) {
        double minDistance = Double.MAX_VALUE;
        Station nearestStation = null;

        for (Station station : this.stationRepository.findAll()) {
            double distance = station.distance(latitude, longitude);
            if (distance < minDistance) {
                minDistance = distance;
                nearestStation = station;
            }
        }
        return nearestStation != null ? this.mapToStationDTO(nearestStation) : null;
    }
}
