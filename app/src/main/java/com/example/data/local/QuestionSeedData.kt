package com.example.data.local

import com.example.data.model.QuestionEntity

object QuestionSeedData {
    val initialQuestions: List<QuestionEntity> = listOf(
        // Category 1: Umumiy qoidalar
        QuestionEntity(
            id = 1,
            ticketNumber = 1,
            ticketQuestionIndex = 1,
            categoryId = 1,
            textUzLatin = "O'zbekiston Respublikasi hududida transport vositalarining harakati qaysi tomondan amalga oshiriladi?",
            textUzCyrillic = "Ўзбекистон Республикаси ҳудудида транспорт воситаларининг ҳаракати қайси томондан амалга оширилади?",
            textRu = "С какой стороны осуществляется движение транспортных средств на территории Республики Узбекистан?",
            optionsUzLatin = listOf(
                "O'ng tomondan",
                "Chap tomondan",
                "Yo'lning har qanday qulay tomonidan",
                "Faqat harakat jadalligi yuqori bo'lgan tomondan"
            ),
            optionsUzCyrillic = listOf(
                "Ўнг томондан",
                "Чап томондан",
                "Йўлнинг ҳар қандай қулай томонидан",
                "Фақат ҳаракат жадаллиги юқори бўлган томондан"
            ),
            optionsRu = listOf(
                "С правой стороны",
                "С левой стороны",
                "С любой удобной стороны дороги",
                "Только со стороны с интенсивным движением"
            ),
            correctOptionIndex = 0,
            explanationUzLatin = "O'zbekiston Respublikasi Vazirlar Mahkamasining 2022-yil 12-apreldagi 172-son qarori bilan tasdiqlangan Yo'l harakati qoidalarining 6-bandiga binoan: O'zbekiston Respublikasida yo'llarda transport vositalarining o'ng tomonlama harakatlanish tartibi o'rnatilgan.",
            explanationUzCyrillic = "Ўзбекистон Республикаси Вазирлар Маҳкамасининг 2022 йил 12 апрелдаги 172-сон қарори билан тасдиқланган Йўл ҳаракати қоидаларининг 6-бандига биноан: Ўзбекистон Республикасида йўлларда транспорт воситаларининг ўнг томонлама ҳаракатланиш тартиби ўрнатилган.",
            explanationRu = "Согласно пункту 6 Правил дорожного движения, утвержденных Постановлением Кабинета Министров № 172 от 12 апреля 2022 года, в Республике Узбекистан установлено правостороннее движение транспортных средств.",
            lawReference = "LexUZ: 172-son VM qarori, 6-band",
            difficulty = "EASY",
            diagramType = "NONE",
            isA1 = true
        ),

        // Category 6: Chorrahada harakatlanish (Equal crossroad)
        QuestionEntity(
            id = 2,
            ticketNumber = 1,
            ticketQuestionIndex = 2,
            categoryId = 6,
            textUzLatin = "Teng ahamiyatli yo'llar chorrahasida to'g'riga harakatlanayotgan haydovchi o'ng tomondan kelayotgan transport vositasiga yo'l berishi shartmi?",
            textUzCyrillic = "Тенг аҳамиятли йўллар чорраҳасида тўғрига ҳаракатланаётган ҳайдовчи ўнг томондан келаётган транспорт воситасига йўл бериши шартми?",
            textRu = "Обязан ли водитель, движущийся прямо на перекрестке равнозначных дорог, уступить дорогу ТС, приближающемуся справа?",
            optionsUzLatin = listOf(
                "Ha, o'ng tomondagi to'siq qoidasiga ko'ra yo'l berishi shart",
                "Yo'q, to'g'riga harakatlanuvchi har doim ustunlikka ega",
                "Faqat u yuk avtomobili bo'lsa yo'l beradi",
                "Faqat chorrahaga birinchi kirib kelgan bo'lsa yo'l beradi"
            ),
            optionsUzCyrillic = listOf(
                "Ҳа, ўнг томондаги тўсиқ қоидасига кўра йўл бериши шарт",
                "Йўқ, тўғрига ҳаракатланувчи ҳар доим устунликка эга",
                "Фақат у юк автомобили бўлса йўл беради",
                "Фақат чорраҳага биринчи кириб келган бўлса йўл беради"
            ),
            optionsRu = listOf(
                "Да, обязан уступить согласно правилу «помехи справа»",
                "Нет, движущийся прямо всегда имеет преимущество",
                "Только если это грузовой автомобиль",
                "Только если въехал на перекресток первым"
            ),
            correctOptionIndex = 0,
            explanationUzLatin = "YHQ 103-band: Teng ahamiyatli yo'llar chorrahasida relssiz transport vositasining haydovchisi o'ng tomondan yaqinlashayotgan transport vositalariga yo'l berishi shart.",
            explanationUzCyrillic = "ЙҲҚ 103-банд: Тенг аҳамиятли йўллар чорраҳасида рельссиз транспорт воситасининг ҳайдовчиси ўнг томондан яқинлашаётган транспорт воситаларига йўл бериши шарт.",
            explanationRu = "Пункт 103 ПДД: На перекрестке равнозначных дорог водитель безрельсового ТС обязан уступить дорогу ТС, приближающимся справа.",
            lawReference = "LexUZ: 172-son VM qarori, 103-band",
            difficulty = "MEDIUM",
            diagramType = "CROSSROAD_EQUAL",
            isA1 = true
        ),

        // Category 2: Yo'l belgilari (Priority road sign 2.1)
        QuestionEntity(
            id = 3,
            ticketNumber = 1,
            ticketQuestionIndex = 3,
            categoryId = 2,
            textUzLatin = "2.1 'Asosiy yo'l' belgisi haydovchiga qanday imtiyoz beradi?",
            textUzCyrillic = "2.1 'Асосий йўл' белгиси ҳайдовчига қандай имтиёз беради?",
            textRu = "Какое преимущество предоставляет водителю дорожный знак 2.1 «Главная дорога»?",
            optionsUzLatin = listOf(
                "Tartibga solinmagan chorrahalardan birinchi bo'lib o'tish huquqini beradi",
                "Harakat tezligini 100 km/soatgacha oshirishga ruxsat beradi",
                "Faqat quvib o'tish imkonini beradi",
                "Barcha turdagi to'xtashlarni taqiqlaydi"
            ),
            optionsUzCyrillic = listOf(
                "Тартибга солинмаган чорраҳалардан биринчи бўлиб ўтиш ҳуқуқини беради",
                "Ҳаракат тезлигини 100 км/соатгача оширишга рухсат беради",
                "Фақат қувиб ўтиш имконини беради",
                "Барча турдаги тўхташларни тақиқлайди"
            ),
            optionsRu = listOf(
                "Право преимущественного проезда нерегулируемых перекрестков",
                "Разрешает увеличение скорости до 100 км/ч",
                "Дает право только на обгон",
                "Запрещает любые виды остановок"
            ),
            correctOptionIndex = 0,
            explanationUzLatin = "1-ilova, 2.1 'Asosiy yo'l' belgisi: Tartibga solinmagan chorrahalarda harakatlanishda birinchi navbatda o'tish huquqini beradi.",
            explanationUzCyrillic = "1-илова, 2.1 'Асосий йўл' белгиси: Тартибга солинмаган чорраҳаларда ҳаракатланишда биринчи навбатда ўтиш ҳуқуқини беради.",
            explanationRu = "Приложение 1, знак 2.1 «Главная дорога»: Предоставляет право преимущественного проезда нерегулируемых перекрестков.",
            lawReference = "LexUZ: 172-son VM qarori, 1-ilova, 2.1-band",
            difficulty = "EASY",
            diagramType = "CROSSROAD_PRIORITY",
            isA1 = true
        ),

        // Category 8: Harakatlanish tezligi (Toshkent / Shaharlar ichida tezlik me'yori)
        QuestionEntity(
            id = 4,
            ticketNumber = 1,
            ticketQuestionIndex = 4,
            categoryId = 8,
            textUzLatin = "Aholi punktlarida (shahar va qishloqlarda) yengil avtomobillarning ruxsat etilgan eng yuqori tezligi qancha (agar qo'shimcha belgilar o'rnatilmagan bo'lsa)?",
            textUzCyrillic = "Аҳоли пунктларида (шаҳар ва қишлоқларда) енгил автомобилларнинг рухсат этилган энг юқори тезлиги қанча (агар қўшимча белгилар ўрнатилмаган бўлса)?",
            textRu = "Какова максимально разрешенная скорость движения легковых автомобилей в населенных пунктах Узбекистана (при отсутствии иных знаков)?",
            optionsUzLatin = listOf(
                "60 km/soat (Toshkent, Nukus va viloyat markazlarida 60 km/soat, boshqa aholi punktlarida 70 km/soat)",
                "80 km/soat",
                "90 km/soat",
                "50 km/soat barcha joylarda"
            ),
            optionsUzCyrillic = listOf(
                "60 км/соат (Тошкент, Нукус ва вилоят марказларида 60 км/соат, бошқа аҳоли пунктларида 70 км/соат)",
                "80 км/соат",
                "90 км/соат",
                "50 км/соат барча жойларда"
            ),
            optionsRu = listOf(
                "60 км/ч (в Ташкенте, Нукусе и областных центрах 60 км/ч, в других населенных пунктах 70 км/ч)",
                "80 км/ч",
                "90 км/ч",
                "50 км/ч повсеместно"
            ),
            correctOptionIndex = 0,
            explanationUzLatin = "YHQ 78-band (so'nggi tahrir): Aholi punktlarida transport vositalarining tezligini soatiga 70 kilometrdan, Toshkent va Nukus shaharlarida, viloyatlar hamda tumanlar markazlarida esa soatiga 60 kilometrdan oshirmasdan harakatlanishga ruxsat etiladi.",
            explanationUzCyrillic = "ЙҲҚ 78-банд (сўнгги таҳрир): Аҳоли пунктларида транспорт воситаларининг тезлигини соатига 70 километрдан, Тошкент ва Нукус шаҳарларида, вилоятлар ҳамда туманлар марказларида эса соатига 60 километрдан оширмасдан ҳаракатланишга рухсат этилади.",
            explanationRu = "Пункт 78 ПДД: В населенных пунктах разрешается движение ТС со скоростью не более 70 км/ч, а в городах Ташкент и Нукус, областных и районных центрах — не более 60 км/ч.",
            lawReference = "LexUZ: 172-son VM qarori, 78-band va tegishli o'zgartirishlar",
            difficulty = "MEDIUM",
            diagramType = "NONE",
            isA1 = true
        ),

        // Category 4: Svetofor ishoralari
        QuestionEntity(
            id = 5,
            ticketNumber = 1,
            ticketQuestionIndex = 5,
            categoryId = 4,
            textUzLatin = "Svetoforning miltillovchi sariq ishorasi nimani bildiradi?",
            textUzCyrillic = "Светофорнинг милтилловчи сариқ ишораси нимани билдиради?",
            textRu = "О чем информирует желтый мигающий сигнал светофора?",
            optionsUzLatin = listOf(
                "Harakatlanishga ruxsat beradi va tartibga solinmagan chorraha yoki xavfli joy mavjudligini bildiradi",
                "Tezda to'xtashni talab qiladi, o'tish qat'iyan man etiladi",
                "Faqat chapga burilishga ruxsat beradi",
                "Faqat maxsus transport vositalari o'tishini bildiradi"
            ),
            optionsUzCyrillic = listOf(
                "Ҳаракатланишга рухсат беради ва тартибга солинмаган чорраҳа ёки хавфли жой мавжудлигини билдиради",
                "Тезроқ тўхташни талаб қилади, ўтиш қатъиян ман этилади",
                "Фақат чапга бурилишга рухсат беради",
                "Фақат махсус транспорт воситалари ўтишини билдиради"
            ),
            optionsRu = listOf(
                "Разрешает движение и информирует о нерегулируемом перекрестке или пешеходном переходе",
                "Требует экстренной остановки, проезд категорически запрещен",
                "Разрешает поворот только налево",
                "Информирует о проезде спецтранспорта"
            ),
            correctOptionIndex = 0,
            explanationUzLatin = "YHQ 31-band: Miltillovchi sariq ishora harakatlanishga ruxsat beradi hamda tartibga solinmagan chorraha yoki piyodalar o'tish joyi borligidan xabardor qiladi, xavf-xatardan ogohlantiradi.",
            explanationUzCyrillic = "ЙҲҚ 31-банд: Милтилловчи сариқ ишора ҳаракатланишга рухсат беради ҳамда тартибга солинмаган чорраҳа ёки пиёдалар ўтиш жойи борлигидан хабардор қилади, хавф-хатардан огоҳлантиради.",
            explanationRu = "Пункт 31 ПДД: Желтый мигающий сигнал разрешает движение и информирует о наличии нерегулируемого перекрестка или пешеходного перехода, предупреждает об опасности.",
            lawReference = "LexUZ: 172-son VM qarori, 31-band",
            difficulty = "EASY",
            diagramType = "CROSSROAD_TRAFFIC_LIGHT",
            isA1 = true
        ),

        // Category 5: Tartibga soluvchi ishoralari (Regulator)
        QuestionEntity(
            id = 6,
            ticketNumber = 1,
            ticketQuestionIndex = 6,
            categoryId = 5,
            textUzLatin = "Tartibga soluvchi o'ng qo'lini oldinga cho'zib turganda, uning chap yonboshidan kelayotgan haydovchilarga qaysi yo'nalishlarda harakatlanishga ruxsat etiladi?",
            textUzCyrillic = "Тартибга солувчи ўнг қўлини олдинга чўзиб турганда, унинг чап ёнбошидан келаётган ҳайдовчиларга қайси йўналишларда ҳаракатланишга рухсат этилади?",
            textRu = "Если регулировщик вытянул правую руку вперед, в каких направлениях разрешено движение со стороны его левого бока?",
            optionsUzLatin = listOf(
                "Barcha yo'nalishlarda (to'g'riga, o'ngga, chapga va qayrilib olishga)",
                "Faqat to'g'riga va o'ngga",
                "Faqat chapga va qayrilib olishga",
                "Harakatlanish butunlay taqiqlanadi"
            ),
            optionsUzCyrillic = listOf(
                "Барча йўналишларда (тўғрига, ўнгга, чапга ва қайрилиб олишга)",
                "Фақат тўғрига ва ўнгга",
                "Фақат чапга ва қайрилиб олишга",
                "Ҳаракатланиш бутунлай тақиқланади"
            ),
            optionsRu = listOf(
                "Во всех направлениях (прямо, направо, налево и на разворот)",
                "Только прямо и направо",
                "Только налево и на разворот",
                "Движение запрещено"
            ),
            correctOptionIndex = 0,
            explanationUzLatin = "YHQ 36-band: Tartibga soluvchining o'ng qo'li oldinga cho'zilgan bo'lsa: chap yonboshi tomonidan relssiz transport vositalariga barcha yo'nalishlarda harakatlanishga ruxsat beriladi.",
            explanationUzCyrillic = "ЙҲҚ 36-банд: Тартибга солувчининг ўнг қўли олдинга чўзилган бўлса: чап ёнбоши томонидан рельссиз транспорт воситаларига барча йўналишларда ҳаракатланишга рухсат берилади.",
            explanationRu = "Пункт 36 ПДД: Рука вытянута вперед: со стороны левого бока безрельсовым ТС разрешено движение во всех направлениях.",
            lawReference = "LexUZ: 172-son VM qarori, 36-band",
            difficulty = "HARD",
            diagramType = "POLICE_REGULATOR",
            isA1 = true
        ),

        // Category 7: Quvib o'tish
        QuestionEntity(
            id = 7,
            ticketNumber = 1,
            ticketQuestionIndex = 7,
            categoryId = 7,
            textUzLatin = "Qaysi joylarda transport vositalarini quvib o'tish taqiqlanadi?",
            textUzCyrillic = "Қайси жойларда транспорт воситаларини қувиб ўтиш тақиқланади?",
            textRu = "В каких местах обгон запрещен?",
            optionsUzLatin = listOf(
                "Tartibga solinadigan chorrahalarda, piyodalar o'tish joylarida, temir yo'l kesishmalarida va ularga 100 metr yetmasdan",
                "Faqat aholi punktlaridan tashqaridagi yo'llarda",
                "Faqat sutkaning qorong'i vaqtida",
                "Faqat ikki tasmali yo'llarda"
            ),
            optionsUzCyrillic = listOf(
                "Тартибга солинадиган чорраҳаларда, пиёдалар ўтиш жойларида, темир йўл кесишмаларида ва уларга 100 метр етмасдан",
                "Фақат аҳоли пунктларидан ташқаридаги йўлларда",
                "Фақат сутканинг қоронғи вақтида",
                "Фақат икки тасмали йўлларда"
            ),
            optionsRu = listOf(
                "На регулируемых перекрестках, пешеходных переходах, ж/д переездах и за 100 м перед ними",
                "Только на дорогах вне населенных пунктов",
                "Только в темное время суток",
                "Только на двухполосных дорогах"
            ),
            correctOptionIndex = 0,
            explanationUzLatin = "YHQ 72-band: Quvib o'tish quyidagi joylarda taqiqlanadi: tartibga solinadigan chorrahalarda; tartibga solinmaydigan chorrahalarda asosiy bo'lmagan yo'lda; piyodalar o'tish joylarida; temir yo'l kesishmalarida va ulargacha 100 metrdan kam masofa qolganda; tepalikning eng baland nuqtasida va xavfli burilishlarda.",
            explanationUzCyrillic = "ЙҲҚ 72-банд: Қувиб ўтиш қуйидаги жойларда тақиқланади: тартибга солинадиган чорраҳаларда; пиёдалар ўтиш жойларида; темир йўл кесишмаларида ва уларгача 100 метрдан кам масофа қолганда.",
            explanationRu = "Пункт 72 ПДД: Обгон запрещен на регулируемых перекрестках, пешеходных переходах, ж/д переездах и за 100 м перед ними.",
            lawReference = "LexUZ: 172-son VM qarori, 72-band",
            difficulty = "MEDIUM",
            diagramType = "OVERTAKING_MANEUVER",
            isA1 = true
        ),

        // Category 9: To'xtash va to'xtab turish
        QuestionEntity(
            id = 8,
            ticketNumber = 1,
            ticketQuestionIndex = 8,
            categoryId = 9,
            textUzLatin = "Piyodalar o'tish joyidan qancha masofada to'xtash taqiqlanadi?",
            textUzCyrillic = "Пиёдалар ўтиш жойидан қанча масофада тўхташ тақиқланади?",
            textRu = "На каком расстоянии от пешеходного перехода запрещена остановка?",
            optionsUzLatin = listOf(
                "Piyodalar o'tish joylarida va ulardan oldin 5 metrdan kam masofada",
                "Faqat piyodalar o'tish joyining o'zida",
                "Kamida 15 metr masofada",
                "Kamida 20 metr masofada"
            ),
            optionsUzCyrillic = listOf(
                "Пиёдалар ўтиш жойларида ва улардан олдин 5 метрдан кам масофада",
                "Фақат пиёдалар ўтиш жойининг ўзида",
                "Камида 15 метр масофада",
                "Камида 20 метр масофада"
            ),
            optionsRu = listOf(
                "На пешеходных переходах и ближе 5 м перед ними",
                "Только непосредственно на самом переходе",
                "Не ближе 15 метров",
                "Не ближе 20 метров"
            ),
            correctOptionIndex = 0,
            explanationUzLatin = "YHQ 89-band: Quyidagi joylarda to'xtash taqiqlanadi: piyodalar o'tish joylarida va ulardan oldin 5 metrdan kam masofada (o'tish joyidan keyin to'xtashga ruxsat etiladi).",
            explanationUzCyrillic = "ЙҲҚ 89-банд: Қуйидаги жойларда тўхташ тақиқланади: пиёдалар ўтиш жойларида ва улардан олдин 5 метрдан кам масофада.",
            explanationRu = "Пункт 89 ПДД: Остановка запрещается на пешеходных переходах и ближе 5 м перед ними.",
            lawReference = "LexUZ: 172-son VM qarori, 89-band",
            difficulty = "MEDIUM",
            diagramType = "PEDESTRIAN_CROSSING",
            isA1 = true
        ),

        // Category 10: Piyodalar o'tish joylari
        QuestionEntity(
            id = 9,
            ticketNumber = 1,
            ticketQuestionIndex = 9,
            categoryId = 10,
            textUzLatin = "Tartibga solinmagan piyodalar o'tish joyiga yaqinlashayotgan haydovchining majburiyati nimadan iborat?",
            textUzCyrillic = "Тартибга солинмаган пиёдалар ўтиш жойига яқинлашаётган ҳайдовчининг мажбурияти нимадан иборат?",
            textRu = "В чем состоит обязанность водителя при приближении к нерегулируемому пешеходному переходу?",
            optionsUzLatin = listOf(
                "Qatnov qismidan o'tayotgan yoki o'tish uchun qadam qo'ygan piyodalarga yo'l berish uchun tezlikni kamaytirishi yoki to'xtashi shart",
                "Faqat tovushli ishora berib tezlikni oshirish",
                "Faqat piyoda yo'lning o'rtasiga kelganida to'xtash",
                "Hech qanday majburiyat yo'q, piyoda kutishi shart"
            ),
            optionsUzCyrillic = listOf(
                "Қатнов қисмидан ўтаётган ёки ўтиш учун қадам қўйган пиёдаларга йўл бериш учун тезликни камайтириши ёки тўхташи шарт",
                "Фақат товушли ишора бериб тезликни ошириш",
                "Фақат пиёда йўлнинг ўртасига келганида тўхташ",
                "Ҳеч қандай мажбурият йўқ, пиёда кутиши шарт"
            ),
            optionsRu = listOf(
                "Снизить скорость или остановиться перед переходом, чтобы уступить дорогу пешеходам, переходящим проезжую часть",
                "Подать звуковой сигнал и увеличить скорость",
                "Остановиться только если пешеход дошел до середины",
                "Обязанностей нет, пешеход обязан пропустить"
            ),
            correctOptionIndex = 0,
            explanationUzLatin = "YHQ 112-band: Tartibga solinmagan piyodalar o'tish joyiga yaqinlashayotgan transport vositasining haydovchisi qatnov qismini (tramvay yo'llarini) kesib o'tayotgan yoki unga qadam qo'ygan piyodalarga yo'l berishi shart.",
            explanationUzCyrillic = "ЙҲҚ 112-банд: Тартибга солинмаган пиёдалар ўтиш жойига яқинлашаётган транспорт воситасининг ҳайдовчиси қатнов қисмини кесиб ўтаётган пиёдаларга йўл бериши шарт.",
            explanationRu = "Пункт 112 ПДД: Водитель ТС, приближающегося к нерегулируемому пешеходному переходу, обязан уступить дорогу пешеходам, переходящим дорогу или вступившим на проезжую часть.",
            lawReference = "LexUZ: 172-son VM qarori, 112-band",
            difficulty = "EASY",
            diagramType = "PEDESTRIAN_CROSSING",
            isA1 = true
        ),

        // Category 11: Temir yo'l kesishmalari
        QuestionEntity(
            id = 10,
            ticketNumber = 1,
            ticketQuestionIndex = 10,
            categoryId = 11,
            textUzLatin = "Shlagbaumsiz temir yo'l kesishmasida poyezd yaqinlashayotganda qayerda to'xtash kerak?",
            textUzCyrillic = "Шлагбаумсиз темир йўл кесишмасида поезд яқинлашаётганда қаерда тўхташ керак?",
            textRu = "Где обязан остановиться водитель при приближении поезда на ж/д переезде без шлагбаума?",
            optionsUzLatin = listOf(
                "2.5 'To'xtamasdan harakatlanish taqiqlangan' belgisi yoki birinchi relsdan kamida 10 metr masofada",
                "Birinchi relsdan 2 metr masofada",
                "Kesishmaga 50 metr yetmasdan",
                "Ixtiyoriy masofada"
            ),
            optionsUzCyrillic = listOf(
                "2.5 'Тўхтамасдан ҳаракатланиш тақиқланган' белгиси ёки биринчи рельсдан камида 10 метр масофада",
                "Биринчи рельсдан 2 метр масофада",
                "Кесишмага 50 метр етмасдан",
                "Ихтиёрий масофада"
            ),
            optionsRu = listOf(
                "Перед знаком 2.5 «Движение без остановки запрещено» или не ближе 10 м до первого рельса",
                "В 2 метрах от первого рельса",
                "За 50 метров до переезда",
                "На произвольном расстоянии"
            ),
            correctOptionIndex = 0,
            explanationUzLatin = "YHQ 118-band: Kesishma oldida harakatlanish taqiqlangan hollarda haydovchi to'xtash chizig'i (svetofor) oldida, agar ular bo'lmasa, birinchi relsdan kamida 10 metr masofada to'xtashi shart.",
            explanationUzCyrillic = "ЙҲҚ 118-банд: Кесишма олдида ҳаракатланиш тақиқланган ҳолларда ҳайдовчи биринчи рельсдан камида 10 метр масофада тўхташи шарт.",
            explanationRu = "Пункт 118 ПДД: При запрещении движения через переезд водитель должен остановиться у знака 2.5 или светофора, а при их отсутствии — не ближе 10 м до первого рельса.",
            lawReference = "LexUZ: 172-son VM qarori, 118-band",
            difficulty = "MEDIUM",
            diagramType = "RAILWAY_CROSSING",
            isA1 = true
        ),

        // Category 12: Avtomagistrallarda harakatlanish
        QuestionEntity(
            id = 11,
            ticketNumber = 1,
            ticketQuestionIndex = 11,
            categoryId = 12,
            textUzLatin = "Avtomagistrallarda qaysi harakatlar qat'iyan taqiqlanadi?",
            textUzCyrillic = "Автомагистралларда қайси ҳаракатлар қатъиян тақиқланади?",
            textRu = "Что категорически запрещается на автомагистралях?",
            optionsUzLatin = listOf(
                "Piyodalar, chorva mollari, velosipedlar harakati, texnik tezligi 40 km/soatdan kam bo'lgan transportlar, orqaga harakatlanish va o'quv mashg'ulotlari",
                "Faqat 100 km/soatdan yuqori tezlikda yurish",
                "Kunduzi chiroqlarni yoqib yurish",
                "O'ng tomondagi tasmada harakatlanish"
            ),
            optionsUzCyrillic = listOf(
                "Пиёдалар, чорва моллари, велосипедлар ҳаракати, техник тезлиги 40 км/соатдан кам бўлган транспортлар, орқага ҳаракатланиш ва ўқув машғулотлари",
                "Фақат 100 км/соатдан юқори тезликда юриш",
                "Кундузи чироқларни ёқиб юриш",
                "Ўнг томондаги тасмада ҳаракатланиш"
            ),
            optionsRu = listOf(
                "Движение пешеходов, гужевых повозок, велосипедов, ТС со скоростью менее 40 км/ч, движение задним ходом и учебная езда",
                "Только движение со скоростью более 100 км/ч",
                "Включение фар в светлое время суток",
                "Движение по правой полосе"
            ),
            correctOptionIndex = 0,
            explanationUzLatin = "YHQ 122-band: Avtomagistrallarda piyodalar, uy hayvonlari, ot-arava, velosipedlar, mopedlar, traktorlar, tezligi 40 km/soatdan kam bo'lgan transportlar, qayrilib olish va orqaga harakatlanish taqiqlanadi.",
            explanationUzCyrillic = "ЙҲҚ 122-банд: Автомагистралларда пиёдалар, велосипедлар, тезлиги 40 км/соатдан кам бўлган транспортлар ва орқага ҳаракатланиш тақиқланади.",
            explanationRu = "Пункт 122 ПДД: На автомагистралях запрещаются движение пешеходов, велосипедов, тихоходных ТС (менее 40 км/ч), разворот и движение задним ходом.",
            lawReference = "LexUZ: 172-son VM qarori, 122-band",
            difficulty = "MEDIUM",
            diagramType = "NONE",
            isA1 = false
        ),

        // Category 14: Manevr qilish va burilish
        QuestionEntity(
            id = 12,
            ticketNumber = 1,
            ticketQuestionIndex = 12,
            categoryId = 14,
            textUzLatin = "Chapga burilish yoki qayrilib olishdan oldin haydovchi qaysi holatni egallashi shart?",
            textUzCyrillic = "Чапга бурилиш ёки қайрилиб олишдан олдин ҳайдовчи қайси ҳолатни эгаллаши шарт?",
            textRu = "Какое крайнее положение обязан занять водитель перед поворотом налево или разворотом?",
            optionsUzLatin = listOf(
                "O'sha yo'nalishdagi qatnov qismining chekka chap holatini",
                "Yo'lning o'rta qismini",
                "Chekka o'ng holatni",
                "Farqi yo'q, asosiy qulay bo'lgan har qanday holatni"
            ),
            optionsUzCyrillic = listOf(
                "Ўша йўналишдаги қатнов қисмининг чекка чап ҳолатини",
                "Йўлнинг ўрта қисмини",
                "Чекка ўнг ҳолатни",
                "Фарқи йўқ, асосий қулай бўлган ҳар қандай ҳолатни"
            ),
            optionsRu = listOf(
                "Крайнее левое положение на проезжей части данного направления",
                "Среднюю часть дороги",
                "Крайнее правое положение",
                "Любое удобное положение"
            ),
            correctOptionIndex = 0,
            explanationUzLatin = "YHQ 57-band: Haydovchi o'ngga, chapga burilish yoki qayrilib olishdan oldin o'sha yo'nalishdagi qatnov qismining tegishli chekka holatini egallashi shart.",
            explanationUzCyrillic = "ЙҲҚ 57-банд: Ҳайдовчи бурилиш ёки қайрилиб олишдан олдин қатнов қисмининг тегишли чекка ҳолатини эгаллаши шарт.",
            explanationRu = "Пункт 57 ПДД: Перед поворотом направо, налево или разворотом водитель обязан заблаговременно занять соответствующее крайнее положение на проезжей части.",
            lawReference = "LexUZ: 172-son VM qarori, 57-band",
            difficulty = "EASY",
            diagramType = "NONE",
            isA1 = true
        ),

        // Category 13: Yo'lda transport vositalarining joylashuvi
        QuestionEntity(
            id = 13,
            ticketNumber = 1,
            ticketQuestionIndex = 13,
            categoryId = 13,
            textUzLatin = "Aholi punktlaridan tashqarida uch yoki undan ortiq tasmali yo'llarda yengil avtomobillar qaysi tasmalarda harakatlanishi kerak?",
            textUzCyrillic = "Аҳоли пунктларидан ташқарида уч ёки ундан ортиқ тасмали йўлларда енгил автомобиллар қайси тасмаларда ҳаракатланиши керак?",
            textRu = "По каким полосам должны двигаться легковые автомобили вне населенных пунктов на дорогах с тремя и более полосами?",
            optionsUzLatin = listOf(
                "Iloji boricha yo'lning o'ng chetiga yaqin bo'lgan tasmalarda (o'ng bo'sh bo'lganda chap tasmalarni egallash taqiqlanadi)",
                "Faqat eng chap tasmada",
                "O'rtadagi tasmada",
                "Ixtiyoriy tasmada"
            ),
            optionsUzCyrillic = listOf(
                "Иложи борича йўлнинг ўнг четига яқин бўлган тасмаларда (ўнг бўш бўлганда чап тасмаларни эгаллаш тақиқланади)",
                "Фақат энг чап тасмада",
                "Ўртадаги тасмада",
                "Ихтиёрий тасмада"
            ),
            optionsRu = listOf(
                "Как можно ближе к правому краю проезжей части (запрещается занимать левые полосы при свободных правых)",
                "Только по крайней левой полосе",
                "По средней полосе",
                "По любой полосе"
            ),
            correctOptionIndex = 0,
            explanationUzLatin = "YHQ 67-band: Aholi punktlaridan tashqarida transport vositalari qatnov qismining iloji boricha o'ng chetiga yaqinroq harakatlanishi shart. O'ng tasmalar bo'sh bo'lganda chap tasmalarni egallash taqiqlanadi.",
            explanationUzCyrillic = "ЙҲҚ 67-банд: Аҳоли пунктларидан ташқарида транспорт воситалари қатнов қисмининг иложи борича ўнг четига яқинроқ ҳаракатланиши шарт.",
            explanationRu = "Пункт 67 ПДД: Вне населенных пунктов водители должны вести ТС по возможности ближе к правому краю проезжей части. Запрещается занимать левые полосы при свободных правых.",
            lawReference = "LexUZ: 172-son VM qarori, 67-band",
            difficulty = "MEDIUM",
            diagramType = "NONE",
            isA1 = true
        ),

        // Category 17: Transport vositalarining texnik holati
        QuestionEntity(
            id = 14,
            ticketNumber = 1,
            ticketQuestionIndex = 14,
            categoryId = 17,
            textUzLatin = "Yengil avtomobillar shinalarining qoldiq protektor naqsh balandligi kamida qancha bo'lishi talab qilinadi?",
            textUzCyrillic = "Енгил автомобиллар шиналарининг қолдиқ протектор нақш баландлиги камида қанча бўлиши талаб қилинади?",
            textRu = "Какова минимально допустимая остаточная высота рисунка протектора шин для легковых автомобилей?",
            optionsUzLatin = listOf(
                "1,6 mm",
                "1,0 mm",
                "2,0 mm",
                "0,8 mm"
            ),
            optionsUzCyrillic = listOf(
                "1,6 мм",
                "1,0 мм",
                "2,0 мм",
                "0,8 мм"
            ),
            optionsRu = listOf(
                "1,6 мм",
                "1,0 мм",
                "2,0 мм",
                "0,8 мм"
            ),
            correctOptionIndex = 0,
            explanationUzLatin = "3-ilova (Transport vositalaridan foydalanishni taqiqlovchi shartlar): Yengil avtomobillar shinalarining qoldiq protektor balandligi 1,6 millimetrdan kam bo'lmasligi shart (mototsikllar uchun 0,8 mm, yuk avtomobillari uchun 1,0 mm, avtobuslar uchun 2,0 mm).",
            explanationUzCyrillic = "3-илова: Енгил автомобиллар шиналарининг қолдиқ протектор баландлиги 1,6 мм дан кам бўлмаслиги шарт.",
            explanationRu = "Приложение 3 к ПДД: Остаточная глубина рисунка протектора шин для легковых автомобилей должна составлять не менее 1,6 мм.",
            lawReference = "LexUZ: 172-son VM qarori, 3-ilova, 5.1-band",
            difficulty = "EASY",
            diagramType = "NONE",
            isA1 = true
        ),

        // Category 19: Birinchi tibbiy yordam
        QuestionEntity(
            id = 15,
            ticketNumber = 1,
            ticketQuestionIndex = 15,
            categoryId = 19,
            textUzLatin = "Arterial qon ketishida qon to'xtatuvchi bog'lam (jgut) yoz oylarida tanaga maksimal qancha vaqtga qo'yiladi?",
            textUzCyrillic = "Артериал қон кетишида қон тўхтатувчи боғлам (жгут) ёз ойларида танага максимал қанча вақтга қўйилади?",
            textRu = "На какое максимальное время накладывается кровоостанавливающий жгут при артериальном кровотечении в теплое время года?",
            optionsUzLatin = listOf(
                "Ko'pi bilan 1 soat (qishda 30 daqiqa)",
                "2 soat",
                "3 soat",
                "Cheklovsiz, shifoxonaga yetib borguncha"
            ),
            optionsUzCyrillic = listOf(
                "Кўпи билан 1 соат (қишда 30 дақиқа)",
                "2 соат",
                "3 соат",
                "Чекловсиз, шифохонага етиб боргунча"
            ),
            optionsRu = listOf(
                "Не более 1 часа (зимой не более 30 минут)",
                "Не более 2 часов",
                "Не более 3 часов",
                "Без ограничения до прибытия в больницу"
            ),
            correctOptionIndex = 0,
            explanationUzLatin = "Birinchi tibbiy yordam ko'rsatish qoidalariga ko'ra: To'qimalarning o'lishi (nekroz)ning oldini olish maqsadida qon to'xtatish jguti issiq mavsumda ko'pi bilan 1 soat, sovuq mavsumda esa 30 daqiqadan oshmagan vaqtga qo'yiladi va vaqti yozilgan qog'oz qistirib qo'yiladi.",
            explanationUzCyrillic = "Биринчи ёрдам қоидаларига кўра: Қон тўхтатиш жгути иссиқ мавсумда кўпи билан 1 соат, совуқ мавсумда эса 30 дақиқагача қўйилади.",
            explanationRu = "Согласно правилам оказания первой помощи: жгут накладывается летом максимум на 1 час, зимой — не более чем на 30 минут, с обязательным указанием времени наложения в записке.",
            lawReference = "Birinchi tibbiy yordam ko'rsatish standartlari",
            difficulty = "MEDIUM",
            diagramType = "NONE",
            isA1 = true
        ),

        // Category 20: Haydovchilarning umumiy majburiyatlari
        QuestionEntity(
            id = 16,
            ticketNumber = 1,
            ticketQuestionIndex = 16,
            categoryId = 20,
            textUzLatin = "Transport vositasini boshqarish paytida telefondan (qo'llardan foydalanmasdan gaplashish imkonini beruvchi quloqchinsiz) foydalanish qanday javobgarlikka sabab bo'ladi?",
            textUzCyrillic = "Транспорт воситасини бошқариш пайтида телефондан (қулоқчинсиз) фойдаланиш қандай жавобгарликка сабаб бўлади?",
            textRu = "Какая ответственность предусмотрена за пользование телефоном во время управления ТС (без гарнитуры hands-free)?",
            optionsUzLatin = listOf(
                "Qat'iyan taqiqlanadi va ma'muriy jarimaga sabab bo'ladi",
                "Faqat tirbandlik paytida ruxsat beriladi",
                "Faqat 30 km/soatdan past tezlikda ruxsat beriladi",
                "Faqat ovozli xabar jo'natish mumkin"
            ),
            optionsUzCyrillic = listOf(
                "Қатъиян тақиқланади ва маъмурий жаримага сабаб бўлади",
                "Фақат тирбандлик пайтида рухсат берилади",
                "Фақат 30 км/соатдан паст тезликда рухсат берилади",
                "Фақат овозли хабар жўнатиш мумкин"
            ),
            optionsRu = listOf(
                "Категорически запрещено и влечет наложение административного штрафа",
                "Разрешено только в пробках",
                "Разрешено на скорости менее 30 км/ч",
                "Разрешено отправлять голосовые сообщения"
            ),
            correctOptionIndex = 0,
            explanationUzLatin = "YHQ 12-band: Haydovchiga transport vositasini boshqarish vaqtida telefondan (qo'llarni ishlatmasdan foydalanish imkonini beruvchi quloqchinlar va moslamalardan tashqari) foydalanish taqiqlanadi. MJtK 128-1-moddasi bo'yicha jarima qo'llaniladi.",
            explanationUzCyrillic = "ЙҲҚ 12-банд: Ҳайдовчига бошқариш пайтида телефондан қулоқчинсиз фойдаланиш тақиқланади.",
            explanationRu = "Пункт 12 ПДД: Водителю запрещается во время движения пользоваться телефоном без специальной гарнитуры hands-free.",
            lawReference = "LexUZ: 172-son VM qarori, 12-band, MJtK 128-1-modda",
            difficulty = "EASY",
            diagramType = "NONE",
            isA1 = true
        ),

        // Category 18: Favqulodda vaziyatlar va xavfsizlik
        QuestionEntity(
            id = 17,
            ticketNumber = 1,
            ticketQuestionIndex = 17,
            categoryId = 18,
            textUzLatin = "Avariya holatida to'xtash belgisi aholi punktlarida transport vositasidan kamida qancha masofada o'rnatilishi shart?",
            textUzCyrillic = "Авария ҳолатида тўхташ белгиси аҳоли пунктларида транспорт воситасидан камида қанча масофада ўрнатилиши шарт?",
            textRu = "На каком расстоянии от транспортного средства должен быть выставлен знак аварийной остановки в населенных пунктах?",
            optionsUzLatin = listOf(
                "Kamida 15 metr (aholi punktlaridan tashqarida kamida 30 metr)",
                "Kamida 5 metr",
                "Kamida 10 metr",
                "Kamida 50 metr"
            ),
            optionsUzCyrillic = listOf(
                "Камида 15 метр (аҳоли пунктларидан ташқарида камида 30 метр)",
                "Камида 5 метр",
                "Камида 10 метр",
                "Камида 50 метр"
            ),
            optionsRu = listOf(
                "Не менее 15 метров (вне населенных пунктов не менее 30 метров)",
                "Не менее 5 метров",
                "Не менее 10 метров",
                "Не менее 50 метров"
            ),
            correctOptionIndex = 0,
            explanationUzLatin = "YHQ 51-band: Avariya sababli to'xtash belgisi transport vositasidan aholi punktlarida kamida 15 metr, aholi punktlaridan tashqarida esa kamida 30 metr masofada o'rnatiladi.",
            explanationUzCyrillic = "ЙҲҚ 51-банд: Авария белгиси аҳоли пунктларида камида 15 метр, аҳоли пунктларидан ташқарида камида 30 метр масофада ўрнатилади.",
            explanationRu = "Пункт 51 ПДД: Знак аварийной остановки устанавливается на расстоянии не менее 15 м в населенных пунктах и не менее 30 м вне населенных пунктов.",
            lawReference = "LexUZ: 172-son VM qarori, 51-band",
            difficulty = "EASY",
            diagramType = "PARKING_STOPPING",
            isA1 = true
        ),

        // Category 3: Yo'l chiziqlari (1.1 yaxlit chiziq)
        QuestionEntity(
            id = 18,
            ticketNumber = 1,
            ticketQuestionIndex = 18,
            categoryId = 3,
            textUzLatin = "1.1 yotiq chiziq (yaxlit oq chiziq) qanday talabni bildiradi?",
            textUzCyrillic = "1.1 ётиқ чизиқ (яхлит оқ чизиқ) қандай талабни билдиради?",
            textRu = "Какие требования предъявляет сплошная горизонтальная разметка 1.1?",
            optionsUzLatin = listOf(
                "Qarama-qarshi oqimlarni ajratadi, uni bosib o'tish qat'iyan taqiqlanadi",
                "Faqat quvib o'tishga ruxsat beradi",
                "Faqat to'xtash joyini bildiradi",
                "Ixtiyoriy paytda kesib o'tish mumkin"
            ),
            optionsUzCyrillic = listOf(
                "Қарама-қарши оқимларни ажратади, уни босиб ўтиш қатъиян тақиқланади",
                "Фақат қувиб ўтишга рухсат беради",
                "Фақат тўхташ жойини билдиради",
                "Ихтиёрий пайтда кесиб ўтиш мумкин"
            ),
            optionsRu = listOf(
                "Разделяет транспортные потоки противоположных направлений, пересекать ее запрещено",
                "Разрешает только обгон",
                "Указывает только место стоянки",
                "Можно пересекать в любое время"
            ),
            correctOptionIndex = 0,
            explanationUzLatin = "2-ilova (Yo'l chiziqlari): 1.1 chizig'ini bosib o'tish taqiqlanadi (faqat qatnov qismi chetida to'xtashga ruxsat etilgan joylar bundan mustasno).",
            explanationUzCyrillic = "2-илова: 1.1 чизиғини босиб ўтиш тақиқланади.",
            explanationRu = "Приложение 2 (Дорожная разметка): Линию 1.1 пересекать запрещается.",
            lawReference = "LexUZ: 172-son VM qarori, 2-ilova, 1.1-band",
            difficulty = "EASY",
            diagramType = "NONE",
            isA1 = true
        ),

        // Category 15: Odam tashish
        QuestionEntity(
            id = 19,
            ticketNumber = 1,
            ticketQuestionIndex = 19,
            categoryId = 15,
            textUzLatin = "Bolalarni yengil avtomobilning oldingi o'rindig'ida qachondan boshlab maxsus bolalar o'rindig'isiz olib yurishga ruxsat beriladi?",
            textUzCyrillic = "Болаларни енгил автомобилнинг олдинги ўриндиғида қачондан бошлаб махсус болалар ўриндиғисиз олиб юришга рухсат берилади?",
            textRu = "С какого возраста разрешается перевозка детей на переднем сиденье легкового автомобиля без детского удерживающего устройства?",
            optionsUzLatin = listOf(
                "12 yoshga to'lgandan keyin (xavfsizlik kamari taqilgan holda)",
                "7 yoshdan boshlab",
                "10 yoshdan boshlab",
                "14 yoshdan boshlab"
            ),
            optionsUzCyrillic = listOf(
                "12 ёшга тўлгандан кейин (хавфсизлик камари тақилган ҳолда)",
                "7 ёшдан бошлаб",
                "10 ёшдан бошлаб",
                "14 ёшдан бошлаб"
            ),
            optionsRu = listOf(
                "С 12 лет (с обязательным пристегиванием ремнем безопасности)",
                "С 7 лет",
                "С 10 лет",
                "С 14 лет"
            ),
            correctOptionIndex = 0,
            explanationUzLatin = "YHQ 159-band: Bolalarni 12 yoshga to'lguniga qadar yengil avtomobilning old o'rindig'ida faqat bolalarni ushlab turuvchi maxsus moslama (avtokreslo) orqali tashishga ruxsat etiladi.",
            explanationUzCyrillic = "ЙҲҚ 159-банд: 12 ёшга тўлмаган болаларни олдинги ўриндиқда фақат болалар автокреслоси билан ташиш мумкин.",
            explanationRu = "Пункт 159 ПДД: Перевозка детей до 12 лет на переднем сиденье легкового автомобиля разрешается только с использованием специального детского удерживающего устройства.",
            lawReference = "LexUZ: 172-son VM qarori, 159-band",
            difficulty = "MEDIUM",
            diagramType = "NONE",
            isA1 = false
        ),

        // Category 16: Yuk tashish
        QuestionEntity(
            id = 20,
            ticketNumber = 1,
            ticketQuestionIndex = 20,
            categoryId = 16,
            textUzLatin = "Yuk transport vositasining orqa yoki oldi gabaritidan qancha masofaga chiqib tursa, 'Katta o'lchamli yuk' belgisi bilan belgilanishi shart?",
            textUzCyrillic = "Юк транспорт воситасининг орқа ёки олди габаритидан қанча масофага чиқиб турса, 'Катта ўлчамли юк' белгиси билан белгиланиши шарт?",
            textRu = "На какое расстояние груз должен выступать за габариты ТС спереди или сзади, чтобы требовалось обозначение знаком «Крупногабаритный груз»?",
            optionsUzLatin = listOf(
                "1 metrdan ortiq masofaga chiqqanda",
                "0,5 metrdan ortiq",
                "2 metrdan ortiq",
                "1,5 metrdan ortiq"
            ),
            optionsUzCyrillic = listOf(
                "1 метрдан ортиқ масофага чиққанда",
                "0,5 метрдан ортиқ",
                "2 метрдан ортиқ",
                "1,5 метрдан ортиқ"
            ),
            optionsRu = listOf(
                "Более чем на 1 метр",
                "Более чем на 0,5 метра",
                "Более чем на 2 метра",
                "Более чем на 1,5 метра"
            ),
            correctOptionIndex = 0,
            explanationUzLatin = "YHQ 167-band: Transport vositasining gabaritlaridan oldi yoki orqa tomonga 1 metrdan, yon tomonlarga esa 0,4 metrdan ortiq chiqib turgan yuklar 'Katta o'lchamli yuk' taniqlik belgisi bilan belgilanishi shart.",
            explanationUzCyrillic = "ЙҲҚ 167-банд: Олди ёки орқага 1 метрдан, ён томонга 0,4 метрдан ортиқ чиққан юк белгиланиши шарт.",
            explanationRu = "Пункт 167 ПДД: Груз, выступающий за габариты ТС спереди или сзади более чем на 1 м, а сбоку более чем на 0,4 м, должен быть обозначен опознавательным знаком «Крупногабаритный груз».",
            lawReference = "LexUZ: 172-son VM qarori, 167-band",
            difficulty = "HARD",
            diagramType = "NONE",
            isA1 = false
        ),

        // Ticket 2 Questions
        QuestionEntity(
            id = 21,
            ticketNumber = 2,
            ticketQuestionIndex = 1,
            categoryId = 6,
            textUzLatin = "Aylanma harakat chorrahasiga (4.3 belgisi o'rnatilgan) kirib kelayotgan haydovchi aylanma bo'ylab harakatlanayotgan transport vositasiga yo'l berishi shartmi?",
            textUzCyrillic = "Айланма ҳаракат чорраҳасига (4.3 белгиси ўрнатилган) кириб келаётган ҳайдовчи айланма бўйлаб ҳаракатланаётган транспорт воситасига йўл бериши шартми?",
            textRu = "Обязан ли водитель, въезжающий на перекресток с круговым движением (знак 4.3), уступить дорогу ТС, движущемуся по кругу?",
            optionsUzLatin = listOf(
                "Ha, aylanma bo'ylab harakatlanayotgan transport vositasi ustunlikka ega",
                "Yo'q, aylanaga kirib kelayotgan o'ng tomondagi to'siq sifatida ustunlikka ega",
                "Faqat svetofor bo'lsa yo'l beradi",
                "Faqat yuk mashinalari yo'l beradi"
            ),
            optionsUzCyrillic = listOf(
                "Ҳа, айланма бўйлаб ҳаракатланаётган транспорт воситаси устунликка эга",
                "Йўқ, айланага кириб келаётган ўнг томондаги тўсиқ сифатида устунликка эга",
                "Фақат светофор бўлса йўл беради",
                "Фақат юк машиналари йўл беради"
            ),
            optionsRu = listOf(
                "Да, ТС, движущееся по кругу, имеет преимущество",
                "Нет, въезжающий имеет преимущество по помехе справа",
                "Только при наличии светофора",
                "Только грузовые автомобили"
            ),
            correctOptionIndex = 0,
            explanationUzLatin = "YHQ 103-band (so'nggi o'zgartirishlar): 4.3 'Aylanma harakat' belgisi o'rnatilgan chorrahaga kirishda haydovchi aylanma harakat bo'yicha harakatlanayotgan transport vositalariga yo'l berishi shart.",
            explanationUzCyrillic = "ЙҲҚ 103-банд: 4.3 'Айланма ҳаракат' чорраҳасига киришда айлана бўйлаб ҳаракатланаётганларга йўл берилади.",
            explanationRu = "Пункт 103 ПДД: При въезде на перекресток со знаком 4.3 «Круговое движение» водитель обязан уступить дорогу ТС, движущимся по такому перекрестку.",
            lawReference = "LexUZ: 172-son VM qarori, 103-band",
            difficulty = "MEDIUM",
            diagramType = "ROUNDABOUT",
            isA1 = true
        ),

        QuestionEntity(
            id = 22,
            ticketNumber = 2,
            ticketQuestionIndex = 2,
            categoryId = 2,
            textUzLatin = "3.27 'To'xtash taqiqlangan' va 3.28 'To'xtab turish taqiqlangan' belgilari o'rtasidagi asosiy farq nimada?",
            textUzCyrillic = "3.27 'Тўхташ тақиқланган' ва 3.28 'Тўхтаб туриш тақиқланган' белгилари ўртасидаги асосий фарқ нимада?",
            textRu = "В чем главное отличие знака 3.27 «Остановка запрещена» от знака 3.28 «Стоянка запрещена»?",
            optionsUzLatin = listOf(
                "3.27 har qanday qisqa muddatli to'xtashni ham taqiqlaydi, 3.28 esa odam chiqarish-tushirish yoki yuk ortish-tushirish uchun 5 daqiqadan ko'p bo'lmagan to'xtashga ruxsat beradi",
                "Ikkalasi ham bir xil, hech qanday farq yo'q",
                "3.27 faqat tunda amal qiladi",
                "3.28 faqat avtobuslarga tegishli"
            ),
            optionsUzCyrillic = listOf(
                "3.27 ҳар қандай қисқа муддатли тўхташни ҳам тақиқлайди, 3.28 эса одам тушириш ёки юк ортиш учун тўхташга рухсат беради",
                "Иккаласи ҳам бир хил, фарқ йўқ",
                "3.27 фақат тунда амал қилади",
                "3.28 фақат автобусларга тегишли"
            ),
            optionsRu = listOf(
                "3.27 запрещает даже кратковременную остановку, а 3.28 разрешает остановку до 5 минут для посадки/высадки или погрузки/разгрузки",
                "Они полностью одинаковы",
                "3.27 действует только ночью",
                "3.28 действует только для автобусов"
            ),
            correctOptionIndex = 0,
            explanationUzLatin = "YHQ 1-ilova: 3.27 belgisi transport vositalarining to'xtashini va to'xtab turishini taqiqlaydi. 3.28 belgisi esa faqat to'xtab turishni (5 daqiqadan ortiq muddatga harakatsiz qolishni) taqiqlaydi, lekin odam tushirish yoki yuk ortish uchun to'xtashga ruxsat etadi.",
            explanationUzCyrillic = "3.27 белгиси ҳар қандай тўхташни тақиқлайди, 3.28 эса тўхтаб туришни тақиқлайди.",
            explanationRu = "Знак 3.27 запрещает как остановку, так и стоянку. Знак 3.28 запрещает только стоянку, разрешая остановку до 5 минут для посадки/высадки пассажиров либо загрузки/разгрузки груза.",
            lawReference = "LexUZ: 172-son VM qarori, 1-ilova, 3.27 va 3.28 bandlar",
            difficulty = "MEDIUM",
            diagramType = "PARKING_STOPPING",
            isA1 = true
        ),

        // Category A1 specific question
        QuestionEntity(
            id = 23,
            ticketNumber = 2,
            ticketQuestionIndex = 3,
            categoryId = 1,
            textUzLatin = "Mototsikl va moped haydovchilari hamda ularning yo'lovchilari harakatlanish vaqtida qaysi xavfsizlik vositasidan foydalanishlari shart?",
            textUzCyrillic = "Мотоцикл ва мопед ҳайдовчилари ҳамда уларнинг йўловчилари ҳаракатланиш вақтида қайси хавфсизлик воситасидан фойдаланишлари шарт?",
            textRu = "Какое средство индивидуальной защиты обязаны использовать водители мотоциклов и мопедов, а также их пассажиры?",
            optionsUzLatin = listOf(
                "Tugmalangan motoshlem (maxsus bosh kiyimi)",
                "Faqat nur qaytaruvchi jilet",
                "Faqat himoya qo'lqoplari",
                "Majburiy vosita mavjud emas"
            ),
            optionsUzCyrillic = listOf(
                "Тугмаланган мотошлем (махсус бош кийими)",
                "Фақат нур қайтарувчи жилет",
                "Фақат ҳимоя қўлқоплари",
                "Мажбурий восита мавжуд эмас"
            ),
            optionsRu = listOf(
                "Застегнутый мотошлем",
                "Только светоотражающий жилет",
                "Только защитные перчатки",
                "Обязательных средств нет"
            ),
            correctOptionIndex = 0,
            explanationUzLatin = "YHQ 10-band: Mototsikl va moped boshqarayotganda haydovchi hamda yo'lovchilar maxsus motoshlemni kiyishi va uni tugmali bog'lab olishi shart.",
            explanationUzCyrillic = "ЙҲҚ 10-банд: Мотоцикл ҳайдовчиси ва йўловчилари мотошлем кийиши шарт.",
            explanationRu = "Пункт 10 ПДД: Водитель мотоцикла или мопеда, а также его пассажиры обязаны быть в застегнутом мотошлеме во время движения.",
            lawReference = "LexUZ: 172-son VM qarori, 10-band",
            difficulty = "EASY",
            diagramType = "NONE",
            isA1 = true
        ),

        QuestionEntity(
            id = 24,
            ticketNumber = 2,
            ticketQuestionIndex = 4,
            categoryId = 18,
            textUzLatin = "Muzlama yoki sirpanchiq yo'lda avtomobil sirpanib ketganda (zanos bo'lganda) rul chambaragini qaysi tomonga burish kerak?",
            textUzCyrillic = "Музлама ёки сирпанчиқ йўлда автомобиль сирпаниб кетганда (занос бўлганда) руль чамбарагини қайси томонга буриш керак?",
            textRu = "В какую сторону необходимо поворачивать рулевое колесо при заносе задней оси автомобиля на скользкой дороге?",
            optionsUzLatin = listOf(
                "Orqa g'ildiraklar sirpanayotgan (zanos bo'layotgan) tomonga silliq burish va keskin tormoz bosmaslik",
                "Sirpanishga qarama-qarshi tomonga burish va zudlik bilan tormozni polgacha bosish",
                "Rulni qo'yib yuborish",
                "Faqat qo'l tormozini tortish"
            ),
            optionsUzCyrillic = listOf(
                "Орқа ғилдираклар сирпанаётган (занос бўлаётган) томонга силлиқ буриш ва кескин тормоз босмаслик",
                "Сирпанишга қарама-қарши томонга буриш ва тормозни босиш",
                "Рульни қўйиб юбориш",
                "Фақат қўл тормозини тортиш"
            ),
            optionsRu = listOf(
                "Плавно в сторону заноса, не нажимая резко на педаль тормоза",
                "В противоположную сторону и резко затормозить",
                "Отпустить руль",
                "Затянуть стояночный тормоз"
            ),
            correctOptionIndex = 0,
            explanationUzLatin = "Xavfsiz harakatlanish asoslari: Orqa g'ildiraklar zanos bo'lganda boshqaruvni qaytarish uchun rulni silliq ravishda zanos tomoniga burish, gaz tepkisini ozroq bo'shatish va hech qachon tormozni keskin bosmaslik talab etiladi.",
            explanationUzCyrillic = "Хавфсизлик қоидаси: Занос юз берганда руль силлиқ занос томонга бурилади ва кескин тормоз босилмайди.",
            explanationRu = "Основы безопасности движения: при заносе руль необходимо повернуть плавно в сторону заноса и избегать резкого торможения, провоцирующего неуправляемое вращение.",
            lawReference = "Xavfsiz harakatlanish qoidalari va tavsiyalari",
            difficulty = "HARD",
            diagramType = "NONE",
            isA1 = true
        )
    )
}
