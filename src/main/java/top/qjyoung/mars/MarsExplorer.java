package top.qjyoung.mars;

/**
 * @author QiaoJianYong
 * @date 9:56 PM 2018/12/7
 * @email chinaqiaojianyong@gmail.com
 * <p>
 * 既然 是有序的 那么都用静态变量吧 (还是oop把)
 */
public class MarsExplorer {
    private Integer x, y;
    private Direction direction;
    private RectanglePlace rectanglePlace;
    
    public MarsExplorer(Integer x, Integer y, Direction direction, RectanglePlace rectanglePlace) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.rectanglePlace = rectanglePlace;
    }
    
    public void rotate(Motion motion) {
        switch (motion) {
            case R:
                switch (direction) {
                    case E:
                        direction = Direction.S;
                        break;
                    case S:
                        direction = Direction.W;
                        break;
                    case W:
                        direction = Direction.N;
                        break;
                    case N:
                        direction = Direction.E;
                        break;
                }
                break;
            case L:
                switch (direction) {
                    case E:
                        direction = Direction.N;
                        break;
                    case N:
                        direction = Direction.W;
                        break;
                    case W:
                        direction = Direction.S;
                        break;
                    case S:
                        direction = Direction.E;
                        break;
                }
                break;
        }
    }
    
    public void move() {
        x += direction.getX();
        y += direction.getY();
        if (x > rectanglePlace.getBoundX() || y > rectanglePlace.getBoundY() || y < 0 || x < 0) {
            throw new RuntimeException("掉下悬崖啦！");
        }
    }
    
    public void print() {
        System.out.println(String.format("(%s,%s,%s)", x, y, direction));
    }
    
}
