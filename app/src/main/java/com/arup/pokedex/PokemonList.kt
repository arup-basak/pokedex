package com.arup.pokedex

data class PokemonList(
    val count: Int?,
    val next: String?,
    val previous: String?,
    val results: List<PokemonListResult>
)

data class PokemonListResult(
    val name: String,
    val url: String
)

fun getEmptyPokemonList(): PokemonList {
    return PokemonList(
        count = 0,
        next = null,
        previous = null,
        results = emptyList()
    )
}