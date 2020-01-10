package ie.gmit.hdip;

import java.util.Scanner;

public class FourSquareCipher {

    final private String ALPHABET = "ABCDEFGHIJKLMNOPRSTUVWXYZ"; // minus q
    final int SIZE = 10;
    private char[][] matrix = new char[SIZE][SIZE];

    public FourSquareCipher(){

    }

    public void mainMenu(){

        Scanner scan = new Scanner(System.in);

        System.out.println("************************************");
        System.out.println("*        Four Square Cipher        *");
        System.out.println("************************************");

        System.out.println("Enter the message you want to encrypt/decrypt(no special characters)");
        System.out.print("> ");
        String message = scan.nextLine();

        System.out.println("Enter 1 to encrypt.");
        System.out.println("Enter 2 to decrypt.");
        System.out.print("> ");
        int mode = scan.nextInt();

        System.out.println("Enter the key with no spaces.");
        System.out.println("The key must be more than 25 characters long and no numbers.");
        System.out.print("> ");
        String key = scan.nextLine();
        

        scan.close();

        generateMatrix(key);
        //printMatrix(); for debugging

        String output = "";
        if(mode == 1){
            output = encodeMessage(message);
        }
        else if (mode == 2){
            output = decodeMessage(message);
        }
        else{
            System.out.println("Error with mode selection.");
        }

        System.out.println("Original message: " + message);
        System.out.println("New message: " + output.replace(" ", ""));

        //String output = encodeMessage("Hello Worlds");
        //System.out.println("Hello Worlds\n" + output);
        //String decrypted = decodeMessage(output);
        //System.out.println(decrypted);
    }

    public void generateMatrix(String keyword){
        String key = stripDuplicateLetters(keyword);
        key = checkMessageIsEvenLength(key);
        String keywordA = key.substring(0, key.length()/2);
        String keywordB = key.substring(key.length()/2);

        
        
        generateTopLeft();
        generateTopRight(keywordA);
        generateBottomLeft(keywordB);
        generateBottomRight();
    }

    private String checkMessageIsEvenLength(String word){
        word = word.replace(" ", "").replace("'", "");
        if(word.length()%2 == 1){
            return word.toUpperCase() + "X";
        }

        return word.toUpperCase();
    }

    private String stripDuplicateLetters(String word){
        word = word.replace(" ", "");
        StringBuilder sb = new StringBuilder();
        char[] letters = word.toCharArray();

        for (char c : letters){
            if(!sb.toString().contains(Character.toString(c))){
                sb.append(c);
            }
        }
        return sb.toString();
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
        plaintext = checkMessageIsEvenLength(plaintext);
        String[] bigramsArray = new String[plaintext.length() / 2];
        int index = 0;

        for(int i=0; i<plaintext.length(); i+=2){
            bigramsArray[index] = plaintext.substring(i, i+2);
            index++;
        }
        return bigramsArray;
    }

    private String encodeMessage(String plaintext){
        String message = checkMessageIsEvenLength(plaintext);
        //message = replaceQwithX(message);
        String[] bigrams = bigrams(message);
        StringBuilder encrypted = new StringBuilder();

        int x1=0, y1=0, x2=0, y2=0;

        for(String s : bigrams){
            //First letter
            for(int i=0; i<(SIZE/2); i++){
                for(int j=0; j<(SIZE/2); j++) {
                    if (s.charAt(0) == matrix[i][j]){  //s.substring(0, 1).equals(matrix[i][j])){
                        x1 = i;
                        y1 = j;
                        break;
                    }
                }
            }

            //Second letter
            for(int i=(SIZE/2); i<SIZE; i++){
                for(int j=(SIZE/2); j<SIZE; j++) {
                    if (s.charAt(1) == matrix[i][j]){//s.substring(1, 2).equals(matrix[i][j])){
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

        //split each bigram into chars
        //find location of first letter in the top left
        //find location of second letter in the bottom right
        //
        //find first encrypted letter by first letter[row] and second letter[col]
        //find second encrypted letter by first letter[col] and second letter[row]
        //top right + bottom left
        return encrypted.toString();
    }

    private String decodeMessage(String encrypted){
        String message = checkMessageIsEvenLength(encrypted);
        //message = replaceQwithX(message);
        String[] bigrams = bigrams(message);
        StringBuilder decrypted = new StringBuilder();

        int x1=0, y1=0, x2=0, y2=0;

        for(String s : bigrams){
            //First letter
            for(int i=0; i<(SIZE/2); i++){
                for(int j=(SIZE/2); j<SIZE; j++) {
                    if (s.charAt(0) == matrix[i][j]){ //s.substring(0, 1).equals(matrix[i][j])){
                        x1 = i;
                        y1 = j;
                        break;
                    }
                }
            }

            //Second letter
            for(int i=(SIZE/2); i<SIZE; i++){
                for(int j=0; j<(SIZE/2); j++) {
                    if (s.charAt(1) == matrix[i][j]){   //s.substring(1, 2).  (matrix[i][j])){
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
        return decrypted.toString();
    }

    public void printMatrix(){

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
}
