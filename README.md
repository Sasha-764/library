# Онлайн-библиотека (backend)

Учебный backend-проект: каталог книг, бронирование, выдача и возврат, роли пользователей.

## Запуск
1. Установить JDK 17.
2. Клонировать репозиторий:
   ```
   git clone https://github.com/Sasha-764/library.git
   cd library
   ```
3. Запустить:
   ```
   ./mvnw spring-boot:run      (Windows: mvnw.cmd spring-boot:run)
   ```
4. Сервер доступен на `http://localhost:8080`.

## База данных
Используется H2 в файле `./data/librarydb`. Настройка не нужна: файл и таблицы создаются автоматически при первом запуске (`spring.jpa.hibernate.ddl-auto=update`). Таблицы: `users`, `books`, `loans`.

При первом запуске создаётся администратор:
- почта: `admin@library.com`
- пароль: `admin123`

## Роли
- **READER** — смотрит каталог, бронирует книги, видит свои выдачи.
- **LIBRARIAN** — выдаёт и принимает книги, видит все выдачи.
- **ADMIN** — всё то же, плюс управление книгами и пользователями.

## Авторизация
`POST /api/auth/login` возвращает `accessToken` (JWT, действует 24 часа). Дальше его передают в заголовке:
```
Authorization: Bearer <accessToken>
```
