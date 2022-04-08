import java.io.*;
import java.util.Locale;
import java.util.Scanner;

// Hashing av tekststrenger med kjeding i lenket liste
// Bruker Javas innebygde hashfunksjon for strenger
//
// Enkel og begrenset implementasjon:
//
// - Ingen rehashing ved full tabell/lange lister
// - Tilbyr bare innsetting og sÃ¸king
//
public class hashChained {
    // Indre klasse:
    // Node med data, kjedes sammen i lenkede lister
    //
    private class hashNode {
        // Data, en tekststreng
        String data;
        // Neste node i listen
        hashNode neste;

        // KonstruktÃ¸r for listenoder
        public hashNode(String S, hashNode hN) {
            data = S;
            neste = hN;
        }
    }

    // Hashlengde
    private static int hashLengde;

    // Hashtabell, pekere til lister
    private static hashNode[] hashTabell;

    // Antall elementer lagret i tabellen
    private int n;

    // Antall kollisjoner ved innsetting
    private int antKollisjoner;

    // KonstruktÃ¸r
    // Sjekker ikke for fornuftig verdi av hashlengden
    //
    public hashChained(int lengde) {
        hashLengde = lengde;
        hashTabell = new hashNode[lengde];
        n = 0;
        antKollisjoner = 0;
    }

    // Returnerer load factor
    public float loadFactor() {
        return ((float) n) / hashLengde;
    }

    // Returnerer antall data i tabellen
    public int antData() {
        return n;
    }

    // Returnerer antall kollisjoner ved innsetting
    public int antKollisjoner() {
        return antKollisjoner;
    }

    // Hashfunksjon
    static int hash(String S) {
        int h = Math.abs(S.hashCode());
        return h % hashLengde;
    }

    // Innsetting av tekststreng med kjeding
    //
    void insert(String S) {
        // Beregner hashverdien
        int h = hash(S);

        // Ã˜ker antall elementer som er lagret
        n++;

        // Sjekker om kollisjon
        if (hashTabell[h] != null)
            antKollisjoner++;

        // Setter inn ny node fÃ¸rst i listen
        hashTabell[h] = new hashNode(S, hashTabell[h]);
    }


    // SÃ¸king etter tekststreng i hashtabell med kjeding
    // Returnerer true hvis strengen er lagret, false ellers
    //
    static boolean search(String S) {
        // Finner listen som S skal ligge i
        hashNode hN = hashTabell[hash(S)];

        // Leter gjennom listen
        while (hN != null) {
            // Har vi funnet tekststrengen?
            if (hN.data.compareTo(S) == 0)
                return true;
            // PrÃ¸ver neste
            hN = hN.neste;
        }
        // Finner ikke strengen, har kommet til slutten av listen
        return false;
    }

    //Removes the node that has been choosen by user
    void  remover(String S) {
        int h = hash(S);
        n++;
        if (hashTabell[h] != null)
            hashTabell[h] = null;
        program(S);
    }

    //Main program to search for and delete strings
    void program(String S) {
        for (int i = 0; i < hashLengde; i++) {
            if (hashChained.hashTabell[i] != null)
                System.out.println("Index: "
                        + i + " Word:     " + hashChained.hashTabell[i].data);
        }
        System.out.println("""
                    
                    Write the word you want to delete:
                    write exit to close the program!
                    """);

        Scanner uput = new Scanner(System.in);
        S = uput.nextLine();

        if (S.toLowerCase().equals("exit"))
            System.exit(0);

        for (int i = 0; i < hashLengde; i++) {
            if (hashChained.hashTabell[i] != null)
                System.out.println("Index: "
                        + i + " Word:     " + hashChained.hashTabell[i].data
                        + "     Hash: " + hashChained.hashTabell[i].hashCode());
        }


        if (hashChained.search(S)) {
            System.out.println("\"" + S + "\"" + " removed from table\n");
            remover(S);

            }
        program(S);

        }

        // Enkelt testprogram:
        //
        // * Hashlengde gis som input pÃ¥ kommandolinjen
        //
        // * Leser tekststrenger linje for linje fra standard input
        //   og lagrer dem i hashtabellen
        //
        // * Skriver ut litt statistikk etter innsetting
        //
        // * Tester om sÃ¸k fungerer for et par konstante verdier
        //
        public static void main (String argv[]) throws FileNotFoundException {
            // Hashlengde leses fra kommandolinjen
            int hashLengde = 0;
            File file = new File("src/file.txt");
            Scanner input = new Scanner(file);
            Scanner uput = new Scanner(System.in);
            try {
                if (argv.length != 1)
                    throw new IOException("Feil: Hashlengde mÃ¥ angis");
                hashLengde = Integer.parseInt(argv[0]);
                if (hashLengde < 1)
                    throw new IOException("Feil: Hashlengde mÃ¥ vÃ¦re stÃ¸rre enn 0");
            } catch (Exception e) {
                System.err.println(e);
                System.exit(1);
            }

            // Lager ny hashTabell
            hashChained hC = new hashChained(hashLengde);

            // Leser input og hasher alle linjer
            while (input.hasNext()) {
                hC.insert(input.nextLine());
            }


            // Skriver ut hashlengde, antall data lest, antall kollisjoner
            // og load factor
            System.out.println("Hashlengde  : " + hashLengde);
            System.out.println("Elementer   : " + hC.antData());
            System.out.printf("Load factor : %5.3f\n", hC.loadFactor());
            System.out.println("Kollisjoner : " + hC.antKollisjoner() + "\n");



            String S = "A";
            hC.remover(S);


        }
    }
