package org.sid.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.sid.stub.Bank;
import org.sid.stub.BankServiceGrpc;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class BankGrpcClient4 {
    public static void main(String[] args) throws IOException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",5555)
                .usePlaintext()
                .build();
        // mode asynchron
        BankServiceGrpc.BankServiceStub asyncStub = BankServiceGrpc.newStub(channel);
        Bank.ConvertCurencyRequist requist = Bank.ConvertCurencyRequist.newBuilder()
                .setCurrencyFrom("MAD")
                .setCurrencyTo("USD").setAmount(6000)
                .build();

        StreamObserver<Bank.ConvertCurencyRequist> performStream = asyncStub.performStream(new StreamObserver<Bank.ConvertCurrencyResponse>() {
            @Override
            public void onNext(Bank.ConvertCurrencyResponse convertCurrencyResponse) {
                System.out.println("_____________________");
                System.out.println(convertCurrencyResponse);
                System.out.println("_____________________");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("END OF RESULT");
            }
        });
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int counter = 0;
            @Override
            public void run() {
                Bank.ConvertCurencyRequist curencyRequist = Bank.ConvertCurencyRequist.newBuilder().setAmount(Math.random()*7000).build();
              performStream.onNext(curencyRequist);
                System.out.println("===========> counter = " + counter);
              counter++;
              if (counter == 20) {
                  performStream.onCompleted();
                  timer.cancel();
              }
            }
        },1000,1000);
        System.out.println(".....?");
        System.in.read();

    }
}
