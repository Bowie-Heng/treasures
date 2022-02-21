package com.bowie.notes.neo4j.service;

import com.bowie.notes.neo4j.entity.RelationShip;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationShipRepository extends Neo4jRepository<RelationShip,Long> {

}
