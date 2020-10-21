package myProject.dataEskull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class utilEskul {
    protected  static  boolean cekBUkuDiDatabase(String[] keywords, boolean isDisplay)throws IOException {
        FileReader fileInput = new FileReader("databaseEskul.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        String data = bufferInput.readLine();
        boolean isExist = false;
        int nomorAnggota = 0;
        if (isDisplay) {
            System.out.println("----------------------------------------------------------------------------------------------");
            System.out.println("|no|            nama             |   kelas & kejuruan   |       divisi     |      jabatan    |");
            System.out.println("----------------------------------------------------------------------------------------------");
        }
        while (data != null) {
            // cek keywords didalam baris
            isExist = true;

            for (String keyword : keywords) {
                isExist = isExist && data.toLowerCase().contains(keyword.toLowerCase());
            }
        // jika keywordsnya cocok maka tampilkan

            if(isExist){
                if(isDisplay) {
                    nomorAnggota++;
                    StringTokenizer stringToken = new StringTokenizer(data, ",");

                    System.out.printf("|%1d.",nomorAnggota);
                    System.out.printf("|\t%-25s", stringToken.nextToken());
                    System.out.printf("|\t%-20s", stringToken.nextToken());
                    System.out.printf("|\t%-15s", stringToken.nextToken());
                    System.out.printf("|\t%-13s|", stringToken.nextToken());
                    System.out.print("\n");

                }else{
                    break;
                }

            }

            data = bufferInput.readLine();
        }
        if(isDisplay) {
            System.out.println("----------------------------------------------------------------------------------------------");
        }
        return isExist;
    }

    protected static boolean getYesOrNo (String message){
        Scanner inputUser = new Scanner(System.in);
        System.out.print(message + "(y/n) ");
        String pilihanUser = inputUser.next();

        while (!pilihanUser.equalsIgnoreCase("y") && !pilihanUser.equalsIgnoreCase("n")) {
            System.err.println("Pilihan anda bukan y atau n");
            System.out.println("Ingin kembali ke menu awal" + message);
            pilihanUser = inputUser.next();
        }

        return pilihanUser.equalsIgnoreCase("y");
    }

    static void clearScreen(){
        try {
            new ProcessBuilder("cmd","c/","cls" ).inheritIO().start().waitFor();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
