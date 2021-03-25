package com.example.memoryfinder.data

class PhotoDataCacheImpl : PhotoDataCache {
    override val pexeldao: PexelDao = PexelDaoImpl()
}