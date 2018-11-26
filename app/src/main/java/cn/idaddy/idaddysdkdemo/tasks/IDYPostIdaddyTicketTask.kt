package cn.idaddy.idaddysdkdemo.tasks

import android.os.AsyncTask
import cn.idaddy.android.opensdk.lib.IDYConfig
import cn.idaddy.android.opensdk.lib.utils.MD5Util

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response


/**
 * appId	string(20)	√	应用Id
 * uniqueId	string(50)	√	合作方用户的唯一标识
 * mobileNo	string(11)	×	用户的手机号
 * ts	string	√	时间戳，单位：秒
 * sign	string	√	签名串，具体签名规则参见 签名机制
 * signType	string	×  签名方式，目前默认且只有MD5
 */
abstract class IDYPostIdaddyTicketTask(private val appId: String,
                                       private val uniqueId: String,
                                       private val mobileNo: String,
                                       private val ts: String,
                                       private val appkey: String) : AsyncTask<Void, Void, String>() {

    override fun onPreExecute() {
        super.onPreExecute()
        onStart()
    }

    abstract fun onStart()

    override fun doInBackground(vararg params: Void): String? {
        var response: Response? = null
        try {
            response = requestExe()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response?.body()?.string()
    }

    override fun onPostExecute(response: String?) {
        super.onPostExecute(response)
        if (response.isNullOrBlank()) {
            onError("response.isNullOrBlank")
            return
        }
        onSuccess(response!!)
    }

    abstract fun onSuccess(successRet: String)

    abstract fun onError(failureRet: String?)

    @Throws(Exception::class)
    fun requestExe(): Response {
        val formBody = FormBody.Builder()
                .add("appId", appId)
                .add("uniqueId", uniqueId)
                .add("mobileNo", mobileNo)
                .add("ts", ts)
                .add("sign", genSign())
                .build()
        val request = Request.Builder()
                .url(IDYConfig.BASE_URL + "/v3/outer/user/ticket")
                .post(formBody)
                .build()

        return OkHttpClient().newCall(request).execute()
    }

    private fun genSign(): String {
        val valueBuffer = StringBuffer()
                .append("appId=", appId)
                .append("&mobileNo=", mobileNo)
                .append("&ts=", ts)
                .append("&uniqueId=", uniqueId)
                .append(appkey)
        return MD5Util.getMD5String(valueBuffer.toString())
    }

}
