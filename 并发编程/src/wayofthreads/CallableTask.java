package wayofthreads;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 *  Callable创建线程是有返回值的
 *  可以将线程执行的结果作为返回值返回
 */
public class CallableTask implements Callable<Integer> {
    @Override
    public Integer call() throws Exception{
        return new Random().nextInt();
    }
    //创建线程池？？？？是这么用的吗
    ExecutorService service = Executors.newFixedThreadPool(10);
    //提交任务，并用Future提交返回结果
    Future<Integer> future = service.submit(new CallableTask());
}

