
package percolation;


public class Percolation {
    
    WeightedQuickUnionUF uf;
    int[][] grid;
    private final int size;
    

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.size = n;
        uf = new WeightedQuickUnionUF((int)Math.pow(n, 2) + 2); //0 -> head, n+1 -> tail
        this.grid = new int[n][n];
        for (int row = 0; row <n; row++) {
            for (int col = 0; col <n; col++) {
                grid[row][col] = 0; //setting 0 for the closed sites
            } 
        }
    }
    
    public int getId(int row, int col) { //elements indexed from 1 to n
        return (row-1)*size+col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        grid[row-1][col-1] = 1;
            if (isOpen(row-1, col) == true)
                uf.union(getId(row, col), getId(row-1, col));
            if (isOpen(row+1, col) == true)
                uf.union(getId(row, col), getId(row+1, col));
            if (isOpen(row, col-1) == true)
                uf.union(getId(row, col), getId(row, col-1));
            if (isOpen(row, col+1) == true)
                uf.union(getId(row, col), getId(row, col+1));
        
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row-1 <0 || col-1 <0 || row >= size || col >= size) 
            return false;
        else if (grid[row-1][col-1] == 1) 
            return true;
        return false;
    }

    // is the site (row, col) full?
    //public boolean isFull(int row, int col) {
    //    
    //}

    // returns the number of open sitess
    public int numberOfOpenSites() {
        int count = 0;
        for (int row = 1; row <= size; row++) {
            for (int col = 1; col <= size; col++) {
                if (isOpen(row, col))
                    count++;      
            }         
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        int head = 0;
        int tail = (int)Math.pow(size, 2) + 1;
        for (int i=1; i<=size; i++) {
            uf.union(getId(1, i), head);
            uf.union(getId(size, i), tail);
        }
        return uf.connected(head, tail);
    }
    
    public static void main(String[] args) {
    }
    
}
