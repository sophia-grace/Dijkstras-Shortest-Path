/*
Name: Sophia Trump
File: Edge.java
Description: Defines the Edge class, for use in Graph.java. An
edge contains the from vertex, the to vertex, and the weight between the vertices.
Main() tests this functionality by creating some edges and printing them out.
*/

public class Edge {
  // Attributes
  private int from; //vertex#
  private int to; //vertex#
  private double weight; //weight

  //Constructor
  Edge(int from, int to, double weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  } //Edge()

  //Accessors

  //returns the from vertex
  public int from() {
    return from;
  } //from()

  //returns the to vertex
  public int to() {
    return to;
  } //to()

  //returns the weight
  public double weight() {
    return weight;
  } //weight()

  //Print function

  //returns printable edge: "<from, to, weight>"
  public String toString() {
    return "<" + from + ", " + to + ", " + weight + ">";
  } //toString()

  public static void main(String[] args) {
    System.out.println("\nCreating some edges...");

    //create some edges
    Edge e1 = new Edge(1, 13, 0.5);
    Edge e2 = new Edge(5, 12321, 1.32321);
    Edge e3 = new Edge(12404, 83, 0.0002);

    //print them
    System.out.println("\nEdges created. Here are the Edges:");
    System.out.println(e1);
    System.out.println(e2);
    System.out.println(e3);

    //test the accessors
    System.out.println("\nThe 'from' vertices:");
    System.out.println("e1: " + e1.from());
    System.out.println("e2: " + e2.from());
    System.out.println("e3: " + e3.from());

    System.out.println("\nThe 'to' vertices:");
    System.out.println("e1: " + e1.to());
    System.out.println("e2: " + e2.to());
    System.out.println("e3: " + e3.to());

    System.out.println("\nThe 'weights':");
    System.out.println("e1: " + e1.weight());
    System.out.println("e2: " + e2.weight());
    System.out.println("e3: " + e3.weight());
    System.out.println();
  }
} //Edge
