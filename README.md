# College-Java-Semester-1

ASSIGNMENT DESCRIPTION & SCHEDULE
A Four-Square Cipher

You are required to develop a Java application that is capable of encrypting and decrypting a
text file or URL using a four-square cipher. The four-square cipher was invented by the French
cryptographer Felix Delastelle (1840-1902) and uses four 5x5 matrices arranged in a square to
encrypt pairs of characters in a message. Each of the 5x5 matrices contains 25 letters (the
relatively rare letter j is merged with the letter i). The upper-left and lower-right quadrants
(shown in green) are plaintext squares containing a standard alphabet. The upper-right and
lower-left quadrants (shown in blue) are the ciphertext squares contain a mixed random
alphabetic sequence. The four-square cipher is a polygraphic substitution cipher that uses the
same keyword to encrypt plain-text and decrypt cipher-text, i.e. the key is symmetric.

                    A B C D E   Z G P T F
                    F G H I K   O I H M U
                    L M N O P   W D R C N
                    Q R S T U   Y K E Q A
                    V W X Y Z   X V S B L

                    M F N B D   A B C D E
                    C R H S A   F G H I K
                    X Y O G V   L M N O P
                    I T U E W   Q R S T U
                    L Q Z K P   V W X Y Z

The encryption key is used to generate a unique sequence of letters that can be used to populate
the two ciphertext squares. The key can be randomly generated, specified with one keyword
or specified using two different keywords (dropping duplicate letters). Any remaining spaces,
i.e. a key length < 25 characters, should be filled with the remaining letters of the alphabet in
order.

Minimum Requirements
You are required to develop a Java application that can parse, encrypt and decrypt the
contents of a text file (small or very large…) or URL using the four-square cipher.

• The application should contain a command-line menu that prompts the user to enter a
keyword(s) and a file or URL. It should then parse the given resource line-by-line,
encrypt / decrypt the text and then append to an output file.

• You must use a 2D array structure to implement the four-square cipher. Do not use
any of the data structures in the Java Collections Framework. The knowledge you
will gain from mastering array structures will prove invaluable for the programming
modules in the next semester.

The objective of the assignment is to reinforce the programming concepts that we have covered
since the start of the semester. In addition to the methods above, you are free to add any
additional functionality you want. The application of good programming practice and objectoriented
techniques will be rewarded.

