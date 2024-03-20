package ma.fstm.ilisi.buswayticketchatbot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.fstm.ilisi.buswayticketchatbot.dto.BookingDTO;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Node
public class Bus {
    @Id
    private Long matriculation;
    private int busLine;
    private int capacity;
    @Relationship(type = "DRIVELLED", direction = Relationship.Direction.OUTGOING)
    private Driver driver;

    @Relationship(type = "DEPARTURE", direction = Relationship.Direction.OUTGOING)
    private Departure departure;

    @Relationship(type = "ARRIVAL", direction = Relationship.Direction.OUTGOING)
    private Arrival arrival;

    @Relationship(type = "STOPS", direction = Relationship.Direction.OUTGOING)
    private List<Stops> stops;


    /**
     * Check if the bus passed by the stations
     * @param from The departure station
     * @param to The arrival station
     * @return true if the bus passed by the stations
     */
    public BookingDTO passedBy(Station from, Station to) {
        LocalTime departureTime = null;
        if (this.departure.getStation().getId().equals(from.getId())) {
            departureTime = this.departure.getDepartureAt();
        } else {
            for (Stops stop : stops) {
                if (stop.getStation().getId().equals(from.getId())) {
                    departureTime = stop.getStopedAt();
                    break;
                }
            }
        }
        if (departureTime == null || LocalTime.now().isAfter(departureTime))
            return null;
        if (this.arrival.getStation().getId().equals(to.getId())) {
            return new BookingDTO(from.getName(), departureTime.format(DateTimeFormatter.ofPattern("HH:mm")), to.getName(), this.arrival.getArrivalAt().format(DateTimeFormatter.ofPattern("HH:mm")));
        }
        for (Stops stop : stops) {
            if (stop.getStation().getId().equals(to.getId()) && stop.getStopedAt().isAfter(departureTime))
                return new BookingDTO(from.getName(), departureTime.format(DateTimeFormatter.ofPattern("HH:mm")), to.getName(), stop.getStopedAt().format(DateTimeFormatter.ofPattern("HH:mm")));
        }
        return null;
    }

    /**
     * Check if the bus is available for booking
     * @param from the departure station
     * @param to the arrival station
     * @return true if the bus is available for booking
     */
    public boolean isAvailable(Station from, Station to) {
        // TODO implement here
        return true;
    }

}
