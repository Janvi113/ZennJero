package com.zennjero.kook.app.presentation.util

import android.content.Intent
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.zennjero.kook.app.databinding.LayoutProgressBinding
import com.zennjero.kook.app.databinding.LayoutStatusTrackerBinding
import com.zennjero.kook.app.domain.OrderStatus

fun FragmentActivity.addFragment(
    container: Int,
    fragment: Fragment,
    addToBackStack: Boolean = false
) {
    supportFragmentManager.beginTransaction()
        .add(container, fragment, fragment.javaClass.simpleName)
        .apply {
            if (addToBackStack) addToBackStack(fragment.javaClass.simpleName)
        }
        .commit()
}

fun FragmentActivity.replaceFragment(container: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction()
        .replace(container, fragment, fragment.javaClass.simpleName)
        .commit()
}

fun FragmentActivity.popFragment() {
    supportFragmentManager.popBackStack()
}

fun LayoutProgressBinding.show() {
    this.root.visibility = View.VISIBLE
}

fun LayoutProgressBinding.hide() {
    this.root.visibility = View.GONE
}

/**
 * This method will set error feature on the edit text field.
 * Feature to show error in case of empty field and if user
 * enters some data then error is removed.
 */
fun TextInputLayout.setRequired() {
    // This listener will watch the edit text field
    // for showing and removing errors
    editText?.addTextChangedListener {
        if (it.isNullOrBlank()) {
            this.error = "Required"
        } else {
            this.error = null
        }
    }
}

fun FragmentActivity.findNavHostFragment(fragmentId: Int): NavHostFragment {
    return supportFragmentManager.findFragmentById(fragmentId) as NavHostFragment
}

/**
 * For passing data object as extra
 */
fun <T> Intent.putDataObject(key:String, t: T){
    putExtra(key, Gson().toJson(t))
}

/**
 * For getting data object as extra
 */
fun <T> Intent.getDataObjectExtra(key:String, type: Class<T>):T{
    return Gson().fromJson(getStringExtra(key), type)
}

/**
 * For setting up the listener to update status event
 */
fun LayoutStatusTrackerBinding.setStatusUpdateListener(listener: (newStatus:OrderStatus)->Unit){

    val checkBoxList = listOf(completed, inPrep, delivering)
    val statusList = listOf(OrderStatus.DELIVERED, OrderStatus.PREPARING, OrderStatus.ON_THE_WAY)

    checkBoxList.forEachIndexed { index, checkBox ->
        checkBox.setOnClickListener {
            if(checkBox.isChecked){
                listener(statusList[index])
            }
        }
    }

}