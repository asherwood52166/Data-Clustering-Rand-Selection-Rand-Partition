/*
Programming practices followed by Oracle
"Code Conventions for the Java Programming Language"
https://www.oracle.com/java/technologies/javase/codeconventions-introduction.html
*/
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class File_Writer {
    
    String dataSetFile;
    String fileName = "Performance_Measurements.csv";
    
    String initMethod;
    double bestInitSSE;
    double bestFinSSE;
    int bestNumIter;
    
    File_Writer(String nameOfFile){
        dataSetFile = nameOfFile;
    }
    
    void setBestInitSSE(double initSSE){
            bestInitSSE = initSSE;
    }
            
    void setBestFinSSE(double finSSE){
        bestFinSSE = finSSE;
    }
    
    void setBestNumIter(int numIter){
        bestNumIter = numIter;
    }
    
    void setinitMethod(String method){
        initMethod = method;
    }
    
    //Method for writing header only if the file hasn't been created yet
    void writeHeader(){
        File f = new File(fileName);
        if (!f.exists()){
            String[] headers = {"Data file", "Normalization", "Initialization", "Initial SSE", "Final SSE", "Number of iterations"};
        
        try (FileWriter writer = new FileWriter(fileName, true)){
            writer.append(String.join(",", headers)).append("\n"); 
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    }
    
    
    void write(){
        String[] row = {dataSetFile, "min-max", initMethod, String.valueOf(bestInitSSE), String.valueOf(bestFinSSE), String.valueOf(bestNumIter)};
     try (FileWriter writer = new FileWriter(fileName, true)){
      writer.append(String.join(",", row)).append("\n");
    }catch(IOException e){
        e.printStackTrace();
    }
    }
}

