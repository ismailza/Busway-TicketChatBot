package ma.fstm.ilisi.buswayticketchatbot.repository;

import ma.fstm.ilisi.buswayticketchatbot.model.Bus;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepository extends Neo4jRepository<Bus, Long> {
}
