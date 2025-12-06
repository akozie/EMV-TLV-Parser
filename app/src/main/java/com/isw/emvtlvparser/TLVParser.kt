package com.isw.emvtlvparser

import com.isw.emvtlvparser.model.TLV

object TLVParser {

    fun parse(hex: String, recursive: Boolean = true): List<TLV> {
        val cleanedHex = hex.replace("\\s".toRegex(), "").uppercase()
        if (cleanedHex.length % 2 != 0) throw IllegalArgumentException("Invalid hex length")

        val bytes = cleanedHex.chunked(2).map { it.toInt(16) }
        val result = mutableListOf<TLV>()
        var index = 0

        while (index < bytes.size) {
            // TAG
            if (index >= bytes.size) throw IllegalArgumentException("Unexpected end while reading tag")

            var tag = "%02X".format(bytes[index])
            index++

            // Multi-byte tag
            if ((tag.toInt(16) and 0x1F) == 0x1F) {
                var b: Int
                do {
                    if (index >= bytes.size) throw IllegalArgumentException("Incomplete multi-byte tag")
                    b = bytes[index]
                    tag += "%02X".format(b)
                    index++
                } while ((b and 0x80) == 0x80)
            }

            //  LENGTH
            if (index >= bytes.size) throw IllegalArgumentException("Missing length byte for tag $tag")

            val firstLen = bytes[index]
            index++

            val length = if ((firstLen and 0x80) == 0x80) {
                val numBytes = firstLen and 0x7F
                if (index + numBytes > bytes.size) throw IllegalArgumentException("Incomplete length encoding for tag $tag")
                var len = 0
                repeat(numBytes) {
                    len = (len shl 8) or bytes[index]
                    index++
                }
                len
            } else firstLen

            //  VALUE
            if (index + length > bytes.size) break
            val valueBytes = bytes.subList(index, index + length)
            val valueHex = valueBytes.joinToString("") { "%02X".format(it) }


            // BUILD TLV
            val interpretation = interpret(tag, valueBytes)
            val tlv = TLV(tag, length, valueHex, interpretation)
            result.add(tlv)

            // RECURSION
            // Only parse children of known templates (6F, E0)
            if (recursive && (tag.equals("6F", true) || tag.equals("E0", true))) {
                val nested = parse(valueHex, recursive = false) // disable further recursion
                result.addAll(nested)
            }

            index += length
        }

        return result
    }

    private fun interpret(tag: String, valueBytes: List<Int>): String {
        return when (tag.uppercase()) {
            "9F02" -> {
                val amount = valueBytes.joinToString("") { "%02d".format(it) }.toLong()
                "%.2f".format(amount / 100.0)
            }
            "5A", "57" -> valueBytes.joinToString("") { "%X".format(it) }.trimEnd('F')
            "9F26" -> "ARQC: " + valueBytes.joinToString("") { "%02X".format(it) }
            "9F10" -> "Issuer Data: " + valueBytes.joinToString("") { "%02X".format(it) }
            "84" -> "AID: " + valueBytes.joinToString("") { "%02X".format(it) }
            else -> ""
        }
    }

}
