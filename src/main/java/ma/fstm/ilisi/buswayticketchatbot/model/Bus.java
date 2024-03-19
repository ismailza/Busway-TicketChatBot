package ma.fstm.ilisi.buswayticketchatbot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalTime;
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
    public boolean passedBy(Station from, Station to) {
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
            return false;
        if (this.arrival.getStation().getId().equals(to.getId()))
            return true;

        return true;
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
