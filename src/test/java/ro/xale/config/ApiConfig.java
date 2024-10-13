package ro.xale.config;

import io.restassured.RestAssured;
import io.restassured.config.FailureConfig;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.listener.ResponseValidationFailureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiConfig {

    private static final Logger LOG = LoggerFactory.getLogger(ApiConfig.class);

    public RestAssuredConfig getConfig() {
        ResponseValidationFailureListener failureListener =
                (requestSpecification, responseSpecification, response) ->
                        LOG.info("Failed with status {} and body: {}",
                                response.getStatusCode(), response.body().asPrettyString());

        return RestAssured.config().
                logConfig(LogConfig.logConfig()
                        .enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL))
                        .failureConfig(FailureConfig.failureConfig().failureListeners(failureListener));
    }

}
