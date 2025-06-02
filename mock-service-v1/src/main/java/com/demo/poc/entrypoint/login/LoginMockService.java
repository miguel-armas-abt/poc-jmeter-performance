package com.demo.poc.entrypoint.login;

import com.demo.poc.commons.custom.config.MockService;
import org.apache.http.entity.ContentType;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.mockserver.model.HttpStatusCode;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.demo.poc.commons.custom.utils.DelayUtil.generateRandomDelay;
import static com.demo.poc.commons.custom.utils.HeadersGenerator.contentType;
import static com.demo.poc.commons.custom.utils.HeadersGenerator.generateTraceId;
import static com.demo.poc.commons.custom.utils.JsonReader.readJsonAsString;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@Component
public class LoginMockService implements MockService {

  @Override
  public void loadMocks(ClientAndServer mockServer) {

    mockServer
        .when(request()
            .withMethod("POST")
            .withPath("/poc/auth/v1/login"))
        .respond(request -> {

          long randomDelay = generateRandomDelay();
          Header traceIdHeader = generateTraceId();

          return response()
              .withStatusCode(HttpStatusCode.OK_200.code())
              .withHeader(contentType(ContentType.APPLICATION_JSON.getMimeType()))
              .withHeader(traceIdHeader)
              .withBody(readJsonAsString("mocks/login/login.200.json"))
              .withDelay(TimeUnit.MILLISECONDS, randomDelay);
        });
  }
}
