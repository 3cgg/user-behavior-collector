package test.me.libme.ubc;

import me.libme.kernel._c.util.JUniqueUtils;
import me.libme.ubc.CommandPipeline;
import me.libme.ubc.SimpleCommandFactory;
import me.libme.ubc.UBCService;
import me.libme.ubc.UBCStarter;
import me.libme.ubc.access.AccessAction;
import me.libme.ubc.access.AccessCommand;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by J on 2018/4/22.
 */
public class TestUBC {


    public static void main(String[] args) throws InterruptedException {

        CommandPipeline commandPipeline=new CommandPipeline();

        AccessCommand accessCommand=new AccessCommand("access-action");
        commandPipeline.append(accessCommand);

        SimpleCommandFactory commandFactory=new SimpleCommandFactory(commandPipeline);


        UBCStarter ubcStarter=UBCStarter.builder()
                .commandFactory(commandFactory)
                .build();

        UBCService ubcService=ubcStarter.ubcService();

        ubcStarter.start();



        for(int i=0;i<100;i++){
            AccessAction accessAction=new AccessAction();
            accessAction.setUserId(JUniqueUtils.unique());
            accessAction.setUserName("J-"+i);
            accessAction.setTargetId(JUniqueUtils.unique());
            accessAction.setTargetName("Target-"+i);
            accessAction.setTime(new Date());
            accessAction.setSource("program");
            accessAction.setDesc("only test the function by "+accessAction.getUserName());
            ubcService.submit(accessAction);
        }

        TimeUnit.SECONDS.sleep(100);


    }


}
