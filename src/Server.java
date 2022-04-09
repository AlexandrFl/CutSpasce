import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Server {

    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 9090;
        final ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress(host, port));
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        boolean x = true;
        while (x) {
            try (SocketChannel socketChannel = serverSocket.accept()) {
                final ByteBuffer in = ByteBuffer.allocate(50);

                while (socketChannel.isConnected()) {
                    int bytesCount = socketChannel.read(in);
                    if (bytesCount == -1) {
                        x = false;
                        break;
                    }
                    final String text = new String(in.array(), 0, bytesCount, StandardCharsets.UTF_8);
                    MyThread myThread = new MyThread(text);
                    Future<String> future = executorService.submit(myThread::cutSpace);
                    String result = future.get();
                    socketChannel.write(ByteBuffer.wrap(("\nОТВЕТ:\n" + result).getBytes(StandardCharsets.UTF_8)));
                    in.clear();
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
