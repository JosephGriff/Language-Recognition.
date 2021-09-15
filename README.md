This projects consists of the classes: DataBase, Language, LanguageEntry, Parser and ServiceHandler.

The main features from the database would be split into 2 sections. 1-Database 2-Database.OutOfPlaceMetric.
From the 1-Database the features that are employed are: add(), getLangauge(), getTop(), resize(), getLanguageEntries() and getOutOfPlaceDistance().
- The Add function takes in a charsequence and a language where it then converts the sequence or string into hashcode. The language entry is then mapped and frequency is set to 1,
  The lang entry is then checked if it has a kmer is not overwrite the existing kmer with the lang entries kmer.
- The getLanguage function creates a treeset that is ordered from OutOfPlaceMetric. Compares the language query to the database of languages to each language in the database, add 
  a new OutOfPlaceMetric with the language name, calls getOutOfPlaceDistance, the query and gets the language from the database.
- getTop() gets the tio 300 kmers in a given map. Gets the set of frequencies for the language and outs it all into a treeMap, gives the intial entry rank of 1 where it increments
  +1 per entry afterwards, once the rank reaches 300 the function calls a break.
- resize() cuts down the entries in the map to a map that can only hold up to 300, gets a set of all languages and gets the map for the int of the language that have been changed and
  re-inserted into the map, the function then passes the top into the 300 entries for the language.
- getLangaugeEntries() takes the handle of the language entry and checks for a map for the entry if there is an existing map it uses it else the function creates a new map for the lang.
- compares map query to map entry sets initial distance to be zero. Creates a new treeset to be sorted based on the query values (sort the language entries) For each language entry in a 
  query get the language from the database. If entry dosent exist the entry distance will be made to be 301 (thus being out of the computation range of 300) else set distance to be the
  subject rank minus the query rank.
From the 2-Database the main features are: OutOfPlaceMetric(), CompareTo(), getAbsoluteDistance() and getLanguage()
- OutOfPlaceMetric() initializes the lang and the distance to be compared.
- CompareTo() Uses Comparible to compare getAbsoluteDistance and o.getAbsoluteDistance to generate distance value.
- getAbsoluteDistance() uses the Math.abs to get the AbsoluteDistance and make it a positive if it ended up as a negative numver after the 2 distances where taken away from each other.
- getLanguage() simple returns the language being used at the moment.

The Language class consists of a list of enum Languages which are used in other classes when making the Language detection.
The LanguageEntry class main features are as follows: LanguageEntry(), compareTo(), get/setKmer(), get/setFrequency() and get/setRank().
- LanguageEntry() function initializes the kmer aswell as the frequency.
- CompareTo(), The compareTo function compares one kmer to another using their frequency, in descending order.
- get/setKmer() these 2 functions operate as they are names one gets the kmer while the other sets the kmer.
- get/setFrequency() these 2 functions operate as they are names one gets the Frequency while the other sets the Frequency.
- get/setRank() these 2 functions operate as they are names one gets the Rank while the other sets the Rank.

The Parser class features are: Parser(), main(), run(), setDB, parse().
Parser class splits each line in wili-2018-Small-11750-Edited.txt on the @ symbol splits first part of the line(the language) into ngrams then sends them to the db add() method then resize and analyzes the query.
- Parser() initializes k as well as initializes file as the wili-2018-Small-11750-Edited.txt.
- main()  sets ngramSize , initalize new database instance create a thread , start the thread then join it. resize the bf to max 300 insert string of text for the language you have chosen A TreeMap is then created 
  from the query of the integer and LanguageEntry. The ngrams are then created from the string query print db.getLanguage(query)
- the run function uses wili-2018-Small-11750-Edited.txt and splits lines in 2 parts from the @ symbol.
- setDb() sets the database.
- parse() parses the text of the language into arrays of letters or kmers, the kmers.

ServiceHandler's features are: doGet(), doPost() and init()
- doGet() gets the httprequest from the server to get the parameters of the request. The request is then given a job number and added to the queue of jobs.
- doPost() posts the response to the job request from doGet().
- init() gets the handle on the application context then reads the value from the <context-param> in web.xml
