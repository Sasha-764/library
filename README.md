Запуск
 Требования
- JDK 17 или новее (проверка: `java -version`)
- Git (для клонирования)
- Maven ставить не нужно, в проекте есть `mvnw`

Вариант 1: командная строка
git clone https://github.com/Sasha-764/library.git
cd library
mvnw.cmd spring-boot:run        (Windows)
./mvnw spring-boot:run          (Linux / macOS)

Вариант 2: IntelliJ IDEA
1. File → Open → выбрать папку проекта, дождаться загрузки Maven.
2. Открыть `LibraryApplication.java` и нажать зелёный треугольник Run.

 Первый вход
При первом запуске создаётся файл БД `data/librarydb` и администратор:
- почта: `admin@library.com`
- пароль: `admin123`
