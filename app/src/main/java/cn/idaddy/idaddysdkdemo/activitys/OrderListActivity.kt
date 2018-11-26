package cn.idaddy.idaddysdkdemo.activitys

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import cn.idaddy.android.opensdk.lib.IdaddySdk
import cn.idaddy.android.opensdk.lib.order.OnGetOrderListCallback
import cn.idaddy.android.opensdk.lib.order.OrderListBean
import cn.idaddy.idaddysdkdemo.adapters.OrderLIstAdapter
import cn.idaddy.idaddysdkdemo.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_order_list_layout.*


class OrderListActivity : AppCompatActivity(){

   lateinit var  orderLIstAdapter: OrderLIstAdapter

    var list: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list_layout)

        orderLIstAdapter = OrderLIstAdapter(layoutInflater)
        order_list.adapter = orderLIstAdapter

        IdaddySdk.getOrderList(10,object : OnGetOrderListCallback {
            override fun onGetOrderSuccess(successRet: String?) {
                val orderlist =  Gson().fromJson(successRet, OrderListBean::class.java)
                for (orderbean in orderlist.list?.let { it}
                        ?: ArrayList<OrderListBean.ListBean>()) {
                    orderbean?.let { list.add(Gson().toJson(orderbean)) }
                }
                orderLIstAdapter.refresh(list)
                if (list.isEmpty()){
                    Toast.makeText(this@OrderListActivity,"暂无订单",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onGetOrderFailure(failureRet: String?) {
                 Toast.makeText(this@OrderListActivity,failureRet,Toast.LENGTH_SHORT).show()
            }
        })
    }
}