package ma.fstm.ilisi.buswayticketchatbot.repository;

import ma.fstm.ilisi.buswayticketchatbot.model.Bus;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepository extends Neo4jRepository<Bus, Long> {

//    @Query("MATCH (bus:Bus {immatriculation: $immatriculation})-[:STOPS]->(stop:Stops)-[:TO]->(station:Station) " +
//            "RETURN station.name AS stationName, stop.embarking AS embarking, stop.disembarking AS disembarking")
//    List<StopSummary> findStopSummaryForBus(Long immatriculation);

}
