package ie.gmit.sw;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

/***
 * creates class from component
 * Parser class splits each line in wili-2018-Small-11750-Edited.txt on the @ symbol
 * splits first part of the line(the language) into ngrams then sends them to the db add() method
 * then resize and analyzes the query
 */

public class Parser extends Component implements Runnable {
    // Database instance
    private Database db;
    // Library of Language references
    private String file;
    private int k;

    /***
     * initializes file as the wili-2018-Small-11750-Edited.txt
     * @param k
     */
    public Parser(int k){
        //this.file = selectFile();
        this.file = "wili-2018-Small-11750-Edited.txt";
        this.k = k;

    }

    /***
     * sets the database
     * @param db is the database
     */
    public void setDb(Database db){
        this.db = db;
    }

    /***
     * from wili-2018-Small-11750-Edited.txt splits lines in 2 parts from the @
     */
    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = null;

            while ((line = br.readLine())!= null){
                String [] record = line.trim().split("@");
                if (record.length!=2) continue;
                parse(record[0], record[1]);
                //System.out.println("Added: "+record[1]+" to db");
            }
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /***
     *  parses the text of the language into arrays of letters or kmers, the kmers are then added into the db of languages
     * @param text the text of the language
     * @param lang the name of the lang
     * @param ks the array of ints or groups that become kmers
     */
    private void parse(String text, String lang, int... ks){
        Language language = Language.valueOf(lang);

        for(int i=0; i<text.length()-k; i++){
            CharSequence kmer = text.substring(i, i+k);
            db.add(kmer, language);
        }
    }

    /***
     * sets ngramSize , initalize new database instance
     * create a thread , start the thread then join it.
     * resize the bf to max 300
     * insert string of text for the language you have chosen
     * A TreeMap is then created from the query of the integer and LanguageEntry.
     * The ngrams are then created from the string query
     * print db.getLanguage(query)
     * @param args
     * @throws Throwable
     */
    public static void main(String[] args) throws Throwable {

        int ngramSize = 3;
        Parser p = new Parser(ngramSize);

        Database db = new Database();
        p.setDb(db);
        Thread t = new Thread(p);
        t.start();
        t.join();

        db.resize(300);

        //Irish
        //String s = "Phríomh-Aire. Bhuaigh siad na holltoghcháin freisin, faoi cheannas Thatcher.";

        //English
        //String s = "My name is Joseph Griffith, I study in Galway";

        //Afrikaans
        String s = "Die natuurlike veldtipe in die reservaat is gemengde";

        //Neapolitan
        //String s = "Boca Junior, però doppo nu poco 'e tiempo se ne jette a fa' l' elettricista co frato cchiù";

        //Map to hold the query n-grams and frequency
        Map<Integer, LanguageEntry> query = new TreeMap<>();

        // Generate ngrams from the string query
        for (int i=0; i<s.length() - ngramSize; i++){
            CharSequence nGram = s.substring(i, i+ngramSize);

            int kmer = nGram.hashCode();

            int frequency = 2;

            if (query.containsKey(kmer)) {
                frequency += query.get(kmer).getFrequency();
            }

            query.put(kmer, new LanguageEntry(kmer, frequency));
        }

        //System.out.println(query.keySet().toString() + " : " + query.values().toString());


        //System.out.println(db.toString());
        System.out.println(db.getLanguage(query));

    }
}