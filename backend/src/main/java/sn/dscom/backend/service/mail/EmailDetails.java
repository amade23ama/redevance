package sn.dscom.backend.service.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Annotations
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDetails {

    /** recipient */
    private String recipient;

    /** msgBody */
    private String msgBody;

    /** subject */
    private String subject;

    /** attachment */
    private String attachment;
}
