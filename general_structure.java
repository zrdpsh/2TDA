public abstract class General implements Cloneable, Serializable {

  // здесь мы глубоко копируем содержимое
  public void deepCopyTo(General target) extends Object throws IOException, ClassNotFoundException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream out = new ObjectOutputStream(bos);
    out.writeObject(this);

    ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
    ObjectInputStream in = new ObjectInputStream(bis);
    General copy = (General) in.readObject();

    target.copyFrom(copy);
  }

  // нужен для работы deepCopyTo, можно переопределять
  protected void copyFrom(General trgt) {
  }

  // глубокое клонирование
  public General deepClone() throws IOException, ClassNotFoundException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream out = new ObjectOutputStream(bos);
    out.writeObject(this);

    ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
    ObjectInputStream in = new ObjectInputStream(bis);
    return (General) in.readObject();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || this.getClass() != obj.getClass()) return false;
    return this.hashCode() == obj.hashCode(); 
  }

  // глубокое сравнение
  public boolean deepEquals(Object a, Object b) {
        return deepEquals(a, b, new HashSet<>());
    }

    private boolean deepEquals(Object a, Object b, Set<VisitedPair> visited) {
        if (a == b) return true;
        if (a == null || b == null) return false;
        if (a.getClass() != b.getClass()) return false;

        VisitedPair pair = new VisitedPair(a, b);
        if (visited.contains(pair)) return true;

        visited.add(pair);

        if (a.getClass().isArray()) {
            int len = Array.getLength(a);
            if (len != Array.getLength(b)) return false;
            for (int i = 0; i < len; i++) {
                if (!deepEquals(Array.get(a, i), Array.get(b, i), visited)) return false;
            }
            return true;
        }

        if (a instanceof Collection<?> colA && b instanceof Collection<?> colB) {
            if (colA.size() != colB.size()) return false;
            Iterator<?> itA = colA.iterator();
            Iterator<?> itB = colB.iterator();
            while (itA.hasNext()) {
                if (!deepEquals(itA.next(), itB.next(), visited)) return false;
            }
            return true;
        }

        if (a instanceof Map<?, ?> mapA && b instanceof Map<?, ?> mapB) {
            if (mapA.size() != mapB.size()) return false;
            for (Object key : mapA.keySet()) {
                if (!mapB.containsKey(key)) return false;
                if (!deepEquals(mapA.get(key), mapB.get(key), visited)) return false;
            }
            return true;
        }

        if (isPrimitiveOrWrapper(a.getClass()) || a instanceof String) {
            return a.equals(b);
        }

        for (Field field : a.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object valA = field.get(a);
                Object valB = field.get(b);
                if (!deepEquals(valA, valB, visited)) return false;
            } catch (IllegalAccessException e) {
                return false;
            }
        }

        return true;
    }

    private boolean isPrimitiveOrWrapper(Class<?> clazz) {
        return clazz.isPrimitive()
            || clazz == Integer.class || clazz == Long.class || clazz == Boolean.class
            || clazz == Byte.class || clazz == Character.class || clazz == Float.class
            || clazz == Double.class || clazz == Short.class;
    }

    private static class VisitedPair {
        private final Object a, b;

        VisitedPair(Object a, Object b) {
            this.a = a;
            this.b = b;
        }

        public boolean equals(Object o) {
            if (!(o instanceof VisitedPair p)) return false;
            return (a == p.a && b == p.b) || (a == p.b && b == p.a);
        }

        public int hashCode() {
            return System.identityHashCode(a) ^ System.identityHashCode(b);
        }
    }

  // сериализация
  public String serialize() throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream out = new ObjectOutputStream(bos);
    out.writeObject(this);
    return java.util.Base64.getEncoder().encodeToString(bos.toByteArray());
  }

  // десериализация
  public static General deserialize(String data) throws IOException, ClassNotFoundException {
    byte[] bytes = java.util.Base64.getDecoder().decode(data);
    ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes));
    return (General) in.readObject();
  }

  // наглядное представление содержимого объекта
  @Override
  public String toString() {
      StringBuilder result = new StringBuilder();
      result.append(getClass().getSimpleName()).append(" {");
  
      for (Field field : getClass().getDeclaredFields()) {
          field.setAccessible(true);
          try {
              result.append(field.getName()).append("=").append(field.get(this)).append(", ");
          } catch (IllegalAccessException e) {
              result.append(field.getName()).append("=<inaccessible>, ");
          }
      }
      if (result.charAt(sb.length() - 2) == ',') {
          result.setLength(sb.length() - 2); 
      }
  
      result.append("}");
      return result.toString();
  }


  // проверка типа
  public boolean isInstanceOf(Class<?> cls) {
      return cls.isInstance(this);
  }  

  // получение реального типа объекта
  public Class<?> getRealType() {
      return this.getClass();
  }
}

// класс, от которого можно наследоваться
public class Any extends General {
    // пока пусто
}


  


  

