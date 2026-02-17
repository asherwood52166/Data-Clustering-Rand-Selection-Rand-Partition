/*
Programming practices followed by Oracle
"Code Conventions for the Java Programming Language"
https://www.oracle.com/java/technologies/javase/codeconventions-introduction.html
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class FileAnalyzer {
    //variables for the analyzer
    String nameOfFile = "";
    int numOfPoints = 0;    
    int dimension = 0;
    ArrayList<Double[]> contentsOfFile = new ArrayList<>();
    Double[][] info;
    
    FileAnalyzer(String nameOfFile){
        this.nameOfFile = nameOfFile;
    }
    
    int getNumOfPoints(){
        return numOfPoints;
    }
    
    int getdimension(){
        return dimension;
    }
    
    Double[][] ReadFile() throws FileNotFoundException{
        File file = new File(nameOfFile);
        Scanner scan = new Scanner(file);
        
        //Gets first line for variables
        if(scan.hasNextLine()){
            
            //retrieves the firstline and puts it in firstline variable
            String firstline = scan.nextLine();
            
            //splits up that line based on white space
            //The top of each file has the num of points and then the dim of each point
            //This array stores them both
            String[] num_and_dims_of_file = firstline.split("\\s+");
            numOfPoints = Integer.parseInt(num_and_dims_of_file[0]);
            dimension = Integer.parseInt(num_and_dims_of_file[1]);
        }
        
        //Gets data
        while(scan.hasNextLine()){
            String GetNextLine = scan.nextLine();
            String[] eachRow = GetNextLine.split("\\s+");//Separate blank space
            Double[] eachRowDouble = new Double[dimension];
            for(int i = 0; i < dimension; i++){
                eachRowDouble[i] = Double.parseDouble(eachRow[i]);
            }
            contentsOfFile.add(eachRowDouble);
        }
        //Turning array list of arrays into a 2d array 
        info = contentsOfFile.toArray(new Double[contentsOfFile.size()][]);
        return info;
    }
}

