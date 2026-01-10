package game;


import java.util.Scanner;


public class InputHandler {
    // atributos

    private volatile char lastCmd = 0;
    private volatile boolean running = false;
    private Thread thread;


    // construtor

    public InputHandler() {
    }


    // métodos

    public void start() {
        if (running) {
            return;
        }

        running = true;

        thread = new Thread(() -> {
            try (Scanner sc = new Scanner(System.in)) {
                while (running) {
                    String line = sc.nextLine();
                    
                    line = line.trim();
                    if (line.length() == 0) {
                        continue;
                    }
                    
                    lastCmd = Character.toLowerCase(line.charAt(0));
                    
                    if (lastCmd == 'q') {
                        running = false;
                    }
                }
            }
        });

        thread.setDaemon(true);
        thread.start();
    }


    public char pollCommand() {
        char c = lastCmd;
        lastCmd = 0;
        return c;
    }


    public void stop() {
        running = false;

        if (thread != null) {
            thread.interrupt();
        }
    }


    // métodos especiais (getters e setters)

    public boolean getRunning() {
        return running;
    }
}
