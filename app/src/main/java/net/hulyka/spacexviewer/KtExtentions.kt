@file:Suppress("NOTHING_TO_INLINE")

package net.hulyka.spacexviewer

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


inline fun <reified VM : ViewModel> androidx.fragment.app.Fragment.viewModelProvider(
    provider: ViewModelProvider.Factory
) = ViewModelProviders.of(this, provider).get(VM::class.java)

fun <T> lazyFast(operation: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE) {
    operation()
}


inline fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context)
        .inflate(layout, this, attachToRoot)
}

inline fun Context.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(this, text, duration).apply { show() }
}

inline fun Context.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(this, resId, duration).apply { show() }
}

inline fun View.show() {
    visibility = VISIBLE
}


inline fun View.hide() {
    visibility = GONE
}

inline fun ImageView.loadImage(
    url: String?, @DrawableRes placeholder: Int = 0,
    crossinline onFinish: () -> Unit
) {
    Glide.with(this)
        .asBitmap()
        .load(url)
        .placeholder(placeholder)
        .dontTransform()
        .listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>?,
                isFirstResource: Boolean
            ): Boolean {
                onFinish.invoke()
                return false
            }

            override fun onResourceReady(
                resource: Bitmap?,
                model: Any?,
                target: Target<Bitmap>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                onFinish.invoke()
                return false
            }

        })
        .into(this)
}


val Int.toPx: Float
    get() = (this * Resources.getSystem().displayMetrics.density)