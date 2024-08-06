package com.tsnsched.grpc;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.stub.StreamObserver;

public class TSNschedServer {

    private static final Logger logger = Logger.getLogger(TSNschedServer.class.getName());
    private Server server;

    public static void main(String[] args)  throws IOException{
        final TSNschedServer server = new TSNschedServer();
        server.start();
    }


    private void start() throws IOException {
        int port = 50051;
        server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
                .addService(new SchedulerImpl())
                .build()
                .start();
        logger.info("Server started, listenig on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            try {
                TSNschedServer.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            System.err.println("*** server shut down");
}));
    }

    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    static class SchedulerImpl extends SchedulerGrpc.SchedulerImplBase {
        @Override
        public void createSchedule(ScheduleInput req, StreamObserver<ScheduleOutput> responseObserver) {
            ScheduleOutput rply = ScheduleOutput.newBuilder().setOutput("Return from java").build();
            responseObserver.onNext(rply);
            responseObserver.onCompleted();
        }
    }

}
