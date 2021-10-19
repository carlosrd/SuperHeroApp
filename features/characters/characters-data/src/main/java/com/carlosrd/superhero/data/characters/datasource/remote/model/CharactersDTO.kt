package com.carlosrd.superhero.data.characters.datasource.remote.model

data class CharactersDTO(
    val code: Int,
    val status: String,
    val data: Data
) {
    data class Data(
        val offset: Int,
        val limit: Int,
        val total: Int,
        val count: Int,
        val results: List<Results>
    ) {
        data class Results(
            val id: Long,
            val name: String,
            val description: String,
            val modified: String,
            val resourceUri: String,
            val urls: List<Url>,
            val thumbnail: Thumbnail,
            val comics: Comics,
            val stories: Stories,
            val events: Events,
            val series: Series,
        ) {
            data class Url(
                val type: String,
                val url: String
            )

            data class Thumbnail(
                val path: String,
                val extension: String
            )

            data class Comics(
                val available: Int,
                val returned: Int,
                val collectionURI: String,
                val items: List<Comic>
            ) {
                data class Comic(val resourceURI: String,
                                 val name: String)
            }

            data class Stories(
                val available: Int,
                val returned: Int,
                val collectionURI: String,
                val items: List<Story>
            ) {
                data class Story(val resourceURI: String,
                                 val name: String,
                                 val type: String)
            }

            data class Events(
                val available: Int,
                val returned: Int,
                val collectionURI: String,
                val items: List<Event>
            ) {
                data class Event(val resourceURI: String,
                                 val name: String)
            }

            data class Series(
                val available: Int,
                val returned: Int,
                val collectionURI: String,
                val items: List<Serie>
            ) {
                data class Serie(val resourceURI: String,
                                 val name: String)
            }
        }
    }
}

/*
{
    "code": "int",
    "status": "string",
    "copyright": "string",
    "attributionText": "string",
    "attributionHTML": "string",
    "data": {
    "offset": "int",
    "limit": "int",
    "total": "int",
    "count": "int",
    "results": [
    {
        "id": "int",
        "name": "string",
        "description": "string",
        "modified": "Date",
        "resourceURI": "string",
        "urls": [
        {
            "type": "string",
            "url": "string"
        }
        ],
        "thumbnail": {
        "path": "string",
        "extension": "string"
    },
        "comics": {
        "available": "int",
        "returned": "int",
        "collectionURI": "string",
        "items": [
        {
            "resourceURI": "string",
            "name": "string"
        }
        ]
    },
        "stories": {
        "available": "int",
        "returned": "int",
        "collectionURI": "string",
        "items": [
        {
            "resourceURI": "string",
            "name": "string",
            "type": "string"
        }
        ]
    },
        "events": {
        "available": "int",
        "returned": "int",
        "collectionURI": "string",
        "items": [
        {
            "resourceURI": "string",
            "name": "string"
        }
        ]
    },
        "series": {
        "available": "int",
        "returned": "int",
        "collectionURI": "string",
        "items": [
        {
            "resourceURI": "string",
            "name": "string"
        }
        ]
    }
    }
    ]
},
    "etag": "string"
}
*/