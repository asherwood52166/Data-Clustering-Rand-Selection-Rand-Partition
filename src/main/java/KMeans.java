/*
Programming practices followed by Oracle
"Code Conventions for the Java Programming Language"
https://www.oracle.com/java/technologies/javase/codeconventions-introduction.html
*/
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class KMeans {
    int numOfClusters = 0;
    int numOfPoints = 0;    
    int dimension = 0;
    Double[][] info;
    int[] CentroidLoc;
    Double[][] Centroid;
    List<List<Double[]>> pointsInCluster; 
    double totalSSE = 0.0;
    double bestSSE = 0.0;
    Random rand = new Random(System.nanoTime());
    
    KMeans(int numOfClusters, Double[][] info, int numOfPoints,
                                                           int dimsOfEachPoint){
        this.numOfClusters = numOfClusters;
        this.info = info;
        this.numOfPoints = numOfPoints;
        this.dimension = dimsOfEachPoint;
        
        CentroidLoc = new int[numOfClusters];
        Centroid = new Double[numOfClusters][dimsOfEachPoint];
        
        pointsInCluster = new ArrayList<>(); //Populating array list
        for (int i = 0; i < numOfClusters; i++){
            pointsInCluster.add(new ArrayList<>());
        }
    }
    
    double getTotalSSE(){
        return totalSSE;
    }
    
    //Method for generating array of arraylists based on file
    //Create random ints based on num of points
    //Map those ints to physical rows from 2d array to insert them into randCentroid
    //randCentroid
    Double[][] randSelClust(){
        Centroid = new Double[numOfClusters][dimension];
        for (int i = 0; i < numOfClusters; i++){
            CentroidLoc[i] = rand.nextInt(numOfPoints);
            //2 lines to follow 80 length code convention
            System.arraycopy(info[CentroidLoc[i]], 0,
                                     Centroid[i], 0, dimension);
        }
        return Centroid;
    }
    Double[][] randPartClust(){
        
        Centroid = new Double[numOfClusters][dimension];
        
        for (int i = 0; i < numOfClusters; i++){
            pointsInCluster.get(i).clear();
        }
        
        for (int i = 0; i< numOfPoints; i++){
            int randint = rand.nextInt(numOfClusters);
            pointsInCluster.get(randint).add(info[i]);
        }
        
        k_Means_Mean();
        return Centroid;
    }
    
    //Method for mapping every point to a centroid based on random generated clust
    //pointsInCluster is: in each cluster there are "points" corresponding to a row of data
    //each "point" houses the whole row (dimension) of the point 
    double PointToClustAssign(){
        double squaredError[]= new double[numOfClusters];
        bestSSE = 0.0;
        totalSSE = 0;
        int bestClusterCounter = 0;
        
        for (int i = 0; i < numOfClusters; i++){
            pointsInCluster.get(i).clear();
        }
        for (int i = 0; i < numOfPoints; i++){      //go through each point
            
            for (int j = 0; j < numOfClusters; j++){     //In each cluster
            
                for (int l = 0; l < dimension; l++){ //for every dimension
                    //2 lines to follow 80 length code convention
                    squaredError[j] += (info[i][l] - Centroid[j][l]) 
                            * (info[i][l] - Centroid[j][l]);
                }
            }
            //Makes the first squaredError the best for comparison
            bestSSE = squaredError[0];
            bestClusterCounter = 0;
            for (int m = 1; m < numOfClusters; m++){
                if(squaredError[m] < bestSSE){
                bestSSE = squaredError[m];
                bestClusterCounter = m;
                }
            }
            pointsInCluster.get(bestClusterCounter).add(info[i]);
            totalSSE += bestSSE;
        //Resets squaredError for the next point
        for (int p = 0; p < numOfClusters; p++)
        squaredError[p] = 0.0;
        }
        return bestSSE;
    }
    
    void k_Means_Mean(){ //Calculates the mean for each cluster to assign new cluster
        double[][] tempNewCentroids = new double[numOfClusters][dimension];
      
        //Go through each cluster
        for (int i = 0; i < numOfClusters; i++){
            int numPoints = pointsInCluster.get(i).size(); //how many "rows" are in each cluster
            //For each point in i cluster
            for (int j = 0; j < numPoints; j++){
                Double[] point = pointsInCluster.get(i).get(j);
                //Go through each dimension of each point
                for (int l = 0; l < dimension; l++){
                    //NewCentroids will house the summed values which will then be avg
                    tempNewCentroids[i][l] += point[l];
                }
            }
            for (int m = 0; m < dimension; m++){  //Loop for finding avg
                tempNewCentroids[i][m] /= numPoints;
            }
        }
        for (int n = 0; n < numOfClusters; n++){   //Updating centroids
            for(int p = 0; p < dimension; p++){
                Centroid[n][p] = tempNewCentroids[n][p];
            }
        }  
    }
}
