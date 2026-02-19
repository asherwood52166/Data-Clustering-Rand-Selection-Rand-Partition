# Data Clustering: random selection vs random partition
## This includes 10 .txt files that you will run the code with. It will output a performance measurement in a .csv file to see things like sum of squared error and number of iterations until it converged. each time the code is executed, both initialization methods will be ran.
## It's ran through the command line with an example below

### Compile code
`javac DataClustering_RandPartSel.java`
### Run code
`java DataClustering_RandPartSel ecoli.txt 3 100 0.01 5`
- 3 = cluster count
- 100 = number of iterations per run
- 0.01 = convergence threshold
- 5 = number of runs

A brief difference between random selection and random partition is that random selection chooses a random point and makes it the centroid for each one. Random partition takes each point and assigns it to a random cluster.

[Link to repo](https://github.com/asherwood52166/Data-Clustering-Rand-Selection-Rand-Partition.git)


