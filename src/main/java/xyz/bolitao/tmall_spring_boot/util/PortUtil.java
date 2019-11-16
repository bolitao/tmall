package xyz.bolitao.tmall_spring_boot.util;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author 陶波利
 */
public class PortUtil {
    private static boolean testPort(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.close();
            return false;
        } catch (IOException e) {
            return true;
        }
    }

    public static void checkPort(int port, String server) {
        if (!testPort(port)) {
            String message = String.format("在端口 %d 未检查到 %s 启动，将会退出...", port, server);
            System.out.println(message);
            System.exit(1);
        }
    }

}