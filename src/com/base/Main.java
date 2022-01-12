package com.base;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ModelReader;

public class Main {

    public static void main(String[] args) {
        ParameterizedSparqlString qs = new ParameterizedSparqlString(""
                + "PREFIX dbres: <http://dbpedia.org/resource/>\n"
                + "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "select ?o where { dbres:Drug rdf:type ?o}LIMIT 10");

        QueryExecution exec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", qs.asQuery());
        ResultSet results = exec.execSelect();
        while (results.hasNext()) {

            System.out.println(results.next().get("o").toString());
        }
        ResultSetFormatter.out(results);

    }
}
