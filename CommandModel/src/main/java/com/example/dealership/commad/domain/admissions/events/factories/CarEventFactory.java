package com.example.dealership.commad.domain.admissions.events.factories;

import com.example.dealership.commad.domain.admissions.events.events.CarAdmittedEvent;
import com.example.dealership.commad.domain.admissions.snapshots.CarSnapshot;

public interface CarEventFactory {
    CarAdmittedEvent createCarAdmitedEvent(CarSnapshot carSnapshot);
}