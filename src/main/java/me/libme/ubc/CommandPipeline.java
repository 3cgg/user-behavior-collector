package me.libme.ubc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by J on 2018/5/4.
 */
public class CommandPipeline implements Command {


    private List<Command> commands=new ArrayList<>();

    /**
     * test if the command already exists, throw exception if true.
     * append new element in the tail .
     * @param command
     */
    public synchronized void append(Command command){

        boolean exist=commands.stream().filter(test ->test.name().equals(command.name()) )
                .count()>0;
        if(exist){
            throw new IllegalStateException(command.name()+" already exists.");
        }else{
            commands.add(command);
        }
    }

    public synchronized void remove(String name){
        Objects.requireNonNull(name);
        commands.removeIf(command -> name.equals(command.name()));
    }

    @Override
    public void execute(Action action, Context context) {

        commands.forEach(command -> {
            if(command.test(action)){
                command.execute(action, context);
            }
        });

    }

    @Override
    public String name() {
        return "Global Command Pipeline";
    }


}
