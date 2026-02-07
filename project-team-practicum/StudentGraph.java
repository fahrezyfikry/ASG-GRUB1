import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class StudentGraph {
  private HashMap<String, LinkedList<String>> adjList = new HashMap<>();

  /**
   * Add student as a vertex in the graph
   * @param nim
   * @return
   */
  public boolean addStudent(String nim) {
    // Keep graph aligned with student data by ensuring each student exists as a vertex.
    if (adjList.containsKey(nim)) {
      return false;
    }

    adjList.put(nim, new LinkedList<>());
    return true;
  }

  /**
   * Search for a student in the graph
   * @param nim
   * @return
   */
  public boolean searchStudent(String nim) {
    // BFS checks reachability by exploring neighbors level by level.
    // It's a simple fit for unweighted reachability without extra weight handling.
    if (adjList.isEmpty()) {
      return false;
    }

    HashMap<String, Boolean> visited = new HashMap<>();
    Queue<String> queue = new LinkedList<>();

    String start = adjList.keySet().iterator().next(); // start from any vertex
    queue.add(start); // enqueue start
    visited.put(start, true); // mark start as visited

    while (!queue.isEmpty()) {
      String current = queue.poll(); // dequeue
      if (current.equals(nim)) {
        return true;
      }

      for (String neighbor : adjList.get(current)) {
        if (!visited.containsKey(neighbor)) {
          visited.put(neighbor, true); // mark neighbor as visited
          queue.add(neighbor); // enqueue neighbor
        }
      }
    }

    return false;
  }

  /**
   * Add relation between two students
   * @param nim1
   * @param nim2
   * @return
   */
  public boolean addRelation(String nim1, String nim2) {
    // Avoid self-relations since they do not add meaningful collaboration info.
    if (nim1.equals(nim2)) {
      return false;
    }

    if (!adjList.containsKey(nim1) || !adjList.containsKey(nim2)) {
      return false; // one or both vertices do not exist
    }

    LinkedList<String> list1 = adjList.get(nim1);
    LinkedList<String> list2 = adjList.get(nim2);

    if (!list1.contains(nim2)) {
      list1.add(nim2); // add edge from nim1 to nim2
    }

    if (!list2.contains(nim1)) {
      list2.add(nim1); // add edge from nim2 to nim1
    }

    return true;
  }

  /**
   * Remove relation between two students
   * @param nim1
   * @param nim2
   * @return
   */
  public boolean removeRelation(String nim1, String nim2) {
    if (!adjList.containsKey(nim1) || !adjList.containsKey(nim2)) {
      return false;
    }

    LinkedList<String> list1 = adjList.get(nim1); // get adjacency list for nim1
    LinkedList<String> list2 = adjList.get(nim2); // get adjacency list for nim2

    boolean removed1 = list1.remove(nim2); // remove nim2 from nim1's list
    boolean removed2 = list2.remove(nim1); // remove nim1 from nim2's list

    return removed1 || removed2;
  }

  /**
   * Delete student from the graph
   * @param nim
   * @return
   */
  public boolean deleteStudent(String nim) {
    if (!adjList.containsKey(nim)) {
      return false; // vertex does not exist
    }

    // Clean up edges so other vertices stay consistent after deletion.
    Set<String> keys = adjList.keySet();
    for (String key : keys) {
      adjList.get(key).remove(nim);
    }

    adjList.remove(nim); // remove the vertex
    return true;
  }

  /**
   * Display connections of a student
   * @param nim
   */
  public void displayConnections(String nim) {
    if (!adjList.containsKey(nim)) { // vertex does not exist
      System.out.println("Mahasiswa tidak ditemukan di graph.");
      return;
    }

    LinkedList<String> connections = adjList.get(nim);
    if (connections.isEmpty()) { // no connections
      System.out.println("Tidak ada relasi untuk NIM " + nim + ".");
      return;
    }

    System.out.println("Relasi mahasiswa untuk NIM " + nim + ":");
    for (String connectedNim : connections) { // display each connection
      System.out.println("- " + connectedNim);
    }
  }

  /** 
   * Display all students and their connections
   */
  public void displayAll() {
    if (adjList.isEmpty()) {
      System.out.println("Graph masih kosong.");
      return;
    }

    for (String nim : adjList.keySet()) {
      System.out.println("NIM " + nim + " terhubung dengan: " + adjList.get(nim));
    }
  }
}
