@file:Suppress("unused")

package my.noveldokusha.core.utils

import android.net.Uri
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import kotlin.reflect.KProperty

/**
 * Delegate for SavedStateHandle with ArrayList<String> value.
 * Throws IllegalStateException if value is missing.
 */
class StateExtra_StringArrayList(private val state: SavedStateHandle) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): ArrayList<String> =
        state.get(property.name)
            ?: throw IllegalStateException("Required value '${property.name}' not found in SavedStateHandle")

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: ArrayList<String>) =
        state.set(property.name, value)
}

/**
 * Delegate for SavedStateHandle with String value.
 * Throws IllegalStateException if value is missing.
 */
class StateExtra_String(private val state: SavedStateHandle) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String =
        state.get(property.name)
            ?: throw IllegalStateException("Required value '${property.name}' not found in SavedStateHandle")

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) =
        state.set(property.name, value)
}

/**
 * Delegate for SavedStateHandle with nullable String value.
 */
class StateExtra_StringNullable(private val state: SavedStateHandle) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String? =
        state.get(property.name)

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) =
        state.set(property.name, value)
}

/**
 * Delegate for SavedStateHandle with Parcelable value.
 * Throws IllegalStateException if value is missing.
 */
class StateExtra_Parcelable<T : Parcelable>(private val state: SavedStateHandle) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T =
        state.get(property.name)
            ?: throw IllegalStateException("Required value '${property.name}' not found in SavedStateHandle")

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) =
        state.set(property.name, value)
}

/**
 * Delegate for SavedStateHandle with Uri value.
 * Throws IllegalStateException if value is missing.
 */
class StateExtra_Uri(private val state: SavedStateHandle) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Uri =
        state.get(property.name)
            ?: throw IllegalStateException("Required value '${property.name}' not found in SavedStateHandle")

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Uri) =
        state.set(property.name, value)
}

/**
 * Delegate for SavedStateHandle with Int value.
 * Throws IllegalStateException if value is missing.
 */
class StateExtra_Int(private val state: SavedStateHandle) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Int =
        state.get(property.name)
            ?: throw IllegalStateException("Required value '${property.name}' not found in SavedStateHandle")

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) =
        state.set(property.name, value)
}

/**
 * Delegate for SavedStateHandle with Float value.
 * Throws IllegalStateException if value is missing.
 */
class StateExtra_Float(private val state: SavedStateHandle) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Float =
        state.get(property.name)
            ?: throw IllegalStateException("Required value '${property.name}' not found in SavedStateHandle")

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Float) =
        state.set(property.name, value)
}

/**
 * Delegate for SavedStateHandle with Boolean value.
 * Throws IllegalStateException if value is missing.
 */
class StateExtra_Boolean(private val state: SavedStateHandle) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Boolean =
        state.get(property.name)
            ?: throw IllegalStateException("Required value '${property.name}' not found in SavedStateHandle")

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) =
        state.set(property.name, value)
}