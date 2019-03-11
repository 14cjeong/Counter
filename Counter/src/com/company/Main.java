package com.company;

//We haven't worked with variables yet with threads
//We will here
public class Main {

    public static void main(String[] args) {
	Countdown countdown = new Countdown();

	CountdownThread t1 = new CountdownThread(countdown);
	t1.setName("Thread 1");
	CountdownThread t2 = new CountdownThread(countdown);
	t2.setName("Thread 2");

	t1.start();
	t2.start();



    }
}

//Notice that we don't need pubic in front of class
//because it's all in here in main.java
//in other words, there is no need to access, hence,
//lack of access modifier
class Countdown {
    //Now up to here, we've been using local variables
    //let's try instance variables
    //In this case, it is a private variable
    //which warrants a deletion of int in line 47 before i
    private int i;
    //as soon as we run the program with line 32 (and the change)
    //in line 47
    //we get a totally different output in our console
    public void doCountdown() {
        String color;

        switch(Thread.currentThread().getName()) {
            case "Thread 1":
                color = ThreadColor.ANSI_CYAN;
                break;
            case "Thread 2":
                color = ThreadColor.ANSI_PURPLE;
                break;
            default:
                color = ThreadColor.ANSI_GREEN;
        }

        for(i = 10; i>0; i--) {
            System.out.println(color + Thread.currentThread().getName() + ": i =" + i);
        }
    }
}

class CountdownThread extends Thread {
    private Countdown threadCountdown;

    public CountdownThread(Countdown countdown) {
        this.threadCountdown = countdown;
    }

    public void run() {
        threadCountdown.doCountdown();
    }

}

//Now, remember that threads access the same heap
//but each thread has their own thread stack that can
//ONLY be accessed by that specific thread

//Now local variables are stored in the thread stack
//that means that each thread has its own copy of a
//local variable, which is why when we run the program
//we have both threads counting down from 10

//In contrast, the memory required to store an object
//instance value is allocated on the heap
//in other words when multiple threads are working with
//the same object, they in fact share the same object
//so they don't have their own copy as such
//and so when one thread changes the value of one of the
//object's instance variables
//other threads will see the new value from that point forward

//how does this relate to our own program?
//Well, when we had the local variable int i
//each thread had that in their thread stack
//however when we switched to using an instance variable
//the jvm allocated the space for i when it created the
//countdown object and it did so at that point on the heap
//now threads share the heap
//so when thread one executs and changes the value of i
//and then get suspended so that thread 2 can execute
//thread 2 wil then see the new value of i, which is now,
//counted down

//also keep in mind that a for loop is actually made up of
//multiple statements
//It first assigns i=10, the checks to see if the condition is true
//when then it runs the code in the block
//once the code has run, the increment or decrement in this case
//will drop i by 1 and start the while process again
//Basically a for loop has several steps, and threads
//can actually be suspended between these steps
//which is why we have thread 1 and 2, both having i =10,
//the thread must have suspended as soon as it was assigned i-10
//then switched over to the second thread.
//Techncallly, there are multiple steps in the sout in this case
//as well, which iincludes the concatenation and the methods.

//Now, if you look at the sout, sometimes the numbers are skipped
//it only appears that way because the sout is being run
//at different suspension points
//Also, this situation is known as
//THREAD INTERFERENCE