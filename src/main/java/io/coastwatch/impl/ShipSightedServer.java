package io.coastwatch.impl;

import io.coastwatch.grpc.Coastwatch.*;
import io.coastwatch.grpc.ShipSightedSVCGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ShipSightedServer extends ShipSightedSVCGrpc.ShipSightedSVCImplBase{
    private Server server;

    public ShipSightedServer(int port) {
        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(port);
        server = serverBuilder.addService(new ShipSightedServer())
                .build();
    }

    protected ShipSightedServer() {
    }

    /** Start serving requests. */
    public void start() throws IOException {
        server.start();
        System.out.println("Server started, listening on 32000");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                try {
                    ShipSightedServer.this.stop();
                } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                }
                System.err.println("*** server shut down");
            }
        });
    }

    /** Stop serving requests and shutdown resources. */
    public void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main method.  This comment makes the linter happy.
     */
    public static void main(String[] args) throws Exception {
        ShipSightedServer server = new ShipSightedServer(8980);
        server.start();
        server.blockUntilShutdown();
    }

    @Override
    public void notify(io.coastwatch.grpc.Coastwatch.ShipSightedNotification request,
                       io.grpc.stub.StreamObserver<io.coastwatch.grpc.Coastwatch.AntiShipMunition> responseObserver){
        responseObserver.onNext(buildMunition(request));
        responseObserver.onCompleted();
    }

    private AntiShipMunition buildMunition(ShipSightedNotification shipSighted) {

        //Do something here to build the antishipmunition and then send it back

        AntiShipMunition resp = AntiShipMunition.newBuilder().setMunitionType("munition name").build();
        return resp;
    }
}
