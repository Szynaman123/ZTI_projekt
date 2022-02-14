package com.base;
import org.apache.http.HttpException;
import org.apache.http.protocol.HTTP;
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
        ParameterizedSparqlString qs = new ParameterizedSparqlString(""
                + "PREFIX dbr: <http://dbpedia.org/resource/>\n"
                + "PREFIX dbo:<http://dbpedia.org/ontology/>\n"
                + "SELECT DISTINCT *"
                + "WHERE {"
                + "?subject a dbo:Person."
                + "?subject ?predicate ?object." +
                "FILTER (?subject IN (dbr:John_Lennon, dbr:Yoko_Ono, dbr:Jimmy_Carter,\n" +
                "           dbr:Bill_Clinton) && lang(?object) = \"en\")" +
                "} LIMIT 20");
        Scanner odczyt = new Scanner(new File("C:\\Users\\szyna\\IdeaProjects\\RDF_and_SPARQL_test\\src\\com\\base\\endpoints"));
        ArrayList<String>Endpoints=new ArrayList<String>();
        while (odczyt.hasNextLine()) {
            String text = odczyt.nextLine();
            Endpoints.add(text);
        };
        ArrayList<Integer>indexes=new ArrayList<Integer>();
        System.out.println(Endpoints.get(1).toString());
        for(int i=0;i<Endpoints.size();i++){
            try{
                QueryExecution exec = QueryExecutionFactory.sparqlService(Endpoints.get(i).toString(), qs.asQuery());

                ResultSet results = exec.execSelect();
                while (results.hasNext()) {

                    System.out.println(results.next().get("subject").toString());
                }
                ResultSetFormatter.out(results);
            }catch (Exception e){
                indexes.add(i+1);
            }


   }
    System.out.println(indexes);
    }}

