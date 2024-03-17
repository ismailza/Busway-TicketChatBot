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
public class Bus {
    @Id
    private Long immatriculation;
    private int numero;
    private int capacity;

    @Relationship(type = "STARTS", direction = Relationship.Direction.OUTGOING)
    private Starts starts;

    @Relationship(type = "ARRIVAL", direction = Relationship.Direction.OUTGOING)
    private Arrival arrival;

    @Relationship(type = "STOPS", direction = Relationship.Direction.OUTGOING)
    private List<Stops> stops;


}
