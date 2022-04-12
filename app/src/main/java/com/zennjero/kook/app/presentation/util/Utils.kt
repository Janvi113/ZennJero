package com.zennjero.kook.app.presentation.util

import android.app.DatePickerDialog
import android.content.Context
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputLayout
import com.zennjero.kook.app.presentation.util.Constant.EMAIL_PATTERN
import com.zennjero.kook.app.presentation.util.Constant.EMAIL_PATTERN_ERROR_MESSAGE
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class Utils {
    companion object {
        fun returnDate(myCalender: Calendar): String {
            val myFormat = Constant.DATE_FORMAT
            val sdf = SimpleDateFormat(myFormat, Locale.UK)
            return sdf.format(myCalender.time)
        }

        /**
         * This method will create a range of time in millis
         * for a given date and return a pair of long to long
         * @return Pair<Long, Long>
         *     first - start Time in millis
         *     second - end Time in millis
         */
        fun dateToMillisRange(date: Date):Pair<Long, Long>{
            var startTimeMillis:Long = 0
            var endTimeMillis:Long = 0
            val calendar = Calendar.getInstance()
            calendar.time = date

            // set time to 12:00:00 AM
            // for start time in millis
            calendar.set(Calendar.HOUR, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.AM_PM, Calendar.AM)
            startTimeMillis = calendar.timeInMillis

            // add one day
            calendar.add(Calendar.DATE, 1)
            endTimeMillis = calendar.timeInMillis
            return startTimeMillis to endTimeMillis
        }
        /**
         * This method will check if any required field is empty
         * and it will show error if any field field is empty
         * and return true
         */
        fun isAnyFieldEmpty(requiredFields: List<TextInputLayout>): Boolean {
            var isEmpty = false

            requiredFields.forEach { item ->
                if (item.editText?.text.isNullOrBlank()) {
                    item.error = Constant.REQUIRED_FIELD_ERROR_MESSAGE
                    isEmpty = true
                }
            }

            return isEmpty
        }

        private fun emailValidator(email: String): Boolean {
            return Pattern.compile(EMAIL_PATTERN).matcher(email).matches()
        }

        fun isEmailValid(emailField: TextInputLayout): Boolean {
            var isValid = true
            if (!emailValidator(emailField.editText?.text.toString())) {
                emailField.error = EMAIL_PATTERN_ERROR_MESSAGE
                isValid = false
            }
            return isValid
        }

        private fun getDate(myCalender: Calendar): String {
            if (isToday(myCalender.time)){
                return "Today"
            }else if (isYesterday(myCalender.time)){
                return "Yesterday"
            }else if (isTomorrow(myCalender.time)){
                return "Tomorrow"
            }
            val dayNumberSuffix: String =
                getDayNumberSuffix(myCalender.get(Calendar.DAY_OF_MONTH))
            val myFormat = "d'$dayNumberSuffix' MMM"
            val sdf = SimpleDateFormat(myFormat)
            return sdf.format(myCalender.time)
        }

        private fun getDayNumberSuffix(day: Int): String {
            return if (day in 11..13) {
                "th"
            } else when (day % 10) {
                1 -> "st"
                2 -> "nd"
                3 -> "rd"
                else -> "th"
            }
        }

        private fun isYesterday(d: Date): Boolean {
            return DateUtils.isToday(d.time + DateUtils.DAY_IN_MILLIS)
        }
        private fun isToday(d:Date) : Boolean{
            return DateUtils.isToday(d.time)
        }
        private fun isTomorrow(d: Date): Boolean {
            return DateUtils.isToday(d.time - DateUtils.DAY_IN_MILLIS)
        }

        fun setDate(chip: Chip,context: Context,isCompleted : Boolean) : LiveData<Date> {
            val date = MutableLiveData<Date>()
            var myCalender : Calendar = Calendar.getInstance()
            if (isCompleted){
                myCalender = Calendar.getInstance().apply { add(Calendar.DATE,-1) }
            }
            date.postValue(myCalender.time)
            chip.text = getDate(myCalender)
            val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                myCalender.set(Calendar.YEAR, year)
                myCalender.set(Calendar.MONTH, month)
                myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                date.postValue(myCalender.time)
                chip.text = getDate(myCalender)
            }
            chip.setOnClickListener {
                val datePickerDialog = DatePickerDialog(
                    context,
                    datePicker,
                    myCalender.get(Calendar.YEAR),
                    myCalender.get(Calendar.MONTH),
                    myCalender.get(Calendar.DAY_OF_MONTH)
                )
                if(isCompleted){
                    datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
                }else{
                    datePickerDialog.datePicker.minDate = System.currentTimeMillis()
                }
                datePickerDialog.show()
            }

            return date
        }

    }
}