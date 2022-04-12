package com.zennjero.kook.app.presentation.util

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.zennjero.kook.app.R
import com.zennjero.kook.app.domain.OrderStatus
import com.zennjero.kook.app.presentation.util.Constant.KOOK

@BindingAdapter("app:drawable")
fun setSrc(view: AppCompatImageView, drawable: Drawable) {
    view.setImageDrawable(drawable)
}

@BindingAdapter("app:textType")
fun setTVText(view: TextView, role: String) {
    if (role == KOOK) {
        view.setText(R.string.c_mon_let_s_get_your_food_on_the_wheels)
    } else {
        view.setText(R.string.only_a_few_minutes_away_from_delicious_food)
    }
}

@BindingAdapter("app:imageType")
fun setImg(imageView: ImageView, role: String) {
    if (role == KOOK) {
        imageView.setImageResource(R.drawable.ic_cook)
    } else {
        imageView.setImageResource(R.drawable.ic_buyer)
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("app:orderNo")
fun setOrderNo(textView: AppCompatTextView, orderNo:Int){
    textView.text = "ORDER NO: $orderNo"
}

@BindingAdapter("app:orderStatus")
fun setOrderStatus(textView: AppCompatTextView, orderStatus: OrderStatus){
    textView.text = if (orderStatus == OrderStatus.CANCELLED) Constant.CANCELLED_ORDER_MESSAGE else if (orderStatus == OrderStatus.SCHEDULED) Constant.SCHEDULED_ORDER_MESSAGE else null
}

@BindingAdapter("app:orderStatus")
fun setOrderStatus(imageView: AppCompatImageView, orderStatus: OrderStatus){
    when(orderStatus){
        OrderStatus.CANCELLED -> imageView.setImageResource(R.drawable.ic_order_cancelled)
        OrderStatus.SCHEDULED -> imageView.setImageResource(R.drawable.ic_calendar)
        else -> {imageView.setImageResource(0)}
    }
}

@BindingAdapter("app:orderStatus")
fun setOrderStatus(progressBar: ProgressBar, status: OrderStatus){
    progressBar.progress = when(status){
        OrderStatus.PREPARING -> 33
        OrderStatus.ON_THE_WAY -> 67
        OrderStatus.DELIVERED -> 100
        else -> 0
    }
}

@BindingAdapter("app:visibleIf")
fun setVisibleIf(view:View, bool:Boolean){
    view.visibility = if(bool) View.VISIBLE else View.GONE
}

