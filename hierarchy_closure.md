1. Используем тот же пример с классами-разборщиками логов:
```java
public interface LogParser {
  void parseLine(String line);
}

public abstract class BaseLogParser implements LogParser {
  private final LineSink sink;
  protected BaseLogParser(LineSink sink) { this.sink = sink; }
  protected void emit(String msg, String ip) { sink.accept(new ParsedLine(msg, ip)); }
}

// реализация одного из фактических классов в иерархии
public class ApacheLogParser extends BaseLogParser {
  public ApacheLogParser(LineSink sink) { super(sink); }
  @Override
  public void parseLine(String line) {
    String[] parts = line.split(" ");
    if (parts.length > 6) {
      emit(parts[6], parts[0]);
    }
  }
}
```


