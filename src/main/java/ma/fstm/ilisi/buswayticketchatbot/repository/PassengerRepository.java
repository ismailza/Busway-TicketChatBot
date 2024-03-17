package ma.fstm.ilisi.buswayticketchatbot.repository;

import ma.fstm.ilisi.buswayticketchatbot.model.Passenger;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends Neo4jRepository<Passenger, Long> {

}
