# Библиотеки

### lzr

* [net](#net)
  * [http - работа с сетью](#http)
  * [lsoup - работа с HTML ](#lsoup)
  

* [collections](#collections)
  * [hashMap](##hashMap)
  * [concurrentHashMap](##concurrentHashMap)
  * [linkedHashMap](##linkedHashMap)
  * [treeMap](##treeMap)
  * [concurrentSkipListMap](##concurrentSkipListMap)
  * [queue](##Очередь)


* [utils](#utils)
  * [async](#async)
  * [streamApi](#streamApi)
  * [arrays](#arrays)
  * [artify](#artify)
  * [base64](#base64)
  * [clipboard](#clipboard)
  * [json](#json)
  * [lfs](#lfs)
  * [math](#math)
  * [random](#random)
  * [thread](#thread)
  * [flatmap](#flatmap)
  * [time](#time)
  

* [lang](#lang)
  * [system](#system)
  * [io](#io)
  * [Character](#Character)
  * [Integer](#Integer)
  * [Double](#Double)
  * [String](#String)
  * [reflection - внедрение java пакетов](#reflection)



### lzrx
* [awt](#awt)
  * [lgl - графическая библиотека](#lgl)
  * [gforms - графическая библиотека (виджеты)](#gforms)
  * [robot - автоматизация](#robot)
  * [graph - графическая библиотека](#graph)
  * [lxml](#lxml)
  * [colors](#colors)


# net
* [http](#http)
* [lsoup](#lsoup)

# http
`http.download()` - загружает файл из downloadUrl в filePath
````java
using "lzr.net.http"

http.download("https://test/tester.html", "test.txt")
````


`http.getContentLength(url)` - получает длину содержимого, отправляя запрос HEAD на указанный URL-адрес

`http.request(url)` - выполняет GET-запрос на указанный адрес url.

`http.request(url, method)` - выполняет запрос на указанный адрес url методом method (GET, POST, PUT, DELETE, PATCH, OPTIONS).

`http.request(url, callback)` - выполняет GET-запрос на указанный адрес url, ответ сервера передаёт в функцию callback.
````java
using "lzr.net.http"

http.request("https://github.com/ArtyomKingmang/Lazurite/blob/main/docs/Documentation_RU.md", ::echo)
````
`http.request(url, method, params)` - выполняет запрос на указанный адрес url, методом method c данными params (объект).

`http.request(url, method, callback)` - выполняет запрос на указанный адрес url, методом method, ответ сервера передаёт в функцию callback.

`http.request(url, method, params, callback)` - выполняет запрос на указанный адрес url, методом method, с данными params, ответ сервера передаёт в функцию callback.

`http.request(url, method, params, options, callback)` - выполняет запрос на указанный адрес url, методом method, с данными params, параметрами подключения options, ответ сервера передаёт в функцию callback.

Параметрами подключения выступает объект. Допустимы следующие параметры

    header - задаёт http-заголовок, если передана строка или несколько заголовков, если массив.

    encoded - указывает, что данные params уже закодированы в URL-формате.

    content_type - указывает Content-Type.

    extended_result - указывает, что ответ сервера нужно вернуть в расширенном виде, а именно объектом с данными:

        text - текст ответа сервера

        message - сообщение сервера

        code - код ответа сервера

        headers - http-заголовки ответа

        content_length - Content-Length

        content_type - Content-Type

`url.encode(str)` - преобразует строку в URL-формат

# lsoup
`lsoup.parser(url)` - загружает HTML-код веб-страницы по указанному URL.

`lsoup.select("tag")` - выполняет поиск элементов с тегом "tag" в загруженном HTML-коде.

````java
using "lzr.net.lsoup"

lsoup.parser("https://artyomkingmang.github.io/lazurite-pages/")
result = lsoup.select("title")

print(result)
````

# collections

* [hashMap](##hashMap)
* [linkedHashMap](##linkedHashMap)
* [concurrentHashMap](##concurrentHashMap)
* [treeMap](##treeMap)
* [concurrentSkipListMap](##concurrentSkipListMap)
* [queue](##Очередь)

## hashMap
`hashMap(fromMap = {})` — создаёт новый HashMap из значений fromMap

## linkedHashMap
`linkedHashMap(fromMap = {})` — создаёт новый LinkedHashMap из значений fromMap

## concurrentHashMap
`concurrentHashMap(fromMap = {})` — создаёт новый ConcurrentHashMap из значений fromMap

## treeMap
`treeMap(fromMap = {}, comparator = func(a, b) = 0)` — создаёт новый TreeMap из значений fromMap и компаратора comparator

## concurrentSkipListMap
`concurrentSkipListMap(fromMap = {}, comparator = func(a, b) = 0)` — создаёт новый ConcurrentSkipListMap из значений fromMap и компаратора comparator

## Очередь
  `queue` - реализация ConcurrentLinkedQueue. Использование очереди отличается
от других коллекций. 

Пример:

````cpp
using "lzr.collections.queue"

q = queue.`new`()
queue.add(q, 10)
queue.add(q, 20)
println(queue.toArray(q))
````

Методы queue:
````
`new`()

add(yourQueue, arg)

remove(yourQueue)

toArray(yourQueue)

isEmpty(yourQueue)
````

# utils
* [async](#async)
* [arrays](#arrays)
* [artify](#artify)
* [base64](#base64)
* [clipboard](#clipboard)
* [lfs](#lfs)
* [json](#json)
* [math](#math)
* [random](#random)
* [thread](#thread)
* [flatmap](#flatmap)
* [time](#time)

# async
`async.supply(function)` - асинхронный способ получения или предоставления информации, когда данные поступают или отправляются не сразу, а по мере их доступности. Это означает, что вы можете запросить какие-то данные и продолжать выполнять другие задачи, пока данные еще не готовы. Когда данные станут доступны, они будут переданы вам для использования. Это помогает сделать программы более отзывчивыми и эффективными, так как они не блокируются в ожидании данных.

Пример использования:
````java
using "lzr.utils.async"

func hel(arg) {
    print(arg)
}

async.supply(hel("Hello"))
````
# streamApi
Stream API — это способ работать со структурами данных в 
функциональном стиле.

`map(func)` — преобразует каждый элемент

`sortBy(func)` — применяет функцию, затем сортирует элементы

Пример:

````java
using "lzr.utils.streamApi"

data = [
    {"a1" : 3},
    {"a1" : 1},
    {"a1" : 6},
    {"a1" : 4}
]

res = stream(data)
    .sortBy(func(x) = x.a1)
    .toArray()

println(res)
````

`foreach(func)` — вызывает функцию для каждого элемента

`skip(count)` — пропускает указанное количество элементов

`limit(count)` — ограничивает количество элементов

`custom(func)` — выполняет пользовательскую операцию над данными

`count()` — возвращает количество элементов

`toArray()` — возвращает массив элементов

# arrays
`arrays.combine(keys, values)` — создает массив, используя один массив для ключей, а другой — для его значений

Пример:
````java
using "lzr.utils.arrays"

colors = ["green", "red", "yellow"]
fruits = ["avocado", "apple", "banana"]

out = arrays.combine(colors, fruits)
print(out)
````

`arrays.keyExists(key, map)` - Проверяет, существует ли в массиве заданный ключ или индекс. Если существует, возвращает 1, иначе 0.

Пример:
```java
using "lzr.utils.arrays"

map = {
    "apple": "red"
    "banana": "yellow"
}

print(arrays.keyExists("apple", map))
```

`arrays.join(array, "delimiter", "prefix", "suffix")` — объединяет массив в строку с разделителем, префиксом и суффиксом.

Пример:

```java
using "lzr.utils.arrays"

array = ["banana", "apple", "pie"]
out_array = arrays.join(array, "--")

// Вывод: banana--apple--pie
print(out_array)
```

`arrays.sort(array)` — сортирует массив.


# artify
Библиотека для преобразования текста в ASCII арт.

`artify.build(text = "", style = "")` - преобразует текст в арт

Пример:
````java
using "lzr.utils.artify"

println(artify.build("Hello", "doom"))
````
Вывод:
````
  _   _        _  _        
| | | |      | || |       
| |_| |  ___ | || |  ___  
|  _  | / _ \| || | / _ \ 
| | | ||  __/| || || (_) |
\_| |_/ \___||_||_| \___/ 
````

Все стили:

````
banner,
doh,
doom,
georgia,
gothic,
small,
smkeyboard,
speed,
starwars,
swampland
````

# base64
`base64.encode(data, type = 0)` - кодирует массив байт или строку в закодированный base64 массив байт.

# clipboard
Библиотека для работы с для буфером обмена

`clipboard.get()` - получить текст из буфера обмена

`clipboard.set("text")` - установить текст в буфер обмена

`clipboard.has()` - проверяет, есть ли текст в буфере обмена

`clipboard.add("text")` - добавляет текст в конец буфера обмена

`clipboard.clear()` - отчищает буфер обмена

# lfs
`lfs.open(path, mode = "r")` — открывает файл по пути path в заданном режиме mode:

* "" - открывает файл или папку для получения информации;
* "r" - открывает файл для чтения в текстовом режиме;
* "rb" - открывает файл для чтения в бинарном режиме;
* "w" - открывает файл для записи в текстовом режиме;
* "w+" - открывает файл для дозаписи в текстовом режиме;
* "wb" - открывает файл для записи в бинарном режиме;
* "wb+" - открывает файл для дозаписи в бинарном режиме.

`lfs.close(f)` — закрывает файл.

`lfs.fileSize(f)` — возвращает размер файла в байтах.

`lfs.isDir(f)` — проверяет, является ли дескриптор f папкой. 1 - является, 0 - нет.

`lfs.isFile(f)` — проверяет, является ли дескриптор f файлом. 1 - является, 0 - нет.

`lfs.mkdir(f)` — создаёт папку. Возвращает 1, если создание прошло успешно, иначе - 0.

`lfs.read.allBytes(f)` — чтение всех байт файла. Возвращает массив байт файла.

`lfs.read.bytes(f, array, offset = 0, length = length(array))` — чтение заданного количества байт в массив array. Возвращает число прочитанных байт.
Если offset и length не указаны, то читается количество байт равное длине массива.
Если offset и length указаны, то читается length байт в массив array, начиная с offset+1 байта

`lfs.read.double(f)` — чтение 8 байт (вещественное число двойной точности)

`lfs.read.float(f)` — чтение 4 байт (вещественное число)

`lfs.read.int(f)` — чтение 4 байт (целое число)

`lfs.read.line(f)` — чтение строки в текстовом режиме

`lfs.read.long(f)` — чтение 8 байт (длинное целое число)

`lfs.read.short(f)` — чтение 2 байт (короткое целое число)

`lfs.read.text(f)` — чтение всего файла в текстовом режиме в строку

`lfs.read.utf(f)` — чтение строки в бинарном режиме

`lfs.rename(from, to)` — переименование (или перемещение) файла

`lfs.copy(src, dst)` — копирует файл src в dst

`lfs.delete(f)` — удаляет файл или папку. Возвращает 1, если удаление прошло успешно, иначе - 0

# json
`json.decode(data)` - преобразует переданные данные в строку в формате json
Пример:
````java
using "lzr.utils.json"

data = "{\"name\":Kingmang,\"digit\":2,\"array\":\"[2,6,7,7,4,6]\"}"
println(json.decode(data))
````
Результат:

`{array=[2,6,7,7,4,6], name=Kingmang, digit=2}`


# math
### Библиотека для работы с математикой

`math.abs(x)` — модуль числа x

`math.acos(x)` — арккосинус

`math.asin(x)`— арксинус

`math.atan(x)` — арктангенс

`math.atan2(y, x)` — угол θ, тангенс которого равен отношению двух указанных чисел

`math.cbrt(x)` — кубический корень числа x

`math.ceil(x)` — округляет вещественное число в большую сторону

`math.cos(x)` — косинус

`math.cosh(x)` — гиперболический косинус

`math.floor(x)` — округляет вещественное число в меньшую сторону

`math.getExponent(x)` — возвращают несмещенное значение экспоненты числа

`math.hypot(x, y)` — расчёт гипотенузы sqrt(x2 + y2) без переполнения

`math.IEEEremainder(x, y)` — возвращает остаток от деления x на y по стандарту ANSI/IEEE Std 754-1985, раздел 5.1

`math.log(x)` — логарифм

`math.log1p(x)` — натуральный логарифм от x + 1 (ln(x + 1))

`math.log10(x)` — десятичный логарифм

`math.max(x, y)` — максимальное из двух чисел

`math.min(x, y)` — минимальное из двух чисел

`math.pow(x, y)` — возведение x в степень y

`math.round(x)` — округляет вещественное число до ближайшего целого

`math.signum(x)` — возвращает целое число, указывающее знак числа

`math.sin(x)` — синус

`math.sinh(x)` — гиперболический синус

`math.sqrt(x)` — квадратный корень

`math.tan(x)` — тангенс

`math.tanh(x)` — гиперболический тангенс

`math.toDegrees(x)` — перевод радиан в градусы

`math.toRadians(x)` — перевод градусов в радианы

# thread
`thread("function", arg)` - создает новый поток. Первым аргументом выступает название функции (тип string), однако можно использовать и ссылку на функцию (`std.thread("function", arg)`).
Второй аргумент является аргументом переданной функции.

Пример:
````java
using "lzr.utils.thread"

func message(name) {
    print("Hello, " + name)
}

thread("message", "Artyom")
````
В этом потоке вызывается функция message с аргументом "Artyom".
Функция `message` просто выводит приветственное сообщение с именем, переданным в качестве аргумента.

Использование потоков выполнения позволяет выполнять различные задачи параллельно, что может улучшить производительность программы.
В данном случае, создается новый поток, который вызывает функцию message,
тогда как основной поток может продолжать выполнять другие задачи.


# flatmap

`flatmap(array, mapper)` — преобразует каждый элемент массива в массив элементов.

Пример:

````java
using "lzr.collections.flatmap"

numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9]

output = flatmap(numbers, func(x) {
  arr = Array(x)
  for(i = 0, i < x, i++) {
    arr[i] = x
  }
  return arr
})

print(output)
````


# random
`random(from = 0, to = 10)` — возвращает псевдослучайное число.

# time
`time.sleep(time)` - приостановка текущего потока на time миллисекунд.


# lang
* [system](#system)
* [io](#io)
* [Character](#Character)
* [Integer](#Integer)
* [Double](#Double)
* [String](#String)

# system
### Библиотека для работы с системой.
`system.getProperty("property.name")` - возвращает информацию о локальной системе и конфигурации.

Пример:
````java
using "lzr.lang.system"

// Все property, которые есть в java.lang.System.getProperty()
println(system.getProperty("lzr.version"))
println(system.getProperty("os.name"))
println(system.getProperty("java.version"))
println(system.getProperty("file.separator"))
````

`system.currentTimeMillis()` - возвращает текущее время в миллисекундах. Единицей времени возвращаемого значения является миллисекунда, степень детализации значения зависит от базовой операционной системы и может быть больше.



`system.exec(command)` - выполняет указанную строковую команду в отдельном процессе.
Аргумент, который передается - системная команда.

`system.exit(code)` - завершение программы.
Этот метод на вход принимает значение типа int. Обычно это 0, что говорит о том, что программа завершается без ошибок. Любое другое значение говорит о том, что программа завершилась с ошибкой.


`system.nanoTime()` - возвращает текущее значение наиболее точного доступного системного таймера в наносекундах.


`system.getScreenResolution()` - возвращает разрешение экрана.
### Память

`system.getUsedMemory()` - Возвращает используемый объем памяти.

`system.getTotalMemory()` - Возвращает общий объем памяти виртуальной машины (jvm).

`system.getMaxMemory()` - Возвращает максимальный объем памяти, который jvm попытается использовать.

`system.getFreeMemory()` - Возвращает объем свободной памяти в виртуальной машине (jvm).

`system.availableProcessors()` - Возвращает количество процессоров, доступных виртуальной машине (jvm).

# io
### (добавлено с *2.7.4*)
Содержит функции для работы с потоками ввода/вывода

### PrintStream
#### Методы:
`new(filename)` - инициализирует
новый PrintStream. Аргументом
является имя файла, куда будет
выводиться информация.

`print(arg)` - записывает строку
из аргумента в файл.

Поскольку методы классов io
являются ключевыми словами
(в языке уже есть ключевые слова
`new` и `print`), то их методы нужно
перегружать. Если вы не знакомы
с перегрузкой методов, то
обратитесь к DOCUMENTATION_RU.md ->
раздел 26.

Пример использования:

````cpp
using "lzr.lang.io"

ps = io.PrintStream
ps.`new`("test.txt")
ps.`print`("Hello everyone!")
````

### ByteArrayInputStream
#### Методы:
`new(arg)` - инициализирует
новый ByteArrayInputStream.
Переданный аргумент он
переводит в массив byte.

`read()` - Считывает следующий байт данных из этого входного потока.

````cpp
using "lzr.lang.io"

input = "Hello"

bais = io.ByteArrayInputStream
bais.`new`(input)

while((b = bais.read()) != -1) {
    println(b)
}
````

`avaliable()` - Это встроенный метод, который возвращает количество оставшихся байтов, которые можно прочитать (или пропустить) из этого входного потока.

`readNBytes(arg, off, len)` - Считывает запрошенное количество байтов из входного потока в заданный массив байтов. Этот метод блокируется до тех пор, пока не будут прочитаны len байт входных данных, не будет обнаружен конец потока или не будет выдано исключение


# Character
`Character.isAlphabetic(arg)` - Определяет, является ли указанный символ (кодовая точка Юникода) алфавитом.
`Character.isDigit(arg)` - Определяет, является ли указанный символ цифрой.
`Character.isLetter(arg)` - Определяет, является ли указанный символ буквой.
`Character.isLetterOrDigit(arg)` - Определяет, является ли указанный символ буквой или цифрой.



# Integer
`Integer.bitCount(arg = 0)` - Возвращает количество однобитовых чисел в двоичном представлении указанного целочисленного значения.

`Integer.max(arg1 = 0, arg2 = 0)` - Возвращает большее из двух значений int.

`Integer.min(arg1 = 0, arg2 = 0)` - Возвращает меньшее из двух значений int.

`Integer.compare(arg1 = 0, arg2 = 0)` - Сравнивает два значения int

`Integer.parseInt(arg = "")` - Анализирует строковый аргумент как десятичное целое число со знаком.

`Integer.decode(arg = "")` - Декодирует строку в целое число.

`Integer.signum(arg = 0)` - Возвращает функцию signum указанного значения int

`Integer.compareUnsigned(arg1 = 0, arg2 = 0)` - Сравнивает два значения int в числовом виде, рассматривая значения как "беззнаковые".

`Integer.MAX_VALUE` - Константа, содержащая максимальное значение, которое может иметь int (2**31-1).

`Integer.MIN_VALUE `- Константа, содержащая минимальное значение, которое может иметь int, (-2**31).

# Double
`Double.max(arg1 = 0, arg2 = 0)` - Возвращает большее из двух значений double.

`Double.min(arg1 = 0, arg2 = 0)` - Возвращает меньшее из двух значений double.

`Double.doubleToLongBits(arg)` - функция используется для преобразования числа типа double в его битовое представление типа long

`Double.parseDouble(arg)` - Анализирует строковый аргумент как десятичное целое число со знаком.

`Double.MAX_VALUE` - Константа, содержащая максимальное значение, которое может иметь double.

`Double.MIN_VALUE `- Константа, содержащая минимальное значение, которое может иметь double.

# String

`String.valueOf(arg)`

`String.format(arg, arg)`

`String.join(arg, arg)`

`String.CASE_INSENSITIVE_ORDER`


# reflection
Работа с пакетами java и конвертацией java в lazurite (и наоборот)

`JClass(arg)` - создает новый JavaClassValue

````java
using "lzr.lang.reflection"

Locale = JClass("java.util.Locale")

locale1 = new Locale("EN", "INDIA");
println("Locale: " + locale1)

println("Country Name: " + locale1.getDisplayCountry());
````

`JObject(arg)` - конвертирует LZRValue в java объект

`LZRValue(arg)` - конвертирует java объект в LZRValue

### Добавлено с версии *2.7.3*
`JUpload("jarname.jar", "com.example")` -
динамическая загрузка библиотеки
из jar файла. Первый аргумент - имя jar
файла, второй - название пакета, в котором
должен находится класс invoker







# awt

* [lgl](#lgl)
* [gforms](#gforms)
* [robot](#robot)
* [graph](#graph)
* [colors](#colors)



# lgl
### (добавлено с *2.7.4*)


`frame = lgl.createFrame("Title", width, height)` - Создать фрейм. все параметры необязательны.<BR>

`lgl.redraw()` - Перерисовать текущий фрейм. <br>

`frame.rect(x, y, width, height)` - Отрисовка прямоугольника с заданной позицией и размерами.<BR>

`frame.setFill(Color)` - Установка цвета заливки.<BR>

`frame.text(textString, x, y)` - Нарисовать текст в позиции x, y<BR>

`frame.roundRect(x, y, width, height, roundX, roundY)` - Заливка закруглённого прямоугольника. <BR>

`frame.line(x, y, width, height)` - Нарисовать линию <br>

`frame.rotate(degree)` - Повернуть последующие объекты на degree градусов. <br>

`frame.setLineCap(value)` - Установить ограничение линии <br>

`frame.setLineJoin(value) `- Установить соединение линии <br>

`frame.setStroke(Color)` - Установить цвет обводки <br>

`frame.getFill()` - Возвращает установленный цвет заливки <br>

`frame.getStroke()` - Возвращает цвет обводки<br>

`frame.ellipse(v, v1, v2, v3)` - Отрисовка круга <br>

`frame.lineTo(x, y)` - Проложить линию в (x, y) <br>

`frame.getGlobalAlpha()` - Получить глобальный коэффицент прозрачности <br>

`frame.clip()` - Обрезка отрисовки в фрейме

Пример 1:

```python
using "lzrx.awt.lgl"

frame = lgl.createFrame("Hello, Lazurite!", 500, 400)
frame.rect(10, 10, 30, 30)
```

`img = loadImage("file: path")` - загружает
изображение в переменную img. Если указать в строке
`file:` следом идет путь к файлу. Если не указывать file,
то аргумент будет являться ссылка на изображение.

`frame.image(img, x, y, width, height)` - рисует изображение
img в позиции x; y с размерами width; height.

Пример 2:

````python
using "lzrx.awt.lgl"

frame = lgl.createFrame(500, 500)

img = loadImage("https://cs8.pikabu.ru/post_img/2016/02/17/10/145572598117693502.png")
frame.image(img, 100, 100, 100, 100)
````

### Остальные методы

`frame.setFillRule()`

`frame.save()`

`frame.arc()`

`frame.moveTo()`

`frame.beginPath()`

`frame.endPath()`

`frame.quadraticCurveTo()`



### Служебные компоненты
#### Color - Цвета
+ Сolor.rgb(r, g, b)
+ Color.hsb(h, s, b)
+ Color.new()
+ Color.web()


Пример 3:
````python
using "lzrx.awt.lgl"

frame = lgl.createFrame(500, 500)

frame.setFill(Color.rgb(100, 100, 200))
frame.rect(100, 100, 100, 100)
````
#### Effects - эффекты
+ Blend()

+ Bloom()

+ BoxBlur()

+ ColorAdjust()

+ ColorInput()

+ DropShadow()

+ GaussianBlur()

+ Glow()

+ InnerShadow()

+ Lighting()

+ MotionBlur()

+ PerspectiveTransform()

+ Reflection()

+ SepiaTone()

+ Shadow()

Пример 4:

````python
using "lzrx.awt.lgl"

window = lgl.createFrame("Test Frame", 500, 500)

window.setEffect(effect.BoxBlur())
window.setFill(Color.BLUE)
window.rect(100, 100, 100, 100)
````

### Обработка нажатий

#### Event
Функции:
```
Handler()

Filter()
```

Константы:
```
DRAG_DETECTED = 0
MOUSE_CLICKED = 1
MOUSE_DRAGGED = 2
MOUSE_ENTERED = 3
MOUSE_ENTERED_TARGET = 4
MOUSE_EXITED = 5
MOUSE_EXITED_TARGET = 6
MOUSE_MOVED = 7
MOUSE_PRESSED = 8
MOUSE_RELEASED = 9
KEY_PRESSED = 10
KEY_RELEASED = 11
KEY_TYPED = 12
SWIPE_DOWN = 13
SWIPE_LEFT = 14
SWIPE_RIGHT = 15
SWIPE_UP = 16
```

Пример 5:
````python
using "lzrx.awt.lgl"

window = lgl.createFrame("Test Frame", 500, 500)

Event.Handler(Event.KEY_PRESSED, func(e) {
  if (e.code == KeyCode.X) {
    println("Click X")
  }
})
````

Ивенты для работы с мышью:
````
button
clickCount
sceneX
sceneY
screenX
screenY
mouseX
mouseY
mouseZ
isAltDown
isConsumed
isControlDown
isDragDetect
isMetaDown
isMiddleButtonDown
isPopupTrigger
isPrimaryButtonDown
isSecondaryButtonDown
isShiftDown
isShortcutDown
isStillSincePress
isSynthesized
````
Пример 6:
````python
using "lzrx.awt.lgl"

window = lgl.createFrame("Test Frame", 500, 500)

Event.Handler(Event.MOUSE_MOVED, func(e) {
  window.ellipse(e.mouseX - 50, e.mouseY - 50, 100, 100)
  lgl.redraw()
})
````
Ивенты для работы с клавиатурой:
````
code
character
text
isAltDown
isConsumed
isControlDown
isMetaDown
isShiftDown
isShortcutDown
````
(смотреть пример 5)




# gforms
* [Виджеты](#Виджеты)
* [Функции всех виджетов](#Функции-всех-виджетов)
* [Функции Frame](#Функции-Frame)
* [Функции Label](#Функции-Label)
* [Layouts](#Layouts)
  * [Методы layout](#Методы-layout)
  * [BorderLayout](#BorderLayout)
  * [BoxLayout](#BoxLayout)
## Виджеты
`Frame(title = "")` - создает новое окно

`Button(text = "")` - создает новую кнопку

````java
using "lzrx.awt.gforms"

window = Frame("widgets")
window.setMinimumSize(600, 300)

button = Button("Button")

window.add(button)
window.pack()
window.setVisible()
````

`ProgressBar(isVertical = true, min, max)` - создает новый индикатор

`ScrollPane(view, vsbPolicy, hsbPolicy)` - создает новую панель прокрутки

`Label(text = "", SwingConstants.LEADING)` - создает новый текст

`Panel(layoutManager)` - создает новую панель с дополнительным менеджером макетов

`TextField(text = "")` - создает новое текстовое поле

`TextArea(text = "")` - создает новую текстовую область


### Добавлено с версии *2.7.3*

`MenuBar(menu = "", item1 = "")` - создает новый menuBar. Первый аргумент - заголовок к меню.
Последующие аргументы - ячейки в заданном меню


`FileChooser()` - создает контейнер, в котором расположены несколько компонентов, списков и кнопок, «управляющих» выбором файлов


## Функции всех виджетов
````
setFont(Font)
getFont()
onKeyAction(Function)
addKeyListener(Function)
getFocusTraversalKeysEnabled()
getHeight()
getIgnoreRepaint()
getLocation()
getLocationOnScreen()
getMinimumSize()
getMaximumSize()
getName()
getPreferredSize()
getSize()
getWidth()
getX()
getY()
hasFocus()
invalidate()
isDisplayable()
isDoubleBuffered()
isEnabled()
isFocusOwner()
isFocusable()
isLightweight()
isOpaque()
isShowing()
isValid()
isVisible()
requestFocus()
requestFocusInWindow()
repaint()
revalidate()
setMaximumSize(w, h)
setMinimumSize(w, h)
setName(name = "")
setPreferredSize(w, h)
setSize(w, h)
setVisible(bool = true)
setLocation(x, y)
validate()
````

## Функции Frame
````
getTitle()
getResizable()
getDefaultCloseOperation()
setDefaultCloseOperation()
setResizable(boolean = true)
setTitle(title = "")
````

## Функции Label
````
getDisplayedMnemonic()
getDisplayedMnemonicIndex()
getHorizontalAlignment()
getHorizontalTextPosition()
getIconTextGap()
getVerticalAlignment()
getVerticalTextPosition()
getText()
setDisplayedMnemonic(mnemonic = '')
setDisplayedMnemonicIndex(index = 0)
setHorizontalAlignment(SwingConstants.LEFT)
setHorizontalTextPosition(SwingConstants.CENTER)
setVerticalAlignment(SwingConstants.NORTH)
setVerticalTextPosition(SwingConstants.BOTTOM)
setText(text = "")
````

## Layouts

### Методы layout
````
borderLayout(hgap = 0, vgap = 0) - создаёт BorderLayout
boxLayout(panel, axis = BoxLayout.PAGE_AXIS) - создаёт BoxLayout
cardLayout(hgap = 0, vgap = 0) - создаёт CardLayout
flowLayout(align = FlowLayout.CENTER, hgap = 5, vgap = 5) - создаёт FlowLayout
gridLayout(rows = 1, cols = 0, hgap = 0, vgap = 0) - создаёт GridLayout
````

Пример
````java
using "lzrx.awt.gforms"

window = Frame("test")
window.setMinimumSize(600, 300)

panel = Panel(layoutManager = borderLayout())

field = TextField("")
field.setFont("Tahoma", 0, 28)

panel.add(field, BorderLayout.NORTH)
second_panel = Panel(layoutManager = gridLayout(rows = 4, cols = 4))
for(i = 0, i < 4, i++) {
    second_panel.add(Button(i))
}

window.add(panel, BorderLayout.NORTH)
window.add(second_panel, BorderLayout.CENTER)
window.pack()
window.setVisible()
````
### BorderLayout:
````

AFTER_LINE_ENDS=After;

LINE_END=After;

LINE_START=Before;

BEFORE_LINE_BEGINS=Before;

CENTER=Center;

EAST=East;

BEFORE_FIRST_LINE=First;

PAGE_START=First;

AFTER_LAST_LINE;

PAGE_END;

NORTH;

SOUTH;

WEST
````
### BoxLayout:
````
X_AXIS;

Y_AXIS;

LINE_AXIS;

PAGE_AXIS
````

# robot
#### Добавлено с версии *2.7.3*
Содержит методы для автоматизации 
взаимодействия с графическим 
интерфейсом пользователя

`robot.click(buttons)` - Осуществляет клик мышью с заданными клавишами.

`robot.execProcess(args...)` - Запускает процесс с указанными аргументами. Если передано несколько аргументов, они все передаются как параметры. Если передан только один массив, его элементы используются как параметры. Если передан только один параметр, он служит единственным параметром.

````cpp
using "robot"
robot.execProcess("mkdir", "Hello")
````

`robot.keyPress(key)` - Зажимает клавишу с указанным кодом.

````cpp
using "lzrx.robot"

robot.keyPress(KEY.TAB)
robot.keyPress(KEY.A)
````

`robot.keyRelease(key)` - Отпускает клавишу с указанным кодом.

`robot.mouseMove(x, y)` - Перемещает указатель мыши в указанные координаты.

`robot.execProcessAndWait(args...)` - Аналогично execProcess, но ожидает завершение порождаемого процесса и возвращает его статус.

`robot.fromClipboard()` - Получает строку из буфера обмена.

`robot.mousePress(buttons)` - Зажимает указанную кнопку мыши.

`robot.mouseRelease(buttons)` - Отпускает указанную кнопку мыши.

`robot.mouseWheel(value)` - Прокручивает колесо мыши (отрицательное значение - вверх, положительное - вниз).

`robot.setAutoDelay(ms)` - Устанавливает задержку после каждого автоматического события.

`robot.toClipboard(text)` - Копирует строку в буфер обмена.

`robot.typeText(text)` - Последовательно нажимает клавиши для ввода указанного текста



# graph

### Упрощенная библиотека для работы с графикой.

### Окно
`Frame(arg[0], arg[1], arg[2])` - создает окно с заголовком `arg[0]` и размерами `arg[1]`, `arg[2]`.
Все аргументы опциональны и указывать их необязательно, ведь в таком случае будут выбраны заранее заготовленные аргументы.

````java
using "lzrx.awt.graph"

width = 640
height = 400

Frame("test", width, height)
````
Функции окна:
````
background(r, g, b) - установка цвета фона
Redraw() - обновление экрана
````
### Примитивы
`ellipse(x, y, w, h)` - создает закрашенный эллипс в координатах x, y с размерами w, h

`lellipse(x, y, w, h)` - создает эллипс в координатах x, y с размерами w, h

`rect(x, y, w, h)` - создает закрашенный прямоугольник в координатах x, y с размерами w, h

`lrect(x, y, w, h)` - создает прямоугольник в координатах x, y с размерами w, h

`fill(rgb)` - закрашивает все фигуры, идущие после него

````java
using "lzrx.awt.graph"

Frame()

fill(100, 100, 200)
rect(10, 10, 200, 100)

fill(100, 200, 100)
lrect(100, 100, 100, 100)
````


### Текст
`text("text", x, y)` - создает текст в координатах x, y
```java
using "lzrx.awt.graph"

Frame(500,500)
fill(0)
text("Hello",100,100)
```
`font("Arial", size)` - устанавливает шрифт с определенным размером
```java
using "lzrx.awt.graph"
Frame(500,500)

font("Arial",30)

fill(0)
text("Hello",100,100)
```
### Обработка нажатий
`keyPressed()`
```java
using "lzrx.awt.graph"

Frame(500,500)

while(true) {
    key = keyPressed()
    if (key == KEY.LEFT) {
        println("left")
    } else if (key == KEY.A) {
        println("a")
    }
}
```
`mouseHover()`
```java
using "lzrx.awt.graph"

Frame()
mouse = mouseHover()

while(true) {
    background(100, 100, 200)
    fill(255, 255, 255)
    ellipse(mouse[0], mouse[1], 50, 50)
    Redraw()
}
```


### Другие функции
```
translate(arg[0], arg[1])
dispose()
rotate(arg[0]) || rotate(arg[0], arg[1], arg[2])
scale(arg[0], arg[1])
```

# lxml

`xml.read("file.xml")` - читает xml файл


# colors

Упрощает работу с цветами в консоли.
Пример:
```java
using "lzrx.awt.colors"

println(sprintf(color.blue))
println("Hello")
```
### Цвета

`color.red` - красный цвет

`color.yellow` - жёлтый цвет

`color.green` - зелёный цвет

`color.purple` - фиолетовый цвет

`color.blue` - синий цвет

`color.cyan` - голубой цвет

`color.white` - белый цвет

`color.black` - чёрный цвет

`color.clear()` - отчистить цвет



