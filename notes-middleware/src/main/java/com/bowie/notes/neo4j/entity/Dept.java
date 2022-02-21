package com.bowie.notes.neo4j.entity;

import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;


@NodeEntity(label = "dept")
@Data
@Builder
public class Dept {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "deptName")
    private String deptName;

    @Property(name = "deptAge")
    private Integer deptAge;

}
