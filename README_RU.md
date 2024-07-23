<a href="https://github.com/ArtyomKingmang/Lazurite/blob/main/README.md">For English speakers</a>

<div align="center">
  <img src="icon.png" width="300">
</div>

## Подробнее О Языке
>Для работы необходимо установить JDK 17+

Этот язык использует преимущества Java и упрощает его. Это делает Lazurite довольно простым. В основном язык используется для создания игр и приложений, так как имеет мощные библиотеки для этого. Но на нем можно делать ботов для социальных сетей, работать с файлами и многое другое.

Привет Мир на Lazurite!

```shell
a = "Привет"
b = "Мир"
print("$a ${b}!")
```
lsoup:
```cpp
using "lzr.net.lsoup"

lsoup.parser("https://www.lipsum.com")

title = lsoup.select("title")
text = lsoup.select("h3")

println(title)
print(text)
```

async example:
```cpp
using "lzr.async"
func hel(arg){
    print(arg)
}
async.supply(hel("Hello"))
```
graph example:
```cpp
using "lzrx.graph"
Frame()

fill(100,100,200)
rect(10,10,200,100)

fill(100,200,100)
lrect(100,100,100,100)
```
thread example:
```cpp
using "lzr.utils.thread"
func th(arg){
   println("My " + str(arg) + " Thread")
}
for(i=0, i<10, i++){
    std.thread(::th, i)
}
```

## Загрузка

Скачать jar и exe файлы интерпретатора можно на вкладке <a href = "https://github.com/ArtyomKingmang/Lazurite/releases">Releases</a>

## Взносы
Мы рассмотрим и поможем с любыми разумными запросами, если будут соблюдены следующие правила.

- Заголовок лицензии должен быть применен ко всем файлам исходного кода java.
- IDE or system-related files should be added to the .gitignore, never committed in pull requests.
- Файлы, связанные с IDE или системой, должны быть добавлены в .gitignore и никогда не добавляться в pull request.
- Отдайте предпочтение удобству чтения перед компактностью.
- Если вам нужна помощь, обратитесь к [Руководству по стилю Google Java](https://google.github.io/styleguide/javaguide.html).




## License
Lazurite распространяется на условиях <a href="https://github.com/ArtyomKingmang/Lazurite/wiki">MIT License 2.0</a>

Узнайте больше об этом!

