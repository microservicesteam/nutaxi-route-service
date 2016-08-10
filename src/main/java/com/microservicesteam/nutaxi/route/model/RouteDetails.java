package com.microservicesteam.nutaxi.route.model;

public interface RouteDetails<REQ, RESP, ERR> {

    REQ getRequest();

    RESP getResponse();

    ERR getError();

}
