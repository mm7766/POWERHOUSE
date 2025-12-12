package practice;

import java.util.*;

public class StringAssessment {
    private List<String> dictionary;

    public StringAssessment(List<String> words) {
        this.dictionary = new ArrayList<>(words);
        Collections.sort(this.dictionary);
    }

    public List<String> suggest(String typed) {
        typed = typed.toLowerCase();
        List<String> result = new ArrayList<>();

        int bestDist = Integer.MAX_VALUE;

        for (String word : dictionary) {
            String w = word.toLowerCase();
            int dist = editDistance(typed, w);

            if (dist < bestDist) {
                bestDist = dist;
                result.clear();
                result.add(word);
            } else if (dist == bestDist) {
                result.add(word);
            }

       
            if (result.size() >= 3 && dist > bestDist) {
                break; 
            }
        }

      
        return result.size() <= 3 ? result : result.subList(0, 3);
    }

    private int editDistance(String a, String b) {
        int m = a.length(), n = b.length();
        int[] dp = new int[n + 1];

        for (int j = 0; j <= n; j++) dp[j] = j;

        for (int i = 1; i <= m; i++) {
            int prev = dp[0];
            dp[0] = i;
            for (int j = 1; j <= n; j++) {
                int temp = dp[j];
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[j] = prev;
                } else {
                    dp[j] = 1 + Math.min(dp[j], Math.min(dp[j - 1], prev));
                }
                prev = temp;
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        List<String> dict = Arrays.asList("hello", "help", "helping", "helo", "yellow", "shell", "bell", "hell");
        StringAssessment ac = new StringAssessment(dict);

        System.out.println(ac.suggest("helo"));  
        System.out.println(ac.suggest("yello")); 
        System.out.println(ac.suggest("grok"));  
    }
}