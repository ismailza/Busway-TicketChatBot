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
public class DepartureDTO {
    private Long id;
    private LocalTime departureAt;
    private int embarking;
    private StationDTO station;
}
