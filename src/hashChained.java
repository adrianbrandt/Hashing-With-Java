import java.io.*;
import java.util.Scanner;

public class hashChained {
    /*
    Inner Class-
    Node Containing Data, Chained Together with Linked Lists
    */
    private class hashNode {
        // Data "String"
        String data;
        // Next Node in List
        hashNode next;

        // Constructor for List Nodes
        public hashNode(String S, hashNode hN) {
            data = S;
            next = hN;
        }
    }

    // Hash Lenght
    private static int hashLength;

    // Hashtable, Pointers to List
    private static hashNode[] hashTable;

    //Amount of Elements Stored in Tables
    private int n;

    //Amount of Collisions During Insertion
    private int Collisions;

    // Constructor
    public hashChained(int lengde) {
        hashLength = lengde;
        hashTable = new hashNode[lengde];
        n = 0;
        Collisions = 0;
    }

    // Returning Load Factor
    public float loadFactor() {
        return ((float) n) / hashLength;
    }

    // Returning Data Amount
    public int nData() {
        return n;
    }

    // Returning Collision Amount During Insertion
    public int nCollision() {
        return Collisions;
    }

    // Hash Function
    static int hash(String S) {
        int h = Math.abs(S.hashCode());
        return h % hashLength;
    }

    // Inserting Text String with Chaining
    void insert(String S) {
        // Calculate Hash Value
        int h = hash(S);

        //increases Number of Elements Stored
        n++;

        // Checking for Collision
        if (hashTable[h] != null)
            Collisions++;

        // Inserting New Node First in List
        hashTable[h] = new hashNode(S, hashTable[h]);
    }

    /*
    Searching for Text String in Hash Table.
    Returning True if String is Stored. Returning False Else.
     */

    static boolean search(String S) {
        // Gets List Where S Should Be in
        hashNode hN = hashTable[hash(S)];

        // Looks Trough Current List
        while (hN != null) {
            // Text String Found?
            if (hN.data.compareTo(S) == 0)
                return true;
            // Tries Next
            hN = hN.next;
        }
        // Could Not Find String. End of List
        return false;
    }

    //Removes the Node That Has Been Chosen by User
    void  remover(String S) {
        int h = hash(S);
        n++;
        if (hashTable[h] != null)
            hashTable[h] = null;
        program(S);
    }

    //Main program to search for and delete strings
    void program(String S) {
        Scanner uput = new Scanner(System.in);
        for (int i = 0; i < hashLength; i++) {
            if (hashChained.hashTable[i] != null)
                System.out.println("Index: "
                        + i + " \tWord: \"" + hashChained.hashTable[i].data + "\" \tHash: " + hashChained.hashTable[i].hashCode());
        }
        System.out.println("\nWhat would you like to do?\nOptions: Delete  \tAdd \tExit");
        String answer = uput.nextLine();

        switch (answer.toLowerCase()) {
            case "delete" -> {
                System.out.println("Enter Word to Remove");
                S = uput.nextLine();
                System.out.println("\n\n\n\n\n");
                if (hashChained.search(S)) {
                    System.out.println("\"" + S + "\"" + " Removed From Table\n");
                    remover(S);
                }
                if (!hashChained.search(S)) {
                    System.out.println("\"" + S + "\"" + " Not Found in Table\n");
                }
            }
            case "add" -> {
                System.out.println("Enter Word to Insert");
                S = uput.nextLine();
                insert(S);
                System.out.println("\n\n\n\n\n");
                System.out.println("\"" + S + "\"" + " Added to Table\n");
            }
            case "exit" -> System.exit(0);
            default -> {
                System.out.println("\n\n\n\n\n");
                System.out.println("\"" + answer + "\"" + " Was not Recognised\n");
            }
        }
        System.out.flush();
        program(S);
    }

        /*
        Simple Text Program:

        - Hash Length is given as input with arguments

        - Reading Text Strings for Entire Line from Standard Input and Stores Them in Hash Table

        - Outputs Some Statistics After Insertion
        */
        public static void main (String fileName, String[] argv) throws FileNotFoundException {
            // Hash Length Given as Arguments
            int hashLength = 0;
            File file = new File(fileName);
            Scanner input = new Scanner(file);
            try {
                if (argv.length != 1)
                    throw new IOException("Error: Hash Length Must be Given");
                hashLength = Integer.parseInt(argv[0]);
                if (hashLength < 1)
                    throw new IOException("Error: Hash Length Must be Larger Then 0");
            } catch (Exception e) {
                System.err.println(e);
                System.exit(1);
            }

            // Creates New HashTable
            hashChained hC = new hashChained(hashLength);

            // Reads Input and Performs Hashing on All Lines
            while (input.hasNext()) {
                hC.insert(input.nextLine());
            }

            // Writes Out HashLength, Number of Data Read, Number of Collisions and Load Factor.
            System.out.println("Hash Length  : " + hashLength);
            System.out.println("Elements   : " + hC.nData());
            System.out.printf("Load Factor : %5.3f\n", hC.loadFactor());
            System.out.println("Collisions : " + hC.nCollision() + "\n");

            String S = "A";
            hC.remover(S);
        }
    }
