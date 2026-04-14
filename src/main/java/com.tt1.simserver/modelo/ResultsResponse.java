package com.tt1.simserver.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;


/**
 *
 */
@JsonTypeName("ResultsResponse")
public class ResultsResponse {
    private Boolean done;
    private Integer tokenSolicitud;
    private String errorMessage;
    private String data;


    /**
     *
     * @param done
     * @return
     */
    public ResultsResponse done(Boolean done) {
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
     * @param tokenSolicitud
     * @return
     */
    public ResultsResponse tokenSolicitud(Integer tokenSolicitud) {
        this.tokenSolicitud = tokenSolicitud;
        return this;
    }

    /**
     *
     * @return
     */
    @JsonProperty("tokenSolicitud")
    public Integer getTokenSolicitud() {
        return tokenSolicitud;
    }

    /**
     *
     * @param tokenSolicitud
     */
    @JsonProperty("tokenSolicitud")
    public void setTokenSolicitud(Integer tokenSolicitud) {
        this.tokenSolicitud = tokenSolicitud;
    }

    /**
     *
     * @param errorMessage
     * @return
     */
    public ResultsResponse errorMessage(String errorMessage) {
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
     * @param data
     * @return
     */
    public ResultsResponse data(String data) {
        this.data = data;
        return this;
    }

    /**
     *
     * @return
     */
    @JsonProperty("data")
    public String getData() {
        return data;
    }

    /**
     *
     * @param data
     */
    @JsonProperty("data")
    public void setData(String data) {
        this.data = data;
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
        ResultsResponse resultsResponse = (ResultsResponse) o;
        return Objects.equals(this.done, resultsResponse.done) &&
                Objects.equals(this.tokenSolicitud, resultsResponse.tokenSolicitud) &&
                Objects.equals(this.errorMessage, resultsResponse.errorMessage) &&
                Objects.equals(this.data, resultsResponse.data);
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(done, tokenSolicitud, errorMessage, data);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {

        String sb = "class ResultsResponse {\n" +
                "    done: " + toIndentedString(done) + "\n" +
                "    tokenSolicitud: " + toIndentedString(tokenSolicitud) + "\n" +
                "    errorMessage: " + toIndentedString(errorMessage) + "\n" +
                "    data: " + toIndentedString(data) + "\n" +
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

