package OkHttp;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * Project:           sxz
 * Author:            sxz
 * Company:           Big Player Group
 * Created Date:      2021/1/15
 * Description:   {类描述}
 * Copyright @ 2017-2021 BIGPLAYER.GROUP – Confidential and Proprietary
 */
public class AssertUtils {
    private AssertUtils() {}

    @SuppressWarnings("unchecked")
    public static <E extends Throwable> void doThrow(Throwable e) throws E {
        throw (E) e;
    }


    /**
     * 根据条件表达式抛出异常
     *
     * @param condition
     * @param errorMsg
     */
    public static void check(boolean condition, String errorMsg) {
        if (condition) {
            throw new RuntimeException(errorMsg);
        }
    }

    /**
     * 如果对象为空抛出异常
     *
     * @param object
     * @param errorMsg
     */
    public static void isNull(Object object, String errorMsg) {
        check(object == null, errorMsg);
    }

    /**
     * 如果对象不为空抛出异常
     *
     * @param object
     * @param errorMsg
     */
    public static void notNull(Object object, String errorMsg) {
        check(object != null, errorMsg);
    }

    /**
     * 如果数组为空抛出异常
     *
     * @param object
     * @param errorMsg
     */
    public static void isEmpty(Object[] object, String errorMsg) {
        check(Objects.isNull(object) || object.length == 0, errorMsg);
    }

    /**
     * 如果对象为空抛出异常
     *
     * @param object
     * @param errorMsg
     */
    public static void isEmpty(Object object, String errorMsg) {
        check(Objects.isNull(object), errorMsg);
    }

    /**
     * 如果集合为空抛出异常
     *
     * @param collection
     * @param errorMsg
     */
    public static void isEmpty(Collection<?> collection, String errorMsg) {
        check(Objects.isNull(collection) || collection.isEmpty(), errorMsg);
    }

    /**
     * 如果Map为空抛出异常
     *
     * @param map
     * @param errorMsg
     */
    public static void isEmpty(Map<?, ?> map, String errorMsg) {
        check(Objects.isNull(map) || map.isEmpty(), errorMsg);
    }

}
