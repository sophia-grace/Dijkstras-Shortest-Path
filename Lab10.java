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
        String input = getInput(prompt1);

        if(input.equals("quit")) {
          System.out.println();
          break;
        }

        try {
          int source = Integer.parseInt(input);
          String prompt2 = "Enter the destination vertex: ";
          int destination = Integer.parseInt(getInput(prompt2));
          g.dijkstra(source);
          ArrayList<Integer> path = g.getPath(source, destination);
          // print the path
          g.printPath(path, source, destination);
        }
        catch(Exception NumberFormatException) {
          System.out.println("Invalid input.\n");
        }
      }

      // show example that answers the question of when we try to find
      // a path from a start vertex that was not computed with dijkstra
      // this assumes the user only input start vertex of 0, as posed in the handout
      g.getPath(4, 6);

    }
  }
}
