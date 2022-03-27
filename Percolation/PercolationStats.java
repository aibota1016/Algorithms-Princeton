
package percolation;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] threshold;
    private final int trials;
    
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.threshold = new double[trials];
        this.trials = trials;
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                p.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
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
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(trials);
    }
 
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(trials);
    }

   
   public static void main(String[] args) {
       int n = Integer.parseInt(args[0]);
       int t = Integer.parseInt(args[1]);
       PercolationStats p = new PercolationStats(n, t);
       System.out.println("mean = " + p.mean());
       System.out.println("stddev = " + p.stddev());
       System.out.println("95% confidence interval = [" + p.confidenceLo() + ", " + p.confidenceHi() + "]");
   }
    
}
