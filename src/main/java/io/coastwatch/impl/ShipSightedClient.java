package io.coastwatch.impl;

import io.coastwatch.grpc.Coastwatch;
import io.coastwatch.grpc.Coastwatch.*;
import io.coastwatch.grpc.ShipSightedSVCGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

public class ShipSightedClient  {
    public static void main(String[] args) {
        System.out.println("Running the ShipSightedClient");

        ShipSightedClient x = new ShipSightedClient(
                    ManagedChannelBuilder.forAddress("localhost", 32000)
                            .usePlaintext()
                            .build());

        AntiShipMunition mun = x.ShipSighted("Clon", "tinny");
        System.out.println("Client : Munition received : Do something with it "+mun.toString());

    }


    private ShipSightedSVCGrpc.ShipSightedSVCBlockingStub blockingStub;
    private ShipSightedSVCGrpc.ShipSightedSVCStub asyncStub;

    public ShipSightedClient(Channel channel) {
        blockingStub = ShipSightedSVCGrpc.newBlockingStub(channel);
        asyncStub = ShipSightedSVCGrpc.newStub(channel);
    }

    public AntiShipMunition ShipSighted(String location, String shipType){
        AntiShipMunition munition = null;
        ShipSightedNotification shipNotification = ShipSightedNotification.newBuilder().setShipType(shipType).setLocation(location).build();

        try {
            munition = blockingStub.notify(shipNotification);
        } catch (StatusRuntimeException e) {
            //This will return a null....so do some null checking
            System.out.println("Client : Well something shit the bed.....now what!!!!! "+e.getMessage());
        }
        return munition;
    }

}

