package com.druk.lmplayground.models

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import java.io.File

class ModelInfoProviderTest {
    @Test
    fun `models list is sorted by name`() {
        val list = ModelInfoProvider.buildModelList(File("."))
        // Verify not empty
        assertFalse(list.isEmpty())
        // Verify sorted
        val sorted = list.sortedBy { it.name }
        assertEquals(sorted, list)
    }
}
