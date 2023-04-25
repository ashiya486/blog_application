package com.BlogApplication.blogService.core.exceptions;

import java.time.LocalDateTime;

public class ErrorDetails   {
    private String message;
        private LocalDateTime timeStamp;

        public String getMessage() {
            return message;
        }

        public ErrorDetails(String message, LocalDateTime timeStamp) {
            super();
            this.message = message;
            this.timeStamp = timeStamp;
        }

        public LocalDateTime getTimeStamp() {
            return timeStamp;
        }
        public void setTimeStamp(LocalDateTime timeStamp) {
            this.timeStamp = timeStamp;
        }
        public void setMessage(String message) {
            this.message = message;
        }
}
