import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 9090;

        InetSocketAddress socketAddress = new InetSocketAddress(host, port);
        final SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(socketAddress);
        boolean x = true;

        while (x) {
            try (Scanner scanner = new Scanner(System.in)) {
                final ByteBuffer in = ByteBuffer.allocate(50);
                String text;
                while (true) {
                    System.out.println("ВВЕДИТЕ ТЕКСТ:");
                    text = scanner.nextLine();
                    if ("пока".equals(text)) {
                        x = false;
                        break;
                    }
                    else {
                        socketChannel.write(ByteBuffer.wrap(text.getBytes(StandardCharsets.UTF_8)));
                        int byteCount = socketChannel.read(in);
                        System.out.println(new String(in.array(), 0, byteCount, StandardCharsets.UTF_8));
                        in.clear();
                    }
                }
            } finally {
                socketChannel.close();
            }
        }
    }
}
