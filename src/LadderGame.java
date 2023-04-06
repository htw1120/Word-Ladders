import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class LadderGame {
    ArrayList<ArrayList<String>> sortedDictionary;
    ArrayList<ArrayList<String>> clonedSortedDictionary;
    public LadderGame(String dictionaryFile) {

        readDictionary(dictionaryFile);
        clonedDictionary();
    }

    public void play(String start, String end) {

//        Start the Queue
        clonedDictionary();
        Queue<WordInfo> ladderQueue = new Queue<>();
        int enqueueCounter = 0;

//        If the words are not the same length
        if (start.length() != end.length()) {
            System.out.println(start + " -> " + end + " : The words are not the same length");
        }
//        If the words are not found in the dictionary
        else if (!clonedSortedDictionary.get(start.length()).contains(start) || !clonedSortedDictionary.get(start.length()).contains(end)) {
            System.out.println(start + " -> " + end + " : The words are not in the dictionary");

//        Add the starting word to the queue
        } else {
            ladderQueue.enqueue(new WordInfo(start, 1));
            enqueueCounter += 1;

//        Take the next Word ladder off the queue and extract the needed info.
            while (!ladderQueue.isEmpty()) {
                WordInfo oldestLadder = ladderQueue.dequeue();
                String newestWord = oldestLadder.getWord();

//        If the newest word is one away from the end word.
                if (this.diff(newestWord, end)) {
                    System.out.println(start + " -> " + end + " : " + oldestLadder.getMoves() + " Moves [" + oldestLadder.getHistory() + " " + end + "] total enqueues " + enqueueCounter);
                    break;
                }
//        Check to see if the latest word is one away from the words in the dictionary.
                ArrayList<String> listOfWords = this.oneAway(newestWord, true);
                for (String currentWord : listOfWords) {
                    WordInfo newestLadder = new WordInfo(currentWord, oldestLadder.getMoves() + 1, oldestLadder.getHistory() + " " + currentWord);
                    ladderQueue.enqueue(newestLadder);
                    enqueueCounter += 1;
                }
            }

            if (ladderQueue.isEmpty()) {
                System.out.println(start + " -> " + end + " : No ladder was found");
            }
        }
    }




    public ArrayList<String> oneAway(String word, boolean withRemoval) {
        ArrayList<String> words = new ArrayList<>();
        int lengthOfList = this.clonedSortedDictionary.get(word.length()).size();
        for (int i = 0; i < lengthOfList; i++) {
            String dictionaryWord = this.clonedSortedDictionary.get(word.length()).get(i);
            if (this.diff(word, dictionaryWord)) {
                words.add(dictionaryWord);
            }
        }

        if (withRemoval) {
            for (String currentWord : words) {
                this.clonedSortedDictionary.get(word.length()).remove(currentWord);
        }

                    }
        return words;
        }

    private boolean diff(String startWord, String endWord){
        int counter = 0;
        boolean returnStatement = false;
            for (int i = 0; i < startWord.length(); i++) {
                if (startWord.charAt(i)==endWord.charAt(i)){
                    counter += 1;
                }
            }
            if (counter + 1 == startWord.length()){
                returnStatement = true;
            }
             return returnStatement;
        }


    public void listWords(int length, int howMany) {
        for (int i = 0; i < howMany; i++){
            System.out.println(this.sortedDictionary.get(length).get(i));
        }
    }

    public void clonedDictionary() {
        this.clonedSortedDictionary = new ArrayList<>();
        for(int i = 0; i < this.sortedDictionary.size(); i++){
            this.clonedSortedDictionary.add(new ArrayList<>());
            for (String currentWord : this.sortedDictionary.get(i)){
                this.clonedSortedDictionary.get(i).add(currentWord);
            }
        }
    }
    private void readDictionary (String dictionaryFile){
        File file = new File(dictionaryFile);
        ArrayList<String> allWords = new ArrayList<>();

        //
        // Track the longest word, because that tells us how big to make the array.
        int longestWord = 0;
        try (Scanner input = new Scanner(file)) {
            //
            // Start by reading all the words into memory.
            while (input.hasNextLine()) {
                String word = input.nextLine().toLowerCase();
                allWords.add(word);
                longestWord = Math.max(longestWord, word.length());

            }
            sortedDictionary = new ArrayList<>();
            for (int i = 0; i <= longestWord; i++) {
                sortedDictionary.add(new ArrayList<>());
            }
            for (String thisWord : allWords) {
                sortedDictionary.get(thisWord.length()).add(thisWord);

            }



        } catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the dictionary: " + ex);
        }
    }
}
