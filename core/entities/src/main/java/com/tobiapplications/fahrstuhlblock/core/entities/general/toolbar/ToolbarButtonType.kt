package com.tobiapplications.fahrstuhlblock.core.entities.general.toolbar

sealed class ToolbarButtonType {

    object None : ToolbarButtonType()
    object Back : ToolbarButtonType()
    object Close : ToolbarButtonType()
}
