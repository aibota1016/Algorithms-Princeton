
package collinearpoints;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;


public class FastCollinearPoints {
    private static final java.util.Random rd = new java.util.Random();
    private final ArrayList<ArrayList<Point>> collinearPoints = new ArrayList<>();
    private final ArrayList<Point> nonCollinearPoints = new ArrayList<>();
    private LineSegment[] segments;
    
    public FastCollinearPoints(Point[] points) {    // finds all line segments containing 4 or more points
        for (Point point : points) 
            if (point == null)
                throw new IllegalArgumentException("argument constructor contains null ");
        nonCollinearPoints.addAll(Arrays.asList(points));
        while(!nonCollinearPoints.isEmpty()) {
            Point p = nonCollinearPoints.get(0);
            double[] slopes = calculateSlopes(p, points);
            nonCollinearPoints.remove(p);
            sort(slopes, points);
            int j = 0;
            while (j < slopes.length) {
                double current = slopes[j];
                int i = findLastOccurrence(slopes, current);
                if((i - j) >= 2) {
                    ArrayList<Point> Ps = new ArrayList<>();
                    int k = 0;
                    while (j + k <= i) {
                        Ps.add(points[j+k]);
                        nonCollinearPoints.remove(points[j+k]);
                        if (!Ps.contains(points[0])) 
                            Ps.add(points[0]);
                        k++;
                    }
                    j = i + 1;
                    if (!Ps.isEmpty())
                        collinearPoints.add(Ps);
                } else
                    j++;
            }
        }
        segments = new LineSegment[collinearPoints.size()];
        for (int i = 0; i < collinearPoints.size(); i++) {
            LineSegment l = new LineSegment(minPoint(collinearPoints.get(i)), maxPoint(collinearPoints.get(i)));
            segments[i] = l;
        }
    }
    
    public int numberOfSegments()  {      // the number of line segments
        return collinearPoints.size();
    }
    
    public LineSegment[] segments() {
        return segments;
    }

    private static Point maxPoint(ArrayList<Point> points) {
        int i;
        Point max = points.get(0);
        for (i=1; i<points.size(); i++) {
            if (points.get(i).compareTo(max) > 0)
                max = points.get(i);
        }
        return max;
    }
    
    private static Point minPoint(ArrayList<Point> points) {
        int i;
        Point min = points.get(0);
        for (i=1; i<points.size(); i++) {
            if (points.get(i).compareTo(min) < 0)
                min = points.get(i);
        }
        return min;
    }
    
    
  
    // Function to find the last occurrence of a given number
    // in a sorted integer array
    private static int findLastOccurrence(double[] nums, double target) {
        int left = 0;
        int right = nums.length - 1;
        int index = -1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (target == nums[mid]) {
                index = mid;
                left = mid + 1;
            }
            else if (target < nums[mid]) {
                right = mid - 1;
            }
            else {
                left = mid + 1;
            }
        }
        return index;
    }
    
    
    private double[] calculateSlopes(Point p, Point[] points) { //For each other point q, determine the slope it makes with p.
        int n = points.length;
        double[] slopes = new double[n]; //MAKE POINTS NOT CONTAIN ITSELF
        for (int i = 0; i < n; i++) {
            double slope = p.slopeTo(points[i]);
            slopes[i] = p.slopeTo(points[i]);
        }
        return slopes;
    }
    
    private static Point[] sort(double[] slopes, Point[] points) {
        quickSort(slopes, 0, slopes.length - 1, points);
        return points;
    }
    
    private static void quickSort(double[] slopes, int p, int r, Point[] points) { //Sort the points according to the slopes they makes with p.
        if (p < r) {
            int q = Partition(slopes, p, r, points);
            quickSort(slopes, p, q-1, points);
            quickSort(slopes, q + 1, r, points);
        }
    }
    
    private static int Partition(double[] slopes, int p, int r, Point[] points) {
        int index  = p + rd.nextInt(r - p + 1);
        double pivot  = slopes[index];
        int result = p;
        swap(slopes,index,r,points);

        for (int j=p ; j<r ; j++) 
            if (Double.compare(slopes[j], pivot) < 0) 
                swap(slopes, j,result++, points);
        swap(slopes,result,r, points);
        return result;
    }
    
    
    private static void swap(double[] slopes, int p, int r, Point[] points) {
        swap(slopes,p,r);
        swap(points,p,r);
    }

    private static void swap(double[] arr, int i, int j) {
        double tmp  = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;     
    }
    
    private static void swap(Point[] points, int i, int j) {
        Point tmp = points[i];
        points[i] = points[j];  
        points[j] = tmp;
    }
    
    
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        

        }
    
}
