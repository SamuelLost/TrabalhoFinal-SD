package server;

import com.google.protobuf.Message;

public class Despachante {
  private static Despachante uniqueInstance;

  private Despachante() {
  }

  public static synchronized Despachante getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Despachante();
    return uniqueInstance;
  }

  public byte[] invoke(Message request) {
    return null;
  }
}