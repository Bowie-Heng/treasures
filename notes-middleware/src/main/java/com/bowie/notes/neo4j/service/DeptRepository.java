package com.bowie.notes.neo4j.service;

import com.bowie.notes.neo4j.entity.Dept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeptRepository extends Neo4jRepository<Dept,Long> {
    /**
     * MovieRepository中的findByDeptName方法会自动按照deptName帮我们查询Dept，就相当于MATCH (n:dept) WHERE n.deptName = {deptName} RETURN n
     *
     * 可以根据自己的需要组合，比如findBydDeptAgeGreaterThan,就相当于 MATCH (n:dept) WHERE n.deptAge > {age} RETURN n
     * @param deptName
     * @return
     */
    Dept findByDeptName(@Param("deptName") String deptName);


    /**
     * 对于一些复杂的查询，可以使用本方式进行
     */
    @Query(value = "MATCH(d:dept deptName:{deptName})-[:manage]->(d1:dept) RETURN d", countQuery = "MATCH(d:dept deptName:{deptName})-[:manage]->(d1:dept) RETURN count(d)")
    Page<Dept> getDeptListIsManagerFromName(String deptName, Pageable pageable);

}
