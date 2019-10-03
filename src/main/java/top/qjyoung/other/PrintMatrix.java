package top.qjyoung.other;

import java.util.Arrays;

/**
 * @author QiaoJianYong
 * @date 10:07 PM 2018/12/11
 * @email chinaqiaojianyong@gmail.com
 * <p>
 * require
 * 1 2 3 4 5 6 7 8 9 10
 * 20 19 18 17 16 15 14 13 12 11
 * 21 22 23 24 25 26 27 28 29 30
 * 40 39 38 37 36 35 34 33 32 31
 * 41 42 43 44 45 46 47 48 49 50
 * 60 59 58 57 56 55 54 53 52 51
 * 61 62 63 64 65 66 67 68 69 70
 * 80 79 78 77 76 75 74 73 72 71
 * 81 82 83 84 85 86 87 88 89 90
 * 100 99 98 97 96 95 94 93 92 91
 */
public class PrintMatrix {
    public static void main(String[] args) {
        int num = 1;
        int[][] matrix = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i % 2 == 0) {
                    matrix[i][j] = num++;
                } else {
                    matrix[i][9 - j] = num++; // 9!
                }
            }
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        String str = "abc";
        char charArr[] = {'1', '2', 'a'};
        
        System.out.println(charArr);
        System.out.println(charArr.toString());
        System.out.println(charArr + "");
        System.out.println(str + " and " + charArr);
        System.out.println(str + " and " + Arrays.toString(charArr));
        System.out.println(Arrays.toString(charArr));
    }
}