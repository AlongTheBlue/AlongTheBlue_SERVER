package org.alongtheblue.alongtheblue_server.global.data.accommodation;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Accommodation {
    @Id
    private String contentsid;
    private String title;
    private String roadaddress;
    private String introduction;
    private String infocenter;
    private String restdate;
}
