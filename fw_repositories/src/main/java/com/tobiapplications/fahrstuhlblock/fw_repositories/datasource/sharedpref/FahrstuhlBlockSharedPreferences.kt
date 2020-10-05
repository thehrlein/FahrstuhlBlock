package com.tobiapplications.fahrstuhlblock.fw_repositories.datasource.sharedpref

import android.content.Context
import com.tobiapplications.fahrstuhlblock.interactor.datasource.sharedpref.UserSettingsPersistence

class FahrstuhlBlockSharedPreferences(
    context: Context
) : SharedPreferenceDelegates(context), UserSettingsPersistence {

    override val preferencesFileName: String = "FahrstuhlBlockAppPreferences"

    override var isShowTrumpDialogEnabled by BooleanPrefDelegate(
        UserSettingsPersistence.KEY_IS_SHOW_TRUMP_DIALOG_ENABLED,
        UserSettingsPersistence.DEFAULT_IS_SHOW_TRUMP_DIALOG_ENABLED
    )
}
