[For English speakers](https://github.com/ArtyomKingmang/Lazurite/blob/main/README.md)

<div align="center">
  <img src="icon.png" width="300">
</div>

## Подробнее О Языке
> Для работы необходимо установить JDK 17+

Lazurite - интерпретируемый язык программирования с динамической типизацией.
Этот язык использует преимущества Java и упрощает его. Это делает Lazurite довольно простым.
В основном язык используется для создания *игр* и *приложений*, так как имеет мощные библиотеки для этого.
Но на нем можно делать *ботов для социальных сетей*, *работать с файлами* и многое *другое*.

Привет Мир на Lazurite!
```shell
print("Привет мир!")
```

Url запросы:
```java
using "lzr.net.lsoup"

lsoup.parser("https://www.lipsum.com")

title = lsoup.select("title")
text = lsoup.select("h3")

println(title)
print(text)
```

Асинхронность:
```java
using "lzr.utils.async"

func hel(arg) {
    print(arg)
}
async.supply(hel("Hello"))
```

Многопоточность:
```java
using "lzr.utils.thread"

func th(arg) {
   println("My ${arg} thread")
}

for(i : range(10)) {
    std.thread(::th, i)
}
```

Stream Api из Java:
```java
using "lzr.utils.streamApi"

inputArray = range(0, 5) // [0, 0, 0, 0, 0]
resultArray = stream(inputArray)
 .custom(::changeNums)
 .toArray()

func changeNums(container) {
    len = length(container)
    result = Array(len)
    for(i = 0, i < len, i++) {
        result[i] = 7
    }
    return result
}

println(resultArray)
```

И возможность работы с Java в целом:
```java
using "lzr.lang.reflection"

Stack = JClass("java.util.Stack")

stack = new Stack()
stack.push(8)
stack.push(9)

print(stack.pop())
```

## Загрузка
Скачать *.jar* или *.exe* файл интерпретатора можно на вкладке [Releases](https://github.com/ArtyomKingmang/Lazurite/releases).

## Дополнительно
Просмотрите [базовую документацию](https://github.com/ArtyomKingmang/Lazurite/blob/main/docs/Documentation_RU.md)
и [документацию для всех встроенных библиотек](https://github.com/ArtyomKingmang/Lazurite/blob/main/docs/Libraries_RU.md)
перед работой с языком.<br>
Также вы можете посмотреть примеры [простых программ](https://github.com/ArtyomKingmang/Lazurite/tree/main/examples),
на примере которых показано, как использовать ту или иную возможность языка.

## Контрибьютинг
Мы рассмотрим и поможем с любыми разумными запросами, если будут соблюдены следующие правила:

- Лицензия должна быть применена ко всем файлам исходного кода.
- Файлы, связанные с IDE или системой, должны быть добавлены в .gitignore и никогда не добавляться в pull request.
- Отдайте предпочтение удобству чтения перед компактностью.
- Если вам нужна помощь, обратитесь к [Руководству по стилю Google Java](https://google.github.io/styleguide/javaguide.html).

## Лицензия
Lazurite распространяется на условиях [MIT License 2.0](https://github.com/ArtyomKingmang/Lazurite/wiki)

Узнайте больше об этом!

