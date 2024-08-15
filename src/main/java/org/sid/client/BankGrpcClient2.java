package org.sid.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.sid.stub.Bank;
import org.sid.stub.BankServiceGrpc;

import java.io.IOException;

public class BankGrpcClient2 {
    public static void main(String[] args) throws IOException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",5555)
                .usePlaintext()
                .build();
        // mode asynchron
        BankServiceGrpc.BankServiceStub asyncStub = BankServiceGrpc.newStub(channel);
        Bank.ConvertCurencyRequist requist = Bank.ConvertCurencyRequist.newBuilder()
                .setCurrencyFrom("MAD").setCurrencyTo("USD").setAmount(6000)
                .build();
        asyncStub.convert(requist,new StreamObserver<Bank.ConvertCurrencyResponse>() {
            @Override
            public void onNext(Bank.ConvertCurrencyResponse convertCurrencyResponse) {
                System.out.println("***********************");
                System.out.println(convertCurrencyResponse);
                System.out.println("***********************");
            }

            @Override
            public void onError(Throwable throwable) {
               System.out.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("End....");
            }
        });

        System.out.println(".....?");
        System.in.read();

    }
}