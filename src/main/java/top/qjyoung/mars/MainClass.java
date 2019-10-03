package top.qjyoung.mars;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * @author QiaoJianYong
 * @date 1:31 AM 2018/12/8
 * @email chinaqiaojianyong@gmail.com
 */
public class MainClass {
    public static void test() {
//        LMLMLMLMM
        MarsExplorer marsExplorer = new MarsExplorer(1, 2, Direction.getInstance("N"), new RectanglePlace(5, 5));
        marsExplorer.print();
        marsExplorer.rotate(Motion.L);
        marsExplorer.print();
        marsExplorer.move();
        marsExplorer.rotate(Motion.L);
        marsExplorer.print();
        marsExplorer.move();
        marsExplorer.rotate(Motion.L);
        marsExplorer.print();
        marsExplorer.move();
        marsExplorer.rotate(Motion.L);
        marsExplorer.print();
        marsExplorer.move();
        marsExplorer.move();
        marsExplorer.print();
        System.out.println();

//        33E MMRMMRMRRM
        MarsExplorer marsExplorer2 = new MarsExplorer(3, 3, Direction.getInstance("E"), new RectanglePlace(5, 5));
        marsExplorer2.move();
        marsExplorer2.move();
        marsExplorer2.rotate(Motion.R);
        marsExplorer2.move();
        marsExplorer2.move();
        marsExplorer2.rotate(Motion.R);
        marsExplorer2.move();
        marsExplorer2.rotate(Motion.R);
        marsExplorer2.rotate(Motion.R);
        marsExplorer2.move();
        marsExplorer2.print();
    }
    
    public static void main(String[] args) throws Exception {
//        test();
//        System.out.println();
        
        BufferedReader reader = new BufferedReader(new FileReader("src/main/java/top/qjyoung/mars/cmd.txt"));
        String line = reader.readLine();
        MarsExplorer marsExplorer;
        String[] strings_1 = line.trim().split(" ");
        RectanglePlace rectanglePlace = new RectanglePlace(Integer.parseInt(strings_1[0]), Integer.parseInt(strings_1[1]));
        while ((line = reader.readLine()) != null) {
            String[] strings_2 = line.trim().split(" ");
            marsExplorer = new MarsExplorer(Integer.parseInt(strings_2[0]), Integer.parseInt(strings_2[1]), Direction.getInstance(strings_2[2]), rectanglePlace);
            line = reader.readLine();
            String[] strings_3 = line.split("");
            for (String string : strings_3) {
                if (string.equals("M")) {
                    marsExplorer.move();
                } else {
                    marsExplorer.rotate(Motion.getInstance(string));
                }
            }
            marsExplorer.print();
        }
        
        reader.close();
    }
}
