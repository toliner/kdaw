package dev.kotx.diskord

import dev.kotx.diskord.rest.*
import dev.kotx.diskord.util.*

class DiskordImpl(token: String) : Diskord(token) {
    private val restClient = RestClient(this)

    override suspend fun test() {
        restClient.request(EndPoint.Channel.Message.Create(840268542996774912)) {
            "content" to "hello~~~~"
            "components" to jsonArray {
                +json {
                    "type" to 1
                    "components" to jsonArray {
                        +json {
                            "type" to 2
                            "label" to "Click me!"
                            "style" to 1
                            "custom_id" to "click_one"
                        }
                    }
                }
            }
        }
    }
}