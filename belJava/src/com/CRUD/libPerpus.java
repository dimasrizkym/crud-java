package com.CRUD;


import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class operasi {
    protected static void updateData()throws IOException {
        //memanggil file database asli
        File database = new File("database.txt");
        FileReader fileInput= new FileReader (database);
        BufferedReader bufferInput = new BufferedReader(fileInput);

        //membuat file database baru
        File tempDB = new File ("tempDB.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

        //menampilkan data buku
        System.out.println("list buku : ");
        tampilkanData();

        boolean numberNotExist = true;
        boolean isFound = false;

        while (numberNotExist) {
            //memanggil file database asli
            database = new File("database.txt");
            fileInput = new FileReader(database);
            bufferInput = new BufferedReader(fileInput);

            //membuat file database baru
            tempDB = new File("tempDB.txt");
            fileOutput = new FileWriter(tempDB);
            bufferOutput = new BufferedWriter(fileOutput);


            //mengambil input buku yang akan d update
            Scanner terminalInput = new Scanner(System.in);
            System.out.print("\nmasukan nomor buku yang akan diupdate : ");
            int numUpdate = terminalInput.nextInt();

            String data = bufferInput.readLine();

            int entryCounts = 0;

            while (data != null) {
                entryCounts++;

                StringTokenizer st = new StringTokenizer(data, ",");

                if (numUpdate == entryCounts) {
                    System.out.println("\ndata yang akan anda update adalah : ");
                    System.out.println("------------------------------------");
                    System.out.println("referensi      : " + st.nextToken());
                    System.out.println("tahun terbit   : " + st.nextToken());
                    System.out.println("penulis        : " + st.nextToken());
                    System.out.println("penerbit       : " + st.nextToken());
                    System.out.println("judul          : " + st.nextToken());

                    //mengambil input dari user
                    String[] fielData = {"tahun", "penulis", "penerbit", "judul"};
                    String[] tempData = new String[4];

                    //refresh token
                    st = new StringTokenizer(data, ",");
                    String originalData = st.nextToken();

                    for (int i = 0; i < fielData.length; i++) {
                        boolean isUpdate = utility.getYesorNo("apakah anda ingin merubah nama " + fielData[i]);

                        originalData = st.nextToken();
                        if (isUpdate) {

                            if (fielData[i].equalsIgnoreCase("tahun")) {
                                System.out.print("masukan tahun terbit buku,format[YYYY] : ");
                                tempData[i] = utility.ambilTahun();
                            } else {
                                terminalInput = new Scanner(System.in);
                                System.out.print("\nmasukan " + fielData[i] + " baru : ");
                                tempData[i] = terminalInput.nextLine();
                            }
                        } else {
                            tempData[i] = originalData;
                        }
                    }

                    //menampilkan data yang telah di update
                    st = new StringTokenizer(data, ",");
                    st.nextToken();
                    System.out.println("\ndata ynag akan anda update adalah : ");
                    System.out.println("------------------------------------");
                    System.out.println("tahun terbit   : " + st.nextToken() + "-->" + tempData[0]);
                    System.out.println("penulis        : " + st.nextToken() + "-->" + tempData[1]);
                    System.out.println("penerbit       : " + st.nextToken() + "-->" + tempData[2]);
                    System.out.println("judul          : " + st.nextToken() + "-->" + tempData[3]);

                    boolean isUpdate = utility.getYesorNo("apakah anda ingin mengubah data tersebut ? ");
                    isFound = true;

                    if (isUpdate) {

                        boolean isExist = utility.cekBukuDiDatabase(tempData, false);

                        if (isExist) {
                            System.err.println("buku yang anda update telah ada di database\n");
                        } else {
                            //format data baru ke databse
                            String tahun = tempData[0];
                            String penulis = tempData[1];
                            String penerbit = tempData[2];
                            String judul = tempData[3];

                            //membuat primary key
                            long nomorEntry = utility.ambilEntryPerTahun(tahun, penulis);

                            String penulisTanpaSpasi = penulis.replaceAll("\\s", "");
                            String primarykey = penulisTanpaSpasi + "_" + tahun + "_" + nomorEntry;

                            //memindahkan data baru ke database
                            bufferOutput.write(primarykey + "," + tahun + "," + penulis + "," + penerbit + "," + judul);
                        }
                    } else {
                        bufferOutput.write(data);
                    }

                } else {
                    bufferOutput.write(data);
                }
                bufferOutput.newLine();
                data = bufferInput.readLine();
            }

            if (!isFound) {
                System.err.println("nomor yang inngin anda update tidak ada\npastikan masukan nomor yang benar");
            }
            numberNotExist = utility.getYesorNo("ingin mengubah data lagi ? ");
        }



        bufferOutput.flush();
        bufferOutput.close();
        fileOutput.close();
        bufferInput.close();
        fileInput.close();

        System.gc();

        java.nio.file.Files.delete(database.toPath());

        tempDB.renameTo(database);
    }

    protected static void deleteData() throws  IOException {
        //memanggil file database asli
        File database = new File("database.txt");
        FileReader fileInput= new FileReader (database);
        BufferedReader bufferInput = new BufferedReader(fileInput);

        //membuat file database baru
        File tempDB = new File ("tempDB.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

        boolean numNotExist = true;
        while(numNotExist) {
            database = new File("database.txt");
            fileInput = new FileReader(database);
            bufferInput = new BufferedReader(fileInput);

            //membuat file database baru
            tempDB = new File("tempDB.txt");
            fileOutput = new FileWriter(tempDB);
            bufferOutput = new BufferedWriter(fileOutput);

            //menampilkan data buku
            System.out.println("list buku : ");
            tampilkanData();

            //mengambil input buku yang akan d hapus
            Scanner terminalInput = new Scanner(System.in);
            System.out.print("\nmasukan nomor buku yang akan dihapus : ");
            long numDelet = terminalInput.nextInt();

            boolean entryNumber;
            boolean isFound = false;
            long entryCounts = 0;
            String data = bufferInput.readLine();

            while (data != null) {

                entryCounts++;

                boolean isDelet = false;

                StringTokenizer tokenizer = new StringTokenizer(data, ",");

                if (numDelet == entryCounts) {
                    System.out.println("\ndata yang akan anda hapus : ");
                    System.out.println("---------------------------");
                    System.out.println("referensi      : " + tokenizer.nextToken());
                    System.out.println("tahun terbit   : " + tokenizer.nextToken());
                    System.out.println("penulis        : " + tokenizer.nextToken());
                    System.out.println("penerbit       : " + tokenizer.nextToken());
                    System.out.println("judul          : " + tokenizer.nextToken());

                    isDelet = utility.getYesorNo("apakah anda ingin menghapus data tersebut ? ");
                    isFound = true;

                }

                if (isDelet) {
                    System.out.println("data berhasil dihapus");
                } else {
                    bufferOutput.write(data);
                    bufferOutput.newLine();
                }
                data = bufferInput.readLine();
            }

            if (isFound) {
                System.err.println("nomor yang anda masukan tidak ada dalam daftar");
            }

            numNotExist = utility.getYesorNo("apakah anda ingin menghapus data lagi ? ");
        }


        bufferOutput.flush();
        bufferOutput.close();
        fileOutput.close();
        bufferInput.close();
        fileInput.close();

        System.gc();

        java.nio.file.Files.delete(database.toPath());

        tempDB.renameTo(database);

    }

    protected static void tampilkanData() throws IOException{
        FileReader inputFile;
        BufferedReader bufferInput;

        try {
            inputFile = new FileReader("database.txt");
            bufferInput = new BufferedReader(inputFile);
        }catch (Exception e){
            System.err.println("file database tidak ditemukan");
            System.out.println("silahkan buat database terlebih dahulu");
            tambahData();
            return;
        }

        System.out.println("\n| No |\tTahun |\tPenulis                |\tPenerbit               |\tJudul Buku");
        System.out.println("----------------------------------------------------------------------------------------------------------");

        String data = bufferInput.readLine();
        int nomorData = 0;
        while(data != null) {
            nomorData++;

            StringTokenizer stringToken = new StringTokenizer(data, ",");

            stringToken.nextToken();
            System.out.printf("| %2d ", nomorData);
            System.out.printf("|\t%4s  ", stringToken.nextToken());
            System.out.printf("|\t%-20s   ", stringToken.nextToken());
            System.out.printf("|\t%-20s   ", stringToken.nextToken());
            System.out.printf("|\t%s   ", stringToken.nextToken());
            System.out.print("\n");

            data = bufferInput.readLine();
        }

        System.out.println("----------------------------------------------------------------------------------------------------------");

    }

    protected static void cariData() throws IOException{

        // membaca database ada atau tidak

        try {
            File file = new File("database.txt");
        } catch (Exception e){
            System.err.println("Database Tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahoeloe");
            return;
        }

        // kita ambil keyword dari user

        Scanner terminalInput = new Scanner(System.in);
        System.out.print("Masukan kata kunci untuk mencari buku: ");
        String cariString = terminalInput.nextLine();
        String[] keywords = cariString.split("\\s+");

        // kita cek keyword di database
        utility.cekBukuDiDatabase(keywords,true);

    }

    protected static void tambahData() throws IOException {

        boolean tambahBukulagi = true;

        while (tambahBukulagi) {
            FileWriter fileOutput = new FileWriter("database.txt", true);
            BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

            Scanner terminalInput = new Scanner(System.in);
            String penulis, judul, penerbit, tahun;

            System.out.print("masukan nama penulis                   : ");
            penulis = terminalInput.nextLine();
            System.out.print("masukan judul buku                     : ");
            judul = terminalInput.nextLine();
            System.out.print("masukan penerbit buku                  : ");
            penerbit = terminalInput.nextLine();
            System.out.print("masukan tahun terbit buku,format[YYYY] : ");
            tahun = utility.ambilTahun();

//      cek buku didatabase
            String[] keywords = {tahun + "," + penulis + "," + penerbit + "," + judul};
            System.out.println(Arrays.toString(keywords));

            boolean isExist = utility.cekBukuDiDatabase(keywords, false);

//      menulis buku didatabase
            if (!isExist) {
//          fiersabesari_2012_1,2012,fiersa besari,media kita,jejak langkah
                System.out.println(utility.ambilEntryPerTahun(penulis, tahun));
                long nomorEntry = utility.ambilEntryPerTahun(penulis, tahun) + 1;


                String penulisTanpaSpasi = penulis.replaceAll("\\s", "");
                String primarykey = penulisTanpaSpasi + "_" + tahun + "_" + nomorEntry;
                System.out.println("\ndata yang anda masukan adalah :");
                System.out.println("----------------------------------");
                System.out.println("primary key  : " + primarykey);
                System.out.println("tahun terbit : " + tahun);
                System.out.println("penulis      : " + penulis);
                System.out.println("penerbit     : " + penerbit);
                System.out.println("judul        : " + judul);


                boolean isTambah = utility.getYesorNo("apakah anda ingin menambah data tersebut ? ");

                if (isTambah) {
                    bufferOutput.write(primarykey + "," + tahun + "," + penulis + "," + penerbit + "," + judul);
                    bufferOutput.newLine();
                    bufferOutput.flush();
                } else {
                    System.out.println("\ndata tidak tersimpan");
                }

            } else {
                System.out.println("data yang anda masukan sudah terdaftar dengan data berikut :");
                utility.cekBukuDiDatabase(keywords, true);
            }

            bufferOutput.close();

            tambahBukulagi = utility.getYesorNo("apakah anda ingin mengubah data buku lagi ?");
        }

    }
}