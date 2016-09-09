package com.microservicesteam.nutaxi.route.googlemaps;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GoogleMapsDirectionsRequest {

    @Getter
    private String origin;

    @Getter
    private String destination;

    @Getter
    private TravelMode mode;

    @Getter
    private Unit units;

    @Getter
    private String language;

    public DirectionsApiRequest toDirectionsApiRequest(GeoApiContext context) {
        return DirectionsApi.newRequest(context)
                .origin(origin)
                .destination(destination)
                .mode(mode)
                .units(units)
                .language(language);
    }

}
