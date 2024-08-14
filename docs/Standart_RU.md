# Стандарт LZR
    Lexer и все его реализации
    Parser и все его реализации
    TokenType
    Statement и все расширяемые им
    Expression и все расширяемые им
    LzrValue и все расширяемые им
    LzrException (собственный обработчик ошибок для ядра LZR)
 
# Реализация Lexer
## LexerImplementation
Файл: parser/parser/classes/LexerImplementation.java. 

Класс, реализующий метод `tokenize(input)`
интерфейса Lexer

````java
public final class LexerImplementation implements Lexer
````
Список символов для операторов (математических, логических), расположенных в в одну строку без знаков разделения.

```java
 private static final String OPERATOR_CHARS = "+-*/%()[]{}=<>!&|.,^~?:";
```

Массив ключевых слов String
```java
private static final String[] keywords = {..}
```

Словарь ключевых слов String - само ключевое слово представленное в виде строки; TokenType - тип токена из перечисления "TokenType".
````java
private static final Map<String, TokenType> KEYWORDS;
````
Все ключевые слова заполняются из массива keywords


Словарь операторов String - сам оператор представленный в виде строки (может занимать более 1 символа); 
TokenType - тип токена из перечисления "TokenType".
```java
private static final Map<String, TokenType> OPERATORS;
```

Все операторы задаются внутри
```java
static {...}
```

Строка поступающая на вход при анализе лексером (изначально не инициализирована).
```java
private final String input;
```
Длина входной строки (изначально не инициализирована).
```java
private final int length;
```

Лист из найденных токенов (изначально не инициализирован).

```java
private final List<Token> tokens;
```

Буфер данных для фиксации прошлого токена в некоторых случаях (изначально не инициализирован).
```java
private final StringBuilder buffer;
```

Позиция анализируемого символа. (изначально не инициализирована)

```java
private int row, col;
```


Здесь начинается анализ лексером входной строки

```java
public List<Token> tokenize() {...}
```
Анализ на число.

```java
private void tokenizeNumber() {...}
```

Анализ на шестнадцатеричное число.

```java
 private void tokenizeHexNumber(int skipped) {...}
```

Проверка на шестнадцатеричное число
```java
 private static boolean isHexNumber(char current) {...}
```

Анализ на оператор.

```java
private void tokenizeOperator() {...}
```


Анализ на ключевое слово.

```java
private void tokenizeWord() {...}
```
Анализ на расширенное слово

```java
private void tokenizeExtendedWord() {...}
```
Анализ на текст (тип данных в lazurite: string; тип данных в ядре: LzrString).

```java
private void tokenizeText() {...}
```


Анализ на комментарий
```java
 private void tokenizeComment() {...}
```

Анализ на многострочный комментарий
```java
 private void tokenizeMultilineComment() {...}
```


Проверка на часть идентификатора.

```java
private boolean isLZRIdentifierPart(char current) {...}
```

Проверка на идентификатор.

```java
private boolean isLZRIdentifier(char current) {...}
```

Очистка буфера данных.

```java
private void clearBuffer() {
    buffer.setLength(0);
}
```
Перейти к следующему символу.

```java
private char next() {...}
```

Узнать абсолютную позицию символа.

```java
private char peek(int relativePosition) {...}
```


Добавление токена в список токенов.
```java
private void addToken(TokenType type) {...}
private void addToken(TokenType type, String text) {...}
```
# TokenType

Файл: parser/parser/TokenType.java Перечисление всех токенов в lzr.

```java
public enum TokenType {...}
```

# Statement

Файл: parser/AST/Statements/Statement.java

```java
public interface Statement extends Node {...}
```
Запуск процесса выполнения Statement в ядре lzr.

```java
void execute();
```

# Expression

Файл: parser/AST/Expressions/Expression.java

```java
public interface Expression extends Node {...}
```
Запуск выполнения выражения (Expression), результат возвращается в виде значения Value.

```java
LzrValue eval();
```

# LzrValue

Файл: runtime/LzrValue.java

```java
public interface LzrValue extends Comparable<LzrValue>{...}
```

Возвращает "чистый объект" у каждой реализации он свой.
```java
Object raw();
```
Возвращает объект в виде целого числа.

```java
int asInt();
```

Возвращает объект в виде числа.

```java
double asNumber();
```

Возвращает объект в виде строки если это возможно.

```java
String asString();
```

Возвращает объект в виде масссива если это возможно.

```java
int[] asArray();
```
# LZRException

Файл: exceptions/LzrException.java

```java
open class LzrException(val type: String, message: String) : RuntimeException(message) {...}
```

Конструктор класса LzrException, где type это тип ошибки, а text это подробное описание.


