package top.qjyoung.mars;

/**
 * @author QiaoJianYong
 * @date 1:27 AM 2018/12/8
 * @email chinaqiaojianyong@gmail.com
 */
public class RectanglePlace {
    private Integer boundX, boundY;
    
    public RectanglePlace(Integer boundX, Integer boundY) {
        this.boundX = boundX;
        this.boundY = boundY;
    }
    
    public Integer getBoundX() {
        return boundX;
    }
    
    public void setBoundX(Integer boundX) {
        this.boundX = boundX;
    }
    
    public Integer getBoundY() {
        return boundY;
    }
    
    public void setBoundY(Integer boundY) {
        this.boundY = boundY;
    }
}
