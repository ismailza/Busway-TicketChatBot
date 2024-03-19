package ma.fstm.ilisi.buswayticketchatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusDTO {
    private Long matriculation;
    private int busLine;
    private int capacity;
    private DriverDTO driver;

}
