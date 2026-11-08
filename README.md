# Онлайн-библиотека (backend)

Учебный backend-проект: каталог книг, бронирование, выдача и возврат, роли пользователей.

## Запуск

### Требования
- JDK 17 или новее (проверка: `java -version`)
- Git (для клонирования)
- Maven ставить не нужно, в проекте есть `mvnw`

### Вариант 1: командная строка
```
git clone https://github.com/Sasha-764/library.git
cd library
mvnw.cmd spring-boot:run
```
На Linux / macOS: `./mvnw spring-boot:run`

### Вариант 2: IntelliJ IDEA
1. File → Open → выбрать папку проекта, дождаться загрузки Maven.
2. Открыть `LibraryApplication.java` и нажать зелёный треугольник Run.

### Первый вход
Сервер работает на `http://localhost:8080`. При первом запуске создаётся файл БД `data/librarydb` и администратор:
- почта: `admin@library.com`
- пароль: `admin123`

## Роли
- **READER**: 
- **LIBRARIAN**: 
- **ADMIN**: 