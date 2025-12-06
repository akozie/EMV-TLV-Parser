package com.isw.emvtlvparser

import org.junit.Assert.*
import org.junit.Test


class TLVParserTest {

    // -------------------------------
    // Correct parsing
    // -------------------------------

    /** * Simple primitive tag parsing. */
    @Test fun testParseSimpleTag_5A() {
        val hex = "5A084761739001234567"
        val result = TLVParser.parse(hex)
        assertEquals(1, result.size)
        val tlv = result[0]
        assertEquals("5A", tlv.tag)
        assertEquals(8, tlv.length)
        assertEquals("4761739001234567", tlv.value)
    }


    @Test
    fun testInterpretAmount_9F02() {
        val hex = "9F0206000000010000"
        val result = TLVParser.parse(hex)

        assertEquals(1, result.size)
        val tlv = result[0]
        assertEquals("9F02", tlv.tag)
        assertEquals("000000010000", tlv.value)
        assertEquals("100.00", tlv.interpretation)
    }


    @Test
    fun testMultipleTLVs() {
        val hex = "5A0847617390012345679F0206000000010000"
        val result = TLVParser.parse(hex)

        assertEquals(2, result.size)
        assertEquals("5A", result[0].tag)
        assertEquals("9F02", result[1].tag)
    }

    @Test
    fun testUnknownTag() {
        val hex = "DF010101"
        val result = TLVParser.parse(hex)

        assertEquals(1, result.size)
        val tlv = result[0]
        assertEquals("DF01", tlv.tag)
        assertEquals(1, tlv.length)
        assertEquals("01", tlv.value)
        assertEquals("", tlv.interpretation)
    }

    // -------------------------------
    // Invalid / truncated TLV
    // -------------------------------

    @Test(expected = IllegalArgumentException::class)
    fun testInvalidHexLength() {
        TLVParser.parse("5A123") // odd-length hex
    }

    @Test(expected = IllegalArgumentException::class)
    fun testMissingLengthByte() {
        TLVParser.parse("5A") // tag only, no length
    }

    // -------------------------------
    // Multi-byte tag tests
    // -------------------------------

    @Test
    fun testMultiByteTag() {
        val hex = "9F0206000000010000"
        val result = TLVParser.parse(hex)

        assertEquals(1, result.size)
        val tlv = result[0]
        assertEquals("9F02", tlv.tag)
        assertEquals(6, tlv.length)
        assertEquals("000000010000", tlv.value)
    }
}

