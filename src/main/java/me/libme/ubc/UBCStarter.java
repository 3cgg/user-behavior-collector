package me.libme.ubc;

import me.libme.kernel._c.pubsub.QueuePool;
import me.libme.kernel._c.pubsub.QueuePools;
import me.libme.kernel._c.pubsub.Topic;
import me.libme.kernel._c.util.ThreadUtil;
import me.libme.xstream.ConsumerMeta;
import me.libme.xstream.QueueWindowSourcer;
import me.libme.xstream.Topology;
import me.libme.xstream.WindowTopology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by J on 2018/2/28.
 */
public class UBCStarter implements Topology{

    private static Logger LOGGER= LoggerFactory.getLogger(UBCStarter.class);

    private static ExecutorService executeExecutor= Executors.newFixedThreadPool(ThreadUtil.recommendCount(),
            r->new Thread(r,"real thread on executing topology[ubc]"));

    private static ScheduledExecutorService windowExecutor=Executors.newScheduledThreadPool(1,
            r->new Thread(r,"map-window-topology-scheduler[ubc]"));

    private Builder builder;


    @Override
    public void shutdown() {

        executeExecutor.shutdown();
        windowExecutor.shutdown();
    }

    @Override
    public void start() {


        QueuePool queuePool= QueuePools.defaultPool();

        Topic actionTopic= builder.actionTopic;// new Topic("actionTopic");
        QueueWindowSourcer queueWindowSourcer=new QueueWindowSourcer(queuePool.queue(actionTopic));

        ConsumerMeta consumerMeta=new ConsumerMeta("KeywordMatchWorker");
        ActionWorker actionWorker=new ActionWorker(consumerMeta,builder.commandFactory.factory());


        // sensitive recognize worker
        WindowTopology.builder().setName("User Behavior Collector")
                .setSourcer(queueWindowSourcer)
                .addConsumer(actionWorker)
                .windowExecutor(windowExecutor)
                .executor(executeExecutor)
                .setSchedule(builder.schedule)
                .build().start();

    }

    public static Builder builder(){
        return new Builder();
    }

    public UBCService ubcService(){
        InternalUBCService internalUBCService=new InternalUBCService(builder.actionTopic);
        return internalUBCService;
    }


    public static class Builder{

        private Topic actionTopic=new Topic("userBehaviorCollector");

        private int schedule=500;

        private CommandFactory commandFactory;

        public Builder commandFactory(CommandFactory commandFactory) {
            this.commandFactory = commandFactory;
            return this;
        }

        public Builder schedule(int schedule) {
            this.schedule = schedule;
            return this;
        }

        public Builder actionTopic(Topic actionTopic) {
            this.actionTopic = actionTopic;
            return this;
        }


        public UBCStarter build(){
            Objects.requireNonNull(actionTopic);
            Objects.requireNonNull(commandFactory);
            UBCStarter ubcStarter=new UBCStarter();
            ubcStarter.builder=this;
            return ubcStarter;
        }

    }


}
