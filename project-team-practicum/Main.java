import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    StudentHashTable data = new StudentHashTable();

    while (true) {
      System.out.println("\n=== Sistem Data Mahasiswa ===");
      System.out.println("1. Tambah Mahasiswa");
      System.out.println("2. Cari Mahasiswa");
      System.out.println("3. Keluar");
      System.out.print("Pilih menu: ");

      int pilih = input.nextInt();
      input.nextLine();

      switch (pilih) {
          case 1:
            System.out.print("Masukkan NIM: ");
            String nim = input.nextLine();

            System.out.print("Masukkan Nama: ");
            String nama = input.nextLine();

            System.out.print("Masukkan IPK: ");
            double ipk = input.nextDouble();

            data.insert(nim, nama, ipk);
            break;

          case 2:
            System.out.print("Masukkan NIM yang dicari: ");
            String cariNim = input.nextLine();
            data.search(cariNim);
            break;

          case 3:
            System.out.println("Program selesai.");
            return;

          default:
            System.out.println("Menu tidak valid!");
      }
    }
  }
}
