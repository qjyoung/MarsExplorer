package top.qjyoung.mars;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * @author QiaoJianYong
 * @date 9:56 PM 2018/12/7
 * @email chinaqiaojianyong@gmail.com
 * <p>
 * 既然 是有序的 那么都用静态变量吧
 */
public class MarsExplorer {
    private static Integer x, y, boundX, boundY;
    private static Direction direction;
    private static Integer readIndex = 0;
    
    public static void setBound(Integer boundX, Integer boundY) {
        MarsExplorer.boundX = boundX;
        MarsExplorer.boundY = boundY;
    }
    
    public static void init(Integer x, Integer y, Direction direction) {
        MarsExplorer.x = x;
        MarsExplorer.y = y;
        MarsExplorer.direction = direction;
    }
    
    public static void rotate(Motion motion) {
        switch (motion) {
            case R:
                switch (MarsExplorer.direction) {
                    case E:
                        MarsExplorer.direction = Direction.S;
                        break;
                    case S:
                        MarsExplorer.direction = Direction.W;
                        break;
                    case W:
                        MarsExplorer.direction = Direction.N;
                        break;
                    case N:
                        MarsExplorer.direction = Direction.E;
                        break;
                }
                break;
            case L:
                switch (MarsExplorer.direction) {
                    case E:
                        MarsExplorer.direction = Direction.N;
                        break;
                    case N:
                        MarsExplorer.direction = Direction.W;
                        break;
                    case W:
                        MarsExplorer.direction = Direction.S;
                        break;
                    case S:
                        MarsExplorer.direction = Direction.E;
                        break;
                }
                break;
        }
    }
    
    public static void move() {
        switch (MarsExplorer.direction) {
            case E:
                x += 1;
                break;
            case S:
                y -= 1;
                break;
            case W:
                x -= 1;
                break;
            case N:
                y += 1;
                break;
        }
        if (x > boundX || y > boundY || y < 0 || x < 0) {
            throw new RuntimeException("掉下悬崖啦！");
        }
    }
    
    public static void print() {
        System.out.println(String.format("(%s,%s,%s)", x, y, direction));
    }
    
    public static void test() {
        setBound(5, 5);
//        LMLMLMLMM
        init(1, 2, Direction.getInstance("N"));
        print();
        rotate(Motion.L);
        print();
        move();
        rotate(Motion.L);
        print();
        move();
        rotate(Motion.L);
        print();
        move();
        rotate(Motion.L);
        print();
        move();
        move();
        print();
        System.out.println();

//        33E MMRMMRMRRM
        init(3, 3, Direction.getInstance("E"));
        move();
        move();
        rotate(Motion.R);
        move();
        move();
        rotate(Motion.R);
        move();
        rotate(Motion.R);
        rotate(Motion.R);
        move();
        print();
    }
    
    public static void main(String[] args) throws Exception {
//        test();
        BufferedReader reader = new BufferedReader(new FileReader("src/top/qjyoung/mars/cmd.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            readIndex++;
            if (readIndex == 1) {
                String[] strings = line.trim().split(" ");
                setBound(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]));
            } else if (readIndex % 2 == 0) {
                String[] strings = line.trim().split(" ");
                init(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Direction.getInstance(strings[2]));
            } else if (readIndex % 2 != 0) {
                String[] strings = line.split("");
                for (String string : strings) {
                    if (string.equals("M")) {
                        move();
                    } else {
                        rotate(Motion.getInstance(string));
                    }
                }
                print();
            } else {
                throw new RuntimeException("the cmd.txt has wrongs, not match the regulation!");
            }
        }
    
        reader.close();
    }
}
