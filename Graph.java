/*
Name: Sophia Trump
File: Graph.java
Description: Defines the graph class, which implements dijkstra's shortest
path algorithm. Queries the user for a vertex, and then outputs its
neighbors, if any. Also prints out the entire graph.
*/

import java.util.*;
import java.net.*;
import java.io.*;
import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;

public class Graph {
  //Attributes
  private int V; //# vertices in the Graph
  private int E; //# edges in the Graph
  ArrayList<Edge>[] edges; //an array of arraylists of edges. each index is
                          //an arraylist of edges leaving from the vertex of that index

  // Attributes for dijkstra
  private double[] shortest; // contains the weight of the shortest path from s to t
  private int[] pred; // contains the predecessors of each vertex on shortest paths
  private PriorityQueue<Integer> frontier; // vertices yet to be explored

  //Contstructor
  Graph(String url) {
    //reads graph from url, sets up V, E, edges

    try {
      URL urlString = new URL(url);
      BufferedReader in = new BufferedReader(new InputStreamReader(urlString.openStream()));

      String inputLine;

      // INITIALIZE ATTRIBUTES

      //set the number of vertices
      V = Integer.parseInt(in.readLine());

      //set the number of edges
      E = Integer.parseInt(in.readLine());

      //initialize edges
      edges = (ArrayList<Edge>[]) new ArrayList[V];

      // initialize shortest and pred
      // and frontier
      shortest = new double[V];
      pred = new int[V];

      // define a new PriorityQueue, frontier
      // this PriorityQueue overwrites the compare method for integers
      // in order to prioritize elements by their corresponding values
      // in shortest[]. The smaller the corresponding value in shortest,
      // the higher its rank in the queue.
      frontier = new PriorityQueue<Integer>(new Comparator<Integer>() {
        public int compare(Integer v1, Integer v2) {
          if(shortest[v1] < shortest[v2]) {
            return -1;
          }
          else if(shortest[v1] > shortest[v2]) {
            return 1;
          }
          return 0;
        }
      });

      for(int i  = 0; i < edges.length; i++) {
        edges[i] = new ArrayList<Edge>();
      }

      // read each line in the dataset
      while ((inputLine = in.readLine()) != null) {
        // create the edge
        // split the pieces of the line
        // pieces[0] = from, pieces[1] = to, pieces[2] = weight
        String pieces[] = inputLine.trim().split("\\s+");

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

  private static String getInput(String prompt) {
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

  // compute all shortest paths from s
  public void dijkstra(int s) {
      // set the values for pred and shortest
      for(int v = 0; v < V; v++) {
       shortest[v] = Double.POSITIVE_INFINITY;
        pred[v] = -1;
      }

      // set the pred and shortest for this vertex
      shortest[s] = 0;
      pred[s] = -1;

      // add the vertices to the frontier
      // they are ordered by their corresponding values in shortest[]
      for(int w = 0; w < V; w++) {
        frontier.add(w);
      }

      // while the frontier is not empty
      while(frontier.size() > 0) {
        // remove from FRONTIER the vertex with shortest value
        int u = frontier.poll();

        // get the vertices adjacent to u
        ArrayList<Edge> adjacent = getAdj(u);

        // for each vertex v adjacent to u
        for(Edge v: adjacent) {
          relax(u, v);
        }
      }
  } // dijkstra()

  private void relax(int u, Edge v) {
    // updates the values in shortest[] and pred[] if possible
    if(shortest[u] + v.weight() < shortest[v.to()]) {
      shortest[v.to()] = shortest[u] + v.weight();
      pred[v.to()] = u;

      // remove and update its place in the PriorityQueue
      frontier.remove(v.to());
      frontier.add(v.to());
    }
  } // relax()

  // return the shortest path from s to t
  public ArrayList<Integer> getPath(int s, int t) {
    // was djikstra run with s as source???
    // if s was the source, shortest[s] will be 0.
    // because the source to itself has a distance of 0.
    if(shortest[s] != 0) {
      throw new IllegalArgumentException("Invalid source vertex! Try running djikstra() with " + s + " and try again.\n");
    }
    else {
      ArrayList<Integer> path = new ArrayList<Integer>();

      int currentVertex = t;
      path.add(0, currentVertex);
      // stop if the graph is disconnected / if any of the pred values for the current vertex is -1
      while(pred[currentVertex] != s && pred[currentVertex] != -1) {
        path.add(0, pred[currentVertex]);
        currentVertex = pred[currentVertex];
      }
      path.add(0, pred[currentVertex]);
      return path;
    }
  } // getPath()

  public void printPath(ArrayList<Integer> path, int source, int destination) {
    // check if the path exists first
    if(path.get(0) != source || path.get(path.size() - 1) != destination) {
      System.out.println("The path does not exist.");
    }
    // the path exists!
    else {
      System.out.println("There is a path from " + source + " to " + destination + ".");
      // output the cost of this path
      System.out.printf("The shortest path has a cost %.2f. Here it is:\n\n", shortest[destination]);

      // print the nodes in the path and their corresponding weights
      for(int i = 0; i < path.size() - 1; i++) {
        System.out.print(path.get(i) + " -> " + path.get(i + 1));

        // now find the corresponding edge so can find its weight
        double weight = getCorrespondingWeight(path.get(i), path.get(i + 1));
        System.out.println(" [" + weight + "]");
      }
    }
  } // printPath()

  // returns the weight of the edge that has s and d as its 'to' and 'from' vertices
  private double getCorrespondingWeight(int s, int d) {
    ArrayList<Edge> adj = getAdj(s);

    for(Edge e : adj) {
      if(e.to() == d) {
        return e.weight();
      }
    }

    // return a negative weight it no edge matches. This should never happen though
    // because before getting here the program checks that the path exists.
    return -1.0;
  } // getCorrespondingWeight()

  public static void main(String[] args) {
    //create a new graph g with the tinyEWD.txt url as argument.
    String url = "https://cs.brynmawr.edu/Courses/cs330/spring2020/tinyEWD.txt";
    Graph g = new Graph(url);

    //print out the graph, g (using the print methods) (JAVA) System.out.println(g)
    System.out.println();
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
