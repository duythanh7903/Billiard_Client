package com.datn.bia.a.common.base.ext

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmapOrNull
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition

fun ImageView.loadImage(
    context: Context,
    path: Any,
    placeHolderRes: Int = 0,
    errorImgRes: Int = 0,
    isCache: Boolean = true,
    radius: Int = 1,
    loadingView: View? = null,
    requestOptions: RequestOptions? = null,
    bitmapTransformation: BitmapTransformation = CenterInside(),
    onLoading: (() -> Unit)? = null,
    onLoadSuccess: ((resource: Drawable) -> Unit)? = null,
    onLoadError: ((e: GlideException?, isFinishRes: Boolean) -> Unit)? = null,
) {
    onLoading?.invoke()
    loadingView?.visibleView()

    val options: RequestOptions = RequestOptions()
        .diskCacheStrategy(if (isCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
        .placeholder(placeHolderRes)
        .error(errorImgRes)
        .fitCenter()
        .apply(RequestOptions().transform(bitmapTransformation, RoundedCorners(radius)))

    Glide.with(context)
        .load(path)
        .apply(requestOptions ?: options)
        .listener(object: RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                loadingView?.goneView()
                onLoadError?.invoke(e, isFirstResource)

                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                this@loadImage.setImageDrawable(resource)

                loadingView?.goneView()
                onLoadSuccess?.invoke(resource)

                return false
            }
        })
        .into(this)
}

fun ImageView.loadImage(
    context: Context,
    path: Any,
    placeHolderRes: Int = 0,
    errorImgRes: Int = 0,
    isCache: Boolean = true,
    loadingView: View? = null,
    requestOptions: RequestOptions? = null,
    bitmapTransformation: BitmapTransformation = CenterInside(),
    onLoading: (() -> Unit)? = null,
    onLoadSuccess: ((resource: Drawable) -> Unit)? = null,
    onLoadError: ((e: GlideException?, isFinishRes: Boolean) -> Unit)? = null,
) {
    onLoading?.invoke()
    loadingView?.visibleView()

    val options: RequestOptions = RequestOptions()
        .diskCacheStrategy(if (isCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
        .placeholder(placeHolderRes)
        .error(errorImgRes)
        .fitCenter()
        .apply(RequestOptions().transform(bitmapTransformation))

    Glide.with(context)
        .load(path)
        .apply(requestOptions ?: options)
        .listener(object: RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                loadingView?.goneView()
                onLoadError?.invoke(e, isFirstResource)

                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                this@loadImage.setImageDrawable(resource)

                loadingView?.goneView()
                onLoadSuccess?.invoke(resource)

                return false
            }
        })
        .into(this)
}

fun ImageView.loadImageBitmap(
    context: Context,
    path: Any,
    placeHolderRes: Int = 0,
    errorImgRes: Int = 0,
    isCache: Boolean = true,
    radius: Int = 1,
    loadingView: View? = null,
    requestOptions: RequestOptions? = null,
    bitmapTransformation: BitmapTransformation = CenterInside(),
    onLoading: (() -> Unit)? = null,
    onLoadSuccess: ((bitmap: Bitmap) -> Unit)? = null,
    onLoadError: (() -> Unit)? = null,
) {
    onLoading?.invoke()
    loadingView?.visibleView()

    val options: RequestOptions = RequestOptions()
        .diskCacheStrategy(if (isCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
        .placeholder(placeHolderRes)
        .error(errorImgRes)
        .fitCenter()
        .apply(RequestOptions().transform(bitmapTransformation, RoundedCorners(radius)))

    Glide.with(context)
        .asBitmap()
        .load(path)
        .apply(requestOptions ?: options)
        .into(object: CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                this@loadImageBitmap.setImageBitmap(resource)

                loadingView?.goneView()
                onLoadSuccess?.invoke(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) = Unit

            override fun onLoadFailed(errorDrawable: Drawable?) {
                super.onLoadFailed(errorDrawable)

                loadingView?.goneView()
                onLoadError?.invoke()
            }
        })
}

fun ImageView.loadImageBitmap(
    context: Context,
    path: Any,
    placeHolderRes: Int = 0,
    errorImgRes: Int = 0,
    isCache: Boolean = true,
    loadingView: View? = null,
    requestOptions: RequestOptions? = null,
    bitmapTransformation: BitmapTransformation = CenterInside(),
    onLoading: (() -> Unit)? = null,
    onLoadSuccess: ((bitmap: Bitmap) -> Unit)? = null,
    onLoadError: (() -> Unit)? = null,
) {
    onLoading?.invoke()
    loadingView?.visibleView()

    val options: RequestOptions = RequestOptions()
        .diskCacheStrategy(if (isCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
        .placeholder(placeHolderRes)
        .error(errorImgRes)
        .fitCenter()
        .apply(RequestOptions().transform(bitmapTransformation))

    Glide.with(context)
        .asBitmap()
        .load(path)
        .apply(requestOptions ?: options)
        .into(object: CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                this@loadImageBitmap.setImageBitmap(resource)

                loadingView?.goneView()
                onLoadSuccess?.invoke(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) = Unit

            override fun onLoadFailed(errorDrawable: Drawable?) {
                super.onLoadFailed(errorDrawable)

                loadingView?.goneView()
                onLoadError?.invoke()
            }
        })
}