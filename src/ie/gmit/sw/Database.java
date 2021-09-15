package ie.gmit.sw;

import java.util.*;

public class Database {
	private Map<Language, Map<Integer, LanguageEntry>> db = new TreeMap<>();

	/***
	 * converst s(string) into its hashcode then get the handle on the language entry
	 * sets frequency to 1 , where if the lang entry already has the kmer incerement the frequency
	 * if not overwrite existing kmer with new language entry
	 * @param s the sequence of chars that will be made into kmers
	 * @param lang the input language used for the charsequence / kmers
	 */
	public void add(CharSequence s, Language lang) {
		int kmer = s.hashCode();
		Map<Integer, LanguageEntry> langDb = getLanguageEntries(lang);
		
		int frequency = 1;
		if (langDb.containsKey(kmer)) {
			frequency += langDb.get(kmer).getFrequency();
		}
		langDb.put(kmer, new LanguageEntry(kmer, frequency));
		
	}

	/***
	 * takes the handle of the language entry and checks for a map for the entry
	 * if an existing map use it
	 * else create a new map for the lang
	 * @param lang the language that is used  to check for the map to be used
	 * @return returns the lang map
	 */
	private Map<Integer, LanguageEntry> getLanguageEntries(Language lang){
		Map<Integer, LanguageEntry> langDb = null; 
		if (db.containsKey(lang)) {
			langDb = db.get(lang);
		}else {
			langDb = new TreeMap<Integer, LanguageEntry>();
			db.put(lang, langDb);
		}
		return langDb;
	}

	/***
	 * cuts down the entries in to map to a map of 300
	 * gets a set of all languages
	 * gets the map for the int of the language that have been changed and re-insert into the map
	 * then pass the top into the 300 entries for the language
	 * @param max max is set to 300 entries for the map
	 */
	public void resize(int max) {
		Set<Language> keys = db.keySet();
		for (Language lang : keys) {
			Map<Integer, LanguageEntry> top = getTop(max, lang);
			db.put(lang, top);
		}
	}

	/***
	 * get the top 300 kmers in a given map
	 * get the set of frequencies for the language and put it into a treeMap
	 * give intial entry rank of 1, increment rank +1 per every entry after
	 * once rank reaches 300 break
	 * @param max the max is 300
	 * @param lang the lang depicts what language the kmers are made from
	 * @return temp
	 */
	public Map<Integer, LanguageEntry> getTop(int max, Language lang) {
		Map<Integer, LanguageEntry> temp = new TreeMap<>();
		List<LanguageEntry> les = new ArrayList<>(db.get(lang).values());
		Collections.sort(les);
		
		int rank = 1;
		for (LanguageEntry le : les) {
			le.setRank(rank);
			temp.put(le.getKmer(), le);			
			if (rank == max) break;
			rank++;
		}
		
		return temp;
	}

	/***
	 * creates a treeset that is ordered from OutOfPlaceMetric
	 * compares the language query to the database of languages
	 * to each language in the database add a new OutOfPlaceMetric with the language name, calls getOutOfPlaceDistance
	 * the query and get the language from the database
	 * @param query of the language to the database of languages
	 * @return the name of the language
	 */
	public Language getLanguage(Map<Integer, LanguageEntry> query) {
		TreeSet<OutOfPlaceMetric> oopm = new TreeSet<>();
		
		Set<Language> langs = db.keySet();
		for (Language lang : langs) {
			oopm.add(new OutOfPlaceMetric(lang, getOutOfPlaceDistance(query, db.get(lang))));
		}
		return oopm.first().getLanguage();
	}

	/***
	 * compares map query to map entry
	 * sets initial distance to be zero
	 * creates a new treeset to be sorted based on the query values (sort the language entries)
	 * for each language entry in a query get the language from the database
	 * if entry dosent exist the entry distance will be made to be 301 (thus being out of the computation range of 300)
	 * else set distance to be the subject rank minus the query rank
	 * @param query of the language to the database
	 * @param subject
	 * @return the distance being the rank of the query/subject
	 */
	private int getOutOfPlaceDistance(Map<Integer, LanguageEntry> query, Map<Integer, LanguageEntry> subject) {
		int distance = 0;
		
		Set<LanguageEntry> les = new TreeSet<>(query.values());		
		for (LanguageEntry q : les) {
			LanguageEntry s = subject.get(q.getKmer());
			if (s == null) {
				distance += subject.size() + 1;
			}else {
				distance += s.getRank() - q.getRank();
			}
		}
		return distance;
	}

	private class OutOfPlaceMetric implements Comparable<OutOfPlaceMetric>{
		private Language lang;
		private int distance;

		/***
		 * initializes the lang and distance
		 * @param lang the language used
		 * @param distance distance or rank
		 */
		public OutOfPlaceMetric(Language lang, int distance) {
			super();
			this.lang = lang;
			this.distance = distance;
		}

		/***
		 * gets the language used
		 * @return language used
		 */
		public Language getLanguage() {
			return lang;
		}

		/***
		 * if distance = a negative number math.abs removes the minus
		 * @return distance as a positve
		 */
		public int getAbsoluteDistance() {
			return Math.abs(distance);
		}

		/**
		 * Returns distance between integers.
		 * Uses Comparible to compare getAbsoluteDistance and o.getAbsoluteDistance to generate distance value.
		 *
		 * @param o The OutOfPlaceMetric to compare against this OutOfPlaceMetric.
		 * @return int result of comparison
		 */
		@Override
		public int compareTo(OutOfPlaceMetric o) {
			return Integer.compare(this.getAbsoluteDistance(), o.getAbsoluteDistance());
		}

		/***
		 * returns the name of the language and absolute distance
		 * @return the name of the language followed by its absolute distance
		 */
		@Override
		public String toString() {
			return "[lang=" + lang + ", distance=" + getAbsoluteDistance() + "]";
		}
		
		
	}
	/**
	 * Print the details of this kmerCount + langCount
	 *
	 * @return String details of kmerCount + langCount
	 */
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		int langCount = 0;
		int kmerCount = 0;
		Set<Language> keys = db.keySet();
		for (Language lang : keys) {
			langCount++;
			sb.append(lang.name() + "->\n");
			 
			 Collection<LanguageEntry> m = new TreeSet<>(db.get(lang).values());
			 kmerCount += m.size();
			 for (LanguageEntry le : m) {
				 sb.append("\t" + le + "\n");
			 }
		}
		sb.append(kmerCount + " total k-mers in " + langCount + " languages"); 
		return sb.toString();
	}
}