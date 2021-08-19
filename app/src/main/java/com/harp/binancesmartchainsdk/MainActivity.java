package com.harp.binancesmartchainsdk;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    BinanceManager binanceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binanceManager = BinanceManager.getInstance();
        binanceManager.init("https://data-seed-prebsc-1-s1.binance.org:8545");
       // binanceManager.init("https://bsc-dataseed1.binance.org:443");

        binanceManager.createWallet("12345", this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(wallet -> {
                    String walletAddress = wallet.getAddress();
                    String keystore = wallet.getKeystore();
                    System.out.println("*** ***"+walletAddress);
                    Toast.makeText(this, walletAddress, Toast.LENGTH_SHORT).show();
                }, error -> {
                    System.out.println(error);
                });

        binanceManager.searchTokenByContractAddress("0xe37ab1f60987cb4ac3c27918c11412f41460ab0b",
                "0xbce86316c1891843b5fede0036de0ca01d96bbe0",
                "12345",
                this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(wallet -> {
                    String decimals = wallet.getDecimals();
                    String symbol = wallet.getSymbol();
                    System.out.println("*** ***"+symbol);
                    System.out.println("*** ***"+decimals);
                }, error -> {
                    System.out.println(error);
                });

        binanceManager.getTokenBalance("0xbce86316c1891843b5fede0036de0ca01d96bbe0", "12345", "0xe37ab1f60987cb4ac3c27918c11412f41460ab0b", this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(wallet -> {
                    System.out.println(wallet.toString());
                }, error -> {
                    System.out.println(error);
                });
    }
}