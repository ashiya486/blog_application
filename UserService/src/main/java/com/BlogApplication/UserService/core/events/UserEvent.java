package com.BlogApplication.UserService.core.events;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.UUID;

@Data
@Document
public class UserEvent {
    @Id
    private UUID id;
}
