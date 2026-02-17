/*
Programming practices followed by Oracle
"Code Conventions for the Java Programming Language"
https://www.oracle.com/java/technologies/javase/codeconventions-introduction.html
*/
public class Normalization {
    
    Double info[][];
    int dimension;
    int numPoints;
    double max;
    double min;
    
    /*colIter is an iterator for findMinMax and normCalc
    normCalc calls findMinMax so for persistence of column index, this outside
    iterator is created*/
    int colIter; 
    
    public Normalization(Double[][] info, int dimension, int numPoints){
        this.info = info;
        this.dimension = dimension;
        this.numPoints = numPoints;
    }
    
    private void findMinMax(){
        
           max = info[0][colIter];
            min = info[0][colIter];
            
        for (int i = 0; i < numPoints; i++){
            if (max < info[i][colIter])
                max = info[i][colIter];
            if (min > info[i][colIter])
                min = info[i][colIter];
        }
    }
    
    Double[][] normCalc(){
        for (colIter = 0; colIter < dimension; colIter ++){
            findMinMax();
            
            for (int i = 0; i < numPoints; i++){
                if (max != min)
                    //Norm calc
                    info[i][colIter] = (info[i][colIter] - min) / (max - min);
                else
                    //Catch for dividing by zero
                    info[i][colIter] = 0.0; 
            }
        }
        return info;
    }
}
