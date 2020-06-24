package com.tobiapplications.fahrstuhlblock.fw_repositories.datasource.sharedpref

import android.content.Context


class SharedPreferencesDataSource(
    context: Context
) : BaseSharedPreferences(context) {

    override val preferencesFileName: String = "FahrstuhlBlockAppPreferences"


}