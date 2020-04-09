import java.util.*;
import java.net.*;
import java.io.*;
import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;

public class Graph {
  //Attributes
  int V; //# vertices in the Graph
  int E; //# edges in the Graph
  ArrayList<Edge>[] edges; //an array of arraylist of edges

  //Contstructor
  Graph(String url) {
    //reads graph from url, sets up V, E, edges

  } //Graph()

  //Accessors

  //returns the number of vertices in the Graph
  public int V() {
    return V;
  } //V()

  //returns the number of edges in the Graph
  public int E() {
    return E;
  } //E()

  //returns the list of Edges, from vertex, v
  public ArrayList<Edge> getAdj(int v) {
    return new ArrayList<Edge>();
  } //getAdj()

  //prints the entire graph
  public String toString() {
    return "";
  } //toString()

  public static void main(String[] args) {
    try {
      //create a new graph g with the tinyEWD.txt url as argument.
      URL url = new URL("https://cs.brynmawr.edu/Courses/cs330/spring2020/tinyEWD.txt");

      //print out the graph, g (using the print methods) (JAVA) System.out.println(g)

      //for vertex <- 0 to g.V()-1 do
      //      print all edges coming out of v (use getAdj())

    }
    catch(Exception IOException) {
      System.out.println("Unable to access the data.");
    }

  } //main()
} //Graph
