public class VertexShortestComparator implements Comparator<Edge>{
  public int compare(int v1, int v2) {
    if(v1 < v2) {
      return -1;
    }
    if(v1 > v2) {
      return 1;
    }
    return 0;
  }
}
