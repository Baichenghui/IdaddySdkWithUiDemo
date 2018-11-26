package cn.idaddy.idaddysdkdemo.bean

import cn.idaddy.android.opensdk.lib.api.IDYBaseBean

/**
 * Created by journey on 2018/7/27.
 */
class OrderParamsBean : IDYBaseBean() {

    /**
     * {"orderID":"3477044","paySysID":"pay.idaddy","params":{"sysId":"koudaistory","appId":"idaddy999","tradeNo":"3477044","tradeTs":1538105612,"goodsId":"2610","goodsDesc":"3个月VIP会员","amount":1,"userId":"12703924","deviceId":"null_020000000000","extra":"xiguapi_inside","ts":1538105619,"sign":"7238a731ea94ea31e44f90b35966287a","signType":"md5"}}
     */
    var orderID: String? = null
    var params: ParamsBean? = null

    class ParamsBean {
        /**
         * sysId : koudaistory
         * appId : eiot001
         * tradeNo : 1793159
         * tradeTs : 1532659846
         * goodsId : 2514
         * goodsDesc : 12个月VIP会员（送3个月）
         * amount : 1
         * userId : 5517781
         * deviceId : null_020000000000
         * extra : aps_android
         * ts : 1532659846
         * sign : a4a0714ea6e57f6ca2971f7224f0bde5
         * signType : md5
         */

        var sysId: String? = null
        var appId: String? = null
        var tradeNo: String? = null
        var tradeTs: Int = 0
        var goodsId: String? = null
        var goodsDesc: String? = null
        var amount: Int = 0
        var userId: String? = null
        var deviceId: String? = null
        var extra: String? = null
        var ts: Int = 0
        var sign: String? = null
        var signType: String? = null
    }
}
