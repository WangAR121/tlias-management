package heima.item.utils;

/**
 * 字符串长度截断工具。
 * operate_log 表的 method_params / return_value 是 varchar(2000)，
 * 而方法的返回值可能是几百条记录拼成的长字符串，直接入库会报
 * "Data truncation: Data too long for column"，所以入库前统一截断。
 */
public class MaxLenUtils {

    /** 数据库对应字段的最大长度 */
    private static final int MAX_LENGTH = 2000;

    /** 截断后追加的标记，提示该内容不完整 */
    private static final String ELLIPSIS = "…(内容过长已截断)";

    private MaxLenUtils() {
    }

    /**
     * 把字符串截断到能放进 varchar(2000) 的长度（含截断标记）。
     * 字符串本身不超长时原样返回，不做任何改动。
     */
    public static String truncate(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() <= MAX_LENGTH) {
            return str;
        }
        return str.substring(0, MAX_LENGTH - ELLIPSIS.length()) + ELLIPSIS;
    }
}
