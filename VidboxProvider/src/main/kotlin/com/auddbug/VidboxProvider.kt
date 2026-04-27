package com.auddbug

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*

class VidboxProvider : MainAPI() {
    override var name = "Vidbox"
    override val mainUrl = "https://vidbox.dev"
    override val supportedTypes = setOf(TvType.Movie, TvType.TvSeries)
    override val lang = "en"

    override suspend fun search(query: String): List<SearchResponse> {
        val url = "$mainUrl/search?q=${query.encode()}"
        val doc = app.get(url).document
        
        return doc.select(".flw-item").mapNotNull {
            val a = it.selectFirst(".film-name a") ?: return@mapNotNull null
            val title = a.text()
            val href = fixUrl(a.attr("href"))
            val poster = it.selectFirst(".film-poster img")?.attr("data-src")
            
            newMovieSearchResponse(title, href, TvType.Movie) {
                this.posterUrl = poster
            }
        }
    }

    override suspend fun load(url: String): LoadResponse {
        val doc = app.get(url).document
        val title = doc.selectFirst("h2.name")?.text() ?: "Unknown"
        val poster = doc.selectFirst(".film-poster img")?.attr("src")
        
        return newMovieLoadResponse(title, url, TvType.Movie, url) {
            this.posterUrl = poster
            this.plot = doc.selectFirst(".description")?.text()
        }
    }

    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        val doc = app.get(data).document
        doc.select("iframe").forEach { 
            val src = it.attr("src")
            loadExtractor(src, subtitleCallback, callback)
        }
        return true
    }
}
