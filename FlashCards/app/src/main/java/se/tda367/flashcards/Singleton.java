package se.tda367.flashcards;

/**
 * Created by Razzan on 2016-04-21.
 */
public class Singleton {
    private static Singleton flashCards = new Singleton();

    public static Singleton getInstance() {
        return flashCards;
    }

    private Singleton() {
    }
}
