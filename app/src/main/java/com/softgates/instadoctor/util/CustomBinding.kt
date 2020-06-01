package com.softgates.myapplication.util

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.softgates.instadoctor.R


@BindingAdapter("layoutFullscreen")
fun View.bindLayoutFullscreen(previousFullscreen: Boolean,fullscreen:Boolean) {
    if (previousFullscreen != fullscreen && fullscreen) {
        systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    }
}

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Log.e("CHECKDATA","ondata is called...."+imageUrl.toString())
        Glide.with(view.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade()).circleCrop()
            .into(view)
    }
}

@BindingAdapter("setRating")
fun bindsetRating(view: RatingBar, rating: String?) {
    if (!rating.isNullOrEmpty()) {
        Log.e("RATINGBAR","rating......"+rating.toString())
      // var ratingbar= rating.toString().toInt()
        val ratingbar: Float? =rating.toString().toFloat()

        view.rating=ratingbar!!.toFloat()
    }
}

@BindingAdapter("symptom")
fun bindImageFromUrl(view: CardView, tick: String?) {
    if (!tick.isNullOrEmpty()) {
        if(tick.equals("1"))
        {
            view.setCardBackgroundColor(ContextCompat.getColor(view.context,R.color.darkblue))
        }
        else
        {
            view.setCardBackgroundColor(ContextCompat.getColor(view.context,android.R.color.white))
        }
    }
}

@BindingAdapter("statuscolor")
fun bindstatuscolor(view: View, statusType: String?) {
    Log.e("STATUSTYPE","addlImage...."+statusType)
    if(statusType.equals("1"))
    {
      //  view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.green))
        view.setBackgroundResource(R.drawable.online_dr)
    }
    else if(statusType.equals("2"))
    {
     //   view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.yellow))
        view.setBackgroundResource(R.drawable.busy_dr)
    }
    else if(statusType.equals("0"))
    {
        view.setBackgroundResource(R.drawable.offline_dr)
     //   view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.red))
    }
    //context.resources.getString(R.string.nointernet)
}

@BindingAdapter("statustxt")
fun bindstatustxt(view: TextView, statusType: String?) {
    Log.e("STATUSTYPE","addlImage...."+statusType)
    if(statusType.equals("1"))
    {
        //  view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.green))
        view.text ="Online"
    }
    else if(statusType.equals("2"))
    {
        //   view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.yellow))
        view.text ="Busy  "
    }
    else if(statusType.equals("0"))
    {
        view.text ="Offline"
        //   view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.red))
    }
    //context.resources.getString(R.string.nointernet)
}

/*
@BindingAdapter(
    "marginLeftSystemWindowInsets",
    "marginTopSystemWindowInsets",
    "marginRightSystemWindowInsets",
    "marginBottomSystemWindowInsets",
    requireAll = false
)
fun View.applySystemWindowInsetsMargin(
    previousApplyLeft: Boolean,
    previousApplyTop: Boolean,
    previousApplyRight: Boolean,
    previousApplyBottom: Boolean,
    applyLeft: Boolean,
    applyTop: Boolean,
    applyRight: Boolean,
    applyBottom: Boolean
) {
    if (previousApplyLeft == applyLeft &&
        previousApplyTop == applyTop &&
        previousApplyRight == applyRight &&
        previousApplyBottom == applyBottom
    ) {
        return
    }

    doOnApplyWindowInsets { view, insets, _, margin ->
        val left = if (applyLeft) insets.systemWindowInsetLeft else 0
        val top = if (applyTop) insets.systemWindowInsetTop else 0
        val right = if (applyRight) insets.systemWindowInsetRight else 0
        val bottom = if (applyBottom) insets.systemWindowInsetBottom else 0

        view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            leftMargin = margin.left + left
            topMargin = margin.top + top
            rightMargin = margin.right + right
            bottomMargin = margin.bottom + bottom
        }
    }
}

class InitialPadding(val left: Int, val top: Int, val right: Int, val bottom: Int)
class InitialMargin(val left: Int, val top: Int, val right: Int, val bottom: Int)
private fun recordInitialPaddingForView(view: View) = InitialPadding(
    view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom
)
private fun recordInitialMarginForView(view: View): InitialMargin {
    val lp = view.layoutParams as? ViewGroup.MarginLayoutParams
        ?: throw IllegalArgumentException("Invalid view layout params")
    return InitialMargin(lp.leftMargin, lp.topMargin, lp.rightMargin, lp.bottomMargin)
}

fun View.doOnApplyWindowInsets(
    block: (View, WindowInsets, InitialPadding, InitialMargin) -> Unit
) {
    // Create a snapshot of the view's padding & margin states
    val initialPadding = recordInitialPaddingForView(this)
    val initialMargin = recordInitialMarginForView(this)
    // Set an actual OnApplyWindowInsetsListener which proxies to the given
    // lambda, also passing in the original padding & margin states
    setOnApplyWindowInsetsListener { v, insets ->
        block(v, insets, initialPadding, initialMargin)
        // Always return the insets, so that children can also use them
        insets
    }
    // request some insets
    requestApplyInsetsWhenAttached()
}

fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        // We're already attached, just request as normal
        requestApplyInsets()
    } else {
        // We're not attached to the hierarchy, add a listener to
        // request when we are
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}*/
