<a href="https://github.com/ArtyomKingmang/Lazurite/blob/main/README.md">For English speakers</a>

<div align="center">
  <img src="icon.png" width="300">
</div>

## Подробнее О Языке
> Для работы необходимо установить JDK 17+

Этот язык использует преимущества Java и упрощает его. Это делает Lazurite довольно простым.
В основном язык используется для создания *игр* и *приложений*, так как имеет мощные библиотеки для этого.
Но на нем можно делать *ботов для социальных сетей*, *работать с файлами* и многое *другое*.

Привет Мир на Lazurite!

```shell
print("Привет мир!")
```

пример lsoup:
```cpp
using "lzr.net.lsoup"

lsoup.parser("https://www.lipsum.com")

title = lsoup.select("title")
text = lsoup.select("h3")

println(title)
print(text)
```

пример async:
```cpp
using "lzr.utils.async"

func hel(arg) {
    print(arg)
}
async.supply(hel("Hello"))
```

пример thread:
```cpp
using "lzr.utils.thread"

func th(arg) {
   println("My ${arg} thread")
}

for(i = 0, i < 10, i++) {
    std.thread(::th, i)
}
```

пример stream api:
```cpp
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

## Загрузка

Скачать jar и exe файлы интерпретатора можно на вкладке [Releases](https://github.com/ArtyomKingmang/Lazurite/releases).

## Контрибьютинг
Мы рассмотрим и поможем с любыми разумными запросами, если будут соблюдены следующие правила.

- Лицензия должна быть применена ко всем файлам исходного кода.
- Файлы, связанные с IDE или системой, должны быть добавлены в .gitignore и никогда не добавляться в pull request.
- Отдайте предпочтение удобству чтения перед компактностью.
- Если вам нужна помощь, обратитесь к [Руководству по стилю Google Java](https://google.github.io/styleguide/javaguide.html).




## Лицензия
Lazurite распространяется на условиях <a href="https://github.com/ArtyomKingmang/Lazurite/wiki">MIT License 2.0</a>

Узнайте больше об этом!

