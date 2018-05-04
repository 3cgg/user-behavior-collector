package me.libme.ubc;

/**
 * Created by J on 2018/5/4.
 */
public class SimpleCommandFactory implements CommandFactory {

    private final CommandPipeline commandPipeline;

    public SimpleCommandFactory(CommandPipeline commandPipeline) {
        this.commandPipeline = commandPipeline;
    }

    @Override
    public Command factory() {
        return commandPipeline;

    }


}
