package cn.idaddy.idaddysdkdemo

import android.app.Application
import android.os.AsyncTask
import android.widget.Toast
import cn.idaddy.android.opensdk.lib.IDYPayInterface
import cn.idaddy.android.opensdk.lib.IdaddySdk
import cn.idaddy.android.opensdk.lib.api.IDYTokenInterface
import cn.idaddy.android.opensdk.lib.api.OnTaskCallback
import cn.idaddy.android.opensdk.lib.user.PostIdaddyTicketTask
import cn.idaddy.idaddysdkdemo.bean.IdaddyTicketBean
import com.google.gson.Gson

/**
 * Created by journey on 2018/8/1.
 */
class MyApplication : Application() , IDYTokenInterface, IDYPayInterface {
    override fun idyStartPayment(paymentInfo: String) {
        // 用户发起支付请求   包含支付信息
        Toast.makeText(this,paymentInfo,Toast.LENGTH_SHORT).show()
    }
    override fun idyNeedAccessToken() {
        Toast.makeText(this,"静默登录中",Toast.LENGTH_SHORT).show()

        PostIdaddyTicketTask("yourAppid", TestConfig.uniquedId,
                "", (System.currentTimeMillis() / 1000).toString(), null, object : OnTaskCallback {
            override fun onStart() {

            }

            override fun onSuccess(successRet: Any?) {
                val idaddyTicketBean = Gson().fromJson(successRet as String, IdaddyTicketBean::class.java)
                idaddyTicketBean?.data?.let {
                    IdaddySdk.genIDYAccessToken(it.ticket)
                }
            }

            override fun onError(failureRet: Any?) {

            }
        }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    override fun onCreate() {
        super.onCreate()
        IdaddySdk.init(this, true,false)
        IdaddySdk.setIDYTokenInterface(this)
    }
}
