# Telegram-Stenograffia-bot 

## Бот посвещённый фестивалю Стенограффия

Версия 2.0

**Авторы:**
* Митрофанов Дмитрий [obduvan](https://github.com/obduvan)
* Скалкина Полина [bypolinafox](https://github.com/bypolinafox)

## Описание
На данном этапе разработки бот должен:
* выдовать список работ, с прикрепленной фотографией и геолокацией,
* показывать работы в указанном радиусе,
* составлять маршрут по выбранным работам из выбранного радиуса.

## Команды
#### Базовые функции:
* `/start`, `/help` - Показать справку по командам
* `/authors`- Показать авторов

#### Основные функции:
* `/works` - Показать все работы фестиваля
* `/worksl` - Показать работы в указанном радиусе
* `/route ` - Cоставить маршрут по выбранным работам из выбранного радиуса


## Задачи проекта
- [x] - Базовые функции
  - [x] - Хранение состояний у каждого пользователя
  - [x] - Команды `/authors`, `/start`, `/help`
- [x] - Основные функции
  - [x] - Способность показывать список работ с фотографией и локацией
    - [x] - Создана база данных с работами фестиваля "Стенограффия"
  - [x] - Способность показать работы, которые находятся рядом с пользователем 
    - [x] - Алгоритм расчета расстояния на поверхности шара
    - [x] - Функция, создающая ссылку на работу на яндекс картах
- [x] - Cоставлять маршрут по выбранным работам
  - [x] - Подключение к google API
  - [x] - Составление маршрута с помощью алгоритма от google
  - [x] - Добавление кнопок к работам
