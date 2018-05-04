package me.libme.ubc;

/**
 * Created by J on 2018/5/4.
 */
public interface Command<T extends Action> {


    void execute(T action,Context context);


    default boolean test(T action){
        return true;
    }


    default String name(){
        throw new IllegalStateException();
    }

}
