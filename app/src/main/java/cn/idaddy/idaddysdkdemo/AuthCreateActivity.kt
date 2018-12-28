package cn.idaddy.idaddysdkdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import cn.idaddy.android.opensdk.lib.IDYCommon
import cn.idaddy.android.opensdk.lib.IdaddySdk
import cn.idaddy.android.opensdk.lib.auth.OnPostAuthCreateCallBack
import cn.idaddy.android.opensdk.lib.order.OnCreateOrderCallback
import cn.idaddy.android.opensdk.lib.utils.ToastUtils

class AuthCreateActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_create)

        findViewById<TextView>(R.id.content_textView)

        intent?.let {
            var redeemCode = it.getStringExtra("redeemCode")

            if (redeemCode.isNullOrBlank()) {
                redeemCode = "";
            }

            IdaddySdk.authCreateByRedeemCode(redeemCode,object :OnPostAuthCreateCallBack{
                override fun onPostAuthCreateSuccess(successRet: String?) {

                    findViewById<TextView>(R.id.content_textView).text = successRet

                    ToastUtils.showShort(IDYCommon.application,"兑换成功")
                }

                override fun onPostAuthCreateFailure(failureRet: String?) {

                    findViewById<TextView>(R.id.content_textView).text = "兑换失败："+failureRet

                    ToastUtils.showShort(IDYCommon.application,"兑换失败："+failureRet)
                }
            })
        }
    }
}