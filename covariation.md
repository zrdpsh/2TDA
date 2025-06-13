1. Ковариантность возвращаемых типов.

Имеем:
Интерфейс парсера
```java
interface LogParser {
  void parseLine(String line);
}
```
Абстрактный класс, реализующий интерфейс LogParser
```java
abstract class BaseLogParser implements LogParser {
    private final LineSink sink;
    protected BaseLogParser(LineSink sink) { this.sink = sink; }
    protected void emit(String msg, String ip) { sink.accept(new ParsedLine(msg, ip)); }
}
```
Абстрактную фабрику, использующую внутри абстрактный класс BaseLogParser
```java
abstract class ParserFactory {
    public abstract BaseLogParser create(LineSink sink);
}
```
Конкретную реализацию парсера
```java
class ApacheLogParser extends BaseLogParser {
    public ApacheLogParser(LineSink sink) { super(sink); }
    @Override public void parseLine(String line) { /* … */ }
}
```
Конкретную реализацию фабрики, использующую конкретную реализацию парсера
```java
class ApacheParserFactory extends ParserFactory {
    @Override
    public ApacheLogParser create(LineSink sink) {
        return new ApacheLogParser(sink);
    }
}
```
Смысл конструкции в том, что мы закладываем будущую вариантивность сразу в двух местах - и в классе, реализующем функциональность, и в "метаклассе", который его использует.
Любая фабрика, наследующая от ParserFactory, может использовать любой из парсеров логов, наследующих от BaseLogParser. 





2. Дженерики
Создаём список парсеров
```java
List<ApacheLogParser> apacheParsers = List.of(
    new ApacheLogParser(new StdoutSink())
);
```
Теперь его можно присваивать переменной ..
```java
List<? extends BaseLogParser> parsers = apacheParsers;
```
.. и считывать из списка, задавая конкретный тип  - т.е. мы можем быть уверены, что возвращаемое значение - один из наследников BaseLogParser, а не что-то ещё.
```java
BaseLogParser p = parsers.get(0);
```
