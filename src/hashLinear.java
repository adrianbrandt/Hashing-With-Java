import java.io.*;
import java.util.Scanner;

/*
Hashing of Strings Using Linear Probing
Simple and Limited Implementation:
- No Re Hashing When Table is Full
- Uses Only Insertion and Searching
*/
public class hashLinear {
    // Hash length
    private int hashLength;

    // Hash Table
    private String[] hashTable;

    // Elements to be Stored In Table
    private int n;

    // Number of Probes
    private int nProbes;

    // Constructor
    public hashLinear(int length) {
        hashLength = length;
        hashTable = new String[length];
        n = 0;
        nProbes = 0;
    }

    // Returnes Load Factor
    public float loadFactor() {
        return ((float) n)/ hashLength;
    }

    // Returnes Number of Data
    public int nData() {
        return n;
    }

    // Returnes Number of Probes
    public int nProbes() {
        return nProbes;
    }

    // Hash Function
    int hash(String S) {
        int h = Math.abs(S.hashCode());
        return h % hashLength;
    }

    // Inserting String With Linear Probing
    void insert(String S) {
        // Calculates Hash Value
        int h = hash(S);

        // Linear Probing
        int next = h;

        //Sets Waiter
        String wait = hashTable[next];
        hashTable[next] = S;

        while (wait != null) {
            // New Probe
            nProbes++;
            // Tries Next Possible
            next++;

            //Performs Switch
            S = wait;
            wait = hashTable[next];
            hashTable[next] = S;

            // Wrap-Around
            if (next >= hashLength){
                next = 0;
            }

            /*
            When We have Wraped Around and Reached First Hash Value, The Table is Full And We Are Stooping
            Normally We Would Double the Length for the HashTable and Done Re Hashing Here
             */
            if (next == h) {
                System.err.println("\nHashtable Full, Stopping...");
                System.exit(0);
            }
        }

        // Stores String on Found Index
        hashTable[next] = S;

        // Increasing Number of Elements Stored
        n++;
    }

    /*
     Searching for Strings Using Linear Probing
     Returning True if String is Stored
     Returning False if String is Not Stored
     Not Used in Current Program
     */
    boolean search(String S) {
        // Calculates Hash value
        int h = hash(S);

        // Linear Probing
        int neste = h;

        while (hashTable[neste] != null) {
            // String Found?
            if (hashTable[neste].compareTo(S) == 0)
                return true;

            // Tries Next Possible Value
            neste++;

            // Wrap-Around
            if (neste >= hashLength)
                neste = 0;

            /*
            We Have Wrapped Around Meaning the String Does Not Exist in Current Table
             */
            if (neste == h)
                return false;
        }

        // String Could Not Be Found, Reached Probe NULL
        return false;
    }
    /*
    Simple Program:
    - Hash Length is Given as argument
    - Reads Strings Line for Line From Input
    - Displays Some Statistics After Insertion
     */
    public static void main(String fileName,String[] argv) throws FileNotFoundException {
        // Hash Length Given as Argument
        int hashLength = 0;
        File file = new File(fileName);
        Scanner input = new Scanner(file);
        try {
            if (argv.length != 1)
                throw new IOException("Error: Hash Length Must be Given");
            hashLength = Integer.parseInt(argv[0]);
            if (hashLength < 1 )
                throw new IOException("Error: Hash Length Must be Larger Then 0");
        }
        catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }

        // Creates New Hash Table
        hashLinear hL = new hashLinear(hashLength);

        // Reads Input and Performs Hashing on All Lines
        while (input.hasNext()) {
            hL.insert(input.nextLine());
        }

        // Writes Out HashLength, Number of Data Read, Number of Collisions and Load Factor.
        System.out.println("Hash Length\t: " + hashLength);
        System.out.println("Elements\t: " + hL.nData());
        System.out.printf( "Load Factor\t: %5.3f\n",  hL.loadFactor());
        System.out.println("Probes\t\t: " + hL.nProbes() + "\n");

        for (int i =0; i<hashLength; i++) {
            if (hL.hashTable[i] != null)
                System.out.println(hL.hashTable[i] + "-> \tIndex:\t"
                        +i+ "\tReal index:\t" + hL.hash(hL.hashTable[i])+ "\tHashcode:\t" + hL.hashTable[i].hashCode());
            else if (hL.hashTable[i] == null)
                System.out.println(hL.hashTable[i] + "-> \tIndex: " +i);

        }
    }
}
