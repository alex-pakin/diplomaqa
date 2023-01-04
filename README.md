# Дипломный проект по профессии "Тестировщик ПО"
**Дипломный проект** —— автоматизация тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

## Порядок запуска дипломного проекта
1. Запустить программу Docker Desktop
2. Склонировать репозиторий с помощью команды **git clone**, используя Git
3. Открыть склонированный проект в IntelliJ IDEA
4. Запустить контейнеры, выполнив в терминале IntelliJ IDEA команду **docker-copmose up**
5. Запустить приложение, выполнив в терминале Intellij IDEA команду:
- Для БД MySQL: **java -jar ./aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:3306/app**
- Для БД PostgreSQL: **java -jar ./aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/app**
6. Запустить автотесты, выполнив в терминале Intellij IDEA команду:
- Для БД MySQL: **./gradlew clean test -D dbUrl=jdbc:mysql://localhost:3306/app**
- Для БД PostgreSQL: **./gradlew clean test -D dbUrl=jdbc:postgresql://localhost:5432/app**
7. Для получения отчета в терминале IntelliJ IDEA выполнить команду **./gradlew allureServe**

## Ссылки на документацию
1. [Дипломное задание](https://github.com/netology-code/qa-diploma)
2. [План Автоматизации](https://github.com/alex-pakin/diplomaqa/blob/main/docs/Plan.md)