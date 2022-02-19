package com.base;

import org.apache.jena.query.*;
import java.util.Formatter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Struct;
import java.util.Hashtable;
import java.util.Map;

public class CustomQuery {
    private String Label;
    private String wikidataID;
    private String dbpediaID;
    private String yagoID;
    private Hashtable<String,Integer>numberOfProperties;
    private Hashtable<String,Integer>lengthOfDescription;
    private Hashtable<String,Double>percentageScore;

    public Hashtable<String, Integer> getLengthOfDescription() {
        return lengthOfDescription;
    }
    public Hashtable<String, Integer> getNumberOfProperties() {
        return numberOfProperties;
    }
    public Hashtable<String, Double> getPercentageScore() {
        return percentageScore;
    }

    public CustomQuery(String filePath) {
        ResultSet results=makeQuery(txtFileToString(filePath));
        while(results.hasNext()) {
            QuerySolution next = results.next();
            setWikidataID(next.get("wikidataID").toString());
            setDbpediaID(next.get("dbpediaID").toString());
            setYagoID(next.get("yagoID").toString());
            setLabel(next.get("personLabel").toString());
        }
        numberOfProperties=new Hashtable<String,Integer>();
        numberOfProperties=getNumberOfProp();
        lengthOfDescription=new Hashtable<String,Integer>();
        lengthOfDescription=getDescriptionLength();
        percentageScore=new Hashtable<String,Double>();
        percentageScore=getPercScore();

    }
    public String txtFileToString(String filePath){
        String text = "";
        try {
            text = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public String getWikidataID() {
        return wikidataID;
    }

    public void setWikidataID(String wikidataID) {
        this.wikidataID = wikidataID;
    }

    public String getDbpediaID() {
        return dbpediaID;
    }

    public void setDbpediaID(String dbpediaID) {
        this.dbpediaID = dbpediaID;
    }

    public String getYagoID() {
        return yagoID;
    }

    public void setYagoID(String yagoID) {
        this.yagoID = yagoID;
    }


    public ResultSet makeQuery(String queryString){
        ParameterizedSparqlString qs = new ParameterizedSparqlString(queryString);
        QueryExecution exec = QueryExecutionFactory.sparqlService("http://uriburner.com/sparql/", qs.asQuery());
        ResultSet results = exec.execSelect();
        return results;
    }
    public int getNumberOfWikiDataProp(){
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
                "  VALUES (?item) {("+"<"+getWikidataID()+">"+")}\n" +
                "  ?item ?p ?statement .\n" +
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
        return counter;
    }
    public int getNumberOfDBpediaProp(){
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
                "{ <"+getDbpediaID()+"> ?property ?value. }\n" +
                "UNION\n" +
                "{ ?value ?property <"+getDbpediaID()+">. }\n" +
                "}" +
                "}}"
        );
        int counter1=0;
        while (results2.hasNext()) {
            results2.next();
            counter1++;

        }
        return counter1;
    }
    public int getNumberOfYagoProp(){
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
                "{ <"+getYagoID()+"> ?property ?value. }\n" +
                "UNION\n" +
                "{ ?value ?property <"+getYagoID()+">. }\n" +
                "}" +
                "}}"
        );
        int counter2=0;
        while (results3.hasNext()) {
            results3.next();
            counter2++;

        }
        return counter2;
    }
    public Hashtable<String,Integer> getDescriptionLength(){
        ResultSet results=makeQuery("PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
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
                "<"+getWikidataID()+">"+" sch:description ?wikidataDescription.\n" +
                "FILTER ( lang(?wikidataDescription) = \"en\" )" +
                "}" +
                "}"+
                "SERVICE <https://dbpedia.org/sparql>\n" +
                "{\n" +
                "SELECT ?dbpediaDescription  WHERE { \n" +
                "<"+getDbpediaID()+">"+" rdfs:comment ?dbpediaDescription .\n" +
                "\n" +
                "  FILTER(langMatches(lang(?dbpediaDescription),\"en\"))\n" +
                "}" +

                "}" +

                "SERVICE <https://yago-knowledge.org/sparql/query>\n" +
                "{\n" +
                "SELECT ?yagoDescription WHERE {\n" +
                "<"+getYagoID()+">"+" rdfs:comment ?yagoDescription.\n" +
                "  FILTER ( lang(?yagoDescription) = \"en\" )" +
                "}" +
                "}"+
                "}"
        );
        Hashtable<String,Integer>descriptionLengths=new Hashtable<String,Integer>();
        int wikidataDescLength=0;
        int dbpediaDescLength=0;
        int yagoDescLength=0;
        while(results.hasNext()){
            QuerySolution next1=results.next();
            wikidataDescLength=next1.get("wikidataDescription").toString().length();
            dbpediaDescLength=next1.get("dbpediaDescription").toString().length();
            yagoDescLength=next1.get("yagoDescription").toString().length();
        }
        descriptionLengths.put(getWikidataID(),wikidataDescLength);
        descriptionLengths.put(getDbpediaID(),dbpediaDescLength);
        descriptionLengths.put(getYagoID(),yagoDescLength);

        return descriptionLengths;
    }
    public Hashtable<String,Integer> getNumberOfProp(){
        Hashtable<String,Integer>NumberOfProperties=new Hashtable<String,Integer>();
        NumberOfProperties.put(getWikidataID(),getNumberOfWikiDataProp());
        NumberOfProperties.put(getDbpediaID(),getNumberOfDBpediaProp());
        NumberOfProperties.put(getYagoID(),getNumberOfYagoProp());
        return NumberOfProperties;
    }
    public Hashtable<String,Double> getPercScore(){
        Hashtable<String,Integer>descriptionLength=new Hashtable<String,Integer>();
        descriptionLength= (Hashtable<String, Integer>) this.lengthOfDescription.clone();
        Hashtable<String,Integer>numberOfProperties=new Hashtable<String,Integer>();
        numberOfProperties=(Hashtable<String, Integer>) this.numberOfProperties.clone();
        Hashtable<String,Double>percentageScoreDescription=new Hashtable<String,Double>();
        Hashtable<String,Double>percentageScoreProperties=new Hashtable<String,Double>();
        Hashtable<String,Double>percentageScore=new Hashtable<String,Double>();
        Object maxKey=null;
        int maxValue = Integer.MIN_VALUE;
        for(Map.Entry<String, Integer> entry : descriptionLength.entrySet()) {
            if(entry.getValue() > maxValue) {
                maxValue = entry.getValue();
                maxKey = entry.getKey();
            }
        }
        descriptionLength.remove(maxKey);
        percentageScoreDescription.put(maxKey.toString(),1.0);
        for(Map.Entry<String, Integer> entry1 : descriptionLength.entrySet()) {
            percentageScoreDescription.put(entry1.getKey(),entry1.getValue().doubleValue()/maxValue);
        }
        maxKey=null;
        maxValue = Integer.MIN_VALUE;
        for(Map.Entry<String, Integer> entry2 : numberOfProperties.entrySet()) {
            if(entry2.getValue() > maxValue) {
                maxValue = entry2.getValue();
                maxKey = entry2.getKey();
            }
        }
        numberOfProperties.remove(maxKey);
        percentageScoreProperties.put(maxKey.toString(),1.0);
        for(Map.Entry<String, Integer> entry3 : numberOfProperties.entrySet()) {
            percentageScoreProperties.put(entry3.getKey(),entry3.getValue().doubleValue()/maxValue);
        }
        for(Map.Entry<String, Double> entry4 : percentageScoreDescription.entrySet()) {
            percentageScore.put(entry4.getKey(),(entry4.getValue()+percentageScoreProperties.get(entry4.getKey()))/2);
        }
        return percentageScore;
    }
    public void printResults(){
        Hashtable<String,Integer>descriptionLength=new Hashtable<String,Integer>();
        descriptionLength=getLengthOfDescription();
        Hashtable<String,Integer>numberOfProperties=new Hashtable<String,Integer>();
        numberOfProperties=getNumberOfProperties();
        Hashtable<String,Double>percentageScore=new Hashtable<String,Double>();
        percentageScore=getPercentageScore();
        Formatter fmt = new Formatter();
        fmt.format("%30s %30s %30s %30s\n", "Number of properties", "Length of description","Percentage score","URI");
        String [] uriTable={getWikidataID(),getDbpediaID(),getYagoID()};
        for (String uri : uriTable)
        {
            fmt.format("%30s %30s %30s %30s\n", numberOfProperties.get(uri).toString() , descriptionLength.get(uri).toString(), String.format("%.2f",((percentageScore.get(uri))*100.0)).toString()+"%",uri);
        }
        System.out.println(fmt);

    }
}
