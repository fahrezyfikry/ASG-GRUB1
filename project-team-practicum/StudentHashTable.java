import java.util.HashMap;

public class StudentHashTable {
    HashMap<String, Mahasiswa> table = new HashMap<>();

    /**
     * Insert new student into the hash table
     * @param nim
     * @param nama
     * @param ipk
     * @return
     */
    public boolean insert(String nim, String nama, double ipk) {
      // Reject duplicates to keep NIM as a reliable unique key.
      if (table.containsKey(nim)) {
        return false;
      }

      Mahasiswa mhs = new Mahasiswa(nim, nama, ipk);
      table.put(nim, mhs);
      return true;
    }

    /**
     * Search for a student by NIM
     * @param nim
     */
    public void search(String nim) {
      Mahasiswa mhs = table.get(nim); // attempt to retrieve student

      if (mhs != null) { // student found
        System.out.println("Mahasiswa ditemukan:");
        mhs.display();
      } else { // student not found
        System.out.println("Mahasiswa tidak ditemukan.");
      }
    }

    /**
     * Get Mahasiswa object by NIM
     * @param nim
     * @return
     */
    public Mahasiswa get(String nim) {
      return table.get(nim);
    }

    /**
     * Delete student by NIM
     * @param nim
     * @return
     */
    public boolean delete(String nim) {
      // Guard early so callers can react with a clear message.
      if (!table.containsKey(nim)) {
        return false;
      }

      table.remove(nim);
      return true;
    }
}
