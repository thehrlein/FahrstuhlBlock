package com.tobiapplications.fahrstuhlblock.fw_repositories.datasource.sharedpref

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import kotlin.reflect.KProperty


abstract class BaseSharedPreferences(
    context: Context
) {

    protected abstract val preferencesFileName: String
    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(preferencesFileName, Context.MODE_PRIVATE)
    }


    protected abstract class PrefDelegate<T>(val prefKey: String) {
        abstract operator fun getValue(thisRef: Any?, property: KProperty<*>): T
        abstract operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)
    }

    protected fun intPref(prefKey: String, defaultValue: Int = 0): IntPrefDelegate {
        return IntPrefDelegate(prefKey, defaultValue)
    }

    protected inner class IntPrefDelegate(
        prefKey: String,
        private val defaultValue: Int
    ) : PrefDelegate<Int>(prefKey) {
        override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
            return prefs.getInt(prefKey, defaultValue)
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
            prefs.edit().putInt(prefKey, value).apply()
        }
    }

    protected fun floatPref(prefKey: String, defaultValue: Float = 0f): FloatPrefDelegate {
        return FloatPrefDelegate(prefKey, defaultValue)
    }

    protected inner class FloatPrefDelegate(
        prefKey: String,
        private val defaultValue: Float
    ) : PrefDelegate<Float>(prefKey) {
        override fun getValue(thisRef: Any?, property: KProperty<*>): Float {
            return prefs.getFloat(prefKey, defaultValue)
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Float) {
            prefs.edit().putFloat(prefKey, value).apply()
        }
    }

    protected fun booleanPref(prefKey: String, defaultValue: Boolean = false): BooleanPrefDelegate {
        return BooleanPrefDelegate(prefKey, defaultValue)
    }

    protected inner class BooleanPrefDelegate(
        prefKey: String,
        private val defaultValue: Boolean
    ) : PrefDelegate<Boolean>(prefKey) {
        override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
            return prefs.getBoolean(prefKey, defaultValue)
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
            prefs.edit().putBoolean(prefKey, value).apply()
        }
    }

    protected fun longPref(prefKey: String, defaultValue: Long = 0L): LongPrefDelegate {
        return LongPrefDelegate(prefKey, defaultValue)
    }

    protected inner class LongPrefDelegate(
        prefKey: String,
        private val defaultValue: Long
    ) : PrefDelegate<Long>(prefKey) {
        override fun getValue(thisRef: Any?, property: KProperty<*>): Long {
            return prefs.getLong(prefKey, defaultValue)
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Long) {
            prefs.edit().putLong(prefKey, value).apply()
        }
    }

    protected fun stringPref(prefKey: String, defaultValue: String? = null): StringPrefDelegate {
        return StringPrefDelegate(prefKey, defaultValue)
    }

    protected inner class StringPrefDelegate(
        prefKey: String,
        private val defaultValue: String?
    ) : PrefDelegate<String?>(prefKey) {
        override fun getValue(thisRef: Any?, property: KProperty<*>): String? {
            return prefs.getString(prefKey, defaultValue)
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
            prefs.edit().putString(prefKey, value).apply()
        }
    }

    protected fun nonNullStringPref(
        prefKey: String,
        defaultValue: String
    ): NonNullStringPrefDelegate {
        return NonNullStringPrefDelegate(prefKey, defaultValue)
    }

    protected inner class NonNullStringPrefDelegate(
        prefKey: String,
        private val defaultValue: String
    ) : PrefDelegate<String>(prefKey) {
        override fun getValue(thisRef: Any?, property: KProperty<*>): String {
            return prefs.getString(prefKey, defaultValue) ?: defaultValue
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
            prefs.edit().putString(prefKey, value).apply()
        }
    }

    protected fun stringSetPref(
        prefKey: String,
        defaultValue: Set<String> = HashSet()
    ): StringSetPrefDelegate {
        return StringSetPrefDelegate(prefKey, defaultValue)
    }

    protected inner class StringSetPrefDelegate(
        prefKey: String,
        private val defaultValue: Set<String>
    ) : PrefDelegate<Set<String>?>(prefKey) {
        override fun getValue(thisRef: Any?, property: KProperty<*>): MutableSet<String>? {
            return prefs.getStringSet(prefKey, defaultValue)
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Set<String>?) {
            prefs.edit().putStringSet(prefKey, value).apply()
        }
    }

    protected fun <T> genericPref(
        classOfT: Class<T>,
        prefKey: String,
        defaultValue: T? = null
    ): GenericPrefDelegate<T> {
        return GenericPrefDelegate(classOfT, prefKey, defaultValue)
    }

    protected inner class GenericPrefDelegate<T>(
        private val classOfT: Class<T>,
        prefKey: String,
        private val defaultValue: T?
    ) : PrefDelegate<T?>(prefKey) {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
            return try {
                prefs.getString(prefKey, null)?.let { json ->
                    Gson().fromJson(json, classOfT)
                }
            } catch (ex: Exception) {
                defaultValue
            }
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
            prefs.edit().putString(prefKey, Gson().toJson(value)).apply()
        }
    }

    protected fun removePreferences(vararg keys: String) {
        keys.forEach { key ->
            if (prefs.contains(key)) {
                prefs.edit()
                    .remove(key)
                    .apply()
            }
        }
    }
}