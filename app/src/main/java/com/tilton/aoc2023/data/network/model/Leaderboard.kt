package com.tilton.aoc2023.data.network.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonTransformingSerializer

@Serializable
data class Leaderboard(
    val event: String,
    @Serializable(with = MemberListSerializer::class)
    val members: List<Member>
)

object MemberListSerializer : JsonTransformingSerializer<List<Member>>(ListSerializer(Member.serializer())) {
    override fun transformDeserialize(element: JsonElement): JsonElement {
        val jsonObject = element as JsonObject
        return JsonArray(jsonObject.toList().map { it.second })
    }
}
