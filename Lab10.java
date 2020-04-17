public class Lab10 {
  public static void main(String [] args) {
    // let g = Graph(<url string of graph data file>)
    String url = "https://cs.brynmawr.edu/Courses/cs330/spring2020/tinyEWD.txt";
    Graph g = new Graph(url);

    // Computes all shortest paths from vertex, 0
    g.dijkstra(0);
    path = g.getPath(0, 1)

    // print path
    path = g.getPath(0, 4)
    // print path

    // SAFE GUARD AGAINST INVALID GET PATH START ARGUMENT!! SHOULD ALWAYS BE 0!
  }
}
