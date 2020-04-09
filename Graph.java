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

    //initialize Attributes
    V = 0;
    E = 0;
    edges = (ArrayList<Edge>[]) new ArrayList[V];

    try {
      URL urlString = new URL(url);
      BufferedReader in = new BufferedReader(new InputStreamReader(urlString.openStream()));

      String inputLine;
      // skip the metadata
      in.readLine();
      // read each line in the dataset
      while ((inputLine = in.readLine()) != null) {


      }
      in.close();

    } catch(Exception IOException) {
      System.out.println("Unable to access the data.");
    }
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
    //create a new graph g with the tinyEWD.txt url as argument.
    String url = "https://cs.brynmawr.edu/Courses/cs330/spring2020/tinyEWD.txt";
    Graph g = new Graph(url);

    //print out the graph, g (using the print methods) (JAVA) System.out.println(g)

    //for vertex <- 0 to g.V()-1 do
    //      print all edges coming out of v (use getAdj())
  } //main()
} //Graph
