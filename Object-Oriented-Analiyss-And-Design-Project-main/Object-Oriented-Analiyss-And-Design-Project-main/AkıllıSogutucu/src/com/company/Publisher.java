package com.company;

import java.util.ArrayList;
import java.util.List;
public class Publisher implements ISubject {
    private List<IObserver> subscribers = new
            ArrayList<>();
    @Override
    public void attach(IObserver subscriber) {
        subscribers.add(subscriber);
    }
    @Override
    public void detach(IObserver subscriber) {
        subscribers.remove(subscriber);
    }
    @Override
    public void notify(String mesaj, String derece) {
        for(IObserver subscriber: subscribers) {
            subscriber.update(mesaj, derece);
        }
    }
}