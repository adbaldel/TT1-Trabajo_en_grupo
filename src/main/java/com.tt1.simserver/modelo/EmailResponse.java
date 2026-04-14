package com.tt1.simserver.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

/**
 *
 */
@JsonTypeName("EmailResponse")
public class EmailResponse {
    private Boolean done;
    private String errorMessage;


    /**
     *
     * @param done
     * @return
     */
    public EmailResponse done(Boolean done) {
        this.done = done;
        return this;
    }


    /**
     *
     * @return
     */
    @JsonProperty("done")
    public Boolean getDone() {
        return done;
    }

    /**
     *
     * @param done
     */
    @JsonProperty("done")
    public void setDone(Boolean done) {
        this.done = done;
    }

    /**
     *
     * @param errorMessage
     * @return
     */
    public EmailResponse errorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    /**
     *
     * @return
     */
    @JsonProperty("errorMessage")
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     *
     * @param errorMessage
     */
    @JsonProperty("errorMessage")
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     *
     * @param o   the reference object with which to compare.
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmailResponse emailResponse = (EmailResponse) o;
        return Objects.equals(this.done, emailResponse.done) &&
                Objects.equals(this.errorMessage, emailResponse.errorMessage);
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(done, errorMessage);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {

        String sb = "class EmailResponse {\n" +
                "    done: " + toIndentedString(done) + "\n" +
                "    errorMessage: " + toIndentedString(errorMessage) + "\n" +
                "}";
        return sb;
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

