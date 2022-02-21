package com.bowie.notes.neo4j;


import org.neo4j.driver.*;

import java.util.Map;

import static org.neo4j.driver.Values.parameters;

//使用AutoCloseable实现自动关闭资源
public class Neo4JTest implements AutoCloseable {
    String url = "bolt://192.168.11.99:7687";
    String username = "neo4j";
    String password = "123456";

    final Driver driver = GraphDatabase.driver(url, AuthTokens.basic(username, password));

    public void printCompany(final String name) {

        try (Session session = driver.session()) {
            Map companyName = session.writeTransaction(tx ->
            {
                Result result = tx.run("MATCH (n:company {name:$name}) RETURN n",
                        parameters("name", name));
                return result.single().get(0).asNode().asMap();
            });
            System.out.println(companyName);
        }
    }

    public static void main(String[] args) {
        Neo4JTest neo4JTest = new Neo4JTest();
        neo4JTest.printCompany("百胜智能");
    }

    public void close() throws Exception {
        driver.close();
    }
}
