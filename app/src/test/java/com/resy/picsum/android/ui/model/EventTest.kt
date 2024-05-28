package com.resy.picsum.android.ui.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class EventTest {
    @Test
    fun testEvent() {
        val event = Event("Some test")
        val firstValue = event.collect()
        val secondValue = event.collect()
        assertEquals("First value should be set", "Some test", firstValue)
        assertNull("Second value should be null", secondValue)
    }
}
