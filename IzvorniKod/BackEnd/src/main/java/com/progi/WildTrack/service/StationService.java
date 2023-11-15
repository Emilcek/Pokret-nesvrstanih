package com.progi.WildTrack.service;

import com.progi.WildTrack.domain.Station;

import java.util.List;

public interface StationService {
    Station getStation();

    List<Station> getAllStations();
}
