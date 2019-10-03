package top.qjyoung.other;

/**
 * @author QiaoJianYong
 * @date 14:05 2019/2/25
 * @email chinaqiaojianyong@gmail.com
 * @description 螺旋矩阵
 */
public class RollingMatrix {
    
    public static void main(String[] args) {
        int width = 38, height = 18;
        int[][] matrix = new int[height][width];
        fillMatrix(width, height, 0, matrix, 1);
        printMatrix(matrix, width, height);
    }
    
    public static void fillMatrix(int width, int height, int round, int[][] matrix, int num) {
        int max;
        for (int i = 0; i < 4; i++) {
            if (i == 1 || i == 3) {
                max = height - round - 1;
            } else {
                max = width - round - 1;
            }
            // 正方形 中心点
            if (height == width && max == round) {
                matrix[round][round] = num;
                return;
            }
            for (int j = round; j < max; j++) {
                if (i == 0) {
                    matrix[round][j] = num++;
                } else if (i == 1) {
                    matrix[j][width - round - 1] = num++;
                } else if (i == 2) {
                    matrix[height - round - 1][width - 1 - j] = num++;
                } else if (i == 3) {
                    matrix[height - 1 - j][round] = num++;
                }
                printMatrix(matrix, width, height);
                if (num == width * height + 1) {
                    return;
                }
            }
            if (i == 3) {
                round++;
            }
        }
        fillMatrix(width, height, round, matrix, num);
    }
    
    public static void printMatrix(int[][] matrix, int width, int height) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /*
    // in particular, width = height
    public static void fillMatrix(int width, int min, int max, int[][] matrix, int num) {
        if (num == width * width + 1) {
            return;
        }
        if (min == max) {// 奇数中心点
            matrix[min][max] = width * width;
            printMatrix(matrix, width, width);
            return;
        }
        for (int i = 0; i < 4; i++) {
            for (int j = min; j < max; j++) {
                if (i == 0) {
                    matrix[min][j] = num++;
                } else if (i == 1) {
                    matrix[j][max] = num++;
                } else if (i == 2) {
                    matrix[max][width - 1 - j] = num++;
                } else if (i == 3) {
                    matrix[width - 1 - j][min] = num++;
                }
            }
            if (i == 3) {
                max--;
                min++;
            }
        }
        printMatrix(matrix, width, width);
        fillMatrix(width, min, max, matrix, num);
    }
    */
}
