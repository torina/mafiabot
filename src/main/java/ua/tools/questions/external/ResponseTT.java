package ua.tools.questions.external;

import lombok.Data;
import lombok.Value;

@Value
public class ResponseTT {
    String accountId;
    String applcationId;
    String otpId;
    String number;
    Integer attemptCount;
    String otpStatus;
    String referenceId;
    String expire;
    String created;
    String timestampExpire;
    String timestampCreated;
}

