package myProject.dataEskull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class exstrakulikuler {

    public static void main(String[] args)throws IOException {

        Scanner inputUser = new Scanner(System.in);
        String pilihanUser;
        boolean ulang = true;

        while (ulang){

        System.out.println("1.\tlihat anggota eskul");
        System.out.println("2.\tcari anggota eskul");
        System.out.println("3.\ttambah anggota eskul");
        System.out.println("4.\tubah data anggota");
        System.out.println("5.\thapus anggota eskul");

        System.out.print("\n\nmasukan pilihan anda : ");
        pilihanUser = inputUser.next();

            switch (pilihanUser) {
                case "1":
                    System.out.println("\n=============================");
                    System.out.println("==== lihat anggota eskul ====");
                    System.out.println("=============================\n");
                    libEskul.tampilkanDaftarAnggota();

                    break;
                case "2":
                    System.out.println("\n=============================");
                    System.out.println("==== cari anggota eskul =====");
                    System.out.println("=============================\n");
                    libEskul.cariData();

                    break;
                case "3":
                    System.out.println("\n==============================");
                    System.out.println("==== tambah anggota eskul ====");
                    System.out.println("==============================\n");
                    libEskul.tambahAnggota();

                    break;
                case "4":
                    System.out.println("\n=============================");
                    System.out.println("===== ubah data anggota =====");
                    System.out.println("=============================\n");
                    libEskul.updateData();
                    break;
                case "5":
                    System.out.println("\n=============================");
                    System.out.println("==== hapus anggota eskul ====");
                    System.out.println("=============================\n");

                    break;
                default:
                    System.err.println("Input yang anda masukan tidak ditemukan.\nsilahkan pilih angka (1-6)");

            }

            ulang = utilEskul.getYesOrNo("\napakah anda ingin kembali ke menu awal ? ");

        }

    }

}














