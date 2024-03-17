package ma.fstm.ilisi.buswayticketchatbot.repository;

import ma.fstm.ilisi.buswayticketchatbot.model.Station;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends Neo4jRepository<Station, Long> {

}
