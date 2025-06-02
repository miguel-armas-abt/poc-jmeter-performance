package com.demo.poc.entrypoint.products;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.demo.poc.commons.custom.config.MockService;
import org.apache.http.entity.ContentType;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.mockserver.model.HttpStatusCode;
import org.mockserver.model.Parameter;
import org.mockserver.model.ParameterBody;

import org.springframework.stereotype.Component;

import static com.demo.poc.commons.custom.utils.DelayUtil.generateRandomDelay;
import static com.demo.poc.commons.custom.utils.HeadersGenerator.contentType;
import static com.demo.poc.commons.custom.utils.HeadersGenerator.generateTraceId;
import static com.demo.poc.commons.custom.utils.JsonReader.readJsonAsString;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@Component
public class ProductsMockService implements MockService {

  @Override
  public void loadMocks(ClientAndServer mockServer) {

    mockServer
        .when(request()
            .withMethod("GET")
            .withPath("/poc/products/v1/products")
            .withHeaders(
                new Header("Subscription-Key", "fake-subscription-key"),
                contentType(ContentType.APPLICATION_JSON.getMimeType())
            ))
        .respond(request -> {

          long randomDelay = generateRandomDelay();
          Header traceIdHeader = generateTraceId();

          return response()
              .withStatusCode(HttpStatusCode.OK_200.code())
              .withHeader(contentType(ContentType.APPLICATION_JSON.getMimeType()))
              .withHeader(traceIdHeader)
              .withBody(readJsonAsString("mocks/products/products.200.json"))
              .withDelay(TimeUnit.MILLISECONDS, randomDelay);
        });
  }
}
