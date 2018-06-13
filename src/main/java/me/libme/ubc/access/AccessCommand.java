package me.libme.ubc.access;

import me.libme.kernel._c.json.JJSON;
import me.libme.ubc.Action;
import me.libme.ubc.Command;
import me.libme.ubc.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Created by J on 2018/5/4.
 */
public class AccessCommand implements Command<AccessAction> {

    private static final Logger LOGGER= LoggerFactory.getLogger(AccessCommand.class);

    private final String name;

    public AccessCommand(String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }

    @Override
    public void execute(AccessAction action, Context context) {
        LOGGER.info("["+name()+"]-->"+JJSON.get().format(action));

    }

    @Override
    public boolean test(Action action) {
        return action instanceof  AccessAction;
    }

    @Override
    public String name() {
        return name;
    }
}
