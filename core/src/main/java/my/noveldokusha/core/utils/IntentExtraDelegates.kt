@file:Suppress("unused", "DEPRECATION")

package my.noveldokusha.core.utils

import android.content.Intent
import android.net.Uri
import android.os.Parcelable
import kotlin.reflect.KProperty

/**
 * Delegate for Intent extra with ArrayList<String> value.
 * Throws IllegalArgumentException if extra is missing.
 */
class Extra_StringArrayList {
    operator fun getValue(thisRef: Intent, property: KProperty<*>): ArrayList<String> =
        thisRef.extras?.getStringArrayList(property.name)
            ?: throw IllegalArgumentException("Extra '${property.name}' not found in Intent")

    operator fun setValue(thisRef: Intent, property: KProperty<*>, value: ArrayList<String>) =
        thisRef.putExtra(property.name, value)
}

/**
 * Delegate for Intent extra with String value.
 * Throws IllegalArgumentException if extra is missing.
 */
class Extra_String {
    operator fun getValue(thisRef: Intent, property: KProperty<*>): String =
        thisRef.extras?.getString(property.name)
            ?: throw IllegalArgumentException("Extra '${property.name}' not found in Intent")

    operator fun setValue(thisRef: Intent, property: KProperty<*>, value: String) =
        thisRef.putExtra(property.name, value)
}

/**
 * Delegate for Intent extra with nullable String value.
 */
class Extra_StringNullable {
    operator fun getValue(thisRef: Intent, property: KProperty<*>): String? =
        thisRef.extras?.getString(property.name)

    operator fun setValue(thisRef: Intent, property: KProperty<*>, value: String?) =
        thisRef.putExtra(property.name, value)
}

/**
 * Delegate for Intent extra with Parcelable value.
 * Throws IllegalArgumentException if extra is missing.
 */
class Extra_Parcelable<T : Parcelable> {
    operator fun getValue(thisRef: Intent, property: KProperty<*>): T =
        thisRef.extras?.getParcelable(property.name)
            ?: throw IllegalArgumentException("Extra '${property.name}' not found in Intent")

    operator fun setValue(thisRef: Intent, property: KProperty<*>, value: T) =
        thisRef.putExtra(property.name, value)
}

/**
 * Delegate for Intent extra with Uri value.
 * Throws IllegalArgumentException if extra is missing.
 */
class Extra_Uri {
    operator fun getValue(thisRef: Intent, property: KProperty<*>): Uri =
        thisRef.extras?.get(property.name) as? Uri
            ?: throw IllegalArgumentException("Extra '${property.name}' is not a valid Uri")

    operator fun setValue(thisRef: Intent, property: KProperty<*>, value: Uri) =
        thisRef.putExtra(property.name, value)
}

/**
 * Delegate for Intent extra with Int value.
 */
class Extra_Int {
    operator fun getValue(thisRef: Intent, property: KProperty<*>): Int =
        thisRef.extras?.getInt(property.name)
            ?: throw IllegalArgumentException("Extra '${property.name}' not found in Intent")

    operator fun setValue(thisRef: Intent, property: KProperty<*>, value: Int) =
        thisRef.putExtra(property.name, value)
}

/**
 * Delegate for Intent extra with Float value.
 */
class Extra_Float {
    operator fun getValue(thisRef: Intent, property: KProperty<*>): Float =
        thisRef.extras?.getFloat(property.name)
            ?: throw IllegalArgumentException("Extra '${property.name}' not found in Intent")

    operator fun setValue(thisRef: Intent, property: KProperty<*>, value: Float) =
        thisRef.putExtra(property.name, value)
}

/**
 * Delegate for Intent extra with Boolean value.
 */
class Extra_Boolean {
    operator fun getValue(thisRef: Intent, property: KProperty<*>): Boolean =
        thisRef.extras?.getBoolean(property.name)
            ?: throw IllegalArgumentException("Extra '${property.name}' not found in Intent")

    operator fun setValue(thisRef: Intent, property: KProperty<*>, value: Boolean) =
        thisRef.putExtra(property.name, value)
}