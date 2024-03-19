package ma.fstm.ilisi.buswayticketchatbot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RelationshipProperties
public class Stops {
    @Id
    @GeneratedValue
    private Long id;
    private LocalTime stopedAt;
    private int embarking;
    private int disembarking;
    @TargetNode
    private Station station;

}
