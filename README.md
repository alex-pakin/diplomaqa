# Дипломный проект по профессии "Тестировщик ПО"
**Дипломный проект** —— автоматизация тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

## Порядок запуска дипломного проекта
1. Запустить программу Docker Desktop
2. Склонировать репозиторий с помощью команды **git clone**, используя Git
3. Открыть склонированный проект в IntelliJ IDEA
4. Запустить контейнеры, выполнив в терминале IntelliJ IDEA команду **docker-compose up**
5. Запустить приложение, выполнив в терминале Intellij IDEA команду:
- Для БД MySQL: **java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar ./artifacts/aqa-shop.jar**
- Для БД PostgreSQL: **java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar ./artifacts/aqa-shop.jar**
6. Запустить автотесты, выполнив в терминале Intellij IDEA команду:
- Для БД MySQL: **./gradlew clean test -D dbUrl=jdbc:mysql://localhost:3306/app**
- Для БД PostgreSQL: **./gradlew clean test -D dbUrl=jdbc:postgresql://localhost:5432/app**
7. Для получения отчета в терминале IntelliJ IDEA выполнить команду **./gradlew allureServe**

## Ссылки на документацию
1. [Дипломное задание](https://github.com/netology-code/qa-diploma)
2. [План автоматизации](https://github.com/alex-pakin/diplomaqa/blob/main/docs/Plan.md)
3. [Отчет по итогам тестирования](https://github.com/alex-pakin/diplomaqa/blob/main/docs/Report.md.)
4. [Отчет по итогам автоматизации](https://github.com/alex-pakin/diplomaqa/blob/main/docs/Summary.md)