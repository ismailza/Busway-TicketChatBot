package ma.fstm.ilisi.buswayticketchatbot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Data
@Builder
@AllArgsConstructor
@RelationshipProperties
public class Next {
    @TargetNode
    private Station station;
    private int duration;
}
