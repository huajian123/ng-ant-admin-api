package result;

/**
 * 类功能描述: 定义常量
 *
 * @author fbl
 * @date 2020/6/1 9:00
 */
public class CommonConstants {

    /**
     * Result Code状态
     */
    public static class ResultCodeStatus {
        /**
         * RESULT_SUCCESS*
         */
        public final static int RESULT_SUCCESS = 0;

        /**
         * RESULT_FAILURE
         */
        public final static int RESULT_FAILURE = -1;

    }

    /**
     * Result Message
     */
    public static class ResultCodeMessage {
        /**
         * RESULT_SUCCESS_MESSAGE
         */
        public final static String RESULT_SUCCESS_MESSAGE = "SUCCESS";

        /**
         * RESULT_FAILURE_MESSAGE
         */
        public final static String RESULT_FAILURE_MESSAGE = "FAILURE";

    }

    /**
     * Delete Code状态
     */
    public static class DeleteCodeStatus {
        /**
         * IS_NOT_DELETE
         */
        public final static int IS_NOT_DELETE = 0;

        /**
         * IS_DELETE
         */
        public final static int IS_DELETE = 1;

    }

    /**
     * operation Code状态
     */
    public static class DataBaseOperationStatus {
        /**
         * IS_NOT_DELETE
         */
        public final static int IS_SUCCESS = 1;

        /**
         * IS_DELETE
         */
        public final static int IS_FAILURE = 0;

    }

}
