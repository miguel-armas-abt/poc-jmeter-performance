package com.demo.poc.entrypoint.token;

import static com.demo.poc.commons.custom.utils.DelayUtil.generateRandomDelay;
import static com.demo.poc.commons.custom.utils.HeadersGenerator.contentType;
import static com.demo.poc.commons.custom.utils.HeadersGenerator.generateTraceId;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static com.demo.poc.commons.custom.utils.JsonReader.readJsonAsString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.entity.ContentType;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.mockserver.model.HttpStatusCode;

import org.mockserver.model.Parameter;
import org.mockserver.model.ParameterBody;
import org.springframework.stereotype.Component;

import com.demo.poc.commons.custom.config.MockService;

@Component
public class TokenMockService implements MockService {

  @Override
  public void loadMocks(ClientAndServer mockServer) {

    List<Parameter> bodyParameters = new ArrayList<>();
    bodyParameters.add(new Parameter("grant_type", "password"));
    bodyParameters.add(new Parameter("username", "fake-username"));
    bodyParameters.add(new Parameter("password", "fake-password"));

    mockServer
        .when(request()
            .withMethod("POST")
            .withPath("/poc/security/v1/token")
            .withHeader(new Header("Content-Type", "application/x-www-form-urlencoded"))
            .withBody(new ParameterBody(bodyParameters)))
        .respond(request -> {

          long randomDelay = generateRandomDelay();
          Header traceIdHeader = generateTraceId();

          return response()
              .withStatusCode(HttpStatusCode.OK_200.code())
              .withHeader(contentType(ContentType.APPLICATION_JSON.getMimeType()))
              .withHeader(traceIdHeader)
              .withBody(readJsonAsString("mocks/token/token.200.json"))
              .withDelay(TimeUnit.MILLISECONDS, randomDelay);
        });
  }
}
