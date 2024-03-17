package ma.fstm.ilisi.buswayticketchatbot.repository;

import ma.fstm.ilisi.buswayticketchatbot.model.Driver;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface DriverRepository extends Neo4jRepository<Driver, Long> {

}
