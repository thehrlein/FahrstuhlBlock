package com.tobiapplications.fahrstuhlblock.core.databaseroom

import com.tobiapplications.fahrstuhlblock.core.databaseroom.cache.GameCacheImpl
import com.tobiapplications.fahrstuhlblock.core.databaseroom.cache.PlayerCacheImpl
import com.tobiapplications.fahrstuhlblock.core.databaseroom.database.GameDatabase
import com.tobiapplications.fahrstuhlblock.core.interactor.datasource.cache.GameCache
import com.tobiapplications.fahrstuhlblock.core.interactor.datasource.cache.PlayerCache
import org.koin.dsl.module

val databaseModule = module {

    single { GameDatabase.getInstance(get()) }
    single { get<GameDatabase>().gameDao() }
    single { get<GameDatabase>().playerDao() }
    single<GameCache> { GameCacheImpl(get()) }
    single<PlayerCache> { PlayerCacheImpl(get()) }
}
