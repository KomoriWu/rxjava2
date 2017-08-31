package com.txtled.rxjava2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = (TextView) findViewById(R.id.tv);
//        Flowable.just("Hello").
//                compose(applySchedulers()).
//                subscribe((Consumer<String>) s -> Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show());
//
//        Flowable.timer(3, TimeUnit.SECONDS).
//                observeOn(AndroidSchedulers.mainThread()).
//                subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        Toast.makeText(MainActivity.this, "yanshi", Toast.LENGTH_SHORT).show();
//                    }
//                });
//        Flowable.just("Lambda").subscribe(s -> {
//            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
//        });

        countdown();
    }

    private void countdown() {
        int count = 60;
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count + 1)
                .map(aLong -> count - aLong)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription -> mTv.setTextColor(Color.RED))
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        mTv.setText(aLong+" 秒");

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        mTv.setText("结束");
                    }
                });
    }

    <T> FlowableTransformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
}
