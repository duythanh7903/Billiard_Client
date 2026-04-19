package com.datn.vpp.sp26.present.user.activity.confirmation

import android.content.Intent
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.common.AppConst
import com.datn.vpp.sp26.common.base.BaseActivity
import com.datn.vpp.sp26.databinding.ActivityOrderConfirmationBinding
import dagger.hilt.android.AndroidEntryPoint
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener

@AndroidEntryPoint
class OrderConfirmationActivity: BaseActivity<ActivityOrderConfirmationBinding>() {
    override fun getLayoutActivity(): Int = R.layout.activity_order_confirmation

    override fun initViews() {
        super.initViews()

        ZaloPaySDK.init(AppConst.APP_ID_ZPDK, Environment.SANDBOX)

        ZaloPaySDK.getInstance().payOrder(this, "", "", object: PayOrderListener {
            override fun onPaymentCanceled(zpTransToken: String?, appTransID: String?) {
                //Xử lý logic khi người dùng từ chối thanh toán
            }
            override fun onPaymentError(ZalopayErrorCode: ZaloPayError?, zpTransToken: String?, appTransID: String?) {
                //Xử lý logic khi thanh toán lỗi
            }
            override fun onPaymentSucceeded(transactionId: String, transToken: String, appTransID: String?) {
                //Xử lý logic khi thanh toán thành công
            }
        })
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        ZaloPaySDK.getInstance().onResult(intent)
    }



}