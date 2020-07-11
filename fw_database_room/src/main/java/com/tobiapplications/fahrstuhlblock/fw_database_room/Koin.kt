package com.tobiapplications.fahrstuhlblock.fw_database_room

import com.tobiapplications.fahrstuhlblock.fw_database_room.cache.GameCacheImpl
import com.tobiapplications.fahrstuhlblock.fw_database_room.cache.PlayerCacheImpl
import com.tobiapplications.fahrstuhlblock.fw_database_room.database.GameDatabase
import com.tobiapplications.fahrstuhlblock.interactor.datasource.cache.GameCache
import com.tobiapplications.fahrstuhlblock.interactor.datasource.cache.PlayerCache
import org.koin.dsl.module

val databaseModule = module {

    single { GameDatabase.getInstance(get()) }
    single { get<GameDatabase>().gameDao() }
    single { get<GameDatabase>().playerDao() }
    single<GameCache> { GameCacheImpl(get()) }
    single<PlayerCache> { PlayerCacheImpl(get()) }
}