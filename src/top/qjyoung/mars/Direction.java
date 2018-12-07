package top.qjyoung.mars;

/**
 * @author QiaoJianYong
 * @date 9:29 PM 2018/12/7
 * @email chinaqiaojianyong@gmail.com
 */
public enum Direction {
    // 上北下南 左东右西
    E("E", 1, 0),
    S("S", 0, -1),
    W("W", -1, 0),
    N("N", 0, 1);
    
    private String name;
    private Integer x;
    private Integer y;
    
    Direction(String name, Integer x, Integer y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }
    
    public Integer getX() {
        return x;
    }
    
    public Integer getY() {
        return y;
    }
    
    public String getName() {
        return name;
    }
    
    public static Direction getInstance(String name) {
        for (Direction direction : Direction.values()) {
            if (direction.getName().equals(name)) {
                return direction;
            }
        }
        throw new RuntimeException("not found!");
    }
}
