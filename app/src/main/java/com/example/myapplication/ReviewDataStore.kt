package com.example.myapplication

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "review_prefs")

class ReviewDataStore(private val context: Context) {

    companion object {
        private val DRAFT_CONTENT = stringPreferencesKey("draft_content")
    }

    val draftContent: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[DRAFT_CONTENT] ?: ""
        }

    suspend fun saveDraft(content: String) {
        context.dataStore.edit { preferences ->
            preferences[DRAFT_CONTENT] = content
        }
    }
}