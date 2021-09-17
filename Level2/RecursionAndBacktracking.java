package Level2;

import java.util.*;

public class RecursionAndBacktracking {
    public static void abbrevations(String str, String asf, int count, int pos) {
        if (pos == str.length()) {
            if (count == 0)
                System.out.println(asf);
            else
                System.out.println(asf + count);
            return;
        }

        String yasf = "";
        if (count == 0)
            yasf = asf + str.charAt(pos) + "";
        else
            yasf = asf + count + str.charAt(pos) + "";
        abbrevations(str, yasf, 0, pos + 1); // yes

        abbrevations(str, asf, count + 1, pos + 1); // no

    }

    public static void nQueens(boolean[][] board, int r, boolean[] cols, boolean[] ndia, boolean[] rdia, String asf) {
        if (r == board.length) {
            System.out.println(asf + ".");
            return;
        }

        for (int c = 0; c < board.length; c++) {
            if (cols[c] == false && ndia[r + c] == false && rdia[r - c + board.length - 1] == false) {

                board[r][c] = true;
                cols[c] = true;
                ndia[r + c] = true;
                rdia[r - c + board.length - 1] = true;

                nQueens(board, r + 1, cols, ndia, rdia, asf + r + "-" + c + ", ");

                board[r][c] = false;
                cols[c] = false;
                ndia[r + c] = false;
                rdia[r - c + board.length - 1] = false;

            }
        }
    }

    public static int maxScore(String[] words, int[] farr, int[] score, int idx) {
        if (idx == words.length) {
            return 0;
        }

        int sno = 0 + maxScore(words, farr, score, idx + 1);

        int sword = 0;
        boolean flag = true; // if yes call can be made
        String word = words[idx];

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);

            if (farr[ch - 'a'] == 0) {
                flag = false;
            }

            farr[ch - 'a']--;
            sword += score[ch - 'a'];
        }

        int syes = 0;
        if (flag) {
            syes = sword + maxScore(words, farr, score, idx + 1);
        }

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            farr[ch - 'a']++;
        }

        return Math.max(sno, syes);
    }

    public static int josephusProblem(int n, int k) {
        if (n == 1)
            return 0;

        int nowind = josephusProblem(n - 1, k);
        int prevind = (nowind + k) % n;
        return prevind;
    }

    public static void lexixographical(int n) {
        for (int i = 1; i <= 9; i++) {
            if (i < n)
                lexiPrint(i, n);
        }
    }

    public static void lexiPrint(int no, int n) {
        if (no > n)
            return;

        System.out.println(no);
        for (int i = 0; i <= 9; i++) {
            lexiPrint((no * 10) + i, n);
        }
    }

    public static void goldMine2(int[][] arr) {

        int maxSum = 0;
        boolean[][] visited = new boolean[arr.length][arr[0].length];

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (arr[i][j] != 0 || visited[i][j] == false) {
                    ArrayList<Integer> bag = new ArrayList<>();
                    travelAndCollectGold(arr, i, j, visited, bag);

                    int sum = 0;
                    for (int val : bag)
                        sum += val;

                    maxSum = Math.max(sum, maxSum);
                }
            }
        }
    }

    public static void travelAndCollectGold(int[][] arr, int i, int j, boolean[][] visited, ArrayList<Integer> bag) {
        if (i < 0 || j < 0 || i >= arr.length || j >= arr[0].length || arr[i][j] == 0 || visited[i][j] == true)
            return;

        visited[i][j] = true;
        bag.add(arr[i][j]);
        travelAndCollectGold(arr, i - 1, j, visited, bag);
        travelAndCollectGold(arr, i, j - 1, visited, bag);
        travelAndCollectGold(arr, i + 1, j, visited, bag);
        travelAndCollectGold(arr, i, j + 1, visited, bag);

    }

    public static void solveSudoku(int[][] board, int i, int j) {
        if (i == board.length) {
            // display
            return;
        }

        int ni = 0, nj = 0;
        if (j == board[0].length - 1) {
            ni = i + 1;
            nj = 0;
        } else {
            ni = i;
            nj = j + 1;
        }

        if (board[i][j] == 0) {
            for (int po = 1; po <= 9; po++) { // possible options
                if (isValid(board, i, j, po)) {
                    board[i][j] = po;
                    solveSudoku(board, ni, nj);
                    board[i][j] = 0;
                }
            }
        } else
            solveSudoku(board, ni, nj);
    }

    public static boolean isValid(int[][] board, int i, int j, int val) {
        for (int r = 0; r < board.length; r++) {
            if (board[r][j] == val)
                return false;
        }

        for (int c = 0; c < board[0].length; c++) {
            if (board[i][c] == val)
                return false;
        }

        // submatrix
        int si = 3 * (i / 3);
        int sj = 3 * (j / 3);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[si + r][sj + c] == val)
                    return false;
            }
        }

        return true;
    }

    public static void solution(char[][] arr, String[] words, int vidx) {
        if (vidx == words.length) {
            // display
            return;
        }

        String word = words[vidx];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {

                if (arr[i][j] == '-' || arr[i][j] == word.charAt(0)) {

                    if (canPlaceHorizontally(arr, word, i, j)) {
                        boolean[] wePlaced = placeHorizontally(arr, word, i, j);
                        solution(arr, words, vidx + 1);
                        unPlaceHorizontally(arr, wePlaced, i, j);
                    }

                    if (canPlaceVertically(arr, word, i, j)) {
                        boolean[] wePlaced = placeVertically(arr, word, i, j);
                        solution(arr, words, vidx + 1);
                        unPlaceVertically(arr, wePlaced, i, j);
                    }
                }
            }
        }
    }

    public static boolean canPlaceHorizontally(char[][] arr, String word, int i, int j) {
        if (j - 1 >= 0 && arr[i][j - 1] != '+') {
            return false;
        } else if (j + word.length() < arr[0].length && arr[i][j + word.length()] != '+') {
            return false;
        }

        for (int k = 0; k < word.length(); k++) {
            if (j + k >= arr[0].length) {
                return false;
            }
            if (arr[i][j + k] == '-' || arr[i][j + k] == word.charAt(k)) {
                continue;
            } else {
                return false;
            }
        }

        return true;
    }

    public static boolean[] placeHorizontally(char[][] arr, String word, int i, int j) {
        boolean[] wePlaced = new boolean[word.length()];
        for (int k = 0; k < word.length(); k++) {
            if (arr[i][j + k] == '-') {
                arr[i][j + k] = word.charAt(k);
                wePlaced[k] = true;
            }

        }
        return wePlaced;
    }

    public static void unPlaceHorizontally(char[][] arr, boolean[] wePlaced, int i, int j) {
        for (int k = 0; k < wePlaced.length; k++) {
            if (wePlaced[k]) {
                arr[i][j + k] = '-';
            }
        }
    }

    public static boolean canPlaceVertically(char[][] arr, String word, int i, int j) {
        if (i - 1 >= 0 && arr[i - 1][j] != '+') {
            return false;
        } else if (i + word.length() < arr.length && arr[i + word.length()][j] != '+') {
            return false;
        }

        for (int k = 0; k < word.length(); k++) {
            if (i + k >= arr.length) {
                return false;
            }
            if (arr[i + k][j] == '-' || arr[i + k][j] == word.charAt(k)) {
                continue;
            } else {
                return false;
            }
        }

        return true;
    }

    public static boolean[] placeVertically(char[][] arr, String word, int i, int j) {
        boolean[] wePlaced = new boolean[word.length()];
        for (int k = 0; k < word.length(); k++) {
            if (arr[i + k][j] == '-') {
                arr[i + k][j] = word.charAt(k);
                wePlaced[k] = true;
            }

        }
        return wePlaced;
    }

    public static void unPlaceVertically(char[][] arr, boolean[] wePlaced, int i, int j) {
        for (int k = 0; k < wePlaced.length; k++) {
            if (wePlaced[k]) {
                arr[i + k][j] = '-';
            }
        }
    }

    public static int getNum(String s, HashMap<Character, Integer> charIntMap) {
        int n = 0, pow = 1;
        for (int i = s.length() - 1; i >= 0; i--) {
            n += charIntMap.get(s.charAt(i)) * pow;
            pow *= 10;
        }
        return n;
    }

    public static void cryptarithmetic(String unique, int idx, HashMap<Character, Integer> charIntMap,
            boolean[] usedNumbers, String s1, String s2, String s3) {

        if (idx == unique.length()) {
            if (getNum(s1, charIntMap) + getNum(s2, charIntMap) == getNum(s3, charIntMap)) {
                for (int i = 0; i < 26; i++) {
                    char c = (char) ('a' + i);
                    if (charIntMap.containsKey(c)) {
                        System.out.print(c + "-" + charIntMap.get(c) + " ");
                    }
                }
                System.out.println();
            }
            return;
        }

        char ch = unique.charAt(idx);
        for (int i = 0; i < 10; i++) {
            if (!usedNumbers[i]) {
                charIntMap.put(ch, i);
                usedNumbers[i] = true;
                cryptarithmetic(unique, idx + 1, charIntMap, usedNumbers, s1, s2, s3);
                usedNumbers[i] = false;
                charIntMap.put(ch, -1);
            }

        }
    }

    static int counter = 1;

    public static void friendsPairing(int i, int n, boolean[] used, String asf) {
        if (i > n) {
            System.out.println(counter + "." + asf);
            counter++;
            return;
        }

        if (used[i]) {
            friendsPairing(i + 1, n, used, asf);

        } else {
            used[i] = true;
            friendsPairing(i + 1, n, used, asf + "(" + i + ") ");
            for (int j = i + 1; j <= n; j++) {
                if (!used[j]) {
                    used[j] = true;
                    friendsPairing(i + 1, n, used, asf + "(" + i + "," + j + ") ");
                    used[j] = false;
                }

            }
            used[i] = false;
        }
    }

    static int count = 1;

    public static void kPartitions(int i, int n, int k, int cones, ArrayList<ArrayList<Integer>> ans) { // cones- count
                                                                                                        // of
                                                                                                        // non empty
                                                                                                        // sets
        if (i > n) {
            if (cones == k) {
                System.out.print(count + ". ");
                for (ArrayList<Integer> an : ans) {
                    System.out.print(an + " ");
                }
                System.out.println();
                count++;
            }
            return;
        }

        for (int j = 0; j < ans.size(); j++) {
            if (ans.get(j).size() > 0) {
                ans.get(j).add(i);
                kPartitions(i + 1, n, k, cones, ans);
                ans.get(j).remove(ans.get(j).size() - 1);
            } else {
                ans.get(j).add(i);
                kPartitions(i + 1, n, k, cones + 1, ans);
                ans.get(j).remove(ans.get(j).size() - 1);
                break;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        String str = scn.next();
        HashMap<Character, Integer> fmap = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            fmap.put(ch, fmap.getOrDefault(ch, 0) + 1);
        }

        Character odd = null;
        int len = 0, odds = 0;

        for (char c : fmap.keySet()) {
            int freq = fmap.get(c);

            if (freq % 2 == 1) {
                odds++;
                odd = c;
            }

            fmap.put(c, freq / 2);
            len += freq / 2;
        }
        if (odds > 1) {
            System.out.println("-1");
            return;
        }
        generatepw(1, len, fmap, odd, "");

    }

    public static void generatepw(int cs, int ts, HashMap<Character, Integer> fmap, Character oddc, String asf) {
        if (cs > ts) {
            String rev = "";
            for (int i = asf.length() - 1; i >= 0; i--) {
                rev += asf.charAt(i);
            }

            String res = asf;
            if (oddc != null)
                res += oddc;
            res += rev;

            System.out.println(res);
            return;
        }

        for (char ch : fmap.keySet()) {
            int freq = fmap.get(ch);
            if (freq > 0) {
                fmap.put(ch, freq - 1);
                generatepw(cs + 1, ts, fmap, oddc, asf + ch);
                fmap.put(ch, freq);
            }
        }
    }

    public static void palindromicPartitions(String str, String asf) {
        if (str.length() == 0) {
            System.out.println(asf + " ");
            return;
        }
        for (int i = 1; i <= str.length(); i++) {
            String ss = str.substring(0, i);
            if (isPalindrome(ss)) {
                palindromicPartitions(str.substring(i), asf + "(" + ss + ") ");
            }
        }

    }

    public static boolean isPalindrome(String s) {
        int i = 0, j = s.length() - 1;
        while (i < j) {

            if (s.charAt(i) != s.charAt(j))
                return false;
            i++;
            j--;
        }
        return true;
    }

    public static void kSSWithEqualSum(int[] arr, int i, int n, int k, int[] subsetSum, int ssssf,
            ArrayList<ArrayList<Integer>> ans) {
        if (i == arr.length) {
            if (ssssf == k) {
                boolean flag = true;
                for (int j = 0; j < subsetSum.length - 1; j++) {
                    if (subsetSum[j] != subsetSum[j + 1]) {
                        flag = false;
                        break;
                    }
                }

                if (flag) {
                    for (ArrayList<Integer> an : ans) {
                        System.out.print(an + " ");
                    }
                    System.out.println();
                }
            }
            return;
        }

        for (int j = 0; j < ans.size(); j++) {
            if (ans.get(j).size() > 0) {
                ans.get(j).add(arr[i]);
                subsetSum[j] += arr[i];
                kSSWithEqualSum(arr, i + 1, n, k, subsetSum, ssssf, ans);
                subsetSum[j] -= arr[i];
                ans.get(j).remove(ans.get(j).size() - 1);
            } else {
                ans.get(j).add(arr[i]);
                subsetSum[j] += arr[i];
                kSSWithEqualSum(arr, i + 1, n, k, subsetSum, ssssf + 1, ans);
                subsetSum[j] -= arr[i];
                ans.get(j).remove(ans.get(j).size() - 1);
                break;
            }
        }
    }

    static int mindiff = Integer.MAX_VALUE;
    static String ans = "";

    public static void tugOfwar(int[] arr, int i, ArrayList<Integer> set1, ArrayList<Integer> set2, int soset1, int soset2) {

        if (i == arr.length) {
            int diff = Math.abs(soset1 - soset2);
            if (diff < mindiff) {
                mindiff = diff;
                ans = set1 + " " + set2;
            }

            return;
        }

        if (set1.size() < (arr.length + 1) / 2) {
            set1.add(arr[i]);
            tugOfwar(arr, i + 1, set1, set2, soset1 + arr[i], soset2);
            set1.remove(set1.size() - 1);
        }

        if (set2.size() < (arr.length + 1) / 2) {
            set2.add(arr[i]);
            tugOfwar(arr, i + 1, set1, set2, soset1, soset2 + arr[i]);
            set2.remove(set2.size() - 1);
        }

    }

    public static void PatternMatching(String str, String pattern, HashMap<Character, String> map, String op) {

        if (pattern.length() == 0) {
          if (str.length() == 0) {
    
            HashSet<Character> hs = new HashSet<>();
            for (int i = 0; i < op.length(); i++) {
              char ch = op.charAt(i);
              if (!hs.contains(ch)) {
                System.out.print(ch + " -> " + map.get(ch) + ", ");
                hs.add(ch);
              }
            }
            System.out.println(".");
          }
          return;
        }
    
        char ch = pattern.charAt(0);
        String rop = pattern.substring(1);
    
        if (map.containsKey(ch)) {
          String prev = map.get(ch);
    
          if (str.length() >= prev.length()) {
            if (prev.equals(str.substring(0, prev.length()))) {
                PatternMatching(str.substring(prev.length()), rop, map, op);
            }
          }
    
        } else {
          for (int i = 1; i <= str.length(); i++) {
            String ss = str.substring(0, i);
            map.put(ch, ss);
            PatternMatching(str.substring(i), rop, map, op);
            map.remove(ch);
          }
        }
      }
    
    public static void wordBreak1(String str, String ans, HashSet<String> dict) {
        if (str.length() == 0) {
          System.out.println(ans);
          return;
        }
    
        for (int i = 0; i <= str.length(); i++) {
          String l = str.substring(0, i);
          if (dict.contains(l)) {
            String r = str.substring(i);
            wordBreak1(r, ans + l + " ", dict);
          }
    
        }
      }
    
    public static void removeInvalidParenthesis(String str, int mra, HashSet<String> ans) {
        if (mra == 0) {   // minimun removals allowed
          if (getMin(str) == 0) {
            if (!ans.contains(str)) {
              System.out.println(str);
              ans.add(str);
            }
    
          }
          return;
        }
    
        for (int i = 0; i < str.length(); i++) {
          String l = str.substring(0, i);
          String r = str.substring(i + 1);
          removeInvalidParenthesis(l + r, mra - 1, ans);
    
        }
    
      }
    
    public static int getMin(String str) {
        Stack<Character> st = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
          char ch = str.charAt(i);
    
          if (ch == ')' && st.size() == 0){ st.push(ch);}
          else if (ch == ')' && st.peek() == '('){ st.pop(); }
          else{ st.push(ch);}
        }
    
        return st.size();
      }

    static String max;
    public static void findMaximumAfterKSwaps(String str, int k) {
        if (Integer.parseInt(str) > Integer.parseInt(max)) max = str;
        if (k == 0) {
          return;
        }
    
        for (int i = 0; i < str.length() - 1; i++) {
          for (int j = i + 1; j < str.length(); j++) {
            if (str.charAt(j) > str.charAt(i)) {
              str = swap(str, i, j);
              findMaximumAfterKSwaps(str, k - 1);
              str = swap(str, i, j);
            }
          }
        }
    
      }
    
    public static String swap(String s, int i, int j) {
        String ans = "";
        String l = s.substring(0, i);
        String m = s.substring(i + 1, j);
        String r = s.substring(j + 1);
    
        return l + s.charAt(j) + m + s.charAt(i) + r;
      }

     
}
