package com.longhai.moviesservice.model;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
@Builder
public class DataLoadEvent extends ApplicationEvent {
    private Object source;
    private String id;
    private String processName;
    private String fileName;

    public DataLoadEvent() {
        super("DataLoadEvent");
    }

    public DataLoadEvent(Object source, String id, String processName, String fileName) {
        super(source);
        this.id = id;
        this.processName = processName;
        this.fileName = fileName;
    }
}
