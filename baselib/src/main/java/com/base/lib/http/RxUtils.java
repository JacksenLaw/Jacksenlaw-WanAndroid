package com.base.lib.http;

import com.base.lib.http.exception.OtherException;
import com.base.lib.model.BaseResponse;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxUtils {

    /**
     * 统一线程处理
     *
     * @param <T> 指定的泛型类型
     * @return FlowableTransformer
     */
    public static <T> FlowableTransformer<T, T> rxFlSchedulerHelper() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一线程处理
     *
     * @param <T> 指定的泛型类型
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一返回结果处理
     *
     * @param <T> 指定的泛型类型base
     * @return ObservableTransformer
     */
//return upstream.flatMap((Function<BaseResponse<T>, Observable<T>>) baseResponse -> {
//        if (baseResponse.getErrorCode() == BaseResponse.SUCCESS
//                && baseResponse.getData() != null) {
//            return createData(baseResponse.getData());
//        } else {
//            return Observable.error(new OtherException(baseResponse.getErrorMsg()));
//        }
//    });
    public static <T> ObservableTransformer<BaseResponse<T>, T> handleResult() {
        return new ObservableTransformer<BaseResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResponse<T>> upstream) {
                return upstream.flatMap(new Function<BaseResponse<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(BaseResponse<T> tBaseResponse) throws Exception {
                        if (tBaseResponse.getErrorCode() == BaseResponse.SUCCESS
                                && tBaseResponse.getData() != null) {
                            return createData(tBaseResponse.getData());
                        } else {
                            return Observable.error(new OtherException(tBaseResponse.getErrorMsg()));
                        }
                    }
                });
//                return upstream.flatMap((Function<BaseResponse<T>, Observable<T>>) baseResponse -> {
//                    if (baseResponse.getErrorCode() == BaseResponse.SUCCESS
//                            && baseResponse.getData() != null) {
//                        return createData(baseResponse.getData());
//                    } else {
//                        return Observable.error(new OtherException(baseResponse.getErrorMsg()));
//                    }
//                });
            }
        };
    }

    /**
     * 得到 Observable
     *
     * @param <T> 指定的泛型类型
     * @return Observable
     */
    private static <T> Observable<T> createData(final T t) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }


}
