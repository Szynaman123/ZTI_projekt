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

import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        CustomQuery query=new CustomQuery("src/com/base/querries/query1.txt");
        query.printResults();
   }}



