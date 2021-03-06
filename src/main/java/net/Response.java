package net;

import com.fasterxml.jackson.core.JsonProcessingException;
import dragon.Dragon;
import net.codes.EventCode;
import net.codes.ExitCode;
import net.codes.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class Response {
    private List<EventCode> eventCodes = new ArrayList<>();
    private ExitCode exitCode = ExitCode.OK;
    // Additional info if any
    private String info = "";
    // Return elements if any
    private List<Dragon> elements = new ArrayList<>();

    public Response event(EventCode... code) {
        // log event if not NONE
        eventCodes.addAll(Arrays.stream(code).filter(c -> c != EventCode.NONE).collect(Collectors.toList()));
        return this;
    }

    public Response exitCode(ExitCode code) {
        exitCode = code;
        return this;
    }

    public Response logResult(Result result) {
        this.exitCode = result.exitCode;
        this.eventCodes.addAll(result.eventCodes);
        this.info = result.info;
        return this;
    }

    public Response info(String info) {
        this.info += info;
        return this;
    }

    public <T extends Number> Response info(T number) {
        this.info += number;
        return this;
    }

    public Response info(Dragon dragon) {
        try {
            this.info += dragon.description();
        } catch (JsonProcessingException ignored) {}
        return this;
    }

    public Response elements(List<Dragon> dragons) {
        this.elements.addAll(dragons);
        return this;
    }

    public Response elements(Dragon... dragons) {
        this.elements.addAll(List.of(dragons));
        return this;
    }

    @Override
    public String toString() {
        return "Response{" +
                "eventCodes=" + eventCodes +
                ", exitCode=" + exitCode +
                ", info='" + info + '\'' +
                '}';
    }
// For Jackson to work properly

    public List<EventCode> getEventCodes() {
        return eventCodes;
    }

    public void setEventCodes(List<EventCode> eventCodes) {
        this.eventCodes = eventCodes;
    }

    public ExitCode getExitCode() {
        return exitCode;
    }

    public void setExitCode(ExitCode exitCode) {
        this.exitCode = exitCode;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Dragon> getElements() {
        return elements;
    }

    public void setElements(List<Dragon> elements) {
        this.elements = elements;
    }
}
