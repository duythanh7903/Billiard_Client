package com.datn.vpp.sp26.present.user.activity.order.confirm

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.application.GlobalApp
import com.datn.vpp.sp26.common.AppConst
import com.datn.vpp.sp26.common.MethodPayment
import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.common.base.BaseActivity
import com.datn.vpp.sp26.common.base.ext.click
import com.datn.vpp.sp26.common.base.ext.formatVND
import com.datn.vpp.sp26.common.base.ext.showToastOnce
import com.datn.vpp.sp26.common.payment.Api.CreateOrder
import com.datn.vpp.sp26.data.storage.SharedPrefCommon
import com.datn.vpp.sp26.databinding.ActivityConfirmOrderBinding
import com.datn.vpp.sp26.domain.model.domain.Cart
import com.datn.vpp.sp26.domain.model.dto.req.ReqProdCheckOut
import com.datn.vpp.sp26.domain.model.dto.res.ResLoginUserDTO
import com.datn.vpp.sp26.present.user.activity.order.history.OrderActivity
import com.datn.vpp.sp26.present.user.dialog.LoadingDialog
import com.datn.vpp.sp26.present.user.dialog.MessageDialog
import com.datn.vpp.sp26.present.user.dialog.NotificationDialog
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.wallet.contract.TaskResultContracts
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener


@AndroidEntryPoint
class ConfirmOrderActivity : BaseActivity<ActivityConfirmOrderBinding>() {

    private var gson: Gson? = null
    private var cartConfirmAdapter: CartConfirmAdapter? = null
    private var messageDialog: MessageDialog? = null
    private var loadingDialog: LoadingDialog? = null
    private var notificationDialog: NotificationDialog? = null

    private var paymentMethod = MethodPayment.CASH_ON_DELIVERY.name

    private val viewModel: ConfirmViewModel by viewModels()
    private val model: ConfirmOrderViewModel by viewModels()

    private val paymentDataLauncher =
        registerForActivityResult(TaskResultContracts.GetPaymentDataResult()) { taskResult ->
            when (taskResult.status.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    taskResult.result!!.let {
                        Log.i("Google Pay result:", it.toJson())
                        model.setPaymentData(it)

                        viewModel.checkOutOrder(
                            totalPrice = intent.getDoubleExtra(AppConst.KEY_TOTAL_PRICE, 0.0),
                            voucherId = intent.getStringExtra(AppConst.KEY_ID_VOUCHER),
                            listProduct = gson?.fromJson<List<ReqProdCheckOut>>(
                                intent.getStringExtra(AppConst.KEY_LIST_PRODUCT),
                                object : TypeToken<List<ReqProdCheckOut>>() {}.type
                            ) ?: emptyList(),
                            paymentMethod = "GG PAY"
                        )
                    }
                }
                //CommonStatusCodes.CANCELED -> The user canceled
                //CommonStatusCodes.DEVELOPER_ERROR -> The API returned an error (it.status: Status)
                //else -> Handle internal and other unexpected errors
            }
        }

    override fun getLayoutActivity(): Int = R.layout.activity_confirm_order

    override fun initViews() {
        super.initViews()

        initZaloPay()
        gson = Gson()
        paymentMethod = intent.getStringExtra(AppConst.KEY_PAYMENT_METHOD) ?: paymentMethod
        cartConfirmAdapter = CartConfirmAdapter(
            contextParams = this@ConfirmOrderActivity,
            isBusiness = SharedPrefCommon.role == AppConst.ROLE_WHOLESALE
        ).apply {
            val listCart = gson?.fromJson<List<Cart>>(
                intent.getStringExtra(AppConst.KEY_LIST_CART),
                object : TypeToken<List<Cart>>() {}.type
            ) ?: emptyList()
            submitData(listCart)
        }
        binding.rcvOrder.adapter = cartConfirmAdapter
        messageDialog = MessageDialog(
            contextParam = this,
            onSend = { message ->
                viewModel.setMessage(message)
            }, onClose = {
                viewModel.setMessage("")
            }
        )
        loadingDialog = LoadingDialog(this)
        notificationDialog = NotificationDialog(
            context = this,
            onOk = {
                GlobalApp.isPaymentSuccess = true
                startActivity(Intent(this@ConfirmOrderActivity, OrderActivity::class.java))
                finish()
            }
        )
        setData()
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.btnPay.click {
            val list = gson?.fromJson<List<ReqProdCheckOut>>(
                intent.getStringExtra(AppConst.KEY_LIST_PRODUCT),
                object : TypeToken<List<ReqProdCheckOut>>() {}.type
            ) ?: emptyList()

            if (list.isEmpty()) {
                showToastOnce(getString(R.string.msg_wrong))
                return@click
            }

            viewModel.checkStock(list)
        }

        binding.icBack.click {
            finish()
        }

        binding.btnShowMessage.click {
            messageDialog?.show()
        }
    }

    override fun observerData() {
        super.observerData()

        lifecycleScope.launch {
            viewModel.checkStockState.collect {
                when (it) {
                    is UiState.Error -> {
                        showToastOnce(it.message)
                        loadingDialog?.dismiss()
                        viewModel.changeCheckStateToIdle()
                    }

                    UiState.Idle -> {

                    }

                    UiState.Loading -> {
                        loadingDialog?.show()
                    }

                    is UiState.Success -> {
                        loadingDialog?.dismiss()
                        viewModel.changeCheckStateToIdle()

                        if (it.data.message != null) {
                            showToastOnce(it.data.message)

                            return@collect
                        }

                        paymentOrder()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.message.collect { message ->
                binding.tvMessage.text = message
            }
        }

        lifecycleScope.launch {
            viewModel.stateCheckOut.collect { uiState ->
                when (uiState) {
                    is UiState.Error -> {
                        Log.d("duylt", "Message: ${uiState.message}")
                        showToastOnce(getString(R.string.msg_ins_stock))
                        loadingDialog?.cancel()
                        viewModel.changeStateToIdle()
                    }

                    UiState.Idle -> {

                    }

                    UiState.Loading -> {
                        loadingDialog?.show()
                    }

                    is UiState.Success<*> -> {
                        val ids = cartConfirmAdapter?.list?.map { it.cartId }
                            ?: emptyList()
                        viewModel.deleteCartById(ids)

                        loadingDialog?.cancel()
                        notificationDialog?.show()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        gson = null
        cartConfirmAdapter?.list?.clear()
        cartConfirmAdapter = null
        messageDialog?.dismiss()
        messageDialog = null
        loadingDialog?.dismiss()
        loadingDialog = null

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
        if (paymentMethod == MethodPayment.GOOGLE_PAY.name) {
            val value = (intent.getDoubleExtra(AppConst.KEY_TOTAL_PRICE, 0.0) + AppConst.FEE_SHIP)
            val mainValue = value / 27000
            val result = String.format("%.2f", mainValue)

            val task = model.getLoadPaymentDataTask(priceLabel = result)
            task.addOnCompleteListener(paymentDataLauncher::launch)

            return
        }

        if (paymentMethod == MethodPayment.CASH_ON_DELIVERY.name) {
            viewModel.checkOutOrder(
                totalPrice = intent.getDoubleExtra(AppConst.KEY_TOTAL_PRICE, 0.0),
                voucherId = intent.getStringExtra(AppConst.KEY_ID_VOUCHER),
                listProduct = gson?.fromJson<List<ReqProdCheckOut>>(
                    intent.getStringExtra(AppConst.KEY_LIST_PRODUCT),
                    object : TypeToken<List<ReqProdCheckOut>>() {}.type
                ) ?: emptyList(),
                paymentMethod = "COD"
            )
            return
        }

        val orderApi = CreateOrder()

        try {
            val data = orderApi.createOrder(
                (intent.getDoubleExtra(
                    AppConst.KEY_TOTAL_PRICE,
                    0.0
                ) + AppConst.FEE_SHIP).toInt().toString()
            )
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
                            viewModel.checkOutOrder(
                                totalPrice = intent.getDoubleExtra(AppConst.KEY_TOTAL_PRICE, 0.0),
                                voucherId = intent.getStringExtra(AppConst.KEY_ID_VOUCHER),
                                listProduct = gson?.fromJson<List<ReqProdCheckOut>>(
                                    intent.getStringExtra(AppConst.KEY_LIST_PRODUCT),
                                    object : TypeToken<List<ReqProdCheckOut>>() {}.type
                                ) ?: emptyList(),
                                paymentMethod = "ZALO PAY"
                            )
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

        binding.tvTotal.text =
            (intent.getDoubleExtra(AppConst.KEY_TOTAL_PRICE, 0.0) + AppConst.FEE_SHIP).formatVND()
    }
}