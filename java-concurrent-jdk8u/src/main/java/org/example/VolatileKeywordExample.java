package org.example;



class DoubleCheckedSingleton {
    // The single instance, initially null, marked as volatile
    private static volatile DoubleCheckedSingleton instance;

    // Private constructor to prevent instantiation
    private DoubleCheckedSingleton() {}

    // Public method to get the instance
    public static DoubleCheckedSingleton getInstance() {
        // First check (not synchronized)
        if (instance == null) {
            // Synchronize on the class object
            synchronized (DoubleCheckedSingleton.class) {
                // Second check (synchronized)
                if (instance == null) {
                    // Create the instance
                    instance = new DoubleCheckedSingleton();
                }
            }
        }
        // Return the instance (either newly created or existing)
        return instance;
    }
}





public class VolatileKeywordExample {

    private static volatile boolean sayHello = false;


    public static void main(String[] args) throws InterruptedException {
        useVolatile();
    }

    public static void useVolatile() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while(!sayHello) {

            }

            System.out.println("Hello World!");

            while(sayHello) {

            }

            System.out.println("Good Bye!");
        });

        thread.start();

        Thread.sleep(1000);
        System.out.println("Say Hello..");
        sayHello = true;

        Thread.sleep(1000);
        System.out.println("Say Bye..");
        sayHello = false;
    }


}



