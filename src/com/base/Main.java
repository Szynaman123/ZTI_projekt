package com.base;
import org.apache.http.HttpException;
import org.apache.http.protocol.HTTP;
import org.apache.jena.base.Sys;
import org.apache.jena.query.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.apache.jena.query.QueryFactory.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ModelReader;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static ResultSet makeQuery(String queryString){
        ParameterizedSparqlString qs = new ParameterizedSparqlString(queryString);
        QueryExecution exec = QueryExecutionFactory.sparqlService("https://linkeddata.uriburner.com/sparql", qs.asQuery());
        ResultSet results = exec.execSelect();
        return results;
    }
    public static String txtFileToString(String filePath){
        String text = "";
        try {
            text = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
    public static void main(String[] args) throws FileNotFoundException {
        try{
        ResultSet results = makeQuery("PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
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
                "PREFIX sch: <http://schema.org/>\n" +
                "PREFIX yago: <http://yago-knowledge.org/resource/> " +
                "\n" +
                "SELECT DISTINCT\n" +
                "        ?personLabel\n" +
                "        (?person as ?wikidataID)\n" +
                "        ?dbpediaID\n" +
                "        ?yagoID\n" +
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
                "      }SERVICE <https://yago-knowledge.org/sparql/query>\n" +
                "{ \n" +
                "SELECT ?yagoID\n" +
                "WHERE { ?yagoID sch:givenName yago:Adolf;\n" +
                "sch:birthPlace yago:Braunau_am_Inn;\n" +
                "sch:spouse yago:Eva_Braun.\n" +
                "} }}"
);  System.out.println(ResultSetFormatter.asText(results));
        }
    catch(Exception e){
        System.out.println(e.toString());
    }

        ResultSet results1 = makeQuery("PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
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
                "PREFIX sch: <http://schema.org/>\n" +
                "PREFIX yago: <http://yago-knowledge.org/resource/> \n" +
                "SELECT DISTINCT\n" +
                "        ?wdLabel\n" +
                "WHERE{\n"+
                "SERVICE <https://query.wikidata.org/sparql>\n" +
                "{\n" +
                "SELECT DISTINCT ?wdLabel  {\n" +
                "  VALUES (?person) {(wd:Q352)}\n" +
                "  ?person ?p ?statement .\n" +
                "  ?wd wikibase:claim ?p.\n" +
                "  SERVICE wikibase:label { bd:serviceParam wikibase:language \"en\" }\n" +
                "} ORDER BY ?wd ?statement "+
                "}}"
                 );
        int counter=0;
        while (results1.hasNext()) {
            results1.next();
            counter++;

     }
    System.out.println(counter);

        ResultSet results2 = makeQuery("PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
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
                "PREFIX sch: <http://schema.org/>\n" +
                "PREFIX yago: <http://yago-knowledge.org/resource/> \n" +
                "SELECT DISTINCT\n" +
                "        ?property\n" +
                "WHERE{\n"+
                "SERVICE <https://dbpedia.org/sparql>\n" +
                "{\n" +
                "SELECT DISTINCT ?property  {\n" +
                "{ <http://dbpedia.org/resource/Adolf_Hitler> ?property ?value. }\n" +
                "UNION\n" +
                "{ ?value ?property <http://dbpedia.org/resource/Adolf_Hitler>. }\n" +
                "}" +
                "}}"
        );
        int counter1=0;
        while (results2.hasNext()) {
            results2.next();
            counter1++;

        }
        System.out.println(counter1);

        ResultSet results3 = makeQuery("PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
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
                "PREFIX sch: <http://schema.org/>\n" +
                "PREFIX yago: <http://yago-knowledge.org/resource/> \n" +
                "SELECT DISTINCT\n" +
                "        ?property\n" +
                "WHERE{\n"+
                "SERVICE <https://yago-knowledge.org/sparql/query>\n" +
                "{\n" +
                "SELECT DISTINCT ?property  {\n" +
                "{ <http://yago-knowledge.org/resource/Adolf_Hitler> ?property ?value. }\n" +
                "UNION\n" +
                "{ ?value ?property <http://yago-knowledge.org/resource/Adolf_Hitler>. }\n" +
                "}" +
                "}}"
        );
        int counter2=0;
        while (results3.hasNext()) {
            results3.next();
            counter2++;

        }
        System.out.println(counter2);
        ResultSet results4 = makeQuery("PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
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
                "prefix dbpedia: <http://dbpedia.org/resource/>\n" +
                "prefix dbpedia-owl: <http://dbpedia.org/ontology/>\n" +
                "PREFIX dct: <http://purl.org/dc/terms/>\n" +
                "PREFIX sch: <http://schema.org/>\n" +
                "PREFIX yago: <http://yago-knowledge.org/resource/> \n" +
                "SELECT\n" +
                "        ?wikidataDescription\n" +
                "        ?dbpediaDescription\n" +
                "        ?yagoDescription\n" +
                "WHERE{\n"+
                "SERVICE <https://query.wikidata.org/sparql>\n" +
                "{\n" +
                "SELECT ?wikidataDescription WHERE {\n" +
                " wd:Q352 sch:description ?wikidataDescription.\n" +
                "  FILTER ( lang(?wikidataDescription) = \"en\" )" +
                "}" +
                "}"+
                "SERVICE <https://dbpedia.org/sparql>\n" +
                "{\n" +
                "SELECT ?dbpediaDescription  WHERE { \n" +
                "  dbpedia:Adolf_Hitler dbpedia-owl:abstract ?dbpediaDescription .\n" +
                "\n" +
                "  FILTER(langMatches(lang(?dbpediaDescription),\"en\"))\n" +
                "}" +

                "}" +

                "SERVICE <https://yago-knowledge.org/sparql/query>\n" +
                "{\n" +
                "SELECT ?yagoDescription WHERE {\n" +
                " <http://yago-knowledge.org/resource/Adolf_Hitler> rdfs:comment ?yagoDescription.\n" +
                "  FILTER ( lang(?yagoDescription) = \"en\" )" +
                "}" +
                "}"+
                "}"
        );
//        System.out.println(ResultSetFormatter.asText(results4));
        Integer a=0;
        Integer b=0;
        Integer c=0;
        while(results4.hasNext()){
            QuerySolution next=results4.next();
            a=next.get("wikidataDescription").toString().length();
            b=next.get("dbpediaDescription").toString().length();
            c=next.get("yagoDescription").toString().length();
        }
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
   }}
//    System.out.println(indexes);


