package myProject.dataEskull;

import com.CRUD.utility;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class libEskul {

    protected static void updateData() throws IOException{
        //memanggil file database
        File database = new File("database.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferInput = new BufferedReader(fileInput);

        //membuat file database baru
        File secondDatabaseEskul = new File("secondDatabaseEskul ");
        FileWriter fileOutput = new FileWriter(secondDatabaseEskul);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

        System.out.println("data anggota eskul : ");
        tampilkanDaftarAnggota();

        boolean numberNotExist = true;
        boolean isFound = false;

        while (numberNotExist) {
            //memanggil file database
            database = new File("database.txt");
            fileInput = new FileReader(database);
            bufferInput = new BufferedReader(fileInput);

            //membuat file database baru
            secondDatabaseEskul = new File("secondDatabaseEskul ");
            fileOutput = new FileWriter(secondDatabaseEskul);
            bufferOutput = new BufferedWriter(fileOutput);

            //mengambil inputan yang akan d update
            Scanner userOption = new Scanner(System.in);
            System.out.print("masukan nomor data yang akan d update : ");
            int numUpdate = userOption.nextInt();

            String data = bufferInput.readLine();

            int entryCounts = 0;

            while (data != null ){
                entryCounts++;

                StringTokenizer st = new StringTokenizer(data, ",");

                if (numUpdate == entryCounts){
                    System.out.println("data yang akan anda update adalah : ");
                    System.out.println("-------------------------------------");
                    System.out.println("nama                    : " + st.nextToken());
                    System.out.println("kelas & kejuruan        : " + st.nextToken());
                    System.out.println("divisi                  : " + st.nextToken());
                    System.out.println("jabatan                 : " + st.nextToken());

                    String[] fielData = {"nama", "kelas & kejuruan", "divisi", "jabatan"};
                    String[] tempData = new String[4];

                    //refresh token
                    st = new StringTokenizer(data, ",");
                    String originalData = st.nextToken();

                    for (int i = 0; i < fielData.length; i++) {
                        boolean isUpdate = utilEskul.getYesOrNo("apakah anda ingin merubah nama " + fielData[i]);

                        originalData = st.nextToken();
                        if (isUpdate) {
                            userOption = new Scanner(System.in);
                            System.out.print("\nmasukan " + fielData[i] + " baru : ");
                            tempData[i] = userOption.nextLine();
                        }
                    }
                }
            }

        }


    }

    protected static void tampilkanDaftarAnggota() throws IOException {
        FileReader fileInput;
        BufferedReader bufferInput;

        try{
            fileInput = new FileReader("databaseEskul.txt");
            bufferInput = new BufferedReader(fileInput);
        }catch(Exception e){
            System.err.println("file database tidak ditemukan");
            return;
        }

        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.println("|no|            nama             |   kelas & kejuruan   |       divisi     |      jabatan    |");
        System.out.println("----------------------------------------------------------------------------------------------");

        String data = bufferInput.readLine();
        long nomorAnggota = 0;

        while (data != null){

            nomorAnggota++;
            StringTokenizer stringToken = new StringTokenizer(data, ",");


            System.out.printf("|%1d.",nomorAnggota);
            System.out.printf("|\t%-25s", stringToken.nextToken());
            System.out.printf("|\t%-20s", stringToken.nextToken());
            System.out.printf("|\t%-15s", stringToken.nextToken());
            System.out.printf("|\t%-13s|", stringToken.nextToken());
            System.out.print("\n");

            data = bufferInput.readLine();

        }

        System.out.println("----------------------------------------------------------------------------------------------");

    }

    protected static void cariData()throws IOException{
        try {
            File file = new File("databaseEskul.txt");
        }catch(Exception E){
            System.err.println("database tidak ditemukan");
            System.out.println("slahkan buat database terlebih dahulu");
            return;
        }

        Scanner inputUser = new Scanner(System.in);
        System.out.print("masukan kata kunci untuk mencari anggota eskul : ");
        String cariData = inputUser.nextLine();
        String[] keywords = cariData.split("\\s+");

        utilEskul.cekBUkuDiDatabase(keywords,true);
    }

    protected static void tambahAnggota()throws IOException{

        boolean isTambahLagi = true;

        while(isTambahLagi) {
            FileWriter fileOutput = new FileWriter("databaseEskul.txt", true);
            BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

            Scanner inputUser = new Scanner(System.in);
            String nama, kelas, divisi, jabatan;

            System.out.print("nama anggota       : ");
            nama = inputUser.nextLine();
            System.out.print("kelas dan kejuruan : ");
            kelas = inputUser.nextLine();
            System.out.print("divisi             : ");
            divisi = inputUser.nextLine();
            System.out.print("jabatan            : ");
            jabatan = inputUser.nextLine();

            //mengecek ada tidaknya buku d database
            String[] keywords = {nama + "," + kelas + "," + divisi + "," + jabatan};
            System.out.println(Arrays.toString(keywords));

            boolean isExist = utilEskul.cekBUkuDiDatabase(keywords, false);

            if (!isExist) {
                System.out.println("\ndata yang anda masukan adalah :");
                System.out.println("----------------------------------");
                System.out.println("nama angggota      : " + nama);
                System.out.println("kelas dan kejuruan : " + kelas);
                System.out.println("divisi             : " + divisi);
                System.out.println("jabatan            : " + jabatan);

                boolean isTambah = utilEskul.getYesOrNo("apakah anda yakin mnambahkan data tersebut ? ");

                if (isTambah) {
                    bufferOutput.write(nama + "," + kelas + "," + divisi + "," + jabatan);
                    bufferOutput.newLine();
                    bufferOutput.flush();
                    System.out.println("\ndata berhasil ditamahkan");
                } else {
                    System.out.println("\ndata tidak tersimpan");
                }
            } else {
                System.out.println("data yang anda masukan sudah terdaftar dengan data berikut :");
                utilEskul.cekBUkuDiDatabase(keywords, true);
            }
            bufferOutput.close();

            isTambahLagi = utilEskul.getYesOrNo("apakah anda ingin menambah data anggota eskul lagi ? ");
        }
    }
}
