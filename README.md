# File Statistics Analyzer

Консольная утилита для анализа статистики файлов в указанном каталоге.

## Описание

Утилита анализирует файлы в указанной директории и предоставляет статистику по расширениям файлов:

- **Количество файлов** - общее число файлов с данным расширением
- **Размер в байтах** - суммарный размер всех файлов с данным расширением
- **Количество строк всего** - общее количество строк во всех файлах
- **Количество не пустых строк** - строки, содержащие хотя бы один печатный символ
- **Количество строк с комментариями** - однострочные комментарии в начале строки

## Поддерживаемые параметры

| Параметр | Описание |
|----------|----------|
| `<path>` | **Обязательный** путь к анализируемому каталогу |
| `--recursive` | Рекурсивный обход поддиректорий |
| `--max-depth=<number>` | Максимальная глубина рекурсии |
| `--thread=<number>` | Количество потоков для обработки (по умолчанию: количество ядер процессора) |
| `--include-ext=<ext1,ext2,...>` | Обрабатывать только указанные расширения |
| `--exclude-ext=<ext1,ext2,...>` | Исключить указанные расширения |
| `--git-ignore` | Учитывать правила из файлов .gitignore |
| `--output=<plain,xml,json>` | Формат вывода результатов (по умолчанию: plain) |
| `--help` | Показать справку по использованию |

## Поддерживаемые языки для комментариев

Утилита распознает комментарии для следующих языков программирования:

- **Java** (`//`, `/*`, `/**`)
- **Shell/Bash** (`#`)
- **Python** (`#`)
- **JavaScript/TypeScript** (`//`, `/*`, `/**`)
- **C/C++/C#** (`//`, `/*`, `/**`)
- **Ruby** (`#`)
- **Perl** (`#`)
- **XML/HTML** (`<!--`)

Комментарии учитываются только если они находятся в начале строки (допускаются пробелы и табы перед комментарием).

## Сборка

```bash
mvn clean package
```

## Запуск

Базовый запуск:
```bash
 java -jar target/MyJar-jar-with-dependencies.jar /path/to/directory
```

Пример с параметрами:
```bash
java -jar target/MyJar-jar-with-dependencies.jar /path/to/directory \
  --recursive \
  --max-depth=3 \
  --thread=4 \
  --include-ext=java,xml,sh \
  --exclude-ext=class,jar \
  --git-ignore \
  --output=json
```

Показать справку:
```bash
java -jar target/MyJar-jar-with-dependencies.jar --help
```

## Примеры вывода

### Plain формат (по умолчанию)
```
                        
Found 12 files to process
File Statistics:
===============================================================================
Extension       Files      Size(bytes)  Total Lines  Non-empty    Comments
===============================================================================
(no ext)        2          117          6            6            0
txt             1          68           1            1            0
java            1          471          16           15           7
sh              2          228          10           9            4
gitignore       1          73           5            5            0
xml             1          102          4            4            2
md              1          47           2            2            0
js              1          119          4            4            2
py              1          63           2            2            1
properties      1          0            0            0            0

```

### JSON формат
```json
{
  "fileStatistics": [
    {
      "extension": "java",
      "fileCount": 5,
      "totalSize": 24510,
      "totalLines": 478,
      "nonEmptyLines": 412,
      "commentLines": 87
    },
    {
      "extension": "xml",
      "fileCount": 3,
      "totalSize": 1520,
      "totalLines": 45,
      "nonEmptyLines": 42,
      "commentLines": 12
    }
  ]
}
```

### XML формат
```xml
<?xml version="1.0" encoding="UTF-8"?>
<fileStatistics>
  <fileType extension="java">
    <fileCount>5</fileCount>
    <totalSize>24510</totalSize>
    <totalLines>478</totalLines>
    <nonEmptyLines>412</nonEmptyLines>
    <commentLines>87</commentLines>
  </fileType>
  <fileType extension="xml">
    <fileCount>3</fileCount>
    <totalSize>1520</totalSize>
    <totalLines>45</totalLines>
    <nonEmptyLines>42</nonEmptyLines>
    <commentLines>12</commentLines>
  </fileType>
</fileStatistics>
```

## Особенности работы

- **Бинарные файлы** автоматически исключаются из обработки (изображения, архивы, исполняемые файлы и т.д.)
- **Обработка ошибок** - ошибки чтения отдельных файлов выводятся в stderr, но не прерывают работу программы
- **Многопоточность** - файлы обрабатываются параллельно для ускорения работы
- **Кэширование** - .gitignore файлы кэшируются для повышения производительности

## Примеры комментариев

### Java
```java
// Однострочный комментарий
/* Многострочный комментарий */
/** Javadoc комментарий */

    // Комментарий с отступом (учитывается)
System.out.println("// Это не комментарий"); // А это комментарий
```

### Bash/Shell
```bash
# Комментарий в shell скрипте
   # Комментарий с отступом

echo "# Это не комментарий" # А это комментарий
```

