package com.longhai.moviesservice.event;

import com.longhai.moviesservice.model.DataLoadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public EventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvent(DataLoadEvent event) {
            applicationEventPublisher.publishEvent(event);
    }
}
