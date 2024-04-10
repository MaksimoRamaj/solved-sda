package Reset.Threads.ProducerConsumer;

public class Queue {
    int n;
    boolean isSet = false;
    synchronized void put(int n){
        while (isSet){
            try {
                wait();
            }catch (InterruptedException | IllegalMonitorStateException e){
                System.out.println(e);
            }
        }
        //if false
        this.n = n;
        System.out.println("Put: " + this.n);
        isSet = true;
        notify();
    }

    synchronized int get(){
        while (!isSet){
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        System.out.println("Got: " + this.n);
        isSet = false;
        notify();
        return n;
    }
}

class Producer implements Runnable{
    Queue q;
    Thread t;

    public Producer(Queue q) {
        this.q = q;
        this.t = new Thread(this);
    }

    @Override
    public void run() {
        int i = 0;
        while (true){
            q.put(i++);
        }
    }
}

class Consumer implements Runnable{
    Queue q;
    Thread t;

    public Consumer(Queue q) {
        this.q = q;
        this.t = new Thread(this);
    }

    @Override
    public void run() {
        while (true){
            q.get();
        }
    }
}

class Demo{
    public static void main(String[] args) {
        Queue q = new Queue();
        Producer p = new Producer(q);
        Consumer c = new Consumer(q);

        p.t.start();
        c.t.start();
    }
}
