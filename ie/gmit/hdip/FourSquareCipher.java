package ie.gmit.hdip;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;

public class FourSquareCipher {

    final private String ALPHABET = "ABCDEFGHIKLMNOPQRSTUVWXYZ"; // minus j
    final int SIZE = 10;
    private char[][] matrix = new char[SIZE][SIZE];

    public void mainMenu(){

        Scanner scan = new Scanner(System.in);

        
        System.out.println("************************************");
        System.out.println("*        Four Square Cipher        *");
        System.out.println("************************************");

        System.out.println("Enter a keyword with a length >= 50 characters");
        System.out.println("or two keywords with a length >= 25 characters");
        System.out.println("containing unique characters separated by a space.");
        System.out.println("Leave blank to autogenerate a keyword");
        System.out.print(">");
        String keyword = scan.nextLine();
        if(keyword.isEmpty()){
            keyword = autoGenerateKeyword();
        }else {
            keyword.replace(" ", "");
        }
        System.out.println("Keyword: " + keyword);

        System.out.println("Enter the name of the file you want encrypt/decrypt)");
        System.out.print("> ");
        String fileName = scan.nextLine();

        System.out.println("Enter 1 to encrypt.");
        System.out.println("Enter 2 to decrypt.");
        System.out.print("> ");
        int mode = scan.nextInt();

        scan.close();

        generateMatrix(keyword);
        //printMatrix(); //for debugging

        String output = "";
        if(mode == 1 || mode == 2){
            output = readFromFile(fileName, mode);
            writeToFile(output, fileName);
        } else{
            System.out.println("Error with mode selection.");
        }

        System.out.println("Original message: " + fileName);
        System.out.println("New message: " + output.replace(" ", ""));

    }

    private void generateMatrix(String keyword){
        String keywordA = keyword.substring(0, keyword.length()/2);
        String keywordB = keyword.substring(keyword.length()/2);

        generateTopLeft(); //plaintext
        generateTopRight(keywordA); //ciphertext
        generateBottomLeft(keywordB); //ciphertext
        generateBottomRight(); //plaintext

        // printMatrix();
    }

    private String checkIfEvenLength(String word){
        word = word.replace(" ", "").replace("'", "");
        if(word.length()%2 == 1){
            return word.toUpperCase() + "X";
        }

        return word.toUpperCase();
    }

    private void generateTopLeft(){
        char[] alpha = ALPHABET.toCharArray();
        int index = 0;

        for(int i=0; i<(SIZE/2); i++){
            for(int j=0; j<(SIZE/2); j++){
                matrix[i][j] = alpha[index];
                index++;
            }
        }
    }

    private void generateTopRight(String keyword1){
        StringBuilder topRight = new StringBuilder(keyword1.toUpperCase());

        for(char c : ALPHABET.toCharArray()){
            if(!topRight.toString().contains(Character.toString(c))){
                topRight.append(c);
            }
        }

        char[] alpha = topRight.toString().toCharArray();
        int index = 0;

        for(int i=0; i<(SIZE/2); i++){
            for(int j=(SIZE/2); j<SIZE; j++){
                matrix[i][j] = alpha[index];
                index++;
            }
        }

    }

    private void generateBottomLeft(String keyword2){
        StringBuilder bottomLeft = new StringBuilder(keyword2.toUpperCase());

        for(char c : ALPHABET.toCharArray()){
            if(!bottomLeft.toString().contains(Character.toString(c))){
                bottomLeft.append(c);
            }
        }

        char[] alpha = bottomLeft.toString().toCharArray();
        int index = 0;

        for(int i=(SIZE/2); i<SIZE; i++){
            for(int j=0; j<(SIZE/2); j++){
                matrix[i][j] = alpha[index];
                index++;
            }
        }
    }

    private void generateBottomRight(){
        char[] alpha = ALPHABET.toCharArray();
        int index = 0;

        for(int i=(SIZE/2); i<SIZE; i++){
            for(int j=(SIZE/2); j<SIZE; j++){
                matrix[i][j] = alpha[index];
                index++;
            }
        }
    }

    private String[] bigrams(String plaintext){
        plaintext = checkIfEvenLength(plaintext);
        String[] bigramsArray = new String[plaintext.length() / 2];
        int index = 0;

        for(int i=0; i<plaintext.length(); i+=2){
            bigramsArray[index] = plaintext.substring(i, i+2);
            index++;
        }
        return bigramsArray;
    }

    private String encodeMessage(String plaintext){
        String message = checkIfEvenLength(plaintext);
        String[] bigrams = bigrams(message);
        StringBuilder encrypted = new StringBuilder();

        int x1=0, y1=0, x2=0, y2=0;

        for(String s : bigrams){
            //First letter
            for(int i=0; i<(SIZE/2); i++){
                for(int j=0; j<(SIZE/2); j++) {
                    if (s.charAt(0) == matrix[i][j]){
                        x1 = i;
                        y1 = j;
                        break;
                    }
                }
            }

            //Second letter
            for(int i=(SIZE/2); i<SIZE; i++){
                for(int j=(SIZE/2); j<SIZE; j++) {
                    if (s.charAt(1) == matrix[i][j]){
                        x2 = i;
                        y2 = j;
                        break;
                    }
                }
            }

            encrypted.append(matrix[x1][y2]);
            encrypted.append(matrix[x2][y1]);
            encrypted.append(" ");
            x1=0; y1=0; x2=0; y2=0;
        }

        return encrypted.toString().replace(" ", "");
    }

    private String decodeMessage(String encrypted){
        String message = checkIfEvenLength(encrypted);
        String[] bigrams = bigrams(message);
        StringBuilder decrypted = new StringBuilder();

        int x1=0, y1=0, x2=0, y2=0;

        for(String s : bigrams){
            //First letter
            for(int i=0; i<(SIZE/2); i++){
                for(int j=(SIZE/2); j<SIZE; j++) {
                    if (s.charAt(0) == matrix[i][j]){ 
                        x1 = i;
                        y1 = j;
                        break;
                    }
                }
            }

            //Second letter
            for(int i=(SIZE/2); i<SIZE; i++){
                for(int j=0; j<(SIZE/2); j++) {
                    if (s.charAt(1) == matrix[i][j]){ 
                        x2 = i;
                        y2 = j;
                        break;
                    }
                }
            }

            decrypted.append(matrix[x1][y2]);
            decrypted.append(matrix[x2][y1]);
            decrypted.append(" ");
            x1=0; y1=0; x2=0; y2=0;
        }
        /*
        decrypt message
         */
        return decrypted.toString().replace(" ", "");
    }

    private void printMatrix(){

        StringBuilder sb = new StringBuilder();

        for(int i=0; i<SIZE; i++){
            for(int j=0; j<SIZE; j++){
                /*if(i == 4)
                    sb.append("\n");
                */
                // split right and left side of matrix for ease of seeing
                if(j==(SIZE/2))
                    sb.append("  ");

                sb.append(matrix[i][j]);
            }

            // split top and bottom side of matrix for ease of seeing
            if (i==(SIZE/2)-1)
                sb.append("\n");

            System.out.println(sb.toString());
            sb = new StringBuilder();
        }

    }

    private String autoGenerateKeyword(){
        Random rand = new Random();
        rand.setSeed(rand.nextInt(1000000));
        StringBuilder keyword = new StringBuilder();
    
        while (keyword.length() < ALPHABET.length()){
            int x = rand.nextInt(ALPHABET.length());
            char c = ALPHABET.charAt(x);
            if(!keyword.toString().contains("" + c)){
                keyword.append(c);
            }
        }
        return keyword.toString();
    }

    private String readFromFile(String fileName, int mode){
        File file = new File(fileName);
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line;
            if (mode == 1) { //encryption
                while ((line = br.readLine()) != null) {

                    String encrypted = encodeMessage(line);
                    sb.append(encrypted + "\n");
                }
            }else {
                while ((line = br.readLine()) != null) {

                    String decrypted = decodeMessage(line);
                    sb.append(decrypted + "\n");
                }
            }

            br.close();
            isr.close();
            fis.close();

        }catch(IOException ioe){
            System.out.println("Cannot find file: " + fileName);            
        }

        return sb.toString();
    }

    private void writeToFile(String encryptedText, String fileName){
        String outputFile = "AFTER=" + fileName;
        try{
            FileWriter fw = new FileWriter(outputFile);
            BufferedWriter writer = new BufferedWriter(fw);

            writer.write(encryptedText);
            
            writer.close();
            fw.close();
        } catch(IOException ioe) {
            System.out.println("Error with writing to file: " + outputFile);
        }
    }
}
