package cn.idaddy.idaddysdkdemo.bean

/**
 * Created by journey on 2018/8/13.
 */
class IdaddyTicketBean {

    /**
     * result : 0
     * data : {"ticket":"9Khqzr3fQjyFU2ft_c0dKw","expireAt":"2018-08-13T11:07:55Z"}
     */

    var result: Int = 0
    var data: DataBean? = null

    class DataBean {
        /**
         * ticket : 9Khqzr3fQjyFU2ft_c0dKw
         * expireAt : 2018-08-13T11:07:55Z
         */

        var ticket: String? = null
        var expireAt: String? = null
    }
}
