package com.esgi.nova.ui

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.text.InputType
import android.text.TextUtils
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatAutoCompleteTextView



class TextInputDropdownMenu : AppCompatAutoCompleteTextView {

    override fun getDefaultEditable(): Boolean {
        return false
    }

    constructor(context: Context) : super(context)

    constructor(
        context: Context,
        attrs: AttributeSet
    ) : super(context, attrs)

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        inputType = InputType.TYPE_NULL
    }

    override fun getFreezesText(): Boolean {
        return false
    }

    override fun onSaveInstanceState(): Parcelable? {
        val parcelable = super.onSaveInstanceState()
        if (TextUtils.isEmpty(text)) {
            return parcelable
        }
        val customSavedState = CustomSavedState(parcelable)
        customSavedState.text = text.toString()
        return customSavedState
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state !is CustomSavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        val customSavedState: CustomSavedState = state
        setText(customSavedState.text, false)
        super.onRestoreInstanceState(customSavedState.superState)
    }

    private class CustomSavedState : BaseSavedState {
        var text: String? = null

        constructor(superState: Parcelable?) : super(superState)
        constructor(source: Parcel) : super(source) {
            text = source.readString()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeString(text)
        }


        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<CustomSavedState> {
            override fun createFromParcel(parcel: Parcel): CustomSavedState {
                return CustomSavedState(parcel)
            }
            override fun newArray(size: Int): Array<CustomSavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}