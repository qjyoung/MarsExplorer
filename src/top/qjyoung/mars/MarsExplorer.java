package top.qjyoung.mars;

/**
 * @author QiaoJianYong
 * @date 9:56 PM 2018/12/7
 * @email chinaqiaojianyong@gmail.com
 * <p>
 * 既然 是有序的 那么都用静态变量吧
 */
public class MarsExplorer {
    private static Integer x, y;
    private static Direction direction;
    
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
    }
    
    public static void print() {
        System.out.println(String.format("(%s,%s,%s)", x, y, direction));
    }
    
    public static void main(String[] args) {
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
}
