package dev.kotx.kdaw.entity.server

enum class Region(
    val displayName: String, val id: String
) {
    US_WEST("US West", "us-west"),
    US_EAST("US East", "us-east"),
    US_CENTRAL("US Central", "us-central"),
    US_SOUTH("US South", "us-south"),
    SINGAPORE("Singapore", "singapore"),
    SOUTH_AFRICA("South Africa", "southafrica"),
    SYDNEY("Sydney", "sydney"),
    EUROPE("Europe", "europe"),
    BRAZIL("Brazil", "brazil"),
    HONG_KONG("Hong Kong", "hongkong"),
    RUSSIA("Russia", "russia"),
    JAPAN("Japan", "japan"),
    INDIA("India", "india"),
    Dubai("Dubai", "dubai"),
    ATLANTA("Atlanta", "atlanta"),
    ST_PETERSBURG("ST Petersburg", "st-pete"),
    AMSTERDAM("Amsterdam", "amsterdam"),
    LONDON("London", "london"),
    FRANKFURT("Frankfurt", "frankfurt"),
    EU_CENTRAL("Central Europe", "eu-central"),
    EU_WEST("Western Europe", "eu-west"),
}