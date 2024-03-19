package ma.fstm.ilisi.buswayticketchatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripDTO {
    private Long busMatriculation;
    private double tarif;
    private Long departureId;
    private LocalTime departureAt;
    private Long arrivalId;
    private LocalTime arrivalAt;

}
