package com.example.ituneplayer

import com.google.gson.annotations.SerializedName

data class iTuneResponse (
    @SerializedName("feed") val feed:RssFeed){
        data class RssFeed(
            @SerializedName("entry") val entries: List<Entry>
        ) {
            data class Label(val label: String)
            data class Entry(
                @SerializedName("im:image") val image: List<ImageItem>,
                @SerializedName("title") val title: Label,
                @SerializedName("link") val link: List<Link>) {
                data class ImageItem(
                    @SerializedName("label") val url: String,
                    @SerializedName("attributes") val attributes: ImageAttributes
                ) {
                    data class ImageAttributes(@SerializedName("height") val height: Int)
                }
                data class Link(
                    @SerializedName("attributes") val attributes: LinkAttributes){
                    data class LinkAttributes(
                        @SerializedName("type") val type: String,
                        @SerializedName("href") val href: String)
            }
        }
    }
}