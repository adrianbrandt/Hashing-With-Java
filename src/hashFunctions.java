import java.io.*;
import java.util.Scanner;

// Testprogram for ulike hashfunksjoner
// NÃ¸kkelverdiene er tekststrenger

public class hashFunctions
{
    // Hashfunksjon for strenger med folding
    //
    // Enkel versjon: Legger bare sammen tegnverdiene
    // 
    static int hash1(String S, int hashLengde)
    {
        int h = 0;
        for (int i = 0; i < S.length(); i++)
            h += (int)(S.charAt(i));

        return h % hashLengde;
    }

    // Bedre versjon av folding pÃ¥ tekststrenger
    //
    // Legger sammen tegnverdiene vektet pÃ¥ posisjon
    // Betrakter grupper pÃ¥ fire og fire tegn som 
    // desimale sifre
    // 
    static int hash2(String S, int hashLengde)
    {
        int h = 0;
        int i = 0;
        while (i < S.length())
        {
            int potens = 1;
            for (int j = 0; (j < 4 && i < S.length()); j++)
            {
                h += (int)(S.charAt(i)) * potens;
                potens *= 10;
                i++;
            }
        }
        return h % hashLengde;
    }

    // Javas egen innebygde versjon av folding for strenger, hashCode,
    // som beregner en hashverdi som:
    //
    //  s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
    //
    // Merk at denne hashverdien kan bli negativ, hvis vi fÃ¥r en
    // overflow i beregningen -- Java ignorerer bare evt. overflows
    // ved beregning av hashverdier! Returnerer derfor absoluttverdien
    // for Ã¥ unngÃ¥ indekseringsfeil i hashtabellen.
    // 
    static int hash3(String S, int hashLengde)
    {
        int h = Math.abs(S.hashCode());
        return h % hashLengde;
    }

    // Hashfunksjon for strenger, midten-av-kvadratet
    //
    // Bruker fÃ¸rst Javas innebygde hashfunksjon for strenger.
    // Kvadrerer deretter denne verdien og bruker alle sifre i
    // kvadratet unntatt fÃ¸rste og siste.
    //
    // Her kan kvadratet bli et svÃ¦rt stort tall, sÃ¥ vi bruker en
    // varaibel av typen long i stedet for int for Ã¥ redusere risikoen
    // for overflow.
    //
    static int hash4(String S, int hashLengde)
    {
        long h = (long) Math.abs(S.hashCode());

        // Kvadrerer
        h *= h;

        // Tar vekk fÃ¸rste og siste siffer
        String tall = String.valueOf(h);
        h = Long.parseLong(tall.substring(1, tall.length()-1));

        return (int) (h % hashLengde);
    }

    // Hashfunksjon for strenger, bytte av tallsystem
    //
    // Bruker fÃ¸rst Javas innebygde hashfunksjon for strenger.
    // Transformerer deretter den beregnede verdien til et oktalt tall.
    // Bruker ogsÃ¥ her en long-variabel for Ã¥ unngÃ¥ integer overflow
    // ved bytte tilbake fra oktalt til desimalt.
    //
    static int hash5(String S, int hashLengde)
    {
        long h = (long) Math.abs(S.hashCode());

        // Konverterer til oktalt
        h = Long.parseLong(Long.toOctalString(h));

        return (int) (h % hashLengde);
    }

    // Hashfunksjon for strenger, basert pÃ¥ utplukk og ombytting
    //
    // Velger annethvert tegn, snur rekkefÃ¸lgen, og bruker deretter
    // Javas innebygde hashfunksjon for strenger
    //
    static int hash6(String S, int hashLengde)
    {
        int h = 0;

        // Beregner lengden av "halvparten" av strengen (forskjellig
        // for strenglengder som er partall og oddetall)
        int len = S.length()/2;
        if (S.length() % 2 != 0)
            len += 1;

        // Tegn-array som brukes til Ã¥ lagre annethvert tegn
        char C[] = new char[len];

        // LÃ¸kke som plukker ut annethvert tegn fra strengen S og
        // legger dem i omvendt rekkefÃ¸lge i tegn-arrayen C
        for (int i = 0, j = len-1; i < S.length(); i+=2, j--)
            C[j] = S.charAt(i);

        // Returnerer verdien fra Javas innebygde hashfunksjon for
        // strenger brukt pÃ¥ den nye "halve" strengen
        return Math.abs(new String(C).hashCode() % hashLengde);
    }

    // Hashfunksjon for strenger, basert pÃ¥ lengde av nÃ¸kkelverdi
    //
    // Bruker fÃ¸rst Javas innebygde hashfunksjon for strenger.
    // Multipliserer deretter med antall tegn i strengen.
    // Bruker long-variabel for Ã¥ unngÃ¥ integer overflow nÃ¥r vi
    // multipliserer med lengden av strengen.
    //
    static int hash7(String S, int hashLengde)
    {
        long h = (long) Math.abs(S.hashCode());

        // Multipliserer med lengde av strengen
        h = h * S.length();

        return (int) (h % hashLengde);
    }

    // Enkelt hovedprogram.
    // 
    // * Hashlengde gis som input pÃ¥ kommandolinjen
    //
    // * Leser tekststrenger linje for linje fra standard input og
    //   lagrer dem fÃ¸rst i en data-array
    //
    // * For hver hashfunksjon som testes:
    //
    //   - Beregner hash-verdier for hver tekststreng
    //   - Teller opp og skriver ut antall kollisjoner
    //
    public static void main(String argv[]) throws FileNotFoundException {
        // Maks antall tekststrenger som skal hashes
        int N = 100000 ;

        // Hashlengde leses fra kommandolinjen
        int hashLengde = 0;
        File file = new File("src/datasett.txt");
        Scanner input = new Scanner(file);
        try
        {
            if (argv.length != 1)
                throw new IOException("Feil: Hashlengde mÃ¥ angis");
            hashLengde = Integer.parseInt(argv[0]);
            if (hashLengde < 1 )
                throw new IOException("Feil: Hashlengde mÃ¥ vÃ¦re stÃ¸rre enn 0");
        }
        catch (Exception e)
        {
            System.err.println(e);
            System.exit(1);
        }

        // Leser input og lagrer fÃ¸rst alle teksttrengene som skal
        // hashes i en "hjelpearray" med data
        int antData = 0;
        String data[] = new String[N];
        while (input.hasNext())
        {
            data[antData] = input.nextLine();
            antData++;
        }

        // Hashtabellen som brukes for testing
        String HashTabell[] = new String[hashLengde];

        // Variable for hashverdi og telling av kollisjoner
        int hashVerdi, antKollisjoner;

        // Tester de ulike hashfunksjonene:
        System.out.print("\nHasher " + antData + " tekststrenger ");
        System.out.println("med hashlengde lik " + hashLengde);
        System.out.println("Teller antall kollisjoner:\n");

        // ----------------
        // 1. Enkel folding
        // ----------------

        // Setter hele hashtabellen til Ã¥ vÃ¦re tom
        antKollisjoner = 0;
        for (int i = 0; i < hashLengde; i++)
            HashTabell[i] = null;

        // Beregner hashverdi for alle dataene og teller opp antall
        // kollisjoner
        for (int i = 0; i < antData; i++)
        {
            hashVerdi = hash1(data[i], hashLengde);

            if (HashTabell[hashVerdi] == null)
                HashTabell[hashVerdi] = data[i];
            else
                antKollisjoner++;
        }
        System.out.printf("1. Enkel folding       : %4d\n", antKollisjoner);

        // -----------------------------
        // 2. Folding vektet pÃ¥ posisjon
        // -----------------------------

        // Setter hele hashtabellen til Ã¥ vÃ¦re tom
        antKollisjoner = 0;
        for (int i = 0; i < hashLengde; i++)
            HashTabell[i] = null;
        antKollisjoner = 0;

        // Beregner hashverdi for alle dataene og teller opp antall
        // kollisjoner
        for (int i = 0; i < hashLengde; i++)
            HashTabell[i] = null;
        for (int i = 0; i < antData; i++)
        {
            hashVerdi = hash2(data[i], hashLengde);

            if (HashTabell[hashVerdi] == null)
                HashTabell[hashVerdi] = new String(data[i]);
            else
                antKollisjoner++;
        }
        System.out.printf("2. Vektet folding      : %4d\n", antKollisjoner);

        // --------------------------------------------
        // 3. Javas innebygde hashfunksjon for strenger
        // --------------------------------------------

        // Setter hele hashtabellen til Ã¥ vÃ¦re tom
        antKollisjoner = 0;
        for (int i = 0; i < hashLengde; i++)
            HashTabell[i] = null;
        antKollisjoner = 0;

        // Beregner hashverdi for alle dataene og teller opp antall
        // kollisjoner
        for (int i = 0; i < hashLengde; i++)
            HashTabell[i] = null;
        for (int i = 0; i < antData; i++)
        {
            hashVerdi = hash3(data[i], hashLengde);

            if (HashTabell[hashVerdi] == null)
                HashTabell[hashVerdi] = new String(data[i]);
            else
                antKollisjoner++;
        }
        System.out.printf("3. Javas hashfunksjon  : %4d\n", antKollisjoner);

        // --------------------
        // 4. Midten-av-kvadrat
        // --------------------

        // Setter hele hashtabellen til Ã¥ vÃ¦re tom
        antKollisjoner = 0;
        for (int i = 0; i < hashLengde; i++)
            HashTabell[i] = null;
        antKollisjoner = 0;

        // Beregner hashverdi for alle dataene og teller opp antall
        // kollisjoner
        for (int i = 0; i < antData; i++)
        {
            hashVerdi = hash4(data[i], hashLengde);

            if (HashTabell[hashVerdi] == null)
                HashTabell[hashVerdi] = new String(data[i]);
            else
                antKollisjoner++;
        }
        System.out.printf("4. Midten-av-kvadrat   : %4d\n", antKollisjoner);

        // ----------------------
        // 5. Bytte av tallsystem
        // ----------------------

        // Setter hele hashtabellen til Ã¥ vÃ¦re tom
        antKollisjoner = 0;
        for (int i = 0; i < hashLengde; i++)
            HashTabell[i] = null;
        antKollisjoner = 0;

        // Beregner hashverdi for alle dataene og teller opp antall
        // kollisjoner
        for (int i = 0; i < antData; i++)
        {
            hashVerdi = hash5(data[i], hashLengde);

            if (HashTabell[hashVerdi] == null)
                HashTabell[hashVerdi] = new String(data[i]);
            else
                antKollisjoner++;
        }
        System.out.printf("5. Bytte av tallsystem : %4d\n", antKollisjoner);

        // -----------------------
        // 6. Utplukk og ombytting
        // -----------------------

        // Setter hele hashtabellen til Ã¥ vÃ¦re tom
        antKollisjoner = 0;
        for (int i = 0; i < hashLengde; i++)
            HashTabell[i] = null;
        antKollisjoner = 0;

        // Beregner hashverdi for alle dataene og teller opp antall
        // kollisjoner
        for (int i = 0; i < antData; i++)
        {
            hashVerdi = hash6(data[i], hashLengde);

            if (HashTabell[hashVerdi] == null)
                HashTabell[hashVerdi] = new String(data[i]);
            else
                antKollisjoner++;
        }
        System.out.printf("6. Utplukk og bytting  : %4d\n", antKollisjoner);

        // ------------------------
        // 7. Lengde av nÃ¸kkelverdi
        // ------------------------

        // Setter hele hashtabellen til Ã¥ vÃ¦re tom
        antKollisjoner = 0;
        for (int i = 0; i < hashLengde; i++)
            HashTabell[i] = null;
        antKollisjoner = 0;

        // Beregner hashverdi for alle dataene og teller opp antall
        // kollisjoner
        for (int i = 0; i < antData; i++)
        {
            hashVerdi = hash7(data[i], hashLengde);

            if (HashTabell[hashVerdi] == null)
                HashTabell[hashVerdi] = new String(data[i]);
            else
                antKollisjoner++;
        }
        System.out.printf("7. Lengde av dataverdi : %4d\n\n", antKollisjoner);
    }
}