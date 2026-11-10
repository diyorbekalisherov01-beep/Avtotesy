package com.example.data.local

import com.example.data.model.QuestionCategory

object QuestionCategories {
    val list: List<QuestionCategory> = listOf(
        QuestionCategory(1, "Umumiy qoidalar", "Общие положения", "Rule"),
        QuestionCategory(2, "Yo'l belgilari", "Дорожные знаки", "Signpost"),
        QuestionCategory(3, "Yo'l chiziqlari", "Дорожная разметка", "LinearScale"),
        QuestionCategory(4, "Svetofor ishoralari", "Сигналы светофора", "Traffic"),
        QuestionCategory(5, "Tartibga soluvchi ishoralari", "Сигналы регулировщика", "SportsHandball"),
        QuestionCategory(6, "Chorrahada harakatlanish", "Проезд перекрестков", "AltRoute"),
        QuestionCategory(7, "Quvib o'tish", "Обгон и опережение", "FastForward"),
        QuestionCategory(8, "Harakatlanish tezligi", "Скорость движения", "Speed"),
        QuestionCategory(9, "To'xtash va to'xtab turish", "Остановка и стоянка", "LocalParking"),
        QuestionCategory(10, "Piyodalar o'tish joylari", "Пешеходные переходы", "DirectionsWalk"),
        QuestionCategory(11, "Temir yo'l kesishmalari", "Железнодорожные переезды", "Train"),
        QuestionCategory(12, "Avtomagistrallarda harakatlanish", "Движение по автомагистралям", "DirectionsCar"),
        QuestionCategory(13, "Yo'lda transport vositalarining joylashuvi", "Расположение ТС на проезжей части", "ViewColumn"),
        QuestionCategory(14, "Manevr qilish va burilish", "Маневрирование", "TurnRight"),
        QuestionCategory(15, "Odam tashish", "Перевозка людей", "People"),
        QuestionCategory(16, "Yuk tashish", "Перевозка грузов", "LocalShipping"),
        QuestionCategory(17, "Transport vositalarining texnik holati", "Техническое состояние ТС", "Build"),
        QuestionCategory(18, "Favqulodda vaziyatlar va xavfsizlik", "Аварийные ситуации", "Warning"),
        QuestionCategory(19, "Birinchi tibbiy yordam", "Первая медицинская помощь", "MedicalServices"),
        QuestionCategory(20, "Haydovchilarning umumiy majburiyatlari", "Обязанности водителей", "AssignmentInd")
    )

    fun getCategoryName(id: Int, isRu: Boolean = false): String {
        val cat = list.find { it.id == id } ?: return "Umumiy qoidalar"
        return if (isRu) cat.nameRu else cat.nameUz
    }
}
