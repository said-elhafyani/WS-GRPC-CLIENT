package org.sid.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.sid.stub.Bank;
import org.sid.stub.BankServiceGrpc;

public class BankGrpcClient1 {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",5555)
                .usePlaintext()
                .build();
        // mode synchrony
        BankServiceGrpc.BankServiceBlockingStub blockingStub = BankServiceGrpc.newBlockingStub(channel);
        Bank.ConvertCurencyRequist requist = Bank.ConvertCurencyRequist.newBuilder()
                .setCurrencyFrom("MAD").setCurrencyTo("USD").setAmount(6000)
                .build();
        Bank.ConvertCurrencyResponse convertCurrencyResponse = blockingStub.convert(requist);
        System.out.println(convertCurrencyResponse);

    }
}
