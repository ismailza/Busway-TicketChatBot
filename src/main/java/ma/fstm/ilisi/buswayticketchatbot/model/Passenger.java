package ma.fstm.ilisi.buswayticketchatbot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
}
