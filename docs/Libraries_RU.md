# Библиотеки
* [graph - графическая библиотека](#graph)
* [http - работа с сетью](#http)
* [lsoup - работа с HTML ](#lsoup)
* [reflection - внедрение java пакетов](#reflection)
* [gforms - графическая библиотека (виджеты)](#gforms)
* [jloader - загрузка jar файлов в lazurite](#jloader)
* [arrays - работа с массивами](#arrays)
* [async](#async)
* [std](#std)
* [artify](#artify)
* [base64](#base64)
* [clipboard](#clipboard)
* [LFS - работа с файлами](#LFS)
* [system](#system)
* [ML](#ML)
* [random](#random)
* [time](#time)
* [colors](#colors)
* [json](#json)

# graph

## Библиотека для работы с графикой.

### Окно
Frame(arg[0], arg[1], arg[2]) - создает окно с заголовком arg[0] и размерами arg[1], arg[2].
Все аргументы опциональны и указывать их необязательно, ведь в таком случае будут выбраны заранее заготовленные аргументы. 

````java
using "graph"
width = 640
height = 400
Frame("test",width,height)
````
Функции окна:
````
background(r,g,b) - установка цвета фона
Redraw() - обновление экрана
````
### Примитивы
`ellipse(x,y,w,h)` - создает закрашенный эллипс в координатах x, y с размерами w, h

`lellipse(x,y,w,h)` - создает эллипс в координатах x, y с размерами w, h

`rect(x,y,w,h)` - создает закрашенный прямоугольник в координатах x, y с размерами w, h

`lrect(x,y,w,h)` - создает прямоугольник в координатах x, y с размерами w, h

`fill(rgb)` - закрашивает все фигуры, идущие после него 

````java
using "graph"
Frame()

fill(100,100,200)
rect(10,10,200,100)

fill(100,200,100)
lrect(100,100,100,100)
````


### Текст
`text("text", x, y)` - создает текст в координатах x, y
````java
using "graph"
Frame(500,500)
fill(0)
text("Hello",100,100)
````
`font("Arial", size)` - устанавливает шрифт с определенным размером
````java
using "graph"
Frame(500,500)

font("Arial",30)

fill(0)
text("Hello",100,100)
````
### Обработка нажатий
`keyPressed()`
````java
using "graph"
Frame(500,500)

while(1){
    key = keyPressed()
    if(key == KEY.LEFT){
        println("left")
    }else if(key == KEY.A){
        println("a")
    }
}
````
`mouseHover()`
````java
using "graph"

Frame()
mouse = mouseHover()

while(1){
    background(100,100,200)
    fill(255,255,255)
    ellipse(mouse[0], mouse[1], 50,50)
    Redraw()
}
````
        
        

### Другие функции
````
translate(arg[0], arg[1])
dispose()
rotate(arg[0]) || rotate(arg[0], arg[1], arg[2])
scale(arg[0], arg[1])
````
# http
`http.download()` - загружает файл из downloadUrl в filePath
````java
using "http"
http.download("https://test/tester.html", "test.txt")
````


`http.getContentLength(url)` - получает длину содержимого, отправляя запрос HEAD на указанный URL-адрес

`http.request(url)` - выполняет GET-запрос на указанный адрес url.

`http.request(url, method)` - выполняет запрос на указанный адрес url методом method (GET, POST, PUT, DELETE, PATCH, OPTIONS).

`http.request(url, callback)` - выполняет GET-запрос на указанный адрес url, ответ сервера передаёт в функцию callback.
````java
http.request("https://github.com/ArtyomKingmang/Lazurite/blob/main/docs/RU.md", ::echo)
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
`lsoup.parse(url)` - загружает HTML-код веб-страницы по указанному URL.

`lsoup.select("tag")` - выполняет поиск элементов с тегом "tag" в загруженном HTML-коде.

````java
using "lsoup"

lsoup.parse("https://artyomkingmang.github.io/lazurite-pages/")
result = lsoup.select("title")

print(result)
````

# reflection
Работа с пакетами java и конвертацией java в lazurite (и наоборот) 

`JClass(arg)` - создает новый JavaClassValue

````java
using "reflection"
Locale = JClass("java.util.Locale")

locale1 = new Locale("EN", "INDIA");
println("Locale: " + locale1)

println("Country Name: " + locale1.getDisplayCountry());
````

`JObject(arg)` - конвертирует LZRValue в java объект

`LZRValue(arg)` - конвертирует java объект в LZRValue

### Добавлено с версии 2.7.3
`JUpload("jarname.jar", "com.example")` - 
динамическая загрузка библиотеки
из jar файла. Первый аргумент - имя jar
файла, второй - название пакета, в котором
должен находится класс invoker

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
using "gforms"

button = Button("Button")

window = Frame("widgets")
window.setMinimumSize(600,300)
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


### Добавлено с версии 2.7.3

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
using "gforms"

window = Frame("test")
window.setMinimumSize(600,300)

panel = Panel(layoutManager = borderLayout())

field = TextField("")
field.setFont("Tahoma",0,28)

panel.add(field,BorderLayout.NORTH)
second_panel = Panel(layoutManager = gridLayout(rows=4, cols=4))
for(i = 0, i < 4, i++){
second_panel.add(Button(i))
}

window.add(panel,BorderLayout.NORTH)
window.add(second_panel,BorderLayout.CENTER)
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

# jloader
Библиотека позволяет загружать jar-файлы, не добавляя их в сам lazurite

Пример:
````java
using "jloader"


jloader.invoke("examples/jloader/printer.java", "com.monsler.printer.Printer", "print", "Hello from java!", 1)
/*  
Аргументы:
1 - путь к jar;
2 - класс;
3 - метод;
4 - аргумент ( используйте Object[] как аргумент );
5 - статичность метода (0 - нет; 1 - да)
*/
````
# arrays
`arrays.combine(keys, values)` — создает массив, используя один массив для ключей, а другой — для его значений

Пример:
````java
using "arrays"

colors = ["green", "red", "yellow"]
fruits = ["avocado", "apple", "banana"]

out = arrays.combine(colors, fruits)
print(out)
````

`arrays.keyExists(key, map)` - Проверяет, существует ли в массиве заданный ключ или индекс. Если существует, возвращает 1, иначе 0.

Пример:
````java
using "arrays"
map = {
    "apple" : "red"
    "banana" : "yellow"
}
print(arrays.keyExists("apple",map))
````

`arrays.join(array, "delimiter", "prefix", "suffix")` — объединяет массив в строку с разделителем, префиксом и суффиксом.

Пример:

````java
using "arrays"
array = ["banana", "apple", "pie"]

out_array = arrays.join(array, "--")
print(out_array)

//Вывод: banana--apple--pie
````

`arrays.sort(array)` — сортирует массив.



# async
`async.supply(function)` - асинхронный способ получения или предоставления информации, когда данные поступают или отправляются не сразу, а по мере их доступности. Это означает, что вы можете запросить какие-то данные и продолжать выполнять другие задачи, пока данные еще не готовы. Когда данные станут доступны, они будут переданы вам для использования. Это помогает сделать программы более отзывчивыми и эффективными, так как они не блокируются в ожидании данных.

Пример использования:
````java
using "async"
func hel(arg){
    print(arg)
}
async.supply(hel("Hello"))
````
# std
`std.thread("function", arg)` - создает новый поток. Первым аргументом выступает название функции (тип string), однако можно использовать и ссылку на функцию (`std.thread("function", arg)`).
Второй аргумент является аргументом переданной функции.

Пример:
````java
using "std"

func message(name){
    print("Hello, " + name)
}

std.thread("message", "Artyom")
````
В этом потоке вызывается функция message с аргументом "Artyom". Функция message просто выводит приветственное сообщение с именем, переданным в качестве аргумента.

Использование потоков выполнения позволяет выполнять различные задачи параллельно, что может улучшить производительность программы. В данном случае, создается новый поток, который вызывает функцию message, тогда как основной поток может продолжать выполнять другие задачи.

`flatmap(array, mapper)` — преобразует каждый элемент массива в массив элементов.

Пример:

````java
using "std"

numbers = [1,2,3,4,5,6,7,8,9]

output = std.flatmap(numbers, func(x) {
  arr = Array(x)
  for(i = 0, i < x, i++){
    arr[i] = x
  }
  return arr
})

print(output)
````
### Обертки для классов (Добавлено с 2.7.3)

#### Integer
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

#### Double
`Double.max(arg1 = 0, arg2 = 0)` - Возвращает большее из двух значений double.

`Double.min(arg1 = 0, arg2 = 0)` - Возвращает меньшее из двух значений double.

`Double.doubleToLongBits(arg)` - функция используется для преобразования числа типа double в его битовое представление типа long



# artify
Библиотека для преобразования текста в ASCII арт.

`artify.build(text = "")` - преобразует текст с арт
Пример:
````java
using "artify"

print(artify.build("Hello World!"))
````
Вывод:
````
  _  _   ___   _      _       ___        __      __   ___    ___   _      ___    _ 
 | || | | __| | |    | |     / _ \       \ \    / /  / _ \  | _ \ | |    |   \  | |
 | __ | | _|  | |__  | |__  | (_) |       \ \/\/ /  | (_) | |   / | |__  | |) | |_|
 |_||_| |___| |____| |____|  \___/         \_/\_/    \___/  |_|_\ |____| |___/  (_)
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

# LFS
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

# system
### Библиотека для работы с системой.
`system.getProperty("property.name")` - возвращает информацию о локальной системе и конфигурации.

Пример:
````java
using "system"
println(system.getProperty("lzr.version"))
println(system.getProperty("os.name"))
println(system.getProperty("java.version"))
println(system.getProperty("file.separator"))

//Все property, которые есть в java.lang.System.getProperty()
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

# ML
### Библиотека для работы с математикой (Math Library).

`ml.abs(x)` — модуль числа x

`ml.acos(x)` — арккосинус

`ml.asin(x)`— арксинус

`ml.atan(x)` — арктангенс

`ml.atan2(y, x)` — угол θ, тангенс которого равен отношению двух указанных чисел

`ml.cbrt(x)` — кубический корень числа x

`ml.ceil(x)` — округляет вещественное число в большую сторону

`ml.cos(x)` — косинус

`ml.cosh(x)` — гиперболический косинус

`ml.floor(x)` — округляет вещественное число в меньшую сторону

`ml.getExponent(x)` — возвращают несмещенное значение экспоненты числа

`ml.hypot(x, y)` — расчёт гипотенузы sqrt(x2 + y2) без переполнения

`ml.IEEEremainder(x, y)` — возвращает остаток от деления x на y по стандарту ANSI/IEEE Std 754-1985, раздел 5.1

`ml.log(x)` — логарифм

`ml.log1p(x)` — натуральный логарифм от x + 1 (ln(x + 1))

`ml.log10(x)` — десятичный логарифм

`ml.max(x, y)` — максимальное из двух чисел

`ml.min(x, y)` — минимальное из двух чисел

`ml.pow(x, y)` — возведение x в степень y

`ml.round(x)` — округляет вещественное число до ближайшего целого

`ml.signum(x)` — возвращает целое число, указывающее знак числа

`ml.sin(x)` — синус

`ml.sinh(x)` — гиперболический синус

`ml.sqrt(x)` — квадратный корень

`ml.tan(x)` — тангенс

`ml.tanh(x)` — гиперболический тангенс

`ml.toDegrees(x)` — перевод радиан в градусы

`ml.toRadians(x)` — перевод градусов в радианы

# random
`random(from = 0, to = 10)` — возвращает псевдослучайное число.

# time
`time.sleep(time)` - приостановка текущего потока на time миллисекунд.

# colors

Упрощает работу с цветами в консоли.
Пример:
````java
using "colors"
println(sprintf(color.blue))
println("Hello")
````
### Цвета

`color.red`

`color.green`

`color.blue`

`color.white`

`color.black`

`color.purple`

`color.yellow`

`color.cyan`

`color.clear()`

# json
`json.decode(data)` - преобразует переданные данные в строку в формате json
Пример:
````java
using "json"
data = "{\"name\":Kingmang,\"digit\":2,\"array\":\"[2,6,7,7,4,6]\"}"
println(json.decode(data))
````
Результат:

`{array=[2,6,7,7,4,6], name=Kingmang, digit=2}`


