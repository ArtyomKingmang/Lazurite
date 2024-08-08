<a href="https://github.com/ArtyomKingmang/Lazurite/blob/main/README_RU.md">Для русскоговорящих</a>

<div align="center">
  <img src="icon.png" width="300">
</div>

## More About Languge
>To work you need to install jdk 17 +

This language takes advantage of Java and simplifies it. This makes Lazurite quite simple. Basically, the language is used to create games and applications, because it has powerful libraries for this. But on it you can make bots for social networks, work with files, and much more.

Hello World in Lazurite!:

```shell
print("Hello World!")
```
lsoup:
```cpp
using "lzr.net.lsoup"

lsoup.parser("https://www.lipsum.com")

title = lsoup.select("title")
text = lsoup.select("h3")

println(title)
println(text)
```

async example:
```cpp
using "lzr.utils.async"
func hel(arg){
    print(arg)
}
async.supply(hel("Hello"))
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

stream api example:
```cpp
using "lzr.utils.streamApi"

inputArray = range(0,5) // [0,0,0,0,0]
resultArray = stream(inputArray)
 .custom(::changeNums)
 .toArray()

func changeNums(container){
    len = length(container)
    result = Array(len)
    for(i = 0, i < len, i++){
        result[i] = 7
    }
    return result
}

println(resultArray)

```

## Download

Download jar and exe files of the language interpreter can be downloaded in the <a href = "https://github.com/ArtyomKingmang/Lazurite/releases">Releases</a> tab)

## Contributions
We will review and help with all reasonable pull requests as long as the guidelines below are met.

- The license header must be applied to all java source code files.
- IDE or system-related files should be added to the .gitignore, never committed in pull requests.
- In general, check existing code to make sure your code matches relatively close to the code already in the project.
- Favour readability over compactness.
- If you need help, check out the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) for a reference.




## License
Lazurite is relseased under <a href="https://github.com/ArtyomKingmang/Lazurite/wiki">MIT License 2.0</a>

See more about it!


