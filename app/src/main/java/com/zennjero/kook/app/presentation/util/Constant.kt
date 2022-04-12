package com.zennjero.kook.app.presentation.util

object Constant {
    // URLS
    const val FCM_BASE_URL = "https://fcm.googleapis.com/"

    // Times
    const val SPLASH_SCREEN_ANIMATION_DURATION = 2500L // 2.5 seconds
    const val SPLASH_SCREEN_ANIMATION_START_OFFSET = 1000L // 1 second

    // Collection names
    const val COLLECTION_ADDRESS = "address"
    const val COLLECTION_ORDERS = "orders"
    const val COLLECTION_USER_TOKENS = "user_tokens"

    // Error Messages
    const val LOCATION_PROVIDER_OFF_ERROR = "Location is off. Please turn on the location"
    const val DEFAULT_ERROR = "Something went wrong. Please try again"
    const val REQUIRED_FIELD_ERROR_MESSAGE = "Required"
    const val SIGNED_OUT_ERROR = "You are not signed in"

    // Messages
    const val CANCELLED_ORDER_MESSAGE = "Refund under process"
    const val SCHEDULED_ORDER_MESSAGE = "Waiting to get started..."
    const val STATUS_UPDATED_MESSAGE = "Status Updated"
    const val FAILED_STATUS_UPDATE_MESSAGE = "Failed to update order status"

    const val CROP_DIMENSION = 10f
    const val IMAGE_MAXSIZE = 1024
    const val DATE_FORMAT = "dd-MM-yyyy"

    const val DEFAULT_LATITUDE = 0.0
    const val DEFAULT_LONGITUDE = 0.0

    const val LOGIN_TYPE: String = "Login Type"
    const val KOOK: String = "kook"
    const val BUYER = "buyer"
    const val LOGGER = "Logger"
    const val TIMEOUT_TIMER = 60L

    const val MOBILE_NUMBER_TAG = "MOBILE_NUMBER"
    const val EMAIL_PATTERN =
        "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    const val EMAIL_PATTERN_ERROR_MESSAGE = "Enter Valid Email"
    const val MIN_AGE_IN_MILLIS = 568025136000L

    const val NUM_TABS = 2
    const val COMPLETED = "Completed"
    const val UPCOMING = "Upcoming"
    const val ORDER_PREVIEW_DATA_KEY = "ORDER_PREVIEW_DATA_KEY"
}