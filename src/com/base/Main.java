package com.base;
import org.apache.http.HttpException;
import org.apache.http.protocol.HTTP;
import org.apache.jena.base.Sys;
import org.apache.jena.query.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.apache.jena.query.QueryFactory.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ModelReader;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        try{
        ParameterizedSparqlString qs = new ParameterizedSparqlString("PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX wd: <http://www.wikidata.org/entity/>\n" +
                "PREFIX wdt: <http://www.wikidata.org/prop/direct/>\n" +
                "PREFIX wikibase: <http://wikiba.se/ontology#>\n" +
                "PREFIX p: <http://www.wikidata.org/prop/>\n" +
                "PREFIX ps: <http://www.wikidata.org/prop/statement/>\n" +
                "PREFIX pq: <http://www.wikidata.org/prop/qualifier/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX bd: <http://www.bigdata.com/rdf#>\n" +
                "PREFIX dct: <http://purl.org/dc/terms/>\n" +
                "\n" +
                "SELECT DISTINCT\n" +
                "        (?person as ?wikidataID)\n" +
                "        ?personLabel\n" +
                "        ?dbpediaID\n" +
                "WHERE{\n" +
                "SERVICE <https://query.wikidata.org/sparql>\n" +
                "          {\n" +
                "SELECT DISTINCT ?person ?personLabel\n" +
                "WHERE{\n" +
                "\n" +
                "  ?person wdt:P735 wd:Q18145837;\n" +
                "   wdt:P734 wd:Q1621139; \n" +
                "   wdt:P19 wd:Q131128.\n" +
                "  \n" +
                "\n" +
                "  SERVICE wikibase:label \n" +
                "          {\n" +
                "            bd:serviceParam wikibase:language \"en\"\n" +
                "          }\n" +
                "  \n" +
                "}}SERVICE <https://dbpedia.org/sparql>\n" +
                "          {\n" +
                "              SELECT ?dbpediaID ?label ?description\n" +
                "                   WHERE { ?dbpediaID owl:sameAs ?person  ;\n" +
                "                                      rdfs:label ?label ;\n" +
                "                                      rdfs:comment ?description .\n" +
                "                           FILTER (LANG(?label) = \"en\")\n" +
                "                           FILTER (LANG(?description) = \"en\")\n" +
                "                         }\n" +
                "      }}"
);
        Scanner odczyt = new Scanner(new File("C:\\Users\\szyna\\IdeaProjects\\RDF_and_SPARQL_test\\src\\com\\base\\endpoints"));
        ArrayList<String> Endpoints = new ArrayList<String>();
        while (odczyt.hasNextLine()) {
            String text = odczyt.nextLine();
            Endpoints.add(text);
        }
        ;
        ArrayList<Integer> indexes = new ArrayList<Integer>();
//        System.out.println(Endpoints.get(1).toString());
//        for(int i=0;i<Endpoints.size();i++){
//            try{


        QueryExecution exec = QueryExecutionFactory.sparqlService("https://linkeddata.uriburner.com/sparql", qs.asQuery());

        ResultSet results = exec.execSelect();
//        while (results.hasNext()) {

//            System.out.println(results.next().get("dbpediaID"));
//        }
            System.out.println(ResultSetFormatter.asText(results));
//            }catch (Exception e){
//                indexes.add(i+1);
            }
    catch(Exception e){
        System.out.println(e.toString());
    }


   }}
//    System.out.println(indexes);


