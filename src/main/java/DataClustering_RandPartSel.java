/*
Programming practices followed by Oracle
"Code Conventions for the Java Programming Language"
https://www.oracle.com/java/technologies/javase/codeconventions-introduction.html
*/

import java.io.FileNotFoundException;
import java.io.IOException;

public class DataClustering_RandPartSel {
    public static void main(String[] args) throws FileNotFoundException,
                                                                    IOException{
        //Name of file
        String nameOfFile = args[0];     
         //Number of clusters
        int numOfClusters = Integer.parseInt(args[1]);     
        //Maximum number of iterations in a run
        int maxNumOfIterations = Integer.parseInt(args[2]); 
        //Convergence threshold
        double convergenceThreshold = Double.parseDouble(args[3]);
        //Number of runs
        int numOfRuns = Integer.parseInt(args[4]);              
        
        Double info[][];
        double SSE;
        double [] bestRun = new double [numOfRuns]; 
        double oldSSEHolder;
        double bestFinSSE = 0;
        int bestRunCount = 0;
        
        double bestInitSSE = Double.MAX_VALUE;
        int bestNumIter = 0;
        
        //Creates FileAnalyzer object and passes nameOfFile to it
        FileAnalyzer Analyzer = new FileAnalyzer(nameOfFile);
        
        //Creates File_Writer object for writing to csv file
        File_Writer fileWriter = new File_Writer(nameOfFile);
        
        //Gets back a 2d array in info
        info = Analyzer.ReadFile();
        
        //Creates Normalization object and calls method to calculate and return
        //normalized values
        Normalization norm = new Normalization(info, Analyzer.getdimension(), Analyzer.getNumOfPoints());
        info = norm.normCalc();
        
        
        
        /************************************************************************
        *Block of code for executing KMeans with Random selection initialization*
        ************************************************************************/
        
        //Creates KMeans object for random selection
        KMeans KMeansRandSel = new KMeans(numOfClusters, info, Analyzer.getNumOfPoints(),
                                                       Analyzer.getdimension());
        
        for (int i = 0; i < numOfRuns; i++){
            
            //Populates the pointsInCluster
            for (int c = 0; c < KMeansRandSel.pointsInCluster.size(); c++)
                KMeansRandSel.pointsInCluster.get(c).clear();
            //Creates a random set of centroids per Run
            KMeansRandSel.randSelClust();
            //sets max value for comparisons later on
            oldSSEHolder = Double.MAX_VALUE; 
            bestFinSSE = Double.MAX_VALUE;
            
            KMeansRandSel.PointToClustAssign();
            //Searching for best initial SSE
            if (KMeansRandSel.getTotalSSE() < bestInitSSE){
                bestInitSSE = KMeansRandSel.getTotalSSE();
            }
            for (int j = 0; j < maxNumOfIterations; j++){
                //Calculates mean for points in each cluster for new centroid loc
                KMeansRandSel.k_Means_Mean();
                //Assigns points to each random centroid created
                SSE = KMeansRandSel.PointToClustAssign();
                
                double currentSSE = KMeansRandSel.getTotalSSE();
                if(currentSSE < bestFinSSE)   
                    bestFinSSE = currentSSE;  //Comparison
                //Tracking change (convergence) per iteration
                
                if(Math.abs(currentSSE - oldSSEHolder) < convergenceThreshold){   
                    bestNumIter = j + 1;
                    break;
                }
                oldSSEHolder = currentSSE;
            }
            bestRun[i] = bestFinSSE;
        }
        //Finding best SSE of all runs
        bestFinSSE = bestRun[0];
        for (int i = 1; i < numOfRuns; i++){
            if (bestRun[i] < bestFinSSE){
                bestFinSSE = bestRun[i];
                bestRunCount = i;
            }
        }
        
        /***************************************************
        *End of KMeans with Random selection initialization*
        ***************************************************/
         
        fileWriter.setBestInitSSE(bestInitSSE);
        fileWriter.setBestFinSSE(bestFinSSE);
        fileWriter.setBestNumIter(bestNumIter);
        fileWriter.setinitMethod("Random Selection");
        
        fileWriter.writeHeader();
        fileWriter.write();
        
        
        /************************************************************************
        *Block of code for executing KMeans with Random partition initialization*
        ************************************************************************/
        
        //Reinitiating variables for random partition
        bestInitSSE = Double.MAX_VALUE;
        bestFinSSE = Double.MAX_VALUE;
        bestNumIter = 0;
        
        //Creates KMeans object for random partition
         KMeans KMeansRandPart = new KMeans(numOfClusters, info, Analyzer.getNumOfPoints(),
                                                       Analyzer.getdimension());
        
        for (int i = 0; i < numOfRuns; i++){
            
            //Populates the pointsInCluster
            for (int c = 0; c < KMeansRandPart.pointsInCluster.size(); c++)
                KMeansRandPart.pointsInCluster.get(c).clear();
            //Creates a random set of centroids per Run
            KMeansRandPart.randPartClust();
            //sets max value for comparisons later on
            oldSSEHolder = Double.MAX_VALUE; 
            bestFinSSE = Double.MAX_VALUE;
            
            SSE = KMeansRandPart.PointToClustAssign();
            //Searching for best initial SSE
            if (KMeansRandPart.getTotalSSE() < bestInitSSE){
                bestInitSSE = KMeansRandPart.getTotalSSE();
            }
            for (int j = 0; j < maxNumOfIterations; j++){
                //Calculates mean for points in each cluster for new centroid loc
                KMeansRandPart.k_Means_Mean();
                //Assigns points to each random centroid created
                SSE = KMeansRandPart.PointToClustAssign();
                
                double currentSSE = KMeansRandPart.getTotalSSE();
                if(currentSSE < bestFinSSE)   
                    bestFinSSE = currentSSE;  //Comparison
                //Tracking change (convergence) per iteration
                
                if(Math.abs(currentSSE - oldSSEHolder) < convergenceThreshold){   
                    bestNumIter = j + 1;
                    break;
                }
                oldSSEHolder = currentSSE;
            }
            bestRun[i] = bestFinSSE;
        }
        //Finding best SSE of all runs
        bestFinSSE = bestRun[0];
        for (int i = 1; i < numOfRuns; i++){
            if (bestRun[i] < bestFinSSE){
                bestFinSSE = bestRun[i];
                bestRunCount = i;
            }
        }
        
        /***************************************************
        *End of KMeans with Random partition initialization*
        ***************************************************/
        
        
        fileWriter.setBestInitSSE(bestInitSSE);
        fileWriter.setBestFinSSE(bestFinSSE);
        fileWriter.setBestNumIter(bestNumIter);
        fileWriter.setinitMethod("Random Partition");
        
        fileWriter.write();
    }
}
