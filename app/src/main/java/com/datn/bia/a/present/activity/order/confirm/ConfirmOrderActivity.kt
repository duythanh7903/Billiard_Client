package com.datn.bia.a.present.activity.order.confirm

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import androidx.activity.viewModels
import com.datn.bia.a.R
import com.datn.bia.a.common.AppConst
import com.datn.bia.a.common.MethodPayment
import com.datn.bia.a.common.base.BaseActivity
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.common.base.ext.formatVND
import com.datn.bia.a.common.payment.Api.CreateOrder
import com.datn.bia.a.data.storage.SharedPrefCommon
import com.datn.bia.a.databinding.ActivityConfirmOrderBinding
import com.datn.bia.a.domain.model.domain.Cart
import com.datn.bia.a.domain.model.dto.res.ResLoginUserDTO
import com.datn.bia.a.present.dialog.MessageDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener


@AndroidEntryPoint
class ConfirmOrderActivity : BaseActivity<ActivityConfirmOrderBinding>() {

    private var gson: Gson? = null
    private var cartConfirmAdapter: CartConfirmAdapter? = null
    private var messageDialog: MessageDialog? = null

    private var paymentMethod = MethodPayment.CASH_ON_DELIVERY.name

    private val viewModel: ConfirmViewModel by viewModels()

    override fun getLayoutActivity(): Int = R.layout.activity_confirm_order

    override fun initViews() {
        super.initViews()

        initZaloPay()
        gson = Gson()
        paymentMethod = intent.getStringExtra(AppConst.KEY_PAYMENT_METHOD) ?: paymentMethod
        cartConfirmAdapter = CartConfirmAdapter(
            contextParams = this@ConfirmOrderActivity,
        ).apply {
            val listCart = gson?.fromJson<List<Cart>>(intent.getStringExtra(AppConst.KEY_LIST_CART), object : TypeToken<List<Cart>>() {}.type) ?: emptyList()
            submitData(listCart)
        }
        binding.rcvOrder.adapter = cartConfirmAdapter
        messageDialog = MessageDialog(
            contextParam = this,
            onSend = { message ->

            }
        )
        setData()
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.btnPay.click {
            paymentOrder()
        }

        binding.icBack.click {
            finish()
        }

        binding.btnShowMessage.click {
            messageDialog?.show()
        }
    }

    override fun onDestroy() {
        gson = null
        cartConfirmAdapter?.list?.clear()
        cartConfirmAdapter = null
        messageDialog?.dismiss()
        messageDialog = null

        super.onDestroy()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        ZaloPaySDK.getInstance().onResult(intent)
    }

    private fun initZaloPay() {
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        // ZaloPay SDK Init
        ZaloPaySDK.init(553, Environment.SANDBOX)
    }

    private fun paymentOrder() {
        val orderApi = CreateOrder()

        try {
            val data = orderApi.createOrder(50_000.toString())
            val code = data.getString("returncode")

            if (code == "1") {
                val token = data.getString("zptranstoken")
                Log.d("duylt", "Token: $token")
                ZaloPaySDK.getInstance().payOrder(
                    this, token, "demozpdk://app", object : PayOrderListener {
                        override fun onPaymentSucceeded(
                            p0: String?,
                            p1: String?,
                            p2: String?
                        ) {
                            Log.d("duylt", "onPaymentSucceeded: $p0 - $p1 - $p2")

                            runOnUiThread {
                                AlertDialog.Builder(this@ConfirmOrderActivity)
                                    .setTitle("Payment Success")
                                    .setMessage(
                                        String.format(
                                            "TransactionId: %s - TransToken: %s",
                                            p0,
                                            p1
                                        )
                                    )
                                    .setMessage(
                                        String.format(
                                            "Payment success for order: %s",
                                            p1
                                        )
                                    )
                                    .setPositiveButton(
                                        "OK"
                                    ) { dialog, which -> }
                                    .setNegativeButton("Cancel", null).show()
                            }
                        }

                        override fun onPaymentCanceled(p0: String?, p1: String?) {
                            AlertDialog.Builder(this@ConfirmOrderActivity)
                                .setTitle("User Cancel Payment")
                                .setMessage(String.format("zpTransToken: %s \n", p0))
                                .setPositiveButton("OK") { dialog, which -> }
                                .setNegativeButton("Cancel", null).show()
                        }

                        override fun onPaymentError(
                            p0: ZaloPayError?,
                            p1: String?,
                            p2: String?
                        ) {
                            if (p0 == ZaloPayError.PAYMENT_APP_NOT_FOUND) {
                                val handler = Handler(Looper.getMainLooper())
                                handler.postDelayed({
                                    AlertDialog.Builder(this@ConfirmOrderActivity)
                                        .setTitle("Error Payment")
                                        .setMessage("ZaloPay App not install on this Device.")
                                        .setPositiveButton(
                                            "Open Market"
                                        ) { dialog, which ->
                                            ZaloPaySDK.getInstance()
                                                .navigateToZaloPayOnStore(this@ConfirmOrderActivity)
                                        }
                                        .setNegativeButton("Back", null).show()
                                }, 500)
                                Log.d(
                                    "CODE_NOT_INSTALL",
                                    "onError: <br> <b> <i> ZaloPay App not install on this Device. </i> </b>"
                                )
                            } else {
                                Log.d(
                                    "CODE_PAY_ERROR",
                                    "onError: On onPaymentError with paymentErrorCode: " + p0?.toString() + " - zpTransToken: " + p1
                                )
                                AlertDialog.Builder(this@ConfirmOrderActivity)
                                    .setTitle("Payment Fail")
                                    .setMessage(
                                        String.format(
                                            "ZaloPayErrorCode: %s \nTransToken: %s",
                                            p0.toString(),
                                            p1
                                        )
                                    )
                                    .setPositiveButton(
                                        "OK"
                                    ) { dialog, which -> }
                                    .setNegativeButton("Cancel", null).show()
                            }
                        }
                    }
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData() {
        gson?.fromJson(SharedPrefCommon.jsonAcc, ResLoginUserDTO::class.java)
            ?.let { (user, token, message) ->
                binding.tvUserName.text = user?.username
                binding.tvPhoneNumber.text = "(${user?.phone ?: getString(R.string.unknown)})"
                binding.tvAddress.text = user?.address ?: getString(R.string.unknown)

                binding.tvPaymentMethod.text = paymentMethod
            }

        binding.tvTotal.text = intent.getIntExtra(AppConst.KEY_TOTAL_PRICE, 0).formatVND()
    }
}