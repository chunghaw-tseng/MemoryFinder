package com.example.memoryfinder.data.provider

class SearchProviderImpl : SearchProvider {

    private var currentPage: Int = 1
    private var search : String = ""

    override fun setSearchPhotos(keyword: String) {
        search = keyword
    }

    override fun setStartPage() {
        currentPage = 1
    }

    override fun setNextPage() {
        currentPage += 1
    }

    override fun getSearchKeyword() : String{
        return search
    }

    override fun getLastPage() : Int{
       return currentPage
    }

}