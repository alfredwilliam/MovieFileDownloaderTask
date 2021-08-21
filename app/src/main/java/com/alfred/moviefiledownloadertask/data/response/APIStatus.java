package com.alfred.moviefiledownloadertask.data.response;

public enum APIStatus {
    IDLE(501),
    LOADING(0),
    SUCCESS(200),
    ERROR(400),
    ERRORServer(500);
    int Status;

    APIStatus(int status) {
        Status = status;
    }

    public int getStatus() {
        return Status;
    }
}
