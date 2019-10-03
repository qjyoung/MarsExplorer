package top.qjyoung.mars;

/**
 * @author QiaoJianYong
 * @date 9:58 PM 2018/12/7
 * @email chinaqiaojianyong@gmail.com
 */
public enum Motion {
    L,
    R,
    ;
    
    public static Motion getInstance(String name) {
        for (Motion motion : Motion.values()) {
            if (motion.name().equals(name)) {
                return motion;
            }
        }
        throw new RuntimeException("not found!");
    }
}
