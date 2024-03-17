package ma.fstm.ilisi.buswayticketchatbot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@RelationshipProperties
public class Arrival {
    private LocalTime arrivedAt;
    @TargetNode
    private Station station;

}
