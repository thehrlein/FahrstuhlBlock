package com.tobiapplications.fahrstuhlblock.entities.general.toolbar

sealed class ToolbarButtonType {

    object None : ToolbarButtonType()
    object Back : ToolbarButtonType()
    object Close : ToolbarButtonType()
}