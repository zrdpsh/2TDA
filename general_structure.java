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

