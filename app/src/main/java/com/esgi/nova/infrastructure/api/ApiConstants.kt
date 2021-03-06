package com.esgi.nova.infrastructure.api

import com.esgi.nova.BuildConfig

object ApiConstants {
    const val BaseUrl = BuildConfig.apiUrl
    const val SecureNetworkOn: Boolean = BuildConfig.securedNetworkOn == "true"

    object EndPoints {
        const val Auth = "auth/"
        const val Difficulties = "difficulties/"
        const val Events = "events/"
        const val Games = "games/"
        const val Languages = "languages/"
        const val Load = "load/"
        const val Daily = "daily/"
        const val Resources = "resources/"
        const val Users = "users/"
        const val Login = "login/"
    }

    object CustomMediaType {
        object Application {
            const val ResourceWithTranslations = "application/vnd+nova.resource_translations+json"
            const val ResourceName = "application/vnd+nova.resource_name+json"
            const val DifficultyName = "application/vnd+nova.difficulty_name+json"
            const val DetailedDifficulty = "application/vnd+nova.detailed_difficulty+json"
            const val DetailedDifficultyWithAvailableActions =
                "application/vnd+nova.detailed_difficulty_with_available_actions+json"
            const val TranslatedDifficulty = "application/vnd+nova.translated_difficulty+json"
            const val UserUsername = "application/vnd+nova.user_username+json"
            const val UserAdminEdit = "application/vnd+nova.user_admin_edit+json"
            const val UserRole = "application/vnd+nova.user_role+json"
            const val EventTranslation = "application/vnd+nova.event_translation+json"
            const val EventTitle = "application/vnd+nova.event_title+json"
            const val DetailedEvent = "application/vnd+nova.detailed_event+json"
            const val TranslatedEvent = "application/vnd+nova.translated_event+json"
            const val TranslatedResource = "application/vnd+nova.translated_resource+json"
            const val LanguageWithAvailableActions =
                "application/vnd+nova.language_with_available_actions+json"
            const val LeaderBoardGame = "application/vnd+nova.leader_board_game+json"
            const val GameState = "application/vnd+nova.game_state+json"

        }
    }
}

