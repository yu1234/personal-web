package com.yu.music.player.callback;

import reactor.core.publisher.MonoSink;

import java.util.function.Consumer;

/**
 * Created by yuliu on 2017/12/9 0009.
 */
public class Test implements Consumer<MonoSink> {

    /**
     * Performs this operation on the given argument.
     *
     * @param monoSink the input argument
     */
    @Override
    public void accept(MonoSink monoSink) {

    }

    /**
     * Returns a composed {@code Consumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code Consumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    @Override
    public Consumer<MonoSink> andThen(Consumer<? super MonoSink> after) {
        return null;
    }
}
