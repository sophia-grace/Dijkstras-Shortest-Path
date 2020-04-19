import java.util.*;
import java.io.*;

public class Lab10 {

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


  public static void main(String [] args) {
    // let g = Graph(<url string of graph data file>)
    if(args.length == 0) {
      System.out.println("Missing url for the data.");
    }
    else {
      String url = args[0];
      Graph g = new Graph(url);


      while(true) {
        System.out.println();
        String prompt1 = "Enter the source vertex: ";
        int source = Integer.parseInt(getInput(prompt1));
        String prompt2 = "Enter the destination vertex: ";
        int destination = Integer.parseInt(getInput(prompt2));

        g.dijkstra(source);
        ArrayList<Integer> path = g.getPath(source, destination);
        // print the path
        g.printPath(path, source, destination);
      }
    }
  }
}
