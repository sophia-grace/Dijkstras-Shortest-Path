/*
Name: Sophia Trump
File: Lab10.java
Description: Creates a graph and uses dijkstra's shortest path algorithm to find
the shortest path between two vertices, input by the user. It requires as
a command line argument the url for the data to build the graph. It outputs
the shortest path and its cost. EXTRA -- If the user inputs a start
vertex that was not used in the most recent call to dijkstra, it throws
an exception from the getPath() function in Graph.java.
*/

import java.util.*;
import java.io.*;

public class Lab10 {

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

  public static void main(String [] args) {
    // check that the number of command line args is correct
    if(args.length == 0) {
      System.out.println("Missing url for the data.");
    }
    else {
      // let g = Graph(<url string of graph data file>)
      String url = args[0];
      Graph g = new Graph(url);

      // get user input
      while(true) {
        System.out.println();

        String prompt1 = "Enter the source vertex: ";
        String input = getInput(prompt1);

        // check if the user is done inputting vertices
        if(input.equals("quit")) {
          System.out.println();
          break;
        }

        try {
          // get the source vertex as an int
          int source = Integer.parseInt(input);

          // get the destination vertex as an int
          String prompt2 = "Enter the destination vertex: ";
          int destination = Integer.parseInt(getInput(prompt2));

          // set up the shortest[] and pred[] arrays
          g.dijkstra(source);

          // get the path
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
      g.dijkstra(0);
      g.getPath(4, 6);
    }
  } // main()
} // Lab10
