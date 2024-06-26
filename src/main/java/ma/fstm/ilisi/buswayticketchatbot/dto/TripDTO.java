package ma.fstm.ilisi.buswayticketchatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripDTO {
    private Long busMatriculation;
    private int busLine;
    private double tarif;
    private StationDTO departure;
    private LocalTime departureAt;
    private StationDTO arrival;
    private LocalTime arrivalAt;
    private List<StopDTO> stopStations = new ArrayList<>();
}
