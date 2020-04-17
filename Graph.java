import java.util.*;
import java.net.*;
import java.io.*;
import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;

public class Graph {
  //Attributes
  int V; //# vertices in the Graph
  int E; //# edges in the Graph
  ArrayList<Edge>[] edges; //an array of arraylists of edges. each index is
                          //an arraylist of edges leaving from the vertex of that index

  // attributes for dijkstra
  private double[] shortest;
  private int[] pred;
  private PriorityQueue frontier;

  //Contstructor
  Graph(String url) {
    //reads graph from url, sets up V, E, edges

    try {
      URL urlString = new URL(url);
      BufferedReader in = new BufferedReader(new InputStreamReader(urlString.openStream()));

      String inputLine;

      //set the number of vertices
      V = Integer.parseInt(in.readLine());

      //set the number of edges
      E = Integer.parseInt(in.readLine());

      //initialize edges
      edges = (ArrayList<Edge>[]) new ArrayList[V];

      for(int i  = 0; i < edges.length; i++) {
        edges[i] = new ArrayList<Edge>();
      }

      // read each line in the dataset
      while ((inputLine = in.readLine()) != null) {
        // create the edge
        // split the pieces of the line
        // pieces[0] = from, pieces[1] = to, pieces[2] = weight
        String pieces[] = inputLine.split(" ");

        // use pieces[] to create the from, to, and weight, converting each to int/double
        int from = Integer.parseInt(pieces[0]);
        int to = Integer.parseInt(pieces[1]);
        double weight = Double.parseDouble(pieces[2]);

        // use these to create the edge
        Edge e = new Edge(from, to, weight);

        // add this edge to edges
        // use the 'from' vertex as the index
        edges[e.from()].add(e);
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
    try {
      return edges[v];
    } catch(Exception ArrayIndexOutOfBoundsException) {
      System.out.println("That vertex is not in the Graph.\n");
      return null;
    }
  } //getAdj()

  //prints the entire graph
  public String toString() {
    // set up the opening line
    String graphString = "Graph G = <|V|, |E|> = <" + V + ", " + E + ">\n";

    for(int i = 0; i < edges.length; i++) {
      graphString += getAdj(i) + "\n";
    }

    return graphString;
  } //toString()

  public static String getInput(String prompt) {
    System.out.print(prompt);
    String input = null;
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      input = br.readLine();
    } catch (IOException io) {
      io.printStackTrace();
    }
    return input;
  } // getInput()

  // TBD
  // // compute all shortest paths from s
  // public static ??? dijkstra(s) {
  //
  // } // dijkstra()
  //
  // // return the shortest path from s to t
  // public static ??? getPath(s, t) {
  //
  // } // getPath()

  public static void main(String[] args) {
    //create a new graph g with the tinyEWD.txt url as argument.
    String url = "https://cs.brynmawr.edu/Courses/cs330/spring2020/tinyEWD.txt";
    Graph g = new Graph(url);

    //print out the graph, g (using the print methods) (JAVA) System.out.println(g)
    System.out.println(g);

    //for vertex <- 0 to g.V()-1 do
    //      print all edges coming out of v (use getAdj())
    System.out.println("PRINTING ALL EDGES USING getAdj()");
    for(int vertex = 0; vertex <= g.V()-1; vertex++) {
      System.out.println(g.getAdj(vertex));
    }
    System.out.print("\n");

    // get user queries
    while(true) {
      String prompt = "Enter a vertex: ";
      String vertex = getInput(prompt);

      try {
        int v = Integer.parseInt(vertex);

        //get the neighbors for that vertex
        ArrayList<Edge> neighbors = g.getAdj(v);

        // if it is a valid index
        if(neighbors != null) {
          // check if it has neighbors
          if(neighbors.size() == 0) {
            System.out.println(v + " has no neighbors.\n");
          }
          else {
            System.out.println(v + " has the following neighbors:");
            for(Edge e: neighbors) {
              System.out.print(e + ", ");
            }
            System.out.print("\n\n");
          }
        }
      }
      catch(Exception NumberFormatException) {
        System.out.println("Invalid input.\n");
      }
    }
  } //main()
} //Graph
