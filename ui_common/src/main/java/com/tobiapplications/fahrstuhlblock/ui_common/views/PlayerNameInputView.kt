package com.tobiapplications.fahrstuhlblock.ui_common.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.tobiapplications.fahrstuhlblock.ui_common.R
import com.tobiapplications.fahrstuhlblock.ui_common.databinding.WidgetPlayerNameInputBinding
import com.tobiapplications.fahrstuhlblock.ui_common.extension.layoutInflater

class PlayerNameInputView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    private val binding: WidgetPlayerNameInputBinding = DataBindingUtil.inflate(
        context.layoutInflater,
        R.layout.widget_player_name_input,
        this,
        true
    )

    fun setPlayerName(text: String?) {
        binding.autoCompleteText.setText(text)
    }

    fun getPlayerName(): String? {
        return binding.autoCompleteText.text.toString()
    }

    fun setHint(hint: String) {
        binding.textInputLayoutOutlined.hint = hint
    }

    fun addTextChangeListener(textChangeListener: (String) -> Unit) {
        binding.autoCompleteText.addTextChangedListener { editable ->
            editable?.let {
                textChangeListener.invoke(it.toString())
            }
        }
    }

    fun setAdapter(items: Set<String>) {
        binding.autoCompleteText.setAdapter(
            ArrayAdapter(
                context,
                R.layout.item_auto_complete_text_row,
                items.toList()
            )
        )
    }

    fun setError(errorText: String?) {
        binding.textInputLayoutOutlined.apply {
            if (errorText.isNullOrEmpty()) {
                error = null
                isErrorEnabled = false
            } else {
                error = errorText
                isErrorEnabled = true
            }
        }
    }
}