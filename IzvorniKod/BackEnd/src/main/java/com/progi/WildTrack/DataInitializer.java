package com.progi.WildTrack;

import com.progi.WildTrack.domain.Description;
import com.progi.WildTrack.service.ClientService;
import com.progi.WildTrack.service.StationService;
import com.progi.WildTrack.service.StatusService;
import com.progi.WildTrack.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer {
    @Autowired
    StationService stationService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private ClientService clientService;
    private final List<String> vehicleTypes = Arrays.asList("hodanje", "dron", "auto", "brod", "motor", "helikopter");
    private final List<Description> statusTypes = Arrays.asList(Description.PENDING, Description.ACCEPTED, Description.REJECTED);
    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        for (String vehicleType : vehicleTypes) {
            vehicleService.createVehicle(vehicleType);
        }
        for (Description statusType : statusTypes) {
            statusService.createStatus(statusType);
        }
        stationService.createStations();
        clientService.createAdmin();

    }
}

