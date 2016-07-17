package com.microservicesteam.nutaxi.route.service;

import com.microservicesteam.nutaxi.route.model.RouteDetails;

public interface RouteService {

    RouteDetails<?, ?, ?> getRoute(String origin, String destination, String language);

}
