package client;

public class Teste {
    public static void main(String[] args) {
        Proxy proxy = new Proxy();

        proxy.client.sendResquest("msg".getBytes());
        String response = new String(proxy.client.getResponse());
        System.out.println(response);
    }
}
