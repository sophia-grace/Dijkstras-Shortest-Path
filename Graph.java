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
  private PriorityQueue<Integer> frontier;

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

  // compute all shortest paths from s
  public void dijkstra(int s) {

      // initialize shortest and pred
      // and frontier
      shortest = new double[V];
      pred = new int[V];

      // set the values for pred and shortest
      for(int v = 0; v < V; v++) {
       shortest[v] = Double.POSITIVE_INFINITY;
        pred[v] = -1;
      }

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

      // set the pred and shortest for this vertex
      shortest[s] = 0;
      pred[s] = -1;

      for(int w = 0; w < V; w++) {
        frontier.add(w);
      }

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

  public void relax(int u, Edge v) {
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
      System.out.println("Invalid source vertex! Try running djikstra() with " + s + " and try again.");
      return null;
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
    if(path.get(0) != source || path.get(path.size() - 1) != destination) {
      System.out.println("The path does not exist.");
    }
    else {
      System.out.println("There is a path from " + source + " to " + destination + ".");
      System.out.printf("The shortest path has a cost %.2f. Here it is:\n\n", cost(path));
      for(int i = 0; i < path.size() - 1; i++) {
        System.out.print(path.get(i) + " -> " + path.get(i + 1));

        // now find the corresponding edge so can find its weight
        double weight = getCorrespondingWeight(path.get(i), path.get(i + 1));
        System.out.println(" [" + weight + "]");
      }
    }
  }

  // returns the edge that has s and d as its 'to' and 'from' vertices
  public double getCorrespondingWeight(int s, int d) {
    ArrayList<Edge> adj = getAdj(s);

    for(Edge e : adj) {
      if(e.to() == d) {
        return e.weight();
      }
    }

    // return a negative weight it no edge matches. This should never happen though
    // because before getting here the program checks that the path exists.
    return -1.0;
  }

  public double cost(ArrayList<Integer> path) {
    double cost = 0.0;

    for(int i = 0; i < path.size() - 1; i++) {
      ArrayList<Edge> adj = getAdj(path.get(i));

      for(Edge e : adj) {
        if(e.to() == path.get(i + 1)) {
          cost += e.weight();
        }
      }
    }

    return cost;
  }

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
