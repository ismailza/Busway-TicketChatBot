package ma.fstm.ilisi.buswayticketchatbot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private Long id;
    private String name;
    private double latitude;
    private double longitude;

    @Relationship(type = "NEXT", direction = Relationship.Direction.OUTGOING)
    private List<Next> nextStations;

}
