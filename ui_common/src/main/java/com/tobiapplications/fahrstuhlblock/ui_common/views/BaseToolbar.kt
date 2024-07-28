package com.tobiapplications.fahrstuhlblock.ui_common.views

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.ImageViewCompat
import androidx.databinding.DataBindingUtil
import com.tobiapplications.fahrstuhlblock.entities.utils.extensions.checkAllMatched
import com.tobiapplications.fahrstuhlblock.entities.general.toolbar.OnToolbarButtonClickListener
import com.tobiapplications.fahrstuhlblock.entities.general.toolbar.ToolbarButtonType
import com.tobiapplications.fahrstuhlblock.ui_common.R
import com.tobiapplications.fahrstuhlblock.ui_common.databinding.ViewToolbarBinding
import com.tobiapplications.fahrstuhlblock.ui_common.utils.EllipsizeAttribute

class BaseToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = com.google.android.material.R.attr.toolbarStyle,
    defStyleRes: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {

    private var listener: OnToolbarButtonClickListener? = null

    private val binding: ViewToolbarBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.view_toolbar,
        this,
        true
    )

    init {
        val typedArray =
            context.obtainStyledAttributes(
                attrs,
                R.styleable.BaseToolbar,
                defStyleAttr,
                defStyleRes
            )

        typedArray.apply {
            val titleGravity = getInt(R.styleable.BaseToolbar_android_gravity, 0)
            val iconTint = getColor(R.styleable.BaseToolbar_android_buttonTint, 0)
            val maxLines = getInt(R.styleable.BaseToolbar_android_maxLines, 1)
            val ellipsize = getInt(R.styleable.BaseToolbar_android_ellipsize, 1).let {
                EllipsizeAttribute.getEllipsize(it).truncateAt
            }

            binding.apply {
                toolbarTitle.gravity = titleGravity
                toolbarTitle.maxLines = maxLines
                toolbarTitle.ellipsize = ellipsize
                ImageViewCompat.setImageTintList(toolbarIcon, ColorStateList.valueOf(iconTint))
            }
            recycle()
        }

        binding.toolbarIcon.setOnClickListener {
            listener?.onButtonClicked()
        }
    }

    fun setToolbarTitle(title: String?) {
        binding.toolbarTitle.text = title
    }

    fun toolbarButton(toolbarButtonType: ToolbarButtonType) {
        binding.toolbarIcon.setImageResource(getButtonDrawable(toolbarButtonType))
    }

    fun onToolbarButtonClick(toolbarButtonClickListener: OnToolbarButtonClickListener) {
        this.listener = toolbarButtonClickListener
    }

    @DrawableRes
    private fun getButtonDrawable(toolbarButtonType: ToolbarButtonType): Int =
        when (toolbarButtonType) {
            ToolbarButtonType.Back -> R.drawable.ic_arrow_back_black
            ToolbarButtonType.Close -> R.drawable.ic_close_black
            else -> android.R.color.transparent
        }.checkAllMatched
}
