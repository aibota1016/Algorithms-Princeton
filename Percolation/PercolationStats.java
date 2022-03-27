
package percolation;

import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private double[] threshold;
    private int trials;
    
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.threshold = new double[trials];
        this.trials = trials;
        for (int i=0; i<trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                p.open(StdRandom.uniform(1, n+1), StdRandom.uniform(1, n+1));
            }   
            threshold[i] = p.numberOfOpenSites()/ Math.pow(n, 2);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.97 * StdStats.var(threshold)/Math.sqrt(trials));
    }
 
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.97 * StdStats.var(threshold)/Math.sqrt(trials));
    }

   
   public static void main(String[] args) {
       //int n = Integer.parseInt(args[0]);
       //int trials = Integer.parseInt(args[1]);
       Stopwatch stopwatch = new Stopwatch();
       
       PercolationStats p = new PercolationStats(200, 100);
       System.out.println("mean()            =" + p.mean());
       System.out.println("stddev()          =" + p.stddev());
       System.out.println("ConfidenceLow()   =" + p.confidenceLo());
       System.out.println("ConfidenceHigh()  =" + p.confidenceHi());
       System.out.println(stopwatch.elapsedTime());
   }
    
}
