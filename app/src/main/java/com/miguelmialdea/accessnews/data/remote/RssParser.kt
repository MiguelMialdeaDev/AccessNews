package com.miguelmialdea.accessnews.data.remote

import com.miguelmialdea.accessnews.data.remote.dto.ArticleDto
import com.miguelmialdea.accessnews.data.remote.dto.FeedDto
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.Element
import org.w3c.dom.NodeList

/**
 * Parser for RSS and Atom feeds
 */
class RssParser {

    /**
     * Parse RSS/Atom feed from XML string
     */
    fun parseFeed(xmlContent: String, feedUrl: String): FeedDto {
        val inputStream = xmlContent.byteInputStream()
        return parseFeedFromStream(inputStream, feedUrl)
    }

    /**
     * Parse RSS/Atom feed from InputStream
     */
    private fun parseFeedFromStream(inputStream: InputStream, feedUrl: String): FeedDto {
        val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream)
        doc.documentElement.normalize()

        val rootElement = doc.documentElement.tagName

        return when {
            rootElement.equals("rss", ignoreCase = true) -> parseRss(doc.documentElement, feedUrl)
            rootElement.equals("feed", ignoreCase = true) -> parseAtom(doc.documentElement, feedUrl)
            else -> throw IllegalArgumentException("Unsupported feed format: $rootElement")
        }
    }

    /**
     * Parse RSS 2.0 feed
     */
    private fun parseRss(element: Element, feedUrl: String): FeedDto {
        val channel = element.getElementsByTagName("channel").item(0) as Element

        val title = getElementText(channel, "title") ?: "Unknown Feed"
        val description = getElementText(channel, "description") ?: ""
        val imageUrl = channel.getElementsByTagName("image")
            .let { if (it.length > 0) getElementText(it.item(0) as Element, "url") else null }

        val items = channel.getElementsByTagName("item")
        val articles = mutableListOf<ArticleDto>()

        for (i in 0 until items.length) {
            val item = items.item(i) as Element
            val article = parseRssItem(item, feedUrl)
            articles.add(article)
        }

        return FeedDto(
            url = feedUrl,
            title = title,
            description = description,
            imageUrl = imageUrl,
            articles = articles
        )
    }

    /**
     * Parse RSS item
     */
    private fun parseRssItem(item: Element, feedUrl: String): ArticleDto {
        val title = getElementText(item, "title") ?: "Untitled"
        val description = getElementText(item, "description") ?: ""
        val content = getElementText(item, "content:encoded") ?: description
        val url = getElementText(item, "link") ?: ""
        val author = getElementText(item, "author") ?: getElementText(item, "dc:creator")
        val pubDate = getElementText(item, "pubDate") ?: getElementText(item, "published")
        val imageUrl = extractImageUrl(item)

        return ArticleDto(
            feedUrl = feedUrl,
            title = title,
            description = description,
            content = content,
            url = url,
            imageUrl = imageUrl,
            author = author,
            publishedAt = parsePubDate(pubDate)
        )
    }

    /**
     * Parse Atom feed
     */
    private fun parseAtom(element: Element, feedUrl: String): FeedDto {
        val title = getElementText(element, "title") ?: "Unknown Feed"
        val subtitle = getElementText(element, "subtitle") ?: ""
        val imageUrl = element.getElementsByTagName("logo")
            .let { if (it.length > 0) it.item(0).textContent else null }

        val entries = element.getElementsByTagName("entry")
        val articles = mutableListOf<ArticleDto>()

        for (i in 0 until entries.length) {
            val entry = entries.item(i) as Element
            val article = parseAtomEntry(entry, feedUrl)
            articles.add(article)
        }

        return FeedDto(
            url = feedUrl,
            title = title,
            description = subtitle,
            imageUrl = imageUrl,
            articles = articles
        )
    }

    /**
     * Parse Atom entry
     */
    private fun parseAtomEntry(entry: Element, feedUrl: String): ArticleDto {
        val title = getElementText(entry, "title") ?: "Untitled"
        val summary = getElementText(entry, "summary") ?: ""
        val content = getElementText(entry, "content") ?: summary

        val linkNodes = entry.getElementsByTagName("link")
        var url = ""
        for (i in 0 until linkNodes.length) {
            val link = linkNodes.item(i) as Element
            if (link.getAttribute("rel") == "alternate" || link.getAttribute("rel").isEmpty()) {
                url = link.getAttribute("href")
                break
            }
        }

        val author = entry.getElementsByTagName("author")
            .let { if (it.length > 0) getElementText(it.item(0) as Element, "name") else null }
        val published = getElementText(entry, "published") ?: getElementText(entry, "updated")
        val imageUrl = extractImageUrl(entry)

        return ArticleDto(
            feedUrl = feedUrl,
            title = title,
            description = summary,
            content = content,
            url = url,
            imageUrl = imageUrl,
            author = author,
            publishedAt = parsePubDate(published)
        )
    }

    /**
     * Get text content from XML element
     */
    private fun getElementText(parent: Element, tagName: String): String? {
        val nodeList = parent.getElementsByTagName(tagName)
        return if (nodeList.length > 0) {
            nodeList.item(0)?.textContent
        } else null
    }

    /**
     * Extract image URL from media content or enclosures
     */
    private fun extractImageUrl(element: Element): String? {
        // Try media:content
        val mediaContent = element.getElementsByTagName("media:content")
        if (mediaContent.length > 0) {
            val media = mediaContent.item(0) as Element
            if (media.getAttribute("medium") == "image") {
                return media.getAttribute("url")
            }
        }

        // Try enclosure
        val enclosure = element.getElementsByTagName("enclosure")
        if (enclosure.length > 0) {
            val enc = enclosure.item(0) as Element
            if (enc.getAttribute("type")?.startsWith("image/") == true) {
                return enc.getAttribute("url")
            }
        }

        return null
    }

    /**
     * Parse publication date to timestamp
     */
    private fun parsePubDate(dateString: String?): Long {
        if (dateString == null) return System.currentTimeMillis()

        return try {
            // Try RFC 822 format (RSS)
            java.text.SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", java.util.Locale.US)
                .parse(dateString)?.time
                ?: System.currentTimeMillis()
        } catch (e: Exception) {
            try {
                // Try ISO 8601 format (Atom)
                java.time.Instant.parse(dateString).toEpochMilli()
            } catch (e: Exception) {
                System.currentTimeMillis()
            }
        }
    }
}
