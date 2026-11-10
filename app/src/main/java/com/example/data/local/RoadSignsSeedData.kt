package com.example.data.local

import com.example.data.model.RoadSignItem

object RoadSignsSeedData {
    val signs: List<RoadSignItem> = listOf(
        // Ogohlantiruvchi (Warning)
        RoadSignItem(
            id = "s_1_1",
            code = "1.1",
            category = "Ogohlantiruvchi",
            nameUz = "Shlagbaumli temir yo'l kesishmasi",
            nameRu = "Железнодорожный переезд со шлагбаумом",
            descriptionUz = "Shlagbaum bilan jihozlangan temir yo'l kesishmasiga yaqinlashayotganlik haqida ogohlantiradi. Aholi punktlarida 50-100 m, aholi punktlaridan tashqarida 150-300 m masofada o'rnatiladi.",
            descriptionRu = "Предупреждает о приближении к железнодорожному переезду, оборудованному шлагбаумом.",
            shape = "TRIANGLE_RED",
            symbolType = "TRAIN_FENCE"
        ),
        RoadSignItem(
            id = "s_1_2",
            code = "1.2",
            category = "Ogohlantiruvchi",
            nameUz = "Shlagbaumsiz temir yo'l kesishmasi",
            nameRu = "Железнодорожный переезд без шлагбаума",
            descriptionUz = "Shlagbaum bilan jihozlanmagan temir yo'l kesishmasiga yaqinlashayotganlik haqida ogohlantiradi.",
            descriptionRu = "Предупреждает о переезде через железнодорожные пути без шлагбаума.",
            shape = "TRIANGLE_RED",
            symbolType = "TRAIN_LOCOMOTIVE"
        ),
        RoadSignItem(
            id = "s_1_22",
            code = "1.22",
            category = "Ogohlantiruvchi",
            nameUz = "Piyodalar o'tish joyi",
            nameRu = "Пешеходный переход",
            descriptionUz = "5.16.1, 5.16.2 belgilari va (yoki) 1.14.1, 1.14.2 chiziqlari bilan belgilangan piyodalar o'tish joyiga yaqinlashayotganlik haqida ogohlantiradi.",
            descriptionRu = "Предупреждает о приближении к пешеходному переходу.",
            shape = "TRIANGLE_RED",
            symbolType = "PEDESTRIAN"
        ),
        RoadSignItem(
            id = "s_1_23",
            code = "1.23",
            category = "Ogohlantiruvchi",
            nameUz = "Bolalar",
            nameRu = "Дети",
            descriptionUz = "Bolalar muassasasi (maktab, bog'cha) yonidagi yo'l qismiga yaqinlashayotganlik haqida ogohlantiradi.",
            descriptionRu = "Участок дороги вблизи детского учреждения, где возможно появление детей.",
            shape = "TRIANGLE_RED",
            symbolType = "CHILDREN"
        ),

        // Imtiyoz (Priority)
        RoadSignItem(
            id = "s_2_1",
            code = "2.1",
            category = "Imtiyoz",
            nameUz = "Asosiy yo'l",
            nameRu = "Главная дорога",
            descriptionUz = "Tartibga solinmagan chorrahalarda birinchi navbatda o'tish huquqini beradi.",
            descriptionRu = "Предоставляет право преимущественного проезда нерегулируемых перекрестков.",
            shape = "DIAMOND_YELLOW",
            symbolType = "MAIN_ROAD"
        ),
        RoadSignItem(
            id = "s_2_4",
            code = "2.4",
            category = "Imtiyoz",
            nameUz = "Yo'l bering",
            nameRu = "Уступите дорогу",
            descriptionUz = "Haydovchi kesib o'tilayotgan yo'ldan, 7.13 lavhasi bo'lganda esa asosiy yo'ldan kelayotgan transport vositalariga yo'l berishi shart.",
            descriptionRu = "Водитель должен уступить дорогу транспортным средствам, движущимся по пересекаемой дороге.",
            shape = "TRIANGLE_RED_INVERTED",
            symbolType = "YIELD"
        ),
        RoadSignItem(
            id = "s_2_5",
            code = "2.5",
            category = "Imtiyoz",
            nameUz = "To'xtamasdan harakatlanish taqiqlangan",
            nameRu = "Движение без остановки запрещено (STOP)",
            descriptionUz = "To'xtash chizig'i oldida, agar u bo'lmasa, kesib o'tiladigan qatnov qismi chetida to'xtamasdan o'tish taqiqlanadi.",
            descriptionRu = "Запрещается движение без остановки перед стоп-линией или краем пересекаемой проезжей части.",
            shape = "OCTAGON_RED",
            symbolType = "STOP"
        ),

        // Taqiqlivchi (Prohibitory)
        RoadSignItem(
            id = "s_3_1",
            code = "3.1",
            category = "Taqiqlovchi",
            nameUz = "Kirish taqiqlangan (G'isht)",
            nameRu = "Въезд запрещен («Кирпич»)",
            descriptionUz = "Barcha transport vositalarining ushbu yo'nalishda kirishi qat'iyan taqiqlanadi.",
            descriptionRu = "Запрещается въезд всех транспортных средств в данном направлении.",
            shape = "CIRCLE_RED",
            symbolType = "NO_ENTRY"
        ),
        RoadSignItem(
            id = "s_3_20",
            code = "3.20",
            category = "Taqiqlovchi",
            nameUz = "Quvib o'tish taqiqlangan",
            nameRu = "Обгон запрещен",
            descriptionUz = "Barcha transport vositalarini quvib o'tish taqiqlanadi (soatiga 30 km/soatdan kam tezlikda harakatlanayotgan yakka transportlar bundan mustasno).",
            descriptionRu = "Запрещается обгон всех транспортных средств.",
            shape = "CIRCLE_RED",
            symbolType = "NO_OVERTAKE"
        ),
        RoadSignItem(
            id = "s_3_24",
            code = "3.24",
            category = "Taqiqlovchi",
            nameUz = "Yuqori tezlik cheklangan (60 km/h)",
            nameRu = "Ограничение максимальной скорости",
            descriptionUz = "Belgida ko'rsatilganidan ortiq tezlikda harakatlanish taqiqlanadi.",
            descriptionRu = "Запрещается движение со скоростью, превышающей указанную на знаке.",
            shape = "CIRCLE_RED",
            symbolType = "SPEED_60"
        ),
        RoadSignItem(
            id = "s_3_27",
            code = "3.27",
            category = "Taqiqlovchi",
            nameUz = "To'xtash taqiqlangan",
            nameRu = "Остановка запрещена",
            descriptionUz = "Transport vositalarining to'xtashi va to'xtab turishi qat'iyan man etiladi.",
            descriptionRu = "Запрещаются остановка и стоянка транспортных средств.",
            shape = "CIRCLE_RED",
            symbolType = "NO_STOPPING"
        ),
        RoadSignItem(
            id = "s_3_28",
            code = "3.28",
            category = "Taqiqlovchi",
            nameUz = "To'xtab turish taqiqlangan",
            nameRu = "Стоянка запрещена",
            descriptionUz = "Transport vositalarining to'xtab turishi taqiqlanadi (5 daqiqagacha odam mindirish/tushirish ruxsat etiladi).",
            descriptionRu = "Запрещается стоянка транспортных средств.",
            shape = "CIRCLE_RED",
            symbolType = "NO_PARKING"
        ),

        // Buyuruvchi (Mandatory)
        RoadSignItem(
            id = "s_4_1_1",
            code = "4.1.1",
            category = "Buyuruvchi",
            nameUz = "Harakat faqat to'g'riga",
            nameRu = "Движение прямо",
            descriptionUz = "Faqat to'g'riga harakatlanishga ruxsat beriladi.",
            descriptionRu = "Разрешается движение только прямо.",
            shape = "CIRCLE_BLUE",
            symbolType = "ARROW_STRAIGHT"
        ),
        RoadSignItem(
            id = "s_4_3",
            code = "4.3",
            category = "Buyuruvchi",
            nameUz = "Aylanma harakat",
            nameRu = "Круговое движение",
            descriptionUz = "Chorrahada strelkalar ko'rsatilgan yo'nalishda aylanma harakatlanish buyuriladi.",
            descriptionRu = "Разрешается движение только в направлении, указанном стрелками.",
            shape = "CIRCLE_BLUE",
            symbolType = "ROUNDABOUT"
        ),

        // Axborot-ishora (Informative)
        RoadSignItem(
            id = "s_5_1",
            code = "5.1",
            category = "Axborot-ishora",
            nameUz = "Avtomagistral",
            nameRu = "Автомагистраль",
            descriptionUz = "Avtomagistralda harakatlanishning maxsus tartibi amal qiladigan yo'l.",
            descriptionRu = "Дорога, на которой действуют специальные требования правил для автомагистралей.",
            shape = "SQUARE_BLUE",
            symbolType = "HIGHWAY"
        ),
        RoadSignItem(
            id = "s_5_16_1",
            code = "5.16.1",
            category = "Axborot-ishora",
            nameUz = "Piyodalar o'tish joyi",
            nameRu = "Пешеходный переход",
            descriptionUz = "Piyodalar yo'lni xavfsiz kesib o'tish joyining chegarasini belgilaydi.",
            descriptionRu = "Указывает границу пешеходного перехода.",
            shape = "SQUARE_BLUE",
            symbolType = "PEDESTRIAN_BLUE"
        ),

        // Servis
        RoadSignItem(
            id = "s_6_1",
            code = "6.1",
            category = "Servis",
            nameUz = "Birinchi tibbiy yordam punkti",
            nameRu = "Пункт первой медицинской помощи",
            descriptionUz = "Tibbiy yordam ko'rsatish punkti joylashganligini bildiradi.",
            descriptionRu = "Указывает местонахождение пункта первой помощи.",
            shape = "SQUARE_BLUE",
            symbolType = "FIRST_AID"
        ),
        RoadSignItem(
            id = "s_6_3",
            code = "6.3",
            category = "Servis",
            nameUz = "Yoqilg'i quyish shoxobchasi (AYQSh)",
            nameRu = "Автозаправочная станция (АЗС)",
            descriptionUz = "Yoqilg'i quyish stansiyasi borligini bildiradi.",
            descriptionRu = "Указывает на наличие АЗС.",
            shape = "SQUARE_BLUE",
            symbolType = "GAS_STATION"
        )
    )
}
