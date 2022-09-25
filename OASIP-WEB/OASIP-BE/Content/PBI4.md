# VALIDATE-INPUT-LENGTH
1. Validate compulsory fields that are not empty and the length of text fields do not exceed the field size/length.<br>
2. If a compulsory field is empty/null, response with an error code plus the field that is empty/null. If a POST data-string type is longer than the specified length, response with an error code and state which field has the length exceeded the size.
