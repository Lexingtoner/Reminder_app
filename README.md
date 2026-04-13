Reminder App
Android приложение для создания и управления напоминаниями с использованием современных технологий Jetpack Compose и SQLite.
📱 Описание
Приложение позволяет пользователям создавать напоминания с текстом, датой и временем. Все данные хранятся локально в SQLite базе данных. Приложение использует MVVM архитектуру и современный UI фреймворк Jetpack Compose.
✨ Функциональность
•
Добавление напоминаний: Создание новых напоминаний с помощью удобной формы
•
Просмотр списка: Отображение всех сохраненных напоминаний в виде списка
•
Удаление напоминаний: Возможность удалить ненужные напоминания
•
Дата и время: Выбор даты и времени с помощью встроенных Android диалогов
•
Локальное хранение: Все данные сохраняются в локальной SQLite базе данных
🛠 Технологии
•
Язык: Kotlin
•
UI Framework: Jetpack Compose с Material3
•
Архитектура: MVVM (Model-View-ViewModel)
•
База данных: SQLite с DatabaseHelper
•
Дата/Время: ThreeTenABP (backport java.time для Android)
•
Навигация: Navigation Compose
•
Зависимости: Управление через Gradle Version Catalog
🚀 Установка и запуск
Предварительные требования
•
Android Studio Arctic Fox или новее
•
JDK 11 или выше
•
Android SDK API 27+
Шаги установки
1.
Клонировать репозиторий
git clone https://github.com/YOUR_USERNAME/Reminder_app.git
cd Reminder_app
2.
Открыть в Android Studio
◦
Запустить Android Studio
◦
Выбрать "Open" и указать папку проекта
3.
Синхронизировать проект
◦
Android Studio автоматически синхронизирует Gradle файлы
◦
Если есть проблемы, нажать "Sync Project with Gradle Files"
4.
Запустить приложение
◦
Подключить Android устройство или запустить эмулятор
◦
Нажать "Run" (зеленая кнопка воспроизведения) в Android Studio
📁 Структура проекта
Reminder_app/
├── app/
│   ├── build.gradle.kts          # Конфигурация модуля приложения
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml    # Манифест приложения
│       │   ├── java/com/example/reminder_app/
│       │   │   ├── MainActivity.kt    # Главная активность с навигацией
│       │   │   ├── DatabaseHelper.kt  # Работа с SQLite базой данных
│       │   │   ├── RemindersViewModel.kt # ViewModel для управления состоянием
│       │   │   ├── Form.kt            # Экран добавления напоминания
│       │   │   ├── List.kt            # Экран списка напоминаний
│       │   │   ├── ReminderItem.kt    # Компонент для отображения напоминания
│       │   │   ├── Utils.kt           # Вспомогательные функции
│       │   │   ├── ReminderApplication.kt # Инициализация приложения
│       │   │   └── ui/theme/          # Темы и стили
│       │   └── res/                   # Ресурсы (строки, цвета, иконки)
│       └── androidTest/               # Инструментальные тесты
├── gradle/
│   ├── libs.versions.toml         # Управление версиями зависимостей
│   └── wrapper/                   # Gradle wrapper
├── build.gradle.kts               # Корневая конфигурация Gradle
├── settings.gradle.kts            # Настройки проекта
├── AGENTS.md                      # Руководство для AI агентов
└── README.md                      # Этот файл
🏗 Архитектура
Приложение следует принципам MVVM (Model-View-ViewModel):
•
Model: DatabaseHelper - управление данными в SQLite
•
View: Compose компоненты (Form, List, ReminderItem)
•
ViewModel: RemindersViewModel - бизнес-логика и управление состоянием
Поток данных
1.
Пользователь взаимодействует с UI (View)
2.
View вызывает методы ViewModel
3.
ViewModel обновляет данные через DatabaseHelper
4.
ViewModel уведомляет View об изменениях состояния
🔧 Сборка
Debug сборка
./gradlew assembleDebug
Release сборка
./gradlew assembleRelease
Очистка
./gradlew clean
📋 Требования к устройству
•
Минимальная версия Android: API 27 (Android 8.1)
•
Целевая версия: API 36 (Android 16)
•
Разрешения: Нет специальных разрешений (данные хранятся локально)
🔮 Будущие улучшения
•
[ ] Уведомления в указанное время
•
[ ] Повторяющиеся напоминания
•
[ ] Категории напоминаний
•
[ ] Темная тема
•
[ ] Виджеты на домашний экран
•
[ ] Синхронизация с облаком
📄 Лицензия
Этот проект является открытым исходным кодом. Вы можете свободно использовать, модифицировать и распространять его в соответствии с условиями лицензии MIT.
👥 Автор
Crain - GitHub
🤝 Вклад в проект
Вклады приветствуются! Пожалуйста, создавайте issues для багов и feature requests, а также pull requests для улучшений.
1.
Fork проект
2.
Создайте feature branch (git checkout -b feature/AmazingFeature)
3.
Commit изменения (git commit -m 'Add some AmazingFeature')
4.
Push в branch (git push origin feature/AmazingFeature)
5.
Создайте Pull Request</content> <parameter name="filePath">C:\Users\Crain\Desktop\Personal Files\Pet-projects\AndroidStudio\Reminder_app\README.md
