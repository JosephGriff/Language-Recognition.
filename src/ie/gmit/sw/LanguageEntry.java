package ie.gmit.sw;

public class LanguageEntry implements Comparable<LanguageEntry> {
	private int kmer;
	private int frequency;
	private int rank;

	/***
	 * Initializes the kmer as well as the frequency
	 * @param kmer set of letters from a chosen language normally between 1-5 letters
	 * @param frequency correlates to the rank with the one which is most frequent is ranked 1
	 */
	public LanguageEntry(int kmer, int frequency) {
		super();
		this.kmer = kmer;
		this.frequency = frequency;
	}

	/***
	 * returns the kmer
	 * @return the kmer
	 */
	public int getKmer() {
		return kmer;
	}

	/***
	 * sets/intialize the kmer
	 * @param kmer set of letters from a chosen language normally between 1-5 letters
	 */
	public void setKmer(int kmer) {
		this.kmer = kmer;
	}

	/***
	 *
	 * @return the frequency (correlates to the rank with the one which is most frequent is ranked 1)
	 */
	public int getFrequency() {
		return frequency;
	}

	/***
	 * sets/initialize the frequency
	 * @param frequency correlates to the rank with the one which is most frequent is ranked 1
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	/***
	 * returns the rank
	 * @return rank (of the query of the map , max rank = 300) the lower the rank is the higher its precedence is
	 * 	  e.g. Rank 1 = highest rank
	 */
	public int getRank() {
		return rank;
	}

	/***
	  sets the rank
	 * @param rank of the query of the map , max rank = 300 the lower the rank is the higher its precedence is
	 * e.g. Rank 1 = highest rank
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/***
	 * compares one kmer to another by using their frequency, in descending order
	 * @param next moves to the next frequency for comparing
	 * @return the comparison of the original frequency to the next frequency
	 */
	@Override
	public int compareTo(LanguageEntry next) {
		return - Integer.compare(frequency, next.getFrequency());
	}

	/**
	 * Print the details of kmer, frequency and rank
	 *
	 * @return String details of kmer, frequency and rank
	 */
	@Override
	public String toString() {
		return "[" + kmer + "/" + frequency + "/" + rank + "]";
	}
}