package com.haoniu.quchat.activity

import android.os.Bundle
import com.aite.chat.R
import com.haoniu.quchat.base.BaseActivity
import com.haoniu.quchat.entity.EventCenter
import com.haoniu.quchat.http.ApiClient
import com.haoniu.quchat.http.AppConfig
import com.haoniu.quchat.http.ResultListener
import kotlinx.android.synthetic.main.activity_transfer_detail.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.util.*

/**
 *@创建者 Mr.zou
 *@创建时间 2020/2/25
 *@描述
 */
class TransferDetailActivity : BaseActivity() {

    private var status: String? = null
    private var money: String? = null
    private var turnId: String? = null
    private var serialNumber: String? = null
    private var requestId: String? = null
    private var isSelf: Boolean? = false

    override fun onEventComing(center: EventCenter<*>?) {
    }

    override fun initContentView(bundle: Bundle?) {
        setContentView(R.layout.activity_transfer_detail)
    }

    override fun getBundleExtras(extras: Bundle?) {
        status = extras?.getString("status", "")
        money = extras?.getString("money", "")
        turnId = extras?.getString("turnId", "")
        serialNumber = extras?.getString("serialNumber", "")
        requestId = extras?.getString("requestId", "")

        isSelf = extras?.getBoolean("isSelf", false)
    }

    override fun initLogic() {
        toolbar_title.text = "转账"
        tv_money.text = if (money.isNullOrEmpty()) "￥ 0.00" else "￥ $money"
        viewUI()
        tv_collection.setOnClickListener {
            checkTransfer()
        }
    }

    private fun viewUI() {
        when (status) {
            "0" -> {
                tv_status.text="待确认收款"
                tv_collection.setBackgroundResource(if (isSelf == false) R.drawable.group_search else R.drawable.group_search_gray)
                tv_collection.isEnabled = isSelf == false
                img_status.setImageResource(R.drawable.daiqueren)
            }
            "1" -> {
                tv_status.text="已确认收款"
                tv_collection.setBackgroundResource(R.drawable.group_search_gray)
                tv_collection.isEnabled = isSelf == false
                img_status.setImageResource(R.drawable.yx)
            }
            "2" -> {
                tv_status.text="已退回"
                tv_collection.setBackgroundResource(R.drawable.group_search_gray)
                tv_collection.isEnabled = isSelf == false
                img_status.setImageResource(R.drawable.yx)
            }
        }
    }

    private fun confirmMoney() {
        val map = mapOf("transferId" to turnId)
        ApiClient.requestNetHandle(this, AppConfig.CONFIRM, "请稍后...", map, object : ResultListener() {
            override fun onSuccess(json: String?, msg: String?) {
                status = "1"
                viewUI()
            }

            override fun onFailure(msg: String?) {
                toast(msg)
            }
        })
    }

    /**
     * 检测转账
     *
     * @param message
     */
    private fun checkTransfer() {
        val map = mapOf("transferId" to turnId)
        ApiClient.requestNetHandle(this@TransferDetailActivity, AppConfig.TRANSFER_STATUS, "", map, object : ResultListener() {
            override fun onSuccess(json: String, msg: String) {
                val j = json.replace(".0", "")
                status = j
                when (j) {
                    "0" -> {
                        confirmMoney()
                    }
                    "1" -> {
//                        toast("已领取")
                        viewUI()
                        return
                    }
                    "2" -> {
//                        toast("金额已退回")
                        viewUI()
                        return
                    }

                }
            }

            override fun onFailure(msg: String) {
                toast(msg)
            }
        })
    }


}