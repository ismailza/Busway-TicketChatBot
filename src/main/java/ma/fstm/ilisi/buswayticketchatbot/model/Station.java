package ma.fstm.ilisi.buswayticketchatbot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Node
public class Station {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private double latitude;
    private double longitude;

    @Relationship(type = "NEXT", direction = Relationship.Direction.OUTGOING)
    private List<Next> nextStations;

    /**
     * Calculate the distance between this station and a given latitude and longitude
     * @param latitude the latitude
     * @param longitude the longitude
     * @return the distance in kilometers
     */
    public double distance(double latitude, double longitude) {
        final double EARTH_RADIUS = 6371; // in kilometers
        double dLat = Math.toRadians(this.latitude - latitude);
        double dLon = Math.toRadians(this.longitude - longitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(this.latitude)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

}
