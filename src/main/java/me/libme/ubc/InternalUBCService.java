package me.libme.ubc;

import me.libme.kernel._c.pubsub.Produce;
import me.libme.kernel._c.pubsub.Publisher;
import me.libme.kernel._c.pubsub.QueuePools;
import me.libme.kernel._c.pubsub.Topic;

/**
 * Created by J on 2018/5/4.
 */
public class InternalUBCService implements UBCService {

    private final Produce produce;

    InternalUBCService(Topic topic) {
        Publisher publisher=new Publisher(topic, QueuePools.defaultPool());
        this.produce = publisher.produce();
    }

    @Override
    public void submit(Action action) {
        produce.produce(action);
    }


}
