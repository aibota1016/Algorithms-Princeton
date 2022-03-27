
package percolation;


public class Percolation {
    private static final int TOP = 0;
    private final WeightedQuickUnionUF uf;
    private final int size;
    private final int bottom;
    private final boolean[][] openNodes;
    private int openSites;
    

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.size = n;
        bottom = n * n + 1;
        uf = new WeightedQuickUnionUF(n * n + 2);
        openNodes = new boolean[n][n];
        openSites = 0;
    }
    
    private int getId(int row, int col) { //elements indexed from 1 to n
        if (!isValid(row, col)) 
            throw new IllegalArgumentException();
        return size * (row - 1) + col;
    }
    
    // checks whether the passed indexes are valid
    private boolean isValid(int i, int j) {
        return i > 0 && j > 0 && i <= size && j <= size;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isValid(row, col)) 
            throw new IllegalArgumentException();
        openNodes[row - 1][col - 1] = true;
        ++openSites;
        if (row == 1) {
            uf.union(getId(row, col), TOP);
        }
        if (row == size) {
            uf.union(getId(row, col), bottom);
        }
        if (isValid(row-1, col) && isOpen(row-1, col))
            uf.union(getId(row, col), getId(row-1, col));
        if (isValid(row+1, col) && isOpen(row+1, col))
            uf.union(getId(row, col), getId(row+1, col));
        if (isValid(row, col-1) && isOpen(row, col-1))
            uf.union(getId(row, col), getId(row, col-1));
        if (isValid(row, col+1) && isOpen(row, col+1))
            uf.union(getId(row, col), getId(row, col+1));
        
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isValid(row, col)) 
            throw new IllegalArgumentException();
        return openNodes[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
         if (isValid(row, col)) {
            return uf.find(TOP) == uf.find(getId(row, col));
        } else throw new IllegalArgumentException();
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(TOP) ==  uf.find(bottom);
    }
    
    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        p.open(1, 3);
        System.out.println(p.percolates());
    }
    
}
