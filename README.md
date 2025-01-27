[Для русскоговорящих](https://github.com/ArtyomKingmang/Lazurite/blob/main/README_RU.md)

<div align="center">
  <img src="icon.png" width="300">
</div>

> [!WARNING]
> The project is not dead, but is rarely updated

> [Current link to the compiler in Ixion](https://github.com/IxionLang/Lazurite)

## More About Language
>To work you need to install JDK 17+

This language takes advantage of Java and simplifies it. This makes Lazurite quite simple. Basically, the language is used to create games and applications, because it has powerful libraries for this. But on it you can make bots for social networks, work with files, and much more.

Hello World in Lazurite!:

```shell
print("Hello World!")
```


async example:
```cpp
using "lzr.utils.async"

func hel(arg) {
    print(arg)
}
async.supply(hel("Hello"))
```

pattern matching example:
```cpp
func test(x) = match(x) {
    case a: "case a: " + a
    case b: "case b: " + b
    case c: "case c: " + c
}

a = 10
b = 20

println test(15)  // case c: 15
println test(20)  // case b: 20
println test("test")  // case c: test
```

stream api example:
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

## Download
Download jar and exe files of the language interpreter can be downloaded in the [Releases](https://github.com/ArtyomKingmang/Lazurite/releases) tab.

## Contributions
We will review and help with all reasonable pull requests as long as the guidelines below are met.

- The license header must be applied to all java source code files.
- IDE or system-related files should be added to the .gitignore, never committed in pull requests.
- In general, check existing code to make sure your code matches relatively close to the code already in the project.
- Favour readability over compactness.
- If you need help, check out the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) for a reference.




## License
Lazurite is released under [MIT License 2.0](https://github.com/ArtyomKingmang/Lazurite/wiki)

See more about it!

