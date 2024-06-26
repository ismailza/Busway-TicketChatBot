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
public class TripForm {
    private Long busMatriculation;
    private double tarif;
    private Long departureId;
    private LocalTime departureAt;
    private Long arrivalId;
    private LocalTime arrivalAt;
    private List<StopDTO> stopStations = new ArrayList<>();
    private BookingDTO bookingDTO;

}
