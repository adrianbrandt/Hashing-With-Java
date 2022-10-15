import java.io.*;
import java.util.Objects;
import java.util.Scanner;

/*
Hashing Strings Robin Hood With Linear Probing

- No Re Hashing When Table Full
- No Searching, Only Insertion
 */
public class robinHood {
    // Hash Length
    private int hashLength;

    // Hash Table
    private String[] hashTable;

    // Number of Elements to be Stored
    private int n;

    // Number of Probes
    private int nProbes;

    // Constructor
    public robinHood(int lengde) {
        hashLength = lengde;
        hashTable = new String[lengde];
        n = 0;
        nProbes = 0;
    }

    // Returns Load Factor
    public float loadFactor() {
        return ((float) n)/ hashLength;
    }

    // Returns Number of Data in Table
    public int nData() {
        return n;
    }

    // Returns Number of Probes
    public int nProbes() {
        return nProbes;
    }

    // Hash Function
    int hash(String S) {
        int h = Math.abs(S.hashCode());
        return h % hashLength;
    }


    //Insert Method for Robin Hood Hashing
    void insert(String S) {

        // Calculate Hash Value
        int h = hash(S);

        // Linear Probing
        int next = h;

        // Inserting String Where Table is null
        String T = hashTable[next];
        if (T == null) {
            hashTable[next] = S;
        }

        // While Loop Where Table is Not null
        while (T != null) {
            // Ny probe
            nProbes++;

            //Checks if the Object´s Index Equals String
            if (Objects.equals(hashTable[next], S)) {
                S = T;
            }
            next++;

            T = hashTable[next];

            // Index Taken, Trying Next
            next++;

            // Wrap-Around
            if (next >= hashLength)
                next = 0;

            /*
            When We have Wraped Around and Reached First Hash Value, The Table is Full And We Are Stooping
            Normally We Would Double the Length for the HashTable and Done Re Hashing Here
             */
            if (next == h) {
                System.err.println("\nHashtabell full, avbryter");
                System.exit(0);
            }
        }

        // Increasing Number of Elements Stored
        n++;
    }


    /*
     Searching for Strings Using Linear Probing
     Returning True if String is Stored
     Returning False if String is Not Stored
     Not Used in Current Program
     */
    boolean search(String S)
    {
        // Beregner hashverdien
        int h = hash(S);

        // LineÃ¦r probing
        int neste = h;

        while (hashTable[neste] != null)
        {
            // Calculates Hash value
            if (hashTable[neste].compareTo(S) == 0)
                return true;

            // Tries Next Possible
            neste++;

            // Wrap-Around
            if (neste >= hashLength)
                neste = 0;

            //We Have Wrapped Around Meaning the String Does Not Exist in Current Table
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
        int hashLengde = 0;
        File file = new File(fileName);
        Scanner input = new Scanner(file);
        try
        {
            if (argv.length != 1)
                throw new IOException("Error: Hash Length Must be Given");
            hashLengde = Integer.parseInt(argv[0]);
            if (hashLengde < 1 )
                throw new IOException("Error: Hash Length Must be Larger Then 0");
        }
        catch (Exception e)
        {
            System.err.println(e);
            System.exit(1);
        }

        // Creates New Hash Table
        robinHood hL = new robinHood(hashLengde);

        // Reads Input and Performs Hashing on All Lines
        while (input.hasNext()) {
            hL.insert(input.nextLine());
        }

        // Writes Out HashLength, Number of Data Read, Number of Collisions and Load Factor.
        System.out.println("Hash Length  : " + hashLengde);
        System.out.println("Elements   : " + hL.nData());
        System.out.printf( "Load Factor : %5.3f\n",  hL.loadFactor());
        System.out.println("Probes      : " + hL.nProbes());

        System.out.println("\n RESULT: ");
        for (int i =0; i<hashLengde; i++) {
            if (hL.hashTable[i] != null)
                System.out.println(hL.hashTable[i] + "-> \tIndex:\t"
                        +i+ "\tReal index:\t" + hL.hash(hL.hashTable[i])+ "\tHashcode:\t" + hL.hashTable[i].hashCode());
            else if (hL.hashTable[i] == null)
                System.out.println(hL.hashTable[i] + "-> \tIndex: " +i);

        }
    }
}

