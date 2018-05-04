package me.libme.ubc;

import me.libme.xstream.Compositer;
import me.libme.xstream.ConsumerMeta;
import me.libme.xstream.EntryTupe;
import me.libme.xstream.Tupe;

import java.util.Iterator;

/**
 * Created by J on 2018/4/22.
 */
public class ActionWorker extends Compositer {

    private final Command command;

    public ActionWorker(ConsumerMeta consumerMeta,Command command) {
        super(consumerMeta);
        this.command=command;
    }

    @Override
    protected void doConsume(Tupe tupe) throws Exception {
        Iterator iterator= tupe.iterator();
        Action action = (Action) ((EntryTupe.Entry)iterator.next()).getValue();
        command.execute(action,new Context());
    }



}
