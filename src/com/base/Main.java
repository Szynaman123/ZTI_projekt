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
    public static String txtFileToString(String filePath){
        String text = "";
        try {
            text = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public static ResultSet makeQuery(String queryString){
        ParameterizedSparqlString qs = new ParameterizedSparqlString(queryString);
        QueryExecution exec = QueryExecutionFactory.sparqlService("https://linkeddata.uriburner.com/sparql", qs.asQuery());
        ResultSet results = exec.execSelect();
        return results;
    }

    public static void main(String[] args) throws FileNotFoundException {
        try{
            String query1 = txtFileToString("C:\\Users\\banas\\OneDrive\\Pulpit\\ZTI_projekt\\ZTI_projekt\\src\\com\\base\\query1.txt");
            ResultSet results = makeQuery(query1);

            System.out.println(ResultSetFormatter.asText(results));
        }
        catch(Exception e){
            System.out.println(e.toString());
        }

   }}



