package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static final Map<Character, Integer> letterMap = new HashMap<>();
    private static final Map<Integer, Character> numberMap = new HashMap<>();
    private static final Set<String> dictionary = new HashSet<>();
    static {
        for(char c = 'a'; c <= 'z'; c++) {
            letterMap.put(c,c-'a');
            numberMap.put(c - 'a',c);
        }
        letterMap.put(' ',-1);
        numberMap.put(-1, ' ');

        try (BufferedReader in = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("/words_alpha.txt")))) {
            String line;
            while ((line = in.readLine()) != null) {
                dictionary.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //gave minh "congrats youre correct"
    public static void main(String[] args) {
        System.out.println(letterMap);
        boolean interactive = true;
        if(interactive){
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("input your string");
            String word = sc.nextLine();
            word = word.toLowerCase();
            System.out.println();
            System.out.println("Options\n1-2x\n2-x^2\n3-x\nother-quit");
            int option = sc.nextInt();
            sc.nextLine();
            if(option == 1) {
                System.out.println(word);
                System.out.println(twoX(word));
                System.out.println(decodeTwoX(twoX(word)));
            }else if(option == 2) {
                System.out.println(word);
                System.out.println(xSquared(word));
                //System.out.println(decodeXSquared(xSquared(word)));
            }else if(option == 3) {
                System.out.println(word);
                System.out.println(x(word));
                //System.out.println(decodeX(x(word)));
            }else {
                break;
            }
            }

        }
        /*
        //put non interactive code here
        ArrayList<String> decodeToWord = new ArrayList<>();
        for(String word : dictionary) {
            if(decodeTwoX(word).size() != 0) {
                decodeToWord.add(word);
            }
        }
        for (String word: decodeToWord) {
            System.out.println(word + " = " + decodeTwoX(word));
        }

         */
    }
    public static String xSquared(String original) {
        int letterIndex = 0;
        int wordIndex = 0;
        StringBuilder cipheredInput = new StringBuilder();

        for (int i = 0; i < original.length(); i++) {
            char c = original.charAt(i);
            //converts the character to its numerical value
            int number = 0;
            //if the character is a space increase the word index and reset the letter index, add space to the string
            if(c == ' ') {
                letterIndex = 0;
                wordIndex++;
                cipheredInput.append(c);
            }else {
                //creates the x value
                number += letterIndex + wordIndex + letterMap.get(c);
                //plugs it into the function(x*x)
                number *= number;
                //sets the number into a letter within the alphabet
                number = number % 26;
                //converts the number back into a letter
                cipheredInput.append(numberMap.get(number));
                letterIndex++;
            }
        }

        return cipheredInput.toString();
    }


    /** returns the potential values of the x squared cypher
     *
     *need at-> param cypher string that has been cyphered
     * @return the potential words of x squared
     */
    /*
    public static List<String> decodeXSquared(String cypher) {
        int letterIndex = 0;
        int wordIndex = 0;
        int maxCharVal = 0;
        int minCharVal = 0;
        List<List<Integer>> charValPossibilities = new ArrayList<>();
        //goes through each character of the string, and gets the potential numerical values of the original
        for(int i = 0; i < cypher.length(); i++) {
            charValPossibilities.add(new ArrayList<>());
            //change how max and min values are derived to match given function
            //sets the high bound of the letter. derived from assuming the value is "z"
            maxCharVal = (25 + letterIndex + wordIndex);
            maxCharVal *= maxCharVal;
            //sets the low bound of the letter. derived from assuming the value is "a"
            minCharVal = (0 + letterIndex + wordIndex);
            minCharVal *= minCharVal;
            //adds space(-1) to the possible list
            if(cypher.charAt(i) == ' '){
                charValPossibilities.get(i).add(-1);
                letterIndex = 0;
                wordIndex++;
            }else {
                //int k = numerical value of the cypher
                int k = 0;
                k = letterMap.get(cypher.charAt(i));
                //while k is in the maximum and minimum
                while(k <= maxCharVal) {
                    if(k >= minCharVal) {
                        if(k == (int)(Math.sqrt(k)) * (int)(Math.sqrt(k))){
                            charValPossibilities.get(i).add(k);
                        }
                    }
                    k += 26;
                }
                letterIndex++;
            }
        }
        for (List<Integer> list: charValPossibilities) {
            System.out.println(list);
        }
        letterIndex = 0;
        wordIndex = -1;

        List<List<Integer>> processedValues = new ArrayList<>();
        //loops over every character
        for(int i = 0; i < charValPossibilities.size(); i++) {
            processedValues.add(new ArrayList<>());
            //loops over every character possibility
            for(int k = 0; k < charValPossibilities.get(i).size(); k++) {
                int value = charValPossibilities.get(i).get(k);
                if(value == -1) {
                    processedValues.get(i).add(value);
                    wordIndex++;
                    letterIndex = 0;
                    continue;
                }else {
                    value = value - letterIndex;
                    //prepares word index
                    if(wordIndex < 0) {
                        value -= 0;
                    }else {
                        value -= wordIndex;
                    }

                    if(value < 0) {
                        continue;
                    }else {
                        processedValues.get(i).add(value%26);
                    }
                }
            }
            letterIndex++;
        }
        List<String> result = potentialWord(processedValues, true);
        System.out.println(result.size());
        return result;
    }
    */

    public static String x(String original) {
        int letterIndex = 0;
        int wordIndex = 0;
        StringBuilder cipheredInput = new StringBuilder();

        for (int i = 0; i < original.length(); i++) {
            char c = original.charAt(i);
            //converts the character to its numerical value
            int number = 0;
            //if the character is a space increase the word index and reset the letter index, add space to the string
            if(c == ' ') {
                letterIndex = 0;
                wordIndex++;
                cipheredInput.append(c);
            }else {
                //creates the x value
                number += letterIndex + wordIndex + letterMap.get(c);
                //plugs it into the function(x)
                //sets the number into a letter within the alphabet
                number = number % 26;
                //converts the number back into a letter
                cipheredInput.append(numberMap.get(number));
                letterIndex++;
            }
        }

        return cipheredInput.toString();
    }
    public static String twoX(String original) {
        int letterIndex = 0;
        int wordIndex = 0;
        StringBuilder cipheredInput = new StringBuilder();

        for (int i = 0; i < original.length(); i++) {
            char c = original.charAt(i);
            //converts the character to its numerical value
            int number = 0;
            //if the character is a space increase the word index and reset the letter index, add space to the string
            if(c == ' ') {
                letterIndex = 0;
                wordIndex++;
                cipheredInput.append(c);
            }else {
                //creates the x value
                number += letterIndex + wordIndex + letterMap.get(c);
                //plugs it into the function(2x)
                number *= 2;
                //sets the number into a letter within the alphabet
                number = number % 26;
                //converts the number back into a letter
                cipheredInput.append(numberMap.get(number));
                letterIndex++;
            }
        }

        return cipheredInput.toString();
    }

    public static List<String> decodeTwoX(String cypher) {
        return decodeTwoX(cypher,false);
    }

    public static List<String> decodeTwoX(String cypher,boolean allPossibilities) {
        int letterIndex = 0;
        int wordIndex = 0;
        int maxCharVal = 0;
        int minCharVal = 0;
        List<List<Integer>> charValPossibilities = new ArrayList<>();
        //goes through each character of the string, and gets the potential numerical values of the original
        for(int i = 0; i < cypher.length(); i++) {
            charValPossibilities.add(new ArrayList<>());
            //sets the high bound of the letter. derived from assuming the value is "z"
            maxCharVal = 2*(25 + letterIndex + wordIndex);
            //sets the low bound of the letter. derived from assuming the value is "a"
            minCharVal = 2*(0 + letterIndex + wordIndex);
            if(cypher.charAt(i) == ' '){
                charValPossibilities.get(i).add(-1);
                letterIndex = 0;
                wordIndex++;
            }else {
                //int k = numerical value of the cypher
                int k = 0;
                k = letterMap.get(cypher.charAt(i));
                //while k is in the maximum and minimum
                while(k <= maxCharVal) {
                    if(k >= minCharVal) {
                        charValPossibilities.get(i).add(k);
                    }
                    k += 26;
                }
                letterIndex++;
            }
        }
        letterIndex = 0;
        wordIndex = -1;

        List<List<Integer>> processedValues = new ArrayList<>();
        //loops over every character
        for(int i = 0; i < charValPossibilities.size(); i++) {
            processedValues.add(new ArrayList<>());
            //loops over every character possibility
            for(int k = 0; k < charValPossibilities.get(i).size(); k++) {
                int value = charValPossibilities.get(i).get(k);
                if(value == -1) {
                    processedValues.get(i).add(value);
                    wordIndex++;
                    letterIndex = 0;
                    continue;
                }else {
                    value = (value/2) - letterIndex;
                    if(wordIndex < 0) {
                        value -=0;
                    }else {
                        value -= wordIndex;
                    }
                    if(value < 0) {
                        continue;
                    }else {
                        processedValues.get(i).add(value);
                    }
                }
            }
            letterIndex++;
        }
        List<String> result = potentialWord(processedValues, allPossibilities);
        System.out.println(result.size());
        return result;
    }

    //-1 = space
    public static List<String> potentialWord(List<List<Integer>> possibleValues, boolean debug) {
        Set<String> result = new HashSet<>();
        potentialWord(possibleValues, new StringBuilder(), result, debug);
        return new ArrayList<>(result);
    }

    public static void potentialWord(List<List<Integer>> possibleValues, StringBuilder decoded, Set<String> result, boolean debug) {
        if(!debug) {
            if (possibleValues.isEmpty() || possibleValues.get(0).size() == 1 && possibleValues.get(0).get(0) == -1) {
                // find the previous word and ensure that it's in the dictionary
                int startOfWord = decoded.lastIndexOf(" ") + 1;
                String lastWord = decoded.substring(startOfWord);
                if (!dictionary.contains(lastWord)) {
                    return;
                }
            }
        }

        if (possibleValues.isEmpty()) {
            result.add(decoded.toString());
        }
        else {
            List<Integer> nextCharValues = possibleValues.get(0);
            for (Integer value : nextCharValues) {
                // convert to value to a char
                char c = numberMap.get(value);

                // append it to the word so far
                decoded.append(c);

                // recurse with possibleValues.sublist(1)
                potentialWord(possibleValues.subList(1, possibleValues.size()), decoded, result, debug);

                // remove the last char from word
                decoded.setLength(decoded.length() - 1);
            }
        }
    }
}
