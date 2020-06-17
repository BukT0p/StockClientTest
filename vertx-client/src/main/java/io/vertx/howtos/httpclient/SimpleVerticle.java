package io.vertx.howtos.httpclient;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;

public class SimpleVerticle extends AbstractVerticle {

    @Override
    public void start() {
        WebClient client = WebClient.create(vertx);
        client
                .get(8080, "localhost", "/echo")
                .addQueryParam("redirect", "\"http://localhost\"")
                .as(BodyCodec.string())
                .send(ar -> {
                    if (ar.succeeded()) {
                        HttpResponse<String> response = ar.result();
                        System.out.println(response.body());
                    } else {
                        System.out.println("Something went wrong " + ar.cause().getMessage());
                    }
                    vertx.close();
                });
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new SimpleVerticle());
    }
}